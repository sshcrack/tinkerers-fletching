package me.sshcrack.tinkerers_fletching.fabric.recipe;

import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class CustomIngredientWrapper implements CustomIngredient {
    private final ArchCustomIngredient ingredient;

    private CustomIngredientWrapper(ArchCustomIngredient ing) {
        this.ingredient = ing;
    }

    public static CustomIngredientWrapper of(ArchCustomIngredient ingredient) {
        return new CustomIngredientWrapper(ingredient);
    }

    @Override
    public boolean test(ItemStack stack) {
        return ingredient.test(stack);
    }

    @Override
    public List<ItemStack> getMatchingStacks() {
        return ingredient.getMatchingStacks();
    }

    @Override
    public boolean requiresTesting() {
        return ingredient.requiresTesting();
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return CustomIngredientSerializerWrapper.of(ingredient.getSerializer());
    }

    public ArchCustomIngredient getUnderlyingIngredient() {
        return this.ingredient;
    }
}
