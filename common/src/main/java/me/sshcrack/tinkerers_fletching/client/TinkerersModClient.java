package me.sshcrack.tinkerers_fletching.client;

import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.networking.TinkerersC2SNetworking;
import me.sshcrack.tinkerers_fletching.networking.TinkerersS2CNetworking;
import me.sshcrack.tinkerers_fletching.client.registries.ScreenRegister;
import me.sshcrack.tinkerers_fletching.duck.SneakNotifierDuck;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

@Environment(EnvType.CLIENT)
public class TinkerersModClient {
    public static final KeyBinding DETACH_ROPE = new KeyBinding(
            "key.tinkerers_fletching.detach_rope",
            InputUtil.Type.KEYSYM,
            InputUtil.UNKNOWN_KEY.getCode(),
            "category.tinkerers_fletching.general"
    );

    public static void init() {
        TinkerersMod.LOGGER.info("Initializing client features");

        TinkerersC2SNetworking.register();
        TinkerersEntitiesClient.register();
        KeyMappingRegistry.register(DETACH_ROPE);

        ClientTickEvent.CLIENT_POST.register(client -> {
            var duck = (SneakNotifierDuck) client.player;
            var player = MinecraftClient.getInstance().player;
            if (player == null)
                return;

            var detachPressed = false;
            while (DETACH_ROPE.wasPressed()) {
                detachPressed = true;
            }

            if ((DETACH_ROPE.isUnbound() ? player.input.sneaking : detachPressed)) {
                if (duck != null)
                    duck.tinkerers$notifyListeners(true);
            }
        });

        TinkerersModelPredicate.register();
    }

    public static void registerScreens() {
        ScreenRegister.registerScreenFactory(TinkerersMod.FLETCHING_SCREEN_HANDLER.get(), FletchingScreen::new);
    }
}
