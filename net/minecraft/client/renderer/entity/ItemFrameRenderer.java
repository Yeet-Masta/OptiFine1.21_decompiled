package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.saveddata.maps.MapId;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.phys.Vec3;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.Shaders;

public class ItemFrameRenderer extends EntityRenderer {
   private static final ModelResourceLocation f_115044_ = ModelResourceLocation.m_245263_("item_frame", "map=false");
   private static final ModelResourceLocation f_115045_ = ModelResourceLocation.m_245263_("item_frame", "map=true");
   private static final ModelResourceLocation f_174201_ = ModelResourceLocation.m_245263_("glow_item_frame", "map=false");
   private static final ModelResourceLocation f_174202_ = ModelResourceLocation.m_245263_("glow_item_frame", "map=true");
   public static final int f_174199_ = 5;
   public static final int f_174200_ = 30;
   private final ItemRenderer f_115047_;
   private final BlockRenderDispatcher f_234645_;
   private static double itemRenderDistanceSq = 4096.0;
   private static boolean renderItemFrame = false;
   // $FF: renamed from: mc net.minecraft.client.Minecraft
   private static Minecraft field_32 = Minecraft.m_91087_();

   public ItemFrameRenderer(EntityRendererProvider.Context contextIn) {
      super(contextIn);
      this.f_115047_ = contextIn.m_174025_();
      this.f_234645_ = contextIn.m_234597_();
   }

   protected int m_6086_(ItemFrame entityIn, BlockPos partialTicks) {
      return entityIn.m_6095_() == EntityType.f_147033_ ? Math.max(5, super.m_6086_(entityIn, partialTicks)) : super.m_6086_(entityIn, partialTicks);
   }

   public void m_7392_(ItemFrame entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
      matrixStackIn.m_85836_();
      Direction direction = entityIn.m_6350_();
      Vec3 vec3 = this.m_7860_(entityIn, partialTicks);
      matrixStackIn.m_85837_(-vec3.m_7096_(), -vec3.m_7098_(), -vec3.m_7094_());
      double d0 = 0.46875;
      matrixStackIn.m_85837_((double)direction.m_122429_() * 0.46875, (double)direction.m_122430_() * 0.46875, (double)direction.m_122431_() * 0.46875);
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(entityIn.m_146909_()));
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F - entityIn.m_146908_()));
      boolean flag = entityIn.m_20145_();
      ItemStack itemstack = entityIn.m_31822_();
      if (!flag) {
         ModelManager modelmanager = this.f_234645_.m_110907_().m_110881_();
         ModelResourceLocation modelresourcelocation = this.m_174212_(entityIn, itemstack);
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(-0.5F, -0.5F, -0.5F);
         this.f_234645_.m_110937_().m_111067_(matrixStackIn.m_85850_(), bufferIn.m_6299_(Sheets.m_110789_()), (BlockState)null, modelmanager.m_119422_(modelresourcelocation), 1.0F, 1.0F, 1.0F, packedLightIn, OverlayTexture.f_118083_);
         matrixStackIn.m_85849_();
      }

      if (!itemstack.m_41619_()) {
         MapId mapid = entityIn.m_218868_(itemstack);
         if (flag) {
            matrixStackIn.m_252880_(0.0F, 0.0F, 0.5F);
         } else {
            matrixStackIn.m_252880_(0.0F, 0.0F, 0.4375F);
         }

         int j = mapid != null ? entityIn.m_31823_() % 4 * 2 : entityIn.m_31823_();
         matrixStackIn.m_252781_(Axis.f_252403_.m_252977_((float)j * 360.0F / 8.0F));
         if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, entityIn, this, matrixStackIn, bufferIn, packedLightIn)) {
            if (mapid != null) {
               matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(180.0F));
               float f = 0.0078125F;
               matrixStackIn.m_85841_(0.0078125F, 0.0078125F, 0.0078125F);
               matrixStackIn.m_252880_(-64.0F, -64.0F, 0.0F);
               MapItemSavedData mapitemsaveddata = MapItem.m_151128_(mapid, entityIn.m_9236_());
               matrixStackIn.m_252880_(0.0F, 0.0F, -1.0F);
               if (mapitemsaveddata != null) {
                  int i = this.m_174208_(entityIn, 15728850, packedLightIn);
                  Minecraft.m_91087_().f_91063_.m_109151_().m_168771_(matrixStackIn, bufferIn, mapid, mapitemsaveddata, true, i);
               }
            } else {
               int k = this.m_174208_(entityIn, 15728880, packedLightIn);
               matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
               renderItemFrame = true;
               if (this.isRenderItem(entityIn)) {
                  this.f_115047_.m_269128_(itemstack, ItemDisplayContext.FIXED, k, OverlayTexture.f_118083_, matrixStackIn, bufferIn, entityIn.m_9236_(), entityIn.m_19879_());
               }

               renderItemFrame = false;
            }
         }
      }

      matrixStackIn.m_85849_();
   }

   private int m_174208_(ItemFrame itemFrameIn, int lightGlowIn, int lightIn) {
      return itemFrameIn.m_6095_() == EntityType.f_147033_ ? lightGlowIn : lightIn;
   }

   private ModelResourceLocation m_174212_(ItemFrame itemFrameIn, ItemStack itemStackIn) {
      boolean flag = itemFrameIn.m_6095_() == EntityType.f_147033_;
      if (itemStackIn.m_41720_() instanceof MapItem) {
         return flag ? f_174202_ : f_115045_;
      } else {
         return flag ? f_174201_ : f_115044_;
      }
   }

   public Vec3 m_7860_(ItemFrame entityIn, float partialTicks) {
      return new Vec3((double)((float)entityIn.m_6350_().m_122429_() * 0.3F), -0.25, (double)((float)entityIn.m_6350_().m_122431_() * 0.3F));
   }

   public ResourceLocation m_5478_(ItemFrame entity) {
      return TextureAtlas.f_118259_;
   }

   protected boolean m_6512_(ItemFrame entity) {
      if (Minecraft.m_91404_() && !entity.m_31822_().m_41619_() && entity.m_31822_().m_319951_(DataComponents.f_316016_) && this.f_114476_.f_114359_ == entity) {
         double d0 = this.f_114476_.m_114471_(entity);
         float f = entity.m_20163_() ? 32.0F : 64.0F;
         return d0 < (double)(f * f);
      } else {
         return false;
      }
   }

   protected void m_7649_(ItemFrame entityIn, Component displayNameIn, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, float partialTicks) {
      super.m_7649_(entityIn, entityIn.m_31822_().m_41786_(), matrixStackIn, bufferIn, packedLightIn, partialTicks);
   }

   private boolean isRenderItem(ItemFrame itemFrame) {
      if (Shaders.isShadowPass) {
         return false;
      } else {
         if (!Config.zoomMode) {
            Entity viewEntity = field_32.m_91288_();
            double distSq = itemFrame.m_20275_(viewEntity.m_20185_(), viewEntity.m_20186_(), viewEntity.m_20189_());
            if (distSq > itemRenderDistanceSq) {
               return false;
            }
         }

         return true;
      }
   }

   public static void updateItemRenderDistance() {
      Minecraft mc = Minecraft.m_91087_();
      double fov = (double)Config.limit((Integer)mc.f_91066_.m_231837_().m_231551_(), 1, 120);
      double itemRenderDistance = Math.max(6.0 * (double)mc.m_91268_().m_85444_() / fov, 16.0);
      itemRenderDistanceSq = itemRenderDistance * itemRenderDistance;
   }

   public static boolean isRenderItemFrame() {
      return renderItemFrame;
   }
}
