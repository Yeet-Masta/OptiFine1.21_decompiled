package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.CubicSampler;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.Vec3M;
import net.optifine.reflect.Reflector;
import net.optifine.render.GLConst;
import net.optifine.shaders.Shaders;
import org.joml.Vector3f;

public class FogRenderer {
   private static final int f_172575_ = 96;
   private static final List f_234164_ = Lists.newArrayList(new MobEffectFogFunction[]{new BlindnessFogFunction(), new DarknessFogFunction()});
   public static final float f_172574_ = 5000.0F;
   public static float f_109010_;
   public static float f_109011_;
   public static float f_109012_;
   private static int f_109013_ = -1;
   private static int f_109014_ = -1;
   private static long f_109015_ = -1L;
   public static boolean fogStandard = false;

   public static void m_109018_(Camera activeRenderInfoIn, float partialTicks, ClientLevel worldIn, int renderDistanceChunks, float bossColorModifier) {
      FogType fogtype = activeRenderInfoIn.m_167685_();
      Entity entity = activeRenderInfoIn.m_90592_();
      float f13;
      float f15;
      float f16;
      float f5;
      float f7;
      float f9;
      if (fogtype == FogType.WATER) {
         long i = Util.m_137550_();
         int j = ((Biome)worldIn.m_204166_(BlockPos.m_274446_(activeRenderInfoIn.m_90583_())).m_203334_()).m_47561_();
         if (f_109015_ < 0L) {
            f_109013_ = j;
            f_109014_ = j;
            f_109015_ = i;
         }

         int k = f_109013_ >> 16 & 255;
         int l = f_109013_ >> 8 & 255;
         int i1 = f_109013_ & 255;
         int j1 = f_109014_ >> 16 & 255;
         int k1 = f_109014_ >> 8 & 255;
         int l1 = f_109014_ & 255;
         float f = Mth.m_14036_((float)(i - f_109015_) / 5000.0F, 0.0F, 1.0F);
         f13 = Mth.m_14179_(f, (float)j1, (float)k);
         f15 = Mth.m_14179_(f, (float)k1, (float)l);
         f16 = Mth.m_14179_(f, (float)l1, (float)i1);
         f_109010_ = f13 / 255.0F;
         f_109011_ = f15 / 255.0F;
         f_109012_ = f16 / 255.0F;
         if (f_109013_ != j) {
            f_109013_ = j;
            f_109014_ = Mth.m_14143_(f13) << 16 | Mth.m_14143_(f15) << 8 | Mth.m_14143_(f16);
            f_109015_ = i;
         }
      } else if (fogtype == FogType.LAVA) {
         f_109010_ = 0.6F;
         f_109011_ = 0.1F;
         f_109012_ = 0.0F;
         f_109015_ = -1L;
      } else if (fogtype == FogType.POWDER_SNOW) {
         f_109010_ = 0.623F;
         f_109011_ = 0.734F;
         f_109012_ = 0.785F;
         f_109015_ = -1L;
         RenderSystem.clearColor(f_109010_, f_109011_, f_109012_, 0.0F);
      } else {
         f5 = 0.25F + 0.75F * (float)renderDistanceChunks / 32.0F;
         f5 = 1.0F - (float)Math.pow((double)f5, 0.25);
         Vec3 vec3 = worldIn.m_171660_(activeRenderInfoIn.m_90583_(), partialTicks);
         vec3 = CustomColors.getWorldSkyColor(vec3, worldIn, activeRenderInfoIn.m_90592_(), partialTicks);
         f7 = (float)vec3.f_82479_;
         f9 = (float)vec3.f_82480_;
         float f10 = (float)vec3.f_82481_;
         float f11 = Mth.m_14036_(Mth.m_14089_(worldIn.m_46942_(partialTicks) * 6.2831855F) * 2.0F + 0.5F, 0.0F, 1.0F);
         BiomeManager biomemanager = worldIn.m_7062_();
         Vec3 vec31 = activeRenderInfoIn.m_90583_().m_82492_(2.0, 2.0, 2.0).m_82490_(0.25);
         Vec3M vecCol = new Vec3M(0.0, 0.0, 0.0);
         Vec3 vec32 = CubicSampler.sampleM(vec31, (xIn, yIn, zIn) -> {
            int fogColorRgb = ((Biome)biomemanager.m_204210_(xIn, yIn, zIn).m_203334_()).m_47539_();
            vecCol.fromRgbM(fogColorRgb);
            return worldIn.m_104583_().m_5927_(vecCol, f11);
         });
         Vec3 vec32 = worldIn.m_104583_().m_5927_(vec32, f11);
         vec32 = CustomColors.getWorldFogColor(vec32, worldIn, activeRenderInfoIn.m_90592_(), partialTicks);
         f_109010_ = (float)vec32.m_7096_();
         f_109011_ = (float)vec32.m_7098_();
         f_109012_ = (float)vec32.m_7094_();
         if (renderDistanceChunks >= 4) {
            f13 = Mth.m_14031_(worldIn.m_46490_(partialTicks)) > 0.0F ? -1.0F : 1.0F;
            Vector3f vector3f = new Vector3f(f13, 0.0F, 0.0F);
            f16 = activeRenderInfoIn.m_253058_().dot(vector3f);
            if (f16 < 0.0F) {
               f16 = 0.0F;
            }

            if (f16 > 0.0F) {
               float[] afloat = worldIn.m_104583_().m_7518_(worldIn.m_46942_(partialTicks), partialTicks);
               if (afloat != null) {
                  f16 *= afloat[3];
                  f_109010_ = f_109010_ * (1.0F - f16) + afloat[0] * f16;
                  f_109011_ = f_109011_ * (1.0F - f16) + afloat[1] * f16;
                  f_109012_ = f_109012_ * (1.0F - f16) + afloat[2] * f16;
               }
            }
         }

         f_109010_ += (f7 - f_109010_) * f5;
         f_109011_ += (f9 - f_109011_) * f5;
         f_109012_ += (f10 - f_109012_) * f5;
         f13 = worldIn.m_46722_(partialTicks);
         if (f13 > 0.0F) {
            f15 = 1.0F - f13 * 0.5F;
            f16 = 1.0F - f13 * 0.4F;
            f_109010_ *= f15;
            f_109011_ *= f15;
            f_109012_ *= f16;
         }

         f15 = worldIn.m_46661_(partialTicks);
         if (f15 > 0.0F) {
            f16 = 1.0F - f15 * 0.5F;
            f_109010_ *= f16;
            f_109011_ *= f16;
            f_109012_ *= f16;
         }

         f_109015_ = -1L;
      }

      f5 = ((float)activeRenderInfoIn.m_90583_().f_82480_ - (float)worldIn.m_141937_()) * worldIn.m_6106_().m_205519_();
      MobEffectFogFunction fogrenderer$mobeffectfogfunction = m_234165_(entity, partialTicks);
      if (fogrenderer$mobeffectfogfunction != null) {
         LivingEntity livingentity = (LivingEntity)entity;
         f5 = fogrenderer$mobeffectfogfunction.m_213936_(livingentity, livingentity.m_21124_(fogrenderer$mobeffectfogfunction.m_213948_()), f5, partialTicks);
      }

      if (f5 < 1.0F && fogtype != FogType.LAVA && fogtype != FogType.POWDER_SNOW) {
         if (f5 < 0.0F) {
            f5 = 0.0F;
         }

         f5 *= f5;
         f_109010_ *= f5;
         f_109011_ *= f5;
         f_109012_ *= f5;
      }

      if (bossColorModifier > 0.0F) {
         f_109010_ = f_109010_ * (1.0F - bossColorModifier) + f_109010_ * 0.7F * bossColorModifier;
         f_109011_ = f_109011_ * (1.0F - bossColorModifier) + f_109011_ * 0.6F * bossColorModifier;
         f_109012_ = f_109012_ * (1.0F - bossColorModifier) + f_109012_ * 0.6F * bossColorModifier;
      }

      if (fogtype == FogType.WATER) {
         if (entity instanceof LocalPlayer) {
            f7 = ((LocalPlayer)entity).m_108639_();
         } else {
            f7 = 1.0F;
         }
      } else {
         label102: {
            if (entity instanceof LivingEntity) {
               LivingEntity livingentity1 = (LivingEntity)entity;
               if (livingentity1.m_21023_(MobEffects.f_19611_) && !livingentity1.m_21023_(MobEffects.f_216964_)) {
                  f7 = GameRenderer.m_109108_(livingentity1, partialTicks);
                  break label102;
               }
            }

            f7 = 0.0F;
         }
      }

      if (f_109010_ != 0.0F && f_109011_ != 0.0F && f_109012_ != 0.0F) {
         f9 = Math.min(1.0F / f_109010_, Math.min(1.0F / f_109011_, 1.0F / f_109012_));
         f_109010_ = f_109010_ * (1.0F - f7) + f_109010_ * f9 * f7;
         f_109011_ = f_109011_ * (1.0F - f7) + f_109011_ * f9 * f7;
         f_109012_ = f_109012_ * (1.0F - f7) + f_109012_ * f9 * f7;
      }

      Entity renderViewEntity;
      Vec3 colUnderlava;
      if (fogtype == FogType.WATER) {
         renderViewEntity = activeRenderInfoIn.m_90592_();
         colUnderlava = CustomColors.getUnderwaterColor(worldIn, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_());
         if (colUnderlava != null) {
            f_109010_ = (float)colUnderlava.f_82479_;
            f_109011_ = (float)colUnderlava.f_82480_;
            f_109012_ = (float)colUnderlava.f_82481_;
         }
      } else if (fogtype == FogType.LAVA) {
         renderViewEntity = activeRenderInfoIn.m_90592_();
         colUnderlava = CustomColors.getUnderlavaColor(worldIn, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_());
         if (colUnderlava != null) {
            f_109010_ = (float)colUnderlava.f_82479_;
            f_109011_ = (float)colUnderlava.f_82480_;
            f_109012_ = (float)colUnderlava.f_82481_;
         }
      }

      if (Reflector.ForgeHooksClient_getFogColor.exists()) {
         Vector3f fogColor = (Vector3f)Reflector.ForgeHooksClient_getFogColor.call(activeRenderInfoIn, partialTicks, worldIn, renderDistanceChunks, bossColorModifier, f_109010_, f_109011_, f_109012_);
         f_109010_ = fogColor.x();
         f_109011_ = fogColor.y();
         f_109012_ = fogColor.z();
      }

      Shaders.setClearColor(f_109010_, f_109011_, f_109012_, 0.0F);
      RenderSystem.clearColor(f_109010_, f_109011_, f_109012_, 0.0F);
   }

