import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3885_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_922_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.entity.model.ModelAdapter;

public class WolfCollarLayer extends RenderLayer<C_922_, C_3885_<C_922_>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/wolf/wolf_collar.png");
   public C_3885_<C_922_> model = new C_3885_(ModelAdapter.bakeModelLayer(C_141656_.f_171221_));

   public WolfCollarLayer(C_4382_<C_922_, C_3885_<C_922_>> rendererIn) {
      super(rendererIn);
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_922_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entitylivingbaseIn.m_21824_() && !entitylivingbaseIn.ci()) {
         int i = entitylivingbaseIn.gz().d();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.gz(), i);
         }

         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.e(a));
         this.c().a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, i);
      }
   }
}
