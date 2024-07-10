package me.sshcrack.tinkerers_fletching.client.screen_register;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.menu.MenuRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

@Environment(EnvType.CLIENT)
public class ScreenRegister {
    @ExpectPlatform
    public static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void registerScreenFactory(ScreenHandlerType<H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        throw new AssertionError();
    }
}
