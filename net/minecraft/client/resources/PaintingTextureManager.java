package net.minecraft.client.resources;

import net.minecraft.world.entity.decoration.PaintingVariant;
import net.optifine.util.TextureUtils;

public class PaintingTextureManager extends TextureAtlasHolder {
   private static final net.minecraft.resources.ResourceLocation f_118799_ = net.minecraft.resources.ResourceLocation.m_340282_("back");

   public PaintingTextureManager(net.minecraft.client.renderer.texture.TextureManager textureManagerIn) {
      super(
         textureManagerIn,
         net.minecraft.resources.ResourceLocation.m_340282_("textures/atlas/paintings.png"),
         net.minecraft.resources.ResourceLocation.m_340282_("paintings")
      );
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite m_235033_(PaintingVariant variantIn) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = this.m_118901_(variantIn.f_337196_());
      return TextureUtils.getCustomSprite(sprite);
   }

   public net.minecraft.client.renderer.texture.TextureAtlasSprite m_118806_() {
      return this.m_118901_(f_118799_);
   }
}
