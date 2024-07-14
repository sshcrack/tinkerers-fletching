package me.sshcrack.tinkerers_fletching.item.projectile.tiered;

import net.minecraft.util.Identifier;

public enum ArrowTier {
    STONE(4),
    IRON(3),
    DIAMOND(2),
    NETHERITE(1);

    private final int power;

    ArrowTier(int power) {
        this.power = power;
    }


    public int getPower() {
        return power;
    }

    public Identifier getTexture() {
        return Identifier.of("tinkerers_fletching", "textures/entity/projectiles/" + this.name().toLowerCase() + "_arrow.png");
    }
}
