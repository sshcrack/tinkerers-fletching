package me.sshcrack.tinkerers_fletching.duck;


public interface LeashDataDuck {
    void tinkerers$setNoLeadDrop(boolean noLeadDrop);

    void tinkerers$setMaxLeadDistance(double distance);

    void tinkerers$internal$setPreventedDrop(boolean preventedDrop);

    boolean tinkerers$internal$hasPreventedDrop();

    double tinkerers$getMaxLeadDistance();

    boolean tinkerers$hasNoLeadDrop();
}
