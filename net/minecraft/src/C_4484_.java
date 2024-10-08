package net.minecraft.src;

import com.mojang.blaze3d.platform.GlStateManager;
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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;
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

public class C_4484_ extends C_4468_ implements C_276066_, C_4491_ {
   private static final Logger f_118261_ = LogUtils.getLogger();
   /** @deprecated */
   @Deprecated
   public static final C_5265_ f_118259_;
   /** @deprecated */
   @Deprecated
   public static final C_5265_ f_118260_;
   private List f_118263_ = List.of();
   private List f_118262_ = List.of();
   private Map f_118264_ = Map.of();
   @Nullable
   private C_4486_ f_301625_;
   private final C_5265_ f_118265_;
   private final int f_118266_;
   private int f_276067_;
   private int f_276070_;
   private int f_276072_;
   private Map mapRegisteredSprites = new LinkedHashMap();
   private Map mapMissingSprites = new LinkedHashMap();
   private C_4486_[] iconGrid = null;
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

   public C_4484_(C_5265_ textureLocationIn) {
      this.f_118265_ = textureLocationIn;
      this.f_118266_ = RenderSystem.maxSupportedTextureSize();
      this.terrain = textureLocationIn.equals(f_118259_);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      if (this.terrain) {
         Config.setTextureMap(this);
      }

   }

   public void m_6704_(C_77_ manager) {
   }

