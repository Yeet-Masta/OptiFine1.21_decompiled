import net.minecraft.src.C_141653_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3837_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_930_;

public class LlamaDecorLayer extends RenderLayer<C_930_, C_3837_<C_930_>> {
   private static final ResourceLocation[] a = new ResourceLocation[]{
      ResourceLocation.b("textures/entity/llama/decor/white.png"),
      ResourceLocation.b("textures/entity/llama/decor/orange.png"),
      ResourceLocation.b("textures/entity/llama/decor/magenta.png"),
      ResourceLocation.b("textures/entity/llama/decor/light_blue.png"),
      ResourceLocation.b("textures/entity/llama/decor/yellow.png"),
      ResourceLocation.b("textures/entity/llama/decor/lime.png"),
      ResourceLocation.b("textures/entity/llama/decor/pink.png"),
      ResourceLocation.b("textures/entity/llama/decor/gray.png"),
      ResourceLocation.b("textures/entity/llama/decor/light_gray.png"),
      ResourceLocation.b("textures/entity/llama/decor/cyan.png"),
      ResourceLocation.b("textures/entity/llama/decor/purple.png"),
      ResourceLocation.b("textures/entity/llama/decor/blue.png"),
      ResourceLocation.b("textures/entity/llama/decor/brown.png"),
      ResourceLocation.b("textures/entity/llama/decor/green.png"),
      ResourceLocation.b("textures/entity/llama/decor/red.png"),
      ResourceLocation.b("textures/entity/llama/decor/black.png")
   };
   private static final ResourceLocation b = ResourceLocation.b("textures/entity/llama/decor/trader_llama.png");
   private final C_3837_<C_930_> c;

   public LlamaDecorLayer(C_4382_<C_930_, C_3837_<C_930_>> p_i174498_1_, C_141653_ p_i174498_2_) {
      super(p_i174498_1_);
      this.c = new C_3837_(p_i174498_2_.a(C_141656_.f_171195_));
   }

   public void a(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      C_930_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      DyeColor dyecolor = entitylivingbaseIn.gw();
      ResourceLocation resourcelocation;
      if (dyecolor != null) {
         resourcelocation = a[dyecolor.a()];
      } else {
         if (!entitylivingbaseIn.m_7565_()) {
            return;
         }

         resourcelocation = b;
      }

      if (this.c.locationTextureCustom != null) {
         resourcelocation = this.c.locationTextureCustom;
      }

      this.c().m_102624_(this.c);
      this.c.m_6973_(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.e(resourcelocation));
      this.c.a(matrixStackIn, vertexconsumer, packedLightIn, C_4474_.f_118083_);
   }
}
