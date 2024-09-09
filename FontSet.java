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
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_213324_;
import net.minecraft.src.C_213376_;
import net.minecraft.src.C_283725_;
import net.minecraft.src.C_283726_;
import net.minecraft.src.C_3098_;
import net.minecraft.src.C_3099_;
import net.minecraft.src.C_313440_;
import net.minecraft.src.C_3512_;
import net.minecraft.src.C_3099_.C_313852_;

public class FontSet implements AutoCloseable {
   private static final C_212974_ a = C_212974_.m_216327_();
   private static final float b = 32.0F;
   private final TextureManager c;
   private final ResourceLocation d;
   private BakedGlyph e;
   private BakedGlyph f;
   private List<C_313852_> g = List.of();
   private List<C_3099_> h = List.of();
   private final C_283725_<BakedGlyph> i = new C_283725_(BakedGlyph[]::new, BakedGlyph[][]::new);
   private final C_283725_<FontSet.a> j = new C_283725_(FontSet.a[]::new, FontSet.a[][]::new);
   private final Int2ObjectMap<IntList> k = new Int2ObjectOpenHashMap();
   private final List<C_3512_> l = Lists.newArrayList();

   public FontSet(TextureManager textureManagerIn, ResourceLocation resourceLocationIn) {
      this.c = textureManagerIn;
      this.d = resourceLocationIn;
   }

   public void a(List<C_313852_> conditionalsIn, Set<C_313440_> fontOptionsIn) {
      this.g = conditionalsIn;
      this.a(fontOptionsIn);
   }

   public void a(Set<C_313440_> glyphProvidersIn) {
      this.h = List.of();
      this.c();
      this.h = this.b(this.g, glyphProvidersIn);
   }

   private void c() {
      this.d();
      this.i.m_284192_();
      this.j.m_284192_();
      this.k.clear();
      this.e = C_213376_.MISSING.bake(this::a);
      this.f = C_213376_.WHITE.bake(this::a);
   }

   private List<C_3099_> b(List<C_313852_> conditionalsIn, Set<C_313440_> fontOptionsIn) {
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
                  ((IntList)this.k.computeIfAbsent(Mth.f(glyphinfo.m_83827_(false)), widthIn -> new IntArrayList())).add(charIn);
               }
               break;
            }
         }
      });
      return list.stream().filter(set::contains).toList();
   }

   public void close() {
      this.d();
   }

   private void d() {
      for (C_3512_ fonttexture : this.l) {
         fonttexture.close();
      }

      this.l.clear();
   }

   private static boolean b(C_3098_ infoIn) {
      float f = infoIn.m_83827_(false);
      if (!(f < 0.0F) && !(f > 32.0F)) {
         float f1 = infoIn.m_83827_(true);
         return f1 < 0.0F || f1 > 32.0F;
      } else {
         return true;
      }
   }

   private FontSet.a b(int charIn) {
      C_3098_ glyphinfo = null;

      for (C_3099_ glyphprovider : this.h) {
         C_3098_ glyphinfo1 = glyphprovider.m_214022_(charIn);
         if (glyphinfo1 != null) {
            if (glyphinfo == null) {
               glyphinfo = glyphinfo1;
            }

            if (!b(glyphinfo1)) {
               return new FontSet.a(glyphinfo, glyphinfo1);
            }
         }
      }

      return glyphinfo != null ? new FontSet.a(glyphinfo, C_213376_.MISSING) : FontSet.a.c;
   }

   public C_3098_ a(int charIn, boolean notFishyIn) {
      FontSet.a gif = (FontSet.a)this.j.m_284412_(charIn);
      return gif != null ? gif.a(notFishyIn) : ((FontSet.a)this.j.m_284450_(charIn, this::b)).a(notFishyIn);
   }

   private BakedGlyph c(int charIn) {
      for (C_3099_ glyphprovider : this.h) {
         C_3098_ glyphinfo = glyphprovider.m_214022_(charIn);
         if (glyphinfo != null) {
            return glyphinfo.bake(this::a);
         }
      }

      return this.e;
   }

   public BakedGlyph a(int character) {
      BakedGlyph bg = (BakedGlyph)this.i.m_284412_(character);
      return bg != null ? bg : (BakedGlyph)this.i.m_284450_(character, this::c);
   }

   private BakedGlyph a(C_213324_ glyphInfoIn) {
      for (C_3512_ fonttexture : this.l) {
         BakedGlyph bakedglyph = fonttexture.a(glyphInfoIn);
         if (bakedglyph != null) {
            return bakedglyph;
         }
      }

      ResourceLocation resourcelocation = this.d.g("/" + this.l.size());
      boolean flag = glyphInfoIn.m_213965_();
      C_283726_ glyphrendertypes = flag ? C_283726_.b(resourcelocation) : C_283726_.a(resourcelocation);
      C_3512_ fonttexture1 = new C_3512_(glyphrendertypes, flag);
      this.l.add(fonttexture1);
      this.c.a(resourcelocation, fonttexture1);
      BakedGlyph bakedglyph1 = fonttexture1.a(glyphInfoIn);
      return bakedglyph1 == null ? this.e : bakedglyph1;
   }

   public BakedGlyph a(C_3098_ glyph) {
      IntList intlist = (IntList)this.k.get(Mth.f(glyph.m_83827_(false)));
      return intlist != null && !intlist.isEmpty() ? this.a(intlist.getInt(a.m_188503_(intlist.size()))) : this.e;
   }

   public ResourceLocation a() {
      return this.d;
   }

   public BakedGlyph b() {
      return this.f;
   }

   static record a(C_3098_ a, C_3098_ b) {
      static final FontSet.a c = new FontSet.a(C_213376_.MISSING, C_213376_.MISSING);

      C_3098_ a(boolean notFishyIn) {
         return notFishyIn ? this.b : this.a;
      }
   }
}
