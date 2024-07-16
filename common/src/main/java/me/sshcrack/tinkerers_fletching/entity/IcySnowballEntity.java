package me.sshcrack.tinkerers_fletching.entity;

import me.sshcrack.tinkerers_fletching.TinkerersItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class IcySnowballEntity extends SnowballEntity {
    public IcySnowballEntity(EntityType<? extends SnowballEntity> entityType, World world) {
        super(entityType, world);
    }

    public IcySnowballEntity(World world, LivingEntity owner) {
        super(world, owner);
    }

    public IcySnowballEntity(World world, double x, double y, double z) {
        super(world, x, y, z);
    }

    @Override
    protected Item getDefaultItem() {
        return TinkerersItems.ICY_SNOWBALL.get();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);

        var world = getWorld();
        if (world.isClient)
            return;

        var pos = getPos();
        for (int i = 0; i < 50; i++) {
            var entity = new IceSpikeEntity(pos.x, pos.y, pos.z, world);
            var multiplier = 1;
            entity.setVelocity((random.nextFloat() - 0.5) * multiplier, (random.nextFloat() - 0.5) * multiplier, (random.nextFloat() - 0.5) * multiplier);
            entity.setDamage(4);
            world.spawnEntity(entity);
        }
    }
}
