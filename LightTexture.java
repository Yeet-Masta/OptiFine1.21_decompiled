import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.src.C_2175_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_498_;
import net.minecraft.src.C_500_;
import net.minecraft.src.C_524_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import net.optifine.util.TextureUtils;
import org.joml.Vector3f;

public class LightTexture implements AutoCloseable {
   public static final int a = 15728880;
   public static final int b = 15728640;
   public static final int c = 240;
   private final DynamicTexture d;
   private final NativeImage e;
   private final ResourceLocation f;
   private boolean g;
   private float h;
   private final GameRenderer i;
   private final C_3391_ j;
   private boolean allowed = true;
   private boolean custom = false;
   private Vector3f tempVector = new Vector3f();
   public static final int MAX_BRIGHTNESS = a(15, 15);
   public static final int VANILLA_EMISSIVE_BRIGHTNESS = 15794417;

   public LightTexture(GameRenderer entityRendererIn, C_3391_ mcIn) {
      this.i = entityRendererIn;
      this.j = mcIn;
      this.d = new DynamicTexture(16, 16, false);
      this.f = this.j.aa().a("light_map", this.d);
      this.e = this.d.e();

      for (int i = 0; i < 16; i++) {
         for (int j = 0; j < 16; j++) {
            this.e.a(j, i, -1);
         }
      }

      this.d.d();
   }

   public void close() {
      this.d.close();
   }

   public void a() {
      this.h = this.h + (float)((Math.random() - Math.random()) * Math.random() * Math.random() * 0.1);
      this.h *= 0.9F;
      this.g = true;
   }

   public void b() {
      RenderSystem.setShaderTexture(2, 0);
      if (Config.isShaders()) {
         Shaders.disableLightmap();
      }
   }

   public void c() {
      if (!this.allowed) {
         RenderSystem.setShaderTexture(2, TextureUtils.WHITE_TEXTURE_LOCATION);
         this.j.aa().a(TextureUtils.WHITE_TEXTURE_LOCATION);
      } else {
         RenderSystem.setShaderTexture(2, this.f);
         this.j.aa().a(this.f);
      }

      RenderSystem.texParameter(3553, 10241, 9729);
      RenderSystem.texParameter(3553, 10240, 9729);
      if (Config.isShaders()) {
         Shaders.enableLightmap();
      }
   }

   private float b(float partialTicks) {
      C_498_ mobeffectinstance = this.j.f_91074_.c(C_500_.f_216964_);
      return mobeffectinstance != null ? mobeffectinstance.m_318631_(this.j.f_91074_, partialTicks) : 0.0F;
   }

   private float a(C_524_ entityIn, float gammaIn, float partialTicks) {
      float f = 0.45F * gammaIn;
      return Math.max(0.0F, Mth.b(((float)entityIn.f_19797_ - partialTicks) * (float) Math.PI * 0.025F) * f);
   }

