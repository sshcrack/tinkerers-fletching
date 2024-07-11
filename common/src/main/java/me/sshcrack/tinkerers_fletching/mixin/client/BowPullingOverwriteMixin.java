package me.sshcrack.tinkerers_fletching.mixin.client;

import me.sshcrack.tinkerers_fletching.client.BowPullingDummyDuck;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
@Environment(EnvType.CLIENT)
public class BowPullingOverwriteMixin implements BowPullingDummyDuck {
    @Unique
    private Float pull = null;

    @Override
    public void tinkerers$setPull(float pull) {
        this.pull = pull;
    }

    @Override
    @Nullable
    public Float tinkerers$getPull() {
        return pull;
    }
}
