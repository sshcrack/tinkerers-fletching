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

public class FletchingTransformRecipe implements FletchingRecipe {

    final CountedIngredient template;
    final CountedIngredient base;
    final CountedIngredient addition;
    final ItemStack result;

    public FletchingTransformRecipe(CountedIngredient template, CountedIngredient base, CountedIngredient addition, ItemStack result) {
        this.template = template;
        this.base = base;
        this.addition = addition;
        this.result = result;
    }

    public boolean matches(FletchingRecipeInput fletchingRecipeInput, World world) {
        return this.template.test(fletchingRecipeInput.template()) && this.base.test(fletchingRecipeInput.base()) && this.addition.test(fletchingRecipeInput.addition());
    }

    public ItemStack craft(FletchingRecipeInput fletchingRecipeInput, RegistryWrapper.WrapperLookup wrapperLookup) {
        ItemStack itemStack = fletchingRecipeInput.base().copyComponentsToNewStack(this.result.getItem(), this.result.getCount());
        itemStack.applyUnvalidatedChanges(this.result.getComponentChanges());
        return itemStack;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
        return this.result;
    }

    @Override
    public boolean testTemplate(ItemStack stack) {
        return this.template.test(stack);
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
        return Stream.of(this.template, this.base, this.addition).anyMatch(CountedIngredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<FletchingTransformRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = Identifier.of(TinkerersMod.MOD_ID, "fletching_transform");

        private Serializer() {

        }

        private static final MapCodec<FletchingTransformRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                CountedIngredient.CODEC.fieldOf("template").forGetter(recipe -> recipe.template),
                                CountedIngredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                CountedIngredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
                                ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result)
                        )
                        .apply(instance, FletchingTransformRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, FletchingTransformRecipe> PACKET_CODEC = PacketCodec.ofStatic(
                Serializer::write, Serializer::read
        );

        @Override
        public MapCodec<FletchingTransformRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, FletchingTransformRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static FletchingTransformRecipe read(RegistryByteBuf buf) {
            CountedIngredient ingredient = CountedIngredient.PACKET_CODEC.decode(buf);
            CountedIngredient ingredient2 = CountedIngredient.PACKET_CODEC.decode(buf);
            CountedIngredient ingredient3 = CountedIngredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new FletchingTransformRecipe(ingredient, ingredient2, ingredient3, itemStack);
        }

        private static void write(RegistryByteBuf buf, FletchingTransformRecipe recipe) {
            CountedIngredient.PACKET_CODEC.encode(buf, recipe.template);
            CountedIngredient.PACKET_CODEC.encode(buf, recipe.base);
            CountedIngredient.PACKET_CODEC.encode(buf, recipe.addition);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }
    }
}
