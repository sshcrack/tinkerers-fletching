package me.sshcrack.tinkerers_fletching;

import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipe;
import me.sshcrack.tinkerers_fletching.recipe.FletchingRecipeInput;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import net.minecraft.screen.slot.Slot;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.OptionalInt;

public class FletchingScreenHandler extends AbstractRecipeScreenHandler<FletchingRecipeInput, FletchingRecipe> {
    public static int RECIPE_BOOK_OFFSET = 50;
    protected final World world;

    @Nullable
    private RecipeEntry<FletchingRecipe> currentRecipe;
    private final List<RecipeEntry<FletchingRecipe>> recipes;

    // From ForgingScreenHandler
    protected final ScreenHandlerContext context;
    protected final PlayerEntity player;
    protected final Inventory input;
    private final List<Integer> inputSlotIndices;
    protected final CraftingResultInventory output = new CraftingResultInventory();
    private final int resultSlotIndex;

    // End

    public FletchingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public FletchingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(TinkerersMod.FLETCHING_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.getWorld();
        this.player = playerInventory.player;
        this.recipes = this.world.getRecipeManager().listAllOfType(FletchingRecipe.Type.INSTANCE);
        this.context = context;

        ForgingSlotsManager forgingSlotsManager = this.getForgingSlotsManager();
        this.input = this.createInputInventory(forgingSlotsManager.getInputSlotCount());
        this.inputSlotIndices = forgingSlotsManager.getInputSlotIndices();
        this.resultSlotIndex = forgingSlotsManager.getResultSlotIndex();
        this.addInputSlots(forgingSlotsManager);
        this.addResultSlot(forgingSlotsManager);
        this.addPlayerInventorySlots(playerInventory);

    }

