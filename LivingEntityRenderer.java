import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1144_;
import net.minecraft.src.C_252363_;
import net.minecraft.src.C_3078_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3819_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4382_;
import net.minecraft.src.C_4425_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_4856_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_535_;
import net.minecraft.src.C_141742_.C_141743_;
import net.minecraft.src.C_3078_.C_3080_;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.slf4j.Logger;

public abstract class LivingEntityRenderer<T extends C_524_, M extends C_3819_<T>> extends EntityRenderer<T> implements C_4382_<T, M> {
   private static final Logger a = LogUtils.getLogger();
   private static final float i = 0.1F;
   public M g;
   protected final List<RenderLayer<T, M>> h = Lists.newArrayList();
   public float renderLimbSwing;
   public float renderLimbSwingAmount;
   public float renderAgeInTicks;
   public float renderHeadYaw;
   public float renderHeadPitch;
   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
   private static boolean renderItemHead = false;

   public LivingEntityRenderer(C_141743_ contextIn, M entityModelIn, float shadowSizeIn) {
      super(contextIn);
      this.g = entityModelIn;
      this.e = shadowSizeIn;
   }

   public final boolean a(RenderLayer<T, M> layer) {
      return this.h.add(layer);
   }

   public M m_7200_() {
      return this.g;
   }

