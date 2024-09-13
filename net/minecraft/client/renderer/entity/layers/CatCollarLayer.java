package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Cat;
import net.optifine.Config;
import net.optifine.CustomColors;

public class CatCollarLayer extends RenderLayer<Cat, CatModel<Cat>> {
   private static ResourceLocation f_116649_ = ResourceLocation.m_340282_("textures/entity/cat/cat_collar.png");
   public CatModel<Cat> f_116650_;

   public CatCollarLayer(RenderLayerParent<Cat, CatModel<Cat>> parentIn, EntityModelSet modelSetIn) {
      super(parentIn);
      this.f_116650_ = new CatModel(modelSetIn.m_171103_(ModelLayers.f_171273_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Cat entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entitylivingbaseIn.m_21824_()) {
         int i = entitylivingbaseIn.m_28166_().m_340318_();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.m_28166_(), i);
         }

         m_117359_(
            this.m_117386_(),
            this.f_116650_,
            f_116649_,
            matrixStackIn,
            bufferIn,
            packedLightIn,
            entitylivingbaseIn,
            limbSwing,
            limbSwingAmount,
            ageInTicks,
            netHeadYaw,
            headPitch,
            partialTicks,
            i
         );
      }
   }
}
