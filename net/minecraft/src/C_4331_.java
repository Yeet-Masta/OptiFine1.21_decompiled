package net.minecraft.src;

import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.Either;
import org.joml.Matrix4f;

public abstract class C_4331_ implements IEntityRenderer {
   protected static final float f_174006_ = 0.025F;
   public static final int f_336747_ = 24;
   protected final C_4330_ f_114476_;
   private final C_3429_ f_174005_;
   public float f_114477_;
   public float f_114478_ = 1.0F;
   private C_513_ entityType = null;
   private C_5265_ locationTextureCustom = null;
   public float shadowOffsetX;
   public float shadowOffsetZ;
   public float leashOffsetX;
   public float leashOffsetY;
   public float leashOffsetZ;

   protected C_4331_(C_141742_.C_141743_ contextIn) {
      this.f_114476_ = contextIn.m_174022_();
      this.f_174005_ = contextIn.m_174028_();
   }

   public final int m_114505_(C_507_ entityIn, float partialTicks) {
      C_4675_ blockpos = C_4675_.m_274446_(entityIn.m_7371_(partialTicks));
      return C_4138_.m_109885_(this.m_6086_(entityIn, blockpos), this.m_114508_(entityIn, blockpos));
   }

   protected int m_114508_(C_507_ entityIn, C_4675_ blockPosIn) {
      return entityIn.m_9236_().a(C_1607_.SKY, blockPosIn);
   }

   protected int m_6086_(C_507_ entityIn, C_4675_ partialTicks) {
      return entityIn.m_6060_() ? 15 : entityIn.m_9236_().a(C_1607_.BLOCK, partialTicks);
   }

   public boolean m_5523_(C_507_ livingEntityIn, C_4273_ camera, double camX, double camY, double camZ) {
      if (!livingEntityIn.m_6000_(camX, camY, camZ)) {
         return false;
      } else if (livingEntityIn.f_19811_) {
         return true;
      } else {
         C_3040_ aabb = livingEntityIn.m_6921_().m_82400_(0.5);
         if (aabb.m_82392_() || aabb.m_82309_() == 0.0) {
            aabb = new C_3040_(livingEntityIn.m_20185_() - 2.0, livingEntityIn.m_20186_() - 2.0, livingEntityIn.m_20189_() - 2.0, livingEntityIn.m_20185_() + 2.0, livingEntityIn.m_20186_() + 2.0, livingEntityIn.m_20189_() + 2.0);
         }

         if (camera.m_113029_(aabb)) {
            return true;
         } else {
            if (livingEntityIn instanceof C_336597_) {
               C_336597_ leashable = (C_336597_)livingEntityIn;
               C_507_ entity = leashable.m_340614_();
               if (entity != null) {
                  return camera.m_113029_(entity.m_6921_());
               }
            }

            return false;
         }
      }
   }

   public C_3046_ m_7860_(C_507_ entityIn, float partialTicks) {
      return C_3046_.f_82478_;
   }

