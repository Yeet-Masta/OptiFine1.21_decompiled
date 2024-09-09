import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.src.C_213324_;
import net.minecraft.src.C_283725_;
import net.minecraft.src.C_285530_;
import net.minecraft.src.C_285552_;
import net.minecraft.src.C_3098_;
import net.minecraft.src.C_3099_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_285530_.C_285534_;
import net.minecraft.src.C_285530_.C_285550_;
import net.optifine.util.FontUtils;
import org.slf4j.Logger;

public class BitmapProvider implements C_3099_ {
   static final Logger b = LogUtils.getLogger();
   private final NativeImage c;
   private final C_283725_<BitmapProvider.b> d;

   BitmapProvider(NativeImage textureIn, C_283725_<BitmapProvider.b> infosIn) {
      this.c = textureIn;
      this.d = infosIn;
   }

   public void close() {
      this.c.close();
   }

   @Nullable
   public C_3098_ m_214022_(int character) {
      return (C_3098_)this.d.m_284412_(character);
   }

   public IntSet m_6990_() {
      return IntSets.unmodifiable(this.d.m_284498_());
   }

   public static record a(ResourceLocation c, int d, int e, int[][] f) implements C_285530_ {
      private static final Codec<int[][]> g = Codec.STRING.listOf().xmap(listIn -> {
         int i = listIn.size();
         int[][] aint = new int[i][];

         for (int j = 0; j < i; j++) {
            aint[j] = ((String)listIn.get(j)).codePoints().toArray();
         }

         return aint;
      }, intsIn -> {
         List<String> list = new ArrayList(intsIn.length);

         for (int[] aint : intsIn) {
            list.add(new String(aint, 0, aint.length));
         }

         return list;
      }).validate(BitmapProvider.a::a);
      public static final MapCodec<BitmapProvider.a> a = RecordCodecBuilder.mapCodec(
            defIn -> defIn.group(
                     ResourceLocation.a.fieldOf("file").forGetter(BitmapProvider.a::c),
                     Codec.INT.optionalFieldOf("height", 8).forGetter(BitmapProvider.a::d),
                     Codec.INT.fieldOf("ascent").forGetter(BitmapProvider.a::e),
                     g.fieldOf("chars").forGetter(BitmapProvider.a::f)
                  )
                  .apply(defIn, BitmapProvider.a::new)
         )
         .validate(BitmapProvider.a::a);

      public a(ResourceLocation c, int d, int e, int[][] f) {
         c = FontUtils.getHdFontLocation(c);
         this.c = c;
         this.d = d;
         this.e = e;
         this.f = f;
      }

      private static DataResult<int[][]> a(int[][] intsIn) {
         int i = intsIn.length;
         if (i == 0) {
            return DataResult.error(() -> "Expected to find data in codepoint grid");
         } else {
            int[] aint = intsIn[0];
            int j = aint.length;
            if (j == 0) {
               return DataResult.error(() -> "Expected to find data in codepoint grid");
            } else {
               for (int k = 1; k < i; k++) {
                  int[] aint1 = intsIn[k];
                  if (aint1.length != j) {
                     return DataResult.error(
                        () -> "Lines in codepoint grid have to be the same length (found: "
                              + aint1.length
                              + " codepoints, expected: "
                              + j
                              + "), pad with \\u0000"
                     );
                  }
               }

               return DataResult.success(intsIn);
            }
         }
      }

      private static DataResult<BitmapProvider.a> a(BitmapProvider.a defIn) {
         return defIn.e > defIn.d ? DataResult.error(() -> "Ascent " + defIn.e + " higher than height " + defIn.d) : DataResult.success(defIn);
      }

      public C_285552_ m_285843_() {
         return C_285552_.BITMAP;
      }

      public Either<C_285550_, C_285534_> m_285782_() {
         return Either.left(this::a);
      }

      private C_3099_ a(C_77_ resourceManagerIn) throws IOException {
         ResourceLocation resourcelocation = this.c.f("textures/");
         InputStream inputstream = resourceManagerIn.open(resourcelocation);

         BitmapProvider bitmapprovider;
         try {
            NativeImage nativeimage = NativeImage.a(NativeImage.a.a, inputstream);
            int i = nativeimage.a();
            int j = nativeimage.b();
            int k = i / this.f[0].length;
            int l = j / this.f.length;
            float f = (float)this.d / (float)l;
            C_283725_<BitmapProvider.b> codepointmap = new C_283725_(BitmapProvider.b[]::new, BitmapProvider.b[][]::new);

            for (int i1 = 0; i1 < this.f.length; i1++) {
               int j1 = 0;

               for (int k1 : this.f[i1]) {
                  int l1 = j1++;
                  if (k1 != 0) {
                     int i2 = this.a(nativeimage, k, l, l1, i1);
                     BitmapProvider.b bitmapprovider$glyph = (BitmapProvider.b)codepointmap.m_284506_(
                        k1, new BitmapProvider.b(f, nativeimage, l1 * k, i1 * l, k, l, (int)(0.5 + (double)((float)i2 * f)) + 1, this.e)
                     );
                     if (bitmapprovider$glyph != null) {
                        BitmapProvider.b.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(k1), resourcelocation);
                     }
                  }
               }
            }

            bitmapprovider = new BitmapProvider(nativeimage, codepointmap);
         } catch (Throwable var22) {
            if (inputstream != null) {
               try {
                  inputstream.close();
               } catch (Throwable var21) {
                  var22.addSuppressed(var21);
               }
            }

            throw var22;
         }

         if (inputstream != null) {
            inputstream.close();
         }

         return bitmapprovider;
      }

      private int a(NativeImage imageIn, int charWidthIn, int charHeightIn, int columnIn, int rowIn) {
         int i;
         for (i = charWidthIn - 1; i >= 0; i--) {
            int j = columnIn * charWidthIn + i;

            for (int k = 0; k < charHeightIn; k++) {
               int l = rowIn * charHeightIn + k;
               if (imageIn.e(j, l) != 0) {
                  return i + 1;
               }
            }
         }

         return i + 1;
      }
   }

   static record b(float a, NativeImage b, int c, int d, int e, int f, int g, int h) implements C_3098_ {
      public float m_7403_() {
         return (float)this.g;
      }

      public BakedGlyph bake(Function<C_213324_, BakedGlyph> bakerIn) {
         return (BakedGlyph)bakerIn.apply(new C_213324_() {
            public float m_213963_() {
               return 1.0F / b.this.a;
            }

            public int m_213962_() {
               return b.this.e;
            }

            public int m_213961_() {
               return b.this.f;
            }

            public float m_213964_() {
               return (float)b.this.h;
            }

            public void m_213958_(int xOffsetIn, int yOffsetIn) {
               b.this.b.a(0, xOffsetIn, yOffsetIn, b.this.c, b.this.d, b.this.e, b.this.f, false, false);
            }

            public boolean m_213965_() {
               return b.this.b.c().a() > 1;
            }
         });
      }

      public float c() {
         return this.a;
      }

      public NativeImage d() {
         return this.b;
      }

      public int e() {
         return this.c;
      }

      public int f() {
         return this.d;
      }

      public int g() {
         return this.e;
      }

      public int h() {
         return this.f;
      }

      public int i() {
         return this.g;
      }

      public int j() {
         return this.h;
      }
   }
}
