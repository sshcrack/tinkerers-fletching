package me.sshcrack.tinkerers_fletching.registries;

import dev.architectury.injectables.annotations.ExpectPlatform;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;

public class ArchCustomIngredientSerializerRegistry {
    @ExpectPlatform
    public static void register(ArchCustomIngredientSerializer<?> serializer) {
        throw new AssertionError();
    }
}
