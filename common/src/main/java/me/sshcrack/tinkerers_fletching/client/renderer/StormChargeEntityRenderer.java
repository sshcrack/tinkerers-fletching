package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
import me.sshcrack.tinkerers_fletching.client.renderer.model.StormChargeEntityModel;
import me.sshcrack.tinkerers_fletching.entity.StormChargeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.BreezeEyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.BreezeWindFeatureRenderer;
import net.minecraft.client.render.entity.model.BreezeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.WindChargeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class StormChargeEntityRenderer extends EntityRenderer<StormChargeEntity> {
    private static final Identifier TEXTURE = Identifier.of(TinkerersMod.MOD_ID, "textures/entity/storm_charge.png");
    private final StormChargeEntityModel model;

    public StormChargeEntityRenderer(EntityRendererFactory.Context arg) {
        super(arg);
        this.model = new StormChargeEntityModel(arg.getPart(EntityModelLayers.BREEZE_WIND));
    }

    @Override
    public void render(StormChargeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.scale(2, 2, 2);
        matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(180));
        matrices.translate(0.0D, -1.5d, 0.0D);

        float m = (float) entity.age + tickDelta;
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getBreezeWind(TEXTURE, this.getXOffset(m) % 1.0F, 0.0F));
        this.model.setAngles(entity, 0, 0, m, -yaw, 0);

        this.model.getWindBody().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    private float getXOffset(float tickDelta) {
        return tickDelta * 0.02F;
    }


    public Identifier getTexture(StormChargeEntity arg) {
        return TEXTURE;
    }
}
