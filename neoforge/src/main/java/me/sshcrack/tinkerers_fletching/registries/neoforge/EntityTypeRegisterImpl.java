package me.sshcrack.tinkerers_fletching.registries.neoforge;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.neoforge.TinkerersModForge;
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
        return TinkerersModForge.ENTITY_TYPE.register(name, () -> {
            var builder = EntityType.Builder.create(factory, SpawnGroup.MISC);
            builderFactory.accept(builder);

            var id = Identifier.of(TinkerersMod.MOD_ID, name);

            return builder.build(id.toString());
        });
    }
}
