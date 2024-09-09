package net.minecraft.client.gui.font;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.SpecialGlyphs;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class FontSet implements AutoCloseable {
   private static final RandomSource f_95050_ = RandomSource.m_216327_();
   private static final float f_242991_ = 32.0F;
   private final TextureManager f_95051_;
   private final ResourceLocation f_95052_;
   private BakedGlyph f_95053_;
   private BakedGlyph f_95054_;
   private List f_315683_ = List.of();
   private List f_317127_ = List.of();
   private final CodepointMap f_95056_ = new CodepointMap((x$0) -> {
      return new BakedGlyph[x$0];
   }, (x$0) -> {
      return new BakedGlyph[x$0][];
   });
   private final CodepointMap f_95057_ = new CodepointMap((x$0) -> {
      return new GlyphInfoFilter[x$0];
   }, (x$0) -> {
      return new GlyphInfoFilter[x$0][];
   });
   private final Int2ObjectMap f_95058_ = new Int2ObjectOpenHashMap();
   private final List f_95059_ = Lists.newArrayList();

   public FontSet(TextureManager textureManagerIn, ResourceLocation resourceLocationIn) {
      this.f_95051_ = textureManagerIn;
      this.f_95052_ = resourceLocationIn;
   }

   public void m_321905_(List conditionalsIn, Set fontOptionsIn) {
      this.f_315683_ = conditionalsIn;
      this.m_95071_(fontOptionsIn);
   }

   public void m_95071_(Set glyphProvidersIn) {
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

   private List m_321621_(List conditionalsIn, Set fontOptionsIn) {
      IntSet intset = new IntOpenHashSet();
      List list = new ArrayList();
      Iterator var5 = conditionalsIn.iterator();

      while(var5.hasNext()) {
         GlyphProvider.Conditional glyphprovider$conditional = (GlyphProvider.Conditional)var5.next();
         if (glyphprovider$conditional.f_316533_().m_319512_(fontOptionsIn)) {
            list.add(glyphprovider$conditional.f_316017_());
            intset.addAll(glyphprovider$conditional.f_316017_().m_6990_());
         }
      }

      Set set = Sets.newHashSet();
      intset.forEach((charIn) -> {
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            GlyphProvider glyphprovider = (GlyphProvider)var4.next();
            GlyphInfo glyphinfo = glyphprovider.m_214022_(charIn);
            if (glyphinfo != null) {
               set.add(glyphprovider);
               if (glyphinfo != SpecialGlyphs.MISSING) {
                  ((IntList)this.f_95058_.computeIfAbsent(Mth.m_14167_(glyphinfo.m_83827_(false)), (widthIn) -> {
                     return new IntArrayList();
                  })).add(charIn);
               }
               break;
            }
         }

      });
      Stream var10000 = list.stream();
      Objects.requireNonNull(set);
      return var10000.filter(set::contains).toList();
   }

   public void close() {
      this.m_95080_();
   }

   private void m_95080_() {
      Iterator var1 = this.f_95059_.iterator();

      while(var1.hasNext()) {
         FontTexture fonttexture = (FontTexture)var1.next();
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

   private GlyphInfoFilter m_243121_(int charIn) {
      GlyphInfo glyphinfo = null;
      Iterator var3 = this.f_317127_.iterator();

      while(var3.hasNext()) {
         GlyphProvider glyphprovider = (GlyphProvider)var3.next();
         GlyphInfo glyphinfo1 = glyphprovider.m_214022_(charIn);
         if (glyphinfo1 != null) {
            if (glyphinfo == null) {
               glyphinfo = glyphinfo1;
            }

            if (!m_243068_(glyphinfo1)) {
               return new GlyphInfoFilter(glyphinfo, glyphinfo1);
            }
         }
      }

      return glyphinfo != null ? new GlyphInfoFilter(glyphinfo, SpecialGlyphs.MISSING) : FontSet.GlyphInfoFilter.f_243023_;
   }

   public GlyphInfo m_243128_(int charIn, boolean notFishyIn) {
      GlyphInfoFilter gif = (GlyphInfoFilter)this.f_95057_.m_284412_(charIn);
      return gif != null ? gif.m_243099_(notFishyIn) : ((GlyphInfoFilter)this.f_95057_.m_284450_(charIn, this::m_243121_)).m_243099_(notFishyIn);
   }

   private BakedGlyph m_232564_(int charIn) {
      Iterator var2 = this.f_317127_.iterator();

      GlyphInfo glyphinfo;
      do {
         if (!var2.hasNext()) {
            return this.f_95053_;
         }

         GlyphProvider glyphprovider = (GlyphProvider)var2.next();
         glyphinfo = glyphprovider.m_214022_(charIn);
      } while(glyphinfo == null);

      return glyphinfo.m_213604_(this::m_232556_);
   }

   public BakedGlyph m_95078_(int character) {
      BakedGlyph bg = (BakedGlyph)this.f_95056_.m_284412_(character);
      return bg != null ? bg : (BakedGlyph)this.f_95056_.m_284450_(character, this::m_232564_);
   }

   private BakedGlyph m_232556_(SheetGlyphInfo glyphInfoIn) {
      Iterator var2 = this.f_95059_.iterator();

      BakedGlyph bakedglyph;
      do {
         if (!var2.hasNext()) {
            ResourceLocation resourcelocation = this.f_95052_.m_266382_("/" + this.f_95059_.size());
            boolean flag = glyphInfoIn.m_213965_();
            GlyphRenderTypes glyphrendertypes = flag ? GlyphRenderTypes.m_284354_(resourcelocation) : GlyphRenderTypes.m_284520_(resourcelocation);
            FontTexture fonttexture1 = new FontTexture(glyphrendertypes, flag);
            this.f_95059_.add(fonttexture1);
            this.f_95051_.m_118495_(resourcelocation, fonttexture1);
            BakedGlyph bakedglyph1 = fonttexture1.m_232568_(glyphInfoIn);
            return bakedglyph1 == null ? this.f_95053_ : bakedglyph1;
         }

         FontTexture fonttexture = (FontTexture)var2.next();
         bakedglyph = fonttexture.m_232568_(glyphInfoIn);
      } while(bakedglyph == null);

      return bakedglyph;
   }

   public BakedGlyph m_95067_(GlyphInfo glyph) {
      IntList intlist = (IntList)this.f_95058_.get(Mth.m_14167_(glyph.m_83827_(false)));
      return intlist != null && !intlist.isEmpty() ? this.m_95078_(intlist.getInt(f_95050_.m_188503_(intlist.size()))) : this.f_95053_;
   }

   public ResourceLocation m_321601_() {
      return this.f_95052_;
   }

   public BakedGlyph m_95064_() {
      return this.f_95054_;
   }

   static record GlyphInfoFilter(GlyphInfo f_243013_, GlyphInfo f_243006_) {
      static final GlyphInfoFilter f_243023_;

      GlyphInfoFilter(GlyphInfo glyphInfo, GlyphInfo glyphInfoNotFishy) {
         this.f_243013_ = glyphInfo;
         this.f_243006_ = glyphInfoNotFishy;
      }

      GlyphInfo m_243099_(boolean notFishyIn) {
         return notFishyIn ? this.f_243006_ : this.f_243013_;
      }

      public GlyphInfo f_243013_() {
         return this.f_243013_;
      }

      public GlyphInfo f_243006_() {
         return this.f_243006_;
      }

      static {
         f_243023_ = new GlyphInfoFilter(SpecialGlyphs.MISSING, SpecialGlyphs.MISSING);
      }
   }
}
