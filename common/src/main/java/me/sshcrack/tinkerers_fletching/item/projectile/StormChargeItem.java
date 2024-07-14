package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.entity.StormChargeEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.block.DispenserBlock;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class StormChargeItem extends Item implements ProjectileItem, FletchingItem {
    public StormChargeItem() {
        super(new Item.Settings());
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 5;
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            StormChargeEntity entity = new StormChargeEntity(user, world, user.getPos().getX(), user.getEyePos().getY(), user.getPos().getZ());
            entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, 0.3f, 1.0f);
            world.spawnEntity(entity);
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_WIND_CHARGE_THROW, SoundCategory.NEUTRAL, 0.5f, 0.4f / (world.getRandom().nextFloat() * 0.4f + 0.8f));
        ItemStack itemStack = user.getStackInHand(hand);
        user.getItemCooldownManager().set(this, 10);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
        Random random = world.getRandom();
        double d = random.nextTriangular(direction.getOffsetX(), 0.11485000000000001);
        double e = random.nextTriangular(direction.getOffsetY(), 0.11485000000000001);
        double f = random.nextTriangular(direction.getOffsetZ(), 0.11485000000000001);
        Vec3d vec3d = new Vec3d(d, e, f);

        StormChargeEntity entity = new StormChargeEntity(world, pos.getX(), pos.getY(), pos.getZ(), vec3d);
        entity.setVelocity(vec3d);
        return entity;
    }

    @Override
    public void initializeProjectile(ProjectileEntity entity, double x, double y, double z, float power, float uncertainty) {
    }

    @Override
    public ProjectileItem.Settings getProjectileSettings() {
        return ProjectileItem.Settings.builder().positionFunction((pointer, facing) -> DispenserBlock.getOutputLocation(pointer, 1.0, Vec3d.ZERO)).uncertainty(6.6666665f).power(1.0f).overrideDispenseEvent(1051).build();
    }
}
