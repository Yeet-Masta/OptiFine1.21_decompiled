import net.minecraft.src.C_2033_;
import net.minecraft.src.C_141731_.C_141732_;
import net.optifine.Config;
import net.optifine.shaders.ShadersRender;
import org.joml.Matrix4f;

public class TheEndPortalRenderer<T extends C_2033_> implements BlockEntityRenderer<T> {
   public static final ResourceLocation a = ResourceLocation.b("textures/environment/end_sky.png");
   public static final ResourceLocation b = ResourceLocation.b("textures/entity/end_portal.png");

   public TheEndPortalRenderer(C_141732_ contextIn) {
   }

   public void a(T tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (!Config.isShaders()
         || !ShadersRender.renderEndPortal(tileEntityIn, partialTicks, this.b(), matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn)) {
         Matrix4f matrix4f = matrixStackIn.c().a();
         this.a(tileEntityIn, matrix4f, bufferIn.getBuffer(this.d()));
      }
   }

   private void a(T blockEntityIn, Matrix4f matrixIn, VertexConsumer vertexConsumerIn) {
      float f = this.c();
      float f1 = this.b();
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, Direction.d);
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, Direction.c);
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 1.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.f);
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 1.0F, 1.0F, 0.0F, Direction.e);
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f, f, 0.0F, 0.0F, 1.0F, 1.0F, Direction.a);
      this.a(blockEntityIn, matrixIn, vertexConsumerIn, 0.0F, 1.0F, f1, f1, 1.0F, 1.0F, 0.0F, 0.0F, Direction.b);
   }

   private void a(
      T blockEntityIn,
      Matrix4f matrixIn,
      VertexConsumer vertexConsumerIn,
      float x1,
      float x2,
      float y1,
      float y2,
      float z1,
      float z2,
      float z3,
      float z4,
      Direction p_252771_12_
   ) {
      if (blockEntityIn.a(p_252771_12_)) {
         vertexConsumerIn.a(matrixIn, x1, y1, z1);
         vertexConsumerIn.a(matrixIn, x2, y1, z2);
         vertexConsumerIn.a(matrixIn, x2, y2, z3);
         vertexConsumerIn.a(matrixIn, x1, y2, z4);
      }
   }

   protected float b() {
      return 0.75F;
   }

   protected float c() {
      return 0.375F;
   }

   protected RenderType d() {
      return RenderType.u();
   }
}
