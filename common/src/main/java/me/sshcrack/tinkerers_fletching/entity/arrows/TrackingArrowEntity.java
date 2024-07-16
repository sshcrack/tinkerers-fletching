package me.sshcrack.tinkerers_fletching.entity.arrows;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class TrackingArrowEntity extends PersistentProjectileEntity {
    private LivingEntity target;

    public static final double MIN_FORCE = 0.1;
    public static final double MAX_FORCE = 0.5;
    public static final double DISCARD_VELOCITY = 0.1;

    public static final double RADIUS = 25;
    private static final double SQRD_RADIUS = RADIUS * RADIUS;

    private final Predicate<Entity> PREDICATE = e -> (getOwner() == null || e.getUuid().compareTo(getOwner().getUuid()) != 0) &&
            (!(e instanceof PlayerEntity p) || !p.isCreative())
            && e.isAlive();


    private final TargetPredicate LIVING_PREDICATE = TargetPredicate.createAttackable()
            .setPredicate(PREDICATE::test);

    public TrackingArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TrackingArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TRACKING_ARROW.get(), x, y, z, world, stack, shotFrom);
    }

    public TrackingArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TRACKING_ARROW.get(), owner, world, stack, shotFrom);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return TinkerersItems.TRACKING_ARROW.get().getDefaultStack();
    }

    private void expensiveUpdate() {
        if (target != null && this.target.squaredDistanceTo(this) <= SQRD_RADIUS)
            return;
        var owner = getOwner();
        if (owner != null && !(owner instanceof LivingEntity))
            return;

        var living = (LivingEntity) owner;
        var expandedBox = getBoundingBox().expand(RADIUS);

        List<LivingEntity> potentialTargets = getWorld().getEntitiesByClass(LivingEntity.class, expandedBox, PREDICATE);
        var players = getWorld().getPlayers(LIVING_PREDICATE, living, expandedBox);
        potentialTargets.addAll(players);

        var velocityDir = getVelocity().normalize();
        var maxDotP = Math.cos(Math.toRadians(60));


        var smallestDistance = Double.MAX_VALUE;
        LivingEntity currentEntity = null;

        for (LivingEntity target : potentialTargets) {
            var targetVec = new Vec3d(target.getX() - getX(), target.getY() - getY(), target.getZ() - getZ());
            var dot = velocityDir.dotProduct(targetVec.normalize());

            var distance = targetVec.crossProduct(velocityDir).lengthSquared();
            if (dot > maxDotP && distance < smallestDistance) {
                currentEntity = target;
                smallestDistance = distance;
            }
        }

        target = currentEntity;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.age % 20 == 1) {
            this.expensiveUpdate();
        }

        if (this.target != null && (this.target.isSpectator() || this.target.isDead())) {
            this.target = null;
        }

        if (this.target != null && !this.isOnGround()) {
            Vec3d diffVec = new Vec3d(
                    this.target.getX() - this.getX(), this.target.getY() + (double) this.target.getStandingEyeHeight() / 2.0 - this.getY(), this.target.getZ() - this.getZ()
            );
            double d = diffVec.lengthSquared();
            if (d < SQRD_RADIUS) {
                double distancePercentage = 1 - Math.sqrt(d) / RADIUS;

                var force = MathHelper.lerp(distancePercentage, MIN_FORCE, MAX_FORCE);
                var steerVec = diffVec.normalize().multiply(force);
                var newVelocity = this.getVelocity().normalize().add(steerVec);
                var speed = getVelocity().length();

                this.setVelocity(newVelocity.normalize().multiply(speed));
            }

            if (getWorld().isClient) {
                /* For debugging purposes
                var length = diffVec.length();
                var stepSize = 1d / length;

                for (double i = 0; i < 1; i += stepSize * 0.1) {
                    var offset = diffVec.multiply(i);
                    getWorld().addParticle(ParticleTypes.WITCH, getX() + offset.x, getY() + offset.y, getZ() + offset.z, 0, 0, 0);
                }*/
                var v = getVelocity().multiply(-1);
                getWorld().addParticle(ParticleTypes.WITCH, getX(), getY(), getZ(), v.x, v.y, v.z);
            }
        }
        if (getVelocity().length() < DISCARD_VELOCITY)
            this.discard();

        this.move(MovementType.SELF, this.getVelocity());
    }
}
