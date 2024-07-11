package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.item.Item;
import net.minecraft.item.ProjectileItem;

public class StormChargeItem extends FletchingItem {
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

    @Override
    public boolean isResultTextureUsingBase() {
        return false;
    }
}
