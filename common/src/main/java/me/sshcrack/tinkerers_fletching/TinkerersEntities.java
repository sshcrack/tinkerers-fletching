package me.sshcrack.tinkerers_fletching;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.entity.BigSnowballEntity;
import me.sshcrack.tinkerers_fletching.entity.HugeSnowballEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class TinkerersEntities {
    public static final RegistrySupplier<EntityType<BigSnowballEntity>> BIG_SNOWBALL = register("big_snowball", BigSnowballEntity::new, b -> b.dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));
    public static final RegistrySupplier<EntityType<HugeSnowballEntity>> HUGE_SNOWBALL = register("huge_snowball", HugeSnowballEntity::new, b -> b.dimensions(0.25F, 0.25F).maxTrackingRange(4).trackingTickInterval(10));


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
