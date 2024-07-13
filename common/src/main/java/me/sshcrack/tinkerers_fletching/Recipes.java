package me.sshcrack.tinkerers_fletching;

import me.sshcrack.tinkerers_fletching.recipe.CountedIngredient;
import me.sshcrack.tinkerers_fletching.recipe.FletchingCraftRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingTransformRecipe;
import me.sshcrack.tinkerers_fletching.registries.ArchCustomIngredientSerializerRegistry;
import me.sshcrack.tinkerers_fletching.registries.GeneralRegister;
import net.minecraft.registry.Registries;

public class Recipes {
    public static void register() {
        ArchCustomIngredientSerializerRegistry.register(CountedIngredient.SERIALIZER);

        GeneralRegister.registerSup(Registries.RECIPE_TYPE, FletchingRecipe.Type.ID, () -> FletchingRecipe.Type.INSTANCE);

        GeneralRegister.registerSup(Registries.RECIPE_SERIALIZER, FletchingCraftRecipe.Serializer.ID, () -> FletchingCraftRecipe.Serializer.INSTANCE);
        GeneralRegister.registerSup(Registries.RECIPE_SERIALIZER, FletchingTransformRecipe.Serializer.ID, () -> FletchingTransformRecipe.Serializer.INSTANCE);
    }
}
