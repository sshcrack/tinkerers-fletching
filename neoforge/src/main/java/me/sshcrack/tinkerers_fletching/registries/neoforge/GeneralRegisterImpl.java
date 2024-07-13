package me.sshcrack.tinkerers_fletching.registries.neoforge;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Supplier;

public class GeneralRegisterImpl {
    @Nullable
    private static IEventBus bus;
    private static final HashMap<RegistryKey<?>, DeferredRegister<?>> registryMap = new HashMap<>();


    public static <T, E extends T> Supplier<E> registerSup(Registry<T> registry, Identifier name, Supplier<E> object) {
        //noinspection unchecked
        var register = (DeferredRegister<T>) registryMap.computeIfAbsent(registry.getKey(), k -> {
            var temp = DeferredRegister.create(registry, TinkerersMod.MOD_ID);
            if (bus != null)
                temp.register(bus);
            return temp;
        });

        System.out.printf("Registry %s: registering %s %n", registry.getKey().toString(), name.toString());
        return register.register(name.getPath(), object);
    }

    public static void initialize(IEventBus bus) {
        registryMap.forEach((key, registry) -> registry.register(bus));
        GeneralRegisterImpl.bus = bus;
    }
}
