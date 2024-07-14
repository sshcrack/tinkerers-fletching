package me.sshcrack.tinkerers_fletching.entity.arrows;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.TinkerersItems;
import me.sshcrack.tinkerers_fletching.item.projectile.tiered.ArrowTier;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TieredArrowEntity extends PersistentProjectileEntity {
    private static final TrackedData<Integer> ARROW_TIER = DataTracker.registerData(TieredArrowEntity.class, TrackedDataHandlerRegistry.INTEGER);
    @Nullable
    private ArrowTier cachedArrowTier;

    public TieredArrowEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public TieredArrowEntity(ArrowTier arrowTier, World world, double x, double y, double z, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TIERED_ARROW.get(), x, y, z, world, stack, shotFrom);
        this.setArrowTier(arrowTier);
    }

    public TieredArrowEntity(ArrowTier arrowTier, World world, LivingEntity owner, ItemStack stack, @Nullable ItemStack shotFrom) {
        super(TinkerersEntities.TIERED_ARROW.get(), owner, world, stack, shotFrom);
        this.setArrowTier(arrowTier);
    }

    @Nullable
    public ArrowTier getDataArrowTier() {
        var index = this.dataTracker.get(ARROW_TIER);
        var values = ArrowTier.values();
        if (index == -1 || index >= values.length)
            return null;

        return values[index];
    }

    @Nullable
    public ArrowTier getCachedArrowTier() {
        if (cachedArrowTier == null)
            cachedArrowTier = getDataArrowTier();

        return cachedArrowTier;
    }

    @Override
    protected double getGravity() {
        if (cachedArrowTier == null)
            return super.getGravity();

        return super.getGravity() * cachedArrowTier.getGravityMultiplier();
    }

    public void setArrowTier(@NotNull ArrowTier arrowTier) {
        this.dataTracker.set(ARROW_TIER, arrowTier.ordinal());
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (ARROW_TIER.equals(data)) {
            this.cachedArrowTier = getDataArrowTier();
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(ARROW_TIER, -1);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        var key = cachedArrowTier;
        if (key == null)
            key = ArrowTier.STONE;

        return TinkerersItems.TIERED_ARROW.get(key).get().getDefaultStack();
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        dataTracker.set(ARROW_TIER, nbt.getInt("ArrowTier"));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        var t = getDataArrowTier();
        if (t != null)
            nbt.putInt("ArrowTier", t.ordinal());
    }
}
