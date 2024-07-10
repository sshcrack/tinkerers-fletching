package me.sshcrack.tinkerers_fletching.mixin.client;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @Shadow
    private @Nullable ClientWorld world;

    @Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
    private void tinkerers$processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (eventId == TinkerersMod.FLETCHING_USED_WORLD_EVENT) {
            assert this.world != null;
            this.world
                    .playSoundAtBlockCenter(pos, SoundEvents.ENTITY_VILLAGER_WORK_FLETCHER, SoundCategory.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F, false);
            ci.cancel();
        }
    }
}
