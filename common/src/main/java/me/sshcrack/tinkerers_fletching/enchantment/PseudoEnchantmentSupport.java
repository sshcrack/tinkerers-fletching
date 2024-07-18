package me.sshcrack.tinkerers_fletching.enchantment;

import net.minecraft.item.Item;

/**
 * Call {@link PseudoEnchantmentRegistry#registerPseudoEnchantment(PseudoEnchantmentSupport)} in the constructor of the item that has the pseudo-enchantment.
 */
public interface PseudoEnchantmentSupport {
    Item getPseudoEnchantmentItem();

    default Item getThisItem() {
        return (Item) this;
    }
}
