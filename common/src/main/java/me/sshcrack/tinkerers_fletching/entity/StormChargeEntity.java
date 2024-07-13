package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
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
        return new Box(this.getPos().x - (double) f, this.getPos().y - (double) 0.15f, this.getPos().z - (double) f, this.getPos().x + (double) f, this.getPos().y - (double) 0.15f + (double) g, this.getPos().z + (double) f);
    }


    @Override
    public boolean collidesWith(Entity other) {
        if (other instanceof StormChargeEntity) {
            return false;
        }
        return super.collidesWith(other);
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
        this.setVelocity(vec3d.x * (double) 0.99f, Math.min(vec3d.y + (double) 5.0E-4f, (double) 0.06f), vec3d.z * (double) 0.99f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return false;
    }

    protected void pushEntitiesUp(Entity hitEntity, Vec3d pos) {
        hitEntity.addVelocity(0.3 * random.nextFloat(), 0.5 + 0.2 * random.nextFloat(), 0.3 * random.nextFloat());
        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
    }

    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float vX = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float vY = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float vZ = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);

        this.setVelocity(vX, vY, vZ, speed, divergence);
    }
}
