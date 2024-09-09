package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_3657_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_498_;

public interface IClientMobEffectExtensions {
   IClientMobEffectExtensions DUMMY = new IClientMobEffectExtensions() {
   };

   static IClientMobEffectExtensions of(C_498_ mobeffectinstance) {
      return DUMMY;
   }

   static IClientMobEffectExtensions of(C_496_ effect) {
      return DUMMY;
   }

   default boolean isVisibleInInventory(C_498_ instance) {
      return true;
   }

   default boolean isVisibleInGui(C_498_ instance) {
      return true;
   }

   default boolean renderInventoryIcon(C_498_ instance, C_3657_<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
      return false;
   }

   default boolean renderInventoryText(C_498_ instance, C_3657_<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
      return false;
   }

   default boolean renderGuiIcon(C_498_ instance, Gui gui, GuiGraphics guiGraphics, int x, int y, float z, float alpha) {
      return false;
   }
}
