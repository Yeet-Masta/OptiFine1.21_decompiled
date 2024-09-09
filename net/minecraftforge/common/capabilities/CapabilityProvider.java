package net.minecraftforge.common.capabilities;

import net.minecraft.src.C_4917_;

public abstract class CapabilityProvider<B> {
   protected CapabilityProvider(Class<B> baseClass) {
   }

   public final void gatherCapabilities() {
   }

   protected final CapabilityDispatcher getCapabilities() {
      return null;
   }

   protected final void deserializeCaps(C_4917_ tag) {
   }

   protected final C_4917_ serializeCaps() {
      return null;
   }

   public void invalidateCaps() {
   }
}
