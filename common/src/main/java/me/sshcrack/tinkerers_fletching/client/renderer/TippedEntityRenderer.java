package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.entity.arrows.TieredArrowEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(value = EnvType.CLIENT)
public class TippedEntityRenderer extends ProjectileEntityRenderer<TieredArrowEntity> {
    public TippedEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(TieredArrowEntity arrowEntity) {
        return arrowEntity.getArrowTier().getTexture();
    }
}