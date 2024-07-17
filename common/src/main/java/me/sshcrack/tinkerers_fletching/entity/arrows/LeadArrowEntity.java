package me.sshcrack.tinkerers_fletching.entity.arrows;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.client.sound.LeadSoundInstance;
import me.sshcrack.tinkerers_fletching.duck.LeashDataDuck;
import me.sshcrack.tinkerers_fletching.duck.SneakNotifierDuck;
import me.sshcrack.tinkerers_fletching.mixin.LivingEntityAccessor;
import me.sshcrack.tinkerers_fletching.mixin.PersistentProjectileEntityAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.ElytraSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Leashable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LeadArrowEntity extends PersistentProjectileEntity implements SneakNotifierDuck.SneakListener {
    public static final double PULL_SPEED = 0.08;
    public static final double MAX_HORIZONTAL_DISTANCE = Math.pow(3, 2);
    public static final double MAX_VERTICAL_DISTANCE = 20;

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
        if (getOwner() == null)
            return;


        var entity = entityHitResult.getEntity();
        if (!this.getWorld().isClient() && entity instanceof Leashable leashable) {
            leashable.attachLeash(this.getOwner(), true);
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

        var owner = getOwner();
        if (!(owner instanceof LivingEntity living))
            return;


        if (getWorld().isClient && age == 1) {
            var client = MinecraftClient.getInstance();
            if (living == client.player) {
                if (living instanceof ClientPlayerEntity p)
                    client.getSoundManager().play(new LeadSoundInstance(p, this));
                Text text = Text.translatable("mount.onboard", client.options.sneakKey.getBoundKeyLocalizedText());

                client.inGameHud.setOverlayMessage(text, false);
                client.getNarratorManager().narrate(text);
            }
        }

        var horizontalDistance = getHorizontalDistanceSquared(living);
        applyLeashElasticity(living, this, Math.sqrt(horizontalDistance) + 5d);
        ((PersistentProjectileEntityAccessor) this).tinkerers$setLife(0);

        if (age % 5 == 0 && !inGround && getWorld().isClient && living instanceof PlayerEntity p)
            getWorld().playSound(
                    p, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.ENTITY_LEASH_KNOT_PLACE, SoundCategory.BLOCKS,
                    0.5F, 1.0F / (getWorld().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );
/*
        var isJumping = ((LivingEntityAccessor) living).tinkerers$getJumping();
        if (!isJumping || !inGround) {
            living.setNoGravity(false);
            return;
        }

        ((PersistentProjectileEntityAccessor) this).tinkerers$setLife(0);


        if (horizontalDistance > MAX_HORIZONTAL_DISTANCE) {
            living.setNoGravity(false);
            return;
        }

        var diffVec = living.getPos().subtract(getPos()).normalize();
        living.setNoGravity(true);
        var length = living.getVelocity().length();

        living.setVelocity(living.getVelocity().add(diffVec.multiply(-PULL_SPEED)).normalize().multiply(length));


        if (age % 20 == 0 && getWorld().isClient && living instanceof PlayerEntity p)
            getWorld().playSound(
                    p, p.getX(), p.getY(), p.getZ(),
                    SoundEvents.BLOCK_LADDER_STEP, SoundCategory.BLOCKS,
                    0.5F, 1.0F / (getWorld().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );*/
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
        unregisterOwner(getOwner());
        if (getWorld().isClient && getRemovalReason() == RemovalReason.DISCARDED)
            getWorld().playSound(
                    null, getX(), getY(), getZ(),
                    SoundEvents.BLOCK_LADDER_BREAK, SoundCategory.BLOCKS,
                    0.15F, 1.0F / (getWorld().getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F
            );
    }

    private void unregisterOwner(@Nullable Entity owner) {
        if (owner == null)
            return;

        var duck = (SneakNotifierDuck) owner;

        duck.tinkerrers$removeSneakListener(this);
        owner.setNoGravity(false);
    }

    private void registerOwner(@Nullable Entity owner) {
        if (owner == null)
            return;


        if (getWorld().isClient) {
            var client = MinecraftClient.getInstance();
            if (owner == client.player) {
                if (owner instanceof ClientPlayerEntity p)
                    client.getSoundManager().play(new LeadSoundInstance(p, this));
            }
        }

        var duck = (SneakNotifierDuck) owner;
        duck.tinkerers$addSneakListener(this);
    }

    @Override
    public void setOwner(@Nullable Entity newOwner) {
        unregisterOwner(getOwner());


        super.setOwner(newOwner);
        pickupType = PickupPermission.DISALLOWED;

        registerOwner(newOwner);

        if (newOwner == null)
            this.discard();
    }

    @Override
    public ActionResult onSneakChange(Entity entity, boolean isSneaking) {
        if (entity != getOwner())
            return ActionResult.PASS;

        if (isSneaking)
            setOwner(null);

        return ActionResult.CONSUME;
    }
}
