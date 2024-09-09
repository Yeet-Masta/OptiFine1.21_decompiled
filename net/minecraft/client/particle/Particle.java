package net.minecraft.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.optifine.BlockPosM;
import net.optifine.Config;
import net.optifine.CustomColors;

public abstract class Particle {
   private static final AABB f_107206_ = new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
   private static final double f_197408_ = Mth.m_144952_(100.0);
   protected final ClientLevel f_107208_;
   protected double f_107209_;
   protected double f_107210_;
   protected double f_107211_;
   protected double f_107212_;
   protected double f_107213_;
   protected double f_107214_;
   protected double f_107215_;
   protected double f_107216_;
   protected double f_107217_;
   private AABB f_107207_;
   protected boolean f_107218_;
   protected boolean f_107219_;
   private boolean f_107205_;
   protected boolean f_107220_;
   protected float f_107221_;
   protected float f_107222_;
   protected final RandomSource f_107223_;
   protected int f_107224_;
   protected int f_107225_;
   protected float f_107226_;
   protected float f_107227_;
   protected float f_107228_;
   protected float f_107229_;
   protected float f_107230_;
   protected float f_107231_;
   protected float f_107204_;
   protected float f_172258_;
   protected boolean f_172259_;
   private BlockPosM blockPosM;

   protected Particle(ClientLevel worldIn, double posXIn, double posYIn, double posZIn) {
      this.f_107207_ = f_107206_;
      this.f_107219_ = true;
      this.f_107221_ = 0.6F;
      this.f_107222_ = 1.8F;
      this.f_107223_ = RandomSource.m_216327_();
      this.f_107227_ = 1.0F;
      this.f_107228_ = 1.0F;
      this.f_107229_ = 1.0F;
      this.f_107230_ = 1.0F;
      this.f_172258_ = 0.98F;
      this.f_172259_ = false;
      this.blockPosM = new BlockPosM();
      this.f_107208_ = worldIn;
      this.m_107250_(0.2F, 0.2F);
      this.m_107264_(posXIn, posYIn, posZIn);
      this.f_107209_ = posXIn;
      this.f_107210_ = posYIn;
      this.f_107211_ = posZIn;
      this.f_107225_ = (int)(4.0F / (this.f_107223_.m_188501_() * 0.9F + 0.1F));
   }

