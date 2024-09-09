package net.minecraft.src;

import net.optifine.Config;
import net.optifine.CustomColors;

public class C_4422_ extends C_4447_ {
   private static final C_5265_ f_116649_ = C_5265_.m_340282_("textures/entity/cat/cat_collar.png");
   public C_3805_ f_116650_;

   public C_4422_(C_4382_ parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.f_116650_ = new C_3805_(modelSetIn.m_171103_(C_141656_.f_171273_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_819_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entitylivingbaseIn.s()) {
         int i = entitylivingbaseIn.m_28166_().m_340318_();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.m_28166_(), i);
         }

         m_117359_(this.m_117386_(), this.f_116650_, f_116649_, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, i);
      }

   }
}
