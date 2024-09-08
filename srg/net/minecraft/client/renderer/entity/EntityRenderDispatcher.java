package net.minecraft.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.optifine.Config;
import net.optifine.DynamicLights;
import net.optifine.EmissiveTextures;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.player.PlayerItemsLayer;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class EntityRenderDispatcher implements ResourceManagerReloadListener {
   private static final RenderType f_114361_ = RenderType.m_110485_(ResourceLocation.m_340282_("textures/misc/shadow.png"));
   private static final float f_276493_ = 32.0F;
   private static final float f_276586_ = 0.5F;
   private Map<EntityType<?>, EntityRenderer<?>> f_114362_ = ImmutableMap.of();
   private Map<PlayerSkin.Model, EntityRenderer<? extends Player>> f_114363_ = Map.of();
   public final TextureManager f_114357_;
   private Level f_114366_;
   public Camera f_114358_;
   private Quaternionf f_114367_;
   public Entity f_114359_;
   private final ItemRenderer f_173995_;
   private final BlockRenderDispatcher f_234576_;
   private final ItemInHandRenderer f_234577_;
   private final Font f_114365_;
   public final Options f_114360_;
   private final EntityModelSet f_173996_;
   private boolean f_114368_ = true;
   private boolean f_114369_;
   private EntityRenderer entityRenderer = null;
   private Entity renderedEntity = null;
   private Context context = null;

   public <E extends Entity> int m_114394_(E entityIn, float partialTicks) {
      int combinedLight = this.m_114382_(entityIn).m_114505_(entityIn, partialTicks);
      if (Config.isDynamicLights()) {
         combinedLight = DynamicLights.getCombinedLight(entityIn, combinedLight);
      }

      return combinedLight;
   }

   public EntityRenderDispatcher(
      Minecraft mcIn,
      TextureManager textureManagerIn,
      ItemRenderer itemRendererIn,
      BlockRenderDispatcher blockRenderDispatcherIn,
      Font textRendererIn,
      Options optionsIn,
      EntityModelSet entityModelsIn
   ) {
      this.f_114357_ = textureManagerIn;
      this.f_173995_ = itemRendererIn;
      this.f_234577_ = new ItemInHandRenderer(mcIn, this, itemRendererIn);
      this.f_234576_ = blockRenderDispatcherIn;
      this.f_114365_ = textRendererIn;
      this.f_114360_ = optionsIn;
      this.f_173996_ = entityModelsIn;
   }

   public <T extends Entity> EntityRenderer<? super T> m_114382_(T entityIn) {
      if (entityIn instanceof AbstractClientPlayer abstractclientplayer) {
         PlayerSkin.Model playerskin$model = abstractclientplayer.m_294544_().f_290793_();
         EntityRenderer<? extends Player> entityrenderer = (EntityRenderer<? extends Player>)this.f_114363_.get(playerskin$model);
         return (EntityRenderer<? super T>)(entityrenderer != null ? entityrenderer : (EntityRenderer)this.f_114363_.get(PlayerSkin.Model.WIDE));
      } else {
         return (EntityRenderer<? super T>)this.f_114362_.get(entityIn.m_6095_());
      }
   }

   public void m_114408_(Level worldIn, Camera activeRenderInfoIn, Entity entityIn) {
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

   public <E extends Entity> boolean m_114397_(E entityIn, Frustum frustumIn, double camX, double camY, double camZ) {
      EntityRenderer<? super E> entityrenderer = this.m_114382_(entityIn);
      return entityrenderer.m_5523_(entityIn, frustumIn, camX, camY, camZ);
   }

   public <E extends Entity> void m_114384_(
      E entityIn,
      double xIn,
      double yIn,
      double zIn,
      float rotationYawIn,
      float partialTicks,
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn
   ) {
      if (this.f_114358_ != null) {
         EntityRenderer<? super E> entityrenderer = this.m_114382_(entityIn);

         try {
            Vec3 vec3 = entityrenderer.m_7860_(entityIn, partialTicks);
            double d2 = xIn + vec3.m_7096_();
            double d3 = yIn + vec3.m_7098_();
            double d0 = zIn + vec3.m_7094_();
            matrixStackIn.m_85836_();
            matrixStackIn.m_85837_(d2, d3, d0);
            EntityRenderer entityRendererPrev = this.entityRenderer;
            Entity renderedEntityPrev = this.renderedEntity;
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
                  entityrenderer.m_7392_(entityIn, rotationYawIn, partialTicks, matrixStackIn, bufferIn, LightTexture.MAX_BRIGHTNESS);
                  EmissiveTextures.endRenderEmissive();
               }

               EmissiveTextures.endRender();
            }

            this.entityRenderer = entityRendererPrev;
            this.renderedEntity = renderedEntityPrev;
            if (entityIn.m_6051_()) {
               this.m_114453_(matrixStackIn, bufferIn, entityIn, Mth.m_305706_(Mth.f_303648_, this.f_114367_, new Quaternionf()));
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

            if (this.f_114369_ && !entityIn.m_20145_() && !Minecraft.m_91087_().m_91299_()) {
               m_114441_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110504_()), entityIn, partialTicks, 1.0F, 1.0F, 1.0F);
            }

            matrixStackIn.m_85849_();
         } catch (Throwable var28) {
            CrashReport crashreport = CrashReport.m_127521_(var28, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Entity being rendered");
            entityIn.m_7976_(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.m_127514_("Renderer details");
            crashreportcategory1.m_128159_("Assigned renderer", entityrenderer);
            crashreportcategory1.m_128159_("Location", CrashReportCategory.m_178937_(this.f_114366_, xIn, yIn, zIn));
            crashreportcategory1.m_128159_("Rotation", rotationYawIn);
            crashreportcategory1.m_128159_("Delta", partialTicks);
            throw new ReportedException(crashreport);
         }
      }
   }

   private static void m_338553_(PoseStack matrixStackIn, Entity entityIn, MultiBufferSource bufferIn) {
      Entity entity = m_338971_(entityIn);
      if (entity == null) {
         DebugRenderer.m_269271_(matrixStackIn, bufferIn, "Missing", entityIn.m_20185_(), entityIn.m_20191_().f_82292_ + 1.5, entityIn.m_20189_(), -65536);
      } else {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(entity.m_20185_() - entityIn.m_20185_(), entity.m_20186_() - entityIn.m_20186_(), entity.m_20189_() - entityIn.m_20189_());
         m_114441_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110504_()), entity, 1.0F, 0.0F, 1.0F, 0.0F);
         m_339861_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110504_()), new Vector3f(), entity.m_20184_(), -256);
         matrixStackIn.m_85849_();
      }
   }

   @Nullable
   private static Entity m_338971_(Entity entityIn) {
      IntegratedServer integratedserver = Minecraft.m_91087_().m_91092_();
      if (integratedserver != null) {
         ServerLevel serverlevel = integratedserver.m_129880_(entityIn.m_9236_().m_46472_());
         if (serverlevel != null) {
            return serverlevel.m_6815_(entityIn.m_19879_());
         }
      }

      return null;
   }

   private static void m_114441_(PoseStack matrixStackIn, VertexConsumer bufferIn, Entity entityIn, float partialTicks, float red, float green, float blue) {
      if (!Shaders.isShadowPass) {
         AABB aabb = entityIn.m_20191_().m_82386_(-entityIn.m_20185_(), -entityIn.m_20186_(), -entityIn.m_20189_());
         LevelRenderer.m_109646_(matrixStackIn, bufferIn, aabb, red, green, blue, 1.0F);
         boolean multipart = entityIn instanceof EnderDragon;
         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            multipart = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
         }

         if (multipart) {
            double d0 = -Mth.m_14139_((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
            double d1 = -Mth.m_14139_((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
            double d2 = -Mth.m_14139_((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
            Entity[] parts = (Entity[])(Reflector.IForgeEntity_getParts.exists()
               ? (Entity[])Reflector.call(entityIn, Reflector.IForgeEntity_getParts)
               : ((EnderDragon)entityIn).m_31156_());

            for (Entity enderdragonpart : parts) {
               matrixStackIn.m_85836_();
               double d3 = d0 + Mth.m_14139_((double)partialTicks, enderdragonpart.f_19790_, enderdragonpart.m_20185_());
               double d4 = d1 + Mth.m_14139_((double)partialTicks, enderdragonpart.f_19791_, enderdragonpart.m_20186_());
               double d5 = d2 + Mth.m_14139_((double)partialTicks, enderdragonpart.f_19792_, enderdragonpart.m_20189_());
               matrixStackIn.m_85837_(d3, d4, d5);
               LevelRenderer.m_109646_(
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

         if (entityIn instanceof LivingEntity) {
            float f1 = 0.01F;
            LevelRenderer.m_109608_(
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

         Entity entity = entityIn.m_20202_();
         if (entity != null) {
            float f = Math.min(entity.m_20205_(), entityIn.m_20205_()) / 2.0F;
            float f2 = 0.0625F;
            Vec3 vec3 = entity.m_292590_(entityIn).m_82546_(entityIn.m_20182_());
            LevelRenderer.m_109608_(
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

   private static void m_339861_(PoseStack matrixStackIn, VertexConsumer bufferIn, Vector3f vecEyeIn, Vec3 vecLookIn, int colorIn) {
      PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();
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

   private void m_114453_(PoseStack matrixStackIn, MultiBufferSource bufferIn, Entity entityIn, Quaternionf rotIn) {
      TextureAtlasSprite textureatlassprite = ModelBakery.f_119219_.m_119204_();
      TextureAtlasSprite textureatlassprite1 = ModelBakery.f_119220_.m_119204_();
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
      VertexConsumer vertexconsumer = bufferIn.m_6299_(Sheets.m_110790_());

      for (PoseStack.Pose posestack$pose = matrixStackIn.m_85850_(); f3 > 0.0F; i++) {
         TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
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

   private static void m_114414_(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, float x, float y, float z, float texU, float texV) {
      bufferIn.m_338370_(matrixEntryIn, x, y, z).m_338399_(-1).m_167083_(texU, texV).m_338369_(0, 10).m_338973_(240).m_339200_(matrixEntryIn, 0.0F, 1.0F, 0.0F);
   }

   private static void m_114457_(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, Entity entityIn, float weightIn, float partialTicks, LevelReader worldIn, float sizeIn
   ) {
      if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
         double d0 = Mth.m_14139_((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
         double d1 = Mth.m_14139_((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
         double d2 = Mth.m_14139_((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
         float f = Math.min(weightIn / 0.5F, sizeIn);
         int i = Mth.m_14107_(d0 - (double)sizeIn);
         int j = Mth.m_14107_(d0 + (double)sizeIn);
         int k = Mth.m_14107_(d1 - (double)f);
         int l = Mth.m_14107_(d1);
         int i1 = Mth.m_14107_(d2 - (double)sizeIn);
         int j1 = Mth.m_14107_(d2 + (double)sizeIn);
         PoseStack.Pose posestack$pose = matrixStackIn.m_85850_();
         VertexConsumer vertexconsumer = bufferIn.m_6299_(f_114361_);
         MutableBlockPos blockpos$mutableblockpos = new MutableBlockPos();

         for (int k1 = i1; k1 <= j1; k1++) {
            for (int l1 = i; l1 <= j; l1++) {
               blockpos$mutableblockpos.m_122178_(l1, 0, k1);
               ChunkAccess chunkaccess = worldIn.m_46865_(blockpos$mutableblockpos);

               for (int i2 = k; i2 <= l; i2++) {
                  blockpos$mutableblockpos.m_142448_(i2);
                  float f1 = weightIn - (float)(d1 - (double)blockpos$mutableblockpos.m_123342_()) * 0.5F;
                  m_277056_(posestack$pose, vertexconsumer, chunkaccess, worldIn, blockpos$mutableblockpos, d0, d1, d2, sizeIn, f1);
               }
            }
         }
      }
   }

   private static void m_277056_(
      PoseStack.Pose matrixEntryIn,
      VertexConsumer bufferIn,
      ChunkAccess chunkAccessIn,
      LevelReader worldIn,
      BlockPos blockPosIn,
      double xIn,
      double yIn,
      double zIn,
      float sizeIn,
      float weightIn
   ) {
      BlockPos blockpos = blockPosIn.m_7495_();
      BlockState blockstate = chunkAccessIn.m_8055_(blockpos);
      if (blockstate.m_60799_() != RenderShape.INVISIBLE && worldIn.m_46803_(blockPosIn) > 3 && blockstate.m_60838_(chunkAccessIn, blockpos)) {
         VoxelShape voxelshape = blockstate.m_60808_(chunkAccessIn, blockpos);
         if (!voxelshape.m_83281_()) {
            float f = LightTexture.m_234316_(worldIn.m_6042_(), worldIn.m_46803_(blockPosIn));
            float f1 = weightIn * 0.5F * f;
            if (f1 >= 0.0F) {
               if (f1 > 1.0F) {
                  f1 = 1.0F;
               }

               int i = ARGB32.m_13660_(Mth.m_14143_(f1 * 255.0F), 255, 255, 255);
               AABB aabb = voxelshape.m_83215_();
               double d0 = (double)blockPosIn.m_123341_() + aabb.f_82288_;
               double d1 = (double)blockPosIn.m_123341_() + aabb.f_82291_;
               double d2 = (double)blockPosIn.m_123342_() + aabb.f_82289_;
               double d3 = (double)blockPosIn.m_123343_() + aabb.f_82290_;
               double d4 = (double)blockPosIn.m_123343_() + aabb.f_82293_;
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

   private static void m_114422_(PoseStack.Pose matrixEntryIn, VertexConsumer bufferIn, int colorIn, float xIn, float yIn, float zIn, float texU, float texV) {
      Vector3f vector3f = matrixEntryIn.m_252922_().transformPosition(xIn, yIn, zIn, new Vector3f());
      bufferIn.m_338367_(vector3f.x(), vector3f.y(), vector3f.z(), colorIn, texU, texV, OverlayTexture.f_118083_, 15728880, 0.0F, 1.0F, 0.0F);
   }

   public void m_114406_(@Nullable Level worldIn) {
      this.f_114366_ = worldIn;
      if (worldIn == null) {
         this.f_114358_ = null;
      }
   }

   public double m_114471_(Entity entityIn) {
      return this.f_114358_.m_90583_().m_82557_(entityIn.m_20182_());
   }

   public double m_114378_(double x, double y, double z) {
      return this.f_114358_.m_90583_().m_82531_(x, y, z);
   }

   public Quaternionf m_253208_() {
      return this.f_114367_;
   }

   public ItemInHandRenderer m_234586_() {
      return this.f_234577_;
   }

   public void m_6213_(ResourceManager resourceManager) {
      Context entityrendererprovider$context = new Context(
         this, this.f_173995_, this.f_234576_, this.f_234577_, resourceManager, this.f_173996_, this.f_114365_
      );
      this.context = entityrendererprovider$context;
      this.f_114362_ = EntityRenderers.m_174049_(entityrendererprovider$context);
      this.f_114363_ = EntityRenderers.m_174051_(entityrendererprovider$context);
      registerPlayerItems(this.f_114363_);
      if (Reflector.ForgeEventFactoryClient_gatherLayers.exists()) {
         Reflector.ForgeEventFactoryClient_gatherLayers.call(this.f_114362_, this.f_114363_, entityrendererprovider$context);
      }
   }

   private static void registerPlayerItems(Map<String, EntityRenderer> renderPlayerMap) {
      boolean registered = false;

      for (EntityRenderer renderer : renderPlayerMap.values()) {
         if (renderer instanceof PlayerRenderer renderPlayer) {
            renderPlayer.removeLayers(PlayerItemsLayer.class);
            renderPlayer.m_115326_(new PlayerItemsLayer(renderPlayer));
            registered = true;
         }
      }

      if (!registered) {
         Config.warn("PlayerItemsLayer not registered");
      }
   }

   public Map<EntityType, EntityRenderer> getEntityRenderMap() {
      if (this.f_114362_ instanceof ImmutableMap) {
         this.f_114362_ = new HashMap(this.f_114362_);
      }

      return this.f_114362_;
   }

   public Context getContext() {
      return this.context;
   }

   public Entity getRenderedEntity() {
      return this.renderedEntity;
   }

   public EntityRenderer getEntityRenderer() {
      return this.entityRenderer;
   }

   public void setRenderedEntity(Entity renderedEntity) {
      this.renderedEntity = renderedEntity;
   }

   public Map<PlayerSkin.Model, EntityRenderer> getSkinMap() {
      return Collections.unmodifiableMap(this.f_114363_);
   }
}
