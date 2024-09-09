package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.animal.horse.Llama;

public class LlamaDecorLayer extends net.minecraft.client.renderer.entity.layers.RenderLayer<Llama, LlamaModel<Llama>> {
   private static final net.minecraft.resources.ResourceLocation[] f_117214_ = new net.minecraft.resources.ResourceLocation[]{
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/white.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/orange.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/magenta.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/light_blue.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/yellow.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/lime.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/pink.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/gray.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/light_gray.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/cyan.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/purple.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/blue.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/brown.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/green.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/red.png"),
      net.minecraft.resources.ResourceLocation.m_340282_("textures/entity/llama/decor/black.png")
   };
   private static final net.minecraft.resources.ResourceLocation f_117215_ = net.minecraft.resources.ResourceLocation.m_340282_(
      "textures/entity/llama/decor/trader_llama.png"
   );
   private final LlamaModel<Llama> f_117216_;

   public LlamaDecorLayer(RenderLayerParent<Llama, LlamaModel<Llama>> p_i174498_1_, EntityModelSet p_i174498_2_) {
      super(p_i174498_1_);
      this.f_117216_ = new LlamaModel(p_i174498_2_.m_171103_(ModelLayers.f_171195_));
   }

   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      Llama entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      net.minecraft.world.item.DyeColor dyecolor = entitylivingbaseIn.m_30826_();
      net.minecraft.resources.ResourceLocation resourcelocation;
      if (dyecolor != null) {
         resourcelocation = f_117214_[dyecolor.m_41060_()];
      } else {
         if (!entitylivingbaseIn.m_7565_()) {
            return;
         }

         resourcelocation = f_117215_;
      }

      if (this.f_117216_.locationTextureCustom != null) {
         resourcelocation = this.f_117216_.locationTextureCustom;
      }

      this.m_117386_().m_102624_(this.f_117216_);
      this.f_117216_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_110458_(resourcelocation));
      this.f_117216_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }
}
