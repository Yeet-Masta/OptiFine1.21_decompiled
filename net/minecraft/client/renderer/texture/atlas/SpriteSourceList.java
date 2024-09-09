package net.minecraft.client.renderer.texture.atlas;

import com.google.common.collect.ImmutableList;
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
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
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
   private static final Logger f_290791_ = LogUtils.getLogger();
   private static final FileToIdConverter f_291407_ = new FileToIdConverter("atlases", ".json");
   private final List f_291450_;

   private SpriteSourceList(List sourcesIn) {
      this.f_291450_ = sourcesIn;
   }

   public List m_294473_(ResourceManager resourceManagerIn) {
      final Map map = new HashMap();
      SpriteSource.Output spritesource$output = new SpriteSource.Output(this) {
         public void m_260840_(ResourceLocation locIn, SpriteSource.SpriteSupplier supplierIn) {
            SpriteSource.SpriteSupplier spritesource$spritesupplier = (SpriteSource.SpriteSupplier)map.put(locIn, supplierIn);
            if (spritesource$spritesupplier != null) {
               spritesource$spritesupplier.m_260986_();
            }

         }

         public void m_261187_(Predicate checkIn) {
            Iterator iterator = map.entrySet().iterator();

            while(iterator.hasNext()) {
               Map.Entry entry = (Map.Entry)iterator.next();
               if (checkIn.test((ResourceLocation)entry.getKey())) {
                  ((SpriteSource.SpriteSupplier)entry.getValue()).m_260986_();
                  iterator.remove();
               }
            }

         }
      };
      this.f_291450_.forEach((sourceIn) -> {
         sourceIn.m_260891_(resourceManagerIn, spritesource$output);
      });
      this.filterSpriteNames(map.keySet());
      ImmutableList.Builder builder = ImmutableList.builder();
      builder.add((loaderIn) -> {
         return MissingTextureAtlasSprite.m_246104_();
      });
      builder.addAll(map.values());
      return builder.build();
   }

   public static SpriteSourceList m_294995_(ResourceManager resourceManagerIn, ResourceLocation locationIn) {
      ResourceLocation resourcelocation = f_291407_.m_245698_(locationIn);
      List list = new ArrayList();
      Iterator var4 = resourceManagerIn.m_213829_(resourcelocation).iterator();

      while(var4.hasNext()) {
         Resource resource = (Resource)var4.next();

         try {
            BufferedReader bufferedreader = resource.m_215508_();

            try {
               Dynamic dynamic = new Dynamic(JsonOps.INSTANCE, JsonParser.parseReader(bufferedreader));
               list.addAll((Collection)SpriteSources.f_260551_.parse(dynamic).getOrThrow());
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

   public void addSpriteSources(Collection spriteNames) {
      Iterator var2 = spriteNames.iterator();

      while(var2.hasNext()) {
         ResourceLocation spriteName = (ResourceLocation)var2.next();
         this.f_291450_.add(new SingleFile(spriteName, Optional.empty()));
      }

   }

   public List getSpriteSources() {
      return this.f_291450_;
   }

   public Set getSpriteNames(ResourceManager resourceManager) {
      Set spriteNames = new LinkedHashSet();
      Iterator var3 = this.f_291450_.iterator();

      while(var3.hasNext()) {
         SpriteSource source = (SpriteSource)var3.next();
         SpriteSource.Output out = new SpriteSourceCollector(spriteNames);
         source.m_260891_(resourceManager, out);
      }

      return spriteNames;
   }

   public void filterSpriteNames(Set spriteNames) {
      String suffixNormal = ShadersTextureType.NORMAL.getSuffix();
      String suffixSpecular = ShadersTextureType.SPECULAR.getSuffix();
      String[] suffixesShaders = new String[]{suffixNormal, suffixSpecular};
      Iterator it = spriteNames.iterator();

      while(true) {
         ResourceLocation loc;
         String path;
         do {
            if (!it.hasNext()) {
               return;
            }

            loc = (ResourceLocation)it.next();
            path = loc.m_135815_();
         } while(!path.endsWith(suffixNormal) && !path.endsWith(suffixSpecular));

         String pathBase = StrUtils.removeSuffix(path, suffixesShaders);
         ResourceLocation locBase = new ResourceLocation(loc.m_135827_(), pathBase);
         if (spriteNames.contains(locBase)) {
            it.remove();
         }
      }
   }
}
