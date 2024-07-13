package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class RocketPearlEntity extends EnderPearlEntity {
    public RocketPearlEntity(EntityType<? extends EnderPearlEntity> entityType, World world) {
        super(entityType, world);
    }

    public RocketPearlEntity(LivingEntity owner, World world) {
        super(TinkerersEntities.ROCKET_PEARL.get(), world);
        setPosition(owner.getX(), owner.getEyeY() - (double) 0.1f, owner.getZ());
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        getWorld().addParticle(ParticleTypes.FIREWORK, getX(), getY(), getZ(), random.nextGaussian() * 0.05, 0, random.nextGaussian() * 0.05);
    }

    @Override
    protected Item getDefaultItem() {
        return TinkerersItems.ROCKET_PEARL.get();
    }
}
