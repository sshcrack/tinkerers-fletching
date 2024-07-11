package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.client.renderer.model.geo.StormChargeEntityModel;
import me.sshcrack.tinkerers_fletching.entity.StormChargeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class StormChargeEntityRenderer extends GeoEntityRenderer<StormChargeEntity> {
    public StormChargeEntityRenderer(EntityRendererFactory.Context renderManager) {
        super(renderManager, new StormChargeEntityModel());
    }
}
