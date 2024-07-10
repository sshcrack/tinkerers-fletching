package me.sshcrack.tinkerers_fletching.item;

import net.minecraft.item.Item;

public class SpeedoPearlItem extends Item implements FletchingItem {
    public SpeedoPearlItem() {
        super(new Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @Override
    public int getPower() {
        return 6;
    }
}
