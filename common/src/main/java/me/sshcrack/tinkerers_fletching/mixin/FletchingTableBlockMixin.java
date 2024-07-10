package me.sshcrack.tinkerers_fletching.mixin;

import dev.architectury.registry.menu.MenuRegistry;
import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.TinkerersStats;
import net.minecraft.block.BlockState;
import net.minecraft.block.FletchingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FletchingTableBlock.class)
public class FletchingTableBlockMixin {
    @Unique
    private static final Text SCREEN_TITLE = Text.translatable("container.tinkerers_fletching.customize");

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void tinkerers$onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {
        if (world.isClient) {
            cir.setReturnValue(ActionResult.SUCCESS);
        } else {
            MenuRegistry.openMenu((ServerPlayerEntity) player, new NamedScreenHandlerFactory() {
                @Override
                public Text getDisplayName() {
                    return SCREEN_TITLE;
                }

                @Override
                public @NotNull ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
                    return new FletchingScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
                }
            });
            player.incrementStat(TinkerersStats.INTERACT_WITH_FLETCHING_TABLE);

            cir.setReturnValue(ActionResult.CONSUME);
        }
    }
}
