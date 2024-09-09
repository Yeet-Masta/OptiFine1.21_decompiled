package net.optifine.model;

import java.util.Arrays;

public class BakedQuadRetextured extends net.minecraft.client.renderer.block.model.BakedQuad {
   public BakedQuadRetextured(net.minecraft.client.renderer.block.model.BakedQuad quad, net.minecraft.client.renderer.texture.TextureAtlasSprite spriteIn) {
      super(
         remapVertexData(quad.m_111303_(), quad.m_173410_(), spriteIn),
         quad.m_111305_(),
         net.minecraft.client.renderer.block.model.FaceBakery.m_111612_(quad.m_111303_()),
         spriteIn,
         quad.m_111307_()
      );
   }

   private static int[] remapVertexData(
      int[] vertexData, net.minecraft.client.renderer.texture.TextureAtlasSprite sprite, net.minecraft.client.renderer.texture.TextureAtlasSprite spriteNew
   ) {
      int[] vertexDataNew = Arrays.copyOf(vertexData, vertexData.length);

      for (int i = 0; i < 4; i++) {
         com.mojang.blaze3d.vertex.VertexFormat format = com.mojang.blaze3d.vertex.DefaultVertexFormat.f_85811_;
         int j = format.getIntegerSize() * i;
         int uvIndex = format.getOffset(2) / 4;
         vertexDataNew[j + uvIndex] = Float.floatToRawIntBits(
            spriteNew.getInterpolatedU16((double)sprite.getUnInterpolatedU16(Float.intBitsToFloat(vertexData[j + uvIndex])))
         );
         vertexDataNew[j + uvIndex + 1] = Float.floatToRawIntBits(
            spriteNew.getInterpolatedV16((double)sprite.getUnInterpolatedV16(Float.intBitsToFloat(vertexData[j + uvIndex + 1])))
         );
      }

      return vertexDataNew;
   }
}
