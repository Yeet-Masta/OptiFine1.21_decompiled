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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.src.C_1274_;
import net.minecraft.src.C_243504_;
import net.minecraft.src.C_276066_;
import net.minecraft.src.C_4491_;
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
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

public class TextureAtlas extends AbstractTexture implements C_276066_, C_4491_ {
   private static final Logger g = LogUtils.getLogger();
   @Deprecated
   public static final ResourceLocation e = C_1274_.x;
   @Deprecated
   public static final ResourceLocation f = ResourceLocation.b("textures/atlas/particles.png");
   private List<SpriteContents> h = List.of();
   private List<TextureAtlasSprite.a> i = List.of();
   private Map<ResourceLocation, TextureAtlasSprite> j = Map.of();
   @Nullable
   private TextureAtlasSprite k;
   private final ResourceLocation l;
   private final int m;
   private int n;
   private int o;
   private int p;
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
      this.l = textureLocationIn;
      this.m = RenderSystem.maxSupportedTextureSize();
      this.terrain = textureLocationIn.equals(e);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      if (this.terrain) {
         Config.setTextureMap(this);
      }
   }

   @Override
   public void a(C_77_ manager) {
   }

   public void a(SpriteLoader.a sheetDataIn) {
      g.info("Created: {}x{}x{} {}-atlas", new Object[]{sheetDataIn.b(), sheetDataIn.c(), sheetDataIn.d(), this.l});
      TextureUtil.prepareImage(this.a(), sheetDataIn.d(), sheetDataIn.b(), sheetDataIn.c());
      this.n = sheetDataIn.b();
      this.o = sheetDataIn.c();
      this.p = sheetDataIn.d();
      this.atlasWidth = sheetDataIn.b();
      this.atlasHeight = sheetDataIn.c();
      this.mipmapLevel = sheetDataIn.d();
      if (this.shaders) {
         ShadersTex.allocateTextureMapNS(this.mipmapLevel, this.atlasWidth, this.atlasHeight, this);
      }

      this.f();
      this.j = Map.copyOf(sheetDataIn.f());
      this.k = (TextureAtlasSprite)this.j.get(MissingTextureAtlasSprite.b());
      if (this.k == null) {
         throw new IllegalStateException("Atlas '" + this.l + "' (" + this.j.size() + " sprites) has no missing texture sprite");
      } else {
         List<SpriteContents> list = new ArrayList();
         List<TextureAtlasSprite.a> list1 = new ArrayList();

         for (TextureAtlasSprite textureatlassprite : sheetDataIn.f().values()) {
            list.add(textureatlassprite.e());
            textureatlassprite.setTextureAtlas(this);

            try {
               textureatlassprite.j();
            } catch (Throwable var9) {
               CrashReport crashreport = CrashReport.a(var9, "Stitching texture atlas");
               C_4909_ crashreportcategory = crashreport.a("Texture being stitched together");
               crashreportcategory.m_128159_("Atlas path", this.l);
               crashreportcategory.m_128159_("Sprite", textureatlassprite);
               throw new C_5204_(crashreport);
            }

            TextureAtlasSprite.a textureatlassprite$ticker = textureatlassprite.f();
            if (textureatlassprite$ticker != null) {
               textureatlassprite.setTicker(textureatlassprite$ticker);
               textureatlassprite.setAnimationIndex(list1.size());
               list1.add(textureatlassprite$ticker);
            }
         }

         this.h = List.copyOf(list);
         this.i = List.copyOf(list1);
         TextureUtils.refreshCustomSprites(this);
         Config.log("Animated sprites: " + this.i.size());
         if (Config.isMultiTexture()) {
            for (TextureAtlasSprite tas : sheetDataIn.f().values()) {
               uploadMipmapsSingle(tas);
               if (tas.spriteNormal != null) {
                  uploadMipmapsSingle(tas.spriteNormal);
               }

               if (tas.spriteSpecular != null) {
                  uploadMipmapsSingle(tas.spriteSpecular);
               }
            }

            GlStateManager._bindTexture(this.a());
         }

         if (Config.isShaders()) {
            Collection listSprites = sheetDataIn.f().values();
            if (Shaders.configNormalMap) {
               GlStateManager._bindTexture(this.getMultiTexID().norm);

               for (TextureAtlasSprite tas : listSprites) {
                  TextureAtlasSprite spriteNormal = tas.spriteNormal;
                  if (spriteNormal != null) {
                     spriteNormal.j();
                     TextureAtlasSprite.a ticker = spriteNormal.f();
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
                     spriteSpecular.j();
                     TextureAtlasSprite.a ticker = spriteSpecular.f();
                     if (ticker != null) {
                        spriteSpecular.setTicker(ticker);
                     }
                  }
               }
            }

            GlStateManager._bindTexture(this.a());
         }

         Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, this);
         this.updateIconGrid(this.atlasWidth, this.atlasHeight);
         if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
            Config.dbg("Exporting texture map: " + this.l);
            TextureUtils.saveGlTexture("debug/" + this.l.a().replaceAll("/", "_"), this.a(), this.mipmapLevel, this.atlasWidth, this.atlasHeight);
            if (this.shaders) {
               if (Shaders.configNormalMap) {
                  TextureUtils.saveGlTexture(
                     "debug/" + this.l.a().replaceAll("/", "_").replace(".png", "_n.png"),
                     this.multiTex.norm,
                     this.mipmapLevel,
                     this.atlasWidth,
                     this.atlasHeight
                  );
               }

               if (Shaders.configSpecularMap) {
                  TextureUtils.saveGlTexture(
                     "debug/" + this.l.a().replaceAll("/", "_").replace(".png", "_s.png"),
                     this.multiTex.spec,
                     this.mipmapLevel,
                     this.atlasWidth,
                     this.atlasHeight
                  );
               }

               GlStateManager._bindTexture(this.a());
            }
         }
      }
   }

   public void preStitch(Set<ResourceLocation> set, C_77_ resourceManagerIn, int mipmapLevelIn) {
      this.terrain = this.l.equals(e);
      this.shaders = Config.isShaders();
      this.multiTexture = Config.isMultiTexture();
      this.mipmapLevel = mipmapLevelIn;
      Config.dbg("Pre-stitch: " + this.l);
      this.textureFormat = ITextureFormat.readConfiguration();
      this.mapRegisteredSprites.clear();
      this.mapMissingSprites.clear();
      this.counterIndexInMap.reset();
      Config.dbg("Multitexture: " + Config.isMultiTexture());
      TextureUtils.registerCustomSpriteLocations(this.g(), set);
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

   public void a(ResourceLocation locIn, Path pathIn) throws IOException {
      String s = locIn.c();
      TextureUtil.writeAsPNG(pathIn, s, this.a(), this.p, this.n, this.o);
      a(pathIn, s, this.j);
   }

   private static void a(Path pathIn, String nameIn, Map<ResourceLocation, TextureAtlasSprite> mapIn) {
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
                     textureatlassprite.a(),
                     textureatlassprite.b(),
                     textureatlassprite.e().a(),
                     textureatlassprite.e().b()
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
         g.warn("Failed to write file {}", path, var10);
      }
   }

   public void d() {
      boolean hasNormal = false;
      boolean hasSpecular = false;
      if (!this.i.isEmpty()) {
         this.c();
      }

      int countActive = 0;

      for (TextureAtlasSprite.a textureatlassprite$ticker : this.i) {
         TextureAtlasSprite textureatlassprite = textureatlassprite$ticker.getSprite();
         if (textureatlassprite != null) {
            if (this.isAnimationEnabled(textureatlassprite)) {
               textureatlassprite$ticker.a();
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
            textureatlassprite$ticker.a();
         }
      }

      if (Config.isShaders()) {
         if (hasNormal) {
            GlStateManager._bindTexture(this.getMultiTexID().norm);

            for (TextureAtlasSprite.a textureatlassprite$tickerx : this.i) {
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

            for (TextureAtlasSprite.a textureatlassprite$tickerxx : this.i) {
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
            GlStateManager._bindTexture(this.a());
         }
      }

      if (Config.isMultiTexture()) {
         for (TextureAtlasSprite.a textureatlassprite$tickerxxx : this.i) {
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

         GlStateManager._bindTexture(this.a());
      }

      if (this.terrain) {
         int frameCount = Config.getMinecraft().f.getFrameCount();
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
         RenderSystem.recordRenderCall(this::d);
      } else {
         this.d();
      }
   }

   public TextureAtlasSprite a(ResourceLocation location) {
      TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)this.j.getOrDefault(location, this.k);
      if (textureatlassprite == null) {
         throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
      } else {
         return textureatlassprite;
      }
   }

   public void f() {
      if (this.multiTexture) {
         for (TextureAtlasSprite ts : this.j.values()) {
            ts.deleteSpriteTexture();
            if (ts.spriteNormal != null) {
               ts.spriteNormal.deleteSpriteTexture();
            }

            if (ts.spriteSpecular != null) {
               ts.spriteSpecular.deleteSpriteTexture();
            }
         }
      }

      this.h.forEach(SpriteContents::close);
      this.i.forEach(TextureAtlasSprite.a::close);
      this.h = List.of();
      this.i = List.of();
      this.j = Map.of();
      this.k = null;
   }

   public ResourceLocation g() {
      return this.l;
   }

   public int h() {
      return this.m;
   }

   public int i() {
      return this.n;
   }

   public int j() {
      return this.o;
   }

   public void b(SpriteLoader.a preparationsIn) {
      this.a(false, preparationsIn.d() > 0);
   }

   public static boolean isAbsoluteLocation(ResourceLocation loc) {
      String path = loc.a();
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
      return (TextureAtlasSprite)this.j.get(loc);
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
         TextureAtlasSprite.a ticker = ss.f();
         if (ticker != null) {
            ss.setTicker(ticker);
         }

         tas.bindSpriteTexture();

         try {
            ss.j();
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

   private int detectMaxMipmapLevel(Set<ResourceLocation> setSpriteLocations, C_77_ rm) {
      int minSize = this.detectMinimumSpriteSize(setSpriteLocations, rm, 20);
      if (minSize < 16) {
         minSize = 16;
      }

      minSize = Mth.c(minSize);
      if (minSize > 16) {
         Config.log("Sprite size: " + minSize);
      }

      int minLevel = Mth.f(minSize);
      if (minLevel < 4) {
         minLevel = 4;
      }

      return minLevel;
   }

   private int detectMinimumSpriteSize(Set<ResourceLocation> setSpriteLocations, C_77_ rm, int percentScale) {
      Map mapSizeCounts = new HashMap();

      for (ResourceLocation loc : setSpriteLocations) {
         ResourceLocation locComplete = this.getSpritePath(loc);

         try {
            C_76_ res = rm.getResourceOrThrow(locComplete);
            if (res != null) {
               InputStream in = res.m_215507_();
               if (in != null) {
                  Dimension dim = TextureUtils.getImageSize(in, "png");
                  in.close();
                  if (dim != null) {
                     int width = dim.width;
                     int width2 = Mth.c(width);
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

   private static C_243504_ fixSpriteSize(C_243504_ info, int minSpriteSize) {
      if (info.f_244129_() >= minSpriteSize && info.f_244503_() >= minSpriteSize) {
         return info;
      } else {
         int widthNew = Math.max(info.f_244129_(), minSpriteSize);
         int heightNew = Math.max(info.f_244503_(), minSpriteSize);
         return new C_243504_(widthNew, heightNew);
      }
   }

   public boolean isTextureBound() {
      int boundTexId = GlStateManager.getBoundTexture();
      int texId = this.a();
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

         for (TextureAtlasSprite ts : this.j.values()) {
            double deltaU = 0.5 / (double)sheetWidth;
            double deltaV = 0.5 / (double)sheetHeight;
            double uMin = (double)Math.min(ts.c(), ts.d()) + deltaU;
            double vMin = (double)Math.min(ts.g(), ts.h()) + deltaV;
            double uMax = (double)Math.max(ts.c(), ts.d()) - deltaU;
            double vMax = (double)Math.max(ts.g(), ts.h()) - deltaV;
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
      return this.i.size();
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
            sprite = new TextureAtlasSprite(this.l, location);
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
         ? new ResourceLocation(location.b(), location.a() + ".png")
         : new ResourceLocation(location.b(), String.format(Locale.ROOT, "textures/%s%s", location.a(), ".png"));
   }

   public String toString() {
      return this.l + "";
   }

   public Set<ResourceLocation> getTextureLocations() {
      return Collections.unmodifiableSet(this.j.keySet());
   }
}
