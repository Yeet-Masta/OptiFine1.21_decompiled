package net.minecraft.client;

import net.minecraft.src.C_141833_;
import net.optifine.reflect.Reflector;

public class ClientBrandRetriever {
   public static String f_177870_;

   @C_141833_
   public static String getClientModName() {
      return Reflector.BrandingControl_getClientBranding.exists() ? Reflector.BrandingControl_getClientBranding.callString() : "optifine";
   }
}
