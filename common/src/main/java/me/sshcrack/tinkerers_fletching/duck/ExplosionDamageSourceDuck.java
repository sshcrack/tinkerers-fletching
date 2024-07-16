package me.sshcrack.tinkerers_fletching.duck;

import net.minecraft.util.math.BlockPos;

import java.util.List;

public interface ExplosionDamageSourceDuck {
    void tinkerers$setBlocksToDestroy(List<BlockPos> toDestroy);

    void tinkerers$setDestroyOtherBlocks(boolean destroyOtherBlocks);

    List<BlockPos> tinkerers$getBlocksToDestroy();

    boolean tinkerers$shouldDestroyOtherBlocks();

    boolean tinkerers$hasModified();
}
