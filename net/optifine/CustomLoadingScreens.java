package net.optifine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.resources.ResourceKey;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.WorldUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomLoadingScreens {
   private static CustomLoadingScreen[] screens = null;
   private static int screensMinDimensionId = 0;

   public static CustomLoadingScreen getCustomLoadingScreen() {
      if (screens == null) {
         return null;
      } else {
         ResourceKey dimensionType = PacketUtils.lastDimensionType;
         if (dimensionType == null) {
            return null;
         } else {
            int dimension = WorldUtils.getDimensionId(dimensionType);
            int index = dimension - screensMinDimensionId;
            CustomLoadingScreen scr = null;
            if (index >= 0 && index < screens.length) {
               scr = screens[index];
            }

            return scr;
         }
      }
   }

   public static void update() {
      screens = null;
      screensMinDimensionId = 0;
      Pair pair = parseScreens();
      screens = (CustomLoadingScreen[])pair.getLeft();
      screensMinDimensionId = (Integer)pair.getRight();
   }

   private static Pair parseScreens() {
      String prefix = "optifine/gui/loading/background";
      String suffix = ".png";
      String[] paths = ResUtils.collectFiles(prefix, suffix);
      Map map = new HashMap();

      String pathProps;
      for(int i = 0; i < paths.length; ++i) {
         String path = paths[i];
         pathProps = StrUtils.removePrefixSuffix(path, prefix, suffix);
         int dimId = Config.parseInt(pathProps, Integer.MIN_VALUE);
         if (dimId == Integer.MIN_VALUE) {
            warn("Invalid dimension ID: " + pathProps + ", path: " + path);
         } else {
            map.put(dimId, path);
         }
      }

      Set setDimIds = map.keySet();
      Integer[] dimIds = (Integer[])setDimIds.toArray(new Integer[setDimIds.size()]);
      Arrays.sort(dimIds);
      if (dimIds.length <= 0) {
         return new ImmutablePair((Object)null, 0);
      } else {
         pathProps = "optifine/gui/loading/loading.properties";
         Properties props = ResUtils.readProperties(pathProps, "CustomLoadingScreens");
         int minDimId = dimIds[0];
         int maxDimId = dimIds[dimIds.length - 1];
         int countDim = maxDimId - minDimId + 1;
         CustomLoadingScreen[] scrs = new CustomLoadingScreen[countDim];

         for(int i = 0; i < dimIds.length; ++i) {
            Integer dimId = dimIds[i];
            String path = (String)map.get(dimId);
            scrs[dimId - minDimId] = CustomLoadingScreen.parseScreen(path, dimId, props);
         }

         return new ImmutablePair(scrs, minDimId);
      }
   }

   public static void warn(String str) {
      Config.warn("CustomLoadingScreen: " + str);
   }

   public static void dbg(String str) {
      Config.dbg("CustomLoadingScreen: " + str);
   }
}
