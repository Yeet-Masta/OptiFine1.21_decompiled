package net.optifine.player;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3829_;
import net.minecraft.src.C_4102_;
import net.minecraft.src.C_4139_;
import net.minecraft.src.C_4447_;
import net.minecraft.src.C_4462_;
import net.minecraft.src.C_4474_;
import net.minecraft.src.C_507_;
import net.optifine.Config;

public class PlayerItemsLayer extends C_4447_ {
   private C_4462_ renderPlayer = null;

   public PlayerItemsLayer(C_4462_ renderPlayer) {
      super(renderPlayer);
      this.renderPlayer = renderPlayer;
   }

   @Override
   public void m_6494_(
      C_3181_ matrixStackIn,
      C_4139_ bufferIn,
      int packedLightIn,
      C_507_ entitylivingbaseIn,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      this.renderEquippedItems(entitylivingbaseIn, matrixStackIn, bufferIn, packedLightIn, C_4474_.f_118083_);
   }

   protected void renderEquippedItems(C_507_ entityLiving, C_3181_ matrixStackIn, C_4139_ bufferIn, int packedLightIn, int packedOverlayIn) {
      if (Config.isShowCapes()) {
         if (!entityLiving.m_20145_()) {
            if (entityLiving instanceof C_4102_ player) {
               C_3829_ modelBipedMain = (C_3829_)this.renderPlayer.a();
               PlayerConfigurations.renderPlayerItems(modelBipedMain, player, matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            }
         }
      }
   }
}
