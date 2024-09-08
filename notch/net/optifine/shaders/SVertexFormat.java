package net.optifine.shaders;

import net.minecraft.src.C_3188_;
import net.minecraft.src.C_3189_;

public class SVertexFormat {
   public static final int vertexSizeBlock = 18;
   public static final int offsetMidBlock = 8;
   public static final int offsetMidTexCoord = 9;
   public static final int offsetTangent = 11;
   public static final int offsetEntity = 13;
   public static final int offsetVelocity = 15;
   public static final C_3189_ SHADERS_MIDBLOCK_3B = makeElement("SHADERS_MIDOFFSET_3B", 0, C_3189_.C_3190_.BYTE, C_3189_.C_3191_.PADDING, 3);
   public static final C_3189_ PADDING_1B = makeElement("PADDING_1B", 0, C_3189_.C_3190_.BYTE, C_3189_.C_3191_.PADDING, 1);
   public static final C_3189_ SHADERS_MIDTEXCOORD_2F = makeElement("SHADERS_MIDTEXCOORD_2F", 0, C_3189_.C_3190_.FLOAT, C_3189_.C_3191_.PADDING, 2);
   public static final C_3189_ SHADERS_TANGENT_4S = makeElement("SHADERS_TANGENT_4S", 0, C_3189_.C_3190_.SHORT, C_3189_.C_3191_.PADDING, 4);
   public static final C_3189_ SHADERS_MC_ENTITY_4S = makeElement("SHADERS_MC_ENTITY_4S", 0, C_3189_.C_3190_.SHORT, C_3189_.C_3191_.PADDING, 4);
   public static final C_3189_ SHADERS_VELOCITY_3F = makeElement("SHADERS_VELOCITY_3F", 0, C_3189_.C_3190_.FLOAT, C_3189_.C_3191_.PADDING, 3);

   public static C_3188_ makeExtendedFormatBlock(C_3188_ blockVanilla) {
      C_3188_.C_336503_ builder = new C_3188_.C_336503_();
      builder.addAll(blockVanilla);
      builder.m_339091_("MidOffset", SHADERS_MIDBLOCK_3B);
      builder.m_339091_("PaddingMO", PADDING_1B);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      C_3188_ vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   public static C_3188_ makeExtendedFormatEntity(C_3188_ entityVanilla) {
      C_3188_.C_336503_ builder = new C_3188_.C_336503_();
      builder.addAll(entityVanilla);
      builder.m_339091_("MidTexCoord", SHADERS_MIDTEXCOORD_2F);
      builder.m_339091_("Tangent", SHADERS_TANGENT_4S);
      builder.m_339091_("McEntity", SHADERS_MC_ENTITY_4S);
      builder.m_339091_("Velocity", SHADERS_VELOCITY_3F);
      C_3188_ vf = builder.m_339368_();
      vf.setExtended(true);
      return vf;
   }

   private static C_3189_ makeElement(String name, int indexIn, C_3189_.C_3190_ typeIn, C_3189_.C_3191_ usageIn, int count) {
      return C_3189_.register(C_3189_.getElementsCount(), indexIn, typeIn, usageIn, count, name, -1);
   }

   public static int removeExtendedElements(int maskElements) {
      int maskVanilla = (C_3189_.f_336839_.m_339950_() << 1) - 1;
      return maskElements & maskVanilla;
   }
}
