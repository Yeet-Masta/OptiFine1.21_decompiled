package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.DrownedModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class DrownedOuterLayer<T extends Drowned> extends RenderLayer<T, DrownedModel<T>> {
   private static ResourceLocation f_116907_ = ResourceLocation.m_340282_("textures/entity/zombie/drowned_outer_layer.png");
   public DrownedModel<T> f_116908_;
   public ResourceLocation customTextureLocation;

   public DrownedOuterLayer(RenderLayerParent<T, DrownedModel<T>> p_i174489_1_, EntityModelSet p_i174489_2_) {
      super(p_i174489_1_);
      this.f_116908_ = new DrownedModel(p_i174489_2_.m_171103_(ModelLayers.f_171139_));
   }

   public void m_6494_(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      ResourceLocation textureLocation = this.customTextureLocation != null ? this.customTextureLocation : f_116907_;
      m_117359_(
         this.m_117386_(),
         this.f_116908_,
         textureLocation,
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
         -1
      );
   }
}
