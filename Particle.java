import java.util.List;
import java.util.Optional;
import net.minecraft.src.C_141708_;
import net.minecraft.src.C_141791_;
import net.minecraft.src.C_212974_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_4012_;
import net.minecraft.src.C_4015_;
import net.minecraft.src.C_4029_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;

public abstract class Particle {
   private static final C_3040_ a = new C_3040_(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static final double b = Mth.k(100.0);
   protected final ClientLevel c;
   protected double d;
   protected double e;
   protected double f;
   protected double g;
   protected double h;
   protected double i;
   protected double j;
   protected double k;
   protected double l;
   private C_3040_ D = a;
   protected boolean m;
   protected boolean n = true;
   private boolean E;
   protected boolean o;
   protected float p = 0.6F;
   protected float q = 1.8F;
   protected final C_212974_ r = C_212974_.m_216327_();
   protected int s;
   protected int t;
   protected float u;
   protected float v = 1.0F;
   protected float w = 1.0F;
   protected float x = 1.0F;
   protected float y = 1.0F;
   protected float z;
   protected float A;
   protected float B = 0.98F;
   protected boolean C = false;
   private BlockPosM blockPosM = new BlockPosM();

   protected Particle(ClientLevel worldIn, double posXIn, double posYIn, double posZIn) {
      this.c = worldIn;
      this.b(0.2F, 0.2F);
      this.c(posXIn, posYIn, posZIn);
      this.d = posXIn;
      this.e = posYIn;
      this.f = posZIn;
      this.t = (int)(4.0F / (this.r.m_188501_() * 0.9F + 0.1F));
   }

   public Particle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.j = xSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.k = ySpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.l = zSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      double d0 = (Math.random() + Math.random() + 1.0) * 0.15F;
      double d1 = Math.sqrt(this.j * this.j + this.k * this.k + this.l * this.l);
      this.j = this.j / d1 * d0 * 0.4F;
      this.k = this.k / d1 * d0 * 0.4F + 0.1F;
      this.l = this.l / d1 * d0 * 0.4F;
   }

   public Particle c(float multiplier) {
      this.j *= (double)multiplier;
      this.k = (this.k - 0.1F) * (double)multiplier + 0.1F;
      this.l *= (double)multiplier;
      return this;
   }

   public void b(double mxIn, double myIn, double mzIn) {
      this.j = mxIn;
      this.k = myIn;
      this.l = mzIn;
   }

   public Particle d(float scale) {
      this.b(0.2F * scale, 0.2F * scale);
      return this;
   }

   public void a(float particleRedIn, float particleGreenIn, float particleBlueIn) {
      this.v = particleRedIn;
      this.w = particleGreenIn;
      this.x = particleBlueIn;
   }

   protected void e(float alpha) {
      this.y = alpha;
   }

   public void a(int particleLifeTime) {
      this.t = particleLifeTime;
   }

   public int j() {
      return this.t;
   }

   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      if (this.s++ >= this.t) {
         this.k();
      } else {
         this.k = this.k - 0.04 * (double)this.u;
         this.a(this.j, this.k, this.l);
         if (this.C && this.h == this.e) {
            this.j *= 1.1;
            this.l *= 1.1;
         }

         this.j = this.j * (double)this.B;
         this.k = this.k * (double)this.B;
         this.l = this.l * (double)this.B;
         if (this.m) {
            this.j *= 0.7F;
            this.l *= 0.7F;
         }
      }

