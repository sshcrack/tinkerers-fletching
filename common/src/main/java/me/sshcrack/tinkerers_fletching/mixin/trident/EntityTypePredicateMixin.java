package me.sshcrack.tinkerers_fletching.mixin.trident;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.predicate.entity.EntityTypePredicate;
import net.minecraft.registry.entry.RegistryEntryList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityTypePredicate.class)
public abstract class EntityTypePredicateMixin {
    @WrapOperation(method = "matches", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;isIn(Lnet/minecraft/registry/entry/RegistryEntryList;)Z"))
    private boolean tinkerers$wrapMatches(EntityType<?> type, RegistryEntryList<EntityType<?>> types, Operation<Boolean> original) {
        var toTest = type;
        if (toTest == TinkerersEntities.NETHERITE_TRIDENT.get()) {
            toTest = EntityType.TRIDENT;
        }

        return original.call(toTest, types);
    }
}
