package me.sshcrack.tinkerers_fletching.networking;

import dev.architectury.networking.NetworkManager;
import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import me.sshcrack.tinkerers_fletching.packet.DetachLeashC2SPacket;
import me.sshcrack.tinkerers_fletching.packet.RequestLeashAttachmentC2SPacket;
import me.sshcrack.tinkerers_fletching.packet.SetAttachedToS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.OptionalInt;

public class TinkerersC2SNetworking {
    public static void onDetachLeadPacket(DetachLeashC2SPacket packet, NetworkManager.PacketContext context) {
        var world = context.getPlayer().getWorld();
        assert world instanceof ServerWorld : "World is not a server world";

        var sWorld = (ServerWorld) world;

        var entity = world.getEntityById(packet.arrowId());
        if (entity instanceof LeadArrowEntity leadArrow) {
            leadArrow.setRopeAttachedTo(null);
        }

        var players = sWorld.getPlayers();
        NetworkManager.sendToPlayers(players, new SetAttachedToS2CPacket(packet.arrowId(), OptionalInt.empty()));
    }

    public static void register() {
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, DetachLeashC2SPacket.PACKET_TYPE, DetachLeashC2SPacket.PACKET_CODEC, TinkerersC2SNetworking::onDetachLeadPacket);
        NetworkManager.registerReceiver(NetworkManager.Side.C2S, RequestLeashAttachmentC2SPacket.PACKET_TYPE, RequestLeashAttachmentC2SPacket.PACKET_CODEC, TinkerersC2SNetworking::onRequestAttachment);
    }

    private static void onRequestAttachment(RequestLeashAttachmentC2SPacket packet, NetworkManager.PacketContext context) {
        var world = context.getPlayer().getWorld();
        var entity = world.getEntityById(packet.arrowId());
        if (!(entity instanceof LeadArrowEntity leadArrow))
            return;

        if (!(context.getPlayer() instanceof ServerPlayerEntity serverPlayer))
            throw new IllegalStateException("Player is not a server player");

        NetworkManager.sendToPlayer(serverPlayer, new SetAttachedToS2CPacket(leadArrow, leadArrow.getRopeAttachedTo()));
    }
}
