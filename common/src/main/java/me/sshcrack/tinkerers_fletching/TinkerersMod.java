package me.sshcrack.tinkerers_fletching;

import com.google.common.base.Suppliers;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.client.FletchingScreen;
import me.sshcrack.tinkerers_fletching.client.registries.ScreenRegister;
import me.sshcrack.tinkerers_fletching.item.projectile.tiered.ArrowTier;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.Codecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public final class TinkerersMod {
    public static final String MOD_ID = "tinkerers_fletching";

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final RegistrySupplier<ScreenHandlerType<FletchingScreenHandler>> FLETCHING_SCREEN_HANDLER = registerScreenHandler("fletching", FletchingScreenHandler::new);


    public static final int BASE_WORLD_EVENT = 4170000;
    public static final int FLETCHING_USED_WORLD_EVENT = BASE_WORLD_EVENT + 1;

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        TinkerersMod.LOGGER.info("Initializing common...");
        Recipes.register();
        TinkerersStats.register();
        TinkerersItems.register();
        TinkerersEntities.register();
    }

    public static void setup() {
        TinkerersMod.LOGGER.info("Running setup...");
        TinkerersStats.setupEvent();
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends ScreenHandler> RegistrySupplier<ScreenHandlerType<T>> registerScreenHandler(String id, ScreenHandlerType.Factory<T> factory) {
        System.out.println("Registering screen handler");
        return register(RegistryKeys.SCREEN_HANDLER, Identifier.of(MOD_ID, id), () -> MenuRegistry.ofExtended((i, inventory, buf) -> factory.create(i, inventory)));
    }


    public static <T> Registrar<T> getRegistry(RegistryKey<Registry<T>> key) {
        return MANAGER.get().get(key);
    }

    public static <T, E extends T> RegistrySupplier<E> register(RegistryKey<Registry<T>> key, String id, Supplier<E> value) {
        return register(key, Identifier.of(MOD_ID, id), value);
    }

    public static <T, E extends T> RegistrySupplier<E> register(RegistryKey<Registry<T>> key, Identifier id, Supplier<E> value) {
        var reg = getRegistry(key);
        return reg.register(id, value);
    }
}
