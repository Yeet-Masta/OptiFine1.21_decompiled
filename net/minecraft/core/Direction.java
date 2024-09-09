package net.minecraft.core;

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
import net.minecraft.Util;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.ByIdMap.OutOfBoundsStrategy;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public enum Direction implements StringRepresentable {
   DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.field_30, new Vec3i(0, -1, 0)),
   // $FF: renamed from: UP net.minecraft.core.Direction
   field_61(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.field_30, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.field_31, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.field_31, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.field_29, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.field_29, new Vec3i(1, 0, 0));

   public static final StringRepresentable.EnumCodec f_175356_ = StringRepresentable.m_216439_(Direction::values);
   public static final Codec f_194527_ = f_175356_.validate(Direction::m_194528_);
   public static final IntFunction f_315953_ = ByIdMap.m_262839_(Direction::m_122411_, values(), OutOfBoundsStrategy.WRAP);
   public static final StreamCodec f_315582_ = ByteBufCodecs.m_321301_(f_315953_, Direction::m_122411_);
   private final int f_122339_;
   private final int f_122340_;
   private final int f_122341_;
   private final String f_122342_;
   private final Axis f_122343_;
   private final AxisDirection f_122344_;
   private final Vec3i f_122345_;
   public static final Direction[] f_122346_ = values();
   public static final Direction[] f_122348_ = (Direction[])Arrays.stream(f_122346_).sorted(Comparator.comparingInt((dirIn) -> {
      return dirIn.f_122339_;
   })).toArray((x$0) -> {
      return new Direction[x$0];
   });
   private static final Direction[] f_122349_ = (Direction[])Arrays.stream(f_122346_).filter((dirIn) -> {
      return dirIn.m_122434_().m_122479_();
   }).sorted(Comparator.comparingInt((dir2In) -> {
      return dir2In.f_122341_;
   })).toArray((x$0) -> {
      return new Direction[x$0];
   });

   private Direction(final int indexIn, final int oppositeIn, final int horizontalIndexIn, final String nameIn, final AxisDirection axisDirectionIn, final Axis axisIn, final Vec3i directionVecIn) {
      this.f_122339_ = indexIn;
      this.f_122341_ = horizontalIndexIn;
      this.f_122340_ = oppositeIn;
      this.f_122342_ = nameIn;
      this.f_122343_ = axisIn;
      this.f_122344_ = axisDirectionIn;
      this.f_122345_ = directionVecIn;
   }

   public static Direction[] m_122382_(Entity entityIn) {
      float f = entityIn.m_5686_(1.0F) * 0.017453292F;
      float f1 = -entityIn.m_5675_(1.0F) * 0.017453292F;
      float f2 = Mth.m_14031_(f);
      float f3 = Mth.m_14089_(f);
      float f4 = Mth.m_14031_(f1);
      float f5 = Mth.m_14089_(f1);
      boolean flag = f4 > 0.0F;
      boolean flag1 = f2 < 0.0F;
      boolean flag2 = f5 > 0.0F;
      float f6 = flag ? f4 : -f4;
      float f7 = flag1 ? -f2 : f2;
      float f8 = flag2 ? f5 : -f5;
      float f9 = f6 * f3;
      float f10 = f8 * f3;
      Direction direction = flag ? EAST : WEST;
      Direction direction1 = flag1 ? field_61 : DOWN;
      Direction direction2 = flag2 ? SOUTH : NORTH;
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

   private static Direction[] m_122398_(Direction first, Direction second, Direction third) {
      return new Direction[]{first, second, third, third.m_122424_(), second.m_122424_(), first.m_122424_()};
   }

   public static Direction m_252919_(Matrix4f matrixIn, Direction directionIn) {
      Vec3i vec3i = directionIn.m_122436_();
      Vector4f vector4f = matrixIn.transform(new Vector4f((float)vec3i.m_123341_(), (float)vec3i.m_123342_(), (float)vec3i.m_123343_(), 0.0F));
      return m_122372_(vector4f.x(), vector4f.y(), vector4f.z());
   }

   public static Collection m_235667_(RandomSource randomIn) {
      return Util.m_214681_(values(), randomIn);
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

   public AxisDirection m_122421_() {
      return this.f_122344_;
   }

   public static Direction m_175357_(Entity entityIn, Axis axisIn) {
      Direction var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = EAST.m_122370_(entityIn.m_5675_(1.0F)) ? EAST : WEST;
            break;
         case 1:
            var10000 = entityIn.m_5686_(1.0F) < 0.0F ? field_61 : DOWN;
            break;
         case 2:
            var10000 = SOUTH.m_122370_(entityIn.m_5675_(1.0F)) ? SOUTH : NORTH;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public Direction m_122424_() {
      return f_122346_[this.f_122340_];
   }

   public Direction m_175362_(Axis axisIn) {
      Direction var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = this != WEST && this != EAST ? this.m_175366_() : this;
            break;
         case 1:
            var10000 = this != field_61 && this != DOWN ? this.m_122427_() : this;
            break;
         case 2:
            var10000 = this != NORTH && this != SOUTH ? this.m_175368_() : this;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public Direction m_175364_(Axis axisIn) {
      Direction var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = this != WEST && this != EAST ? this.m_175367_() : this;
            break;
         case 1:
            var10000 = this != field_61 && this != DOWN ? this.m_122428_() : this;
            break;
         case 2:
            var10000 = this != NORTH && this != SOUTH ? this.m_175369_() : this;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public Direction m_122427_() {
      Direction var10000;
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

   private Direction m_175366_() {
      Direction var10000;
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
            var10000 = field_61;
            break;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf(this));
      }

      return var10000;
   }

   private Direction m_175367_() {
      Direction var10000;
      switch (this.ordinal()) {
         case 0:
            var10000 = NORTH;
            break;
         case 1:
            var10000 = SOUTH;
            break;
         case 2:
            var10000 = field_61;
            break;
         case 3:
            var10000 = DOWN;
            break;
         default:
            throw new IllegalStateException("Unable to get X-rotated facing of " + String.valueOf(this));
      }

      return var10000;
   }

   private Direction m_175368_() {
      Direction var10000;
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
            var10000 = field_61;
            break;
         case 5:
            var10000 = DOWN;
      }

      return var10000;
   }

   private Direction m_175369_() {
      Direction var10000;
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
            var10000 = field_61;
      }

      return var10000;
   }

   public Direction m_122428_() {
      Direction var10000;
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

   public Axis m_122434_() {
      return this.f_122343_;
   }

   @Nullable
   public static Direction m_122402_(@Nullable String name) {
      return (Direction)f_175356_.m_216455_(name);
   }

   public static Direction m_122376_(int index) {
      return f_122348_[Mth.m_14040_(index % f_122348_.length)];
   }

   public static Direction m_122407_(int horizontalIndexIn) {
      return f_122349_[Mth.m_14040_(horizontalIndexIn % f_122349_.length)];
   }

   @Nullable
   public static Direction m_122378_(int dx, int dy, int dz) {
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
               return field_61;
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

   public static Direction m_122364_(double angle) {
      return m_122407_(Mth.m_14107_(angle / 90.0 + 0.5) & 3);
   }

   public static Direction m_122387_(Axis axisIn, AxisDirection axisDirectionIn) {
      Direction var10000;
      switch (axisIn.ordinal()) {
         case 0:
            var10000 = axisDirectionIn == Direction.AxisDirection.POSITIVE ? EAST : WEST;
            break;
         case 1:
            var10000 = axisDirectionIn == Direction.AxisDirection.POSITIVE ? field_61 : DOWN;
            break;
         case 2:
            var10000 = axisDirectionIn == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
            break;
         default:
            throw new MatchException((String)null, (Throwable)null);
      }

      return var10000;
   }

   public float m_122435_() {
      return (float)((this.f_122341_ & 3) * 90);
   }

   public static Direction m_235672_(RandomSource randomIn) {
      return (Direction)Util.m_214670_(f_122346_, randomIn);
   }

   public static Direction m_122366_(double x, double y, double z) {
      return m_122372_((float)x, (float)y, (float)z);
   }

   public static Direction m_122372_(float x, float y, float z) {
      Direction direction = NORTH;
      float f = Float.MIN_VALUE;
      Direction[] var5 = f_122346_;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction direction1 = var5[var7];
         float f1 = x * (float)direction1.f_122345_.m_123341_() + y * (float)direction1.f_122345_.m_123342_() + z * (float)direction1.f_122345_.m_123343_();
         if (f1 > f) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   public static Direction m_324946_(Vec3 vecIn) {
      return m_122366_(vecIn.f_82479_, vecIn.f_82480_, vecIn.f_82481_);
   }

   public String toString() {
      return this.f_122342_;
   }

   public String m_7912_() {
      return this.f_122342_;
   }

   private static DataResult m_194528_(Direction directionIn) {
      return directionIn.m_122434_().m_122478_() ? DataResult.success(directionIn) : DataResult.error(() -> {
         return "Expected a vertical direction";
      });
   }

   public static Direction m_122390_(AxisDirection axisDirectionIn, Axis axisIn) {
      Direction[] var2 = f_122346_;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Direction direction = var2[var4];
         if (direction.m_122421_() == axisDirectionIn && direction.m_122434_() == axisIn) {
            return direction;
         }
      }

      String var10002 = String.valueOf(axisDirectionIn);
      throw new IllegalArgumentException("No such direction: " + var10002 + " " + String.valueOf(axisIn));
   }

   public Vec3i m_122436_() {
      return this.f_122345_;
   }

   public boolean m_122370_(float angleIn) {
      float f = angleIn * 0.017453292F;
      float f1 = -Mth.m_14031_(f);
      float f2 = Mth.m_14089_(f);
      return (float)this.f_122345_.m_123341_() * f1 + (float)this.f_122345_.m_123343_() * f2 > 0.0F;
   }

   public static Direction getNearestStable(float x, float y, float z) {
      Direction direction = NORTH;
      float f = Float.MIN_VALUE;
      Direction[] var5 = f_122346_;
      int var6 = var5.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Direction direction1 = var5[var7];
         float f1 = x * (float)direction1.f_122345_.m_123341_() + y * (float)direction1.f_122345_.m_123342_() + z * (float)direction1.f_122345_.m_123343_();
         if (f1 > f + 1.0E-6F) {
            f = f1;
            direction = direction1;
         }
      }

      return direction;
   }

   // $FF: synthetic method
   private static Direction[] $values() {
      return new Direction[]{DOWN, field_61, NORTH, SOUTH, WEST, EAST};
   }

   public static enum Axis implements StringRepresentable, Predicate {
      // $FF: renamed from: X net.minecraft.core.Direction$Axis
      field_29("x") {
         public int m_7863_(int x, int y, int z) {
            return x;
         }

         public double m_6150_(double x, double y, double z) {
            return x;
         }
      },
      // $FF: renamed from: Y net.minecraft.core.Direction$Axis
      field_30("y") {
         public int m_7863_(int x, int y, int z) {
            return y;
         }

         public double m_6150_(double x, double y, double z) {
            return y;
         }
      },
      // $FF: renamed from: Z net.minecraft.core.Direction$Axis
      field_31("z") {
         public int m_7863_(int x, int y, int z) {
            return z;
         }

         public double m_6150_(double x, double y, double z) {
            return z;
         }
      };

      public static final Axis[] f_122448_ = values();
      public static final StringRepresentable.EnumCodec f_122447_ = StringRepresentable.m_216439_(Axis::values);
      private final String f_122450_;

      private Axis(final String nameIn) {
         this.f_122450_ = nameIn;
      }

      @Nullable
      public static Axis m_122473_(String name) {
         return (Axis)f_122447_.m_216455_(name);
      }

      public String m_122477_() {
         return this.f_122450_;
      }

      public boolean m_122478_() {
         return this == field_30;
      }

      public boolean m_122479_() {
         return this == field_29 || this == field_31;
      }

      public String toString() {
         return this.f_122450_;
      }

      public static Axis m_235688_(RandomSource randomIn) {
         return (Axis)Util.m_214670_(f_122448_, randomIn);
      }

      public boolean test(@Nullable Direction p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_() == this;
      }

      public Plane m_122480_() {
         Plane var10000;
         switch (this.ordinal()) {
            case 0:
            case 2:
               var10000 = Direction.Plane.HORIZONTAL;
               break;
            case 1:
               var10000 = Direction.Plane.VERTICAL;
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
      private static Axis[] $values() {
         return new Axis[]{field_29, field_30, field_31};
      }
   }

   public static enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int f_122531_;
      private final String f_122532_;

      private AxisDirection(final int offset, final String description) {
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

      public AxisDirection m_122541_() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }

      // $FF: synthetic method
      private static AxisDirection[] $values() {
         return new AxisDirection[]{POSITIVE, NEGATIVE};
      }
   }

   public static enum Plane implements Iterable, Predicate {
      HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Axis[]{Direction.Axis.field_29, Direction.Axis.field_31}),
      VERTICAL(new Direction[]{Direction.field_61, Direction.DOWN}, new Axis[]{Direction.Axis.field_30});

      private final Direction[] f_122548_;
      private final Axis[] f_122549_;

      private Plane(final Direction[] facingValuesIn, final Axis[] axisValuesIn) {
         this.f_122548_ = facingValuesIn;
         this.f_122549_ = axisValuesIn;
      }

      public Direction m_235690_(RandomSource randomIn) {
         return (Direction)Util.m_214670_(this.f_122548_, randomIn);
      }

      public Axis m_235692_(RandomSource randomIn) {
         return (Axis)Util.m_214670_(this.f_122549_, randomIn);
      }

      public boolean test(@Nullable Direction p_test_1_) {
         return p_test_1_ != null && p_test_1_.m_122434_().m_122480_() == this;
      }

      public Iterator iterator() {
         return Iterators.forArray(this.f_122548_);
      }

      public Stream m_122557_() {
         return Arrays.stream(this.f_122548_);
      }

      public List m_235694_(RandomSource randomIn) {
         return Util.m_214681_(this.f_122548_, randomIn);
      }

      public int m_322453_() {
         return this.f_122548_.length;
      }

      // $FF: synthetic method
      private static Plane[] $values() {
         return new Plane[]{HORIZONTAL, VERTICAL};
      }
   }
}
