package net.optifine.player;

import java.awt.Dimension;
import net.minecraft.src.C_3148_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4139_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4470_;
import net.minecraft.src.C_5265_;

public class PlayerItemModel {
   private Dimension textureSize = null;
   private boolean usePlayerTexture = false;
   private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];
   private C_5265_ textureLocation = null;
   private C_3148_ textureImage = null;
   private C_4470_ texture = null;
   private C_5265_ locationMissing = new C_5265_("textures/block/red_wool.png");
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

   public void render(C_3829_ modelBiped, C_4102_ player, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, int packedOverlayIn) {
      C_5265_ locTex = this.locationMissing;
      if (this.usePlayerTexture) {
         locTex = player.getSkinTextureLocation();
      } else if (this.textureLocation != null) {
         if (this.texture == null && this.textureImage != null) {
            this.texture = new C_4470_(this.textureImage);
            C_3391_.m_91087_().m_91097_().m_118495_(this.textureLocation, this.texture);
         }

         locTex = this.textureLocation;
      } else {
         locTex = this.locationMissing;
      }

      for (int i = 0; i < this.modelRenderers.length; i++) {
         PlayerItemRenderer pir = this.modelRenderers[i];
         matrixStackIn.m_85836_();
         C_4168_ renderType = C_4168_.m_110458_(locTex);
         C_3187_ buffer = bufferIn.m_6299_(renderType);
         pir.render(modelBiped, matrixStackIn, buffer, packedLightIn, packedOverlayIn);
         matrixStackIn.m_85849_();
      }
   }

   public static C_3889_ getAttachModel(C_3829_ modelBiped, int attachTo) {
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

   public C_3148_ getTextureImage() {
      return this.textureImage;
   }

   public void setTextureImage(C_3148_ textureImage) {
      this.textureImage = textureImage;
   }

   public C_4470_ getTexture() {
      return this.texture;
   }

   public C_5265_ getTextureLocation() {
      return this.textureLocation;
   }

   public void setTextureLocation(C_5265_ textureLocation) {
      this.textureLocation = textureLocation;
   }

   public boolean isUsePlayerTexture() {
      return this.usePlayerTexture;
   }
}
