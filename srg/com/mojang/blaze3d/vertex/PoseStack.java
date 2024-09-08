package com.mojang.blaze3d.vertex;

import com.google.common.collect.Queues;
import com.mojang.math.Axis;
import com.mojang.math.MatrixUtil;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.Util;
import net.minecraftforge.client.extensions.IForgePoseStack;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PoseStack implements IForgePoseStack {
   Deque<PoseStack.Pose> freeEntries = new ArrayDeque();
   private final Deque<PoseStack.Pose> f_85834_ = Util.m_137469_(Queues.newArrayDeque(), dequeIn -> {
      Matrix4f matrix4f = new Matrix4f();
      Matrix3f matrix3f = new Matrix3f();
      dequeIn.add(new PoseStack.Pose(matrix4f, matrix3f));
   });

   public void m_85837_(double x, double y, double z) {
      this.m_252880_((float)x, (float)y, (float)z);
   }

   public void m_252880_(float x, float y, float z) {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.translate(x, y, z);
   }

   public void m_85841_(float x, float y, float z) {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.scale(x, y, z);
      if (Math.abs(x) != Math.abs(y) || Math.abs(y) != Math.abs(z)) {
         posestack$pose.f_85853_.scale(1.0F / x, 1.0F / y, 1.0F / z);
         posestack$pose.f_317074_ = false;
      } else if (x < 0.0F || y < 0.0F || z < 0.0F) {
         posestack$pose.f_85853_.scale(Math.signum(x), Math.signum(y), Math.signum(z));
      }
   }

   public void m_252781_(Quaternionf quaternionIn) {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.rotate(quaternionIn);
      posestack$pose.f_85853_.rotate(quaternionIn);
   }

   public void m_272245_(Quaternionf quatIn, float xIn, float yIn, float zIn) {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.rotateAround(quatIn, xIn, yIn, zIn);
      posestack$pose.f_85853_.rotate(quatIn);
   }

   public void m_85836_() {
      PoseStack.Pose entry = (PoseStack.Pose)this.freeEntries.pollLast();
      if (entry != null) {
         PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
         entry.f_85852_.set(posestack$pose.f_85852_);
         entry.f_85853_.set(posestack$pose.f_85853_);
         entry.f_317074_ = posestack$pose.f_317074_;
         this.f_85834_.addLast(entry);
      } else {
         this.f_85834_.addLast(new PoseStack.Pose((PoseStack.Pose)this.f_85834_.getLast()));
      }
   }

   public void m_85849_() {
      PoseStack.Pose entry = (PoseStack.Pose)this.f_85834_.removeLast();
      if (entry != null) {
         this.freeEntries.add(entry);
      }
   }

   public PoseStack.Pose m_85850_() {
      return (PoseStack.Pose)this.f_85834_.getLast();
   }

   public boolean m_85851_() {
      return this.f_85834_.size() == 1;
   }

   public void rotateDegXp(float angle) {
      this.m_252781_(Axis.f_252529_.m_252977_(angle));
   }

   public void rotateDegXn(float angle) {
      this.m_252781_(Axis.f_252495_.m_252977_(angle));
   }

   public void rotateDegYp(float angle) {
      this.m_252781_(Axis.f_252436_.m_252977_(angle));
   }

   public void rotateDegYn(float angle) {
      this.m_252781_(Axis.f_252392_.m_252977_(angle));
   }

   public void rotateDegZp(float angle) {
      this.m_252781_(Axis.f_252403_.m_252977_(angle));
   }

   public void rotateDegZn(float angle) {
      this.m_252781_(Axis.f_252393_.m_252977_(angle));
   }

   public void rotateDeg(float angle, float x, float y, float z) {
      Vector3f vec = new Vector3f(x, y, z);
      Quaternionf quat = MathUtils.rotationDegrees(vec, angle);
      this.m_252781_(quat);
   }

   public int size() {
      return this.f_85834_.size();
   }

   public String toString() {
      return this.m_85850_().toString() + "Depth: " + this.f_85834_.size();
   }

   public void m_166856_() {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.identity();
      posestack$pose.f_85853_.identity();
      posestack$pose.f_317074_ = true;
   }

   public void m_318714_(Matrix4f matrixIn) {
      PoseStack.Pose posestack$pose = (PoseStack.Pose)this.f_85834_.getLast();
      posestack$pose.f_85852_.mul(matrixIn);
      if (!MatrixUtil.m_321551_(matrixIn)) {
         if (MatrixUtil.m_319661_(matrixIn)) {
            posestack$pose.f_85853_.mul(new Matrix3f(matrixIn));
         } else {
            posestack$pose.m_319145_();
         }
      }
   }

   public static final class Pose {
      final Matrix4f f_85852_;
      final Matrix3f f_85853_;
      boolean f_317074_ = true;

      Pose(Matrix4f matrixIn, Matrix3f normalIn) {
         this.f_85852_ = matrixIn;
         this.f_85853_ = normalIn;
      }

      Pose(PoseStack.Pose poseIn) {
         this.f_85852_ = new Matrix4f(poseIn.f_85852_);
         this.f_85853_ = new Matrix3f(poseIn.f_85853_);
         this.f_317074_ = poseIn.f_317074_;
      }

      void m_319145_() {
         this.f_85853_.set(this.f_85852_).invert().transpose();
         this.f_317074_ = false;
      }

      public Matrix4f m_252922_() {
         return this.f_85852_;
      }

      public Matrix3f m_252943_() {
         return this.f_85853_;
      }

      public Vector3f m_322076_(Vector3f vectorIn, Vector3f destIn) {
         return this.m_323822_(vectorIn.x, vectorIn.y, vectorIn.z, destIn);
      }

      public Vector3f m_323822_(float xIn, float yIn, float zIn, Vector3f destIn) {
         Vector3f vector3f = this.f_85853_.transform(xIn, yIn, zIn, destIn);
         return this.f_317074_ ? vector3f : vector3f.normalize();
      }

      public PoseStack.Pose m_323639_() {
         return new PoseStack.Pose(this);
      }

      public String toString() {
         return this.f_85852_.toString() + this.f_85853_.toString();
      }
   }
}
