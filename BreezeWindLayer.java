import net.minecraft.src.C_141656_;
import net.minecraft.src.C_301969_;
import net.minecraft.src.C_302054_;
import net.minecraft.src.C_302185_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_141742_.C_141743_;

public class BreezeWindLayer extends RenderLayer<C_301969_, C_302185_<C_301969_>> {
   private ResourceLocation a = ResourceLocation.b("textures/entity/breeze/breeze_wind.png");
   private C_302185_<C_301969_> b;

   public BreezeWindLayer(C_141743_ p_i307292_1_, C_4382_<C_301969_, C_302185_<C_301969_>> p_i307292_2_) {
      super(p_i307292_2_);
      this.b = new C_302185_(p_i307292_1_.a(C_141656_.f_337505_));
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
      float f = (float)entitylivingbaseIn.ai + partialTicks;
      VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.a(this.a, this.a(f) % 1.0F, 0.0F));
      this.b.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      C_302054_.a(this.b, new ModelPart[]{this.b.e()}).a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }

   private float a(float p_306824_1_) {
      return p_306824_1_ * 0.02F;
   }

   public void setModel(C_302185_<C_301969_> model) {
      this.b = model;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.a = textureLocation;
   }
}
