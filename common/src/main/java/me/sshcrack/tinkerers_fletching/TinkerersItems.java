package me.sshcrack.tinkerers_fletching;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.item.projectile.*;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class TinkerersItems {
    public static RegistrySupplier<StormChargeItem> STORM_CHARGE = register("storm_charge", new StormChargeItem());

    public static RegistrySupplier<BigSnowballItem> BIG_SNOWBALL = register("big_snowball", new BigSnowballItem());
    public static RegistrySupplier<HugeSnowballItem> HUGE_SNOWBALL = register("huge_snowball", new HugeSnowballItem());
    public static RegistrySupplier<IceSpikeItem> ICE_SPIKE = register("ice_spike", new IceSpikeItem());

    public static RegistrySupplier<IronEggItem> IRON_EGG = register("iron_egg", new IronEggItem());

    private static <T extends Item> RegistrySupplier<T> register(String name, T item) {
        return TinkerersMod.register(RegistryKeys.ITEM, Identifier.of(TinkerersMod.MOD_ID, name), item);
    }

    public static void register() {
        // NO-OP
    }
}
