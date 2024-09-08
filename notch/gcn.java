package net.minecraft.src;

import java.util.List;
import java.util.Optional;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;

public abstract class C_4022_ {
   private static final C_3040_ f_107206_ = new C_3040_(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static final double f_197408_ = C_188_.m_144952_(100.0);
   protected final C_3899_ f_107208_;
   protected double f_107209_;
   protected double f_107210_;
   protected double f_107211_;
   protected double f_107212_;
   protected double f_107213_;
   protected double f_107214_;
   protected double f_107215_;
   protected double f_107216_;
   protected double f_107217_;
   private C_3040_ f_107207_ = f_107206_;
   protected boolean f_107218_;
   protected boolean f_107219_ = true;
   private boolean f_107205_;
   protected boolean f_107220_;
   protected float f_107221_ = 0.6F;
   protected float f_107222_ = 1.8F;
   protected final C_212974_ f_107223_ = C_212974_.m_216327_();
   protected int f_107224_;
   protected int f_107225_;
   protected float f_107226_;
   protected float f_107227_ = 1.0F;
   protected float f_107228_ = 1.0F;
   protected float f_107229_ = 1.0F;
   protected float f_107230_ = 1.0F;
   protected float f_107231_;
   protected float f_107204_;
   protected float f_172258_ = 0.98F;
   protected boolean f_172259_ = false;
   private BlockPosM blockPosM = new BlockPosM();

   protected C_4022_(C_3899_ worldIn, double posXIn, double posYIn, double posZIn) {
      this.f_107208_ = worldIn;
      this.m_107250_(0.2F, 0.2F);
      this.m_107264_(posXIn, posYIn, posZIn);
      this.f_107209_ = posXIn;
      this.f_107210_ = posYIn;
      this.f_107211_ = posZIn;
      this.f_107225_ = (int)(4.0F / (this.f_107223_.m_188501_() * 0.9F + 0.1F));
   }

   public C_4022_(C_3899_ worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.f_107215_ = xSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.f_107216_ = ySpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      this.f_107217_ = zSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4F;
      double d0 = (Math.random() + Math.random() + 1.0) * 0.15F;
      double d1 = Math.sqrt(this.f_107215_ * this.f_107215_ + this.f_107216_ * this.f_107216_ + this.f_107217_ * this.f_107217_);
      this.f_107215_ = this.f_107215_ / d1 * d0 * 0.4F;
      this.f_107216_ = this.f_107216_ / d1 * d0 * 0.4F + 0.1F;
      this.f_107217_ = this.f_107217_ / d1 * d0 * 0.4F;
   }

   public C_4022_ m_107268_(float multiplier) {
      this.f_107215_ *= (double)multiplier;
      this.f_107216_ = (this.f_107216_ - 0.1F) * (double)multiplier + 0.1F;
      this.f_107217_ *= (double)multiplier;
      return this;
   }

   public void m_172260_(double mxIn, double myIn, double mzIn) {
      this.f_107215_ = mxIn;
      this.f_107216_ = myIn;
      this.f_107217_ = mzIn;
   }

   public C_4022_ m_6569_(float scale) {
      this.m_107250_(0.2F * scale, 0.2F * scale);
      return this;
   }

   public void m_107253_(float particleRedIn, float particleGreenIn, float particleBlueIn) {
      this.f_107227_ = particleRedIn;
      this.f_107228_ = particleGreenIn;
      this.f_107229_ = particleBlueIn;
   }

   protected void m_107271_(float alpha) {
      this.f_107230_ = alpha;
   }

   public void m_107257_(int particleLifeTime) {
      this.f_107225_ = particleLifeTime;
   }

   public int m_107273_() {
      return this.f_107225_;
   }

   public void m_5989_() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      } else {
         this.f_107216_ = this.f_107216_ - 0.04 * (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         if (this.f_172259_ && this.f_107213_ == this.f_107210_) {
            this.f_107215_ *= 1.1;
            this.f_107217_ *= 1.1;
         }

         this.f_107215_ = this.f_107215_ * (double)this.f_172258_;
         this.f_107216_ = this.f_107216_ * (double)this.f_172258_;
         this.f_107217_ = this.f_107217_ * (double)this.f_172258_;
         if (this.f_107218_) {
            this.f_107215_ *= 0.7F;
            this.f_107217_ *= 0.7F;
         }
      }

      if (Config.isCustomColors() && this instanceof C_4012_) {
         CustomColors.updateLavaFX(this);
      }
   }

   public abstract void m_5744_(C_3187_ var1, C_3373_ var2, float var3);

   public abstract C_4029_ m_7556_();

   public String toString() {
      return this.getClass().getSimpleName()
         + ", Pos ("
         + this.f_107212_
         + ","
         + this.f_107213_
         + ","
         + this.f_107214_
         + "), RGBA ("
         + this.f_107227_
         + ","
         + this.f_107228_
         + ","
         + this.f_107229_
         + ","
         + this.f_107230_
         + "), Age "
         + this.f_107224_;
   }

   public void m_107274_() {
      this.f_107220_ = true;
   }

   protected void m_107250_(float particleWidth, float particleHeight) {
      if (particleWidth != this.f_107221_ || particleHeight != this.f_107222_) {
         this.f_107221_ = particleWidth;
         this.f_107222_ = particleHeight;
         C_3040_ aabb = this.m_107277_();
         double d0 = (aabb.f_82288_ + aabb.f_82291_ - (double)particleWidth) / 2.0;
         double d1 = (aabb.f_82290_ + aabb.f_82293_ - (double)particleWidth) / 2.0;
         this.m_107259_(new C_3040_(d0, aabb.f_82289_, d1, d0 + (double)this.f_107221_, aabb.f_82289_ + (double)this.f_107222_, d1 + (double)this.f_107221_));
      }
   }

