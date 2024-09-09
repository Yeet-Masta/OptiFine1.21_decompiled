import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3862_;
import net.minecraft.src.C_3863_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_896_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.Config;
import net.optifine.CustomColors;

public class SheepFurLayer extends RenderLayer<C_896_, C_3863_<C_896_>> {
   private static final ResourceLocation a = ResourceLocation.b("textures/entity/sheep/sheep_fur.png");
   public C_3862_<C_896_> b;

   public SheepFurLayer(C_4382_<C_896_, C_3863_<C_896_>> parentIn, C_141653_ modelSetIn) {
      super(parentIn);
      this.b = new C_3862_(modelSetIn.a(C_141656_.f_171178_));
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_896_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      if (!entitylivingbaseIn.m_29875_()) {
         if (entitylivingbaseIn.ci()) {
            C_3391_ minecraft = C_3391_.m_91087_();
            boolean flag = minecraft.m_91314_(entitylivingbaseIn);
            if (flag) {
               this.c().m_102624_(this.b);
               this.b.m_6839_(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
               this.b.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
               VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.r(a));
               this.b.a(matrixStackIn, vertexconsumer, packedLightIn, LivingEntityRenderer.c(entitylivingbaseIn, 0.0F), -16777216);
            }
         } else {
            int i;
            if (entitylivingbaseIn.ai() && "jeb_".equals(entitylivingbaseIn.ah().getString())) {
               int j = 25;
               int k = entitylivingbaseIn.ai / 25 + entitylivingbaseIn.an();
               int l = DyeColor.values().length;
               int i1 = k % l;
               int j1 = (k + 1) % l;
               float f = ((float)(entitylivingbaseIn.ai % 25) + partialTicks) / 25.0F;
               int k1 = C_896_.a(DyeColor.a(i1));
               int l1 = C_896_.a(DyeColor.a(j1));
               if (Config.isCustomColors()) {
                  k1 = CustomColors.getSheepColors(DyeColor.a(i1), k1);
                  l1 = CustomColors.getSheepColors(DyeColor.a(j1), l1);
               }

               i = C_175_.m_269105_(f, k1, l1);
            } else {
               i = C_896_.a(entitylivingbaseIn.t());
               if (Config.isCustomColors()) {
                  i = CustomColors.getSheepColors(entitylivingbaseIn.t(), i);
               }
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
}