   public void m_247065_(C_243537_.C_243503_ sheetDataIn) {
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
      this.f_301625_ = (C_4486_)this.f_118264_.get(C_4473_.m_118071_());
      if (this.f_301625_ == null) {
         String var10002 = String.valueOf(this.f_118265_);
         throw new IllegalStateException("Atlas '" + var10002 + "' (" + this.f_118264_.size() + " sprites) has no missing texture sprite");
      } else {
         List list = new ArrayList();
         List list1 = new ArrayList();
         Iterator var4 = sheetDataIn.f_243807_().values().iterator();

         while(var4.hasNext()) {
            C_4486_ textureatlassprite = (C_4486_)var4.next();
            list.add(textureatlassprite.m_245424_());
            textureatlassprite.setTextureAtlas(this);

            try {
               textureatlassprite.m_118416_();
            } catch (Throwable var9) {
               C_4883_ crashreport = C_4883_.m_127521_(var9, "Stitching texture atlas");
               C_4909_ crashreportcategory = crashreport.m_127514_("Texture being stitched together");
               crashreportcategory.m_128159_("Atlas path", this.f_118265_);
               crashreportcategory.m_128159_("Sprite", textureatlassprite);
               throw new C_5204_(crashreport);
            }

            C_4486_.C_243545_ textureatlassprite$ticker = textureatlassprite.m_247406_();
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
         Collection listSprites;
         Iterator it;
         C_4486_ tas;
         if (Config.isMultiTexture()) {
            listSprites = sheetDataIn.f_243807_().values();
            it = listSprites.iterator();

            while(it.hasNext()) {
               tas = (C_4486_)it.next();
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
            listSprites = sheetDataIn.f_243807_().values();
            C_4486_ spriteSpecular;
            C_4486_.C_243545_ ticker;
            if (Shaders.configNormalMap) {
               GlStateManager._bindTexture(this.getMultiTexID().norm);
               it = listSprites.iterator();

               while(it.hasNext()) {
                  tas = (C_4486_)it.next();
                  spriteSpecular = tas.spriteNormal;
                  if (spriteSpecular != null) {
                     spriteSpecular.m_118416_();
                     ticker = spriteSpecular.m_247406_();
                     if (ticker != null) {
                        spriteSpecular.setTicker(ticker);
                     }
                  }
               }
            }

            if (Shaders.configSpecularMap) {
               GlStateManager._bindTexture(this.getMultiTexID().spec);
               it = listSprites.iterator();

               while(it.hasNext()) {
                  tas = (C_4486_)it.next();
                  spriteSpecular = tas.spriteSpecular;
                  if (spriteSpecular != null) {
                     spriteSpecular.m_118416_();
                     ticker = spriteSpecular.m_247406_();
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
            Config.dbg("Exporting texture map: " + String.valueOf(this.f_118265_));
            TextureUtils.saveGlTexture("debug/" + this.f_118265_.m_135815_().replaceAll("/", "_"), this.m_117963_(), this.mipmapLevel, this.atlasWidth, this.atlasHeight);
            if (this.shaders) {
               if (Shaders.configNormalMap) {
                  TextureUtils.saveGlTexture("debug/" + this.f_118265_.m_135815_().replaceAll("/", "_").replace(".png", "_n.png"), this.multiTex.norm, this.mipmapLevel, this.atlasWidth, this.atlasHeight);
               }

               if (Shaders.configSpecularMap) {
                  TextureUtils.saveGlTexture("debug/" + this.f_118265_.m_135815_().replaceAll("/", "_").replace(".png", "_s.png"), this.multiTex.spec, this.mipmapLevel, this.atlasWidth, this.atlasHeight);
               }

               GlStateManager._bindTexture(this.m_117963_());
            }
         }

      }
   }

   public void preStitch(Set set, C_77_ resourceManagerIn, int mipmapLevelIn) {
      this.terrain = this.f_118265_.equals(f_118259_);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      this.mipmapLevel = mipmapLevelIn;
      Config.dbg("Pre-stitch: " + String.valueOf(this.f_118265_));
      this.textureFormat = ITextureFormat.readConfiguration();
      this.mapRegisteredSprites.clear();
      this.mapMissingSprites.clear();
      this.counterIndexInMap.reset();
      Config.dbg("Multitexture: " + Config.isMultiTexture());
      TextureUtils.registerCustomSpriteLocations(this.m_118330_(), set);
      TextureUtils.registerCustomSprites(this);
      set.addAll(this.mapRegisteredSprites.keySet());
      Set locsEmissive = newHashSet(set, this.mapRegisteredSprites.keySet());
      EmissiveTextures.updateIcons(this, locsEmissive);
      set.addAll(this.mapRegisteredSprites.keySet());
      if (this.mipmapLevel >= 4) {
         this.mipmapLevel = this.detectMaxMipmapLevel(set, resourceManagerIn);
         Config.log("Mipmap levels: " + this.mipmapLevel);
      }

      int minSpriteSize = getMinSpriteSize(this.mipmapLevel);
      this.iconGridSize = minSpriteSize;
   }

   public void m_276079_(C_5265_ locIn, Path pathIn) throws IOException {
      String s = locIn.m_179910_();
      TextureUtil.writeAsPNG(pathIn, s, this.m_117963_(), this.f_276072_, this.f_276067_, this.f_276070_);
      m_260988_(pathIn, s, this.f_118264_);
   }

   private static void m_260988_(Path pathIn, String nameIn, Map mapIn) {
      Path path = pathIn.resolve(nameIn + ".txt");

      try {
         Writer writer = Files.newBufferedWriter(path);

         try {
            Iterator var5 = mapIn.entrySet().stream().sorted(Entry.comparingByKey()).toList().iterator();

            while(var5.hasNext()) {
               Map.Entry entry = (Map.Entry)var5.next();
               C_4486_ textureatlassprite = (C_4486_)entry.getValue();
               writer.write(String.format(Locale.ROOT, "%s\tx=%d\ty=%d\tw=%d\th=%d%n", entry.getKey(), textureatlassprite.m_174743_(), textureatlassprite.m_174744_(), textureatlassprite.m_245424_().m_246492_(), textureatlassprite.m_245424_().m_245330_()));
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
      Iterator var4 = this.f_118262_.iterator();

      C_4486_.C_243545_ textureatlassprite$ticker;
      C_4486_ ts;
      while(var4.hasNext()) {
         textureatlassprite$ticker = (C_4486_.C_243545_)var4.next();
         ts = textureatlassprite$ticker.getSprite();
         if (ts != null) {
            if (this.isAnimationEnabled(ts)) {
               textureatlassprite$ticker.m_245385_();
               if (ts.isAnimationActive()) {
                  ++countActive;
               }

               if (ts.spriteNormal != null) {
                  hasNormal = true;
               }

               if (ts.spriteSpecular != null) {
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
            var4 = this.f_118262_.iterator();

            while(var4.hasNext()) {
               textureatlassprite$ticker = (C_4486_.C_243545_)var4.next();
               ts = textureatlassprite$ticker.getSprite();
               if (ts != null && ts.spriteNormal != null && this.isAnimationEnabled(ts) && ts.isAnimationActive()) {
                  ts.spriteNormal.updateAnimation();
                  if (ts.spriteNormal.isAnimationActive()) {
                     ++countActive;
                  }
               }
            }
         }

         if (hasSpecular) {
            GlStateManager._bindTexture(this.getMultiTexID().spec);
            var4 = this.f_118262_.iterator();

            while(var4.hasNext()) {
               textureatlassprite$ticker = (C_4486_.C_243545_)var4.next();
               ts = textureatlassprite$ticker.getSprite();
               if (ts != null && ts.spriteSpecular != null && this.isAnimationEnabled(ts) && ts.isAnimationActive()) {
                  ts.spriteSpecular.updateAnimation();
                  if (ts.spriteSpecular.isAnimationActive()) {
                     ++countActive;
                  }
               }
            }
         }

         if (hasNormal || hasSpecular) {
            GlStateManager._bindTexture(this.m_117963_());
         }
      }

      if (Config.isMultiTexture()) {
         var4 = this.f_118262_.iterator();

         while(var4.hasNext()) {
            textureatlassprite$ticker = (C_4486_.C_243545_)var4.next();
            ts = textureatlassprite$ticker.getSprite();
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

   public C_4486_ m_118316_(C_5265_ location) {
      C_4486_ textureatlassprite = (C_4486_)this.f_118264_.getOrDefault(location, this.f_301625_);
      if (textureatlassprite == null) {
         throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
      } else {
         return textureatlassprite;
      }
   }

   public void m_118329_() {
      if (this.multiTexture) {
         Iterator it = this.f_118264_.values().iterator();

         while(it.hasNext()) {
            C_4486_ ts = (C_4486_)it.next();
            ts.deleteSpriteTexture();
            if (ts.spriteNormal != null) {
               ts.spriteNormal.deleteSpriteTexture();
            }

            if (ts.spriteSpecular != null) {
               ts.spriteSpecular.deleteSpriteTexture();
            }
         }
      }

      this.f_118263_.forEach(C_243582_::close);
      this.f_118262_.forEach(C_4486_.C_243545_::close);
      this.f_118263_ = List.of();
      this.f_118262_ = List.of();
      this.f_118264_ = Map.of();
      this.f_301625_ = null;
   }

   public C_5265_ m_118330_() {
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

   public void m_247255_(C_243537_.C_243503_ preparationsIn) {
      this.m_117960_(false, preparationsIn.f_244353_() > 0);
   }

   public static boolean isAbsoluteLocation(C_5265_ loc) {
      String path = loc.m_135815_();
      return isAbsoluteLocationPath(path);
   }

   private static boolean isAbsoluteLocationPath(String resPath) {
      String path = resPath.toLowerCase();
      return path.startsWith("optifine/");
   }

   public C_4486_ getRegisteredSprite(String name) {
      C_5265_ loc = new C_5265_(name);
      return this.getRegisteredSprite(loc);
   }

   public C_4486_ getRegisteredSprite(C_5265_ loc) {
      return (C_4486_)this.mapRegisteredSprites.get(loc);
   }

   public C_4486_ getUploadedSprite(String name) {
      C_5265_ loc = new C_5265_(name);
      return this.getUploadedSprite(loc);
   }

   public C_4486_ getUploadedSprite(C_5265_ loc) {
      return (C_4486_)this.f_118264_.get(loc);
   }

   private boolean isAnimationEnabled(C_4486_ ts) {
      if (!this.terrain) {
         return true;
      } else if (ts != TextureUtils.iconWaterStill && ts != TextureUtils.iconWaterFlow) {
         if (ts != TextureUtils.iconLavaStill && ts != TextureUtils.iconLavaFlow) {
            if (ts != TextureUtils.iconFireLayer0 && ts != TextureUtils.iconFireLayer1) {
               if (ts != TextureUtils.iconSoulFireLayer0 && ts != TextureUtils.iconSoulFireLayer1) {
                  if (ts != TextureUtils.iconCampFire && ts != TextureUtils.iconCampFireLogLit) {
                     if (ts != TextureUtils.iconSoulCampFire && ts != TextureUtils.iconSoulCampFireLogLit) {
                        return ts == TextureUtils.iconPortal ? Config.isAnimatedPortal() : Config.isAnimatedTerrain();
                     } else {
                        return Config.isAnimatedFire();
                     }
                  } else {
                     return Config.isAnimatedFire();
                  }
               } else {
                  return Config.isAnimatedFire();
               }
            } else {
               return Config.isAnimatedFire();
            }
         } else {
            return Config.isAnimatedLava();
         }
      } else {
         return Config.isAnimatedWater();
      }
   }

   private static void uploadMipmapsSingle(C_4486_ tas) {
      C_4486_ ss = tas.spriteSingle;
      if (ss != null) {
         ss.setAnimationIndex(tas.getAnimationIndex());
         C_4486_.C_243545_ ticker = ss.m_247406_();
         if (ticker != null) {
            ss.setTicker(ticker);
         }

         tas.bindSpriteTexture();

         try {
            ss.m_118416_();
         } catch (Exception var4) {
            String var10000 = String.valueOf(ss);
            Config.dbg("Error uploading sprite single: " + var10000 + ", parent: " + String.valueOf(tas));
            var4.printStackTrace();
         }
      }

   }

   private static int updateAnimationSingle(C_4486_ tas) {
      C_4486_ spriteSingle = tas.spriteSingle;
      if (spriteSingle != null) {
         tas.bindSpriteTexture();
         C_3148_.setUpdateBlurMipmap(false);
         spriteSingle.updateAnimation();
         C_3148_.setUpdateBlurMipmap(true);
         if (spriteSingle.isAnimationActive()) {
            return 1;
         }
      }

      return 0;
   }

   public int getCountRegisteredSprites() {
      return this.counterIndexInMap.getValue();
   }

   private int detectMaxMipmapLevel(Set setSpriteLocations, C_77_ rm) {
      int minSize = this.detectMinimumSpriteSize(setSpriteLocations, rm, 20);
      if (minSize < 16) {
         minSize = 16;
      }

      minSize = C_188_.m_14125_(minSize);
      if (minSize > 16) {
         Config.log("Sprite size: " + minSize);
      }

      int minLevel = C_188_.m_14173_(minSize);
      if (minLevel < 4) {
         minLevel = 4;
      }

      return minLevel;
   }

   private int detectMinimumSpriteSize(Set setSpriteLocations, C_77_ rm, int percentScale) {
      Map mapSizeCounts = new HashMap();
      Iterator it = setSpriteLocations.iterator();

      int width2;
      int count;
      while(it.hasNext()) {
         C_5265_ loc = (C_5265_)it.next();
         C_5265_ locComplete = this.getSpritePath(loc);

         try {
            C_76_ res = rm.getResourceOrThrow(locComplete);
            if (res != null) {
               InputStream in = res.m_215507_();
               if (in != null) {
                  Dimension dim = TextureUtils.getImageSize(in, "png");
                  in.close();
                  if (dim != null) {
                     int width = dim.width;
                     width2 = C_188_.m_14125_(width);
                     if (!mapSizeCounts.containsKey(width2)) {
                        mapSizeCounts.put(width2, 1);
                     } else {
                        count = (Integer)mapSizeCounts.get(width2);
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

      int countScale;
      int countScaleMax;
      for(Iterator it = setSizesSorted.iterator(); it.hasNext(); countSprites += countScaleMax) {
         countScale = (Integer)it.next();
         countScaleMax = (Integer)mapSizeCounts.get(countScale);
      }

      int minSize = 16;
      countScale = 0;
      countScaleMax = countSprites * percentScale / 100;
      Iterator it = setSizesSorted.iterator();

      do {
         if (!it.hasNext()) {
            return minSize;
         }

         width2 = (Integer)it.next();
         count = (Integer)mapSizeCounts.get(width2);
         countScale += count;
         if (width2 > minSize) {
            minSize = width2;
         }
      } while(countScale <= countScaleMax);

      return minSize;
   }

   private static int getMinSpriteSize(int mipmapLevels) {
      int minSize = 1 << mipmapLevels;
      if (minSize < 8) {
         minSize = 8;
      }

      return minSize;
   }

   private static C_243504_ fixSpriteSize(C_243504_ info, int minSpriteSize) {
      if (info.f_244129_() >= minSpriteSize && info.f_244503_() >= minSpriteSize) {
         return info;
      } else {
         int widthNew = Math.max(info.f_244129_(), minSpriteSize);
         int heightNew = Math.max(info.f_244503_(), minSpriteSize);
         C_243504_ infoNew = new C_243504_(widthNew, heightNew);
         return infoNew;
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
         this.iconGrid = new C_4486_[this.iconGridCountX * this.iconGridCountY];
         this.iconGridSizeU = 1.0 / (double)this.iconGridCountX;
         this.iconGridSizeV = 1.0 / (double)this.iconGridCountY;
         Iterator it = this.f_118264_.values().iterator();

         while(it.hasNext()) {
            C_4486_ ts = (C_4486_)it.next();
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

            for(int iu = iuMin; iu <= iuMax; ++iu) {
               if (iu >= 0 && iu < this.iconGridCountX) {
                  for(int iv = ivMin; iv <= ivMax; ++iv) {
                     if (iv >= 0 && iv < this.iconGridCountX) {
                        int index = iv * this.iconGridCountX + iu;
                        this.iconGrid[index] = ts;
                     } else {
                        Config.warn("Invalid grid V: " + iv + ", icon: " + String.valueOf(ts.getName()));
                     }
                  }
               } else {
                  Config.warn("Invalid grid U: " + iu + ", icon: " + String.valueOf(ts.getName()));
               }
            }
         }

      }
   }

   public C_4486_ getIconByUV(double u, double v) {
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

   public C_4486_ registerSprite(C_5265_ location) {
      if (location == null) {
         throw new IllegalArgumentException("Location cannot be null!");
      } else {
         C_4486_ sprite = (C_4486_)this.mapRegisteredSprites.get(location);
         if (sprite != null) {
            return sprite;
         } else {
            sprite = new C_4486_(this.f_118265_, location);
            sprite.setTextureAtlas(this);
            this.mapRegisteredSprites.put(location, sprite);
            sprite.updateIndexInMap(this.counterIndexInMap);
            return sprite;
         }
      }
   }

   public Collection getRegisteredSprites() {
      return Collections.unmodifiableCollection(this.mapRegisteredSprites.values());
   }

   public Collection getRegisteredSpriteNames() {
      return Collections.unmodifiableCollection(this.mapRegisteredSprites.keySet());
   }

   public boolean isTerrain() {
      return this.terrain;
   }

   public CounterInt getCounterIndexInMap() {
      return this.counterIndexInMap;
   }

   private void onSpriteMissing(C_5265_ loc) {
      C_4486_ sprite = (C_4486_)this.mapRegisteredSprites.get(loc);
      if (sprite != null) {
         this.mapMissingSprites.put(loc, sprite);
      }
   }

   private static Set newHashSet(Set set1, Set set2) {
      Set set = new HashSet();
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

   public C_5265_ getSpritePath(C_5265_ location) {
      return isAbsoluteLocation(location) ? new C_5265_(location.m_135827_(), location.m_135815_() + ".png") : new C_5265_(location.m_135827_(), String.format(Locale.ROOT, "textures/%s%s", location.m_135815_(), ".png"));
   }

   public String toString() {
      return "" + String.valueOf(this.f_118265_);
   }

   public Set getTextureLocations() {
      return Collections.unmodifiableSet(this.f_118264_.keySet());
   }

   static {
      f_118259_ = C_1274_.f_39692_;
      f_118260_ = C_5265_.m_340282_("textures/atlas/particles.png");
   }
}
