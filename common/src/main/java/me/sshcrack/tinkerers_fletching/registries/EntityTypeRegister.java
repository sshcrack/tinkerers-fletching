package me.sshcrack.tinkerers_fletching.registries;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityTypeRegister {
    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, Consumer<EntityType.Builder<T>> builderFactory) {
        throw new AssertionError();
    }
}
