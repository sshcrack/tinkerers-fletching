package me.sshcrack.tinkerers_fletching;

import me.sshcrack.tinkerers_fletching.recipe.CountedIngredient;
import me.sshcrack.tinkerers_fletching.recipe.FletchingCraftRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingTransformRecipe;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializerRegistry;
import net.minecraft.registry.RegistryKeys;

public class Recipes {
    public static void register() {
        ArchCustomIngredientSerializerRegistry.register(CountedIngredient.SERIALIZER);

        TinkerersMod.register(RegistryKeys.RECIPE_TYPE, FletchingRecipe.Type.ID, FletchingRecipe.Type.INSTANCE);
        TinkerersMod.register(RegistryKeys.RECIPE_SERIALIZER, FletchingCraftRecipe.Serializer.ID, FletchingCraftRecipe.Serializer.INSTANCE);
        TinkerersMod.register(RegistryKeys.RECIPE_SERIALIZER, FletchingTransformRecipe.Serializer.ID, FletchingTransformRecipe.Serializer.INSTANCE);
    }
}
