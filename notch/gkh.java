package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_141742_.C_141743_;
import net.minecraft.src.C_174_.C_175_;
import net.minecraft.src.C_4675_.C_4681_;
import net.optifine.Config;
import net.optifine.DynamicLights;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.player.PlayerItemsLayer;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class C_4330_ implements C_79_ {
   private static final C_4168_ f_114361_ = C_4168_.m_110485_(C_5265_.m_340282_("textures/misc/shadow.png"));
   private static final float f_276493_ = 32.0F;
   private static final float f_276586_ = 0.5F;
   private Map<C_513_<?>, C_4331_<?>> f_114362_ = ImmutableMap.of();
   private Map<C_290287_.C_290238_, C_4331_<? extends C_1141_>> f_114363_ = Map.of();
   public final C_4490_ f_114357_;
   private C_1596_ f_114366_;
   public C_3373_ f_114358_;
   private Quaternionf f_114367_;
   public C_507_ f_114359_;
   private final C_4354_ f_173995_;
   private final C_4183_ f_234576_;
   private final C_4131_ f_234577_;
   private final C_3429_ f_114365_;
   public final C_3401_ f_114360_;
   private final C_141653_ f_173996_;
   private boolean f_114368_ = true;
   private boolean f_114369_;
   private C_4331_ entityRenderer = null;
   private C_507_ renderedEntity = null;
   private C_141743_ context = null;

   public <E extends C_507_> int m_114394_(E entityIn, float partialTicks) {
      int combinedLight = this.m_114382_(entityIn).m_114505_(entityIn, partialTicks);
      if (Config.isDynamicLights()) {
         combinedLight = DynamicLights.getCombinedLight(entityIn, combinedLight);
      }

      return combinedLight;
   }

   public C_4330_(
      C_3391_ mcIn,
      C_4490_ textureManagerIn,
      C_4354_ itemRendererIn,
      C_4183_ blockRenderDispatcherIn,
      C_3429_ textRendererIn,
      C_3401_ optionsIn,
      C_141653_ entityModelsIn
   ) {
      this.f_114357_ = textureManagerIn;
      this.f_173995_ = itemRendererIn;
      this.f_234577_ = new C_4131_(mcIn, this, itemRendererIn);
      this.f_234576_ = blockRenderDispatcherIn;
      this.f_114365_ = textRendererIn;
      this.f_114360_ = optionsIn;
      this.f_173996_ = entityModelsIn;
   }

   public <T extends C_507_> C_4331_<? super T> m_114382_(T entityIn) {
      if (entityIn instanceof C_4102_ abstractclientplayer) {
         C_290287_.C_290238_ playerskin$model = abstractclientplayer.m_294544_().f_290793_();
         C_4331_<? extends C_1141_> entityrenderer = (C_4331_<? extends C_1141_>)this.f_114363_.get(playerskin$model);
         return (C_4331_<? super T>)(entityrenderer != null ? entityrenderer : (C_4331_)this.f_114363_.get(C_290287_.C_290238_.WIDE));
      } else {
         return (C_4331_<? super T>)this.f_114362_.get(entityIn.m_6095_());
      }
   }

   public void m_114408_(C_1596_ worldIn, C_3373_ activeRenderInfoIn, C_507_ entityIn) {
      this.f_114366_ = worldIn;
      this.f_114358_ = activeRenderInfoIn;
      this.f_114367_ = activeRenderInfoIn.m_253121_();
      this.f_114359_ = entityIn;
   }

   public void m_252923_(Quaternionf quaternionIn) {
      this.f_114367_ = quaternionIn;
   }

   public void m_114468_(boolean renderShadowIn) {
      this.f_114368_ = renderShadowIn;
   }

   public void m_114473_(boolean debugBoundingBoxIn) {
      this.f_114369_ = debugBoundingBoxIn;
   }

   public boolean m_114377_() {
      return this.f_114369_;
   }

   public <E extends C_507_> boolean m_114397_(E entityIn, C_4273_ frustumIn, double camX, double camY, double camZ) {
      C_4331_<? super E> entityrenderer = this.m_114382_(entityIn);
      return entityrenderer.m_5523_(entityIn, frustumIn, camX, camY, camZ);
   }

   public <E extends C_507_> void m_114384_(
      E entityIn, double xIn, double yIn, double zIn, float rotationYawIn, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn
   ) {
      if (this.f_114358_ != null) {
         C_4331_<? super E> entityrenderer = this.m_114382_(entityIn);

         try {
            C_3046_ vec3 = entityrenderer.m_7860_(entityIn, partialTicks);
            double d2 = xIn + vec3.m_7096_();
            double d3 = yIn + vec3.m_7098_();
            double d0 = zIn + vec3.m_7094_();
            matrixStackIn.m_85836_();
            matrixStackIn.m_85837_(d2, d3, d0);
            C_4331_ entityRendererPrev = this.entityRenderer;
            C_507_ renderedEntityPrev = this.renderedEntity;
            entityrenderer = CustomEntityModels.getEntityRenderer(entityIn, entityrenderer);
            this.entityRenderer = entityrenderer;
            this.renderedEntity = entityIn;
            if (EmissiveTextures.isActive()) {
               EmissiveTextures.beginRender();
            }

            entityrenderer.m_7392_(entityIn, rotationYawIn, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            if (EmissiveTextures.isActive()) {
               if (EmissiveTextures.hasEmissive()) {
                  EmissiveTextures.beginRenderEmissive();
                  entityrenderer.m_7392_(entityIn, rotationYawIn, partialTicks, matrixStackIn, bufferIn, C_4138_.MAX_BRIGHTNESS);
                  EmissiveTextures.endRenderEmissive();
               }

               EmissiveTextures.endRender();
            }

            this.entityRenderer = entityRendererPrev;
            this.renderedEntity = renderedEntityPrev;
            if (entityIn.m_6051_()) {
               this.m_114453_(matrixStackIn, bufferIn, entityIn, C_188_.m_305706_(C_188_.f_303648_, this.f_114367_, new Quaternionf()));
            }

            matrixStackIn.m_85837_(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
            if (this.f_114360_.m_231818_().m_231551_() && this.f_114368_ && !entityIn.m_20145_()) {
               float f = entityrenderer.m_318622_(entityIn);
               if (f > 0.0F) {
                  boolean shadowOffset = CustomEntityModels.isActive() && entityrenderer.shadowOffsetX != 0.0F && entityrenderer.shadowOffsetZ != 0.0F;
                  if (shadowOffset) {
                     matrixStackIn.m_252880_(entityrenderer.shadowOffsetX, 0.0F, entityrenderer.shadowOffsetZ);
                  }

                  double d1 = this.m_114378_(entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_());
                  float f1 = (float)((1.0 - d1 / 256.0) * (double)entityrenderer.f_114478_);
                  if (f1 > 0.0F) {
                     m_114457_(matrixStackIn, bufferIn, entityIn, f1, partialTicks, this.f_114366_, Math.min(f, 32.0F));
                  }

                  if (shadowOffset) {
                     matrixStackIn.m_252880_(-entityrenderer.shadowOffsetX, 0.0F, -entityrenderer.shadowOffsetZ);
                  }
               }
            }

            if (this.f_114369_ && !entityIn.m_20145_() && !C_3391_.m_91087_().m_91299_()) {
               m_114441_(matrixStackIn, bufferIn.m_6299_(C_4168_.m_110504_()), entityIn, partialTicks, 1.0F, 1.0F, 1.0F);
            }

            matrixStackIn.m_85849_();
         } catch (Throwable var28) {
            C_4883_ crashreport = C_4883_.m_127521_(var28, "Rendering entity in world");
            C_4909_ crashreportcategory = crashreport.m_127514_("Entity being rendered");
            entityIn.m_7976_(crashreportcategory);
            C_4909_ crashreportcategory1 = crashreport.m_127514_("Renderer details");
            crashreportcategory1.m_128159_("Assigned renderer", entityrenderer);
            crashreportcategory1.m_128159_("Location", C_4909_.m_178937_(this.f_114366_, xIn, yIn, zIn));
            crashreportcategory1.m_128159_("Rotation", rotationYawIn);
            crashreportcategory1.m_128159_("Delta", partialTicks);
            throw new C_5204_(crashreport);
         }
      }
   }

   private static void m_338553_(C_3181_ matrixStackIn, C_507_ entityIn, C_4139_ bufferIn) {
      C_507_ entity = m_338971_(entityIn);
      if (entity == null) {
         C_4287_.m_269271_(matrixStackIn, bufferIn, "Missing", entityIn.m_20185_(), entityIn.m_20191_().f_82292_ + 1.5, entityIn.m_20189_(), -65536);
      } else {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(entity.m_20185_() - entityIn.m_20185_(), entity.m_20186_() - entityIn.m_20186_(), entity.m_20189_() - entityIn.m_20189_());
         m_114441_(matrixStackIn, bufferIn.m_6299_(C_4168_.m_110504_()), entity, 1.0F, 0.0F, 1.0F, 0.0F);
         m_339861_(matrixStackIn, bufferIn.m_6299_(C_4168_.m_110504_()), new Vector3f(), entity.m_20184_(), -256);
         matrixStackIn.m_85849_();
      }
   }

   @Nullable
   private static C_507_ m_338971_(C_507_ entityIn) {
      C_4585_ integratedserver = C_3391_.m_91087_().m_91092_();
      if (integratedserver != null) {
         C_12_ serverlevel = integratedserver.m_129880_(entityIn.m_9236_().m_46472_());
         if (serverlevel != null) {
            return serverlevel.m_6815_(entityIn.m_19879_());
         }
      }

      return null;
   }

   private static void m_114441_(C_3181_ matrixStackIn, C_3187_ bufferIn, C_507_ entityIn, float partialTicks, float red, float green, float blue) {
      if (!Shaders.isShadowPass) {
         C_3040_ aabb = entityIn.m_20191_().m_82386_(-entityIn.m_20185_(), -entityIn.m_20186_(), -entityIn.m_20189_());
         C_4134_.m_109646_(matrixStackIn, bufferIn, aabb, red, green, blue, 1.0F);
         boolean multipart = entityIn instanceof C_945_;
         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            multipart = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
         }

         if (multipart) {
            double d0 = -C_188_.m_14139_((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
            double d1 = -C_188_.m_14139_((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
            double d2 = -C_188_.m_14139_((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
            C_507_[] parts = (C_507_[])(Reflector.IForgeEntity_getParts.exists()
               ? (C_507_[])Reflector.call(entityIn, Reflector.IForgeEntity_getParts)
               : ((C_945_)entityIn).m_31156_());

            for (C_507_ enderdragonpart : parts) {
               matrixStackIn.m_85836_();
               double d3 = d0 + C_188_.m_14139_((double)partialTicks, enderdragonpart.f_19790_, enderdragonpart.m_20185_());
               double d4 = d1 + C_188_.m_14139_((double)partialTicks, enderdragonpart.f_19791_, enderdragonpart.m_20186_());
               double d5 = d2 + C_188_.m_14139_((double)partialTicks, enderdragonpart.f_19792_, enderdragonpart.m_20189_());
               matrixStackIn.m_85837_(d3, d4, d5);
               C_4134_.m_109646_(
                  matrixStackIn,
                  bufferIn,
                  enderdragonpart.m_20191_().m_82386_(-enderdragonpart.m_20185_(), -enderdragonpart.m_20186_(), -enderdragonpart.m_20189_()),
                  0.25F,
                  1.0F,
                  0.0F,
                  1.0F
               );
               matrixStackIn.m_85849_();
            }
         }

         if (entityIn instanceof C_524_) {
            float f1 = 0.01F;
            C_4134_.m_109608_(
               matrixStackIn,
               bufferIn,
               aabb.f_82288_,
               (double)(entityIn.m_20192_() - 0.01F),
               aabb.f_82290_,
               aabb.f_82291_,
               (double)(entityIn.m_20192_() + 0.01F),
               aabb.f_82293_,
               1.0F,
               0.0F,
               0.0F,
               1.0F
            );
         }

         C_507_ entity = entityIn.m_20202_();
         if (entity != null) {
            float f = Math.min(entity.m_20205_(), entityIn.m_20205_()) / 2.0F;
            float f2 = 0.0625F;
            C_3046_ vec3 = entity.m_292590_(entityIn).m_82546_(entityIn.m_20182_());
            C_4134_.m_109608_(
               matrixStackIn,
               bufferIn,
               vec3.f_82479_ - (double)f,
               vec3.f_82480_,
               vec3.f_82481_ - (double)f,
               vec3.f_82479_ + (double)f,
               vec3.f_82480_ + 0.0625,
               vec3.f_82481_ + (double)f,
               1.0F,
               1.0F,
               0.0F,
               1.0F
            );
         }

         m_339861_(matrixStackIn, bufferIn, new Vector3f(0.0F, entityIn.m_20192_(), 0.0F), entityIn.m_20252_(partialTicks).m_82490_(2.0), -16776961);
      }
   }

   private static void m_339861_(C_3181_ matrixStackIn, C_3187_ bufferIn, Vector3f vecEyeIn, C_3046_ vecLookIn, int colorIn) {
      C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
      bufferIn.m_340301_(posestack$pose, vecEyeIn)
         .m_338399_(colorIn)
         .m_339200_(posestack$pose, (float)vecLookIn.f_82479_, (float)vecLookIn.f_82480_, (float)vecLookIn.f_82481_);
      bufferIn.m_338370_(
            posestack$pose,
            (float)((double)vecEyeIn.x() + vecLookIn.f_82479_),
            (float)((double)vecEyeIn.y() + vecLookIn.f_82480_),
            (float)((double)vecEyeIn.z() + vecLookIn.f_82481_)
         )
         .m_338399_(colorIn)
         .m_339200_(posestack$pose, (float)vecLookIn.f_82479_, (float)vecLookIn.f_82480_, (float)vecLookIn.f_82481_);
   }

   private void m_114453_(C_3181_ matrixStackIn, C_4139_ bufferIn, C_507_ entityIn, Quaternionf rotIn) {
      C_4486_ textureatlassprite = C_4532_.f_119219_.m_119204_();
      C_4486_ textureatlassprite1 = C_4532_.f_119220_.m_119204_();
      matrixStackIn.m_85836_();
      float f = entityIn.m_20205_() * 1.4F;
      matrixStackIn.m_85841_(f, f, f);
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = entityIn.m_20206_() / f;
      float f4 = 0.0F;
      matrixStackIn.m_252781_(rotIn);
      matrixStackIn.m_252880_(0.0F, 0.0F, 0.3F - (float)((int)f3) * 0.02F);
      float f5 = 0.0F;
      int i = 0;
      C_3187_ vertexconsumer = bufferIn.m_6299_(C_4177_.m_110790_());

      for (C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_(); f3 > 0.0F; i++) {
         C_4486_ textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
         vertexconsumer.setSprite(textureatlassprite2);
         float f6 = textureatlassprite2.m_118409_();
         float f7 = textureatlassprite2.m_118411_();
         float f8 = textureatlassprite2.m_118410_();
         float f9 = textureatlassprite2.m_118412_();
         if (i / 2 % 2 == 0) {
            float f10 = f8;
            f8 = f6;
            f6 = f10;
         }

         m_114414_(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f8, f9);
         m_114414_(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f6, f9);
         m_114414_(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f6, f7);
         m_114414_(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f8, f7);
         f3 -= 0.45F;
         f4 -= 0.45F;
         f1 *= 0.9F;
         f5 -= 0.03F;
      }

      matrixStackIn.m_85849_();
   }

   private static void m_114414_(C_3181_.C_3183_ matrixEntryIn, C_3187_ bufferIn, float x, float y, float z, float texU, float texV) {
      bufferIn.m_338370_(matrixEntryIn, x, y, z).m_338399_(-1).m_167083_(texU, texV).m_338369_(0, 10).m_338973_(240).m_339200_(matrixEntryIn, 0.0F, 1.0F, 0.0F);
   }

   private static void m_114457_(C_3181_ matrixStackIn, C_4139_ bufferIn, C_507_ entityIn, float weightIn, float partialTicks, C_1599_ worldIn, float sizeIn) {
      if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
         double d0 = C_188_.m_14139_((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
         double d1 = C_188_.m_14139_((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
         double d2 = C_188_.m_14139_((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
         float f = Math.min(weightIn / 0.5F, sizeIn);
         int i = C_188_.m_14107_(d0 - (double)sizeIn);
         int j = C_188_.m_14107_(d0 + (double)sizeIn);
         int k = C_188_.m_14107_(d1 - (double)f);
         int l = C_188_.m_14107_(d1);
         int i1 = C_188_.m_14107_(d2 - (double)sizeIn);
         int j1 = C_188_.m_14107_(d2 + (double)sizeIn);
         C_3181_.C_3183_ posestack$pose = matrixStackIn.m_85850_();
         C_3187_ vertexconsumer = bufferIn.m_6299_(f_114361_);
         C_4681_ blockpos$mutableblockpos = new C_4681_();

         for (int k1 = i1; k1 <= j1; k1++) {
            for (int l1 = i; l1 <= j; l1++) {
               blockpos$mutableblockpos.m_122178_(l1, 0, k1);
               C_2116_ chunkaccess = worldIn.m_46865_(blockpos$mutableblockpos);

               for (int i2 = k; i2 <= l; i2++) {
                  blockpos$mutableblockpos.m_142448_(i2);
                  float f1 = weightIn - (float)(d1 - (double)blockpos$mutableblockpos.v()) * 0.5F;
                  m_277056_(posestack$pose, vertexconsumer, chunkaccess, worldIn, blockpos$mutableblockpos, d0, d1, d2, sizeIn, f1);
               }
            }
         }
      }
   }

   private static void m_277056_(
      C_3181_.C_3183_ matrixEntryIn,
      C_3187_ bufferIn,
      C_2116_ chunkAccessIn,
      C_1599_ worldIn,
      C_4675_ blockPosIn,
      double xIn,
      double yIn,
      double zIn,
      float sizeIn,
      float weightIn
   ) {
      C_4675_ blockpos = blockPosIn.m_7495_();
      C_2064_ blockstate = chunkAccessIn.a_(blockpos);
      if (blockstate.m_60799_() != C_1879_.INVISIBLE && worldIn.m_46803_(blockPosIn) > 3 && blockstate.m_60838_(chunkAccessIn, blockpos)) {
         C_3072_ voxelshape = blockstate.m_60808_(chunkAccessIn, blockpos);
         if (!voxelshape.m_83281_()) {
            float f = C_4138_.m_234316_(worldIn.m_6042_(), worldIn.m_46803_(blockPosIn));
            float f1 = weightIn * 0.5F * f;
            if (f1 >= 0.0F) {
               if (f1 > 1.0F) {
                  f1 = 1.0F;
               }

               int i = C_175_.m_13660_(C_188_.m_14143_(f1 * 255.0F), 255, 255, 255);
               C_3040_ aabb = voxelshape.m_83215_();
               double d0 = (double)blockPosIn.u() + aabb.f_82288_;
               double d1 = (double)blockPosIn.u() + aabb.f_82291_;
               double d2 = (double)blockPosIn.v() + aabb.f_82289_;
               double d3 = (double)blockPosIn.w() + aabb.f_82290_;
               double d4 = (double)blockPosIn.w() + aabb.f_82293_;
               float f2 = (float)(d0 - xIn);
               float f3 = (float)(d1 - xIn);
               float f4 = (float)(d2 - yIn);
               float f5 = (float)(d3 - zIn);
               float f6 = (float)(d4 - zIn);
               float f7 = -f2 / 2.0F / sizeIn + 0.5F;
               float f8 = -f3 / 2.0F / sizeIn + 0.5F;
               float f9 = -f5 / 2.0F / sizeIn + 0.5F;
               float f10 = -f6 / 2.0F / sizeIn + 0.5F;
               m_114422_(matrixEntryIn, bufferIn, i, f2, f4, f5, f7, f9);
               m_114422_(matrixEntryIn, bufferIn, i, f2, f4, f6, f7, f10);
               m_114422_(matrixEntryIn, bufferIn, i, f3, f4, f6, f8, f10);
               m_114422_(matrixEntryIn, bufferIn, i, f3, f4, f5, f8, f9);
            }
         }
      }
   }

   private static void m_114422_(C_3181_.C_3183_ matrixEntryIn, C_3187_ bufferIn, int colorIn, float xIn, float yIn, float zIn, float texU, float texV) {
      Vector3f vector3f = matrixEntryIn.m_252922_().transformPosition(xIn, yIn, zIn, new Vector3f());
      bufferIn.m_338367_(vector3f.x(), vector3f.y(), vector3f.z(), colorIn, texU, texV, C_4474_.f_118083_, 15728880, 0.0F, 1.0F, 0.0F);
   }

   public void m_114406_(@Nullable C_1596_ worldIn) {
      this.f_114366_ = worldIn;
      if (worldIn == null) {
         this.f_114358_ = null;
      }
   }

   public double m_114471_(C_507_ entityIn) {
      return this.f_114358_.m_90583_().m_82557_(entityIn.m_20182_());
   }

   public double m_114378_(double x, double y, double z) {
      return this.f_114358_.m_90583_().m_82531_(x, y, z);
   }

   public Quaternionf m_253208_() {
      return this.f_114367_;
   }

   public C_4131_ m_234586_() {
      return this.f_234577_;
   }

   public void m_6213_(C_77_ resourceManager) {
      C_141743_ entityrendererprovider$context = new C_141743_(
         this, this.f_173995_, this.f_234576_, this.f_234577_, resourceManager, this.f_173996_, this.f_114365_
      );
      this.context = entityrendererprovider$context;
      this.f_114362_ = C_141744_.m_174049_(entityrendererprovider$context);
      this.f_114363_ = C_141744_.m_174051_(entityrendererprovider$context);
      registerPlayerItems(this.f_114363_);
      if (Reflector.ForgeEventFactoryClient_gatherLayers.exists()) {
         Reflector.ForgeEventFactoryClient_gatherLayers.call(this.f_114362_, this.f_114363_, entityrendererprovider$context);
      }
   }

   private static void registerPlayerItems(Map<String, C_4331_> renderPlayerMap) {
      boolean registered = false;

      for (C_4331_ renderer : renderPlayerMap.values()) {
         if (renderer instanceof C_4462_ renderPlayer) {
            renderPlayer.removeLayers(PlayerItemsLayer.class);
            renderPlayer.a(new PlayerItemsLayer(renderPlayer));
            registered = true;
         }
      }

      if (!registered) {
         Config.warn("PlayerItemsLayer not registered");
      }
   }

   public Map<C_513_, C_4331_> getEntityRenderMap() {
      if (this.f_114362_ instanceof ImmutableMap) {
         this.f_114362_ = new HashMap(this.f_114362_);
      }

      return this.f_114362_;
   }

   public C_141743_ getContext() {
      return this.context;
   }

   public C_507_ getRenderedEntity() {
      return this.renderedEntity;
   }

   public C_4331_ getEntityRenderer() {
      return this.entityRenderer;
   }

   public void setRenderedEntity(C_507_ renderedEntity) {
      this.renderedEntity = renderedEntity;
   }

   public Map<C_290287_.C_290238_, C_4331_> getSkinMap() {
      return Collections.unmodifiableMap(this.f_114363_);
   }
}
