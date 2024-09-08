package net.minecraft.src;

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
import net.minecraft.src.C_285530_.C_285534_;
import net.minecraft.src.C_285530_.C_285550_;
import net.optifine.util.FontUtils;
import org.slf4j.Logger;

public class C_3522_ implements C_3099_ {
   static final Logger f_95328_ = LogUtils.getLogger();
   private final C_3148_ f_95329_;
   private final C_283725_<C_3522_.C_3525_> f_95330_;

   C_3522_(C_3148_ textureIn, C_283725_<C_3522_.C_3525_> infosIn) {
      this.f_95329_ = textureIn;
      this.f_95330_ = infosIn;
   }

   public void close() {
      this.f_95329_.close();
   }

   @Nullable
   public C_3098_ m_214022_(int character) {
      return (C_3098_)this.f_95330_.m_284412_(character);
   }

   public IntSet m_6990_() {
      return IntSets.unmodifiable(this.f_95330_.m_284498_());
   }

   public static record C_285541_(C_5265_ f_285631_, int f_285660_, int f_285577_, int[][] f_285611_) implements C_285530_ {
      private static final Codec<int[][]> f_285599_ = Codec.STRING.listOf().xmap(listIn -> {
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
      }).validate(C_3522_.C_285541_::m_285860_);
      public static final MapCodec<C_3522_.C_285541_> f_285606_ = RecordCodecBuilder.mapCodec(
            defIn -> defIn.group(
                     C_5265_.f_135803_.fieldOf("file").forGetter(C_3522_.C_285541_::f_285631_),
                     Codec.INT.optionalFieldOf("height", 8).forGetter(C_3522_.C_285541_::f_285660_),
                     Codec.INT.fieldOf("ascent").forGetter(C_3522_.C_285541_::f_285577_),
                     f_285599_.fieldOf("chars").forGetter(C_3522_.C_285541_::f_285611_)
                  )
                  .apply(defIn, C_3522_.C_285541_::new)
         )
         .validate(C_3522_.C_285541_::m_285746_);

      public C_285541_(C_5265_ f_285631_, int f_285660_, int f_285577_, int[][] f_285611_) {
         f_285631_ = FontUtils.getHdFontLocation(f_285631_);
         this.f_285631_ = f_285631_;
         this.f_285660_ = f_285660_;
         this.f_285577_ = f_285577_;
         this.f_285611_ = f_285611_;
      }

      private static DataResult<int[][]> m_285860_(int[][] intsIn) {
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

      private static DataResult<C_3522_.C_285541_> m_285746_(C_3522_.C_285541_ defIn) {
         return defIn.f_285577_ > defIn.f_285660_
            ? DataResult.error(() -> "Ascent " + defIn.f_285577_ + " higher than height " + defIn.f_285660_)
            : DataResult.success(defIn);
      }

      public C_285552_ m_285843_() {
         return C_285552_.BITMAP;
      }

      public Either<C_285550_, C_285534_> m_285782_() {
         return Either.left(this::m_286048_);
      }

      private C_3099_ m_286048_(C_77_ resourceManagerIn) throws IOException {
         C_5265_ resourcelocation = this.f_285631_.m_246208_("textures/");
         InputStream inputstream = resourceManagerIn.open(resourcelocation);

         C_3522_ bitmapprovider;
         try {
            C_3148_ nativeimage = C_3148_.m_85048_(C_3148_.C_3150_.RGBA, inputstream);
            int i = nativeimage.m_84982_();
            int j = nativeimage.m_85084_();
            int k = i / this.f_285611_[0].length;
            int l = j / this.f_285611_.length;
            float f = (float)this.f_285660_ / (float)l;
            C_283725_<C_3522_.C_3525_> codepointmap = new C_283725_(C_3522_.C_3525_[]::new, C_3522_.C_3525_[][]::new);

            for (int i1 = 0; i1 < this.f_285611_.length; i1++) {
               int j1 = 0;

               for (int k1 : this.f_285611_[i1]) {
                  int l1 = j1++;
                  if (k1 != 0) {
                     int i2 = this.m_285979_(nativeimage, k, l, l1, i1);
                     C_3522_.C_3525_ bitmapprovider$glyph = (C_3522_.C_3525_)codepointmap.m_284506_(
                        k1, new C_3522_.C_3525_(f, nativeimage, l1 * k, i1 * l, k, l, (int)(0.5 + (double)((float)i2 * f)) + 1, this.f_285577_)
                     );
                     if (bitmapprovider$glyph != null) {
                        C_3522_.f_95328_.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(k1), resourcelocation);
                     }
                  }
               }
            }

            bitmapprovider = new C_3522_(nativeimage, codepointmap);
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

      private int m_285979_(C_3148_ imageIn, int charWidthIn, int charHeightIn, int columnIn, int rowIn) {
         int i;
         for (i = charWidthIn - 1; i >= 0; i--) {
            int j = columnIn * charWidthIn + i;

            for (int k = 0; k < charHeightIn; k++) {
               int l = rowIn * charHeightIn + k;
               if (imageIn.m_85087_(j, l) != 0) {
                  return i + 1;
               }
            }
         }

         return i + 1;
      }
   }

   static record C_3525_(float f_95363_, C_3148_ f_95364_, int f_95365_, int f_95366_, int f_95367_, int f_95368_, int f_95369_, int f_95370_)
      implements C_3098_ {
      public float m_7403_() {
         return (float)this.f_95369_;
      }

      public C_3516_ m_213604_(Function<C_213324_, C_3516_> bakerIn) {
         return (C_3516_)bakerIn.apply(
            new C_213324_() {
               public float m_213963_() {
                  return 1.0F / C_3525_.this.f_95363_;
               }

               public int m_213962_() {
                  return C_3525_.this.f_95367_;
               }

               public int m_213961_() {
                  return C_3525_.this.f_95368_;
               }

               public float m_213964_() {
                  return (float)C_3525_.this.f_95370_;
               }

               public void m_213958_(int xOffsetIn, int yOffsetIn) {
                  C_3525_.this.f_95364_
                     .m_85003_(
                        0, xOffsetIn, yOffsetIn, C_3525_.this.f_95365_, C_3525_.this.f_95366_, C_3525_.this.f_95367_, C_3525_.this.f_95368_, false, false
                     );
               }

               public boolean m_213965_() {
                  return C_3525_.this.f_95364_.m_85102_().m_85161_() > 1;
               }
            }
         );
      }
   }
}
