import com.google.common.collect.ImmutableMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_12_;
import net.minecraft.src.C_141653_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1599_;
import net.minecraft.src.C_1879_;
import net.minecraft.src.C_2116_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_3072_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4287_;
import net.minecraft.src.C_4462_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_79_;
import net.minecraft.src.C_945_;
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

public class EntityRenderDispatcher implements C_79_ {
   private static final RenderType e = RenderType.n(ResourceLocation.b("textures/misc/shadow.png"));
   private static final float f = 32.0F;
   private static final float g = 0.5F;
   private Map<C_513_<?>, EntityRenderer<?>> h = ImmutableMap.of();
   private Map<PlayerSkin.a, EntityRenderer<? extends C_1141_>> i = Map.of();
   public final TextureManager a;
   private C_1596_ j;
   public Camera b;
   private Quaternionf k;
   public C_507_ c;
   private final ItemRenderer l;
   private final BlockRenderDispatcher m;
   private final ItemInHandRenderer n;
   private final Font o;
   public final Options d;
   private final C_141653_ p;
   private boolean q = true;
   private boolean r;
   private EntityRenderer entityRenderer = null;
   private C_507_ renderedEntity = null;
   private C_141743_ context = null;

   public <E extends C_507_> int a(E entityIn, float partialTicks) {
      int combinedLight = this.a(entityIn).b(entityIn, partialTicks);
      if (Config.isDynamicLights()) {
         combinedLight = DynamicLights.getCombinedLight(entityIn, combinedLight);
      }

      return combinedLight;
   }

   public EntityRenderDispatcher(
      C_3391_ mcIn,
      TextureManager textureManagerIn,
      ItemRenderer itemRendererIn,
      BlockRenderDispatcher blockRenderDispatcherIn,
      Font textRendererIn,
      Options optionsIn,
      C_141653_ entityModelsIn
   ) {
      this.a = textureManagerIn;
      this.l = itemRendererIn;
      this.n = new ItemInHandRenderer(mcIn, this, itemRendererIn);
      this.m = blockRenderDispatcherIn;
      this.o = textRendererIn;
      this.d = optionsIn;
      this.p = entityModelsIn;
   }

   public <T extends C_507_> EntityRenderer<? super T> a(T entityIn) {
      if (entityIn instanceof AbstractClientPlayer abstractclientplayer) {
         PlayerSkin.a playerskin$model = abstractclientplayer.b().e();
         EntityRenderer<? extends C_1141_> entityrenderer = (EntityRenderer<? extends C_1141_>)this.i.get(playerskin$model);
         return (EntityRenderer<? super T>)(entityrenderer != null ? entityrenderer : (EntityRenderer)this.i.get(PlayerSkin.a.b));
      } else {
         return (EntityRenderer<? super T>)this.h.get(entityIn.m_6095_());
      }
   }

   public void a(C_1596_ worldIn, Camera activeRenderInfoIn, C_507_ entityIn) {
      this.j = worldIn;
      this.b = activeRenderInfoIn;
      this.k = activeRenderInfoIn.f();
      this.c = entityIn;
   }

   public void a(Quaternionf quaternionIn) {
      this.k = quaternionIn;
   }

   public void a(boolean renderShadowIn) {
      this.q = renderShadowIn;
   }

   public void b(boolean debugBoundingBoxIn) {
      this.r = debugBoundingBoxIn;
   }

   public boolean a() {
      return this.r;
   }

   public <E extends C_507_> boolean a(E entityIn, Frustum frustumIn, double camX, double camY, double camZ) {
      EntityRenderer<? super E> entityrenderer = this.a(entityIn);
      return entityrenderer.a(entityIn, frustumIn, camX, camY, camZ);
   }

