package net.minecraft.client.resources;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.optifine.util.TextureUtils;

public class PaintingTextureManager extends TextureAtlasHolder {
   private static final ResourceLocation f_118799_ = ResourceLocation.m_340282_("back");

   public PaintingTextureManager(TextureManager textureManagerIn) {
      super(textureManagerIn, ResourceLocation.m_340282_("textures/atlas/paintings.png"), ResourceLocation.m_340282_("paintings"));
   }

   public TextureAtlasSprite m_235033_(PaintingVariant variantIn) {
      TextureAtlasSprite sprite = this.m_118901_(variantIn.f_337196_());
      sprite = TextureUtils.getCustomSprite(sprite);
      return sprite;
   }

   public TextureAtlasSprite m_118806_() {
      return this.m_118901_(f_118799_);
   }
}
