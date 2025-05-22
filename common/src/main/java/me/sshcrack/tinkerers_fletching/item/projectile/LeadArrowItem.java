package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Locale;

public class LeadArrowItem extends ArrowItem implements FletchingItem {
    public LeadArrowItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 6;
    }


    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        var entity = new LeadArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
        entity.setRopeAttachedTo(shooter);

        return entity;
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        LeadArrowEntity leadArrow = new LeadArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
        leadArrow.setDamage(getPower(stack));

        return leadArrow;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.tinkerers_fletching.lead_arrow.description.1").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.tinkerers_fletching.lead_arrow.description.2").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.tinkerers_fletching.lead_arrow.description.3").formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("item.tinkerers_fletching.lead_arrow.description.elytra.1").formatted(Formatting.RED));
        tooltip.add(Text.translatable("item.tinkerers_fletching.lead_arrow.description.elytra.2").formatted(Formatting.RED));
    }
}
