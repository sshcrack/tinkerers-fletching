package me.sshcrack.tinkerers_fletching.mixin.enchantment;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.enchantment.PseudoEnchantmentSupport;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @WrapOperation(method = "isSupportedItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isIn(Lnet/minecraft/registry/entry/RegistryEntryList;)Z"))
    private boolean tinkerers$checkPseudoEnchantmentSupport(ItemStack stack, RegistryEntryList<Item> list, Operation<Boolean> original) {
        var item = stack.getItem();
        if (item instanceof PseudoEnchantmentSupport pseudo)
            return stack.isOf(pseudo.getPseudoEnchantmentItem());

        return original.call(stack, list);
    }
}
