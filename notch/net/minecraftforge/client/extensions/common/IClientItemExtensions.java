package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_1391_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3429_;
import net.minecraft.src.C_4105_;
import net.minecraft.src.C_4109_;
import net.minecraft.src.C_520_;

public interface IClientItemExtensions {
   IClientItemExtensions DUMMY = new IClientItemExtensions() {
   };

   static IClientItemExtensions of(C_1391_ itemStack) {
      return DUMMY;
   }

   default boolean applyForgeHandTransform(
      C_3181_ poseStack, C_4105_ player, C_520_ arm, C_1391_ itemInHand, float partialTick, float equipProcess, float swingProcess
   ) {
      return false;
   }

   default C_3429_ getFont(C_1391_ stack, IClientItemExtensions.FontContext context) {
      return null;
   }

   default C_4109_ getCustomRenderer() {
      return C_3391_.m_91087_().m_91291_().getBlockEntityRenderer();
   }

   public static enum FontContext {
      ITEM_COUNT,
      TOOLTIP,
      SELECTED_ITEM_NAME;
   }
}
