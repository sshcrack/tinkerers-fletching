package me.sshcrack.tinkerers_fletching.client.registries.neoforge;

import dev.architectury.registry.menu.MenuRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.text.Text;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

public class ScreenRegisterImpl {
    public static Queue<Consumer<RegisterMenuScreensEvent>> screenFactories = new LinkedList<>();

    public static <H extends ScreenHandler, S extends Screen & ScreenHandlerProvider<H>> void registerScreenFactory(ScreenHandlerType<H> type, MenuRegistry.ScreenFactory<H, S> factory) {
        screenFactories.add(event -> {
            event.register(type, factory::create);
        });
    }
}
