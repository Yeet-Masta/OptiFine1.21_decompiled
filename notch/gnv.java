package net.minecraft.src;

import net.optifine.Config;

public class C_4420_ extends C_4447_<C_4102_, C_3853_<C_4102_>> {
   public C_4420_(C_4382_<C_4102_, C_3853_<C_4102_>> playerModelIn) {
      super(playerModelIn);
   }

   public void m_6494_(
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int packedLightIn,
      C_4102_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.ci() && entitylivingbaseIn.m_36170_(C_1144_.CAPE)) {
         C_290287_ playerskin = entitylivingbaseIn.m_294544_();
         if (entitylivingbaseIn.getLocationCape() != null) {
            C_1391_ itemstack = entitylivingbaseIn.m_6844_(C_516_.CHEST);
            if (!itemstack.m_150930_(C_1394_.f_42741_)) {
               matrixStackIn.m_85836_();
               matrixStackIn.m_252880_(0.0F, 0.0F, 0.125F);
               double d0 = C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.f_36102_, entitylivingbaseIn.f_36105_)
                  - C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.L, entitylivingbaseIn.dt());
               double d1 = C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.f_36103_, entitylivingbaseIn.f_36106_)
                  - C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.M, entitylivingbaseIn.dv());
               double d2 = C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.f_36104_, entitylivingbaseIn.f_36075_)
                  - C_188_.m_14139_((double)partialTicks, entitylivingbaseIn.N, entitylivingbaseIn.dz());
               float f = C_188_.m_14189_(partialTicks, entitylivingbaseIn.aZ, entitylivingbaseIn.aY);
               double d3 = (double)C_188_.m_14031_(f * (float) (Math.PI / 180.0));
               double d4 = (double)(-C_188_.m_14089_(f * (float) (Math.PI / 180.0)));
               float f1 = (float)d1 * 10.0F;
               f1 = C_188_.m_14036_(f1, -6.0F, 32.0F);
               float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
               f2 = C_188_.m_14036_(f2, 0.0F, 150.0F);
               float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
               f3 = C_188_.m_14036_(f3, -20.0F, 20.0F);
               if (f2 < 0.0F) {
                  f2 = 0.0F;
               }

               if (f2 > 165.0F) {
                  f2 = 165.0F;
               }

               if (f1 < -5.0F) {
                  f1 = -5.0F;
               }

               float f4 = C_188_.m_14179_(partialTicks, entitylivingbaseIn.f_36099_, entitylivingbaseIn.f_36100_);
               f1 += C_188_.m_14031_(C_188_.m_14179_(partialTicks, entitylivingbaseIn.Y, entitylivingbaseIn.Z) * 6.0F) * 32.0F * f4;
               if (entitylivingbaseIn.cb()) {
                  f1 += 25.0F;
               }

               float pct = Config.getAverageFrameTimeSec() * 20.0F;
               pct = Config.limit(pct, 0.02F, 1.0F);
               entitylivingbaseIn.capeRotateX = C_188_.m_14179_(pct, entitylivingbaseIn.capeRotateX, 6.0F + f2 / 2.0F + f1);
               entitylivingbaseIn.capeRotateZ = C_188_.m_14179_(pct, entitylivingbaseIn.capeRotateZ, f3 / 2.0F);
               entitylivingbaseIn.capeRotateY = C_188_.m_14179_(pct, entitylivingbaseIn.capeRotateY, 180.0F - f3 / 2.0F);
               matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(entitylivingbaseIn.capeRotateX));
               matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(entitylivingbaseIn.capeRotateZ));
               matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(entitylivingbaseIn.capeRotateY));
               C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110446_(entitylivingbaseIn.getLocationCape()));
               this.m_117386_().m_103411_(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
               matrixStackIn.m_85849_();
            }
         }
      }
   }
}
