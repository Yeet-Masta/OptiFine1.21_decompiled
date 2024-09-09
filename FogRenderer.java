import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_141436_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1642_;
import net.minecraft.src.C_201410_;
import net.minecraft.src.C_203228_;
import net.minecraft.src.C_206957_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_498_;
import net.minecraft.src.C_500_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_524_;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.Vec3M;
import net.optifine.reflect.Reflector;
import net.optifine.render.GLConst;
import net.optifine.shaders.Shaders;
import org.joml.Vector3f;

public class FogRenderer {
   private static final int b = 96;
   private static final List<FogRenderer.e> c = Lists.newArrayList(new FogRenderer.e[]{new FogRenderer.a(), new FogRenderer.b()});
   public static final float a = 5000.0F;
   public static float d;
   public static float e;
   public static float f;
   private static int g = -1;
   private static int h = -1;
   private static long i = -1L;
   public static boolean fogStandard = false;

   public static void a(Camera activeRenderInfoIn, float partialTicks, ClientLevel worldIn, int renderDistanceChunks, float bossColorModifier) {
      C_141436_ fogtype = activeRenderInfoIn.k();
      C_507_ entity = activeRenderInfoIn.g();
      if (fogtype == C_141436_.WATER) {
         long i = Util.c();
         int j = ((C_1629_)worldIn.m_204166_(C_4675_.m_274446_(activeRenderInfoIn.b())).m_203334_()).m_47561_();
         if (FogRenderer.i < 0L) {
            g = j;
            h = j;
            FogRenderer.i = i;
         }

         int k = g >> 16 & 0xFF;
         int l = g >> 8 & 0xFF;
         int i1 = g & 0xFF;
         int j1 = h >> 16 & 0xFF;
         int k1 = h >> 8 & 0xFF;
         int l1 = h & 0xFF;
         float f = Mth.a((float)(i - FogRenderer.i) / 5000.0F, 0.0F, 1.0F);
         float f1 = Mth.i(f, (float)j1, (float)k);
         float f2 = Mth.i(f, (float)k1, (float)l);
         float f3 = Mth.i(f, (float)l1, (float)i1);
         d = f1 / 255.0F;
         e = f2 / 255.0F;
         FogRenderer.f = f3 / 255.0F;
         if (g != j) {
            g = j;
            h = Mth.d(f1) << 16 | Mth.d(f2) << 8 | Mth.d(f3);
            FogRenderer.i = i;
         }
      } else if (fogtype == C_141436_.LAVA) {
         d = 0.6F;
         e = 0.1F;
         FogRenderer.f = 0.0F;
         FogRenderer.i = -1L;
      } else if (fogtype == C_141436_.POWDER_SNOW) {
         d = 0.623F;
         e = 0.734F;
         FogRenderer.f = 0.785F;
         FogRenderer.i = -1L;
         RenderSystem.clearColor(d, e, FogRenderer.f, 0.0F);
      } else {
         float f4 = 0.25F + 0.75F * (float)renderDistanceChunks / 32.0F;
         f4 = 1.0F - (float)Math.pow((double)f4, 0.25);
         Vec3 vec3 = worldIn.a(activeRenderInfoIn.b(), partialTicks);
         vec3 = CustomColors.getWorldSkyColor(vec3, worldIn, activeRenderInfoIn.g(), partialTicks);
         float f6 = (float)vec3.c;
         float f8 = (float)vec3.d;
         float f10 = (float)vec3.e;
         float f11 = Mth.a(Mth.b(worldIn.m_46942_(partialTicks) * (float) (Math.PI * 2)) * 2.0F + 0.5F, 0.0F, 1.0F);
         C_1642_ biomemanager = worldIn.m_7062_();
         Vec3 vec31 = activeRenderInfoIn.b().a(2.0, 2.0, 2.0).a(0.25);
         Vec3M vecCol = new Vec3M(0.0, 0.0, 0.0);
         Vec3 vec32 = CubicSampler.sampleM(vec31, (xIn, yIn, zIn) -> {
            int fogColorRgb = ((C_1629_)biomemanager.m_204210_(xIn, yIn, zIn).m_203334_()).m_47539_();
            vecCol.fromRgbM(fogColorRgb);
            return worldIn.d().a(vecCol, f11);
         });
         vec32 = worldIn.d().a(vec32, f11);
         vec32 = CustomColors.getWorldFogColor(vec32, worldIn, activeRenderInfoIn.g(), partialTicks);
         d = (float)vec32.m_7096_();
         e = (float)vec32.m_7098_();
         FogRenderer.f = (float)vec32.m_7094_();
         if (renderDistanceChunks >= 4) {
            float f12 = Mth.a(worldIn.m_46490_(partialTicks)) > 0.0F ? -1.0F : 1.0F;
            Vector3f vector3f = new Vector3f(f12, 0.0F, 0.0F);
            float f16 = activeRenderInfoIn.l().dot(vector3f);
            if (f16 < 0.0F) {
               f16 = 0.0F;
            }

            if (f16 > 0.0F) {
               float[] afloat = worldIn.d().m_7518_(worldIn.m_46942_(partialTicks), partialTicks);
               if (afloat != null) {
                  f16 *= afloat[3];
                  d = d * (1.0F - f16) + afloat[0] * f16;
                  e = e * (1.0F - f16) + afloat[1] * f16;
                  FogRenderer.f = FogRenderer.f * (1.0F - f16) + afloat[2] * f16;
               }
            }
         }

         d = d + (f6 - d) * f4;
         e = e + (f8 - e) * f4;
         FogRenderer.f = FogRenderer.f + (f10 - FogRenderer.f) * f4;
         float f13 = worldIn.m_46722_(partialTicks);
         if (f13 > 0.0F) {
            float f14 = 1.0F - f13 * 0.5F;
            float f17 = 1.0F - f13 * 0.4F;
            d *= f14;
            e *= f14;
            FogRenderer.f *= f17;
         }

         float f15 = worldIn.m_46661_(partialTicks);
         if (f15 > 0.0F) {
            float f18 = 1.0F - f15 * 0.5F;
            d *= f18;
            e *= f18;
            FogRenderer.f *= f18;
         }

         FogRenderer.i = -1L;
      }

      float f5 = ((float)activeRenderInfoIn.b().d - (float)worldIn.m_141937_()) * worldIn.k().e();
      FogRenderer.e fogrenderer$mobeffectfogfunction = a(entity, partialTicks);
      if (fogrenderer$mobeffectfogfunction != null) {
         C_524_ livingentity = (C_524_)entity;
         f5 = fogrenderer$mobeffectfogfunction.a(livingentity, livingentity.m_21124_(fogrenderer$mobeffectfogfunction.a()), f5, partialTicks);
      }

      if (f5 < 1.0F && fogtype != C_141436_.LAVA && fogtype != C_141436_.POWDER_SNOW) {
         if (f5 < 0.0F) {
            f5 = 0.0F;
         }

         f5 *= f5;
         d *= f5;
         e *= f5;
         FogRenderer.f *= f5;
      }

      if (bossColorModifier > 0.0F) {
         d = d * (1.0F - bossColorModifier) + d * 0.7F * bossColorModifier;
         e = e * (1.0F - bossColorModifier) + e * 0.6F * bossColorModifier;
         FogRenderer.f = FogRenderer.f * (1.0F - bossColorModifier) + FogRenderer.f * 0.6F * bossColorModifier;
      }

      float f7;
      if (fogtype == C_141436_.WATER) {
         if (entity instanceof C_4105_) {
            f7 = ((C_4105_)entity).m_108639_();
         } else {
            f7 = 1.0F;
         }
      } else {
         label102: {
            if (entity instanceof C_524_ livingentity1 && livingentity1.m_21023_(C_500_.f_19611_) && !livingentity1.m_21023_(C_500_.f_216964_)) {
               f7 = GameRenderer.a(livingentity1, partialTicks);
               break label102;
            }

            f7 = 0.0F;
         }
      }

      if (d != 0.0F && e != 0.0F && FogRenderer.f != 0.0F) {
         float f9 = Math.min(1.0F / d, Math.min(1.0F / e, 1.0F / FogRenderer.f));
         d = d * (1.0F - f7) + d * f9 * f7;
         e = e * (1.0F - f7) + e * f9 * f7;
         FogRenderer.f = FogRenderer.f * (1.0F - f7) + FogRenderer.f * f9 * f7;
      }

      if (fogtype == C_141436_.WATER) {
         C_507_ renderViewEntity = activeRenderInfoIn.g();
         Vec3 colUnderwater = CustomColors.getUnderwaterColor(
            worldIn, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_()
         );
         if (colUnderwater != null) {
            d = (float)colUnderwater.c;
            e = (float)colUnderwater.d;
            FogRenderer.f = (float)colUnderwater.e;
         }
      } else if (fogtype == C_141436_.LAVA) {
         C_507_ renderViewEntity = activeRenderInfoIn.g();
         Vec3 colUnderlava = CustomColors.getUnderlavaColor(
            worldIn, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_()
         );
         if (colUnderlava != null) {
            d = (float)colUnderlava.c;
            e = (float)colUnderlava.d;
            FogRenderer.f = (float)colUnderlava.e;
         }
      }

      if (Reflector.ForgeHooksClient_getFogColor.exists()) {
         Vector3f fogColor = (Vector3f)Reflector.ForgeHooksClient_getFogColor
            .call(activeRenderInfoIn, partialTicks, worldIn, renderDistanceChunks, bossColorModifier, d, e, FogRenderer.f);
         d = fogColor.x();
         e = fogColor.y();
         FogRenderer.f = fogColor.z();
      }

      Shaders.setClearColor(d, e, FogRenderer.f, 0.0F);
      RenderSystem.clearColor(d, e, FogRenderer.f, 0.0F);
   }

