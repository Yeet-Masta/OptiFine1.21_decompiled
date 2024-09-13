package net.minecraft.client.renderer.texture.atlas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.Output;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.SpriteSupplier;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.SpriteSourceCollector;
import net.optifine.util.StrUtils;
import org.slf4j.Logger;

public class SpriteSourceList {
   private static Logger f_290791_ = LogUtils.getLogger();
   private static FileToIdConverter f_291407_ = new FileToIdConverter("atlases", ".json");
   private List<SpriteSource> f_291450_;

   private SpriteSourceList(List<SpriteSource> sourcesIn) {
      this.f_291450_ = sourcesIn;
   }

   public List<Function<SpriteResourceLoader, SpriteContents>> m_294473_(ResourceManager resourceManagerIn) {
      final Map<ResourceLocation, SpriteSupplier> map = new HashMap();
      Output spritesource$output = new Output() {
         public void m_260840_(ResourceLocation locIn, SpriteSupplier supplierIn) {
            SpriteSupplier spritesource$spritesupplier = (SpriteSupplier)map.put(locIn, supplierIn);
            if (spritesource$spritesupplier != null) {
               spritesource$spritesupplier.m_260986_();
            }
         }

         public void m_261187_(Predicate<ResourceLocation> checkIn) {
            Iterator<Entry<ResourceLocation, SpriteSupplier>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<ResourceLocation, SpriteSupplier> entry = (Entry<ResourceLocation, SpriteSupplier>)iterator.next();
               if (checkIn.m_125854_((ResourceLocation)entry.getKey())) {
                  ((SpriteSupplier)entry.getValue()).m_260986_();
                  iterator.remove();
               }
            }
         }
      };
      this.f_291450_.forEach(sourceIn -> sourceIn.m_260891_(resourceManagerIn, spritesource$output));
      this.filterSpriteNames(map.keySet());
      Builder<Function<SpriteResourceLoader, SpriteContents>> builder = ImmutableList.builder();
      builder.add((Function)loaderIn -> MissingTextureAtlasSprite.m_246104_());
      builder.addAll(map.values());
      return builder.build();
   }

   public static SpriteSourceList m_294995_(ResourceManager resourceManagerIn, ResourceLocation locationIn) {
      ResourceLocation resourcelocation = f_291407_.m_245698_(locationIn);
      List<SpriteSource> list = new ArrayList();

      for (Resource resource : resourceManagerIn.m_213829_(resourcelocation)) {
         try {
            BufferedReader bufferedreader = resource.m_215508_();

            try {
               Dynamic<JsonElement> dynamic = new Dynamic(JsonOps.INSTANCE, JsonParser.parseReader(bufferedreader));
               list.addAll((Collection)SpriteSources.f_260551_.m_82160_(dynamic).getOrThrow());
            } catch (Throwable var10) {
               if (bufferedreader != null) {
                  try {
                     bufferedreader.close();
                  } catch (Throwable var9) {
                     var10.addSuppressed(var9);
                  }
               }

               throw var10;
            }

            if (bufferedreader != null) {
               bufferedreader.close();
            }
         } catch (Exception var11) {
            f_290791_.error("Failed to parse atlas definition {} in pack {}", new Object[]{resourcelocation, resource.m_215506_(), var11});
         }
      }

      return new SpriteSourceList(list);
   }

   public void addSpriteSources(Collection<ResourceLocation> spriteNames) {
      for (ResourceLocation spriteName : spriteNames) {
         this.f_291450_.add(new SingleFile(spriteName, Optional.m_274566_()));
      }
   }

   public List<SpriteSource> getSpriteSources() {
      return this.f_291450_;
   }

   public Set<ResourceLocation> getSpriteNames(ResourceManager resourceManager) {
      Set<ResourceLocation> spriteNames = new LinkedHashSet();

      for (SpriteSource source : this.f_291450_) {
         Output out = new SpriteSourceCollector(spriteNames);
         source.m_260891_(resourceManager, out);
      }

      return spriteNames;
   }

   public void filterSpriteNames(Set<ResourceLocation> spriteNames) {
      String suffixNormal = ShadersTextureType.NORMAL.getSuffix();
      String suffixSpecular = ShadersTextureType.SPECULAR.getSuffix();
      String[] suffixesShaders = new String[]{suffixNormal, suffixSpecular};
      Iterator it = spriteNames.iterator();

      while (it.hasNext()) {
         ResourceLocation loc = (ResourceLocation)it.next();
         String path = loc.m_135815_();
         if (path.endsWith(suffixNormal) || path.endsWith(suffixSpecular)) {
            String pathBase = StrUtils.removeSuffix(path, suffixesShaders);
            ResourceLocation locBase = new ResourceLocation(loc.m_135827_(), pathBase);
            if (spriteNames.m_274455_(locBase)) {
               it.remove();
            }
         }
      }
   }
}
