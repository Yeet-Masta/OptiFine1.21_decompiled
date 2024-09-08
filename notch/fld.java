package net.minecraft.src;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.src.C_3099_.C_313852_;

public class C_3511_ implements AutoCloseable {
   private static final C_212974_ f_95050_ = C_212974_.m_216327_();
   private static final float f_242991_ = 32.0F;
   private final C_4490_ f_95051_;
   private final C_5265_ f_95052_;
   private C_3516_ f_95053_;
   private C_3516_ f_95054_;
   private List<C_313852_> f_315683_ = List.of();
   private List<C_3099_> f_317127_ = List.of();
   private final C_283725_<C_3516_> f_95056_ = new C_283725_(C_3516_[]::new, C_3516_[][]::new);
   private final C_283725_<C_3511_.C_242985_> f_95057_ = new C_283725_(C_3511_.C_242985_[]::new, C_3511_.C_242985_[][]::new);
   private final Int2ObjectMap<IntList> f_95058_ = new Int2ObjectOpenHashMap();
   private final List<C_3512_> f_95059_ = Lists.newArrayList();

   public C_3511_(C_4490_ textureManagerIn, C_5265_ resourceLocationIn) {
      this.f_95051_ = textureManagerIn;
      this.f_95052_ = resourceLocationIn;
   }

   public void m_321905_(List<C_313852_> conditionalsIn, Set<C_313440_> fontOptionsIn) {
      this.f_315683_ = conditionalsIn;
      this.m_95071_(fontOptionsIn);
   }

   public void m_95071_(Set<C_313440_> glyphProvidersIn) {
      this.f_317127_ = List.of();
      this.m_322787_();
      this.f_317127_ = this.m_321621_(this.f_315683_, glyphProvidersIn);
   }

   private void m_322787_() {
      this.m_95080_();
      this.f_95056_.m_284192_();
      this.f_95057_.m_284192_();
      this.f_95058_.clear();
      this.f_95053_ = C_213376_.MISSING.m_213604_(this::m_232556_);
      this.f_95054_ = C_213376_.WHITE.m_213604_(this::m_232556_);
   }

   private List<C_3099_> m_321621_(List<C_313852_> conditionalsIn, Set<C_313440_> fontOptionsIn) {
      IntSet intset = new IntOpenHashSet();
      List<C_3099_> list = new ArrayList();

      for (C_313852_ glyphprovider$conditional : conditionalsIn) {
         if (glyphprovider$conditional.f_316533_().m_319512_(fontOptionsIn)) {
            list.add(glyphprovider$conditional.f_316017_());
            intset.addAll(glyphprovider$conditional.f_316017_().m_6990_());
         }
      }

      Set<C_3099_> set = Sets.newHashSet();
      intset.forEach(charIn -> {
         for (C_3099_ glyphprovider : list) {
            C_3098_ glyphinfo = glyphprovider.m_214022_(charIn);
            if (glyphinfo != null) {
               set.add(glyphprovider);
               if (glyphinfo != C_213376_.MISSING) {
                  ((IntList)this.f_95058_.computeIfAbsent(C_188_.m_14167_(glyphinfo.m_83827_(false)), widthIn -> new IntArrayList())).add(charIn);
               }
               break;
            }
         }
      });
      return list.stream().filter(set::contains).toList();
   }

   public void close() {
      this.m_95080_();
   }

   private void m_95080_() {
      for (C_3512_ fonttexture : this.f_95059_) {
         fonttexture.close();
      }

      this.f_95059_.clear();
   }

   private static boolean m_243068_(C_3098_ infoIn) {
      float f = infoIn.m_83827_(false);
      if (!(f < 0.0F) && !(f > 32.0F)) {
         float f1 = infoIn.m_83827_(true);
         return f1 < 0.0F || f1 > 32.0F;
      } else {
         return true;
      }
   }

   private C_3511_.C_242985_ m_243121_(int charIn) {
      C_3098_ glyphinfo = null;

      for (C_3099_ glyphprovider : this.f_317127_) {
         C_3098_ glyphinfo1 = glyphprovider.m_214022_(charIn);
         if (glyphinfo1 != null) {
            if (glyphinfo == null) {
               glyphinfo = glyphinfo1;
            }

            if (!m_243068_(glyphinfo1)) {
               return new C_3511_.C_242985_(glyphinfo, glyphinfo1);
            }
         }
      }

      return glyphinfo != null ? new C_3511_.C_242985_(glyphinfo, C_213376_.MISSING) : C_3511_.C_242985_.f_243023_;
   }

   public C_3098_ m_243128_(int charIn, boolean notFishyIn) {
      C_3511_.C_242985_ gif = (C_3511_.C_242985_)this.f_95057_.m_284412_(charIn);
      return gif != null ? gif.m_243099_(notFishyIn) : ((C_3511_.C_242985_)this.f_95057_.m_284450_(charIn, this::m_243121_)).m_243099_(notFishyIn);
   }

   private C_3516_ m_232564_(int charIn) {
      for (C_3099_ glyphprovider : this.f_317127_) {
         C_3098_ glyphinfo = glyphprovider.m_214022_(charIn);
         if (glyphinfo != null) {
            return glyphinfo.m_213604_(this::m_232556_);
         }
      }

      return this.f_95053_;
   }

   public C_3516_ m_95078_(int character) {
      C_3516_ bg = (C_3516_)this.f_95056_.m_284412_(character);
      return bg != null ? bg : (C_3516_)this.f_95056_.m_284450_(character, this::m_232564_);
   }

   private C_3516_ m_232556_(C_213324_ glyphInfoIn) {
      for (C_3512_ fonttexture : this.f_95059_) {
         C_3516_ bakedglyph = fonttexture.m_232568_(glyphInfoIn);
         if (bakedglyph != null) {
            return bakedglyph;
         }
      }

      C_5265_ resourcelocation = this.f_95052_.m_266382_("/" + this.f_95059_.size());
      boolean flag = glyphInfoIn.m_213965_();
      C_283726_ glyphrendertypes = flag ? C_283726_.m_284354_(resourcelocation) : C_283726_.m_284520_(resourcelocation);
      C_3512_ fonttexture1 = new C_3512_(glyphrendertypes, flag);
      this.f_95059_.add(fonttexture1);
      this.f_95051_.m_118495_(resourcelocation, fonttexture1);
      C_3516_ bakedglyph1 = fonttexture1.m_232568_(glyphInfoIn);
      return bakedglyph1 == null ? this.f_95053_ : bakedglyph1;
   }

   public C_3516_ m_95067_(C_3098_ glyph) {
      IntList intlist = (IntList)this.f_95058_.get(C_188_.m_14167_(glyph.m_83827_(false)));
      return intlist != null && !intlist.isEmpty() ? this.m_95078_(intlist.getInt(f_95050_.m_188503_(intlist.size()))) : this.f_95053_;
   }

   public C_5265_ m_321601_() {
      return this.f_95052_;
   }

   public C_3516_ m_95064_() {
      return this.f_95054_;
   }

   static record C_242985_(C_3098_ f_243013_, C_3098_ f_243006_) {
      static final C_3511_.C_242985_ f_243023_ = new C_3511_.C_242985_(C_213376_.MISSING, C_213376_.MISSING);

      C_3098_ m_243099_(boolean notFishyIn) {
         return notFishyIn ? this.f_243006_ : this.f_243013_;
      }
   }
}
