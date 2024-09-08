package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.logging.LogUtils;
import java.awt.Dimension;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.SmartAnimations;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ITextureFormat;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTex;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.ColorBlenderLinear;
import net.optifine.texture.IColorBlender;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class TextureAtlas extends AbstractTexture implements Dumpable, Tickable {
   private static final Logger f_118261_ = LogUtils.getLogger();
   @Deprecated
   public static final ResourceLocation f_118259_ = InventoryMenu.f_39692_;
   @Deprecated
   public static final ResourceLocation f_118260_ = ResourceLocation.m_340282_("textures/atlas/particles.png");
   private List<SpriteContents> f_118263_ = List.of();
   private List<TextureAtlasSprite.Ticker> f_118262_ = List.of();
   private Map<ResourceLocation, TextureAtlasSprite> f_118264_ = Map.of();
   @Nullable
   private TextureAtlasSprite f_301625_;
   private final ResourceLocation f_118265_;
   private final int f_118266_;
   private int f_276067_;
   private int f_276070_;
   private int f_276072_;
   private Map<ResourceLocation, TextureAtlasSprite> mapRegisteredSprites = new LinkedHashMap();
   private Map<ResourceLocation, TextureAtlasSprite> mapMissingSprites = new LinkedHashMap();
   private TextureAtlasSprite[] iconGrid = null;
   private int iconGridSize = -1;
   private int iconGridCountX = -1;
   private int iconGridCountY = -1;
   private double iconGridSizeU = -1.0;
   private double iconGridSizeV = -1.0;
   private CounterInt counterIndexInMap = new CounterInt(0);
   public int atlasWidth = 0;
   public int atlasHeight = 0;
   public int mipmapLevel = 0;
   private int countAnimationsActive;
   private int frameCountAnimations;
   private boolean terrain;
   private boolean shaders;
   private boolean multiTexture;
   private ITextureFormat textureFormat;

   public TextureAtlas(ResourceLocation textureLocationIn) {
      this.f_118265_ = textureLocationIn;
      this.f_118266_ = RenderSystem.maxSupportedTextureSize();
      this.terrain = textureLocationIn.equals(f_118259_);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      if (this.terrain) {
         Config.setTextureMap(this);
      }
   }

   @Override
   public void m_6704_(ResourceManager manager) {
   }

   public void m_247065_(SpriteLoader.Preparations sheetDataIn) {
      f_118261_.info("Created: {}x{}x{} {}-atlas", new Object[]{sheetDataIn.f_243669_(), sheetDataIn.f_244632_(), sheetDataIn.f_244353_(), this.f_118265_});
      TextureUtil.prepareImage(this.m_117963_(), sheetDataIn.f_244353_(), sheetDataIn.f_243669_(), sheetDataIn.f_244632_());
      this.f_276067_ = sheetDataIn.f_243669_();
      this.f_276070_ = sheetDataIn.f_244632_();
      this.f_276072_ = sheetDataIn.f_244353_();
      this.atlasWidth = sheetDataIn.f_243669_();
      this.atlasHeight = sheetDataIn.f_244632_();
      this.mipmapLevel = sheetDataIn.f_244353_();
      if (this.shaders) {
         ShadersTex.allocateTextureMapNS(this.mipmapLevel, this.atlasWidth, this.atlasHeight, this);
      }

      this.m_118329_();
      this.f_118264_ = Map.copyOf(sheetDataIn.f_243807_());
      this.f_301625_ = (TextureAtlasSprite)this.f_118264_.get(MissingTextureAtlasSprite.m_118071_());
      if (this.f_301625_ == null) {
         throw new IllegalStateException("Atlas '" + this.f_118265_ + "' (" + this.f_118264_.size() + " sprites) has no missing texture sprite");
      } else {
         List<SpriteContents> list = new ArrayList();
         List<TextureAtlasSprite.Ticker> list1 = new ArrayList();

         for (TextureAtlasSprite textureatlassprite : sheetDataIn.f_243807_().values()) {
            list.add(textureatlassprite.m_245424_());
            textureatlassprite.setTextureAtlas(this);

            try {
               textureatlassprite.m_118416_();
            } catch (Throwable var9) {
               CrashReport crashreport = CrashReport.m_127521_(var9, "Stitching texture atlas");
               CrashReportCategory crashreportcategory = crashreport.m_127514_("Texture being stitched together");
               crashreportcategory.m_128159_("Atlas path", this.f_118265_);
               crashreportcategory.m_128159_("Sprite", textureatlassprite);
               throw new ReportedException(crashreport);
            }

            TextureAtlasSprite.Ticker textureatlassprite$ticker = textureatlassprite.m_247406_();
            if (textureatlassprite$ticker != null) {
               textureatlassprite.setTicker(textureatlassprite$ticker);
               textureatlassprite.setAnimationIndex(list1.size());
               list1.add(textureatlassprite$ticker);
            }
         }

         this.f_118263_ = List.copyOf(list);
         this.f_118262_ = List.copyOf(list1);
         TextureUtils.refreshCustomSprites(this);
         Config.log("Animated sprites: " + this.f_118262_.size());
         if (Config.isMultiTexture()) {
            for (TextureAtlasSprite tas : sheetDataIn.f_243807_().values()) {
               uploadMipmapsSingle(tas);
               if (tas.spriteNormal != null) {
                  uploadMipmapsSingle(tas.spriteNormal);
               }

               if (tas.spriteSpecular != null) {
                  uploadMipmapsSingle(tas.spriteSpecular);
               }
            }

            GlStateManager._bindTexture(this.m_117963_());
         }

         if (Config.isShaders()) {
            Collection listSprites = sheetDataIn.f_243807_().values();
            if (Shaders.configNormalMap) {
               GlStateManager._bindTexture(this.getMultiTexID().norm);

               for (TextureAtlasSprite tas : listSprites) {
                  TextureAtlasSprite spriteNormal = tas.spriteNormal;
                  if (spriteNormal != null) {
                     spriteNormal.m_118416_();
                     TextureAtlasSprite.Ticker ticker = spriteNormal.m_247406_();
                     if (ticker != null) {
                        spriteNormal.setTicker(ticker);
                     }
                  }
               }
            }

            if (Shaders.configSpecularMap) {
               GlStateManager._bindTexture(this.getMultiTexID().spec);

               for (TextureAtlasSprite tasx : listSprites) {
                  TextureAtlasSprite spriteSpecular = tasx.spriteSpecular;
                  if (spriteSpecular != null) {
                     spriteSpecular.m_118416_();
                     TextureAtlasSprite.Ticker ticker = spriteSpecular.m_247406_();
                     if (ticker != null) {
                        spriteSpecular.setTicker(ticker);
                     }
                  }
               }
            }

            GlStateManager._bindTexture(this.m_117963_());
         }

         Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
         this.updateIconGrid(this.atlasWidth, this.atlasHeight);
         if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
            Config.dbg("Exporting texture map: " + this.f_118265_);
            TextureUtils.saveGlTexture(
               "debug/" + this.f_118265_.m_135815_().replaceAll("/", "_"), this.m_117963_(), this.mipmapLevel, this.atlasWidth, this.atlasHeight
            );
            if (this.shaders) {
               if (Shaders.configNormalMap) {
                  TextureUtils.saveGlTexture(
                     "debug/" + this.f_118265_.m_135815_().replaceAll("/", "_").replace(".png", "_n.png"),
                     this.multiTex.norm,
                     this.mipmapLevel,
                     this.atlasWidth,
                     this.atlasHeight
                  );
               }

               if (Shaders.configSpecularMap) {
                  TextureUtils.saveGlTexture(
                     "debug/" + this.f_118265_.m_135815_().replaceAll("/", "_").replace(".png", "_s.png"),
                     this.multiTex.spec,
                     this.mipmapLevel,
                     this.atlasWidth,
                     this.atlasHeight
                  );
               }

               GlStateManager._bindTexture(this.m_117963_());
            }
         }
      }
   }

   public void preStitch(Set<ResourceLocation> set, ResourceManager resourceManagerIn, int mipmapLevelIn) {
      this.terrain = this.f_118265_.equals(f_118259_);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      this.mipmapLevel = mipmapLevelIn;
      Config.dbg("Pre-stitch: " + this.f_118265_);
      this.textureFormat = ITextureFormat.readConfiguration();
      this.mapRegisteredSprites.clear();
      this.mapMissingSprites.clear();
      this.counterIndexInMap.reset();
      Config.dbg("Multitexture: " + Config.isMultiTexture());
      TextureUtils.registerCustomSpriteLocations(this.m_118330_(), set);
      TextureUtils.registerCustomSprites(this);
      set.addAll(this.mapRegisteredSprites.keySet());
      Set<ResourceLocation> locsEmissive = newHashSet(set, this.mapRegisteredSprites.keySet());
      EmissiveTextures.updateIcons(this, locsEmissive);
      set.addAll(this.mapRegisteredSprites.keySet());
      if (this.mipmapLevel >= 4) {
         this.mipmapLevel = this.detectMaxMipmapLevel(set, resourceManagerIn);
         Config.log("Mipmap levels: " + this.mipmapLevel);
      }

      int minSpriteSize = getMinSpriteSize(this.mipmapLevel);
      this.iconGridSize = minSpriteSize;
   }

   public void m_276079_(ResourceLocation locIn, Path pathIn) throws IOException {
      String s = locIn.m_179910_();
      TextureUtil.writeAsPNG(pathIn, s, this.m_117963_(), this.f_276072_, this.f_276067_, this.f_276070_);
      m_260988_(pathIn, s, this.f_118264_);
   }

   private static void m_260988_(Path pathIn, String nameIn, Map<ResourceLocation, TextureAtlasSprite> mapIn) {
      Path path = pathIn.resolve(nameIn + ".txt");

      try {
         Writer writer = Files.newBufferedWriter(path);

         try {
            for (Entry<ResourceLocation, TextureAtlasSprite> entry : mapIn.entrySet().stream().sorted(Entry.comparingByKey()).toList()) {
               TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)entry.getValue();
               writer.write(
                  String.format(
                     Locale.ROOT,
                     "%s\tx=%d\ty=%d\tw=%d\th=%d%n",
                     entry.getKey(),
                     textureatlassprite.m_174743_(),
                     textureatlassprite.m_174744_(),
                     textureatlassprite.m_245424_().m_246492_(),
                     textureatlassprite.m_245424_().m_245330_()
                  )
               );
            }
         } catch (Throwable var9) {
            if (writer != null) {
               try {
                  writer.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }
            }

            throw var9;
         }

         if (writer != null) {
            writer.close();
         }
      } catch (IOException var10) {
         f_118261_.warn("Failed to write file {}", path, var10);
      }
   }

   public void m_118270_() {
      boolean hasNormal = false;
      boolean hasSpecular = false;
      if (!this.f_118262_.isEmpty()) {
         this.m_117966_();
      }

      int countActive = 0;

      for (TextureAtlasSprite.Ticker textureatlassprite$ticker : this.f_118262_) {
         TextureAtlasSprite textureatlassprite = textureatlassprite$ticker.getSprite();
         if (textureatlassprite != null) {
            if (this.isAnimationEnabled(textureatlassprite)) {
               textureatlassprite$ticker.m_245385_();
               if (textureatlassprite.isAnimationActive()) {
                  countActive++;
               }

               if (textureatlassprite.spriteNormal != null) {
                  hasNormal = true;
               }

               if (textureatlassprite.spriteSpecular != null) {
                  hasSpecular = true;
               }
            }
         } else {
            textureatlassprite$ticker.m_245385_();
         }
      }

      if (Config.isShaders()) {
         if (hasNormal) {
            GlStateManager._bindTexture(this.getMultiTexID().norm);

            for (TextureAtlasSprite.Ticker textureatlassprite$tickerx : this.f_118262_) {
               TextureAtlasSprite textureatlassprite = textureatlassprite$tickerx.getSprite();
               if (textureatlassprite != null
                  && textureatlassprite.spriteNormal != null
                  && this.isAnimationEnabled(textureatlassprite)
                  && textureatlassprite.isAnimationActive()) {
                  textureatlassprite.spriteNormal.updateAnimation();
                  if (textureatlassprite.spriteNormal.isAnimationActive()) {
                     countActive++;
                  }
               }
            }
         }

         if (hasSpecular) {
            GlStateManager._bindTexture(this.getMultiTexID().spec);

            for (TextureAtlasSprite.Ticker textureatlassprite$tickerxx : this.f_118262_) {
               TextureAtlasSprite textureatlassprite = textureatlassprite$tickerxx.getSprite();
               if (textureatlassprite != null
                  && textureatlassprite.spriteSpecular != null
                  && this.isAnimationEnabled(textureatlassprite)
                  && textureatlassprite.isAnimationActive()) {
                  textureatlassprite.spriteSpecular.updateAnimation();
                  if (textureatlassprite.spriteSpecular.isAnimationActive()) {
                     countActive++;
                  }
               }
            }
         }

         if (hasNormal || hasSpecular) {
            GlStateManager._bindTexture(this.m_117963_());
         }
      }

      if (Config.isMultiTexture()) {
         for (TextureAtlasSprite.Ticker textureatlassprite$tickerxxx : this.f_118262_) {
            TextureAtlasSprite ts = textureatlassprite$tickerxxx.getSprite();
            if (ts != null && this.isAnimationEnabled(ts) && ts.isAnimationActive()) {
               countActive += updateAnimationSingle(ts);
               if (ts.spriteNormal != null) {
                  countActive += updateAnimationSingle(ts.spriteNormal);
               }

               if (ts.spriteSpecular != null) {
                  countActive += updateAnimationSingle(ts.spriteSpecular);
               }
            }
         }

         GlStateManager._bindTexture(this.m_117963_());
      }

      if (this.terrain) {
         int frameCount = Config.getMinecraft().f_91060_.getFrameCount();
         if (frameCount != this.frameCountAnimations) {
            this.countAnimationsActive = countActive;
            this.frameCountAnimations = frameCount;
         }

         if (SmartAnimations.isActive()) {
            SmartAnimations.resetSpritesRendered(this);
         }
      }
   }

   public void m_7673_() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::m_118270_);
      } else {
         this.m_118270_();
      }
   }

   public TextureAtlasSprite m_118316_(ResourceLocation location) {
      TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.f_118264_.getOrDefault(location, this.f_301625_);
      if (textureatlassprite == null) {
         throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
      } else {
         return textureatlassprite;
      }
   }

   public void m_118329_() {
      if (this.multiTexture) {
         for (TextureAtlasSprite ts : this.f_118264_.values()) {
            ts.deleteSpriteTexture();
            if (ts.spriteNormal != null) {
               ts.spriteNormal.deleteSpriteTexture();
            }

            if (ts.spriteSpecular != null) {
               ts.spriteSpecular.deleteSpriteTexture();
            }
         }
      }

      this.f_118263_.forEach(SpriteContents::close);
      this.f_118262_.forEach(TextureAtlasSprite.Ticker::close);
      this.f_118263_ = List.of();
      this.f_118262_ = List.of();
      this.f_118264_ = Map.of();
      this.f_301625_ = null;
   }

   public ResourceLocation m_118330_() {
      return this.f_118265_;
   }

   public int m_245285_() {
      return this.f_118266_;
   }

   public int m_276092_() {
      return this.f_276067_;
   }

   public int m_276095_() {
      return this.f_276070_;
   }

   public void m_247255_(SpriteLoader.Preparations preparationsIn) {
      this.m_117960_(false, preparationsIn.f_244353_() > 0);
   }

   public static boolean isAbsoluteLocation(ResourceLocation loc) {
      String path = loc.m_135815_();
      return isAbsoluteLocationPath(path);
   }

   private static boolean isAbsoluteLocationPath(String resPath) {
      String path = resPath.toLowerCase();
      return path.startsWith("optifine/");
   }

   public TextureAtlasSprite getRegisteredSprite(String name) {
      ResourceLocation loc = new ResourceLocation(name);
      return this.getRegisteredSprite(loc);
   }

   public TextureAtlasSprite getRegisteredSprite(ResourceLocation loc) {
      return (TextureAtlasSprite)this.mapRegisteredSprites.get(loc);
   }

   public TextureAtlasSprite getUploadedSprite(String name) {
      ResourceLocation loc = new ResourceLocation(name);
      return this.getUploadedSprite(loc);
   }

   public TextureAtlasSprite getUploadedSprite(ResourceLocation loc) {
      return (TextureAtlasSprite)this.f_118264_.get(loc);
   }

   private boolean isAnimationEnabled(TextureAtlasSprite ts) {
      if (!this.terrain) {
         return true;
      } else if (ts == TextureUtils.iconWaterStill || ts == TextureUtils.iconWaterFlow) {
         return Config.isAnimatedWater();
      } else if (ts == TextureUtils.iconLavaStill || ts == TextureUtils.iconLavaFlow) {
         return Config.isAnimatedLava();
      } else if (ts == TextureUtils.iconFireLayer0 || ts == TextureUtils.iconFireLayer1) {
         return Config.isAnimatedFire();
      } else if (ts == TextureUtils.iconSoulFireLayer0 || ts == TextureUtils.iconSoulFireLayer1) {
         return Config.isAnimatedFire();
      } else if (ts == TextureUtils.iconCampFire || ts == TextureUtils.iconCampFireLogLit) {
         return Config.isAnimatedFire();
      } else if (ts == TextureUtils.iconSoulCampFire || ts == TextureUtils.iconSoulCampFireLogLit) {
         return Config.isAnimatedFire();
      } else {
         return ts == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedTerrain();
      }
   }

   private static void uploadMipmapsSingle(TextureAtlasSprite tas) {
      TextureAtlasSprite ss = tas.spriteSingle;
      if (ss != null) {
         ss.setAnimationIndex(tas.getAnimationIndex());
         TextureAtlasSprite.Ticker ticker = ss.m_247406_();
         if (ticker != null) {
            ss.setTicker(ticker);
         }

         tas.bindSpriteTexture();

         try {
            ss.m_118416_();
         } catch (Exception var4) {
            Config.dbg("Error uploading sprite single: " + ss + ", parent: " + tas);
            var4.printStackTrace();
         }
      }
   }

   private static int updateAnimationSingle(TextureAtlasSprite tas) {
      TextureAtlasSprite spriteSingle = tas.spriteSingle;
      if (spriteSingle != null) {
         tas.bindSpriteTexture();
         NativeImage.setUpdateBlurMipmap(false);
         spriteSingle.updateAnimation();
         NativeImage.setUpdateBlurMipmap(true);
         if (spriteSingle.isAnimationActive()) {
            return 1;
         }
      }

      return 0;
   }

   public int getCountRegisteredSprites() {
      return this.counterIndexInMap.getValue();
   }

   private int detectMaxMipmapLevel(Set<ResourceLocation> setSpriteLocations, ResourceManager rm) {
      int minSize = this.detectMinimumSpriteSize(setSpriteLocations, rm, 20);
      if (minSize < 16) {
         minSize = 16;
      }

      minSize = Mth.m_14125_(minSize);
      if (minSize > 16) {
         Config.log("Sprite size: " + minSize);
      }

      int minLevel = Mth.m_14173_(minSize);
      if (minLevel < 4) {
         minLevel = 4;
      }

      return minLevel;
   }

   private int detectMinimumSpriteSize(Set<ResourceLocation> setSpriteLocations, ResourceManager rm, int percentScale) {
      Map mapSizeCounts = new HashMap();

      for (ResourceLocation loc : setSpriteLocations) {
         ResourceLocation locComplete = this.getSpritePath(loc);

         try {
            Resource res = rm.m_215593_(locComplete);
            if (res != null) {
               InputStream in = res.m_215507_();
               if (in != null) {
                  Dimension dim = TextureUtils.getImageSize(in, "png");
                  in.close();
                  if (dim != null) {
                     int width = dim.width;
                     int width2 = Mth.m_14125_(width);
                     if (!mapSizeCounts.containsKey(width2)) {
                        mapSizeCounts.put(width2, 1);
                     } else {
                        int count = (Integer)mapSizeCounts.get(width2);
                        mapSizeCounts.put(width2, count + 1);
                     }
                  }
               }
            }
         } catch (Exception var14) {
         }
      }

      int countSprites = 0;
      Set setSizes = mapSizeCounts.keySet();
      Set setSizesSorted = new TreeSet(setSizes);

      for (int size : setSizesSorted) {
         int count = (Integer)mapSizeCounts.get(size);
         countSprites += count;
      }

      int minSize = 16;
      int countScale = 0;
      int countScaleMax = countSprites * percentScale / 100;

      for (int size : setSizesSorted) {
         int count = (Integer)mapSizeCounts.get(size);
         countScale += count;
         if (size > minSize) {
            minSize = size;
         }

         if (countScale > countScaleMax) {
            return minSize;
         }
      }

      return minSize;
   }

   private static int getMinSpriteSize(int mipmapLevels) {
      int minSize = 1 << mipmapLevels;
      if (minSize < 8) {
         minSize = 8;
      }

      return minSize;
   }

   private static FrameSize fixSpriteSize(FrameSize info, int minSpriteSize) {
      if (info.f_244129_() >= minSpriteSize && info.f_244503_() >= minSpriteSize) {
         return info;
      } else {
         int widthNew = Math.max(info.f_244129_(), minSpriteSize);
         int heightNew = Math.max(info.f_244503_(), minSpriteSize);
         return new FrameSize(widthNew, heightNew);
      }
   }

   public boolean isTextureBound() {
      int boundTexId = GlStateManager.getBoundTexture();
      int texId = this.m_117963_();
      return boundTexId == texId;
   }

   private void updateIconGrid(int sheetWidth, int sheetHeight) {
      this.iconGridCountX = -1;
      this.iconGridCountY = -1;
      this.iconGrid = null;
      if (this.iconGridSize > 0) {
         this.iconGridCountX = sheetWidth / this.iconGridSize;
         this.iconGridCountY = sheetHeight / this.iconGridSize;
         this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
         this.iconGridSizeU = 1.0 / (double)this.iconGridCountX;
         this.iconGridSizeV = 1.0 / (double)this.iconGridCountY;

         for (TextureAtlasSprite ts : this.f_118264_.values()) {
            double deltaU = 0.5 / (double)sheetWidth;
            double deltaV = 0.5 / (double)sheetHeight;
            double uMin = (double)Math.min(ts.m_118409_(), ts.m_118410_()) + deltaU;
            double vMin = (double)Math.min(ts.m_118411_(), ts.m_118412_()) + deltaV;
            double uMax = (double)Math.max(ts.m_118409_(), ts.m_118410_()) - deltaU;
            double vMax = (double)Math.max(ts.m_118411_(), ts.m_118412_()) - deltaV;
            int iuMin = (int)(uMin / this.iconGridSizeU);
            int ivMin = (int)(vMin / this.iconGridSizeV);
            int iuMax = (int)(uMax / this.iconGridSizeU);
            int ivMax = (int)(vMax / this.iconGridSizeV);

            for (int iu = iuMin; iu <= iuMax; iu++) {
               if (iu >= 0 && iu < this.iconGridCountX) {
                  for (int iv = ivMin; iv <= ivMax; iv++) {
                     if (iv >= 0 && iv < this.iconGridCountX) {
                        int index = iv * this.iconGridCountX + iu;
                        this.iconGrid[index] = ts;
                     } else {
                        Config.warn("Invalid grid V: " + iv + ", icon: " + ts.getName());
                     }
                  }
               } else {
                  Config.warn("Invalid grid U: " + iu + ", icon: " + ts.getName());
               }
            }
         }
      }
   }

   public TextureAtlasSprite getIconByUV(double u, double v) {
      if (this.iconGrid == null) {
         return null;
      } else {
         int iu = (int)(u / this.iconGridSizeU);
         int iv = (int)(v / this.iconGridSizeV);
         int index = iv * this.iconGridCountX + iu;
         return index >= 0 && index <= this.iconGrid.length ? this.iconGrid[index] : null;
      }
   }

   public int getCountAnimations() {
      return this.f_118262_.size();
   }

   public int getCountAnimationsActive() {
      return this.countAnimationsActive;
   }

   public int getIconGridSize() {
      return this.iconGridSize;
   }

   public TextureAtlasSprite registerSprite(ResourceLocation location) {
      if (location == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         TextureAtlasSprite sprite = (TextureAtlasSprite)this.mapRegisteredSprites.get(location);
         if (sprite != null) {
            return sprite;
         } else {
            sprite = new TextureAtlasSprite(this.f_118265_, location);
            sprite.setTextureAtlas(this);
            this.mapRegisteredSprites.put(location, sprite);
            sprite.updateIndexInMap(this.counterIndexInMap);
            return sprite;
         }
      }
   }

   public Collection<TextureAtlasSprite> getRegisteredSprites() {
      return Collections.unmodifiableCollection(this.mapRegisteredSprites.values());
   }

   public Collection<ResourceLocation> getRegisteredSpriteNames() {
      return Collections.unmodifiableCollection(this.mapRegisteredSprites.keySet());
   }

   public boolean isTerrain() {
      return this.terrain;
   }

   public CounterInt getCounterIndexInMap() {
      return this.counterIndexInMap;
   }

   private void onSpriteMissing(ResourceLocation loc) {
      TextureAtlasSprite sprite = (TextureAtlasSprite)this.mapRegisteredSprites.get(loc);
      if (sprite != null) {
         this.mapMissingSprites.put(loc, sprite);
      }
   }

   private static <T> Set<T> newHashSet(Set<T> set1, Set<T> set2) {
      Set<T> set = new HashSet();
      set.addAll(set1);
      set.addAll(set2);
      return set;
   }

   public int getMipmapLevel() {
      return this.mipmapLevel;
   }

   public boolean isMipmaps() {
      return this.mipmapLevel > 0;
   }

   public ITextureFormat getTextureFormat() {
      return this.textureFormat;
   }

   public IColorBlender getShadersColorBlender(ShadersTextureType typeIn) {
      if (typeIn == null) {
         return null;
      } else {
         return (IColorBlender)(this.textureFormat != null ? this.textureFormat.getColorBlender(typeIn) : new ColorBlenderLinear());
      }
   }

   public boolean isTextureBlend(ShadersTextureType typeIn) {
      if (typeIn == null) {
         return true;
      } else {
         return this.textureFormat != null ? this.textureFormat.isTextureBlend(typeIn) : true;
      }
   }

   public boolean isNormalBlend() {
      return this.isTextureBlend(ShadersTextureType.NORMAL);
   }

   public boolean isSpecularBlend() {
      return this.isTextureBlend(ShadersTextureType.SPECULAR);
   }

   public ResourceLocation getSpritePath(ResourceLocation location) {
      return isAbsoluteLocation(location)
         ? new ResourceLocation(location.m_135827_(), location.m_135815_() + ".png")
         : new ResourceLocation(location.m_135827_(), String.format(Locale.ROOT, "textures/%s%s", location.m_135815_(), ".png"));
   }

   public String toString() {
      return this.f_118265_ + "";
   }

   public Set<ResourceLocation> getTextureLocations() {
      return Collections.unmodifiableSet(this.f_118264_.keySet());
   }
}
