package net.minecraft.client.renderer.texture;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.Util;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.Mth;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class SpriteLoader {
   public static final Set f_291626_;
   private static final Logger f_244357_;
   private final ResourceLocation f_244500_;
   private final int f_243676_;
   private final int f_276071_;
   private final int f_276068_;
   private TextureAtlas atlas;

   public SpriteLoader(ResourceLocation locIn, int maxSizeIn, int minWidthIn, int minHeightIn) {
      this.f_244500_ = locIn;
      this.f_243676_ = maxSizeIn;
      this.f_276071_ = minWidthIn;
      this.f_276068_ = minHeightIn;
   }

   public static SpriteLoader m_245483_(TextureAtlas atlasIn) {
      SpriteLoader loader = new SpriteLoader(atlasIn.m_118330_(), atlasIn.m_245285_(), atlasIn.m_276092_(), atlasIn.m_276095_());
      loader.atlas = atlasIn;
      return loader;
   }

   public Preparations m_261295_(List spritesIn, int mipmapLevelIn, Executor executorIn) {
      int mipmapLevels = this.atlas.mipmapLevel;
      int minSpriteSize = this.atlas.getIconGridSize();
      int i = this.f_243676_;
      Stitcher stitcher = new Stitcher(i, i, mipmapLevelIn);
      int j = Integer.MAX_VALUE;
      int k = 1 << mipmapLevelIn;
      Iterator var10 = spritesIn.iterator();

      while(true) {
         int l1;
         int i1;
         int i2;
         while(var10.hasNext()) {
            SpriteContents spritecontents = (SpriteContents)var10.next();
            l1 = spritecontents.getSpriteWidth();
            i1 = spritecontents.getSpriteHeight();
            if (l1 >= 1 && i1 >= 1) {
               if (l1 < minSpriteSize || mipmapLevels > 0) {
                  i2 = mipmapLevels > 0 ? TextureUtils.scaleToGrid(l1, minSpriteSize) : TextureUtils.scaleToMin(l1, minSpriteSize);
                  if (i2 != l1) {
                     if (!TextureUtils.isPowerOfTwo(l1)) {
                        Config.log("Scaled non power of 2: " + String.valueOf(spritecontents.getSpriteLocation()) + ", " + l1 + " -> " + i2);
                     } else {
                        Config.log("Scaled too small texture: " + String.valueOf(spritecontents.getSpriteLocation()) + ", " + l1 + " -> " + i2);
                     }

                     int hs2 = i1 * i2 / l1;
                     double scaleFactor = (double)i2 * 1.0 / (double)l1;
                     spritecontents.setSpriteWidth(i2);
                     spritecontents.setSpriteHeight(hs2);
                     spritecontents.setScaleFactor(scaleFactor);
                     spritecontents.rescale();
                  }
               }

               j = Math.min(j, Math.min(spritecontents.m_246492_(), spritecontents.m_245330_()));
               i2 = Math.min(Integer.lowestOneBit(spritecontents.m_246492_()), Integer.lowestOneBit(spritecontents.m_245330_()));
               if (i2 < k) {
                  f_244357_.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[]{spritecontents.m_246162_(), spritecontents.m_246492_(), spritecontents.m_245330_(), Mth.m_14173_(k), Mth.m_14173_(i2)});
                  k = i2;
               }

               stitcher.m_246099_(spritecontents);
            } else {
               Config.warn("Invalid sprite size: " + String.valueOf(spritecontents.getSpriteLocation()));
            }
         }

         int j1 = Math.min(j, k);
         int k1 = Mth.m_14173_(j1);
         if (k1 < 0) {
            k1 = 0;
         }

         if (k1 < mipmapLevelIn) {
            f_244357_.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.f_244500_, mipmapLevelIn, k1, j1});
            l1 = k1;
         } else {
            l1 = mipmapLevelIn;
         }

         try {
            stitcher.m_118193_();
         } catch (StitcherException var18) {
            CrashReport crashreport = CrashReport.m_127521_(var18, "Stitching");
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Stitcher");
            crashreportcategory.m_128159_("Sprites", var18.m_118258_().stream().map((entryIn) -> {
               return String.format(Locale.ROOT, "%s[%dx%d]", entryIn.m_246162_(), entryIn.m_246492_(), entryIn.m_245330_());
            }).collect(Collectors.joining(",")));
            crashreportcategory.m_128159_("Max Texture Size", i);
            throw new ReportedException(crashreport);
         }

         i1 = Math.max(stitcher.m_118174_(), this.f_276071_);
         i2 = Math.max(stitcher.m_118187_(), this.f_276068_);
         Map map = this.m_276091_(stitcher, i1, i2);
         TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)map.get(MissingTextureAtlasSprite.m_118071_());
         CompletableFuture completablefuture;
         if (l1 > 0) {
            completablefuture = CompletableFuture.runAsync(() -> {
               map.values().forEach((spriteIn) -> {
                  spriteIn.setTextureAtlas(this.atlas);
                  spriteIn.increaseMipLevel(l1);
               });
            }, executorIn);
         } else {
            completablefuture = CompletableFuture.completedFuture((Object)null);
         }

         return new Preparations(i1, i2, l1, textureatlassprite, map, completablefuture);
      }
   }

   public static CompletableFuture m_260809_(SpriteResourceLoader spritesIn, List functionsIn, Executor executorIn) {
      List list = functionsIn.stream().map((functionIn) -> {
         return CompletableFuture.supplyAsync(() -> {
            return (SpriteContents)functionIn.apply(spritesIn);
         }, executorIn);
      }).toList();
      return Util.m_137567_(list).thenApply((listSpritesIn) -> {
         return listSpritesIn.stream().filter(Objects::nonNull).toList();
      });
   }

   public CompletableFuture m_260881_(ResourceManager resourceManagerIn, ResourceLocation locationIn, int mipmapLevelIn, Executor executorIn) {
      return this.m_293475_(resourceManagerIn, locationIn, mipmapLevelIn, executorIn, f_291626_);
   }

   public CompletableFuture m_293475_(ResourceManager resourceManagerIn, ResourceLocation resourceLocationIn, int mipmapLevelIn, Executor executorIn, Collection serializersIn) {
      SpriteResourceLoader spriteresourceloader = SpriteResourceLoader.m_292996_(serializersIn);
      return CompletableFuture.supplyAsync(() -> {
         SpriteSourceList srl = SpriteSourceList.m_294995_(resourceManagerIn, resourceLocationIn);
         Set spriteNames = srl.getSpriteNames(resourceManagerIn);
         srl.filterSpriteNames(spriteNames);
         Set spriteNamesNew = new LinkedHashSet(spriteNames);
         this.atlas.preStitch(spriteNamesNew, resourceManagerIn, mipmapLevelIn);
         spriteNamesNew.removeAll(spriteNames);
         srl.addSpriteSources(spriteNamesNew);
         return srl.m_294473_(resourceManagerIn);
      }, executorIn).thenCompose((functionsIn) -> {
         return m_260809_(spriteresourceloader, functionsIn, executorIn);
      }).thenApply((contentsIn) -> {
         return this.m_261295_(contentsIn, mipmapLevelIn, executorIn);
      });
   }

   private Map m_276091_(Stitcher stitcherIn, int widthIn, int heightIn) {
      Map map = new HashMap();
      stitcherIn.m_118180_((contentsIn, xIn, yIn) -> {
         TextureAtlasSprite sprite;
         if (Reflector.ForgeHooksClient_loadTextureAtlasSprite.exists()) {
            sprite = (TextureAtlasSprite)Reflector.ForgeHooksClient_loadTextureAtlasSprite.call(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, contentsIn.f_243731_.length - 1);
            if (sprite != null) {
               map.put(contentsIn.m_246162_(), sprite);
               return;
            }
         }

         sprite = this.atlas.getRegisteredSprite(contentsIn.m_246162_());
         if (sprite != null) {
            sprite.init(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn);
         } else {
            sprite = new TextureAtlasSprite(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, this.atlas, (ShadersTextureType)null);
         }

         sprite.update(Config.getResourceManager());
         map.put(contentsIn.m_246162_(), sprite);
      });
      return map;
   }

   static {
      f_291626_ = Set.of(AnimationMetadataSection.f_119011_);
      f_244357_ = LogUtils.getLogger();
   }

   public static record Preparations(int f_243669_, int f_244632_, int f_244353_, TextureAtlasSprite f_243912_, Map f_243807_, CompletableFuture f_244415_) {
      public Preparations(int width, int height, int mipLevel, TextureAtlasSprite missing, Map regions, CompletableFuture readyForUpload) {
         this.f_243669_ = width;
         this.f_244632_ = height;
         this.f_244353_ = mipLevel;
         this.f_243912_ = missing;
         this.f_243807_ = regions;
         this.f_244415_ = readyForUpload;
      }

      public CompletableFuture m_246429_() {
         return this.f_244415_.thenApply((voidIn) -> {
            return this;
         });
      }

      public int f_243669_() {
         return this.f_243669_;
      }

      public int f_244632_() {
         return this.f_244632_;
      }

      public int f_244353_() {
         return this.f_244353_;
      }

      public TextureAtlasSprite f_243912_() {
         return this.f_243912_;
      }

      public Map f_243807_() {
         return this.f_243807_;
      }

      public CompletableFuture f_244415_() {
         return this.f_244415_;
      }
   }
}
