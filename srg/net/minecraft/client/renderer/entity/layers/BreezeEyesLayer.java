package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.breeze.Breeze;

public class BreezeEyesLayer extends RenderLayer<Breeze, BreezeModel<Breeze>> {
   private RenderType f_316997_ = RenderType.m_305574_(ResourceLocation.m_340282_("textures/entity/breeze/breeze_eyes.png"));
   private BreezeModel<Breeze> customModel;

   public BreezeEyesLayer(RenderLayerParent<Breeze, BreezeModel<Breeze>> p_i306560_1_) {
      super(p_i306560_1_);
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
      VertexConsumer vertexconsumer = bufferIn.m_6299_(this.f_316997_);
      BreezeModel<Breeze> breezemodel = this.getEntityModel();
      BreezeRenderer.m_323838_(breezemodel, new ModelPart[]{breezemodel.m_319970_(), breezemodel.m_323648_()})
         .m_340227_(matrixStackIn, vertexconsumer, packedLightIn, OverlayTexture.f_118083_);
   }

   public void setModel(BreezeModel<Breeze> model) {
      this.customModel = model;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.f_316997_ = RenderType.m_305574_(textureLocation);
   }

   public BreezeModel<Breeze> getEntityModel() {
      return this.customModel != null ? this.customModel : (BreezeModel)super.m_117386_();
   }
}
