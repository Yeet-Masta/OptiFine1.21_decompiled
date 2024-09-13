package net.optifine;

import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.ResourceLocationException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.Vec3;
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
   private static Vec3 fogColorNether = null;
   private static Vec3 fogColorEnd = null;
   private static Vec3 skyColorEnd = null;
   private static int[] spawnEggPrimaryColors = null;
   private static int[] spawnEggSecondaryColors = null;
   private static int[] wolfCollarColors = null;
   private static int[] sheepColors = null;
   private static int[] textColors = null;
   private static int[] mapColorsOriginal = null;
   private static int[] dyeColorsOriginal = null;
   private static int[] potionColors = null;
   private static BlockState BLOCK_STATE_DIRT = Blocks.f_50493_.m_49966_();
   private static BlockState BLOCK_STATE_WATER = Blocks.f_49990_.m_49966_();
   public static Random random = new Random();
   private static CustomColors.IColorizer COLORIZER_GRASS = new CustomColors.IColorizer() {
      @Override
      public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
         Biome biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.swampGrassColors != null && biome == BiomeUtils.SWAMP
            ? CustomColors.swampGrassColors.m_130045_(biome, blockPos)
            : biome.m_47464_((double)blockPos.m_123341_(), (double)blockPos.m_123343_());
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static CustomColors.IColorizer COLORIZER_FOLIAGE = new CustomColors.IColorizer() {
      @Override
      public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
         Biome biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.swampFoliageColors != null && biome == BiomeUtils.SWAMP
            ? CustomColors.swampFoliageColors.m_130045_(biome, blockPos)
            : biome.m_47542_();
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };
   private static CustomColors.IColorizer COLORIZER_FOLIAGE_PINE = new CustomColors.IColorizer() {
      @Override
      public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
         return CustomColors.foliagePineColors != null ? CustomColors.foliagePineColors.m_130045_(blockAccess, blockPos) : FoliageColor.m_46106_();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.foliagePineColors == null;
      }
   };
   private static CustomColors.IColorizer COLORIZER_FOLIAGE_BIRCH = new CustomColors.IColorizer() {
      @Override
      public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
         return CustomColors.foliageBirchColors != null ? CustomColors.foliageBirchColors.m_130045_(blockAccess, blockPos) : FoliageColor.m_46112_();
      }

      @Override
      public boolean isColorConstant() {
         return CustomColors.foliageBirchColors == null;
      }
   };
   private static CustomColors.IColorizer COLORIZER_WATER = new CustomColors.IColorizer() {
      @Override
      public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
         Biome biome = CustomColors.getColorBiome(blockAccess, blockPos);
         return CustomColors.waterColors != null ? CustomColors.waterColors.m_130045_(biome, blockPos) : biome.m_47560_();
      }

      @Override
      public boolean isColorConstant() {
         return false;
      }
   };

   public static void m_252999_() {
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
         ResourceLocation loc = new ResourceLocation(fileName);
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
               if (!listValidValues.m_274455_(val)) {
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
      Arrays.m_277065_(dimIds);
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
               if (colors.m_92515_() < 16) {
                  warn("Invalid lightmap width: " + colors.m_92515_() + ", path: " + path);
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
         InputStream in = Config.getResourceStream(new ResourceLocation(path));
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
         ResourceLocation loc = new ResourceLocation(fileName);
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
      Arrays.m_277065_(paths);
      List blockList = new ArrayList();

      for (int i = 0; i < paths.length; i++) {
         String path = paths[i];
         dbg("Block colormap: " + path);

         try {
            ResourceLocation locFile = new ResourceLocation("minecraft", path);
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

   private static Vec3 readColorVec3(Properties props, String name) {
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
         return new Vec3((double)redF, (double)greenF, (double)blueF);
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
         ResourceLocation loc = new ResourceLocation(pathImage);
         if (!Config.hasResource(loc)) {
            return null;
         } else {
            dbg("Colormap " + pathImage);
            Properties props = new PropertiesOrdered();
            String pathProps = StrUtils.replaceSuffix(pathImage, ".png", ".properties");
            ResourceLocation locProps = new ResourceLocation(pathProps);
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

   public static int getColorMultiplier(BakedQuad quad, BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
      return getColorMultiplier(quad.m_111304_(), blockState, blockAccess, blockPos, renderEnv);
   }

   public static int getColorMultiplier(boolean quadHasTintIndex, BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
      Block block = blockState.m_60734_();
      BlockState blockStateColormap = blockState;
      if (blockColormaps != null) {
         if (!quadHasTintIndex) {
            if (block == Blocks.f_50440_) {
               blockStateColormap = BLOCK_STATE_DIRT;
            }

            if (block == Blocks.f_50088_) {
               return -1;
            }
         }

         if (block instanceof DoublePlantBlock && blockState.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.UPPER) {
            blockPos = blockPos.m_7495_();
            blockStateColormap = blockAccess.m_8055_(blockPos);
         }

         CustomColormap cm = getBlockColormap(blockStateColormap);
         if (cm != null) {
            if (Config.isSmoothBiomes() && !cm.isColorConstant()) {
               return getSmoothColorMultiplier(blockState, blockAccess, blockPos, cm, renderEnv.getColorizerBlockPosM());
            }

            return cm.m_130045_(blockAccess, blockPos);
         }
      }

      if (!quadHasTintIndex) {
         return -1;
      } else if (block == Blocks.f_50196_) {
         return getLilypadColorMultiplier(blockAccess, blockPos);
      } else if (block == Blocks.f_50088_) {
         return getRedstoneColor(renderEnv.getBlockState());
      } else if (block instanceof StemBlock) {
         return getStemColorMultiplier(blockState, blockAccess, blockPos, renderEnv);
      } else if (useDefaultGrassFoliageColors) {
         return -1;
      } else {
         CustomColors.IColorizer colorizer;
         if (block == Blocks.f_50440_ || block instanceof TallGrassBlock || block instanceof DoublePlantBlock || block == Blocks.f_50130_) {
            colorizer = COLORIZER_GRASS;
         } else if (block instanceof DoublePlantBlock) {
            colorizer = COLORIZER_GRASS;
            if (blockState.m_61143_(DoublePlantBlock.f_52858_) == DoubleBlockHalf.UPPER) {
               blockPos = blockPos.m_7495_();
            }
         } else if (block instanceof LeavesBlock) {
            if (block == Blocks.f_50051_) {
               colorizer = COLORIZER_FOLIAGE_PINE;
            } else if (block == Blocks.f_50052_) {
               colorizer = COLORIZER_FOLIAGE_BIRCH;
            } else {
               if (block == Blocks.f_271115_) {
                  return -1;
               }

               if (!blockState.getBlockLocation().isDefaultNamespace()) {
                  return -1;
               }

               colorizer = COLORIZER_FOLIAGE;
            }
         } else {
            if (block != Blocks.f_50191_) {
               return -1;
            }

            colorizer = COLORIZER_FOLIAGE;
         }

         return Config.isSmoothBiomes() && !colorizer.isColorConstant()
            ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM())
            : colorizer.m_130045_(blockStateColormap, blockAccess, blockPos);
      }
   }

   protected static Biome getColorBiome(BlockAndTintGetter blockAccess, BlockPos blockPos) {
      Biome biome = BiomeUtils.getBiome(blockAccess, blockPos);
      return fixBiome(biome);
   }

   public static Biome fixBiome(Biome biome) {
      return (biome == BiomeUtils.SWAMP || biome == BiomeUtils.MANGROVE_SWAMP) && !Config.isSwampColors() ? BiomeUtils.PLAINS : biome;
   }

   private static CustomColormap getBlockColormap(BlockState blockState) {
      if (blockColormaps == null) {
         return null;
      } else if (!(blockState instanceof BlockState)) {
         return null;
      } else {
         BlockState bs = blockState;
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
      BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos, CustomColors.IColorizer colorizer, BlockPosM blockPosM
   ) {
      int sumRed = 0;
      int sumGreen = 0;
      int sumBlue = 0;
      int x = blockPos.m_123341_();
      int y = blockPos.m_123342_();
      int z = blockPos.m_123343_();
      BlockPosM posM = blockPosM;
      int radius = Config.getBiomeBlendRadius();
      int width = radius * 2 + 1;
      int count = width * width;

      for (int ix = x - radius; ix <= x + radius; ix++) {
         for (int iz = z - radius; iz <= z + radius; iz++) {
            posM.setXyz(ix, y, iz);
            int col = colorizer.m_130045_(blockState, blockAccess, posM);
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

   public static int getFluidColor(BlockAndTintGetter blockAccess, BlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
      Block block = blockState.m_60734_();
      CustomColors.IColorizer colorizer = getBlockColormap(blockState);
      if (colorizer == null && blockState.m_60734_() == Blocks.f_49990_) {
         colorizer = COLORIZER_WATER;
      }

      if (colorizer == null) {
         return getBlockColors().m_92577_(blockState, blockAccess, blockPos, 0);
      } else {
         return Config.isSmoothBiomes() && !colorizer.isColorConstant()
            ? getSmoothColorMultiplier(blockState, blockAccess, blockPos, colorizer, renderEnv.getColorizerBlockPosM())
            : colorizer.m_130045_(blockState, blockAccess, blockPos);
      }
   }

   public static BlockColors getBlockColors() {
      return Minecraft.m_91087_().m_91298_();
   }

   public static void updatePortalFX(Particle fx) {
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

   public static void updateLavaFX(Particle fx) {
      if (lavaDropColors != null) {
         int age = fx.getAge();
         int col = lavaDropColors.m_130045_(age);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   public static void updateMyceliumFX(Particle fx) {
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

   private static int getRedstoneColor(BlockState blockState) {
      if (redstoneColors == null) {
         return -1;
      } else {
         int level = getRedstoneLevel(blockState, 15);
         return redstoneColors.m_130045_(level);
      }
   }

   public static void updateReddustFX(Particle fx, BlockAndTintGetter blockAccess, double x, double y, double z) {
      if (redstoneColors != null) {
         BlockState state = blockAccess.m_8055_(BlockPos.m_274561_(x, y, z));
         int level = getRedstoneLevel(state, 15);
         int col = redstoneColors.m_130045_(level);
         int red = col >> 16 & 0xFF;
         int green = col >> 8 & 0xFF;
         int blue = col & 0xFF;
         float redF = (float)red / 255.0F;
         float greenF = (float)green / 255.0F;
         float blueF = (float)blue / 255.0F;
         fx.m_107253_(redF, greenF, blueF);
      }
   }

   private static int getRedstoneLevel(BlockState state, int def) {
      Block block = state.m_60734_();
      if (!(block instanceof RedStoneWireBlock)) {
         return def;
      } else {
         return !(state.m_61143_(RedStoneWireBlock.f_55500_) instanceof Integer valInt) ? def : valInt;
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
         int index = (int)Math.round((double)((Mth.m_14031_(timer) + 1.0F) * (float)(xpOrbColors.getLength() - 1)) / 2.0);
         return xpOrbColors.m_130045_(index);
      }
   }

   public static int getDurabilityColor(float dur, int color) {
      if (durabilityColors == null) {
         return color;
      } else {
         int index = (int)(dur * (float)durabilityColors.getLength());
         return durabilityColors.m_130045_(index);
      }
   }

   public static void updateWaterFX(Particle fx, BlockAndTintGetter blockAccess, double x, double y, double z, RenderEnv renderEnv) {
      if (waterColors != null || blockColormaps != null || particleWaterColor >= 0) {
         BlockPos blockPos = BlockPos.m_274561_(x, y, z);
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

   private static int getLilypadColorMultiplier(BlockAndTintGetter blockAccess, BlockPos blockPos) {
      return lilyPadColor < 0 ? getBlockColors().m_92577_(Blocks.f_50196_.m_49966_(), blockAccess, blockPos, 0) : lilyPadColor;
   }

   private static Vec3 getFogColorNether(Vec3 col) {
      return fogColorNether == null ? col : fogColorNether;
   }

   private static Vec3 getFogColorEnd(Vec3 col) {
      return fogColorEnd == null ? col : fogColorEnd;
   }

   private static Vec3 getSkyColorEnd(Vec3 col) {
      return skyColorEnd == null ? col : skyColorEnd;
   }

   public static Vec3 getSkyColor(Vec3 skyColor3d, BlockAndTintGetter blockAccess, double x, double y, double z) {
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
         return skyColorFader.m_130045_((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static Vec3 getFogColor(Vec3 fogColor3d, BlockAndTintGetter blockAccess, double x, double y, double z) {
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
         return fogColorFader.m_130045_((double)redF, (double)greenF, (double)blueF);
      }
   }

   public static Vec3 getUnderwaterColor(BlockAndTintGetter blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underwaterColors, underwaterColorFader);
   }

   public static Vec3 getUnderlavaColor(BlockAndTintGetter blockAccess, double x, double y, double z) {
      return getUnderFluidColor(blockAccess, x, y, z, underlavaColors, underlavaColorFader);
   }

   public static Vec3 getUnderFluidColor(
      BlockAndTintGetter blockAccess, double x, double y, double z, CustomColormap underFluidColors, CustomColorFader underFluidColorFader
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
         return underFluidColorFader.m_130045_((double)redF, (double)greenF, (double)blueF);
      }
   }

   private static int getStemColorMultiplier(BlockState blockState, BlockGetter blockAccess, BlockPos blockPos, RenderEnv renderEnv) {
      CustomColormap colors = stemColors;
      Block blockStem = blockState.m_60734_();
      if (blockStem == Blocks.f_50189_ && stemPumpkinColors != null) {
         colors = stemPumpkinColors;
      }

      if (blockStem == Blocks.f_50190_ && stemMelonColors != null) {
         colors = stemMelonColors;
      }

      if (colors == null) {
         return -1;
      } else if (!(blockStem instanceof StemBlock)) {
         return -1;
      } else {
         int level = (Integer)blockState.m_61143_(StemBlock.f_57013_);
         return colors.m_130045_(level);
      }
   }

   public static boolean updateLightmap(ClientLevel world, float torchFlickerX, NativeImage lmColors, boolean nightvision, float darkLight, float partialTicks) {
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

   public static Vec3 getWorldFogColor(Vec3 fogVec, Level world, Entity renderViewEntity, float partialTicks) {
      Minecraft mc = Minecraft.m_91087_();
      if (WorldUtils.isNether(world)) {
         return getFogColorNether(fogVec);
      } else if (WorldUtils.isOverworld(world)) {
         return getFogColor(fogVec, mc.f_91073_, renderViewEntity.m_20185_(), renderViewEntity.m_20186_() + 1.0, renderViewEntity.m_20189_());
      } else {
         return WorldUtils.isEnd(world) ? getFogColorEnd(fogVec) : fogVec;
      }
   }

   public static Vec3 getWorldSkyColor(Vec3 skyVec, Level world, Entity renderViewEntity, float partialTicks) {
      Minecraft mc = Minecraft.m_91087_();
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
                  id = EntityUtils.getEntityIdByLocation(new ResourceLocation(name).toString());
               }
            } catch (ResourceLocationException var13) {
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

   private static int getSpawnEggColor(SpawnEggItem item, ItemStack itemStack, int layer, int color) {
      if (spawnEggPrimaryColors == null && spawnEggSecondaryColors == null) {
         return color;
      } else {
         EntityType entityType = item.m_43228_(itemStack);
         if (entityType == null) {
            return color;
         } else {
            int id = BuiltInRegistries.f_256780_.m_7447_(entityType);
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

   public static int getColorFromItemStack(ItemStack itemStack, int layer, int color) {
      if (itemStack == null) {
         return color;
      } else {
         Item item = itemStack.m_41720_();
         if (item == null) {
            return color;
         } else if (item instanceof SpawnEggItem) {
            return getSpawnEggColor((SpawnEggItem)item, itemStack, layer, color);
         } else {
            return item == Items.f_42094_ && lilyPadColor != -1 ? lilyPadColor : color;
         }
      }
   }

   private static int[] readDyeColors(Properties props, String fileName, String prefix, String logName) {
      DyeColor[] dyeValues = DyeColor.values();
      Map<String, DyeColor> mapDyes = new HashMap();

      for (int i = 0; i < dyeValues.length; i++) {
         DyeColor dye = dyeValues[i];
         mapDyes.put(dye.m_7912_(), dye);
      }

      mapDyes.put("lightBlue", DyeColor.LIGHT_BLUE);
      mapDyes.put("silver", DyeColor.LIGHT_GRAY);
      int[] colors = new int[dyeValues.length];
      int countColors = 0;

      for (String key : props.keySet()) {
         String value = props.getProperty(key);
         if (key.startsWith(prefix)) {
            String name = StrUtils.removePrefix(key, prefix);
            DyeColor dye = (DyeColor)mapDyes.get(name);
            int color = parseColor(value);
            if (dye != null && color >= 0) {
               int argb = ARGB32.m_321570_(color);
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

   private static int getDyeColors(DyeColor dye, int[] dyeColors, int color) {
      if (dyeColors == null) {
         return color;
      } else if (dye == null) {
         return color;
      } else {
         int customColor = dyeColors[dye.ordinal()];
         return customColor == 0 ? color : customColor;
      }
   }

   public static int getWolfCollarColors(DyeColor dye, int color) {
      return getDyeColors(dye, wolfCollarColors, color);
   }

   public static int getSheepColors(DyeColor dye, int color) {
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
      int[] colors = new int[MapColor.f_283862_.length];
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

      for (ResourceLocation rl : BuiltInRegistries.f_256974_.m_6566_()) {
         MobEffect potion = PotionUtils.getPotion(rl);
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

         for (ResourceLocation rl : BuiltInRegistries.f_256974_.m_6566_()) {
            MobEffect potion = PotionUtils.getPotion(rl);
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

   public static int getPotionColor(MobEffect potion, int color) {
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
         return MapColor.f_283808_.f_283805_;
      } else if (name.equals("grass")) {
         return MapColor.f_283824_.f_283805_;
      } else if (name.equals("sand")) {
         return MapColor.f_283761_.f_283805_;
      } else if (name.equals("cloth")) {
         return MapColor.f_283930_.f_283805_;
      } else if (name.equals("tnt")) {
         return MapColor.f_283816_.f_283805_;
      } else if (name.equals("ice")) {
         return MapColor.f_283828_.f_283805_;
      } else if (name.equals("iron")) {
         return MapColor.f_283906_.f_283805_;
      } else if (name.equals("foliage")) {
         return MapColor.f_283915_.f_283805_;
      } else if (name.equals("clay")) {
         return MapColor.f_283744_.f_283805_;
      } else if (name.equals("dirt")) {
         return MapColor.f_283762_.f_283805_;
      } else if (name.equals("stone")) {
         return MapColor.f_283947_.f_283805_;
      } else if (name.equals("water")) {
         return MapColor.f_283864_.f_283805_;
      } else if (name.equals("wood")) {
         return MapColor.f_283825_.f_283805_;
      } else if (name.equals("quartz")) {
         return MapColor.f_283942_.f_283805_;
      } else if (name.equals("gold")) {
         return MapColor.f_283757_.f_283805_;
      } else if (name.equals("diamond")) {
         return MapColor.f_283821_.f_283805_;
      } else if (name.equals("lapis")) {
         return MapColor.f_283933_.f_283805_;
      } else if (name.equals("emerald")) {
         return MapColor.f_283812_.f_283805_;
      } else if (name.equals("podzol")) {
         return MapColor.f_283819_.f_283805_;
      } else if (name.equals("netherrack")) {
         return MapColor.f_283820_.f_283805_;
      } else if (name.equals("snow") || name.equals("white")) {
         return MapColor.f_283811_.f_283805_;
      } else if (name.equals("adobe") || name.equals("orange")) {
         return MapColor.f_283750_.f_283805_;
      } else if (name.equals("magenta")) {
         return MapColor.f_283931_.f_283805_;
      } else if (name.equals("light_blue") || name.equals("lightBlue")) {
         return MapColor.f_283869_.f_283805_;
      } else if (name.equals("yellow")) {
         return MapColor.f_283832_.f_283805_;
      } else if (name.equals("lime")) {
         return MapColor.f_283916_.f_283805_;
      } else if (name.equals("pink")) {
         return MapColor.f_283765_.f_283805_;
      } else if (name.equals("gray")) {
         return MapColor.f_283818_.f_283805_;
      } else if (name.equals("silver") || name.equals("light_gray")) {
         return MapColor.f_283779_.f_283805_;
      } else if (name.equals("cyan")) {
         return MapColor.f_283772_.f_283805_;
      } else if (name.equals("purple")) {
         return MapColor.f_283889_.f_283805_;
      } else if (name.equals("blue")) {
         return MapColor.f_283743_.f_283805_;
      } else if (name.equals("brown")) {
         return MapColor.f_283748_.f_283805_;
      } else if (name.equals("green")) {
         return MapColor.f_283784_.f_283805_;
      } else if (name.equals("red")) {
         return MapColor.f_283913_.f_283805_;
      } else if (name.equals("black")) {
         return MapColor.f_283927_.f_283805_;
      } else if (name.equals("white_terracotta")) {
         return MapColor.f_283919_.f_283805_;
      } else if (name.equals("orange_terracotta")) {
         return MapColor.f_283895_.f_283805_;
      } else if (name.equals("magenta_terracotta")) {
         return MapColor.f_283850_.f_283805_;
      } else if (name.equals("light_blue_terracotta")) {
         return MapColor.f_283791_.f_283805_;
      } else if (name.equals("yellow_terracotta")) {
         return MapColor.f_283843_.f_283805_;
      } else if (name.equals("lime_terracotta")) {
         return MapColor.f_283778_.f_283805_;
      } else if (name.equals("pink_terracotta")) {
         return MapColor.f_283870_.f_283805_;
      } else if (name.equals("gray_terracotta")) {
         return MapColor.f_283861_.f_283805_;
      } else if (name.equals("light_gray_terracotta")) {
         return MapColor.f_283907_.f_283805_;
      } else if (name.equals("cyan_terracotta")) {
         return MapColor.f_283846_.f_283805_;
      } else if (name.equals("purple_terracotta")) {
         return MapColor.f_283892_.f_283805_;
      } else if (name.equals("blue_terracotta")) {
         return MapColor.f_283908_.f_283805_;
      } else if (name.equals("brown_terracotta")) {
         return MapColor.f_283774_.f_283805_;
      } else if (name.equals("green_terracotta")) {
         return MapColor.f_283856_.f_283805_;
      } else if (name.equals("red_terracotta")) {
         return MapColor.f_283798_.f_283805_;
      } else if (name.equals("black_terracotta")) {
         return MapColor.f_283771_.f_283805_;
      } else if (name.equals("crimson_nylium")) {
         return MapColor.f_283909_.f_283805_;
      } else if (name.equals("crimson_stem")) {
         return MapColor.f_283804_.f_283805_;
      } else if (name.equals("crimson_hyphae")) {
         return MapColor.f_283883_.f_283805_;
      } else if (name.equals("warped_nylium")) {
         return MapColor.f_283745_.f_283805_;
      } else if (name.equals("warped_stem")) {
         return MapColor.f_283749_.f_283805_;
      } else if (name.equals("warped_hyphae")) {
         return MapColor.f_283807_.f_283805_;
      } else if (name.equals("warped_wart_block")) {
         return MapColor.f_283898_.f_283805_;
      } else if (name.equals("deepslate")) {
         return MapColor.f_283875_.f_283805_;
      } else if (name.equals("raw_iron")) {
         return MapColor.f_283877_.f_283805_;
      } else {
         return name.equals("glow_lichen") ? MapColor.f_283769_.f_283805_ : -1;
      }
   }

   private static int[] getMapColors() {
      MapColor[] mapColors = MapColor.f_283862_;
      int[] colors = new int[mapColors.length];
      Arrays.fill(colors, -1);

      for (int i = 0; i < mapColors.length && i < colors.length; i++) {
         MapColor mapColor = mapColors[i];
         if (mapColor != null) {
            colors[i] = mapColor.f_283871_;
         }
      }

      return colors;
   }

   private static void setMapColors(int[] colors) {
      if (colors != null) {
         MapColor[] mapColors = MapColor.f_283862_;

         for (int i = 0; i < mapColors.length && i < colors.length; i++) {
            MapColor mapColor = mapColors[i];
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
      DyeColor[] dyeColors = DyeColor.values();
      int[] colors = new int[dyeColors.length];

      for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
         DyeColor dyeColor = dyeColors[i];
         if (dyeColor != null) {
            colors[i] = dyeColor.m_340318_();
         }
      }

      return colors;
   }

   private static void setDyeColors(int[] colors) {
      if (colors != null) {
         DyeColor[] dyeColors = DyeColor.values();

         for (int i = 0; i < dyeColors.length && i < colors.length; i++) {
            DyeColor dyeColor = dyeColors[i];
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
      int m_130045_(BlockState var1, BlockAndTintGetter var2, BlockPos var3);

      boolean isColorConstant();
   }
}
