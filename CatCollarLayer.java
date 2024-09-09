import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3805_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_819_;
import net.optifine.Config;
import net.optifine.CustomColors;

public class CatCollarLayer extends RenderLayer<C_819_, C_3805_<C_819_>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/cat/cat_collar.png");
   public C_3805_<C_819_> b;

   public CatCollarLayer(C_4382_<C_819_, C_3805_<C_819_>> parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.b = new C_3805_(modelSetIn.a(C_141656_.f_171273_));
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_819_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (entitylivingbaseIn.m_21824_()) {
         int i = entitylivingbaseIn.gx().d();
         if (Config.isCustomColors()) {
            i = CustomColors.getWolfCollarColors(entitylivingbaseIn.gx(), i);
         }

         a(
            this.c(),
            this.b,
            a,
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
            i
         );
      }
   }
}
