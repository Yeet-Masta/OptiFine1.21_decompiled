package net.minecraft.client.gui.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider.Conditional;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
import net.minecraft.util.RandomSource;

public class FontSet implements AutoCloseable {
   private static final RandomSource f_95050_ = RandomSource.m_216327_();
   private static final float f_242991_ = 32.0F;
   private final net.minecraft.client.renderer.texture.TextureManager f_95051_;
   private final net.minecraft.resources.ResourceLocation f_95052_;
   private net.minecraft.client.gui.font.glyphs.BakedGlyph f_95053_;
   private net.minecraft.client.gui.font.glyphs.BakedGlyph f_95054_;
   private List<Conditional> f_315683_ = List.of();
   private List<GlyphProvider> f_317127_ = List.of();
   private final CodepointMap<net.minecraft.client.gui.font.glyphs.BakedGlyph> f_95056_ = new CodepointMap(
      net.minecraft.client.gui.font.glyphs.BakedGlyph[]::new, net.minecraft.client.gui.font.glyphs.BakedGlyph[][]::new
   );
   private final CodepointMap<net.minecraft.client.gui.font.FontSet.GlyphInfoFilter> f_95057_ = new CodepointMap(
      net.minecraft.client.gui.font.FontSet.GlyphInfoFilter[]::new, net.minecraft.client.gui.font.FontSet.GlyphInfoFilter[][]::new
   );
   private final Int2ObjectMap<IntList> f_95058_ = new Int2ObjectOpenHashMap();
   private final List<FontTexture> f_95059_ = Lists.newArrayList();

   public FontSet(net.minecraft.client.renderer.texture.TextureManager textureManagerIn, net.minecraft.resources.ResourceLocation resourceLocationIn) {
      this.f_95051_ = textureManagerIn;
      this.f_95052_ = resourceLocationIn;
   }

   public void m_321905_(List<Conditional> conditionalsIn, Set<FontOption> fontOptionsIn) {
      this.f_315683_ = conditionalsIn;
      this.m_95071_(fontOptionsIn);
   }

   public void m_95071_(Set<FontOption> glyphProvidersIn) {
      this.f_317127_ = List.of();
      this.m_322787_();
      this.f_317127_ = this.m_321621_(this.f_315683_, glyphProvidersIn);
   }

   private void m_322787_() {
      this.m_95080_();
      this.f_95056_.m_284192_();
      this.f_95057_.m_284192_();
      this.f_95058_.clear();
      this.f_95053_ = SpecialGlyphs.MISSING.m_213604_(this::m_232556_);
      this.f_95054_ = SpecialGlyphs.WHITE.m_213604_(this::m_232556_);
   }

   private List<GlyphProvider> m_321621_(List<Conditional> conditionalsIn, Set<FontOption> fontOptionsIn) {
      IntSet intset = new IntOpenHashSet();
      List<GlyphProvider> list = new ArrayList();

      for (Conditional glyphprovider$conditional : conditionalsIn) {
         if (glyphprovider$conditional.f_316533_().m_319512_(fontOptionsIn)) {
            list.add(glyphprovider$conditional.f_316017_());
            intset.addAll(glyphprovider$conditional.f_316017_().m_6990_());
         }
      }

      Set<GlyphProvider> set = Sets.newHashSet();
      intset.forEach(
         charIn -> {
            for (GlyphProvider glyphprovider : list) {
               GlyphInfo glyphinfo = glyphprovider.m_214022_(charIn);
               if (glyphinfo != null) {
                  set.add(glyphprovider);
                  if (glyphinfo != SpecialGlyphs.MISSING) {
                     ((IntList)this.f_95058_.computeIfAbsent(net.minecraft.util.Mth.m_14167_(glyphinfo.m_83827_(false)), widthIn -> new IntArrayList()))
                        .add(charIn);
                  }
                  break;
               }
            }
         }
      );
      return list.stream().filter(set::contains).toList();
   }

   public void close() {
      this.m_95080_();
   }

   private void m_95080_() {
      for (FontTexture fonttexture : this.f_95059_) {
         fonttexture.close();
      }

      this.f_95059_.clear();
   }

   private static boolean m_243068_(GlyphInfo infoIn) {
      float f = infoIn.m_83827_(false);
      if (!(f < 0.0F) && !(f > 32.0F)) {
         float f1 = infoIn.m_83827_(true);
         return f1 < 0.0F || f1 > 32.0F;
      } else {
         return true;
      }
   }

