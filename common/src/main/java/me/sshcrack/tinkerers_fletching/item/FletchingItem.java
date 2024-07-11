package me.sshcrack.tinkerers_fletching.item;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public abstract class FletchingItem extends Item {
    public FletchingItem(Settings settings) {
        super(settings);
    }

    public abstract SpeedLevel getSpeedLevel();

    /**
     * Power has to be between 1 and 6 inclusive.
     *
     * @return the power of this item
     */
    public abstract int getPower();

    public enum SpeedLevel {
        SLOW,
        NORMAL,
        FAST
    }

    public boolean isResultTextureUsingBase() {
        return true;
    }

    public Identifier getResultTexture() {
        var splitTexture = Registries.ITEM.getId(this).getPath();

        var useBase = isResultTextureUsingBase();
        return Identifier.of(TinkerersMod.MOD_ID, "container/result/" + (useBase ? "additions" : "no_base") + "/" + splitTexture);
    }
}
