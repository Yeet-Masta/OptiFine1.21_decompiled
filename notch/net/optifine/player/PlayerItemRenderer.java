package net.optifine.player;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_3889_;

public class PlayerItemRenderer {
   private int attachTo = 0;
   private C_3889_ modelRenderer = null;

   public PlayerItemRenderer(int attachTo, C_3889_ modelRenderer) {
      this.attachTo = attachTo;
      this.modelRenderer = modelRenderer;
   }

   public C_3889_ getModelRenderer() {
      return this.modelRenderer;
   }

   public void render(C_3829_ modelBiped, C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn) {
      C_3889_ attachModel = PlayerItemModel.getAttachModel(modelBiped, this.attachTo);
      if (attachModel != null) {
         attachModel.m_104299_(matrixStackIn);
      }

      this.modelRenderer.m_104301_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
   }
}
