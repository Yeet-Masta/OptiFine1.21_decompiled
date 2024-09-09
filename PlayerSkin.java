import javax.annotation.Nullable;

public record PlayerSkin(ResourceLocation a, @Nullable String b, @Nullable ResourceLocation c, @Nullable ResourceLocation d, PlayerSkin.a e, boolean f) {
   public static enum a {
      a("slim"),
      b("default");

      private final String c;

      private a(final String idIn) {
         this.c = idIn;
      }

      public static PlayerSkin.a a(@Nullable String nameIn) {
         if (nameIn == null) {
            return b;
         } else {
            byte b0 = -1;
            switch (nameIn.hashCode()) {
               case 3533117:
                  if (nameIn.equals("slim")) {
                     b0 = 0;
                  }
               default:
                  return switch (b0) {
                     case 0 -> a;
                     default -> b;
                  };
            }
         }
      }

      public String a() {
         return this.c;
      }
   }
}
