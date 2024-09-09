import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_252379_;
import net.minecraftforge.client.extensions.IForgePoseStack;
import net.optifine.util.MathUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PoseStack implements IForgePoseStack {
   Deque<PoseStack.a> freeEntries = new ArrayDeque();
   private final Deque<PoseStack.a> a = Util.a(Queues.newArrayDeque(), dequeIn -> {
      Matrix4f matrix4f = new Matrix4f();
      Matrix3f matrix3f = new Matrix3f();
      dequeIn.add(new PoseStack.a(matrix4f, matrix3f));
   });

   public void a(double x, double y, double z) {
      this.a((float)x, (float)y, (float)z);
   }

   public void a(float x, float y, float z) {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.translate(x, y, z);
   }

   public void b(float x, float y, float z) {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.scale(x, y, z);
      if (Math.abs(x) != Math.abs(y) || Math.abs(y) != Math.abs(z)) {
         posestack$pose.b.scale(1.0F / x, 1.0F / y, 1.0F / z);
         posestack$pose.c = false;
      } else if (x < 0.0F || y < 0.0F || z < 0.0F) {
         posestack$pose.b.scale(Math.signum(x), Math.signum(y), Math.signum(z));
      }
   }

   public void a(Quaternionf quaternionIn) {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.rotate(quaternionIn);
      posestack$pose.b.rotate(quaternionIn);
   }

   public void a(Quaternionf quatIn, float xIn, float yIn, float zIn) {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.rotateAround(quatIn, xIn, yIn, zIn);
      posestack$pose.b.rotate(quatIn);
   }

   public void a() {
      PoseStack.a entry = (PoseStack.a)this.freeEntries.pollLast();
      if (entry != null) {
         PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
         entry.a.set(posestack$pose.a);
         entry.b.set(posestack$pose.b);
         entry.c = posestack$pose.c;
         this.a.addLast(entry);
      } else {
         this.a.addLast(new PoseStack.a((PoseStack.a)this.a.getLast()));
      }
   }

   public void b() {
      PoseStack.a entry = (PoseStack.a)this.a.removeLast();
      if (entry != null) {
         this.freeEntries.add(entry);
      }
   }

   public PoseStack.a c() {
      return (PoseStack.a)this.a.getLast();
   }

   public boolean d() {
      return this.a.size() == 1;
   }

   public void rotateDegXp(float angle) {
      this.a(C_252363_.f_252529_.m_252977_(angle));
   }

   public void rotateDegXn(float angle) {
      this.a(C_252363_.f_252495_.m_252977_(angle));
   }

   public void rotateDegYp(float angle) {
      this.a(C_252363_.f_252436_.m_252977_(angle));
   }

   public void rotateDegYn(float angle) {
      this.a(C_252363_.f_252392_.m_252977_(angle));
   }

   public void rotateDegZp(float angle) {
      this.a(C_252363_.f_252403_.m_252977_(angle));
   }

   public void rotateDegZn(float angle) {
      this.a(C_252363_.f_252393_.m_252977_(angle));
   }

   public void rotateDeg(float angle, float x, float y, float z) {
      Vector3f vec = new Vector3f(x, y, z);
      Quaternionf quat = MathUtils.rotationDegrees(vec, angle);
      this.a(quat);
   }

   public int size() {
      return this.a.size();
   }

   public String toString() {
      return this.c().toString() + "Depth: " + this.a.size();
   }

   public void e() {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.identity();
      posestack$pose.b.identity();
      posestack$pose.c = true;
   }

   public void a(Matrix4f matrixIn) {
      PoseStack.a posestack$pose = (PoseStack.a)this.a.getLast();
      posestack$pose.a.mul(matrixIn);
      if (!C_252379_.m_321551_(matrixIn)) {
         if (C_252379_.m_319661_(matrixIn)) {
            posestack$pose.b.mul(new Matrix3f(matrixIn));
         } else {
            posestack$pose.d();
         }
      }
   }

   public static final class a {
      final Matrix4f a;
      final Matrix3f b;
      boolean c = true;

      a(Matrix4f matrixIn, Matrix3f normalIn) {
         this.a = matrixIn;
         this.b = normalIn;
      }

      a(PoseStack.a poseIn) {
         this.a = new Matrix4f(poseIn.a);
         this.b = new Matrix3f(poseIn.b);
         this.c = poseIn.c;
      }

      void d() {
         this.b.set(this.a).invert().transpose();
         this.c = false;
      }

      public Matrix4f a() {
         return this.a;
      }

      public Matrix3f b() {
         return this.b;
      }

      public Vector3f a(Vector3f vectorIn, Vector3f destIn) {
         return this.a(vectorIn.x, vectorIn.y, vectorIn.z, destIn);
      }

      public Vector3f a(float xIn, float yIn, float zIn, Vector3f destIn) {
         Vector3f vector3f = this.b.transform(xIn, yIn, zIn, destIn);
         return this.c ? vector3f : vector3f.normalize();
      }

      public PoseStack.a c() {
         return new PoseStack.a(this);
      }

      public String toString() {
         return this.a.toString() + this.b.toString();
      }
   }
}
