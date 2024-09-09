import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_540_;

public class SaddleLayer<T extends C_507_ & C_540_, M extends C_3819_<T>> extends RenderLayer<T, M> {
   public ResourceLocation a;
   public M b;

   public SaddleLayer(C_4382_<T, M> p_i117389_1_, M p_i117389_2_, ResourceLocation p_i117389_3_) {
      super(p_i117389_1_);
      this.b = p_i117389_2_;
      this.a = p_i117389_3_;
   }

   @Override
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
      if (entitylivingbaseIn.m_6254_()) {
         this.c().m_102624_(this.b);
         this.b.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.b.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.e(this.a));
         this.b.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
      }
   }
}
