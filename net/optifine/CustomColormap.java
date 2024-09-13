package net.optifine;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.optifine.config.ConnectedParser;
import net.optifine.config.MatchBlock;
import net.optifine.config.Matches;
import net.optifine.util.BiomeUtils;
import net.optifine.util.BlockUtils;
import net.optifine.util.TextureUtils;

public class CustomColormap implements CustomColors.IColorizer {
   public String name = null;
   public String basePath = null;
   private int format = -1;
   private MatchBlock[] matchBlocks = null;
   private String source = null;
   private int color = -1;
   private int yVariance = 0;
   private int yOffset = 0;
   private int width = 0;
   private int height = 0;
   private int[] colors = null;
   private float[][] colorsRgb = null;
   private static int FORMAT_UNKNOWN;
   private static int FORMAT_VANILLA;
   private static int FORMAT_GRID;
   private static int FORMAT_FIXED;
   public static String FORMAT_VANILLA_STRING;
   public static String FORMAT_GRID_STRING;
   public static String FORMAT_FIXED_STRING;
   public static String[] FORMAT_STRINGS = new String[]{"vanilla", "grid", "fixed"};
   public static String KEY_FORMAT;
   public static String KEY_BLOCKS;
   public static String KEY_SOURCE;
   public static String KEY_COLOR;
   public static String KEY_Y_VARIANCE;
   public static String KEY_Y_OFFSET;

   public CustomColormap(Properties props, String path, int width, int height, String formatDefault) {
      ConnectedParser cp = new ConnectedParser("Colormap");
      this.name = cp.parseName(path);
      this.basePath = cp.parseBasePath(path);
      this.format = this.parseFormat(props.getProperty("format", formatDefault));
      this.matchBlocks = cp.parseMatchBlocks(props.getProperty("blocks"));
      this.source = parseTexture(props.getProperty("source"), path, this.basePath);
      this.color = ConnectedParser.parseColor(props.getProperty("color"), -1);
      this.yVariance = cp.parseInt(props.getProperty("yVariance"), 0);
      this.yOffset = cp.parseIntNeg(props.getProperty("yOffset"), 0);
      this.width = width;
      this.height = height;
   }

   private int parseFormat(String str) {
      if (str == null) {
         return 0;
      } else {
         str = str.trim();
         if (str.equals("vanilla")) {
            return 0;
         } else if (str.equals("grid")) {
            return 1;
         } else if (str.equals("fixed")) {
            return 2;
         } else {
            warn("Unknown format: " + str);
            return -1;
         }
      }
   }

   public boolean isValid(String path) {
      if (this.format != 0 && this.format != 1) {
         if (this.format != 2) {
            return false;
         }

         if (this.color < 0) {
            this.color = 16777215;
         }
      } else {
         if (this.source == null) {
            warn("Source not defined: " + path);
            return false;
         }

         this.readColors();
         if (this.colors == null) {
            return false;
         }

         if (this.color < 0) {
            if (this.format == 0) {
               this.color = this.m_130045_(127, 127);
            }

            if (this.format == 1) {
               this.color = this.getColorGrid(BiomeUtils.PLAINS, new BlockPos(0, 64, 0));
            }
         }
      }

      return true;
   }

   public boolean isValidMatchBlocks(String path) {
      if (this.matchBlocks == null) {
         this.matchBlocks = this.detectMatchBlocks();
         if (this.matchBlocks == null) {
            warn("Match blocks not defined: " + path);
            return false;
         }
      }

      return true;
   }

   private MatchBlock[] detectMatchBlocks() {
      ResourceLocation loc = new ResourceLocation(this.name);
      if (BuiltInRegistries.f_256975_.m_7804_(loc)) {
         Block block = (Block)BuiltInRegistries.f_256975_.m_7745_(loc);
         return new MatchBlock[]{new MatchBlock(BlockUtils.getBlockId(block))};
      } else {
         Pattern p = Pattern.m_289905_("^block([0-9]+).*$");
         Matcher m = p.matcher(this.name);
         if (m.matches()) {
            String idStr = m.group(1);
            int id = Config.parseInt(idStr, -1);
            if (id >= 0) {
               return new MatchBlock[]{new MatchBlock(id)};
            }
         }

         ConnectedParser cp = new ConnectedParser("Colormap");
         MatchBlock[] mbs = cp.parseMatchBlock(this.name);
         return mbs != null ? mbs : null;
      }
   }

