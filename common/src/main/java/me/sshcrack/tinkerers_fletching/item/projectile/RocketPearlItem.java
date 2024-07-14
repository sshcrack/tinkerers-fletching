package me.sshcrack.tinkerers_fletching.item.projectile;

import me.sshcrack.tinkerers_fletching.entity.RocketPearlEntity;
import me.sshcrack.tinkerers_fletching.item.FletchingItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RocketPearlItem extends Item implements FletchingItem {
    protected final Random random = Random.create();

    public RocketPearlItem() {
        super(new Settings().maxCount(16));
    }

    @Override
    public SpeedLevel getSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @Override
    public int getPower(ItemStack stack) {
        return 6;
    }

    @Override
    public @Nullable Identifier getBaseTexture() {
        return null;
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_ENDER_PEARL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FIREWORK_ROCKET_LAUNCH, SoundCategory.AMBIENT, 3.0f, 1.0f);

        user.getItemCooldownManager().set(this, 20);
        if (!world.isClient) {
            RocketPearlEntity rocketPearl = new RocketPearlEntity(user, world);
            rocketPearl.setItem(itemStack);
            rocketPearl.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 3F, 1.0F);
            world.spawnEntity(rocketPearl);
        }


        world.addParticle(ParticleTypes.FIREWORK, user.getX(), user.getY(), user.getZ(), random.nextGaussian() * 0.05, 0, random.nextGaussian() * 0.05);

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack, world.isClient());
    }
}