    // Straight up yeeted

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }

    }

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> {
            this.dropInventory(player, this.input);
        });
    }

    private SimpleInventory createInputInventory(int size) {
        return new SimpleInventory(size) {

            @Override
            public void markDirty() {
                super.markDirty();
                FletchingScreenHandler.this.onContentChanged(this);
            }
        };
    }

    private void addInputSlots(ForgingSlotsManager forgingSlotsManager) {
        for (final ForgingSlotsManager.ForgingSlot forgingSlot : forgingSlotsManager.getInputSlots()) {
            this.addSlot(new Slot(this.input, forgingSlot.slotId(), forgingSlot.x(), forgingSlot.y()) {

                @Override
                public boolean canInsert(ItemStack stack) {
                    return forgingSlot.mayPlace().test(stack);
                }
            });
        }
    }

    private void addResultSlot(ForgingSlotsManager forgingSlotsManager) {
        this.addSlot(new Slot(this.output, forgingSlotsManager.getResultSlot().slotId(), forgingSlotsManager.getResultSlot().x(), forgingSlotsManager.getResultSlot().y()) {

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public boolean canTakeItems(PlayerEntity playerEntity) {
                return FletchingScreenHandler.this.canTakeOutput(playerEntity, this.hasStack());
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                FletchingScreenHandler.this.onTakeOutput(player, stack);
            }
        });
    }

    private void addPlayerInventorySlots(PlayerInventory playerInventory) {
        int i;
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }


    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        if (this.input instanceof RecipeInputProvider) {
            ((RecipeInputProvider) this.input).provideRecipeInputs(finder);
        }
    }

    @Override
    public void clearCraftingSlots() {
        this.getSlot(0).setStackNoCallbacks(ItemStack.EMPTY);
        this.getSlot(2).setStackNoCallbacks(ItemStack.EMPTY);
    }


    @Override
    public boolean matches(RecipeEntry<FletchingRecipe> recipe) {
        return recipe.value().matches(createRecipeInput(), this.world);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return 2;
    }

    @Override
    public int getCraftingWidth() {
        return 1;
    }

    @Override
    public int getCraftingHeight() {
        return 1;
    }

    @Override
    public int getCraftingSlotCount() {
        return 3;
    }

    public int getResultSlotIndex() {
        return this.resultSlotIndex;
    }

    private int getPlayerInventoryStartIndex() {
        return this.getResultSlotIndex() + 1;
    }

    private int getPlayerInventoryEndIndex() {
        return this.getPlayerInventoryStartIndex() + 27;
    }

    private int getPlayerHotbarStartIndex() {
        return this.getPlayerInventoryEndIndex();
    }

    private int getPlayerHotbarEndIndex() {
        return this.getPlayerHotbarStartIndex() + 9;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.context.get((world, pos) -> this.canUse(world.getBlockState(pos)) && player.canInteractWithBlockAt(pos, 4.0), true);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        Slot slot = this.slots.get(slotIndex);
        if (!slot.hasStack())
            return ItemStack.EMPTY;

        ItemStack itemStack = slot.getStack();
        ItemStack resultStack = itemStack.copy();

        int i = this.getPlayerInventoryStartIndex();
        int j = this.getPlayerHotbarEndIndex();
        if (slotIndex == this.getResultSlotIndex()) {
            if (!this.insertItem(itemStack, i, j, true))
                return ItemStack.EMPTY;

            slot.onQuickTransfer(itemStack, itemStack);
        } else if (this.inputSlotIndices.contains(slotIndex) ? !this.insertItem(itemStack, i, j, false) : (this.isValidIngredient(itemStack) && slotIndex >= this.getPlayerInventoryStartIndex() && slotIndex < this.getPlayerHotbarEndIndex() ? !this.insertItem(itemStack, this.getSlotFor(itemStack), this.getResultSlotIndex(), false) : (slotIndex >= this.getPlayerInventoryStartIndex() && slotIndex < this.getPlayerInventoryEndIndex() ? !this.insertItem(itemStack, this.getPlayerHotbarStartIndex(), this.getPlayerHotbarEndIndex(), false) : slotIndex >= this.getPlayerHotbarStartIndex() && slotIndex < this.getPlayerHotbarEndIndex() && !this.insertItem(itemStack, this.getPlayerInventoryStartIndex(), this.getPlayerInventoryEndIndex(), false)))) {
            return ItemStack.EMPTY;
        }
        if (itemStack.isEmpty()) {
            slot.setStack(ItemStack.EMPTY);
        } else {
            slot.markDirty();
        }
        if (itemStack.getCount() == itemStack.getCount())
            return ItemStack.EMPTY;

        slot.onTakeItem(player, itemStack);

        return resultStack;
    }


    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.FURNACE;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != 1;
    }


    // Yeeted from smithing table

    protected boolean canUse(BlockState state) {
        return state.isOf(Blocks.FLETCHING_TABLE);
    }

    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return this.currentRecipe != null && this.currentRecipe.value().matches(this.createRecipeInput(), this.world);
    }

    protected void onTakeOutput(PlayerEntity player, ItemStack stack) {
        stack.onCraftByPlayer(player.getWorld(), player, stack.getCount());
        this.output.unlockLastRecipe(player, this.getInputStacks());
        this.decrementStack(0);
        this.decrementStack(1);
        this.decrementStack(2);
        this.context.run((world, pos) -> world.syncWorldEvent(TinkerersMod.FLETCHING_USED_WORLD_EVENT, pos, 0));
    }

    private List<ItemStack> getInputStacks() {
        return List.of(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
    }

    private FletchingRecipeInput createRecipeInput() {
        return new FletchingRecipeInput(this.input.getStack(0), this.input.getStack(1), this.input.getStack(2));
    }

    private void decrementStack(int slot) {
        ItemStack itemStack = this.input.getStack(slot);
        if (!itemStack.isEmpty()) {
            itemStack.decrement(1);
            this.input.setStack(slot, itemStack);
        }
    }

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


    protected ForgingSlotsManager getForgingSlotsManager() {

        return ForgingSlotsManager.create()
                .input(0, 8 + RECIPE_BOOK_OFFSET, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testTemplate(stack)))
                .input(1, 26 + RECIPE_BOOK_OFFSET, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testBase(stack)))
                .input(2, 44 + RECIPE_BOOK_OFFSET, 48, stack -> this.recipes.stream().anyMatch(recipe -> recipe.value().testAddition(stack)))
                .output(3, 98 + RECIPE_BOOK_OFFSET, 48)
                .build();
    }
}
