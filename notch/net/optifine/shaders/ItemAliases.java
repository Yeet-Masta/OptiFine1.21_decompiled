package net.optifine.shaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_5265_;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.config.MacroProcessor;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.StrUtils;

public class ItemAliases {
   private static int[] itemAliases = null;
   private static boolean updateOnResourcesReloaded;

   public static int getItemAliasId(int itemId) {
      if (itemAliases == null) {
         return -1;
      } else {
         return itemId >= 0 && itemId < itemAliases.length ? itemAliases[itemId] : -1;
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
         if (Reflector.ModList.exists() && C_3391_.m_91087_().m_91098_() == null) {
            Config.dbg("[Shaders] Delayed loading of item mappings after resources are loaded");
            updateOnResourcesReloaded = true;
         } else {
            List<Integer> listItemAliases = new ArrayList();
            String path = "/shaders/item.properties";
            InputStream in = shaderPack.getResourceAsStream(path);
            if (in != null) {
               loadItemAliases(in, path, listItemAliases);
            }

            loadModItemAliases(listItemAliases);
            if (listItemAliases.size() > 0) {
               itemAliases = toArray(listItemAliases);
            }
         }
      }
   }

   private static void loadModItemAliases(List<Integer> listItemAliases) {
      String[] modIds = ReflectorForge.getForgeModIds();

      for (int i = 0; i < modIds.length; i++) {
         String modId = modIds[i];

         try {
            C_5265_ loc = new C_5265_(modId, "shaders/item.properties");
            InputStream in = Config.getResourceStream(loc);
            loadItemAliases(in, loc.toString(), listItemAliases);
         } catch (IOException var6) {
         }
      }
   }

   private static void loadItemAliases(InputStream in, String path, List<Integer> listItemAliases) {
      if (in != null) {
         try {
            in = MacroProcessor.process(in, path, true);
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            Config.dbg("[Shaders] Parsing item mappings: " + path);
            ConnectedParser cp = new ConnectedParser("Shaders");

            for (String key : props.keySet()) {
               String val = props.getProperty(key);
               String prefix = "item.";
               if (!key.startsWith(prefix)) {
                  Config.warn("[Shaders] Invalid item ID: " + key);
               } else {
                  String aliasIdStr = StrUtils.removePrefix(key, prefix);
                  int aliasId = Config.parseInt(aliasIdStr, -1);
                  if (aliasId < 0) {
                     Config.warn("[Shaders] Invalid item alias ID: " + aliasId);
                  } else {
                     int[] itemIds = cp.parseItems(val);
                     if (itemIds != null && itemIds.length >= 1) {
                        for (int i = 0; i < itemIds.length; i++) {
                           int itemId = itemIds[i];
                           addToList(listItemAliases, itemId, aliasId);
                        }
                     } else {
                        Config.warn("[Shaders] Invalid item ID mapping: " + key + "=" + val);
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
      itemAliases = null;
   }
}