   public <E extends C_507_> void a(
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
      if (this.b != null) {
         EntityRenderer<? super E> entityrenderer = this.a(entityIn);

         try {
            Vec3 vec3 = entityrenderer.a(entityIn, partialTicks);
            double d2 = xIn + vec3.m_7096_();
            double d3 = yIn + vec3.m_7098_();
            double d0 = zIn + vec3.m_7094_();
            matrixStackIn.a();
            matrixStackIn.a(d2, d3, d0);
            EntityRenderer entityRendererPrev = this.entityRenderer;
            C_507_ renderedEntityPrev = this.renderedEntity;
            entityrenderer = CustomEntityModels.getEntityRenderer(entityIn, entityrenderer);
            this.entityRenderer = entityrenderer;
            this.renderedEntity = entityIn;
            if (EmissiveTextures.isActive()) {
               EmissiveTextures.beginRender();
            }

            entityrenderer.a(entityIn, rotationYawIn, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            if (EmissiveTextures.isActive()) {
               if (EmissiveTextures.hasEmissive()) {
                  EmissiveTextures.beginRenderEmissive();
                  entityrenderer.a(entityIn, rotationYawIn, partialTicks, matrixStackIn, bufferIn, LightTexture.MAX_BRIGHTNESS);
                  EmissiveTextures.endRenderEmissive();
               }

               EmissiveTextures.endRender();
            }

            this.entityRenderer = entityRendererPrev;
            this.renderedEntity = renderedEntityPrev;
            if (entityIn.m_6051_()) {
               this.a(matrixStackIn, bufferIn, entityIn, Mth.a(Mth.h, this.k, new Quaternionf()));
            }

            matrixStackIn.a(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
            if (this.d.O().c() && this.q && !entityIn.m_20145_()) {
               float f = entityrenderer.c(entityIn);
               if (f > 0.0F) {
                  boolean shadowOffset = CustomEntityModels.isActive() && entityrenderer.shadowOffsetX != 0.0F && entityrenderer.shadowOffsetZ != 0.0F;
                  if (shadowOffset) {
                     matrixStackIn.a(entityrenderer.shadowOffsetX, 0.0F, entityrenderer.shadowOffsetZ);
                  }

                  double d1 = this.a(entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_());
                  float f1 = (float)((1.0 - d1 / 256.0) * (double)entityrenderer.f);
                  if (f1 > 0.0F) {
                     a(matrixStackIn, bufferIn, entityIn, f1, partialTicks, this.j, Math.min(f, 32.0F));
                  }

                  if (shadowOffset) {
                     matrixStackIn.a(-entityrenderer.shadowOffsetX, 0.0F, -entityrenderer.shadowOffsetZ);
                  }
               }
            }

            if (this.r && !entityIn.m_20145_() && !C_3391_.m_91087_().m_91299_()) {
               a(matrixStackIn, bufferIn.getBuffer(RenderType.y()), entityIn, partialTicks, 1.0F, 1.0F, 1.0F);
            }

            matrixStackIn.b();
         } catch (Throwable var28) {
            CrashReport crashreport = CrashReport.a(var28, "Rendering entity in world");
            C_4909_ crashreportcategory = crashreport.a("Entity being rendered");
            entityIn.m_7976_(crashreportcategory);
            C_4909_ crashreportcategory1 = crashreport.a("Renderer details");
            crashreportcategory1.m_128159_("Assigned renderer", entityrenderer);
            crashreportcategory1.m_128159_("Location", C_4909_.m_178937_(this.j, xIn, yIn, zIn));
            crashreportcategory1.m_128159_("Rotation", rotationYawIn);
            crashreportcategory1.m_128159_("Delta", partialTicks);
            throw new C_5204_(crashreport);
         }
      }
   }

   private static void a(PoseStack matrixStackIn, C_507_ entityIn, MultiBufferSource bufferIn) {
      C_507_ entity = c(entityIn);
      if (entity == null) {
         C_4287_.a(matrixStackIn, bufferIn, "Missing", entityIn.m_20185_(), entityIn.m_20191_().f_82292_ + 1.5, entityIn.m_20189_(), -65536);
      } else {
         matrixStackIn.a();
         matrixStackIn.a(entity.m_20185_() - entityIn.m_20185_(), entity.m_20186_() - entityIn.m_20186_(), entity.m_20189_() - entityIn.m_20189_());
         a(matrixStackIn, bufferIn.getBuffer(RenderType.y()), entity, 1.0F, 0.0F, 1.0F, 0.0F);
         a(matrixStackIn, bufferIn.getBuffer(RenderType.y()), new Vector3f(), entity.dr(), -256);
         matrixStackIn.b();
      }
   }

   @Nullable
   private static C_507_ c(C_507_ entityIn) {
      IntegratedServer integratedserver = C_3391_.m_91087_().V();
      if (integratedserver != null) {
         C_12_ serverlevel = integratedserver.m_129880_(entityIn.m_9236_().m_46472_());
         if (serverlevel != null) {
            return serverlevel.m_6815_(entityIn.m_19879_());
         }
      }

      return null;
   }

   private static void a(PoseStack matrixStackIn, VertexConsumer bufferIn, C_507_ entityIn, float partialTicks, float red, float green, float blue) {
      if (!Shaders.isShadowPass) {
         C_3040_ aabb = entityIn.m_20191_().m_82386_(-entityIn.m_20185_(), -entityIn.m_20186_(), -entityIn.m_20189_());
         LevelRenderer.a(matrixStackIn, bufferIn, aabb, red, green, blue, 1.0F);
         boolean multipart = entityIn instanceof C_945_;
         if (Reflector.IForgeEntity_isMultipartEntity.exists() && Reflector.IForgeEntity_getParts.exists()) {
            multipart = Reflector.callBoolean(entityIn, Reflector.IForgeEntity_isMultipartEntity);
         }

         if (multipart) {
            double d0 = -Mth.d((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
            double d1 = -Mth.d((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
            double d2 = -Mth.d((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
            C_507_[] parts = (C_507_[])(Reflector.IForgeEntity_getParts.exists()
               ? (C_507_[])Reflector.call(entityIn, Reflector.IForgeEntity_getParts)
               : ((C_945_)entityIn).m_31156_());

            for (C_507_ enderdragonpart : parts) {
               matrixStackIn.a();
               double d3 = d0 + Mth.d((double)partialTicks, enderdragonpart.f_19790_, enderdragonpart.m_20185_());
               double d4 = d1 + Mth.d((double)partialTicks, enderdragonpart.f_19791_, enderdragonpart.m_20186_());
               double d5 = d2 + Mth.d((double)partialTicks, enderdragonpart.f_19792_, enderdragonpart.m_20189_());
               matrixStackIn.a(d3, d4, d5);
               LevelRenderer.a(
                  matrixStackIn,
                  bufferIn,
                  enderdragonpart.m_20191_().m_82386_(-enderdragonpart.m_20185_(), -enderdragonpart.m_20186_(), -enderdragonpart.m_20189_()),
                  0.25F,
                  1.0F,
                  0.0F,
                  1.0F
               );
               matrixStackIn.b();
            }
         }

         if (entityIn instanceof C_524_) {
            float f1 = 0.01F;
            LevelRenderer.a(
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
            Vec3 vec3 = entity.m(entityIn).d(entityIn.dm());
            LevelRenderer.a(
               matrixStackIn,
               bufferIn,
               vec3.c - (double)f,
               vec3.d,
               vec3.e - (double)f,
               vec3.c + (double)f,
               vec3.d + 0.0625,
               vec3.e + (double)f,
               1.0F,
               1.0F,
               0.0F,
               1.0F
            );
         }

         a(matrixStackIn, bufferIn, new Vector3f(0.0F, entityIn.m_20192_(), 0.0F), entityIn.g(partialTicks).a(2.0), -16776961);
      }
   }

   private static void a(PoseStack matrixStackIn, VertexConsumer bufferIn, Vector3f vecEyeIn, Vec3 vecLookIn, int colorIn) {
      PoseStack.a posestack$pose = matrixStackIn.c();
      bufferIn.a(posestack$pose, vecEyeIn).a(colorIn).b(posestack$pose, (float)vecLookIn.c, (float)vecLookIn.d, (float)vecLookIn.e);
      bufferIn.a(
            posestack$pose,
            (float)((double)vecEyeIn.x() + vecLookIn.c),
            (float)((double)vecEyeIn.y() + vecLookIn.d),
            (float)((double)vecEyeIn.z() + vecLookIn.e)
         )
         .a(colorIn)
         .b(posestack$pose, (float)vecLookIn.c, (float)vecLookIn.d, (float)vecLookIn.e);
   }

   private void a(PoseStack matrixStackIn, MultiBufferSource bufferIn, C_507_ entityIn, Quaternionf rotIn) {
      TextureAtlasSprite textureatlassprite = ModelBakery.a.c();
      TextureAtlasSprite textureatlassprite1 = ModelBakery.b.c();
      matrixStackIn.a();
      float f = entityIn.m_20205_() * 1.4F;
      matrixStackIn.b(f, f, f);
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = entityIn.m_20206_() / f;
      float f4 = 0.0F;
      matrixStackIn.a(rotIn);
      matrixStackIn.a(0.0F, 0.0F, 0.3F - (float)((int)f3) * 0.02F);
      float f5 = 0.0F;
      int i = 0;
      VertexConsumer vertexconsumer = bufferIn.getBuffer(C_4177_.i());

      for (PoseStack.a posestack$pose = matrixStackIn.c(); f3 > 0.0F; i++) {
         TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
         vertexconsumer.setSprite(textureatlassprite2);
         float f6 = textureatlassprite2.c();
         float f7 = textureatlassprite2.g();
         float f8 = textureatlassprite2.d();
         float f9 = textureatlassprite2.h();
         if (i / 2 % 2 == 0) {
            float f10 = f8;
            f8 = f6;
            f6 = f10;
         }

         a(posestack$pose, vertexconsumer, -f1 - 0.0F, 0.0F - f4, f5, f8, f9);
         a(posestack$pose, vertexconsumer, f1 - 0.0F, 0.0F - f4, f5, f6, f9);
         a(posestack$pose, vertexconsumer, f1 - 0.0F, 1.4F - f4, f5, f6, f7);
         a(posestack$pose, vertexconsumer, -f1 - 0.0F, 1.4F - f4, f5, f8, f7);
         f3 -= 0.45F;
         f4 -= 0.45F;
         f1 *= 0.9F;
         f5 -= 0.03F;
      }

      matrixStackIn.b();
   }

   private static void a(PoseStack.a matrixEntryIn, VertexConsumer bufferIn, float x, float y, float z, float texU, float texV) {
      bufferIn.a(matrixEntryIn, x, y, z).a(-1).a(texU, texV).a(0, 10).c(240).b(matrixEntryIn, 0.0F, 1.0F, 0.0F);
   }

   private static void a(
      PoseStack matrixStackIn, MultiBufferSource bufferIn, C_507_ entityIn, float weightIn, float partialTicks, C_1599_ worldIn, float sizeIn
   ) {
      if (!Config.isShaders() || !Shaders.shouldSkipDefaultShadow) {
         double d0 = Mth.d((double)partialTicks, entityIn.f_19790_, entityIn.m_20185_());
         double d1 = Mth.d((double)partialTicks, entityIn.f_19791_, entityIn.m_20186_());
         double d2 = Mth.d((double)partialTicks, entityIn.f_19792_, entityIn.m_20189_());
         float f = Math.min(weightIn / 0.5F, sizeIn);
         int i = Mth.a(d0 - (double)sizeIn);
         int j = Mth.a(d0 + (double)sizeIn);
         int k = Mth.a(d1 - (double)f);
         int l = Mth.a(d1);
         int i1 = Mth.a(d2 - (double)sizeIn);
         int j1 = Mth.a(d2 + (double)sizeIn);
         PoseStack.a posestack$pose = matrixStackIn.c();
         VertexConsumer vertexconsumer = bufferIn.getBuffer(e);
         C_4681_ blockpos$mutableblockpos = new C_4681_();

         for (int k1 = i1; k1 <= j1; k1++) {
            for (int l1 = i; l1 <= j; l1++) {
               blockpos$mutableblockpos.m_122178_(l1, 0, k1);
               C_2116_ chunkaccess = worldIn.m_46865_(blockpos$mutableblockpos);

               for (int i2 = k; i2 <= l; i2++) {
                  blockpos$mutableblockpos.m_142448_(i2);
                  float f1 = weightIn - (float)(d1 - (double)blockpos$mutableblockpos.m_123342_()) * 0.5F;
                  a(posestack$pose, vertexconsumer, chunkaccess, worldIn, blockpos$mutableblockpos, d0, d1, d2, sizeIn, f1);
               }
            }
         }
      }
   }

   private static void a(
      PoseStack.a matrixEntryIn,
      VertexConsumer bufferIn,
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
      BlockState blockstate = chunkAccessIn.a_(blockpos);
      if (blockstate.m_60799_() != C_1879_.INVISIBLE && worldIn.m_46803_(blockPosIn) > 3 && blockstate.m_60838_(chunkAccessIn, blockpos)) {
         C_3072_ voxelshape = blockstate.m_60808_(chunkAccessIn, blockpos);
         if (!voxelshape.m_83281_()) {
            float f = LightTexture.a(worldIn.m_6042_(), worldIn.m_46803_(blockPosIn));
            float f1 = weightIn * 0.5F * f;
            if (f1 >= 0.0F) {
               if (f1 > 1.0F) {
                  f1 = 1.0F;
               }

               int i = C_175_.m_13660_(Mth.d(f1 * 255.0F), 255, 255, 255);
               C_3040_ aabb = voxelshape.m_83215_();
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
               a(matrixEntryIn, bufferIn, i, f2, f4, f5, f7, f9);
               a(matrixEntryIn, bufferIn, i, f2, f4, f6, f7, f10);
               a(matrixEntryIn, bufferIn, i, f3, f4, f6, f8, f10);
               a(matrixEntryIn, bufferIn, i, f3, f4, f5, f8, f9);
            }
         }
      }
   }

   private static void a(PoseStack.a matrixEntryIn, VertexConsumer bufferIn, int colorIn, float xIn, float yIn, float zIn, float texU, float texV) {
      Vector3f vector3f = matrixEntryIn.a().transformPosition(xIn, yIn, zIn, new Vector3f());
      bufferIn.a(vector3f.x(), vector3f.y(), vector3f.z(), colorIn, texU, texV, C_4474_.f_118083_, 15728880, 0.0F, 1.0F, 0.0F);
   }

   public void a(@Nullable C_1596_ worldIn) {
      this.j = worldIn;
      if (worldIn == null) {
         this.b = null;
      }
   }

   public double b(C_507_ entityIn) {
      return this.b.b().g(entityIn.dm());
   }

   public double a(double x, double y, double z) {
      return this.b.b().c(x, y, z);
   }

   public Quaternionf b() {
      return this.k;
   }

   public ItemInHandRenderer d() {
      return this.n;
   }

   public void m_6213_(C_77_ resourceManager) {
      C_141743_ entityrendererprovider$context = new C_141743_(this, this.l, this.m, this.n, resourceManager, this.p, this.o);
      this.context = entityrendererprovider$context;
      this.h = EntityRenderers.a(entityrendererprovider$context);
      this.i = EntityRenderers.b(entityrendererprovider$context);
      registerPlayerItems(this.i);
      if (Reflector.ForgeEventFactoryClient_gatherLayers.exists()) {
         Reflector.ForgeEventFactoryClient_gatherLayers.call(this.h, this.i, entityrendererprovider$context);
      }
   }

   private static void registerPlayerItems(Map<String, EntityRenderer> renderPlayerMap) {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.RuntimeException: Constructor net/optifine/player/PlayerItemsLayer.<init>(Lnet/minecraft/src/C_4462_;)V not found
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.ExprUtil.getSyntheticParametersMask(ExprUtil.java:49)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:957)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.NewExprent.toJava(NewExprent.java:460)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.getCastedExprent(ExprProcessor.java:1018)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.appendParamList(InvocationExprent.java:1153)
      //   at org.jetbrains.java.decompiler.modules.decompiler.exps.InvocationExprent.toJava(InvocationExprent.java:902)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.listToJava(ExprProcessor.java:895)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.BasicBlockStatement.toJava(BasicBlockStatement.java:90)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.IfStatement.toJava(IfStatement.java:241)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.DoStatement.toJava(DoStatement.java:148)
      //   at org.jetbrains.java.decompiler.modules.decompiler.ExprProcessor.jmpWrapper(ExprProcessor.java:833)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.SequenceStatement.toJava(SequenceStatement.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.stats.RootStatement.toJava(RootStatement.java:36)
      //   at org.jetbrains.java.decompiler.main.ClassWriter.writeMethod(ClassWriter.java:1283)
      //
      // Bytecode:
      // 00: bipush 0
      // 01: istore 1
      // 02: aload 0
      // 03: invokeinterface java/util/Map.values ()Ljava/util/Collection; 1
      // 08: invokeinterface java/util/Collection.iterator ()Ljava/util/Iterator; 1
      // 0d: astore 2
      // 0e: aload 2
      // 0f: invokeinterface java/util/Iterator.hasNext ()Z 1
      // 14: ifeq 4a
      // 17: aload 2
      // 18: invokeinterface java/util/Iterator.next ()Ljava/lang/Object; 1
      // 1d: checkcast EntityRenderer
      // 20: astore 3
      // 21: aload 3
      // 22: instanceof net/minecraft/src/C_4462_
      // 25: ifeq 47
      // 28: aload 3
      // 29: checkcast net/minecraft/src/C_4462_
      // 2c: astore 4
      // 2e: aload 4
      // 30: ldc_w net/optifine/player/PlayerItemsLayer
      // 33: invokevirtual net/minecraft/src/C_4462_.removeLayers (Ljava/lang/Class;)V
      // 36: aload 4
      // 38: new net/optifine/player/PlayerItemsLayer
      // 3b: dup
      // 3c: aload 4
      // 3e: invokespecial net/optifine/player/PlayerItemsLayer.<init> (Lnet/minecraft/src/C_4462_;)V
      // 41: invokevirtual net/minecraft/src/C_4462_.a (LRenderLayer;)Z
      // 44: pop
      // 45: bipush 1
      // 46: istore 1
      // 47: goto 0e
      // 4a: iload 1
      // 4b: ifne 54
      // 4e: ldc_w "PlayerItemsLayer not registered"
      // 51: invokestatic net/optifine/Config.warn (Ljava/lang/String;)V
      // 54: return
   }

   public Map<C_513_, EntityRenderer> getEntityRenderMap() {
      if (this.h instanceof ImmutableMap) {
         this.h = new HashMap(this.h);
      }

      return this.h;
   }

   public C_141743_ getContext() {
      return this.context;
   }

   public C_507_ getRenderedEntity() {
      return this.renderedEntity;
   }

   public EntityRenderer getEntityRenderer() {
      return this.entityRenderer;
   }

   public void setRenderedEntity(C_507_ renderedEntity) {
      this.renderedEntity = renderedEntity;
   }

   public Map<PlayerSkin.a, EntityRenderer> getSkinMap() {
      return Collections.unmodifiableMap(this.i);
   }
}