   public static void m_109017_() {
      RenderSystem.setShaderFogStart(Float.MAX_VALUE);
      if (Config.isShaders()) {
         Shaders.setFogDensity(0.0F);
         Shaders.setFogMode(GLConst.GL_EXP2);
         Shaders.setFogStart(1.7014117E38F);
         Shaders.setFogEnd(Float.MAX_VALUE);
      }

   }

   @Nullable
   private static MobEffectFogFunction m_234165_(Entity entityIn, float partialTicks) {
      MobEffectFogFunction var10000;
      if (entityIn instanceof LivingEntity livingentity) {
         var10000 = (MobEffectFogFunction)f_234164_.stream().filter((fogFunctionIn) -> {
            return fogFunctionIn.m_234205_(livingentity, partialTicks);
         }).findFirst().orElse((Object)null);
      } else {
         var10000 = null;
      }

      return var10000;
   }

   public static void m_234172_(Camera activeRenderInfoIn, FogMode fogTypeIn, float farPlaneDistance, boolean nearFog, float partialTicks) {
      fogStandard = false;
      FogType fogtype = activeRenderInfoIn.m_167685_();
      Entity entity = activeRenderInfoIn.m_90592_();
      FogData fogrenderer$fogdata = new FogData(fogTypeIn);
      MobEffectFogFunction fogrenderer$mobeffectfogfunction = m_234165_(entity, partialTicks);
      if (fogtype == FogType.LAVA) {
         if (entity.m_5833_()) {
            fogrenderer$fogdata.f_234200_ = -8.0F;
            fogrenderer$fogdata.f_234201_ = farPlaneDistance * 0.5F;
         } else if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19607_)) {
            fogrenderer$fogdata.f_234200_ = 0.0F;
            fogrenderer$fogdata.f_234201_ = 5.0F;
         } else {
            fogrenderer$fogdata.f_234200_ = 0.25F;
            fogrenderer$fogdata.f_234201_ = 1.0F;
         }
      } else if (fogtype == FogType.POWDER_SNOW) {
         if (entity.m_5833_()) {
            fogrenderer$fogdata.f_234200_ = -8.0F;
            fogrenderer$fogdata.f_234201_ = farPlaneDistance * 0.5F;
         } else {
            fogrenderer$fogdata.f_234200_ = 0.0F;
            fogrenderer$fogdata.f_234201_ = 2.0F;
         }
      } else if (fogrenderer$mobeffectfogfunction != null) {
         LivingEntity livingentity = (LivingEntity)entity;
         MobEffectInstance mobeffectinstance = livingentity.m_21124_(fogrenderer$mobeffectfogfunction.m_213948_());
         if (mobeffectinstance != null) {
            fogrenderer$mobeffectfogfunction.m_213725_(fogrenderer$fogdata, livingentity, mobeffectinstance, farPlaneDistance, partialTicks);
         }
      } else if (fogtype == FogType.WATER) {
         fogrenderer$fogdata.f_234200_ = -8.0F;
         fogrenderer$fogdata.f_234201_ = 96.0F;
         if (entity instanceof LocalPlayer) {
            LocalPlayer localplayer = (LocalPlayer)entity;
            fogrenderer$fogdata.f_234201_ *= Math.max(0.25F, localplayer.m_108639_());
            Holder holder = localplayer.m_9236_().m_204166_(localplayer.m_20183_());
            if (holder.m_203656_(BiomeTags.f_215802_)) {
               fogrenderer$fogdata.f_234201_ *= 0.85F;
            }
         }

         if (fogrenderer$fogdata.f_234201_ > farPlaneDistance) {
            fogrenderer$fogdata.f_234201_ = farPlaneDistance;
            fogrenderer$fogdata.f_234202_ = FogShape.CYLINDER;
         }
      } else if (nearFog) {
         fogStandard = true;
         fogrenderer$fogdata.f_234200_ = farPlaneDistance * 0.05F;
         fogrenderer$fogdata.f_234201_ = Math.min(farPlaneDistance, 192.0F) * 0.5F;
      } else if (fogTypeIn == FogRenderer.FogMode.FOG_SKY) {
         fogrenderer$fogdata.f_234200_ = 0.0F;
         fogrenderer$fogdata.f_234201_ = Math.min(farPlaneDistance, 512.0F);
         fogrenderer$fogdata.f_234202_ = FogShape.CYLINDER;
      } else {
         fogStandard = true;
         float f = Mth.m_14036_(farPlaneDistance / 10.0F, 4.0F, 64.0F);
         fogrenderer$fogdata.f_234200_ = farPlaneDistance * Config.getFogStart();
         fogrenderer$fogdata.f_234201_ = farPlaneDistance;
         fogrenderer$fogdata.f_234202_ = FogShape.CYLINDER;
      }

      RenderSystem.setShaderFogStart(fogrenderer$fogdata.f_234200_);
      RenderSystem.setShaderFogEnd(fogrenderer$fogdata.f_234201_);
      RenderSystem.setShaderFogShape(fogrenderer$fogdata.f_234202_);
      if (Config.isShaders()) {
         Shaders.setFogStart(fogrenderer$fogdata.f_234200_);
         Shaders.setFogEnd(fogrenderer$fogdata.f_234201_);
         Shaders.setFogMode(9729);
         Shaders.setFogShape(fogrenderer$fogdata.f_234202_.ordinal());
      }

      if (Reflector.ForgeHooksClient_onFogRender.exists()) {
         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, fogTypeIn, fogtype, activeRenderInfoIn, partialTicks, farPlaneDistance, fogrenderer$fogdata.f_234200_, fogrenderer$fogdata.f_234201_, fogrenderer$fogdata.f_234202_);
      }

   }

   public static void m_109036_() {
      RenderSystem.setShaderFogColor(f_109010_, f_109011_, f_109012_);
      if (Config.isShaders()) {
         Shaders.setFogColor(f_109010_, f_109011_, f_109012_);
      }

   }

   interface MobEffectFogFunction {
      Holder m_213948_();

      void m_213725_(FogData var1, LivingEntity var2, MobEffectInstance var3, float var4, float var5);

      default boolean m_234205_(LivingEntity entityIn, float partialTicks) {
         return entityIn.m_21023_(this.m_213948_());
      }

      default float m_213936_(LivingEntity entityIn, MobEffectInstance effectIn, float darknessIn, float partialTicks) {
         MobEffectInstance mobeffectinstance = entityIn.m_21124_(this.m_213948_());
         if (mobeffectinstance != null) {
            if (mobeffectinstance.m_267633_(19)) {
               darknessIn = 1.0F - (float)mobeffectinstance.m_19557_() / 20.0F;
            } else {
               darknessIn = 0.0F;
            }
         }

         return darknessIn;
      }
   }

   static class FogData {
      public final FogMode f_234199_;
      public float f_234200_;
      public float f_234201_;
      public FogShape f_234202_;

      public FogData(FogMode fogModeIn) {
         this.f_234202_ = FogShape.SPHERE;
         this.f_234199_ = fogModeIn;
      }
   }

   public static enum FogMode {
      FOG_SKY,
      FOG_TERRAIN;

      // $FF: synthetic method
      private static FogMode[] $values() {
         return new FogMode[]{FOG_SKY, FOG_TERRAIN};
      }
   }

   static class BlindnessFogFunction implements MobEffectFogFunction {
      public Holder m_213948_() {
         return MobEffects.f_19610_;
      }

      public void m_213725_(FogData fogDataIn, LivingEntity entityIn, MobEffectInstance effectIn, float farPlaneDistance, float partialTicks) {
         float f = effectIn.m_267577_() ? 5.0F : Mth.m_14179_(Math.min(1.0F, (float)effectIn.m_19557_() / 20.0F), farPlaneDistance, 5.0F);
         if (fogDataIn.f_234199_ == FogRenderer.FogMode.FOG_SKY) {
            fogDataIn.f_234200_ = 0.0F;
            fogDataIn.f_234201_ = f * 0.8F;
         } else {
            fogDataIn.f_234200_ = f * 0.25F;
            fogDataIn.f_234201_ = f;
         }

      }
   }

   static class DarknessFogFunction implements MobEffectFogFunction {
      public Holder m_213948_() {
         return MobEffects.f_216964_;
      }

      public void m_213725_(FogData fogDataIn, LivingEntity entityIn, MobEffectInstance effectIn, float farPlaneDistance, float partialTicks) {
         float f = Mth.m_14179_(effectIn.m_318631_(entityIn, partialTicks), farPlaneDistance, 15.0F);
         fogDataIn.f_234200_ = fogDataIn.f_234199_ == FogRenderer.FogMode.FOG_SKY ? 0.0F : f * 0.75F;
         fogDataIn.f_234201_ = f;
      }

      public float m_213936_(LivingEntity entityIn, MobEffectInstance effectIn, float darknessIn, float partialTicks) {
         return 1.0F - effectIn.m_318631_(entityIn, partialTicks);
      }
   }
}
