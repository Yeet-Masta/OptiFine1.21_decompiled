package net.minecraft.client;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.optifine.reflect.Reflector;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Camera {
   private static final float f_315635_ = 4.0F;
   private static final Vector3f f_337503_ = new Vector3f(0.0F, 0.0F, -1.0F);
   private static final Vector3f f_336762_ = new Vector3f(0.0F, 1.0F, 0.0F);
   private static final Vector3f f_336682_ = new Vector3f(-1.0F, 0.0F, 0.0F);
   private boolean f_90549_;
   private BlockGetter f_90550_;
   private Entity f_90551_;
   private Vec3 f_90552_ = Vec3.f_82478_;
   private final MutableBlockPos f_90553_ = new MutableBlockPos();
   private final Vector3f f_90554_ = new Vector3f(f_337503_);
   private final Vector3f f_90555_ = new Vector3f(f_336762_);
   private final Vector3f f_90556_ = new Vector3f(f_336682_);
   private float f_90557_;
   private float f_90558_;
   private final Quaternionf f_90559_ = new Quaternionf();
   private boolean f_90560_;
   private float f_90562_;
   private float f_90563_;
   private float f_303114_;
   public static final float f_167683_ = 0.083333336F;

   public void m_90575_(BlockGetter worldIn, Entity renderViewEntity, boolean thirdPersonIn, boolean thirdPersonReverseIn, float partialTicks) {
      this.f_90549_ = true;
      this.f_90550_ = worldIn;
      this.f_90551_ = renderViewEntity;
      this.f_90560_ = thirdPersonIn;
      this.f_303114_ = partialTicks;
      this.m_90572_(renderViewEntity.m_5675_(partialTicks), renderViewEntity.m_5686_(partialTicks));
      this.m_90584_(
         Mth.m_14139_((double)partialTicks, renderViewEntity.f_19854_, renderViewEntity.m_20185_()),
         Mth.m_14139_((double)partialTicks, renderViewEntity.f_19855_, renderViewEntity.m_20186_())
            + (double)Mth.m_14179_(partialTicks, this.f_90563_, this.f_90562_),
         Mth.m_14139_((double)partialTicks, renderViewEntity.f_19856_, renderViewEntity.m_20189_())
      );
      if (thirdPersonIn) {
         if (thirdPersonReverseIn) {
            this.m_90572_(this.f_90558_ + 180.0F, -this.f_90557_);
         }

         float f = renderViewEntity instanceof LivingEntity livingentity ? livingentity.m_6134_() : 1.0F;
         this.m_90568_(-this.m_90566_(4.0F * f), 0.0F, 0.0F);
      } else if (renderViewEntity instanceof LivingEntity && ((LivingEntity)renderViewEntity).m_5803_()) {
         Direction direction = ((LivingEntity)renderViewEntity).m_21259_();
         this.m_90572_(direction != null ? direction.m_122435_() - 180.0F : 0.0F, 0.0F);
         this.m_90568_(0.0F, 0.3F, 0.0F);
      }
   }

   public void m_90565_() {
      if (this.f_90551_ != null) {
         this.f_90563_ = this.f_90562_;
         this.f_90562_ = this.f_90562_ + (this.f_90551_.m_20192_() - this.f_90562_) * 0.5F;
      }
   }

   private float m_90566_(float startingDistance) {
      float f = 0.1F;

      for (int i = 0; i < 8; i++) {
         float f1 = (float)((i & 1) * 2 - 1);
         float f2 = (float)((i >> 1 & 1) * 2 - 1);
         float f3 = (float)((i >> 2 & 1) * 2 - 1);
         Vec3 vec3 = this.f_90552_.m_82520_((double)(f1 * 0.1F), (double)(f2 * 0.1F), (double)(f3 * 0.1F));
         Vec3 vec31 = vec3.m_82549_(new Vec3(this.f_90554_).m_82490_((double)(-startingDistance)));
         HitResult hitresult = this.f_90550_.m_45547_(new ClipContext(vec3, vec31, Block.VISUAL, Fluid.NONE, this.f_90551_));
         if (hitresult.m_6662_() != Type.MISS) {
            float f4 = (float)hitresult.m_82450_().m_82557_(this.f_90552_);
            if (f4 < Mth.m_14207_(startingDistance)) {
               startingDistance = Mth.m_14116_(f4);
            }
         }
      }

      return startingDistance;
   }

   protected void m_90568_(float distanceOffset, float verticalOffset, float horizontalOffset) {
      Vector3f vector3f = new Vector3f(horizontalOffset, verticalOffset, -distanceOffset).rotate(this.f_90559_);
      this.m_90581_(
         new Vec3(this.f_90552_.f_82479_ + (double)vector3f.x, this.f_90552_.f_82480_ + (double)vector3f.y, this.f_90552_.f_82481_ + (double)vector3f.z)
      );
   }

   protected void m_90572_(float pitchIn, float yawIn) {
      this.f_90557_ = yawIn;
      this.f_90558_ = pitchIn;
      this.f_90559_.rotationYXZ((float) Math.PI - pitchIn * (float) (Math.PI / 180.0), -yawIn * (float) (Math.PI / 180.0), 0.0F);
      f_337503_.rotate(this.f_90559_, this.f_90554_);
      f_336762_.rotate(this.f_90559_, this.f_90555_);
      f_336682_.rotate(this.f_90559_, this.f_90556_);
   }

   protected void m_90584_(double x, double y, double z) {
      this.m_90581_(new Vec3(x, y, z));
   }

   protected void m_90581_(Vec3 posIn) {
      this.f_90552_ = posIn;
      this.f_90553_.m_122169_(posIn.f_82479_, posIn.f_82480_, posIn.f_82481_);
   }

   public Vec3 m_90583_() {
      return this.f_90552_;
   }

   public BlockPos m_90588_() {
      return this.f_90553_;
   }

   public float m_90589_() {
      return this.f_90557_;
   }

   public float m_90590_() {
      return this.f_90558_;
   }

   public Quaternionf m_253121_() {
      return this.f_90559_;
   }

   public Entity m_90592_() {
      return this.f_90551_;
   }

   public boolean m_90593_() {
      return this.f_90549_;
   }

   public boolean m_90594_() {
      return this.f_90560_;
   }

   public Camera.NearPlane m_167684_() {
      Minecraft minecraft = Minecraft.m_91087_();
      double d0 = (double)minecraft.m_91268_().m_85441_() / (double)minecraft.m_91268_().m_85442_();
      double d1 = Math.tan((double)((float)minecraft.f_91066_.m_231837_().m_231551_().intValue() * (float) (Math.PI / 180.0)) / 2.0) * 0.05F;
      double d2 = d1 * d0;
      Vec3 vec3 = new Vec3(this.f_90554_).m_82490_(0.05F);
      Vec3 vec31 = new Vec3(this.f_90556_).m_82490_(d2);
      Vec3 vec32 = new Vec3(this.f_90555_).m_82490_(d1);
      return new Camera.NearPlane(vec3, vec31, vec32);
   }

   public FogType m_167685_() {
      if (!this.f_90549_) {
         return FogType.NONE;
      } else {
         FluidState fluidstate = this.f_90550_.m_6425_(this.f_90553_);
         if (fluidstate.m_205070_(FluidTags.f_13131_)
            && this.f_90552_.f_82480_ < (double)((float)this.f_90553_.m_123342_() + fluidstate.m_76155_(this.f_90550_, this.f_90553_))) {
            return FogType.WATER;
         } else {
            Camera.NearPlane camera$nearplane = this.m_167684_();

            for (Vec3 vec3 : Arrays.asList(
               camera$nearplane.f_167687_,
               camera$nearplane.m_167694_(),
               camera$nearplane.m_167698_(),
               camera$nearplane.m_167699_(),
               camera$nearplane.m_167700_()
            )) {
               Vec3 vec31 = this.f_90552_.m_82549_(vec3);
               BlockPos blockpos = BlockPos.m_274446_(vec31);
               FluidState fluidstate1 = this.f_90550_.m_6425_(blockpos);
               if (fluidstate1.m_205070_(FluidTags.f_13132_)) {
                  if (vec31.f_82480_ <= (double)(fluidstate1.m_76155_(this.f_90550_, blockpos) + (float)blockpos.m_123342_())) {
                     return FogType.LAVA;
                  }
               } else {
                  BlockState blockstate = this.f_90550_.m_8055_(blockpos);
                  if (blockstate.m_60713_(Blocks.f_152499_)) {
                     return FogType.POWDER_SNOW;
                  }
               }
            }

            return FogType.NONE;
         }
      }
   }

   public BlockState getBlockState() {
      return !this.f_90549_ ? Blocks.f_50016_.m_49966_() : this.f_90550_.m_8055_(this.f_90553_);
   }

   public void setAnglesInternal(float yaw, float pitch) {
      this.f_90558_ = yaw;
      this.f_90557_ = pitch;
   }

   public BlockState getBlockAtCamera() {
      if (!this.f_90549_) {
         return Blocks.f_50016_.m_49966_();
      } else {
         BlockState state = this.f_90550_.m_8055_(this.f_90553_);
         if (Reflector.IForgeBlockState_getStateAtViewpoint.exists()) {
            state = (BlockState)Reflector.call(state, Reflector.IForgeBlockState_getStateAtViewpoint, this.f_90550_, this.f_90553_, this.f_90552_);
         }

         return state;
      }
   }

   public final Vector3f m_253058_() {
      return this.f_90554_;
   }

   public final Vector3f m_253028_() {
      return this.f_90555_;
   }

   public final Vector3f m_252775_() {
      return this.f_90556_;
   }

   public void m_90598_() {
      this.f_90550_ = null;
      this.f_90551_ = null;
      this.f_90549_ = false;
   }

   public float m_306445_() {
      return this.f_303114_;
   }

   public static class NearPlane {
      final Vec3 f_167687_;
      private final Vec3 f_167688_;
      private final Vec3 f_167689_;

      NearPlane(Vec3 forwardIn, Vec3 leftIn, Vec3 upIn) {
         this.f_167687_ = forwardIn;
         this.f_167688_ = leftIn;
         this.f_167689_ = upIn;
      }

      public Vec3 m_167694_() {
         return this.f_167687_.m_82549_(this.f_167689_).m_82549_(this.f_167688_);
      }

      public Vec3 m_167698_() {
         return this.f_167687_.m_82549_(this.f_167689_).m_82546_(this.f_167688_);
      }

      public Vec3 m_167699_() {
         return this.f_167687_.m_82546_(this.f_167689_).m_82549_(this.f_167688_);
      }

      public Vec3 m_167700_() {
         return this.f_167687_.m_82546_(this.f_167689_).m_82546_(this.f_167688_);
      }

      public Vec3 m_167695_(float xIn, float yIn) {
         return this.f_167687_.m_82549_(this.f_167689_.m_82490_((double)yIn)).m_82546_(this.f_167688_.m_82490_((double)xIn));
      }
   }
}
