package me.sshcrack.tinkerers_fletching.fabric.recipe;

import com.mojang.serialization.MapCodec;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredientSerializer;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.util.Identifier;

public class CustomIngredientSerializerWrapper implements CustomIngredientSerializer<CustomIngredientWrapper> {
    private final ArchCustomIngredientSerializer<ArchCustomIngredient> serializer;
    private final PacketCodec<RegistryByteBuf, CustomIngredientWrapper> packetCodec;

    private CustomIngredientSerializerWrapper(ArchCustomIngredientSerializer<ArchCustomIngredient> serializer) {
        this.serializer = serializer;
        this.packetCodec = serializer.getPacketCodec()
                .xmap(CustomIngredientWrapper::of, CustomIngredientWrapper::getUnderlyingIngredient);
    }

    public static CustomIngredientSerializerWrapper of(ArchCustomIngredientSerializer<?> serializer) {
        // Should work I guess
        //noinspection unchecked
        return new CustomIngredientSerializerWrapper((ArchCustomIngredientSerializer<ArchCustomIngredient>) serializer);
    }

    @Override
    public Identifier getIdentifier() {
        return serializer.getIdentifier();
    }

    @Override
    public MapCodec<CustomIngredientWrapper> getCodec(boolean allowEmpty) {
        //TODO Optimize by caching
        return serializer.getCodec(allowEmpty)
                .xmap(CustomIngredientWrapper::of, CustomIngredientWrapper::getUnderlyingIngredient);
    }

    @Override
    public PacketCodec<RegistryByteBuf, CustomIngredientWrapper> getPacketCodec() {
        return packetCodec;
    }
}
