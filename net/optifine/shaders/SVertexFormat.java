package net.optifine.shaders;

import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;

public class SVertexFormat {
   public static int vertexSizeBlock;
   public static int offsetMidBlock;
   public static int offsetMidTexCoord;
   public static int offsetTangent;
   public static int offsetEntity;
   public static int offsetVelocity;
   public static VertexFormatElement SHADERS_MIDBLOCK_3B = makeElement(
      "SHADERS_MIDOFFSET_3B", 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 3
   );
   public static VertexFormatElement PADDING_1B = makeElement("PADDING_1B", 0, VertexFormatElement.Type.BYTE, VertexFormatElement.Usage.PADDING, 1);
   public static VertexFormatElement SHADERS_MIDTEXCOORD_2F = makeElement(
      "SHADERS_MIDTEXCOORD_2F", 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.PADDING, 2
   );
   public static VertexFormatElement SHADERS_TANGENT_4S = makeElement(
      "SHADERS_TANGENT_4S", 0, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.PADDING, 4
   );
   public static VertexFormatElement SHADERS_MC_ENTITY_4S = makeElement(
      "SHADERS_MC_ENTITY_4S", 0, VertexFormatElement.Type.SHORT, VertexFormatElement.Usage.PADDING, 4
   );
   public static VertexFormatElement SHADERS_VELOCITY_3F = makeElement(
      "SHADERS_VELOCITY_3F", 0, VertexFormatElement.Type.FLOAT, VertexFormatElement.Usage.PADDING, 3
   );

   public static VertexFormat makeExtendedFormatBlock(VertexFormat blockVanilla) {
      VertexFormat.Builder builder = new VertexFormat.Builder();
      builder.addAll(blockVanilla);
      builder.m_339091_("MidOffset", SHADERS_MIDBLOCK_3B);
      builder.m_339091_("PaddingMO", PADDING_1B);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      VertexFormat vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   public static VertexFormat makeExtendedFormatEntity(VertexFormat entityVanilla) {
      VertexFormat.Builder builder = new VertexFormat.Builder();
      builder.addAll(entityVanilla);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      VertexFormat vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   private static VertexFormatElement makeElement(String name, int indexIn, VertexFormatElement.Type typeIn, VertexFormatElement.Usage usageIn, int count) {
      return VertexFormatElement.register(VertexFormatElement.getElementsCount(), indexIn, typeIn, usageIn, count, name, -1);
   }

   public static int removeExtendedElements(int maskElements) {
      int maskVanilla = (VertexFormatElement.f_336839_.m_339950_() << 1) - 1;
      return maskElements & maskVanilla;
   }
}
