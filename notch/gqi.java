package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public class C_4478_<T extends C_4478_.C_243583_> {
   private static final Comparator<C_4478_.C_4479_<?>> f_118161_ = Comparator.comparing(holderIn -> -holderIn.f_118204_)
      .thenComparing(holderIn -> -holderIn.f_118203_)
      .thenComparing(holderIn -> holderIn.f_244486_.m_246162_());
   private final int f_118162_;
   private final List<C_4478_.C_4479_<T>> f_118163_ = new ArrayList();
   private final List<C_4478_.C_4480_<T>> f_118164_ = new ArrayList();
   private int f_118165_;
   private int f_118166_;
   private final int f_118167_;
   private final int f_118168_;

   public C_4478_(int mipmapLevelIn, int maxWidthIn, int maxHeightIn) {
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

   public void m_246099_(T entryIn) {
      C_4478_.C_4479_<T> holder = new C_4478_.C_4479_<>(entryIn, this.f_118162_);
      this.f_118163_.add(holder);
   }

   public void m_118193_() {
      List<C_4478_.C_4479_<T>> list = new ArrayList(this.f_118163_);
      list.sort(f_118161_);

      for (C_4478_.C_4479_<T> holder : list) {
         if (!this.m_118178_(holder)) {
            throw new C_4483_(
               holder.f_244486_,
               (Collection<C_4478_.C_243583_>)list.stream().map(holderIn -> holderIn.f_244486_).collect(ImmutableList.toImmutableList()),
               this.f_118165_,
               this.f_118166_,
               this.f_118167_,
               this.f_118168_
            );
         }
      }
   }

   public void m_118180_(C_4478_.C_4481_<T> spriteLoaderIn) {
      for (C_4478_.C_4480_<T> region : this.f_118164_) {
         region.m_246763_(spriteLoaderIn);
      }
   }

   static int m_118188_(int dimensionIn, int mipmapLevelIn) {
      return (dimensionIn >> mipmapLevelIn) + ((dimensionIn & (1 << mipmapLevelIn) - 1) == 0 ? 0 : 1) << mipmapLevelIn;
   }

   private boolean m_118178_(C_4478_.C_4479_<T> holderIn) {
      for (C_4478_.C_4480_<T> region : this.f_118164_) {
         if (region.m_118221_(holderIn)) {
            return true;
         }
      }

      return this.m_118191_(holderIn);
   }

   private boolean m_118191_(C_4478_.C_4479_<T> holderIn) {
      int i = C_188_.m_14125_(this.f_118165_);
      int j = C_188_.m_14125_(this.f_118166_);
      int k = C_188_.m_14125_(this.f_118165_ + holderIn.f_118203_);
      int l = C_188_.m_14125_(this.f_118166_ + holderIn.f_118204_);
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

         C_4478_.C_4480_<T> region;
         if (flag) {
            if (this.f_118166_ == 0) {
               this.f_118166_ = l;
            }

            region = new C_4478_.C_4480_<>(this.f_118165_, 0, k - this.f_118165_, this.f_118166_);
            this.f_118165_ = k;
         } else {
            region = new C_4478_.C_4480_<>(0, this.f_118166_, this.f_118165_, l - this.f_118166_);
            this.f_118166_ = l;
         }

         region.m_118221_(holderIn);
         this.f_118164_.add(region);
         return true;
      }
   }

   public interface C_243583_ {
      int m_246492_();

      int m_245330_();

      C_5265_ m_246162_();
   }

   static record C_4479_<T extends C_4478_.C_243583_>(T f_244486_, int f_118203_, int f_118204_) {
      public C_4479_(T entry, int width) {
         this(entry, C_4478_.m_118188_(entry.m_246492_(), width), C_4478_.m_118188_(entry.m_245330_(), width));
      }
   }

   public static class C_4480_<T extends C_4478_.C_243583_> {
      private final int f_118209_;
      private final int f_118210_;
      private final int f_118211_;
      private final int f_118212_;
      @Nullable
      private List<C_4478_.C_4480_<T>> f_118213_;
      @Nullable
      private C_4478_.C_4479_<T> f_118214_;

      public C_4480_(int originXIn, int originYIn, int widthIn, int heightIn) {
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

      public boolean m_118221_(C_4478_.C_4479_<T> holderIn) {
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
                     this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_, this.f_118210_, i, j));
                     int k = this.f_118211_ - i;
                     int l = this.f_118212_ - j;
                     if (l > 0 && k > 0) {
                        int i1 = Math.max(this.f_118212_, k);
                        int j1 = Math.max(this.f_118211_, l);
                        if (i1 >= j1) {
                           this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_, this.f_118210_ + j, i, l));
                           this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_ + i, this.f_118210_, k, this.f_118212_));
                        } else {
                           this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_ + i, this.f_118210_, k, j));
                           this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_, this.f_118210_ + j, this.f_118211_, l));
                        }
                     } else if (k == 0) {
                        this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_, this.f_118210_ + j, i, l));
                     } else if (l == 0) {
                        this.f_118213_.add(new C_4478_.C_4480_(this.f_118209_ + i, this.f_118210_, k, j));
                     }
                  }

                  for (C_4478_.C_4480_<T> region : this.f_118213_) {
                     if (region.m_118221_(holderIn)) {
                        return true;
                     }
                  }

                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public void m_246763_(C_4478_.C_4481_<T> spriteLoaderIn) {
         if (this.f_118214_ != null) {
            spriteLoaderIn.m_118228_(this.f_118214_.f_244486_, this.m_118225_(), this.m_118226_());
         } else if (this.f_118213_ != null) {
            for (C_4478_.C_4480_<T> region : this.f_118213_) {
               region.m_246763_(spriteLoaderIn);
            }
         }
      }

      public String toString() {
         return "Slot{originX="
            + this.f_118209_
            + ", originY="
            + this.f_118210_
            + ", width="
            + this.f_118211_
            + ", height="
            + this.f_118212_
            + ", texture="
            + this.f_118214_
            + ", subSlots="
            + this.f_118213_
            + "}";
      }
   }

   public interface C_4481_<T extends C_4478_.C_243583_> {
      void m_118228_(T var1, int var2, int var3);
   }
}
