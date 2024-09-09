package net.optifine.shaders;

import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;

public class CustomTexture implements ICustomTexture {
   private int textureUnit = -1;
   private String path = null;
   private AbstractTexture texture = null;

   public CustomTexture(int textureUnit, String path, AbstractTexture texture) {
      this.textureUnit = textureUnit;
      this.path = path;
      this.texture = texture;
   }

   public int getTextureUnit() {
      return this.textureUnit;
   }

   public String getPath() {
      return this.path;
   }

   public AbstractTexture getTexture() {
      return this.texture;
   }

   public int getTextureId() {
      return this.texture.m_117963_();
   }

   public void deleteTexture() {
      TextureUtil.releaseTextureId(this.texture.m_117963_());
   }

   public String toString() {
      int var10000 = this.textureUnit;
      return "textureUnit: " + var10000 + ", path: " + this.path + ", glTextureId: " + this.getTextureId();
   }
}
