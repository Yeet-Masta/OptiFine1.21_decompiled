import net.minecraft.src.C_1042_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141655_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_3868_;
import net.minecraft.src.C_4382_;

public class SkeletonClothingLayer<T extends Mob & C_1042_, M extends C_3819_<T>> extends RenderLayer<T, M> {
   public C_3868_<T> a;
   public ResourceLocation b;

   public SkeletonClothingLayer(C_4382_<T, M> p_i323834_1_, C_141653_ p_i323834_2_, C_141655_ p_i323834_3_, ResourceLocation p_i323834_4_) {
      super(p_i323834_1_);
      this.b = p_i323834_4_;
      this.a = new C_3868_(p_i323834_2_.a(p_i323834_3_));
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
      a(
         this.c(),
         this.a,
         this.b,
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
