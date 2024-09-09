import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3045_;
import net.minecraft.src.C_4703_;
import net.minecraft.src.C_4713_;
import org.joml.Vector3f;

public class Vec3 implements C_4703_ {
   public static final Codec<Vec3> a = Codec.DOUBLE
      .listOf()
      .comapFlatMap(
         p_318574_0_ -> Util.a(p_318574_0_, 3).map(p_231080_0_ -> new Vec3((Double)p_231080_0_.get(0), (Double)p_231080_0_.get(1), (Double)p_231080_0_.get(2))),
         p_231082_0_ -> List.of(p_231082_0_.m_7096_(), p_231082_0_.m_7098_(), p_231082_0_.m_7094_())
      );
   public static final Vec3 b = new Vec3(0.0, 0.0, 0.0);
   public double c;
   public double d;
   public double e;

   public static Vec3 a(int p_82501_0_) {
      double d0 = (double)(p_82501_0_ >> 16 & 0xFF) / 255.0;
      double d1 = (double)(p_82501_0_ >> 8 & 0xFF) / 255.0;
      double d2 = (double)(p_82501_0_ & 0xFF) / 255.0;
      return new Vec3(d0, d1, d2);
   }

   public static Vec3 a(C_4713_ p_82528_0_) {
      return new Vec3((double)p_82528_0_.m_123341_(), (double)p_82528_0_.m_123342_(), (double)p_82528_0_.m_123343_());
   }

   public static Vec3 a(C_4713_ p_272021_0_, double p_272021_1_, double p_272021_3_, double p_272021_5_) {
      return new Vec3(
         (double)p_272021_0_.m_123341_() + p_272021_1_, (double)p_272021_0_.m_123342_() + p_272021_3_, (double)p_272021_0_.m_123343_() + p_272021_5_
      );
   }

   public static Vec3 b(C_4713_ p_82512_0_) {
      return a(p_82512_0_, 0.5, 0.5, 0.5);
   }

   public static Vec3 c(C_4713_ p_82539_0_) {
      return a(p_82539_0_, 0.5, 0.0, 0.5);
   }

   public static Vec3 a(C_4713_ p_82514_0_, double p_82514_1_) {
      return a(p_82514_0_, 0.5, p_82514_1_, 0.5);
   }

   public Vec3(double xIn, double yIn, double zIn) {
      this.c = xIn;
      this.d = yIn;
      this.e = zIn;
   }

   public Vec3(Vector3f p_i252809_1_) {
      this((double)p_i252809_1_.x(), (double)p_i252809_1_.y(), (double)p_i252809_1_.z());
   }

   public Vec3 a(Vec3 vec) {
      return new Vec3(vec.c - this.c, vec.d - this.d, vec.e - this.e);
   }

   public Vec3 d() {
      double d0 = Math.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
      return d0 < 1.0E-4 ? b : new Vec3(this.c / d0, this.d / d0, this.e / d0);
   }

   public double b(Vec3 vec) {
      return this.c * vec.c + this.d * vec.d + this.e * vec.e;
   }

   public Vec3 c(Vec3 vec) {
      return new Vec3(this.d * vec.e - this.e * vec.d, this.e * vec.c - this.c * vec.e, this.c * vec.d - this.d * vec.c);
   }

   public Vec3 d(Vec3 vec) {
      return this.a(vec.c, vec.d, vec.e);
   }

   public Vec3 a(double x, double y, double z) {
      return this.b(-x, -y, -z);
   }

   public Vec3 e(Vec3 vec) {
      return this.b(vec.c, vec.d, vec.e);
   }

   public Vec3 b(double x, double y, double z) {
      return new Vec3(this.c + x, this.d + y, this.e + z);
   }

   public boolean a(C_4703_ p_82509_1_, double p_82509_2_) {
      return this.c(p_82509_1_.m_7096_(), p_82509_1_.m_7098_(), p_82509_1_.m_7094_()) < p_82509_2_ * p_82509_2_;
   }

   public double f(Vec3 vec) {
      double d0 = vec.c - this.c;
      double d1 = vec.d - this.d;
      double d2 = vec.e - this.e;
      return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double g(Vec3 vec) {
      double d0 = vec.c - this.c;
      double d1 = vec.d - this.d;
      double d2 = vec.e - this.e;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double c(double xIn, double yIn, double zIn) {
      double d0 = xIn - this.c;
      double d1 = yIn - this.d;
      double d2 = zIn - this.e;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public boolean a(Vec3 p_306338_1_, double p_306338_2_, double p_306338_4_) {
      double d0 = p_306338_1_.m_7096_() - this.c;
      double d1 = p_306338_1_.m_7098_() - this.d;
      double d2 = p_306338_1_.m_7094_() - this.e;
      return Mth.e(d0, d2) < Mth.k(p_306338_2_) && Math.abs(d1) < p_306338_4_;
   }

   public Vec3 a(double factor) {
      return this.d(factor, factor, factor);
   }

   public Vec3 e() {
      return this.a(-1.0);
   }

   public Vec3 h(Vec3 p_82559_1_) {
      return this.d(p_82559_1_.c, p_82559_1_.d, p_82559_1_.e);
   }

   public Vec3 d(double factorX, double factorY, double factorZ) {
      return new Vec3(this.c * factorX, this.d * factorY, this.e * factorZ);
   }

   public Vec3 a(C_212974_ p_272010_1_, float p_272010_2_) {
      return this.b(
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_),
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_),
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_)
      );
   }