   public Particle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.f_107215_ = xSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
      this.f_107216_ = ySpeedIn + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
      this.f_107217_ = zSpeedIn + (Math.random() * 2.0 - 1.0) * 0.4000000059604645;
      double d0 = (Math.random() + Math.random() + 1.0) * 0.15000000596046448;
      double d1 = Math.sqrt(this.f_107215_ * this.f_107215_ + this.f_107216_ * this.f_107216_ + this.f_107217_ * this.f_107217_);
      this.f_107215_ = this.f_107215_ / d1 * d0 * 0.4000000059604645;
      this.f_107216_ = this.f_107216_ / d1 * d0 * 0.4000000059604645 + 0.10000000149011612;
      this.f_107217_ = this.f_107217_ / d1 * d0 * 0.4000000059604645;
   }

   public Particle m_107268_(float multiplier) {
      this.f_107215_ *= (double)multiplier;
      this.f_107216_ = (this.f_107216_ - 0.10000000149011612) * (double)multiplier + 0.10000000149011612;
      this.f_107217_ *= (double)multiplier;
      return this;
   }

   public void m_172260_(double mxIn, double myIn, double mzIn) {
      this.f_107215_ = mxIn;
      this.f_107216_ = myIn;
      this.f_107217_ = mzIn;
   }

   public Particle m_6569_(float scale) {
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
         this.f_107216_ -= 0.04 * (double)this.f_107226_;
         this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
         if (this.f_172259_ && this.f_107213_ == this.f_107210_) {
            this.f_107215_ *= 1.1;
            this.f_107217_ *= 1.1;
         }

         this.f_107215_ *= (double)this.f_172258_;
         this.f_107216_ *= (double)this.f_172258_;
         this.f_107217_ *= (double)this.f_172258_;
         if (this.f_107218_) {
            this.f_107215_ *= 0.699999988079071;
            this.f_107217_ *= 0.699999988079071;
         }
      }

      if (Config.isCustomColors() && this instanceof LavaParticle) {
         CustomColors.updateLavaFX(this);
      }

   }

   public abstract void m_5744_(VertexConsumer var1, Camera var2, float var3);

   public abstract ParticleRenderType m_7556_();

   public String toString() {
      String var10000 = this.getClass().getSimpleName();
      return var10000 + ", Pos (" + this.f_107212_ + "," + this.f_107213_ + "," + this.f_107214_ + "), RGBA (" + this.f_107227_ + "," + this.f_107228_ + "," + this.f_107229_ + "," + this.f_107230_ + "), Age " + this.f_107224_;
   }

   public void m_107274_() {
      this.f_107220_ = true;
   }

   protected void m_107250_(float particleWidth, float particleHeight) {
      if (particleWidth != this.f_107221_ || particleHeight != this.f_107222_) {
         this.f_107221_ = particleWidth;
         this.f_107222_ = particleHeight;
         AABB aabb = this.m_107277_();
         double d0 = (aabb.f_82288_ + aabb.f_82291_ - (double)particleWidth) / 2.0;
         double d1 = (aabb.f_82290_ + aabb.f_82293_ - (double)particleWidth) / 2.0;
         this.m_107259_(new AABB(d0, aabb.f_82289_, d1, d0 + (double)this.f_107221_, aabb.f_82289_ + (double)this.f_107222_, d1 + (double)this.f_107221_));
      }

   }

   public void m_107264_(double x, double y, double z) {
      this.f_107212_ = x;
      this.f_107213_ = y;
      this.f_107214_ = z;
      float f = this.f_107221_ / 2.0F;
      float f1 = this.f_107222_;
      this.m_107259_(new AABB(x - (double)f, y, z - (double)f, x + (double)f, y + (double)f1, z + (double)f));
   }

   public void m_6257_(double x, double y, double z) {
      if (!this.f_107205_) {
         double d0 = x;
         double d1 = y;
         if (this.f_107219_ && (x != 0.0 || y != 0.0 || z != 0.0) && x * x + y * y + z * z < f_197408_ && this.hasNearBlocks(x, y, z)) {
            Vec3 vec3 = Entity.m_198894_((Entity)null, new Vec3(x, y, z), this.m_107277_(), this.f_107208_, List.of());
            x = vec3.f_82479_;
            y = vec3.f_82480_;
            z = vec3.f_82481_;
         }

         if (x != 0.0 || y != 0.0 || z != 0.0) {
            this.m_107259_(this.m_107277_().m_82386_(x, y, z));
            this.m_107275_();
         }

         if (Math.abs(y) >= 9.999999747378752E-6 && Math.abs(y) < 9.999999747378752E-6) {
            this.f_107205_ = true;
         }

         this.f_107218_ = y != y && d1 < 0.0;
         if (d0 != x) {
            this.f_107215_ = 0.0;
         }

         if (z != z) {
            this.f_107217_ = 0.0;
         }
      }

   }

   protected void m_107275_() {
      AABB aabb = this.m_107277_();
      this.f_107212_ = (aabb.f_82288_ + aabb.f_82291_) / 2.0;
      this.f_107213_ = aabb.f_82289_;
      this.f_107214_ = (aabb.f_82290_ + aabb.f_82293_) / 2.0;
   }

   protected int m_6355_(float partialTick) {
      BlockPos blockpos = BlockPos.m_274561_(this.f_107212_, this.f_107213_, this.f_107214_);
      return this.f_107208_.m_46805_(blockpos) ? LevelRenderer.m_109541_(this.f_107208_, blockpos) : 0;
   }

   public boolean m_107276_() {
      return !this.f_107220_;
   }

   public AABB m_107277_() {
      return this.f_107207_;
   }

   public void m_107259_(AABB bb) {
      this.f_107207_ = bb;
   }

   public Optional m_142654_() {
      return Optional.empty();
   }

   private boolean hasNearBlocks(double dx, double dy, double dz) {
      if (!(this.f_107221_ > 1.0F) && !(this.f_107222_ > 1.0F)) {
         int posXi = Mth.m_14107_(this.f_107212_);
         int posYi = Mth.m_14107_(this.f_107213_);
         int posZi = Mth.m_14107_(this.f_107214_);
         this.blockPosM.setXyz(posXi, posYi, posZi);
         BlockState bs = this.f_107208_.m_8055_(this.blockPosM);
         if (!bs.m_60795_()) {
            return true;
         } else {
            double posX2 = dx > 0.0 ? this.f_107207_.f_82291_ : (dx < 0.0 ? this.f_107207_.f_82288_ : this.f_107212_);
            double posY2 = dy > 0.0 ? this.f_107207_.f_82292_ : (dy < 0.0 ? this.f_107207_.f_82289_ : this.f_107213_);
            double posZ2 = dz > 0.0 ? this.f_107207_.f_82293_ : (dz < 0.0 ? this.f_107207_.f_82290_ : this.f_107214_);
            int posXi2 = Mth.m_14107_(posX2 + dx);
            int posYi2 = Mth.m_14107_(posY2 + dy);
            int posZi2 = Mth.m_14107_(posZ2 + dz);
            if (posXi2 != posXi || posYi2 != posYi || posZi2 != posZi) {
               this.blockPosM.setXyz(posXi2, posYi2, posZi2);
               BlockState bs2 = this.f_107208_.m_8055_(this.blockPosM);
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
      if (this instanceof MobAppearanceParticle) {
         return false;
      } else {
         return !(this instanceof VibrationSignalParticle);
      }
   }

   public Vec3 getPos() {
      return new Vec3(this.f_107212_, this.f_107213_, this.f_107214_);
   }

   public static record LifetimeAlpha(float f_315806_, float f_316797_, float f_315332_, float f_315978_) {
      public static final LifetimeAlpha f_314120_ = new LifetimeAlpha(1.0F, 1.0F, 0.0F, 1.0F);

      public LifetimeAlpha(float startAlpha, float endAlpha, float startAtNormalizedAge, float endAtNormalizedAge) {
         this.f_315806_ = startAlpha;
         this.f_316797_ = endAlpha;
         this.f_315332_ = startAtNormalizedAge;
         this.f_315978_ = endAtNormalizedAge;
      }

      public boolean m_320160_() {
         return this.f_315806_ >= 1.0F && this.f_316797_ >= 1.0F;
      }

      public float m_320282_(int ageIn, int maxAgeIn, float partialTicks) {
         if (Mth.m_14033_(this.f_315806_, this.f_316797_)) {
            return this.f_315806_;
         } else {
            float f = Mth.m_184655_(((float)ageIn + partialTicks) / (float)maxAgeIn, this.f_315332_, this.f_315978_);
            return Mth.m_144920_(this.f_315806_, this.f_316797_, f);
         }
      }

      public float f_315806_() {
         return this.f_315806_;
      }

      public float f_316797_() {
         return this.f_316797_;
      }

      public float f_315332_() {
         return this.f_315332_;
      }

      public float f_315978_() {
         return this.f_315978_;
      }
   }
}
