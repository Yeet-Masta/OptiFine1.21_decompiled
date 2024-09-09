package net.optifine.texture;

public enum PixelFormat {
   RED(6403),
   // $FF: renamed from: RG net.optifine.texture.PixelFormat
   field_66(33319),
   RGB(6407),
   BGR(32992),
   RGBA(6408),
   BGRA(32993),
   RED_INTEGER(36244),
   RG_INTEGER(33320),
   RGB_INTEGER(36248),
   BGR_INTEGER(36250),
   RGBA_INTEGER(36249),
   BGRA_INTEGER(36251);

   // $FF: renamed from: id int
   private int field_67;

   private PixelFormat(int id) {
      this.field_67 = id;
   }

   public int getId() {
      return this.field_67;
   }

   // $FF: synthetic method
   private static PixelFormat[] $values() {
      return new PixelFormat[]{RED, field_66, RGB, BGR, RGBA, BGRA, RED_INTEGER, RG_INTEGER, RGB_INTEGER, BGR_INTEGER, RGBA_INTEGER, BGRA_INTEGER};
   }
}
