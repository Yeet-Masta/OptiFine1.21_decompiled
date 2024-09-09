import net.minecraft.src.C_1218_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_4362_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;

public class TntMinecartRenderer extends C_4362_<C_1218_> {
   private final BlockRenderDispatcher g;

   public TntMinecartRenderer(C_141743_ contextIn) {
      super(contextIn, C_141656_.f_171253_);
      this.g = contextIn.c();
   }

   protected void a(C_1218_ entityIn, float partialTicks, BlockState stateIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      int i = entityIn.m_38694_();
      if (i > -1 && (float)i - partialTicks + 1.0F < 10.0F) {
         float f = 1.0F - ((float)i - partialTicks + 1.0F) / 10.0F;
         f = Mth.a(f, 0.0F, 1.0F);
         f *= f;
         f *= f;
         float f1 = 1.0F + f * 0.3F;
         matrixStackIn.b(f1, f1, f1);
      }

      a(this.g, stateIn, matrixStackIn, bufferIn, packedLightIn, i > -1 && i / 5 % 2 == 0);
   }

   public static void a(
      BlockRenderDispatcher renderDispatcherIn,
      BlockState blockStateIn,
      PoseStack matrixStackIn,
      MultiBufferSource renderTypeBuffer,
      int combinedLight,
      boolean doFullBright
   ) {
      int i;
      if (doFullBright) {
         i = C_4474_.m_118093_(C_4474_.m_118088_(1.0F), 10);
      } else {
         i = C_4474_.f_118083_;
      }

      if (Config.isShaders() && doFullBright) {
         Shaders.setEntityColor(1.0F, 1.0F, 1.0F, 0.5F);
      }

      renderDispatcherIn.a(blockStateIn, matrixStackIn, renderTypeBuffer, combinedLight, i);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }
   }
}
