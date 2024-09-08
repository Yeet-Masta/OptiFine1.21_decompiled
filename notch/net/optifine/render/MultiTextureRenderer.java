package net.optifine.render;

import com.mojang.blaze3d.platform.GlStateManager;
import java.nio.IntBuffer;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_3188_;
import net.minecraft.src.C_4484_;
import net.minecraft.src.C_4486_;
import net.optifine.Config;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import org.lwjgl.PointerBuffer;

public class MultiTextureRenderer {
   private static PointerBuffer bufferPositions = Config.createDirectPointerBuffer(1024);
   private static IntBuffer bufferCounts = Config.createDirectIntBuffer(1024);
   private static boolean shaders;

   public static void draw(C_3188_.C_141549_ drawMode, int indexTypeIn, MultiTextureData multiTextureData) {
      shaders = Config.isShaders();
      SpriteRenderData[] srds = multiTextureData.getSpriteRenderDatas();

      for (int i = 0; i < srds.length; i++) {
         SpriteRenderData srd = srds[i];
         draw(drawMode, indexTypeIn, srd);
      }
   }

   private static void draw(C_3188_.C_141549_ drawMode, int indexTypeIn, SpriteRenderData srd) {
      C_4486_ sprite = srd.getSprite();
      int[] positions = srd.getPositions();
      int[] counts = srd.getCounts();
      sprite.bindSpriteTexture();
      if (shaders) {
         int normalTex = sprite.spriteNormal != null ? sprite.spriteNormal.glSpriteTextureId : 0;
         int specularTex = sprite.spriteSpecular != null ? sprite.spriteSpecular.glSpriteTextureId : 0;
         C_4484_ at = sprite.getTextureAtlas();
         ShadersTex.bindNSTextures(normalTex, specularTex, at.isNormalBlend(), at.isSpecularBlend(), at.isMipmaps());
         if (Shaders.uniform_spriteBounds.isDefined()) {
            Shaders.uniform_spriteBounds.setValue(sprite.m_118409_(), sprite.m_118411_(), sprite.m_118410_(), sprite.m_118412_());
         }
      }

      if (bufferPositions.capacity() < positions.length) {
         int size = C_188_.m_14125_(positions.length);
         bufferPositions = Config.createDirectPointerBuffer(size);
         bufferCounts = Config.createDirectIntBuffer(size);
      }

      bufferPositions.clear();
      bufferCounts.clear();
      int indexSize = getIndexSize(indexTypeIn);

      for (int i = 0; i < positions.length; i++) {
         bufferPositions.put((long)(drawMode.m_166958_(positions[i]) * indexSize));
      }

      for (int i = 0; i < counts.length; i++) {
         bufferCounts.put(drawMode.m_166958_(counts[i]));
      }

      bufferPositions.flip();
      bufferCounts.flip();
      GlStateManager.glMultiDrawElements(drawMode.f_166946_, bufferCounts, indexTypeIn, bufferPositions);
   }

   private static int getIndexSize(int indexTypeIn) {
      if (indexTypeIn == 5125) {
         return 4;
      } else {
         return indexTypeIn == 5123 ? 2 : 1;
      }
   }
}
