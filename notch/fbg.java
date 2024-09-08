package net.minecraft.src;

import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.SVertexFormat;

public class C_3179_ {
   public static final C_3188_ f_166850_ = C_3188_.m_339703_().m_339091_("Position", C_3189_.f_336661_).m_339368_();
   public static final C_3188_ f_85811_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339091_("Normal", C_3189_.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static final C_3188_ f_85812_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("UV1", C_3189_.f_337543_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339091_("Normal", C_3189_.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static final C_3188_ f_85813_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339368_();
   public static final C_3188_ f_85814_ = C_3188_.m_339703_().m_339091_("Position", C_3189_.f_336661_).m_339368_();
   public static final C_3188_ f_85815_ = C_3188_.m_339703_().m_339091_("Position", C_3189_.f_336661_).m_339091_("Color", C_3189_.f_336914_).m_339368_();
   public static final C_3188_ f_166851_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("Normal", C_3189_.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static final C_3188_ f_85816_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339368_();
   public static final C_3188_ f_85817_ = C_3188_.m_339703_().m_339091_("Position", C_3189_.f_336661_).m_339091_("UV0", C_3189_.f_336642_).m_339368_();
   public static final C_3188_ f_85819_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339368_();
   public static final C_3188_ f_85820_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339368_();
   public static final C_3188_ f_85821_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("UV2", C_3189_.f_337050_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339368_();
   public static final C_3188_ f_85822_ = C_3188_.m_339703_()
      .m_339091_("Position", C_3189_.f_336661_)
      .m_339091_("UV0", C_3189_.f_336642_)
      .m_339091_("Color", C_3189_.f_336914_)
      .m_339091_("Normal", C_3189_.f_336839_)
      .m_339010_(1)
      .m_339368_();
   public static final C_3188_ BLOCK_VANILLA = f_85811_.duplicate();
   public static final C_3188_ BLOCK_SHADERS = SVertexFormat.makeExtendedFormatBlock(BLOCK_VANILLA);
   public static final int BLOCK_VANILLA_SIZE = BLOCK_VANILLA.m_86020_();
   public static final int BLOCK_SHADERS_SIZE = BLOCK_SHADERS.m_86020_();
   public static final C_3188_ ENTITY_VANILLA = f_85812_.duplicate();
   public static final C_3188_ ENTITY_SHADERS = SVertexFormat.makeExtendedFormatEntity(ENTITY_VANILLA);
   public static final int ENTITY_VANILLA_SIZE = ENTITY_VANILLA.m_86020_();
   public static final int ENTITY_SHADERS_SIZE = ENTITY_SHADERS.m_86020_();

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
