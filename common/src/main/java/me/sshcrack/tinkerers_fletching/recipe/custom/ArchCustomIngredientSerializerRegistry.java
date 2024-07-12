package me.sshcrack.tinkerers_fletching.recipe.custom;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class ArchCustomIngredientSerializerRegistry {
    @ExpectPlatform
    public static void register(ArchCustomIngredientSerializer<?> serializer) {
        throw new AssertionError();
    }
}
