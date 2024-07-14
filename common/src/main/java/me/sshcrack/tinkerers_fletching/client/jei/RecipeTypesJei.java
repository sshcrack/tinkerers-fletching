package me.sshcrack.tinkerers_fletching.client.jei;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.recipe.RecipeEntry;

public class RecipeTypesJei {
    public static final RecipeType<RecipeEntry<FletchingRecipe>> FLETCHING_RECIPE_TYPE;

    static {
        @SuppressWarnings({"RedundantCast", "unchecked"})
        Class<? extends RecipeEntry<FletchingRecipe>> holderClass = (Class<? extends RecipeEntry<FletchingRecipe>>) (Object) RecipeEntry.class;

        FLETCHING_RECIPE_TYPE = RecipeType.create(TinkerersMod.MOD_ID, FletchingRecipe.Type.ID.getPath(), holderClass);
    }
}
