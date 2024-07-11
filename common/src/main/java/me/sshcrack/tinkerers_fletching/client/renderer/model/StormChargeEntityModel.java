//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sshcrack.tinkerers_fletching.client.renderer.model;

import me.sshcrack.tinkerers_fletching.entity.StormChargeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.animation.BreezeAnimations;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class StormChargeEntityModel extends SinglePartEntityModel<StormChargeEntity> {
    private final ModelPart root;
    private final ModelPart windBody;
    private final ModelPart windTop;
    private final ModelPart windMid;
    private final ModelPart windBottom;

    public StormChargeEntityModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucent);
        this.root = root;
        this.windBody = root.getChild("wind_body");
        this.windBottom = this.windBody.getChild("wind_bottom");
        this.windMid = this.windBottom.getChild("wind_mid");
        this.windTop = this.windMid.getChild("wind_top");
    }

    public void setAngles(StormChargeEntity stormEntity, float f, float g, float h, float i, float j) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        float k = h * 3.1415927F * -0.1F;
        this.windTop.pivotX = MathHelper.cos(k) * 1.0F * 0.6F;
        this.windTop.pivotZ = MathHelper.sin(k) * 1.0F * 0.6F;
        this.windMid.pivotX = MathHelper.sin(k) * 0.5F * 0.8F;
        this.windMid.pivotZ = MathHelper.cos(k) * 0.8F;
        this.windBottom.pivotX = MathHelper.cos(k) * -0.25F * 1.0F;
        this.windBottom.pivotZ = MathHelper.sin(k) * -0.25F * 1.0F;
    }

    public ModelPart getPart() {
        return this.root;
    }

    public ModelPart getWindBody() {
        return this.windBody;
    }
}
