import net.minecraft.src.C_1391_;
import net.minecraft.src.C_140_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_313678_;
import net.minecraft.src.C_313801_;
import net.minecraft.src.C_3827_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_928_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_313678_.C_313439_;

public class HorseArmorLayer extends RenderLayer<C_928_, C_3827_<C_928_>> {
   public C_3827_<C_928_> a;
   public ResourceLocation customTextureLocation;

   public HorseArmorLayer(C_4382_<C_928_, C_3827_<C_928_>> p_i174495_1_, C_141653_ p_i174495_2_) {
      super(p_i174495_1_);
      this.a = new C_3827_(p_i174495_2_.a(C_141656_.f_171187_));
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_928_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      C_1391_ itemstack = entitylivingbaseIn.fO();
      if (itemstack.m_41720_() instanceof C_313678_ animalarmoritem && animalarmoritem.m_319458_() == C_313439_.EQUESTRIAN) {
         this.c().m_102624_(this.a);
         this.a.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
         this.a.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
         int i;
         if (itemstack.m_204117_(C_140_.f_314020_)) {
            i = C_175_.m_321570_(C_313801_.m_322889_(itemstack, -6265536));
         } else {
            i = -1;
         }

         ResourceLocation armorTexture = this.customTextureLocation != null ? this.customTextureLocation : animalarmoritem.b();
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.e(armorTexture));
         this.a.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_, i);
         return;
      }
   }
}
