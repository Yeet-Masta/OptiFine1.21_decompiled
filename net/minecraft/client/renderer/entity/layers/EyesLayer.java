package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public abstract class EyesLayer<T extends Entity, M extends EntityModel<T>> extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   public EyesLayer(RenderLayerParent<T, M> p_i116980_1_) {
      super(p_i116980_1_);
   }

   @Override
   public void m_6494_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(this.m_5708_());
      if (Config.isShaders()) {
         Shaders.beginSpiderEyes();
      }

      Config.getRenderGlobal().renderOverlayEyes = true;
      this.m_117386_().m_340227_(matrixStackIn, vertexconsumer, 15728640, OverlayTexture.f_118083_);
      Config.getRenderGlobal().renderOverlayEyes = false;
      if (Config.isShaders()) {
         Shaders.endSpiderEyes();
      }
   }

   public abstract net.minecraft.client.renderer.RenderType m_5708_();
}
