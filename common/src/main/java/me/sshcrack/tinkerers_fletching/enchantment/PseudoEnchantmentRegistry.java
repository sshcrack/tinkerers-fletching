package me.sshcrack.tinkerers_fletching.enchantment;

import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PseudoEnchantmentRegistry {
    private static final HashMap<RegistryEntry<Item>, List<RegistryEntry<Item>>> pseudoEnchantments = new HashMap<>();

    public static void register() {

    }

    private static void registerPseudoEnchantment(PseudoEnchantmentSupport support) {
        pseudoEnchantments.compute(support.getPseudoEnchantmentItem().arch$holder(), e -> {
            if (e == null)
                e = new ArrayList<>();

            e.add(support.getThisItem().arch$holder());
        });
    }
}
