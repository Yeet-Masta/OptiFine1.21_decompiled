package net.minecraft.src;

import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.CustomColors;

public class C_4449_ extends C_4447_<C_896_, C_3863_<C_896_>> {
   private static final C_5265_ f_117404_ = C_5265_.m_340282_("textures/entity/sheep/sheep_fur.png");
   public C_3862_<C_896_> f_117405_;

   public C_4449_(C_4382_<C_896_, C_3863_<C_896_>> parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.f_117405_ = new C_3862_(modelSetIn.m_171103_(C_141656_.f_171178_));
   }

   public void m_6494_(
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int packedLightIn,
      C_896_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.m_29875_()) {
         if (entitylivingbaseIn.ci()) {
            C_3391_ minecraft = C_3391_.m_91087_();
            boolean flag = minecraft.m_91314_(entitylivingbaseIn);
            if (flag) {
               this.m_117386_().a(this.f_117405_);
               this.f_117405_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
               this.f_117405_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
               C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110491_(f_117404_));
               this.f_117405_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4357_.m_115338_(entitylivingbaseIn, 0.0F), -16777216);
            }
         } else {
            int i;
            if (entitylivingbaseIn.ai() && "jeb_".equals(entitylivingbaseIn.ah().getString())) {
               int j = 25;
               int k = entitylivingbaseIn.ai / 25 + entitylivingbaseIn.an();
               int l = C_1353_.values().length;
               int i1 = k % l;
               int j1 = (k + 1) % l;
               float f = ((float)(entitylivingbaseIn.ai % 25) + partialTicks) / 25.0F;
               int k1 = C_896_.m_339153_(C_1353_.m_41053_(i1));
               int l1 = C_896_.m_339153_(C_1353_.m_41053_(j1));
               if (Config.isCustomColors()) {
                  k1 = CustomColors.getSheepColors(C_1353_.m_41053_(i1), k1);
                  l1 = CustomColors.getSheepColors(C_1353_.m_41053_(j1), l1);
               }

               i = C_175_.m_269105_(f, k1, l1);
            } else {
               i = C_896_.m_339153_(entitylivingbaseIn.m_29874_());
               if (Config.isCustomColors()) {
                  i = CustomColors.getSheepColors(entitylivingbaseIn.m_29874_(), i);
               }
            }

            m_117359_(
               this.m_117386_(),
               this.f_117405_,
               f_117404_,
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
               i
            );
         }
      }
   }
}
