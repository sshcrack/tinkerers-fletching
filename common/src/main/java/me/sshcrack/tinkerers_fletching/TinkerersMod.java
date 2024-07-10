package me.sshcrack.tinkerers_fletching;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TinkerersMod {
    public static final String MOD_ID = "tinkerers_fletching";

    private static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final ScreenHandlerType<FletchingScreenHandler> FLETCHING_SCREEN_HANDLER = registerScreenHandler("fletching", FletchingScreenHandler::new);
    public static final Identifier INTERACT_WITH_FLETCHING_TABLE = registerStat("interact_with_fletching_table", StatFormatter.DEFAULT);


    public static final int BASE_WORLD_EVENT = 4170000;
    public static final int FLETCHING_USED_WORLD_EVENT = BASE_WORLD_EVENT + 1;

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        Recipes.register();
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(MOD_ID, id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }


    private static Identifier registerStat(String id, StatFormatter formatter) {
        Identifier identifier = Identifier.of(MOD_ID, id);

        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static <T> Registrar<T> getRegistry(RegistryKey<Registry<T>> key) {
        return MANAGER.get().get(key);
    }


    public static <T, E extends T> void register(RegistryKey<Registry<T>> key, Identifier id, Supplier<E> value) {
        var reg = getRegistry(key);
        reg.register(id, value);
    }

    public static <T, E extends T> void register(RegistryKey<Registry<T>> key, Identifier id, E value) {
        register(key, id, (Supplier<E>) () -> value);
    }
}
