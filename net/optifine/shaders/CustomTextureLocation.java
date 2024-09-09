package net.optifine.shaders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class CustomTextureLocation implements ICustomTexture {
   private int textureUnit = -1;
   private ResourceLocation location;
   private int variant = 0;
   private AbstractTexture texture;
   public static final int VARIANT_BASE = 0;
   public static final int VARIANT_NORMAL = 1;
   public static final int VARIANT_SPECULAR = 2;

   public CustomTextureLocation(int textureUnit, ResourceLocation location, int variant) {
      this.textureUnit = textureUnit;
      this.location = location;
      this.variant = variant;
   }

   public AbstractTexture getTexture() {
      if (this.texture == null) {
         TextureManager textureManager = Minecraft.m_91087_().m_91097_();
         this.texture = textureManager.m_118506_(this.location);
         if (this.texture == null) {
            this.texture = new SimpleTexture(this.location);
            textureManager.m_118495_(this.location, this.texture);
            this.texture = textureManager.m_118506_(this.location);
         }
      }

      return this.texture;
   }

   public void reloadTexture() {
      this.texture = null;
   }

   public int getTextureId() {
      AbstractTexture tex = this.getTexture();
      if (this.variant != 0 && tex instanceof AbstractTexture) {
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

   public int getTextureUnit() {
      return this.textureUnit;
   }

   public void deleteTexture() {
   }

   public String toString() {
      int var10000 = this.textureUnit;
      return "textureUnit: " + var10000 + ", location: " + String.valueOf(this.location) + ", glTextureId: " + String.valueOf(this.texture != null ? this.texture.m_117963_() : "");
   }
}
