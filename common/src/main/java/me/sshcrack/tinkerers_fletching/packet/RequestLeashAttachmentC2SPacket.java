package me.sshcrack.tinkerers_fletching.packet;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record RequestLeashAttachmentC2SPacket(int arrowId) implements CustomPayload {
    private static final Identifier PACKET_ID = Identifier.of(TinkerersMod.MOD_ID, "request_attachment");
    public static final Id<RequestLeashAttachmentC2SPacket> PACKET_TYPE = new Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, RequestLeashAttachmentC2SPacket> PACKET_CODEC = CustomPayload.codecOf(RequestLeashAttachmentC2SPacket::write, RequestLeashAttachmentC2SPacket::new);

    public RequestLeashAttachmentC2SPacket(RegistryByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_TYPE;
    }

    private void write(RegistryByteBuf buf) {
        buf.writeInt(this.arrowId);
    }
}
