package net.optifine.player;

import java.awt.Dimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private net.minecraft.resources.ResourceLocation textureLocation = null;
   private com.mojang.blaze3d.platform.NativeImage textureImage = null;
   private net.minecraft.client.renderer.texture.DynamicTexture texture = null;
   private net.minecraft.resources.ResourceLocation locationMissing = new net.minecraft.resources.ResourceLocation("textures/block/red_wool.png");
   public static final int ATTACH_BODY = 0;
   public static final int ATTACH_HEAD = 1;
   public static final int ATTACH_LEFT_ARM = 2;
   public static final int ATTACH_RIGHT_ARM = 3;
   public static final int ATTACH_LEFT_LEG = 4;
   public static final int ATTACH_RIGHT_LEG = 5;
   public static final int ATTACH_CAPE = 6;

   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
      this.textureSize = textureSize;
      this.usePlayerTexture = usePlayerTexture;
      this.modelRenderers = modelRenderers;
   }

   public void render(
      HumanoidModel modelBiped,
      net.minecraft.client.player.AbstractClientPlayer player,
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn,
      net.minecraft.client.renderer.MultiBufferSource bufferIn,
      int packedLightIn,
      int packedOverlayIn
   ) {
      net.minecraft.resources.ResourceLocation locTex = this.locationMissing;
      if (this.usePlayerTexture) {
         locTex = player.getSkinTextureLocation();
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new net.minecraft.client.renderer.texture.DynamicTexture(this.textureImage);
            Minecraft.m_91087_().m_91097_().m_118495_(this.textureLocation, this.texture);
         }

         locTex = this.textureLocation;
      } else {
         locTex = this.locationMissing;
      }

      for (int i = 0; i < this.modelRenderers.length; i++) {
         PlayerItemRenderer pir = this.modelRenderers[i];
         matrixStackIn.m_85836_();
         net.minecraft.client.renderer.RenderType renderType = net.minecraft.client.renderer.RenderType.m_110458_(locTex);
         com.mojang.blaze3d.vertex.VertexConsumer buffer = bufferIn.m_6299_(renderType);
         pir.render(modelBiped, matrixStackIn, buffer, packedLightIn, packedOverlayIn);
         matrixStackIn.m_85849_();
      }
   }

   public static net.minecraft.client.model.geom.ModelPart getAttachModel(HumanoidModel modelBiped, int attachTo) {
      switch (attachTo) {
         case 0:
            return modelBiped.f_102810_;
         case 1:
            return modelBiped.f_102808_;
         case 2:
            return modelBiped.f_102812_;
         case 3:
            return modelBiped.f_102811_;
         case 4:
            return modelBiped.f_102814_;
         case 5:
            return modelBiped.f_102813_;
         default:
            return null;
      }
   }

   public com.mojang.blaze3d.platform.NativeImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(com.mojang.blaze3d.platform.NativeImage textureImage) {
      this.textureImage = textureImage;
   }

   public net.minecraft.client.renderer.texture.DynamicTexture getTexture() {
      return this.texture;
   }

   public net.minecraft.resources.ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(net.minecraft.resources.ResourceLocation textureLocation) {
      this.textureLocation = textureLocation;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
