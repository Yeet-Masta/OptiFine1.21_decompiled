package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraftforge.client.ForgeRenderTypes;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.RandomEntities;
import net.optifine.reflect.Reflector;
import net.optifine.render.RenderStateManager;
import net.optifine.render.RenderUtils;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersRender;
import net.optifine.util.CompoundKey;

public abstract class C_4168_ extends C_4149_ {
   private static final int f_173154_ = 1048576;
   public static final int f_173148_ = 4194304;
   public static final int f_173150_ = 786432;
   public static final int f_173151_ = 1536;
   private static final C_4168_ f_110372_ = m_173215_(
      "solid",
      C_3179_.f_85811_,
      C_3188_.C_141549_.QUADS,
      4194304,
      true,
      false,
      C_4168_.C_4172_.m_110628_().m_110671_(f_110152_).m_173292_(f_173105_).m_173290_(f_110145_).m_110691_(true)
   );
   private static final C_4168_ f_110373_ = m_173215_(
      "cutout_mipped",
      C_3179_.f_85811_,
      C_3188_.C_141549_.QUADS,
      4194304,
      true,
      false,
      C_4168_.C_4172_.m_110628_().m_110671_(f_110152_).m_173292_(f_173106_).m_173290_(f_110145_).m_110691_(true)
   );
   private static final C_4168_ f_110374_ = m_173215_(
      "cutout",
      C_3179_.f_85811_,
      C_3188_.C_141549_.QUADS,
      786432,
      true,
      false,
      C_4168_.C_4172_.m_110628_().m_110671_(f_110152_).m_173292_(f_173107_).m_173290_(f_110146_).m_110691_(true)
   );
   private static final C_4168_ f_110375_ = m_173215_("translucent", C_3179_.f_85811_, C_3188_.C_141549_.QUADS, 786432, true, true, m_173207_(f_173108_));
   private static final C_4168_ f_110376_ = m_173215_("translucent_moving_block", C_3179_.f_85811_, C_3188_.C_141549_.QUADS, 786432, false, true, m_110408_());
   private static final Function<C_5265_, C_4168_> f_173155_ = C_5322_.m_143827_(p_292067_0_ -> m_293457_("armor_cutout_no_cull", p_292067_0_, false));
   private static final Function<C_5265_, C_4168_> f_173156_ = C_5322_.m_143827_(
      p_285690_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173112_)
            .m_173290_(new C_4149_.C_4164_(p_285690_0_, false, false))
            .m_110685_(f_110134_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_solid", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173157_ = C_5322_.m_143827_(
      p_285702_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173113_)
            .m_173290_(new C_4149_.C_4164_(p_285702_0_, false, false))
            .m_110685_(f_110134_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_cutout", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, Boolean, C_4168_> f_173160_ = C_5322_.m_143821_(
      (p_285696_0_, p_285696_1_) -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173114_)
            .m_173290_(new C_4149_.C_4164_(p_285696_0_, false, false))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(p_285696_1_);
         return m_173215_("entity_cutout_no_cull", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, Boolean, C_4168_> f_173161_ = C_5322_.m_143821_(
      (p_285686_0_, p_285686_1_) -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173063_)
            .m_173290_(new C_4149_.C_4164_(p_285686_0_, false, false))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110669_(f_110119_)
            .m_110691_(p_285686_1_);
         return m_173215_("entity_cutout_no_cull_z_offset", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173162_ = C_5322_.m_143827_(
      p_285687_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173064_)
            .m_173290_(new C_4149_.C_4164_(p_285687_0_, false, false))
            .m_110685_(f_110139_)
            .m_110675_(f_110129_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(C_4149_.f_110114_)
            .m_110691_(true);
         return m_173215_("item_entity_translucent_cull", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173163_ = C_5322_.m_143827_(
      p_285695_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173065_)
            .m_173290_(new C_4149_.C_4164_(p_285695_0_, false, false))
            .m_110685_(f_110139_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_translucent_cull", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, Boolean, C_4168_> f_173164_ = C_5322_.m_143821_(
      (p_285688_0_, p_285688_1_) -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173066_)
            .m_173290_(new C_4149_.C_4164_(p_285688_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(p_285688_1_);
         return m_173215_("entity_translucent", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, Boolean, C_4168_> f_234325_ = C_5322_.m_143821_(
      (p_285694_0_, p_285694_1_) -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_234323_)
            .m_173290_(new C_4149_.C_4164_(p_285694_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110687_(f_110115_)
            .m_110677_(f_110154_)
            .m_110691_(p_285694_1_);
         return m_173215_("entity_translucent_emissive", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173165_ = C_5322_.m_143827_(
      p_285698_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173067_)
            .m_173290_(new C_4149_.C_4164_(p_285698_0_, false, false))
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110691_(true);
         return m_173209_("entity_smooth_cutout", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, Boolean, C_4168_> f_173166_ = C_5322_.m_143821_(
      (p_234329_0_, p_234329_1_) -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173068_)
            .m_173290_(new C_4149_.C_4164_(p_234329_0_, false, false))
            .m_110685_(p_234329_1_ ? f_110139_ : f_110134_)
            .m_110687_(p_234329_1_ ? f_110115_ : f_110114_)
            .m_110691_(false);
         return m_173215_("beacon_beam", C_3179_.f_85811_, C_3188_.C_141549_.QUADS, 1536, false, true, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173167_ = C_5322_.m_143827_(
      p_285700_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173069_)
            .m_173290_(new C_4149_.C_4164_(p_285700_0_, false, false))
            .m_110663_(f_110112_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false);
         return m_173209_("entity_decal", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173168_ = C_5322_.m_143827_(
      p_285691_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173070_)
            .m_173290_(new C_4149_.C_4164_(p_285691_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110691_(false);
         return m_173215_("entity_no_outline", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, false, true, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173169_ = C_5322_.m_143827_(
      p_285684_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173071_)
            .m_173290_(new C_4149_.C_4164_(p_285684_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110158_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110663_(f_110113_)
            .m_110669_(f_110119_)
            .m_110691_(false);
         return m_173215_("entity_shadow", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, false, false, rendertype$compositestate);
      }
   );
   private static final Function<C_5265_, C_4168_> f_173170_ = C_5322_.m_143827_(
      p_285683_0_ -> {
         C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173072_)
            .m_173290_(new C_4149_.C_4164_(p_285683_0_, false, false))
            .m_110661_(f_110110_)
            .m_110691_(true);
         return m_173209_("entity_alpha", C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final BiFunction<C_5265_, C_4149_.C_4166_, C_4168_> f_173171_ = C_5322_.m_143821_(
      (p_304056_0_, p_304056_1_) -> {
         C_4149_.C_4164_ renderstateshard$texturestateshard = new C_4149_.C_4164_(p_304056_0_, false, false);
         return m_173215_(
            "eyes",
            C_3179_.f_85812_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            true,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173073_)
               .m_173290_(renderstateshard$texturestateshard)
               .m_110685_(p_304056_1_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         );
      }
   );
   private static final C_4168_ f_110378_ = m_173209_(
      "leash",
      C_3179_.f_85816_,
      C_3188_.C_141549_.TRIANGLE_STRIP,
      1536,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173075_).m_173290_(f_110147_).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(false)
   );
   private static final C_4168_ f_110379_ = m_173209_(
      "water_mask",
      C_3179_.f_85814_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173076_).m_173290_(f_110147_).m_110687_(f_110116_).m_110691_(false)
   );
   private static final C_4168_ f_110381_ = m_173209_(
      "armor_entity_glint",
      C_3179_.f_85817_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173079_)
         .m_173290_(new C_4149_.C_4164_(C_4354_.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110151_)
         .m_110669_(f_110119_)
         .m_110691_(false)
   );
   private static final C_4168_ f_110382_ = m_173209_(
      "glint_translucent",
      C_3179_.f_85817_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173080_)
         .m_173290_(new C_4149_.C_4164_(C_4354_.f_273833_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110150_)
         .m_110675_(f_110129_)
         .m_110691_(false)
   );
   private static final C_4168_ f_110383_ = m_173209_(
      "glint",
      C_3179_.f_85817_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173081_)
         .m_173290_(new C_4149_.C_4164_(C_4354_.f_273833_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110150_)
         .m_110691_(false)
   );
   private static final C_4168_ f_110385_ = m_173209_(
      "entity_glint",
      C_3179_.f_85817_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173083_)
         .m_173290_(new C_4149_.C_4164_(C_4354_.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110675_(f_110129_)
         .m_110683_(f_110151_)
         .m_110691_(false)
   );
   private static final C_4168_ f_110386_ = m_173209_(
      "entity_glint_direct",
      C_3179_.f_85817_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173084_)
         .m_173290_(new C_4149_.C_4164_(C_4354_.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110151_)
         .m_110691_(false)
   );
   private static final Function<C_5265_, C_4168_> f_173172_ = C_5322_.m_143827_(
      p_285703_0_ -> {
         C_4149_.C_4164_ renderstateshard$texturestateshard = new C_4149_.C_4164_(p_285703_0_, false, false);
         return m_173215_(
            "crumbling",
            C_3179_.f_85811_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            true,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173085_)
               .m_173290_(renderstateshard$texturestateshard)
               .m_110685_(f_110138_)
               .m_110687_(f_110115_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         );
      }
   );
   private static final Function<C_5265_, C_4168_> f_173173_ = C_5322_.m_143827_(
      p_304055_0_ -> m_173215_(
            "text",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            786432,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173086_)
               .m_173290_(new C_4149_.C_4164_(p_304055_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110691_(false)
         )
   );
   private static final C_4168_ f_268665_ = m_173215_(
      "text_background",
      C_3179_.f_85816_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_268568_).m_173290_(f_110147_).m_110685_(f_110139_).m_110671_(f_110152_).m_110691_(false)
   );
   private static final Function<C_5265_, C_4168_> f_173174_ = C_5322_.m_143827_(
      p_304057_0_ -> m_173215_(
            "text_intensity",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            786432,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173087_)
               .m_173290_(new C_4149_.C_4164_(p_304057_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110691_(false)
         )
   );
   private static final Function<C_5265_, C_4168_> f_181442_ = C_5322_.m_143827_(
      p_285685_0_ -> m_173215_(
            "text_polygon_offset",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173086_)
               .m_173290_(new C_4149_.C_4164_(p_285685_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         )
   );
   private static final Function<C_5265_, C_4168_> f_181443_ = C_5322_.m_143827_(
      p_285704_0_ -> m_173215_(
            "text_intensity_polygon_offset",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173087_)
               .m_173290_(new C_4149_.C_4164_(p_285704_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         )
   );
   private static final Function<C_5265_, C_4168_> f_173175_ = C_5322_.m_143827_(
      p_285689_0_ -> m_173215_(
            "text_see_through",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173088_)
               .m_173290_(new C_4149_.C_4164_(p_285689_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110663_(f_110111_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         )
   );
   private static final C_4168_ f_268619_ = m_173215_(
      "text_background_see_through",
      C_3179_.f_85816_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_268491_)
         .m_173290_(f_110147_)
         .m_110685_(f_110139_)
         .m_110671_(f_110152_)
         .m_110663_(f_110111_)
         .m_110687_(f_110115_)
         .m_110691_(false)
   );
   private static final Function<C_5265_, C_4168_> f_173176_ = C_5322_.m_143827_(
      p_285697_0_ -> m_173215_(
            "text_intensity_see_through",
            C_3179_.f_85820_,
            C_3188_.C_141549_.QUADS,
            1536,
            false,
            false,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173090_)
               .m_173290_(new C_4149_.C_4164_(p_285697_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110663_(f_110111_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         )
   );
   private static final C_4168_ f_110387_ = m_173215_(
      "lightning",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173091_).m_110687_(f_110114_).m_110685_(f_110136_).m_110675_(f_110127_).m_110691_(false)
   );
   private static final C_4168_ f_336773_ = m_173215_(
      "dragon_rays",
      C_3179_.f_85815_,
      C_3188_.C_141549_.TRIANGLES,
      1536,
      false,
      false,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173091_).m_110687_(f_110115_).m_110685_(f_110136_).m_110691_(false)
   );
   private static final C_4168_ f_337318_ = m_173215_(
      "dragon_rays_depth",
      C_3179_.f_85814_,
      C_3188_.C_141549_.TRIANGLES,
      1536,
      false,
      false,
      C_4168_.C_4172_.m_110628_().m_173292_(C_4149_.f_173100_).m_110687_(f_110116_).m_110691_(false)
   );
   private static final C_4168_ f_110388_ = m_173215_("tripwire", C_3179_.f_85811_, C_3188_.C_141549_.QUADS, 1536, true, true, m_110409_());
   private static final C_4168_ f_173158_ = m_173215_(
      "end_portal",
      C_3179_.f_85814_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      false,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173093_)
         .m_173290_(C_4149_.C_141718_.m_173127_().m_173132_(C_4260_.f_112626_, false, false).m_173132_(C_4260_.f_112627_, false, false).m_173131_())
         .m_110691_(false)
   );
   private static final C_4168_ f_173159_ = m_173215_(
      "end_gateway",
      C_3179_.f_85814_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      false,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173094_)
         .m_173290_(C_4149_.C_141718_.m_173127_().m_173132_(C_4260_.f_112626_, false, false).m_173132_(C_4260_.f_112627_, false, false).m_173131_())
         .m_110691_(false)
   );
   private static final C_4168_ f_314866_ = m_318882_(false);
   private static final C_4168_ f_315561_ = m_318882_(true);
   public static final C_4168_.C_4170_ f_110371_ = m_173209_(
      "lines",
      C_3179_.f_166851_,
      C_3188_.C_141549_.LINES,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173095_)
         .m_110673_(new C_4149_.C_4158_(OptionalDouble.empty()))
         .m_110669_(f_110119_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110687_(f_110114_)
         .m_110661_(f_110110_)
         .m_110691_(false)
   );
   public static final C_4168_.C_4170_ f_173152_ = m_173209_(
      "line_strip",
      C_3179_.f_166851_,
      C_3188_.C_141549_.LINE_STRIP,
      1536,
      C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173095_)
         .m_110673_(new C_4149_.C_4158_(OptionalDouble.empty()))
         .m_110669_(f_110119_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110687_(f_110114_)
         .m_110661_(f_110110_)
         .m_110691_(false)
   );
   private static final Function<Double, C_4168_.C_4170_> f_268753_ = C_5322_.m_143827_(
      p_285693_0_ -> m_173209_(
            "debug_line_strip",
            C_3179_.f_85815_,
            C_3188_.C_141549_.DEBUG_LINE_STRIP,
            1536,
            C_4168_.C_4172_.m_110628_()
               .m_173292_(f_173104_)
               .m_110673_(new C_4149_.C_4158_(OptionalDouble.of(p_285693_0_)))
               .m_110685_(f_110134_)
               .m_110661_(f_110110_)
               .m_110691_(false)
         )
   );
   private static final C_4168_.C_4170_ f_268540_ = m_173215_(
      "debug_filled_box",
      C_3179_.f_85815_,
      C_3188_.C_141549_.TRIANGLE_STRIP,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173104_).m_110669_(f_110119_).m_110685_(f_110139_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_268519_ = m_173215_(
      "debug_quads",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173104_).m_110685_(f_110139_).m_110661_(f_110110_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_337222_ = m_173215_(
      "debug_structure_quads",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173104_).m_110685_(f_110139_).m_110661_(f_110110_).m_110663_(f_110113_).m_110687_(f_110115_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_279582_ = m_173215_(
      "debug_section_quads",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      false,
      true,
      C_4168_.C_4172_.m_110628_().m_173292_(f_173104_).m_110669_(f_110119_).m_110685_(f_110139_).m_110661_(f_110158_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_285558_ = m_173209_(
      "gui",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      786432,
      C_4168_.C_4172_.m_110628_().m_173292_(f_285573_).m_110685_(f_110139_).m_110663_(f_110113_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_285624_ = m_173209_(
      "gui_overlay",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_().m_173292_(f_285619_).m_110685_(f_110139_).m_110663_(f_110111_).m_110687_(f_110115_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_285662_ = m_173209_(
      "gui_text_highlight",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_().m_173292_(f_285642_).m_110685_(f_110139_).m_110663_(f_110111_).m_286027_(f_285603_).m_110691_(false)
   );
   private static final C_4168_.C_4170_ f_285639_ = m_173209_(
      "gui_ghost_recipe_overlay",
      C_3179_.f_85815_,
      C_3188_.C_141549_.QUADS,
      1536,
      C_4168_.C_4172_.m_110628_().m_173292_(f_285582_).m_110685_(f_110139_).m_110663_(f_285579_).m_110687_(f_110115_).m_110691_(false)
   );
   private static final ImmutableList<C_4168_> f_234324_ = ImmutableList.of(m_110451_(), m_110457_(), m_110463_(), m_110466_(), m_110503_());
   private final C_3188_ f_110389_;
   private final C_3188_.C_141549_ f_110390_;
   private final int f_110391_;
   private final boolean f_110392_;
   private final boolean f_110393_;
   private int id = -1;
   public static final C_4168_[] CHUNK_RENDER_TYPES = getChunkRenderTypesArray();
   private static Map<CompoundKey, C_4168_> RENDER_TYPES;
   private int chunkLayerId = -1;

   public int ordinal() {
      return this.id;
   }

   public boolean isNeedsSorting() {
      return this.f_110393_;
   }

   private static C_4168_[] getChunkRenderTypesArray() {
      C_4168_[] renderTypes = (C_4168_[])m_110506_().toArray(new C_4168_[0]);
      int i = 0;

      while (i < renderTypes.length) {
         C_4168_ renderType = renderTypes[i];
         renderType.id = i++;
      }

      return renderTypes;
   }

   public static C_4168_ m_110451_() {
      return f_110372_;
   }

   public static C_4168_ m_110457_() {
      return f_110373_;
   }

   public static C_4168_ m_110463_() {
      return f_110374_;
   }

   private static C_4168_.C_4172_ m_173207_(C_4149_.C_141720_ shardIn) {
      return C_4168_.C_4172_.m_110628_().m_110671_(f_110152_).m_173292_(shardIn).m_173290_(f_110145_).m_110685_(f_110139_).m_110675_(f_110125_).m_110691_(true);
   }

   public static C_4168_ m_110466_() {
      return f_110375_;
   }

   private static C_4168_.C_4172_ m_110408_() {
      return C_4168_.C_4172_.m_110628_()
         .m_110671_(f_110152_)
         .m_173292_(f_173109_)
         .m_173290_(f_110145_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110691_(true);
   }

   public static C_4168_ m_110469_() {
      return f_110376_;
   }

   private static C_4168_.C_4170_ m_293457_(String nameIn, C_5265_ locationIn, boolean depthIn) {
      C_4168_.C_4172_ rendertype$compositestate = C_4168_.C_4172_.m_110628_()
         .m_173292_(f_173111_)
         .m_173290_(new C_4149_.C_4164_(locationIn, false, false))
         .m_110685_(f_110134_)
         .m_110661_(f_110110_)
         .m_110671_(f_110152_)
         .m_110677_(f_110154_)
         .m_110669_(f_110119_)
         .m_110663_(depthIn ? f_110112_ : f_110113_)
         .m_110691_(true);
      return m_173215_(nameIn, C_3179_.f_85812_, C_3188_.C_141549_.QUADS, 1536, true, false, rendertype$compositestate);
   }

   public static C_4168_ m_110431_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173155_.apply(locationIn);
   }

   public static C_4168_ m_294378_(C_5265_ locationIn) {
      return m_293457_("armor_decal_cutout_no_cull", locationIn, true);
   }

   public static C_4168_ m_110446_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return EmissiveTextures.isRenderEmissive() ? (C_4168_)f_173157_.apply(locationIn) : (C_4168_)f_173156_.apply(locationIn);
   }

   public static C_4168_ m_110452_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173157_.apply(locationIn);
   }

   public static C_4168_ m_110443_(C_5265_ locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173160_.apply(locationIn, outlineIn);
   }

   public static C_4168_ m_110458_(C_5265_ locationIn) {
      return m_110443_(locationIn, true);
   }

   public static C_4168_ m_110448_(C_5265_ locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173161_.apply(locationIn, outlineIn);
   }

   public static C_4168_ m_110464_(C_5265_ locationIn) {
      return m_110448_(locationIn, true);
   }

   public static C_4168_ m_110467_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173162_.apply(locationIn);
   }

   public static C_4168_ m_110470_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173163_.apply(locationIn);
   }

   public static C_4168_ m_110454_(C_5265_ locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173164_.apply(locationIn, outlineIn);
   }

   public static C_4168_ m_110473_(C_5265_ locationIn) {
      return m_110454_(locationIn, true);
   }

   public static C_4168_ m_234335_(C_5265_ locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_234325_.apply(locationIn, outlineIn);
   }

   public static C_4168_ m_234338_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return m_234335_(locationIn, true);
   }

   public static C_4168_ m_110476_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173165_.apply(locationIn);
   }

   public static C_4168_ m_110460_(C_5265_ locationIn, boolean colorFlagIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173166_.apply(locationIn, colorFlagIn);
   }

   public static C_4168_ m_110479_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173167_.apply(locationIn);
   }

   public static C_4168_ m_110482_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173168_.apply(locationIn);
   }

   public static C_4168_ m_110485_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173169_.apply(locationIn);
   }

   public static C_4168_ m_173235_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173170_.apply(locationIn);
   }

   public static C_4168_ m_110488_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173171_.apply(locationIn, f_110135_);
   }

   public static C_4168_ m_305574_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_234325_.apply(locationIn, false);
   }

   public static C_4168_ m_305520_(C_5265_ locationIn, float offsetUIn, float offsetVIn) {
      locationIn = getCustomTexture(locationIn);
      return m_173215_(
         "breeze_wind",
         C_3179_.f_85812_,
         C_3188_.C_141549_.QUADS,
         1536,
         false,
         true,
         C_4168_.C_4172_.m_110628_()
            .m_173292_(f_303180_)
            .m_173290_(new C_4149_.C_4164_(locationIn, false, false))
            .m_110683_(new C_4149_.C_4159_(offsetUIn, offsetVIn))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110155_)
            .m_110691_(false)
      );
   }

   public static C_4168_ m_110436_(C_5265_ locationIn, float uIn, float vIn) {
      locationIn = getCustomTexture(locationIn);
      return m_173215_(
         "energy_swirl",
         C_3179_.f_85812_,
         C_3188_.C_141549_.QUADS,
         1536,
         false,
         true,
         C_4168_.C_4172_.m_110628_()
            .m_173292_(f_173074_)
            .m_173290_(new C_4149_.C_4164_(locationIn, false, false))
            .m_110683_(new C_4149_.C_4159_(uIn, vIn))
            .m_110685_(f_110135_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false)
      );
   }

   public static C_4168_ m_110475_() {
      return f_110378_;
   }

   public static C_4168_ m_110478_() {
      return f_110379_;
   }

   public static C_4168_ m_110491_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)C_4168_.C_4170_.f_173256_.apply(locationIn, f_110110_);
   }

   public static C_4168_ m_110484_() {
      return f_110381_;
   }

   public static C_4168_ m_110487_() {
      return f_110382_;
   }

   public static C_4168_ m_110490_() {
      return f_110383_;
   }

   public static C_4168_ m_110496_() {
      return f_110385_;
   }

   public static C_4168_ m_110499_() {
      return f_110386_;
   }

   public static C_4168_ m_110494_(C_5265_ locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (C_4168_)f_173172_.apply(locationIn);
   }

   public static C_4168_ m_110497_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getText(locationIn) : (C_4168_)f_173173_.apply(locationIn);
   }

   public static C_4168_ m_269058_() {
      return f_268665_;
   }

   public static C_4168_ m_173237_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensity(locationIn) : (C_4168_)f_173174_.apply(locationIn);
   }

   public static C_4168_ m_181444_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextPolygonOffset(locationIn) : (C_4168_)f_181442_.apply(locationIn);
   }

   public static C_4168_ m_181446_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensityPolygonOffset(locationIn) : (C_4168_)f_181443_.apply(locationIn);
   }

   public static C_4168_ m_110500_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextSeeThrough(locationIn) : (C_4168_)f_173175_.apply(locationIn);
   }

   public static C_4168_ m_269508_() {
      return f_268619_;
   }

   public static C_4168_ m_173240_(C_5265_ locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensitySeeThrough(locationIn) : (C_4168_)f_173176_.apply(locationIn);
   }

   public static C_4168_ m_110502_() {
      return f_110387_;
   }

   public static C_4168_ m_339127_() {
      return f_336773_;
   }

   public static C_4168_ m_339251_() {
      return f_337318_;
   }

   private static C_4168_.C_4172_ m_110409_() {
      return C_4168_.C_4172_.m_110628_()
         .m_110671_(f_110152_)
         .m_173292_(f_173092_)
         .m_173290_(f_110145_)
         .m_110685_(f_110139_)
         .m_110675_(f_110127_)
         .m_110691_(true);
   }

   public static C_4168_ m_110503_() {
      return f_110388_;
   }

   public static C_4168_ m_173239_() {
      return f_173158_;
   }

   public static C_4168_ m_173242_() {
      return f_173159_;
   }

   private static C_4168_.C_4170_ m_318882_(boolean depthOnly) {
      return m_173215_(
         "clouds",
         C_3179_.f_85822_,
         C_3188_.C_141549_.QUADS,
         786432,
         false,
         false,
         C_4168_.C_4172_.m_110628_()
            .m_173292_(f_316212_)
            .m_173290_(new C_4149_.C_4164_(C_4134_.f_109456_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110687_(depthOnly ? f_110116_ : f_110114_)
            .m_110675_(f_110128_)
            .m_110691_(true)
      );
   }

   public static C_4168_ m_325090_() {
      return f_314866_;
   }

   public static C_4168_ m_319097_() {
      return f_315561_;
   }

   public static C_4168_ m_110504_() {
      return f_110371_;
   }

   public static C_4168_ m_173247_() {
      return f_173152_;
   }

   public static C_4168_ m_269399_(double widthIn) {
      return (C_4168_)f_268753_.apply(widthIn);
   }

   public static C_4168_ m_269313_() {
      return f_268540_;
   }

   public static C_4168_ m_269166_() {
      return f_268519_;
   }

   public static C_4168_ m_340348_() {
      return f_337222_;
   }

   public static C_4168_ m_280070_() {
      return f_279582_;
   }

   public static C_4168_ m_285907_() {
      return f_285558_;
   }

   public static C_4168_ m_286086_() {
      return f_285624_;
   }

   public static C_4168_ m_285783_() {
      return f_285662_;
   }

   public static C_4168_ m_285811_() {
      return f_285639_;
   }

   public C_4168_(
      String nameIn,
      C_3188_ formatIn,
      C_3188_.C_141549_ drawModeIn,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      Runnable setupTaskIn,
      Runnable clearTaskIn
   ) {
      super(nameIn, setupTaskIn, clearTaskIn);
      this.f_110389_ = formatIn;
      this.f_110390_ = drawModeIn;
      this.f_110391_ = bufferSizeIn;
      this.f_110392_ = useDelegateIn;
      this.f_110393_ = needsSortingIn;
   }

   static C_4168_.C_4170_ m_173209_(String nameIn, C_3188_ vertexFormatIn, C_3188_.C_141549_ drawModeIn, int bufferSizeIn, C_4168_.C_4172_ renderStateIn) {
      return m_173215_(nameIn, vertexFormatIn, drawModeIn, bufferSizeIn, false, false, renderStateIn);
   }

   static C_4168_.C_4170_ m_173215_(
      String name,
      C_3188_ vertexFormatIn,
      C_3188_.C_141549_ glMode,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      C_4168_.C_4172_ renderStateIn
   ) {
      return new C_4168_.C_4170_(name, vertexFormatIn, glMode, bufferSizeIn, useDelegateIn, needsSortingIn, renderStateIn);
   }

   public void m_339876_(C_336471_ dataIn) {
      this.m_110185_();
      if (Config.isShaders()) {
         RenderUtils.setFlushRenderBuffers(false);
         Shaders.pushProgram();
         ShadersRender.preRender(this);
      }

      C_3177_.m_231202_(dataIn);
      if (Config.isShaders()) {
         ShadersRender.postRender(this);
         Shaders.popProgram();
         RenderUtils.setFlushRenderBuffers(true);
      }

      this.m_110188_();
   }

   @Override
   public String toString() {
      return this.f_110133_;
   }

   public static List<C_4168_> m_110506_() {
      return f_234324_;
   }

   public int m_110507_() {
      return this.f_110391_;
   }

   public C_3188_ m_110508_() {
      return this.f_110389_;
   }

   public C_3188_.C_141549_ m_173186_() {
      return this.f_110390_;
   }

   public Optional<C_4168_> m_7280_() {
      return Optional.empty();
   }

   public boolean m_5492_() {
      return false;
   }

   public boolean m_110405_() {
      return this.f_110392_;
   }

   public boolean m_234326_() {
      return !this.f_110390_.f_231234_;
   }

   public boolean m_340332_() {
      return this.f_110393_;
   }

   public static C_5265_ getCustomTexture(C_5265_ locationIn) {
      if (Config.isRandomEntities()) {
         locationIn = RandomEntities.getTextureLocation(locationIn);
      }

      if (EmissiveTextures.isActive()) {
         locationIn = EmissiveTextures.getEmissiveTexture(locationIn);
      }

      return locationIn;
   }

   public boolean isEntitySolid() {
      return this.getName().equals("entity_solid");
   }

   public static int getCountRenderStates() {
      return f_110371_.f_110511_.f_110592_.size();
   }

   public C_5265_ getTextureLocation() {
      return null;
   }

   public boolean isGlint() {
      return this.getTextureLocation() == C_4354_.f_273897_ | this.getTextureLocation() == C_4354_.f_273833_;
   }

   public boolean isAtlasTextureBlocks() {
      C_5265_ loc = this.getTextureLocation();
      return loc == C_4484_.f_118259_;
   }

   public final int getChunkLayerId() {
      return this.chunkLayerId;
   }

   static {
      int i = 0;

      for (C_4168_ layer : m_110506_()) {
         layer.chunkLayerId = i++;
      }
   }

   static final class C_4170_ extends C_4168_ {
      static final BiFunction<C_5265_, C_4149_.C_4152_, C_4168_> f_173256_ = C_5322_.m_143821_(
         (p_337854_0_, p_337854_1_) -> C_4168_.m_173209_(
               "outline",
               C_3179_.f_85819_,
               C_3188_.C_141549_.QUADS,
               1536,
               C_4168_.C_4172_.m_110628_()
                  .m_173292_(f_173077_)
                  .m_173290_(new C_4149_.C_4164_(p_337854_0_, false, false))
                  .m_110661_(p_337854_1_)
                  .m_110663_(f_110111_)
                  .m_110675_(f_110124_)
                  .m_110689_(C_4168_.C_4174_.IS_OUTLINE)
            )
      );
      private final C_4168_.C_4172_ f_110511_;
      private final Optional<C_4168_> f_110513_;
      private final boolean f_110514_;
      private Map<C_5265_, C_4168_.C_4170_> mapTextured = new HashMap();

      C_4170_(
         String name,
         C_3188_ vertexFormatIn,
         C_3188_.C_141549_ glMode,
         int bufferSizeIn,
         boolean useDelegateIn,
         boolean needsSortingIn,
         C_4168_.C_4172_ renderStateIn
      ) {
         super(
            name,
            vertexFormatIn,
            glMode,
            bufferSizeIn,
            useDelegateIn,
            needsSortingIn,
            () -> RenderStateManager.setupRenderStates(renderStateIn.f_110592_),
            () -> RenderStateManager.clearRenderStates(renderStateIn.f_110592_)
         );
         this.f_110511_ = renderStateIn;
         this.f_110513_ = renderStateIn.f_110591_ == C_4168_.C_4174_.AFFECTS_OUTLINE
            ? renderStateIn.f_110576_.m_142706_().map(locationIn -> (C_4168_)f_173256_.apply(locationIn, renderStateIn.f_110582_))
            : Optional.empty();
         this.f_110514_ = renderStateIn.f_110591_ == C_4168_.C_4174_.IS_OUTLINE;
      }

      @Override
      public Optional<C_4168_> m_7280_() {
         return this.f_110513_;
      }

      @Override
      public boolean m_5492_() {
         return this.f_110514_;
      }

      protected final C_4168_.C_4172_ m_173265_() {
         return this.f_110511_;
      }

      @Override
      public String toString() {
         return "RenderType[" + this.f_110133_ + ":" + this.f_110511_ + "]";
      }

      public C_4168_.C_4170_ getTextured(C_5265_ textureLocation) {
         if (textureLocation == null) {
            return this;
         } else {
            Optional<C_5265_> optLoc = this.f_110511_.f_110576_.m_142706_();
            if (!optLoc.isPresent()) {
               return this;
            } else {
               C_5265_ loc = (C_5265_)optLoc.get();
               if (loc == null) {
                  return this;
               } else if (textureLocation.equals(loc)) {
                  return this;
               } else {
                  C_4168_.C_4170_ typeTex = (C_4168_.C_4170_)this.mapTextured.get(textureLocation);
                  if (typeTex == null) {
                     C_4168_.C_4172_.C_4173_ builderTex = this.f_110511_.getCopyBuilder();
                     builderTex.m_173290_(new C_4149_.C_4164_(textureLocation, this.f_110511_.f_110576_.isBlur(), this.f_110511_.f_110576_.isMipmap()));
                     C_4168_.C_4172_ stateTex = builderTex.m_110691_(this.f_110514_);
                     typeTex = m_173215_(
                        this.f_110133_, this.m_110508_(), this.m_173186_(), this.m_110507_(), this.m_110405_(), this.isNeedsSorting(), stateTex
                     );
                     this.mapTextured.put(textureLocation, typeTex);
                  }

                  return typeTex;
               }
            }
         }
      }

      @Override
      public C_5265_ getTextureLocation() {
         Optional<C_5265_> optLoc = this.f_110511_.f_110576_.m_142706_();
         return !optLoc.isPresent() ? null : (C_5265_)optLoc.get();
      }
   }

   protected static final class C_4172_ {
      final C_4149_.C_141717_ f_110576_;
      private final C_4149_.C_141720_ f_173274_;
      private final C_4149_.C_4166_ f_110577_;
      private final C_4149_.C_4153_ f_110581_;
      final C_4149_.C_4152_ f_110582_;
      private final C_4149_.C_4157_ f_110583_;
      private final C_4149_.C_4161_ f_110584_;
      private final C_4149_.C_4156_ f_110586_;
      private final C_4149_.C_4160_ f_110587_;
      private final C_4149_.C_4165_ f_110588_;
      private final C_4149_.C_4167_ f_110589_;
      private final C_4149_.C_4158_ f_110590_;
      private final C_4149_.C_285538_ f_285566_;
      final C_4168_.C_4174_ f_110591_;
      final ImmutableList<C_4149_> f_110592_;

      C_4172_(
         C_4149_.C_141717_ textureIn,
         C_4149_.C_141720_ shaderStateIn,
         C_4149_.C_4166_ transparencyIn,
         C_4149_.C_4153_ depthTestIn,
         C_4149_.C_4152_ cullIn,
         C_4149_.C_4157_ lightmapIn,
         C_4149_.C_4161_ overlayIn,
         C_4149_.C_4156_ layerIn,
         C_4149_.C_4160_ targetIn,
         C_4149_.C_4165_ texturingIn,
         C_4149_.C_4167_ writeMaskIn,
         C_4149_.C_4158_ lineIn,
         C_4149_.C_285538_ colorLogicStateIn,
         C_4168_.C_4174_ outlineIn
      ) {
         this.f_110576_ = textureIn;
         this.f_173274_ = shaderStateIn;
         this.f_110577_ = transparencyIn;
         this.f_110581_ = depthTestIn;
         this.f_110582_ = cullIn;
         this.f_110583_ = lightmapIn;
         this.f_110584_ = overlayIn;
         this.f_110586_ = layerIn;
         this.f_110587_ = targetIn;
         this.f_110588_ = texturingIn;
         this.f_110589_ = writeMaskIn;
         this.f_110590_ = lineIn;
         this.f_285566_ = colorLogicStateIn;
         this.f_110591_ = outlineIn;
         this.f_110592_ = ImmutableList.of(
            this.f_110576_,
            this.f_173274_,
            this.f_110577_,
            this.f_110581_,
            this.f_110582_,
            this.f_110583_,
            this.f_110584_,
            this.f_110586_,
            this.f_110587_,
            this.f_110588_,
            this.f_110589_,
            this.f_285566_,
            new C_4149_[]{this.f_110590_}
         );
      }

      public String toString() {
         return "CompositeState[" + this.f_110592_ + ", outlineProperty=" + this.f_110591_ + "]";
      }

      public static C_4168_.C_4172_.C_4173_ m_110628_() {
         return new C_4168_.C_4172_.C_4173_();
      }

      public C_4168_.C_4172_.C_4173_ getCopyBuilder() {
         C_4168_.C_4172_.C_4173_ builder = new C_4168_.C_4172_.C_4173_();
         builder.m_173290_(this.f_110576_);
         builder.m_173292_(this.f_173274_);
         builder.m_110685_(this.f_110577_);
         builder.m_110663_(this.f_110581_);
         builder.m_110661_(this.f_110582_);
         builder.m_110671_(this.f_110583_);
         builder.m_110677_(this.f_110584_);
         builder.m_110669_(this.f_110586_);
         builder.m_110675_(this.f_110587_);
         builder.m_110683_(this.f_110588_);
         builder.m_110687_(this.f_110589_);
         builder.m_110673_(this.f_110590_);
         return builder;
      }

      public static class C_4173_ {
         private C_4149_.C_141717_ f_110641_ = C_4149_.f_110147_;
         private C_4149_.C_141720_ f_173289_ = C_4149_.f_173096_;
         private C_4149_.C_4166_ f_110642_ = C_4149_.f_110134_;
         private C_4149_.C_4153_ f_110646_ = C_4149_.f_110113_;
         private C_4149_.C_4152_ f_110647_ = C_4149_.f_110158_;
         private C_4149_.C_4157_ f_110648_ = C_4149_.f_110153_;
         private C_4149_.C_4161_ f_110649_ = C_4149_.f_110155_;
         private C_4149_.C_4156_ f_110651_ = C_4149_.f_110117_;
         private C_4149_.C_4160_ f_110652_ = C_4149_.f_110123_;
         private C_4149_.C_4165_ f_110653_ = C_4149_.f_110148_;
         private C_4149_.C_4167_ f_110654_ = C_4149_.f_110114_;
         private C_4149_.C_4158_ f_110655_ = C_4149_.f_110130_;
         private C_4149_.C_285538_ f_285600_ = C_4149_.f_285585_;

         C_4173_() {
         }

         public C_4168_.C_4172_.C_4173_ m_173290_(C_4149_.C_141717_ textureIn) {
            this.f_110641_ = textureIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_173292_(C_4149_.C_141720_ shaderStateIn) {
            this.f_173289_ = shaderStateIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110685_(C_4149_.C_4166_ transparencyIn) {
            this.f_110642_ = transparencyIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110663_(C_4149_.C_4153_ depthTestIn) {
            this.f_110646_ = depthTestIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110661_(C_4149_.C_4152_ cullIn) {
            this.f_110647_ = cullIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110671_(C_4149_.C_4157_ lightmapIn) {
            this.f_110648_ = lightmapIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110677_(C_4149_.C_4161_ overlayIn) {
            this.f_110649_ = overlayIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110669_(C_4149_.C_4156_ layerIn) {
            this.f_110651_ = layerIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110675_(C_4149_.C_4160_ targetIn) {
            this.f_110652_ = targetIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110683_(C_4149_.C_4165_ texturingIn) {
            this.f_110653_ = texturingIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110687_(C_4149_.C_4167_ writeMaskIn) {
            this.f_110654_ = writeMaskIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_110673_(C_4149_.C_4158_ lineIn) {
            this.f_110655_ = lineIn;
            return this;
         }

         public C_4168_.C_4172_.C_4173_ m_286027_(C_4149_.C_285538_ colorLogicStateIn) {
            this.f_285600_ = colorLogicStateIn;
            return this;
         }

         public C_4168_.C_4172_ m_110691_(boolean outlineIn) {
            return this.m_110689_(outlineIn ? C_4168_.C_4174_.AFFECTS_OUTLINE : C_4168_.C_4174_.NONE);
         }

         public C_4168_.C_4172_ m_110689_(C_4168_.C_4174_ outlineStateIn) {
            return new C_4168_.C_4172_(
               this.f_110641_,
               this.f_173289_,
               this.f_110642_,
               this.f_110646_,
               this.f_110647_,
               this.f_110648_,
               this.f_110649_,
               this.f_110651_,
               this.f_110652_,
               this.f_110653_,
               this.f_110654_,
               this.f_110655_,
               this.f_285600_,
               outlineStateIn
            );
         }
      }
   }

   static enum C_4174_ {
      NONE("none"),
      IS_OUTLINE("is_outline"),
      AFFECTS_OUTLINE("affects_outline");

      private final String f_110696_;

      private C_4174_(final String nameIn) {
         this.f_110696_ = nameIn;
      }

      public String toString() {
         return this.f_110696_;
      }
   }
}
