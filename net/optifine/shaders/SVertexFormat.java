package net.optifine.shaders;

public class SVertexFormat {
   public static final int vertexSizeBlock = 18;
   public static final int offsetMidBlock = 8;
   public static final int offsetMidTexCoord = 9;
   public static final int offsetTangent = 11;
   public static final int offsetEntity = 13;
   public static final int offsetVelocity = 15;
   public static final com.mojang.blaze3d.vertex.VertexFormatElement SHADERS_MIDBLOCK_3B = makeElement(
      "SHADERS_MIDOFFSET_3B", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.BYTE, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 3
   );
   public static final com.mojang.blaze3d.vertex.VertexFormatElement PADDING_1B = makeElement(
      "PADDING_1B", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.BYTE, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 1
   );
   public static final com.mojang.blaze3d.vertex.VertexFormatElement SHADERS_MIDTEXCOORD_2F = makeElement(
      "SHADERS_MIDTEXCOORD_2F", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.FLOAT, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 2
   );
   public static final com.mojang.blaze3d.vertex.VertexFormatElement SHADERS_TANGENT_4S = makeElement(
      "SHADERS_TANGENT_4S", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.SHORT, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 4
   );
   public static final com.mojang.blaze3d.vertex.VertexFormatElement SHADERS_MC_ENTITY_4S = makeElement(
      "SHADERS_MC_ENTITY_4S", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.SHORT, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 4
   );
   public static final com.mojang.blaze3d.vertex.VertexFormatElement SHADERS_VELOCITY_3F = makeElement(
      "SHADERS_VELOCITY_3F", 0, com.mojang.blaze3d.vertex.VertexFormatElement.Type.FLOAT, com.mojang.blaze3d.vertex.VertexFormatElement.Usage.PADDING, 3
   );

   public static com.mojang.blaze3d.vertex.VertexFormat makeExtendedFormatBlock(com.mojang.blaze3d.vertex.VertexFormat blockVanilla) {
      com.mojang.blaze3d.vertex.VertexFormat.Builder builder = new com.mojang.blaze3d.vertex.VertexFormat.Builder();
      builder.addAll(blockVanilla);
      builder.m_339091_("MidOffset", SHADERS_MIDBLOCK_3B);
      builder.m_339091_("PaddingMO", PADDING_1B);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      com.mojang.blaze3d.vertex.VertexFormat vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   public static com.mojang.blaze3d.vertex.VertexFormat makeExtendedFormatEntity(com.mojang.blaze3d.vertex.VertexFormat entityVanilla) {
      com.mojang.blaze3d.vertex.VertexFormat.Builder builder = new com.mojang.blaze3d.vertex.VertexFormat.Builder();
      builder.addAll(entityVanilla);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      com.mojang.blaze3d.vertex.VertexFormat vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   private static com.mojang.blaze3d.vertex.VertexFormatElement makeElement(
      String name,
      int indexIn,
      com.mojang.blaze3d.vertex.VertexFormatElement.Type typeIn,
      com.mojang.blaze3d.vertex.VertexFormatElement.Usage usageIn,
      int count
   ) {
      return com.mojang.blaze3d.vertex.VertexFormatElement.register(
         com.mojang.blaze3d.vertex.VertexFormatElement.getElementsCount(), indexIn, typeIn, usageIn, count, name, -1
      );
   }

   public static int removeExtendedElements(int maskElements) {
      int maskVanilla = (com.mojang.blaze3d.vertex.VertexFormatElement.f_336839_.m_339950_() << 1) - 1;
      return maskElements & maskVanilla;
   }
}
