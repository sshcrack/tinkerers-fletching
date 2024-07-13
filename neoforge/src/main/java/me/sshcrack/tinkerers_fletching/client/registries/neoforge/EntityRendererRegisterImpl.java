package me.sshcrack.tinkerers_fletching.client.registries.neoforge;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.neoforged.neoforge.client.NeoForgeRenderTypes;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class EntityRendererRegisterImpl {
    public static <T extends Entity> void register(Supplier<? extends EntityType<? extends T>> type, EntityRendererFactory<T> provider) {
        EntityRenderers.register(type.get(), provider);
    }
}
