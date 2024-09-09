import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3814_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_990_;

public class DrownedOuterLayer<T extends C_990_> extends RenderLayer<T, C_3814_<T>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/zombie/drowned_outer_layer.png");
   public C_3814_<T> b;
   public ResourceLocation customTextureLocation;

   public DrownedOuterLayer(C_4382_<T, C_3814_<T>> p_i174489_1_, C_141653_ p_i174489_2_) {
      super(p_i174489_1_);
      this.b = new C_3814_(p_i174489_2_.a(C_141656_.f_171139_));
   }

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
      ResourceLocation textureLocation = this.customTextureLocation != null ? this.customTextureLocation : a;
      a(
         this.c(),
         this.b,
         textureLocation,
         matrixStackIn,
         bufferIn,
         packedLightIn,
         entitylivingbaseIn,
         limbSwing,
         limbSwingAmount,
         ageInTicks,
         netHeadYaw,
         headPitch,
         partialTicks,
         -1
      );
   }
}
