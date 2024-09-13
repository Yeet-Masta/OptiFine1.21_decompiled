package net.optifine.texture;

public enum TextureType {
   TEXTURE_1D(3552),
   TEXTURE_2D(3553),
   TEXTURE_3D(32879),
   TEXTURE_RECTANGLE(34037);

   private int f_11893_;

   private TextureType(int id) {
      this.f_11893_ = id;
   }

   public int getId() {
      return this.f_11893_;
   }
}
