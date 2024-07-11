package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ProjectileDeflection;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractWindChargeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.AdvancedExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;
import java.util.function.Function;

public class StormChargeEntity extends AbstractWindChargeEntity implements GeoEntity {
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);


    private static final ExplosionBehavior EXPLOSION_BEHAVIOR = new AdvancedExplosionBehavior(true, false, Optional.of(1.22F), Registries.BLOCK.getEntryList(BlockTags.BLOCKS_WIND_CHARGE_EXPLOSIONS).map(Function.identity()));
    private static final float EXPLOSION_POWER = 3f;
    private int deflectCooldown = 5;

    public StormChargeEntity(PlayerEntity player, World world, double x, double y, double z) {
        super(TinkerersEntities.STORM_CHARGE.get(), world, player, x, y, z);
    }

    public StormChargeEntity(World world, double x, double y, double z, Vec3d velocity) {
        super(TinkerersEntities.STORM_CHARGE.get(), x, y, z, velocity, world);
    }

    public StormChargeEntity(EntityType<? extends AbstractWindChargeEntity> entityType, World world) {
        super(entityType, world);
    }


    public void tick() {
        super.tick();
        if (this.deflectCooldown > 0) {
            --this.deflectCooldown;
        }

    }

    public boolean deflect(ProjectileDeflection deflection, @Nullable Entity deflector, @Nullable Entity owner, boolean fromAttack) {
        return this.deflectCooldown <= 0 && super.deflect(deflection, deflector, owner, fromAttack);
    }

    protected void createExplosion(Vec3d pos) {
        this.getWorld().createExplosion(this, null, EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), EXPLOSION_POWER, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Idle", 5, this::flyAnimController));
    }


    protected <E extends StormChargeEntity> PlayState flyAnimController(final AnimationState<E> event) {
        return event.setAndContinue(IDLE);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
