package net.optifine.shaders;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.util.Mth;
import net.optifine.Config;
import net.optifine.util.MathUtils;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class ShadowFrustum extends Frustum {
   public ShadowFrustum(Matrix4f matrixIn, Matrix4f projectionIn) {
      super(matrixIn, projectionIn);
      this.extendForShadows(matrixIn, projectionIn);
      super.usePlanes = true;
   }

   private void extendForShadows(Matrix4f matrixIn, Matrix4f projectionIn) {
      ClientLevel world = Config.getMinecraft().f_91073_;
      if (world != null) {
         Matrix4f matrixFull = MathUtils.copy(projectionIn);
         matrixFull.mul(matrixIn);
         matrixFull.transpose();
         Vector4f viewUp = new Vector4f(0.0F, 1.0F, 0.0F, 0.0F);
         MathUtils.transform(viewUp, matrixFull);
         viewUp.normalize();
         Vector4f viewRight = new Vector4f(-1.0F, 0.0F, 0.0F, 0.0F);
         MathUtils.transform(viewRight, matrixFull);
         viewRight.normalize();
         float partialTicks = 0.0F;
         float car = world.m_46490_(partialTicks);
         float sunTiltRad = Shaders.sunPathRotation * Mth.deg2Rad;
         float sar = car > Mth.PId2 && car < 3.0F * Mth.PId2 ? car + (float) Math.PI : car;
         float sx = -Mth.m_14031_(sar);
         float sy = Mth.m_14089_(sar) * Mth.m_14089_(sunTiltRad);
         float sz = -Mth.m_14089_(sar) * Mth.m_14031_(sunTiltRad);
         Vector4f vecSun = new Vector4f(sx, sy, sz, 0.0F);
         vecSun.normalize();
         Vector3f viewUpDot = MathUtils.makeVector3f(viewUp);
         viewUpDot.mul(viewUp.dot(vecSun));
         Vector3f vecSunH3 = MathUtils.makeVector3f(vecSun);
         vecSunH3.sub(viewUpDot);
         vecSunH3.normalize();
         Vector4f vecSunH = new Vector4f(vecSunH3.m_305649_(), vecSunH3.m_306225_(), vecSunH3.m_240700_(), 0.0F);
         Vector3f viewRightDot = MathUtils.makeVector3f(viewRight);
         viewRightDot.mul(viewRight.dot(vecSun));
         Vector3f vecSunV3 = MathUtils.makeVector3f(vecSun);
         vecSunV3.sub(viewRightDot);
         vecSunV3.normalize();
         Vector4f vecSunV = new Vector4f(vecSunV3.m_305649_(), vecSunV3.m_306225_(), vecSunV3.m_240700_(), 0.0F);
         Vector4f vecRight = this.frustum[0];
         Vector4f vecLeft = this.frustum[1];
         Vector4f vecTop = this.frustum[2];
         Vector4f vecBottom = this.frustum[3];
         Vector4f vecFar = this.frustum[4];
         Vector4f vecNear = this.frustum[5];
         vecRight.normalize();
         vecLeft.normalize();
         vecTop.normalize();
         vecBottom.normalize();
         vecFar.normalize();
         vecNear.normalize();
         float dotRight = vecRight.dot(vecSunH);
         float dotLeft = vecLeft.dot(vecSunH);
         float dotTop = vecTop.dot(vecSunV);
         float dotBottom = vecBottom.dot(vecSunV);
         float farPlaneDistance = Config.getGameRenderer().m_109152_();
         float mulFarDist = Config.isFogOff() ? 1.414F : 1.0F;
         float rotRight = 0.0F;
         float rotLeft = 0.0F;
         if (dotRight < 0.0F || dotLeft < 0.0F) {
            vecNear.add(0.0F, 0.0F, 0.0F, farPlaneDistance);
            if (dotRight < 0.0F && dotLeft < 0.0F) {
               rotRight = this.rotateDotPlus(vecRight, vecSunH, -1, viewUp);
               rotLeft = this.rotateDotPlus(vecLeft, vecSunH, 1, viewUp);
               vecRight.set(-vecRight.m_305649_(), -vecRight.m_306225_(), -vecRight.m_240700_(), -vecRight.m_245239_());
               vecLeft.set(-vecLeft.m_305649_(), -vecLeft.m_306225_(), -vecLeft.m_240700_(), -vecLeft.m_245239_());
               float distRight = -dotRight * farPlaneDistance * mulFarDist;
               float distLeft = -dotLeft * farPlaneDistance * mulFarDist;
               vecRight.add(0.0F, 0.0F, 0.0F, distRight);
               vecLeft.add(0.0F, 0.0F, 0.0F, distLeft);
            } else if (dotRight < 0.0F) {
               rotRight = this.rotateDotPlus(vecRight, vecSunH, -1, viewUp);
            } else {
               rotLeft = this.rotateDotPlus(vecLeft, vecSunH, 1, viewUp);
            }
         }

         int minWorldHeight = world.m_141937_();
         int maxWorldHeight = world.m_151558_();
         float eyeHeight = (float)((int)Config.getMinecraft().f_91074_.m_20188_());
         float maxDistBottom = Config.limit(eyeHeight - (float)minWorldHeight, 0.0F, farPlaneDistance);
         float maxDistTop = Config.limit((float)maxWorldHeight - eyeHeight, 0.0F, farPlaneDistance);
         float rotTop = 0.0F;
         float rotBottom = 0.0F;
         if (dotTop < 0.0F || dotBottom < 0.0F) {
            vecNear.add(0.0F, 0.0F, 0.0F, farPlaneDistance);
            if (dotTop < 0.0F && dotBottom < 0.0F) {
               rotTop = this.rotateDotPlus(vecTop, vecSunV, -1, viewRight);
               rotBottom = this.rotateDotPlus(vecBottom, vecSunV, 1, viewRight);
               vecTop.set(-vecTop.m_305649_(), -vecTop.m_306225_(), -vecTop.m_240700_(), -vecTop.m_245239_());
               vecBottom.set(-vecBottom.m_305649_(), -vecBottom.m_306225_(), -vecBottom.m_240700_(), -vecBottom.m_245239_());
               float distTop = -dotTop * maxDistTop;
               float distBottom = -dotBottom * maxDistBottom;
               vecTop.add(0.0F, 0.0F, 0.0F, distTop);
               vecBottom.add(0.0F, 0.0F, 0.0F, distBottom);
            } else if (dotTop < 0.0F) {
               rotTop = this.rotateDotPlus(vecTop, vecSunV, -1, viewRight);
            } else {
               rotBottom = this.rotateDotPlus(vecBottom, vecSunV, 1, viewRight);
            }
         }
      }
   }

   private float rotateDotPlus(Vector4f vecFrustum, Vector4f vecSun, int angleDeg, Vector4f vecRot) {
      Vector3f vecRot3 = MathUtils.makeVector3f(vecRot);
      Quaternionf rot = MathUtils.m_252977_(vecRot3, (float)angleDeg);
      float angleDegSum = 0.0F;

      while (true) {
         float dot = vecFrustum.dot(vecSun);
         if (dot >= 0.0F) {
            return angleDegSum;
         }

         MathUtils.transform(vecFrustum, rot);
         vecFrustum.normalize();
         angleDegSum += (float)angleDeg;
      }
   }
}
