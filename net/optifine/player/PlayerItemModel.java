package net.optifine.player;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.Dimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private ResourceLocation textureLocation = null;
   private NativeImage textureImage = null;
   private DynamicTexture texture = null;
   private ResourceLocation locationMissing = new ResourceLocation("textures/block/red_wool.png");
   public static int ATTACH_BODY;
   public static int ATTACH_HEAD;
   public static int ATTACH_LEFT_ARM;
   public static int ATTACH_RIGHT_ARM;
   public static int ATTACH_LEFT_LEG;
   public static int ATTACH_RIGHT_LEG;
   public static int ATTACH_CAPE;

   public PlayerItemModel(Dimension textureSize, boolean usePlayerTexture, PlayerItemRenderer[] modelRenderers) {
      this.textureSize = textureSize;
      this.usePlayerTexture = usePlayerTexture;
      this.modelRenderers = modelRenderers;
   }

   public void m_324219_(
      HumanoidModel modelBiped, AbstractClientPlayer player, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, int packedOverlayIn
   ) {
      ResourceLocation locTex = this.locationMissing;
      if (this.usePlayerTexture) {
         locTex = player.getSkinTextureLocation();
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new DynamicTexture(this.textureImage);
            Minecraft.m_91087_().m_91097_().m_118495_(this.textureLocation, this.texture);
         }

         locTex = this.textureLocation;
      } else {
         locTex = this.locationMissing;
      }

      for (int i = 0; i < this.modelRenderers.length; i++) {
         PlayerItemRenderer pir = this.modelRenderers[i];
         matrixStackIn.m_85836_();
         RenderType renderType = RenderType.m_110458_(locTex);
         VertexConsumer buffer = bufferIn.m_6299_(renderType);
         pir.m_324219_(modelBiped, matrixStackIn, buffer, packedLightIn, packedOverlayIn);
         matrixStackIn.m_85849_();
      }
   }

   public static ModelPart getAttachModel(HumanoidModel modelBiped, int attachTo) {
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

   public NativeImage getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(NativeImage textureImage) {
      this.textureImage = textureImage;
   }

   public DynamicTexture getTexture() {
      return this.texture;
   }

   public ResourceLocation getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(ResourceLocation textureLocation) {
      this.textureLocation = textureLocation;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
