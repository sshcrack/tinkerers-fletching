package me.sshcrack.tinkerers_fletching.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class FletchingCraftRecipe implements FletchingRecipe {
    final Ingredient base;
    final Ingredient addition;
    final ItemStack result;

    public FletchingCraftRecipe(Ingredient base, Ingredient addition, ItemStack result) {
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public boolean matches(FletchingRecipeInput fletchingRecipeInput, World world) {
        return this.base.test(fletchingRecipeInput.base()) && this.addition.test(fletchingRecipeInput.addition());
    }

    public ItemStack craft(FletchingRecipeInput fletchingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        return result.copy();
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return stack.isEmpty();
    }

    @Override
    public boolean testBase(ItemStack stack) {
        return this.base.test(stack);
    }

    @Override
    public boolean testAddition(ItemStack stack) {
        return this.addition.test(stack);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.base, this.addition).anyMatch(Ingredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<FletchingCraftRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = Identifier.of(TinkerersMod.MOD_ID, "fletching_craft");

        private Serializer() {

        }

        private static final MapCodec<FletchingCraftRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                Ingredient.ALLOW_EMPTY_CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
                        )
                        .apply(instance, FletchingCraftRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, FletchingCraftRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        @Override
        public MapCodec<FletchingCraftRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FletchingCraftRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static FletchingCraftRecipe read(RegistryByteBuf buf) {
            Ingredient ingredient2 = Ingredient.PACKET_CODEC.decode(buf);
            Ingredient ingredient3 = Ingredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new FletchingCraftRecipe(ingredient2, ingredient3, itemStack);
        }

        private static void write(RegistryByteBuf buf, FletchingCraftRecipe recipe) {
            Ingredient.PACKET_CODEC.encode(buf, recipe.base);
            Ingredient.PACKET_CODEC.encode(buf, recipe.addition);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }
    }
}