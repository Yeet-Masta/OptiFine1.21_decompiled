package net.minecraft.src;

import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_313678_.C_313439_;

public class C_4435_ extends C_4447_ {
   public C_3827_ f_117017_;
   public C_5265_ customTextureLocation;

   public C_4435_(C_4382_ p_i174495_1_, C_141653_ p_i174495_2_) {
      super(p_i174495_1_);
      this.f_117017_ = new C_3827_(p_i174495_2_.m_171103_(C_141656_.f_171187_));
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_928_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      C_1391_ itemstack = entitylivingbaseIn.fO();
      C_1381_ var13 = itemstack.m_41720_();
      if (var13 instanceof C_313678_ animalarmoritem) {
         if (animalarmoritem.m_319458_() == C_313439_.EQUESTRIAN) {
            ((C_3827_)this.m_117386_()).a(this.f_117017_);
            this.f_117017_.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.f_117017_.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            int i;
            if (itemstack.m_204117_(C_140_.f_314020_)) {
               i = C_175_.m_321570_(C_313801_.m_322889_(itemstack, -6265536));
            } else {
               i = -1;
            }

            C_5265_ armorTexture = this.customTextureLocation != null ? this.customTextureLocation : animalarmoritem.m_320881_();
            C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110458_(armorTexture));
            this.f_117017_.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, i);
            return;
         }
      }

   }
}
