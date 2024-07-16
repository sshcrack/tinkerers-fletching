package me.sshcrack.tinkerers_fletching.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.sshcrack.tinkerers_fletching.duck.ExplosionDamageSourceDuck;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Collection;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    @Final
    private DamageSource damageSource;

    @WrapOperation(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;addAll(Ljava/util/Collection;)Z"))
    private boolean tinkerers$onCollectBlocksAndDamageEntities(ObjectArrayList<BlockPos> instance, Collection<BlockPos> collection, Operation<Boolean> original) {
        if (!(damageSource instanceof ExplosionDamageSourceDuck duck) || !duck.tinkerers$hasModified())
            return original.call(instance, collection);

        var res = original.call(instance, duck.tinkerers$getBlocksToDestroy());

        if (duck.tinkerers$shouldDestroyOtherBlocks())
            res = res && original.call(instance, collection);

        return res;
    }
}
