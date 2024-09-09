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
import net.minecraft.src.C_4909_;
import net.minecraft.src.C_5204_;
import net.minecraft.src.C_54_;
import net.minecraft.src.C_77_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.util.TextureUtils;
import org.slf4j.Logger;

public class SpriteLoader {
   public static final Set<C_54_<?>> a = Set.of(AnimationMetadataSection.a);
   private static final Logger b = LogUtils.getLogger();
   private final ResourceLocation c;
   private final int d;
   private final int e;
   private final int f;
   private TextureAtlas atlas;

   public SpriteLoader(ResourceLocation locIn, int maxSizeIn, int minWidthIn, int minHeightIn) {
      this.c = locIn;
      this.d = maxSizeIn;
      this.e = minWidthIn;
      this.f = minHeightIn;
   }

   public static SpriteLoader a(TextureAtlas atlasIn) {
      SpriteLoader loader = new SpriteLoader(atlasIn.g(), atlasIn.h(), atlasIn.i(), atlasIn.j());
      loader.atlas = atlasIn;
      return loader;
   }

   public SpriteLoader.a a(List<SpriteContents> spritesIn, int mipmapLevelIn, Executor executorIn) {
      int mipmapLevels = this.atlas.mipmapLevel;
      int minSpriteSize = this.atlas.getIconGridSize();
      int i = this.d;
      Stitcher<SpriteContents> stitcher = new Stitcher<>(i, i, mipmapLevelIn);
      int j = Integer.MAX_VALUE;
      int k = 1 << mipmapLevelIn;

      for (SpriteContents spritecontents : spritesIn) {
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

            j = Math.min(j, Math.min(spritecontents.a(), spritecontents.b()));
            int l = Math.min(Integer.lowestOneBit(spritecontents.a()), Integer.lowestOneBit(spritecontents.b()));
            if (l < k) {
               b.warn(
                  "Texture {} with size {}x{} limits mip level from {} to {}",
                  new Object[]{spritecontents.c(), spritecontents.a(), spritecontents.b(), Mth.f(k), Mth.f(l)}
               );
               k = l;
            }

            stitcher.a(spritecontents);
         } else {
            Config.warn("Invalid sprite size: " + spritecontents.getSpriteLocation());
         }
      }

      int j1 = Math.min(j, k);
      int k1 = Mth.f(j1);
      if (k1 < 0) {
         k1 = 0;
      }

