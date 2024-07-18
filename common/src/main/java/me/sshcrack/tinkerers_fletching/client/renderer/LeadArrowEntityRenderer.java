package me.sshcrack.tinkerers_fletching.client.renderer;

import me.sshcrack.tinkerers_fletching.entity.arrows.LeadArrowEntity;
import me.sshcrack.tinkerers_fletching.mixin.EntityRendererInvoker;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.joml.Matrix4f;

public class LeadArrowEntityRenderer extends ProjectileEntityRenderer<LeadArrowEntity> {
    public LeadArrowEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    public void render(LeadArrowEntity leadArrow, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(leadArrow, yaw, tickDelta, matrices, vertexConsumers, light);
        var ropeAttachment = leadArrow.getRopeAttachedTo();
        if (ropeAttachment == null)
            return;

        renderLeash(leadArrow, tickDelta, matrices, vertexConsumers, ropeAttachment);
    }


    private <E extends Entity> void renderLeash(E leadArrow, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, E owner) {
        matrices.push();
        Vec3d leashPos = owner.getLeashPos(tickDelta);
        double d = (double) (leadArrow.lerpYaw(tickDelta) * MathHelper.RADIANS_PER_DEGREE) + MathHelper.HALF_PI;
        Vec3d leashOffset = leadArrow.getLeashOffset(tickDelta);

        double rotateOffsetX = Math.cos(d) * leashOffset.z + Math.sin(d) * leashOffset.x;
        double rotateOffsetZ = Math.sin(d) * leashOffset.z - Math.cos(d) * leashOffset.x;

        double ownerX = MathHelper.lerp(tickDelta, leadArrow.prevX, leadArrow.getX()) + rotateOffsetX;
        double ownerY = MathHelper.lerp(tickDelta, leadArrow.prevY, leadArrow.getY()) + leashOffset.y;
        double ownerZ = MathHelper.lerp(tickDelta, leadArrow.prevZ, leadArrow.getZ()) + rotateOffsetZ;

        matrices.translate(rotateOffsetX, leashOffset.y, rotateOffsetZ);
        float diffX = (float) (leashPos.x - ownerX);
        float diffY = (float) (leashPos.y - ownerY);
        float diffZ = (float) (leashPos.z - ownerZ);

        VertexConsumer leashConsumer = vertexConsumers.getBuffer(RenderLayer.getLeash());
        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();

        float invLength = MathHelper.inverseSqrt(diffX * diffX + diffZ * diffZ) * 0.025F / 2.0F;
        float normDiffZ = diffZ * invLength;
        float normDiffX = diffX * invLength;

        BlockPos ownerBlockPos = BlockPos.ofFloored(leadArrow.getCameraPosVec(tickDelta));
        BlockPos leadBlockPos = BlockPos.ofFloored(owner.getCameraPosVec(tickDelta));

        var leadRenderer = (EntityRendererInvoker) this.dispatcher.getRenderer(leadArrow);
        int leadBlockLight = leadRenderer.tinkerers$getBlockLight(leadArrow, ownerBlockPos);

        var ownerRenderer = (EntityRendererInvoker) this.dispatcher.getRenderer(owner);
        int ownerBlockLight = ownerRenderer.tinkerers$getBlockLight(owner, leadBlockPos);

        int ownerSkyLight = leadArrow.getWorld().getLightLevel(LightType.SKY, ownerBlockPos);
        int leadSkyLight = leadArrow.getWorld().getLightLevel(LightType.SKY, leadBlockPos);

        int u;
        for (u = 0; u <= 50; ++u) {
            renderLeashSegment(leashConsumer, positionMatrix, diffX, diffY, diffZ, leadBlockLight, ownerBlockLight, ownerSkyLight, leadSkyLight, 0.025F, 0.025F, normDiffZ, normDiffX, u, false);
        }

        for (u = 50; u >= 0; --u) {
            renderLeashSegment(leashConsumer, positionMatrix, diffX, diffY, diffZ, leadBlockLight, ownerBlockLight, ownerSkyLight, leadSkyLight, 0.025F, 0.0F, normDiffZ, normDiffX, u, true);
        }

        matrices.pop();
    }


    private static void renderLeashSegment(VertexConsumer vertexConsumer, Matrix4f matrix, float leashedEntityX, float leashedEntityY, float leashedEntityZ, int leashedEntityBlockLight, int leashHolderBlockLight, int leashedEntitySkyLight, int leashHolderSkyLight, float idk1, float idk2, float normDiffZ, float normDiffX, int segmentIndex, boolean isLeashKnot) {
        float percentage = (float) segmentIndex / 50.0F;
        int blockLight = (int) MathHelper.lerp(percentage, (float) leashedEntityBlockLight, (float) leashHolderBlockLight);
        int skyLight = (int) MathHelper.lerp(percentage, (float) leashedEntitySkyLight, (float) leashHolderSkyLight);
        int lightUV = LightmapTextureManager.pack(blockLight, skyLight);

        float colorMultiplier = segmentIndex % 2 == (isLeashKnot ? 1 : 0) ? 0.7F : 1.0F;
        float r = 0.5F * colorMultiplier;
        float g = 0.4F * colorMultiplier;
        float b = 0.3F * colorMultiplier;

        float x = leashedEntityX * percentage;
        float y = leashedEntityY > 0.0F ? leashedEntityY * percentage * percentage : leashedEntityY - leashedEntityY * (1.0F - percentage) * (1.0F - percentage);
        float z = leashedEntityZ * percentage;

        vertexConsumer.vertex(matrix, x - normDiffZ, y + idk2, z + normDiffX).color(r, g, b, 1.0F).light(lightUV);
        vertexConsumer.vertex(matrix, x + normDiffZ, y + idk1 - idk2, z - normDiffX).color(r, g, b, 1.0F).light(lightUV);
    }

    @Override
    public Identifier getTexture(LeadArrowEntity entity) {
        return ArrowEntityRenderer.TEXTURE;
    }
}
