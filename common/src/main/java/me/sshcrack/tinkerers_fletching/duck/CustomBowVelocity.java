package me.sshcrack.tinkerers_fletching.duck;

import net.minecraft.entity.Entity;

public interface CustomBowVelocity {
    float getBowVelocityMultiplier(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence);
}
