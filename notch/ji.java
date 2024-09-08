package net.minecraft.src;

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
import net.minecraft.src.C_197_.C_212981_;
import net.minecraft.src.C_262716_.C_262714_;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public enum C_4687_ implements C_197_ {
   DOWN(0, 1, -1, "down", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.Y, new C_4713_(0, -1, 0)),
   UP(1, 0, -1, "up", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.Y, new C_4713_(0, 1, 0)),
   NORTH(2, 3, 2, "north", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.Z, new C_4713_(0, 0, -1)),
   SOUTH(3, 2, 0, "south", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.Z, new C_4713_(0, 0, 1)),
   WEST(4, 5, 1, "west", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.X, new C_4713_(-1, 0, 0)),
   EAST(5, 4, 3, "east", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.X, new C_4713_(1, 0, 0));

   public static final C_212981_<C_4687_> f_175356_ = C_197_.m_216439_(C_4687_::values);
   public static final Codec<C_4687_> f_194527_ = f_175356_.validate(C_4687_::m_194528_);
   public static final IntFunction<C_4687_> f_315953_ = C_262716_.m_262839_(C_4687_::m_122411_, values(), C_262714_.WRAP);
   public static final C_313866_<ByteBuf, C_4687_> f_315582_ = C_313613_.m_321301_(f_315953_, C_4687_::m_122411_);
   private final int f_122339_;
   private final int f_122340_;
   private final int f_122341_;
   private final String f_122342_;
   private final C_4687_.C_4689_ f_122343_;
   private final C_4687_.C_4693_ f_122344_;
   private final C_4713_ f_122345_;
   public static final C_4687_[] f_122346_ = values();
   public static final C_4687_[] f_122348_ = (C_4687_[])Arrays.stream(f_122346_)
      .sorted(Comparator.comparingInt(dirIn -> dirIn.f_122339_))
      .toArray(C_4687_[]::new);
   private static final C_4687_[] f_122349_ = (C_4687_[])Arrays.stream(f_122346_)
      .filter(dirIn -> dirIn.m_122434_().m_122479_())
      .sorted(Comparator.comparingInt(dir2In -> dir2In.f_122341_))
      .toArray(C_4687_[]::new);

   private C_4687_(
      final int indexIn,
      final int oppositeIn,
      final int horizontalIndexIn,
      final String nameIn,
      final C_4687_.C_4693_ axisDirectionIn,
      final C_4687_.C_4689_ axisIn,
      final C_4713_ directionVecIn
   ) {
      this.f_122339_ = indexIn;
      this.f_122341_ = horizontalIndexIn;
      this.f_122340_ = oppositeIn;
      this.f_122342_ = nameIn;
      this.f_122343_ = axisIn;
      this.f_122344_ = axisDirectionIn;
      this.f_122345_ = directionVecIn;
   }

   public static C_4687_[] m_122382_(C_507_ entityIn) {
      float f = entityIn.m_5686_(1.0F) * (float) (Math.PI / 180.0);
      float f1 = -entityIn.m_5675_(1.0F) * (float) (Math.PI / 180.0);
      float f2 = C_188_.m_14031_(f);
      float f3 = C_188_.m_14089_(f);
      float f4 = C_188_.m_14031_(f1);
      float f5 = C_188_.m_14089_(f1);
      boolean flag = f4 > 0.0F;
      boolean flag1 = f2 < 0.0F;
      boolean flag2 = f5 > 0.0F;
      float f6 = flag ? f4 : -f4;
      float f7 = flag1 ? -f2 : f2;
      float f8 = flag2 ? f5 : -f5;
      float f9 = f6 * f3;
      float f10 = f8 * f3;
      C_4687_ direction = flag ? EAST : WEST;
      C_4687_ direction1 = flag1 ? UP : DOWN;
      C_4687_ direction2 = flag2 ? SOUTH : NORTH;
      if (f6 > f8) {
         if (f7 > f9) {
            return m_122398_(direction1, direction, direction2);
         } else {
            return f10 > f7 ? m_122398_(direction, direction2, direction1) : m_122398_(direction, direction1, direction2);
         }
      } else if (f7 > f10) {
         return m_122398_(direction1, direction2, direction);
      } else {
         return f9 > f7 ? m_122398_(direction2, direction, direction1) : m_122398_(direction2, direction1, direction);
      }
   }

   private static C_4687_[] m_122398_(C_4687_ first, C_4687_ second, C_4687_ third) {
      return new C_4687_[]{first, second, third, third.m_122424_(), second.m_122424_(), first.m_122424_()};
   }

   public static C_4687_ m_252919_(Matrix4f matrixIn, C_4687_ directionIn) {
      C_4713_ vec3i = directionIn.m_122436_();
      Vector4f vector4f = matrixIn.transform(new Vector4f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), 0.0F));
      return m_122372_(vector4f.x(), vector4f.y(), vector4f.z());
   }

   public static Collection<C_4687_> m_235667_(C_212974_ randomIn) {
      return C_5322_.<C_4687_>m_214681_(values(), randomIn);
   }

   public static Stream<C_4687_> m_235666_() {
      return Stream.of(f_122346_);
   }

   public Quaternionf m_253075_() {
      return switch (this) {
         case DOWN -> new Quaternionf().rotationX((float) Math.PI);
         case UP -> new Quaternionf();
         case NORTH -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) Math.PI);
         case SOUTH -> new Quaternionf().rotationX((float) (Math.PI / 2));
         case WEST -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (Math.PI / 2));
         case EAST -> new Quaternionf().rotationXYZ((float) (Math.PI / 2), 0.0F, (float) (-Math.PI / 2));
      };
   }

   public int m_122411_() {
      return this.f_122339_;
   }

   public int m_122416_() {
      return this.f_122341_;
   }

   public C_4687_.C_4693_ m_122421_() {
      return this.f_122344_;
   }

   public static C_4687_ m_175357_(C_507_ entityIn, C_4687_.C_4689_ axisIn) {
      return switch (axisIn) {
         case X -> EAST.m_122370_(entityIn.m_5675_(1.0F)) ? EAST : WEST;
         case Y -> entityIn.m_5686_(1.0F) < 0.0F ? UP : DOWN;
         case Z -> SOUTH.m_122370_(entityIn.m_5675_(1.0F)) ? SOUTH : NORTH;
      };
   }

   public C_4687_ m_122424_() {
      return f_122346_[this.f_122340_];
   }

   public C_4687_ m_175362_(C_4687_.C_4689_ axisIn) {
      return switch (axisIn) {
         case X -> this != WEST && this != EAST ? this.m_175366_() : this;
         case Y -> this != UP && this != DOWN ? this.m_122427_() : this;
         case Z -> this != NORTH && this != SOUTH ? this.m_175368_() : this;
      };
   }

   public C_4687_ m_175364_(C_4687_.C_4689_ axisIn) {
      return switch (axisIn) {
         case X -> this != WEST && this != EAST ? this.m_175367_() : this;
         case Y -> this != UP && this != DOWN ? this.m_122428_() : this;
         case Z -> this != NORTH && this != SOUTH ? this.m_175369_() : this;
      };
   }

   public C_4687_ m_122427_() {
      return switch (this) {
         case NORTH -> EAST;
         case SOUTH -> WEST;
         case WEST -> NORTH;
         case EAST -> SOUTH;
         default -> throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      };
   }

   private C_4687_ m_175366_() {
      return switch (this) {
         case DOWN -> SOUTH;
         case UP -> NORTH;
         case NORTH -> DOWN;
         case SOUTH -> UP;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private C_4687_ m_175367_() {
      return switch (this) {
         case DOWN -> NORTH;
         case UP -> SOUTH;
         case NORTH -> UP;
         case SOUTH -> DOWN;
         default -> throw new IllegalStateException("Unable to get X-rotated facing of " + this);
      };
   }

   private C_4687_ m_175368_() {
      return switch (this) {
         case DOWN -> WEST;
         case UP -> EAST;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case WEST -> UP;
         case EAST -> DOWN;
      };
   }

   private C_4687_ m_175369_() {
      return switch (this) {
         case DOWN -> EAST;
         case UP -> WEST;
         default -> throw new IllegalStateException("Unable to get Z-rotated facing of " + this);
         case WEST -> DOWN;
         case EAST -> UP;
      };
   }

   public C_4687_ m_122428_() {
      return switch (this) {
         case NORTH -> WEST;
         case SOUTH -> EAST;
         case WEST -> SOUTH;
         case EAST -> NORTH;
         default -> throw new IllegalStateException("Unable to get CCW facing of " + this);
      };
   }

   public int m_122429_() {
      return this.f_122345_.m_123341_();
   }

   public int m_122430_() {
      return this.f_122345_.m_123342_();
   }

   public int m_122431_() {
      return this.f_122345_.m_123343_();
   }

   public Vector3f m_253071_() {
      return new Vector3f((float)this.m_122429_(), (float)this.m_122430_(), (float)this.m_122431_());
   }

   public String m_122433_() {
      return this.f_122342_;
   }

   public C_4687_.C_4689_ m_122434_() {
      return this.f_122343_;
   }

   @Nullable
   public static C_4687_ m_122402_(@Nullable String name) {
      return (C_4687_)f_175356_.m_216455_(name);
   }

   public static C_4687_ m_122376_(int index) {
      return f_122348_[C_188_.m_14040_(index % f_122348_.length)];
   }

   public static C_4687_ m_122407_(int horizontalIndexIn) {
      return f_122349_[C_188_.m_14040_(horizontalIndexIn % f_122349_.length)];
   }

   @Nullable
   public static C_4687_ m_122378_(int dx, int dy, int dz) {
      if (dx == 0) {
         if (dy == 0) {
            if (dz > 0) {
               return SOUTH;
            }

            if (dz < 0) {
               return NORTH;
            }
         } else if (dz == 0) {
            if (dy > 0) {
               return UP;
            }

            return DOWN;
         }
      } else if (dy == 0 && dz == 0) {
         if (dx > 0) {
            return EAST;
         }

         return WEST;
      }

      return null;
   }

   public static C_4687_ m_122364_(double angle) {
      return m_122407_(C_188_.m_14107_(angle / 90.0 + 0.5) & 3);
   }

   public static C_4687_ m_122387_(C_4687_.C_4689_ axisIn, C_4687_.C_4693_ axisDirectionIn) {
      return switch (axisIn) {
         case X -> axisDirectionIn == C_4687_.C_4693_.POSITIVE ? EAST : WEST;
         case Y -> axisDirectionIn == C_4687_.C_4693_.POSITIVE ? UP : DOWN;
         case Z -> axisDirectionIn == C_4687_.C_4693_.POSITIVE ? SOUTH : NORTH;
      };
   }

   public float m_122435_() {
      return (float)((this.f_122341_ & 3) * 90);
   }

   public static C_4687_ m_235672_(C_212974_ randomIn) {
      return C_5322_.m_214670_(f_122346_, randomIn);
   }

   public static C_4687_ m_122366_(double x, double y, double z) {
      return m_122372_((float)x, (float)y, (float)z);
   }

   public static C_4687_ m_122372_(float x, float y, float z) {
      C_4687_ direction = NORTH;
      float f = Float.MIN_VALUE;

      for (C_4687_ direction1 : f_122346_) {
         float f1 = x * (float)direction1.f_122345_.m_123341_() + y * (float)direction1.f_122345_.m_123342_() + z * (float)direction1.f_122345_.m_123343_();
         if (f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public static C_4687_ m_324946_(C_3046_ vecIn) {
      return m_122366_(vecIn.f_82479_, vecIn.f_82480_, vecIn.f_82481_);
   }

   public String toString() {
      return this.f_122342_;
   }

   public String m_7912_() {
      return this.f_122342_;
   }

   private static DataResult<C_4687_> m_194528_(C_4687_ directionIn) {
      return directionIn.m_122434_().m_122478_() ? DataResult.success(directionIn) : DataResult.error(() -> "Expected a vertical direction");
   }

   public static C_4687_ m_122390_(C_4687_.C_4693_ axisDirectionIn, C_4687_.C_4689_ axisIn) {
      for (C_4687_ direction : f_122346_) {
         if (direction.m_122421_() == axisDirectionIn && direction.m_122434_() == axisIn) {
            return direction;
         }
      }

      throw new IllegalArgumentException("No such direction: " + axisDirectionIn + " " + axisIn);
   }

   public C_4713_ m_122436_() {
      return this.f_122345_;
   }

   public boolean m_122370_(float angleIn) {
      float f = angleIn * (float) (Math.PI / 180.0);
      float f1 = -C_188_.m_14031_(f);
      float f2 = C_188_.m_14089_(f);
      return (float)this.f_122345_.m_123341_() * f1 + (float)this.f_122345_.m_123343_() * f2 > 0.0F;
   }

   public static C_4687_ getNearestStable(float x, float y, float z) {
      C_4687_ direction = NORTH;
      float f = Float.MIN_VALUE;

      for (C_4687_ direction1 : f_122346_) {
         float f1 = x * (float)direction1.f_122345_.m_123341_() + y * (float)direction1.f_122345_.m_123342_() + z * (float)direction1.f_122345_.m_123343_();
         if (f1 > f + 1.0E-6F) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public static enum C_4689_ implements C_197_, Predicate<C_4687_> {
      X("x") {
         @Override
         public int m_7863_(int x, int y, int z) {
            return x;
         }

         @Override
         public double m_6150_(double x, double y, double z) {
            return x;
         }
      },
      Y("y") {
         @Override
         public int m_7863_(int x, int y, int z) {
            return y;
         }

         @Override
         public double m_6150_(double x, double y, double z) {
            return y;
         }
      },
      Z("z") {
         @Override
         public int m_7863_(int x, int y, int z) {
            return z;
         }

         @Override
         public double m_6150_(double x, double y, double z) {
            return z;
         }
      };

      public static final C_4687_.C_4689_[] f_122448_ = values();
      public static final C_212981_<C_4687_.C_4689_> f_122447_ = C_197_.m_216439_(C_4687_.C_4689_::values);
      private final String f_122450_;

      private C_4689_(final String nameIn) {
         this.f_122450_ = nameIn;
      }

      @Nullable
      public static C_4687_.C_4689_ m_122473_(String name) {
         return (C_4687_.C_4689_)f_122447_.m_216455_(name);
      }

      public String m_122477_() {
         return this.f_122450_;
      }

      public boolean m_122478_() {
         return this == Y;
      }

      public boolean m_122479_() {
         return this == X || this == Z;
      }

      public String toString() {
         return this.f_122450_;
      }

      public static C_4687_.C_4689_ m_235688_(C_212974_ randomIn) {
         return C_5322_.m_214670_(f_122448_, randomIn);
      }

      public boolean test(@Nullable C_4687_ p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_() == this;
      }

      public C_4687_.C_4694_ m_122480_() {
         return switch (this) {
            case X, Z -> C_4687_.C_4694_.HORIZONTAL;
            case Y -> C_4687_.C_4694_.VERTICAL;
         };
      }

      public String m_7912_() {
         return this.f_122450_;
      }

      public abstract int m_7863_(int var1, int var2, int var3);

      public abstract double m_6150_(double var1, double var3, double var5);
   }

   public static enum C_4693_ {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int f_122531_;
      private final String f_122532_;

      private C_4693_(final int offset, final String description) {
         this.f_122531_ = offset;
         this.f_122532_ = description;
      }

      public int m_122540_() {
         return this.f_122531_;
      }

      public String m_175372_() {
         return this.f_122532_;
      }

      public String toString() {
         return this.f_122532_;
      }

      public C_4687_.C_4693_ m_122541_() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }
   }

   public static enum C_4694_ implements Iterable<C_4687_>, Predicate<C_4687_> {
      HORIZONTAL(new C_4687_[]{C_4687_.NORTH, C_4687_.EAST, C_4687_.SOUTH, C_4687_.WEST}, new C_4687_.C_4689_[]{C_4687_.C_4689_.X, C_4687_.C_4689_.Z}),
      VERTICAL(new C_4687_[]{C_4687_.UP, C_4687_.DOWN}, new C_4687_.C_4689_[]{C_4687_.C_4689_.Y});

      private final C_4687_[] f_122548_;
      private final C_4687_.C_4689_[] f_122549_;

      private C_4694_(final C_4687_[] facingValuesIn, final C_4687_.C_4689_[] axisValuesIn) {
         this.f_122548_ = facingValuesIn;
         this.f_122549_ = axisValuesIn;
      }

      public C_4687_ m_235690_(C_212974_ randomIn) {
         return C_5322_.m_214670_(this.f_122548_, randomIn);
      }

      public C_4687_.C_4689_ m_235692_(C_212974_ randomIn) {
         return C_5322_.m_214670_(this.f_122549_, randomIn);
      }

      public boolean test(@Nullable C_4687_ p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_().m_122480_() == this;
      }

      public Iterator<C_4687_> iterator() {
         return Iterators.forArray(this.f_122548_);
      }

      public Stream<C_4687_> m_122557_() {
         return Arrays.stream(this.f_122548_);
      }

      public List<C_4687_> m_235694_(C_212974_ randomIn) {
         return C_5322_.m_214681_(this.f_122548_, randomIn);
      }

      public int m_322453_() {
         return this.f_122548_.length;
      }
   }
}
