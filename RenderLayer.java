import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_524_;

public abstract class RenderLayer<T extends C_507_, M extends C_3819_<T>> {
   private final C_4382_<T, M> a;
   public boolean custom = false;

   public RenderLayer(C_4382_<T, M> entityRendererIn) {
      this.a = entityRendererIn;
   }

   protected static <T extends C_524_> void a(
      C_3819_<T> modelParentIn,
      C_3819_<T> modelIn,
      ResourceLocation textureLocationIn,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      T entityIn,
      float limbSwing,
      float limbSwingAmount,
      float ageInTicks,
      float netHeadYaw,
      float headPitch,
      float partialTicks,
      int colorIn
   ) {
      if (!entityIn.m_20145_()) {
         modelParentIn.m_102624_(modelIn);
         modelIn.m_6839_(entityIn, limbSwing, limbSwingAmount, partialTicks);
         modelIn.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         a(modelIn, textureLocationIn, matrixStackIn, bufferIn, packedLightIn, entityIn, colorIn);
      }
   }

   protected static <T extends C_524_> void a(
      C_3819_<T> modelIn, ResourceLocation textureLocationIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, T entityIn, int colorIn
   ) {
      if (modelIn.locationTextureCustom != null) {
         textureLocationIn = modelIn.locationTextureCustom;
      }

      VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.e(textureLocationIn));
      modelIn.a(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.c(entityIn, 0.0F), colorIn);
   }

   public M c() {
      return (M)this.a.m_7200_();
   }

   protected ResourceLocation a(T entityIn) {
      return this.a.a(entityIn);
   }

   public abstract void a(PoseStack var1, MultiBufferSource var2, int var3, T var4, float var5, float var6, float var7, float var8, float var9, float var10);
}
