package net.minecraft.client.renderer.texture;

import com.mojang.logging.LogUtils;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class SpriteLoader {
   public static final Set<MetadataSectionSerializer<?>> f_291626_ = Set.of(
      net.minecraft.client.resources.metadata.animation.AnimationMetadataSection.f_119011_
   );
   private static final Logger f_244357_ = LogUtils.getLogger();
   private final net.minecraft.resources.ResourceLocation f_244500_;
   private final int f_243676_;
   private final int f_276071_;
   private final int f_276068_;
   private net.minecraft.client.renderer.texture.TextureAtlas atlas;

   public SpriteLoader(net.minecraft.resources.ResourceLocation locIn, int maxSizeIn, int minWidthIn, int minHeightIn) {
      this.f_244500_ = locIn;
      this.f_243676_ = maxSizeIn;
      this.f_276071_ = minWidthIn;
      this.f_276068_ = minHeightIn;
   }

   public static net.minecraft.client.renderer.texture.SpriteLoader m_245483_(net.minecraft.client.renderer.texture.TextureAtlas atlasIn) {
      net.minecraft.client.renderer.texture.SpriteLoader loader = new net.minecraft.client.renderer.texture.SpriteLoader(
         atlasIn.m_118330_(), atlasIn.m_245285_(), atlasIn.m_276092_(), atlasIn.m_276095_()
      );
      loader.atlas = atlasIn;
      return loader;
   }

   public net.minecraft.client.renderer.texture.SpriteLoader.Preparations m_261295_(
      List<net.minecraft.client.renderer.texture.SpriteContents> spritesIn, int mipmapLevelIn, Executor executorIn
   ) {
      int mipmapLevels = this.atlas.mipmapLevel;
      int minSpriteSize = this.atlas.getIconGridSize();
      int i = this.f_243676_;
      net.minecraft.client.renderer.texture.Stitcher<net.minecraft.client.renderer.texture.SpriteContents> stitcher = new net.minecraft.client.renderer.texture.Stitcher<>(
         i, i, mipmapLevelIn
      );
      int j = Integer.MAX_VALUE;
      int k = 1 << mipmapLevelIn;

      for (net.minecraft.client.renderer.texture.SpriteContents spritecontents : spritesIn) {
         int ws = spritecontents.getSpriteWidth();
         int hs = spritecontents.getSpriteHeight();
         if (ws >= 1 && hs >= 1) {
            if (ws < minSpriteSize || mipmapLevels > 0) {
               int ws2 = mipmapLevels > 0 ? TextureUtils.scaleToGrid(ws, minSpriteSize) : TextureUtils.scaleToMin(ws, minSpriteSize);
               if (ws2 != ws) {
                  if (!TextureUtils.isPowerOfTwo(ws)) {
                     Config.log("Scaled non power of 2: " + spritecontents.getSpriteLocation() + ", " + ws + " -> " + ws2);
                  } else {
                     Config.log("Scaled too small texture: " + spritecontents.getSpriteLocation() + ", " + ws + " -> " + ws2);
                  }

                  int hs2 = hs * ws2 / ws;
                  double scaleFactor = (double)ws2 * 1.0 / (double)ws;
                  spritecontents.setSpriteWidth(ws2);
                  spritecontents.setSpriteHeight(hs2);
                  spritecontents.setScaleFactor(scaleFactor);
                  spritecontents.rescale();
               }
            }

            j = Math.min(j, Math.min(spritecontents.m_246492_(), spritecontents.m_245330_()));
            int l = Math.min(Integer.lowestOneBit(spritecontents.m_246492_()), Integer.lowestOneBit(spritecontents.m_245330_()));
            if (l < k) {
               f_244357_.warn(
                  "Texture {} with size {}x{} limits mip level from {} to {}",
                  new Object[]{
                     spritecontents.m_246162_(),
                     spritecontents.m_246492_(),
                     spritecontents.m_245330_(),
                     net.minecraft.util.Mth.m_14173_(k),
                     net.minecraft.util.Mth.m_14173_(l)
                  }
               );
               k = l;
            }

            stitcher.m_246099_(spritecontents);
         } else {
            Config.warn("Invalid sprite size: " + spritecontents.getSpriteLocation());
         }
      }

      int j1 = Math.min(j, k);
      int k1 = net.minecraft.util.Mth.m_14173_(j1);
      if (k1 < 0) {
         k1 = 0;
      }

      int l1;
      if (k1 < mipmapLevelIn) {
         f_244357_.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.f_244500_, mipmapLevelIn, k1, j1});
         l1 = k1;
      } else {
         l1 = mipmapLevelIn;
      }

      try {
         stitcher.m_118193_();
      } catch (net.minecraft.client.renderer.texture.StitcherException var18) {
         net.minecraft.CrashReport crashreport = net.minecraft.CrashReport.m_127521_(var18, "Stitching");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Stitcher");
         crashreportcategory.m_128159_(
            "Sprites",
            var18.m_118258_()
               .stream()
               .map(entryIn -> String.format(Locale.ROOT, "%s[%dx%d]", entryIn.m_246162_(), entryIn.m_246492_(), entryIn.m_245330_()))
               .collect(Collectors.joining(","))
         );
         crashreportcategory.m_128159_("Max Texture Size", i);
         throw new ReportedException(crashreport);
      }

      int i1 = Math.max(stitcher.m_118174_(), this.f_276071_);
      int i2 = Math.max(stitcher.m_118187_(), this.f_276068_);
      Map<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.TextureAtlasSprite> map = this.m_276091_(stitcher, i1, i2);
      net.minecraft.client.renderer.texture.TextureAtlasSprite textureatlassprite = (net.minecraft.client.renderer.texture.TextureAtlasSprite)map.get(
         net.minecraft.client.renderer.texture.MissingTextureAtlasSprite.m_118071_()
      );
      CompletableFuture<Void> completablefuture;
      if (l1 > 0) {
         completablefuture = CompletableFuture.runAsync(() -> map.values().forEach(spriteIn -> {
               spriteIn.setTextureAtlas(this.atlas);
               spriteIn.increaseMipLevel(l1);
            }), executorIn);
      } else {
         completablefuture = CompletableFuture.completedFuture(null);
      }

      return new net.minecraft.client.renderer.texture.SpriteLoader.Preparations(i1, i2, l1, textureatlassprite, map, completablefuture);
   }

   public static CompletableFuture<List<net.minecraft.client.renderer.texture.SpriteContents>> m_260809_(
      net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader spritesIn,
      List<Function<net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader, net.minecraft.client.renderer.texture.SpriteContents>> functionsIn,
      Executor executorIn
   ) {
      List<CompletableFuture<net.minecraft.client.renderer.texture.SpriteContents>> list = functionsIn.stream()
         .map(functionIn -> CompletableFuture.supplyAsync(() -> (net.minecraft.client.renderer.texture.SpriteContents)functionIn.apply(spritesIn), executorIn))
         .toList();
      return net.minecraft.Util.m_137567_(list).thenApply(listSpritesIn -> listSpritesIn.stream().filter(Objects::nonNull).toList());
   }

   public CompletableFuture<net.minecraft.client.renderer.texture.SpriteLoader.Preparations> m_260881_(
      ResourceManager resourceManagerIn, net.minecraft.resources.ResourceLocation locationIn, int mipmapLevelIn, Executor executorIn
   ) {
      return this.m_293475_(resourceManagerIn, locationIn, mipmapLevelIn, executorIn, f_291626_);
   }

   public CompletableFuture<net.minecraft.client.renderer.texture.SpriteLoader.Preparations> m_293475_(
      ResourceManager resourceManagerIn,
      net.minecraft.resources.ResourceLocation resourceLocationIn,
      int mipmapLevelIn,
      Executor executorIn,
      Collection<MetadataSectionSerializer<?>> serializersIn
   ) {
      net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader spriteresourceloader = net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader.m_292996_(
         serializersIn
      );
      return CompletableFuture.supplyAsync(
            () -> {
               net.minecraft.client.renderer.texture.atlas.SpriteSourceList srl = net.minecraft.client.renderer.texture.atlas.SpriteSourceList.m_294995_(
                  resourceManagerIn, resourceLocationIn
               );
               Set<net.minecraft.resources.ResourceLocation> spriteNames = srl.getSpriteNames(resourceManagerIn);
               srl.filterSpriteNames(spriteNames);
               Set<net.minecraft.resources.ResourceLocation> spriteNamesNew = new LinkedHashSet(spriteNames);
               this.atlas.preStitch(spriteNamesNew, resourceManagerIn, mipmapLevelIn);
               spriteNamesNew.removeAll(spriteNames);
               srl.addSpriteSources(spriteNamesNew);
               return srl.m_294473_(resourceManagerIn);
            },
            executorIn
         )
         .thenCompose(functionsIn -> m_260809_(spriteresourceloader, functionsIn, executorIn))
         .thenApply(contentsIn -> this.m_261295_(contentsIn, mipmapLevelIn, executorIn));
   }

   private Map<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.TextureAtlasSprite> m_276091_(
      net.minecraft.client.renderer.texture.Stitcher<net.minecraft.client.renderer.texture.SpriteContents> stitcherIn, int widthIn, int heightIn
   ) {
      Map<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.TextureAtlasSprite> map = new HashMap();
      stitcherIn.m_118180_(
         (contentsIn, xIn, yIn) -> {
            if (Reflector.ForgeHooksClient_loadTextureAtlasSprite.exists()) {
               net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = (net.minecraft.client.renderer.texture.TextureAtlasSprite)Reflector.ForgeHooksClient_loadTextureAtlasSprite
                  .call(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, contentsIn.f_243731_.length - 1);
               if (sprite != null) {
                  map.put(contentsIn.m_246162_(), sprite);
                  return;
               }
            }

            net.minecraft.client.renderer.texture.TextureAtlasSprite sprite = this.atlas.getRegisteredSprite(contentsIn.m_246162_());
            if (sprite != null) {
               sprite.init(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn);
            } else {
               sprite = new net.minecraft.client.renderer.texture.TextureAtlasSprite(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, this.atlas, null);
            }

            sprite.update(Config.getResourceManager());
            map.put(contentsIn.m_246162_(), sprite);
         }
      );
      return map;
   }

   public static record Preparations(
      int f_243669_,
      int f_244632_,
      int f_244353_,
      net.minecraft.client.renderer.texture.TextureAtlasSprite f_243912_,
      Map<net.minecraft.resources.ResourceLocation, net.minecraft.client.renderer.texture.TextureAtlasSprite> f_243807_,
      CompletableFuture<Void> f_244415_
   ) {
      public CompletableFuture<net.minecraft.client.renderer.texture.SpriteLoader.Preparations> m_246429_() {
         return this.f_244415_.thenApply(voidIn -> this);
      }
   }
}
