package me.sshcrack.tinkerers_fletching.item;

public interface FletchingItem {
    SpeedLevel getSpeedLevel();

    /**
     * Power has to be between 1 and 6 inclusive.
     *
     * @return the power of this item
     */
    int getPower();

    enum SpeedLevel {
        SLOW,
        NORMAL,
        FAST
    }
}
