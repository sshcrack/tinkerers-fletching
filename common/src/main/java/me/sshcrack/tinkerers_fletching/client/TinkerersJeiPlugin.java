package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import me.sshcrack.tinkerers_fletching.Recipes;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.jei.CountedIngredientType;
import me.sshcrack.tinkerers_fletching.client.jei.FletchingCategory;
import me.sshcrack.tinkerers_fletching.client.jei.RecipeTypesJei;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.*;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("unused")
@JeiPlugin
public class TinkerersJeiPlugin implements IModPlugin {
    @Nullable
    private IRecipeCategory<RecipeEntry<FletchingRecipe>> fletchingCategory;

    @Override
    public @NotNull Identifier getPluginUid() {
        return Identifier.of(TinkerersMod.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var helper = registration.getJeiHelpers().getGuiHelper();

        fletchingCategory = new FletchingCategory(helper);

        registration.addRecipeCategories(fletchingCategory);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        assert fletchingCategory != null : "Fletching category must not be null";

        var ingredientManager = registration.getIngredientManager();
        var minecraft = MinecraftClient.getInstance();

        assert minecraft.world != null : "Minecraft world must not be null";
        var manager = minecraft.world.getRecipeManager();

        var recipes = manager.listAllOfType(Recipes.FLETCHING_RECIPE_TYPE.get());
        registration.addRecipes(RecipeTypesJei.FLETCHING_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FletchingScreen.class, 68, 49, 22, 15, RecipeTypesJei.FLETCHING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Blocks.FLETCHING_TABLE), RecipeTypesJei.FLETCHING_RECIPE_TYPE);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(FletchingScreenHandler.class, TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), RecipeTypesJei.FLETCHING_RECIPE_TYPE, 0, 3, 3, 36);
    }
}
