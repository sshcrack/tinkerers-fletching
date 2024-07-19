package me.sshcrack.tinkerers_fletching.client;

import me.sshcrack.tinkerers_fletching.TinkerersStatuses;
import me.sshcrack.tinkerers_fletching.client.sound.LeadSoundInstance;
import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class TinkerersEntityStatusHandler {
    public static ActionResult handleStatus(Entity entity, byte status) {
        if (!(entity instanceof LeadArrowEntity arrow))
            return ActionResult.PASS;

        if (status == TinkerersStatuses.SEND_ATTACHED_NOTICE) {
            var client = MinecraftClient.getInstance();
            var key = client.options.sneakKey;
            if (!TinkerersModClient.DETACH_ROPE.isUnbound())
                key = TinkerersModClient.DETACH_ROPE;

            Text text = Text.translatable("mount.onboard", key.getBoundKeyLocalizedText());

            client.inGameHud.setOverlayMessage(text, false);
            client.getNarratorManager().narrate(text);
            return ActionResult.CONSUME;
        }

        if (status == TinkerersStatuses.PLAY_SOUND) {
            var client = MinecraftClient.getInstance();

            client.getSoundManager().play(new LeadSoundInstance(client.player, arrow));
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
}
