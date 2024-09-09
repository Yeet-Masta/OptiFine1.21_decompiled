import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3870_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_524_;

public class SlimeOuterLayer<T extends C_524_> extends RenderLayer<T, C_3870_<T>> {
   public C_3819_<T> a;
   public ResourceLocation customTextureLocation;

   public SlimeOuterLayer(C_4382_<T, C_3870_<T>> p_i174535_1_, C_141653_ p_i174535_2_) {
      super(p_i174535_1_);
      this.a = new C_3870_(p_i174535_2_.a(C_141656_.f_171242_));
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      C_3391_ minecraft = C_3391_.m_91087_();
      boolean flag = minecraft.m_91314_(entitylivingbaseIn) && entitylivingbaseIn.m_20145_();
      if (!entitylivingbaseIn.m_20145_() || flag) {
         ResourceLocation textureLocation = this.customTextureLocation != null ? this.customTextureLocation : this.a(entitylivingbaseIn);
         VertexConsumer vertexconsumer;
         if (flag) {
            vertexconsumer = bufferIn.getBuffer(RenderType.r(textureLocation));
         } else {
            vertexconsumer = bufferIn.getBuffer(RenderType.i(textureLocation));
         }

         this.c().m_102624_(this.a);
         this.a.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.a.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         this.a.a(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.c(entitylivingbaseIn, 0.0F));
      }
   }
}
