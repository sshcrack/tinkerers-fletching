package me.sshcrack.tinkerers_fletching.registries.fabric;

import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;
import me.sshcrack.tinkerers_fletching.fabric.recipe.CustomIngredientSerializerWrapper;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public class ArchCustomIngredientSerializerRegistryImpl {
    public static void register(ArchCustomIngredientSerializer<?> serializer) {
        CustomIngredientSerializer.register(CustomIngredientSerializerWrapper.of(serializer));
    }
}
