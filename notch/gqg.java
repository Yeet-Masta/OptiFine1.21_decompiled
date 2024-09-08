package net.minecraft.src;

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
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class C_243537_ {
   public static final Set<C_54_<?>> f_291626_ = Set.of(C_4518_.f_119011_);
   private static final Logger f_244357_ = LogUtils.getLogger();
   private final C_5265_ f_244500_;
   private final int f_243676_;
   private final int f_276071_;
   private final int f_276068_;
   private C_4484_ atlas;

   public C_243537_(C_5265_ locIn, int maxSizeIn, int minWidthIn, int minHeightIn) {
      this.f_244500_ = locIn;
      this.f_243676_ = maxSizeIn;
      this.f_276071_ = minWidthIn;
      this.f_276068_ = minHeightIn;
   }

   public static C_243537_ m_245483_(C_4484_ atlasIn) {
      C_243537_ loader = new C_243537_(atlasIn.m_118330_(), atlasIn.m_245285_(), atlasIn.m_276092_(), atlasIn.m_276095_());
      loader.atlas = atlasIn;
      return loader;
   }

   public C_243537_.C_243503_ m_261295_(List<C_243582_> spritesIn, int mipmapLevelIn, Executor executorIn) {
      int mipmapLevels = this.atlas.mipmapLevel;
      int minSpriteSize = this.atlas.getIconGridSize();
      int i = this.f_243676_;
      C_4478_<C_243582_> stitcher = new C_4478_<>(i, i, mipmapLevelIn);
      int j = Integer.MAX_VALUE;
      int k = 1 << mipmapLevelIn;

      for (C_243582_ spritecontents : spritesIn) {
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
                  new Object[]{spritecontents.m_246162_(), spritecontents.m_246492_(), spritecontents.m_245330_(), C_188_.m_14173_(k), C_188_.m_14173_(l)}
               );
               k = l;
            }

            stitcher.m_246099_(spritecontents);
         } else {
            Config.warn("Invalid sprite size: " + spritecontents.getSpriteLocation());
         }
      }

      int j1 = Math.min(j, k);
      int k1 = C_188_.m_14173_(j1);
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
      } catch (C_4483_ var18) {
         C_4883_ crashreport = C_4883_.m_127521_(var18, "Stitching");
         C_4909_ crashreportcategory = crashreport.m_127514_("Stitcher");
         crashreportcategory.m_128159_(
            "Sprites",
            var18.m_118258_()
               .stream()
               .map(entryIn -> String.format(Locale.ROOT, "%s[%dx%d]", entryIn.m_246162_(), entryIn.m_246492_(), entryIn.m_245330_()))
               .collect(Collectors.joining(","))
         );
         crashreportcategory.m_128159_("Max Texture Size", i);
         throw new C_5204_(crashreport);
      }

      int i1 = Math.max(stitcher.m_118174_(), this.f_276071_);
      int i2 = Math.max(stitcher.m_118187_(), this.f_276068_);
      Map<C_5265_, C_4486_> map = this.m_276091_(stitcher, i1, i2);
      C_4486_ textureatlassprite = (C_4486_)map.get(C_4473_.m_118071_());
      CompletableFuture<Void> completablefuture;
      if (l1 > 0) {
         completablefuture = CompletableFuture.runAsync(() -> map.values().forEach(spriteIn -> {
               spriteIn.setTextureAtlas(this.atlas);
               spriteIn.increaseMipLevel(l1);
            }), executorIn);
      } else {
         completablefuture = CompletableFuture.completedFuture(null);
      }

      return new C_243537_.C_243503_(i1, i2, l1, textureatlassprite, map, completablefuture);
   }

   public static CompletableFuture<List<C_243582_>> m_260809_(C_260391_ spritesIn, List<Function<C_260391_, C_243582_>> functionsIn, Executor executorIn) {
      List<CompletableFuture<C_243582_>> list = functionsIn.stream()
         .map(functionIn -> CompletableFuture.supplyAsync(() -> (C_243582_)functionIn.apply(spritesIn), executorIn))
         .toList();
      return C_5322_.m_137567_(list).thenApply(listSpritesIn -> listSpritesIn.stream().filter(Objects::nonNull).toList());
   }

   public CompletableFuture<C_243537_.C_243503_> m_260881_(C_77_ resourceManagerIn, C_5265_ locationIn, int mipmapLevelIn, Executor executorIn) {
      return this.m_293475_(resourceManagerIn, locationIn, mipmapLevelIn, executorIn, f_291626_);
   }

   public CompletableFuture<C_243537_.C_243503_> m_293475_(
      C_77_ resourceManagerIn, C_5265_ resourceLocationIn, int mipmapLevelIn, Executor executorIn, Collection<C_54_<?>> serializersIn
   ) {
      C_260391_ spriteresourceloader = C_260391_.m_292996_(serializersIn);
      return CompletableFuture.supplyAsync(() -> {
            C_290208_ srl = C_290208_.m_294995_(resourceManagerIn, resourceLocationIn);
            Set<C_5265_> spriteNames = srl.getSpriteNames(resourceManagerIn);
            srl.filterSpriteNames(spriteNames);
            Set<C_5265_> spriteNamesNew = new LinkedHashSet(spriteNames);
            this.atlas.preStitch(spriteNamesNew, resourceManagerIn, mipmapLevelIn);
            spriteNamesNew.removeAll(spriteNames);
            srl.addSpriteSources(spriteNamesNew);
            return srl.m_294473_(resourceManagerIn);
         }, executorIn)
         .thenCompose(functionsIn -> m_260809_(spriteresourceloader, functionsIn, executorIn))
         .thenApply(contentsIn -> this.m_261295_(contentsIn, mipmapLevelIn, executorIn));
   }

   private Map<C_5265_, C_4486_> m_276091_(C_4478_<C_243582_> stitcherIn, int widthIn, int heightIn) {
      Map<C_5265_, C_4486_> map = new HashMap();
      stitcherIn.m_118180_(
         (contentsIn, xIn, yIn) -> {
            if (Reflector.ForgeHooksClient_loadTextureAtlasSprite.exists()) {
               C_4486_ sprite = (C_4486_)Reflector.ForgeHooksClient_loadTextureAtlasSprite
                  .call(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, contentsIn.f_243731_.length - 1);
               if (sprite != null) {
                  map.put(contentsIn.m_246162_(), sprite);
                  return;
               }
            }

            C_4486_ sprite = this.atlas.getRegisteredSprite(contentsIn.m_246162_());
            if (sprite != null) {
               sprite.init(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn);
            } else {
               sprite = new C_4486_(this.f_244500_, contentsIn, widthIn, heightIn, xIn, yIn, this.atlas, null);
            }

            sprite.update(Config.getResourceManager());
            map.put(contentsIn.m_246162_(), sprite);
         }
      );
      return map;
   }

   public static record C_243503_(
      int f_243669_, int f_244632_, int f_244353_, C_4486_ f_243912_, Map<C_5265_, C_4486_> f_243807_, CompletableFuture<Void> f_244415_
   ) {
      public CompletableFuture<C_243537_.C_243503_> m_246429_() {
         return this.f_244415_.thenApply(voidIn -> this);
      }
   }
}
