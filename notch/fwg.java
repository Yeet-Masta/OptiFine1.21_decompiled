package net.minecraft.src;

import java.util.function.Function;
import net.optifine.EmissiveTextures;

public abstract class C_3840_ {
   protected final Function<C_5265_, C_4168_> f_103106_;
   public int textureWidth = 64;
   public int textureHeight = 32;
   public C_5265_ locationTextureCustom;

   public C_3840_(Function<C_5265_, C_4168_> renderTypeIn) {
      this.f_103106_ = renderTypeIn;
   }

   public final C_4168_ m_103119_(C_5265_ locationIn) {
      C_4168_ type = (C_4168_)this.f_103106_.apply(locationIn);
      if (EmissiveTextures.isRenderEmissive() && type.isEntitySolid()) {
         type = C_4168_.m_110452_(locationIn);
      }

      return type;
   }

   public abstract void m_7695_(C_3181_ var1, C_3187_ var2, int var3, int var4, int var5);

   public final void m_340227_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, -1);
   }
}
