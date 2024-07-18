package me.sshcrack.tinkerers_fletching.packet;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record DetachLeashC2SPacket(int arrowId) implements CustomPayload {
    private static final Identifier PACKET_ID = Identifier.of(TinkerersMod.MOD_ID, "detach_leash");
    public static final CustomPayload.Id<DetachLeashC2SPacket> PACKET_TYPE = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, DetachLeashC2SPacket> PACKET_CODEC = CustomPayload.codecOf(DetachLeashC2SPacket::write, DetachLeashC2SPacket::new);

    public DetachLeashC2SPacket(RegistryByteBuf buf) {
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
