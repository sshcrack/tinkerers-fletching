//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.sshcrack.tinkerers_fletching.client.renderer.model.geo;

import me.sshcrack.tinkerers_fletching.TinkerersMod;
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
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@Environment(EnvType.CLIENT)
public class StormChargeEntityModel extends GeoModel<StormChargeEntity> {

    @Override
    public Identifier getModelResource(StormChargeEntity animatable) {
        return Identifier.of(TinkerersMod.MOD_ID, "geo/storm_charge.geo.json");
    }

    @Override
    public Identifier getTextureResource(StormChargeEntity animatable) {
        return Identifier.of(TinkerersMod.MOD_ID, "textures/entity/storm_charge.png");
    }

    @Override
    public Identifier getAnimationResource(StormChargeEntity animatable) {
        return Identifier.of(TinkerersMod.MOD_ID, "animations/storm_charge.animation.json");
    }
}
