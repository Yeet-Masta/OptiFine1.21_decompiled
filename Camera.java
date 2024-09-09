import java.util.Arrays;
import net.minecraft.src.C_139_;
import net.minecraft.src.C_141436_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1565_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_3043_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_1565_.C_1566_;
import net.minecraft.src.C_1565_.C_1567_;
import net.minecraft.src.C_3043_.C_3044_;
import net.minecraft.src.C_4675_.C_4681_;
import net.optifine.reflect.Reflector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
   private static final float b = 4.0F;
   private static final Vector3f c = new Vector3f(0.0F, 0.0F, -1.0F);
   private static final Vector3f d = new Vector3f(0.0F, 1.0F, 0.0F);
   private static final Vector3f e = new Vector3f(-1.0F, 0.0F, 0.0F);
   private boolean f;
   private C_1559_ g;
   private C_507_ h;
   private Vec3 i = Vec3.b;
   private final C_4681_ j = new C_4681_();
   private final Vector3f k = new Vector3f(c);
   private final Vector3f l = new Vector3f(d);
   private final Vector3f m = new Vector3f(e);
   private float n;
   private float o;
   private final Quaternionf p = new Quaternionf();
   private boolean q;
   private float r;
   private float s;
   private float t;
   public static final float a = 0.083333336F;

   public void a(C_1559_ worldIn, C_507_ renderViewEntity, boolean thirdPersonIn, boolean thirdPersonReverseIn, float partialTicks) {
      this.f = true;
      this.g = worldIn;
      this.h = renderViewEntity;
      this.q = thirdPersonIn;
      this.t = partialTicks;
      this.a(renderViewEntity.m_5675_(partialTicks), renderViewEntity.m_5686_(partialTicks));
      this.a(
         Mth.d((double)partialTicks, renderViewEntity.f_19854_, renderViewEntity.m_20185_()),
         Mth.d((double)partialTicks, renderViewEntity.f_19855_, renderViewEntity.m_20186_()) + (double)Mth.i(partialTicks, this.s, this.r),
         Mth.d((double)partialTicks, renderViewEntity.f_19856_, renderViewEntity.m_20189_())
      );
      if (thirdPersonIn) {
         if (thirdPersonReverseIn) {
            this.a(this.o + 180.0F, -this.n);
         }

         float f = renderViewEntity instanceof C_524_ livingentity ? livingentity.m_6134_() : 1.0F;
         this.a(-this.a(4.0F * f), 0.0F, 0.0F);
      } else if (renderViewEntity instanceof C_524_ && ((C_524_)renderViewEntity).m_5803_()) {
         Direction direction = ((C_524_)renderViewEntity).fJ();
         this.a(direction != null ? direction.p() - 180.0F : 0.0F, 0.0F);
         this.a(0.0F, 0.3F, 0.0F);
      }
   }

   public void a() {
      if (this.h != null) {
         this.s = this.r;
         this.r = this.r + (this.h.m_20192_() - this.r) * 0.5F;
      }
   }

   private float a(float startingDistance) {
      float f = 0.1F;

      for (int i = 0; i < 8; i++) {
         float f1 = (float)((i & 1) * 2 - 1);
         float f2 = (float)((i >> 1 & 1) * 2 - 1);
         float f3 = (float)((i >> 2 & 1) * 2 - 1);
         Vec3 vec3 = this.i.b((double)(f1 * 0.1F), (double)(f2 * 0.1F), (double)(f3 * 0.1F));
         Vec3 vec31 = vec3.e(new Vec3(this.k).a((double)(-startingDistance)));
         C_3043_ hitresult = this.g.m_45547_(new C_1565_(vec3, vec31, C_1566_.VISUAL, C_1567_.NONE, this.h));
         if (hitresult.m_6662_() != C_3044_.MISS) {
            float f4 = (float)hitresult.e().g(this.i);
            if (f4 < Mth.k(startingDistance)) {
               startingDistance = Mth.c(f4);
            }
         }
      }

      return startingDistance;
   }

   protected void a(float distanceOffset, float verticalOffset, float horizontalOffset) {
      Vector3f vector3f = new Vector3f(horizontalOffset, verticalOffset, -distanceOffset).rotate(this.p);
      this.a(new Vec3(this.i.c + (double)vector3f.x, this.i.d + (double)vector3f.y, this.i.e + (double)vector3f.z));
   }

   protected void a(float pitchIn, float yawIn) {
      this.n = yawIn;
      this.o = pitchIn;
      this.p.rotationYXZ((float) Math.PI - pitchIn * (float) (Math.PI / 180.0), -yawIn * (float) (Math.PI / 180.0), 0.0F);
      c.rotate(this.p, this.k);
      d.rotate(this.p, this.l);
      e.rotate(this.p, this.m);
   }

   protected void a(double x, double y, double z) {
      this.a(new Vec3(x, y, z));
   }

   protected void a(Vec3 posIn) {
      this.i = posIn;
      this.j.m_122169_(posIn.c, posIn.d, posIn.e);
   }

   public Vec3 b() {
      return this.i;
   }

   public C_4675_ c() {
      return this.j;
   }

   public float d() {
      return this.n;
   }

   public float e() {
      return this.o;
   }

   public Quaternionf f() {
      return this.p;
   }

   public C_507_ g() {
      return this.h;
   }

   public boolean h() {
      return this.f;
   }

   public boolean i() {
      return this.q;
   }

   public Camera.a j() {
      C_3391_ minecraft = C_3391_.m_91087_();
      double d0 = (double)minecraft.aM().l() / (double)minecraft.aM().m();
      double d1 = Math.tan((double)((float)minecraft.m.ah().c().intValue() * (float) (Math.PI / 180.0)) / 2.0) * 0.05F;
      double d2 = d1 * d0;
      Vec3 vec3 = new Vec3(this.k).a(0.05F);
      Vec3 vec31 = new Vec3(this.m).a(d2);
      Vec3 vec32 = new Vec3(this.l).a(d1);
      return new Camera.a(vec3, vec31, vec32);
   }

   public C_141436_ k() {
      if (!this.f) {
         return C_141436_.NONE;
      } else {
         C_2691_ fluidstate = this.g.m_6425_(this.j);
         if (fluidstate.m_205070_(C_139_.f_13131_) && this.i.d < (double)((float)this.j.m_123342_() + fluidstate.m_76155_(this.g, this.j))) {
            return C_141436_.WATER;
         } else {
            Camera.a camera$nearplane = this.j();

            for (Vec3 vec3 : Arrays.asList(camera$nearplane.a, camera$nearplane.a(), camera$nearplane.b(), camera$nearplane.c(), camera$nearplane.d())) {
               Vec3 vec31 = this.i.e(vec3);
               C_4675_ blockpos = C_4675_.m_274446_(vec31);
               C_2691_ fluidstate1 = this.g.m_6425_(blockpos);
               if (fluidstate1.m_205070_(C_139_.f_13132_)) {
                  if (vec31.d <= (double)(fluidstate1.m_76155_(this.g, blockpos) + (float)blockpos.m_123342_())) {
                     return C_141436_.LAVA;
                  }
               } else {
                  BlockState blockstate = this.g.a_(blockpos);
                  if (blockstate.m_60713_(C_1710_.f_152499_)) {
                     return C_141436_.POWDER_SNOW;
                  }
               }
            }

            return C_141436_.NONE;
         }
      }
   }

   public BlockState getBlockState() {
      return !this.f ? C_1710_.f_50016_.o() : this.g.a_(this.j);
   }

   public void setAnglesInternal(float yaw, float pitch) {
      this.o = yaw;
      this.n = pitch;
   }

   public BlockState getBlockAtCamera() {
      if (!this.f) {
         return C_1710_.f_50016_.o();
      } else {
         BlockState state = this.g.a_(this.j);
         if (Reflector.IForgeBlockState_getStateAtViewpoint.exists()) {
            state = (BlockState)Reflector.call(state, Reflector.IForgeBlockState_getStateAtViewpoint, this.g, this.j, this.i);
         }

         return state;
      }
   }

   public final Vector3f l() {
      return this.k;
   }

   public final Vector3f m() {
      return this.l;
   }

   public final Vector3f n() {
      return this.m;
   }

   public void o() {
      this.g = null;
      this.h = null;
      this.f = false;
   }

   public float p() {
      return this.t;
   }

   public static class a {
      final Vec3 a;
      private final Vec3 b;
      private final Vec3 c;

      a(Vec3 forwardIn, Vec3 leftIn, Vec3 upIn) {
         this.a = forwardIn;
         this.b = leftIn;
         this.c = upIn;
      }

      public Vec3 a() {
         return this.a.e(this.c).e(this.b);
      }

      public Vec3 b() {
         return this.a.e(this.c).d(this.b);
      }

      public Vec3 c() {
         return this.a.d(this.c).e(this.b);
      }

      public Vec3 d() {
         return this.a.d(this.c).d(this.b);
      }

      public Vec3 a(float xIn, float yIn) {
         return this.a.e(this.c.a((double)yIn)).d(this.b.a((double)xIn));
      }
   }
}
