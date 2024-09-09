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

public abstract class RenderType extends RenderStateShard {
   private static final int aV = 1048576;
   public static final int aQ = 4194304;
   public static final int aR = 786432;
   public static final int aS = 1536;
   private static final RenderType aW = a("solid", DefaultVertexFormat.b, VertexFormat.c.h, 4194304, true, false, RenderType.b.a().a(aq).a(o).a(ak).a(true));
   private static final RenderType aX = a(
      "cutout_mipped", DefaultVertexFormat.b, VertexFormat.c.h, 4194304, true, false, RenderType.b.a().a(aq).a(p).a(ak).a(true)
   );
   private static final RenderType aY = a("cutout", DefaultVertexFormat.b, VertexFormat.c.h, 786432, true, false, RenderType.b.a().a(aq).a(q).a(al).a(true));
   private static final RenderType aZ = a("translucent", DefaultVertexFormat.b, VertexFormat.c.h, 786432, true, true, a(r));
   private static final RenderType ba = a("translucent_moving_block", DefaultVertexFormat.b, VertexFormat.c.h, 786432, false, true, R());
   private static final Function<ResourceLocation, RenderType> bb = Util.b(
      (Function<ResourceLocation, RenderType>)(p_292067_0_ -> a("armor_cutout_no_cull", p_292067_0_, false))
   );
   private static final Function<ResourceLocation, RenderType> bc = Util.b((Function<ResourceLocation, RenderType>)(p_285690_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(u).a(new RenderStateShard.n(p_285690_0_, false, false)).a(c).a(aq).a(as).a(true);
      return a("entity_solid", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, false, rendertype$compositestate);
   }));
   private static final Function<ResourceLocation, RenderType> bd = Util.b((Function<ResourceLocation, RenderType>)(p_285702_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(v).a(new RenderStateShard.n(p_285702_0_, false, false)).a(c).a(aq).a(as).a(true);
      return a("entity_cutout", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, false, rendertype$compositestate);
   }));
   private static final BiFunction<ResourceLocation, Boolean, RenderType> be = Util.a(
      (BiFunction<ResourceLocation, Boolean, RenderType>)((p_285696_0_, p_285696_1_) -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(w)
            .a(new RenderStateShard.n(p_285696_0_, false, false))
            .a(c)
            .a(av)
            .a(aq)
            .a(as)
            .a(p_285696_1_);
         return a("entity_cutout_no_cull", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, false, rendertype$compositestate);
      })
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> bf = Util.a(
      (BiFunction<ResourceLocation, Boolean, RenderType>)((p_285686_0_, p_285686_1_) -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(x)
            .a(new RenderStateShard.n(p_285686_0_, false, false))
            .a(c)
            .a(av)
            .a(aq)
            .a(as)
            .a(aF)
            .a(p_285686_1_);
         return a("entity_cutout_no_cull_z_offset", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, false, rendertype$compositestate);
      })
   );
   private static final Function<ResourceLocation, RenderType> bg = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285687_0_ -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(y)
            .a(new RenderStateShard.n(p_285687_0_, false, false))
            .a(h)
            .a(aM)
            .a(aq)
            .a(as)
            .a(RenderStateShard.aA)
            .a(true);
         return a("item_entity_translucent_cull", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, true, rendertype$compositestate);
      })
   );
   private static final Function<ResourceLocation, RenderType> bh = Util.b((Function<ResourceLocation, RenderType>)(p_285695_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(z).a(new RenderStateShard.n(p_285695_0_, false, false)).a(h).a(aq).a(as).a(true);
      return a("entity_translucent_cull", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, true, rendertype$compositestate);
   }));
   private static final BiFunction<ResourceLocation, Boolean, RenderType> bi = Util.a(
      (BiFunction<ResourceLocation, Boolean, RenderType>)((p_285688_0_, p_285688_1_) -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(A)
            .a(new RenderStateShard.n(p_285688_0_, false, false))
            .a(h)
            .a(av)
            .a(aq)
            .a(as)
            .a(p_285688_1_);
         return a("entity_translucent", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, true, rendertype$compositestate);
      })
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> bj = Util.a(
      (BiFunction<ResourceLocation, Boolean, RenderType>)((p_285694_0_, p_285694_1_) -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(B)
            .a(new RenderStateShard.n(p_285694_0_, false, false))
            .a(h)
            .a(av)
            .a(aB)
            .a(as)
            .a(p_285694_1_);
         return a("entity_translucent_emissive", DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, true, rendertype$compositestate);
      })
   );
   private static final Function<ResourceLocation, RenderType> bk = Util.b((Function<ResourceLocation, RenderType>)(p_285698_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(C).a(new RenderStateShard.n(p_285698_0_, false, false)).a(av).a(aq).a(true);
      return a("entity_smooth_cutout", DefaultVertexFormat.c, VertexFormat.c.h, 1536, rendertype$compositestate);
   }));
   private static final BiFunction<ResourceLocation, Boolean, RenderType> bl = Util.a(
      (BiFunction<ResourceLocation, Boolean, RenderType>)((p_234329_0_, p_234329_1_) -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(D)
            .a(new RenderStateShard.n(p_234329_0_, false, false))
            .a(p_234329_1_ ? h : c)
            .a(p_234329_1_ ? aB : aA)
            .a(false);
         return a("beacon_beam", DefaultVertexFormat.b, VertexFormat.c.h, 1536, false, true, rendertype$compositestate);
      })
   );
   private static final Function<ResourceLocation, RenderType> bm = Util.b((Function<ResourceLocation, RenderType>)(p_285700_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(E).a(new RenderStateShard.n(p_285700_0_, false, false)).a(ax).a(av).a(aq).a(as).a(false);
      return a("entity_decal", DefaultVertexFormat.c, VertexFormat.c.h, 1536, rendertype$compositestate);
   }));
   private static final Function<ResourceLocation, RenderType> bn = Util.b((Function<ResourceLocation, RenderType>)(p_285691_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(F).a(new RenderStateShard.n(p_285691_0_, false, false)).a(h).a(av).a(aq).a(as).a(aB).a(false);
      return a("entity_no_outline", DefaultVertexFormat.c, VertexFormat.c.h, 1536, false, true, rendertype$compositestate);
   }));
   private static final Function<ResourceLocation, RenderType> bo = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285684_0_ -> {
         RenderType.b rendertype$compositestate = RenderType.b.a()
            .a(G)
            .a(new RenderStateShard.n(p_285684_0_, false, false))
            .a(h)
            .a(au)
            .a(aq)
            .a(as)
            .a(aB)
            .a(ay)
            .a(aF)
            .a(false);
         return a("entity_shadow", DefaultVertexFormat.c, VertexFormat.c.h, 1536, false, false, rendertype$compositestate);
      })
   );
   private static final Function<ResourceLocation, RenderType> bp = Util.b((Function<ResourceLocation, RenderType>)(p_285683_0_ -> {
      RenderType.b rendertype$compositestate = RenderType.b.a().a(H).a(new RenderStateShard.n(p_285683_0_, false, false)).a(av).a(true);
      return a("entity_alpha", DefaultVertexFormat.c, VertexFormat.c.h, 1536, rendertype$compositestate);
   }));
   private static final BiFunction<ResourceLocation, RenderStateShard.p, RenderType> bq = Util.a(
      (BiFunction<ResourceLocation, RenderStateShard.p, RenderType>)((p_304056_0_, p_304056_1_) -> {
         RenderStateShard.n renderstateshard$texturestateshard = new RenderStateShard.n(p_304056_0_, false, false);
         return a(
            "eyes",
            DefaultVertexFormat.c,
            VertexFormat.c.h,
            1536,
            false,
            true,
            RenderType.b.a().a(I).a(renderstateshard$texturestateshard).a(p_304056_1_).a(aB).a(false)
         );
      })
   );
   private static final RenderType br = a("leash", DefaultVertexFormat.h, VertexFormat.c.f, 1536, RenderType.b.a().a(K).a(am).a(av).a(aq).a(false));
   private static final RenderType bs = a("water_mask", DefaultVertexFormat.e, VertexFormat.c.h, 1536, RenderType.b.a().a(L).a(am).a(aC).a(false));
   private static final RenderType bt = a(
      "armor_entity_glint",
      DefaultVertexFormat.i,
      VertexFormat.c.h,
      1536,
      RenderType.b.a().a(N).a(new RenderStateShard.n(ItemRenderer.a, true, false)).a(aB).a(av).a(ax).a(f).a(ap).a(aF).a(false)
   );
   private static final RenderType bu = a(
      "glint_translucent",
      DefaultVertexFormat.i,
      VertexFormat.c.h,
      1536,
      RenderType.b.a().a(O).a(new RenderStateShard.n(ItemRenderer.b, true, false)).a(aB).a(av).a(ax).a(f).a(ao).a(aM).a(false)
   );
   private static final RenderType bv = a(
      "glint",
      DefaultVertexFormat.i,
      VertexFormat.c.h,
      1536,
      RenderType.b.a().a(P).a(new RenderStateShard.n(ItemRenderer.b, true, false)).a(aB).a(av).a(ax).a(f).a(ao).a(false)
   );
   private static final RenderType bw = a(
      "entity_glint",
      DefaultVertexFormat.i,
      VertexFormat.c.h,
      1536,
      RenderType.b.a().a(Q).a(new RenderStateShard.n(ItemRenderer.a, true, false)).a(aB).a(av).a(ax).a(f).a(aM).a(ap).a(false)
   );
   private static final RenderType bx = a(
      "entity_glint_direct",
      DefaultVertexFormat.i,
      VertexFormat.c.h,
      1536,
      RenderType.b.a().a(R).a(new RenderStateShard.n(ItemRenderer.a, true, false)).a(aB).a(av).a(ax).a(f).a(ap).a(false)
   );
   private static final Function<ResourceLocation, RenderType> by = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285703_0_ -> {
         RenderStateShard.n renderstateshard$texturestateshard = new RenderStateShard.n(p_285703_0_, false, false);
         return a(
            "crumbling",
            DefaultVertexFormat.b,
            VertexFormat.c.h,
            1536,
            false,
            true,
            RenderType.b.a().a(S).a(renderstateshard$texturestateshard).a(g).a(aB).a(aE).a(false)
         );
      })
   );
   private static final Function<ResourceLocation, RenderType> bz = Util.b(
      (Function<ResourceLocation, RenderType>)(p_304055_0_ -> a(
            "text",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            786432,
            false,
            false,
            RenderType.b.a().a(T).a(new RenderStateShard.n(p_304055_0_, false, false)).a(h).a(aq).a(false)
         ))
   );
   private static final RenderType bA = a(
      "text_background", DefaultVertexFormat.h, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(U).a(am).a(h).a(aq).a(false)
   );
   private static final Function<ResourceLocation, RenderType> bB = Util.b(
      (Function<ResourceLocation, RenderType>)(p_304057_0_ -> a(
            "text_intensity",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            786432,
            false,
            false,
            RenderType.b.a().a(V).a(new RenderStateShard.n(p_304057_0_, false, false)).a(h).a(aq).a(false)
         ))
   );
   private static final Function<ResourceLocation, RenderType> bC = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285685_0_ -> a(
            "text_polygon_offset",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            1536,
            false,
            false,
            RenderType.b.a().a(T).a(new RenderStateShard.n(p_285685_0_, false, false)).a(h).a(aq).a(aE).a(false)
         ))
   );
   private static final Function<ResourceLocation, RenderType> bD = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285704_0_ -> a(
            "text_intensity_polygon_offset",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            1536,
            false,
            false,
            RenderType.b.a().a(V).a(new RenderStateShard.n(p_285704_0_, false, false)).a(h).a(aq).a(aE).a(false)
         ))
   );
   private static final Function<ResourceLocation, RenderType> bE = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285689_0_ -> a(
            "text_see_through",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            1536,
            false,
            false,
            RenderType.b.a().a(W).a(new RenderStateShard.n(p_285689_0_, false, false)).a(h).a(aq).a(aw).a(aB).a(false)
         ))
   );
   private static final RenderType bF = a(
      "text_background_see_through", DefaultVertexFormat.h, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(X).a(am).a(h).a(aq).a(aw).a(aB).a(false)
   );
   private static final Function<ResourceLocation, RenderType> bG = Util.b(
      (Function<ResourceLocation, RenderType>)(p_285697_0_ -> a(
            "text_intensity_see_through",
            DefaultVertexFormat.k,
            VertexFormat.c.h,
            1536,
            false,
            false,
            RenderType.b.a().a(Y).a(new RenderStateShard.n(p_285697_0_, false, false)).a(h).a(aq).a(aw).a(aB).a(false)
         ))
   );
   private static final RenderType bH = a(
      "lightning", DefaultVertexFormat.f, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(Z).a(aA).a(e).a(aK).a(false)
   );
   private static final RenderType bI = a("dragon_rays", DefaultVertexFormat.f, VertexFormat.c.e, 1536, false, false, RenderType.b.a().a(Z).a(aB).a(e).a(false));
   private static final RenderType bJ = a(
      "dragon_rays_depth", DefaultVertexFormat.e, VertexFormat.c.e, 1536, false, false, RenderType.b.a().a(RenderStateShard.k).a(aC).a(false)
   );
   private static final RenderType bK = a("tripwire", DefaultVertexFormat.b, VertexFormat.c.h, 1536, true, true, S());
   private static final RenderType bL = a(
      "end_portal",
      DefaultVertexFormat.e,
      VertexFormat.c.h,
      1536,
      false,
      false,
      RenderType.b.a().a(ab).a(RenderStateShard.i.d().a(TheEndPortalRenderer.a, false, false).a(TheEndPortalRenderer.b, false, false).a()).a(false)
   );
   private static final RenderType bM = a(
      "end_gateway",
      DefaultVertexFormat.e,
      VertexFormat.c.h,
      1536,
      false,
      false,
      RenderType.b.a().a(ac).a(RenderStateShard.i.d().a(TheEndPortalRenderer.a, false, false).a(TheEndPortalRenderer.b, false, false).a()).a(false)
   );
   private static final RenderType bN = a(false);
   private static final RenderType bO = a(true);
   public static final RenderType.a aT = a(
      "lines",
      DefaultVertexFormat.g,
      VertexFormat.c.a,
      1536,
      RenderType.b.a().a(ae).a(new RenderStateShard.h(OptionalDouble.empty())).a(aF).a(h).a(aM).a(aA).a(av).a(false)
   );
   public static final RenderType.a aU = a(
      "line_strip",
      DefaultVertexFormat.g,
      VertexFormat.c.b,
      1536,
      RenderType.b.a().a(ae).a(new RenderStateShard.h(OptionalDouble.empty())).a(aF).a(h).a(aM).a(aA).a(av).a(false)
   );
   private static final Function<Double, RenderType.a> bP = Util.b(
      (Function<Double, RenderType.a>)(p_285693_0_ -> a(
            "debug_line_strip",
            DefaultVertexFormat.f,
            VertexFormat.c.d,
            1536,
            RenderType.b.a().a(n).a(new RenderStateShard.h(OptionalDouble.of(p_285693_0_))).a(c).a(av).a(false)
         ))
   );
   private static final RenderType.a bQ = a(
      "debug_filled_box", DefaultVertexFormat.f, VertexFormat.c.f, 1536, false, true, RenderType.b.a().a(n).a(aF).a(h).a(false)
   );
   private static final RenderType.a bR = a(
      "debug_quads", DefaultVertexFormat.f, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(n).a(h).a(av).a(false)
   );
   private static final RenderType.a bS = a(
      "debug_structure_quads", DefaultVertexFormat.f, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(n).a(h).a(av).a(ay).a(aB).a(false)
   );
   private static final RenderType.a bT = a(
      "debug_section_quads", DefaultVertexFormat.f, VertexFormat.c.h, 1536, false, true, RenderType.b.a().a(n).a(aF).a(h).a(au).a(false)
   );
   private static final RenderType.a bU = a("gui", DefaultVertexFormat.f, VertexFormat.c.h, 786432, RenderType.b.a().a(af).a(h).a(ay).a(false));
   private static final RenderType.a bV = a("gui_overlay", DefaultVertexFormat.f, VertexFormat.c.h, 1536, RenderType.b.a().a(ag).a(h).a(aw).a(aB).a(false));
   private static final RenderType.a bW = a(
      "gui_text_highlight", DefaultVertexFormat.f, VertexFormat.c.h, 1536, RenderType.b.a().a(ah).a(h).a(aw).a(aP).a(false)
   );
   private static final RenderType.a bX = a(
      "gui_ghost_recipe_overlay", DefaultVertexFormat.f, VertexFormat.c.h, 1536, RenderType.b.a().a(ai).a(h).a(az).a(aB).a(false)
   );
   private static final ImmutableList<RenderType> bY = ImmutableList.of(c(), d(), e(), f(), t());
   private final VertexFormat bZ;
   private final VertexFormat.c ca;
   private final int cb;
   private final boolean cc;
   private final boolean cd;
   private int id = -1;
   public static final RenderType[] CHUNK_RENDER_TYPES = getChunkRenderTypesArray();
   private static Map<CompoundKey, RenderType> RENDER_TYPES;
   private int chunkLayerId = -1;

   public int ordinal() {
      return this.id;
   }

   public boolean isNeedsSorting() {
      return this.cd;
   }

   private static RenderType[] getChunkRenderTypesArray() {
      RenderType[] renderTypes = (RenderType[])I().toArray(new RenderType[0]);
      int i = 0;

      while (i < renderTypes.length) {
         RenderType renderType = renderTypes[i];
         renderType.id = i++;
      }

      return renderTypes;
   }

   public static RenderType c() {
      return aW;
   }

   public static RenderType d() {
      return aX;
   }

   public static RenderType e() {
      return aY;
   }

   private static RenderType.b a(RenderStateShard.m shardIn) {
      return RenderType.b.a().a(aq).a(shardIn).a(ak).a(h).a(aI).a(true);
   }

   public static RenderType f() {
      return aZ;
   }

   private static RenderType.b R() {
      return RenderType.b.a().a(aq).a(s).a(ak).a(h).a(aM).a(true);
   }

   public static RenderType g() {
      return ba;
   }

   private static RenderType.a a(String nameIn, ResourceLocation locationIn, boolean depthIn) {
      RenderType.b rendertype$compositestate = RenderType.b.a()
         .a(t)
         .a(new RenderStateShard.n(locationIn, false, false))
         .a(c)
         .a(av)
         .a(aq)
         .a(as)
         .a(aF)
         .a(depthIn ? ax : ay)
         .a(true);
      return a(nameIn, DefaultVertexFormat.c, VertexFormat.c.h, 1536, true, false, rendertype$compositestate);
   }

   public static RenderType a(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bb.apply(locationIn);
   }

   public static RenderType b(ResourceLocation locationIn) {
      return a("armor_decal_cutout_no_cull", locationIn, true);
   }

   public static RenderType c(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return EmissiveTextures.isRenderEmissive() ? (RenderType)bd.apply(locationIn) : (RenderType)bc.apply(locationIn);
   }

   public static RenderType d(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bd.apply(locationIn);
   }

   public static RenderType a(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)be.apply(locationIn, outlineIn);
   }

   public static RenderType e(ResourceLocation locationIn) {
      return a(locationIn, true);
   }

   public static RenderType b(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bf.apply(locationIn, outlineIn);
   }

   public static RenderType f(ResourceLocation locationIn) {
      return b(locationIn, true);
   }

   public static RenderType g(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bg.apply(locationIn);
   }

   public static RenderType h(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bh.apply(locationIn);
   }

   public static RenderType c(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bi.apply(locationIn, outlineIn);
   }

   public static RenderType i(ResourceLocation locationIn) {
      return c(locationIn, true);
   }

   public static RenderType d(ResourceLocation locationIn, boolean outlineIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bj.apply(locationIn, outlineIn);
   }

   public static RenderType j(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return d(locationIn, true);
   }

   public static RenderType k(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bk.apply(locationIn);
   }

   public static RenderType e(ResourceLocation locationIn, boolean colorFlagIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bl.apply(locationIn, colorFlagIn);
   }

   public static RenderType l(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bm.apply(locationIn);
   }

   public static RenderType m(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bn.apply(locationIn);
   }

   public static RenderType n(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bo.apply(locationIn);
   }

   public static RenderType o(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bp.apply(locationIn);
   }

   public static RenderType p(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bq.apply(locationIn, d);
   }

   public static RenderType q(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)bj.apply(locationIn, false);
   }

   public static RenderType a(ResourceLocation locationIn, float offsetUIn, float offsetVIn) {
      locationIn = getCustomTexture(locationIn);
      return a(
         "breeze_wind",
         DefaultVertexFormat.c,
         VertexFormat.c.h,
         1536,
         false,
         true,
         RenderType.b.a()
            .a(aj)
            .a(new RenderStateShard.n(locationIn, false, false))
            .a(new RenderStateShard.j(offsetUIn, offsetVIn))
            .a(h)
            .a(av)
            .a(aq)
            .a(at)
            .a(false)
      );
   }

   public static RenderType b(ResourceLocation locationIn, float uIn, float vIn) {
      locationIn = getCustomTexture(locationIn);
      return a(
         "energy_swirl",
         DefaultVertexFormat.c,
         VertexFormat.c.h,
         1536,
         false,
         true,
         RenderType.b.a().a(J).a(new RenderStateShard.n(locationIn, false, false)).a(new RenderStateShard.j(uIn, vIn)).a(d).a(av).a(aq).a(as).a(false)
      );
   }

   public static RenderType h() {
      return br;
   }

   public static RenderType i() {
      return bs;
   }

   public static RenderType r(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)RenderType.a.aV.apply(locationIn, av);
   }

   public static RenderType j() {
      return bt;
   }

   public static RenderType k() {
      return bu;
   }

   public static RenderType l() {
      return bv;
   }

   public static RenderType m() {
      return bw;
   }

   public static RenderType n() {
      return bx;
   }

   public static RenderType s(ResourceLocation locationIn) {
      locationIn = getCustomTexture(locationIn);
      return (RenderType)by.apply(locationIn);
   }

   public static RenderType t(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getText(locationIn) : (RenderType)bz.apply(locationIn);
   }

   public static RenderType o() {
      return bA;
   }

   public static RenderType u(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensity(locationIn) : (RenderType)bB.apply(locationIn);
   }

   public static RenderType v(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextPolygonOffset(locationIn) : (RenderType)bC.apply(locationIn);
   }

   public static RenderType w(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensityPolygonOffset(locationIn) : (RenderType)bD.apply(locationIn);
   }

   public static RenderType x(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextSeeThrough(locationIn) : (RenderType)bE.apply(locationIn);
   }

   public static RenderType p() {
      return bF;
   }

   public static RenderType y(ResourceLocation locationIn) {
      return Reflector.ForgeHooksClient.exists() ? ForgeRenderTypes.getTextIntensitySeeThrough(locationIn) : (RenderType)bG.apply(locationIn);
   }

   public static RenderType q() {
      return bH;
   }

   public static RenderType r() {
      return bI;
   }

   public static RenderType s() {
      return bJ;
   }

   private static RenderType.b S() {
      return RenderType.b.a().a(aq).a(aa).a(ak).a(h).a(aK).a(true);
   }

   public static RenderType t() {
      return bK;
   }

   public static RenderType u() {
      return bL;
   }

   public static RenderType v() {
      return bM;
   }

   private static RenderType.a a(boolean depthOnly) {
      return a(
         "clouds",
         DefaultVertexFormat.m,
         VertexFormat.c.h,
         786432,
         false,
         false,
         RenderType.b.a().a(ad).a(new RenderStateShard.n(LevelRenderer.c, false, false)).a(h).a(av).a(depthOnly ? aC : aA).a(aL).a(true)
      );
   }

   public static RenderType w() {
      return bN;
   }

   public static RenderType x() {
      return bO;
   }

   public static RenderType y() {
      return aT;
   }

   public static RenderType z() {
      return aU;
   }

   public static RenderType a(double widthIn) {
      return (RenderType)bP.apply(widthIn);
   }

   public static RenderType A() {
      return bQ;
   }

   public static RenderType B() {
      return bR;
   }

   public static RenderType C() {
      return bS;
   }

   public static RenderType D() {
      return bT;
   }

   public static RenderType E() {
      return bU;
   }

   public static RenderType F() {
      return bV;
   }

   public static RenderType G() {
      return bW;
   }

   public static RenderType H() {
      return bX;
   }

   public RenderType(
      String nameIn,
      VertexFormat formatIn,
      VertexFormat.c drawModeIn,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      Runnable setupTaskIn,
      Runnable clearTaskIn
   ) {
      super(nameIn, setupTaskIn, clearTaskIn);
      this.bZ = formatIn;
      this.ca = drawModeIn;
      this.cb = bufferSizeIn;
      this.cc = useDelegateIn;
      this.cd = needsSortingIn;
   }

   static RenderType.a a(String nameIn, VertexFormat vertexFormatIn, VertexFormat.c drawModeIn, int bufferSizeIn, RenderType.b renderStateIn) {
      return a(nameIn, vertexFormatIn, drawModeIn, bufferSizeIn, false, false, renderStateIn);
   }

   static RenderType.a a(
      String name,
      VertexFormat vertexFormatIn,
      VertexFormat.c glMode,
      int bufferSizeIn,
      boolean useDelegateIn,
      boolean needsSortingIn,
      RenderType.b renderStateIn
   ) {
      return new RenderType.a(name, vertexFormatIn, glMode, bufferSizeIn, useDelegateIn, needsSortingIn, renderStateIn);
   }

   public void a(MeshData dataIn) {
      this.a();
      if (Config.isShaders()) {
         RenderUtils.setFlushRenderBuffers(false);
         Shaders.pushProgram();
         ShadersRender.preRender(this);
      }

      BufferUploader.a(dataIn);
      if (Config.isShaders()) {
         ShadersRender.postRender(this);
         Shaders.popProgram();
         RenderUtils.setFlushRenderBuffers(true);
      }

      this.b();
   }

   @Override
   public String toString() {
      return this.b;
   }

   public static List<RenderType> I() {
      return bY;
   }

   public int J() {
      return this.cb;
   }

   public VertexFormat K() {
      return this.bZ;
   }

   public VertexFormat.c L() {
      return this.ca;
   }

   public Optional<RenderType> M() {
      return Optional.empty();
   }

   public boolean N() {
      return false;
   }

   public boolean O() {
      return this.cc;
   }

   public boolean P() {
      return !this.ca.l;
   }

   public boolean Q() {
      return this.cd;
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
      return aT.aW.o.size();
   }

   public ResourceLocation getTextureLocation() {
      return null;
   }

   public boolean isGlint() {
      return this.getTextureLocation() == ItemRenderer.a | this.getTextureLocation() == ItemRenderer.b;
   }

   public boolean isAtlasTextureBlocks() {
      ResourceLocation loc = this.getTextureLocation();
      return loc == TextureAtlas.e;
   }

   public final int getChunkLayerId() {
      return this.chunkLayerId;
   }

   static {
      int i = 0;

      for (RenderType layer : I()) {
         layer.chunkLayerId = i++;
      }
   }

   static final class a extends RenderType {
      static final BiFunction<ResourceLocation, RenderStateShard.c, RenderType> aV = Util.a(
         (BiFunction<ResourceLocation, RenderStateShard.c, RenderType>)((p_337854_0_, p_337854_1_) -> RenderType.a(
               "outline",
               DefaultVertexFormat.j,
               VertexFormat.c.h,
               1536,
               RenderType.b.a().a(M).a(new RenderStateShard.n(p_337854_0_, false, false)).a(p_337854_1_).a(aw).a(aH).a(RenderType.c.b)
            ))
      );
      private final RenderType.b aW;
      private final Optional<RenderType> aX;
      private final boolean aY;
      private Map<ResourceLocation, RenderType.a> mapTextured = new HashMap();

      a(
         String name,
         VertexFormat vertexFormatIn,
         VertexFormat.c glMode,
         int bufferSizeIn,
         boolean useDelegateIn,
         boolean needsSortingIn,
         RenderType.b renderStateIn
      ) {
         super(
            name,
            vertexFormatIn,
            glMode,
            bufferSizeIn,
            useDelegateIn,
            needsSortingIn,
            () -> RenderStateManager.setupRenderStates(renderStateIn.o),
            () -> RenderStateManager.clearRenderStates(renderStateIn.o)
         );
         this.aW = renderStateIn;
         this.aX = renderStateIn.n == RenderType.c.c
            ? renderStateIn.a.c().map(locationIn -> (RenderType)aV.apply(locationIn, renderStateIn.e))
            : Optional.empty();
         this.aY = renderStateIn.n == RenderType.c.b;
      }

      @Override
      public Optional<RenderType> M() {
         return this.aX;
      }

      @Override
      public boolean N() {
         return this.aY;
      }

      protected final RenderType.b R() {
         return this.aW;
      }

      @Override
      public String toString() {
         return "RenderType[" + this.b + ":" + this.aW + "]";
      }

      public RenderType.a getTextured(ResourceLocation textureLocation) {
         if (textureLocation == null) {
            return this;
         } else {
            Optional<ResourceLocation> optLoc = this.aW.a.c();
            if (!optLoc.isPresent()) {
               return this;
            } else {
               ResourceLocation loc = (ResourceLocation)optLoc.get();
               if (loc == null) {
                  return this;
               } else if (textureLocation.equals(loc)) {
                  return this;
               } else {
                  RenderType.a typeTex = (RenderType.a)this.mapTextured.get(textureLocation);
                  if (typeTex == null) {
                     RenderType.b.a builderTex = this.aW.getCopyBuilder();
                     builderTex.a(new RenderStateShard.n(textureLocation, this.aW.a.isBlur(), this.aW.a.isMipmap()));
                     RenderType.b stateTex = builderTex.a(this.aY);
                     typeTex = a(this.b, this.K(), this.L(), this.J(), this.O(), this.isNeedsSorting(), stateTex);
                     this.mapTextured.put(textureLocation, typeTex);
                  }

                  return typeTex;
               }
            }
         }
      }

      @Override
      public ResourceLocation getTextureLocation() {
         Optional<ResourceLocation> optLoc = this.aW.a.c();
         return !optLoc.isPresent() ? null : (ResourceLocation)optLoc.get();
      }
   }

   protected static final class b {
      final RenderStateShard.e a;
      private final RenderStateShard.m b;
      private final RenderStateShard.p c;
      private final RenderStateShard.d d;
      final RenderStateShard.c e;
      private final RenderStateShard.g f;
      private final RenderStateShard.l g;
      private final RenderStateShard.f h;
      private final RenderStateShard.k i;
      private final RenderStateShard.o j;
      private final RenderStateShard.q k;
      private final RenderStateShard.h l;
      private final RenderStateShard.b m;
      final RenderType.c n;
      final ImmutableList<RenderStateShard> o;

      b(
         RenderStateShard.e textureIn,
         RenderStateShard.m shaderStateIn,
         RenderStateShard.p transparencyIn,
         RenderStateShard.d depthTestIn,
         RenderStateShard.c cullIn,
         RenderStateShard.g lightmapIn,
         RenderStateShard.l overlayIn,
         RenderStateShard.f layerIn,
         RenderStateShard.k targetIn,
         RenderStateShard.o texturingIn,
         RenderStateShard.q writeMaskIn,
         RenderStateShard.h lineIn,
         RenderStateShard.b colorLogicStateIn,
         RenderType.c outlineIn
      ) {
         this.a = textureIn;
         this.b = shaderStateIn;
         this.c = transparencyIn;
         this.d = depthTestIn;
         this.e = cullIn;
         this.f = lightmapIn;
         this.g = overlayIn;
         this.h = layerIn;
         this.i = targetIn;
         this.j = texturingIn;
         this.k = writeMaskIn;
         this.l = lineIn;
         this.m = colorLogicStateIn;
         this.n = outlineIn;
         this.o = ImmutableList.of(
            this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.m, new RenderStateShard[]{this.l}
         );
      }

      public String toString() {
         return "CompositeState[" + this.o + ", outlineProperty=" + this.n + "]";
      }

      public static RenderType.b.a a() {
         return new RenderType.b.a();
      }

      public RenderType.b.a getCopyBuilder() {
         RenderType.b.a builder = new RenderType.b.a();
         builder.a(this.a);
         builder.a(this.b);
         builder.a(this.c);
         builder.a(this.d);
         builder.a(this.e);
         builder.a(this.f);
         builder.a(this.g);
         builder.a(this.h);
         builder.a(this.i);
         builder.a(this.j);
         builder.a(this.k);
         builder.a(this.l);
         return builder;
      }

      public static class a {
         private RenderStateShard.e a = RenderStateShard.am;
         private RenderStateShard.m b = RenderStateShard.i;
         private RenderStateShard.p c;
         private RenderStateShard.d d;
         private RenderStateShard.c e;
         private RenderStateShard.g f;
         private RenderStateShard.l g;
         private RenderStateShard.f h;
         private RenderStateShard.k i;
         private RenderStateShard.o j;
         private RenderStateShard.q k;
         private RenderStateShard.h l;
         private RenderStateShard.b m;

         a() {
            this.c = RenderStateShard.c;
            this.d = RenderStateShard.ay;
            this.e = RenderStateShard.au;
            this.f = RenderStateShard.ar;
            this.g = RenderStateShard.at;
            this.h = RenderStateShard.aD;
            this.i = RenderStateShard.aG;
            this.j = RenderStateShard.an;
            this.k = RenderStateShard.aA;
            this.l = RenderStateShard.aN;
            this.m = RenderStateShard.aO;
         }

         public RenderType.b.a a(RenderStateShard.e textureIn) {
            this.a = textureIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.m shaderStateIn) {
            this.b = shaderStateIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.p transparencyIn) {
            this.c = transparencyIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.d depthTestIn) {
            this.d = depthTestIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.c cullIn) {
            this.e = cullIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.g lightmapIn) {
            this.f = lightmapIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.l overlayIn) {
            this.g = overlayIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.f layerIn) {
            this.h = layerIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.k targetIn) {
            this.i = targetIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.o texturingIn) {
            this.j = texturingIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.q writeMaskIn) {
            this.k = writeMaskIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.h lineIn) {
            this.l = lineIn;
            return this;
         }

         public RenderType.b.a a(RenderStateShard.b colorLogicStateIn) {
            this.m = colorLogicStateIn;
            return this;
         }

         public RenderType.b a(boolean outlineIn) {
            return this.a(outlineIn ? RenderType.c.c : RenderType.c.a);
         }

         public RenderType.b a(RenderType.c outlineStateIn) {
            return new RenderType.b(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, outlineStateIn);
         }
      }
   }

   static enum c {
      a("none"),
      b("is_outline"),
      c("affects_outline");

      private final String d;

      private c(final String nameIn) {
         this.d = nameIn;
      }

      public String toString() {
         return this.d;
      }
   }
}
