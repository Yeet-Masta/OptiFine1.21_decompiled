package net.minecraft.src;

import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.entity.model.ModelAdapter;

public class C_4461_ extends C_4447_ {
   private static final C_5265_ f_117704_ = C_5265_.m_340282_("textures/entity/wolf/wolf_collar.png");
   public C_3885_ model;

   public C_4461_(C_4382_ rendererIn) {
      super(rendererIn);
      this.model = new C_3885_(ModelAdapter.bakeModelLayer(C_141656_.f_171221_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_922_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entitylivingbaseIn.s() && !entitylivingbaseIn.ci()) {
         int i = entitylivingbaseIn.m_30428_().m_340318_();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.m_30428_(), i);
         }

         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(f_117704_));
         ((C_3885_)this.m_117386_()).a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, i);
      }

   }
}
