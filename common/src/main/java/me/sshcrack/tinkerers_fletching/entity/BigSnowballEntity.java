package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BigSnowballEntity extends BaseSnowballEntity {
    public BigSnowballEntity(EntityType<? extends BaseSnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public BigSnowballEntity(World world, LivingEntity owner) {
        super(TinkerersEntities.BIG_SNOWBALL.get(), world, owner);
    }

    public BigSnowballEntity(World world, double x, double y, double z) {
        super(TinkerersEntities.BIG_SNOWBALL.get(), world, x, y, z);
    }

    @Override
    protected FletchingItem getDefaultItem() {
        return TinkerersItems.BIG_SNOWBALL.get();
    }

    @Override
    public int getDamage() {
        return getDefaultItem().getPower();
    }
}
