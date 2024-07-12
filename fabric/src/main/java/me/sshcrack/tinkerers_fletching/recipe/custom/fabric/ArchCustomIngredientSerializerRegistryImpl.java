package me.sshcrack.tinkerers_fletching.recipe.custom.fabric;

import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;

public class ArchCustomIngredientSerializerRegistryImpl {
    public static void register(ArchCustomIngredientSerializer<?> serializer) {
        CustomIngredientSerializer.register(CustomIngredientSerializerWrapper.of(serializer));
    }
}
