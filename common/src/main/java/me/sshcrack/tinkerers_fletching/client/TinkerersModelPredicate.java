package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.mixin.client.ModelPredicateProviderRegistryInvoker;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class TinkerersModelPredicate {
    private static final List<Item> registerBowQueue = new ArrayList<>();
    private static final List<Item> registerTridentQueue = new ArrayList<>();

    public static void register() {
        for (Item item : registerBowQueue) {
            ModelPredicateProviderRegistryInvoker.register(
                    item,
                    Identifier.ofVanilla("pulling"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );

            ModelPredicateProviderRegistryInvoker.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
                if (entity == null) {
                    return 0.0F;
                } else {
                    return entity.getActiveItem() != stack ? 0.0F : (float) (stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
                }
            });
        }

        for (Item item : registerTridentQueue) {
            ModelPredicateProviderRegistryInvoker.register(
                    item,
                    Identifier.ofVanilla("throwing"),
                    (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
            );
        }
    }

    public static void registerBow(Item item) {
        registerBowQueue.add(item);
    }
    public static void registerTrident(Item item) {
        registerTridentQueue.add(item);
    }
}
