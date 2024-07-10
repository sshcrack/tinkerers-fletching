package me.sshcrack.tinkerers_fletching.neoforge;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.TinkerersModClient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@SuppressWarnings("unused")
@Mod(TinkerersMod.MOD_ID)
public final class TinkerersModForge {
    public TinkerersModForge(IEventBus eventBus) {
        // Run our common setup.

        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::onCommonSetup);
        //NeoForge.EVENT_BUS.register(this);
        TinkerersMod.init();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        System.out.println("Hello from client setup");
        TinkerersModClient.init();
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        System.out.println("Hello from common setup");
        TinkerersMod.setup();
    }
}
