package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_279497_;
import net.minecraft.src.C_3431_;
import net.minecraft.src.C_3657_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_498_;

public interface IClientMobEffectExtensions {
   IClientMobEffectExtensions DUMMY = new IClientMobEffectExtensions() {
   };

   // $FF: renamed from: of (net.minecraft.src.C_498_) net.minecraftforge.client.extensions.common.IClientMobEffectExtensions
   static IClientMobEffectExtensions method_3(C_498_ mobeffectinstance) {
      return DUMMY;
   }

   // $FF: renamed from: of (net.minecraft.src.C_496_) net.minecraftforge.client.extensions.common.IClientMobEffectExtensions
   static IClientMobEffectExtensions method_4(C_496_ effect) {
      return DUMMY;
   }

   default boolean isVisibleInInventory(C_498_ instance) {
      return true;
   }

   default boolean isVisibleInGui(C_498_ instance) {
      return true;
   }

   default boolean renderInventoryIcon(C_498_ instance, C_3657_ screen, C_279497_ guiGraphics, int x, int y, int blitOffset) {
      return false;
   }

   default boolean renderInventoryText(C_498_ instance, C_3657_ screen, C_279497_ guiGraphics, int x, int y, int blitOffset) {
      return false;
   }

   default boolean renderGuiIcon(C_498_ instance, C_3431_ gui, C_279497_ guiGraphics, int x, int y, float z, float alpha) {
      return false;
   }
}
