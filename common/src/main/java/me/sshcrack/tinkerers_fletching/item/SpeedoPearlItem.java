package me.sshcrack.tinkerers_fletching.item;

public class SpeedoPearlItem extends FletchingItem {
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
