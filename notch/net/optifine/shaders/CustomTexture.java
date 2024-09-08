package net.optifine.shaders;

import com.mojang.blaze3d.platform.TextureUtil;
import net.minecraft.src.C_4468_;

public class CustomTexture implements ICustomTexture {
   private int textureUnit = -1;
   private String path = null;
   private C_4468_ texture = null;

   public CustomTexture(int textureUnit, String path, C_4468_ texture) {
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

   public C_4468_ getTexture() {
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
