package me.sshcrack.tinkerers_fletching.packet;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.OptionalInt;

public record SetAttachedToS2CPacket(int arrowId, OptionalInt newAttached) implements CustomPayload {
    private static final Identifier PACKET_ID = Identifier.of(TinkerersMod.MOD_ID, "set_attached");
    public static final CustomPayload.Id<SetAttachedToS2CPacket> PACKET_TYPE = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, SetAttachedToS2CPacket> PACKET_CODEC = CustomPayload.codecOf(SetAttachedToS2CPacket::write, SetAttachedToS2CPacket::new);

    public SetAttachedToS2CPacket(LeadArrowEntity entity, LivingEntity attached) {
        this(entity.getId(), attached == null ? OptionalInt.empty() : OptionalInt.of(attached.getId()));
    }

    public SetAttachedToS2CPacket(RegistryByteBuf buf) {
        this(buf.readInt(), buf.readBoolean() ? OptionalInt.empty() : OptionalInt.of(buf.readInt()));
    }

    private void write(RegistryByteBuf buf) {
        buf.writeInt(this.arrowId);

        var present = newAttached.isPresent();
        buf.writeBoolean(present);
        if (present)
            buf.writeInt(this.newAttached.getAsInt());
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_TYPE;
    }
}
