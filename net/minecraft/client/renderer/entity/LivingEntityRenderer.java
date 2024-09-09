package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import com.mojang.math.Axis;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.scores.Team;
import net.minecraft.world.scores.Team.Visibility;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;
import org.slf4j.Logger;

public abstract class LivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>>
   extends net.minecraft.client.renderer.entity.EntityRenderer<T>
   implements RenderLayerParent<T, M> {
   private static final Logger f_115289_ = LogUtils.getLogger();
   private static final float f_174287_ = 0.1F;
   public M f_115290_;
   protected final List<net.minecraft.client.renderer.entity.layers.RenderLayer<T, M>> f_115291_ = Lists.newArrayList();
   public float renderLimbSwing;
   public float renderLimbSwingAmount;
   public float renderAgeInTicks;
   public float renderHeadYaw;
   public float renderHeadPitch;
   public static final boolean animateModelLiving = Boolean.getBoolean("animate.model.living");
   private static boolean renderItemHead = false;

   public LivingEntityRenderer(Context contextIn, M entityModelIn, float shadowSizeIn) {
      super(contextIn);
      this.f_115290_ = entityModelIn;
      this.f_114477_ = shadowSizeIn;
   }

   public final boolean m_115326_(net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> layer) {
      return this.f_115291_.add(layer);
   }

   public M m_7200_() {
      return this.f_115290_;
   }

   public void m_7392_(
      T entityIn,
      float entityYaw,
      float partialTicks,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn
   ) {
      if (!Reflector.RenderLivingEvent_Pre_Constructor.exists()
         || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn)) {
         if (animateModelLiving) {
            entityIn.f_267362_.m_267771_(1.5F);
         }

         matrixStackIn.m_85836_();
         this.f_115290_.f_102608_ = this.m_115342_(entityIn, partialTicks);
         this.f_115290_.f_102609_ = entityIn.m_20159_();
         if (Reflector.IForgeEntity_shouldRiderSit.exists()) {
            this.f_115290_.f_102609_ = entityIn.m_20159_()
               && entityIn.m_20202_() != null
               && Reflector.callBoolean(entityIn.m_20202_(), Reflector.IForgeEntity_shouldRiderSit);
         }

         this.f_115290_.f_102610_ = entityIn.m_6162_();
         float f = net.minecraft.util.Mth.m_14189_(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
         float f1 = net.minecraft.util.Mth.m_14189_(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
         float f2 = f1 - f;
         if (this.f_115290_.f_102609_ && entityIn.m_20159_() && entityIn.m_20202_() instanceof LivingEntity livingentity) {
            f = net.minecraft.util.Mth.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
            f2 = f1 - f;
            float f7 = net.minecraft.util.Mth.m_14177_(f2);
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

         float f6 = net.minecraft.util.Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
         if (m_194453_(entityIn)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
         }

         f2 = net.minecraft.util.Mth.m_14177_(f2);
         if (entityIn.m_217003_(Pose.SLEEPING)) {
            net.minecraft.core.Direction direction = entityIn.m_21259_();
            if (direction != null) {
               float f3 = entityIn.m_20236_(Pose.STANDING) - 0.1F;
               matrixStackIn.m_252880_((float)(-direction.m_122429_()) * f3, 0.0F, (float)(-direction.m_122431_()) * f3);
            }
         }

         float f8 = entityIn.m_6134_();
         matrixStackIn.m_85841_(f8, f8, f8);
         float f9 = this.m_6930_(entityIn, partialTicks);
         this.m_7523_(entityIn, matrixStackIn, f9, f, partialTicks, f8);
         matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
         this.m_7546_(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.m_252880_(0.0F, -1.501F, 0.0F);
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
         Minecraft minecraft = Minecraft.m_91087_();
         boolean flag = this.m_5933_(entityIn);
         boolean flag1 = !flag && !entityIn.m_20177_(minecraft.f_91074_);
         boolean flag2 = minecraft.m_91314_(entityIn);
         net.minecraft.client.renderer.RenderType rendertype = this.m_7225_(entityIn, flag, flag1, flag2);
         if (rendertype != null) {
            com.mojang.blaze3d.vertex.VertexConsumer vertexconsumer = bufferIn.m_6299_(rendertype);
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
            this.f_115290_.m_7695_(matrixStackIn, vertexconsumer, packedLightIn, i, flag1 ? 654311423 : -1);
         }

         if (!entityIn.m_5833_()) {
            for (net.minecraft.client.renderer.entity.layers.RenderLayer<T, M> renderlayer : this.f_115291_) {
               if (renderlayer instanceof CustomHeadLayer) {
                  renderItemHead = true;
               }

               renderlayer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f4, partialTicks, f9, f2, f6);
               renderItemHead = false;
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
   protected net.minecraft.client.renderer.RenderType m_7225_(T entityIn, boolean visibleIn, boolean translucentIn, boolean glowingIn) {
      net.minecraft.resources.ResourceLocation resourcelocation = this.m_5478_(entityIn);
      if (this.getLocationTextureCustom() != null) {
         resourcelocation = this.getLocationTextureCustom();
      }

      if (translucentIn) {
         return net.minecraft.client.renderer.RenderType.m_110467_(resourcelocation);
      } else if (visibleIn) {
         return this.f_115290_.m_103119_(resourcelocation);
      } else if (glowingIn && !Config.getMinecraft().f_91060_.m_109817_()) {
         return this.f_115290_.m_103119_(resourcelocation);
      } else {
         return glowingIn ? net.minecraft.client.renderer.RenderType.m_110491_(resourcelocation) : null;
      }
   }

   public static int m_115338_(LivingEntity livingEntityIn, float uIn) {
      return OverlayTexture.m_118093_(OverlayTexture.m_118088_(uIn), OverlayTexture.m_118096_(livingEntityIn.f_20916_ > 0 || livingEntityIn.f_20919_ > 0));
   }

   protected boolean m_5933_(T livingEntityIn) {
      return !livingEntityIn.m_20145_();
   }

   private static float m_115328_(net.minecraft.core.Direction facingIn) {
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

   protected boolean m_5936_(T entityIn) {
      return entityIn.m_146890_();
   }

   protected void m_7523_(
      T entityLiving, com.mojang.blaze3d.vertex.PoseStack matrixStackIn, float ageInTicks, float rotationYaw, float partialTicks, float renderScale
   ) {
      if (this.m_5936_(entityLiving)) {
         rotationYaw += (float)(Math.cos((double)entityLiving.f_19797_ * 3.25) * Math.PI * 0.4F);
      }

      if (!entityLiving.m_217003_(Pose.SLEEPING)) {
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F - rotationYaw));
      }

      if (entityLiving.f_20919_ > 0) {
         float f = ((float)entityLiving.f_20919_ + partialTicks - 1.0F) / 20.0F * 1.6F;
         f = net.minecraft.util.Mth.m_14116_(f);
         if (f > 1.0F) {
            f = 1.0F;
         }

         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(f * this.m_6441_(entityLiving)));
      } else if (entityLiving.m_21209_()) {
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(-90.0F - entityLiving.m_146909_()));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(((float)entityLiving.f_19797_ + partialTicks) * -75.0F));
      } else if (entityLiving.m_217003_(Pose.SLEEPING)) {
         net.minecraft.core.Direction direction = entityLiving.m_21259_();
         float f1 = direction != null ? m_115328_(direction) : rotationYaw;
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(f1));
         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(this.m_6441_(entityLiving)));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(270.0F));
      } else if (m_194453_(entityLiving)) {
         matrixStackIn.m_252880_(0.0F, (entityLiving.m_20206_() + 0.1F) / renderScale, 0.0F);
         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(180.0F));
      }
   }

   protected float m_115342_(T livingBase, float partialTickTime) {
      return livingBase.m_21324_(partialTickTime);
   }

   protected float m_6930_(T livingBase, float partialTicks) {
      return (float)livingBase.f_19797_ + partialTicks;
   }

   protected float m_6441_(T entityLivingBaseIn) {
      return 90.0F;
   }

   protected float m_6931_(T livingEntityIn, float partialTicks) {
      return 0.0F;
   }

   protected void m_7546_(T entitylivingbaseIn, com.mojang.blaze3d.vertex.PoseStack matrixStackIn, float partialTickTime) {
   }

   protected boolean m_6512_(T entity) {
      double d0 = this.f_114476_.m_114471_(entity);
      float f = entity.m_20163_() ? 32.0F : 64.0F;
      if (d0 >= (double)(f * f)) {
         return false;
      } else {
         Minecraft minecraft = Minecraft.m_91087_();
         LocalPlayer localplayer = minecraft.f_91074_;
         boolean flag = !entity.m_20177_(localplayer);
         if (entity != localplayer) {
            Team team = entity.m_5647_();
            Team team1 = localplayer.m_5647_();
            if (team != null) {
               Visibility team$visibility = team.m_7470_();
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

         return Minecraft.m_91404_() && entity != minecraft.m_91288_() && flag && !entity.m_20160_();
      }
   }

   public static boolean m_194453_(LivingEntity entityIn) {
      if (entityIn instanceof Player || entityIn.m_8077_()) {
         String s = ChatFormatting.m_126649_(entityIn.m_7755_().getString());
         if ("Dinnerbone".equals(s) || "Grumm".equals(s)) {
            return !(entityIn instanceof Player) || ((Player)entityIn).m_36170_(PlayerModelPart.CAPE);
         }
      }

      return false;
   }

   protected float m_318622_(T entityIn) {
      return super.m_318622_(entityIn) * entityIn.m_6134_();
   }

   public <T extends net.minecraft.client.renderer.entity.layers.RenderLayer> T getLayer(Class<T> cls) {
      List<T> list = this.getLayers(cls);
      return (T)(list.isEmpty() ? null : list.get(0));
   }

   public <T extends net.minecraft.client.renderer.entity.layers.RenderLayer> List<T> getLayers(Class<T> cls) {
      List<net.minecraft.client.renderer.entity.layers.RenderLayer> list = new ArrayList();

      for (net.minecraft.client.renderer.entity.layers.RenderLayer layer : this.f_115291_) {
         if (cls.isInstance(layer)) {
            list.add(layer);
         }
      }

      return (List<T>)list;
   }

   public void removeLayers(Class cls) {
      Iterator it = this.f_115291_.iterator();

      while (it.hasNext()) {
         net.minecraft.client.renderer.entity.layers.RenderLayer layer = (net.minecraft.client.renderer.entity.layers.RenderLayer)it.next();
         if (cls.isInstance(layer)) {
            it.remove();
         }
      }
   }

   public static boolean isRenderItemHead() {
      return renderItemHead;
   }
}
