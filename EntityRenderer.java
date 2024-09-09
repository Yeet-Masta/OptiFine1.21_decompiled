import net.minecraft.src.C_1607_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_313489_;
import net.minecraft.src.C_336597_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_141742_.C_141743_;
import net.optifine.Config;
import net.optifine.entity.model.IEntityRenderer;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;
import net.optifine.util.Either;
import org.joml.Matrix4f;

public abstract class EntityRenderer<T extends C_507_> implements IEntityRenderer {
   protected static final float b = 0.025F;
   public static final int c = 24;
   protected final EntityRenderDispatcher d;
   private final Font a;
   public float e;
   public float f = 1.0F;
   private C_513_ entityType = null;
   private ResourceLocation locationTextureCustom = null;
   public float shadowOffsetX;
   public float shadowOffsetZ;
   public float leashOffsetX;
   public float leashOffsetY;
   public float leashOffsetZ;

   protected EntityRenderer(C_141743_ contextIn) {
      this.d = contextIn.a();
      this.a = contextIn.h();
   }

   public final int b(T entityIn, float partialTicks) {
      C_4675_ blockpos = C_4675_.m_274446_(entityIn.l(partialTicks));
      return LightTexture.a(this.a(entityIn, blockpos), this.b(entityIn, blockpos));
   }

   protected int b(T entityIn, C_4675_ blockPosIn) {
      return entityIn.m_9236_().m_45517_(C_1607_.SKY, blockPosIn);
   }

   protected int a(T entityIn, C_4675_ partialTicks) {
      return entityIn.m_6060_() ? 15 : entityIn.m_9236_().m_45517_(C_1607_.BLOCK, partialTicks);
   }

   public boolean a(T livingEntityIn, Frustum camera, double camX, double camY, double camZ) {
      if (!livingEntityIn.m_6000_(camX, camY, camZ)) {
         return false;
      } else if (livingEntityIn.f_19811_) {
         return true;
      } else {
         C_3040_ aabb = livingEntityIn.m_6921_().m_82400_(0.5);
         if (aabb.m_82392_() || aabb.m_82309_() == 0.0) {
            aabb = new C_3040_(
               livingEntityIn.m_20185_() - 2.0,
               livingEntityIn.m_20186_() - 2.0,
               livingEntityIn.m_20189_() - 2.0,
               livingEntityIn.m_20185_() + 2.0,
               livingEntityIn.m_20186_() + 2.0,
               livingEntityIn.m_20189_() + 2.0
            );
         }

         if (camera.a(aabb)) {
            return true;
         } else {
            if (livingEntityIn instanceof C_336597_ leashable) {
               C_507_ entity = leashable.m_340614_();
               if (entity != null) {
                  return camera.a(entity.m_6921_());
               }
            }

            return false;
         }
      }
   }

   public Vec3 a(T entityIn, float partialTicks) {
      return Vec3.b;
   }

