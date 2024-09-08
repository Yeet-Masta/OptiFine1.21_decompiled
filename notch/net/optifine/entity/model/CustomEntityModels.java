package net.optifine.entity.model;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_141651_;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3583_;
import net.minecraft.src.C_3659_;
import net.minecraft.src.C_3804_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3844_;
import net.minecraft.src.C_3864_;
import net.minecraft.src.C_3875_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4109_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4249_;
import net.minecraft.src.C_4255_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_4357_;
import net.minecraft.src.C_4373_;
import net.minecraft.src.C_4397_;
import net.minecraft.src.C_4445_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_1897_.C_1898_;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.Log;
import net.optifine.RandomEntities;
import net.optifine.RandomEntityContext;
import net.optifine.RandomEntityProperties;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;
import net.optifine.util.StrUtils;

public class CustomEntityModels {
   private static boolean active = false;
   private static Map<C_513_, RandomEntityProperties<IEntityRenderer>> mapEntityProperties = new HashMap();
   private static Map<C_1992_, RandomEntityProperties<IEntityRenderer>> mapBlockEntityProperties = new HashMap();
   private static int matchingRuleIndex;
   private static Map<C_513_, C_4331_> originalEntityRenderMap = null;
   private static Map<C_1992_, C_4244_> originalTileEntityRenderMap = null;
   private static Map<C_1898_, C_141651_> originalSkullModelMap = null;
   private static List<C_1992_> customTileEntityTypes = new ArrayList();
   private static C_3804_ customBookModel;
   private static boolean debugModels = Boolean.getBoolean("cem.debug.models");
   public static final String PREFIX_OPTIFINE_CEM = "optifine/cem/";
   public static final String SUFFIX_JEM = ".jem";
   public static final String SUFFIX_PROPERTIES = ".properties";

   public static void update() {
      Map<C_513_, C_4331_> entityRenderMap = getEntityRenderMap();
      Map<C_1992_, C_4244_> tileEntityRenderMap = getTileEntityRenderMap();
      Map<C_1898_, C_141651_> skullModelMap = getSkullModelMap();
      if (entityRenderMap == null) {
         Config.warn("Entity render map not found, custom entity models are DISABLED.");
      } else if (tileEntityRenderMap == null) {
         Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
      } else {
         active = false;
         entityRenderMap.clear();
         tileEntityRenderMap.clear();
         skullModelMap.clear();
         customTileEntityTypes.clear();
         entityRenderMap.putAll(originalEntityRenderMap);
         tileEntityRenderMap.putAll(originalTileEntityRenderMap);
         skullModelMap.putAll(originalSkullModelMap);
         C_4109_ blockEntityRenderer = C_3391_.m_91087_().m_91291_().getBlockEntityRenderer();
         blockEntityRenderer.f_108824_ = new C_3875_(ModelAdapter.bakeModelLayer(C_141656_.f_171255_));
         blockEntityRenderer.f_108823_ = new C_3864_(ModelAdapter.bakeModelLayer(C_141656_.f_171179_));
         C_4445_.customParrotModel = null;
         customBookModel = null;
         C_4244_.CACHED_TYPES.clear();
         if (C_3391_.m_91087_().f_91073_ != null) {
            for (C_507_ entity : C_3391_.m_91087_().f_91073_.m_104735_()) {
               Map modelVariables = entity.m_20088_().modelVariables;
               if (modelVariables != null) {
                  modelVariables.clear();
               }
            }
         }

         mapEntityProperties.clear();
         mapBlockEntityProperties.clear();
         if (Config.isCustomEntityModels()) {
            RandomEntityContext.Models context = new RandomEntityContext.Models();
            RendererCache rendererCache = context.getRendererCache();
            C_5265_[] locs = getModelLocations();

            for (int i = 0; i < locs.length; i++) {
               C_5265_ loc = locs[i];
               Config.dbg("CustomEntityModel: " + loc.m_135815_());
               IEntityRenderer rc = parseEntityRender(loc, rendererCache, 0);
               if (rc != null) {
                  Either<C_513_, C_1992_> type = rc.getType();
                  if (rc instanceof C_4331_) {
                     entityRenderMap.put((C_513_)type.getLeft().get(), (C_4331_)rc);
                     rendererCache.put((C_513_)type.getLeft().get(), 0, (C_4331_)rc);
                     if (rc instanceof C_4397_) {
                        C_4397_ tr = (C_4397_)rc;
                        C_3875_ tm = (C_3875_)Reflector.getFieldValue(tr, Reflector.RenderTrident_modelTrident);
                        if (tm != null) {
                           blockEntityRenderer.f_108824_ = tm;
                        }
                     }

                     if (rc instanceof C_4373_) {
                        C_4373_ pr = (C_4373_)rc;
                        C_3844_ pm = (C_3844_)pr.a();
                        if (pm != null) {
                           C_4445_.customParrotModel = pm;
                        }
                     }
                  } else if (rc instanceof C_4244_) {
                     tileEntityRenderMap.put((C_1992_)type.getRight().get(), (C_4244_)rc);
                     rendererCache.put((C_1992_)type.getRight().get(), 0, (C_4244_)rc);
                     if (rc instanceof C_4249_ etr) {
                        C_3804_ bm = (C_3804_)Reflector.getFieldValue(etr, Reflector.TileEntityEnchantmentTableRenderer_modelBook);
                        setEnchantmentScreenBookModel(bm);
                     }

                     customTileEntityTypes.add((C_1992_)type.getRight().get());
                  } else {
                     if (!(rc instanceof VirtualEntityRenderer)) {
                        Config.warn("Unknown renderer type: " + rc.getClass().getName());
                        continue;
                     }

                     VirtualEntityRenderer ver = (VirtualEntityRenderer)rc;
                     if (ver.getModel() instanceof C_3864_) {
                        C_3864_ sm = (C_3864_)ver.getModel();
                        blockEntityRenderer.f_108823_ = sm;
                     }
                  }

                  active = true;
               }
            }

            updateRandomProperties(context);
         }
      }
   }

