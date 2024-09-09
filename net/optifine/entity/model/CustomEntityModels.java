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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.layers.ParrotOnShoulderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.Log;
import net.optifine.RandomEntities;
import net.optifine.RandomEntityContext;
import net.optifine.RandomEntityProperties;
import net.optifine.entity.model.anim.IModelRendererVariable;
import net.optifine.entity.model.anim.IModelVariable;
import net.optifine.entity.model.anim.ModelResolver;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;
import net.optifine.util.StrUtils;

public class CustomEntityModels {
   private static boolean active = false;
   private static Map mapEntityProperties = new HashMap();
   private static Map mapBlockEntityProperties = new HashMap();
   private static int matchingRuleIndex;
   private static Map originalEntityRenderMap = null;
   private static Map originalTileEntityRenderMap = null;
   private static Map originalSkullModelMap = null;
   private static List customTileEntityTypes = new ArrayList();
   private static BookModel customBookModel;
   private static boolean debugModels = Boolean.getBoolean("cem.debug.models");
   public static final String PREFIX_OPTIFINE_CEM = "optifine/cem/";
   public static final String SUFFIX_JEM = ".jem";
   public static final String SUFFIX_PROPERTIES = ".properties";

   public static void update() {
      Map entityRenderMap = getEntityRenderMap();
      Map tileEntityRenderMap = getTileEntityRenderMap();
      Map skullModelMap = getSkullModelMap();
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
         BlockEntityWithoutLevelRenderer blockEntityRenderer = Minecraft.m_91087_().m_91291_().getBlockEntityRenderer();
         blockEntityRenderer.f_108824_ = new TridentModel(ModelAdapter.bakeModelLayer(ModelLayers.f_171255_));
         blockEntityRenderer.f_108823_ = new ShieldModel(ModelAdapter.bakeModelLayer(ModelLayers.f_171179_));
         ParrotOnShoulderLayer.customParrotModel = null;
         customBookModel = null;
         BlockEntityRenderer.CACHED_TYPES.clear();
         if (Minecraft.m_91087_().f_91073_ != null) {
            Iterable entities = Minecraft.m_91087_().f_91073_.m_104735_();
            Iterator var5 = entities.iterator();

            while(var5.hasNext()) {
               Entity entity = (Entity)var5.next();
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
            ResourceLocation[] locs = getModelLocations();

            for(int i = 0; i < locs.length; ++i) {
               ResourceLocation loc = locs[i];
               Config.dbg("CustomEntityModel: " + loc.m_135815_());
               IEntityRenderer rc = parseEntityRender(loc, rendererCache, 0);
               if (rc != null) {
                  Either type = rc.getType();
                  if (rc instanceof EntityRenderer) {
                     entityRenderMap.put((EntityType)type.getLeft().get(), (EntityRenderer)rc);
                     rendererCache.put((EntityType)((EntityType)type.getLeft().get()), 0, (EntityRenderer)((EntityRenderer)rc));
                     if (rc instanceof ThrownTridentRenderer) {
                        ThrownTridentRenderer tr = (ThrownTridentRenderer)rc;
                        TridentModel tm = (TridentModel)Reflector.getFieldValue(tr, Reflector.RenderTrident_modelTrident);
                        if (tm != null) {
                           blockEntityRenderer.f_108824_ = tm;
                        }
                     }

                     if (rc instanceof ParrotRenderer) {
                        ParrotRenderer pr = (ParrotRenderer)rc;
                        ParrotModel pm = (ParrotModel)pr.m_7200_();
                        if (pm != null) {
                           ParrotOnShoulderLayer.customParrotModel = pm;
                        }
                     }
                  } else if (rc instanceof BlockEntityRenderer) {
                     tileEntityRenderMap.put((BlockEntityType)type.getRight().get(), (BlockEntityRenderer)rc);
                     rendererCache.put((BlockEntityType)((BlockEntityType)type.getRight().get()), 0, (BlockEntityRenderer)((BlockEntityRenderer)rc));
                     if (rc instanceof EnchantTableRenderer) {
                        EnchantTableRenderer etr = (EnchantTableRenderer)rc;
                        BookModel bm = (BookModel)Reflector.getFieldValue(etr, Reflector.TileEntityEnchantmentTableRenderer_modelBook);
                        setEnchantmentScreenBookModel(bm);
                     }

                     customTileEntityTypes.add((BlockEntityType)type.getRight().get());
                  } else {
                     if (!(rc instanceof VirtualEntityRenderer)) {
                        Config.warn("Unknown renderer type: " + rc.getClass().getName());
                        continue;
                     }

                     VirtualEntityRenderer ver = (VirtualEntityRenderer)rc;
                     if (ver.getModel() instanceof ShieldModel) {
                        ShieldModel sm = (ShieldModel)ver.getModel();
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

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         ModelAdapter modelAdapter = CustomModelRegistry.getModelAdapter(name);
         Either type = modelAdapter.getType();
         RandomEntityProperties props = makeProperties(name, context);
         if (props == null) {
            props = makeProperties(name + "/" + name, context);
         }

         if (props != null) {
            if (type != null && type.getLeft().isPresent()) {
               mapEntityProperties.put((EntityType)type.getLeft().get(), props);
            }

            if (type != null && type.getRight().isPresent()) {
               mapBlockEntityProperties.put((BlockEntityType)type.getRight().get(), props);
            }
         }
      }

   }

   private static RandomEntityProperties makeProperties(String name, RandomEntityContext.Models context) {
      ResourceLocation locJem = new ResourceLocation("optifine/cem/" + name + ".jem");
      ResourceLocation locProps = new ResourceLocation("optifine/cem/" + name + ".properties");
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
            RandomEntityProperties props = new RandomEntityProperties(locJem.m_135815_(), locJem, variants, context);
            return !props.isValid(locJem.m_135815_()) ? null : props;
         }
      }
   }

   private static void setEnchantmentScreenBookModel(BookModel bookModel) {
      customBookModel = bookModel;
   }

   private static Map getEntityRenderMap() {
      EntityRenderDispatcher rm = Minecraft.m_91087_().m_91290_();
      Map entityRenderMap = rm.getEntityRenderMap();
      if (entityRenderMap == null) {
         return null;
      } else {
         if (originalEntityRenderMap == null) {
            originalEntityRenderMap = new HashMap(entityRenderMap);
         }

         return entityRenderMap;
      }
   }

   private static Map getTileEntityRenderMap() {
      BlockEntityRenderDispatcher blockEntityRenderDispatcher = Minecraft.m_91087_().m_167982_();
      Map tileEntityRenderMap = blockEntityRenderDispatcher.getBlockEntityRenderMap();
      if (originalTileEntityRenderMap == null) {
         originalTileEntityRenderMap = new HashMap(tileEntityRenderMap);
      }

      return tileEntityRenderMap;
   }

   private static Map getSkullModelMap() {
      Map skullModelMap = SkullBlockRenderer.models;
      if (skullModelMap == null) {
         Config.warn("Field not found: SkullBlockRenderer.MODELS");
         skullModelMap = new HashMap();
      }

      if (originalSkullModelMap == null) {
         originalSkullModelMap = new HashMap((Map)skullModelMap);
      }

      return (Map)skullModelMap;
   }

   private static ResourceLocation[] getModelLocations() {
      String prefix = "optifine/cem/";
      String suffix = ".jem";
      List resourceLocations = new ArrayList();
      String[] names = CustomModelRegistry.getModelNames();

      for(int i = 0; i < names.length; ++i) {
         String name = names[i];
         String path = prefix + name + suffix;
         ResourceLocation loc = new ResourceLocation(path);
         if (Config.hasResource(loc) || debugModels) {
            resourceLocations.add(loc);
         }
      }

      ResourceLocation[] locs = (ResourceLocation[])resourceLocations.toArray(new ResourceLocation[resourceLocations.size()]);
      return locs;
   }

   public static IEntityRenderer parseEntityRender(ResourceLocation location, RendererCache rendererCache, int index) {
      String var10000;
      try {
         if (debugModels && index == 0) {
            return makeDebugEntityRenderer(location, rendererCache, index);
         } else {
            JsonObject jo = CustomEntityModelParser.loadJson(location);
            IEntityRenderer render = parseEntityRender(jo, location.m_135815_(), rendererCache, index);
            return render;
         }
      } catch (IOException var5) {
         var10000 = var5.getClass().getName();
         Config.error(var10000 + ": " + var5.getMessage());
         return null;
      } catch (JsonParseException var6) {
         var10000 = var6.getClass().getName();
         Config.error(var10000 + ": " + var6.getMessage());
         return null;
      } catch (Exception var7) {
         Log.warn("Error loading CEM: " + String.valueOf(location), var7);
         return null;
      }
   }

   private static IEntityRenderer makeDebugEntityRenderer(ResourceLocation loc, RendererCache rendererCache, int index) {
      String path = loc.m_135815_();
      String nameJem = StrUtils.removePrefix(path, "optifine/cem/");
      String name = StrUtils.removeSuffix(nameJem, ".jem");
      ModelAdapter ma = CustomModelRegistry.getModelAdapter(name);
      Model model = ma.makeModel();
      DyeColor[] colors = DyeColor.values();
      int offset = Math.abs(loc.hashCode()) % 256;
      String[] partNames = ma.getModelRendererNames();

      for(int i = 0; i < partNames.length; ++i) {
         String partName = partNames[i];
         ModelPart part = ma.getModelRenderer(model, partName);
         if (part != null) {
            DyeColor col = colors[(i + offset) % colors.length];
            ResourceLocation locTexture = new ResourceLocation("textures/block/" + col.m_7912_() + "_stained_glass.png");
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
      Either type = modelAdapter.getType();
      IEntityRenderer render = makeEntityRender(modelAdapter, cer, rendererCache, index);
      if (render == null) {
         return null;
      } else {
         render.setType(type);
         return render;
      }
   }

   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer, RendererCache rendererCache, int index) {
      ResourceLocation textureLocation = cer.getTextureLocation();
      CustomModelRenderer[] modelRenderers = cer.getCustomModelRenderers();
      float shadowSize = cer.getShadowSize();
      if (shadowSize < 0.0F) {
         shadowSize = modelAdapter.getShadowSize();
      }

      Model model = modelAdapter.makeModel();
      if (model == null) {
         return null;
      } else {
         ModelResolver mr = new ModelResolver(modelAdapter, model, modelRenderers);
         if (!modifyModel(modelAdapter, model, modelRenderers, mr)) {
            return null;
         } else {
            IEntityRenderer r = modelAdapter.makeEntityRender(model, shadowSize, rendererCache, index);
            if (r == null) {
               String var10002 = modelAdapter.getName();
               throw new JsonParseException("Entity renderer is null, model: " + var10002 + ", adapter: " + modelAdapter.getClass().getName());
            } else {
               if (textureLocation != null) {
                  setTextureLocation(modelAdapter, model, r, textureLocation);
               }

               return r;
            }
         }
      }
   }

   private static void setTextureLocation(ModelAdapter modelAdapter, Model model, IEntityRenderer er, ResourceLocation textureLocation) {
      if (!modelAdapter.setTextureLocation(er, textureLocation)) {
         if (er instanceof LivingEntityRenderer) {
            er.setLocationTextureCustom(textureLocation);
         } else {
            setTextureTopModelRenderers(modelAdapter, model, textureLocation);
         }
      }
   }

   public static void setTextureTopModelRenderers(ModelAdapter modelAdapter, Model model, ResourceLocation textureLocation) {
      String[] parts = modelAdapter.getModelRendererNames();

      for(int i = 0; i < parts.length; ++i) {
         String part = parts[i];
         ModelPart modelRenderer = modelAdapter.getModelRenderer(model, part);
         if (modelRenderer != null && modelRenderer.getTextureLocation() == null) {
            modelRenderer.setTextureLocation(textureLocation);
         }
      }

   }

   private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
      List listVariableUpdaters = new ArrayList();

      for(int i = 0; i < modelRenderers.length; ++i) {
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

      int i;
      for(i = 0; i < modelRenderers.length; ++i) {
         CustomModelRenderer cmr = modelRenderers[i];
         if (cmr.getModelRenderer().getModelUpdater() != null) {
            cmr.getModelRenderer().setModelUpdater(globvalUpdater);
         }
      }

      for(i = 0; i < mvus.length; ++i) {
         ModelVariableUpdater mvu = mvus[i];
         IModelVariable mv = mvu.getModelVariable();
         if (mv instanceof IModelRendererVariable mrv) {
            mrv.getModelRenderer().setModelUpdater(globvalUpdater);
         }
      }

      return true;
   }

   private static boolean modifyModel(ModelAdapter modelAdapter, Model model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
      String modelPart = customModelRenderer.getModelPart();
      ModelPart parent = modelAdapter.getModelRenderer(model, modelPart);
      if (parent == null) {
         Config.warn("Model part not found: " + modelPart + ", model: " + String.valueOf(model));
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
               ModelPart[] mrs = modelAdapter.getModelRenderers(model);
               Set setMrs = Collections.newSetFromMap(new IdentityHashMap());
               setMrs.addAll(Arrays.asList(mrs));
               Set childModelKeys = new HashSet(parent.f_104213_.keySet());
               Iterator var9 = childModelKeys.iterator();

               while(var9.hasNext()) {
                  String key = (String)var9.next();
                  ModelPart mr = (ModelPart)parent.f_104213_.get(key);
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

   public static boolean isCustomModel(BlockState blockStateIn) {
      for(int i = 0; i < customTileEntityTypes.size(); ++i) {
         BlockEntityType type = (BlockEntityType)customTileEntityTypes.get(i);
         if (type.m_155262_(blockStateIn)) {
            return true;
         }
      }

      return false;
   }

   public static void onRenderScreen(Screen screen) {
      if (customBookModel != null && screen instanceof EnchantmentScreen es) {
         Reflector.GuiEnchantment_bookModel.setValue(es, customBookModel);
      }

   }

   public static EntityRenderer getEntityRenderer(Entity entityIn, EntityRenderer renderer) {
      if (mapEntityProperties.isEmpty()) {
         return renderer;
      } else {
         IRandomEntity randomEntity = RandomEntities.getRandomEntity(entityIn);
         if (randomEntity == null) {
            return renderer;
         } else {
            RandomEntityProperties props = (RandomEntityProperties)mapEntityProperties.get(entityIn.m_6095_());
            if (props == null) {
               return renderer;
            } else {
               IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
               if (!(ier instanceof EntityRenderer)) {
                  return null;
               } else {
                  matchingRuleIndex = props.getMatchingRuleIndex();
                  EntityRenderer er = (EntityRenderer)ier;
                  return er;
               }
            }
         }
      }
   }

   public static BlockEntityRenderer getBlockEntityRenderer(BlockEntity entityIn, BlockEntityRenderer renderer) {
      if (mapBlockEntityProperties.isEmpty()) {
         return renderer;
      } else {
         IRandomEntity randomEntity = RandomEntities.getRandomBlockEntity(entityIn);
         if (randomEntity == null) {
            return renderer;
         } else {
            RandomEntityProperties props = (RandomEntityProperties)mapBlockEntityProperties.get(entityIn.m_58903_());
            if (props == null) {
               return renderer;
            } else {
               IEntityRenderer ier = (IEntityRenderer)props.getResource(randomEntity, renderer);
               if (!(ier instanceof BlockEntityRenderer)) {
                  return null;
               } else {
                  matchingRuleIndex = props.getMatchingRuleIndex();
                  BlockEntityRenderer ber = (BlockEntityRenderer)ier;
                  return ber;
               }
            }
         }
      }
   }

   public static int getMatchingRuleIndex() {
      return matchingRuleIndex;
   }
}
