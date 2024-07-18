package me.sshcrack.tinkerers_fletching.item;


import me.sshcrack.tinkerers_fletching.duck.PseudoEnchantmentSupport;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class BowOfAccelerationItem extends BowItem implements FletchingItem, PseudoEnchantmentSupport {
    public static float VELOCITY_MULTIPLIER = 1.5f;
    public static double MAX_DAMAGE_MULTIPLIER = 1.2d;

    public BowOfAccelerationItem() {
        super(new Item.Settings().maxDamage((int) (384d * MAX_DAMAGE_MULTIPLIER)));
    }

    @Override
    protected void shoot(LivingEntity shooter, ProjectileEntity projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
        super.shoot(shooter, projectile, index, speed * VELOCITY_MULTIPLIER, divergence, yaw, target);
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.VERY_FAST;
    }

    @Override
cd .        return 5;
    }

    @Override
    public @Nullable Identifier getResultTexture() {
        return null;
    }

    @Override
    public int getRange() {
        return (int) (super.getRange() * VELOCITY_MULTIPLIER);
    }

    @Override
    public Item getPseudoEnchantmentItem() {
        return Items.BOW;
    }
}