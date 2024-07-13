package me.sshcrack.tinkerers_fletching.registries.neoforge;

import me.sshcrack.tinkerers_fletching.neoforge.TinkerersModForge;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;
import me.sshcrack.tinkerers_fletching.neoforge.recipe.IngredientTypeWrapper;

public class ArchCustomIngredientSerializerRegistryImpl {
    public static void register(ArchCustomIngredientSerializer<?> serializer) {
        var name = serializer.getIdentifier().getPath();
        TinkerersModForge.INGREDIENT_TYPE.register(name, () -> IngredientTypeWrapper.of(serializer));
    }
}
