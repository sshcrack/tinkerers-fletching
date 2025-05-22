// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package me.sshcrack.tinkerers_fletching.client.renderer.model;
   
public class NetheriteTrident extends EntityModel<NetheriteTrident> {
	private final ModelPart body;
	private final ModelPart base;
	private final ModelPart left_spike;
	private final ModelPart middle_spike;
	private final ModelPart right_spike;
	private final ModelPart right_spike_r1;
	private final ModelPart right_spike_r2;
	private final ModelPart right_spike_r3;
	private final ModelPart right_spike_r4;
	private final ModelPart root;
	public NetheriteTrident(ModelPart root) {
		this.body = root.getChild("body");
		this.base = root.getChild("base");
		this.left_spike = root.getChild("left_spike");
		this.middle_spike = root.getChild("middle_spike");
		this.right_spike = root.getChild("right_spike");
		this.root = root.getChild("root");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 6).cuboid(-0.5F, 2.0F, -0.5F, 1.0F, 25.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData base = modelPartData.addChild("base", ModelPartBuilder.create().uv(4, 0).cuboid(-1.5F, 0.0F, -0.5F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData left_spike = modelPartData.addChild("left_spike", ModelPartBuilder.create().uv(4, 3).cuboid(-2.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData middle_spike = modelPartData.addChild("middle_spike", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData right_spike = modelPartData.addChild("right_spike", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(1.5F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(4, 3).mirrored().cuboid(3.25F, -2.75F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, -3.0F, 0.0F));

		ModelPartData right_spike_r1 = right_spike.addChild("right_spike_r1", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(1.25F, -5.75F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, 3.0F, 0.0F, -3.1416F, -0.0436F, 3.1416F));

		ModelPartData right_spike_r2 = right_spike.addChild("right_spike_r2", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(-1.0F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-2.0F, 3.0F, 0.0F, -3.1082F, -0.028F, 2.2685F));

		ModelPartData right_spike_r3 = right_spike.addChild("right_spike_r3", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(-1.25F, -3.0F, -0.5F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

		ModelPartData right_spike_r4 = right_spike.addChild("right_spike_r4", ModelPartBuilder.create().uv(4, 3).mirrored().cuboid(-0.5F, 0.5F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(2.0F, 3.0F, 0.0F, -0.0165F, -0.0012F, 1.5676F));

		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(NetheriteTrident entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		base.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_spike.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		middle_spike.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_spike.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}