package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.monster.warden.Warden;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class WardenEmissiveLayer extends RenderLayer {
   private final ResourceLocation f_234881_;
   private final AlphaFunction f_234882_;
   private final DrawSelector f_234883_;

   public WardenEmissiveLayer(RenderLayerParent p_i234884_1_, ResourceLocation p_i234884_2_, AlphaFunction p_i234884_3_, DrawSelector p_i234884_4_) {
      super(p_i234884_1_);
      this.f_234881_ = p_i234884_2_;
      this.f_234882_ = p_i234884_3_;
      this.f_234883_ = p_i234884_4_;
   }

   public void m_6494_(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Warden entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entitylivingbaseIn.m_20145_()) {
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.m_234889_();
         VertexConsumer vertexconsumer = bufferIn.m_6299_(RenderType.m_234338_(this.f_234881_));
         float f = this.f_234882_.m_234919_(entitylivingbaseIn, partialTicks, ageInTicks);
         int i = ARGB32.m_13660_(Mth.m_14143_(f * 255.0F), 255, 255, 255);
         ((WardenModel)this.m_117386_()).m_7695_(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.m_115338_(entitylivingbaseIn, 0.0F), i);
         this.m_234914_();
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }
      }

   }

   private void m_234889_() {
      List list = this.f_234883_.m_234923_((WardenModel)this.m_117386_());
      ((WardenModel)this.m_117386_()).m_142109_().m_171331_().forEach((partIn) -> {
         partIn.f_233556_ = true;
      });
      list.forEach((partIn) -> {
         partIn.f_233556_ = false;
      });
      list.forEach((partIn) -> {
         Iterator var1 = partIn.f_104213_.entrySet().iterator();

         while(var1.hasNext()) {
            Map.Entry entry = (Map.Entry)var1.next();
            if (((String)entry.getKey()).startsWith("CEM-")) {
               ((ModelPart)entry.getValue()).f_233556_ = false;
            }
         }

      });
   }

   private void m_234914_() {
      ((WardenModel)this.m_117386_()).m_142109_().m_171331_().forEach((partIn) -> {
         partIn.f_233556_ = false;
      });
   }

   public interface AlphaFunction {
      float m_234919_(Warden var1, float var2, float var3);
   }

   public interface DrawSelector {
      List m_234923_(EntityModel var1);
   }
}
