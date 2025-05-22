package me.sshcrack.tinkerers_fletching.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.duck.EntityFallDamageReducerDuck;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin implements EntityFallDamageReducerDuck {
    @Shadow public float fallDistance;
    @Unique
    private float fallDamageMultiplier = 1.0F;

    @Override
    public void tinkerers$setFallDamageMultiplier(float multiplier) {
        this.fallDamageMultiplier = multiplier;
    }

    @Inject(method="fall", at= @At("HEAD"))
    private void tinkerers$applyFallDamageMultiplier(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition, CallbackInfo ci) {
        if(this.fallDistance > 0.0F && onGround) {
            this.fallDistance *= fallDamageMultiplier;
        }
    }
}
