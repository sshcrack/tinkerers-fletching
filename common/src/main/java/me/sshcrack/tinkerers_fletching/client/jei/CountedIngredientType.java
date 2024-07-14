package me.sshcrack.tinkerers_fletching.client.jei;

import mezz.jei.api.ingredients.IIngredientType;
import org.jetbrains.annotations.NotNull;

public class CountedIngredientType implements IIngredientType<CountedIngredientType> {
    public static CountedIngredientType INSTANCE = new CountedIngredientType();

    private CountedIngredientType() {

    }

    @Override
    public @NotNull Class<? extends CountedIngredientType> getIngredientClass() {
        return CountedIngredientType.class;
    }
}
