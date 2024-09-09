package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;

public class C_4253_ implements C_4244_ {
   private static final String f_173629_ = "stick";
   private static final int f_173630_ = -988212;
   private static final int f_173631_ = C_188_.m_144944_(16);
   private static final float f_278501_ = 0.6666667F;
   private static final C_3046_ f_278459_ = new C_3046_(0.0, 0.3333333432674408, 0.046666666865348816);
   private final Map f_173632_;
   private final C_3429_ f_173633_;
   private static double textRenderDistanceSq = 4096.0;

   public C_4253_(C_141731_.C_141732_ contextIn) {
      this.f_173632_ = (Map)C_2106_.m_61843_().collect(ImmutableMap.toImmutableMap((woodTypeIn) -> {
         return woodTypeIn;
      }, (woodTypeIn) -> {
         return new C_4254_(contextIn.m_173582_(C_141656_.m_171291_(woodTypeIn)));
      }));
      this.f_173633_ = contextIn.m_173586_();
   }

   public void m_6922_(C_2024_ tileEntityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn) {
      C_2064_ blockstate = tileEntityIn.n();
      C_1895_ signblock = (C_1895_)blockstate.m_60734_();
      C_2106_ woodtype = C_1895_.m_247329_(signblock);
      C_4254_ signrenderer$signmodel = (C_4254_)this.f_173632_.get(woodtype);
      signrenderer$signmodel.f_112507_.f_104207_ = blockstate.m_60734_() instanceof C_1922_;
      this.m_278756_(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, blockstate, signblock, woodtype, signrenderer$signmodel);
   }

   public float m_278770_() {
      return 0.6666667F;
   }

   public float m_278631_() {
      return 0.6666667F;
   }

