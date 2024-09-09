package net.optifine;

import java.util.EnumSet;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class Vec3M extends Vec3 implements Position {
   public Vec3M() {
      super(0.0, 0.0, 0.0);
   }

   public Vec3M(double xIn, double yIn, double zIn) {
      super(xIn, yIn, zIn);
   }

   public Vec3M(Vector3f vecIn) {
      super(vecIn);
   }

   public Vec3M set(double x, double y, double z) {
      this.f_82479_ = x;
      this.f_82480_ = y;
      this.f_82481_ = z;
      return this;
   }

   public Vec3M set(Vec3 vec) {
      return this.set(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public Vec3M subtractReverse(Vec3 vec) {
      return this.set(vec.f_82479_ - this.f_82479_, vec.f_82480_ - this.f_82480_, vec.f_82481_ - this.f_82481_);
   }

   public Vec3M normalize() {
      double d0 = Math.sqrt(this.f_82479_ * this.f_82479_ + this.f_82480_ * this.f_82480_ + this.f_82481_ * this.f_82481_);
      return d0 < 1.0E-4 ? this.set(0.0, 0.0, 0.0) : this.set(this.f_82479_ / d0, this.f_82480_ / d0, this.f_82481_ / d0);
   }

   public double m_82526_(Vec3 vec) {
      return this.f_82479_ * vec.f_82479_ + this.f_82480_ * vec.f_82480_ + this.f_82481_ * vec.f_82481_;
   }

   public Vec3M crossProduct(Vec3 vec) {
      return this.set(this.f_82480_ * vec.f_82481_ - this.f_82481_ * vec.f_82480_, this.f_82481_ * vec.f_82479_ - this.f_82479_ * vec.f_82481_, this.f_82479_ * vec.f_82480_ - this.f_82480_ * vec.f_82479_);
   }

   public Vec3M subtract(Vec3 vec) {
      return this.subtract(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public Vec3M subtract(double x, double y, double z) {
      return this.add(-x, -y, -z);
   }

   public Vec3M add(Vec3 vec) {
      return this.add(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public Vec3M add(double x, double y, double z) {
      return this.set(this.f_82479_ + x, this.f_82480_ + y, this.f_82481_ + z);
   }

   public Vec3M scale(double factor) {
      return this.mul(factor, factor, factor);
   }

   public Vec3M inverse() {
      return this.scale(-1.0);
   }

   public Vec3M mul(Vec3 vec) {
      return this.mul(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }

   public Vec3M mul(double factorX, double factorY, double factorZ) {
      return this.set(this.f_82479_ * factorX, this.f_82480_ * factorY, this.f_82481_ * factorZ);
   }

   public Vec3M lerp(Vec3M vec, double factor) {
      return this.set(Mth.m_14139_(factor, this.f_82479_, vec.f_82479_), Mth.m_14139_(factor, this.f_82480_, vec.f_82480_), Mth.m_14139_(factor, this.f_82481_, vec.f_82481_));
   }

   public Vec3M rotatePitch(float pitch) {
      float f = Mth.m_14089_(pitch);
      float f1 = Mth.m_14031_(pitch);
      double d0 = this.f_82479_;
      double d1 = this.f_82480_ * (double)f + this.f_82481_ * (double)f1;
      double d2 = this.f_82481_ * (double)f - this.f_82480_ * (double)f1;
      return this.set(d0, d1, d2);
   }

   public Vec3M rotateYaw(float yaw) {
      float f = Mth.m_14089_(yaw);
      float f1 = Mth.m_14031_(yaw);
      double d0 = this.f_82479_ * (double)f + this.f_82481_ * (double)f1;
      double d1 = this.f_82480_;
      double d2 = this.f_82481_ * (double)f - this.f_82479_ * (double)f1;
      return this.set(d0, d1, d2);
   }

   public Vec3M zRot(float angle) {
      float f = Mth.m_14089_(angle);
      float f1 = Mth.m_14031_(angle);
      double d0 = this.f_82479_ * (double)f + this.f_82480_ * (double)f1;
      double d1 = this.f_82480_ * (double)f - this.f_82479_ * (double)f1;
      double d2 = this.f_82481_;
      return this.set(d0, d1, d2);
   }

   public Vec3M align(EnumSet axes) {
      double d0 = axes.contains(Direction.Axis.field_29) ? (double)Mth.m_14107_(this.f_82479_) : this.f_82479_;
      double d1 = axes.contains(Direction.Axis.field_30) ? (double)Mth.m_14107_(this.f_82480_) : this.f_82480_;
      double d2 = axes.contains(Direction.Axis.field_31) ? (double)Mth.m_14107_(this.f_82481_) : this.f_82481_;
      return this.set(d0, d1, d2);
   }

   public double m_82507_(Direction.Axis axis) {
      return axis.m_6150_(this.f_82479_, this.f_82480_, this.f_82481_);
   }

   public Vec3M with(Direction.Axis axis, double distance) {
      double d0 = axis == Direction.Axis.field_29 ? distance : this.f_82479_;
      double d1 = axis == Direction.Axis.field_30 ? distance : this.f_82480_;
      double d2 = axis == Direction.Axis.field_31 ? distance : this.f_82481_;
      return this.set(d0, d1, d2);
   }

   public void setRgb(int rgb) {
      double r = (double)(rgb >> 16 & 255) / 255.0;
      double g = (double)(rgb >> 8 & 255) / 255.0;
      double b = (double)(rgb & 255) / 255.0;
      this.set(r, g, b);
   }

   public Vec3M fromRgbM(int rgb) {
      this.setRgb(rgb);
      return this;
   }
}
