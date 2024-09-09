import net.minecraft.src.C_1151_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.entity.model.ArrowModel;

public abstract class ArrowRenderer<T extends C_1151_> extends EntityRenderer<T> {
   public ArrowModel model;

   public ArrowRenderer(C_141743_ p_i173916_1_) {
      super(p_i173916_1_);
   }

   public void a(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.a();
      matrixStackIn.a(C_252363_.f_252436_.m_252977_(Mth.i(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.a(C_252363_.f_252403_.m_252977_(Mth.i(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      int i = 0;
      float f = 0.0F;
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = 0.15625F;
      float f4 = 0.0F;
      float f5 = 0.15625F;
      float f6 = 0.15625F;
      float f7 = 0.3125F;
      float f8 = 0.05625F;
      float f9 = (float)entityIn.f_36706_ - partialTicks;
      if (f9 > 0.0F) {
         float f10 = -Mth.a(f9 * 3.0F) * f9;
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(f10));
      }

      matrixStackIn.a(C_252363_.f_252529_.m_252977_(45.0F));
      matrixStackIn.b(0.05625F, 0.05625F, 0.05625F);
      matrixStackIn.a(-4.0F, 0.0F, 0.0F);
      RenderType renderType = RenderType.d(this.a(entityIn));
      if (this.model != null) {
         renderType = this.model.a(this.a(entityIn));
      }

      VertexConsumer vertexconsumer = bufferIn.getBuffer(renderType);
      PoseStack.a posestack$pose = matrixStackIn.c();
      if (this.model != null) {
         matrixStackIn.b(16.0F, 16.0F, 16.0F);
         this.model.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, -1);
      } else {
         this.a(posestack$pose, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, packedLightIn);
         this.a(posestack$pose, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, packedLightIn);

         for (int j = 0; j < 4; j++) {
            matrixStackIn.a(C_252363_.f_252529_.m_252977_(90.0F));
            this.a(posestack$pose, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, packedLightIn);
            this.a(posestack$pose, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, packedLightIn);
            this.a(posestack$pose, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, packedLightIn);
            this.a(posestack$pose, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, packedLightIn);
         }
      }

      matrixStackIn.b();
      super.a(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public void a(
      PoseStack.a matrixStack,
      VertexConsumer vertexBuilder,
      int offsetX,
      int offsetY,
      int offsetZ,
      float textureX,
      float textureY,
      int normalX,
      int normalZ,
      int normalY,
      int packedLightIn
   ) {
      vertexBuilder.a(matrixStack, (float)offsetX, (float)offsetY, (float)offsetZ)
         .a(-1)
         .a(textureX, textureY)
         .b(C_4474_.f_118083_)
         .c(packedLightIn)
         .b(matrixStack, (float)normalX, (float)normalY, (float)normalZ);
   }
}