   void m_278756_(C_2024_ tileEntityIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn, C_2064_ stateIn, C_1895_ blockIn, C_2106_ woodTypeIn, C_3840_ modelIn) {
      matrixStackIn.m_85836_();
      this.m_276777_(matrixStackIn, -blockIn.m_276903_(stateIn), stateIn);
      this.m_278784_(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, woodTypeIn, modelIn);
      this.m_278841_(tileEntityIn.aD_(), tileEntityIn.m_277142_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), true);
      this.m_278841_(tileEntityIn.aD_(), tileEntityIn.m_277159_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), false);
      matrixStackIn.m_85849_();
   }

   void m_276777_(C_3181_ matrixStackIn, float rotationIn, C_2064_ stateIn) {
      matrixStackIn.m_252880_(0.5F, 0.75F * this.m_278770_(), 0.5F);
      matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(rotationIn));
      if (!(stateIn.m_60734_() instanceof C_1922_)) {
         matrixStackIn.m_252880_(0.0F, -0.3125F, -0.4375F);
      }

   }

   void m_278784_(C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int combinedOverlayIn, C_2106_ woodTypeIn, C_3840_ modelIn) {
      matrixStackIn.m_85836_();
      float f = this.m_278770_();
      matrixStackIn.m_85841_(f, -f, -f);
      C_4531_ material = this.m_245629_(woodTypeIn);
      Objects.requireNonNull(modelIn);
      C_3187_ vertexconsumer = material.m_119194_(bufferIn, modelIn::m_103119_);
      this.m_245885_(matrixStackIn, combinedLightIn, combinedOverlayIn, modelIn, vertexconsumer);
      matrixStackIn.m_85849_();
   }

   void m_245885_(C_3181_ matrixStackIn, int packedLightIn, int packedOverlayIn, C_3840_ modelIn, C_3187_ bufferIn) {
      C_4254_ signrenderer$signmodel = (C_4254_)modelIn;
      signrenderer$signmodel.f_173655_.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }

   C_4531_ m_245629_(C_2106_ typeIn) {
      return C_4177_.m_173381_(typeIn);
   }

   void m_278841_(C_4675_ posIn, C_276381_ textIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int combinedLightIn, int lineHeight, int lineWidth, boolean frontIn) {
      if (isRenderText(posIn)) {
         matrixStackIn.m_85836_();
         this.m_278823_(matrixStackIn, frontIn, this.m_278725_());
         int i = m_173639_(textIn);
         int j = 4 * lineHeight / 2;
         C_178_[] aformattedcharsequence = textIn.m_277130_(C_3391_.m_91087_().m_167974_(), (componentIn) -> {
            List list = this.f_173633_.m_92923_(componentIn, lineWidth);
            return list.isEmpty() ? C_178_.f_13691_ : (C_178_)list.get(0);
         });
         int k;
         boolean flag;
         int l;
         if (textIn.m_276843_()) {
            k = textIn.m_276773_().m_41071_();
            if (Config.isCustomColors()) {
               k = CustomColors.getSignTextColor(k);
            }

            flag = m_277119_(posIn, k);
            l = 15728880;
         } else {
            k = i;
            flag = false;
            l = combinedLightIn;
         }

         for(int i1 = 0; i1 < 4; ++i1) {
            C_178_ formattedcharsequence = aformattedcharsequence[i1];
            float f = (float)(-this.f_173633_.m_92724_(formattedcharsequence) / 2);
            if (flag) {
               this.f_173633_.m_168645_(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, i, matrixStackIn.m_85850_().m_252922_(), bufferIn, l);
            } else {
               this.f_173633_.m_272191_(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, false, matrixStackIn.m_85850_().m_252922_(), bufferIn, C_3429_.C_180532_.POLYGON_OFFSET, 0, l);
            }
         }

         matrixStackIn.m_85849_();
      }
   }

   private void m_278823_(C_3181_ matrixStackIn, boolean frontIn, C_3046_ offsetIn) {
      if (!frontIn) {
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(180.0F));
      }

      float f = 0.015625F * this.m_278631_();
      matrixStackIn.m_85837_(offsetIn.f_82479_, offsetIn.f_82480_, offsetIn.f_82481_);
      matrixStackIn.m_85841_(f, -f, f);
   }

   C_3046_ m_278725_() {
      return f_278459_;
   }

   static boolean m_277119_(C_4675_ posIn, int colorIn) {
      if (colorIn == C_1353_.BLACK.m_41071_()) {
         return true;
      } else {
         C_3391_ minecraft = C_3391_.m_91087_();
         C_4105_ localplayer = minecraft.f_91074_;
         if (localplayer != null && minecraft.f_91066_.m_92176_().m_90612_() && localplayer.gw()) {
            return true;
         } else {
            C_507_ entity = minecraft.m_91288_();
            return entity != null && entity.m_20238_(C_3046_.m_82512_(posIn)) < (double)f_173631_;
         }
      }
   }

   public static int m_173639_(C_276381_ entityIn) {
      int i = entityIn.m_276773_().m_41071_();
      if (Config.isCustomColors()) {
         i = CustomColors.getSignTextColor(i);
      }

      if (i == C_1353_.BLACK.m_41071_() && entityIn.m_276843_()) {
         return -988212;
      } else {
         double d0 = 0.4;
         int j = (int)((double)C_175_.m_13665_(i) * 0.4);
         int k = (int)((double)C_175_.m_13667_(i) * 0.4);
         int l = (int)((double)C_175_.m_13669_(i) * 0.4);
         return C_175_.m_13660_(0, j, k, l);
      }
   }

   public static C_4254_ m_173646_(C_141653_ modelSetIn, C_2106_ woodTypeIn) {
      return new C_4254_(modelSetIn.m_171103_(C_141656_.m_171291_(woodTypeIn)));
   }

   public static C_141663_ m_173654_() {
      C_141665_ meshdefinition = new C_141665_();
      C_141666_ partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171599_("sign", C_141662_.m_171558_().m_171514_(0, 0).m_171481_(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), C_141659_.f_171404_);
      partdefinition.m_171599_("stick", C_141662_.m_171558_().m_171514_(0, 14).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), C_141659_.f_171404_);
      return C_141663_.m_171565_(meshdefinition, 64, 32);
   }

   private static boolean isRenderText(C_4675_ tileEntityPos) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            C_507_ viewEntity = C_3391_.m_91087_().m_91288_();
            double distSq = viewEntity.m_20275_((double)tileEntityPos.u(), (double)tileEntityPos.v(), (double)tileEntityPos.w());
            if (distSq > textRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateTextRenderDistance() {
      C_3391_ mc = C_3391_.m_91087_();
      double fov = (double)Config.limit((Integer)mc.f_91066_.m_231837_().m_231551_(), 1, 120);
      double textRenderDistance = Math.max(1.5 * (double)mc.m_91268_().m_85444_() / fov, 16.0);
      textRenderDistanceSq = textRenderDistance * textRenderDistance;
   }

   public static final class C_4254_ extends C_3840_ {
      public final C_3889_ f_173655_;
      public final C_3889_ f_112507_;

      public C_4254_(C_3889_ partIn) {
         super(C_4168_::m_110458_);
         this.f_173655_ = partIn;
         this.f_112507_ = partIn.m_171324_("stick");
      }

      public void m_7695_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         this.f_173655_.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
      }
   }
}
