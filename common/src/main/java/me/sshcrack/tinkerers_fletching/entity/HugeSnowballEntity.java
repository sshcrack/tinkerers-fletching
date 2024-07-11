package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class HugeSnowballEntity extends BaseSnowballEntity {
    public HugeSnowballEntity(EntityType<? extends BaseSnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public HugeSnowballEntity(World world, LivingEntity owner) {
        super(TinkerersEntities.HUGE_SNOWBALL.get(), world, owner);
    }

    public HugeSnowballEntity(World world, double x, double y, double z) {
        super(TinkerersEntities.HUGE_SNOWBALL.get(), world, x, y, z);
    }

    @Override
    protected FletchingItem getDefaultItem() {
        return TinkerersItems.HUGE_SNOWBALL.get();
    }

    @Override
    public int getDamage() {
        return getDefaultItem().getPower();
    }
}
