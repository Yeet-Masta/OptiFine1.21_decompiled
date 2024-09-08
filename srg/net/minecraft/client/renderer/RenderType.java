package net.minecraft.client.renderer;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
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

public abstract class RenderType extends RenderStateShard {
   private static final int f_173154_ = 1048576;
   public static final int f_173148_ = 4194304;
   public static final int f_173150_ = 786432;
   public static final int f_173151_ = 1536;
   private static final RenderType f_110372_ = m_173215_(
      "solid",
      DefaultVertexFormat.f_85811_,
      VertexFormat.Mode.QUADS,
      4194304,
      true,
      false,
      RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173105_).m_173290_(f_110145_).m_110691_(true)
   );
   private static final RenderType f_110373_ = m_173215_(
      "cutout_mipped",
      DefaultVertexFormat.f_85811_,
      VertexFormat.Mode.QUADS,
      4194304,
      true,
      false,
      RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173106_).m_173290_(f_110145_).m_110691_(true)
   );
   private static final RenderType f_110374_ = m_173215_(
      "cutout",
      DefaultVertexFormat.f_85811_,
      VertexFormat.Mode.QUADS,
      786432,
      true,
      false,
      RenderType.CompositeState.m_110628_().m_110671_(f_110152_).m_173292_(f_173107_).m_173290_(f_110146_).m_110691_(true)
   );
   private static final RenderType f_110375_ = m_173215_(
      "translucent", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 786432, true, true, m_173207_(f_173108_)
   );
   private static final RenderType f_110376_ = m_173215_(
      "translucent_moving_block", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 786432, false, true, m_110408_()
   );
   private static final Function<ResourceLocation, RenderType> f_173155_ = Util.m_143827_(p_292067_0_ -> m_293457_("armor_cutout_no_cull", p_292067_0_, false));
   private static final Function<ResourceLocation, RenderType> f_173156_ = Util.m_143827_(
      p_285690_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173112_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285690_0_, false, false))
            .m_110685_(f_110134_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_solid", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173157_ = Util.m_143827_(
      p_285702_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173113_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285702_0_, false, false))
            .m_110685_(f_110134_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_cutout", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173160_ = Util.m_143821_(
      (p_285696_0_, p_285696_1_) -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173114_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285696_0_, false, false))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(p_285696_1_);
         return m_173215_("entity_cutout_no_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173161_ = Util.m_143821_(
      (p_285686_0_, p_285686_1_) -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173063_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285686_0_, false, false))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110669_(f_110119_)
            .m_110691_(p_285686_1_);
         return m_173215_("entity_cutout_no_cull_z_offset", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173162_ = Util.m_143827_(
      p_285687_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173064_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285687_0_, false, false))
            .m_110685_(f_110139_)
            .m_110675_(f_110129_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(RenderStateShard.f_110114_)
            .m_110691_(true);
         return m_173215_("item_entity_translucent_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173163_ = Util.m_143827_(
      p_285695_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173065_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285695_0_, false, false))
            .m_110685_(f_110139_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(true);
         return m_173215_("entity_translucent_cull", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173164_ = Util.m_143821_(
      (p_285688_0_, p_285688_1_) -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173066_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285688_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(p_285688_1_);
         return m_173215_("entity_translucent", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_234325_ = Util.m_143821_(
      (p_285694_0_, p_285694_1_) -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_234323_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285694_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110687_(f_110115_)
            .m_110677_(f_110154_)
            .m_110691_(p_285694_1_);
         return m_173215_("entity_translucent_emissive", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, true, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173165_ = Util.m_143827_(
      p_285698_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173067_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285698_0_, false, false))
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110691_(true);
         return m_173209_("entity_smooth_cutout", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> f_173166_ = Util.m_143821_(
      (p_234329_0_, p_234329_1_) -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173068_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_234329_0_, false, false))
            .m_110685_(p_234329_1_ ? f_110139_ : f_110134_)
            .m_110687_(p_234329_1_ ? f_110115_ : f_110114_)
            .m_110691_(false);
         return m_173215_("beacon_beam", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 1536, false, true, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173167_ = Util.m_143827_(
      p_285700_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173069_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285700_0_, false, false))
            .m_110663_(f_110112_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false);
         return m_173209_("entity_decal", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173168_ = Util.m_143827_(
      p_285691_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173070_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285691_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110691_(false);
         return m_173215_("entity_no_outline", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, false, true, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173169_ = Util.m_143827_(
      p_285684_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173071_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285684_0_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110158_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110687_(f_110115_)
            .m_110663_(f_110113_)
            .m_110669_(f_110119_)
            .m_110691_(false);
         return m_173215_("entity_shadow", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, false, false, rendertype$compositestate);
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173170_ = Util.m_143827_(
      p_285683_0_ -> {
         RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
            .m_173292_(f_173072_)
            .m_173290_(new RenderStateShard.TextureStateShard(p_285683_0_, false, false))
            .m_110661_(f_110110_)
            .m_110691_(true);
         return m_173209_("entity_alpha", DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, RenderStateShard.TransparencyStateShard, RenderType> f_173171_ = Util.m_143821_(
      (p_304056_0_, p_304056_1_) -> {
         RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_304056_0_, false, false);
         return m_173215_(
            "eyes",
            DefaultVertexFormat.f_85812_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173073_)
               .m_173290_(renderstateshard$texturestateshard)
               .m_110685_(p_304056_1_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         );
      }
   );
   private static final RenderType f_110378_ = m_173209_(
      "leash",
      DefaultVertexFormat.f_85816_,
      VertexFormat.Mode.TRIANGLE_STRIP,
      1536,
      RenderType.CompositeState.m_110628_().m_173292_(f_173075_).m_173290_(f_110147_).m_110661_(f_110110_).m_110671_(f_110152_).m_110691_(false)
   );
   private static final RenderType f_110379_ = m_173209_(
      "water_mask",
      DefaultVertexFormat.f_85814_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_().m_173292_(f_173076_).m_173290_(f_110147_).m_110687_(f_110116_).m_110691_(false)
   );
   private static final RenderType f_110381_ = m_173209_(
      "armor_entity_glint",
      DefaultVertexFormat.f_85817_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173079_)
         .m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110151_)
         .m_110669_(f_110119_)
         .m_110691_(false)
   );
   private static final RenderType f_110382_ = m_173209_(
      "glint_translucent",
      DefaultVertexFormat.f_85817_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173080_)
         .m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_273833_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110150_)
         .m_110675_(f_110129_)
         .m_110691_(false)
   );
   private static final RenderType f_110383_ = m_173209_(
      "glint",
      DefaultVertexFormat.f_85817_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173081_)
         .m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_273833_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110150_)
         .m_110691_(false)
   );
   private static final RenderType f_110385_ = m_173209_(
      "entity_glint",
      DefaultVertexFormat.f_85817_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173083_)
         .m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110675_(f_110129_)
         .m_110683_(f_110151_)
         .m_110691_(false)
   );
   private static final RenderType f_110386_ = m_173209_(
      "entity_glint_direct",
      DefaultVertexFormat.f_85817_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173084_)
         .m_173290_(new RenderStateShard.TextureStateShard(ItemRenderer.f_273897_, true, false))
         .m_110687_(f_110115_)
         .m_110661_(f_110110_)
         .m_110663_(f_110112_)
         .m_110685_(f_110137_)
         .m_110683_(f_110151_)
         .m_110691_(false)
   );
   private static final Function<ResourceLocation, RenderType> f_173172_ = Util.m_143827_(
      p_285703_0_ -> {
         RenderStateShard.TextureStateShard renderstateshard$texturestateshard = new RenderStateShard.TextureStateShard(p_285703_0_, false, false);
         return m_173215_(
            "crumbling",
            DefaultVertexFormat.f_85811_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173085_)
               .m_173290_(renderstateshard$texturestateshard)
               .m_110685_(f_110138_)
               .m_110687_(f_110115_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         );
      }
   );
   private static final Function<ResourceLocation, RenderType> f_173173_ = Util.m_143827_(
      p_304055_0_ -> m_173215_(
            "text",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            786432,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173086_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_304055_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110691_(false)
         )
   );
   private static final RenderType f_268665_ = m_173215_(
      "text_background",
      DefaultVertexFormat.f_85816_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_().m_173292_(f_268568_).m_173290_(f_110147_).m_110685_(f_110139_).m_110671_(f_110152_).m_110691_(false)
   );
   private static final Function<ResourceLocation, RenderType> f_173174_ = Util.m_143827_(
      p_304057_0_ -> m_173215_(
            "text_intensity",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            786432,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173087_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_304057_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110691_(false)
         )
   );
   private static final Function<ResourceLocation, RenderType> f_181442_ = Util.m_143827_(
      p_285685_0_ -> m_173215_(
            "text_polygon_offset",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173086_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_285685_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         )
   );
   private static final Function<ResourceLocation, RenderType> f_181443_ = Util.m_143827_(
      p_285704_0_ -> m_173215_(
            "text_intensity_polygon_offset",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173087_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_285704_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110669_(f_110118_)
               .m_110691_(false)
         )
   );
   private static final Function<ResourceLocation, RenderType> f_173175_ = Util.m_143827_(
      p_285689_0_ -> m_173215_(
            "text_see_through",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173088_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_285689_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110663_(f_110111_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         )
   );
   private static final RenderType f_268619_ = m_173215_(
      "text_background_see_through",
      DefaultVertexFormat.f_85816_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_268491_)
         .m_173290_(f_110147_)
         .m_110685_(f_110139_)
         .m_110671_(f_110152_)
         .m_110663_(f_110111_)
         .m_110687_(f_110115_)
         .m_110691_(false)
   );
   private static final Function<ResourceLocation, RenderType> f_173176_ = Util.m_143827_(
      p_285697_0_ -> m_173215_(
            "text_intensity_see_through",
            DefaultVertexFormat.f_85820_,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            false,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173090_)
               .m_173290_(new RenderStateShard.TextureStateShard(p_285697_0_, false, false))
               .m_110685_(f_110139_)
               .m_110671_(f_110152_)
               .m_110663_(f_110111_)
               .m_110687_(f_110115_)
               .m_110691_(false)
         )
   );
   private static final RenderType f_110387_ = m_173215_(
      "lightning",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_().m_173292_(f_173091_).m_110687_(f_110114_).m_110685_(f_110136_).m_110675_(f_110127_).m_110691_(false)
   );
   private static final RenderType f_336773_ = m_173215_(
      "dragon_rays",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.TRIANGLES,
      1536,
      false,
      false,
      RenderType.CompositeState.m_110628_().m_173292_(f_173091_).m_110687_(f_110115_).m_110685_(f_110136_).m_110691_(false)
   );
   private static final RenderType f_337318_ = m_173215_(
      "dragon_rays_depth",
      DefaultVertexFormat.f_85814_,
      VertexFormat.Mode.TRIANGLES,
      1536,
      false,
      false,
      RenderType.CompositeState.m_110628_().m_173292_(RenderStateShard.f_173100_).m_110687_(f_110116_).m_110691_(false)
   );
   private static final RenderType f_110388_ = m_173215_("tripwire", DefaultVertexFormat.f_85811_, VertexFormat.Mode.QUADS, 1536, true, true, m_110409_());
   private static final RenderType f_173158_ = m_173215_(
      "end_portal",
      DefaultVertexFormat.f_85814_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173093_)
         .m_173290_(
            RenderStateShard.MultiTextureStateShard.m_173127_()
               .m_173132_(TheEndPortalRenderer.f_112626_, false, false)
               .m_173132_(TheEndPortalRenderer.f_112627_, false, false)
               .m_173131_()
         )
         .m_110691_(false)
   );
   private static final RenderType f_173159_ = m_173215_(
      "end_gateway",
      DefaultVertexFormat.f_85814_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      false,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173094_)
         .m_173290_(
            RenderStateShard.MultiTextureStateShard.m_173127_()
               .m_173132_(TheEndPortalRenderer.f_112626_, false, false)
               .m_173132_(TheEndPortalRenderer.f_112627_, false, false)
               .m_173131_()
         )
         .m_110691_(false)
   );
   private static final RenderType f_314866_ = m_318882_(false);
   private static final RenderType f_315561_ = m_318882_(true);
   public static final RenderType.CompositeRenderType f_110371_ = m_173209_(
      "lines",
      DefaultVertexFormat.f_166851_,
      VertexFormat.Mode.LINES,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173095_)
         .m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
         .m_110669_(f_110119_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110687_(f_110114_)
         .m_110661_(f_110110_)
         .m_110691_(false)
   );
   public static final RenderType.CompositeRenderType f_173152_ = m_173209_(
      "line_strip",
      DefaultVertexFormat.f_166851_,
      VertexFormat.Mode.LINE_STRIP,
      1536,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173095_)
         .m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
         .m_110669_(f_110119_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110687_(f_110114_)
         .m_110661_(f_110110_)
         .m_110691_(false)
   );
   private static final Function<Double, RenderType.CompositeRenderType> f_268753_ = Util.m_143827_(
      p_285693_0_ -> m_173209_(
            "debug_line_strip",
            DefaultVertexFormat.f_85815_,
            VertexFormat.Mode.DEBUG_LINE_STRIP,
            1536,
            RenderType.CompositeState.m_110628_()
               .m_173292_(f_173104_)
               .m_110673_(new RenderStateShard.LineStateShard(OptionalDouble.of(p_285693_0_)))
               .m_110685_(f_110134_)
               .m_110661_(f_110110_)
               .m_110691_(false)
         )
   );
   private static final RenderType.CompositeRenderType f_268540_ = m_173215_(
      "debug_filled_box",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.TRIANGLE_STRIP,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_().m_173292_(f_173104_).m_110669_(f_110119_).m_110685_(f_110139_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_268519_ = m_173215_(
      "debug_quads",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_().m_173292_(f_173104_).m_110685_(f_110139_).m_110661_(f_110110_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_337222_ = m_173215_(
      "debug_structure_quads",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_()
         .m_173292_(f_173104_)
         .m_110685_(f_110139_)
         .m_110661_(f_110110_)
         .m_110663_(f_110113_)
         .m_110687_(f_110115_)
         .m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_279582_ = m_173215_(
      "debug_section_quads",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      false,
      true,
      RenderType.CompositeState.m_110628_().m_173292_(f_173104_).m_110669_(f_110119_).m_110685_(f_110139_).m_110661_(f_110158_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_285558_ = m_173209_(
      "gui",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      786432,
      RenderType.CompositeState.m_110628_().m_173292_(f_285573_).m_110685_(f_110139_).m_110663_(f_110113_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_285624_ = m_173209_(
      "gui_overlay",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_().m_173292_(f_285619_).m_110685_(f_110139_).m_110663_(f_110111_).m_110687_(f_110115_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_285662_ = m_173209_(
      "gui_text_highlight",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_().m_173292_(f_285642_).m_110685_(f_110139_).m_110663_(f_110111_).m_286027_(f_285603_).m_110691_(false)
   );
   private static final RenderType.CompositeRenderType f_285639_ = m_173209_(
      "gui_ghost_recipe_overlay",
      DefaultVertexFormat.f_85815_,
      VertexFormat.Mode.QUADS,
      1536,
      RenderType.CompositeState.m_110628_().m_173292_(f_285582_).m_110685_(f_110139_).m_110663_(f_285579_).m_110687_(f_110115_).m_110691_(false)
   );
   private static final ImmutableList<RenderType> f_234324_ = ImmutableList.of(m_110451_(), m_110457_(), m_110463_(), m_110466_(), m_110503_());
   private final VertexFormat f_110389_;
   private final VertexFormat.Mode f_110390_;
   private final int f_110391_;
   private final boolean f_110392_;
   private final boolean f_110393_;
   private int id = -1;
   public static final RenderType[] CHUNK_RENDER_TYPES = getChunkRenderTypesArray();
   private static Map<CompoundKey, RenderType> RENDER_TYPES;
   private int chunkLayerId = -1;

   public int ordinal() {
      return this.id;
   }

   public boolean isNeedsSorting() {
      return this.f_110393_;
   }

   private static RenderType[] getChunkRenderTypesArray() {
      RenderType[] renderTypes = (RenderType[])m_110506_().toArray(new RenderType[0]);
      int i = 0;

      while (i < renderTypes.length) {
         RenderType renderType = renderTypes[i];
         renderType.id = i++;
      }

      return renderTypes;
   }

   public static RenderType m_110451_() {
      return f_110372_;
   }

   public static RenderType m_110457_() {
      return f_110373_;
   }

   public static RenderType m_110463_() {
      return f_110374_;
   }

   private static RenderType.CompositeState m_173207_(RenderStateShard.ShaderStateShard shardIn) {
      return RenderType.CompositeState.m_110628_()
         .m_110671_(f_110152_)
         .m_173292_(shardIn)
         .m_173290_(f_110145_)
         .m_110685_(f_110139_)
         .m_110675_(f_110125_)
         .m_110691_(true);
   }

   public static RenderType m_110466_() {
      return f_110375_;
   }

   private static RenderType.CompositeState m_110408_() {
      return RenderType.CompositeState.m_110628_()
         .m_110671_(f_110152_)
         .m_173292_(f_173109_)
         .m_173290_(f_110145_)
         .m_110685_(f_110139_)
         .m_110675_(f_110129_)
         .m_110691_(true);
   }

   public static RenderType m_110469_() {
      return f_110376_;
   }

   private static RenderType.CompositeRenderType m_293457_(String nameIn, ResourceLocation locationIn, boolean depthIn) {
      RenderType.CompositeState rendertype$compositestate = RenderType.CompositeState.m_110628_()
         .m_173292_(f_173111_)
         .m_173290_(new RenderStateShard.TextureStateShard(locationIn, false, false))
         .m_110685_(f_110134_)
         .m_110661_(f_110110_)
         .m_110671_(f_110152_)
         .m_110677_(f_110154_)
         .m_110669_(f_110119_)
         .m_110663_(depthIn ? f_110112_ : f_110113_)
         .m_110691_(true);
      return m_173215_(nameIn, DefaultVertexFormat.f_85812_, VertexFormat.Mode.QUADS, 1536, true, false, rendertype$compositestate);
   }

   public static RenderType m_110431_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173155_.apply(locationIn);
   }

   public static RenderType m_294378_(ResourceLocation locationIn) {
      return m_293457_("armor_decal_cutout_no_cull", locationIn, true);
   }

   public static RenderType m_110446_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return EmissiveTextures.isRenderEmissive() ? (RenderType)f_173157_.apply(locationIn) : (RenderType)f_173156_.apply(locationIn);
   }

   public static RenderType m_110452_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173157_.apply(locationIn);
   }

   public static RenderType m_110443_(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173160_.apply(locationIn, outlineIn);
   }

   public static RenderType m_110458_(ResourceLocation locationIn) {
      return m_110443_(locationIn, true);
   }

   public static RenderType m_110448_(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173161_.apply(locationIn, outlineIn);
   }

   public static RenderType m_110464_(ResourceLocation locationIn) {
      return m_110448_(locationIn, true);
   }

   public static RenderType m_110467_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173162_.apply(locationIn);
   }

   public static RenderType m_110470_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173163_.apply(locationIn);
   }

   public static RenderType m_110454_(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173164_.apply(locationIn, outlineIn);
   }

   public static RenderType m_110473_(ResourceLocation locationIn) {
      return m_110454_(locationIn, true);
   }

   public static RenderType m_234335_(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_234325_.apply(locationIn, outlineIn);
   }

   public static RenderType m_234338_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return m_234335_(locationIn, true);
   }

   public static RenderType m_110476_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173165_.apply(locationIn);
   }

   public static RenderType m_110460_(ResourceLocation locationIn, boolean colorFlagIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173166_.apply(locationIn, colorFlagIn);
   }

   public static RenderType m_110479_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173167_.apply(locationIn);
   }

   public static RenderType m_110482_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173168_.apply(locationIn);
   }

   public static RenderType m_110485_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173169_.apply(locationIn);
   }

   public static RenderType m_173235_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173170_.apply(locationIn);
   }

   public static RenderType m_110488_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173171_.apply(locationIn, f_110135_);
   }

   public static RenderType m_305574_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_234325_.apply(locationIn, false);
   }

   public static RenderType m_305520_(ResourceLocation locationIn, float offsetUIn, float offsetVIn) {
      locationIn = getCustomTexture(locationIn);
      return m_173215_(
         "breeze_wind",
         DefaultVertexFormat.f_85812_,
         VertexFormat.Mode.QUADS,
         1536,
         false,
         true,
         RenderType.CompositeState.m_110628_()
            .m_173292_(f_303180_)
            .m_173290_(new RenderStateShard.TextureStateShard(locationIn, false, false))
            .m_110683_(new RenderStateShard.OffsetTexturingStateShard(offsetUIn, offsetVIn))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110155_)
            .m_110691_(false)
      );
   }

   public static RenderType m_110436_(ResourceLocation locationIn, float uIn, float vIn) {
      locationIn = getCustomTexture(locationIn);
      return m_173215_(
         "energy_swirl",
         DefaultVertexFormat.f_85812_,
         VertexFormat.Mode.QUADS,
         1536,
         false,
         true,
         RenderType.CompositeState.m_110628_()
            .m_173292_(f_173074_)
            .m_173290_(new RenderStateShard.TextureStateShard(locationIn, false, false))
            .m_110683_(new RenderStateShard.OffsetTexturingStateShard(uIn, vIn))
            .m_110685_(f_110135_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(false)
      );
   }

   public static RenderType m_110475_() {
      return f_110378_;
   }

   public static RenderType m_110478_() {
      return f_110379_;
   }

   public static RenderType m_110491_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)RenderType.CompositeRenderType.f_173256_.apply(locationIn, f_110110_);
   }

   public static RenderType m_110484_() {
      return f_110381_;
   }

   public static RenderType m_110487_() {
      return f_110382_;
   }

   public static RenderType m_110490_() {
      return f_110383_;
   }

   public static RenderType m_110496_() {
      return f_110385_;
   }

   public static RenderType m_110499_() {
      return f_110386_;
   }

   public static RenderType m_110494_(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)f_173172_.apply(locationIn);
   }

   public static RenderType m_110497_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getText(locationIn) : (RenderType)f_173173_.apply(locationIn);
   }

   public static RenderType m_269058_() {
      return f_268665_;
   }

   public static RenderType m_173237_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensity(locationIn) : (RenderType)f_173174_.apply(locationIn);
   }

   public static RenderType m_181444_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextPolygonOffset(locationIn) : (RenderType)f_181442_.apply(locationIn);
   }

   public static RenderType m_181446_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensityPolygonOffset(locationIn) : (RenderType)f_181443_.apply(locationIn);
   }

   public static RenderType m_110500_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextSeeThrough(locationIn) : (RenderType)f_173175_.apply(locationIn);
   }

   public static RenderType m_269508_() {
      return f_268619_;
   }

   public static RenderType m_173240_(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensitySeeThrough(locationIn) : (RenderType)f_173176_.apply(locationIn);
   }

   public static RenderType m_110502_() {
      return f_110387_;
   }

   public static RenderType m_339127_() {
      return f_336773_;
   }

   public static RenderType m_339251_() {
      return f_337318_;
   }

   private static RenderType.CompositeState m_110409_() {
      return RenderType.CompositeState.m_110628_()
         .m_110671_(f_110152_)
         .m_173292_(f_173092_)
         .m_173290_(f_110145_)
         .m_110685_(f_110139_)
         .m_110675_(f_110127_)
         .m_110691_(true);
   }

   public static RenderType m_110503_() {
      return f_110388_;
   }

   public static RenderType m_173239_() {
      return f_173158_;
   }

   public static RenderType m_173242_() {
      return f_173159_;
   }

   private static RenderType.CompositeRenderType m_318882_(boolean depthOnly) {
      return m_173215_(
         "clouds",
         DefaultVertexFormat.f_85822_,
         VertexFormat.Mode.QUADS,
         786432,
         false,
         false,
         RenderType.CompositeState.m_110628_()
            .m_173292_(f_316212_)
            .m_173290_(new RenderStateShard.TextureStateShard(LevelRenderer.f_109456_, false, false))
            .m_110685_(f_110139_)
            .m_110661_(f_110110_)
            .m_110687_(depthOnly ? f_110116_ : f_110114_)
            .m_110675_(f_110128_)
            .m_110691_(true)
      );
   }

   public static RenderType m_325090_() {
      return f_314866_;
   }

   public static RenderType m_319097_() {
      return f_315561_;
   }

   public static RenderType m_110504_() {
      return f_110371_;
   }

   public static RenderType m_173247_() {
      return f_173152_;
   }

   public static RenderType m_269399_(double widthIn) {
      return (RenderType)f_268753_.apply(widthIn);
   }

   public static RenderType m_269313_() {
      return f_268540_;
   }

   public static RenderType m_269166_() {
      return f_268519_;
   }

   public static RenderType m_340348_() {
      return f_337222_;
   }

   public static RenderType m_280070_() {
      return f_279582_;
   }

   public static RenderType m_285907_() {
      return f_285558_;
   }

   public static RenderType m_286086_() {
      return f_285624_;
   }

   public static RenderType m_285783_() {
      return f_285662_;
   }

   public static RenderType m_285811_() {
      return f_285639_;
   }

   public RenderType(
      String nameIn,
      VertexFormat formatIn,
      VertexFormat.Mode drawModeIn,
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

   static RenderType.CompositeRenderType m_173209_(
      String nameIn, VertexFormat vertexFormatIn, VertexFormat.Mode drawModeIn, int bufferSizeIn, RenderType.CompositeState renderStateIn
   ) {
      return m_173215_(nameIn, vertexFormatIn, drawModeIn, bufferSizeIn, false, false, renderStateIn);
   }

   static RenderType.CompositeRenderType m_173215_(
      String name,
      VertexFormat vertexFormatIn,
      VertexFormat.Mode glMode,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      RenderType.CompositeState renderStateIn
   ) {
      return new RenderType.CompositeRenderType(name, vertexFormatIn, glMode, bufferSizeIn, useDelegateIn, needsSortingIn, renderStateIn);
   }

   public void m_339876_(MeshData dataIn) {
      this.m_110185_();
      if (Config.isShaders()) {
         RenderUtils.setFlushRenderBuffers(false);
         Shaders.pushProgram();
         ShadersRender.preRender(this);
      }

      BufferUploader.m_231202_(dataIn);
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

   public static List<RenderType> m_110506_() {
      return f_234324_;
   }

   public int m_110507_() {
      return this.f_110391_;
   }

   public VertexFormat m_110508_() {
      return this.f_110389_;
   }

   public VertexFormat.Mode m_173186_() {
      return this.f_110390_;
   }

   public Optional<RenderType> m_7280_() {
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

   public static ResourceLocation getCustomTexture(ResourceLocation locationIn) {
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

   public ResourceLocation getTextureLocation() {
      return null;
   }

   public boolean isGlint() {
      return this.getTextureLocation() == ItemRenderer.f_273897_ | this.getTextureLocation() == ItemRenderer.f_273833_;
   }

   public boolean isAtlasTextureBlocks() {
      ResourceLocation loc = this.getTextureLocation();
      return loc == TextureAtlas.f_118259_;
   }

   public final int getChunkLayerId() {
      return this.chunkLayerId;
   }

   static {
      int i = 0;

      for (RenderType layer : m_110506_()) {
         layer.chunkLayerId = i++;
      }
   }

   static final class CompositeRenderType extends RenderType {
      static final BiFunction<ResourceLocation, RenderStateShard.CullStateShard, RenderType> f_173256_ = Util.m_143821_(
         (p_337854_0_, p_337854_1_) -> RenderType.m_173209_(
               "outline",
               DefaultVertexFormat.f_85819_,
               VertexFormat.Mode.QUADS,
               1536,
               RenderType.CompositeState.m_110628_()
                  .m_173292_(f_173077_)
                  .m_173290_(new RenderStateShard.TextureStateShard(p_337854_0_, false, false))
                  .m_110661_(p_337854_1_)
                  .m_110663_(f_110111_)
                  .m_110675_(f_110124_)
                  .m_110689_(RenderType.OutlineProperty.IS_OUTLINE)
            )
      );
      private final RenderType.CompositeState f_110511_;
      private final Optional<RenderType> f_110513_;
      private final boolean f_110514_;
      private Map<ResourceLocation, RenderType.CompositeRenderType> mapTextured = new HashMap();

      CompositeRenderType(
         String name,
         VertexFormat vertexFormatIn,
         VertexFormat.Mode glMode,
         int bufferSizeIn,
         boolean useDelegateIn,
         boolean needsSortingIn,
         RenderType.CompositeState renderStateIn
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
         this.f_110513_ = renderStateIn.f_110591_ == RenderType.OutlineProperty.AFFECTS_OUTLINE
            ? renderStateIn.f_110576_.m_142706_().map(locationIn -> (RenderType)f_173256_.apply(locationIn, renderStateIn.f_110582_))
            : Optional.empty();
         this.f_110514_ = renderStateIn.f_110591_ == RenderType.OutlineProperty.IS_OUTLINE;
      }

      @Override
      public Optional<RenderType> m_7280_() {
         return this.f_110513_;
      }

      @Override
      public boolean m_5492_() {
         return this.f_110514_;
      }

      protected final RenderType.CompositeState m_173265_() {
         return this.f_110511_;
      }

      @Override
      public String toString() {
         return "RenderType[" + this.f_110133_ + ":" + this.f_110511_ + "]";
      }

      public RenderType.CompositeRenderType getTextured(ResourceLocation textureLocation) {
         if (textureLocation == null) {
            return this;
         } else {
            Optional<ResourceLocation> optLoc = this.f_110511_.f_110576_.m_142706_();
            if (!optLoc.isPresent()) {
               return this;
            } else {
               ResourceLocation loc = (ResourceLocation)optLoc.get();
               if (loc == null) {
                  return this;
               } else if (textureLocation.equals(loc)) {
                  return this;
               } else {
                  RenderType.CompositeRenderType typeTex = (RenderType.CompositeRenderType)this.mapTextured.get(textureLocation);
                  if (typeTex == null) {
                     RenderType.CompositeState.CompositeStateBuilder builderTex = this.f_110511_.getCopyBuilder();
                     builderTex.m_173290_(
                        new RenderStateShard.TextureStateShard(textureLocation, this.f_110511_.f_110576_.isBlur(), this.f_110511_.f_110576_.isMipmap())
                     );
                     RenderType.CompositeState stateTex = builderTex.m_110691_(this.f_110514_);
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
      public ResourceLocation getTextureLocation() {
         Optional<ResourceLocation> optLoc = this.f_110511_.f_110576_.m_142706_();
         return !optLoc.isPresent() ? null : (ResourceLocation)optLoc.get();
      }
   }

   protected static final class CompositeState {
      final RenderStateShard.EmptyTextureStateShard f_110576_;
      private final RenderStateShard.ShaderStateShard f_173274_;
      private final RenderStateShard.TransparencyStateShard f_110577_;
      private final RenderStateShard.DepthTestStateShard f_110581_;
      final RenderStateShard.CullStateShard f_110582_;
      private final RenderStateShard.LightmapStateShard f_110583_;
      private final RenderStateShard.OverlayStateShard f_110584_;
      private final RenderStateShard.LayeringStateShard f_110586_;
      private final RenderStateShard.OutputStateShard f_110587_;
      private final RenderStateShard.TexturingStateShard f_110588_;
      private final RenderStateShard.WriteMaskStateShard f_110589_;
      private final RenderStateShard.LineStateShard f_110590_;
      private final RenderStateShard.ColorLogicStateShard f_285566_;
      final RenderType.OutlineProperty f_110591_;
      final ImmutableList<RenderStateShard> f_110592_;

      CompositeState(
         RenderStateShard.EmptyTextureStateShard textureIn,
         RenderStateShard.ShaderStateShard shaderStateIn,
         RenderStateShard.TransparencyStateShard transparencyIn,
         RenderStateShard.DepthTestStateShard depthTestIn,
         RenderStateShard.CullStateShard cullIn,
         RenderStateShard.LightmapStateShard lightmapIn,
         RenderStateShard.OverlayStateShard overlayIn,
         RenderStateShard.LayeringStateShard layerIn,
         RenderStateShard.OutputStateShard targetIn,
         RenderStateShard.TexturingStateShard texturingIn,
         RenderStateShard.WriteMaskStateShard writeMaskIn,
         RenderStateShard.LineStateShard lineIn,
         RenderStateShard.ColorLogicStateShard colorLogicStateIn,
         RenderType.OutlineProperty outlineIn
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
            new RenderStateShard[]{this.f_110590_}
         );
      }

      public String toString() {
         return "CompositeState[" + this.f_110592_ + ", outlineProperty=" + this.f_110591_ + "]";
      }

      public static RenderType.CompositeState.CompositeStateBuilder m_110628_() {
         return new RenderType.CompositeState.CompositeStateBuilder();
      }

      public RenderType.CompositeState.CompositeStateBuilder getCopyBuilder() {
         RenderType.CompositeState.CompositeStateBuilder builder = new RenderType.CompositeState.CompositeStateBuilder();
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

      public static class CompositeStateBuilder {
         private RenderStateShard.EmptyTextureStateShard f_110641_ = RenderStateShard.f_110147_;
         private RenderStateShard.ShaderStateShard f_173289_ = RenderStateShard.f_173096_;
         private RenderStateShard.TransparencyStateShard f_110642_ = RenderStateShard.f_110134_;
         private RenderStateShard.DepthTestStateShard f_110646_ = RenderStateShard.f_110113_;
         private RenderStateShard.CullStateShard f_110647_ = RenderStateShard.f_110158_;
         private RenderStateShard.LightmapStateShard f_110648_ = RenderStateShard.f_110153_;
         private RenderStateShard.OverlayStateShard f_110649_ = RenderStateShard.f_110155_;
         private RenderStateShard.LayeringStateShard f_110651_ = RenderStateShard.f_110117_;
         private RenderStateShard.OutputStateShard f_110652_ = RenderStateShard.f_110123_;
         private RenderStateShard.TexturingStateShard f_110653_ = RenderStateShard.f_110148_;
         private RenderStateShard.WriteMaskStateShard f_110654_ = RenderStateShard.f_110114_;
         private RenderStateShard.LineStateShard f_110655_ = RenderStateShard.f_110130_;
         private RenderStateShard.ColorLogicStateShard f_285600_ = RenderStateShard.f_285585_;

         CompositeStateBuilder() {
         }

         public RenderType.CompositeState.CompositeStateBuilder m_173290_(RenderStateShard.EmptyTextureStateShard textureIn) {
            this.f_110641_ = textureIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_173292_(RenderStateShard.ShaderStateShard shaderStateIn) {
            this.f_173289_ = shaderStateIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110685_(RenderStateShard.TransparencyStateShard transparencyIn) {
            this.f_110642_ = transparencyIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110663_(RenderStateShard.DepthTestStateShard depthTestIn) {
            this.f_110646_ = depthTestIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110661_(RenderStateShard.CullStateShard cullIn) {
            this.f_110647_ = cullIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110671_(RenderStateShard.LightmapStateShard lightmapIn) {
            this.f_110648_ = lightmapIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110677_(RenderStateShard.OverlayStateShard overlayIn) {
            this.f_110649_ = overlayIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110669_(RenderStateShard.LayeringStateShard layerIn) {
            this.f_110651_ = layerIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110675_(RenderStateShard.OutputStateShard targetIn) {
            this.f_110652_ = targetIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110683_(RenderStateShard.TexturingStateShard texturingIn) {
            this.f_110653_ = texturingIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110687_(RenderStateShard.WriteMaskStateShard writeMaskIn) {
            this.f_110654_ = writeMaskIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_110673_(RenderStateShard.LineStateShard lineIn) {
            this.f_110655_ = lineIn;
            return this;
         }

         public RenderType.CompositeState.CompositeStateBuilder m_286027_(RenderStateShard.ColorLogicStateShard colorLogicStateIn) {
            this.f_285600_ = colorLogicStateIn;
            return this;
         }

         public RenderType.CompositeState m_110691_(boolean outlineIn) {
            return this.m_110689_(outlineIn ? RenderType.OutlineProperty.AFFECTS_OUTLINE : RenderType.OutlineProperty.NONE);
         }

         public RenderType.CompositeState m_110689_(RenderType.OutlineProperty outlineStateIn) {
            return new RenderType.CompositeState(
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

   static enum OutlineProperty {
      NONE("none"),
      IS_OUTLINE("is_outline"),
      AFFECTS_OUTLINE("affects_outline");

      private final String f_110696_;

      private OutlineProperty(final String nameIn) {
         this.f_110696_ = nameIn;
      }

      public String toString() {
         return this.f_110696_;
      }
   }
}
