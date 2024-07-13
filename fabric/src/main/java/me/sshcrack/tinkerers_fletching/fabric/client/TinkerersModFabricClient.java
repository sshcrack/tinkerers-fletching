package me.sshcrack.tinkerers_fletching.fabric.client;

import me.sshcrack.tinkerers_fletching.client.TinkerersModClient;
import net.fabricmc.api.ClientModInitializer;

public final class TinkerersModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        TinkerersModClient.init();
        TinkerersModClient.registerScreens();
    }
}