   private static void updateRandomProperties(RandomEntityContext.Models context) {
      String[] var10000 = new String[]{"optifine/cem/"};
      var10000 = new String[]{".jem", ".properties"};
      String[] names = CustomModelRegistry.getModelNames();

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
         Either<C_513_, C_1992_> type = modelAdapter.getType();
         RandomEntityProperties props = makeProperties(name, context);
         if (props == null) {
            props = makeProperties(name + "/" + name, context);
         }

         if (props != null) {
            if (type != null && type.getLeft().isPresent()) {
               mapEntityProperties.put((C_513_)type.getLeft().get(), props);
            }

            if (type != null && type.getRight().isPresent()) {
               mapBlockEntityProperties.put((C_1992_)type.getRight().get(), props);
            }
         }
      }
   }

   private static RandomEntityProperties makeProperties(String name, RandomEntityContext.Models context) {
      C_5265_ locJem = new C_5265_("optifine/cem/" + name + ".jem");
      C_5265_ locProps = new C_5265_("optifine/cem/" + name + ".properties");
      if (Config.hasResource(locProps)) {
         RandomEntityProperties props = RandomEntityProperties.parse(locProps, locJem, context);
         if (props != null) {
            return props;
         }
      }

      if (!Config.hasResource(locJem)) {
         return null;
      } else {
         int[] variants = RandomEntities.getLocationsVariants(locJem, false, context);
         if (variants == null) {
            return null;
         } else {
            RandomEntityProperties<IEntityRenderer> props = new RandomEntityProperties(locJem.m_135815_(), locJem, variants, context);
            return !props.isValid(locJem.m_135815_()) ? null : props;
         }
      }
   }

   private static void setEnchantmentScreenBookModel(C_3804_ bookModel) {
      customBookModel = bookModel;
   }

   private static Map<C_513_, C_4331_> getEntityRenderMap() {
      C_4330_ rm = C_3391_.m_91087_().m_91290_();
      Map<C_513_, C_4331_> entityRenderMap = rm.getEntityRenderMap();
      if (entityRenderMap == null) {
         return null;
      } else {
         if (originalEntityRenderMap == null) {
            originalEntityRenderMap = new HashMap(entityRenderMap);
         }

         return entityRenderMap;
      }
   }

   private static Map<C_1992_, C_4244_> getTileEntityRenderMap() {
      C_4243_ blockEntityRenderDispatcher = C_3391_.m_91087_().m_167982_();
      Map<C_1992_, C_4244_> tileEntityRenderMap = blockEntityRenderDispatcher.getBlockEntityRenderMap();
      if (originalTileEntityRenderMap == null) {
         originalTileEntityRenderMap = new HashMap(tileEntityRenderMap);
      }

      return tileEntityRenderMap;
   }

   private static Map<C_1898_, C_141651_> getSkullModelMap() {
      Map<C_1898_, C_141651_> skullModelMap = C_4255_.models;
      if (skullModelMap == null) {
         Config.warn("Field not found: SkullBlockRenderer.MODELS");
         skullModelMap = new HashMap();
      }

      if (originalSkullModelMap == null) {
         originalSkullModelMap = new HashMap(skullModelMap);
      }

      return skullModelMap;
   }

   private static C_5265_[] getModelLocations() {
      String prefix = "optifine/cem/";
      String suffix = ".jem";
      List<C_5265_> resourceLocations = new ArrayList();
      String[] names = CustomModelRegistry.getModelNames();

      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         String path = prefix + name + suffix;
         C_5265_ loc = new C_5265_(path);
         if (Config.hasResource(loc) || debugModels) {
            resourceLocations.add(loc);
         }
      }

      return (C_5265_[])resourceLocations.toArray(new C_5265_[resourceLocations.size()]);
   }

   public static IEntityRenderer parseEntityRender(C_5265_ location, RendererCache rendererCache, int index) {
      try {
         if (debugModels && index == 0) {
            return makeDebugEntityRenderer(location, rendererCache, index);
         } else {
            JsonObject jo = CustomEntityModelParser.loadJson(location);
            return parseEntityRender(jo, location.m_135815_(), rendererCache, index);
         }
      } catch (IOException var5) {
         Config.error(var5.getClass().getName() + ": " + var5.getMessage());
         return null;
      } catch (JsonParseException var6) {
         Config.error(var6.getClass().getName() + ": " + var6.getMessage());
         return null;
      } catch (Exception var7) {
         Log.warn("Error loading CEM: " + location, var7);
         return null;
      }
   }

   private static IEntityRenderer makeDebugEntityRenderer(C_5265_ loc, RendererCache rendererCache, int index) {
      String path = loc.m_135815_();
      String nameJem = StrUtils.removePrefix(path, "optifine/cem/");
      String name = StrUtils.removeSuffix(nameJem, ".jem");
      ModelAdapter ma = CustomModelRegistry.getModelAdapter(name);
      C_3840_ model = ma.makeModel();
      C_1353_[] colors = C_1353_.values();
      int offset = Math.abs(loc.hashCode()) % 256;
      String[] partNames = ma.getModelRendererNames();

      for (int i = 0; i < partNames.length; i++) {
         String partName = partNames[i];
         C_3889_ part = ma.getModelRenderer(model, partName);
         if (part != null) {
            C_1353_ col = colors[(i + offset) % colors.length];
            C_5265_ locTexture = new C_5265_("textures/block/" + col.m_7912_() + "_stained_glass.png");
            part.setTextureLocation(locTexture);
            Config.dbg("  " + partName + ": " + col.m_7912_());
         }
      }

      IEntityRenderer er = ma.makeEntityRender(model, ma.getShadowSize(), rendererCache, index);
      if (er == null) {
         return null;
      } else {
         er.setType(ma.getType());
         return er;
      }
   }

   private static IEntityRenderer parseEntityRender(JsonObject obj, String path, RendererCache rendererCache, int index) {
      CustomEntityRenderer cer = CustomEntityModelParser.parseEntityRender(obj, path);
      String name = cer.getName();
      name = StrUtils.trimTrailing(name, "0123456789");
      ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
      checkNull(modelAdapter, "Entity not found: " + name);
      Either<C_513_, C_1992_> type = modelAdapter.getType();
      IEntityRenderer render = makeEntityRender(modelAdapter, cer, rendererCache, index);
      if (render == null) {
         return null;
      } else {
         render.setType(type);
         return render;
      }
   }

   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer, RendererCache rendererCache, int index) {
      C_5265_ textureLocation = cer.getTextureLocation();
      CustomModelRenderer[] modelRenderers = cer.getCustomModelRenderers();
      float shadowSize = cer.getShadowSize();
      if (shadowSize < 0.0F) {
         shadowSize = modelAdapter.getShadowSize();
      }

      C_3840_ model = modelAdapter.makeModel();
      if (model == null) {
         return null;
      } else {
         ModelResolver mr = new ModelResolver(modelAdapter, model, modelRenderers);
         if (!modifyModel(modelAdapter, model, modelRenderers, mr)) {
            return null;
         } else {
            IEntityRenderer r = modelAdapter.makeEntityRender(model, shadowSize, rendererCache, index);
            if (r == null) {
               throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
            } else {
               if (textureLocation != null) {
                  setTextureLocation(modelAdapter, model, r, textureLocation);
               }

               return r;
            }
         }
      }
   }

   private static void setTextureLocation(ModelAdapter modelAdapter, C_3840_ model, IEntityRenderer er, C_5265_ textureLocation) {
      if (!modelAdapter.setTextureLocation(er, textureLocation)) {
         if (er instanceof C_4357_) {
            er.setLocationTextureCustom(textureLocation);
         } else {
            setTextureTopModelRenderers(modelAdapter, model, textureLocation);
         }
      }
   }

   public static void setTextureTopModelRenderers(ModelAdapter modelAdapter, C_3840_ model, C_5265_ textureLocation) {
      String[] parts = modelAdapter.getModelRendererNames();

      for (int i = 0; i < parts.length; i++) {
         String part = parts[i];
         C_3889_ modelRenderer = modelAdapter.getModelRenderer(model, part);
         if (modelRenderer != null && modelRenderer.getTextureLocation() == null) {
            modelRenderer.setTextureLocation(textureLocation);
         }
      }
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, C_3840_ model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
      List<ModelVariableUpdater> listVariableUpdaters = new ArrayList();

      for (int i = 0; i < modelRenderers.length; i++) {
         CustomModelRenderer cmr = modelRenderers[i];
         if (!modifyModel(modelAdapter, model, cmr, mr)) {
            return false;
         }

         if (cmr.getModelRenderer().getModelUpdater() != null) {
            listVariableUpdaters.addAll(Arrays.asList(cmr.getModelRenderer().getModelUpdater().getModelVariableUpdaters()));
         }
      }

      ModelVariableUpdater[] mvus = (ModelVariableUpdater[])listVariableUpdaters.toArray(new ModelVariableUpdater[listVariableUpdaters.size()]);
      ModelUpdater globvalUpdater = new ModelUpdater(mvus);

      for (int i = 0; i < modelRenderers.length; i++) {
         CustomModelRenderer cmrx = modelRenderers[i];
         if (cmrx.getModelRenderer().getModelUpdater() != null) {
            cmrx.getModelRenderer().setModelUpdater(globvalUpdater);
         }
      }

      for (int ix = 0; ix < mvus.length; ix++) {
         ModelVariableUpdater mvu = mvus[ix];
         if (mvu.getModelVariable() instanceof IModelRendererVariable mrv) {
            mrv.getModelRenderer().setModelUpdater(globvalUpdater);
         }
      }

      return true;
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, C_3840_ model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
      String modelPart = customModelRenderer.getModelPart();
      C_3889_ parent = modelAdapter.getModelRenderer(model, modelPart);
      if (parent == null) {
         Config.warn("Model part not found: " + modelPart + ", model: " + model);
         return false;
      } else {
         if (!customModelRenderer.isAttach()) {
            if (parent.f_104212_ != null) {
               parent.f_104212_.clear();
            }

            if (parent.spriteList != null) {
               parent.spriteList.clear();
            }

            if (parent.f_104213_ != null) {
               C_3889_[] mrs = modelAdapter.getModelRenderers(model);
               Set<C_3889_> setMrs = Collections.newSetFromMap(new IdentityHashMap());
               setMrs.addAll(Arrays.asList(mrs));

               for (String key : new HashSet(parent.f_104213_.keySet())) {
                  C_3889_ mr = (C_3889_)parent.f_104213_.get(key);
                  if (!setMrs.contains(mr)) {
                     parent.f_104213_.remove(key);
                  }
               }
            }
         }

         String childName = parent.getUniqueChildModelName("CEM-" + modelPart);
         parent.addChildModel(childName, customModelRenderer.getModelRenderer());
         ModelUpdater mu = customModelRenderer.getModelUpdater();
         if (mu != null) {
            modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
            modelResolver.setPartModelRenderer(parent);
            if (!mu.initialize(modelResolver)) {
               return false;
            }

            customModelRenderer.getModelRenderer().setModelUpdater(mu);
         }

         return true;
      }
   }

   private static void checkNull(Object obj, String msg) {
      if (obj == null) {
         throw new JsonParseException(msg);
      }
   }

   public static boolean isActive() {
      return active;
   }

   public static boolean isCustomModel(C_2064_ blockStateIn) {
      for (int i = 0; i < customTileEntityTypes.size(); i++) {
         C_1992_ type = (C_1992_)customTileEntityTypes.get(i);
         if (type.m_155262_(blockStateIn)) {
            return true;
         }
      }

      return false;
   }

   public static void onRenderScreen(C_3583_ screen) {
      if (customBookModel != null && screen instanceof C_3659_ es) {
         Reflector.GuiEnchantment_bookModel.setValue(es, customBookModel);
      }
   }

   public static C_4331_ getEntityRenderer(C_507_ entityIn, C_4331_ renderer) {
      if (mapEntityProperties.isEmpty()) {
         return renderer;
      } else {
         IRandomEntity randomEntity = RandomEntities.getRandomEntity(entityIn);
         if (randomEntity == null) {
            return renderer;
         } else {
            RandomEntityProperties<IEntityRenderer> props = (RandomEntityProperties<IEntityRenderer>)mapEntityProperties.get(entityIn.m_6095_());
            if (props == null) {
               return renderer;
            } else {
               IEntityRenderer ier = props.getResource(randomEntity, renderer);
               if (!(ier instanceof C_4331_)) {
                  return null;
               } else {
                  matchingRuleIndex = props.getMatchingRuleIndex();
                  return (C_4331_)ier;
               }
            }
         }
      }
   }

   public static C_4244_ getBlockEntityRenderer(C_1991_ entityIn, C_4244_ renderer) {
      if (mapBlockEntityProperties.isEmpty()) {
         return renderer;
      } else {
         IRandomEntity randomEntity = RandomEntities.getRandomBlockEntity(entityIn);
         if (randomEntity == null) {
            return renderer;
         } else {
            RandomEntityProperties<IEntityRenderer> props = (RandomEntityProperties<IEntityRenderer>)mapBlockEntityProperties.get(entityIn.m_58903_());
            if (props == null) {
               return renderer;
            } else {
               IEntityRenderer ier = props.getResource(randomEntity, renderer);
               if (!(ier instanceof C_4244_)) {
                  return null;
               } else {
                  matchingRuleIndex = props.getMatchingRuleIndex();
                  return (C_4244_)ier;
               }
            }
         }
      }
   }

   public static int getMatchingRuleIndex() {
      return matchingRuleIndex;
   }
}
