package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.minecraft.src.C_1026_;
import net.minecraft.src.C_1141_;
import net.minecraft.src.C_1153_;
import net.minecraft.src.C_1325_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_141068_;
import net.minecraft.src.C_141142_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4134_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_516_;
import net.minecraft.src.C_5225_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_976_;
import net.minecraft.src.C_977_;
import net.minecraft.src.C_985_;
import net.minecraft.src.C_988_;
import net.optifine.config.ConnectedParser;
import net.optifine.config.EntityTypeNameLocator;
import net.optifine.config.IObjectLocator;
import net.optifine.config.ItemLocator;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.util.PropertiesOrdered;

public class DynamicLights {
   private static DynamicLightsMap mapDynamicLights = new DynamicLightsMap();
   private static Map<String, Integer> mapEntityLightLevels = new HashMap();
   private static Map<C_1381_, Integer> mapItemLightLevels = new HashMap();
   private static long timeUpdateMs = 0L;
   private static final double MAX_DIST = 7.5;
   private static final double MAX_DIST_SQ = 56.25;
   private static final int LIGHT_LEVEL_MAX = 15;
   private static final int LIGHT_LEVEL_FIRE = 15;
   private static final int LIGHT_LEVEL_BLAZE = 10;
   private static final int LIGHT_LEVEL_MAGMA_CUBE = 8;
   private static final int LIGHT_LEVEL_MAGMA_CUBE_CORE = 13;
   private static final int LIGHT_LEVEL_GLOWSTONE_DUST = 8;
   private static final int LIGHT_LEVEL_PRISMARINE_CRYSTALS = 8;
   private static final int LIGHT_LEVEL_GLOW_SQUID = 11;
   private static final int LIGHT_LEVEL_GLOW_INK_SAC = 8;
   private static final int LIGHT_LEVEL_GLOW_LICHEN = 6;
   private static final int LIGHT_LEVEL_GLOW_BERRIES = 12;
   private static final int LIGHT_LEVEL_GLOW_ITEM_FRAME = 8;
   private static final C_5225_<C_1391_> PARAMETER_ITEM_STACK = (C_5225_<C_1391_>)Reflector.EntityItem_ITEM.getValue();
   private static boolean initialized;

   public static void entityAdded(C_507_ entityIn, C_4134_ renderGlobal) {
   }

