package net.minecraft.client.resources;

import javax.annotation.Nullable;

public record PlayerSkin(
   net.minecraft.resources.ResourceLocation f_290339_,
   @Nullable String f_290349_,
   @Nullable net.minecraft.resources.ResourceLocation f_291348_,
   @Nullable net.minecraft.resources.ResourceLocation f_290452_,
   net.minecraft.client.resources.PlayerSkin.Model f_290793_,
   boolean f_290871_
) {
   public static enum Model {
      SLIM("slim"),
      WIDE("default");

      private final String f_290420_;

      private Model(final String idIn) {
         this.f_290420_ = idIn;
      }

      public static net.minecraft.client.resources.PlayerSkin.Model m_293021_(@Nullable String nameIn) {
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
