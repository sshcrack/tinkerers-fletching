package me.sshcrack.tinkerers_fletching.registries.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityTypeRegisterImpl {
    public static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, Consumer<EntityType.Builder<T>> builderFactory) {
        var builder = EntityType.Builder.create(factory, SpawnGroup.MISC);
        builderFactory.accept(builder);

        var id = Identifier.of(TinkerersMod.MOD_ID, name);

        var entityType = builder.build(id.toString());
        Registry.register(Registries.ENTITY_TYPE, id, entityType);

        return () -> entityType;
    }
}
