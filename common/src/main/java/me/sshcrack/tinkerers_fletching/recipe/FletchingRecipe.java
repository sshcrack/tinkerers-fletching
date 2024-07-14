package me.sshcrack.tinkerers_fletching.recipe;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface FletchingRecipe extends Recipe<FletchingRecipeInput> {
    @Override
    default RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    @Override
    default boolean fits(int width, int height) {
        return width >= 3 && height >= 1;
    }

    @Override
    default ItemStack createIcon() {
        return new ItemStack(Blocks.FLETCHING_TABLE);
    }

    boolean testTemplate(ItemStack stack);

    boolean testBase(ItemStack stack);

    boolean testAddition(ItemStack stack);

    @Nullable
    CountedIngredient getTemplateIngredient();

    CountedIngredient getBaseIngredient();

    CountedIngredient getAdditionIngredient();

    class Type implements RecipeType<FletchingRecipe> {
        private Type() {
        }

        public static final Type INSTANCE = new Type();

        // This will be needed in step 4
        public static final Identifier ID = Identifier.of(TinkerersMod.MOD_ID, "fletching");
    }
}
