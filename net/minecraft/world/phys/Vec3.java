package net.minecraft.world.phys;

import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public class Vec3 implements Position {
   public static final Codec<net.minecraft.world.phys.Vec3> f_231074_ = Codec.DOUBLE
      .listOf()
      .comapFlatMap(
         p_318574_0_ -> net.minecraft.Util.m_143795_(p_318574_0_, 3)
               .map(p_231080_0_ -> new net.minecraft.world.phys.Vec3((Double)p_231080_0_.get(0), (Double)p_231080_0_.get(1), (Double)p_231080_0_.get(2))),
         p_231082_0_ -> List.of(p_231082_0_.m_7096_(), p_231082_0_.m_7098_(), p_231082_0_.m_7094_())
      );
   public static final net.minecraft.world.phys.Vec3 f_82478_ = new net.minecraft.world.phys.Vec3(0.0, 0.0, 0.0);
   public double f_82479_;
   public double f_82480_;
   public double f_82481_;

   public static net.minecraft.world.phys.Vec3 m_82501_(int p_82501_0_) {
      double d0 = (double)(p_82501_0_ >> 16 & 0xFF) / 255.0;
      double d1 = (double)(p_82501_0_ >> 8 & 0xFF) / 255.0;
      double d2 = (double)(p_82501_0_ & 0xFF) / 255.0;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public static net.minecraft.world.phys.Vec3 m_82528_(Vec3i p_82528_0_) {
      return new net.minecraft.world.phys.Vec3((double)p_82528_0_.m_123341_(), (double)p_82528_0_.m_123342_(), (double)p_82528_0_.m_123343_());
   }

   public static net.minecraft.world.phys.Vec3 m_272021_(Vec3i p_272021_0_, double p_272021_1_, double p_272021_3_, double p_272021_5_) {
      return new net.minecraft.world.phys.Vec3(
         (double)p_272021_0_.m_123341_() + p_272021_1_, (double)p_272021_0_.m_123342_() + p_272021_3_, (double)p_272021_0_.m_123343_() + p_272021_5_
      );
   }

   public static net.minecraft.world.phys.Vec3 m_82512_(Vec3i p_82512_0_) {
      return m_272021_(p_82512_0_, 0.5, 0.5, 0.5);
   }

   public static net.minecraft.world.phys.Vec3 m_82539_(Vec3i p_82539_0_) {
      return m_272021_(p_82539_0_, 0.5, 0.0, 0.5);
   }

   public static net.minecraft.world.phys.Vec3 m_82514_(Vec3i p_82514_0_, double p_82514_1_) {
      return m_272021_(p_82514_0_, 0.5, p_82514_1_, 0.5);
   }

   public Vec3(double xIn, double yIn, double zIn) {
      this.f_82479_ = xIn;
      this.f_82480_ = yIn;
      this.f_82481_ = zIn;
   }

   public Vec3(Vector3f p_i252809_1_) {
      this((double)p_i252809_1_.x(), (double)p_i252809_1_.y(), (double)p_i252809_1_.z());
   }

   public net.minecraft.world.phys.Vec3 m_82505_(net.minecraft.world.phys.Vec3 vec) {
      return new net.minecraft.world.phys.Vec3(vec.f_82479_ - this.f_82479_, vec.f_82480_ - this.f_82480_, vec.f_82481_ - this.f_82481_);
   }

   public net.minecraft.world.phys.Vec3 m_82541_() {
      double d0 = Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_);
      return d0 < 1.0E-4 ? f_82478_ : new net.minecraft.world.phys.Vec3(this.f_82479_ / d0, this.f_82480_ / d0, this.f_82481_ / d0);
   }

   public double m_82526_(net.minecraft.world.phys.Vec3 vec) {
      return this.f_82479_ * vec.f_82479_ + this.f_82480_ * vec.f_82480_ + this.f_82481_ * vec.f_82481_;
   }

   public net.minecraft.world.phys.Vec3 m_82537_(net.minecraft.world.phys.Vec3 vec) {
      return new net.minecraft.world.phys.Vec3(
         this.f_82480_ * vec.f_82481_ - this.f_82481_ * vec.f_82480_,
         this.f_82481_ * vec.f_82479_ - this.f_82479_ * vec.f_82481_,
         this.f_82479_ * vec.f_82480_ - this.f_82480_ * vec.f_82479_
      );
   }

   public net.minecraft.world.phys.Vec3 m_82546_(net.minecraft.world.phys.Vec3 vec) {
      return this.m_82492_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public net.minecraft.world.phys.Vec3 m_82492_(double x, double y, double z) {
      return this.m_82520_(-x, -y, -z);
   }

   public net.minecraft.world.phys.Vec3 m_82549_(net.minecraft.world.phys.Vec3 vec) {
      return this.m_82520_(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public net.minecraft.world.phys.Vec3 m_82520_(double x, double y, double z) {
      return new net.minecraft.world.phys.Vec3(this.f_82479_ + x, this.f_82480_ + y, this.f_82481_ + z);
   }

   public boolean m_82509_(Position p_82509_1_, double p_82509_2_) {
      return this.m_82531_(p_82509_1_.m_7096_(), p_82509_1_.m_7098_(), p_82509_1_.m_7094_()) < p_82509_2_ * p_82509_2_;
   }

   public double m_82554_(net.minecraft.world.phys.Vec3 vec) {
      double d0 = vec.f_82479_ - this.f_82479_;
      double d1 = vec.f_82480_ - this.f_82480_;
      double d2 = vec.f_82481_ - this.f_82481_;
      return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
   }

   public double m_82557_(net.minecraft.world.phys.Vec3 vec) {
      double d0 = vec.f_82479_ - this.f_82479_;
      double d1 = vec.f_82480_ - this.f_82480_;
      double d2 = vec.f_82481_ - this.f_82481_;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double m_82531_(double xIn, double yIn, double zIn) {
      double d0 = xIn - this.f_82479_;
      double d1 = yIn - this.f_82480_;
      double d2 = zIn - this.f_82481_;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public boolean m_306338_(net.minecraft.world.phys.Vec3 p_306338_1_, double p_306338_2_, double p_306338_4_) {
      double d0 = p_306338_1_.m_7096_() - this.f_82479_;
      double d1 = p_306338_1_.m_7098_() - this.f_82480_;
      double d2 = p_306338_1_.m_7094_() - this.f_82481_;
      return net.minecraft.util.Mth.m_211589_(d0, d2) < net.minecraft.util.Mth.m_144952_(p_306338_2_) && Math.abs(d1) < p_306338_4_;
   }

   public net.minecraft.world.phys.Vec3 m_82490_(double factor) {
      return this.m_82542_(factor, factor, factor);
   }

   public net.minecraft.world.phys.Vec3 m_82548_() {
      return this.m_82490_(-1.0);
   }

   public net.minecraft.world.phys.Vec3 m_82559_(net.minecraft.world.phys.Vec3 p_82559_1_) {
      return this.m_82542_(p_82559_1_.f_82479_, p_82559_1_.f_82480_, p_82559_1_.f_82481_);
   }

   public net.minecraft.world.phys.Vec3 m_82542_(double factorX, double factorY, double factorZ) {
      return new net.minecraft.world.phys.Vec3(this.f_82479_ * factorX, this.f_82480_ * factorY, this.f_82481_ * factorZ);
   }

   public net.minecraft.world.phys.Vec3 m_272010_(RandomSource p_272010_1_, float p_272010_2_) {
      return this.m_82520_(
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_),
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_),
         (double)((p_272010_1_.m_188501_() - 0.5F) * p_272010_2_)
      );
   }

   public double m_82553_() {
      return Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_);
   }

   public double m_82556_() {
      return this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_;
   }

   public double m_165924_() {
      return Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82481_ * this.f_82481_);
   }

   public double m_165925_() {
      return this.f_82479_ * this.f_82479_ + this.f_82481_ * this.f_82481_;
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else if (p_equals_1_ instanceof net.minecraft.world.phys.Vec3 vec3) {
         if (Double.compare(vec3.f_82479_, this.f_82479_) != 0) {
            return false;
         } else {
            return Double.compare(vec3.f_82480_, this.f_82480_) != 0 ? false : Double.compare(vec3.f_82481_, this.f_82481_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      long j = Double.doubleToLongBits(this.f_82479_);
      int i = (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.f_82480_);
      i = 31 * i + (int)(j ^ j >>> 32);
      j = Double.doubleToLongBits(this.f_82481_);
      return 31 * i + (int)(j ^ j >>> 32);
   }

   public String toString() {
      return "(" + this.f_82479_ + ", " + this.f_82480_ + ", " + this.f_82481_ + ")";
   }

   public net.minecraft.world.phys.Vec3 m_165921_(net.minecraft.world.phys.Vec3 p_165921_1_, double p_165921_2_) {
      return new net.minecraft.world.phys.Vec3(
         net.minecraft.util.Mth.m_14139_(p_165921_2_, this.f_82479_, p_165921_1_.f_82479_),
         net.minecraft.util.Mth.m_14139_(p_165921_2_, this.f_82480_, p_165921_1_.f_82480_),
         net.minecraft.util.Mth.m_14139_(p_165921_2_, this.f_82481_, p_165921_1_.f_82481_)
      );
   }

   public net.minecraft.world.phys.Vec3 m_82496_(float pitch) {
      float f = net.minecraft.util.Mth.m_14089_(pitch);
      float f1 = net.minecraft.util.Mth.m_14031_(pitch);
      double d0 = this.f_82479_;
      double d1 = this.f_82480_ * (double)f + this.f_82481_ * (double)f1;
      double d2 = this.f_82481_ * (double)f - this.f_82480_ * (double)f1;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public net.minecraft.world.phys.Vec3 m_82524_(float yaw) {
      float f = net.minecraft.util.Mth.m_14089_(yaw);
      float f1 = net.minecraft.util.Mth.m_14031_(yaw);
      double d0 = this.f_82479_ * (double)f + this.f_82481_ * (double)f1;
      double d1 = this.f_82480_;
      double d2 = this.f_82481_ * (double)f - this.f_82479_ * (double)f1;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public net.minecraft.world.phys.Vec3 m_82535_(float p_82535_1_) {
      float f = net.minecraft.util.Mth.m_14089_(p_82535_1_);
      float f1 = net.minecraft.util.Mth.m_14031_(p_82535_1_);
      double d0 = this.f_82479_ * (double)f + this.f_82480_ * (double)f1;
      double d1 = this.f_82480_ * (double)f - this.f_82479_ * (double)f1;
      double d2 = this.f_82481_;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public static net.minecraft.world.phys.Vec3 m_82503_(Vec2 p_82503_0_) {
      return m_82498_(p_82503_0_.f_82470_, p_82503_0_.f_82471_);
   }

   public static net.minecraft.world.phys.Vec3 m_82498_(float pitch, float yaw) {
      float f = net.minecraft.util.Mth.m_14089_(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f1 = net.minecraft.util.Mth.m_14031_(-yaw * (float) (Math.PI / 180.0) - (float) Math.PI);
      float f2 = -net.minecraft.util.Mth.m_14089_(-pitch * (float) (Math.PI / 180.0));
      float f3 = net.minecraft.util.Mth.m_14031_(-pitch * (float) (Math.PI / 180.0));
      return new net.minecraft.world.phys.Vec3((double)(f1 * f2), (double)f3, (double)(f * f2));
   }

   public net.minecraft.world.phys.Vec3 m_82517_(EnumSet<net.minecraft.core.Direction.Axis> axes) {
      double d0 = axes.contains(net.minecraft.core.Direction.Axis.X) ? (double)net.minecraft.util.Mth.m_14107_(this.f_82479_) : this.f_82479_;
      double d1 = axes.contains(net.minecraft.core.Direction.Axis.Y) ? (double)net.minecraft.util.Mth.m_14107_(this.f_82480_) : this.f_82480_;
      double d2 = axes.contains(net.minecraft.core.Direction.Axis.Z) ? (double)net.minecraft.util.Mth.m_14107_(this.f_82481_) : this.f_82481_;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public double m_82507_(net.minecraft.core.Direction.Axis axis) {
      return axis.m_6150_(this.f_82479_, this.f_82480_, this.f_82481_);
   }

   public net.minecraft.world.phys.Vec3 m_193103_(net.minecraft.core.Direction.Axis p_193103_1_, double p_193103_2_) {
      double d0 = p_193103_1_ == net.minecraft.core.Direction.Axis.X ? p_193103_2_ : this.f_82479_;
      double d1 = p_193103_1_ == net.minecraft.core.Direction.Axis.Y ? p_193103_2_ : this.f_82480_;
      double d2 = p_193103_1_ == net.minecraft.core.Direction.Axis.Z ? p_193103_2_ : this.f_82481_;
      return new net.minecraft.world.phys.Vec3(d0, d1, d2);
   }

   public net.minecraft.world.phys.Vec3 m_231075_(net.minecraft.core.Direction p_231075_1_, double p_231075_2_) {
      Vec3i vec3i = p_231075_1_.m_122436_();
      return new net.minecraft.world.phys.Vec3(
         this.f_82479_ + p_231075_2_ * (double)vec3i.m_123341_(),
         this.f_82480_ + p_231075_2_ * (double)vec3i.m_123342_(),
         this.f_82481_ + p_231075_2_ * (double)vec3i.m_123343_()
      );
   }

   public final double m_7096_() {
      return this.f_82479_;
   }

   public final double m_7098_() {
      return this.f_82480_;
   }

   public final double m_7094_() {
      return this.f_82481_;
   }

   public Vector3f m_252839_() {
      return new Vector3f((float)this.f_82479_, (float)this.f_82480_, (float)this.f_82481_);
   }
}
