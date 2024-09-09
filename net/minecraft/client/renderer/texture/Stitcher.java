package net.minecraft.client.renderer.texture;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class Stitcher {
   private static final Comparator f_118161_ = Comparator.comparing((holderIn) -> {
      return -holderIn.f_118204_;
   }).thenComparing((holderIn) -> {
      return -holderIn.f_118203_;
   }).thenComparing((holderIn) -> {
      return holderIn.f_244486_.m_246162_();
   });
   private final int f_118162_;
   private final List f_118163_ = new ArrayList();
   private final List f_118164_ = new ArrayList();
   private int f_118165_;
   private int f_118166_;
   private final int f_118167_;
   private final int f_118168_;

   public Stitcher(int mipmapLevelIn, int maxWidthIn, int maxHeightIn) {
      this.f_118162_ = maxHeightIn;
      this.f_118167_ = mipmapLevelIn;
      this.f_118168_ = maxWidthIn;
   }

   public int m_118174_() {
      return this.f_118165_;
   }

   public int m_118187_() {
      return this.f_118166_;
   }

   public void m_246099_(Entry entryIn) {
      Holder holder = new Holder(entryIn, this.f_118162_);
      this.f_118163_.add(holder);
   }

   public void m_118193_() {
      List list = new ArrayList(this.f_118163_);
      list.sort(f_118161_);
      Iterator var2 = list.iterator();

      Holder holder;
      do {
         if (!var2.hasNext()) {
            return;
         }

         holder = (Holder)var2.next();
      } while(this.m_118178_(holder));

      throw new StitcherException(holder.f_244486_, (Collection)list.stream().map((holderIn) -> {
         return holderIn.f_244486_;
      }).collect(ImmutableList.toImmutableList()), this.f_118165_, this.f_118166_, this.f_118167_, this.f_118168_);
   }

   public void m_118180_(SpriteLoader spriteLoaderIn) {
      Iterator var2 = this.f_118164_.iterator();

      while(var2.hasNext()) {
         Region region = (Region)var2.next();
         region.m_246763_(spriteLoaderIn);
      }

   }

   static int m_118188_(int dimensionIn, int mipmapLevelIn) {
      return (dimensionIn >> mipmapLevelIn) + ((dimensionIn & (1 << mipmapLevelIn) - 1) == 0 ? 0 : 1) << mipmapLevelIn;
   }

   private boolean m_118178_(Holder holderIn) {
      Iterator var2 = this.f_118164_.iterator();

      Region region;
      do {
         if (!var2.hasNext()) {
            return this.m_118191_(holderIn);
         }

         region = (Region)var2.next();
      } while(!region.m_118221_(holderIn));

      return true;
   }

   private boolean m_118191_(Holder holderIn) {
      int i = Mth.m_14125_(this.f_118165_);
      int j = Mth.m_14125_(this.f_118166_);
      int k = Mth.m_14125_(this.f_118165_ + holderIn.f_118203_);
      int l = Mth.m_14125_(this.f_118166_ + holderIn.f_118204_);
      boolean flag1 = k <= this.f_118167_;
      boolean flag2 = l <= this.f_118168_;
      if (!flag1 && !flag2) {
         return false;
      } else {
         boolean flag3 = flag1 && i != k;
         boolean flag4 = flag2 && j != l;
         boolean flag;
         if (flag3 ^ flag4) {
            flag = flag3;
         } else {
            flag = flag1 && i <= j;
         }

         Region region;
         if (flag) {
            if (this.f_118166_ == 0) {
               this.f_118166_ = l;
            }

            region = new Region(this.f_118165_, 0, k - this.f_118165_, this.f_118166_);
            this.f_118165_ = k;
         } else {
            region = new Region(0, this.f_118166_, this.f_118165_, l - this.f_118166_);
            this.f_118166_ = l;
         }

         region.m_118221_(holderIn);
         this.f_118164_.add(region);
         return true;
      }
   }

   static record Holder(Entry f_244486_, int f_118203_, int f_118204_) {
      public Holder(Entry entry, int width) {
         this(entry, Stitcher.m_118188_(entry.m_246492_(), width), Stitcher.m_118188_(entry.m_245330_(), width));
      }

      Holder(Entry entry, int width, int height) {
         this.f_244486_ = entry;
         this.f_118203_ = width;
         this.f_118204_ = height;
      }

      public Entry f_244486_() {
         return this.f_244486_;
      }

      public int f_118203_() {
         return this.f_118203_;
      }

      public int f_118204_() {
         return this.f_118204_;
      }
   }

   public interface Entry {
      int m_246492_();

      int m_245330_();

      ResourceLocation m_246162_();
   }

   public static class Region {
      private final int f_118209_;
      private final int f_118210_;
      private final int f_118211_;
      private final int f_118212_;
      @Nullable
      private List f_118213_;
      @Nullable
      private Holder f_118214_;

      public Region(int originXIn, int originYIn, int widthIn, int heightIn) {
         this.f_118209_ = originXIn;
         this.f_118210_ = originYIn;
         this.f_118211_ = widthIn;
         this.f_118212_ = heightIn;
      }

      public int m_118225_() {
         return this.f_118209_;
      }

      public int m_118226_() {
         return this.f_118210_;
      }

      public boolean m_118221_(Holder holderIn) {
         if (this.f_118214_ != null) {
            return false;
         } else {
            int i = holderIn.f_118203_;
            int j = holderIn.f_118204_;
            if (i <= this.f_118211_ && j <= this.f_118212_) {
               if (i == this.f_118211_ && j == this.f_118212_) {
                  this.f_118214_ = holderIn;
                  return true;
               } else {
                  if (this.f_118213_ == null) {
                     this.f_118213_ = new ArrayList(1);
                     this.f_118213_.add(new Region(this.f_118209_, this.f_118210_, i, j));
                     int k = this.f_118211_ - i;
                     int l = this.f_118212_ - j;
                     if (l > 0 && k > 0) {
                        int i1 = Math.max(this.f_118212_, k);
                        int j1 = Math.max(this.f_118211_, l);
                        if (i1 >= j1) {
                           this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + j, i, l));
                           this.f_118213_.add(new Region(this.f_118209_ + i, this.f_118210_, k, this.f_118212_));
                        } else {
                           this.f_118213_.add(new Region(this.f_118209_ + i, this.f_118210_, k, j));
                           this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + j, this.f_118211_, l));
                        }
                     } else if (k == 0) {
                        this.f_118213_.add(new Region(this.f_118209_, this.f_118210_ + j, i, l));
                     } else if (l == 0) {
                        this.f_118213_.add(new Region(this.f_118209_ + i, this.f_118210_, k, j));
                     }
                  }

                  Iterator var8 = this.f_118213_.iterator();

                  Region region;
                  do {
                     if (!var8.hasNext()) {
                        return false;
                     }

                     region = (Region)var8.next();
                  } while(!region.m_118221_(holderIn));

                  return true;
               }
            } else {
               return false;
            }
         }
      }

      public void m_246763_(SpriteLoader spriteLoaderIn) {
         if (this.f_118214_ != null) {
            spriteLoaderIn.m_118228_(this.f_118214_.f_244486_, this.m_118225_(), this.m_118226_());
         } else if (this.f_118213_ != null) {
            Iterator var2 = this.f_118213_.iterator();

            while(var2.hasNext()) {
               Region region = (Region)var2.next();
               region.m_246763_(spriteLoaderIn);
            }
         }

      }

      public String toString() {
         int var10000 = this.f_118209_;
         return "Slot{originX=" + var10000 + ", originY=" + this.f_118210_ + ", width=" + this.f_118211_ + ", height=" + this.f_118212_ + ", texture=" + String.valueOf(this.f_118214_) + ", subSlots=" + String.valueOf(this.f_118213_) + "}";
      }
   }

   public interface SpriteLoader {
      void m_118228_(Entry var1, int var2, int var3);
   }
}
