package net.minecraftforge.client.extensions;

public interface IForgeVertexConsumer {
   default void putBulkData(
      PoseStack.a matrixStack, BakedQuad bakedQuad, float red, float green, float blue, int lightmapCoord, int overlayColor, boolean readExistingColor
   ) {
   }

   default void putBulkData(
      PoseStack.a pose, BakedQuad bakedQuad, float red, float green, float blue, float alpha, int packedLight, int packedOverlay, boolean readExistingColor
   ) {
   }
}
