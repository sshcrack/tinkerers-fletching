package me.sshcrack.tinkerers_fletching.client;

import com.mojang.blaze3d.systems.RenderSystem;
import me.sshcrack.tinkerers_fletching.FletchingScreenHandler;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.CyclingSlotIcon;
import net.minecraft.client.gui.screen.ingame.ForgingScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

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

    private static final Text POWER_TEXT = Text.translatable("container.tinkerers_fletching.power");
    private static final Text SPEED_TEXT = Text.translatable("container.tinkerers_fletching.speed");

    private static final List<Identifier> EMPTY_SLOT_TEXTURES = List.of(EMPTY_SLOT_SMITHING_TEMPLATE_NETHERITE_UPGRADE_TEXTURE);
    private final CyclingSlotIcon templateSlotIcon = new CyclingSlotIcon(0);
    private final CyclingSlotIcon baseSlotIcon = new CyclingSlotIcon(1);
    private final CyclingSlotIcon additionsSlotIcon = new CyclingSlotIcon(2);

    private final FletchingRecipeBookScreen recipeBook;
    @Nullable
    private ItemStack resItem;

    public FletchingScreen(FletchingScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title, Identifier.of(TinkerersMod.MOD_ID, "textures/gui/container/fletching.png"));
        this.titleX = 44;
        this.titleY = 4;

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
        if (resItem != null) {
            var xPos = 117;
            var yPos = 22;

            var size = 55;
            var item = (FletchingItem) this.resItem.getItem();
            if (item.getBaseTexture() != null) {
                context.drawGuiTexture(item.getBaseTexture(), this.x + xPos, this.y + yPos, size, size);
            }
            context.drawGuiTexture(item.getResultTexture(), this.x + xPos, this.y + yPos, size, size);

            context.drawText(this.textRenderer, POWER_TEXT, this.x + 44, this.y + 15, 0x8b898a, false);
            renderHealthBar(context, this.x + 77, this.y + 15, 6, item.getPower(resItem));

            context.drawText(this.textRenderer, SPEED_TEXT, this.x + 44, this.y + 35, 0x8b898a, false);

            context.drawGuiTexture(item.getSpeedLevel().getTexture(), this.x + 77, this.y + 35 - 8, 16, 16);
        }

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
        if (slotId != 3)
            return;
        if (!(stack.getItem() instanceof FletchingItem)) {
            resItem = null;
            return;
        }

        resItem = stack;
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


    private void renderHealthBar(DrawContext context, int x, int y, int maxHealth, int health) {
        drawHearts(context, InGameHud.HeartType.CONTAINER, x, y, maxHealth);
        drawHearts(context, InGameHud.HeartType.NORMAL, x, y, health);
    }

    private void drawHearts(DrawContext context, InGameHud.HeartType type, int x, int y, int health) {
        for (int i = 0; i < health; i++) {
            var xOffset = x + (i / 2) * 8;
            var isLastHalf = i == health - 1;
            if (!isLastHalf)
                i++;
            this.drawHeart(context, type, xOffset, y, isLastHalf);
        }
    }

    private void drawHeart(DrawContext context, InGameHud.HeartType type, int x, int y, boolean half) {
        RenderSystem.enableBlend();
        context.drawGuiTexture(type.getTexture(false, half, false), x, y, 9, 9);
        RenderSystem.disableBlend();
    }
}