   public static void a() {
      RenderSystem.setShaderFogStart(Float.MAX_VALUE);
      if (Config.isShaders()) {
         Shaders.setFogDensity(0.0F);
         Shaders.setFogMode(GLConst.GL_EXP2);
         Shaders.setFogStart(1.7014117E38F);
         Shaders.setFogEnd(Float.MAX_VALUE);
      }
   }

   @Nullable
   private static FogRenderer.e a(C_507_ entityIn, float partialTicks) {
      return entityIn instanceof C_524_ livingentity
         ? (FogRenderer.e)c.stream().filter(fogFunctionIn -> fogFunctionIn.a(livingentity, partialTicks)).findFirst().orElse(null)
         : null;
   }

   public static void a(Camera activeRenderInfoIn, FogRenderer.d fogTypeIn, float farPlaneDistance, boolean nearFog, float partialTicks) {
      fogStandard = false;
      C_141436_ fogtype = activeRenderInfoIn.k();
      C_507_ entity = activeRenderInfoIn.g();
      FogRenderer.c fogrenderer$fogdata = new FogRenderer.c(fogTypeIn);
      FogRenderer.e fogrenderer$mobeffectfogfunction = a(entity, partialTicks);
      if (fogtype == C_141436_.LAVA) {
         if (entity.m_5833_()) {
            fogrenderer$fogdata.b = -8.0F;
            fogrenderer$fogdata.c = farPlaneDistance * 0.5F;
         } else if (entity instanceof C_524_ && ((C_524_)entity).m_21023_(C_500_.f_19607_)) {
            fogrenderer$fogdata.b = 0.0F;
            fogrenderer$fogdata.c = 5.0F;
         } else {
            fogrenderer$fogdata.b = 0.25F;
            fogrenderer$fogdata.c = 1.0F;
         }
      } else if (fogtype == C_141436_.POWDER_SNOW) {
         if (entity.m_5833_()) {
            fogrenderer$fogdata.b = -8.0F;
            fogrenderer$fogdata.c = farPlaneDistance * 0.5F;
         } else {
            fogrenderer$fogdata.b = 0.0F;
            fogrenderer$fogdata.c = 2.0F;
         }
      } else if (fogrenderer$mobeffectfogfunction != null) {
         C_524_ livingentity = (C_524_)entity;
         C_498_ mobeffectinstance = livingentity.m_21124_(fogrenderer$mobeffectfogfunction.a());
         if (mobeffectinstance != null) {
            fogrenderer$mobeffectfogfunction.a(fogrenderer$fogdata, livingentity, mobeffectinstance, farPlaneDistance, partialTicks);
         }
      } else if (fogtype == C_141436_.WATER) {
         fogrenderer$fogdata.b = -8.0F;
         fogrenderer$fogdata.c = 96.0F;
         if (entity instanceof C_4105_ localplayer) {
            fogrenderer$fogdata.c = fogrenderer$fogdata.c * Math.max(0.25F, localplayer.m_108639_());
            C_203228_<C_1629_> holder = localplayer.dO().m_204166_(localplayer.do());
            if (holder.m_203656_(C_206957_.f_215802_)) {
               fogrenderer$fogdata.c *= 0.85F;
            }
         }

         if (fogrenderer$fogdata.c > farPlaneDistance) {
            fogrenderer$fogdata.c = farPlaneDistance;
            fogrenderer$fogdata.d = C_201410_.CYLINDER;
         }
      } else if (nearFog) {
         fogStandard = true;
         fogrenderer$fogdata.b = farPlaneDistance * 0.05F;
         fogrenderer$fogdata.c = Math.min(farPlaneDistance, 192.0F) * 0.5F;
      } else if (fogTypeIn == FogRenderer.d.a) {
         fogrenderer$fogdata.b = 0.0F;
         fogrenderer$fogdata.c = Math.min(farPlaneDistance, 512.0F);
         fogrenderer$fogdata.d = C_201410_.CYLINDER;
      } else {
         fogStandard = true;
         float f = Mth.a(farPlaneDistance / 10.0F, 4.0F, 64.0F);
         fogrenderer$fogdata.b = farPlaneDistance * Config.getFogStart();
         fogrenderer$fogdata.c = farPlaneDistance;
         fogrenderer$fogdata.d = C_201410_.CYLINDER;
      }

      RenderSystem.setShaderFogStart(fogrenderer$fogdata.b);
      RenderSystem.setShaderFogEnd(fogrenderer$fogdata.c);
      RenderSystem.setShaderFogShape(fogrenderer$fogdata.d);
      if (Config.isShaders()) {
         Shaders.setFogStart(fogrenderer$fogdata.b);
         Shaders.setFogEnd(fogrenderer$fogdata.c);
         Shaders.setFogMode(9729);
         Shaders.setFogShape(fogrenderer$fogdata.d.ordinal());
      }

      if (Reflector.ForgeHooksClient_onFogRender.exists()) {
         Reflector.callVoid(
            Reflector.ForgeHooksClient_onFogRender,
            fogTypeIn,
            fogtype,
            activeRenderInfoIn,
            partialTicks,
            farPlaneDistance,
            fogrenderer$fogdata.b,
            fogrenderer$fogdata.c,
            fogrenderer$fogdata.d
         );
      }
   }

