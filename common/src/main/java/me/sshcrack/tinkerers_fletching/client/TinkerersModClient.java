package me.sshcrack.tinkerers_fletching.client;

import dev.architectury.registry.menu.MenuRegistry;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class TinkerersModClient {
    public static void init() {
        MenuRegistry.registerScreenFactory(TinkerersMod.FLETCHING_SCREEN_HANDLER, FletchingScreen::new);
    }
}
