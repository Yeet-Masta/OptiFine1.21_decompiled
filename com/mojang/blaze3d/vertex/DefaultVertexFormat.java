package com.mojang.blaze3d.vertex;

import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.SVertexFormat;

public class DefaultVertexFormat {
   public static VertexFormat f_166850_ = VertexFormat.m_339703_().m_339091_("Position", VertexFormatElement.f_336661_).m_339368_();
   public static VertexFormat f_85811_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339091_("Normal", VertexFormatElement.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static VertexFormat f_85812_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("UV1", VertexFormatElement.f_337543_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339091_("Normal", VertexFormatElement.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static VertexFormat f_85813_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339368_();
   public static VertexFormat f_85814_ = VertexFormat.m_339703_().m_339091_("Position", VertexFormatElement.f_336661_).m_339368_();
   public static VertexFormat f_85815_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339368_();
   public static VertexFormat f_166851_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("Normal", VertexFormatElement.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static VertexFormat f_85816_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339368_();
   public static VertexFormat f_85817_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339368_();
   public static VertexFormat f_85819_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339368_();
   public static VertexFormat f_85820_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339368_();
   public static VertexFormat f_85821_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("UV2", VertexFormatElement.f_337050_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339368_();
   public static VertexFormat f_85822_ = VertexFormat.m_339703_()
      .m_339091_("Position", VertexFormatElement.f_336661_)
      .m_339091_("UV0", VertexFormatElement.f_336642_)
      .m_339091_("Color", VertexFormatElement.f_336914_)
      .m_339091_("Normal", VertexFormatElement.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static VertexFormat BLOCK_VANILLA = f_85811_.duplicate();
   public static VertexFormat BLOCK_SHADERS = SVertexFormat.makeExtendedFormatBlock(BLOCK_VANILLA);
   public static int BLOCK_VANILLA_SIZE = BLOCK_VANILLA.m_86020_();
   public static int BLOCK_SHADERS_SIZE = BLOCK_SHADERS.m_86020_();
   public static VertexFormat ENTITY_VANILLA = f_85812_.duplicate();
   public static VertexFormat ENTITY_SHADERS = SVertexFormat.makeExtendedFormatEntity(ENTITY_VANILLA);
   public static int ENTITY_VANILLA_SIZE = ENTITY_VANILLA.m_86020_();
   public static int ENTITY_SHADERS_SIZE = ENTITY_SHADERS.m_86020_();

   public static void updateVertexFormats() {
      if (Config.isShaders()) {
         f_85811_.copyFrom(BLOCK_SHADERS);
         f_85812_.copyFrom(ENTITY_SHADERS);
      } else {
         f_85811_.copyFrom(BLOCK_VANILLA);
         f_85812_.copyFrom(ENTITY_VANILLA);
      }

      if (Reflector.IQuadTransformer.exists()) {
         int stride = f_85811_.getIntegerSize();
         Reflector.IQuadTransformer_STRIDE.setStaticIntUnsafe(stride);
         Reflector.QuadBakingVertexConsumer_QUAD_DATA_SIZE.setStaticIntUnsafe(stride * 4);
      }
   }

   static {
      f_166850_.setName("BLIT_SCREEN");
      f_85811_.setName("BLOCK");
      f_85812_.setName("ENTITY");
      f_85813_.setName("PARTICLE_POSITION_TEX_COLOR_LMAP");
      f_85814_.setName("POSITION");
      f_85815_.setName("POSITION_COLOR");
      f_166851_.setName("POSITION_COLOR_NORMAL");
      f_85816_.setName("POSITION_COLOR_LIGHTMAP");
      f_85817_.setName("POSITION_TEX");
      f_85819_.setName("POSITION_TEX_COLOR");
      f_85820_.setName("POSITION_COLOR_TEX_LIGHTMAP");
      f_85821_.setName("POSITION_TEX_LIGHTMAP_COLOR");
      f_85822_.setName("POSITION_TEX_COLOR_NORMAL");
      BLOCK_VANILLA.setName("BLOCK");
      ENTITY_VANILLA.setName("ENTITY");
      BLOCK_SHADERS.setName("BLOCK_SHADERS");
      ENTITY_SHADERS.setName("ENTITY_SHADERS");
   }
}
