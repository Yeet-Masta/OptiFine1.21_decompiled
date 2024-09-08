package net.minecraft.client.renderer.texture;

public interface SpriteTicker extends AutoCloseable {
   void m_247697_(int var1, int var2);

   void close();

   default TextureAtlasSprite getSprite() {
      return null;
   }

   default void setSprite(TextureAtlasSprite sprite) {
   }
}
