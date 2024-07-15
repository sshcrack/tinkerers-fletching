package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.entity.arrows.TntArrowEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class TntArrowEntityRenderer extends ProjectileEntityRenderer<TntArrowEntity> {
    public static final Identifier TEXTURE = Identifier.of(TinkerersMod.MOD_ID, "textures/entity/projectiles/tnt_arrow.png");

    public TntArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TntArrowEntity entity) {
        return TEXTURE;
    }
}