   public static void b() {
      RenderSystem.setShaderFogColor(d, e, f);
      if (Config.isShaders()) {
         Shaders.setFogColor(d, e, f);
      }
   }

   static class a implements FogRenderer.e {
      @Override
      public C_203228_<C_496_> a() {
         return C_500_.f_19610_;
      }

      @Override
      public void a(FogRenderer.c fogDataIn, C_524_ entityIn, C_498_ effectIn, float farPlaneDistance, float partialTicks) {
         float f = effectIn.m_267577_() ? 5.0F : Mth.i(Math.min(1.0F, (float)effectIn.m_19557_() / 20.0F), farPlaneDistance, 5.0F);
         if (fogDataIn.a == FogRenderer.d.a) {
            fogDataIn.b = 0.0F;
            fogDataIn.c = f * 0.8F;
         } else {
            fogDataIn.b = f * 0.25F;
            fogDataIn.c = f;
         }
      }
   }

   static class b implements FogRenderer.e {
      @Override
      public C_203228_<C_496_> a() {
         return C_500_.f_216964_;
      }

      @Override
      public void a(FogRenderer.c fogDataIn, C_524_ entityIn, C_498_ effectIn, float farPlaneDistance, float partialTicks) {
         float f = Mth.i(effectIn.m_318631_(entityIn, partialTicks), farPlaneDistance, 15.0F);
         fogDataIn.b = fogDataIn.a == FogRenderer.d.a ? 0.0F : f * 0.75F;
         fogDataIn.c = f;
      }

      @Override
      public float a(C_524_ entityIn, C_498_ effectIn, float darknessIn, float partialTicks) {
         return 1.0F - effectIn.m_318631_(entityIn, partialTicks);
      }
   }

   static class c {
      public final FogRenderer.d a;
      public float b;
      public float c;
      public C_201410_ d = C_201410_.SPHERE;

      public c(FogRenderer.d fogModeIn) {
         this.a = fogModeIn;
      }
   }

   public static enum d {
      a,
      b;
   }

   interface e {
      C_203228_<C_496_> a();

      void a(FogRenderer.c var1, C_524_ var2, C_498_ var3, float var4, float var5);

      default boolean a(C_524_ entityIn, float partialTicks) {
         return entityIn.m_21023_(this.a());
      }

      default float a(C_524_ entityIn, C_498_ effectIn, float darknessIn, float partialTicks) {
         C_498_ mobeffectinstance = entityIn.m_21124_(this.a());
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
}
