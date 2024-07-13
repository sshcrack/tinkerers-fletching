package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.TinkerersEntities;
import me.sshcrack.tinkerers_fletching.client.registries.EntityRendererRegister;
import me.sshcrack.tinkerers_fletching.client.renderer.IceSpikeEntityRenderer;
import me.sshcrack.tinkerers_fletching.client.renderer.StormChargeEntityRenderer;
import me.sshcrack.tinkerers_fletching.client.renderer.TippedEntityRenderer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class TinkerersEntitiesClient {
    public static void register() {
        EntityRendererRegister.register(TinkerersEntities.IRON_EGG, FlyingItemEntityRenderer::new);
        EntityRendererRegister.register(TinkerersEntities.BIG_SNOWBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegister.register(TinkerersEntities.HUGE_SNOWBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegister.register(TinkerersEntities.ICY_SNOWBALL, FlyingItemEntityRenderer::new);
        EntityRendererRegister.register(TinkerersEntities.ROCKET_PEARL, FlyingItemEntityRenderer::new);

        EntityRendererRegister.register(TinkerersEntities.ICE_SPIKE, IceSpikeEntityRenderer::new);
        EntityRendererRegister.register(TinkerersEntities.STORM_CHARGE, StormChargeEntityRenderer::new);

        EntityRendererRegister.register(TinkerersEntities.TIERED_ARROW, TippedEntityRenderer::new);
    }
}
