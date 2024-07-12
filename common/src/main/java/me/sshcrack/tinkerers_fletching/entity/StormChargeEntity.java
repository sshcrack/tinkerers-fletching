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
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public class StormChargeEntity extends ExplosiveProjectileEntity {
    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    private static final float EXPLOSION_POWER = 3f;

    public StormChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(TinkerersEntities.STORM_CHARGE.get(), x, y, z, world);
        this.setOwner(player);
    }

    public StormChargeEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(TinkerersEntities.STORM_CHARGE.get(), x, y, z, velocity, world);
    }

    public StormChargeEntity(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
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
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.getWorld().isClient)
            return;

        if (blockHitResult.getSide() == Direction.UP) {
            return;
        }
/* calc, don't use for now
        Vec3i sideDirectionVec = blockHitResult.getSide().getVector();
        Vec3d middleVec = Vec3d.of(sideDirectionVec).multiply(0.25, 0.25, 0.25);
        Vec3d middlePos = blockHitResult.getPos().add(middleVec);
*/
        //this.discard();
    }

    @Override
    protected double getGravity() {
        return 0.08;
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            //this.discard();
        }
    }

    @Override
    protected boolean isBurning() {
        return false;
    }

    @Override
    public void tick() {
        var world = getWorld();
        if (!world.isClient && this.getBlockY() > world.getTopY() + 30) {
            this.discard();
            return;
        }

        super.tick();

        this.prevX = this.getX();
        this.prevY = this.getY();
        this.prevZ = this.getZ();
        if (this.isSubmergedIn(FluidTags.WATER)) {
            this.applyWaterMovement();
        } else {
            this.applyGravity();
        }
        if (world.getFluidState(this.getBlockPos()).isIn(FluidTags.LAVA)) {
            this.setVelocity((this.random.nextFloat() - this.random.nextFloat()) * 0.2f, 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f);
        }


        var v = getVelocity();
        var futurePos = getPos().add(v.x, 0, v.z);
        var futureBlockPos = new BlockPos((int) futurePos.x, (int) futurePos.y, (int) futurePos.z);
        var futureBlock = world.getBlockState(futureBlockPos);
        if (futureBlock.isFullCube(world, futureBlockPos)) {
            var aboveBlock = world.getBlockState(futureBlockPos.up());
            if (!aboveBlock.isFullCube(world, futureBlockPos.up())) {
                //addVelocity(0, 0.4, 0);
            }
        }

        this.move(MovementType.SELF, this.getVelocity());
        setVelocity(getVelocity().multiply(0.7));
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
        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
    }

    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float vX = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
        float vY = -MathHelper.sin((pitch + roll) * 0.017453292F);
        float vZ = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);

        this.setVelocity(vX, vY, vZ, speed, divergence);
        setVelocity(getVelocity().multiply(0.5 * 0.5));
    }
}
