package net.minecraft.src;

import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class C_4400_ extends C_4362_<C_1218_> {
   private final C_4183_ f_234660_;

   public C_4400_(C_141743_ contextIn) {
      super(contextIn, C_141656_.f_171253_);
      this.f_234660_ = contextIn.m_234597_();
   }

   protected void m_7002_(C_1218_ entityIn, float partialTicks, C_2064_ stateIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn) {
      int i = entityIn.m_38694_();
      if (i > -1 && (float)i - partialTicks + 1.0F < 10.0F) {
         float f = 1.0F - ((float)i - partialTicks + 1.0F) / 10.0F;
         f = C_188_.m_14036_(f, 0.0F, 1.0F);
         f *= f;
         f *= f;
         float f1 = 1.0F + f * 0.3F;
         matrixStackIn.m_85841_(f1, f1, f1);
      }

      m_234661_(this.f_234660_, stateIn, matrixStackIn, bufferIn, packedLightIn, i > -1 && i / 5 % 2 == 0);
   }

   public static void m_234661_(
      C_4183_ renderDispatcherIn, C_2064_ blockStateIn, C_3181_ matrixStackIn, C_4139_ renderTypeBuffer, int combinedLight, boolean doFullBright
   ) {
      int i;
      if (doFullBright) {
         i = C_4474_.m_118093_(C_4474_.m_118088_(1.0F), 10);
      } else {
         i = C_4474_.f_118083_;
      }

      if (Config.isShaders() && doFullBright) {
         Shaders.setEntityColor(1.0F, 1.0F, 1.0F, 0.5F);
      }

      renderDispatcherIn.m_110912_(blockStateIn, matrixStackIn, renderTypeBuffer, combinedLight, i);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }
}
