package me.sshcrack.tinkerers_fletching.client;

import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TinkerersEntitiesClient {
    public static void register() {
        EntityRendererRegistry.register(TinkerersEntities.IRON_EGG, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(TinkerersEntities.BIG_SNOWBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(TinkerersEntities.HUGE_SNOWBALL, FlyingItemEntityRenderer::new);
    }
}
