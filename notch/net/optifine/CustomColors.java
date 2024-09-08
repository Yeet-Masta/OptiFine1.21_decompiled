package net.optifine;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import javax.imageio.ImageIO;
import net.minecraft.src.C_1353_;
import net.minecraft.src.C_1381_;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_1394_;
import net.minecraft.src.C_1420_;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1581_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1710_;
import net.minecraft.src.C_1772_;
import net.minecraft.src.C_1826_;
import net.minecraft.src.C_1873_;
import net.minecraft.src.C_188_;
import net.minecraft.src.C_1923_;
import net.minecraft.src.C_1937_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2091_;
import net.minecraft.src.C_256712_;
import net.minecraft.src.C_283734_;
import net.minecraft.src.C_3046_;
import net.minecraft.src.C_3148_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3423_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4022_;
import net.minecraft.src.C_4196_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_496_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5250_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_174_.C_175_;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.render.RenderEnv;
import net.optifine.util.BiomeUtils;
import net.optifine.util.EntityUtils;
import net.optifine.util.PotionUtils;
import net.optifine.util.PropertiesOrdered;
import net.optifine.util.ResUtils;
import net.optifine.util.StrUtils;
import net.optifine.util.TextureUtils;
import net.optifine.util.WorldUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class CustomColors {
   private static String paletteFormatDefault = "vanilla";
   private static CustomColormap waterColors = null;
   private static CustomColormap foliagePineColors = null;
   private static CustomColormap foliageBirchColors = null;
   private static CustomColormap swampFoliageColors = null;
   private static CustomColormap swampGrassColors = null;
   private static CustomColormap[] colorsBlockColormaps = null;
   private static CustomColormap[][] blockColormaps = null;
   private static CustomColormap skyColors = null;
   private static CustomColorFader skyColorFader = new CustomColorFader();
   private static CustomColormap fogColors = null;
   private static CustomColorFader fogColorFader = new CustomColorFader();
   private static CustomColormap underwaterColors = null;
   private static CustomColorFader underwaterColorFader = new CustomColorFader();
   private static CustomColormap underlavaColors = null;
   private static CustomColorFader underlavaColorFader = new CustomColorFader();
   private static LightMapPack[] lightMapPacks = null;
   private static int lightmapMinDimensionId = 0;
   private static CustomColormap redstoneColors = null;
   private static CustomColormap xpOrbColors = null;
   private static int xpOrbTime = -1;
   private static CustomColormap durabilityColors = null;
   private static CustomColormap stemColors = null;
   private static CustomColormap stemMelonColors = null;
   private static CustomColormap stemPumpkinColors = null;
   private static CustomColormap lavaDropColors = null;
   private static CustomColormap myceliumParticleColors = null;
   private static boolean useDefaultGrassFoliageColors = true;
   private static int particleWaterColor = -1;
   private static int particlePortalColor = -1;
   private static int lilyPadColor = -1;
   private static int expBarTextColor = -1;
   private static int bossTextColor = -1;
   private static int signTextColor = -1;
   private static C_3046_ fogColorNether = null;
   private static C_3046_ fogColorEnd = null;
   private static C_3046_ skyColorEnd = null;
   private static int[] spawnEggPrimaryColors = null;
   private static int[] spawnEggSecondaryColors = null;
   private static int[] wolfCollarColors = null;
   private static int[] sheepColors = null;
   private static int[] textColors = null;
   private static int[] mapColorsOriginal = null;
   private static int[] dyeColorsOriginal = null;
   private static int[] potionColors = null;
   private static final C_2064_ BLOCK_STATE_DIRT = C_1710_.f_50493_.m_49966_();
   private static final C_2064_ BLOCK_STATE_WATER = C_1710_.f_49990_.m_49966_();
   public static Random random = new Random();
   private static final CustomColors.IColorizer COLORIZER_GRASS = new CustomColors.IColorizer() {
      public int getColor(C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos) {
         C_1629_ biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.swampGrassColors != null && biome == BiomeUtils.SWAMP
            ? CustomColors.swampGrassColors.getColor(biome, blockPos)
            : biome.m_47464_((double)blockPos.u(), (double)blockPos.w());
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE = new CustomColors.IColorizer() {
      public int getColor(C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos) {
         C_1629_ biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.swampFoliageColors != null && biome == BiomeUtils.SWAMP
            ? CustomColors.swampFoliageColors.getColor(biome, blockPos)
            : biome.m_47542_();
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_PINE = new CustomColors.IColorizer() {
      public int getColor(C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos) {
         return CustomColors.foliagePineColors != null ? CustomColors.foliagePineColors.getColor(blockAccess, blockPos) : C_1581_.m_46106_();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.foliagePineColors == null;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_FOLIAGE_BIRCH = new CustomColors.IColorizer() {
      public int getColor(C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos) {
         return CustomColors.foliageBirchColors != null ? CustomColors.foliageBirchColors.getColor(blockAccess, blockPos) : C_1581_.m_46112_();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.foliageBirchColors == null;
      }
   };
   private static final CustomColors.IColorizer COLORIZER_WATER = new CustomColors.IColorizer() {
      public int getColor(C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos) {
         C_1629_ biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.waterColors != null ? CustomColors.waterColors.getColor(biome, blockPos) : biome.m_47560_();
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };

   public static void update() {
      paletteFormatDefault = "vanilla";
      waterColors = null;
      foliageBirchColors = null;
      foliagePineColors = null;
      swampGrassColors = null;
      swampFoliageColors = null;
      skyColors = null;
      fogColors = null;
      underwaterColors = null;
      underlavaColors = null;
      redstoneColors = null;
      xpOrbColors = null;
      xpOrbTime = -1;
      durabilityColors = null;
      stemColors = null;
      lavaDropColors = null;
      myceliumParticleColors = null;
      lightMapPacks = null;
      particleWaterColor = -1;
      particlePortalColor = -1;
      lilyPadColor = -1;
      expBarTextColor = -1;
      bossTextColor = -1;
      signTextColor = -1;
      fogColorNether = null;
      fogColorEnd = null;
      skyColorEnd = null;
      colorsBlockColormaps = null;
      blockColormaps = null;
      useDefaultGrassFoliageColors = true;
      spawnEggPrimaryColors = null;
      spawnEggSecondaryColors = null;
      wolfCollarColors = null;
      sheepColors = null;
      textColors = null;
      setMapColors(mapColorsOriginal);
      setDyeColors(dyeColorsOriginal);
      potionColors = null;
      paletteFormatDefault = getValidProperty("optifine/color.properties", "palette.format", CustomColormap.FORMAT_STRINGS, "vanilla");
      String mcpColormap = "optifine/colormap/";
      String[] waterPaths = new String[]{"water.png", "watercolorx.png"};
      waterColors = getCustomColors(mcpColormap, waterPaths, 256, -1);
      updateUseDefaultGrassFoliageColors();
      if (Config.isCustomColors()) {
         String[] pinePaths = new String[]{"pine.png", "pinecolor.png"};
         foliagePineColors = getCustomColors(mcpColormap, pinePaths, 256, -1);
         String[] birchPaths = new String[]{"birch.png", "birchcolor.png"};
         foliageBirchColors = getCustomColors(mcpColormap, birchPaths, 256, -1);
         String[] swampGrassPaths = new String[]{"swampgrass.png", "swampgrasscolor.png"};
         swampGrassColors = getCustomColors(mcpColormap, swampGrassPaths, 256, -1);
         String[] swampFoliagePaths = new String[]{"swampfoliage.png", "swampfoliagecolor.png"};
         swampFoliageColors = getCustomColors(mcpColormap, swampFoliagePaths, 256, -1);
         String[] sky0Paths = new String[]{"sky0.png", "skycolor0.png"};
         skyColors = getCustomColors(mcpColormap, sky0Paths, 256, -1);
         String[] fog0Paths = new String[]{"fog0.png", "fogcolor0.png"};
         fogColors = getCustomColors(mcpColormap, fog0Paths, 256, -1);
         String[] underwaterPaths = new String[]{"underwater.png", "underwatercolor.png"};
         underwaterColors = getCustomColors(mcpColormap, underwaterPaths, 256, -1);
         String[] underlavaPaths = new String[]{"underlava.png", "underlavacolor.png"};
         underlavaColors = getCustomColors(mcpColormap, underlavaPaths, 256, -1);
         String[] redstonePaths = new String[]{"redstone.png", "redstonecolor.png"};
         redstoneColors = getCustomColors(mcpColormap, redstonePaths, 16, 1);
         xpOrbColors = getCustomColors(mcpColormap + "xporb.png", -1, -1);
         durabilityColors = getCustomColors(mcpColormap + "durability.png", -1, -1);
         String[] stemPaths = new String[]{"stem.png", "stemcolor.png"};
         stemColors = getCustomColors(mcpColormap, stemPaths, 8, 1);
         stemPumpkinColors = getCustomColors(mcpColormap + "pumpkinstem.png", 8, 1);
         stemMelonColors = getCustomColors(mcpColormap + "melonstem.png", 8, 1);
         lavaDropColors = getCustomColors(mcpColormap + "lavadrop.png", -1, 1);
         String[] myceliumPaths = new String[]{"myceliumparticle.png", "myceliumparticlecolor.png"};
         myceliumParticleColors = getCustomColors(mcpColormap, myceliumPaths, -1, -1);
         Pair<LightMapPack[], Integer> lightMaps = parseLightMapPacks();
         lightMapPacks = (LightMapPack[])lightMaps.getLeft();
         lightmapMinDimensionId = (Integer)lightMaps.getRight();
         readColorProperties("optifine/color.properties");
         blockColormaps = readBlockColormaps(new String[]{mcpColormap + "custom/", mcpColormap + "blocks/"}, colorsBlockColormaps, 256, -1);
         updateUseDefaultGrassFoliageColors();
      }
   }

   private static String getValidProperty(String fileName, String key, String[] validValues, String valDef) {
      try {
         C_5265_ loc = new C_5265_(fileName);
         InputStream in = Config.getResourceStream(loc);
         if (in == null) {
            return valDef;
         } else {
            Properties props = new PropertiesOrdered();
            props.load(in);
            in.close();
            String val = props.getProperty(key);
            if (val == null) {
               return valDef;
            } else {
               List<String> listValidValues = Arrays.asList(validValues);
               if (!listValidValues.contains(val)) {
                  warn("Invalid value: " + key + "=" + val);
                  warn("Expected values: " + Config.arrayToString((Object[])validValues));
                  return valDef;
               } else {
                  dbg(key + "=" + val);
                  return val;
               }
            }
         }
      } catch (FileNotFoundException var9) {
         return valDef;
      } catch (IOException var10) {
         var10.printStackTrace();
         return valDef;
      }
   }

   private static Pair<LightMapPack[], Integer> parseLightMapPacks() {
      String lightmapPrefix = "optifine/lightmap/world";
      String lightmapSuffix = ".png";
      String[] pathsLightmap = ResUtils.collectFiles(lightmapPrefix, lightmapSuffix);
      Map<Integer, String> mapLightmaps = new HashMap();

      for (int i = 0; i < pathsLightmap.length; i++) {
         String path = pathsLightmap[i];
         String dimIdStr = StrUtils.removePrefixSuffix(path, lightmapPrefix, lightmapSuffix);
         int dimId = Config.parseInt(dimIdStr, Integer.MIN_VALUE);
         if (dimId != Integer.MIN_VALUE) {
            mapLightmaps.put(dimId, path);
         }
      }

      Set<Integer> setDimIds = mapLightmaps.keySet();
      Integer[] dimIds = (Integer[])setDimIds.toArray(new Integer[setDimIds.size()]);
      Arrays.sort(dimIds);
      if (dimIds.length <= 0) {
         return new ImmutablePair(null, 0);
      } else {
         int minDimId = dimIds[0];
         int maxDimId = dimIds[dimIds.length - 1];
         int countDim = maxDimId - minDimId + 1;
         CustomColormap[] colormaps = new CustomColormap[countDim];

         for (int ix = 0; ix < dimIds.length; ix++) {
            Integer dimId = dimIds[ix];
            String path = (String)mapLightmaps.get(dimId);
            CustomColormap colors = getCustomColors(path, -1, -1);
            if (colors != null) {
               if (colors.getWidth() < 16) {
                  warn("Invalid lightmap width: " + colors.getWidth() + ", path: " + path);
               } else {
                  int lightmapIndex = dimId - minDimId;
                  colormaps[lightmapIndex] = colors;
               }
            }
         }

         LightMapPack[] lmps = new LightMapPack[colormaps.length];

         for (int ixx = 0; ixx < colormaps.length; ixx++) {
            CustomColormap cm = colormaps[ixx];
            if (cm != null) {
               String name = cm.name;
               String basePath = cm.basePath;
               CustomColormap cmRain = getCustomColors(basePath + "/" + name + "_rain.png", -1, -1);
               CustomColormap cmThunder = getCustomColors(basePath + "/" + name + "_thunder.png", -1, -1);
               LightMap lm = new LightMap(cm);
               LightMap lmRain = cmRain != null ? new LightMap(cmRain) : null;
               LightMap lmThunder = cmThunder != null ? new LightMap(cmThunder) : null;
               LightMapPack lmp = new LightMapPack(lm, lmRain, lmThunder);
               lmps[ixx] = lmp;
            }
         }

         return new ImmutablePair(lmps, minDimId);
      }
   }

   private static int getTextureHeight(String path, int defHeight) {
      try {
         InputStream in = Config.getResourceStream(new C_5265_(path));
         if (in == null) {
            return defHeight;
         } else {
            BufferedImage bi = ImageIO.read(in);
            in.close();
            return bi == null ? defHeight : bi.getHeight();
         }
      } catch (IOException var4) {
         return defHeight;
      }
   }

   private static void readColorProperties(String fileName) {
      try {
         C_5265_ loc = new C_5265_(fileName);
         InputStream in = Config.getResourceStream(loc);
         if (in == null) {
            return;
         }

         dbg("Loading " + fileName);
         Properties props = new PropertiesOrdered();
         props.load(in);
         in.close();
         particleWaterColor = readColor(props, new String[]{"particle.water", "drop.water"});
         particlePortalColor = readColor(props, "particle.portal");
         lilyPadColor = readColor(props, "lilypad");
         expBarTextColor = readColor(props, "text.xpbar");
         bossTextColor = readColor(props, "text.boss");
         signTextColor = readColor(props, "text.sign");
         fogColorNether = readColorVec3(props, "fog.nether");
         fogColorEnd = readColorVec3(props, "fog.end");
         skyColorEnd = readColorVec3(props, "sky.end");
         colorsBlockColormaps = readCustomColormaps(props, fileName);
         spawnEggPrimaryColors = readSpawnEggColors(props, fileName, "egg.shell.", "Spawn egg shell");
         spawnEggSecondaryColors = readSpawnEggColors(props, fileName, "egg.spots.", "Spawn egg spot");
         wolfCollarColors = readDyeColors(props, fileName, "collar.", "Wolf collar");
         sheepColors = readDyeColors(props, fileName, "sheep.", "Sheep");
         textColors = readTextColors(props, fileName, "text.code.", "Text");
         int[] mapColors = readMapColors(props, fileName, "map.", "Map");
         if (mapColors != null) {
            if (mapColorsOriginal == null) {
               mapColorsOriginal = getMapColors();
            }

            setMapColors(mapColors);
         }

         int[] dyeColors = readDyeColors(props, fileName, "dye.", "Dye");
         if (dyeColors != null) {
            if (dyeColorsOriginal == null) {
               dyeColorsOriginal = getDyeColors();
            }

            setDyeColors(dyeColors);
         }

         potionColors = readPotionColors(props, fileName, "potion.", "Potion");
         xpOrbTime = Config.parseInt(props.getProperty("xporb.time"), -1);
      } catch (FileNotFoundException var6) {
         return;
      } catch (IOException var7) {
         Config.warn("Error parsing: " + fileName);
         Config.warn(var7.getClass().getName() + ": " + var7.getMessage());
      }
   }

   private static CustomColormap[] readCustomColormaps(Properties props, String fileName) {
      List list = new ArrayList();
      String palettePrefix = "palette.block.";
      Map map = new HashMap();

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(palettePrefix)) {
            map.put(key, value);
         }
      }

      String[] propNames = (String[])map.keySet().toArray(new String[map.size()]);

      for (int i = 0; i < propNames.length; i++) {
         String name = propNames[i];
         String value = props.getProperty(name);
         dbg("Block palette: " + name + " = " + value);
         String path = name.substring(palettePrefix.length());
         String basePath = TextureUtils.getBasePath(fileName);
         path = TextureUtils.fixResourcePath(path, basePath);
         CustomColormap colors = getCustomColors(path, 256, -1);
         if (colors == null) {
            warn("Colormap not found: " + path);
         } else {
            ConnectedParser cp = new ConnectedParser("CustomColors");
            MatchBlock[] mbs = cp.parseMatchBlocks(value);
            if (mbs != null && mbs.length > 0) {
               for (int m = 0; m < mbs.length; m++) {
                  MatchBlock mb = mbs[m];
                  colors.addMatchBlock(mb);
               }

               list.add(colors);
            } else {
               warn("Invalid match blocks: " + value);
            }
         }
      }

      return list.size() <= 0 ? null : (CustomColormap[])list.toArray(new CustomColormap[list.size()]);
   }

   private static CustomColormap[][] readBlockColormaps(String[] basePaths, CustomColormap[] basePalettes, int width, int height) {
      String[] paths = ResUtils.collectFiles(basePaths, new String[]{".properties"});
      Arrays.sort(paths);
      List blockList = new ArrayList();

      for (int i = 0; i < paths.length; i++) {
         String path = paths[i];
         dbg("Block colormap: " + path);

         try {
            C_5265_ locFile = new C_5265_("minecraft", path);
            InputStream in = Config.getResourceStream(locFile);
            if (in == null) {
               warn("File not found: " + path);
            } else {
               Properties props = new PropertiesOrdered();
               props.load(in);
               in.close();
               CustomColormap cm = new CustomColormap(props, path, width, height, paletteFormatDefault);
               if (cm.isValid(path) && cm.isValidMatchBlocks(path)) {
                  addToBlockList(cm, blockList);
               }
            }
         } catch (FileNotFoundException var12) {
            warn("File not found: " + path);
         } catch (Exception var13) {
            var13.printStackTrace();
         }
      }

      if (basePalettes != null) {
         for (int i = 0; i < basePalettes.length; i++) {
            CustomColormap cm = basePalettes[i];
            addToBlockList(cm, blockList);
         }
      }

      return blockList.size() <= 0 ? null : blockListToArray(blockList);
   }

   private static void addToBlockList(CustomColormap cm, List blockList) {
      int[] ids = cm.getMatchBlockIds();
      if (ids != null && ids.length > 0) {
         for (int i = 0; i < ids.length; i++) {
            int blockId = ids[i];
            if (blockId < 0) {
               warn("Invalid block ID: " + blockId);
            } else {
               addToList(cm, blockList, blockId);
            }
         }
      } else {
         warn("No match blocks: " + Config.arrayToString(ids));
      }
   }

   private static void addToList(CustomColormap cm, List list, int id) {
      while (id >= list.size()) {
         list.add(null);
      }

      List subList = (List)list.get(id);
      if (subList == null) {
         subList = new ArrayList();
         list.set(id, subList);
      }

      subList.add(cm);
   }

   private static CustomColormap[][] blockListToArray(List list) {
      CustomColormap[][] colArr = new CustomColormap[list.size()][];

      for (int i = 0; i < list.size(); i++) {
         List subList = (List)list.get(i);
         if (subList != null) {
            CustomColormap[] subArr = (CustomColormap[])subList.toArray(new CustomColormap[subList.size()]);
            colArr[i] = subArr;
         }
      }

      return colArr;
   }

   private static int readColor(Properties props, String[] names) {
      for (int i = 0; i < names.length; i++) {
         String name = names[i];
         int col = readColor(props, name);
         if (col >= 0) {
            return col;
         }
      }

      return -1;
   }

   private static int readColor(Properties props, String name) {
      String str = props.getProperty(name);
      if (str == null) {
         return -1;
      } else {
         str = str.trim();
         int color = parseColor(str);
         if (color < 0) {
            warn("Invalid color: " + name + " = " + str);
            return color;
         } else {
            dbg(name + " = " + str);
            return color;
         }
      }
   }

   private static int parseColor(String str) {
      if (str == null) {
         return -1;
      } else {
         str = str.trim();

         try {
            return Integer.parseInt(str, 16) & 16777215;
         } catch (NumberFormatException var2) {
            return -1;
         }
      }
   }

   private static C_3046_ readColorVec3(Properties props, String name) {
      int col = readColor(props, name);
      if (col < 0) {
         return null;
      } else {
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         return new C_3046_((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static CustomColormap getCustomColors(String basePath, String[] paths, int width, int height) {
      for (int i = 0; i < paths.length; i++) {
         String path = paths[i];
         path = basePath + path;
         CustomColormap cols = getCustomColors(path, width, height);
         if (cols != null) {
            return cols;
         }
      }

      return null;
   }

   public static CustomColormap getCustomColors(String pathImage, int width, int height) {
      try {
         C_5265_ loc = new C_5265_(pathImage);
         if (!Config.hasResource(loc)) {
            return null;
         } else {
            dbg("Colormap " + pathImage);
            Properties props = new PropertiesOrdered();
            String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
            C_5265_ locProps = new C_5265_(pathProps);
            if (Config.hasResource(locProps)) {
               InputStream in = Config.getResourceStream(locProps);
               props.load(in);
               in.close();
               dbg("Colormap properties: " + pathProps);
            } else {
               props.put("format", paletteFormatDefault);
               props.put("source", pathImage);
               pathProps = pathImage;
            }

            CustomColormap cm = new CustomColormap(props, pathProps, width, height, paletteFormatDefault);
            return !cm.isValid(pathProps) ? null : cm;
         }
      } catch (Exception var8) {
         var8.printStackTrace();
         return null;
      }
   }

   public static void updateUseDefaultGrassFoliageColors() {
      useDefaultGrassFoliageColors = foliageBirchColors == null && foliagePineColors == null && swampGrassColors == null && swampFoliageColors == null;
   }

   public static int getColorMultiplier(C_4196_ quad, C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos, RenderEnv renderEnv) {
      return getColorMultiplier(quad.m_111304_(), blockState, blockAccess, blockPos, renderEnv);
   }

   public static int getColorMultiplier(boolean quadHasTintIndex, C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos, RenderEnv renderEnv) {
      C_1706_ block = blockState.b();
      C_2064_ blockStateColormap = blockState;
      if (blockColormaps != null) {
         if (!quadHasTintIndex) {
            if (block == C_1710_.f_50440_) {
               blockStateColormap = BLOCK_STATE_DIRT;
            }

            if (block == C_1710_.f_50088_) {
               return -1;
            }
         }

         if (block instanceof C_1772_ && blockState.c(C_1772_.f_52858_) == C_2091_.UPPER) {
            blockPos = blockPos.m_7495_();
            blockStateColormap = blockAccess.a_(blockPos);
         }

         CustomColormap cm = getBlockColormap(blockStateColormap);
         if (cm != null) {
            if (Config.isSmoothBiomes() && !cm.isColorConstant()) {
               return getSmoothColorMultiplier(blockState, blockAccess, blockPos, cm, renderEnv.getColorizerBlockPosM());
            }

            return cm.getColor(blockAccess, blockPos);
         }
      }

      if (!quadHasTintIndex) {
         return -1;
      } else if (block == C_1710_.f_50196_) {
         return getLilypadColorMultiplier(blockAccess, blockPos);
      } else if (block == C_1710_.f_50088_) {
         return getRedstoneColor(renderEnv.getBlockState());
      } else if (block instanceof C_1923_) {
         return getStemColorMultiplier(blockState, blockAccess, blockPos, renderEnv);
      } else if (useDefaultGrassFoliageColors) {
         return -1;
      } else {
         CustomColors.IColorizer colorizer;
         if (block == C_1710_.f_50440_ || block instanceof C_1937_ || block instanceof C_1772_ || block == C_1710_.f_50130_) {
            colorizer = COLORIZER_GRASS;
         } else if (block instanceof C_1772_) {
            colorizer = COLORIZER_GRASS;
            if (blockState.c(C_1772_.f_52858_) == C_2091_.UPPER) {
               blockPos = blockPos.m_7495_();
            }
         } else if (block instanceof C_1826_) {
            if (block == C_1710_.f_50051_) {
               colorizer = COLORIZER_FOLIAGE_PINE;
            } else if (block == C_1710_.f_50052_) {
               colorizer = COLORIZER_FOLIAGE_BIRCH;
            } else {
               if (block == C_1710_.f_271115_) {
                  return -1;
               }

               if (!blockState.getBlockLocation().isDefaultNamespace()) {
                  return -1;
               }

               colorizer = COLORIZER_FOLIAGE;
            }
         } else {
            if (block != C_1710_.f_50191_) {
               return -1;
            }

            colorizer = COLORIZER_FOLIAGE;
         }

         return Config.isSmoothBiomes() && !colorizer.isColorConstant()
            ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM())
            : colorizer.getColor(blockStateColormap, blockAccess, blockPos);
      }
   }

   protected static C_1629_ getColorBiome(C_1557_ blockAccess, C_4675_ blockPos) {
      C_1629_ biome = BiomeUtils.getBiome(blockAccess, blockPos);
      return fixBiome(biome);
   }

   public static C_1629_ fixBiome(C_1629_ biome) {
      return (biome == BiomeUtils.SWAMP || biome == BiomeUtils.MANGROVE_SWAMP) && !Config.isSwampColors() ? BiomeUtils.PLAINS : biome;
   }

   private static CustomColormap getBlockColormap(C_2064_ blockState) {
      if (blockColormaps == null) {
         return null;
      } else if (!(blockState instanceof C_2064_)) {
         return null;
      } else {
         C_2064_ bs = blockState;
         int blockId = blockState.getBlockId();
         if (blockId >= 0 && blockId < blockColormaps.length) {
            CustomColormap[] cms = blockColormaps[blockId];
            if (cms == null) {
               return null;
            } else {
               for (int i = 0; i < cms.length; i++) {
                  CustomColormap cm = cms[i];
                  if (cm.matchesBlock(bs)) {
                     return cm;
                  }
               }

               return null;
            }
         } else {
            return null;
         }
      }
   }

   private static int getSmoothColorMultiplier(
      C_2064_ blockState, C_1557_ blockAccess, C_4675_ blockPos, CustomColors.IColorizer colorizer, BlockPosM blockPosM
   ) {
      int sumRed = 0;
      int sumGreen = 0;
      int sumBlue = 0;
      int x = blockPos.u();
      int y = blockPos.v();
      int z = blockPos.w();
      BlockPosM posM = blockPosM;
      int radius = Config.getBiomeBlendRadius();
      int width = radius * 2 + 1;
      int count = width * width;

      for (int ix = x - radius; ix <= x + radius; ix++) {
         for (int iz = z - radius; iz <= z + radius; iz++) {
            posM.setXyz(ix, y, iz);
            int col = colorizer.getColor(blockState, blockAccess, posM);
            sumRed += col >> 16 & 0xFF;
            sumGreen += col >> 8 & 0xFF;
            sumBlue += col & 0xFF;
         }
      }

      int r = sumRed / count;
      int g = sumGreen / count;
      int b = sumBlue / count;
      return r << 16 | g << 8 | b;
   }

   public static int getFluidColor(C_1557_ blockAccess, C_2064_ blockState, C_4675_ blockPos, RenderEnv renderEnv) {
      C_1706_ block = blockState.b();
      CustomColors.IColorizer colorizer = getBlockColormap(blockState);
      if (colorizer == null && blockState.b() == C_1710_.f_49990_) {
         colorizer = COLORIZER_WATER;
      }

      if (colorizer == null) {
         return getBlockColors().m_92577_(blockState, blockAccess, blockPos, 0);
      } else {
         return Config.isSmoothBiomes() && !colorizer.isColorConstant()
            ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM())
            : colorizer.getColor(blockState, blockAccess, blockPos);
      }
   }

   public static C_3423_ getBlockColors() {
      return C_3391_.m_91087_().m_91298_();
   }

   public static void updatePortalFX(C_4022_ fx) {
      if (particlePortalColor >= 0) {
         int col = particlePortalColor;
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   public static void updateLavaFX(C_4022_ fx) {
      if (lavaDropColors != null) {
         int age = fx.getAge();
         int col = lavaDropColors.getColor(age);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   public static void updateMyceliumFX(C_4022_ fx) {
      if (myceliumParticleColors != null) {
         int col = myceliumParticleColors.getColorRandom();
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   private static int getRedstoneColor(C_2064_ blockState) {
      if (redstoneColors == null) {
         return -1;
      } else {
         int level = getRedstoneLevel(blockState, 15);
         return redstoneColors.getColor(level);
      }
   }

   public static void updateReddustFX(C_4022_ fx, C_1557_ blockAccess, double x, double y, double z) {
      if (redstoneColors != null) {
         C_2064_ state = blockAccess.a_(C_4675_.m_274561_(x, y, z));
         int level = getRedstoneLevel(state, 15);
         int col = redstoneColors.getColor(level);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   private static int getRedstoneLevel(C_2064_ state, int def) {
      C_1706_ block = state.b();
      if (!(block instanceof C_1873_)) {
         return def;
      } else {
         return !(state.c(C_1873_.f_55500_) instanceof Integer valInt) ? def : valInt;
      }
   }

   public static float getXpOrbTimer(float timer) {
      if (xpOrbTime <= 0) {
         return timer;
      } else {
         float kt = 628.0F / (float)xpOrbTime;
         return timer * kt;
      }
   }

   public static int getXpOrbColor(float timer) {
      if (xpOrbColors == null) {
         return -1;
      } else {
         int index = (int)Math.round((double)((C_188_.m_14031_(timer) + 1.0F) * (float)(xpOrbColors.getLength() - 1)) / 2.0);
         return xpOrbColors.getColor(index);
      }
   }

   public static int getDurabilityColor(float dur, int color) {
      if (durabilityColors == null) {
         return color;
      } else {
         int index = (int)(dur * (float)durabilityColors.getLength());
         return durabilityColors.getColor(index);
      }
   }

   public static void updateWaterFX(C_4022_ fx, C_1557_ blockAccess, double x, double y, double z, RenderEnv renderEnv) {
      if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
         C_4675_ blockPos = C_4675_.m_274561_(x, y, z);
         renderEnv.reset(BLOCK_STATE_WATER, blockPos);
         int col = getFluidColor(blockAccess, BLOCK_STATE_WATER, blockPos, renderEnv);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         if (particleWaterColor >= 0) {
            int redDrop = particleWaterColor >> 16 & 0xFF;
            int greenDrop = particleWaterColor >> 8 & 0xFF;
            int blueDrop = particleWaterColor & 0xFF;
            redF = (float)redDrop / 255.0F;
            greenF = (float)greenDrop / 255.0F;
            blueF = (float)blueDrop / 255.0F;
            redF *= (float)redDrop / 255.0F;
            greenF *= (float)greenDrop / 255.0F;
            blueF *= (float)blueDrop / 255.0F;
         }

         fx.m_107253_(redF, greenF, blueF);
      }
   }

   private static int getLilypadColorMultiplier(C_1557_ blockAccess, C_4675_ blockPos) {
      return lilyPadColor < 0 ? getBlockColors().m_92577_(C_1710_.f_50196_.m_49966_(), blockAccess, blockPos, 0) : lilyPadColor;
   }

   private static C_3046_ getFogColorNether(C_3046_ col) {
      return fogColorNether == null ? col : fogColorNether;
   }

   private static C_3046_ getFogColorEnd(C_3046_ col) {
      return fogColorEnd == null ? col : fogColorEnd;
   }

   private static C_3046_ getSkyColorEnd(C_3046_ col) {
      return skyColorEnd == null ? col : skyColorEnd;
   }

   public static C_3046_ getSkyColor(C_3046_ skyColor3d, C_1557_ blockAccess, double x, double y, double z) {
      if (skyColors == null) {
         return skyColor3d;
      } else {
         int col = skyColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         float cRed = (float)skyColor3d.f_82479_ / 0.5F;
         float cGreen = (float)skyColor3d.f_82480_ / 0.66275F;
         float cBlue = (float)skyColor3d.f_82481_;
         redF *= cRed;
         greenF *= cGreen;
         blueF *= cBlue;
         return skyColorFader.getColor((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static C_3046_ getFogColor(C_3046_ fogColor3d, C_1557_ blockAccess, double x, double y, double z) {
      if (fogColors == null) {
         return fogColor3d;
      } else {
         int col = fogColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         float cRed = (float)fogColor3d.f_82479_ / 0.753F;
         float cGreen = (float)fogColor3d.f_82480_ / 0.8471F;
         float cBlue = (float)fogColor3d.f_82481_;
         redF *= cRed;
         greenF *= cGreen;
         blueF *= cBlue;
         return fogColorFader.getColor((double)redF, (double)greenF, (double)blueF);
      }
   }

   public static C_3046_ getUnderwaterColor(C_1557_ blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
   }

   public static C_3046_ getUnderlavaColor(C_1557_ blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
   }

   public static C_3046_ getUnderFluidColor(
      C_1557_ blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader
   ) {
      if (underFluidColors == null) {
         return null;
      } else {
         int col = underFluidColors.getColorSmooth(blockAccess, x, y, z, 3);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         return underFluidColorFader.getColor((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static int getStemColorMultiplier(C_2064_ blockState, C_1559_ blockAccess, C_4675_ blockPos, RenderEnv renderEnv) {
      CustomColormap colors = stemColors;
      C_1706_ blockStem = blockState.b();
      if (blockStem == C_1710_.f_50189_ && stemPumpkinColors != null) {
         colors = stemPumpkinColors;
      }

      if (blockStem == C_1710_.f_50190_ && stemMelonColors != null) {
         colors = stemMelonColors;
      }

      if (colors == null) {
         return -1;
      } else if (!(blockStem instanceof C_1923_)) {
         return -1;
      } else {
         int level = (Integer)blockState.c(C_1923_.f_57013_);
         return colors.getColor(level);
      }
   }

   public static boolean updateLightmap(C_3899_ world, float torchFlickerX, C_3148_ lmColors, boolean nightvision, float darkLight, float partialTicks) {
      if (world == null) {
         return false;
      } else if (lightMapPacks == null) {
         return false;
      } else {
         int dimensionId = WorldUtils.getDimensionId(world);
         int lightMapIndex = dimensionId - lightmapMinDimensionId;
         if (lightMapIndex >= 0 && lightMapIndex < lightMapPacks.length) {
            LightMapPack lightMapPack = lightMapPacks[lightMapIndex];
            return lightMapPack == null ? false : lightMapPack.updateLightmap(world, torchFlickerX, lmColors, nightvision, darkLight, partialTicks);
         } else {
            return false;
         }
      }
   }

   public static C_3046_ getWorldFogColor(C_3046_ fogVec, C_1596_ world, C_507_ renderViewEntity, float partialTicks) {
      C_3391_ mc = C_3391_.m_91087_();
      if (WorldUtils.isNether(world)) {
         return getFogColorNether(fogVec);
      } else if (WorldUtils.isOverworld(world)) {
         return getFogColor(fogVec, mc.f_91073_, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_());
      } else {
         return WorldUtils.isEnd(world) ? getFogColorEnd(fogVec) : fogVec;
      }
   }

   public static C_3046_ getWorldSkyColor(C_3046_ skyVec, C_1596_ world, C_507_ renderViewEntity, float partialTicks) {
      C_3391_ mc = C_3391_.m_91087_();
      if (WorldUtils.isOverworld(world) && renderViewEntity != null) {
         return getSkyColor(skyVec, mc.f_91073_, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_());
      } else {
         return WorldUtils.isEnd(world) ? getSkyColorEnd(skyVec) : skyVec;
      }
   }

   private static int[] readSpawnEggColors(Properties props, String fileName, String prefix, String logName) {
      List<Integer> list = new ArrayList();
      Set keys = props.keySet();
      int countColors = 0;

      for (String key : keys) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            String name = StrUtils.removePrefix(key, prefix);
            int id = EntityUtils.getEntityIdByName(name);

            try {
               if (id < 0) {
                  id = EntityUtils.getEntityIdByLocation(new C_5265_(name).toString());
               }
            } catch (C_5250_ var13) {
               Config.warn("ResourceLocationException: " + var13.getMessage());
            }

            if (id < 0) {
               warn("Invalid spawn egg name: " + key);
            } else {
               int color = parseColor(value);
               if (color < 0) {
                  warn("Invalid spawn egg color: " + key + " = " + value);
               } else {
                  while (list.size() <= id) {
                     list.add(-1);
                  }

                  list.set(id, color);
                  countColors++;
               }
            }
         }
      }

      if (countColors <= 0) {
         return null;
      } else {
         dbg(logName + " colors: " + countColors);
         int[] colors = new int[list.size()];

         for (int i = 0; i < colors.length; i++) {
            colors[i] = (Integer)list.get(i);
         }

         return colors;
      }
   }

   private static int getSpawnEggColor(C_1420_ item, C_1391_ itemStack, int layer, int color) {
      if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null) {
         return color;
      } else {
         C_513_ entityType = item.m_43228_(itemStack);
         if (entityType == null) {
            return color;
         } else {
            int id = C_256712_.f_256780_.a(entityType);
            if (id < 0) {
               return color;
            } else {
               int[] eggColors = layer == 0 ? spawnEggPrimaryColors : spawnEggSecondaryColors;
               if (eggColors == null) {
                  return color;
               } else if (id >= 0 && id < eggColors.length) {
                  int eggColor = eggColors[id];
                  return eggColor < 0 ? color : eggColor;
               } else {
                  return color;
               }
            }
         }
      }
   }

   public static int getColorFromItemStack(C_1391_ itemStack, int layer, int color) {
      if (itemStack == null) {
         return color;
      } else {
         C_1381_ item = itemStack.m_41720_();
         if (item == null) {
            return color;
         } else if (item instanceof C_1420_) {
            return getSpawnEggColor((C_1420_)item, itemStack, layer, color);
         } else {
            return item == C_1394_.f_42094_ && lilyPadColor != -1 ? lilyPadColor : color;
         }
      }
   }

   private static int[] readDyeColors(Properties props, String fileName, String prefix, String logName) {
      C_1353_[] dyeValues = C_1353_.values();
      Map<String, C_1353_> mapDyes = new HashMap();

      for (int i = 0; i < dyeValues.length; i++) {
         C_1353_ dye = dyeValues[i];
         mapDyes.put(dye.m_7912_(), dye);
      }

      mapDyes.put("lightBlue", C_1353_.LIGHT_BLUE);
      mapDyes.put("silver", C_1353_.LIGHT_GRAY);
      int[] colors = new int[dyeValues.length];
      int countColors = 0;

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            String name = StrUtils.removePrefix(key, prefix);
            C_1353_ dye = (C_1353_)mapDyes.get(name);
            int color = parseColor(value);
            if (dye != null && color >= 0) {
               int argb = C_175_.m_321570_(color);
               colors[dye.ordinal()] = argb;
               countColors++;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }

      if (countColors <= 0) {
         return null;
      } else {
         dbg(logName + " colors: " + countColors);
         return colors;
      }
   }

   private static int getDyeColors(C_1353_ dye, int[] dyeColors, int color) {
      if (dyeColors == null) {
         return color;
      } else if (dye == null) {
         return color;
      } else {
         int customColor = dyeColors[dye.ordinal()];
         return customColor == 0 ? color : customColor;
      }
   }

   public static int getWolfCollarColors(C_1353_ dye, int color) {
      return getDyeColors(dye, wolfCollarColors, color);
   }

   public static int getSheepColors(C_1353_ dye, int color) {
      return getDyeColors(dye, sheepColors, color);
   }

   private static int[] readTextColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[32];
      Arrays.fill(colors, -1);
      int countColors = 0;

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            String name = StrUtils.removePrefix(key, prefix);
            int code = Config.parseInt(name, -1);
            int color = parseColor(value);
            if (code >= 0 && code < colors.length && color >= 0) {
               colors[code] = color;
               countColors++;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }

      if (countColors <= 0) {
         return null;
      } else {
         dbg(logName + " colors: " + countColors);
         return colors;
      }
   }

   public static int getTextColor(int index, int color) {
      if (textColors == null) {
         return color;
      } else if (index >= 0 && index < textColors.length) {
         int customColor = textColors[index];
         return customColor < 0 ? color : customColor;
      } else {
         return color;
      }
   }

   private static int[] readMapColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[C_283734_.f_283862_.length];
      Arrays.fill(colors, -1);
      int countColors = 0;

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            String name = StrUtils.removePrefix(key, prefix);
            int index = getMapColorIndex(name);
            int color = parseColor(value);
            if (index >= 0 && index < colors.length && color >= 0) {
               colors[index] = color;
               countColors++;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }

      if (countColors <= 0) {
         return null;
      } else {
         dbg(logName + " colors: " + countColors);
         return colors;
      }
   }

   private static int[] readPotionColors(Properties props, String fileName, String prefix, String logName) {
      int[] colors = new int[getMaxPotionId()];
      Arrays.fill(colors, -1);
      int countColors = 0;

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            int index = getPotionId(key);
            int color = parseColor(value);
            if (index >= 0 && index < colors.length && color >= 0) {
               colors[index] = color;
               countColors++;
            } else {
               warn("Invalid color: " + key + " = " + value);
            }
         }
      }

      if (countColors <= 0) {
         return null;
      } else {
         dbg(logName + " colors: " + countColors);
         return colors;
      }
   }

   private static int getMaxPotionId() {
      int maxId = 0;

      for (C_5265_ rl : C_256712_.f_256974_.m_6566_()) {
         C_496_ potion = PotionUtils.getPotion(rl);
         int id = PotionUtils.getId(potion);
         if (id > maxId) {
            maxId = id;
         }
      }

      return maxId;
   }

   private static int getPotionId(String name) {
      if (name.equals("potion.water")) {
         return 0;
      } else {
         name = StrUtils.replacePrefix(name, "potion.", "effect.");
         String nameMc = StrUtils.replacePrefix(name, "effect.", "effect.minecraft.");

         for (C_5265_ rl : C_256712_.f_256974_.m_6566_()) {
            C_496_ potion = PotionUtils.getPotion(rl);
            if (potion.m_19481_().equals(name)) {
               return PotionUtils.getId(potion);
            }

            if (potion.m_19481_().equals(nameMc)) {
               return PotionUtils.getId(potion);
            }
         }

         return -1;
      }
   }

   public static int getPotionColor(C_496_ potion, int color) {
      int potionId = 0;
      if (potion != null) {
         potionId = PotionUtils.getId(potion);
      }

      return getPotionColor(potionId, color);
   }

   public static int getPotionColor(int potionId, int color) {
      if (potionColors == null) {
         return color;
      } else if (potionId >= 0 && potionId < potionColors.length) {
         int potionColor = potionColors[potionId];
         return potionColor < 0 ? color : potionColor;
      } else {
         return color;
      }
   }

   private static int getMapColorIndex(String name) {
      if (name == null) {
         return -1;
      } else if (name.equals("air")) {
         return C_283734_.f_283808_.f_283805_;
      } else if (name.equals("grass")) {
         return C_283734_.f_283824_.f_283805_;
      } else if (name.equals("sand")) {
         return C_283734_.f_283761_.f_283805_;
      } else if (name.equals("cloth")) {
         return C_283734_.f_283930_.f_283805_;
      } else if (name.equals("tnt")) {
         return C_283734_.f_283816_.f_283805_;
      } else if (name.equals("ice")) {
         return C_283734_.f_283828_.f_283805_;
      } else if (name.equals("iron")) {
         return C_283734_.f_283906_.f_283805_;
      } else if (name.equals("foliage")) {
         return C_283734_.f_283915_.f_283805_;
      } else if (name.equals("clay")) {
         return C_283734_.f_283744_.f_283805_;
      } else if (name.equals("dirt")) {
         return C_283734_.f_283762_.f_283805_;
      } else if (name.equals("stone")) {
         return C_283734_.f_283947_.f_283805_;
      } else if (name.equals("water")) {
         return C_283734_.f_283864_.f_283805_;
      } else if (name.equals("wood")) {
         return C_283734_.f_283825_.f_283805_;
      } else if (name.equals("quartz")) {
         return C_283734_.f_283942_.f_283805_;
      } else if (name.equals("gold")) {
         return C_283734_.f_283757_.f_283805_;
      } else if (name.equals("diamond")) {
         return C_283734_.f_283821_.f_283805_;
      } else if (name.equals("lapis")) {
         return C_283734_.f_283933_.f_283805_;
      } else if (name.equals("emerald")) {
         return C_283734_.f_283812_.f_283805_;
      } else if (name.equals("podzol")) {
         return C_283734_.f_283819_.f_283805_;
      } else if (name.equals("netherrack")) {
         return C_283734_.f_283820_.f_283805_;
      } else if (name.equals("snow") || name.equals("white")) {
         return C_283734_.f_283811_.f_283805_;
      } else if (name.equals("adobe") || name.equals("orange")) {
         return C_283734_.f_283750_.f_283805_;
      } else if (name.equals("magenta")) {
         return C_283734_.f_283931_.f_283805_;
      } else if (name.equals("light_blue") || name.equals("lightBlue")) {
         return C_283734_.f_283869_.f_283805_;
      } else if (name.equals("yellow")) {
         return C_283734_.f_283832_.f_283805_;
      } else if (name.equals("lime")) {
         return C_283734_.f_283916_.f_283805_;
      } else if (name.equals("pink")) {
         return C_283734_.f_283765_.f_283805_;
      } else if (name.equals("gray")) {
         return C_283734_.f_283818_.f_283805_;
      } else if (name.equals("silver") || name.equals("light_gray")) {
         return C_283734_.f_283779_.f_283805_;
      } else if (name.equals("cyan")) {
         return C_283734_.f_283772_.f_283805_;
      } else if (name.equals("purple")) {
         return C_283734_.f_283889_.f_283805_;
      } else if (name.equals("blue")) {
         return C_283734_.f_283743_.f_283805_;
      } else if (name.equals("brown")) {
         return C_283734_.f_283748_.f_283805_;
      } else if (name.equals("green")) {
         return C_283734_.f_283784_.f_283805_;
      } else if (name.equals("red")) {
         return C_283734_.f_283913_.f_283805_;
      } else if (name.equals("black")) {
         return C_283734_.f_283927_.f_283805_;
      } else if (name.equals("white_terracotta")) {
         return C_283734_.f_283919_.f_283805_;
      } else if (name.equals("orange_terracotta")) {
         return C_283734_.f_283895_.f_283805_;
      } else if (name.equals("magenta_terracotta")) {
         return C_283734_.f_283850_.f_283805_;
      } else if (name.equals("light_blue_terracotta")) {
         return C_283734_.f_283791_.f_283805_;
      } else if (name.equals("yellow_terracotta")) {
         return C_283734_.f_283843_.f_283805_;
      } else if (name.equals("lime_terracotta")) {
         return C_283734_.f_283778_.f_283805_;
      } else if (name.equals("pink_terracotta")) {
         return C_283734_.f_283870_.f_283805_;
      } else if (name.equals("gray_terracotta")) {
         return C_283734_.f_283861_.f_283805_;
      } else if (name.equals("light_gray_terracotta")) {
         return C_283734_.f_283907_.f_283805_;
      } else if (name.equals("cyan_terracotta")) {
         return C_283734_.f_283846_.f_283805_;
      } else if (name.equals("purple_terracotta")) {
         return C_283734_.f_283892_.f_283805_;
      } else if (name.equals("blue_terracotta")) {
         return C_283734_.f_283908_.f_283805_;
      } else if (name.equals("brown_terracotta")) {
         return C_283734_.f_283774_.f_283805_;
      } else if (name.equals("green_terracotta")) {
         return C_283734_.f_283856_.f_283805_;
      } else if (name.equals("red_terracotta")) {
         return C_283734_.f_283798_.f_283805_;
      } else if (name.equals("black_terracotta")) {
         return C_283734_.f_283771_.f_283805_;
      } else if (name.equals("crimson_nylium")) {
         return C_283734_.f_283909_.f_283805_;
      } else if (name.equals("crimson_stem")) {
         return C_283734_.f_283804_.f_283805_;
      } else if (name.equals("crimson_hyphae")) {
         return C_283734_.f_283883_.f_283805_;
      } else if (name.equals("warped_nylium")) {
         return C_283734_.f_283745_.f_283805_;
      } else if (name.equals("warped_stem")) {
         return C_283734_.f_283749_.f_283805_;
      } else if (name.equals("warped_hyphae")) {
         return C_283734_.f_283807_.f_283805_;
      } else if (name.equals("warped_wart_block")) {
         return C_283734_.f_283898_.f_283805_;
      } else if (name.equals("deepslate")) {
         return C_283734_.f_283875_.f_283805_;
      } else if (name.equals("raw_iron")) {
         return C_283734_.f_283877_.f_283805_;
      } else {
         return name.equals("glow_lichen") ? C_283734_.f_283769_.f_283805_ : -1;
      }
   }

   private static int[] getMapColors() {
      C_283734_[] mapColors = C_283734_.f_283862_;
      int[] colors = new int[mapColors.length];
      Arrays.fill(colors, -1);

      for (int i = 0; i < mapColors.length && i < colors.length; i++) {
         C_283734_ mapColor = mapColors[i];
         if (mapColor != null) {
            colors[i] = mapColor.f_283871_;
         }
      }

      return colors;
   }

   private static void setMapColors(int[] colors) {
      if (colors != null) {
         C_283734_[] mapColors = C_283734_.f_283862_;

         for (int i = 0; i < mapColors.length && i < colors.length; i++) {
            C_283734_ mapColor = mapColors[i];
            if (mapColor != null) {
               int color = colors[i];
               if (color >= 0 && mapColor.f_283871_ != color) {
                  mapColor.f_283871_ = color;
               }
            }
         }
      }
   }

   private static int[] getDyeColors() {
      C_1353_[] dyeColors = C_1353_.values();
      int[] colors = new int[dyeColors.length];

      for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
         C_1353_ dyeColor = dyeColors[i];
         if (dyeColor != null) {
            colors[i] = dyeColor.m_340318_();
         }
      }

      return colors;
   }

   private static void setDyeColors(int[] colors) {
      if (colors != null) {
         C_1353_[] dyeColors = C_1353_.values();

         for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
            C_1353_ dyeColor = dyeColors[i];
            if (dyeColor != null) {
               int color = colors[i];
               if (color != 0 && dyeColor.m_340318_() != color) {
                  dyeColor.setTextureDiffuseColor(color);
               }
            }
         }
      }
   }

   private static void dbg(String str) {
      Config.dbg("CustomColors: " + str);
   }

   private static void warn(String str) {
      Config.warn("CustomColors: " + str);
   }

   public static int getExpBarTextColor(int color) {
      return expBarTextColor < 0 ? color : expBarTextColor;
   }

   public static int getBossTextColor(int color) {
      return bossTextColor < 0 ? color : bossTextColor;
   }

   public static int getSignTextColor(int color) {
      if (color != 0) {
         return color;
      } else {
         return signTextColor < 0 ? color : signTextColor;
      }
   }

   public interface IColorizer {
      int getColor(C_2064_ var1, C_1557_ var2, C_4675_ var3);

      boolean isColorConstant();
   }
}
