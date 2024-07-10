package me.sshcrack.tinkerers_fletching.neoforge;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.TinkerersModClient;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@SuppressWarnings("unused")
@Mod(TinkerersMod.MOD_ID)
public final class TinkerersModForge {
    public TinkerersModForge() {
        // Run our common setup.
        TinkerersMod.init();
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        TinkerersModClient.init();
    }
}
