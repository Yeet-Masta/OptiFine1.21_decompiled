package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_1391_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_520_;

public interface IClientItemExtensions {
   IClientItemExtensions DUMMY = new IClientItemExtensions() {
   };

   static IClientItemExtensions of(C_1391_ itemStack) {
      return DUMMY;
   }

   default boolean applyForgeHandTransform(
      PoseStack poseStack, C_4105_ player, C_520_ arm, C_1391_ itemInHand, float partialTick, float equipProcess, float swingProcess
   ) {
      return false;
   }

   default Font getFont(C_1391_ stack, IClientItemExtensions.FontContext context) {
      return null;
   }

   default BlockEntityWithoutLevelRenderer getCustomRenderer() {
      return C_3391_.m_91087_().ar().getBlockEntityRenderer();
   }

   public static enum FontContext {
      ITEM_COUNT,
      TOOLTIP,
      SELECTED_ITEM_NAME;
   }
}
