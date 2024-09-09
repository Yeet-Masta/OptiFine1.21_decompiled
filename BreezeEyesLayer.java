import net.minecraft.src.C_301969_;
import net.minecraft.src.C_302054_;
import net.minecraft.src.C_302185_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;

public class BreezeEyesLayer extends RenderLayer<C_301969_, C_302185_<C_301969_>> {
   private RenderType a = RenderType.q(ResourceLocation.b("textures/entity/breeze/breeze_eyes.png"));
   private C_302185_<C_301969_> customModel;

   public BreezeEyesLayer(C_4382_<C_301969_, C_302185_<C_301969_>> p_i306560_1_) {
      super(p_i306560_1_);
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_301969_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      VertexConsumer vertexconsumer = bufferIn.getBuffer(this.a);
      C_302185_<C_301969_> breezemodel = this.getEntityModel();
      C_302054_.a(breezemodel, new ModelPart[]{breezemodel.b(), breezemodel.c()}).a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   public void setModel(C_302185_<C_301969_> model) {
      this.customModel = model;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.a = RenderType.q(textureLocation);
   }

   public C_302185_<C_301969_> getEntityModel() {
      return this.customModel != null ? this.customModel : (C_302185_)super.c();
   }
}
