package me.sshcrack.tinkerers_fletching.client.registries.neoforge;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

import java.util.LinkedList;
import java.util.Queue;

public class ScreenRegisterImpl {
    public static Queue<ScreenProviderInfo<?, ?>> screenFactories = new LinkedList<>();

    public static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void registerScreenFactory(ScreenHandlerType<H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        screenFactories.add(new ScreenProviderInfo<>(type, factory));
    }
}
