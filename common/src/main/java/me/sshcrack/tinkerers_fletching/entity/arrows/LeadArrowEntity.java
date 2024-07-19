package me.sshcrack.tinkerers_fletching.entity.arrows;

import dev.architectury.networking.NetworkManager;
import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.TinkerersStatuses;
import me.sshcrack.tinkerers_fletching.duck.CustomBowVelocity;
import me.sshcrack.tinkerers_fletching.duck.LeashDataDuck;
import me.sshcrack.tinkerers_fletching.duck.SneakNotifierDuck;
import me.sshcrack.tinkerers_fletching.mixin.PersistentProjectileEntityAccessor;
import me.sshcrack.tinkerers_fletching.packet.DetachLeashC2SPacket;
import me.sshcrack.tinkerers_fletching.packet.RequestLeashAttachmentC2SPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LeadArrowEntity extends PersistentProjectileEntity implements SneakNotifierDuck.SneakListener, CustomBowVelocity {
    private boolean requestedData = false;

    /**
     * If not null, the player to be notified
     */
    @Nullable
    private ServerPlayerEntity notifyPlayer;

    @Nullable
    private LivingEntity attachedTo;
    private UUID attachedToUuid;

    public LeadArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public LeadArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.LEAD_ARROW.get(), x, y, z, world, stack, shotFrom);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    public LeadArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.LEAD_ARROW.get(), owner, world, stack, shotFrom);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return TinkerersItems.LEAD_ARROW.get().getDefaultStack();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (getRopeAttachedTo() == null)
            return;


        var entity = entityHitResult.getEntity();
        if (!this.getWorld().isClient() && entity instanceof Leashable leashable) {
            leashable.attachLeash(this.getRopeAttachedTo(), true);
            var data = LeashDataDuck.class.cast(leashable.getLeashData());
            //noinspection ConstantValue
            assert data != null : "LeashData is null";

            //TODO this doesn't work
            data.tinkerers$setNoLeadDrop(true);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if(notifyPlayer != null) {
            notifyPlayer.networkHandler.sendPacket(new EntityStatusS2CPacket(this, TinkerersStatuses.PLAY_SOUND));
            notifyPlayer.networkHandler.sendPacket(new EntityStatusS2CPacket(this, TinkerersStatuses.SEND_ATTACHED_NOTICE));

            notifyPlayer = null;
        }

        if (!requestedData && getWorld().isClient) {
            NetworkManager.sendToServer(new RequestLeashAttachmentC2SPacket(getId()));
            requestedData = true;
            return;
        }

        var living = getRopeAttachedTo();
        if (living == null)
            return;

        var horizontalDistance = getHorizontalDistanceSquared(living);
        applyLeashElasticity(living, this, Math.sqrt(horizontalDistance) + 5d);
        living.limitFallDistance();
        ((PersistentProjectileEntityAccessor) this).tinkerers$setLife(0);
    }

    /**
     * Copied from {@link Leashable#applyLeashElasticity(Entity, Entity, float)}
     */
    @SuppressWarnings("JavadocReference")
    private static void applyLeashElasticity(Entity entity, Entity leashHolder, double distance) {
        double deltaX = (leashHolder.getX() - entity.getX()) / distance;
        double deltaY = (leashHolder.getY() - entity.getY()) / distance;
        double deltaZ = (leashHolder.getZ() - entity.getZ()) / distance;
        entity.setVelocity(entity.getVelocity().add(Math.copySign(deltaX * deltaX * 0.4, deltaX), Math.copySign(deltaY * deltaY * 0.4, deltaY), Math.copySign(deltaZ * deltaZ * 0.4, deltaZ)));
    }

    private double getHorizontalDistanceSquared(Entity living) {
        var x = living.getX() - getX();
        var z = living.getZ() - getZ();
        return x * x + z * z;
    }

    @Override
    public boolean shouldRender(double distance) {
        var o = getOwner();
        if (o != null) {
            ignoreCameraFrustum = true;
            return true;
        }

        ignoreCameraFrustum = false;
        return super.shouldRender(distance);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        unregisterAttachment(getOwner());
        if (getWorld().isClient && getRemovalReason() == RemovalReason.DISCARDED)
            getWorld().playSound(
                    null, getX(), getY(), getZ(),
                    SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.BLOCKS,
                    0.15F, 1.0F / (getWorld().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );
    }

    private void unregisterAttachment(@Nullable Entity attachedEntity) {
        if (attachedEntity == null)
            return;

        var duck = (SneakNotifierDuck) attachedEntity;

        duck.tinkerrers$removeSneakListener(this);
    }

    private void registerAttachment(@Nullable Entity attachedEntity) {
        if (attachedEntity == null)
            return;


        if (attachedEntity instanceof ServerPlayerEntity pEntity)
            notifyPlayer = pEntity;


        var duck = (SneakNotifierDuck) attachedEntity;
        duck.tinkerers$addSneakListener(this);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (attachedToUuid != null)
            nbt.putUuid("RopeAttachedTo", attachedToUuid);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.containsUuid("RopeAttachedTo"))
            attachedToUuid = nbt.getUuid("RopeAttachedTo");
    }

    @Nullable
    public LivingEntity getRopeAttachedTo() {
        if (attachedTo != null && attachedToUuid != null && attachedTo.getUuid().compareTo(attachedToUuid) != 0) {
            attachedTo = null;
        }

        if (this.attachedTo == null && this.attachedToUuid != null) {
            if (this.getWorld() instanceof ServerWorld sWorld) {
                Entity entity = sWorld.getEntity(this.attachedToUuid);
                if (entity instanceof LivingEntity l) {
                    this.attachedTo = l;
                }
            }
        }

        return this.attachedTo;
    }

    public void setRopeAttachedTo(@Nullable LivingEntity attachedTo) {
        unregisterAttachment(this.attachedTo);

        this.attachedTo = attachedTo;
        registerAttachment(attachedTo);

        if (attachedTo == null)
            this.discard();
    }

    public void sendDetachRope() {
        if (!getWorld().isClient) {
            TinkerersMod.LOGGER.info("Tried to call detach rope server sided");
            return;
        }

        NetworkManager.sendToServer(new DetachLeashC2SPacket(this.getId()));
    }

    @Override
    public ActionResult onSneakChange(Entity entity, boolean isSneaking) {
        if (entity != getRopeAttachedTo() || !getWorld().isClient)
            return ActionResult.PASS;

        if (isSneaking)
            sendDetachRope();

        return ActionResult.CONSUME;
    }

    @Override
    public float getBowVelocityMultiplier(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        return 0.5f;
    }

    public boolean isInGround() {
        return inGround;
    }
}
