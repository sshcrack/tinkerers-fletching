package me.sshcrack.tinkerers_fletching.neoforge.recipe;

import me.sshcrack.tinkerers_fletching.recipe.custom.ArchCustomIngredient;
import net.minecraft.item.ItemStack;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class CustomIngredientWrapper implements ICustomIngredient {
    private final ArchCustomIngredient ingredient;
    private final IngredientType<?> type;

    public CustomIngredientWrapper(ArchCustomIngredient ingredient, IngredientType<?> type) {
        this.ingredient = ingredient;
        this.type = type;
    }

    public static CustomIngredientWrapper of(ArchCustomIngredient ingredient, IngredientType<?> type) {
        return new CustomIngredientWrapper(ingredient, type);
    }

    @Override
    public boolean test(@NotNull ItemStack itemStack) {
        return ingredient.test(itemStack);
    }

    @Override
    public @NotNull Stream<ItemStack> getItems() {
        return ingredient.getMatchingStacks().stream();
    }

    @Override
    public boolean isSimple() {
        return ingredient.requiresTesting();
    }

    @Override
    public @NotNull IngredientType<?> getType() {
        return type;
    }

    public ArchCustomIngredient getUnderlyingIngredient() {
        return this.ingredient;
    }
}