      if (Config.isCustomColors() && this instanceof C_4012_) {
         CustomColors.updateLavaFX(this);
      }
   }

   public abstract void a(VertexConsumer var1, Camera var2, float var3);

   public abstract C_4029_ b();

   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.g
         + ","
         + this.h
         + ","
         + this.i
         + "), RGBA ("
         + this.v
         + ","
         + this.w
         + ","
         + this.x
         + ","
         + this.y
         + "), Age "
         + this.s;
   }

   public void k() {
      this.o = true;
   }

   protected void b(float particleWidth, float particleHeight) {
      if (particleWidth != this.p || particleHeight != this.q) {
         this.p = particleWidth;
         this.q = particleHeight;
         C_3040_ aabb = this.n();
         double d0 = (aabb.f_82288_ + aabb.f_82291_ - (double)particleWidth) / 2.0;
         double d1 = (aabb.f_82290_ + aabb.f_82293_ - (double)particleWidth) / 2.0;
         this.a(new C_3040_(d0, aabb.f_82289_, d1, d0 + (double)this.p, aabb.f_82289_ + (double)this.q, d1 + (double)this.p));
      }
   }

   public void c(double x, double y, double z) {
      this.g = x;
      this.h = y;
      this.i = z;
      float f = this.p / 2.0F;
      float f1 = this.q;
      this.a(new C_3040_(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
   }

   public void a(double x, double y, double z) {
      if (!this.E) {
         double d0 = x;
         double d1 = y;
         double d2 = z;
         if (this.n && (x != 0.0 || y != 0.0 || z != 0.0) && x * x + y * y + z * z < b && this.hasNearBlocks(x, y, z)) {
            Vec3 vec3 = C_507_.a(null, new Vec3(x, y, z), this.n(), this.c, List.of());
            x = vec3.c;
            y = vec3.d;
            z = vec3.e;
         }

         if (x != 0.0 || y != 0.0 || z != 0.0) {
            this.a(this.n().m_82386_(x, y, z));
            this.l();
         }

         if (Math.abs(d1) >= 1.0E-5F && Math.abs(y) < 1.0E-5F) {
            this.E = true;
         }

         this.m = d1 != y && d1 < 0.0;
         if (d0 != x) {
            this.j = 0.0;
         }

         if (d2 != z) {
            this.l = 0.0;
         }
      }
   }

   protected void l() {
      C_3040_ aabb = this.n();
      this.g = (aabb.f_82288_ + aabb.f_82291_) / 2.0;
      this.h = aabb.f_82289_;
      this.i = (aabb.f_82290_ + aabb.f_82293_) / 2.0;
   }

   protected int a(float partialTick) {
      C_4675_ blockpos = C_4675_.m_274561_(this.g, this.h, this.i);
      return this.c.m_46805_(blockpos) ? LevelRenderer.a(this.c, blockpos) : 0;
   }

   public boolean m() {
      return !this.o;
   }

   public C_3040_ n() {
      return this.D;
   }

   public void a(C_3040_ bb) {
      this.D = bb;
   }

   public Optional<C_141791_> o() {
      return Optional.empty();
   }

   private boolean hasNearBlocks(double dx, double dy, double dz) {
      if (!(this.p > 1.0F) && !(this.q > 1.0F)) {
         int posXi = Mth.a(this.g);
         int posYi = Mth.a(this.h);
         int posZi = Mth.a(this.i);
         this.blockPosM.setXyz(posXi, posYi, posZi);
         BlockState bs = this.c.a_(this.blockPosM);
         if (!bs.m_60795_()) {
            return true;
         } else {
            double posX2 = dx > 0.0 ? this.D.f_82291_ : (dx < 0.0 ? this.D.f_82288_ : this.g);
            double posY2 = dy > 0.0 ? this.D.f_82292_ : (dy < 0.0 ? this.D.f_82289_ : this.h);
            double posZ2 = dz > 0.0 ? this.D.f_82293_ : (dz < 0.0 ? this.D.f_82290_ : this.i);
            int posXi2 = Mth.a(posX2 + dx);
            int posYi2 = Mth.a(posY2 + dy);
            int posZi2 = Mth.a(posZ2 + dz);
            if (posXi2 != posXi || posYi2 != posYi || posZi2 != posZi) {
               this.blockPosM.setXyz(posXi2, posYi2, posZi2);
               BlockState bs2 = this.c.a_(this.blockPosM);
               if (!bs2.m_60795_()) {
                  return true;
               }
            }

            return false;
         }
      } else {
         return true;
      }
   }

   public int getAge() {
      return this.s;
   }

   public boolean shouldCull() {
      return this instanceof C_4015_ ? false : !(this instanceof C_141708_);
   }

   public Vec3 getPos() {
      return new Vec3(this.g, this.h, this.i);
   }

   public static record a(float b, float c, float d, float e) {
      public static final Particle.a a = new Particle.a(1.0F, 1.0F, 0.0F, 1.0F);

      public boolean a() {
         return this.b >= 1.0F && this.c >= 1.0F;
      }

      public float a(int ageIn, int maxAgeIn, float partialTicks) {
         if (Mth.a(this.b, this.c)) {
            return this.b;
         } else {
            float f = Mth.g(((float)ageIn + partialTicks) / (float)maxAgeIn, this.d, this.e);
            return Mth.b(this.b, this.c, f);
         }
      }
   }
}
