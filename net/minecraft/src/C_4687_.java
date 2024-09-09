package net.minecraft.src;

import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_262716_.C_262714_;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public enum C_4687_ implements C_197_ {
   DOWN(0, 1, -1, "down", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.field_7, new C_4713_(0, -1, 0)),
   // $FF: renamed from: UP net.minecraft.src.C_4687_
   field_50(1, 0, -1, "up", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.field_7, new C_4713_(0, 1, 0)),
   NORTH(2, 3, 2, "north", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.field_8, new C_4713_(0, 0, -1)),
   SOUTH(3, 2, 0, "south", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.field_8, new C_4713_(0, 0, 1)),
   WEST(4, 5, 1, "west", C_4687_.C_4693_.NEGATIVE, C_4687_.C_4689_.field_6, new C_4713_(-1, 0, 0)),
   EAST(5, 4, 3, "east", C_4687_.C_4693_.POSITIVE, C_4687_.C_4689_.field_6, new C_4713_(1, 0, 0));

   public static final C_197_.C_212981_ f_175356_ = C_197_.m_216439_(C_4687_::values);
   public static final Codec f_194527_ = f_175356_.validate(C_4687_::m_194528_);
   public static final IntFunction f_315953_ = C_262716_.m_262839_(C_4687_::m_122411_, values(), C_262714_.WRAP);
   public static final C_313866_ f_315582_ = C_313613_.m_321301_(f_315953_, C_4687_::m_122411_);
   private final int f_122339_;
   private final int f_122340_;
   private final int f_122341_;
   private final String f_122342_;
   private final C_4689_ f_122343_;
   private final C_4693_ f_122344_;
   private final C_4713_ f_122345_;
   public static final C_4687_[] f_122346_ = values();
   public static final C_4687_[] f_122348_ = (C_4687_[])Arrays.stream(f_122346_).sorted(Comparator.comparingInt((dirIn) -> {
      return dirIn.f_122339_;
   })).toArray((x$0) -> {
      return new C_4687_[x$0];
   });
   private static final C_4687_[] f_122349_ = (C_4687_[])Arrays.stream(f_122346_).filter((dirIn) -> {
      return dirIn.m_122434_().m_122479_();
   }).sorted(Comparator.comparingInt((dir2In) -> {
      return dir2In.f_122341_;
   })).toArray((x$0) -> {
      return new C_4687_[x$0];
   });

   private C_4687_(final int indexIn, final int oppositeIn, final int horizontalIndexIn, final String nameIn, final C_4693_ axisDirectionIn, final C_4689_ axisIn, final C_4713_ directionVecIn) {
      this.f_122339_ = indexIn;
      this.f_122341_ = horizontalIndexIn;
      this.f_122340_ = oppositeIn;
      this.f_122342_ = nameIn;
      this.f_122343_ = axisIn;
      this.f_122344_ = axisDirectionIn;
      this.f_122345_ = directionVecIn;
   }

   public static C_4687_[] m_122382_(C_507_ entityIn) {
      float f = entityIn.m_5686_(1.0F) * 0.017453292F;
      float f1 = -entityIn.m_5675_(1.0F) * 0.017453292F;
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
      C_4687_ direction1 = flag1 ? field_50 : DOWN;
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

   public static Collection m_235667_(C_212974_ randomIn) {
      return C_5322_.m_214681_(values(), randomIn);
   }

   public static Stream m_235666_() {
      return Stream.of(f_122346_);
   }

   public Quaternionf m_253075_() {
      Quaternionf var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = (new Quaternionf()).rotationX(3.1415927F);
            break;
         case 1:
            var10000 = new Quaternionf();
            break;
         case 2:
            var10000 = (new Quaternionf()).rotationXYZ(1.5707964F, 0.0F, 3.1415927F);
            break;
         case 3:
            var10000 = (new Quaternionf()).rotationX(1.5707964F);
            break;
         case 4:
            var10000 = (new Quaternionf()).rotationXYZ(1.5707964F, 0.0F, 1.5707964F);
            break;
         case 5:
            var10000 = (new Quaternionf()).rotationXYZ(1.5707964F, 0.0F, -1.5707964F);
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public int m_122411_() {
      return this.f_122339_;
   }

   public int m_122416_() {
      return this.f_122341_;
   }

   public C_4693_ m_122421_() {
      return this.f_122344_;
   }

   public static C_4687_ m_175357_(C_507_ entityIn, C_4689_ axisIn) {
      C_4687_ var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = EAST.m_122370_(entityIn.m_5675_(1.0F)) ? EAST : WEST;
            break;
         case 1:
            var10000 = entityIn.m_5686_(1.0F) < 0.0F ? field_50 : DOWN;
            break;
         case 2:
            var10000 = SOUTH.m_122370_(entityIn.m_5675_(1.0F)) ? SOUTH : NORTH;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public C_4687_ m_122424_() {
      return f_122346_[this.f_122340_];
   }

   public C_4687_ m_175362_(C_4689_ axisIn) {
      C_4687_ var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = this != WEST && this != EAST ? this.m_175366_() : this;
            break;
         case 1:
            var10000 = this != field_50 && this != DOWN ? this.m_122427_() : this;
            break;
         case 2:
            var10000 = this != NORTH && this != SOUTH ? this.m_175368_() : this;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public C_4687_ m_175364_(C_4689_ axisIn) {
      C_4687_ var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = this != WEST && this != EAST ? this.m_175367_() : this;
            break;
         case 1:
            var10000 = this != field_50 && this != DOWN ? this.m_122428_() : this;
            break;
         case 2:
            var10000 = this != NORTH && this != SOUTH ? this.m_175369_() : this;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public C_4687_ m_122427_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 2:
            var10000 = EAST;
            break;
         case 3:
            var10000 = WEST;
            break;
         case 4:
            var10000 = NORTH;
            break;
         case 5:
            var10000 = SOUTH;
            break;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + String.valueOf(this));
      }

      return var10000;
   }

   private C_4687_ m_175366_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = SOUTH;
            break;
         case 1:
            var10000 = NORTH;
            break;
         case 2:
            var10000 = DOWN;
            break;
         case 3:
            var10000 = field_50;
            break;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf(this));
      }

      return var10000;
   }

   private C_4687_ m_175367_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = NORTH;
            break;
         case 1:
            var10000 = SOUTH;
            break;
         case 2:
            var10000 = field_50;
            break;
         case 3:
            var10000 = DOWN;
            break;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf(this));
      }

      return var10000;
   }

   private C_4687_ m_175368_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = WEST;
            break;
         case 1:
            var10000 = EAST;
            break;
         case 2:
         case 3:
         default:
            throw new IllegalStateException("Unable to get Z-rotated facing of " + String.valueOf(this));
         case 4:
            var10000 = field_50;
            break;
         case 5:
            var10000 = DOWN;
      }

      return var10000;
   }

   private C_4687_ m_175369_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = EAST;
            break;
         case 1:
            var10000 = WEST;
            break;
         case 2:
         case 3:
         default:
            throw new IllegalStateException("Unable to get Z-rotated facing of " + String.valueOf(this));
         case 4:
            var10000 = DOWN;
            break;
         case 5:
            var10000 = field_50;
      }

      return var10000;
   }

   public C_4687_ m_122428_() {
      C_4687_ var10000;
      switch (this.ordinal()) {
         case 2:
            var10000 = WEST;
            break;
         case 3:
            var10000 = EAST;
            break;
         case 4:
            var10000 = SOUTH;
            break;
         case 5:
            var10000 = NORTH;
            break;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + String.valueOf(this));
      }

      return var10000;
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

   public C_4689_ m_122434_() {
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
               return field_50;
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

   public static C_4687_ m_122387_(C_4689_ axisIn, C_4693_ axisDirectionIn) {
      C_4687_ var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = axisDirectionIn == C_4687_.C_4693_.POSITIVE ? EAST : WEST;
            break;
         case 1:
            var10000 = axisDirectionIn == C_4687_.C_4693_.POSITIVE ? field_50 : DOWN;
            break;
         case 2:
            var10000 = axisDirectionIn == C_4687_.C_4693_.POSITIVE ? SOUTH : NORTH;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public float m_122435_() {
      return (float)((this.f_122341_ & 3) * 90);
   }

   public static C_4687_ m_235672_(C_212974_ randomIn) {
      return (C_4687_)C_5322_.m_214670_(f_122346_, randomIn);
   }

   public static C_4687_ m_122366_(double x, double y, double z) {
      return m_122372_((float)x, (float)y, (float)z);
   }

   public static C_4687_ m_122372_(float x, float y, float z) {
      C_4687_ direction = NORTH;
      float f = Float.MIN_VALUE;
      C_4687_[] var5 = f_122346_;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         C_4687_ direction1 = var5[var7];
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

   private static DataResult m_194528_(C_4687_ directionIn) {
      return directionIn.m_122434_().m_122478_() ? DataResult.success(directionIn) : DataResult.error(() -> {
         return "Expected a vertical direction";
      });
   }

   public static C_4687_ m_122390_(C_4693_ axisDirectionIn, C_4689_ axisIn) {
      C_4687_[] var2 = f_122346_;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         C_4687_ direction = var2[var4];
         if (direction.m_122421_() == axisDirectionIn && direction.m_122434_() == axisIn) {
            return direction;
         }
      }

      String var10002 = String.valueOf(axisDirectionIn);
      throw new IllegalArgumentException("No such direction: " + var10002 + " " + String.valueOf(axisIn));
   }

   public C_4713_ m_122436_() {
      return this.f_122345_;
   }

   public boolean m_122370_(float angleIn) {
      float f = angleIn * 0.017453292F;
      float f1 = -C_188_.m_14031_(f);
      float f2 = C_188_.m_14089_(f);
      return (float)this.f_122345_.m_123341_() * f1 + (float)this.f_122345_.m_123343_() * f2 > 0.0F;
   }

   public static C_4687_ getNearestStable(float x, float y, float z) {
      C_4687_ direction = NORTH;
      float f = Float.MIN_VALUE;
      C_4687_[] var5 = f_122346_;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         C_4687_ direction1 = var5[var7];
         float f1 = x * (float)direction1.f_122345_.m_123341_() + y * (float)direction1.f_122345_.m_123342_() + z * (float)direction1.f_122345_.m_123343_();
         if (f1 > f + 1.0E-6F) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   // $FF: synthetic method
   private static C_4687_[] $values() {
      return new C_4687_[]{DOWN, field_50, NORTH, SOUTH, WEST, EAST};
   }

   public static enum C_4689_ implements C_197_, Predicate {
      // $FF: renamed from: X net.minecraft.src.C_4687_$C_4689_
      field_6("x") {
         public int m_7863_(int x, int y, int z) {
            return x;
         }

         public double m_6150_(double x, double y, double z) {
            return x;
         }
      },
      // $FF: renamed from: Y net.minecraft.src.C_4687_$C_4689_
      field_7("y") {
         public int m_7863_(int x, int y, int z) {
            return y;
         }

         public double m_6150_(double x, double y, double z) {
            return y;
         }
      },
      // $FF: renamed from: Z net.minecraft.src.C_4687_$C_4689_
      field_8("z") {
         public int m_7863_(int x, int y, int z) {
            return z;
         }

         public double m_6150_(double x, double y, double z) {
            return z;
         }
      };

      public static final C_4689_[] f_122448_ = values();
      public static final C_197_.C_212981_ f_122447_ = C_197_.m_216439_(C_4689_::values);
      private final String f_122450_;

      private C_4689_(final String nameIn) {
         this.f_122450_ = nameIn;
      }

      @Nullable
      public static C_4689_ m_122473_(String name) {
         return (C_4689_)f_122447_.m_216455_(name);
      }

      public String m_122477_() {
         return this.f_122450_;
      }

      public boolean m_122478_() {
         return this == field_7;
      }

      public boolean m_122479_() {
         return this == field_6 || this == field_8;
      }

      public String toString() {
         return this.f_122450_;
      }

      public static C_4689_ m_235688_(C_212974_ randomIn) {
         return (C_4689_)C_5322_.m_214670_(f_122448_, randomIn);
      }

      public boolean test(@Nullable C_4687_ p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_() == this;
      }

      public C_4694_ m_122480_() {
         C_4694_ var10000;
         switch (this.ordinal()) {
            case 0:
            case 2:
               var10000 = C_4687_.C_4694_.HORIZONTAL;
               break;
            case 1:
               var10000 = C_4687_.C_4694_.VERTICAL;
               break;
            default:
               throw new MatchException((String)null, (Throwable)null);
         }

         return var10000;
      }

      public String m_7912_() {
         return this.f_122450_;
      }

      public abstract int m_7863_(int var1, int var2, int var3);

      public abstract double m_6150_(double var1, double var3, double var5);

      // $FF: synthetic method
      private static C_4689_[] $values() {
         return new C_4689_[]{field_6, field_7, field_8};
      }
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

      public C_4693_ m_122541_() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }

      // $FF: synthetic method
      private static C_4693_[] $values() {
         return new C_4693_[]{POSITIVE, NEGATIVE};
      }
   }

   public static enum C_4694_ implements Iterable, Predicate {
      HORIZONTAL(new C_4687_[]{C_4687_.NORTH, C_4687_.EAST, C_4687_.SOUTH, C_4687_.WEST}, new C_4689_[]{C_4687_.C_4689_.field_6, C_4687_.C_4689_.field_8}),
      VERTICAL(new C_4687_[]{C_4687_.field_50, C_4687_.DOWN}, new C_4689_[]{C_4687_.C_4689_.field_7});

      private final C_4687_[] f_122548_;
      private final C_4689_[] f_122549_;

      private C_4694_(final C_4687_[] facingValuesIn, final C_4689_[] axisValuesIn) {
         this.f_122548_ = facingValuesIn;
         this.f_122549_ = axisValuesIn;
      }

      public C_4687_ m_235690_(C_212974_ randomIn) {
         return (C_4687_)C_5322_.m_214670_(this.f_122548_, randomIn);
      }

      public C_4689_ m_235692_(C_212974_ randomIn) {
         return (C_4689_)C_5322_.m_214670_(this.f_122549_, randomIn);
      }

      public boolean test(@Nullable C_4687_ p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_().m_122480_() == this;
      }

      public Iterator iterator() {
         return Iterators.forArray(this.f_122548_);
      }

      public Stream m_122557_() {
         return Arrays.stream(this.f_122548_);
      }

      public List m_235694_(C_212974_ randomIn) {
         return C_5322_.m_214681_(this.f_122548_, randomIn);
      }

      public int m_322453_() {
         return this.f_122548_.length;
      }

      // $FF: synthetic method
      private static C_4694_[] $values() {
         return new C_4694_[]{HORIZONTAL, VERTICAL};
      }
   }
}
