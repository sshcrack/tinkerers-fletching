package me.sshcrack.tinkerers_fletching.item.projectile.tiered;

import net.minecraft.util.Identifier;

public enum ArrowTier {
    STONE(4, 1.2),
    IRON(3, 1.4),
    DIAMOND(2, 1.6),
    NETHERITE(1, 1.8);

    private final int power;
    private final double gravityMultiplier;

    ArrowTier(int power, double gravityMultiplier) {
        this.power = power;
        this.gravityMultiplier = gravityMultiplier;
    }


    public int getPower() {
        return power;
    }

    public Identifier getTexture() {
        return Identifier.of("tinkerers_fletching", "textures/entity/projectiles/" + this.name().toLowerCase() + "_arrow.png");
    }

    public double getGravityMultiplier() {
        return gravityMultiplier;
    }

    public static ArrowTier fromId(int id) {
        for (ArrowTier value : values()) {
            if (value.ordinal() == id) {
                return value;
            }
        }

        throw new AssertionError();
    }
}
