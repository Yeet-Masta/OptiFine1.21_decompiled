package net.minecraft.src;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class C_4532_ {
   public static final C_4531_ f_119219_;
   public static final C_4531_ f_119220_;
   public static final C_4531_ f_119221_;
   public static final C_4531_ f_119222_;
   public static final C_4531_ f_119223_;
   public static final C_4531_ f_119224_;
   public static final C_4531_ f_119225_;
   public static final C_4531_ f_119226_;
   public static final int f_174875_ = 10;
   public static final List f_119227_;
   public static final List f_119228_;
   public static final List f_119229_;
   private static final Logger f_119235_;
   private static final String f_174878_ = "builtin/";
   private static final String f_174879_ = "builtin/generated";
   private static final String f_174880_ = "builtin/entity";
   private static final String f_174881_ = "missing";
   public static final C_5265_ f_119230_;
   public static final C_4536_ f_336634_;
   public static final C_243621_ f_244378_;
   @VisibleForTesting
   public static final String f_119231_;
   private static final Map f_119237_;
   public static final C_4205_ f_119232_;
   public static final C_4205_ f_119233_;
   static final C_4213_ f_119241_;
   private final Map f_244132_;
   private final Set f_119210_ = new HashSet();
   private final Map f_119212_ = new HashMap();
   final Map f_119213_ = new HashMap();
   private final Map f_119214_ = new HashMap();
   private final Map f_119215_ = new HashMap();
   private final C_4542_ f_336931_;
   private final Object2IntMap f_119218_;
   public Map mapUnbakedModels;

   public C_4532_(C_3423_ blockColorsIn, C_442_ profilerIn, Map modelResourcesIn, Map blockStateResourcesIn) {
      this.f_244132_ = new HashMap(modelResourcesIn);
      profilerIn.m_6180_("missing_model");

      try {
         this.f_336931_ = this.m_119364_(f_119230_);
         this.m_338699_(f_336634_, this.f_336931_);
      } catch (IOException var9) {
         f_119235_.error("Error loading missing model, should never happen :(", var9);
         throw new RuntimeException(var9);
      }

      C_336451_ blockstatemodelloader = new C_336451_(blockStateResourcesIn, profilerIn, this.f_336931_, blockColorsIn, this::m_340411_);
      blockstatemodelloader.m_338905_();
      this.f_119218_ = blockstatemodelloader.m_338779_();
      profilerIn.m_6182_("items");
      Iterator var6 = C_256712_.f_257033_.f().iterator();

      while(var6.hasNext()) {
         C_5265_ resourcelocation = (C_5265_)var6.next();
         this.m_339007_(resourcelocation);
      }

      profilerIn.m_6182_("special");
      this.m_338793_(C_4354_.f_244055_);
      this.m_338793_(C_4354_.f_243706_);
      Set additionalModels = Sets.newHashSet();
      Reflector.ForgeHooksClient_onRegisterAdditionalModels.call((Object)additionalModels);
      Iterator var11 = additionalModels.iterator();

      while(var11.hasNext()) {
         C_4536_ rl = (C_4536_)var11.next();
         this.m_338699_(rl, this.m_119341_(rl.f_336625_()));
      }

      this.mapUnbakedModels = this.f_119212_;
      TextureUtils.registerCustomModels(this);
      this.f_119214_.values().forEach((modelIn) -> {
         modelIn.m_5500_(this::m_119341_);
      });
      profilerIn.m_7238_();
   }

   public void m_245909_(C_336541_ funcIn) {
      this.f_119214_.forEach((locIn, modelIn) -> {
         C_4528_ bakedmodel = null;

         try {
            bakedmodel = (new C_243522_(funcIn, locIn)).m_339454_(modelIn, C_4529_.X0_Y0);
         } catch (Exception var6) {
            f_119235_.warn("Unable to bake model: '{}': {}", locIn, var6);
         }

         if (bakedmodel != null) {
            this.f_119215_.put(locIn, bakedmodel);
         }

      });
   }

   public C_4542_ m_119341_(C_5265_ modelLocation) {
      if (this.f_119212_.containsKey(modelLocation)) {
         return (C_4542_)this.f_119212_.get(modelLocation);
      } else if (this.f_119210_.contains(modelLocation)) {
         throw new IllegalStateException("Circular reference while loading " + String.valueOf(modelLocation));
      } else {
         this.f_119210_.add(modelLocation);

         while(!this.f_119210_.isEmpty()) {
            C_5265_ resourcelocation = (C_5265_)this.f_119210_.iterator().next();

            try {
               if (!this.f_119212_.containsKey(resourcelocation)) {
                  C_4542_ unbakedmodel = this.m_119364_(resourcelocation);
                  this.f_119212_.put(resourcelocation, unbakedmodel);
                  this.f_119210_.addAll(unbakedmodel.m_7970_());
               }
            } catch (Exception var7) {
               f_119235_.warn("Unable to load model: '{}' referenced from: {}", resourcelocation, modelLocation);
               Logger var10000 = f_119235_;
               String var10001 = var7.getClass().getName();
               var10000.warn(var10001 + ": " + var7.getMessage());
               this.f_119212_.put(resourcelocation, this.f_336931_);
            } finally {
               this.f_119210_.remove(resourcelocation);
            }
         }

         return (C_4542_)this.f_119212_.getOrDefault(modelLocation, this.f_336931_);
      }
   }

   public void m_339007_(C_5265_ locationIn) {
      C_4536_ modelresourcelocation = C_4536_.m_340229_(locationIn);
      C_5265_ resourcelocation = locationIn.m_246208_("item/");
      String path = locationIn.m_135815_();
      if (path.startsWith("optifine/") || path.startsWith("item/")) {
         resourcelocation = locationIn.m_246208_("");
      }

      C_4542_ unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(modelresourcelocation, unbakedmodel);
   }

   private void m_338793_(C_4536_ locationIn) {
      C_5265_ resourcelocation = locationIn.f_336625_().m_246208_("item/");
      C_4542_ unbakedmodel = this.m_119341_(resourcelocation);
      this.m_340411_(locationIn, unbakedmodel);
   }

   private void m_340411_(C_4536_ locationIn, C_4542_ modelIn) {
      Iterator var3 = modelIn.m_7970_().iterator();

      while(var3.hasNext()) {
         C_5265_ resourcelocation = (C_5265_)var3.next();
         this.m_119341_(resourcelocation);
      }

      this.m_338699_(locationIn, modelIn);
   }

   private void m_338699_(C_4536_ locationIn, C_4542_ modelIn) {
      this.f_119214_.put(locationIn, modelIn);
   }

   private C_4205_ m_119364_(C_5265_ location) throws IOException {
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
            C_4205_ blockmodel1 = C_4205_.m_111461_(reader);
            blockmodel1.f_111416_ = location.toString();
            return blockmodel1;
         }
      } else {
         C_5265_ resourcelocation = this.getModelLocation(location);
         C_4205_ blockmodel = (C_4205_)this.f_244132_.get(resourcelocation);
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

   public Map m_119251_() {
      return this.f_119215_;
   }

   public Object2IntMap m_119355_() {
      return this.f_119218_;
   }

   private C_5265_ getModelLocation(C_5265_ location) {
      String path = location.m_135815_();
      if (path.startsWith("optifine/")) {
         if (!path.endsWith(".json")) {
            location = new C_5265_(location.m_135827_(), path + ".json");
         }

         return location;
      } else {
         return f_244378_.m_245698_(location);
      }
   }

   public static void fixModelLocations(C_4205_ modelBlock, String basePath) {
      C_5265_ parentLocFixed = fixModelLocation(C_4211_.getParentLocation(modelBlock), basePath);
      if (parentLocFixed != C_4211_.getParentLocation(modelBlock)) {
         C_4211_.setParentLocation(modelBlock, parentLocFixed);
      }

      if (C_4211_.getTextures(modelBlock) != null) {
         Iterator it = C_4211_.getTextures(modelBlock).entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            Either value = (Either)entry.getValue();
            Optional optionalMaterial = value.left();
            if (optionalMaterial.isPresent()) {
               C_4531_ material = (C_4531_)optionalMaterial.get();
               C_5265_ textureLocation = material.m_119203_();
               String path = textureLocation.m_135815_();
               String pathFixed = fixResourcePath(path, basePath);
               if (!pathFixed.equals(path)) {
                  C_5265_ textureLocationFixed = new C_5265_(textureLocation.m_135827_(), pathFixed);
                  C_4531_ materialFixed = new C_4531_(material.m_119193_(), textureLocationFixed);
                  Either valueFixed = Either.left(materialFixed);
                  entry.setValue(valueFixed);
               }
            }
         }
      }

   }

   public static C_5265_ fixModelLocation(C_5265_ loc, String basePath) {
      if (loc != null && basePath != null) {
         if (!loc.m_135827_().equals("minecraft")) {
            return loc;
         } else {
            String path = loc.m_135815_();
            String pathFixed = fixResourcePath(path, basePath);
            if (pathFixed != path) {
               loc = new C_5265_(loc.m_135827_(), pathFixed);
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
      path = StrUtils.removeSuffix(path, ".png");
      return path;
   }

   public C_4205_ loadBlockModel(C_5265_ locJson) {
      try {
         C_76_ res = Config.getResource(locJson);
         Reader reader = res.m_215508_();
         C_4205_ bm = C_4205_.m_111461_(reader);
         return bm;
      } catch (Exception var5) {
         Config.warn("Error loading model: " + String.valueOf(locJson));
         String var10000 = var5.getClass().getName();
         Config.warn(var10000 + ": " + var5.getMessage());
         return null;
      }
   }

   static {
      f_119219_ = new C_4531_(C_4484_.f_118259_, C_5265_.m_340282_("block/fire_0"));
      f_119220_ = new C_4531_(C_4484_.f_118259_, C_5265_.m_340282_("block/fire_1"));
      f_119221_ = new C_4531_(C_4484_.f_118259_, C_5265_.m_340282_("block/lava_flow"));
      f_119222_ = new C_4531_(C_4484_.f_118259_, C_5265_.m_340282_("block/water_flow"));
      f_119223_ = new C_4531_(C_4484_.f_118259_, C_5265_.m_340282_("block/water_overlay"));
      f_119224_ = new C_4531_(C_4177_.f_110737_, C_5265_.m_340282_("entity/banner_base"));
      f_119225_ = new C_4531_(C_4177_.f_110738_, C_5265_.m_340282_("entity/shield_base"));
      f_119226_ = new C_4531_(C_4177_.f_110738_, C_5265_.m_340282_("entity/shield_base_nopattern"));
      f_119227_ = (List)IntStream.range(0, 10).mapToObj((indexIn) -> {
         return C_5265_.m_340282_("block/destroy_stage_" + indexIn);
      }).collect(Collectors.toList());
      f_119228_ = (List)f_119227_.stream().map((locationIn) -> {
         return locationIn.m_247266_((pathIn) -> {
            return "textures/" + pathIn + ".png";
         });
      }).collect(Collectors.toList());
      f_119229_ = (List)f_119228_.stream().map(C_4168_::m_110494_).collect(Collectors.toList());
      f_119235_ = LogUtils.getLogger();
      f_119230_ = C_5265_.m_340282_("builtin/missing");
      f_336634_ = new C_4536_(f_119230_, "missing");
      f_244378_ = C_243621_.m_246568_("models");
      f_119231_ = ("{    'textures': {       'particle': '" + C_4473_.m_118071_().m_135815_() + "',       'missingno': '" + C_4473_.m_118071_().m_135815_() + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}").replace('\'', '"');
      f_119237_ = Map.of("missing", f_119231_);
      f_119232_ = (C_4205_)C_5322_.m_137469_(C_4205_.m_111463_("{\"gui_light\": \"front\"}"), (modelIn) -> {
         modelIn.f_111416_ = "generation marker";
      });
      f_119233_ = (C_4205_)C_5322_.m_137469_(C_4205_.m_111463_("{\"gui_light\": \"side\"}"), (modelIn) -> {
         modelIn.f_111416_ = "block entity marker";
      });
      f_119241_ = new C_4213_();
   }

   @FunctionalInterface
   public interface C_336541_ {
      C_4486_ m_338804_(C_4536_ var1, C_4531_ var2);
   }

   class C_243522_ implements C_243643_ {
      private final Function f_243920_;

      C_243522_(final C_336541_ funcIn, final C_4536_ locIn) {
         this.f_243920_ = (materialIn) -> {
            return funcIn.m_338804_(locIn, materialIn);
         };
      }

      public C_4542_ m_245361_(C_5265_ locIn) {
         return C_4532_.this.m_119341_(locIn);
      }

      public Function getModelTextureGetter() {
         return this.f_243920_;
      }

      public C_4528_ m_245240_(C_5265_ locIn, C_4537_ stateIn) {
         return this.bake(locIn, stateIn, this.f_243920_);
      }

      public C_4528_ bake(C_5265_ locIn, C_4537_ stateIn, Function sprites) {
         C_243580_ modelbakery$bakedcachekey = new C_243580_(locIn, stateIn.m_6189_(), stateIn.m_7538_());
         C_4528_ bakedmodel = (C_4528_)C_4532_.this.f_119213_.get(modelbakery$bakedcachekey);
         if (bakedmodel != null) {
            return bakedmodel;
         } else {
            C_4542_ unbakedmodel = this.m_245361_(locIn);
            C_4528_ bakedmodel1 = this.m_339454_(unbakedmodel, stateIn);
            C_4532_.this.f_119213_.put(modelbakery$bakedcachekey, bakedmodel1);
            return bakedmodel1;
         }
      }

      @Nullable
      C_4528_ m_339454_(C_4542_ modelIn, C_4537_ stateIn) {
         if (modelIn instanceof C_4205_ blockmodel) {
            if (blockmodel.m_111490_() == C_4532_.f_119232_) {
               return C_4532_.f_119241_.m_111670_(this.f_243920_, blockmodel).m_111449_(this, blockmodel, this.f_243920_, stateIn, false);
            }
         }

         return modelIn.m_7611_(this, this.f_243920_, stateIn);
      }
   }

   static record C_243580_(C_5265_ f_243934_, C_4649_ f_243798_, boolean f_243915_) {
      C_243580_(C_5265_ id, C_4649_ transformation, boolean isUvLocked) {
         this.f_243934_ = id;
         this.f_243798_ = transformation;
         this.f_243915_ = isUvLocked;
      }

      public C_5265_ f_243934_() {
         return this.f_243934_;
      }

      public C_4649_ f_243798_() {
         return this.f_243798_;
      }

      public boolean f_243915_() {
         return this.f_243915_;
      }
   }
}
