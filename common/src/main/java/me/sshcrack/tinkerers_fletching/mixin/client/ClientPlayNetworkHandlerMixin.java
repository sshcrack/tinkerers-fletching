package me.sshcrack.tinkerers_fletching.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.client.TinkerersEntityStatusHandler;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @WrapOperation(method = "onEntityStatus", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;handleStatus(B)V"))
    private void tinkerers$handleCustomStatuses(Entity instance, byte status, Operation<Void> original) {
        var res = TinkerersEntityStatusHandler.handleStatus(instance, status);

        if (res != ActionResult.CONSUME)
            original.call(instance, status);
    }
}
