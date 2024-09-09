package net.minecraft.src;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.slf4j.Logger;

public abstract class C_4357_ extends C_4331_ implements C_4382_ {
   private static final Logger f_115289_ = LogUtils.getLogger();
   private static final float f_174287_ = 0.1F;
   public C_3819_ f_115290_;
   protected final List f_115291_ = Lists.newArrayList();
   public float renderLimbSwing;
   public float renderLimbSwingAmount;
   public float renderAgeInTicks;
   public float renderHeadYaw;
   public float renderHeadPitch;
   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
   private static boolean renderItemHead = false;

   public C_4357_(C_141742_.C_141743_ contextIn, C_3819_ entityModelIn, float shadowSizeIn) {
      super(contextIn);
      this.f_115290_ = entityModelIn;
      this.f_114477_ = shadowSizeIn;
   }

   public final boolean m_115326_(C_4447_ layer) {
      return this.f_115291_.add(layer);
   }

   public C_3819_ m_7200_() {
      return this.f_115290_;
   }

   public void m_7392_(C_524_ entityIn, float entityYaw, float partialTicks, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn) {
      if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn)) {
         if (animateModelLiving) {
            entityIn.f_267362_.m_267771_(1.5F);
         }

         matrixStackIn.m_85836_();
         this.f_115290_.f_102608_ = this.m_115342_(entityIn, partialTicks);
         this.f_115290_.f_102609_ = entityIn.bS();
         if (Reflector.IForgeEntity_shouldRiderSit.exists()) {
            this.f_115290_.f_102609_ = entityIn.bS() && entityIn.dc() != null && Reflector.callBoolean(entityIn.dc(), Reflector.IForgeEntity_shouldRiderSit);
         }

         this.f_115290_.f_102610_ = entityIn.m_6162_();
         float f = C_188_.m_14189_(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
         float f1 = C_188_.m_14189_(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
         float f2 = f1 - f;
         float f7;
         if (this.f_115290_.f_102609_ && entityIn.bS()) {
            C_507_ var11 = entityIn.dc();
            if (var11 instanceof C_524_) {
               C_524_ livingentity = (C_524_)var11;
               f = C_188_.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
               f2 = f1 - f;
               f7 = C_188_.m_14177_(f2);
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
         }

         float f6 = C_188_.m_14179_(partialTicks, entityIn.P, entityIn.dG());
         if (m_194453_(entityIn)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
         }

         f2 = C_188_.m_14177_(f2);
         float f9;
         if (entityIn.c(C_535_.SLEEPING)) {
            C_4687_ direction = entityIn.m_21259_();
            if (direction != null) {
               f9 = entityIn.d(C_535_.STANDING) - 0.1F;
               matrixStackIn.m_252880_((float)(-direction.m_122429_()) * f9, 0.0F, (float)(-direction.m_122431_()) * f9);
            }
         }

         f7 = entityIn.m_6134_();
         matrixStackIn.m_85841_(f7, f7, f7);
         f9 = this.m_6930_(entityIn, partialTicks);
         this.m_7523_(entityIn, matrixStackIn, f9, f, partialTicks, f7);
         matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
         this.m_7546_(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.m_252880_(0.0F, -1.501F, 0.0F);
         float f4 = 0.0F;
         float f5 = 0.0F;
         if (!entityIn.bS() && entityIn.m_6084_()) {
            f4 = entityIn.f_267362_.m_267711_(partialTicks);
            f5 = entityIn.f_267362_.m_267590_(partialTicks);
            if (entityIn.m_6162_()) {
               f5 *= 3.0F;
            }

            if (f4 > 1.0F) {
               f4 = 1.0F;
            }
         }

         this.f_115290_.m_6839_(entityIn, f5, f4, partialTicks);
         this.f_115290_.m_6973_(entityIn, f5, f4, f9, f2, f6);
         if (CustomEntityModels.isActive()) {
            this.renderLimbSwing = f5;
            this.renderLimbSwingAmount = f4;
            this.renderAgeInTicks = f9;
            this.renderHeadYaw = f2;
            this.renderHeadPitch = f6;
         }

         boolean isShaders = Config.isShaders();
         C_3391_ minecraft = C_3391_.m_91087_();
         boolean flag = this.m_5933_(entityIn);
         boolean flag1 = !flag && !entityIn.d(minecraft.f_91074_);
         boolean flag2 = minecraft.m_91314_(entityIn);
         C_4168_ rendertype = this.m_7225_(entityIn, flag, flag1, flag2);
         if (rendertype != null) {
            C_3187_ vertexconsumer = bufferIn.m_6299_(rendertype);
            float overlayProgress = this.m_6931_(entityIn, partialTicks);
            if (isShaders) {
               if (entityIn.f_20916_ > 0 || entityIn.f_20919_ > 0) {
                  Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
               }

               if (overlayProgress > 0.0F) {
                  Shaders.setEntityColor(overlayProgress, overlayProgress, overlayProgress, 0.5F);
               }
            }

            int i = m_115338_(entityIn, overlayProgress);
            this.f_115290_.a(matrixStackIn, vertexconsumer, packedLightIn, i, flag1 ? 654311423 : -1);
         }

         if (!entityIn.R_()) {
            for(Iterator var27 = this.f_115291_.iterator(); var27.hasNext(); renderItemHead = false) {
               C_4447_ renderlayer = (C_4447_)var27.next();
               if (renderlayer instanceof C_4425_) {
                  renderItemHead = true;
               }

               renderlayer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f4, partialTicks, f9, f2, f6);
            }
         }

         if (Config.isShaders()) {
            Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
         }

         matrixStackIn.m_85849_();
         super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
         if (Reflector.RenderLivingEvent_Post_Constructor.exists()) {
            Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn);
         }

      }
   }

   @Nullable
   protected C_4168_ m_7225_(C_524_ entityIn, boolean visibleIn, boolean translucentIn, boolean glowingIn) {
      C_5265_ resourcelocation = this.m_5478_(entityIn);
      if (this.getLocationTextureCustom() != null) {
         resourcelocation = this.getLocationTextureCustom();
      }

      if (translucentIn) {
         return C_4168_.m_110467_(resourcelocation);
      } else if (visibleIn) {
         return this.f_115290_.a(resourcelocation);
      } else if (glowingIn && !Config.getMinecraft().f_91060_.m_109817_()) {
         return this.f_115290_.a(resourcelocation);
      } else {
         return glowingIn ? C_4168_.m_110491_(resourcelocation) : null;
      }
   }

   public static int m_115338_(C_524_ livingEntityIn, float uIn) {
      return C_4474_.m_118093_(C_4474_.m_118088_(uIn), C_4474_.m_118096_(livingEntityIn.f_20916_ > 0 || livingEntityIn.f_20919_ > 0));
   }

   protected boolean m_5933_(C_524_ livingEntityIn) {
      return !livingEntityIn.ci();
   }

   private static float m_115328_(C_4687_ facingIn) {
      switch (facingIn) {
         case SOUTH:
            return 90.0F;
         case WEST:
            return 0.0F;
         case NORTH:
            return 270.0F;
         case EAST:
            return 180.0F;
         default:
            return 0.0F;
      }
   }

   protected boolean m_5936_(C_524_ entityIn) {
      return entityIn.cp();
   }

   protected void m_7523_(C_524_ entityLiving, C_3181_ matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float renderScale) {
      if (this.m_5936_(entityLiving)) {
         rotationYaw += (float)(Math.cos((double)entityLiving.ai * 3.25) * Math.PI * 0.4000000059604645);
      }

      if (!entityLiving.c(C_535_.SLEEPING)) {
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(180.0F - rotationYaw));
      }

      if (entityLiving.f_20919_ > 0) {
         float f = ((float)entityLiving.f_20919_ + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = C_188_.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(f * this.m_6441_(entityLiving)));
      } else if (entityLiving.m_21209_()) {
         matrixStackIn.m_252781_(C_252363_.f_252529_.m_252977_(-90.0F - entityLiving.dG()));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(((float)entityLiving.ai + partialTicks) * -75.0F));
      } else if (entityLiving.c(C_535_.SLEEPING)) {
         C_4687_ direction = entityLiving.m_21259_();
         float f1 = direction != null ? m_115328_(direction) : rotationYaw;
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(f1));
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(this.m_6441_(entityLiving)));
         matrixStackIn.m_252781_(C_252363_.f_252436_.m_252977_(270.0F));
      } else if (m_194453_(entityLiving)) {
         matrixStackIn.m_252880_(0.0F, (entityLiving.dk() + 0.1F) / renderScale, 0.0F);
         matrixStackIn.m_252781_(C_252363_.f_252403_.m_252977_(180.0F));
      }

   }

   protected float m_115342_(C_524_ livingBase, float partialTickTime) {
      return livingBase.m_21324_(partialTickTime);
   }

   protected float m_6930_(C_524_ livingBase, float partialTicks) {
      return (float)livingBase.ai + partialTicks;
   }

   protected float m_6441_(C_524_ entityLivingBaseIn) {
      return 90.0F;
   }

   protected float m_6931_(C_524_ livingEntityIn, float partialTicks) {
      return 0.0F;
   }

   protected void m_7546_(C_524_ entitylivingbaseIn, C_3181_ matrixStackIn, float partialTickTime) {
   }

   protected boolean m_6512_(C_524_ entity) {
      double d0 = this.f_114476_.m_114471_(entity);
      float f = entity.bZ() ? 32.0F : 64.0F;
      if (d0 >= (double)(f * f)) {
         return false;
      } else {
         C_3391_ minecraft = C_3391_.m_91087_();
         C_4105_ localplayer = minecraft.f_91074_;
         boolean flag = !entity.d(localplayer);
         if (entity != localplayer) {
            C_3078_ team = entity.ck();
            C_3078_ team1 = localplayer.ck();
            if (team != null) {
               C_3078_.C_3080_ team$visibility = team.m_7470_();
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

         return C_3391_.m_91404_() && entity != minecraft.m_91288_() && flag && !entity.bT();
      }
   }

   public static boolean m_194453_(C_524_ entityIn) {
      if (entityIn instanceof C_1141_ || entityIn.ai()) {
         String s = C_4856_.m_126649_(entityIn.ah().getString());
         if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
            return !(entityIn instanceof C_1141_) || ((C_1141_)entityIn).m_36170_(C_1144_.CAPE);
         }
      }

      return false;
   }

   protected float m_318622_(C_524_ entityIn) {
      return super.m_318622_(entityIn) * entityIn.m_6134_();
   }

   public C_4447_ getLayer(Class cls) {
      List list = this.getLayers(cls);
      return list.isEmpty() ? null : (C_4447_)list.get(0);
   }

   public List getLayers(Class cls) {
      List list = new ArrayList();
      Iterator var3 = this.f_115291_.iterator();

      while(var3.hasNext()) {
         C_4447_ layer = (C_4447_)var3.next();
         if (cls.isInstance(layer)) {
            list.add(layer);
         }
      }

      return list;
   }

   public void removeLayers(Class cls) {
      Iterator it = this.f_115291_.iterator();

      while(it.hasNext()) {
         C_4447_ layer = (C_4447_)it.next();
         if (cls.isInstance(layer)) {
            it.remove();
         }
      }

   }

   public static boolean isRenderItemHead() {
      return renderItemHead;
   }
}
