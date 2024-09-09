import net.minecraft.src.C_1144_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_3853_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_516_;
import net.optifine.Config;

public class CapeLayer extends RenderLayer<AbstractClientPlayer, C_3853_<AbstractClientPlayer>> {
   public CapeLayer(C_4382_<AbstractClientPlayer, C_3853_<AbstractClientPlayer>> playerModelIn) {
      super(playerModelIn);
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      AbstractClientPlayer entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.m_20145_() && entitylivingbaseIn.m_36170_(C_1144_.CAPE)) {
         PlayerSkin playerskin = entitylivingbaseIn.b();
         if (entitylivingbaseIn.getLocationCape() != null) {
            C_1391_ itemstack = entitylivingbaseIn.m_6844_(C_516_.CHEST);
            if (!itemstack.m_150930_(C_1394_.f_42741_)) {
               matrixStackIn.a();
               matrixStackIn.a(0.0F, 0.0F, 0.125F);
               double d0 = Mth.d((double)partialTicks, entitylivingbaseIn.f_36102_, entitylivingbaseIn.f_36105_)
                  - Mth.d((double)partialTicks, entitylivingbaseIn.f_19854_, entitylivingbaseIn.m_20185_());
               double d1 = Mth.d((double)partialTicks, entitylivingbaseIn.f_36103_, entitylivingbaseIn.f_36106_)
                  - Mth.d((double)partialTicks, entitylivingbaseIn.f_19855_, entitylivingbaseIn.m_20186_());
               double d2 = Mth.d((double)partialTicks, entitylivingbaseIn.f_36104_, entitylivingbaseIn.f_36075_)
                  - Mth.d((double)partialTicks, entitylivingbaseIn.f_19856_, entitylivingbaseIn.m_20189_());
               float f = Mth.j(partialTicks, entitylivingbaseIn.f_20884_, entitylivingbaseIn.f_20883_);
               double d3 = (double)Mth.a(f * (float) (Math.PI / 180.0));
               double d4 = (double)(-Mth.b(f * (float) (Math.PI / 180.0)));
               float f1 = (float)d1 * 10.0F;
               f1 = Mth.a(f1, -6.0F, 32.0F);
               float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
               f2 = Mth.a(f2, 0.0F, 150.0F);
               float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
               f3 = Mth.a(f3, -20.0F, 20.0F);
               if (f2 < 0.0F) {
                  f2 = 0.0F;
               }

               if (f2 > 165.0F) {
                  f2 = 165.0F;
               }

               if (f1 < -5.0F) {
                  f1 = -5.0F;
               }

               float f4 = Mth.i(partialTicks, entitylivingbaseIn.f_20953_, entitylivingbaseIn.f_36100_);
               f1 += Mth.a(Mth.i(partialTicks, entitylivingbaseIn.f_19867_, entitylivingbaseIn.f_19787_) * 6.0F) * 32.0F * f4;
               if (entitylivingbaseIn.m_6047_()) {
                  f1 += 25.0F;
               }

               float pct = Config.getAverageFrameTimeSec() * 20.0F;
               pct = Config.limit(pct, 0.02F, 1.0F);
               entitylivingbaseIn.capeRotateX = Mth.i(pct, entitylivingbaseIn.capeRotateX, 6.0F + f2 / 2.0F + f1);
               entitylivingbaseIn.capeRotateZ = Mth.i(pct, entitylivingbaseIn.capeRotateZ, f3 / 2.0F);
               entitylivingbaseIn.capeRotateY = Mth.i(pct, entitylivingbaseIn.capeRotateY, 180.0F - f3 / 2.0F);
               matrixStackIn.a(C_252363_.f_252529_.m_252977_(entitylivingbaseIn.capeRotateX));
               matrixStackIn.a(C_252363_.f_252403_.m_252977_(entitylivingbaseIn.capeRotateZ));
               matrixStackIn.a(C_252363_.f_252436_.m_252977_(entitylivingbaseIn.capeRotateY));
               VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.c(entitylivingbaseIn.getLocationCape()));
               this.c().c(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
               matrixStackIn.b();
            }
         }
      }
   }
}
