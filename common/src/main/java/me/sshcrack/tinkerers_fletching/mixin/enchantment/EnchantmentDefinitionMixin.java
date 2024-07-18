package me.sshcrack.tinkerers_fletching.mixin.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.Definition.class)
public class EnchantmentDefinitionMixin {
    @Inject(method = "supportedItems", at = @At("RETURN"))
    private void tinkerers$addPseudoEnchantmentSupport(CallbackInfoReturnable<RegistryEntryList<Item>> cir) {

    }
}