   private net.minecraft.client.gui.font.FontSet.GlyphInfoFilter m_243121_(int charIn) {
      GlyphInfo glyphinfo = null;

      for (GlyphProvider glyphprovider : this.f_317127_) {
         GlyphInfo glyphinfo1 = glyphprovider.m_214022_(charIn);
         if (glyphinfo1 != null) {
            if (glyphinfo == null) {
               glyphinfo = glyphinfo1;
            }

            if (!m_243068_(glyphinfo1)) {
               return new net.minecraft.client.gui.font.FontSet.GlyphInfoFilter(glyphinfo, glyphinfo1);
            }
         }
      }

      return glyphinfo != null
         ? new net.minecraft.client.gui.font.FontSet.GlyphInfoFilter(glyphinfo, SpecialGlyphs.MISSING)
         : net.minecraft.client.gui.font.FontSet.GlyphInfoFilter.f_243023_;
   }

   public GlyphInfo m_243128_(int charIn, boolean notFishyIn) {
      net.minecraft.client.gui.font.FontSet.GlyphInfoFilter gif = (net.minecraft.client.gui.font.FontSet.GlyphInfoFilter)this.f_95057_.m_284412_(charIn);
      return gif != null
         ? gif.m_243099_(notFishyIn)
         : ((net.minecraft.client.gui.font.FontSet.GlyphInfoFilter)this.f_95057_.m_284450_(charIn, this::m_243121_)).m_243099_(notFishyIn);
   }

   private net.minecraft.client.gui.font.glyphs.BakedGlyph m_232564_(int charIn) {
      for (GlyphProvider glyphprovider : this.f_317127_) {
         GlyphInfo glyphinfo = glyphprovider.m_214022_(charIn);
         if (glyphinfo != null) {
            return glyphinfo.m_213604_(this::m_232556_);
         }
      }

      return this.f_95053_;
   }

   public net.minecraft.client.gui.font.glyphs.BakedGlyph m_95078_(int character) {
      net.minecraft.client.gui.font.glyphs.BakedGlyph bg = (net.minecraft.client.gui.font.glyphs.BakedGlyph)this.f_95056_.m_284412_(character);
      return bg != null ? bg : (net.minecraft.client.gui.font.glyphs.BakedGlyph)this.f_95056_.m_284450_(character, this::m_232564_);
   }

   private net.minecraft.client.gui.font.glyphs.BakedGlyph m_232556_(SheetGlyphInfo glyphInfoIn) {
      for (FontTexture fonttexture : this.f_95059_) {
         net.minecraft.client.gui.font.glyphs.BakedGlyph bakedglyph = fonttexture.m_232568_(glyphInfoIn);
         if (bakedglyph != null) {
            return bakedglyph;
         }
      }

      net.minecraft.resources.ResourceLocation resourcelocation = this.f_95052_.m_266382_("/" + this.f_95059_.size());
      boolean flag = glyphInfoIn.m_213965_();
      GlyphRenderTypes glyphrendertypes = flag ? GlyphRenderTypes.m_284354_(resourcelocation) : GlyphRenderTypes.m_284520_(resourcelocation);
      FontTexture fonttexture1 = new FontTexture(glyphrendertypes, flag);
      this.f_95059_.add(fonttexture1);
      this.f_95051_.m_118495_(resourcelocation, fonttexture1);
      net.minecraft.client.gui.font.glyphs.BakedGlyph bakedglyph1 = fonttexture1.m_232568_(glyphInfoIn);
      return bakedglyph1 == null ? this.f_95053_ : bakedglyph1;
   }

   public net.minecraft.client.gui.font.glyphs.BakedGlyph m_95067_(GlyphInfo glyph) {
      IntList intlist = (IntList)this.f_95058_.get(net.minecraft.util.Mth.m_14167_(glyph.m_83827_(false)));
      return intlist != null && !intlist.isEmpty() ? this.m_95078_(intlist.getInt(f_95050_.m_188503_(intlist.size()))) : this.f_95053_;
   }

   public net.minecraft.resources.ResourceLocation m_321601_() {
      return this.f_95052_;
   }

   public net.minecraft.client.gui.font.glyphs.BakedGlyph m_95064_() {
      return this.f_95054_;
   }

   static record GlyphInfoFilter(GlyphInfo f_243013_, GlyphInfo f_243006_) {
      static final net.minecraft.client.gui.font.FontSet.GlyphInfoFilter f_243023_ = new net.minecraft.client.gui.font.FontSet.GlyphInfoFilter(
         SpecialGlyphs.MISSING, SpecialGlyphs.MISSING
      );

      GlyphInfo m_243099_(boolean notFishyIn) {
         return notFishyIn ? this.f_243006_ : this.f_243013_;
      }
   }
}
