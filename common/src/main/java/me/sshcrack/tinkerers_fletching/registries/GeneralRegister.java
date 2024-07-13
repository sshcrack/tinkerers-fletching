package me.sshcrack.tinkerers_fletching.registries;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

public class GeneralRegister {
    public static <T, E extends T> Supplier<E> register(Registry<T> registry, Identifier name, E object) {
        return registerSup(registry, name, () -> object);
    }


    @ExpectPlatform
    public static <T, E extends T> Supplier<E> registerSup(Registry<T> registry, Identifier name, Supplier<E> object) {
        throw new AssertionError();
    }
}
