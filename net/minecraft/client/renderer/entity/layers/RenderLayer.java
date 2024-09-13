package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public abstract class RenderLayer<T extends Entity, M extends EntityModel<T>> {
   private RenderLayerParent<T, M> f_117344_;
   public boolean custom = false;

   public RenderLayer(RenderLayerParent<T, M> entityRendererIn) {
      this.f_117344_ = entityRendererIn;
   }

   protected static <T extends LivingEntity> void m_117359_(
      EntityModel<T> modelParentIn,
      EntityModel<T> modelIn,
      ResourceLocation textureLocationIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entityIn,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float partialTicks,
      int colorIn
   ) {
      if (!entityIn.m_20145_()) {
         modelParentIn.m_102624_(modelIn);
         modelIn.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTicks);
         modelIn.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         m_117376_(modelIn, textureLocationIn, matrixStackIn, bufferIn, packedLightIn, entityIn, colorIn);
      }
   }

   protected static <T extends LivingEntity> void m_117376_(
      EntityModel<T> modelIn,
      ResourceLocation textureLocationIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entityIn,
      int colorIn
   ) {
      if (modelIn.locationTextureCustom != null) {
         textureLocationIn = modelIn.locationTextureCustom;
      }

      VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_110458_(textureLocationIn));
      modelIn.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.m_115338_(entityIn, 0.0F), colorIn);
   }

   public M m_117386_() {
      return (M)this.f_117344_.m_7200_();
   }

   protected ResourceLocation m_117347_(T entityIn) {
      return this.f_117344_.m_5478_(entityIn);
   }

   public abstract void m_6494_(
      PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10
   );
}
