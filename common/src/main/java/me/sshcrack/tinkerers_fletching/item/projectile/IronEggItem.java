package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.entity.IronEggEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class IronEggItem extends Item implements ProjectileItem, FletchingItem {
    public IronEggItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 6;
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        if (!world.isClient) {
            IronEggEntity ironEgg = new IronEggEntity(world, user);
            ironEgg.setItem(itemStack);
            ironEgg.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
            world.spawnEntity(ironEgg);
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        IronEggEntity ironEgg = new IronEggEntity(world, pos.getX(), pos.getY(), pos.getZ());
        ironEgg.setItem(stack);
        return ironEgg;
    }
}
