package me.sshcrack.tinkerers_fletching.mixin;

import me.sshcrack.tinkerers_fletching.duck.ExplosionDamageSourceDuck;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;

@Mixin(DamageSource.class)
public class MixinDamageSource implements ExplosionDamageSourceDuck {
    @Unique
    List<BlockPos> toDestroy;
    @Unique
    boolean shouldDestroyNoOther;

    @Unique
    boolean modified = false;

    @Override
    public void tinkerers$setBlocksToDestroy(List<BlockPos> toDestroy) {
        this.toDestroy = toDestroy;
        modified = true;
    }

    @Override
    public void tinkerers$setDestroyOtherBlocks(boolean destroyOtherBlocks) {
        this.shouldDestroyNoOther = destroyOtherBlocks;
        modified = true;
    }

    @Override
    public List<BlockPos> tinkerers$getBlocksToDestroy() {
        return toDestroy;
    }

    @Override
    public boolean tinkerers$shouldDestroyOtherBlocks() {
        return shouldDestroyNoOther;
    }

    @Override
    public boolean tinkerers$hasModified() {
        return modified;
    }
}
