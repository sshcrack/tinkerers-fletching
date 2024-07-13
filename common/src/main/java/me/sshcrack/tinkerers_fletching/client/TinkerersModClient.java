package me.sshcrack.tinkerers_fletching.client;

import dev.architectury.registry.menu.MenuRegistry;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.registries.ScreenRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TinkerersModClient {
    public static void init() {
        TinkerersMod.LOGGER.info("Initializing client features");

        TinkerersEntitiesClient.register();
    }

    public static void registerScreens() {
        ScreenRegister.registerScreenFactory(TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), FletchingScreen::new);
    }
}
