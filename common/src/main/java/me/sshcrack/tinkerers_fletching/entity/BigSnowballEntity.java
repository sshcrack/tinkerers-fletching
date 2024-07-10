package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BigSnowballEntity extends SnowballEntity {
    public BigSnowballEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public BigSnowballEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public BigSnowballEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected Item getDefaultItem() {
        return TinkerersItems.BIG_SNOWBALL.get();
    }
}
