package net.optifine.model;

import java.util.Arrays;
import net.minecraft.src.C_3179_;
import net.minecraft.src.C_3188_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4211_;
import net.minecraft.src.C_4486_;

public class BakedQuadRetextured extends C_4196_ {
   public BakedQuadRetextured(C_4196_ quad, C_4486_ spriteIn) {
      super(remapVertexData(quad.m_111303_(), quad.m_173410_(), spriteIn), quad.m_111305_(), C_4211_.m_111612_(quad.m_111303_()), spriteIn, quad.m_111307_());
   }

   private static int[] remapVertexData(int[] vertexData, C_4486_ sprite, C_4486_ spriteNew) {
      int[] vertexDataNew = Arrays.copyOf(vertexData, vertexData.length);

      for (int i = 0; i < 4; i++) {
         C_3188_ format = C_3179_.f_85811_;
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
