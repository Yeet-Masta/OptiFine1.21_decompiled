import com.google.common.collect.ImmutableList;
import javax.annotation.Nullable;
import net.minecraft.src.C_212950_;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4517_;
import net.minecraft.src.C_212950_.C_290050_;

public final class MissingTextureAtlasSprite {
   private static final int a = 16;
   private static final int b = 16;
   private static final String c = "missingno";
   private static final ResourceLocation d = ResourceLocation.b("missingno");
   private static final C_212950_ e = new C_290050_()
      .m_294003_(AnimationMetadataSection.a, new AnimationMetadataSection(ImmutableList.of(new C_4517_(0, -1)), 16, 16, 1, false))
      .m_293106_();
   @Nullable
   private static DynamicTexture f;

   private static NativeImage a(int widthIn, int heightIn) {
      NativeImage nativeimage = new NativeImage(widthIn, heightIn, false);
      int i = -16777216;
      int j = -524040;

      for (int k = 0; k < heightIn; k++) {
         for (int l = 0; l < widthIn; l++) {
            if (k < heightIn / 2 ^ l < widthIn / 2) {
               nativeimage.a(l, k, -524040);
            } else {
               nativeimage.a(l, k, -16777216);
            }
         }
      }

      return nativeimage;
   }

   public static SpriteContents a() {
      NativeImage nativeimage = a(16, 16);
      return new SpriteContents(d, new C_243504_(16, 16), nativeimage, e);
   }

   public static ResourceLocation b() {
      return d;
   }

   public static DynamicTexture c() {
      if (f == null) {
         NativeImage nativeimage = a(16, 16);
         nativeimage.i();
         f = new DynamicTexture(nativeimage);
         C_3391_.m_91087_().aa().a(d, f);
      }

      return f;
   }

   public static boolean isMisingSprite(TextureAtlasSprite sprite) {
      return sprite.getName() == d;
   }
}
