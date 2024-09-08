package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.breeze.Breeze;

public class BreezeWindLayer extends RenderLayer<Breeze, BreezeModel<Breeze>> {
   private ResourceLocation f_315155_ = ResourceLocation.m_340282_("textures/entity/breeze/breeze_wind.png");
   private BreezeModel<Breeze> f_336859_;

   public BreezeWindLayer(Context p_i307292_1_, RenderLayerParent<Breeze, BreezeModel<Breeze>> p_i307292_2_) {
      super(p_i307292_2_);
      this.f_336859_ = new BreezeModel(p_i307292_1_.m_174023_(ModelLayers.f_337505_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Breeze entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      float f = (float)entitylivingbaseIn.f_19797_ + partialTicks;
      VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_305520_(this.f_315155_, this.m_306824_(f) % 1.0F, 0.0F));
      this.f_336859_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      BreezeRenderer.m_323838_(this.f_336859_, new ModelPart[]{this.f_336859_.m_321100_()})
         .m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }

   private float m_306824_(float p_306824_1_) {
      return p_306824_1_ * 0.02F;
   }

   public void setModel(BreezeModel<Breeze> model) {
      this.f_336859_ = model;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.f_315155_ = textureLocation;
   }
}
