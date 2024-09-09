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
import net.minecraft.src.C_243621_;
import net.minecraft.src.C_260366_;
import net.minecraft.src.C_260369_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.minecraft.src.C_260369_.C_260371_;
import net.minecraft.src.C_260369_.C_260402_;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.SpriteSourceCollector;
import net.optifine.util.StrUtils;
import org.slf4j.Logger;

public class SpriteSourceList {
   private static final Logger a = LogUtils.getLogger();
   private static final C_243621_ b = new C_243621_("atlases", ".json");
   private final List<C_260369_> c;

   private SpriteSourceList(List<C_260369_> sourcesIn) {
      this.c = sourcesIn;
   }

   public List<Function<SpriteResourceLoader, SpriteContents>> a(C_77_ resourceManagerIn) {
      final Map<ResourceLocation, C_260402_> map = new HashMap();
      C_260371_ spritesource$output = new C_260371_() {
         public void a(ResourceLocation locIn, C_260402_ supplierIn) {
            C_260402_ spritesource$spritesupplier = (C_260402_)map.put(locIn, supplierIn);
            if (spritesource$spritesupplier != null) {
               spritesource$spritesupplier.m_260986_();
            }
         }

         public void m_261187_(Predicate<ResourceLocation> checkIn) {
            Iterator<Entry<ResourceLocation, C_260402_>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<ResourceLocation, C_260402_> entry = (Entry<ResourceLocation, C_260402_>)iterator.next();
               if (checkIn.test((ResourceLocation)entry.getKey())) {
                  ((C_260402_)entry.getValue()).m_260986_();
                  iterator.remove();
               }
            }
         }
      };
      this.c.forEach(sourceIn -> sourceIn.m_260891_(resourceManagerIn, spritesource$output));
      this.filterSpriteNames(map.keySet());
      Builder<Function<SpriteResourceLoader, SpriteContents>> builder = ImmutableList.builder();
      builder.add((Function)loaderIn -> MissingTextureAtlasSprite.a());
      builder.addAll(map.values());
      return builder.build();
   }

   public static SpriteSourceList a(C_77_ resourceManagerIn, ResourceLocation locationIn) {
      ResourceLocation resourcelocation = b.a(locationIn);
      List<C_260369_> list = new ArrayList();

      for (C_76_ resource : resourceManagerIn.a(resourcelocation)) {
         try {
            BufferedReader bufferedreader = resource.m_215508_();

            try {
               Dynamic<JsonElement> dynamic = new Dynamic(JsonOps.INSTANCE, JsonParser.parseReader(bufferedreader));
               list.addAll((Collection)C_260366_.f_260551_.parse(dynamic).getOrThrow());
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
            a.error("Failed to parse atlas definition {} in pack {}", new Object[]{resourcelocation, resource.m_215506_(), var11});
         }
      }

      return new SpriteSourceList(list);
   }

   public void addSpriteSources(Collection<ResourceLocation> spriteNames) {
      for (ResourceLocation spriteName : spriteNames) {
         this.c.add(new SingleFile(spriteName, Optional.empty()));
      }
   }

   public List<C_260369_> getSpriteSources() {
      return this.c;
   }

   public Set<ResourceLocation> getSpriteNames(C_77_ resourceManager) {
      Set<ResourceLocation> spriteNames = new LinkedHashSet();

      for (C_260369_ source : this.c) {
         C_260371_ out = new SpriteSourceCollector(spriteNames);
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
         String path = loc.a();
         if (path.endsWith(suffixNormal) || path.endsWith(suffixSpecular)) {
            String pathBase = StrUtils.removeSuffix(path, suffixesShaders);
            ResourceLocation locBase = new ResourceLocation(loc.b(), pathBase);
            if (spriteNames.contains(locBase)) {
               it.remove();
            }
         }
      }
   }
}
