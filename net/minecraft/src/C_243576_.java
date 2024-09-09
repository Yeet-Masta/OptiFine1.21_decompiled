package net.minecraft.src;

public interface C_243576_ extends AutoCloseable {
   void m_247697_(int var1, int var2);

   void close();

   default C_4486_ getSprite() {
      return null;
   }

   default void setSprite(C_4486_ sprite) {
   }
}
