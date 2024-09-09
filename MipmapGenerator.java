import net.optifine.Mipmaps;
import net.optifine.texture.IColorBlender;

public class MipmapGenerator {
   private static final int a = 96;
   private static final float[] b = Util.a(new float[256], floatsIn -> {
      for (int i = 0; i < floatsIn.length; i++) {
         floatsIn[i] = (float)Math.pow((double)((float)i / 255.0F), 2.2);
      }
   });

   private MipmapGenerator() {
   }

   public static NativeImage[] a(NativeImage[] imageIn, int mipmapLevelsIn) {
      return generateMipLevels(imageIn, mipmapLevelsIn, null);
   }

   public static NativeImage[] generateMipLevels(NativeImage[] imageIn, int mipmapLevelsIn, IColorBlender colorBlender) {
      if (mipmapLevelsIn + 1 <= imageIn.length) {
         return imageIn;
      } else {
         NativeImage[] anativeimage = new NativeImage[mipmapLevelsIn + 1];
         anativeimage[0] = imageIn[0];
         boolean flag = false;

         for (int i = 1; i <= mipmapLevelsIn; i++) {
            if (i < imageIn.length) {
               anativeimage[i] = imageIn[i];
            } else {
               NativeImage nativeimage = anativeimage[i - 1];
               int widthNext = Math.max(nativeimage.a() >> 1, 1);
               int heightNext = Math.max(nativeimage.b() >> 1, 1);
               NativeImage nativeimage1 = new NativeImage(widthNext, heightNext, false);
               int j = nativeimage1.a();
               int k = nativeimage1.b();

               for (int l = 0; l < j; l++) {
                  for (int i1 = 0; i1 < k; i1++) {
                     if (colorBlender != null) {
                        nativeimage1.a(
                           l,
                           i1,
                           colorBlender.blend(
                              nativeimage.a(l * 2 + 0, i1 * 2 + 0),
                              nativeimage.a(l * 2 + 1, i1 * 2 + 0),
                              nativeimage.a(l * 2 + 0, i1 * 2 + 1),
                              nativeimage.a(l * 2 + 1, i1 * 2 + 1)
                           )
                        );
                     } else {
                        nativeimage1.a(
                           l,
                           i1,
                           a(
                              nativeimage.a(l * 2 + 0, i1 * 2 + 0),
                              nativeimage.a(l * 2 + 1, i1 * 2 + 0),
                              nativeimage.a(l * 2 + 0, i1 * 2 + 1),
                              nativeimage.a(l * 2 + 1, i1 * 2 + 1),
                              flag
                           )
                        );
                     }
                  }
               }

               anativeimage[i] = nativeimage1;
            }
         }

         return anativeimage;
      }
   }

   private static boolean a(NativeImage imageIn) {
      for (int i = 0; i < imageIn.a(); i++) {
         for (int j = 0; j < imageIn.b(); j++) {
            if (imageIn.a(i, j) >> 24 == 0) {
               return true;
            }
         }
      }

      return false;
   }

   private static int a(int col1, int col2, int col3, int col4, boolean transparent) {
      return Mipmaps.alphaBlend(col1, col2, col3, col4);
   }

   private static int a(int col1, int col2, int col3, int col4, int bitOffset) {
      float f = a(col1 >> bitOffset);
      float f1 = a(col2 >> bitOffset);
      float f2 = a(col3 >> bitOffset);
      float f3 = a(col4 >> bitOffset);
      float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25, 0.45454545454545453)));
      return (int)((double)f4 * 255.0);
   }

   private static float a(int valIn) {
      return b[valIn & 0xFF];
   }
}
