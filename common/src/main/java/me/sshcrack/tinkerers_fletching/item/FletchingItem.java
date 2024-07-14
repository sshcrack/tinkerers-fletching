package me.sshcrack.tinkerers_fletching.item;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FletchingItem {
    SpeedLevel getSpeedLevel();

    default int getPower() {
        return getPower(ItemStack.EMPTY);
    }

    /**
     * Power has to be between 1 and 6 inclusive.
     *
     * @return the power of this item
     */
    int getPower(ItemStack stack);

    enum SpeedLevel {
        SLOW(Identifier.of(TinkerersMod.MOD_ID, "container/fletching/speed/slow")),
        NORMAL(Identifier.of(TinkerersMod.MOD_ID, "container/fletching/speed/normal")),
        FAST(Identifier.of(TinkerersMod.MOD_ID, "container/fletching/speed/fast")),
        VERY_FAST(Identifier.of(TinkerersMod.MOD_ID, "container/fletching/speed/very_fast"));

        public Identifier getTexture() {
            return texture;
        }

        private final Identifier texture;

        SpeedLevel(Identifier of) {
            this.texture = of;
        }
    }

    @Nullable
    default Identifier getBaseTexture() {
        return Identifier.of(TinkerersMod.MOD_ID, "container/fletching/result/overworld");
    }

    @NotNull
    default Identifier getResultTexture() {
        var itemName = Registries.ITEM.getId((Item) this).getPath();

        return Identifier.of(TinkerersMod.MOD_ID, "container/fletching/result/item/" + itemName);
    }
}
