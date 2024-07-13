package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.registries.ScreenRegister;
import me.sshcrack.tinkerers_fletching.registries.GeneralRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.Registries;

@Environment(EnvType.CLIENT)
public class TinkerersModClient {
    public static void init() {
        TinkerersMod.LOGGER.info("Initializing client features");

        ScreenRegister.registerScreenFactory(TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), FletchingScreen::new);
        TinkerersEntitiesClient.register();
    }
}
