package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TntArrowItem extends ArrowItem implements FletchingItem {
    public TntArrowItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 5;
    }
}
