package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.entity.arrows.TieredArrowEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.TntArrowEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TntArrowItem extends ArrowItem implements FletchingItem {
    public TntArrowItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.SLOW;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 5;
    }


    @Override
    public PersistentProjectileEntity createArrow(World world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
        return new TntArrowEntity(world, shooter, stack.copyWithCount(1), shotFrom);
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        TntArrowEntity tntArrow = new TntArrowEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1), null);
        tntArrow.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
        tntArrow.setDamage(getPower(stack));

        return tntArrow;
    }
}
