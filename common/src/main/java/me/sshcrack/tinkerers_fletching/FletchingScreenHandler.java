package me.sshcrack.tinkerers_fletching;

import me.sshcrack.tinkerers_fletching.recipe.CountedIngredient;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipeInput;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public class FletchingScreenHandler extends ForgingScreenHandler {
    private final World world;
    @Nullable
    private RecipeEntry<FletchingRecipe> currentRecipe;
    private final List<RecipeEntry<FletchingRecipe>> recipes;

    public FletchingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public FletchingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), syncId, playerInventory, context);
        this.world = playerInventory.player.getWorld();
        this.recipes = this.world.getRecipeManager().listAllOfType(FletchingRecipe.Type.INSTANCE);
    }


    @Override
    protected ForgingSlotsManager getForgingSlotsManager() {
        return ForgingSlotsManager.create()
                .input(0, 8, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testTemplate(stack)))
                .input(1, 26, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testBase(stack)))
                .input(2, 44, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testAddition(stack)))
                .output(3, 98, 48)
                .build();
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isOf(Blocks.FLETCHING_TABLE);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.currentRecipe != null && this.currentRecipe.value().matches(this.createRecipeInput(), this.world);
    }

    @Override
    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        if (currentRecipe == null)
            return;

        stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
        this.output.unlockLastRecipe(player, this.getInputStacks());

        var val = currentRecipe.value();
        var templateCount = 1;
        var baseCount = 1;
        var additionCount = 1;

        if (val.getTemplateIngredient() instanceof CountedIngredient i)
            templateCount = i.count();

        if (val.getBaseIngredient() instanceof CountedIngredient i)
            baseCount = i.count();

        if (val.getAdditionIngredient() instanceof CountedIngredient i)
            additionCount = i.count();

        this.decrementStack(0, templateCount);
        this.decrementStack(1, baseCount);
        this.decrementStack(2, additionCount);
        this.context.run((world, pos) -> world.syncWorldEvent(TinkerersMod.FLETCHING_USED_WORLD_EVENT, pos, 0));
    }

    private List<ItemStack> getInputStacks() {
        return List.of(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
    }

    private FletchingRecipeInput createRecipeInput() {
        return new FletchingRecipeInput(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
    }

    private void decrementStack(int slot, int count) {
        ItemStack itemStack = this.input.getStack(slot);
        if (!itemStack.isEmpty()) {
            itemStack.decrement(count);
            this.input.setStack(slot, itemStack);
        }
    }

    @Override
    public void updateResult() {
        FletchingRecipeInput fletchingRecipeInput = this.createRecipeInput();
        List<RecipeEntry<FletchingRecipe>> list = this.world.getRecipeManager().getAllMatches(FletchingRecipe.Type.INSTANCE, fletchingRecipeInput, this.world);
        if (list.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
        } else {
            RecipeEntry<FletchingRecipe> recipeEntry = list.getFirst();
            ItemStack itemStack = recipeEntry.value().craft(fletchingRecipeInput, this.world.getRegistryManager());
            if (itemStack.isItemEnabled(this.world.getEnabledFeatures())) {
                this.currentRecipe = recipeEntry;
                this.output.setLastRecipe(recipeEntry);
                this.output.setStack(0, itemStack);
            }
        }
    }

    @Override
    public int getSlotFor(ItemStack stack) {
        return this.getQuickMoveSlot(stack).orElse(0);
    }

    private static OptionalInt getQuickMoveSlot(FletchingRecipe recipe, ItemStack stack) {
        if (recipe.testTemplate(stack)) {
            return OptionalInt.of(0);
        } else if (recipe.testBase(stack)) {
            return OptionalInt.of(1);
        } else {
            return recipe.testAddition(stack) ? OptionalInt.of(2) : OptionalInt.empty();
        }
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.output && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public boolean isValidIngredient(ItemStack stack) {
        return this.getQuickMoveSlot(stack).isPresent();
    }

    private OptionalInt getQuickMoveSlot(ItemStack stack) {
        return this.recipes
                .stream()
                .flatMapToInt(recipe -> getQuickMoveSlot(recipe.value(), stack).stream())
                .filter(slot -> !this.getSlot(slot).hasStack())
                .findFirst();
    }
}
