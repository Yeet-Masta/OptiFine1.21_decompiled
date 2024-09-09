package net.optifine.shaders;

import net.minecraft.client.Minecraft;

public class CustomTextureLocation implements ICustomTexture {
   private int textureUnit = -1;
   private net.minecraft.resources.ResourceLocation location;
   private int variant = 0;
   private net.minecraft.client.renderer.texture.AbstractTexture texture;
   public static final int VARIANT_BASE = 0;
   public static final int VARIANT_NORMAL = 1;
   public static final int VARIANT_SPECULAR = 2;

   public CustomTextureLocation(int textureUnit, net.minecraft.resources.ResourceLocation location, int variant) {
      this.textureUnit = textureUnit;
      this.location = location;
      this.variant = variant;
   }

   public net.minecraft.client.renderer.texture.AbstractTexture getTexture() {
      if (this.texture == null) {
         net.minecraft.client.renderer.texture.TextureManager textureManager = Minecraft.m_91087_().m_91097_();
         this.texture = textureManager.m_118506_(this.location);
         if (this.texture == null) {
            this.texture = new net.minecraft.client.renderer.texture.SimpleTexture(this.location);
            textureManager.m_118495_(this.location, this.texture);
            this.texture = textureManager.m_118506_(this.location);
         }
      }

      return this.texture;
   }

   public void reloadTexture() {
      this.texture = null;
   }

   @Override
   public int getTextureId() {
      net.minecraft.client.renderer.texture.AbstractTexture tex = this.getTexture();
      if (this.variant != 0 && tex instanceof net.minecraft.client.renderer.texture.AbstractTexture) {
         MultiTexID mtid = tex.multiTex;
         if (mtid != null) {
            if (this.variant == 1) {
               return mtid.norm;
            }

            if (this.variant == 2) {
               return mtid.spec;
            }
         }
      }

      return tex.m_117963_();
   }

   @Override
   public int getTextureUnit() {
      return this.textureUnit;
   }

   @Override
   public void deleteTexture() {
   }

   public String toString() {
      return "textureUnit: " + this.textureUnit + ", location: " + this.location + ", glTextureId: " + (this.texture != null ? this.texture.m_117963_() : "");
   }
}
