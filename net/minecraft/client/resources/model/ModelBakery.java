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
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.resources.model.BlockStateModelLoader.LoadedJson;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class ModelBakery {
   public static final net.minecraft.client.resources.model.Material f_119219_ = new net.minecraft.client.resources.model.Material(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, net.minecraft.resources.ResourceLocation.m_340282_("block/fire_0")
   );
   public static final net.minecraft.client.resources.model.Material f_119220_ = new net.minecraft.client.resources.model.Material(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, net.minecraft.resources.ResourceLocation.m_340282_("block/fire_1")
   );
   public static final net.minecraft.client.resources.model.Material f_119221_ = new net.minecraft.client.resources.model.Material(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, net.minecraft.resources.ResourceLocation.m_340282_("block/lava_flow")
   );
   public static final net.minecraft.client.resources.model.Material f_119222_ = new net.minecraft.client.resources.model.Material(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, net.minecraft.resources.ResourceLocation.m_340282_("block/water_flow")
   );
   public static final net.minecraft.client.resources.model.Material f_119223_ = new net.minecraft.client.resources.model.Material(
      net.minecraft.client.renderer.texture.TextureAtlas.f_118259_, net.minecraft.resources.ResourceLocation.m_340282_("block/water_overlay")
   );
   public static final net.minecraft.client.resources.model.Material f_119224_ = new net.minecraft.client.resources.model.Material(
      Sheets.f_110737_, net.minecraft.resources.ResourceLocation.m_340282_("entity/banner_base")
   );
   public static final net.minecraft.client.resources.model.Material f_119225_ = new net.minecraft.client.resources.model.Material(
      Sheets.f_110738_, net.minecraft.resources.ResourceLocation.m_340282_("entity/shield_base")
   );
   public static final net.minecraft.client.resources.model.Material f_119226_ = new net.minecraft.client.resources.model.Material(
      Sheets.f_110738_, net.minecraft.resources.ResourceLocation.m_340282_("entity/shield_base_nopattern")
   );
   public static final int f_174875_ = 10;
   public static final List<net.minecraft.resources.ResourceLocation> f_119227_ = (List<net.minecraft.resources.ResourceLocation>)IntStream.range(0, 10)
      .mapToObj(indexIn -> net.minecraft.resources.ResourceLocation.m_340282_("block/destroy_stage_" + indexIn))
      .collect(Collectors.toList());
   public static final List<net.minecraft.resources.ResourceLocation> f_119228_ = (List<net.minecraft.resources.ResourceLocation>)f_119227_.stream()
      .map(locationIn -> locationIn.m_247266_(pathIn -> "textures/" + pathIn + ".png"))
      .collect(Collectors.toList());
   public static final List<net.minecraft.client.renderer.RenderType> f_119229_ = (List<net.minecraft.client.renderer.RenderType>)f_119228_.stream()
      .map(net.minecraft.client.renderer.RenderType::m_110494_)
      .collect(Collectors.toList());
   private static final Logger f_119235_ = LogUtils.getLogger();
   private static final String f_174878_ = "builtin/";
   private static final String f_174879_ = "builtin/generated";
   private static final String f_174880_ = "builtin/entity";
   private static final String f_174881_ = "missing";
   public static final net.minecraft.resources.ResourceLocation f_119230_ = net.minecraft.resources.ResourceLocation.m_340282_("builtin/missing");
   public static final ModelResourceLocation f_336634_ = new ModelResourceLocation(f_119230_, "missing");
   public static final FileToIdConverter f_244378_ = FileToIdConverter.m_246568_("models");
   @VisibleForTesting
   public static final String f_119231_ = ("{    'textures': {       'particle': '"
         + net.minecraft.client.renderer.texture.MissingTextureAtlasSprite.m_118071_().m_135815_()
         + "',       'missingno': '"
         + net.minecraft.client.renderer.texture.MissingTextureAtlasSprite.m_118071_().m_135815_()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> f_119237_ = Map.of("missing", f_119231_);
   public static final BlockModel f_119232_ = net.minecraft.Util.m_137469_(
      BlockModel.m_111463_("{\"gui_light\": \"front\"}"), modelIn -> modelIn.f_111416_ = "generation marker"
   );
   public static final BlockModel f_119233_ = net.minecraft.Util.m_137469_(
      BlockModel.m_111463_("{\"gui_light\": \"side\"}"), modelIn -> modelIn.f_111416_ = "block entity marker"
   );
   static final ItemModelGenerator f_119241_ = new ItemModelGenerator();
   private final Map<net.minecraft.resources.ResourceLocation, BlockModel> f_244132_;
   private final Set<net.minecraft.resources.ResourceLocation> f_119210_ = new HashSet();
   private final Map<net.minecraft.resources.ResourceLocation, UnbakedModel> f_119212_ = new HashMap();
   final Map<net.minecraft.client.resources.model.ModelBakery.BakedCacheKey, net.minecraft.client.resources.model.BakedModel> f_119213_ = new HashMap();
   private final Map<ModelResourceLocation, UnbakedModel> f_119214_ = new HashMap();
   private final Map<ModelResourceLocation, net.minecraft.client.resources.model.BakedModel> f_119215_ = new HashMap();
   private final UnbakedModel f_336931_;
   private final Object2IntMap<net.minecraft.world.level.block.state.BlockState> f_119218_;
   public Map<net.minecraft.resources.ResourceLocation, UnbakedModel> mapUnbakedModels;

   public ModelBakery(
      BlockColors blockColorsIn,
      ProfilerFiller profilerIn,
      Map<net.minecraft.resources.ResourceLocation, BlockModel> modelResourcesIn,
      Map<net.minecraft.resources.ResourceLocation, List<LoadedJson>> blockStateResourcesIn
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

      for (net.minecraft.resources.ResourceLocation resourcelocation : BuiltInRegistries.f_257033_.m_6566_()) {
         this.m_339007_(resourcelocation);
      }

      profilerIn.m_6182_("special");
      this.m_338793_(net.minecraft.client.renderer.entity.ItemRenderer.f_244055_);
      this.m_338793_(net.minecraft.client.renderer.entity.ItemRenderer.f_243706_);
      Set<ModelResourceLocation> additionalModels = Sets.newHashSet();
      Reflector.ForgeHooksClient_onRegisterAdditionalModels.call(additionalModels);

      for (ModelResourceLocation rl : additionalModels) {
         this.m_338699_(rl, this.m_119341_(rl.f_336625_()));
      }

      this.mapUnbakedModels = this.f_119212_;
      TextureUtils.registerCustomModels(this);
      this.f_119214_.values().forEach(modelIn -> modelIn.m_5500_(this::m_119341_));
      profilerIn.m_7238_();
   }

   public void m_245909_(net.minecraft.client.resources.model.ModelBakery.TextureGetter funcIn) {
      this.f_119214_.forEach((locIn, modelIn) -> {
         net.minecraft.client.resources.model.BakedModel bakedmodel = null;

         try {
            bakedmodel = new net.minecraft.client.resources.model.ModelBakery.ModelBakerImpl(funcIn, locIn).m_339454_(modelIn, BlockModelRotation.X0_Y0);
         } catch (Exception var6) {
            f_119235_.warn("Unable to bake model: '{}': {}", locIn, var6);
         }

         if (bakedmodel != null) {
            this.f_119215_.put(locIn, bakedmodel);
         }
      });
   }

   public UnbakedModel m_119341_(net.minecraft.resources.ResourceLocation modelLocation) {
      if (this.f_119212_.containsKey(modelLocation)) {
         return (UnbakedModel)this.f_119212_.get(modelLocation);
      } else if (this.f_119210_.contains(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + modelLocation);
      } else {
         this.f_119210_.add(modelLocation);

         while (!this.f_119210_.isEmpty()) {
            net.minecraft.resources.ResourceLocation resourcelocation = (net.minecraft.resources.ResourceLocation)this.f_119210_.iterator().next();

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

   public void m_339007_(net.minecraft.resources.ResourceLocation locationIn) {
      ModelResourceLocation modelresourcelocation = ModelResourceLocation.m_340229_(locationIn);
      net.minecraft.resources.ResourceLocation resourcelocation = locationIn.m_246208_("item/");
      String path = locationIn.m_135815_();
      if (path.startsWith("optifine/") || path.startsWith("item/")) {
         resourcelocation = locationIn.m_246208_("");
      }

      UnbakedModel unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(modelresourcelocation, unbakedmodel);
   }

   private void m_338793_(ModelResourceLocation locationIn) {
      net.minecraft.resources.ResourceLocation resourcelocation = locationIn.f_336625_().m_246208_("item/");
      UnbakedModel unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(locationIn, unbakedmodel);
   }

   private void m_340411_(ModelResourceLocation locationIn, UnbakedModel modelIn) {
      for (net.minecraft.resources.ResourceLocation resourcelocation : modelIn.m_7970_()) {
         this.m_119341_(resourcelocation);
      }

      this.m_338699_(locationIn, modelIn);
   }

   private void m_338699_(ModelResourceLocation locationIn, UnbakedModel modelIn) {
      this.f_119214_.put(locationIn, modelIn);
   }

   private BlockModel m_119364_(net.minecraft.resources.ResourceLocation location) throws IOException {
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
         net.minecraft.resources.ResourceLocation resourcelocation = this.getModelLocation(location);
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

   public Map<ModelResourceLocation, net.minecraft.client.resources.model.BakedModel> m_119251_() {
      return this.f_119215_;
   }

   public Object2IntMap<net.minecraft.world.level.block.state.BlockState> m_119355_() {
      return this.f_119218_;
   }

   private net.minecraft.resources.ResourceLocation getModelLocation(net.minecraft.resources.ResourceLocation location) {
      String path = location.m_135815_();
      if (path.startsWith("optifine/")) {
         if (!path.endsWith(".json")) {
            location = new net.minecraft.resources.ResourceLocation(location.m_135827_(), path + ".json");
         }

         return location;
      } else {
         return f_244378_.m_245698_(location);
      }
   }

   public static void fixModelLocations(BlockModel modelBlock, String basePath) {
      net.minecraft.resources.ResourceLocation parentLocFixed = fixModelLocation(
         net.minecraft.client.renderer.block.model.FaceBakery.getParentLocation(modelBlock), basePath
      );
      if (parentLocFixed != net.minecraft.client.renderer.block.model.FaceBakery.getParentLocation(modelBlock)) {
         net.minecraft.client.renderer.block.model.FaceBakery.setParentLocation(modelBlock, parentLocFixed);
      }

      if (net.minecraft.client.renderer.block.model.FaceBakery.getTextures(modelBlock) != null) {
         for (Entry<String, Either<net.minecraft.client.resources.model.Material, String>> entry : net.minecraft.client.renderer.block.model.FaceBakery.getTextures(
               modelBlock
            )
            .entrySet()) {
            Either<net.minecraft.client.resources.model.Material, String> value = (Either<net.minecraft.client.resources.model.Material, String>)entry.getValue();
            Optional<net.minecraft.client.resources.model.Material> optionalMaterial = value.left();
            if (optionalMaterial.isPresent()) {
               net.minecraft.client.resources.model.Material material = (net.minecraft.client.resources.model.Material)optionalMaterial.get();
               net.minecraft.resources.ResourceLocation textureLocation = material.m_119203_();
               String path = textureLocation.m_135815_();
               String pathFixed = fixResourcePath(path, basePath);
               if (!pathFixed.equals(path)) {
                  net.minecraft.resources.ResourceLocation textureLocationFixed = new net.minecraft.resources.ResourceLocation(
                     textureLocation.m_135827_(), pathFixed
                  );
                  net.minecraft.client.resources.model.Material materialFixed = new net.minecraft.client.resources.model.Material(
                     material.m_119193_(), textureLocationFixed
                  );
                  Either<net.minecraft.client.resources.model.Material, String> valueFixed = Either.left(materialFixed);
                  entry.setValue(valueFixed);
               }
            }
         }
      }
   }

   public static net.minecraft.resources.ResourceLocation fixModelLocation(net.minecraft.resources.ResourceLocation loc, String basePath) {
      if (loc != null && basePath != null) {
         if (!loc.m_135827_().equals("minecraft")) {
            return loc;
         } else {
            String path = loc.m_135815_();
            String pathFixed = fixResourcePath(path, basePath);
            if (pathFixed != path) {
               loc = new net.minecraft.resources.ResourceLocation(loc.m_135827_(), pathFixed);
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

   public BlockModel loadBlockModel(net.minecraft.resources.ResourceLocation locJson) {
      try {
         Resource res = Config.getResource(locJson);
         Reader reader = res.m_215508_();
         return BlockModel.m_111461_(reader);
      } catch (Exception var5) {
         Config.warn("Error loading model: " + locJson);
         Config.warn(var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      }
   }

   static record BakedCacheKey(net.minecraft.resources.ResourceLocation f_243934_, Transformation f_243798_, boolean f_243915_) {
   }

   class ModelBakerImpl implements net.minecraft.client.resources.model.ModelBaker {
      private final Function<net.minecraft.client.resources.model.Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> f_243920_;

      ModelBakerImpl(final net.minecraft.client.resources.model.ModelBakery.TextureGetter funcIn, final ModelResourceLocation locIn) {
         this.f_243920_ = materialIn -> funcIn.m_338804_(locIn, materialIn);
      }

      @Override
      public UnbakedModel m_245361_(net.minecraft.resources.ResourceLocation locIn) {
         return ModelBakery.this.m_119341_(locIn);
      }

      @Override
      public Function<net.minecraft.client.resources.model.Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> getModelTextureGetter() {
         return this.f_243920_;
      }

      @Override
      public net.minecraft.client.resources.model.BakedModel m_245240_(net.minecraft.resources.ResourceLocation locIn, ModelState stateIn) {
         return this.bake(locIn, stateIn, this.f_243920_);
      }

      public net.minecraft.client.resources.model.BakedModel bake(
         net.minecraft.resources.ResourceLocation locIn,
         ModelState stateIn,
         Function<net.minecraft.client.resources.model.Material, net.minecraft.client.renderer.texture.TextureAtlasSprite> sprites
      ) {
         net.minecraft.client.resources.model.ModelBakery.BakedCacheKey modelbakery$bakedcachekey = new net.minecraft.client.resources.model.ModelBakery.BakedCacheKey(
            locIn, stateIn.m_6189_(), stateIn.m_7538_()
         );
         net.minecraft.client.resources.model.BakedModel bakedmodel = (net.minecraft.client.resources.model.BakedModel)ModelBakery.this.f_119213_
            .get(modelbakery$bakedcachekey);
         if (bakedmodel != null) {
            return bakedmodel;
         } else {
            UnbakedModel unbakedmodel = this.m_245361_(locIn);
            net.minecraft.client.resources.model.BakedModel bakedmodel1 = this.m_339454_(unbakedmodel, stateIn);
            ModelBakery.this.f_119213_.put(modelbakery$bakedcachekey, bakedmodel1);
            return bakedmodel1;
         }
      }

      @Nullable
      net.minecraft.client.resources.model.BakedModel m_339454_(UnbakedModel modelIn, ModelState stateIn) {
         if (modelIn instanceof BlockModel blockmodel && blockmodel.m_111490_() == net.minecraft.client.resources.model.ModelBakery.f_119232_) {
            return net.minecraft.client.resources.model.ModelBakery.f_119241_
               .m_111670_(this.f_243920_, blockmodel)
               .m_111449_(this, blockmodel, this.f_243920_, stateIn, false);
         }

         return modelIn.m_7611_(this, this.f_243920_, stateIn);
      }
   }

   @FunctionalInterface
   public interface TextureGetter {
      net.minecraft.client.renderer.texture.TextureAtlasSprite m_338804_(ModelResourceLocation var1, net.minecraft.client.resources.model.Material var2);
   }
}
