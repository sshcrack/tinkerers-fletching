package me.sshcrack.tinkerers_fletching.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.item.projectile.tiered.ArrowTier;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.stream.Stream;

public class FletchingCraftRecipe implements FletchingRecipe {
    final CountedIngredient base;
    final CountedIngredient addition;
    final ItemStack result;

    public FletchingCraftRecipe(CountedIngredient base, CountedIngredient addition, ItemStack result) {
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
    public ArchCustomIngredient getTemplateIngredient() {
        return null;
    }

    @Override
    public ArchCustomIngredient getBaseIngredient() {
        return base;
    }

    @Override
    public ArchCustomIngredient getAdditionIngredient() {
        return addition;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public boolean isEmpty() {
        return Stream.of(this.base, this.addition).anyMatch(CountedIngredient::isEmpty);
    }

    public static class Serializer implements RecipeSerializer<FletchingCraftRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final Identifier ID = Identifier.of(TinkerersMod.MOD_ID, "fletching_craft");

        private Serializer() {

        }

        private static final MapCodec<FletchingCraftRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                                CountedIngredient.CODEC.fieldOf("base").forGetter(recipe -> recipe.base),
                                CountedIngredient.CODEC.fieldOf("addition").forGetter(recipe -> recipe.addition),
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
            CountedIngredient ingredient2 = CountedIngredient.PACKET_CODEC.decode(buf);
            CountedIngredient ingredient3 = CountedIngredient.PACKET_CODEC.decode(buf);
            ItemStack itemStack = ItemStack.PACKET_CODEC.decode(buf);
            return new FletchingCraftRecipe(ingredient2, ingredient3, itemStack);
        }

        private static void write(RegistryByteBuf buf, FletchingCraftRecipe recipe) {
            CountedIngredient.PACKET_CODEC.encode(buf, recipe.base);
            CountedIngredient.PACKET_CODEC.encode(buf, recipe.addition);
            ItemStack.PACKET_CODEC.encode(buf, recipe.result);
        }
    }
}
