package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.client.Minecraft;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class EntityAliases {
   private static int[] entityAliases = null;
   private static boolean updateOnResourcesReloaded;

   public static int getEntityAliasId(int entityId) {
      if (entityAliases == null) {
         return -1;
      } else {
         return entityId >= 0 && entityId < entityAliases.length ? entityAliases[entityId] : -1;
      }
   }

   public static void resourcesReloaded() {
      if (updateOnResourcesReloaded) {
         updateOnResourcesReloaded = false;
         update(Shaders.getShaderPack());
      }
   }

   public static void update(IShaderPack shaderPack) {
      reset();
      if (shaderPack != null) {
         if (Reflector.ModList.exists() && Minecraft.m_91087_().m_91098_() == null) {
            Config.dbg("[Shaders] Delayed loading of entity mappings after resources are loaded");
            updateOnResourcesReloaded = true;
         } else {
            List<Integer> listEntityAliases = new ArrayList();
            String path = "/shaders/entity.properties";
            InputStream in = shaderPack.getResourceAsStream(path);
            if (in != null) {
               loadEntityAliases(in, path, listEntityAliases);
            }

            loadModEntityAliases(listEntityAliases);
            if (listEntityAliases.size() > 0) {
               entityAliases = toArray(listEntityAliases);
            }
         }
      }
   }

   private static void loadModEntityAliases(List<Integer> listEntityAliases) {
      String[] modIds = ReflectorForge.getForgeModIds();

      for (int i = 0; i < modIds.length; i++) {
         String modId = modIds[i];

         try {
            net.minecraft.resources.ResourceLocation loc = new net.minecraft.resources.ResourceLocation(modId, "shaders/entity.properties");
            InputStream in = Config.getResourceStream(loc);
            loadEntityAliases(in, loc.toString(), listEntityAliases);
         } catch (IOException var6) {
         }
      }
   }

   private static void loadEntityAliases(InputStream in, String path, List<Integer> listEntityAliases) {
      if (in != null) {
         try {
            in = MacroProcessor.process(in, path, true);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg("[Shaders] Parsing entity mappings: " + path);
            ConnectedParser cp = new ConnectedParser("Shaders");

            for (String key : props.keySet()) {
               String val = props.getProperty(key);
               String prefix = "entity.";
               if (!key.startsWith(prefix)) {
                  Config.warn("[Shaders] Invalid entity ID: " + key);
               } else {
                  String aliasIdStr = StrUtils.removePrefix(key, prefix);
                  int aliasId = Config.parseInt(aliasIdStr, -1);
                  if (aliasId < 0) {
                     Config.warn("[Shaders] Invalid entity alias ID: " + aliasId);
                  } else {
                     int[] entityIds = cp.parseEntities(val);
                     if (entityIds != null && entityIds.length >= 1) {
                        for (int i = 0; i < entityIds.length; i++) {
                           int entityId = entityIds[i];
                           addToList(listEntityAliases, entityId, aliasId);
                        }
                     } else {
                        Config.warn("[Shaders] Invalid entity ID mapping: " + key + "=" + val);
                     }
                  }
               }
            }
         } catch (IOException var15) {
            Config.warn("[Shaders] Error reading: " + path);
         }
      }
   }

   private static void addToList(List<Integer> list, int index, int val) {
      while (list.size() <= index) {
         list.add(-1);
      }

      list.set(index, val);
   }

   private static int[] toArray(List<Integer> list) {
      int[] arr = new int[list.size()];

      for (int i = 0; i < arr.length; i++) {
         arr[i] = (Integer)list.get(i);
      }

      return arr;
   }

   public static void reset() {
      entityAliases = null;
   }
}
