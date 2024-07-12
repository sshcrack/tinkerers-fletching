package me.sshcrack.tinkerers_fletching.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

public record CountedIngredient(Ingredient ingredient, int count) implements CustomIngredient {
    public static final Codec<CountedIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.ALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(CountedIngredient::ingredient),
            Codec.INT.fieldOf("count").orElse(1).forGetter(CountedIngredient::count)
    ).apply(instance, CountedIngredient::new));
    public static final PacketCodec<RegistryByteBuf, CountedIngredient> PACKET_CODEC = new PacketCodec<>() {
        @Override
        public CountedIngredient decode(RegistryByteBuf buf) {
            var list = ItemStack.LIST_PACKET_CODEC.decode(buf);
            var count = buf.readInt();

            return new CountedIngredient(Ingredient.ofStacks(list.stream()), count);
        }

        @Override
        public void encode(RegistryByteBuf buf, CountedIngredient value) {
            ItemStack.LIST_PACKET_CODEC.encode(buf, value.getMatchingStacks());
            buf.writeInt(value.count());
        }
    };

    public static final Serializer SERIALIZER = new Serializer();
    public static final CountedIngredient EMPTY = new CountedIngredient(Ingredient.EMPTY, 0);

    @Override
    public boolean test(ItemStack stack) {
        return this.ingredient.test(stack) && stack.getCount() >= this.count;
    }

    public boolean isEmpty() {
        return getMatchingStacks().isEmpty();
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return List.of(Arrays.stream(this.ingredient.getMatchingStacks()).map(stack -> {
            ItemStack copy = stack.copy();
            copy.setCount(this.count);
            return copy;
        }).toArray(ItemStack[]::new));
    }


    @Override
    public boolean requiresTesting() {
        return false;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public static class Serializer implements CustomIngredientSerializer<CountedIngredient> {
        public static final Identifier ID = Identifier.of(TinkerersMod.MOD_ID, "counted_ingredient");

        @Override
        public Identifier getIdentifier() {
            return ID;
        }

        @Override
        public MapCodec<CountedIngredient> getCodec(boolean allowEmpty) {
            return MapCodec.assumeMapUnsafe(CODEC);
        }

        @Override
        public PacketCodec<RegistryByteBuf, CountedIngredient> getPacketCodec() {
            return PACKET_CODEC;
        }


    }
}
