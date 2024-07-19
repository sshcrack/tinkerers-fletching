package me.sshcrack.tinkerers_fletching.client.sound;

import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class LeadSoundInstance extends MovingSoundInstance {
    private final ClientPlayerEntity player;
    private final LeadArrowEntity arrow;
    private int tickCount;

    public LeadSoundInstance(ClientPlayerEntity player, LeadArrowEntity arrow) {
        super(SoundEvents.ITEM_ELYTRA_FLYING, SoundCategory.PLAYERS, SoundInstance.createRandom());
        this.player = player;
        this.arrow = arrow;

        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.1F;
    }

    @Override
    public void tick() {
        this.tickCount++;
        if (!this.player.isRemoved() && (this.tickCount <= 20 || arrow.getRopeAttachedTo() == player) && !arrow.isInGround()) {
            this.x = (float) this.player.getX();
            this.y = (float) this.player.getY();
            this.z = (float) this.player.getZ();
            float f = (float) this.player.getVelocity().lengthSquared();
            if ((double) f >= 1.0E-7) {
                this.volume = MathHelper.clamp(f / 4.0F, 0.0F, 1.0F);
            } else {
                this.volume = 0.0F;
            }

            if (this.tickCount < 20) {
                this.volume = 0.0F;
            } else if (this.tickCount < 40) {
                this.volume = this.volume * ((float) (this.tickCount - 20) / 20.0F);
            }

            if (this.volume > 0.8F) {
                this.pitch = 1.0F + (this.volume - 0.8F);
            } else {
                this.pitch = 1.0F;
            }
        } else {
            this.setDone();
        }
    }
}
