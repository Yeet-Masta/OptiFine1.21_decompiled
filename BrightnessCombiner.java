import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.src.C_1766_.C_1768_;
import net.optifine.EmissiveTextures;

public class BrightnessCombiner<S extends BlockEntity> implements C_1768_<S, Int2IntFunction> {
   public Int2IntFunction a(S blockEntity1, S blockEntity2) {
      return valIn -> {
         if (EmissiveTextures.isRenderEmissive()) {
            return LightTexture.MAX_BRIGHTNESS;
         } else {
            int i = LevelRenderer.a(blockEntity1.i(), blockEntity1.aD_());
            int j = LevelRenderer.a(blockEntity2.i(), blockEntity2.aD_());
            int k = LightTexture.a(i);
            int l = LightTexture.a(j);
            int i1 = LightTexture.b(i);
            int j1 = LightTexture.b(j);
            return LightTexture.a(Math.max(k, l), Math.max(i1, j1));
         }
      };
   }

   public Int2IntFunction a(S blockEntityIn) {
      return valIn -> valIn;
   }

   public Int2IntFunction a() {
      return valIn -> valIn;
   }
}