   private void readColors() {
      try {
         this.colors = null;
         if (this.source == null) {
            return;
         }

         String imagePath = this.source + ".png";
         ResourceLocation loc = new ResourceLocation(imagePath);
         InputStream is = Config.getResourceStream(loc);
         if (is == null) {
            return;
         }

         BufferedImage img = TextureUtils.readBufferedImage(is);
         if (img == null) {
            return;
         }

         int imgWidth = img.m_92515_();
         int imgHeight = img.getHeight();
         boolean widthOk = this.width < 0 || this.width == imgWidth;
         boolean heightOk = this.height < 0 || this.height == imgHeight;
         if (!widthOk || !heightOk) {
            dbg("Non-standard palette size: " + imgWidth + "x" + imgHeight + ", should be: " + this.width + "x" + this.height + ", path: " + imagePath);
         }

         this.width = imgWidth;
         this.height = imgHeight;
         if (this.width <= 0 || this.height <= 0) {
            warn("Invalid palette size: " + imgWidth + "x" + imgHeight + ", path: " + imagePath);
            return;
         }

         this.colors = new int[imgWidth * imgHeight];
         img.getRGB(0, 0, imgWidth, imgHeight, this.colors, 0, imgWidth);
      } catch (IOException var9) {
         var9.printStackTrace();
      }
   }

   private static void dbg(String str) {
      Config.dbg("CustomColors: " + str);
   }

   private static void warn(String str) {
      Config.warn("CustomColors: " + str);
   }

   private static String parseTexture(String texStr, String path, String basePath) {
      if (texStr != null) {
         texStr = texStr.trim();
         String png = ".png";
         if (texStr.endsWith(png)) {
            texStr = texStr.substring(0, texStr.length() - png.length());
         }

         return fixTextureName(texStr, basePath);
      } else {
         String str = path;
         int pos = path.lastIndexOf(47);
         if (pos >= 0) {
            str = path.substring(pos + 1);
         }

         int pos2 = str.lastIndexOf(46);
         if (pos2 >= 0) {
            str = str.substring(0, pos2);
         }

         return fixTextureName(str, basePath);
      }
   }

   private static String fixTextureName(String iconName, String basePath) {
      iconName = TextureUtils.fixResourcePath(iconName, basePath);
      if (!iconName.startsWith(basePath) && !iconName.startsWith("textures/") && !iconName.startsWith("optifine/")) {
         iconName = basePath + "/" + iconName;
      }

      if (iconName.endsWith(".png")) {
         iconName = iconName.substring(0, iconName.length() - 4);
      }

      String pathBlocks = "textures/block/";
      if (iconName.startsWith(pathBlocks)) {
         iconName = iconName.substring(pathBlocks.length());
      }

      if (iconName.startsWith("/")) {
         iconName = iconName.substring(1);
      }

      return iconName;
   }

   public boolean matchesBlock(BlockState blockState) {
      return Matches.block(blockState, this.matchBlocks);
   }

   public int getColorRandom() {
      if (this.format == 2) {
         return this.color;
      } else {
         int index = CustomColors.random.nextInt(this.colors.length);
         return this.colors[index];
      }
   }

   public int m_130045_(int index) {
      index = Config.limit(index, 0, this.colors.length - 1);
      return this.colors[index] & 16777215;
   }

   public int m_130045_(int cx, int cy) {
      cx = Config.limit(cx, 0, this.width - 1);
      cy = Config.limit(cy, 0, this.height - 1);
      return this.colors[cy * this.width + cx] & 16777215;
   }

   public float[][] getColorsRgb() {
      if (this.colorsRgb == null) {
         this.colorsRgb = toRgb(this.colors);
      }

      return this.colorsRgb;
   }

   @Override
   public int m_130045_(BlockState blockState, BlockAndTintGetter blockAccess, BlockPos blockPos) {
      return this.m_130045_(blockAccess, blockPos);
   }

   public int m_130045_(BlockAndTintGetter blockAccess, BlockPos blockPos) {
      Biome biome = CustomColors.getColorBiome(blockAccess, blockPos);
      return this.m_130045_(biome, blockPos);
   }

   @Override
   public boolean isColorConstant() {
      return this.format == 2;
   }

   public int m_130045_(Biome biome, BlockPos blockPos) {
      if (this.format == 0) {
         return this.getColorVanilla(biome, blockPos);
      } else {
         return this.format == 1 ? this.getColorGrid(biome, blockPos) : this.color;
      }
   }

