import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.src.C_243621_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_336451_;
import net.minecraft.src.C_3423_;
import net.minecraft.src.C_4177_;
import net.minecraft.src.C_4205_;
import net.minecraft.src.C_4213_;
import net.minecraft.src.C_442_;
import net.minecraft.src.C_4529_;
import net.minecraft.src.C_4536_;
import net.minecraft.src.C_4537_;
import net.minecraft.src.C_4542_;
import net.minecraft.src.C_4649_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_336451_.C_336438_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class ModelBakery {
   public static final Material a = new Material(TextureAtlas.e, ResourceLocation.b("block/fire_0"));
   public static final Material b = new Material(TextureAtlas.e, ResourceLocation.b("block/fire_1"));
   public static final Material c = new Material(TextureAtlas.e, ResourceLocation.b("block/lava_flow"));
   public static final Material d = new Material(TextureAtlas.e, ResourceLocation.b("block/water_flow"));
   public static final Material e = new Material(TextureAtlas.e, ResourceLocation.b("block/water_overlay"));
   public static final Material f = new Material(C_4177_.c, ResourceLocation.b("entity/banner_base"));
   public static final Material g = new Material(C_4177_.d, ResourceLocation.b("entity/shield_base"));
   public static final Material h = new Material(C_4177_.d, ResourceLocation.b("entity/shield_base_nopattern"));
   public static final int i = 10;
   public static final List<ResourceLocation> j = (List<ResourceLocation>)IntStream.range(0, 10)
      .mapToObj(indexIn -> ResourceLocation.b("block/destroy_stage_" + indexIn))
      .collect(Collectors.toList());
   public static final List<ResourceLocation> k = (List<ResourceLocation>)j.stream()
      .map(locationIn -> locationIn.a((UnaryOperator<String>)(pathIn -> "textures/" + pathIn + ".png")))
      .collect(Collectors.toList());
   public static final List<RenderType> l = (List<RenderType>)k.stream().map(RenderType::s).collect(Collectors.toList());
   private static final Logger s = LogUtils.getLogger();
   private static final String t = "builtin/";
   private static final String u = "builtin/generated";
   private static final String v = "builtin/entity";
   private static final String w = "missing";
   public static final ResourceLocation m = ResourceLocation.b("builtin/missing");
   public static final C_4536_ n = new C_4536_(m, "missing");
   public static final C_243621_ o = C_243621_.m_246568_("models");
   @VisibleForTesting
   public static final String p = ("{    'textures': {       'particle': '"
         + MissingTextureAtlasSprite.b().a()
         + "',       'missingno': '"
         + MissingTextureAtlasSprite.b().a()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> x = Map.of("missing", p);
   public static final C_4205_ q = Util.a(C_4205_.m_111463_("{\"gui_light\": \"front\"}"), modelIn -> modelIn.f_111416_ = "generation marker");
   public static final C_4205_ r = Util.a(C_4205_.m_111463_("{\"gui_light\": \"side\"}"), modelIn -> modelIn.f_111416_ = "block entity marker");
   static final C_4213_ y = new C_4213_();
   private final Map<ResourceLocation, C_4205_> z;
   private final Set<ResourceLocation> A = new HashSet();
   private final Map<ResourceLocation, C_4542_> B = new HashMap();
   final Map<ModelBakery.a, BakedModel> C = new HashMap();
   private final Map<C_4536_, C_4542_> D = new HashMap();
   private final Map<C_4536_, BakedModel> E = new HashMap();
   private final C_4542_ F;
   private final Object2IntMap<BlockState> G;
   public Map<ResourceLocation, C_4542_> mapUnbakedModels;

   public ModelBakery(
      C_3423_ blockColorsIn, C_442_ profilerIn, Map<ResourceLocation, C_4205_> modelResourcesIn, Map<ResourceLocation, List<C_336438_>> blockStateResourcesIn
   ) {
      this.z = new HashMap(modelResourcesIn);
      profilerIn.m_6180_("missing_model");

      try {
         this.F = this.c(m);
         this.b(n, this.F);
      } catch (IOException var9) {
         s.error("Error loading missing model, should never happen :(", var9);
         throw new RuntimeException(var9);
      }

      C_336451_ blockstatemodelloader = new C_336451_(blockStateResourcesIn, profilerIn, this.F, blockColorsIn, this::a);
      blockstatemodelloader.m_338905_();
      this.G = blockstatemodelloader.m_338779_();
      profilerIn.m_6182_("items");

      for (ResourceLocation resourcelocation : C_256712_.f_257033_.m_6566_()) {
         this.b(resourcelocation);
      }

      profilerIn.m_6182_("special");
      this.a(ItemRenderer.i);
      this.a(ItemRenderer.j);
      Set<C_4536_> additionalModels = Sets.newHashSet();
      Reflector.ForgeHooksClient_onRegisterAdditionalModels.call(additionalModels);

      for (C_4536_ rl : additionalModels) {
         this.b(rl, this.a(rl.b()));
      }

      this.mapUnbakedModels = this.B;
      TextureUtils.registerCustomModels(this);
      this.D.values().forEach(modelIn -> modelIn.m_5500_(this::a));
      profilerIn.m_7238_();
   }

   public void a(ModelBakery.c funcIn) {
      this.D.forEach((locIn, modelIn) -> {
         BakedModel bakedmodel = null;

         try {
            bakedmodel = new ModelBakery.b(funcIn, locIn).a(modelIn, C_4529_.X0_Y0);
         } catch (Exception var6) {
            s.warn("Unable to bake model: '{}': {}", locIn, var6);
         }

         if (bakedmodel != null) {
            this.E.put(locIn, bakedmodel);
         }
      });
   }

   public C_4542_ a(ResourceLocation modelLocation) {
      if (this.B.containsKey(modelLocation)) {
         return (C_4542_)this.B.get(modelLocation);
      } else if (this.A.contains(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + modelLocation);
      } else {
         this.A.add(modelLocation);

         while (!this.A.isEmpty()) {
            ResourceLocation resourcelocation = (ResourceLocation)this.A.iterator().next();

            try {
               if (!this.B.containsKey(resourcelocation)) {
                  C_4542_ unbakedmodel = this.c(resourcelocation);
                  this.B.put(resourcelocation, unbakedmodel);
                  this.A.addAll(unbakedmodel.m_7970_());
               }
            } catch (Exception var7) {
               s.warn("Unable to load model: '{}' referenced from: {}", resourcelocation, modelLocation);
               s.warn(var7.getClass().getName() + ": " + var7.getMessage());
               this.B.put(resourcelocation, this.F);
            } finally {
               this.A.remove(resourcelocation);
            }
         }

         return (C_4542_)this.B.getOrDefault(modelLocation, this.F);
      }
   }

   public void b(ResourceLocation locationIn) {
      C_4536_ modelresourcelocation = C_4536_.a(locationIn);
      ResourceLocation resourcelocation = locationIn.f("item/");
      String path = locationIn.a();
      if (path.startsWith("optifine/") || path.startsWith("item/")) {
         resourcelocation = locationIn.f("");
      }

      C_4542_ unbakedmodel = this.a(resourcelocation);
      this.a(modelresourcelocation, unbakedmodel);
   }

   private void a(C_4536_ locationIn) {
      ResourceLocation resourcelocation = locationIn.b().f("item/");
      C_4542_ unbakedmodel = this.a(resourcelocation);
      this.a(locationIn, unbakedmodel);
   }

   private void a(C_4536_ locationIn, C_4542_ modelIn) {
      for (ResourceLocation resourcelocation : modelIn.m_7970_()) {
         this.a(resourcelocation);
      }

      this.b(locationIn, modelIn);
   }

   private void b(C_4536_ locationIn, C_4542_ modelIn) {
      this.D.put(locationIn, modelIn);
   }

   private C_4205_ c(ResourceLocation location) throws IOException {
      String s = location.a();
      if ("builtin/generated".equals(s)) {
         return q;
      } else if ("builtin/entity".equals(s)) {
         return r;
      } else if (s.startsWith("builtin/")) {
         String s1 = s.substring("builtin/".length());
         String s2 = (String)x.get(s1);
         if (s2 == null) {
            throw new FileNotFoundException(location.toString());
         } else {
            Reader reader = new StringReader(s2);
            C_4205_ blockmodel1 = C_4205_.m_111461_(reader);
            blockmodel1.f_111416_ = location.toString();
            return blockmodel1;
         }
      } else {
         ResourceLocation resourcelocation = this.getModelLocation(location);
         C_4205_ blockmodel = (C_4205_)this.z.get(resourcelocation);
         if (blockmodel == null) {
            blockmodel = this.loadBlockModel(resourcelocation);
            if (blockmodel != null) {
               this.z.put(resourcelocation, blockmodel);
            }
         }

         if (blockmodel == null) {
            throw new FileNotFoundException(resourcelocation.toString());
         } else {
            blockmodel.f_111416_ = location.toString();
            String basePath = TextureUtils.getBasePath(resourcelocation.a());
            fixModelLocations(blockmodel, basePath);
            return blockmodel;
         }
      }
   }

   public Map<C_4536_, BakedModel> a() {
      return this.E;
   }

   public Object2IntMap<BlockState> b() {
      return this.G;
   }

   private ResourceLocation getModelLocation(ResourceLocation location) {
      String path = location.a();
      if (path.startsWith("optifine/")) {
         if (!path.endsWith(".json")) {
            location = new ResourceLocation(location.b(), path + ".json");
         }

         return location;
      } else {
         return o.a(location);
      }
   }

   public static void fixModelLocations(C_4205_ modelBlock, String basePath) {
      ResourceLocation parentLocFixed = fixModelLocation(FaceBakery.getParentLocation(modelBlock), basePath);
      if (parentLocFixed != FaceBakery.getParentLocation(modelBlock)) {
         FaceBakery.setParentLocation(modelBlock, parentLocFixed);
      }

      if (FaceBakery.getTextures(modelBlock) != null) {
         for (Entry<String, Either<Material, String>> entry : FaceBakery.getTextures(modelBlock).entrySet()) {
            Either<Material, String> value = (Either<Material, String>)entry.getValue();
            Optional<Material> optionalMaterial = value.left();
            if (optionalMaterial.isPresent()) {
               Material material = (Material)optionalMaterial.get();
               ResourceLocation textureLocation = material.b();
               String path = textureLocation.a();
               String pathFixed = fixResourcePath(path, basePath);
               if (!pathFixed.equals(path)) {
                  ResourceLocation textureLocationFixed = new ResourceLocation(textureLocation.b(), pathFixed);
                  Material materialFixed = new Material(material.a(), textureLocationFixed);
                  Either<Material, String> valueFixed = Either.left(materialFixed);
                  entry.setValue(valueFixed);
               }
            }
         }
      }
   }

   public static ResourceLocation fixModelLocation(ResourceLocation loc, String basePath) {
      if (loc != null && basePath != null) {
         if (!loc.b().equals("minecraft")) {
            return loc;
         } else {
            String path = loc.a();
            String pathFixed = fixResourcePath(path, basePath);
            if (pathFixed != path) {
               loc = new ResourceLocation(loc.b(), pathFixed);
            }

            return loc;
         }
      } else {
         return loc;
      }
   }

   private static String fixResourcePath(String path, String basePath) {
      path = TextureUtils.fixResourcePath(path, basePath);
      path = StrUtils.removeSuffix(path, ".json");
      return StrUtils.removeSuffix(path, ".png");
   }

   public C_4205_ loadBlockModel(ResourceLocation locJson) {
      try {
         C_76_ res = Config.getResource(locJson);
         Reader reader = res.m_215508_();
         return C_4205_.m_111461_(reader);
      } catch (Exception var5) {
         Config.warn("Error loading model: " + locJson);
         Config.warn(var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   static record a(ResourceLocation a, C_4649_ b, boolean c) {
   }

   class b implements ModelBaker {
      private final Function<Material, TextureAtlasSprite> b;

      b(final ModelBakery.c funcIn, final C_4536_ locIn) {
         this.b = materialIn -> funcIn.get(locIn, materialIn);
      }

      @Override
      public C_4542_ a(ResourceLocation locIn) {
         return ModelBakery.this.a(locIn);
      }

      @Override
      public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
         return this.b;
      }

      @Override
      public BakedModel a(ResourceLocation locIn, C_4537_ stateIn) {
         return this.bake(locIn, stateIn, this.b);
      }

      @Override
      public BakedModel bake(ResourceLocation locIn, C_4537_ stateIn, Function<Material, TextureAtlasSprite> sprites) {
         ModelBakery.a modelbakery$bakedcachekey = new ModelBakery.a(locIn, stateIn.m_6189_(), stateIn.m_7538_());
         BakedModel bakedmodel = (BakedModel)ModelBakery.this.C.get(modelbakery$bakedcachekey);
         if (bakedmodel != null) {
            return bakedmodel;
         } else {
            C_4542_ unbakedmodel = this.a(locIn);
            BakedModel bakedmodel1 = this.a(unbakedmodel, stateIn);
            ModelBakery.this.C.put(modelbakery$bakedcachekey, bakedmodel1);
            return bakedmodel1;
         }
      }

      @Nullable
      BakedModel a(C_4542_ modelIn, C_4537_ stateIn) {
         if (modelIn instanceof C_4205_ blockmodel && blockmodel.m_111490_() == ModelBakery.q) {
            return ModelBakery.y.m_111670_(this.b, blockmodel).a(this, blockmodel, this.b, stateIn, false);
         }

         return modelIn.a(this, this.b, stateIn);
      }
   }

   @FunctionalInterface
   public interface c {
      TextureAtlasSprite get(C_4536_ var1, Material var2);
   }
}
