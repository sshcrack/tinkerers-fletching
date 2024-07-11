package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class IceSpikeEntity extends PersistentProjectileEntity {

    public IceSpikeEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public IceSpikeEntity(double x, double y, double z, World world) {
        super(TinkerersEntities.ICE_SPIKE.get(), x, y, z, world, TinkerersItems.ICY_SNOWBALL.get().getDefaultStack(), null);

    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return false;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        // Using Icy Snowball for now, doesn't really matter
        return TinkerersItems.ICY_SNOWBALL.get().getDefaultStack();
    }
}
