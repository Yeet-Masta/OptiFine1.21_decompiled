import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import io.netty.buffer.ByteBuf;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_197_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_262716_;
import net.minecraft.src.C_313613_;
import net.minecraft.src.C_313866_;
import net.minecraft.src.C_4713_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_197_.C_212981_;
import net.minecraft.src.C_262716_.C_262714_;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public enum Direction implements C_197_ {
   a(0, 1, -1, "down", Direction.b.b, Direction.a.b, new C_4713_(0, -1, 0)),
   b(1, 0, -1, "up", Direction.b.a, Direction.a.b, new C_4713_(0, 1, 0)),
   c(2, 3, 2, "north", Direction.b.b, Direction.a.c, new C_4713_(0, 0, -1)),
   d(3, 2, 0, "south", Direction.b.a, Direction.a.c, new C_4713_(0, 0, 1)),
   e(4, 5, 1, "west", Direction.b.b, Direction.a.a, new C_4713_(-1, 0, 0)),
   f(5, 4, 3, "east", Direction.b.a, Direction.a.a, new C_4713_(1, 0, 0));

   public static final C_212981_<Direction> g = C_197_.m_216439_(Direction::values);
   public static final Codec<Direction> h = g.validate(Direction::a);
   public static final IntFunction<Direction> i = C_262716_.m_262839_(Direction::d, values(), C_262714_.WRAP);
   public static final C_313866_<ByteBuf, Direction> j = C_313613_.m_321301_(i, Direction::d);
   private final int k;
   private final int l;
   private final int m;
   private final String n;
   private final Direction.a o;
   private final Direction.b p;
   private final C_4713_ q;
   public static final Direction[] r = values();
   public static final Direction[] s = (Direction[])Arrays.stream(r).sorted(Comparator.comparingInt(dirIn -> dirIn.k)).toArray(Direction[]::new);
   private static final Direction[] t = (Direction[])Arrays.stream(r)
      .filter(dirIn -> dirIn.o().d())
      .sorted(Comparator.comparingInt(dir2In -> dir2In.m))
      .toArray(Direction[]::new);

   private Direction(
      final int indexIn,
      final int oppositeIn,
      final int horizontalIndexIn,
      final String nameIn,
      final Direction.b axisDirectionIn,
      final Direction.a axisIn,
      final C_4713_ directionVecIn
   ) {
      this.k = indexIn;
      this.m = horizontalIndexIn;
      this.l = oppositeIn;
      this.n = nameIn;
      this.o = axisIn;
      this.p = axisDirectionIn;
      this.q = directionVecIn;
   }

   public static Direction[] a(C_507_ entityIn) {
      float f = entityIn.m_5686_(1.0F) * (float) (Math.PI / 180.0);
      float f1 = -entityIn.m_5675_(1.0F) * (float) (Math.PI / 180.0);
      float f2 = Mth.a(f);
      float f3 = Mth.b(f);
      float f4 = Mth.a(f1);
      float f5 = Mth.b(f1);
      boolean flag = f4 > 0.0F;
      boolean flag1 = f2 < 0.0F;
      boolean flag2 = f5 > 0.0F;
      float f6 = flag ? f4 : -f4;
      float f7 = flag1 ? -f2 : f2;
      float f8 = flag2 ? f5 : -f5;
      float f9 = f6 * f3;
      float f10 = f8 * f3;
      Direction direction = flag ? Direction.f : e;
      Direction direction1 = flag1 ? b : a;
      Direction direction2 = flag2 ? d : c;
      if (f6 > f8) {
         if (f7 > f9) {
            return a(direction1, direction, direction2);
         } else {
            return f10 > f7 ? a(direction, direction2, direction1) : a(direction, direction1, direction2);
         }
      } else if (f7 > f10) {
         return a(direction1, direction2, direction);
      } else {
         return f9 > f7 ? a(direction2, direction, direction1) : a(direction2, direction1, direction);
      }
   }

   private static Direction[] a(Direction first, Direction second, Direction third) {
      return new Direction[]{first, second, third, third.g(), second.g(), first.g()};
   }

   public static Direction a(Matrix4f matrixIn, Direction directionIn) {
      C_4713_ vec3i = directionIn.q();
      Vector4f vector4f = matrixIn.transform(new Vector4f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), 0.0F));
      return a(vector4f.x(), vector4f.y(), vector4f.z());
   }

   public static Collection<Direction> a(C_212974_ randomIn) {
      return Util.<Direction>b(values(), randomIn);
   }

   public static Stream<Direction> a() {
      return Stream.of(r);
   }

   public Quaternionf b() {
      return switch (this) {
         case a -> new Quaternionf().rotationX((float) Math.PI);
         case b -> new Quaternionf();
         case c -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) Math.PI);
         case d -> new Quaternionf().rotationX((float) (Math.PI / 2));
         case e -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (Math.PI / 2));
         case f -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (-Math.PI / 2));
      };
   }

   public int d() {
      return this.k;
   }

   public int e() {
      return this.m;
   }

   public Direction.b f() {
      return this.p;
   }

   public static Direction a(C_507_ entityIn, Direction.a axisIn) {
      return switch (axisIn) {
         case a -> f.a(entityIn.m_5675_(1.0F)) ? f : e;
         case b -> entityIn.m_5686_(1.0F) < 0.0F ? b : a;
         case c -> d.a(entityIn.m_5675_(1.0F)) ? d : c;
      };
   }

   public Direction g() {
      return r[this.l];
   }

   public Direction a(Direction.a axisIn) {
      return switch (axisIn) {
         case a -> this != e && this != f ? this.r() : this;
         case b -> this != b && this != a ? this.h() : this;
         case c -> this != c && this != d ? this.t() : this;
      };
   }

   public Direction b(Direction.a axisIn) {
      return switch (axisIn) {
         case a -> this != e && this != f ? this.s() : this;
         case b -> this != b && this != a ? this.i() : this;
         case c -> this != c && this != d ? this.u() : this;
      };
   }

   public Direction h() {
      return switch (this) {
         case c -> f;
         case d -> e;
         case e -> c;
         case f -> d;
         default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      };
   }

   private Direction r() {
      return switch (this) {
         case a -> d;
         case b -> c;
         case c -> a;
         case d -> b;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private Direction s() {
      return switch (this) {
         case a -> c;
         case b -> d;
         case c -> b;
         case d -> a;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private Direction t() {
      return switch (this) {
         case a -> e;
         case b -> f;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case e -> b;
         case f -> a;
      };
   }

   private Direction u() {
      return switch (this) {
         case a -> f;
         case b -> e;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case e -> a;
         case f -> b;
      };
   }

   public Direction i() {
      return switch (this) {
         case c -> e;
         case d -> f;
         case e -> d;
         case f -> c;
         default -> throw new IllegalStateException("Unable to get CCW facing of " + this);
      };
   }

   public int j() {
      return this.q.m_123341_();
   }

   public int k() {
      return this.q.m_123342_();
   }

   public int l() {
      return this.q.m_123343_();
   }

   public Vector3f m() {
      return new Vector3f((float)this.j(), (float)this.k(), (float)this.l());
   }

   public String n() {
      return this.n;
   }

   public Direction.a o() {
      return this.o;
   }

   @Nullable
   public static Direction a(@Nullable String name) {
      return (Direction)g.m_216455_(name);
   }

   public static Direction a(int index) {
      return s[Mth.a(index % s.length)];
   }

   public static Direction b(int horizontalIndexIn) {
      return t[Mth.a(horizontalIndexIn % t.length)];
   }

   @Nullable
   public static Direction a(int dx, int dy, int dz) {
      if (dx == 0) {
         if (dy == 0) {
            if (dz > 0) {
               return d;
            }

            if (dz < 0) {
               return c;
            }
         } else if (dz == 0) {
            if (dy > 0) {
               return b;
            }

            return a;
         }
      } else if (dy == 0 && dz == 0) {
         if (dx > 0) {
            return f;
         }

         return e;
      }

      return null;
   }

   public static Direction a(double angle) {
      return b(Mth.a(angle / 90.0 + 0.5) & 3);
   }

   public static Direction a(Direction.a axisIn, Direction.b axisDirectionIn) {
      return switch (axisIn) {
         case a -> axisDirectionIn == Direction.b.a ? f : e;
         case b -> axisDirectionIn == Direction.b.a ? b : a;
         case c -> axisDirectionIn == Direction.b.a ? d : c;
      };
   }

   public float p() {
      return (float)((this.m & 3) * 90);
   }

   public static Direction b(C_212974_ randomIn) {
      return Util.a(r, randomIn);
   }

   public static Direction a(double x, double y, double z) {
      return a((float)x, (float)y, (float)z);
   }

   public static Direction a(float x, float y, float z) {
      Direction direction = c;
      float f = Float.MIN_VALUE;

      for (Direction direction1 : r) {
         float f1 = x * (float)direction1.q.m_123341_() + y * (float)direction1.q.m_123342_() + z * (float)direction1.q.m_123343_();
         if (f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public static Direction a(Vec3 vecIn) {
      return a(vecIn.c, vecIn.d, vecIn.e);
   }

   public String toString() {
      return this.n;
   }

   public String m_7912_() {
      return this.n;
   }

   private static DataResult<Direction> a(Direction directionIn) {
      return directionIn.o().b() ? DataResult.success(directionIn) : DataResult.error(() -> "Expected a vertical direction");
   }

   public static Direction a(Direction.b axisDirectionIn, Direction.a axisIn) {
      for (Direction direction : r) {
         if (direction.f() == axisDirectionIn && direction.o() == axisIn) {
            return direction;
         }
      }

      throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn);
   }

   public C_4713_ q() {
      return this.q;
   }

   public boolean a(float angleIn) {
      float f = angleIn * (float) (Math.PI / 180.0);
      float f1 = -Mth.a(f);
      float f2 = Mth.b(f);
      return (float)this.q.m_123341_() * f1 + (float)this.q.m_123343_() * f2 > 0.0F;
   }

   public static Direction getNearestStable(float x, float y, float z) {
      Direction direction = c;
      float f = Float.MIN_VALUE;

      for (Direction direction1 : r) {
         float f1 = x * (float)direction1.q.m_123341_() + y * (float)direction1.q.m_123342_() + z * (float)direction1.q.m_123343_();
         if (f1 > f + 1.0E-6F) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public static enum a implements C_197_, Predicate<Direction> {
      a("x") {
         @Override
         public int a(int x, int y, int z) {
            return x;
         }

         @Override
         public double a(double x, double y, double z) {
            return x;
         }
      },
      b("y") {
         @Override
         public int a(int x, int y, int z) {
            return y;
         }

         @Override
         public double a(double x, double y, double z) {
            return y;
         }
      },
      c("z") {
         @Override
         public int a(int x, int y, int z) {
            return z;
         }

         @Override
         public double a(double x, double y, double z) {
            return z;
         }
      };

      public static final Direction.a[] d = values();
      public static final C_212981_<Direction.a> e = C_197_.m_216439_(Direction.a::values);
      private final String f;

      private a(final String nameIn) {
         this.f = nameIn;
      }

      @Nullable
      public static Direction.a a(String name) {
         return (Direction.a)e.m_216455_(name);
      }

      public String a() {
         return this.f;
      }

      public boolean b() {
         return this == b;
      }

      public boolean d() {
         return this == a || this == c;
      }

      public String toString() {
         return this.f;
      }

      public static Direction.a a(C_212974_ randomIn) {
         return Util.a(d, randomIn);
      }

      public boolean a(@Nullable Direction p_test_1_) {
         return p_test_1_ != null && p_test_1_.o() == this;
      }

      public Direction.c e() {
         return switch (this) {
            case a, c -> Direction.c.a;
            case b -> Direction.c.b;
         };
      }

      public String m_7912_() {
         return this.f;
      }

      public abstract int a(int var1, int var2, int var3);

      public abstract double a(double var1, double var3, double var5);
   }

   public static enum b {
      a(1, "Towards positive"),
      b(-1, "Towards negative");

      private final int c;
      private final String d;

      private b(final int offset, final String description) {
         this.c = offset;
         this.d = description;
      }

      public int a() {
         return this.c;
      }

      public String b() {
         return this.d;
      }

      public String toString() {
         return this.d;
      }

      public Direction.b c() {
         return this == a ? b : a;
      }
   }

   public static enum c implements Iterable<Direction>, Predicate<Direction> {
      a(new Direction[]{Direction.c, Direction.f, Direction.d, Direction.e}, new Direction.a[]{Direction.a.a, Direction.a.c}),
      b(new Direction[]{Direction.b, Direction.a}, new Direction.a[]{Direction.a.b});

      private final Direction[] c;
      private final Direction.a[] d;

      private c(final Direction[] facingValuesIn, final Direction.a[] axisValuesIn) {
         this.c = facingValuesIn;
         this.d = axisValuesIn;
      }

      public Direction a(C_212974_ randomIn) {
         return Util.a(this.c, randomIn);
      }

      public Direction.a b(C_212974_ randomIn) {
         return Util.a(this.d, randomIn);
      }

      public boolean a(@Nullable Direction p_test_1_) {
         return p_test_1_ != null && p_test_1_.o().e() == this;
      }

      public Iterator<Direction> iterator() {
         return Iterators.forArray(this.c);
      }

      public Stream<Direction> a() {
         return Arrays.stream(this.c);
      }

      public List<Direction> c(C_212974_ randomIn) {
         return Util.b(this.c, randomIn);
      }

      public int b() {
         return this.c.length;
      }
   }
}
