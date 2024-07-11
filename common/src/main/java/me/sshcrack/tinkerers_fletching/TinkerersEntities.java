package me.sshcrack.tinkerers_fletching;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class TinkerersEntities {
    public static final RegistrySupplier<EntityType<BigSnowballEntity>> BIG_SNOWBALL = register("big_snowball", BigSnowballEntity::new, b -> b.dimensions(0.3F, 0.3F).maxTrackingRange(4).trackingTickInterval(10));
    public static final RegistrySupplier<EntityType<HugeSnowballEntity>> HUGE_SNOWBALL = register("huge_snowball", HugeSnowballEntity::new, b -> b.dimensions(0.45F, 0.45F).maxTrackingRange(4).trackingTickInterval(10));

    public static final RegistrySupplier<EntityType<IronEggEntity>> IRON_EGG = register("iron_egg", IronEggEntity::new, b -> b.dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10));
    public static final RegistrySupplier<EntityType<IcySnowballEntity>> ICY_SNOWBALL = register("icy_snowball", IcySnowballEntity::new, b -> b.dimensions(0.25f, 0.25f).maxTrackingRange(4).trackingTickInterval(10));
    public static final RegistrySupplier<EntityType<IceSpikeEntity>> ICE_SPIKE = register("ice_spike", IceSpikeEntity::new, b -> b.dimensions(0.15f, 0.15f).maxTrackingRange(4).trackingTickInterval(10));

    public static final RegistrySupplier<EntityType<StormChargeEntity>> STORM_CHARGE = register("storm_charge", StormChargeEntity::new, b -> b.dimensions(0.3125F, 0.3125F).eyeHeight(0.0F).maxTrackingRange(4).trackingTickInterval(10));


    private static <T extends Entity> RegistrySupplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, Consumer<EntityType.Builder<T>> configureBuilder) {
        var builder = EntityType.Builder.create(factory, SpawnGroup.MISC);
        configureBuilder.accept(builder);


        var id = Identifier.of(TinkerersMod.MOD_ID, name);
        return TinkerersMod.register(RegistryKeys.ENTITY_TYPE, id, builder.build(id.toString()));
    }


    public static void register() {
        // NO-OP
    }
}
