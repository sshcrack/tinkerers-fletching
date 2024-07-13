package me.sshcrack.tinkerers_fletching.neoforge.recipe;

import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.concurrent.atomic.AtomicReference;

public class IngredientTypeWrapper {
    public static IngredientType<CustomIngredientWrapper> of(ArchCustomIngredientSerializer<?> serializer) {
        // Should work I guess
        //noinspection unchecked
        var castSerializer = (ArchCustomIngredientSerializer<ArchCustomIngredient>) serializer;
        var ref = new AtomicReference<IngredientType<CustomIngredientWrapper>>();

        var codec = castSerializer.getCodec(true)
                .xmap(e -> {
                    var type = ref.get();
                    return CustomIngredientWrapper.of(e, type);
                }, CustomIngredientWrapper::getUnderlyingIngredient);

        var packetCodec = castSerializer.getPacketCodec()
                .xmap(e -> {
                    var type = ref.get();
                    return CustomIngredientWrapper.of(e, type);
                }, CustomIngredientWrapper::getUnderlyingIngredient);

        var type = new IngredientType<>(codec, packetCodec);
        ref.set(type);

        return type;
    }
}
