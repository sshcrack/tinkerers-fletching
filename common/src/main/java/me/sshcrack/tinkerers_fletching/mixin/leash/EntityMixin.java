package me.sshcrack.tinkerers_fletching.mixin.leash;

import me.sshcrack.tinkerers_fletching.duck.LeashDataDuck;
import me.sshcrack.tinkerers_fletching.duck.SneakNotifierDuck;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.Leashable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mixin(Entity.class)
public abstract class EntityMixin implements SneakNotifierDuck {
    @Shadow
    public abstract EntityPose getPose();

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void tinkerers$writeLeashDataToNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (!(this instanceof Leashable leashable))
            return;

        var leashData = leashable.getLeashData();
        if (leashData == null)
            return;

        var cast = LeashDataDuck.class.cast(leashData);
        var parent = new NbtCompound();

        parent.putBoolean("NoLeadDrop", cast.tinkerers$hasNoLeadDrop());
        parent.putBoolean("HasPreventedDrop", cast.tinkerers$internal$hasPreventedDrop());

        nbt.put("TinkerersFletchingLeashData", parent);
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void tinkerers$readLeashDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        var parent = nbt.getCompound("TinkerersFletchingLeashData");
        if (parent == null || !(this instanceof Leashable leashable))
            return;

        var data = leashable.getLeashData();
        if (data == null)
            return;

        var cast = LeashDataDuck.class.cast(data);
        cast.tinkerers$setNoLeadDrop(parent.getBoolean("NoLeadDrop"));
        cast.tinkerers$internal$setPreventedDrop(parent.getBoolean("HasPreventedDrop"));
    }

    /* This is just something I can't fix because Leashable interface invokes setLeashData before dropItem :(
    @Inject(method="dropItem(Lnet/minecraft/item/ItemConvertible;)Lnet/minecraft/entity/ItemEntity;", at=@At("HEAD"))
    private void tinkerers$preventLeashDrop(ItemConvertible item, CallbackInfoReturnable<ItemEntity> cir) {
        if (Items.LEAD != item.asItem())
            return original.call(instance, item);

        var leashable = (Leashable) instance;
        var data = leashable.getLeashData();
        if (data == null)
            return original.call(instance, item);

        var cast = LeashDataDuck.class.cast(data);
        if (!cast.tinkerers$hasNoLeadDrop() || cast.tinkerers$internal$hasPreventedDrop())
            return original.call(instance, item);

        cast.tinkerers$internal$setPreventedDrop(true);
        return null;
    }
     */


    @Unique
    @Nullable
    private List<SneakListener> tinkerers$sneakListeners;

    @Override
    public void tinkerers$addSneakListener(SneakListener listener) {
        if (tinkerers$sneakListeners == null)
            tinkerers$sneakListeners = new ArrayList<>();
        tinkerers$sneakListeners.add(listener);
    }

    @Override
    public void tinkerrers$removeSneakListener(SneakListener listener) {
        if (tinkerers$sneakListeners == null)
            tinkerers$sneakListeners = new ArrayList<>();
        tinkerers$sneakListeners.remove(listener);
    }

    @Override
    public void tinkerers$notifyListeners(boolean isSneaking) {
        if (tinkerers$sneakListeners == null)
            return;

        System.out.println("Notifying listeners");
        var entity = Entity.class.cast(this);
        for (var listener : tinkerers$sneakListeners) {
            var res = listener.onSneakChange(entity, isSneaking);
            if (res == ActionResult.CONSUME)
                break;
        }
    }
}
