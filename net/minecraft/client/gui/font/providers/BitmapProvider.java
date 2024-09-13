package net.minecraft.client.gui.font.providers;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.client.gui.font.CodepointMap;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition.Loader;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.util.FontUtils;
import org.slf4j.Logger;

public class BitmapProvider implements GlyphProvider {
   static Logger f_95328_ = LogUtils.getLogger();
   private NativeImage f_95329_;
   private CodepointMap<BitmapProvider.Glyph> f_95330_;

   BitmapProvider(NativeImage textureIn, CodepointMap<BitmapProvider.Glyph> infosIn) {
      this.f_95329_ = textureIn;
      this.f_95330_ = infosIn;
   }

   public void close() {
      this.f_95329_.close();
   }

   @Nullable
   public GlyphInfo m_214022_(int character) {
      return (GlyphInfo)this.f_95330_.m_284412_(character);
   }

   public IntSet m_6990_() {
      return IntSets.unmodifiable(this.f_95330_.m_284498_());
   }

   public static record Definition(ResourceLocation f_285631_, int f_285660_, int f_285577_, int[][] f_285611_) implements GlyphProviderDefinition {
      private static Codec<int[][]> f_285599_ = Codec.STRING.listOf().xmap(listIn -> {
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
      }).m_216378_(BitmapProvider.Definition::m_285860_);
      public static MapCodec<BitmapProvider.Definition> f_285606_ = RecordCodecBuilder.mapCodec(
            defIn -> defIn.group(
                     ResourceLocation.f_135803_.fieldOf("file").forGetter(BitmapProvider.Definition::f_285631_),
                     Codec.INT.optionalFieldOf("height", 8).forGetter(BitmapProvider.Definition::f_285660_),
                     Codec.INT.fieldOf("ascent").forGetter(BitmapProvider.Definition::f_285577_),
                     f_285599_.fieldOf("chars").forGetter(BitmapProvider.Definition::f_285611_)
                  )
                  .apply(defIn, BitmapProvider.Definition::new)
         )
         .m_216378_(BitmapProvider.Definition::m_285746_);

      public Definition(ResourceLocation f_285631_, int f_285660_, int f_285577_, int[][] f_285611_) {
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

      private static DataResult<BitmapProvider.Definition> m_285746_(BitmapProvider.Definition defIn) {
         return defIn.f_285577_ > defIn.f_285660_
            ? DataResult.error(() -> "Ascent " + defIn.f_285577_ + " higher than height " + defIn.f_285660_)
            : DataResult.success(defIn);
      }

      public GlyphProviderType m_285843_() {
         return GlyphProviderType.BITMAP;
      }

      public Either<Loader, Reference> m_285782_() {
         return Either.left(this::m_286048_);
      }

      private GlyphProvider m_286048_(ResourceManager resourceManagerIn) throws IOException {
         ResourceLocation resourcelocation = this.f_285631_.m_246208_("textures/");
         InputStream inputstream = resourceManagerIn.m_215595_(resourcelocation);

         BitmapProvider bitmapprovider;
         try {
            NativeImage nativeimage = NativeImage.m_85048_(NativeImage.Format.RGBA, inputstream);
            int i = nativeimage.m_84982_();
            int j = nativeimage.m_85084_();
            int k = i / this.f_285611_[0].length;
            int l = j / this.f_285611_.length;
            float f = (float)this.f_285660_ / (float)l;
            CodepointMap<BitmapProvider.Glyph> codepointmap = new CodepointMap(BitmapProvider.Glyph[]::new, BitmapProvider.Glyph[][]::new);

            for (int i1 = 0; i1 < this.f_285611_.length; i1++) {
               int j1 = 0;

               for (int k1 : this.f_285611_[i1]) {
                  int l1 = j1++;
                  if (k1 != 0) {
                     int i2 = this.m_285979_(nativeimage, k, l, l1, i1);
                     BitmapProvider.Glyph bitmapprovider$glyph = (BitmapProvider.Glyph)codepointmap.m_284506_(
                        k1, new BitmapProvider.Glyph(f, nativeimage, l1 * k, i1 * l, k, l, (int)(0.5 + (double)((float)i2 * f)) + 1, this.f_285577_)
                     );
                     if (bitmapprovider$glyph != null) {
                        BitmapProvider.f_95328_.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(k1), resourcelocation);
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

      private int m_285979_(NativeImage imageIn, int charWidthIn, int charHeightIn, int columnIn, int rowIn) {
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

   static record Glyph(float f_95363_, NativeImage f_95364_, int f_95365_, int f_95366_, int f_95367_, int f_95368_, int f_95369_, int f_95370_)
      implements GlyphInfo {

      public float m_7403_() {
         return (float)this.f_95369_;
      }

      public BakedGlyph m_213604_(Function<SheetGlyphInfo, BakedGlyph> bakerIn) {
         return (BakedGlyph)bakerIn.apply(
            new SheetGlyphInfo() {
               public float m_213963_() {
                  return 1.0F / Glyph.this.f_95363_;
               }

               public int m_213962_() {
                  return Glyph.this.f_95367_;
               }

               public int m_213961_() {
                  return Glyph.this.f_95368_;
               }

               public float m_213964_() {
                  return (float)Glyph.this.f_95370_;
               }

               public void m_213958_(int xOffsetIn, int yOffsetIn) {
                  Glyph.this.f_95364_
                     .m_85003_(0, xOffsetIn, yOffsetIn, Glyph.this.f_95365_, Glyph.this.f_95366_, Glyph.this.f_95367_, Glyph.this.f_95368_, false, false);
               }

               public boolean m_213965_() {
                  return Glyph.this.f_95364_.m_85102_().m_85161_() > 1;
               }
            }
         );
      }
   }
}
