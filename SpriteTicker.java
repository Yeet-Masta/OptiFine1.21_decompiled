public interface SpriteTicker extends AutoCloseable {
   void a(int var1, int var2);

   void close();

   default TextureAtlasSprite getSprite() {
      return null;
   }

   default void setSprite(TextureAtlasSprite sprite) {
   }
}
