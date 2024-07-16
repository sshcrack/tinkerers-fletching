package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.entity.arrows.TntArrowEntity;
import me.sshcrack.tinkerers_fletching.entity.arrows.TrackingArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class TrackingArrowEntityRenderer extends ProjectileEntityRenderer<TrackingArrowEntity> {
    public static final Identifier TEXTURE = Identifier.of(TinkerersMod.MOD_ID, "textures/entity/projectiles/tracking_arrow.png");

    public TrackingArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TrackingArrowEntity entity) {
        return TEXTURE;
    }
}
