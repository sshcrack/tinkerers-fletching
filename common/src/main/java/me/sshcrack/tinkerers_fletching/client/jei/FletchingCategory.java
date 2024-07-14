package me.sshcrack.tinkerers_fletching.client.jei;

import com.google.common.collect.Lists;
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
import mezz.jei.common.Internal;
import mezz.jei.common.gui.textures.Textures;
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

import java.util.ArrayList;
import java.util.List;

public class FletchingCategory implements IRecipeCategory<RecipeEntry<FletchingRecipe>> {
    private final IDrawable background;
    private final IDrawable slot;
    private final IDrawable icon;
    private final Text localizedName;
    private final IDrawable recipeArrow;

    public FletchingCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(108, 28);
        slot = guiHelper.getSlotDrawable();

        icon = guiHelper.createDrawableItemStack(new ItemStack(Blocks.FLETCHING_TABLE));
        localizedName = Text.translatable("gui.tinkerers_fletching.category.brewing");

        Textures textures = Internal.getTextures();
        recipeArrow = textures.getRecipeArrow();
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

        List<ItemStack> stack = new ArrayList<>();
        var template = recipe.getTemplateIngredient();
        if (template != null)
            stack = template.getMatchingStacks();

        builder.addSlot(RecipeIngredientRole.INPUT, 1, 6)
                .setBackground(slot, -1, -1)
                .addItemStacks(stack);

        builder.addSlot(RecipeIngredientRole.INPUT, 19, 6)
                .setBackground(slot, -1, -1)
                .addItemStacks(recipe.getBaseIngredient().getMatchingStacks());

        builder.addSlot(RecipeIngredientRole.INPUT, 37, 6)
                .setBackground(slot, -1, -1)
                .addItemStacks(recipe.getAdditionIngredient().getMatchingStacks());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 91, 6)
                .setBackground(slot, -1, -1)
                .addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public void draw(RecipeEntry<FletchingRecipe> recipe, IRecipeSlotsView recipeSlotsView, DrawContext guiGraphics, double mouseX, double mouseY) {
        recipeArrow.draw(guiGraphics, 61, 7);
    }

    @Override
    public @Nullable Identifier getRegistryName(RecipeEntry<FletchingRecipe> recipe) {
        return recipe.id();
    }
}