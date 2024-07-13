package me.sshcrack.tinkerers_fletching.item.projectile.tiered;

import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public enum ArrowTier {

    STONE(4, ofItems("block/stones")),
    IRON(3, ofItems("ingots/iron")),
    DIAMOND(2, ofItems("item/gems/diamond")),
    NETHERITE(1, ofItems("item/ores/netherite_scrap"));

    private final int power;
    private final TagKey<Item> materialTag;

    ArrowTier(int power, TagKey<Item> materialTag) {
        this.power = power;
        this.materialTag = materialTag;
    }

    private static TagKey<Item> ofItems(String name) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of("c", name));
    }

    public int getPower() {
        return power;
    }

    public TagKey<Item> getMaterialTag() {
        return materialTag;
    }
}
