package net.minecraft.src;

import javax.annotation.Nullable;

public record C_290287_(
   C_5265_ f_290339_, @Nullable String f_290349_, @Nullable C_5265_ f_291348_, @Nullable C_5265_ f_290452_, C_290287_.C_290238_ f_290793_, boolean f_290871_
) {
   public static enum C_290238_ {
      SLIM("slim"),
      WIDE("default");

      private final String f_290420_;

      private C_290238_(final String idIn) {
         this.f_290420_ = idIn;
      }

      public static C_290287_.C_290238_ m_293021_(@Nullable String nameIn) {
         if (nameIn == null) {
            return WIDE;
         } else {
            byte b0 = -1;
            switch (nameIn.hashCode()) {
               case 3533117:
                  if (nameIn.equals("slim")) {
                     b0 = 0;
                  }
               default:
                  return switch (b0) {
                     case 0 -> SLIM;
                     default -> WIDE;
                  };
            }
         }
      }

      public String m_295601_() {
         return this.f_290420_;
      }
   }
}
