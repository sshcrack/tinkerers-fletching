package me.sshcrack.tinkerers_fletching.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.duck.CustomBowVelocity;
import me.sshcrack.tinkerers_fletching.duck.ShootSoundOverwriteProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BowItem.class)
public class BowItemMixin {
    @Unique
    private ItemStack tinkerers$projectileStack;

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;getProjectileType(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;"))
    private ItemStack tinkerers$storeProjectileStack(PlayerEntity instance, ItemStack stack, Operation<ItemStack> original) {
        tinkerers$projectileStack = original.call(instance, stack);
        return tinkerers$projectileStack;
    }

    @WrapOperation(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V"))
    private void tinkerers$onStoppedUsing(World instance, PlayerEntity source, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, Operation<Void> original) {
        if (tinkerers$projectileStack.getItem() instanceof ShootSoundOverwriteProvider provider) {
            original.call(instance, source, x, y, z, provider.getShootSound(), category, volume, pitch);
        }

        original.call(instance, source, x, y, z, sound, category, volume, pitch);
    }

    @WrapOperation(method = "shoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V"))
    private void tinkerers$setVelocity(ProjectileEntity instance, Entity shooter, float pitch, float yaw, float roll, float speed, float divergence, Operation<Void> original) {
        if (instance instanceof CustomBowVelocity custom)
            speed *= custom.getBowVelocityMultiplier(shooter, pitch, yaw, roll, speed, divergence);

        original.call(instance, shooter, pitch, yaw, roll, speed, divergence);
    }
}
