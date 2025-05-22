package me.sshcrack.tinkerers_fletching;

import me.sshcrack.tinkerers_fletching.entity.*;
import me.sshcrack.tinkerers_fletching.entity.RocketPearlEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.TieredArrowEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.TntArrowEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.TrackingArrowEntity;
import me.sshcrack.tinkerers_fletching.registries.EntityTypeRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.TridentEntity;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TinkerersEntities {
    public static final Supplier<EntityType<BigSnowballEntity>> BIG_SNOWBALL = register("big_snowball", BigSnowballEntity::new, b -> b.dimensions(0.3F, 0.3F).maxTrackingRange(4).trackingTickInterval(10));
    public static final Supplier<EntityType<HugeSnowballEntity>> HUGE_SNOWBALL = register("huge_snowball", HugeSnowballEntity::new, b -> b.dimensions(0.45F, 0.45F).maxTrackingRange(4).trackingTickInterval(10));

    public static final Supplier<EntityType<IronEggEntity>> IRON_EGG = register("iron_egg", IronEggEntity::new, b -> b.dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10));
    public static final Supplier<EntityType<IcySnowballEntity>> ICY_SNOWBALL = register("icy_snowball", IcySnowballEntity::new, b -> b.dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10));
    public static final Supplier<EntityType<IceSpikeEntity>> ICE_SPIKE = register("ice_spike", IceSpikeEntity::new, b -> b.dimensions(0.15f, 0.15f).maxTrackingRange(4).trackingTickInterval(10));

    public static final Supplier<EntityType<StormChargeEntity>> STORM_CHARGE = register("storm_charge", StormChargeEntity::new, b -> b.dimensions(0.6F * 2.5f, 1.77F * 2.5f).eyeHeight(1.3452F).maxTrackingRange(6).trackingTickInterval(4));


    public static final Supplier<EntityType<RocketPearlEntity>> ROCKET_PEARL = register("rocket_pearl", RocketPearlEntity::new, b -> b.dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));

    public static final Supplier<EntityType<TieredArrowEntity>> TIERED_ARROW = register("tiered_arrow", TieredArrowEntity::new, b -> b.dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
    public static final Supplier<EntityType<TntArrowEntity>> TNT_ARROW = register("tnt_arrow", TntArrowEntity::new, b -> b.dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
    public static final Supplier<EntityType<TrackingArrowEntity>> TRACKING_ARROW = register("tracking_arrow", TrackingArrowEntity::new, b -> b.dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));
    public static final Supplier<EntityType<LeadArrowEntity>> LEAD_ARROW = register("lead_arrow", LeadArrowEntity::new, b -> b.dimensions(0.5F, 0.5F).eyeHeight(0.13F).maxTrackingRange(4).trackingTickInterval(20));

    public static final Supplier<EntityType<NetheriteTridentEntity>> NETHERITE_TRIDENT = register(
            "netherite_trident",
            NetheriteTridentEntity::new, b -> b
                    .dimensions(0.5F, 0.5F)
                    .eyeHeight(0.13F)
                    .maxTrackingRange(4)
                    .trackingTickInterval(20)
    );


    private static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, Consumer<EntityType.Builder<T>> configureBuilder) {
        return EntityTypeRegister.register(name, factory, configureBuilder);
    }


    public static void register() {
        // NO-OP
    }
}
