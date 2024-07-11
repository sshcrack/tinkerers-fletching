package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.entity.IceSpikeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.util.Identifier;

public class IceSpikeEntityRenderer extends ProjectileEntityRenderer<IceSpikeEntity> {
    public static final Identifier TEXTURE = Identifier.of(TinkerersMod.MOD_ID, "textures/entity/ice_spike.png");

    public IceSpikeEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public Identifier getTexture(IceSpikeEntity entity) {
        return TEXTURE;
    }
}
