package me.sshcrack.tinkerers_fletching.client.networking;

import dev.architectury.networking.NetworkManager;
import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import me.sshcrack.tinkerers_fletching.packet.SetAttachedToS2CPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class TinkerersS2CNetworking {
    private static void onSetAttachedToPacket(SetAttachedToS2CPacket packet, NetworkManager.PacketContext packetContext) {
        var player = packetContext.getPlayer();
        var world = player.getWorld();

        var arrow = world.getEntityById(packet.arrowId());
        var attached = packet.newAttached();

        var newAttachment = attached.isPresent() ? world.getEntityById(attached.getAsInt()) : null;
        if (newAttachment != null && !(newAttachment instanceof LivingEntity))
            return;

        var living = newAttachment == null ? null : (LivingEntity) newAttachment;
        if (!(arrow instanceof LeadArrowEntity lead))
            return;

        lead.setRopeAttachedTo(living);
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, SetAttachedToS2CPacket.PACKET_TYPE, SetAttachedToS2CPacket.PACKET_CODEC, TinkerersS2CNetworking::onSetAttachedToPacket);
    }
}
