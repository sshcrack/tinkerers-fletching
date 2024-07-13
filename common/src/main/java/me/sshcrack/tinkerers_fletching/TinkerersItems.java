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

import java.util.function.Supplier;

public class TinkerersItems {
    public static RegistrySupplier<StormChargeItem> STORM_CHARGE = register("storm_charge", StormChargeItem::new);

    public static RegistrySupplier<BigSnowballItem> BIG_SNOWBALL = register("big_snowball", BigSnowballItem::new);
    public static RegistrySupplier<HugeSnowballItem> HUGE_SNOWBALL = register("huge_snowball", HugeSnowballItem::new);
    public static RegistrySupplier<IcySnowballItem> ICY_SNOWBALL = register("icy_snowball", IcySnowballItem::new);

    public static RegistrySupplier<IronEggItem> IRON_EGG = register("iron_egg", IronEggItem::new);
    public static RegistrySupplier<RocketPearlItem> ROCKET_PEARL = register("rocket_pearl", RocketPearlItem::new);

    public static RegistrySupplier<TieredArrowItem> STONE_ARROW = register("stone_arrow", StoneArrowItem::new);
    public static RegistrySupplier<TieredArrowItem> IRON_ARROW = register("iron_arrow", IronArrowItem::new);
    public static RegistrySupplier<TieredArrowItem> DIAMOND_ARROW = register("diamond_arrow", DiamondArrowItem::new);
    public static RegistrySupplier<TieredArrowItem> NETHERITE_ARROW = register("netherite_arrow", NetheriteArrowItem::new);

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
        });

        CreativeTabRegistry.modifyBuiltin(Registries.ITEM_GROUP.getOrThrow(ItemGroups.TOOLS), (flags, output, canUseGameMasterBlocks) -> {
            output.acceptAfter(Items.ENDER_PEARL, ROCKET_PEARL.get());
        });
    }
}
