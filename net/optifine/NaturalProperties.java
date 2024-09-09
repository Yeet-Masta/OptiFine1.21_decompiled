package net.optifine;

import java.util.IdentityHashMap;
import java.util.Map;

public class NaturalProperties {
   public int rotation = 1;
   public boolean flip = false;
   private Map[] quadMaps = new Map[8];

   public NaturalProperties(String type) {
      if (type.equals("4")) {
         this.rotation = 4;
      } else if (type.equals("2")) {
         this.rotation = 2;
      } else if (type.equals("F")) {
         this.flip = true;
      } else if (type.equals("4F")) {
         this.rotation = 4;
         this.flip = true;
      } else if (type.equals("2F")) {
         this.rotation = 2;
         this.flip = true;
      } else {
         Config.warn("NaturalTextures: Unknown type: " + type);
      }
   }

   public boolean isValid() {
      return this.rotation == 2 || this.rotation == 4 ? true : this.flip;
   }

   public synchronized net.minecraft.client.renderer.block.model.BakedQuad getQuad(
      net.minecraft.client.renderer.block.model.BakedQuad quadIn, int rotate, boolean flipU
   ) {
      int index = rotate;
      if (flipU) {
         index = rotate | 4;
      }

      if (index > 0 && index < this.quadMaps.length) {
         Map map = this.quadMaps[index];
         if (map == null) {
            map = new IdentityHashMap(1);
            this.quadMaps[index] = map;
         }

         net.minecraft.client.renderer.block.model.BakedQuad quad = (net.minecraft.client.renderer.block.model.BakedQuad)map.get(quadIn);
         if (quad == null) {
            quad = this.makeQuad(quadIn, rotate, flipU);
            map.put(quadIn, quad);
         }

         return quad;
      } else {
         return quadIn;
      }
   }

   private net.minecraft.client.renderer.block.model.BakedQuad makeQuad(net.minecraft.client.renderer.block.model.BakedQuad quad, int rotate, boolean flipU) {
      int[] vertexData = quad.m_111303_();
      int tintIndex = quad.m_111305_();
      net.minecraft.core.Direction face = quad.m_111306_();
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = quad.m_173410_();
      boolean shade = quad.m_111307_();
      if (!this.isFullSprite(quad)) {
         rotate = 0;
      }

      vertexData = this.transformVertexData(vertexData, rotate, flipU);
      return new net.minecraft.client.renderer.block.model.BakedQuad(vertexData, tintIndex, face, sprite, shade);
   }

   private int[] transformVertexData(int[] vertexData, int rotate, boolean flipU) {
      int[] vertexData2 = (int[])vertexData.clone();
      int v2 = 4 - rotate;
      if (flipU) {
         v2 += 3;
      }

      v2 %= 4;
      int step = vertexData2.length / 4;

      for (int v = 0; v < 4; v++) {
         int pos = v * step;
         int pos2 = v2 * step;
         vertexData2[pos2 + 4] = vertexData[pos + 4];
         vertexData2[pos2 + 4 + 1] = vertexData[pos + 4 + 1];
         if (flipU) {
            if (--v2 < 0) {
               v2 = 3;
            }
         } else if (++v2 > 3) {
            v2 = 0;
         }
      }

      return vertexData2;
   }

   private boolean isFullSprite(net.minecraft.client.renderer.block.model.BakedQuad quad) {
      net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = quad.m_173410_();
      float uMin = sprite.m_118409_();
      float uMax = sprite.m_118410_();
      float uSize = uMax - uMin;
      float uDelta = uSize / 256.0F;
      float vMin = sprite.m_118411_();
      float vMax = sprite.m_118412_();
      float vSize = vMax - vMin;
      float vDelta = vSize / 256.0F;
      int[] vertexData = quad.m_111303_();
      int step = vertexData.length / 4;

      for (int i = 0; i < 4; i++) {
         int pos = i * step;
         float u = Float.intBitsToFloat(vertexData[pos + 4]);
         float v = Float.intBitsToFloat(vertexData[pos + 4 + 1]);
         if (!this.equalsDelta(u, uMin, uDelta) && !this.equalsDelta(u, uMax, uDelta)) {
            return false;
         }

         if (!this.equalsDelta(v, vMin, vDelta) && !this.equalsDelta(v, vMax, vDelta)) {
            return false;
         }
      }

      return true;
   }

   private boolean equalsDelta(float x1, float x2, float deltaMax) {
      float deltaAbs = net.minecraft.util.Mth.m_14154_(x1 - x2);
      return deltaAbs < deltaMax;
   }
}
