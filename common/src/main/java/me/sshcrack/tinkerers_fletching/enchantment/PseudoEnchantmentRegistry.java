package me.sshcrack.tinkerers_fletching.enchantment;

import java.util.ArrayList;
import java.util.List;

public class PseudoEnchantmentRegistry {
    public static final List<PseudoEnchantmentSupport> pseudoEnchantments = new ArrayList<>();

    public static void registerPseudoEnchantment(PseudoEnchantmentSupport support) {
        pseudoEnchantments.add(support);
    }
}
