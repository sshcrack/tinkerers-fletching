package me.sshcrack.tinkerers_fletching;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.item.projectile.*;
import me.sshcrack.tinkerers_fletching.item.projectile.tiered.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.function.Supplier;

public class TinkerersItems {
    public final static RegistrySupplier<StormChargeItem> STORM_CHARGE = register("storm_charge", StormChargeItem::new);

    public final static RegistrySupplier<BigSnowballItem> BIG_SNOWBALL = register("big_snowball", BigSnowballItem::new);
    public final static RegistrySupplier<HugeSnowballItem> HUGE_SNOWBALL = register("huge_snowball", HugeSnowballItem::new);
    public final static RegistrySupplier<IcySnowballItem> ICY_SNOWBALL = register("icy_snowball", IcySnowballItem::new);

    public final static RegistrySupplier<IronEggItem> IRON_EGG = register("iron_egg", IronEggItem::new);
    public final static RegistrySupplier<RocketPearlItem> ROCKET_PEARL = register("rocket_pearl", RocketPearlItem::new);

    public final static HashMap<ArrowTier, RegistrySupplier<TieredArrowItem>> TIERED_ARROW = new HashMap<>();
    public final static RegistrySupplier<TntArrowItem> TNT_ARROW = register("tnt_arrow", TntArrowItem::new);

    private static <T extends Item> RegistrySupplier<T> register(String name, Supplier<T> item) {
        return TinkerersMod.register(RegistryKeys.ITEM, Identifier.of(TinkerersMod.MOD_ID, name), item);
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void register() {
        CreativeTabRegistry.modifyBuiltin(Registries.ITEM_GROUP.getOrThrow(ItemGroups.COMBAT), (flags, output, canUseGameMasterBlocks) -> {
            output.acceptAfter(Items.SNOWBALL, BIG_SNOWBALL.get());
            output.acceptAfter(Items.SNOWBALL, HUGE_SNOWBALL.get());
            output.acceptAfter(Items.SNOWBALL, ICY_SNOWBALL.get());

            output.acceptAfter(Items.EGG, IRON_EGG.get());
            output.acceptAfter(Items.WIND_CHARGE, STORM_CHARGE.get());

            output.acceptAfter(Items.ARROW, TNT_ARROW.get());
            var items = TIERED_ARROW.values().stream()
                    .sorted(Comparator.comparingInt(o -> -o.getType().ordinal()))
                    .map(e -> e.get().getDefaultStack())
                    .toList();
            output.acceptAllAfter(Items.ARROW, items);
        });

        CreativeTabRegistry.modifyBuiltin(Registries.ITEM_GROUP.getOrThrow(ItemGroups.TOOLS), (flags, output, canUseGameMasterBlocks) -> {
            output.acceptAfter(Items.ENDER_PEARL, ROCKET_PEARL.get());
        });
    }

    static {
        for (ArrowTier tier : ArrowTier.values()) {
            TIERED_ARROW.put(tier, register(tier.name().toLowerCase() + "_arrow", () -> new TieredArrowItem(tier)));
        }
    }
}
