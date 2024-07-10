package me.sshcrack.tinkerers_fletching.mixin.client;

import me.sshcrack.tinkerers_fletching.recipe.FletchingCraftRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingTransformRecipe;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientRecipeBook.class)
public class ClientRecipeBookMixin {
    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void tinkerers$injectRecipeType(RecipeEntry<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        Recipe<?> v = recipe.value();
        if (v instanceof FletchingCraftRecipe || v instanceof FletchingTransformRecipe) {
            cir.setReturnValue(RecipeBookGroup.CRAFTING_MISC);
        }
    }
}
