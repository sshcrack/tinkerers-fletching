package me.sshcrack.tinkerers_fletching.client.registries;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public class EntityRendererRegister {
    @ExpectPlatform
    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererFactory<T> provider) {
        throw new AssertionError();
    }
}
