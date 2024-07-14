package me.sshcrack.tinkerers_fletching.item.projectile.tiered;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.entity.arrows.TieredArrowEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TieredArrowItem extends ArrowItem implements ProjectileItem, FletchingItem {
    private final ArrowTier tier;

    public TieredArrowItem(ArrowTier tier) {
        super(new Item.Settings());
        this.tier = tier;
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public int getPower(ItemStack stack) {
        return tier.getPower();
    }

    @Override
    public Item getItem() {
        return this;
    }

    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new TieredArrowEntity(tier, world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        TieredArrowEntity tieredEntity = new TieredArrowEntity(tier, world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
        tieredEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        tieredEntity.setDamage(getPower(stack));

        return tieredEntity;
    }
}
