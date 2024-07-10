package me.sshcrack.tinkerers_fletching.client;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.screen_register.ScreenRegister;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class TinkerersModClient {
    public static void init() {
        TinkerersMod.LOGGER.info("Initializing client features");

        ScreenRegister.registerScreenFactory(TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), FletchingScreen::new);
    }
}
