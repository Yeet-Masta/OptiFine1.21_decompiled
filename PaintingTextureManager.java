import net.minecraft.src.C_213053_;
import net.minecraft.src.C_4510_;
import net.optifine.util.TextureUtils;

public class PaintingTextureManager extends C_4510_ {
   private static final ResourceLocation a = ResourceLocation.b("back");

   public PaintingTextureManager(TextureManager textureManagerIn) {
      super(textureManagerIn, ResourceLocation.b("textures/atlas/paintings.png"), ResourceLocation.b("paintings"));
   }

   public TextureAtlasSprite a(C_213053_ variantIn) {
      TextureAtlasSprite sprite = this.a(variantIn.d());
      return TextureUtils.getCustomSprite(sprite);
   }

   public TextureAtlasSprite a() {
      return this.a(a);
   }
}