   public static void entityRemoved(C_507_ entityIn, C_4134_ renderGlobal) {
      synchronized (mapDynamicLights) {
         DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.m_19879_());
         if (dynamicLight != null) {
            dynamicLight.updateLitChunks(renderGlobal);
         }
      }
   }

   public static void update(C_4134_ renderGlobal) {
      long timeNowMs = System.currentTimeMillis();
      if (timeNowMs >= timeUpdateMs + 50L) {
         timeUpdateMs = timeNowMs;
         if (!initialized) {
            initialize();
         }

         synchronized (mapDynamicLights) {
            updateMapDynamicLights(renderGlobal);
            if (mapDynamicLights.size() > 0) {
               List<DynamicLight> dynamicLights = mapDynamicLights.valueList();

               for (int i = 0; i < dynamicLights.size(); i++) {
                  DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
                  dynamicLight.update(renderGlobal);
               }
            }
         }
      }
   }

   private static void initialize() {
      initialized = true;
      mapEntityLightLevels.clear();
      mapItemLightLevels.clear();
      String[] modIds = ReflectorForge.getForgeModIds();

      for (int i = 0; i < modIds.length; i++) {
         String modId = modIds[i];

         try {
            C_5265_ loc = new C_5265_(modId, "optifine/dynamic_lights.properties");
            InputStream in = Config.getResourceStream(loc);
            loadModConfiguration(in, loc.toString(), modId);
         } catch (IOException var5) {
         }
      }

      if (mapEntityLightLevels.size() > 0) {
         Config.dbg("DynamicLights entities: " + mapEntityLightLevels.size());
      }

      if (mapItemLightLevels.size() > 0) {
         Config.dbg("DynamicLights items: " + mapItemLightLevels.size());
      }
   }

   private static void loadModConfiguration(InputStream in, String path, String modId) {
      if (in != null) {
         try {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg("DynamicLights: Parsing " + path);
            ConnectedParser cp = new ConnectedParser("DynamicLights");
            loadModLightLevels(props.getProperty("entities"), mapEntityLightLevels, new EntityTypeNameLocator(), cp, path, modId);
            loadModLightLevels(props.getProperty("items"), mapItemLightLevels, new ItemLocator(), cp, path, modId);
         } catch (IOException var5) {
            Config.warn("DynamicLights: Error reading " + path);
         }
      }
   }

   private static <T> void loadModLightLevels(String prop, Map<T, Integer> mapLightLevels, IObjectLocator<T> ol, ConnectedParser cp, String path, String modId) {
      if (prop != null) {
         String[] parts = Config.tokenize(prop, " ");

         for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            String[] tokens = Config.tokenize(part, ":");
            if (tokens.length != 2) {
               cp.warn("Invalid entry: " + part + ", in:" + path);
            } else {
               String name = tokens[0];
               String light = tokens[1];
               String nameFull = modId + ":" + name;
               C_5265_ loc = new C_5265_(nameFull);
               T obj = (T)ol.getObject(loc);
               if (obj == null) {
                  cp.warn("Object not found: " + nameFull);
               } else {
                  int lightLevel = cp.parseInt(light, -1);
                  if (lightLevel >= 0 && lightLevel <= 15) {
                     mapLightLevels.put(obj, new Integer(lightLevel));
                  } else {
                     cp.warn("Invalid light level: " + part);
                  }
               }
            }
         }
      }
   }

   private static void updateMapDynamicLights(C_4134_ renderGlobal) {
      C_3899_ world = renderGlobal.getWorld();
      if (world != null) {
         for (C_507_ entity : world.m_104735_()) {
            int lightLevel = getLightLevel(entity);
            if (lightLevel > 0) {
               int key = entity.m_19879_();
               DynamicLight dynamicLight = mapDynamicLights.get(key);
               if (dynamicLight == null) {
                  dynamicLight = new DynamicLight(entity);
                  mapDynamicLights.put(key, dynamicLight);
               }
            } else {
               int key = entity.m_19879_();
               DynamicLight dynamicLight = mapDynamicLights.remove(key);
               if (dynamicLight != null) {
                  dynamicLight.updateLitChunks(renderGlobal);
               }
            }
         }
      }
   }

   public static int getCombinedLight(C_4675_ pos, int combinedLight) {
      double lightPos = getLightLevel(pos);
      return getCombinedLight(lightPos, combinedLight);
   }

   public static int getCombinedLight(C_507_ entity, int combinedLight) {
      double lightPos = getLightLevel(entity.m_20183_());
      if (entity == Config.getMinecraft().f_91074_) {
         double lightOwn = (double)getLightLevel(entity);
         lightPos = Math.max(lightPos, lightOwn);
      }

      return getCombinedLight(lightPos, combinedLight);
   }

   public static int getCombinedLight(double lightPlayer, int combinedLight) {
      if (lightPlayer > 0.0) {
         int lightPlayerFF = (int)(lightPlayer * 16.0);
         int lightBlockFF = combinedLight & 0xFF;
         if (lightPlayerFF > lightBlockFF) {
            combinedLight &= -256;
            combinedLight |= lightPlayerFF;
         }
      }

      return combinedLight;
   }

   public static double getLightLevel(C_4675_ pos) {
      double lightLevelMax = 0.0;
      synchronized (mapDynamicLights) {
         List<DynamicLight> dynamicLights = mapDynamicLights.valueList();
         int dynamicLightsSize = dynamicLights.size();

         for (int i = 0; i < dynamicLightsSize; i++) {
            DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
            int dynamicLightLevel = dynamicLight.getLastLightLevel();
            if (dynamicLightLevel > 0) {
               double px = dynamicLight.getLastPosX();
               double py = dynamicLight.getLastPosY();
               double pz = dynamicLight.getLastPosZ();
               double dx = (double)pos.u() - px;
               double dy = (double)pos.v() - py;
               double dz = (double)pos.w() - pz;
               double distSq = dx * dx + dy * dy + dz * dz;
               if (!(distSq > 56.25)) {
                  double dist = Math.sqrt(distSq);
                  double light = 1.0 - dist / 7.5;
                  double lightLevel = light * (double)dynamicLightLevel;
                  if (lightLevel > lightLevelMax) {
                     lightLevelMax = lightLevel;
                  }
               }
            }
         }
      }

      return Config.limit(lightLevelMax, 0.0, 15.0);
   }

   public static int getLightLevel(C_1391_ itemStack) {
      if (itemStack == null) {
         return 0;
      } else {
         C_1381_ item = itemStack.m_41720_();
         if (item instanceof C_1325_ itemBlock) {
            C_1706_ block = itemBlock.m_40614_();
            if (block != null) {
               if (block == C_1710_.f_152480_) {
                  return 0;
               }

               if (block == C_1710_.f_152475_) {
                  return 6;
               }

               if (block == C_1710_.f_152538_) {
                  return 12;
               }

               return block.m_49966_().h();
            }
         }

         if (item == C_1394_.f_42448_) {
            return C_1710_.f_49991_.m_49966_().h();
         } else if (item == C_1394_.f_42585_ || item == C_1394_.f_42593_) {
            return 10;
         } else if (item == C_1394_.f_42525_) {
            return 8;
         } else if (item == C_1394_.f_42696_) {
            return 8;
         } else if (item == C_1394_.f_42542_) {
            return 8;
         } else if (item == C_1394_.f_42686_) {
            return C_1710_.f_50273_.m_49966_().h() / 2;
         } else if (item == C_1394_.f_151056_) {
            return 8;
         } else if (item == C_1394_.f_151063_) {
            return 8;
         } else {
            if (!mapItemLightLevels.isEmpty()) {
               Integer level = (Integer)mapItemLightLevels.get(item);
               if (level != null) {
                  return level;
               }
            }

            return 0;
         }
      }
   }

   public static int getLightLevel(C_507_ entity) {
      if (entity == Config.getMinecraft().m_91288_() && !Config.isDynamicHandLight()) {
         return 0;
      } else {
         if (entity instanceof C_1141_ player && player.m_5833_()) {
            return 0;
         }

         if (entity.m_6060_()) {
            return 15;
         } else {
            if (!mapEntityLightLevels.isEmpty()) {
               String typeName = EntityTypeNameLocator.getEntityTypeName(entity);
               Integer level = (Integer)mapEntityLightLevels.get(typeName);
               if (level != null) {
                  return level;
               }
            }

            if (entity instanceof C_1153_) {
               return 15;
            } else if (entity instanceof C_977_) {
               return 15;
            } else if (entity instanceof C_985_ entityBlaze) {
               return entityBlaze.m_6060_() ? 15 : 10;
            } else if (entity instanceof C_1026_ emc) {
               return (double)emc.cb > 0.6 ? 13 : 8;
            } else {
               if (entity instanceof C_988_ entityCreeper && (double)entityCreeper.m_32320_(0.0F) > 0.001) {
                  return 15;
               }

               if (entity instanceof C_141068_ glowSquid) {
                  return (int)C_188_.m_144920_(0.0F, 11.0F, 1.0F - (float)glowSquid.m_147128_() / 10.0F);
               } else if (entity instanceof C_141142_) {
                  return 8;
               } else if (entity instanceof C_524_ player) {
                  C_1391_ stackMain = player.m_21205_();
                  int levelMain = getLightLevel(stackMain);
                  C_1391_ stackOff = player.m_21206_();
                  int levelOff = getLightLevel(stackOff);
                  C_1391_ stackHead = player.m_6844_(C_516_.HEAD);
                  int levelHead = getLightLevel(stackHead);
                  int levelHandMax = Math.max(levelMain, levelOff);
                  return Math.max(levelHandMax, levelHead);
               } else if (entity instanceof C_976_ entityItem) {
                  C_1391_ itemStack = getItemStack(entityItem);
                  return getLightLevel(itemStack);
               } else {
                  return 0;
               }
            }
         }
      }
   }

   public static void removeLights(C_4134_ renderGlobal) {
      synchronized (mapDynamicLights) {
         List<DynamicLight> dynamicLights = mapDynamicLights.valueList();

         for (int i = 0; i < dynamicLights.size(); i++) {
            DynamicLight dynamicLight = (DynamicLight)dynamicLights.get(i);
            dynamicLight.updateLitChunks(renderGlobal);
         }

         mapDynamicLights.clear();
      }
   }

   public static void clear() {
      synchronized (mapDynamicLights) {
         mapDynamicLights.clear();
      }
   }

   public static int getCount() {
      synchronized (mapDynamicLights) {
         return mapDynamicLights.size();
      }
   }

   public static C_1391_ getItemStack(C_976_ entityItem) {
      return (C_1391_)entityItem.ar().m_135370_(PARAMETER_ITEM_STACK);
   }
}
