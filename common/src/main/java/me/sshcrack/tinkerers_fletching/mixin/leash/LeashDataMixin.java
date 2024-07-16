package me.sshcrack.tinkerers_fletching.mixin.leash;

import me.sshcrack.tinkerers_fletching.duck.LeashDataDuck;
import net.minecraft.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Leashable.LeashData.class)
public class LeashDataMixin implements LeashDataDuck {
    @Unique
    private boolean noLeadDrop = false;
    @Unique
    private boolean hasPreventedDrop = true;
    @Unique
    private double maxDistance = -1;

    @Override
    public void tinkerers$setNoLeadDrop(boolean noLeadDrop) {
        this.noLeadDrop = noLeadDrop;
        hasPreventedDrop = false;

    }

    @Override
    public void tinkerers$setMaxLeadDistance(double distance) {
        this.maxDistance = distance;
    }

    @Override
    public void tinkerers$internal$setPreventedDrop(boolean preventedDrop) {
        this.hasPreventedDrop = preventedDrop;
    }

    @Override
    public boolean tinkerers$internal$hasPreventedDrop() {
        return hasPreventedDrop;
    }

    @Override
    public double tinkerers$getMaxLeadDistance() {
        return maxDistance;
    }

    @Override
    public boolean tinkerers$hasNoLeadDrop() {
        return noLeadDrop;
    }
}
