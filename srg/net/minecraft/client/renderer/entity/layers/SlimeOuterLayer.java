package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class SlimeOuterLayer<T extends LivingEntity> extends RenderLayer<T, SlimeModel<T>> {
   public EntityModel<T> f_117455_;
   public ResourceLocation customTextureLocation;

   public SlimeOuterLayer(RenderLayerParent<T, SlimeModel<T>> p_i174535_1_, EntityModelSet p_i174535_2_) {
      super(p_i174535_1_);
      this.f_117455_ = new SlimeModel(p_i174535_2_.m_171103_(ModelLayers.f_171242_));
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
      Minecraft minecraft = Minecraft.m_91087_();
      boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.m_20145_();
      if (!entitylivingbaseIn.m_20145_() || flag) {
         ResourceLocation textureLocation = this.customTextureLocation != null ? this.customTextureLocation : this.m_117347_(entitylivingbaseIn);
         VertexConsumer vertexconsumer;
         if (flag) {
            vertexconsumer = bufferIn.m_6299_(RenderType.m_110491_(textureLocation));
         } else {
            vertexconsumer = bufferIn.m_6299_(RenderType.m_110473_(textureLocation));
         }

         this.m_117386_().m_102624_(this.f_117455_);
         this.f_117455_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.f_117455_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.f_117455_.m_340227_(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F));
      }
   }
}
