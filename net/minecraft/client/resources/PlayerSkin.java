package net.minecraft.client.resources;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public record PlayerSkin(ResourceLocation f_290339_, @Nullable String f_290349_, @Nullable ResourceLocation f_291348_, @Nullable ResourceLocation f_290452_, Model f_290793_, boolean f_290871_) {
   public PlayerSkin(ResourceLocation texture, @Nullable String textureUrl, @Nullable ResourceLocation capeTexture, @Nullable ResourceLocation elytraTexture, Model model, boolean secure) {
      this.f_290339_ = texture;
      this.f_290349_ = textureUrl;
      this.f_291348_ = capeTexture;
      this.f_290452_ = elytraTexture;
      this.f_290793_ = model;
      this.f_290871_ = secure;
   }

   public ResourceLocation f_291348_() {
      return this.f_291348_;
   }

   public ResourceLocation f_290452_() {
      return this.f_290452_;
   }

   public ResourceLocation f_290339_() {
      return this.f_290339_;
   }

   @Nullable
   public String f_290349_() {
      return this.f_290349_;
   }

   public Model f_290793_() {
      return this.f_290793_;
   }

   public boolean f_290871_() {
      return this.f_290871_;
   }

   public static enum Model {
      SLIM("slim"),
      WIDE("default");

      private final String f_290420_;

      private Model(final String idIn) {
         this.f_290420_ = idIn;
      }

      public static Model m_293021_(@Nullable String nameIn) {
         if (nameIn == null) {
            return WIDE;
         } else {
            Model var10000;
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
      private static Model[] $values() {
         return new Model[]{SLIM, WIDE};
      }
   }
}
