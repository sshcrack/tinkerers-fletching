package me.sshcrack.tinkerers_fletching;

import net.minecraft.registry.RegistryKeys;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.Queue;

public class TinkerersStats {
    public static Queue<Runnable> addStats = new LinkedList<>();
    public static final Identifier INTERACT_WITH_FLETCHING_TABLE = registerStat("interact_with_fletching_table", StatFormatter.DEFAULT);

    @SuppressWarnings("SameParameterValue")
    private static Identifier registerStat(String idStr, StatFormatter formatter) {
        var id = Identifier.of(TinkerersMod.MOD_ID, idStr);
        var res = TinkerersMod.register(RegistryKeys.CUSTOM_STAT, id, () -> id);

        addStats.add(() -> Stats.CUSTOM.getOrCreateStat(res.get(), formatter));

        return id;
    }

    public static void setupEvent() {
        addStats.forEach(Runnable::run);
        addStats.clear();
    }

    public static void register() {
        // Needed for the class to load
    }

}
