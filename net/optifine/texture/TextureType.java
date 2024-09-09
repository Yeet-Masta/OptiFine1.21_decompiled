package net.optifine.texture;

public enum TextureType {
   TEXTURE_1D(3552),
   TEXTURE_2D(3553),
   TEXTURE_3D(32879),
   TEXTURE_RECTANGLE(34037);

   // $FF: renamed from: id int
   private int field_33;

   private TextureType(int id) {
      this.field_33 = id;
   }

   public int getId() {
      return this.field_33;
   }

   // $FF: synthetic method
   private static TextureType[] $values() {
      return new TextureType[]{TEXTURE_1D, TEXTURE_2D, TEXTURE_3D, TEXTURE_RECTANGLE};
   }
}
