package net.minecraft.src;

import javax.annotation.Nullable;

public record C_290287_(C_5265_ f_290339_, @Nullable String f_290349_, @Nullable C_5265_ f_291348_, @Nullable C_5265_ f_290452_, C_290238_ f_290793_, boolean f_290871_) {
   public C_290287_(C_5265_ texture, @Nullable String textureUrl, @Nullable C_5265_ capeTexture, @Nullable C_5265_ elytraTexture, C_290238_ model, boolean secure) {
      this.f_290339_ = texture;
      this.f_290349_ = textureUrl;
      this.f_291348_ = capeTexture;
      this.f_290452_ = elytraTexture;
      this.f_290793_ = model;
      this.f_290871_ = secure;
   }

   public C_5265_ f_291348_() {
      return this.f_291348_;
   }

   public C_5265_ f_290452_() {
      return this.f_290452_;
   }

   public C_5265_ f_290339_() {
      return this.f_290339_;
   }

   @Nullable
   public String f_290349_() {
      return this.f_290349_;
   }

   public C_290238_ f_290793_() {
      return this.f_290793_;
   }

   public boolean f_290871_() {
      return this.f_290871_;
   }

   public static enum C_290238_ {
      SLIM("slim"),
      WIDE("default");

      private final String f_290420_;

      private C_290238_(final String idIn) {
         this.f_290420_ = idIn;
      }

      public static C_290238_ m_293021_(@Nullable String nameIn) {
         if (nameIn == null) {
            return WIDE;
         } else {
            C_290238_ var10000;
            switch (nameIn) {
               case "slim":
                  var10000 = SLIM;
                  break;
               default:
                  var10000 = WIDE;
            }

            return var10000;
         }
      }

      public String m_295601_() {
         return this.f_290420_;
      }

      // $FF: synthetic method
      private static C_290238_[] $values() {
         return new C_290238_[]{SLIM, WIDE};
      }
   }
}
