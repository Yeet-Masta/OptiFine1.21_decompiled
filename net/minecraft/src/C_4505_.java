package net.minecraft.src;

import net.optifine.util.TextureUtils;

public class C_4505_ extends C_4510_ {
   private static final C_5265_ f_118799_ = C_5265_.m_340282_("back");

   public C_4505_(C_4490_ textureManagerIn) {
      super(textureManagerIn, C_5265_.m_340282_("textures/atlas/paintings.png"), C_5265_.m_340282_("paintings"));
   }

   public C_4486_ m_235033_(C_213053_ variantIn) {
      C_4486_ sprite = this.m_118901_(variantIn.f_337196_());
      sprite = TextureUtils.getCustomSprite(sprite);
      return sprite;
   }

   public C_4486_ m_118806_() {
      return this.m_118901_(f_118799_);
   }
}
