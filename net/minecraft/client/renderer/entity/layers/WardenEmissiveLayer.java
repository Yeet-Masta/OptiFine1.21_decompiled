package net.minecraft.client.renderer.entity.layers;

import java.util.List;
import java.util.Map.Entry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.monster.warden.Warden;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class WardenEmissiveLayer<T extends Warden, M extends WardenModel<T>> extends net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> {
   private final net.minecraft.resources.ResourceLocation f_234881_;
   private final net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer.AlphaFunction<T> f_234882_;
   private final net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer.DrawSelector<T, M> f_234883_;

   public WardenEmissiveLayer(
      RenderLayerParent<T, M> p_i234884_1_,
      net.minecraft.resources.ResourceLocation p_i234884_2_,
      net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer.AlphaFunction<T> p_i234884_3_,
      net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer.DrawSelector<T, M> p_i234884_4_
   ) {
      super(p_i234884_1_);
      this.f_234881_ = p_i234884_2_;
      this.f_234882_ = p_i234884_3_;
      this.f_234883_ = p_i234884_4_;
   }

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
      if (!entitylivingbaseIn.m_20145_()) {
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.m_234889_();
         com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(net.minecraft.client.renderer.RenderType.m_234338_(this.f_234881_));
         float f = this.f_234882_.m_234919_(entitylivingbaseIn, partialTicks, ageInTicks);
         int i = ARGB32.m_13660_(net.minecraft.util.Mth.m_14143_(f * 255.0F), 255, 255, 255);
         this.m_117386_()
            .m_7695_(
               matrixStackIn, vertexconsumer, packedLightIn, net.minecraft.client.renderer.entity.LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), i
            );
         this.m_234914_();
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }
      }
   }

   private void m_234889_() {
      List<net.minecraft.client.model.geom.ModelPart> list = this.f_234883_.m_234923_(this.m_117386_());
      this.m_117386_().m_142109_().m_171331_().forEach(partIn -> partIn.f_233556_ = true);
      list.forEach(partIn -> partIn.f_233556_ = false);
      list.forEach(partIn -> {
         for (Entry<String, net.minecraft.client.model.geom.ModelPart> entry : partIn.f_104213_.entrySet()) {
            if (((String)entry.getKey()).startsWith("CEM-")) {
               ((net.minecraft.client.model.geom.ModelPart)entry.getValue()).f_233556_ = false;
            }
         }
      });
   }

   private void m_234914_() {
      this.m_117386_().m_142109_().m_171331_().forEach(partIn -> partIn.f_233556_ = false);
   }

   public interface AlphaFunction<T extends Warden> {
      float m_234919_(T var1, float var2, float var3);
   }

   public interface DrawSelector<T extends Warden, M extends EntityModel<T>> {
      List<net.minecraft.client.model.geom.ModelPart> m_234923_(M var1);
   }
}