   public void m_107264_(double x, double y, double z) {
      this.f_107212_ = x;
      this.f_107213_ = y;
      this.f_107214_ = z;
      float f = this.f_107221_ / 2.0F;
      float f1 = this.f_107222_;
      this.m_107259_(new C_3040_(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
   }

   public void m_6257_(double x, double y, double z) {
      if (!this.f_107205_) {
         double d0 = x;
         double d1 = y;
         double d2 = z;
         if (this.f_107219_ && (x != 0.0 || y != 0.0 || z != 0.0) && x * x + y * y + z * z < f_197408_ && this.hasNearBlocks(x, y, z)) {
            C_3046_ vec3 = C_507_.m_198894_(null, new C_3046_(x, y, z), this.m_107277_(), this.f_107208_, List.of());
            x = vec3.f_82479_;
            y = vec3.f_82480_;
            z = vec3.f_82481_;
         }

         if (x != 0.0 || y != 0.0 || z != 0.0) {
            this.m_107259_(this.m_107277_().m_82386_(x, y, z));
            this.m_107275_();
         }

         if (Math.abs(d1) >= 1.0E-5F && Math.abs(y) < 1.0E-5F) {
            this.f_107205_ = true;
         }

         this.f_107218_ = d1 != y && d1 < 0.0;
         if (d0 != x) {
            this.f_107215_ = 0.0;
         }

         if (d2 != z) {
            this.f_107217_ = 0.0;
         }
      }
   }

   protected void m_107275_() {
      C_3040_ aabb = this.m_107277_();
      this.f_107212_ = (aabb.f_82288_ + aabb.f_82291_) / 2.0;
      this.f_107213_ = aabb.f_82289_;
      this.f_107214_ = (aabb.f_82290_ + aabb.f_82293_) / 2.0;
   }

   protected int m_6355_(float partialTick) {
      C_4675_ blockpos = C_4675_.m_274561_(this.f_107212_, this.f_107213_, this.f_107214_);
      return this.f_107208_.B(blockpos) ? C_4134_.m_109541_(this.f_107208_, blockpos) : 0;
   }

   public boolean m_107276_() {
      return !this.f_107220_;
   }

   public C_3040_ m_107277_() {
      return this.f_107207_;
   }

   public void m_107259_(C_3040_ bb) {
      this.f_107207_ = bb;
   }

   public Optional<C_141791_> m_142654_() {
      return Optional.empty();
   }

   private boolean hasNearBlocks(double dx, double dy, double dz) {
      if (!(this.f_107221_ > 1.0F) && !(this.f_107222_ > 1.0F)) {
         int posXi = C_188_.m_14107_(this.f_107212_);
         int posYi = C_188_.m_14107_(this.f_107213_);
         int posZi = C_188_.m_14107_(this.f_107214_);
         this.blockPosM.setXyz(posXi, posYi, posZi);
         C_2064_ bs = this.f_107208_.m_8055_(this.blockPosM);
         if (!bs.m_60795_()) {
            return true;
         } else {
            double posX2 = dx > 0.0 ? this.f_107207_.f_82291_ : (dx < 0.0 ? this.f_107207_.f_82288_ : this.f_107212_);
            double posY2 = dy > 0.0 ? this.f_107207_.f_82292_ : (dy < 0.0 ? this.f_107207_.f_82289_ : this.f_107213_);
            double posZ2 = dz > 0.0 ? this.f_107207_.f_82293_ : (dz < 0.0 ? this.f_107207_.f_82290_ : this.f_107214_);
            int posXi2 = C_188_.m_14107_(posX2 + dx);
            int posYi2 = C_188_.m_14107_(posY2 + dy);
            int posZi2 = C_188_.m_14107_(posZ2 + dz);
            if (posXi2 != posXi || posYi2 != posYi || posZi2 != posZi) {
               this.blockPosM.setXyz(posXi2, posYi2, posZi2);
               C_2064_ bs2 = this.f_107208_.m_8055_(this.blockPosM);
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
      return this.f_107224_;
   }

   public boolean shouldCull() {
      return this instanceof C_4015_ ? false : !(this instanceof C_141708_);
   }

   public C_3046_ getPos() {
      return new C_3046_(this.f_107212_, this.f_107213_, this.f_107214_);
   }

   public static record C_313259_(float f_315806_, float f_316797_, float f_315332_, float f_315978_) {
      public static final C_4022_.C_313259_ f_314120_ = new C_4022_.C_313259_(1.0F, 1.0F, 0.0F, 1.0F);

      public boolean m_320160_() {
         return this.f_315806_ >= 1.0F && this.f_316797_ >= 1.0F;
      }

      public float m_320282_(int ageIn, int maxAgeIn, float partialTicks) {
         if (C_188_.m_14033_(this.f_315806_, this.f_316797_)) {
            return this.f_315806_;
         } else {
            float f = C_188_.m_184655_(((float)ageIn + partialTicks) / (float)maxAgeIn, this.f_315332_, this.f_315978_);
            return C_188_.m_144920_(this.f_315806_, this.f_316797_, f);
         }
      }
   }
}
