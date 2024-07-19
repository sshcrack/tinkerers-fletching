package me.sshcrack.tinkerers_fletching.fabric.server;

import me.sshcrack.tinkerers_fletching.server.TinkerersModDedicatedServer;
import net.fabricmc.api.DedicatedServerModInitializer;

public class TinkerersModFabricServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        TinkerersModDedicatedServer.init();
    }
}
