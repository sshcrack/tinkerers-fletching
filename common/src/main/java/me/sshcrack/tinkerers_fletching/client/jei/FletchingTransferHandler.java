package me.sshcrack.tinkerers_fletching.client.jei;

import com.google.gson.internal.NonNullElementWrapperList;
import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.ingredient.IRecipeSlotView;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.transfer.IRecipeTransferError;
import mezz.jei.api.recipe.transfer.IRecipeTransferHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FletchingTransferHandler implements IRecipeTransferHandler<FletchingScreenHandler, RecipeEntry<FletchingRecipe>> {
    public static DefaultedList<ItemStack> getFirstItemStacks(IRecipeSlotsView recipeSlots) {
        List<IRecipeSlotView> slotViews = recipeSlots.getSlotViews(RecipeIngredientRole.INPUT);
        return slotViews.stream()
                .map(FletchingTransferHandler::getFirstItemStack)
                .collect(Collectors.toCollection(DefaultedList::of));
    }

    private static ItemStack getFirstItemStack(IRecipeSlotView slotView) {
        return slotView.getDisplayedIngredient(VanillaTypes.ITEM_STACK)
                .or(() -> slotView.getIngredients(VanillaTypes.ITEM_STACK).findFirst())
                .map(ItemStack::copy)
                .orElse(ItemStack.EMPTY);
    }

    @Override
    public @NotNull Class<? extends FletchingScreenHandler> getContainerClass() {
        return FletchingScreenHandler.class;
    }

    @Override
    public @NotNull Optional<ScreenHandlerType<FletchingScreenHandler>> getMenuType() {
        return Optional.of(TinkerersMod.FLETCHING_SCREEN_HANDLER.get());
    }

    @Override
    public @NotNull RecipeType<RecipeEntry<FletchingRecipe>> getRecipeType() {
        return RecipeTypesJei.FLETCHING_RECIPE_TYPE;
    }

    @Override
    public @Nullable IRecipeTransferError transferRecipe(FletchingScreenHandler container, RecipeEntry<FletchingRecipe> recipe, IRecipeSlotsView recipeSlots, PlayerEntity player, boolean maxTransfer, boolean doTransfer) {
        //TODO actually tell server and stuff but I'm wayy to lazy
        if (doTransfer) {
            DefaultedList<ItemStack> firstItemStacks = getFirstItemStacks(recipeSlots);
            for (int i = 0; i < firstItemStacks.size(); i++) {

                container.setStackInSlot(i, container.nextRevision(), firstItemStacks.get(i));
            }

            var recipes = player.getWorld().getRecipeManager().listAllOfType(FletchingRecipe.Type.INSTANCE).stream()
                    .toList();

            var matchingRecipe = recipes.stream()
                    .filter(recipeHolder -> recipeHolder.value() == recipe.value())
                    .findFirst()
                    .orElse(null);
            if (matchingRecipe == null) {
                return () -> IRecipeTransferError.Type.INTERNAL;
            }
        }

        return null;
    }
}
