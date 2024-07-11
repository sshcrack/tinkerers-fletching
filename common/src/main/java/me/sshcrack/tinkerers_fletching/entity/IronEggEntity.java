package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class IronEggEntity extends EggEntity {
    public IronEggEntity(EntityType<? extends EggEntity> entityType, World world) {
        super(entityType, world);
    }

    protected Item getDefaultItem() {
        return TinkerersItems.IRON_EGG.get();
    }
}