   public int getColorSmooth(BlockAndTintGetter blockAccess, double x, double y, double z, int radius) {
      if (this.format == 2) {
         return this.color;
      } else {
         int x0 = Mth.m_14107_(x);
         int y0 = Mth.m_14107_(y);
         int z0 = Mth.m_14107_(z);
         int sumRed = 0;
         int sumGreen = 0;
         int sumBlue = 0;
         int count = 0;
         BlockPosM blockPosM = new BlockPosM(0, 0, 0);

         for (int ix = x0 - radius; ix <= x0 + radius; ix++) {
            for (int iz = z0 - radius; iz <= z0 + radius; iz++) {
               blockPosM.setXyz(ix, y0, iz);
               int col = this.m_130045_(blockAccess, blockPosM);
               sumRed += col >> 16 & 0xFF;
               sumGreen += col >> 8 & 0xFF;
               sumBlue += col & 0xFF;
               count++;
            }
         }

         int r = sumRed / count;
         int g = sumGreen / count;
         int b = sumBlue / count;
         return r << 16 | g << 8 | b;
      }
   }

   private int getColorVanilla(Biome biome, BlockPos blockPos) {
      double temperature = (double)Mth.m_14036_(biome.m_47554_(), 0.0F, 1.0F);
      double rainfall = (double)Mth.m_14036_(BiomeUtils.getDownfall(biome), 0.0F, 1.0F);
      rainfall *= temperature;
      int cx = (int)((1.0 - temperature) * (double)(this.width - 1));
      int cy = (int)((1.0 - rainfall) * (double)(this.height - 1));
      return this.m_130045_(cx, cy);
   }

   private int getColorGrid(Biome biome, BlockPos blockPos) {
      int biomeId = BiomeUtils.getId(biome);
      int cy = blockPos.m_123342_() - this.yOffset;
      if (this.yVariance > 0) {
         int seed = blockPos.m_123341_() << 16 + blockPos.m_123343_();
         int rand = Config.intHash(seed);
         int range = this.yVariance * 2 + 1;
         int diff = (rand & 0xFF) % range - this.yVariance;
         cy += diff;
      }

      return this.m_130045_(biomeId, cy);
   }

   public int getLength() {
      return this.format == 2 ? 1 : this.colors.length;
   }

   public int m_92515_() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   private static float[][] toRgb(int[] cols) {
      float[][] colsRgb = new float[cols.length][3];

      for (int i = 0; i < cols.length; i++) {
         int col = cols[i];
         float rf = (float)(col >> 16 & 0xFF) / 255.0F;
         float gf = (float)(col >> 8 & 0xFF) / 255.0F;
         float bf = (float)(col & 0xFF) / 255.0F;
         float[] colRgb = colsRgb[i];
         colRgb[0] = rf;
         colRgb[1] = gf;
         colRgb[2] = bf;
      }

      return colsRgb;
   }

   public void addMatchBlock(MatchBlock mb) {
      if (this.matchBlocks == null) {
         this.matchBlocks = new MatchBlock[0];
      }

      this.matchBlocks = (MatchBlock[])Config.addObjectToArray(this.matchBlocks, mb);
   }

   public void addMatchBlock(int blockId, int metadata) {
      MatchBlock mb = this.getMatchBlock(blockId);
      if (mb != null) {
         if (metadata >= 0) {
            mb.addMetadata(metadata);
         }
      } else {
         this.addMatchBlock(new MatchBlock(blockId, metadata));
      }
   }

   private MatchBlock getMatchBlock(int blockId) {
      if (this.matchBlocks == null) {
         return null;
      } else {
         for (int i = 0; i < this.matchBlocks.length; i++) {
            MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() == blockId) {
               return mb;
            }
         }

         return null;
      }
   }

   public int[] getMatchBlockIds() {
      if (this.matchBlocks == null) {
         return null;
      } else {
         Set setIds = new HashSet();

         for (int i = 0; i < this.matchBlocks.length; i++) {
            MatchBlock mb = this.matchBlocks[i];
            if (mb.getBlockId() >= 0) {
               setIds.add(mb.getBlockId());
            }
         }

         Integer[] ints = (Integer[])setIds.toArray(new Integer[setIds.size()]);
         int[] ids = new int[ints.length];

         for (int ix = 0; ix < ints.length; ix++) {
            ids[ix] = ints[ix];
         }

         return ids;
      }
   }

   public String toString() {
      return this.basePath + "/" + this.name + ", blocks: " + Config.arrayToString((Object[])this.matchBlocks) + ", source: " + this.source;
   }
}
