package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;

@Environment(EnvType.CLIENT)
public class FletchingScreen extends ForgingScreen<FletchingScreenHandler> {
    private static final Identifier ERROR_TEXTURE = Identifier.ofVanilla("container/smithing/error");
    private static final Identifier EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE_TEXTURE = Identifier.ofVanilla(
            "item/empty_slot_smithing_template_netherite_upgrade"
    );
    private static final Text MISSING_TEMPLATE_TOOLTIP = Text.translatable("container.upgrade.missing_template_tooltip");
    private static final Text ERROR_TOOLTIP = Text.translatable("container.upgrade.error_tooltip");
    private static final List<Identifier> EMPTY_SLOT_TEXTURES = List.of(EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE_TEXTURE);
    private final CyclingSlotIcon templateSlotIcon = new CyclingSlotIcon(0);
    private final CyclingSlotIcon baseSlotIcon = new CyclingSlotIcon(1);
    private final CyclingSlotIcon additionsSlotIcon = new CyclingSlotIcon(2);

    private final FletchingRecipeBookScreen recipeBook;

    public FletchingScreen(FletchingScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, Identifier.of(TinkerersMod.MOD_ID, "textures/gui/container/fletching.png"));
        this.titleX = 44;
        this.titleY = 15;

        recipeBook = new FletchingRecipeBookScreen();
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void setup() {
        /* sdf
        this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
        this.armorStand.setHideBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.bodyYaw = 210.0F;
        this.armorStand.setPitch(25.0F);
        this.armorStand.headYaw = this.armorStand.getYaw();
        this.armorStand.prevHeadYaw = this.armorStand.getYaw();
         */
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        //Optional<SmithingTemplateItem> optional = this.getFletchingTemplates();
        this.templateSlotIcon.updateTexture(EMPTY_SLOT_TEXTURES);
        //this.baseSlotIcon.updateTexture(optional.map(SmithingTemplateItem::getEmptyBaseSlotTextures).orElse(List.of()));
        //this.additionsSlotIcon.updateTexture(optional.map(SmithingTemplateItem::getEmptyAdditionsSlotTextures).orElse(List.of()));
    }

    /*
        private Optional<SmithingTemplateItem> getFletchingTemplates() {
            ItemStack itemStack = this.handler.getSlot(0).getStack();
            return !itemStack.isEmpty() && itemStack.getItem() instanceof SmithingTemplateItem smithingTemplateItem
                    ? Optional.of(smithingTemplateItem)
                    : Optional.empty();
        }
    */
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.renderSlotTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        super.drawBackground(context, delta, mouseX, mouseY);
        this.templateSlotIcon.render(this.handler, context, delta, this.x, this.y);
        this.baseSlotIcon.render(this.handler, context, delta, this.x, this.y);
        this.additionsSlotIcon.render(this.handler, context, delta, this.x, this.y);
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 3) {
            //this.equipArmorStand(stack);
        }
    }

    @Override
    protected void drawInvalidRecipeArrow(DrawContext context, int x, int y) {
        if (this.hasInvalidRecipe()) {
            context.drawGuiTexture(ERROR_TEXTURE, x + 65, y + 46, 28, 21);
        }
    }

    private void renderSlotTooltip(DrawContext context, int mouseX, int mouseY) {
        Optional<Text> optional = Optional.empty();
        if (this.hasInvalidRecipe() && this.isPointWithinBounds(65, 46, 28, 21, mouseX, mouseY)) {
            optional = Optional.of(ERROR_TOOLTIP);
        }

        if (this.focusedSlot != null) {
            ItemStack itemStack = this.handler.getSlot(0).getStack();
            ItemStack itemStack2 = this.focusedSlot.getStack();
            if (itemStack.isEmpty()) {
                if (this.focusedSlot.id == 0) {
                    optional = Optional.of(MISSING_TEMPLATE_TOOLTIP);
                }
            }/* else if (itemStack.getItem() instanceof SmithingTemplateItem smithingTemplateItem && itemStack2.isEmpty()) {
                if (this.focusedSlot.id == 1) {
                    optional = Optional.of(smithingTemplateItem.getBaseSlotDescription());
                } else if (this.focusedSlot.id == 2) {
                    optional = Optional.of(smithingTemplateItem.getAdditionsSlotDescription());
                }
            }*/
        }

        optional.ifPresent(text -> context.drawOrderedTooltip(this.textRenderer, this.textRenderer.wrapLines(text, 115), mouseX, mouseY));
    }

    private boolean hasInvalidRecipe() {
        return this.handler.getSlot(0).hasStack()
                && this.handler.getSlot(1).hasStack()
                && this.handler.getSlot(2).hasStack()
                && !this.handler.getSlot(this.handler.getResultSlotIndex()).hasStack();
    }
}
