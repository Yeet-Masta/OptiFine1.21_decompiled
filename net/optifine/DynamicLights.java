package net.optifine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.GlowItemFrame;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
   private static Map<Item, Integer> mapItemLightLevels = new HashMap();
   private static long timeUpdateMs = 0L;
   private static double MAX_DIST;
   private static double MAX_DIST_SQ;
   private static int LIGHT_LEVEL_MAX;
   private static int LIGHT_LEVEL_FIRE;
   private static int LIGHT_LEVEL_BLAZE;
   private static int LIGHT_LEVEL_MAGMA_CUBE;
   private static int LIGHT_LEVEL_MAGMA_CUBE_CORE;
   private static int LIGHT_LEVEL_GLOWSTONE_DUST;
   private static int LIGHT_LEVEL_PRISMARINE_CRYSTALS;
   private static int LIGHT_LEVEL_GLOW_SQUID;
   private static int LIGHT_LEVEL_GLOW_INK_SAC;
   private static int LIGHT_LEVEL_GLOW_LICHEN;
   private static int LIGHT_LEVEL_GLOW_BERRIES;
   private static int LIGHT_LEVEL_GLOW_ITEM_FRAME;
   private static EntityDataAccessor<ItemStack> PARAMETER_ITEM_STACK = (EntityDataAccessor<ItemStack>)Reflector.EntityItem_ITEM.getValue();
   private static boolean initialized;

   public static void entityAdded(Entity entityIn, LevelRenderer renderGlobal) {
   }

   public static void entityRemoved(Entity entityIn, LevelRenderer renderGlobal) {
      synchronized (mapDynamicLights) {
         DynamicLight dynamicLight = mapDynamicLights.remove(entityIn.m_19879_());
         if (dynamicLight != null) {
            dynamicLight.updateLitChunks(renderGlobal);
         }
      }
   }

   public static void m_252999_(LevelRenderer renderGlobal) {
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
                  dynamicLight.m_252999_(renderGlobal);
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
            ResourceLocation loc = new ResourceLocation(modId, "optifine/dynamic_lights.properties");
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
               ResourceLocation loc = new ResourceLocation(nameFull);
               T obj = ol.getObject(loc);
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

   private static void updateMapDynamicLights(LevelRenderer renderGlobal) {
      ClientLevel world = renderGlobal.getWorld();
      if (world != null) {
         for (Entity entity : world.m_104735_()) {
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

   public static int getCombinedLight(BlockPos pos, int combinedLight) {
      double lightPos = getLightLevel(pos);
      return getCombinedLight(lightPos, combinedLight);
   }

   public static int getCombinedLight(Entity entity, int combinedLight) {
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

   public static double getLightLevel(BlockPos pos) {
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
               double dx = (double)pos.m_123341_() - px;
               double dy = (double)pos.m_123342_() - py;
               double dz = (double)pos.m_123343_() - pz;
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

   public static int getLightLevel(ItemStack itemStack) {
      if (itemStack == null) {
         return 0;
      } else {
         Item item = itemStack.m_41720_();
         if (item instanceof BlockItem itemBlock) {
            Block block = itemBlock.m_40614_();
            if (block != null) {
               if (block == Blocks.f_152480_) {
                  return 0;
               }

               if (block == Blocks.f_152475_) {
                  return 6;
               }

               if (block == Blocks.f_152538_) {
                  return 12;
               }

               return block.m_49966_().m_60791_();
            }
         }

         if (item == Items.f_42448_) {
            return Blocks.f_49991_.m_49966_().m_60791_();
         } else if (item == Items.f_42585_ || item == Items.f_42593_) {
            return 10;
         } else if (item == Items.f_42525_) {
            return 8;
         } else if (item == Items.f_42696_) {
            return 8;
         } else if (item == Items.f_42542_) {
            return 8;
         } else if (item == Items.f_42686_) {
            return Blocks.f_50273_.m_49966_().m_60791_() / 2;
         } else if (item == Items.f_151056_) {
            return 8;
         } else if (item == Items.f_151063_) {
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

   public static int getLightLevel(Entity entity) {
      if (entity == Config.getMinecraft().m_91288_() && !Config.isDynamicHandLight()) {
         return 0;
      } else {
         if (entity instanceof Player player && player.m_5833_()) {
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

            if (entity instanceof AbstractHurtingProjectile) {
               return 15;
            } else if (entity instanceof PrimedTnt) {
               return 15;
            } else if (entity instanceof Blaze entityBlaze) {
               return entityBlaze.m_6060_() ? 15 : 10;
            } else if (entity instanceof MagmaCube emc) {
               return (double)emc.f_33584_ > 0.6 ? 13 : 8;
            } else {
               if (entity instanceof Creeper entityCreeper && (double)entityCreeper.m_32320_(0.0F) > 0.001) {
                  return 15;
               }

               if (entity instanceof GlowSquid glowSquid) {
                  return (int)Mth.m_144920_(0.0F, 11.0F, 1.0F - (float)glowSquid.m_147128_() / 10.0F);
               } else if (entity instanceof GlowItemFrame) {
                  return 8;
               } else if (entity instanceof LivingEntity player) {
                  ItemStack stackMain = player.m_21205_();
                  int levelMain = getLightLevel(stackMain);
                  ItemStack stackOff = player.m_21206_();
                  int levelOff = getLightLevel(stackOff);
                  ItemStack stackHead = player.m_6844_(EquipmentSlot.HEAD);
                  int levelHead = getLightLevel(stackHead);
                  int levelHandMax = Math.max(levelMain, levelOff);
                  return Math.max(levelHandMax, levelHead);
               } else if (entity instanceof ItemEntity entityItem) {
                  ItemStack itemStack = getItemStack(entityItem);
                  return getLightLevel(itemStack);
               } else {
                  return 0;
               }
            }
         }
      }
   }

   public static void removeLights(LevelRenderer renderGlobal) {
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

   public static ItemStack getItemStack(ItemEntity entityItem) {
      return entityItem.m_20088_().m_135370_(PARAMETER_ITEM_STACK);
   }
}
