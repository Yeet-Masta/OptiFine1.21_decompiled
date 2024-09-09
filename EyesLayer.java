import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_507_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public abstract class EyesLayer<T extends C_507_, M extends C_3819_<T>> extends RenderLayer<T, M> {
   public EyesLayer(C_4382_<T, M> p_i116980_1_) {
      super(p_i116980_1_);
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
      VertexConsumer vertexconsumer = bufferIn.getBuffer(this.a());
      if (Config.isShaders()) {
         Shaders.beginSpiderEyes();
      }

      Config.getRenderGlobal().renderOverlayEyes = true;
      this.c().a(matrixStackIn, vertexconsumer, 15728640, C_4474_.f_118083_);
      Config.getRenderGlobal().renderOverlayEyes = false;
      if (Config.isShaders()) {
         Shaders.endSpiderEyes();
      }
   }

   public abstract RenderType a();
}
