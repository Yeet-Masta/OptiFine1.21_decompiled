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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.util.FontUtils;
import org.slf4j.Logger;

public class BitmapProvider implements GlyphProvider {
   static final Logger f_95328_ = LogUtils.getLogger();
   private final NativeImage f_95329_;
   private final CodepointMap f_95330_;

   BitmapProvider(NativeImage textureIn, CodepointMap infosIn) {
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

   static record Glyph(float f_95363_, NativeImage f_95364_, int f_95365_, int f_95366_, int f_95367_, int f_95368_, int f_95369_, int f_95370_) implements GlyphInfo {
      Glyph(float scale, NativeImage texture, int unpackSkipPixels, int unpackSkipRows, int width, int height, int advanceWidth, int ascent) {
         this.f_95363_ = scale;
         this.f_95364_ = texture;
         this.f_95365_ = unpackSkipPixels;
         this.f_95366_ = unpackSkipRows;
         this.f_95367_ = width;
         this.f_95368_ = height;
         this.f_95369_ = advanceWidth;
         this.f_95370_ = ascent;
      }

      public float m_7403_() {
         return (float)this.f_95369_;
      }

      public BakedGlyph m_213604_(Function bakerIn) {
         return (BakedGlyph)bakerIn.apply(new SheetGlyphInfo() {
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
               Glyph.this.f_95364_.m_85003_(0, xOffsetIn, yOffsetIn, Glyph.this.f_95365_, Glyph.this.f_95366_, Glyph.this.f_95367_, Glyph.this.f_95368_, false, false);
            }

            public boolean m_213965_() {
               return Glyph.this.f_95364_.m_85102_().m_85161_() > 1;
            }
         });
      }

      public float f_95363_() {
         return this.f_95363_;
      }

      public NativeImage f_95364_() {
         return this.f_95364_;
      }

      public int f_95365_() {
         return this.f_95365_;
      }

      public int f_95366_() {
         return this.f_95366_;
      }

      public int f_95367_() {
         return this.f_95367_;
      }

      public int f_95368_() {
         return this.f_95368_;
      }

      public int f_95369_() {
         return this.f_95369_;
      }

      public int f_95370_() {
         return this.f_95370_;
      }
   }

   public static record Definition(ResourceLocation f_285631_, int f_285660_, int f_285577_, int[][] f_285611_) implements GlyphProviderDefinition {
      private static final Codec f_285599_;
      public static final MapCodec f_285606_;

      public Definition(ResourceLocation file, int height, int ascent, int[][] codepointGrid) {
         file = FontUtils.getHdFontLocation(file);
         this.f_285631_ = file;
         this.f_285660_ = height;
         this.f_285577_ = ascent;
         this.f_285611_ = codepointGrid;
      }

      private static DataResult m_285860_(int[][] intsIn) {
         int i = intsIn.length;
         if (i == 0) {
            return DataResult.error(() -> {
               return "Expected to find data in codepoint grid";
            });
         } else {
            int[] aint = intsIn[0];
            int j = aint.length;
            if (j == 0) {
               return DataResult.error(() -> {
                  return "Expected to find data in codepoint grid";
               });
            } else {
               for(int k = 1; k < i; ++k) {
                  int[] aint1 = intsIn[k];
                  if (aint1.length != j) {
                     return DataResult.error(() -> {
                        return "Lines in codepoint grid have to be the same length (found: " + aint1.length + " codepoints, expected: " + j + "), pad with \\u0000";
                     });
                  }
               }

               return DataResult.success(intsIn);
            }
         }
      }

      private static DataResult m_285746_(Definition defIn) {
         return defIn.f_285577_ > defIn.f_285660_ ? DataResult.error(() -> {
            return "Ascent " + defIn.f_285577_ + " higher than height " + defIn.f_285660_;
         }) : DataResult.success(defIn);
      }

      public GlyphProviderType m_285843_() {
         return GlyphProviderType.BITMAP;
      }

      public Either m_285782_() {
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
            CodepointMap codepointmap = new CodepointMap((x$0) -> {
               return new Glyph[x$0];
            }, (x$0) -> {
               return new Glyph[x$0][];
            });
            int i1 = 0;

            while(true) {
               if (i1 >= this.f_285611_.length) {
                  bitmapprovider = new BitmapProvider(nativeimage, codepointmap);
                  break;
               }

               int j1 = 0;
               int[] var14 = this.f_285611_[i1];
               int var15 = var14.length;

               for(int var16 = 0; var16 < var15; ++var16) {
                  int k1 = var14[var16];
                  int l1 = j1++;
                  if (k1 != 0) {
                     int i2 = this.m_285979_(nativeimage, k, l, l1, i1);
                     Glyph bitmapprovider$glyph = (Glyph)codepointmap.m_284506_(k1, new Glyph(f, nativeimage, l1 * k, i1 * l, k, l, (int)(0.5 + (double)((float)i2 * f)) + 1, this.f_285577_));
                     if (bitmapprovider$glyph != null) {
                        BitmapProvider.f_95328_.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString(k1), resourcelocation);
                     }
                  }
               }

               ++i1;
            }
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
         for(i = charWidthIn - 1; i >= 0; --i) {
            int j = columnIn * charWidthIn + i;

            for(int k = 0; k < charHeightIn; ++k) {
               int l = rowIn * charHeightIn + k;
               if (imageIn.m_85087_(j, l) != 0) {
                  return i + 1;
               }
            }
         }

         return i + 1;
      }

      public ResourceLocation f_285631_() {
         return this.f_285631_;
      }

      public int f_285660_() {
         return this.f_285660_;
      }

      public int f_285577_() {
         return this.f_285577_;
      }

      public int[][] f_285611_() {
         return this.f_285611_;
      }

      static {
         f_285599_ = Codec.STRING.listOf().xmap((listIn) -> {
            int i = listIn.size();
            int[][] aint = new int[i][];

            for(int j = 0; j < i; ++j) {
               aint[j] = ((String)listIn.get(j)).codePoints().toArray();
            }

            return aint;
         }, (intsIn) -> {
            List list = new ArrayList(intsIn.length);
            int[][] var2 = intsIn;
            int var3 = intsIn.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               int[] aint = var2[var4];
               list.add(new String(aint, 0, aint.length));
            }

            return list;
         }).validate(Definition::m_285860_);
         f_285606_ = RecordCodecBuilder.mapCodec((defIn) -> {
            return defIn.group(ResourceLocation.f_135803_.fieldOf("file").forGetter(Definition::f_285631_), Codec.INT.optionalFieldOf("height", 8).forGetter(Definition::f_285660_), Codec.INT.fieldOf("ascent").forGetter(Definition::f_285577_), f_285599_.fieldOf("chars").forGetter(Definition::f_285611_)).apply(defIn, Definition::new);
         }).validate(Definition::m_285746_);
      }
   }
}
