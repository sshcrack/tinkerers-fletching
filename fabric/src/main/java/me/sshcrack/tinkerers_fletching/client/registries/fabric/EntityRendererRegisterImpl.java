package me.sshcrack.tinkerers_fletching.client.registries.fabric;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public class EntityRendererRegisterImpl {
    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererFactory<T> provider) {
        EntityRendererRegistry.register(type.get(), provider);
    }
}
