package net.optifine.shaders;

import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4468_;
import net.minecraft.src.C_4476_;
import net.minecraft.src.C_4490_;
import net.minecraft.src.C_5265_;

public class CustomTextureLocation implements ICustomTexture {
   private int textureUnit = -1;
   private C_5265_ location;
   private int variant = 0;
   private C_4468_ texture;
   public static final int VARIANT_BASE = 0;
   public static final int VARIANT_NORMAL = 1;
   public static final int VARIANT_SPECULAR = 2;

   public CustomTextureLocation(int textureUnit, C_5265_ location, int variant) {
      this.textureUnit = textureUnit;
      this.location = location;
      this.variant = variant;
   }

   public C_4468_ getTexture() {
      if (this.texture == null) {
         C_4490_ textureManager = C_3391_.m_91087_().m_91097_();
         this.texture = textureManager.m_118506_(this.location);
         if (this.texture == null) {
            this.texture = new C_4476_(this.location);
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
      C_4468_ tex = this.getTexture();
      if (this.variant != 0 && tex instanceof C_4468_) {
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
