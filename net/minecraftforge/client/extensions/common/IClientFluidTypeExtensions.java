package net.minecraftforge.client.extensions.common;

import net.minecraft.src.C_1557_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraftforge.fluids.FluidType;

public interface IClientFluidTypeExtensions {
   IClientFluidTypeExtensions DUMMY = new IClientFluidTypeExtensions() {
   };

   // $FF: renamed from: of (net.minecraft.src.C_2691_) net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
   static IClientFluidTypeExtensions method_0(C_2691_ fluidState) {
      return DUMMY;
   }

   // $FF: renamed from: of (net.minecraftforge.fluids.FluidType) net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions
   static IClientFluidTypeExtensions method_1(FluidType fluidType) {
      return DUMMY;
   }

   default int getColorTint() {
      return -1;
   }

   default void renderOverlay(C_3391_ mc, C_3181_ stack) {
   }

   default int getTintColor(C_2691_ state, C_1557_ getter, C_4675_ pos) {
      return this.getColorTint();
   }
}
