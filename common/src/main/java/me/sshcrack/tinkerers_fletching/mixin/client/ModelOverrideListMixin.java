package me.sshcrack.tinkerers_fletching.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.client.BowPullingDummyDuck;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("deprecation")
@Mixin(ModelOverrideList.class)
@Environment(EnvType.CLIENT)
public class ModelOverrideListMixin {
    @Shadow
    @Final
    private Identifier[] conditionTypes;

    @Unique
    Identifier tinkerers$conditionType;

    @WrapOperation(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/ModelPredicateProviderRegistry;get(Lnet/minecraft/item/ItemStack;Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/item/ModelPredicateProvider;"))
    private ModelPredicateProvider tinkerers$wrapConditionType(ItemStack stack, Identifier id, Operation<ModelPredicateProvider> original) {
        tinkerers$conditionType = id;
        return original.call(stack, id);
    }

    @WrapOperation(method = "apply", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/item/ModelPredicateProvider;call(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/entity/LivingEntity;I)F"))
    private float tinkerers$redirectDynamicValues(ModelPredicateProvider instance, ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, int i, Operation<Float> original) {
        var duck = BowPullingDummyDuck.class.cast(itemStack);
        //noinspection ConstantValue
        assert duck != null;

        if (duck.tinkerers$getPull() == null)
            return instance.call(itemStack, clientWorld, livingEntity, i);

        var customPull = duck.tinkerers$getPull();
        if (tinkerers$conditionType.equals(Identifier.ofVanilla("pull")))
            return customPull;
        if (tinkerers$conditionType.equals(Identifier.ofVanilla("pulling")))
            return customPull > 0.0F ? 1.0F : 0.0F;

        return instance.call(itemStack, clientWorld, livingEntity, i);
    }
}