   public void a(float partialTicks) {
      if (this.g) {
         this.g = false;
         this.j.m_91307_().m_6180_("lightTex");
         ClientLevel clientlevel = this.j.r;
         if (clientlevel != null) {
            this.custom = false;
            if (Config.isCustomColors()) {
               boolean nightVision = this.j.f_91074_.b(C_500_.f_19611_) || this.j.f_91074_.b(C_500_.f_19592_);
               float darkGammaFactor = this.getDarknessGammaFactor(partialTicks);
               float darkLightFactor = this.getDarknessLightFactor(clientlevel, partialTicks);
               float darkLight = darkGammaFactor * 0.25F + darkLightFactor * 0.75F;
               if (CustomColors.updateLightmap(clientlevel, this.h, this.e, nightVision, darkLight, partialTicks)) {
                  this.d.d();
                  this.g = false;
                  this.j.m_91307_().m_7238_();
                  this.custom = true;
                  return;
               }
            }

            float f = clientlevel.g(1.0F);
            float f1;
            if (clientlevel.j() > 0) {
               f1 = 1.0F;
            } else {
               f1 = f * 0.95F + 0.05F;
            }

            float f2 = this.j.m.al().c().floatValue();
            float f3 = this.b(partialTicks) * f2;
            float f4 = this.a(this.j.f_91074_, f3, partialTicks) * f2;
            if (Config.isShaders()) {
               Shaders.setDarknessFactor(f3);
               Shaders.setDarknessLightFactor(f4);
            }

            float f6 = this.j.f_91074_.m_108639_();
            float f5;
            if (this.j.f_91074_.b(C_500_.f_19611_)) {
               f5 = GameRenderer.a(this.j.f_91074_, partialTicks);
            } else if (f6 > 0.0F && this.j.f_91074_.b(C_500_.f_19592_)) {
               f5 = f6;
            } else {
               f5 = 0.0F;
            }

            Vector3f vector3f = new Vector3f(f, f, 1.0F).lerp(new Vector3f(1.0F, 1.0F, 1.0F), 0.35F);
            float f7 = this.h + 1.5F;
            Vector3f vector3f1 = new Vector3f();

            for (int i = 0; i < 16; i++) {
               for (int j = 0; j < 16; j++) {
                  float f8 = a(clientlevel.m_6042_(), i) * f1;
                  float f9 = a(clientlevel.m_6042_(), j) * f7;
                  float f10 = f9 * ((f9 * 0.6F + 0.4F) * 0.6F + 0.4F);
                  float f11 = f9 * (f9 * f9 * 0.6F + 0.4F);
                  vector3f1.set(f9, f10, f11);
                  boolean flag = clientlevel.d().m_108884_();
                  if (flag) {
                     vector3f1.lerp(this.getTempVector3f(0.99F, 1.12F, 1.0F), 0.25F);
                     a(vector3f1);
                  } else {
                     Vector3f vector3f2 = this.getTempCopy(vector3f).mul(f8);
                     vector3f1.add(vector3f2);
                     vector3f1.lerp(this.getTempVector3f(0.75F, 0.75F, 0.75F), 0.04F);
                     if (this.i.c(partialTicks) > 0.0F) {
                        float f12 = this.i.c(partialTicks);
                        Vector3f vector3f3 = this.getTempCopy(vector3f1).mul(0.7F, 0.6F, 0.6F);
                        vector3f1.lerp(vector3f3, f12);
                     }
                  }

                  if (Reflector.IForgeDimensionSpecialEffects_adjustLightmapColors.exists()) {
                     Reflector.call(
                        clientlevel.d(), Reflector.IForgeDimensionSpecialEffects_adjustLightmapColors, clientlevel, partialTicks, f, f7, f8, j, i, vector3f1
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

                     a(vector3f1);
                  }

                  float f14 = this.j.m.ap().c().floatValue();
                  Vector3f vector3f4 = this.getTempVector3f(this.c(vector3f1.x), this.c(vector3f1.y), this.c(vector3f1.z));
                  vector3f1.lerp(vector3f4, Math.max(0.0F, f14 - f3));
                  vector3f1.lerp(this.getTempVector3f(0.75F, 0.75F, 0.75F), 0.04F);
                  a(vector3f1);
                  vector3f1.mul(255.0F);
                  int j1 = 255;
                  int k = (int)vector3f1.x();
                  int l = (int)vector3f1.y();
                  int i1 = (int)vector3f1.z();
                  this.e.a(j, i, 0xFF000000 | i1 << 16 | l << 8 | k);
               }
            }

            this.d.d();
            this.j.m_91307_().m_7238_();
         }
      }
   }

   private static void a(Vector3f vecIn) {
      vecIn.set(Mth.a(vecIn.x, 0.0F, 1.0F), Mth.a(vecIn.y, 0.0F, 1.0F), Mth.a(vecIn.z, 0.0F, 1.0F));
   }

   private float c(float valueIn) {
      float f = 1.0F - valueIn;
      return 1.0F - f * f * f * f;
   }

   public static float a(C_2175_ dimensionIn, int lightLevelIn) {
      float f = (float)lightLevelIn / 15.0F;
      float f1 = f / (4.0F - 3.0F * f);
      return Mth.i(dimensionIn.f_63838_(), f1, 1.0F);
   }

   public static int a(int blockLightIn, int skyLightIn) {
      return blockLightIn << 4 | skyLightIn << 20;
   }

   public static int a(int packedLightIn) {
      return (packedLightIn & 65535) >> 4;
   }

   public static int b(int packedLightIn) {
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
      float darknessScale = this.j.m.al().c().floatValue();
      return this.b(partialTicks) * darknessScale;
   }

   public float getDarknessLightFactor(ClientLevel clientLevel, float partialTicks) {
      boolean forceBrightness = clientLevel.d().m_108884_();
      if (forceBrightness) {
         return 0.0F;
      } else {
         float darknessScale = this.j.m.al().c().floatValue();
         float darknessGamma = this.b(partialTicks) * darknessScale;
         return this.a(this.j.f_91074_, darknessGamma, partialTicks) * darknessScale;
      }
   }
}
