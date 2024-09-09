package net.minecraft.src;

import net.optifine.Mipmaps;
import net.optifine.texture.IColorBlender;

public class C_4472_ {
   private static final int f_174686_ = 96;
   private static final float[] f_118038_ = (float[])C_5322_.m_137469_(new float[256], (floatsIn) -> {
      for(int i = 0; i < floatsIn.length; ++i) {
         floatsIn[i] = (float)Math.pow((double)((float)i / 255.0F), 2.2);
      }

   });

   private C_4472_() {
   }

   public static C_3148_[] m_246246_(C_3148_[] imageIn, int mipmapLevelsIn) {
      return generateMipLevels(imageIn, mipmapLevelsIn, (IColorBlender)null);
   }

   public static C_3148_[] generateMipLevels(C_3148_[] imageIn, int mipmapLevelsIn, IColorBlender colorBlender) {
      if (mipmapLevelsIn + 1 <= imageIn.length) {
         return imageIn;
      } else {
         C_3148_[] anativeimage = new C_3148_[mipmapLevelsIn + 1];
         anativeimage[0] = imageIn[0];
         boolean flag = false;

         for(int i = 1; i <= mipmapLevelsIn; ++i) {
            if (i < imageIn.length) {
               anativeimage[i] = imageIn[i];
            } else {
               C_3148_ nativeimage = anativeimage[i - 1];
               int widthNext = Math.max(nativeimage.m_84982_() >> 1, 1);
               int heightNext = Math.max(nativeimage.m_85084_() >> 1, 1);
               C_3148_ nativeimage1 = new C_3148_(widthNext, heightNext, false);
               int j = nativeimage1.m_84982_();
               int k = nativeimage1.m_85084_();

               for(int l = 0; l < j; ++l) {
                  for(int i1 = 0; i1 < k; ++i1) {
                     if (colorBlender != null) {
                        nativeimage1.m_84988_(l, i1, colorBlender.blend(nativeimage.m_84985_(l * 2 + 0, i1 * 2 + 0), nativeimage.m_84985_(l * 2 + 1, i1 * 2 + 0), nativeimage.m_84985_(l * 2 + 0, i1 * 2 + 1), nativeimage.m_84985_(l * 2 + 1, i1 * 2 + 1)));
                     } else {
                        nativeimage1.m_84988_(l, i1, m_118048_(nativeimage.m_84985_(l * 2 + 0, i1 * 2 + 0), nativeimage.m_84985_(l * 2 + 1, i1 * 2 + 0), nativeimage.m_84985_(l * 2 + 0, i1 * 2 + 1), nativeimage.m_84985_(l * 2 + 1, i1 * 2 + 1), flag));
                     }
                  }
               }

               anativeimage[i] = nativeimage1;
            }
         }

         return anativeimage;
      }
   }

   private static boolean m_246464_(C_3148_ imageIn) {
      for(int i = 0; i < imageIn.m_84982_(); ++i) {
         for(int j = 0; j < imageIn.m_85084_(); ++j) {
            if (imageIn.m_84985_(i, j) >> 24 == 0) {
               return true;
            }
         }
      }

      return false;
   }

   private static int m_118048_(int col1, int col2, int col3, int col4, boolean transparent) {
      return Mipmaps.alphaBlend(col1, col2, col3, col4);
   }

   private static int m_118042_(int col1, int col2, int col3, int col4, int bitOffset) {
      float f = m_118040_(col1 >> bitOffset);
      float f1 = m_118040_(col2 >> bitOffset);
      float f2 = m_118040_(col3 >> bitOffset);
      float f3 = m_118040_(col4 >> bitOffset);
      float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25, 0.45454545454545453)));
      return (int)((double)f4 * 255.0);
   }

   private static float m_118040_(int valIn) {
      return f_118038_[valIn & 255];
   }
}
