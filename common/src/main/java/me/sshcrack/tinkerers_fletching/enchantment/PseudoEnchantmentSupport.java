package me.sshcrack.tinkerers_fletching.enchantment;

import net.minecraft.item.Item;

public interface PseudoEnchantmentSupport {
    Item getPseudoEnchantmentItem();

    default Item getThisItem() {
        return (Item) this;
    }
}
