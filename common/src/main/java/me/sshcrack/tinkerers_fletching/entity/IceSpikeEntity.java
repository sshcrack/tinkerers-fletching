package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class IceSpikeEntity extends SnowballEntity {
    public IceSpikeEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceSpikeEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public IceSpikeEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected Item getDefaultItem() {
        return TinkerersItems.ICE_SPIKE.get();
    }
}
