package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.LlamaModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.item.DyeColor;

public class LlamaDecorLayer extends RenderLayer {
   private static final ResourceLocation[] f_117214_ = new ResourceLocation[]{ResourceLocation.m_340282_("textures/entity/llama/decor/white.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/orange.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/magenta.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/light_blue.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/yellow.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/lime.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/pink.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/gray.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/light_gray.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/cyan.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/purple.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/blue.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/brown.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/green.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/red.png"), ResourceLocation.m_340282_("textures/entity/llama/decor/black.png")};
   private static final ResourceLocation f_117215_ = ResourceLocation.m_340282_("textures/entity/llama/decor/trader_llama.png");
   private final LlamaModel f_117216_;

   public LlamaDecorLayer(RenderLayerParent p_i174498_1_, EntityModelSet p_i174498_2_) {
      super(p_i174498_1_);
      this.f_117216_ = new LlamaModel(p_i174498_2_.m_171103_(ModelLayers.f_171195_));
   }

   public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Llama entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      DyeColor dyecolor = entitylivingbaseIn.m_30826_();
      ResourceLocation resourcelocation;
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

      ((LlamaModel)this.m_117386_()).m_102624_(this.f_117216_);
      this.f_117216_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110458_(resourcelocation));
      this.f_117216_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }
}
