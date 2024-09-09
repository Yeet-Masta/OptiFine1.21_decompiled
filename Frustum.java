import net.minecraft.src.C_3040_;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import net.optifine.util.MathUtils;
import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class Frustum {
   public static final int a = 4;
   private final FrustumIntersection b = new FrustumIntersection();
   private final Matrix4f c = new Matrix4f();
   protected Vector4f d;
   private double e;
   private double f;
   private double g;
   public boolean disabled = false;
   protected boolean usePlanes = false;
   protected final Vector4f[] frustum = new Vector4f[6];

   public Frustum(Matrix4f matrixIn, Matrix4f projectionIn) {
      this.a(matrixIn, projectionIn);
   }

   public Frustum(Frustum frustumIn) {
      this.b.set(frustumIn.c);
      this.c.set(frustumIn.c);
      this.e = frustumIn.e;
      this.f = frustumIn.f;
      this.g = frustumIn.g;
      this.d = frustumIn.d;
      this.disabled = frustumIn.disabled;
      this.usePlanes = frustumIn.usePlanes;
      System.arraycopy(frustumIn.frustum, 0, this.frustum, 0, frustumIn.frustum.length);
   }

   public Frustum a(int stepIn) {
      double d0 = Math.floor(this.e / (double)stepIn) * (double)stepIn;
      double d1 = Math.floor(this.f / (double)stepIn) * (double)stepIn;
      double d2 = Math.floor(this.g / (double)stepIn) * (double)stepIn;
      double d3 = Math.ceil(this.e / (double)stepIn) * (double)stepIn;
      double d4 = Math.ceil(this.f / (double)stepIn) * (double)stepIn;
      int count = 0;

      for (double d5 = Math.ceil(this.g / (double)stepIn) * (double)stepIn;
         this.b
               .intersectAab((float)(d0 - this.e), (float)(d1 - this.f), (float)(d2 - this.g), (float)(d3 - this.e), (float)(d4 - this.f), (float)(d5 - this.g))
            != -2;
         this.g = this.g - (double)(this.d.z() * 4.0F)
      ) {
         this.e = this.e - (double)(this.d.x() * 4.0F);
         this.f = this.f - (double)(this.d.y() * 4.0F);
         if (count++ > 10) {
            break;
         }
      }

      return this;
   }

   public void a(double camX, double camY, double camZ) {
      this.e = camX;
      this.f = camY;
      this.g = camZ;
   }

   private void a(Matrix4f matrixIn, Matrix4f projectionIn) {
      projectionIn.mul(matrixIn, this.c);
      this.b.set(this.c);
      this.d = this.c.transformTranspose(new Vector4f(0.0F, 0.0F, 1.0F, 0.0F));
      Matrix4f matrix4f = new Matrix4f(this.c).transpose();
      this.setFrustumPlane(matrix4f, -1, 0, 0, 0);
      this.setFrustumPlane(matrix4f, 1, 0, 0, 1);
      this.setFrustumPlane(matrix4f, 0, -1, 0, 2);
      this.setFrustumPlane(matrix4f, 0, 1, 0, 3);
      this.setFrustumPlane(matrix4f, 0, 0, -1, 4);
      this.setFrustumPlane(matrix4f, 0, 0, 1, 5);
   }

   public boolean a(C_3040_ aabbIn) {
      return aabbIn == IForgeBlockEntity.INFINITE_EXTENT_AABB
         ? true
         : this.a(aabbIn.f_82288_, aabbIn.f_82289_, aabbIn.f_82290_, aabbIn.f_82291_, aabbIn.f_82292_, aabbIn.f_82293_);
   }

   private boolean a(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
      if (this.disabled) {
         return true;
      } else {
         float f = (float)(minX - this.e);
         float f1 = (float)(minY - this.f);
         float f2 = (float)(minZ - this.g);
         float f3 = (float)(maxX - this.e);
         float f4 = (float)(maxY - this.f);
         float f5 = (float)(maxZ - this.g);
         return this.usePlanes ? this.isBoxInFrustumRaw(f, f1, f2, f3, f4, f5) : this.b.testAab(f, f1, f2, f3, f4, f5);
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
      return this.e;
   }

   public double getCameraY() {
      return this.f;
   }

   public double getCameraZ() {
      return this.g;
   }

   private void setFrustumPlane(Matrix4f matrixIn, int xIn, int yIn, int zIn, int wIn) {
      Vector4f vector4f = new Vector4f((float)xIn, (float)yIn, (float)zIn, 1.0F);
      MathUtils.transform(vector4f, matrixIn);
      vector4f.normalize();
      this.frustum[wIn] = vector4f;
   }
}
