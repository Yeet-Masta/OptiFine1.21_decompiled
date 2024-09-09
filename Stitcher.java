import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public class Stitcher<T extends Stitcher.a> {
   private static final Comparator<Stitcher.b<?>> a = Comparator.comparing(holderIn -> -holderIn.c)
      .thenComparing(holderIn -> -holderIn.b)
      .thenComparing(holderIn -> holderIn.a.c());
   private final int b;
   private final List<Stitcher.b<T>> c = new ArrayList();
   private final List<Stitcher.c<T>> d = new ArrayList();
   private int e;
   private int f;
   private final int g;
   private final int h;

   public Stitcher(int mipmapLevelIn, int maxWidthIn, int maxHeightIn) {
      this.b = maxHeightIn;
      this.g = mipmapLevelIn;
      this.h = maxWidthIn;
   }

   public int a() {
      return this.e;
   }

   public int b() {
      return this.f;
   }

   public void a(T entryIn) {
      Stitcher.b<T> holder = new Stitcher.b<>(entryIn, this.b);
      this.c.add(holder);
   }

   public void c() {
      List<Stitcher.b<T>> list = new ArrayList(this.c);
      list.sort(a);

      for (Stitcher.b<T> holder : list) {
         if (!this.a(holder)) {
            throw new StitcherException(
               holder.a,
               (Collection<Stitcher.a>)list.stream().map(holderIn -> holderIn.a).collect(ImmutableList.toImmutableList()),
               this.e,
               this.f,
               this.g,
               this.h
            );
         }
      }
   }

   public void a(Stitcher.d<T> spriteLoaderIn) {
      for (Stitcher.c<T> region : this.d) {
         region.a(spriteLoaderIn);
      }
   }

   static int a(int dimensionIn, int mipmapLevelIn) {
      return (dimensionIn >> mipmapLevelIn) + ((dimensionIn & (1 << mipmapLevelIn) - 1) == 0 ? 0 : 1) << mipmapLevelIn;
   }

   private boolean a(Stitcher.b<T> holderIn) {
      for (Stitcher.c<T> region : this.d) {
         if (region.a(holderIn)) {
            return true;
         }
      }

      return this.b(holderIn);
   }

   private boolean b(Stitcher.b<T> holderIn) {
      int i = Mth.c(this.e);
      int j = Mth.c(this.f);
      int k = Mth.c(this.e + holderIn.b);
      int l = Mth.c(this.f + holderIn.c);
      boolean flag1 = k <= this.g;
      boolean flag2 = l <= this.h;
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

         Stitcher.c<T> region;
         if (flag) {
            if (this.f == 0) {
               this.f = l;
            }

            region = new Stitcher.c<>(this.e, 0, k - this.e, this.f);
            this.e = k;
         } else {
            region = new Stitcher.c<>(0, this.f, this.e, l - this.f);
            this.f = l;
         }

         region.a(holderIn);
         this.d.add(region);
         return true;
      }
   }

   public interface a {
      int a();

      int b();

      ResourceLocation c();
   }

   static record b<T extends Stitcher.a>(T a, int b, int c) {
      public b(T entry, int width) {
         this(entry, Stitcher.a(entry.a(), width), Stitcher.a(entry.b(), width));
      }
   }

   public static class c<T extends Stitcher.a> {
      private final int a;
      private final int b;
      private final int c;
      private final int d;
      @Nullable
      private List<Stitcher.c<T>> e;
      @Nullable
      private Stitcher.b<T> f;

      public c(int originXIn, int originYIn, int widthIn, int heightIn) {
         this.a = originXIn;
         this.b = originYIn;
         this.c = widthIn;
         this.d = heightIn;
      }

      public int a() {
         return this.a;
      }

      public int b() {
         return this.b;
      }

      public boolean a(Stitcher.b<T> holderIn) {
         if (this.f != null) {
            return false;
         } else {
            int i = holderIn.b;
            int j = holderIn.c;
            if (i <= this.c && j <= this.d) {
               if (i == this.c && j == this.d) {
                  this.f = holderIn;
                  return true;
               } else {
                  if (this.e == null) {
                     this.e = new ArrayList(1);
                     this.e.add(new Stitcher.c(this.a, this.b, i, j));
                     int k = this.c - i;
                     int l = this.d - j;
                     if (l > 0 && k > 0) {
                        int i1 = Math.max(this.d, k);
                        int j1 = Math.max(this.c, l);
                        if (i1 >= j1) {
                           this.e.add(new Stitcher.c(this.a, this.b + j, i, l));
                           this.e.add(new Stitcher.c(this.a + i, this.b, k, this.d));
                        } else {
                           this.e.add(new Stitcher.c(this.a + i, this.b, k, j));
                           this.e.add(new Stitcher.c(this.a, this.b + j, this.c, l));
                        }
                     } else if (k == 0) {
                        this.e.add(new Stitcher.c(this.a, this.b + j, i, l));
                     } else if (l == 0) {
                        this.e.add(new Stitcher.c(this.a + i, this.b, k, j));
                     }
                  }

                  for (Stitcher.c<T> region : this.e) {
                     if (region.a(holderIn)) {
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

      public void a(Stitcher.d<T> spriteLoaderIn) {
         if (this.f != null) {
            spriteLoaderIn.load(this.f.a, this.a(), this.b());
         } else if (this.e != null) {
            for (Stitcher.c<T> region : this.e) {
               region.a(spriteLoaderIn);
            }
         }
      }

      public String toString() {
         return "Slot{originX="
            + this.a
            + ", originY="
            + this.b
            + ", width="
            + this.c
            + ", height="
            + this.d
            + ", texture="
            + this.f
            + ", subSlots="
            + this.e
            + "}";
      }
   }

   public interface d<T extends Stitcher.a> {
      void load(T var1, int var2, int var3);
   }
}