   public void a(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (entityIn instanceof C_336597_ leashable) {
         C_507_ entity = leashable.m_340614_();
         if (entity != null) {
            this.a(entityIn, partialTicks, matrixStackIn, bufferIn, entity);
         }
      }

      if (!Reflector.RenderNameTagEvent_Constructor.exists()) {
         if (this.b(entityIn)) {
            this.a(entityIn, entityIn.m_5446_(), matrixStackIn, bufferIn, packedLightIn, partialTicks);
         }
      } else {
         Object renderNameplateEvent = Reflector.newInstance(
            Reflector.RenderNameTagEvent_Constructor, entityIn, entityIn.m_5446_(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks
         );
         Reflector.postForgeBusEvent(renderNameplateEvent);
         Object result = Reflector.call(renderNameplateEvent, Reflector.Event_getResult);
         if (result != ReflectorForge.EVENT_RESULT_DENY && (result == ReflectorForge.EVENT_RESULT_ALLOW || this.b(entityIn))) {
            C_4996_ content = (C_4996_)Reflector.call(renderNameplateEvent, Reflector.RenderNameTagEvent_getContent);
            this.a(entityIn, content, matrixStackIn, bufferIn, packedLightIn, partialTicks);
         }
      }
   }

   private <E extends C_507_> void a(T entityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, E leashHolderIn) {
      if (!Config.isShaders() || !Shaders.isShadowPass) {
         matrixStackIn.a();
         Vec3 vec3 = leashHolderIn.s(partialTicks);
         double d0 = (double)(entityIn.m_339180_(partialTicks) * (float) (Math.PI / 180.0)) + (Math.PI / 2);
         Vec3 vec31 = entityIn.q(partialTicks);
         if (this.leashOffsetX != 0.0F || this.leashOffsetY != 0.0F || this.leashOffsetZ != 0.0F) {
            vec31 = new Vec3((double)this.leashOffsetX, (double)this.leashOffsetY, (double)this.leashOffsetZ);
         }

         double d1 = Math.cos(d0) * vec31.e + Math.sin(d0) * vec31.c;
         double d2 = Math.sin(d0) * vec31.e - Math.cos(d0) * vec31.c;
         double d3 = Mth.d((double)partialTicks, entityIn.f_19854_, entityIn.m_20185_()) + d1;
         double d4 = Mth.d((double)partialTicks, entityIn.f_19855_, entityIn.m_20186_()) + vec31.d;
         double d5 = Mth.d((double)partialTicks, entityIn.f_19856_, entityIn.m_20189_()) + d2;
         matrixStackIn.a(d1, vec31.d, d2);
         float f = (float)(vec3.c - d3);
         float f1 = (float)(vec3.d - d4);
         float f2 = (float)(vec3.e - d5);
         float f3 = 0.025F;
         VertexConsumer vertexconsumer = bufferIn.getBuffer(RenderType.h());
         Matrix4f matrix4f = matrixStackIn.c().a();
         float f4 = Mth.i(f * f + f2 * f2) * 0.025F / 2.0F;
         float f5 = f2 * f4;
         float f6 = f * f4;
         C_4675_ blockpos = C_4675_.m_274446_(entityIn.k(partialTicks));
         C_4675_ blockpos1 = C_4675_.m_274446_(leashHolderIn.k(partialTicks));
         int i = this.a(entityIn, blockpos);
         int j = this.d.a(leashHolderIn).a(leashHolderIn, blockpos1);
         int k = entityIn.m_9236_().m_45517_(C_1607_.SKY, blockpos);
         int l = entityIn.m_9236_().m_45517_(C_1607_.SKY, blockpos1);
         if (Config.isShaders()) {
            Shaders.beginLeash();
         }

         for (int i1 = 0; i1 <= 24; i1++) {
            a(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.025F, f5, f6, i1, false);
         }

         for (int j1 = 24; j1 >= 0; j1--) {
            a(vertexconsumer, matrix4f, f, f1, f2, i, j, k, l, 0.025F, 0.0F, f5, f6, j1, true);
         }

         if (Config.isShaders()) {
            Shaders.endLeash();
         }

         matrixStackIn.b();
      }
   }

   private static void a(
      VertexConsumer bufferIn,
      Matrix4f matrixIn,
      float x,
      float y,
      float z,
      int lightEntityIn,
      int lightHolderIn,
      int lightSkyEntityIn,
      int lightSkyHolderIn,
      float p_340096_9_,
      float p_340096_10_,
      float p_340096_11_,
      float p_340096_12_,
      int p_340096_13_,
      boolean p_340096_14_
   ) {
      float f = (float)p_340096_13_ / 24.0F;
      int i = (int)Mth.i(f, (float)lightEntityIn, (float)lightHolderIn);
      int j = (int)Mth.i(f, (float)lightSkyEntityIn, (float)lightSkyHolderIn);
      int k = LightTexture.a(i, j);
      float f1 = p_340096_13_ % 2 == (p_340096_14_ ? 1 : 0) ? 0.7F : 1.0F;
      float f2 = 0.5F * f1;
      float f3 = 0.4F * f1;
      float f4 = 0.3F * f1;
      float f5 = x * f;
      float f6 = y > 0.0F ? y * f * f : y - y * (1.0F - f) * (1.0F - f);
      float f7 = z * f;
      bufferIn.a(matrixIn, f5 - p_340096_11_, f6 + p_340096_10_, f7 + p_340096_12_).a(f2, f3, f4, 1.0F).c(k);
      bufferIn.a(matrixIn, f5 + p_340096_11_, f6 + p_340096_9_ - p_340096_10_, f7 - p_340096_12_).a(f2, f3, f4, 1.0F).c(k);
   }

   protected boolean b(T entity) {
      return entity.m_6052_() || entity.m_8077_() && entity == this.d.c;
   }

   public abstract ResourceLocation a(T var1);

   public Font b() {
      return this.a;
   }

   protected void a(T entityIn, C_4996_ displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, float partialTicks) {
      double d0 = this.d.b(entityIn);
      boolean isNameplateInRenderDistance = !(d0 > 4096.0);
      if (Reflector.ForgeHooksClient_isNameplateInRenderDistance.exists()) {
         isNameplateInRenderDistance = Reflector.ForgeHooksClient_isNameplateInRenderDistance.callBoolean(entityIn, d0);
      }

      if (isNameplateInRenderDistance) {
         Vec3 vec3 = entityIn.m_319864_().a(C_313489_.NAME_TAG, 0, entityIn.m_5675_(partialTicks));
         if (vec3 != null) {
            boolean flag = !entityIn.m_20163_();
            int i = "deadmau5".equals(displayNameIn.getString()) ? -10 : 0;
            matrixStackIn.a();
            matrixStackIn.a(vec3.c, vec3.d + 0.5, vec3.e);
            matrixStackIn.a(this.d.b());
            matrixStackIn.b(0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrixStackIn.c().a();
            float f = C_3391_.m_91087_().m.a(0.25F);
            int j = (int)(f * 255.0F) << 24;
            Font font = this.b();
            float f1 = (float)(-font.a(displayNameIn) / 2);
            font.a(displayNameIn, f1, (float)i, 553648127, false, matrix4f, bufferIn, flag ? Font.a.b : Font.a.a, j, packedLightIn);
            if (flag) {
               font.a(displayNameIn, f1, (float)i, -1, false, matrix4f, bufferIn, Font.a.a, 0, packedLightIn);
            }

            matrixStackIn.b();
         }
      }
   }

   protected float c(T entityIn) {
      return this.e;
   }

   @Override
   public Either<C_513_, C_1992_> getType() {
      return this.entityType == null ? null : Either.makeLeft(this.entityType);
   }

   @Override
   public void setType(Either<C_513_, C_1992_> type) {
      this.entityType = (C_513_)type.getLeft().get();
   }

   public ResourceLocation getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   public void setLocationTextureCustom(ResourceLocation locationTextureCustom) {
      this.locationTextureCustom = locationTextureCustom;
   }
}
