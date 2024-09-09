import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_518_;
import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.CustomColors;

public class ExperienceOrbRenderer extends EntityRenderer<C_518_> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/experience_orb.png");
   private static final RenderType g = RenderType.g(a);

   public ExperienceOrbRenderer(C_141743_ contextIn) {
      super(contextIn);
      this.e = 0.15F;
      this.f = 0.75F;
   }

   protected int a(C_518_ entityIn, C_4675_ partialTicks) {
      return Mth.a(super.a(entityIn, partialTicks) + 7, 0, 15);
   }

   public void a(C_518_ entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.a();
      int i = entityIn.m_20802_();
      float f = (float)(i % 4 * 16 + 0) / 64.0F;
      float f1 = (float)(i % 4 * 16 + 16) / 64.0F;
      float f2 = (float)(i / 4 * 16 + 0) / 64.0F;
      float f3 = (float)(i / 4 * 16 + 16) / 64.0F;
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      float f7 = 255.0F;
      float f8 = ((float)entityIn.f_19797_ + partialTicks) / 2.0F;
      if (Config.isCustomColors()) {
         f8 = CustomColors.getXpOrbTimer(f8);
      }

      int j = (int)((Mth.a(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int k = 255;
      int l = (int)((Mth.a(f8 + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      matrixStackIn.a(0.0F, 0.1F, 0.0F);
      matrixStackIn.a(this.d.b());
      float f9 = 0.3F;
      matrixStackIn.b(0.3F, 0.3F, 0.3F);
      VertexConsumer vertexconsumer = bufferIn.getBuffer(g);
      PoseStack.a posestack$pose = matrixStackIn.c();
      int red = j;
      int green = 255;
      int blue = l;
      if (Config.isCustomColors()) {
         int col = CustomColors.getXpOrbColor(f8);
         if (col >= 0) {
            red = col >> 16 & 0xFF;
            green = col >> 8 & 0xFF;
            blue = col >> 0 & 0xFF;
         }
      }

      a(vertexconsumer, posestack$pose, -0.5F, -0.25F, red, green, blue, f, f3, packedLightIn);
      a(vertexconsumer, posestack$pose, 0.5F, -0.25F, red, green, blue, f1, f3, packedLightIn);
      a(vertexconsumer, posestack$pose, 0.5F, 0.75F, red, green, blue, f1, f2, packedLightIn);
      a(vertexconsumer, posestack$pose, -0.5F, 0.75F, red, green, blue, f, f2, packedLightIn);
      matrixStackIn.b();
      super.a(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private static void a(
      VertexConsumer bufferIn, PoseStack.a matrixStackIn, float x, float y, int red, int green, int blue, float texU, float texV, int packedLight
   ) {
      bufferIn.a(matrixStackIn, x, y, 0.0F).a(red, green, blue, 128).a(texU, texV).b(C_4474_.f_118083_).c(packedLight).b(matrixStackIn, 0.0F, 1.0F, 0.0F);
   }

   public ResourceLocation a(C_518_ entity) {
      return a;
   }
}
