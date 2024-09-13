package net.minecraft.client.resources.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.math.Transformation;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BlockStateModelLoader.LoadedJson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class ModelBakery {
   public static Material f_119219_ = new Material(TextureAtlas.f_118259_, ResourceLocation.m_340282_("block/fire_0"));
   public static Material f_119220_ = new Material(TextureAtlas.f_118259_, ResourceLocation.m_340282_("block/fire_1"));
   public static Material f_119221_ = new Material(TextureAtlas.f_118259_, ResourceLocation.m_340282_("block/lava_flow"));
   public static Material f_119222_ = new Material(TextureAtlas.f_118259_, ResourceLocation.m_340282_("block/water_flow"));
   public static Material f_119223_ = new Material(TextureAtlas.f_118259_, ResourceLocation.m_340282_("block/water_overlay"));
   public static Material f_119224_ = new Material(Sheets.f_110737_, ResourceLocation.m_340282_("entity/banner_base"));
   public static Material f_119225_ = new Material(Sheets.f_110738_, ResourceLocation.m_340282_("entity/shield_base"));
   public static Material f_119226_ = new Material(Sheets.f_110738_, ResourceLocation.m_340282_("entity/shield_base_nopattern"));
   public static int f_174875_;
   public static List<ResourceLocation> f_119227_ = (List<ResourceLocation>)IntStream.range(0, 10)
      .mapToObj(indexIn -> ResourceLocation.m_340282_("block/destroy_stage_" + indexIn))
      .collect(Collectors.toList());
   public static List<ResourceLocation> f_119228_ = (List<ResourceLocation>)f_119227_.stream()
      .map(locationIn -> locationIn.m_247266_(pathIn -> "textures/" + pathIn + ".png"))
      .collect(Collectors.toList());
   public static List<RenderType> f_119229_ = (List<RenderType>)f_119228_.stream().map(RenderType::m_110494_).collect(Collectors.toList());
   private static Logger f_119235_ = LogUtils.getLogger();
   private static String f_174878_;
   private static String f_174879_;
   private static String f_174880_;
   private static String f_174881_;
   public static ResourceLocation f_119230_ = ResourceLocation.m_340282_("builtin/missing");
   public static ModelResourceLocation f_336634_ = new ModelResourceLocation(f_119230_, "missing");
   public static FileToIdConverter f_244378_ = FileToIdConverter.m_246568_("models");
   @VisibleForTesting
   public static String f_119231_ = ("{    'textures': {       'particle': '"
         + MissingTextureAtlasSprite.m_118071_().m_135815_()
         + "',       'missingno': '"
         + MissingTextureAtlasSprite.m_118071_().m_135815_()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static Map<String, String> f_119237_ = Map.m_253057_("missing", f_119231_);
   public static BlockModel f_119232_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"front\"}"), modelIn -> modelIn.f_111416_ = "generation marker");
   public static BlockModel f_119233_ = Util.m_137469_(BlockModel.m_111463_("{\"gui_light\": \"side\"}"), modelIn -> modelIn.f_111416_ = "block entity marker");
   static ItemModelGenerator f_119241_ = new ItemModelGenerator();
   private Map<ResourceLocation, BlockModel> f_244132_;
   private Set<ResourceLocation> f_119210_ = new HashSet();
   private Map<ResourceLocation, UnbakedModel> f_119212_ = new HashMap();
   Map<ModelBakery.BakedCacheKey, BakedModel> f_119213_ = new HashMap();
   private Map<ModelResourceLocation, UnbakedModel> f_119214_ = new HashMap();
   private Map<ModelResourceLocation, BakedModel> f_119215_ = new HashMap();
   private UnbakedModel f_336931_;
   private Object2IntMap<BlockState> f_119218_;
   public Map<ResourceLocation, UnbakedModel> mapUnbakedModels;

   public ModelBakery(
      BlockColors blockColorsIn,
      ProfilerFiller profilerIn,
      Map<ResourceLocation, BlockModel> modelResourcesIn,
      Map<ResourceLocation, List<LoadedJson>> blockStateResourcesIn
   ) {
      this.f_244132_ = new HashMap(modelResourcesIn);
      profilerIn.m_6180_("missing_model");

      try {
         this.f_336931_ = this.m_119364_(f_119230_);
         this.m_338699_(f_336634_, this.f_336931_);
      } catch (IOException var9) {
         f_119235_.error("Error loading missing model, should never happen :(", var9);
         throw new RuntimeException(var9);
      }

      BlockStateModelLoader blockstatemodelloader = new BlockStateModelLoader(blockStateResourcesIn, profilerIn, this.f_336931_, blockColorsIn, this::m_340411_);
      blockstatemodelloader.m_338905_();
      this.f_119218_ = blockstatemodelloader.m_338779_();
      profilerIn.m_6182_("items");

      for (ResourceLocation resourcelocation : BuiltInRegistries.f_257033_.m_6566_()) {
         this.m_339007_(resourcelocation);
      }

      profilerIn.m_6182_("special");
      this.m_338793_(ItemRenderer.f_244055_);
      this.m_338793_(ItemRenderer.f_243706_);
      Set<ModelResourceLocation> additionalModels = Sets.newHashSet();
      Reflector.ForgeHooksClient_onRegisterAdditionalModels.m_46374_(additionalModels);

      for (ModelResourceLocation rl : additionalModels) {
         this.m_338699_(rl, this.m_119341_(rl.f_336625_()));
      }

      this.mapUnbakedModels = this.f_119212_;
      TextureUtils.registerCustomModels(this);
      this.f_119214_.values().forEach(modelIn -> modelIn.m_5500_(this::m_119341_));
      profilerIn.m_7238_();
   }

   public void m_245909_(ModelBakery.TextureGetter funcIn) {
      this.f_119214_.forEach((locIn, modelIn) -> {
         BakedModel bakedmodel = null;

         try {
            bakedmodel = new ModelBakery.ModelBakerImpl(funcIn, locIn).m_339454_(modelIn, BlockModelRotation.X0_Y0);
         } catch (Exception var6) {
            f_119235_.warn("Unable to bake model: '{}': {}", locIn, var6);
         }

         if (bakedmodel != null) {
            this.f_119215_.put(locIn, bakedmodel);
         }
      });
   }

   public UnbakedModel m_119341_(ResourceLocation modelLocation) {
      if (this.f_119212_.containsKey(modelLocation)) {
         return (UnbakedModel)this.f_119212_.get(modelLocation);
      } else if (this.f_119210_.m_274455_(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + modelLocation);
      } else {
         this.f_119210_.add(modelLocation);

         while (!this.f_119210_.isEmpty()) {
            ResourceLocation resourcelocation = (ResourceLocation)this.f_119210_.iterator().next();

            try {
               if (!this.f_119212_.containsKey(resourcelocation)) {
                  UnbakedModel unbakedmodel = this.m_119364_(resourcelocation);
                  this.f_119212_.put(resourcelocation, unbakedmodel);
                  this.f_119210_.addAll(unbakedmodel.m_7970_());
               }
            } catch (Exception var7) {
               f_119235_.warn("Unable to load model: '{}' referenced from: {}", resourcelocation, modelLocation);
               f_119235_.warn(var7.getClass().getName() + ": " + var7.getMessage());
               this.f_119212_.put(resourcelocation, this.f_336931_);
            } finally {
               this.f_119210_.remove(resourcelocation);
            }
         }

         return (UnbakedModel)this.f_119212_.getOrDefault(modelLocation, this.f_336931_);
      }
   }

   public void m_339007_(ResourceLocation locationIn) {
      ModelResourceLocation modelresourcelocation = ModelResourceLocation.m_340229_(locationIn);
      ResourceLocation resourcelocation = locationIn.m_246208_("item/");
      String path = locationIn.m_135815_();
      if (path.startsWith("optifine/") || path.startsWith("item/")) {
         resourcelocation = locationIn.m_246208_("");
      }

      UnbakedModel unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(modelresourcelocation, unbakedmodel);
   }

   private void m_338793_(ModelResourceLocation locationIn) {
      ResourceLocation resourcelocation = locationIn.f_336625_().m_246208_("item/");
      UnbakedModel unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(locationIn, unbakedmodel);
   }

   private void m_340411_(ModelResourceLocation locationIn, UnbakedModel modelIn) {
      for (ResourceLocation resourcelocation : modelIn.m_7970_()) {
         this.m_119341_(resourcelocation);
      }

      this.m_338699_(locationIn, modelIn);
   }

   private void m_338699_(ModelResourceLocation locationIn, UnbakedModel modelIn) {
      this.f_119214_.put(locationIn, modelIn);
   }

   private BlockModel m_119364_(ResourceLocation location) throws IOException {
      String s = location.m_135815_();
      if ("builtin/generated".equals(s)) {
         return f_119232_;
      } else if ("builtin/entity".equals(s)) {
         return f_119233_;
      } else if (s.startsWith("builtin/")) {
         String s1 = s.substring("builtin/".length());
         String s2 = (String)f_119237_.get(s1);
         if (s2 == null) {
            throw new FileNotFoundException(location.toString());
         } else {
            Reader reader = new StringReader(s2);
            BlockModel blockmodel1 = BlockModel.m_111461_(reader);
            blockmodel1.f_111416_ = location.toString();
            return blockmodel1;
         }
      } else {
         ResourceLocation resourcelocation = this.getModelLocation(location);
         BlockModel blockmodel = (BlockModel)this.f_244132_.get(resourcelocation);
         if (blockmodel == null) {
            blockmodel = this.loadBlockModel(resourcelocation);
            if (blockmodel != null) {
               this.f_244132_.put(resourcelocation, blockmodel);
            }
         }

         if (blockmodel == null) {
            throw new FileNotFoundException(resourcelocation.toString());
         } else {
            blockmodel.f_111416_ = location.toString();
            String basePath = TextureUtils.getBasePath(resourcelocation.m_135815_());
            fixModelLocations(blockmodel, basePath);
            return blockmodel;
         }
      }
   }

   public Map<ModelResourceLocation, BakedModel> m_119251_() {
      return this.f_119215_;
   }

   public Object2IntMap<BlockState> m_119355_() {
      return this.f_119218_;
   }

   private ResourceLocation getModelLocation(ResourceLocation location) {
      String path = location.m_135815_();
      if (path.startsWith("optifine/")) {
         if (!path.endsWith(".json")) {
            location = new ResourceLocation(location.m_135827_(), path + ".json");
         }

         return location;
      } else {
         return f_244378_.m_245698_(location);
      }
   }

   public static void fixModelLocations(BlockModel modelBlock, String basePath) {
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
               ResourceLocation textureLocation = material.m_119203_();
               String path = textureLocation.m_135815_();
               String pathFixed = fixResourcePath(path, basePath);
               if (!pathFixed.equals(path)) {
                  ResourceLocation textureLocationFixed = new ResourceLocation(textureLocation.m_135827_(), pathFixed);
                  Material materialFixed = new Material(material.m_119193_(), textureLocationFixed);
                  Either<Material, String> valueFixed = Either.left(materialFixed);
                  entry.setValue(valueFixed);
               }
            }
         }
      }
   }

   public static ResourceLocation fixModelLocation(ResourceLocation loc, String basePath) {
      if (loc != null && basePath != null) {
         if (!loc.m_135827_().equals("minecraft")) {
            return loc;
         } else {
            String path = loc.m_135815_();
            String pathFixed = fixResourcePath(path, basePath);
            if (pathFixed != path) {
               loc = new ResourceLocation(loc.m_135827_(), pathFixed);
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

   public BlockModel loadBlockModel(ResourceLocation locJson) {
      try {
         Resource res = Config.m_213713_(locJson);
         Reader reader = res.m_215508_();
         return BlockModel.m_111461_(reader);
      } catch (Exception var5) {
         Config.warn("Error loading model: " + locJson);
         Config.warn(var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   static record BakedCacheKey(ResourceLocation f_243934_, Transformation f_243798_, boolean f_243915_) {
   }

   class ModelBakerImpl implements ModelBaker {
      private Function<Material, TextureAtlasSprite> f_243920_;

      ModelBakerImpl(final ModelBakery.TextureGetter funcIn, final ModelResourceLocation locIn) {
         this.f_243920_ = materialIn -> funcIn.m_338804_(locIn, materialIn);
      }

      @Override
      public UnbakedModel m_245361_(ResourceLocation locIn) {
         return ModelBakery.this.m_119341_(locIn);
      }

      public Function<Material, TextureAtlasSprite> getModelTextureGetter() {
         return this.f_243920_;
      }

      @Override
      public BakedModel m_245240_(ResourceLocation locIn, ModelState stateIn) {
         return this.m_213604_(locIn, stateIn, this.f_243920_);
      }

      public BakedModel m_213604_(ResourceLocation locIn, ModelState stateIn, Function<Material, TextureAtlasSprite> sprites) {
         ModelBakery.BakedCacheKey modelbakery$bakedcachekey = new ModelBakery.BakedCacheKey(locIn, stateIn.m_6189_(), stateIn.m_7538_());
         BakedModel bakedmodel = (BakedModel)ModelBakery.this.f_119213_.get(modelbakery$bakedcachekey);
         if (bakedmodel != null) {
            return bakedmodel;
         } else {
            UnbakedModel unbakedmodel = this.m_245361_(locIn);
            BakedModel bakedmodel1 = this.m_339454_(unbakedmodel, stateIn);
            ModelBakery.this.f_119213_.put(modelbakery$bakedcachekey, bakedmodel1);
            return bakedmodel1;
         }
      }

      @Nullable
      BakedModel m_339454_(UnbakedModel modelIn, ModelState stateIn) {
         if (modelIn instanceof BlockModel blockmodel && blockmodel.m_111490_() == ModelBakery.f_119232_) {
            return ModelBakery.f_119241_.m_111670_(this.f_243920_, blockmodel).m_111449_(this, blockmodel, this.f_243920_, stateIn, false);
         }

         return modelIn.m_7611_(this, this.f_243920_, stateIn);
      }
   }

   @FunctionalInterface
   public interface TextureGetter {
      TextureAtlasSprite m_338804_(ModelResourceLocation var1, Material var2);
   }
}
