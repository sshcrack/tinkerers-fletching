package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.item.Item;

public class StormChargeItem extends Item implements FletchingItem {
    public StormChargeItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @Override
    public int getPower() {
        return 5;
    }
}
