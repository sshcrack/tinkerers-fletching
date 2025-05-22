package me.sshcrack.tinkerers_fletching.mixin.trident;

import me.sshcrack.tinkerers_fletching.entity.NetheriteTridentEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.PointedDripstoneBlock;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PointedDripstoneBlock.class)
public class PointedDripstoneBlockMixin {
    @Inject(method = "onProjectileHit", at= @At("HEAD"))
    private void tinkerers$checkForTridentHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (projectile.canModifyAt(world, blockPos)
                    && projectile.canBreakBlocks(world)
                    && projectile instanceof NetheriteTridentEntity
                    && projectile.getVelocity().length() > 0.6) {
                world.breakBlock(blockPos, true);
            }
        }
    }
}