   public void a(T entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      if (!Reflector.RenderLivingEvent_Pre_Constructor.exists()
         || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn)) {
         if (animateModelLiving) {
            entityIn.f_267362_.m_267771_(1.5F);
         }

         matrixStackIn.a();
         this.g.f_102608_ = this.d(entityIn, partialTicks);
         this.g.f_102609_ = entityIn.m_20159_();
         if (Reflector.IForgeEntity_shouldRiderSit.exists()) {
            this.g.f_102609_ = entityIn.m_20159_()
               && entityIn.m_20202_() != null
               && Reflector.callBoolean(entityIn.m_20202_(), Reflector.IForgeEntity_shouldRiderSit);
         }

         this.g.f_102610_ = entityIn.m_6162_();
         float f = Mth.j(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
         float f1 = Mth.j(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
         float f2 = f1 - f;
         if (this.g.f_102609_ && entityIn.m_20159_() && entityIn.m_20202_() instanceof C_524_ livingentity) {
            f = Mth.j(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
            f2 = f1 - f;
            float f7 = Mth.g(f2);
            if (f7 < -85.0F) {
               f7 = -85.0F;
            }

            if (f7 >= 85.0F) {
               f7 = 85.0F;
            }

            f = f1 - f7;
            if (f7 * f7 > 2500.0F) {
               f += f7 * 0.2F;
            }

            f2 = f1 - f;
         }

         float f6 = Mth.i(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
         if (e(entityIn)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
         }

         f2 = Mth.g(f2);
         if (entityIn.m_217003_(C_535_.SLEEPING)) {
            Direction direction = entityIn.fJ();
            if (direction != null) {
               float f3 = entityIn.m_20236_(C_535_.STANDING) - 0.1F;
               matrixStackIn.a((float)(-direction.j()) * f3, 0.0F, (float)(-direction.l()) * f3);
            }
         }

         float f8 = entityIn.m_6134_();
         matrixStackIn.b(f8, f8, f8);
         float f9 = this.a(entityIn, partialTicks);
         this.a(entityIn, matrixStackIn, f9, f, partialTicks, f8);
         matrixStackIn.b(-1.0F, -1.0F, 1.0F);
         this.a(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.a(0.0F, -1.501F, 0.0F);
         float f4 = 0.0F;
         float f5 = 0.0F;
         if (!entityIn.m_20159_() && entityIn.m_6084_()) {
            f4 = entityIn.f_267362_.m_267711_(partialTicks);
            f5 = entityIn.f_267362_.m_267590_(partialTicks);
            if (entityIn.m_6162_()) {
               f5 *= 3.0F;
            }

            if (f4 > 1.0F) {
               f4 = 1.0F;
            }
         }

         this.g.m_6839_(entityIn, f5, f4, partialTicks);
         this.g.m_6973_(entityIn, f5, f4, f9, f2, f6);
         if (CustomEntityModels.isActive()) {
            this.renderLimbSwing = f5;
            this.renderLimbSwingAmount = f4;
            this.renderAgeInTicks = f9;
            this.renderHeadYaw = f2;
            this.renderHeadPitch = f6;
         }

         boolean isShaders = Config.isShaders();
         C_3391_ minecraft = C_3391_.m_91087_();
         boolean flag = this.d(entityIn);
         boolean flag1 = !flag && !entityIn.m_20177_(minecraft.f_91074_);
         boolean flag2 = minecraft.m_91314_(entityIn);
         RenderType rendertype = this.a(entityIn, flag, flag1, flag2);
         if (rendertype != null) {
            VertexConsumer vertexconsumer = bufferIn.getBuffer(rendertype);
            float overlayProgress = this.b(entityIn, partialTicks);
            if (isShaders) {
               if (entityIn.f_20916_ > 0 || entityIn.f_20919_ > 0) {
                  Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
               }

               if (overlayProgress > 0.0F) {
                  Shaders.setEntityColor(overlayProgress, overlayProgress, overlayProgress, 0.5F);
               }
            }

            int i = c(entityIn, overlayProgress);
            this.g.a(matrixStackIn, vertexconsumer, packedLightIn, i, flag1 ? 654311423 : -1);
         }

         if (!entityIn.m_5833_()) {
            for (RenderLayer<T, M> renderlayer : this.h) {
               if (renderlayer instanceof C_4425_) {
                  renderItemHead = true;
               }

               renderlayer.a(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f4, partialTicks, f9, f2, f6);
               renderItemHead = false;
            }
         }

         if (Config.isShaders()) {
            Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
         }

         matrixStackIn.b();
         super.a(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
         if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn);
         }
      }
   }

   @Nullable
   protected RenderType a(T entityIn, boolean visibleIn, boolean translucentIn, boolean glowingIn) {
      ResourceLocation resourcelocation = this.a(entityIn);
      if (this.getLocationTextureCustom() != null) {
         resourcelocation = this.getLocationTextureCustom();
      }

      if (translucentIn) {
         return RenderType.g(resourcelocation);
      } else if (visibleIn) {
         return this.g.a(resourcelocation);
      } else if (glowingIn && !Config.getMinecraft().f.d()) {
         return this.g.a(resourcelocation);
      } else {
         return glowingIn ? RenderType.r(resourcelocation) : null;
      }
   }

   public static int c(C_524_ livingEntityIn, float uIn) {
      return C_4474_.m_118093_(C_4474_.m_118088_(uIn), C_4474_.m_118096_(livingEntityIn.f_20916_ > 0 || livingEntityIn.f_20919_ > 0));
   }

   protected boolean d(T livingEntityIn) {
      return !livingEntityIn.m_20145_();
   }

   private static float a(Direction facingIn) {
      switch (facingIn) {
         case d:
            return 90.0F;
         case e:
            return 0.0F;
         case c:
            return 270.0F;
         case f:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected boolean a(T entityIn) {
      return entityIn.m_146890_();
   }

   protected void a(T entityLiving, PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float renderScale) {
      if (this.a(entityLiving)) {
         rotationYaw += (float)(Math.cos((double)entityLiving.f_19797_ * 3.25) * Math.PI * 0.4F);
      }

      if (!entityLiving.m_217003_(C_535_.SLEEPING)) {
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(180.0F - rotationYaw));
      }

      if (entityLiving.f_20919_ > 0) {
         float f = ((float)entityLiving.f_20919_ + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = Mth.c(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         matrixStackIn.a(C_252363_.f_252403_.m_252977_(f * this.c(entityLiving)));
      } else if (entityLiving.m_21209_()) {
         matrixStackIn.a(C_252363_.f_252529_.m_252977_(-90.0F - entityLiving.m_146909_()));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(((float)entityLiving.f_19797_ + partialTicks) * -75.0F));
      } else if (entityLiving.m_217003_(C_535_.SLEEPING)) {
         Direction direction = entityLiving.fJ();
         float f1 = direction != null ? a(direction) : rotationYaw;
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(f1));
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(this.c(entityLiving)));
         matrixStackIn.a(C_252363_.f_252436_.m_252977_(270.0F));
      } else if (e(entityLiving)) {
         matrixStackIn.a(0.0F, (entityLiving.m_20206_() + 0.1F) / renderScale, 0.0F);
         matrixStackIn.a(C_252363_.f_252403_.m_252977_(180.0F));
      }
   }

   protected float d(T livingBase, float partialTickTime) {
      return livingBase.m_21324_(partialTickTime);
   }

   protected float a(T livingBase, float partialTicks) {
      return (float)livingBase.f_19797_ + partialTicks;
   }

   protected float c(T entityLivingBaseIn) {
      return 90.0F;
   }

   protected float b(T livingEntityIn, float partialTicks) {
      return 0.0F;
   }

   protected void a(T entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
   }

   protected boolean b(T entity) {
      double d0 = this.d.b(entity);
      float f = entity.m_20163_() ? 32.0F : 64.0F;
      if (d0 >= (double)(f * f)) {
         return false;
      } else {
         C_3391_ minecraft = C_3391_.m_91087_();
         C_4105_ localplayer = minecraft.f_91074_;
         boolean flag = !entity.m_20177_(localplayer);
         if (entity != localplayer) {
            C_3078_ team = entity.m_5647_();
            C_3078_ team1 = localplayer.ck();
            if (team != null) {
               C_3080_ team$visibility = team.m_7470_();
               switch (team$visibility) {
                  case ALWAYS:
                     return flag;
                  case NEVER:
                     return false;
                  case HIDE_FOR_OTHER_TEAMS:
                     return team1 == null ? flag : team.m_83536_(team1) && (team.m_6259_() || flag);
                  case HIDE_FOR_OWN_TEAM:
                     return team1 == null ? flag : !team.m_83536_(team1) && flag;
                  default:
                     return true;
               }
            }
         }

         return C_3391_.m_91404_() && entity != minecraft.m_91288_() && flag && !entity.m_20160_();
      }
   }

   public static boolean e(C_524_ entityIn) {
      if (entityIn instanceof C_1141_ || entityIn.m_8077_()) {
         String s = C_4856_.m_126649_(entityIn.m_7755_().getString());
         if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
            return !(entityIn instanceof C_1141_) || ((C_1141_)entityIn).m_36170_(C_1144_.CAPE);
         }
      }

      return false;
   }

   protected float f(T entityIn) {
      return super.c(entityIn) * entityIn.m_6134_();
   }

   public <T extends RenderLayer> T getLayer(Class<T> cls) {
      List<T> list = this.getLayers(cls);
      return (T)(list.isEmpty() ? null : list.get(0));
   }

   public <T extends RenderLayer> List<T> getLayers(Class<T> cls) {
      List<RenderLayer> list = new ArrayList();

      for (RenderLayer layer : this.h) {
         if (cls.isInstance(layer)) {
            list.add(layer);
         }
      }

      return (List<T>)list;
   }

   public void removeLayers(Class cls) {
      Iterator it = this.h.iterator();

      while (it.hasNext()) {
         RenderLayer layer = (RenderLayer)it.next();
         if (cls.isInstance(layer)) {
            it.remove();
         }
      }
   }

   public static boolean isRenderItemHead() {
      return renderItemHead;
   }
}