   public void m_7392_(C_507_ entityIn, float entityYaw, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn) {
      if (entityIn instanceof C_336597_ leashable) {
         C_507_ entity = leashable.m_340614_();
         if (entity != null) {
            this.m_340483_(entityIn, partialTicks, matrixStackIn, bufferIn, entity);
         }
      }

      if (!Reflector.RenderNameTagEvent_Constructor.exists()) {
         if (this.m_6512_(entityIn)) {
            this.m_7649_(entityIn, entityIn.m_5446_(), matrixStackIn, bufferIn, packedLightIn, partialTicks);
         }

      } else {
         Object renderNameplateEvent = Reflector.newInstance(Reflector.RenderNameTagEvent_Constructor, entityIn, entityIn.m_5446_(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
         Reflector.postForgeBusEvent(renderNameplateEvent);
         Object result = Reflector.call(renderNameplateEvent, Reflector.Event_getResult);
         if (result != ReflectorForge.EVENT_RESULT_DENY && (result == ReflectorForge.EVENT_RESULT_ALLOW || this.m_6512_(entityIn))) {
            C_4996_ content = (C_4996_)Reflector.call(renderNameplateEvent, Reflector.RenderNameTagEvent_getContent);
            this.m_7649_(entityIn, content, matrixStackIn, bufferIn, packedLightIn, partialTicks);
         }

      }
   }

   private void m_340483_(C_507_ entityIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, C_507_ leashHolderIn) {
      if (!Config.isShaders() || !Shaders.isShadowPass) {
         matrixStackIn.m_85836_();
         C_3046_ vec3 = leashHolderIn.m_7398_(partialTicks);
         double d0 = (double)(entityIn.m_339180_(partialTicks) * 0.017453292F) + 1.5707963267948966;
         C_3046_ vec31 = entityIn.m_245894_(partialTicks);
         if (this.leashOffsetX != 0.0F || this.leashOffsetY != 0.0F || this.leashOffsetZ != 0.0F) {
            vec31 = new C_3046_((double)this.leashOffsetX, (double)this.leashOffsetY, (double)this.leashOffsetZ);
         }

         double d1 = Math.cos(d0) * vec31.f_82481_ + Math.sin(d0) * vec31.f_82479_;
         double d2 = Math.sin(d0) * vec31.f_82481_ - Math.cos(d0) * vec31.f_82479_;
         double d3 = C_188_.m_14139_((double)partialTicks, entityIn.f_19854_, entityIn.m_20185_()) + d1;
         double d4 = C_188_.m_14139_((double)partialTicks, entityIn.f_19855_, entityIn.m_20186_()) + vec31.f_82480_;
         double d5 = C_188_.m_14139_((double)partialTicks, entityIn.f_19856_, entityIn.m_20189_()) + d2;
         matrixStackIn.m_85837_(d1, vec31.f_82480_, d2);
         float f = (float)(vec3.f_82479_ - d3);
         float f1 = (float)(vec3.f_82480_ - d4);
         float f2 = (float)(vec3.f_82481_ - d5);
         float f3 = 0.025F;
         C_3187_ vertexconsumer = bufferIn.m_6299_(C_4168_.m_110475_());
         Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
         float f4 = C_188_.m_264536_(f * f + f2 * f2) * 0.025F / 2.0F;
         float f5 = f2 * f4;
         float f6 = f * f4;
         C_4675_ blockpos = C_4675_.m_274446_(entityIn.m_20299_(partialTicks));
         C_4675_ blockpos1 = C_4675_.m_274446_(leashHolderIn.m_20299_(partialTicks));
         int i = this.m_6086_(entityIn, blockpos);
         int j = this.f_114476_.m_114382_(leashHolderIn).m_6086_(leashHolderIn, blockpos1);
         int k = entityIn.m_9236_().a(C_1607_.SKY, blockpos);
         int l = entityIn.m_9236_().a(C_1607_.SKY, blockpos1);
         if (Config.isShaders()) {
            Shaders.beginLeash();
         }

         int j1;
         for(j1 = 0; j1 <= 24; ++j1) {
            m_340096_(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, j1, false);
         }

         for(j1 = 24; j1 >= 0; --j1) {
            m_340096_(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
         }

         if (Config.isShaders()) {
            Shaders.endLeash();
         }

         matrixStackIn.m_85849_();
      }
   }

   private static void m_340096_(C_3187_ bufferIn, Matrix4f matrixIn, float x, float y, float z, int lightEntityIn, int lightHolderIn, int lightSkyEntityIn, int lightSkyHolderIn, float p_340096_9_, float p_340096_10_, float p_340096_11_, float p_340096_12_, int p_340096_13_, boolean p_340096_14_) {
      float f = (float)p_340096_13_ / 24.0F;
      int i = (int)C_188_.m_14179_(f, (float)lightEntityIn, (float)lightHolderIn);
      int j = (int)C_188_.m_14179_(f, (float)lightSkyEntityIn, (float)lightSkyHolderIn);
      int k = C_4138_.m_109885_(i, j);
      float f1 = p_340096_13_ % 2 == (p_340096_14_ ? 1 : 0) ? 0.7F : 1.0F;
      float f2 = 0.5F * f1;
      float f3 = 0.4F * f1;
      float f4 = 0.3F * f1;
      float f5 = x * f;
      float f6 = y > 0.0F ? y * f * f : y - y * (1.0F - f) * (1.0F - f);
      float f7 = z * f;
      bufferIn.m_339083_(matrixIn, f5 - p_340096_11_, f6 + p_340096_10_, f7 + p_340096_12_).m_340057_(f2, f3, f4, 1.0F).m_338973_(k);
      bufferIn.m_339083_(matrixIn, f5 + p_340096_11_, f6 + p_340096_9_ - p_340096_10_, f7 - p_340096_12_).m_340057_(f2, f3, f4, 1.0F).m_338973_(k);
   }

   protected boolean m_6512_(C_507_ entity) {
      return entity.m_6052_() || entity.m_8077_() && entity == this.f_114476_.f_114359_;
   }

   public abstract C_5265_ m_5478_(C_507_ var1);

   public C_3429_ m_114481_() {
      return this.f_174005_;
   }

   protected void m_7649_(C_507_ entityIn, C_4996_ displayNameIn, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, float partialTicks) {
      double d0 = this.f_114476_.m_114471_(entityIn);
      boolean isNameplateInRenderDistance = !(d0 > 4096.0);
      if (Reflector.ForgeHooksClient_isNameplateInRenderDistance.exists()) {
         isNameplateInRenderDistance = Reflector.ForgeHooksClient_isNameplateInRenderDistance.callBoolean(entityIn, d0);
      }

      if (isNameplateInRenderDistance) {
         C_3046_ vec3 = entityIn.m_319864_().m_318717_(C_313489_.NAME_TAG, 0, entityIn.m_5675_(partialTicks));
         if (vec3 != null) {
            boolean flag = !entityIn.m_20163_();
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.m_85836_();
            matrixStackIn.m_85837_(vec3.f_82479_, vec3.f_82480_ + 0.5, vec3.f_82481_);
            matrixStackIn.m_252781_(this.f_114476_.m_253208_());
            matrixStackIn.m_85841_(0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.m_85850_().m_252922_();
            float f = C_3391_.m_91087_().f_91066_.m_92141_(0.25F);
            int j = (int)(f * 255.0F) << 24;
            C_3429_ font = this.m_114481_();
            float f1 = (float)(-font.m_92852_(displayNameIn) / 2);
            font.m_272077_(displayNameIn, f1, (float)i, 553648127, false, matrix4f, bufferIn, flag ? C_3429_.C_180532_.SEE_THROUGH : C_3429_.C_180532_.NORMAL, j, packedLightIn);
            if (flag) {
               font.m_272077_(displayNameIn, f1, (float)i, -1, false, matrix4f, bufferIn, C_3429_.C_180532_.NORMAL, 0, packedLightIn);
            }

            matrixStackIn.m_85849_();
         }
      }

   }

   protected float m_318622_(C_507_ entityIn) {
      return this.f_114477_;
   }

   public Either getType() {
      return this.entityType == null ? null : Either.makeLeft(this.entityType);
   }

   public void setType(Either type) {
      this.entityType = (C_513_)type.getLeft().get();
   }

   public C_5265_ getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   public void setLocationTextureCustom(C_5265_ locationTextureCustom) {
      this.locationTextureCustom = locationTextureCustom;
   }
}
