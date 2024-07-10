package me.sshcrack.tinkerers_fletching.client.screen_register.neoforge;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public record ScreenProviderInfo<H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>>(
        ScreenHandlerType<H> type, MenuRegistry.ScreenFactory<H, S> factory) {
}
