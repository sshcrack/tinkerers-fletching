package me.sshcrack.tinkerers_fletching.mixin;

import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.block.FletchingTableBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingTableBlock.class)
public class CraftingTableBlockMixin {
    @Unique
    private static final Text SCREEN_TITLE = Text.translatable("container.tinkerers_fletching.customize");

    @Inject(method = "createScreenHandlerFactory", at = @At("HEAD"), cancellable = true)
    private void tinkerers$createScreenHandlerFactory(BlockState state, World world, BlockPos pos, CallbackInfoReturnable<NamedScreenHandlerFactory> cir) {
        var block = CraftingTableBlock.class.cast(this);

        if (block instanceof FletchingTableBlock) {
            cir.setReturnValue(new SimpleNamedScreenHandlerFactory(
                    (syncId, inventory, player) -> new FletchingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), SCREEN_TITLE
            ));
        }
    }
}
