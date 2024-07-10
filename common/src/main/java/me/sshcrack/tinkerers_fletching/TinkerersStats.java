package me.sshcrack.tinkerers_fletching;

import com.google.common.base.Supplier;
import dev.architectury.platform.Platform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

public class TinkerersStats {
    public static Queue<Runnable> addStats = new LinkedList<>();
    public static final Identifier INTERACT_WITH_FLETCHING_TABLE = registerStat("interact_with_fletching_table", StatFormatter.DEFAULT);

    @SuppressWarnings("SameParameterValue")
    private static Identifier registerStat(String id, StatFormatter formatter) {
        AtomicReference<Identifier> ref = new AtomicReference<>();


        Supplier<Identifier> res = null;
        if (Platform.isFabric()) {
            res = () -> Registry.register(Registries.CUSTOM_STAT, id, Identifier.of(TinkerersMod.MOD_ID, id));
        } else {
            var tmp = TinkerersMod.register(RegistryKeys.CUSTOM_STAT, Identifier.of(id), (Supplier<Identifier>) ref::get);
            res = tmp::getId;
        }

        ref.set(res.get());
        Supplier<Identifier> finalRes = res;
        addStats.add(() -> Stats.CUSTOM.getOrCreateStat(finalRes.get(), formatter));

        return ref.get();
    }

    public static void setupEvent() {
        addStats.forEach(Runnable::run);
        addStats.clear();
    }

    public static void register() {
        // Needed for the class to load
    }

}