      int l1;
      if (k1 < mipmapLevelIn) {
         b.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[]{this.c, mipmapLevelIn, k1, j1});
         l1 = k1;
      } else {
         l1 = mipmapLevelIn;
      }

      try {
         stitcher.c();
      } catch (StitcherException var18) {
         CrashReport crashreport = CrashReport.a(var18, "Stitching");
         C_4909_ crashreportcategory = crashreport.a("Stitcher");
         crashreportcategory.m_128159_(
            "Sprites",
            var18.a().stream().map(entryIn -> String.format(Locale.ROOT, "%s[%dx%d]", entryIn.c(), entryIn.a(), entryIn.b())).collect(Collectors.joining(","))
         );
         crashreportcategory.m_128159_("Max Texture Size", i);
         throw new C_5204_(crashreport);
      }

      int i1 = Math.max(stitcher.a(), this.e);
      int i2 = Math.max(stitcher.b(), this.f);
      Map<ResourceLocation, TextureAtlasSprite> map = this.a(stitcher, i1, i2);
      TextureAtlasSprite textureatlassprite = (TextureAtlasSprite)map.get(MissingTextureAtlasSprite.b());
      CompletableFuture<Void> completablefuture;
      if (l1 > 0) {
         completablefuture = CompletableFuture.runAsync(() -> map.values().forEach(spriteIn -> {
               spriteIn.setTextureAtlas(this.atlas);
               spriteIn.increaseMipLevel(l1);
            }), executorIn);
      } else {
         completablefuture = CompletableFuture.completedFuture(null);
      }

      return new SpriteLoader.a(i1, i2, l1, textureatlassprite, map, completablefuture);
   }

   public static CompletableFuture<List<SpriteContents>> a(
      SpriteResourceLoader spritesIn, List<Function<SpriteResourceLoader, SpriteContents>> functionsIn, Executor executorIn
   ) {
      List<CompletableFuture<SpriteContents>> list = functionsIn.stream()
         .map(functionIn -> CompletableFuture.supplyAsync(() -> (SpriteContents)functionIn.apply(spritesIn), executorIn))
         .toList();
      return Util.d(list).thenApply(listSpritesIn -> listSpritesIn.stream().filter(Objects::nonNull).toList());
   }

   public CompletableFuture<SpriteLoader.a> a(C_77_ resourceManagerIn, ResourceLocation locationIn, int mipmapLevelIn, Executor executorIn) {
      return this.a(resourceManagerIn, locationIn, mipmapLevelIn, executorIn, a);
   }

   public CompletableFuture<SpriteLoader.a> a(
      C_77_ resourceManagerIn, ResourceLocation resourceLocationIn, int mipmapLevelIn, Executor executorIn, Collection<C_54_<?>> serializersIn
   ) {
      SpriteResourceLoader spriteresourceloader = SpriteResourceLoader.create(serializersIn);
      return CompletableFuture.supplyAsync(() -> {
            SpriteSourceList srl = SpriteSourceList.a(resourceManagerIn, resourceLocationIn);
            Set<ResourceLocation> spriteNames = srl.getSpriteNames(resourceManagerIn);
            srl.filterSpriteNames(spriteNames);
            Set<ResourceLocation> spriteNamesNew = new LinkedHashSet(spriteNames);
            this.atlas.preStitch(spriteNamesNew, resourceManagerIn, mipmapLevelIn);
            spriteNamesNew.removeAll(spriteNames);
            srl.addSpriteSources(spriteNamesNew);
            return srl.a(resourceManagerIn);
         }, executorIn)
         .thenCompose(functionsIn -> a(spriteresourceloader, functionsIn, executorIn))
         .thenApply(contentsIn -> this.a(contentsIn, mipmapLevelIn, executorIn));
   }

   private Map<ResourceLocation, TextureAtlasSprite> a(Stitcher<SpriteContents> stitcherIn, int widthIn, int heightIn) {
      Map<ResourceLocation, TextureAtlasSprite> map = new HashMap();
      stitcherIn.a(
         (contentsIn, xIn, yIn) -> {
            if (Reflector.ForgeHooksClient_loadTextureAtlasSprite.exists()) {
               TextureAtlasSprite sprite = (TextureAtlasSprite)Reflector.ForgeHooksClient_loadTextureAtlasSprite
                  .call(this.c, contentsIn, widthIn, heightIn, xIn, yIn, contentsIn.f.length - 1);
               if (sprite != null) {
                  map.put(contentsIn.c(), sprite);
                  return;
               }
            }

            TextureAtlasSprite sprite = this.atlas.getRegisteredSprite(contentsIn.c());
            if (sprite != null) {
               sprite.init(this.c, contentsIn, widthIn, heightIn, xIn, yIn);
            } else {
               sprite = new TextureAtlasSprite(this.c, contentsIn, widthIn, heightIn, xIn, yIn, this.atlas, null);
            }

            sprite.update(Config.getResourceManager());
            map.put(contentsIn.c(), sprite);
         }
      );
      return map;
   }

   public static record a(int a, int b, int c, TextureAtlasSprite d, Map<ResourceLocation, TextureAtlasSprite> e, CompletableFuture<Void> f) {
      public CompletableFuture<SpriteLoader.a> a() {
         return this.f.thenApply(voidIn -> this);
      }

      public int b() {
         return this.a;
      }

      public int c() {
         return this.b;
      }

      public int d() {
         return this.c;
      }

      public TextureAtlasSprite e() {
         return this.d;
      }

      public Map<ResourceLocation, TextureAtlasSprite> f() {
         return this.e;
      }

      public CompletableFuture<Void> g() {
         return this.f;
      }
   }
}
