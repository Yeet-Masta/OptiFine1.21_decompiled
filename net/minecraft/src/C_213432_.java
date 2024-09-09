package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class C_213432_ extends C_4447_ {
   private final C_5265_ f_234881_;
   private final C_213433_ f_234882_;
   private final C_213434_ f_234883_;

   public C_213432_(C_4382_ p_i234884_1_, C_5265_ p_i234884_2_, C_213433_ p_i234884_3_, C_213434_ p_i234884_4_) {
      super(p_i234884_1_);
      this.f_234881_ = p_i234884_2_;
      this.f_234882_ = p_i234884_3_;
      this.f_234883_ = p_i234884_4_;
   }

   public void m_6494_(C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, C_213061_ entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!entitylivingbaseIn.ci()) {
         if (Config.isShaders()) {
            Shaders.beginSpiderEyes();
         }

         Config.getRenderGlobal().renderOverlayEyes = true;
         this.m_234889_();
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_234338_(this.f_234881_));
         float f = this.f_234882_.m_234919_(entitylivingbaseIn, partialTicks, ageInTicks);
         int i = C_175_.m_13660_(C_188_.m_14143_(f * 255.0F), 255, 255, 255);
         ((C_213401_)this.m_117386_()).a(matrixStackIn, vertexconsumer, packedLightIn, C_4357_.m_115338_(entitylivingbaseIn, 0.0F), i);
         this.m_234914_();
         Config.getRenderGlobal().renderOverlayEyes = false;
         if (Config.isShaders()) {
            Shaders.endSpiderEyes();
         }
      }

   }

   private void m_234889_() {
      List list = this.f_234883_.m_234923_((C_213401_)this.m_117386_());
      ((C_213401_)this.m_117386_()).m_142109_().m_171331_().forEach((partIn) -> {
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
               ((C_3889_)entry.getValue()).f_233556_ = false;
            }
         }

      });
   }

   private void m_234914_() {
      ((C_213401_)this.m_117386_()).m_142109_().m_171331_().forEach((partIn) -> {
         partIn.f_233556_ = false;
      });
   }

   public interface C_213433_ {
      float m_234919_(C_213061_ var1, float var2, float var3);
   }

   public interface C_213434_ {
      List m_234923_(C_3819_ var1);
   }
}
