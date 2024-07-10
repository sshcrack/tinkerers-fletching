package me.sshcrack.tinkerers_fletching;

import dev.architectury.registry.registries.RegistrySupplier;
import me.sshcrack.tinkerers_fletching.item.projectile.BigSnowballItem;
import me.sshcrack.tinkerers_fletching.item.projectile.HugeSnowballItem;
import me.sshcrack.tinkerers_fletching.item.projectile.StormChargeItem;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class TinkerersItems {
    public static RegistrySupplier<StormChargeItem> STORM_CHARGE = register("storm_charge", new StormChargeItem());

    public static RegistrySupplier<BigSnowballItem> BIG_SNOWBALL = register("big_snowball", new BigSnowballItem());
    public static RegistrySupplier<HugeSnowballItem> HUGE_SNOWBALL = register("huge_snowball", new HugeSnowballItem());

    private static <T extends Item> RegistrySupplier<T> register(String name, T item) {
        return TinkerersMod.register(RegistryKeys.ITEM, Identifier.of(TinkerersMod.MOD_ID, name), item);
    }

    public static void register() {
        // NO-OP
    }
}
