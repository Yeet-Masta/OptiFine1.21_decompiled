package net.minecraft.client.renderer.culling;

import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import net.optifine.util.MathUtils;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Frustum {
   public static final int f_194437_ = 4;
   private final FrustumIntersection f_252531_ = new FrustumIntersection();
   private final Matrix4f f_252406_ = new Matrix4f();
   protected Vector4f f_194438_;
   private double f_112996_;
   private double f_112997_;
   private double f_112998_;
   public boolean disabled = false;
   protected boolean usePlanes = false;
   protected final Vector4f[] frustum = new Vector4f[6];

   public Frustum(Matrix4f matrixIn, Matrix4f projectionIn) {
      this.m_253155_(matrixIn, projectionIn);
   }

   public Frustum(net.minecraft.client.renderer.culling.Frustum frustumIn) {
      this.f_252531_.set(frustumIn.f_252406_);
      this.f_252406_.set(frustumIn.f_252406_);
      this.f_112996_ = frustumIn.f_112996_;
      this.f_112997_ = frustumIn.f_112997_;
      this.f_112998_ = frustumIn.f_112998_;
      this.f_194438_ = frustumIn.f_194438_;
      this.disabled = frustumIn.disabled;
      this.usePlanes = frustumIn.usePlanes;
      System.arraycopy(frustumIn.frustum, 0, this.frustum, 0, frustumIn.frustum.length);
   }

   public net.minecraft.client.renderer.culling.Frustum m_194441_(int stepIn) {
      double d0 = Math.floor(this.f_112996_ / (double)stepIn) * (double)stepIn;
      double d1 = Math.floor(this.f_112997_ / (double)stepIn) * (double)stepIn;
      double d2 = Math.floor(this.f_112998_ / (double)stepIn) * (double)stepIn;
      double d3 = Math.ceil(this.f_112996_ / (double)stepIn) * (double)stepIn;
      double d4 = Math.ceil(this.f_112997_ / (double)stepIn) * (double)stepIn;
      int count = 0;

      for (double d5 = Math.ceil(this.f_112998_ / (double)stepIn) * (double)stepIn;
         this.f_252531_
               .intersectAab(
                  (float)(d0 - this.f_112996_),
                  (float)(d1 - this.f_112997_),
                  (float)(d2 - this.f_112998_),
                  (float)(d3 - this.f_112996_),
                  (float)(d4 - this.f_112997_),
                  (float)(d5 - this.f_112998_)
               )
            != -2;
         this.f_112998_ = this.f_112998_ - (double)(this.f_194438_.z() * 4.0F)
      ) {
         this.f_112996_ = this.f_112996_ - (double)(this.f_194438_.x() * 4.0F);
         this.f_112997_ = this.f_112997_ - (double)(this.f_194438_.y() * 4.0F);
         if (count++ > 10) {
            break;
         }
      }

      return this;
   }

   public void m_113002_(double camX, double camY, double camZ) {
      this.f_112996_ = camX;
      this.f_112997_ = camY;
      this.f_112998_ = camZ;
   }

   private void m_253155_(Matrix4f matrixIn, Matrix4f projectionIn) {
      projectionIn.mul(matrixIn, this.f_252406_);
      this.f_252531_.set(this.f_252406_);
      this.f_194438_ = this.f_252406_.transformTranspose(new Vector4f(0.0F, 0.0F, 1.0F, 0.0F));
      Matrix4f matrix4f = new Matrix4f(this.f_252406_).transpose();
      this.setFrustumPlane(matrix4f, -1, 0, 0, 0);
      this.setFrustumPlane(matrix4f, 1, 0, 0, 1);
      this.setFrustumPlane(matrix4f, 0, -1, 0, 2);
      this.setFrustumPlane(matrix4f, 0, 1, 0, 3);
      this.setFrustumPlane(matrix4f, 0, 0, -1, 4);
      this.setFrustumPlane(matrix4f, 0, 0, 1, 5);
   }

   public boolean m_113029_(AABB aabbIn) {
      return aabbIn == IForgeBlockEntity.INFINITE_EXTENT_AABB
         ? true
         : this.m_113006_(aabbIn.f_82288_, aabbIn.f_82289_, aabbIn.f_82290_, aabbIn.f_82291_, aabbIn.f_82292_, aabbIn.f_82293_);
   }

   private boolean m_113006_(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      if (this.disabled) {
         return true;
      } else {
         float f = (float)(minX - this.f_112996_);
         float f1 = (float)(minY - this.f_112997_);
         float f2 = (float)(minZ - this.f_112998_);
         float f3 = (float)(maxX - this.f_112996_);
         float f4 = (float)(maxY - this.f_112997_);
         float f5 = (float)(maxZ - this.f_112998_);
         return this.usePlanes ? this.isBoxInFrustumRaw(f, f1, f2, f3, f4, f5) : this.f_252531_.testAab(f, f1, f2, f3, f4, f5);
      }
   }

   private boolean isBoxInFrustumRaw(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
      for (int i = 0; i < 6; i++) {
         Vector4f vector4f = this.frustum[i];
         float x = vector4f.x();
         float y = vector4f.y();
         float z = vector4f.z();
         float w = vector4f.w();
         if (x * minX + y * minY + z * minZ + w <= 0.0F
            && x * maxX + y * minY + z * minZ + w <= 0.0F
            && x * minX + y * maxY + z * minZ + w <= 0.0F
            && x * maxX + y * maxY + z * minZ + w <= 0.0F
            && x * minX + y * minY + z * maxZ + w <= 0.0F
            && x * maxX + y * minY + z * maxZ + w <= 0.0F
            && x * minX + y * maxY + z * maxZ + w <= 0.0F
            && x * maxX + y * maxY + z * maxZ + w <= 0.0F) {
            return false;
         }
      }

      return true;
   }

   public boolean isBoxInFrustumFully(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      if (this.disabled) {
         return true;
      } else {
         float minXf = (float)minX;
         float minYf = (float)minY;
         float minZf = (float)minZ;
         float maxXf = (float)maxX;
         float maxYf = (float)maxY;
         float maxZf = (float)maxZ;

         for (int i = 0; i < 6; i++) {
            Vector4f frustumi = this.frustum[i];
            float x = frustumi.x();
            float y = frustumi.y();
            float z = frustumi.z();
            float w = frustumi.w();
            if (i < 4) {
               if (x * minXf + y * minYf + z * minZf + w <= 0.0F
                  || x * maxXf + y * minYf + z * minZf + w <= 0.0F
                  || x * minXf + y * maxYf + z * minZf + w <= 0.0F
                  || x * maxXf + y * maxYf + z * minZf + w <= 0.0F
                  || x * minXf + y * minYf + z * maxZf + w <= 0.0F
                  || x * maxXf + y * minYf + z * maxZf + w <= 0.0F
                  || x * minXf + y * maxYf + z * maxZf + w <= 0.0F
                  || x * maxXf + y * maxYf + z * maxZf + w <= 0.0F) {
                  return false;
               }
            } else if (x * minXf + y * minYf + z * minZf + w <= 0.0F
               && x * maxXf + y * minYf + z * minZf + w <= 0.0F
               && x * minXf + y * maxYf + z * minZf + w <= 0.0F
               && x * maxXf + y * maxYf + z * minZf + w <= 0.0F
               && x * minXf + y * minYf + z * maxZf + w <= 0.0F
               && x * maxXf + y * minYf + z * maxZf + w <= 0.0F
               && x * minXf + y * maxYf + z * maxZf + w <= 0.0F
               && x * maxXf + y * maxYf + z * maxZf + w <= 0.0F) {
               return false;
            }
         }

         return true;
      }
   }

   public double getCameraX() {
      return this.f_112996_;
   }

   public double getCameraY() {
      return this.f_112997_;
   }

   public double getCameraZ() {
      return this.f_112998_;
   }

   private void setFrustumPlane(Matrix4f matrixIn, int xIn, int yIn, int zIn, int wIn) {
      Vector4f vector4f = new Vector4f((float)xIn, (float)yIn, (float)zIn, 1.0F);
      MathUtils.transform(vector4f, matrixIn);
      vector4f.normalize();
      this.frustum[wIn] = vector4f;
   }
}
