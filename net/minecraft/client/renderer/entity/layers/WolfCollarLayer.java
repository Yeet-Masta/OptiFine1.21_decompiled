package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.WolfModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Wolf;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.entity.model.ModelAdapter;

public class WolfCollarLayer extends RenderLayer<Wolf, WolfModel<Wolf>> {
   private static ResourceLocation f_117704_ = ResourceLocation.m_340282_("textures/entity/wolf/wolf_collar.png");
   public WolfModel<Wolf> model = new WolfModel(ModelAdapter.bakeModelLayer(ModelLayers.f_171221_));

   public WolfCollarLayer(RenderLayerParent<Wolf, WolfModel<Wolf>> rendererIn) {
      super(rendererIn);
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Wolf entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entitylivingbaseIn.m_21824_() && !entitylivingbaseIn.m_20145_()) {
         int i = entitylivingbaseIn.m_30428_().m_340318_();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.m_30428_(), i);
         }

         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110458_(f_117704_));
         this.m_117386_().m_7695_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_, i);
      }
   }
}
