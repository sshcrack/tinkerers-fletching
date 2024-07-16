package me.sshcrack.tinkerers_fletching.entity.arrows;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.duck.ExplosionDamageSourceDuck;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class LeadArrowEntity extends PersistentProjectileEntity {
    private boolean teleported;

    private static final ExplosionBehavior TELEPORTED_EXPLOSION_BEHAVIOR = new ExplosionBehavior() {
        public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
            return !state.isOf(Blocks.NETHER_PORTAL) && super.canDestroyBlock(explosion, world, pos, state, power);
        }

        public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
            return blockState.isOf(Blocks.NETHER_PORTAL) ? Optional.empty() : super.getBlastResistance(explosion, world, pos, blockState, fluidState);
        }
    };

    public LeadArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public LeadArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.LEAD_ARROW.get(), x, y, z, world, stack, shotFrom);
    }

    public LeadArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.LEAD_ARROW.get(), owner, world, stack, shotFrom);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return TinkerersItems.LEAD_ARROW.get().getDefaultStack();
    }


    @SuppressWarnings("SameParameterValue")
    private void setTeleported(boolean teleported) {
        this.teleported = teleported;
    }

    @Override
    @Nullable
    public Entity teleportTo(TeleportTarget teleportTarget) {
        Entity entity = super.teleportTo(teleportTarget);
        if (entity instanceof LeadArrowEntity arrowEntity) {
            arrowEntity.setTeleported(true);
        }

        return entity;
    }

    private DamageSource createDamageSource(List<BlockPos> toDestroy) {
        var source = Explosion.createDamageSource(this.getWorld(), this);
        var duck = (ExplosionDamageSourceDuck) source;

        duck.tinkerers$setBlocksToDestroy(toDestroy);
        duck.tinkerers$setDestroyOtherBlocks(false);
        return source;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if (!getWorld().isClient) {
            var source = createDamageSource(List.of(blockHitResult.getBlockPos()));
            this.getWorld().createExplosion(this, source, this.teleported ? TELEPORTED_EXPLOSION_BEHAVIOR : null, this.getX(), this.getBodyY(0.0625), this.getZ(), 1.0F, false, World.ExplosionSourceType.TNT);
        }
        this.discard();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        var prev = isRemoved();
        super.onEntityHit(entityHitResult);

        if (isRemoved() && !prev) {
            if (!getWorld().isClient) {
                var source = createDamageSource(List.of(getBlockPos()));
                getWorld().createExplosion(this, source, this.teleported ? TELEPORTED_EXPLOSION_BEHAVIOR : null, this.getX(), this.getBodyY(0.0625), this.getZ(), 1.0F, false, World.ExplosionSourceType.TNT);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getWorld().isClient)
            getWorld().addParticle(ParticleTypes.SMOKE, getX(), getY(), getZ(), random.nextGaussian() * 0.05, 0, random.nextGaussian() * 0.05);
    }
}
