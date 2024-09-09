import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_141659_;
import net.minecraft.src.C_141662_;
import net.minecraft.src.C_141663_;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_1895_;
import net.minecraft.src.C_1922_;
import net.minecraft.src.C_2024_;
import net.minecraft.src.C_2106_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_276381_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_141731_.C_141732_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;

public class SignRenderer implements BlockEntityRenderer<C_2024_> {
   private static final String a = "stick";
   private static final int b = -988212;
   private static final int c = Mth.h(16);
   private static final float d = 0.6666667F;
   private static final Vec3 e = new Vec3(0.0, 0.33333334F, 0.046666667F);
   private final Map<C_2106_, SignRenderer.a> f;
   private final Font g;
   private static double textRenderDistanceSq = 4096.0;

   public SignRenderer(C_141732_ contextIn) {
      this.f = (Map<C_2106_, SignRenderer.a>)C_2106_.m_61843_()
         .collect(ImmutableMap.toImmutableMap(woodTypeIn -> woodTypeIn, woodTypeIn -> new SignRenderer.a(contextIn.a(C_141656_.m_171291_(woodTypeIn)))));
      this.g = contextIn.f();
   }

   public void a(C_2024_ tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      BlockState blockstate = tileEntityIn.n();
      C_1895_ signblock = (C_1895_)blockstate.m_60734_();
      C_2106_ woodtype = C_1895_.m_247329_(signblock);
      SignRenderer.a signrenderer$signmodel = (SignRenderer.a)this.f.get(woodtype);
      signrenderer$signmodel.b.k = blockstate.m_60734_() instanceof C_1922_;
      this.a(tileEntityIn, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, blockstate, signblock, woodtype, signrenderer$signmodel);
   }

   public float b() {
      return 0.6666667F;
   }

   public float c() {
      return 0.6666667F;
   }

