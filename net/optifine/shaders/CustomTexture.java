package net.optifine.shaders;

import com.mojang.blaze3d.platform.TextureUtil;

public class CustomTexture implements ICustomTexture {
   private int textureUnit = -1;
   private String path = null;
   private net.minecraft.client.renderer.texture.AbstractTexture texture = null;

   public CustomTexture(int textureUnit, String path, net.minecraft.client.renderer.texture.AbstractTexture texture) {
      this.textureUnit = textureUnit;
      this.path = path;
      this.texture = texture;
   }

   @Override
   public int getTextureUnit() {
      return this.textureUnit;
   }

   public String getPath() {
      return this.path;
   }

   public net.minecraft.client.renderer.texture.AbstractTexture getTexture() {
      return this.texture;
   }

   @Override
   public int getTextureId() {
      return this.texture.m_117963_();
   }

   @Override
   public void deleteTexture() {
      TextureUtil.releaseTextureId(this.texture.m_117963_());
   }

   public String toString() {
      return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.getTextureId();
   }
}
