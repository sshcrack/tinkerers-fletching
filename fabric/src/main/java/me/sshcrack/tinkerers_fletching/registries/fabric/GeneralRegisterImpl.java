package me.sshcrack.tinkerers_fletching.registries.fabric;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class GeneralRegisterImpl {
    public static <T, E extends T> Supplier<E> registerSup(Registry<T> registry, Identifier name, Supplier<E> object) {
        var res = Registry.register(registry, name, object.get());

        return () -> res;
    }
}
