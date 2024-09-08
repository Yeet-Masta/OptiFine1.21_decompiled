package net.minecraft.client.renderer;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.dimension.DimensionType;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.util.TextureUtils;
import org.joml.Vector3f;

public class LightTexture implements AutoCloseable {
   public static final int f_173040_ = 15728880;
   public static final int f_173041_ = 15728640;
   public static final int f_173042_ = 240;
   private final DynamicTexture f_109870_;
   private final NativeImage f_109871_;
   private final ResourceLocation f_109872_;
   private boolean f_109873_;
   private float f_109874_;
   private final GameRenderer f_109875_;
   private final Minecraft f_109876_;
   private boolean allowed = true;
   private boolean custom = false;
   private Vector3f tempVector = new Vector3f();
   public static final int MAX_BRIGHTNESS = m_109885_(15, 15);
   public static final int VANILLA_EMISSIVE_BRIGHTNESS = 15794417;

   public LightTexture(GameRenderer entityRendererIn, Minecraft mcIn) {
      this.f_109875_ = entityRendererIn;
      this.f_109876_ = mcIn;
      this.f_109870_ = new DynamicTexture(16, 16, false);
      this.f_109872_ = this.f_109876_.m_91097_().m_118490_("light_map", this.f_109870_);
      this.f_109871_ = this.f_109870_.m_117991_();

      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            this.f_109871_.m_84988_(j, i, -1);
         }
      }

      this.f_109870_.m_117985_();
   }

   public void close() {
      this.f_109870_.close();
   }

   public void m_109880_() {
      this.f_109874_ = this.f_109874_ + (float)((Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
      this.f_109874_ *= 0.9F;
      this.f_109873_ = true;
   }

   public void m_109891_() {
      RenderSystem.setShaderTexture(2, 0);
      if (Config.isShaders()) {
         Shaders.disableLightmap();
      }
   }

   public void m_109896_() {
      if (!this.allowed) {
         RenderSystem.setShaderTexture(2, TextureUtils.WHITE_TEXTURE_LOCATION);
         this.f_109876_.m_91097_().m_174784_(TextureUtils.WHITE_TEXTURE_LOCATION);
      } else {
         RenderSystem.setShaderTexture(2, this.f_109872_);
         this.f_109876_.m_91097_().m_174784_(this.f_109872_);
      }

      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      if (Config.isShaders()) {
         Shaders.enableLightmap();
      }
   }

   private float m_234319_(float partialTicks) {
      MobEffectInstance mobeffectinstance = this.f_109876_.f_91074_.m_21124_(MobEffects.f_216964_);
      return mobeffectinstance != null ? mobeffectinstance.m_318631_(this.f_109876_.f_91074_, partialTicks) : 0.0F;
   }

   private float m_234312_(LivingEntity entityIn, float gammaIn, float partialTicks) {
      float f = 0.45F * gammaIn;
      return Math.max(0.0F, Mth.m_14089_(((float)entityIn.f_19797_ - partialTicks) * (float) Math.PI * 0.025F) * f);
   }

   public void m_109881_(float partialTicks) {
      if (this.f_109873_) {
         this.f_109873_ = false;
         this.f_109876_.m_91307_().m_6180_("lightTex");
         ClientLevel clientlevel = this.f_109876_.f_91073_;
         if (clientlevel != null) {
            this.custom = false;
            if (Config.isCustomColors()) {
               boolean nightVision = this.f_109876_.f_91074_.m_21023_(MobEffects.f_19611_) || this.f_109876_.f_91074_.m_21023_(MobEffects.f_19592_);
               float darkGammaFactor = this.getDarknessGammaFactor(partialTicks);
               float darkLightFactor = this.getDarknessLightFactor(clientlevel, partialTicks);
               float darkLight = darkGammaFactor * 0.25F + darkLightFactor * 0.75F;
               if (CustomColors.updateLightmap(clientlevel, this.f_109874_, this.f_109871_, nightVision, darkLight, partialTicks)) {
                  this.f_109870_.m_117985_();
                  this.f_109873_ = false;
                  this.f_109876_.m_91307_().m_7238_();
                  this.custom = true;
                  return;
               }
            }

            float f = clientlevel.m_104805_(1.0F);
            float f1;
            if (clientlevel.m_104819_() > 0) {
               f1 = 1.0F;
            } else {
               f1 = f * 0.95F + 0.05F;
            }

            float f2 = this.f_109876_.f_91066_.m_231926_().m_231551_().floatValue();
            float f3 = this.m_234319_(partialTicks) * f2;
            float f4 = this.m_234312_(this.f_109876_.f_91074_, f3, partialTicks) * f2;
            if (Config.isShaders()) {
               Shaders.setDarknessFactor(f3);
               Shaders.setDarknessLightFactor(f4);
            }

            float f6 = this.f_109876_.f_91074_.m_108639_();
            float f5;
            if (this.f_109876_.f_91074_.m_21023_(MobEffects.f_19611_)) {
               f5 = GameRenderer.m_109108_(this.f_109876_.f_91074_, partialTicks);
            } else if (f6 > 0.0F && this.f_109876_.f_91074_.m_21023_(MobEffects.f_19592_)) {
               f5 = f6;
            } else {
               f5 = 0.0F;
            }

            Vector3f vector3f = new Vector3f(f, f, 1.0F).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float f7 = this.f_109874_ + 1.5F;
            Vector3f vector3f1 = new Vector3f();

            for (int i = 0; i < 16; i++) {
               for (int j = 0; j < 16; j++) {
                  float f8 = m_234316_(clientlevel.m_6042_(), i) * f1;
                  float f9 = m_234316_(clientlevel.m_6042_(), j) * f7;
                  float f10 = f9 * ((f9 * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float f11 = f9 * (f9 * f9 * 0.6F + 0.4F);
                  vector3f1.set(f9, f10, f11);
                  boolean flag = clientlevel.m_104583_().m_108884_();
                  if (flag) {
                     vector3f1.lerp(this.getTempVector3f(0.99F, 1.12F, 1.0F), 0.25F);
                     m_252983_(vector3f1);
                  } else {
                     Vector3f vector3f2 = this.getTempCopy(vector3f).mul(f8);
                     vector3f1.add(vector3f2);
                     vector3f1.lerp(this.getTempVector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.f_109875_.m_109131_(partialTicks) > 0.0F) {
                        float f12 = this.f_109875_.m_109131_(partialTicks);
                        Vector3f vector3f3 = this.getTempCopy(vector3f1).mul(0.7F, 0.6F, 0.6F);
                        vector3f1.lerp(vector3f3, f12);
                     }
                  }

                  if (Reflector.IForgeDimensionSpecialEffects_adjustLightmapColors.exists()) {
                     Reflector.call(
                        clientlevel.m_104583_(),
                        Reflector.IForgeDimensionSpecialEffects_adjustLightmapColors,
                        clientlevel,
                        partialTicks,
                        f,
                        f7,
                        f8,
                        j,
                        i,
                        vector3f1
                     );
                  }

                  if (f5 > 0.0F) {
                     float f13 = Math.max(vector3f1.x(), Math.max(vector3f1.y(), vector3f1.z()));
                     if (f13 < 1.0F) {
                        float f15 = 1.0F / f13;
                        Vector3f vector3f5 = this.getTempCopy(vector3f1).mul(f15);
                        vector3f1.lerp(vector3f5, f5);
                     }
                  }

                  if (!flag) {
                     if (f4 > 0.0F) {
                        vector3f1.add(-f4, -f4, -f4);
                     }

                     m_252983_(vector3f1);
                  }

                  float f14 = this.f_109876_.f_91066_.m_231927_().m_231551_().floatValue();
                  Vector3f vector3f4 = this.getTempVector3f(this.m_109892_(vector3f1.x), this.m_109892_(vector3f1.y), this.m_109892_(vector3f1.z));
                  vector3f1.lerp(vector3f4, Math.max(0.0F, f14 - f3));
                  vector3f1.lerp(this.getTempVector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  m_252983_(vector3f1);
                  vector3f1.mul(255.0F);
                  int j1 = 255;
                  int k = (int)vector3f1.x();
                  int l = (int)vector3f1.y();
                  int i1 = (int)vector3f1.z();
                  this.f_109871_.m_84988_(j, i, 0xFF000000 | i1 << 16 | l << 8 | k);
               }
            }

            this.f_109870_.m_117985_();
            this.f_109876_.m_91307_().m_7238_();
         }
      }
   }

   private static void m_252983_(Vector3f vecIn) {
      vecIn.set(Mth.m_14036_(vecIn.x, 0.0F, 1.0F), Mth.m_14036_(vecIn.y, 0.0F, 1.0F), Mth.m_14036_(vecIn.z, 0.0F, 1.0F));
   }

   private float m_109892_(float valueIn) {
      float f = 1.0F - valueIn;
      return 1.0F - f * f * f * f;
   }

   public static float m_234316_(DimensionType dimensionIn, int lightLevelIn) {
      float f = (float)lightLevelIn / 15.0F;
      float f1 = f / (4.0F - 3.0F * f);
      return Mth.m_14179_(dimensionIn.f_63838_(), f1, 1.0F);
   }

   public static int m_109885_(int blockLightIn, int skyLightIn) {
      return blockLightIn << 4 | skyLightIn << 20;
   }

   public static int m_109883_(int packedLightIn) {
      return (packedLightIn & 65535) >> 4;
   }

   public static int m_109894_(int packedLightIn) {
      return packedLightIn >> 20 & 65535;
   }

   private Vector3f getTempVector3f(float x, float y, float z) {
      this.tempVector.set(x, y, z);
      return this.tempVector;
   }

   private Vector3f getTempCopy(Vector3f vec) {
      this.tempVector.set(vec.x(), vec.y(), vec.z());
      return this.tempVector;
   }

   public boolean isAllowed() {
      return this.allowed;
   }

   public void setAllowed(boolean allowed) {
      this.allowed = allowed;
   }

   public boolean isCustom() {
      return this.custom;
   }

   public float getDarknessGammaFactor(float partialTicks) {
      float darknessScale = this.f_109876_.f_91066_.m_231926_().m_231551_().floatValue();
      return this.m_234319_(partialTicks) * darknessScale;
   }

   public float getDarknessLightFactor(ClientLevel clientLevel, float partialTicks) {
      boolean forceBrightness = clientLevel.m_104583_().m_108884_();
      if (forceBrightness) {
         return 0.0F;
      } else {
         float darknessScale = this.f_109876_.f_91066_.m_231926_().m_231551_().floatValue();
         float darknessGamma = this.m_234319_(partialTicks) * darknessScale;
         return this.m_234312_(this.f_109876_.f_91074_, darknessGamma, partialTicks) * darknessScale;
      }
   }
}