   void a(
      C_2024_ tileEntityIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int combinedLightIn,
      int combinedOverlayIn,
      BlockState stateIn,
      C_1895_ blockIn,
      C_2106_ woodTypeIn,
      Model modelIn
   ) {
      matrixStackIn.a();
      this.a(matrixStackIn, -blockIn.g(stateIn), stateIn);
      this.a(matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, woodTypeIn, modelIn);
      this.a(tileEntityIn.aD_(), tileEntityIn.m_277142_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), true);
      this.a(tileEntityIn.aD_(), tileEntityIn.m_277159_(), matrixStackIn, bufferIn, combinedLightIn, tileEntityIn.m_245065_(), tileEntityIn.m_245123_(), false);
      matrixStackIn.b();
   }

   void a(PoseStack matrixStackIn, float rotationIn, BlockState stateIn) {
      matrixStackIn.a(0.5F, 0.75F * this.b(), 0.5F);
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(rotationIn));
      if (!(stateIn.m_60734_() instanceof C_1922_)) {
         matrixStackIn.a(0.0F, -0.3125F, -0.4375F);
      }
   }

   void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, C_2106_ woodTypeIn, Model modelIn) {
      matrixStackIn.a();
      float f = this.b();
      matrixStackIn.b(f, -f, -f);
      Material material = this.a(woodTypeIn);
      VertexConsumer vertexconsumer = material.a(bufferIn, modelIn::a);
      this.a(matrixStackIn, combinedLightIn, combinedOverlayIn, modelIn, vertexconsumer);
      matrixStackIn.b();
   }

   void a(PoseStack matrixStackIn, int packedLightIn, int packedOverlayIn, Model modelIn, VertexConsumer bufferIn) {
      SignRenderer.a signrenderer$signmodel = (SignRenderer.a)modelIn;
      signrenderer$signmodel.a.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }

   Material a(C_2106_ typeIn) {
      return C_4177_.a(typeIn);
   }

   void a(
      C_4675_ posIn, C_276381_ textIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int lineHeight, int lineWidth, boolean frontIn
   ) {
      if (isRenderText(posIn)) {
         matrixStackIn.a();
         this.a(matrixStackIn, frontIn, this.d());
         int i = a(textIn);
         int j = 4 * lineHeight / 2;
         C_178_[] aformattedcharsequence = textIn.m_277130_(C_3391_.m_91087_().m_167974_(), componentIn -> {
            List<C_178_> list = this.g.c(componentIn, lineWidth);
            return list.isEmpty() ? C_178_.f_13691_ : (C_178_)list.get(0);
         });
         int k;
         boolean flag;
         int l;
         if (textIn.m_276843_()) {
            k = textIn.b().g();
            if (Config.isCustomColors()) {
               k = CustomColors.getSignTextColor(k);
            }

            flag = a(posIn, k);
            l = 15728880;
         } else {
            k = i;
            flag = false;
            l = combinedLightIn;
         }

         for (int i1 = 0; i1 < 4; i1++) {
            C_178_ formattedcharsequence = aformattedcharsequence[i1];
            float f = (float)(-this.g.a(formattedcharsequence) / 2);
            if (flag) {
               this.g.a(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, i, matrixStackIn.c().a(), bufferIn, l);
            } else {
               this.g.a(formattedcharsequence, f, (float)(i1 * lineHeight - j), k, false, matrixStackIn.c().a(), bufferIn, Font.a.c, 0, l);
            }
         }

         matrixStackIn.b();
      }
   }

   private void a(PoseStack matrixStackIn, boolean frontIn, Vec3 offsetIn) {
      if (!frontIn) {
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(180.0F));
      }

      float f = 0.015625F * this.c();
      matrixStackIn.a(offsetIn.c, offsetIn.d, offsetIn.e);
      matrixStackIn.b(f, -f, f);
   }

   Vec3 d() {
      return e;
   }

   static boolean a(C_4675_ posIn, int colorIn) {
      if (colorIn == DyeColor.p.g()) {
         return true;
      } else {
         C_3391_ minecraft = C_3391_.m_91087_();
         C_4105_ localplayer = minecraft.f_91074_;
         if (localplayer != null && minecraft.m.aB().m_90612_() && localplayer.gw()) {
            return true;
         } else {
            C_507_ entity = minecraft.m_91288_();
            return entity != null && entity.g(Vec3.b(posIn)) < (double)c;
         }
      }
   }

   public static int a(C_276381_ entityIn) {
      int i = entityIn.b().g();
      if (Config.isCustomColors()) {
         i = CustomColors.getSignTextColor(i);
      }

      if (i == DyeColor.p.g() && entityIn.m_276843_()) {
         return -988212;
      } else {
         double d0 = 0.4;
         int j = (int)((double)C_175_.m_13665_(i) * 0.4);
         int k = (int)((double)C_175_.m_13667_(i) * 0.4);
         int l = (int)((double)C_175_.m_13669_(i) * 0.4);
         return C_175_.m_13660_(0, j, k, l);
      }
   }

   public static SignRenderer.a a(C_141653_ modelSetIn, C_2106_ woodTypeIn) {
      return new SignRenderer.a(modelSetIn.a(C_141656_.m_171291_(woodTypeIn)));
   }

   public static C_141663_ f() {
      MeshDefinition meshdefinition = new MeshDefinition();
      PartDefinition partdefinition = meshdefinition.a();
      partdefinition.a("sign", C_141662_.m_171558_().m_171514_(0, 0).m_171481_(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F), C_141659_.f_171404_);
      partdefinition.a("stick", C_141662_.m_171558_().m_171514_(0, 14).m_171481_(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F), C_141659_.f_171404_);
      return C_141663_.a(meshdefinition, 64, 32);
   }

   private static boolean isRenderText(C_4675_ tileEntityPos) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            C_507_ viewEntity = C_3391_.m_91087_().m_91288_();
            double distSq = viewEntity.m_20275_((double)tileEntityPos.m_123341_(), (double)tileEntityPos.m_123342_(), (double)tileEntityPos.m_123343_());
            if (distSq > textRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateTextRenderDistance() {
      C_3391_ mc = C_3391_.m_91087_();
      double fov = (double)Config.limit(mc.m.ah().c(), 1, 120);
      double textRenderDistance = Math.max(1.5 * (double)mc.aM().o() / fov, 16.0);
      textRenderDistanceSq = textRenderDistance * textRenderDistance;
   }

   public static final class a extends Model {
      public final ModelPart a;
      public final ModelPart b;

      public a(ModelPart partIn) {
         super(RenderType::e);
         this.a = partIn;
         this.b = partIn.b("stick");
      }

      @Override
      public void a(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
         this.a.a(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
      }
   }
}
