package me.sshcrack.tinkerers_fletching.entity.arrows;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.item.projectile.tiered.ArrowTier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TieredArrowEntity extends PersistentProjectileEntity {
    public TieredArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TieredArrowEntity(World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TIERED_ARROW.get(), x, y, z, world, stack, shotFrom);
    }

    public TieredArrowEntity(World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TIERED_ARROW.get(), owner, world, stack, shotFrom);
    }

    public ArrowTier getArrowTier() {
        var component = this.getItemStack().get(TinkerersMod.ARROW_MATERIAL.get());
        if (component == null) return ArrowTier.STONE;

        return ArrowTier.values()[component];
    }


    @Override
    protected ItemStack getDefaultItemStack() {
        return TinkerersItems.TIERED_ARROW.get().getDefaultStack();
    }
}
