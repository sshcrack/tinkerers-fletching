package me.sshcrack.tinkerers_fletching.client.jei;

import me.sshcrack.tinkerers_fletching.client.FletchingScreen;
import me.sshcrack.tinkerers_fletching.recipe.CountedIngredient;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FletchingCategory implements IRecipeCategory<RecipeEntry<FletchingRecipe>> {
    private final IDrawable background;
    private final IDrawable icon;
    private final Text localizedName;

    public FletchingCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(FletchingScreen.FLETCHING_TEXTURE, 0, 0, 176, 166)
                .addPadding(1, 0, 0, 50)
                .build();
        icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.FLETCHING_TABLE));
        localizedName = Text.translatable("gui.tinkerers_fletching.category.brewing");
    }

    @Override
    public @NotNull RecipeType<RecipeEntry<FletchingRecipe>> getRecipeType() {
        return RecipeTypesJei.FLETCHING_RECIPE_TYPE;
    }

    @Override
    public @NotNull Text getTitle() {
        return localizedName;
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeEntry<FletchingRecipe> recipeHolder, IFocusGroup focuses) {
        var recipe = recipeHolder.value();

        var template = recipe.getTemplateIngredient();
        if (template != null) {
            builder.addSlot(RecipeIngredientRole.INPUT, 8, 48)
                    .addItemStacks(template.getMatchingStacks());
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 48)
                .addItemStacks(recipe.getBaseIngredient().getMatchingStacks());

        builder.addSlot(RecipeIngredientRole.INPUT, 44, 48)
                .addItemStacks(recipe.getAdditionIngredient().getMatchingStacks());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 48)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public @Nullable Identifier getRegistryName(RecipeEntry<FletchingRecipe> recipe) {
        return recipe.id();
    }
}