   public double f() {
      return Math.sqrt(this.c * this.c + this.d * this.d + this.e * this.e);
   }

   public double g() {
      return this.c * this.c + this.d * this.d + this.e * this.e;
   }

   public double h() {
      return Math.sqrt(this.c * this.c + this.e * this.e);
   }

   public double i() {
      return this.c * this.c + this.e * this.e;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ instanceof Vec3 vec3) {
         if (Double.compare(vec3.c, this.c) != 0) {
            return false;
         } else {
            return Double.compare(vec3.d, this.d) != 0 ? false : Double.compare(vec3.e, this.e) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      long j = Double.doubleToLongBits(this.c);
      int i = (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.d);
      i = 31 * i + (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.e);
      return 31 * i + (int)(j ^ j >>> 32);
   }

   public String toString() {
      return "(" + this.c + ", " + this.d + ", " + this.e + ")";
   }

   public Vec3 a(Vec3 p_165921_1_, double p_165921_2_) {
      return new Vec3(Mth.d(p_165921_2_, this.c, p_165921_1_.c), Mth.d(p_165921_2_, this.d, p_165921_1_.d), Mth.d(p_165921_2_, this.e, p_165921_1_.e));
   }

   public Vec3 a(float pitch) {
      float f = Mth.b(pitch);
      float f1 = Mth.a(pitch);
      double d0 = this.c;
      double d1 = this.d * (double)f + this.e * (double)f1;
      double d2 = this.e * (double)f - this.d * (double)f1;
      return new Vec3(d0, d1, d2);
   }

   public Vec3 b(float yaw) {
      float f = Mth.b(yaw);
      float f1 = Mth.a(yaw);
      double d0 = this.c * (double)f + this.e * (double)f1;
      double d1 = this.d;
      double d2 = this.e * (double)f - this.c * (double)f1;
      return new Vec3(d0, d1, d2);
   }

   public Vec3 c(float p_82535_1_) {
      float f = Mth.b(p_82535_1_);
      float f1 = Mth.a(p_82535_1_);
      double d0 = this.c * (double)f + this.d * (double)f1;
      double d1 = this.d * (double)f - this.c * (double)f1;
      double d2 = this.e;
      return new Vec3(d0, d1, d2);
   }

   public static Vec3 a(C_3045_ p_82503_0_) {
      return a(p_82503_0_.f_82470_, p_82503_0_.f_82471_);
   }

   public static Vec3 a(float pitch, float yaw) {
      float f = Mth.b(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = Mth.a(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -Mth.b(-pitch * (float) (Math.PI / 180.0));
      float f3 = Mth.a(-pitch * (float) (Math.PI / 180.0));
      return new Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public Vec3 a(EnumSet<Direction.a> axes) {
      double d0 = axes.contains(Direction.a.a) ? (double)Mth.a(this.c) : this.c;
      double d1 = axes.contains(Direction.a.b) ? (double)Mth.a(this.d) : this.d;
      double d2 = axes.contains(Direction.a.c) ? (double)Mth.a(this.e) : this.e;
      return new Vec3(d0, d1, d2);
   }

   public double a(Direction.a axis) {
      return axis.a(this.c, this.d, this.e);
   }

   public Vec3 a(Direction.a p_193103_1_, double p_193103_2_) {
      double d0 = p_193103_1_ == Direction.a.a ? p_193103_2_ : this.c;
      double d1 = p_193103_1_ == Direction.a.b ? p_193103_2_ : this.d;
      double d2 = p_193103_1_ == Direction.a.c ? p_193103_2_ : this.e;
      return new Vec3(d0, d1, d2);
   }

   public Vec3 a(Direction p_231075_1_, double p_231075_2_) {
      C_4713_ vec3i = p_231075_1_.q();
      return new Vec3(
         this.c + p_231075_2_ * (double)vec3i.m_123341_(), this.d + p_231075_2_ * (double)vec3i.m_123342_(), this.e + p_231075_2_ * (double)vec3i.m_123343_()
      );
   }

   public final double m_7096_() {
      return this.c;
   }

   public final double m_7098_() {
      return this.d;
   }

   public final double m_7094_() {
      return this.e;
   }

   public Vector3f j() {
      return new Vector3f((float)this.c, (float)this.d, (float)this.e);
   }
}
