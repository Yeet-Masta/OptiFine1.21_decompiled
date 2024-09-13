package net.optifine.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.minecraft.client.renderer.entity.layers.MushroomCowMushroomLayer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.optifine.BetterGrass;
import net.optifine.BetterSnow;
import net.optifine.Config;
import net.optifine.ConnectedTextures;
import net.optifine.CustomBlockLayers;
import net.optifine.CustomColors;
import net.optifine.CustomGuis;
import net.optifine.CustomItems;
import net.optifine.CustomLoadingScreens;
import net.optifine.CustomPanorama;
import net.optifine.CustomSky;
import net.optifine.EmissiveTextures;
import net.optifine.Lang;
import net.optifine.NaturalTextures;
import net.optifine.RandomEntities;
import net.optifine.SmartLeaves;
import net.optifine.TextureAnimations;
import net.optifine.entity.model.CustomEntityModels;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.MultiTexID;
import net.optifine.shaders.Shaders;
import org.apache.commons.io.IOUtils;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class TextureUtils {
   private static String texGrassTop;
   private static String texGrassSide;
   private static String texGrassSideOverlay;
   private static String texSnow;
   private static String texGrassSideSnowed;
   private static String texMyceliumSide;
   private static String texMyceliumTop;
   private static String texWaterStill;
   private static String texWaterFlow;
   private static String texLavaStill;
   private static String texLavaFlow;
   private static String texFireLayer0;
   private static String texFireLayer1;
   private static String texSoulFireLayer0;
   private static String texSoulFireLayer1;
   private static String texCampFire;
   private static String texCampFireLogLit;
   private static String texSoulCampFire;
   private static String texSoulCampFireLogLit;
   private static String texPortal;
   private static String texGlass;
   private static String texGlassPaneTop;
   public static TextureAtlasSprite iconGrassTop;
   public static TextureAtlasSprite iconGrassSide;
   public static TextureAtlasSprite iconGrassSideOverlay;
   public static TextureAtlasSprite iconSnow;
   public static TextureAtlasSprite iconGrassSideSnowed;
   public static TextureAtlasSprite iconMyceliumSide;
   public static TextureAtlasSprite iconMyceliumTop;
   public static TextureAtlasSprite iconWaterStill;
   public static TextureAtlasSprite iconWaterFlow;
   public static TextureAtlasSprite iconLavaStill;
   public static TextureAtlasSprite iconLavaFlow;
   public static TextureAtlasSprite iconFireLayer0;
   public static TextureAtlasSprite iconFireLayer1;
   public static TextureAtlasSprite iconSoulFireLayer0;
   public static TextureAtlasSprite iconSoulFireLayer1;
   public static TextureAtlasSprite iconCampFire;
   public static TextureAtlasSprite iconCampFireLogLit;
   public static TextureAtlasSprite iconSoulCampFire;
   public static TextureAtlasSprite iconSoulCampFireLogLit;
   public static TextureAtlasSprite iconPortal;
   public static TextureAtlasSprite iconGlass;
   public static TextureAtlasSprite iconGlassPaneTop;
   public static String SPRITE_PREFIX_BLOCKS;
   public static String SPRITE_PREFIX_ITEMS;
   public static ResourceLocation LOCATION_SPRITE_EMPTY = new ResourceLocation("optifine/ctm/default/empty");
   public static ResourceLocation LOCATION_TEXTURE_EMPTY = new ResourceLocation("optifine/ctm/default/empty.png");
   public static ResourceLocation WHITE_TEXTURE_LOCATION = new ResourceLocation("textures/misc/white.png");
   private static IntBuffer staticBuffer = Config.createDirectIntBuffer(256);
   private static int glMaximumTextureSize = -1;
   private static Map<Integer, String> mapTextureAllocations = new HashMap();
   private static Map<ResourceLocation, ResourceLocation> mapSpriteLocations = new HashMap();
   private static ResourceLocation LOCATION_ATLAS_PAINTINGS = new ResourceLocation("textures/atlas/paintings.png");

   public static void m_252999_() {
      TextureAtlas mapBlocks = getTextureMapBlocks();
      if (mapBlocks != null) {
         String prefix = "minecraft:block/";
         iconGrassTop = getSpriteCheck(mapBlocks, prefix + "grass_block_top");
         iconGrassSide = getSpriteCheck(mapBlocks, prefix + "grass_block_side");
         iconGrassSideOverlay = getSpriteCheck(mapBlocks, prefix + "grass_block_side_overlay");
         iconSnow = getSpriteCheck(mapBlocks, prefix + "snow");
         iconGrassSideSnowed = getSpriteCheck(mapBlocks, prefix + "grass_block_snow");
         iconMyceliumSide = getSpriteCheck(mapBlocks, prefix + "mycelium_side");
         iconMyceliumTop = getSpriteCheck(mapBlocks, prefix + "mycelium_top");
         iconWaterStill = getSpriteCheck(mapBlocks, prefix + "water_still");
         iconWaterFlow = getSpriteCheck(mapBlocks, prefix + "water_flow");
         iconLavaStill = getSpriteCheck(mapBlocks, prefix + "lava_still");
         iconLavaFlow = getSpriteCheck(mapBlocks, prefix + "lava_flow");
         iconFireLayer0 = getSpriteCheck(mapBlocks, prefix + "fire_0");
         iconFireLayer1 = getSpriteCheck(mapBlocks, prefix + "fire_1");
         iconSoulFireLayer0 = getSpriteCheck(mapBlocks, prefix + "soul_fire_0");
         iconSoulFireLayer1 = getSpriteCheck(mapBlocks, prefix + "soul_fire_1");
         iconCampFire = getSpriteCheck(mapBlocks, prefix + "campfire_fire");
         iconCampFireLogLit = getSpriteCheck(mapBlocks, prefix + "campfire_log_lit");
         iconSoulCampFire = getSpriteCheck(mapBlocks, prefix + "soul_campfire_fire");
         iconSoulCampFireLogLit = getSpriteCheck(mapBlocks, prefix + "soul_campfire_log_lit");
         iconPortal = getSpriteCheck(mapBlocks, prefix + "nether_portal");
         iconGlass = getSpriteCheck(mapBlocks, prefix + "glass");
         iconGlassPaneTop = getSpriteCheck(mapBlocks, prefix + "glass_pane_top");
         String prefixItems = "minecraft:item/";
         mapSpriteLocations.clear();
      }
   }

   public static TextureAtlasSprite getSpriteCheck(TextureAtlas textureMap, String name) {
      TextureAtlasSprite sprite = textureMap.getUploadedSprite(name);
      if (sprite == null || MissingTextureAtlasSprite.isMisingSprite(sprite)) {
         Config.warn("Sprite not found: " + name);
      }

      return sprite;
   }

   public static BufferedImage fixTextureDimensions(String name, BufferedImage bi) {
      if (name.startsWith("/mob/zombie") || name.startsWith("/mob/pigzombie")) {
         int width = bi.m_92515_();
         int height = bi.getHeight();
         if (width == height * 2) {
            BufferedImage scaledImage = new BufferedImage(width, height * 2, 2);
            Graphics2D gr = scaledImage.createGraphics();
            gr.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            gr.drawImage(bi, 0, 0, width, height, null);
            return scaledImage;
         }
      }

      return bi;
   }

   public static int ceilPowerOfTwo(int val) {
      int i = 1;

      while (i < val) {
         i *= 2;
      }

      return i;
   }

   public static int getPowerOfTwo(int val) {
      int i = 1;

      int po2;
      for (po2 = 0; i < val; po2++) {
         i *= 2;
      }

      return po2;
   }

   public static int twoToPower(int power) {
      int val = 1;

      for (int i = 0; i < power; i++) {
         val *= 2;
      }

      return val;
   }

   public static AbstractTexture getTexture(ResourceLocation loc) {
      AbstractTexture tex = Config.getTextureManager().m_118506_(loc);
      if (tex != null) {
         return tex;
      } else if (!Config.hasResource(loc)) {
         return null;
      } else {
         AbstractTexture var2 = new SimpleTexture(loc);
         Config.getTextureManager().m_118495_(loc, var2);
         return var2;
      }
   }

   public static void resourcesPreReload(ResourceManager rm) {
      CustomItems.m_252999_();
   }

   public static void resourcesReloaded(ResourceManager rm) {
      if (getTextureMapBlocks() != null) {
         Config.dbg("*** Reloading custom textures ***");
         CustomSky.reset();
         TextureAnimations.reset();
         m_252999_();
         NaturalTextures.m_252999_();
         BetterGrass.m_252999_();
         BetterSnow.m_252999_();
         TextureAnimations.m_252999_();
         CustomColors.m_252999_();
         CustomSky.m_252999_();
         CustomItems.updateModels();
         CustomEntityModels.m_252999_();
         Shaders.resourcesReloaded();
         Lang.resourcesReloaded();
         Config.updateTexturePackClouds();
         SmartLeaves.updateLeavesModels();
         CustomPanorama.m_252999_();
         CustomGuis.m_252999_();
         MushroomCowMushroomLayer.m_252999_();
         CustomLoadingScreens.m_252999_();
         CustomBlockLayers.m_252999_();
         Config.getTextureManager().m_7673_();
         Config.dbg("Disable Forge light pipeline");
         ReflectorForge.setForgeLightPipelineEnabled(false);
      }
   }

   public static TextureAtlas getTextureMapBlocks() {
      return Config.getTextureMap();
   }

   public static void registerResourceListener() {
      if (Config.getResourceManager() instanceof ReloadableResourceManager rrm) {
         SimplePreparableReloadListener rl = new SimplePreparableReloadListener() {
            protected Object m_5944_(ResourceManager p_212854_1_, ProfilerFiller p_212854_2_) {
               return null;
            }

            protected void m_5787_(Object p_212853_1_, ResourceManager p_212853_2_, ProfilerFiller p_212853_3_) {
            }
         };
         rrm.m_7217_(rl);
         ResourceManagerReloadListener rmrl = new ResourceManagerReloadListener() {
            public void m_6213_(ResourceManager resourceManager) {
               TextureUtils.resourcesReloaded(resourceManager);
            }
         };
         rrm.m_7217_(rmrl);
      }
   }

   public static void registerTickableTextures() {
      TickableTexture tt = new TickableTexture() {
         public void m_7673_() {
            TextureAnimations.updateAnimations();
         }

         @Override
         public void m_6704_(ResourceManager var1) throws IOException {
         }

         @Override
         public int m_117963_() {
            return 0;
         }

         @Override
         public void restoreLastBlurMipmap() {
         }

         @Override
         public MultiTexID getMultiTexID() {
            return null;
         }
      };
      ResourceLocation ttl = new ResourceLocation("optifine/tickable_textures");
      Config.getTextureManager().m_118495_(ttl, tt);
   }

   public static void registerCustomModels(ModelBakery modelBakery) {
      CustomItems.loadModels(modelBakery);
   }

   public static void registerCustomSprites(TextureAtlas textureMap) {
      if (textureMap.m_118330_().equals(TextureAtlas.f_118259_)) {
         ConnectedTextures.updateIcons(textureMap);
         CustomItems.updateIcons(textureMap);
         BetterGrass.updateIcons(textureMap);
      }

      textureMap.registerSprite(LOCATION_SPRITE_EMPTY);
   }

   public static void registerCustomSpriteLocations(ResourceLocation atlasLocation, Set<ResourceLocation> spriteLocations) {
      RandomEntities.registerSprites(atlasLocation, spriteLocations);
   }

   public static void refreshCustomSprites(TextureAtlas textureMap) {
      if (textureMap.m_118330_().equals(TextureAtlas.f_118259_)) {
         ConnectedTextures.refreshIcons(textureMap);
         CustomItems.refreshIcons(textureMap);
         BetterGrass.refreshIcons(textureMap);
      }

      EmissiveTextures.refreshIcons(textureMap);
   }

   public static ResourceLocation fixResourceLocation(ResourceLocation loc, String basePath) {
      if (!loc.m_135827_().equals("minecraft")) {
         return loc;
      } else {
         String path = loc.m_135815_();
         String pathFixed = fixResourcePath(path, basePath);
         if (pathFixed != path) {
            loc = new ResourceLocation(loc.m_135827_(), pathFixed);
         }

         return loc;
      }
   }

   public static String fixResourcePath(String path, String basePath) {
      String strAssMc = "assets/minecraft/";
      if (path.startsWith(strAssMc)) {
         return path.substring(strAssMc.length());
      } else if (path.startsWith("./")) {
         path = path.substring(2);
         if (!basePath.endsWith("/")) {
            basePath = basePath + "/";
         }

         return basePath + path;
      } else {
         if (path.startsWith("/~")) {
            path = path.substring(1);
         }

         String strOptifine = "optifine/";
         if (path.startsWith("~/")) {
            path = path.substring(2);
            return strOptifine + path;
         } else {
            return path.startsWith("/") ? strOptifine + path.substring(1) : path;
         }
      }
   }

   public static String getBasePath(String path) {
      int pos = path.lastIndexOf(47);
      return pos < 0 ? "" : path.substring(0, pos);
   }

   public static void applyAnisotropicLevel() {
      if (GL.getCapabilities().GL_EXT_texture_filter_anisotropic) {
         float maxLevel = GL11.glGetFloat(34047);
         float level = (float)Config.getAnisotropicFilterLevel();
         level = Math.min(level, maxLevel);
         GL11.glTexParameterf(3553, 34046, level);
      }
   }

   public static void bindTexture(int glTexId) {
      GlStateManager._bindTexture(glTexId);
   }

   public static boolean isPowerOfTwo(int x) {
      int x2 = Mth.m_14125_(x);
      return x2 == x;
   }

   public static NativeImage scaleImage(NativeImage ni, int w2) {
      BufferedImage bi = toBufferedImage(ni);
      BufferedImage bi2 = scaleImage(bi, w2);
      return toNativeImage(bi2);
   }

   public static BufferedImage toBufferedImage(NativeImage ni) {
      int width = ni.m_84982_();
      int height = ni.m_85084_();
      int[] data = new int[width * height];
      ni.getBufferRGBA().get(data);
      BufferedImage bi = new BufferedImage(width, height, 2);
      bi.setRGB(0, 0, width, height, data, 0, width);
      return bi;
   }

   private static NativeImage toNativeImage(BufferedImage bi) {
      int width = bi.m_92515_();
      int height = bi.getHeight();
      int[] data = new int[width * height];
      bi.getRGB(0, 0, width, height, data, 0, width);
      NativeImage ni = new NativeImage(width, height, false);
      ni.getBufferRGBA().put(data);
      return ni;
   }

   public static BufferedImage scaleImage(BufferedImage bi, int w2) {
      int w = bi.m_92515_();
      int h = bi.getHeight();
      int h2 = h * w2 / w;
      BufferedImage bi2 = new BufferedImage(w2, h2, 2);
      Graphics2D g2 = bi2.createGraphics();
      Object method = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
      if (w2 < w || w2 % w != 0) {
         method = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
      }

      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, method);
      g2.drawImage(bi, 0, 0, w2, h2, null);
      return bi2;
   }

   public static int scaleToGrid(int size, int sizeGrid) {
      if (size == sizeGrid) {
         return size;
      } else {
         int sizeNew = size / sizeGrid * sizeGrid;

         while (sizeNew < size) {
            sizeNew += sizeGrid;
         }

         return sizeNew;
      }
   }

   public static int scaleToMin(int size, int sizeMin) {
      if (size >= sizeMin) {
         return size;
      } else {
         int sizeNew = sizeMin / size * size;

         while (sizeNew < sizeMin) {
            sizeNew += size;
         }

         return sizeNew;
      }
   }

   public static Dimension getImageSize(InputStream in, String suffix) {
      Iterator iter = ImageIO.getImageReadersBySuffix(suffix);

      while (iter.hasNext()) {
         ImageReader reader = (ImageReader)iter.next();

         Dimension var7;
         try {
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis);
            int width = reader.m_92515_(reader.getMinIndex());
            int height = reader.getHeight(reader.getMinIndex());
            var7 = new Dimension(width, height);
         } catch (IOException var11) {
            continue;
         } finally {
            reader.dispose();
         }

         return var7;
      }

      return null;
   }

   public static void dbgMipmaps(TextureAtlasSprite textureatlassprite) {
      NativeImage[] mipmapImages = textureatlassprite.getMipmapImages();

      for (int l = 0; l < mipmapImages.length; l++) {
         NativeImage image = mipmapImages[l];
         if (image == null) {
            Config.dbg(l + ": " + image);
         } else {
            Config.dbg(l + ": " + image.m_84982_() * image.m_85084_());
         }
      }
   }

   public static void saveGlTexture(String name, int textureId, int mipmapLevels, int width, int height) {
      bindTexture(textureId);
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      name = StrUtils.removeSuffix(name, ".png");
      File fileBase = new File(name);
      File dir = fileBase.getParentFile();
      if (dir != null) {
         dir.mkdirs();
      }

      for (int i = 0; i < 16; i++) {
         String namePng = name + "_" + i + ".png";
         File filePng = new File(namePng);
         filePng.delete();
      }

      for (int level = 0; level <= mipmapLevels; level++) {
         File filePng = new File(name + "_" + level + ".png");
         int widthLevel = width >> level;
         int heightLevel = height >> level;
         int sizeLevel = widthLevel * heightLevel;
         IntBuffer buf = BufferUtils.createIntBuffer(sizeLevel);
         int[] data = new int[sizeLevel];
         GL11.glGetTexImage(3553, level, 32993, 33639, buf);
         buf.get(data);
         BufferedImage image = new BufferedImage(widthLevel, heightLevel, 2);
         image.setRGB(0, 0, widthLevel, heightLevel, data, 0, widthLevel);

         try {
            ImageIO.write(image, "png", filePng);
            Config.dbg("Exported: " + filePng);
         } catch (Exception var16) {
            Config.warn("Error writing: " + filePng);
            Config.warn(var16.getClass().getName() + ": " + var16.getMessage());
         }
      }
   }

   public static int getGLMaximumTextureSize() {
      if (glMaximumTextureSize < 0) {
         glMaximumTextureSize = detectGLMaximumTextureSize();
      }

      return glMaximumTextureSize;
   }

   private static int detectGLMaximumTextureSize() {
      for (int i = 65536; i > 0; i >>= 1) {
         GlStateManager._texImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (IntBuffer)null);
         int err = GL11.glGetError();
         int width = GlStateManager._getTexLevelParameter(32868, 0, 4096);
         if (width != 0) {
            return i;
         }
      }

      return 0;
   }

   public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
      if (imageStream == null) {
         return null;
      } else {
         BufferedImage var2;
         try {
            BufferedImage bufferedimage = ImageIO.read(imageStream);
            var2 = bufferedimage;
         } finally {
            IOUtils.closeQuietly(imageStream);
         }

         return var2;
      }
   }

   public static int toAbgr(int argb) {
      int a = argb >> 24 & 0xFF;
      int r = argb >> 16 & 0xFF;
      int g = argb >> 8 & 0xFF;
      int b = argb >> 0 & 0xFF;
      return a << 24 | b << 16 | g << 8 | r;
   }

   public static void resetDataUnpacking() {
      GlStateManager._pixelStore(3314, 0);
      GlStateManager._pixelStore(3316, 0);
      GlStateManager._pixelStore(3315, 0);
      GlStateManager._pixelStore(3317, 4);
   }

   public static String getStackTrace(Throwable t) {
      CharArrayWriter caw = new CharArrayWriter();
      t.printStackTrace(new PrintWriter(caw));
      return caw.toString();
   }

   public static void debugTextureGenerated(int id) {
      mapTextureAllocations.put(id, getStackTrace(new Throwable("StackTrace")));
      Config.dbg("Textures: " + mapTextureAllocations.size());
   }

   public static void debugTextureDeleted(int id) {
      mapTextureAllocations.remove(id);
      Config.dbg("Textures: " + mapTextureAllocations.size());
   }

   public static TextureAtlasSprite getCustomSprite(TextureAtlasSprite sprite) {
      if (Config.isRandomEntities()) {
         sprite = RandomEntities.getRandomSprite(sprite);
      }

      if (EmissiveTextures.isActive()) {
         sprite = EmissiveTextures.getEmissiveSprite(sprite);
      }

      return sprite;
   }

   public static ResourceLocation getSpriteLocation(ResourceLocation loc) {
      ResourceLocation locSprite = (ResourceLocation)mapSpriteLocations.get(loc);
      if (locSprite == null) {
         String pathSprite = loc.m_135815_();
         pathSprite = StrUtils.removePrefix(pathSprite, "textures/");
         pathSprite = StrUtils.removeSuffix(pathSprite, ".png");
         locSprite = new ResourceLocation(loc.m_135827_(), pathSprite);
         mapSpriteLocations.put(loc, locSprite);
      }

      return locSprite;
   }
}
