package net.minecraftforge.client.extensions;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_4196_;

public interface IForgeVertexConsumer {
   default void putBulkData(
      C_3181_.C_3183_ matrixStack, C_4196_ bakedQuad, float red, float green, float blue, int lightmapCoord, int overlayColor, boolean readExistingColor
   ) {
   }

   default void putBulkData(
      C_3181_.C_3183_ pose, C_4196_ bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor
   ) {
   }
}
