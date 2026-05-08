package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class StormChargeEntity extends ExplosiveProjectileEntity {
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    private static final float EXPLOSION_POWER = 3f;

    public StormChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(TinkerersEntities.STORM_CHARGE.get(), x, y, z, world);
        this.setOwner(player);
        accelerationPower = 0;
    }

    public StormChargeEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(TinkerersEntities.STORM_CHARGE.get(), x, y, z, velocity, world);
        accelerationPower = 0;
    }

    public StormChargeEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
        accelerationPower = 0;
    }

    @Override
    protected Box calculateBoundingBox() {
        float f = this.getType().getDimensions().width() / 2.0f;
        float g = this.getType().getDimensions().height();
        return new Box(this.getPos().x - f, this.getPos().y - 0.15f, this.getPos().z - f, this.getPos().x + f, this.getPos().y - 0.15f + g, this.getPos().z + f);
    }


    @Override
    public boolean collidesWith(Entity other) {
        if (other instanceof StormChargeEntity) {
            return false;
        }
        return super.collidesWith(other);
    }

    @Override
    protected Vec3d adjustMovementForCollisions(Vec3d movement) {
        Box box = this.getBoundingBox();
        List<VoxelShape> list = this.getWorld().getEntityCollisions(this, box.stretch(movement));
        Vec3d vec3d = movement.lengthSquared() == 0.0 ? movement : adjustMovementForCollisions(this, movement, box, this.getWorld(), List.of());
        boolean bl = movement.x != vec3d.x;
        boolean bl2 = movement.y != vec3d.y;
        boolean bl3 = movement.z != vec3d.z;
        boolean bl4 = bl2 && movement.y < 0.0;
        if (this.getStepHeight() > 0.0F && (bl4 || this.isOnGround()) && (bl || bl3)) {
            Box box2 = bl4 ? box.offset(0.0, vec3d.y, 0.0) : box;
            Box box3 = box2.stretch(movement.x, (double)this.getStepHeight(), movement.z);
            if (!bl4) {
                box3 = box3.stretch(0.0, -1.0E-5F, 0.0);
            }

            List<VoxelShape> list2 = findCollisionsForMovement(this, this.getWorld(), list, box3)
                    .stream()
                    .filter(e -> e == VoxelShapes.fullCube() || VoxelShapes.unionCoversFullCube(e,e) )
                    .toList();

            float f = (float)vec3d.y;
            float[] fs = collectStepHeights(box2, list2, this.getStepHeight(), f);

            for (float g : fs) {
                Vec3d vec3d2 = adjustMovementForCollisions(new Vec3d(movement.x, (double)g, movement.z), box2, list2);
                if (vec3d2.horizontalLengthSquared() > vec3d.horizontalLengthSquared()) {
                    double d = box.minY - box2.minY;
                    return vec3d2.add(0.0, -d, 0.0);
                }
            }
        }

        return vec3d;
    }

        @Override
    protected boolean canHit(Entity entity) {
        if (entity instanceof StormChargeEntity) {
            return false;
        }
        if (entity.getType() == EntityType.END_CRYSTAL) {
            return false;
        }
        return super.canHit(entity);
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        if (this.getWorld().isClient)
            return;

        Entity ownerEntity = this.getOwner();
        LivingEntity livingOwner = ownerEntity instanceof LivingEntity living ? living : null;
        Entity hitEntity = entityHitResult.getEntity();

        if (livingOwner != null)
            livingOwner.onAttacking(hitEntity);

        DamageSource damageSource = this.getDamageSources().windCharge(this, livingOwner);
        if (hitEntity.damage(damageSource, 1.0f) && hitEntity instanceof LivingEntity hitLiving)
            EnchantmentHelper.onTargetDamaged((ServerWorld) this.getWorld(), hitLiving, damageSource);

        this.pushEntitiesUp(hitEntity, this.getPos());
    }

    @Override
    protected double getGravity() {
        return 0.08;
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();

        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        if (this.isSubmergedIn(FluidTags.WATER)) {
            this.applyWaterMovement();
        } else {
            this.applyGravity();
        }
        if (this.getWorld().getFluidState(this.getBlockPos()).isIn(FluidTags.LAVA)) {
            this.setVelocity((this.random.nextFloat() - this.random.nextFloat()) * 0.2f, 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
        }

        var v = getVelocity().multiply(2);
        if (Math.abs(v.x) < 0.1 && Math.abs(v.z) < 0.1 && !getWorld().isClient) {
            this.discard();
            return;
        }

        Box box = this.getBoundingBox();
        Iterable<VoxelShape> list = this.getWorld().getBlockCollisions(this, box.stretch(v.x, 0, v.z));
        if (list.iterator().hasNext()) {
            addVelocity(0, 0.3, 0);
        }

        this.move(MovementType.SELF, this.getVelocity());
    }

    private void applyWaterMovement() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * 0.99f, Math.min(vec3d.y + 5.0E-4f, 0.06f), vec3d.z * 0.99f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return false;
    }

    protected void pushEntitiesUp(Entity hitEntity, Vec3d pos) {
        hitEntity.addVelocity(0.3 * random.nextFloat(), 0.5 + 0.2 * random.nextFloat(), 0.3 * random.nextFloat());
        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
    }

    @Override
    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float vX = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float vY = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float vZ = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);

        this.setVelocity(vX, vY, vZ, speed, divergence);
    }
}
