package net.minecraft.src;

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
import net.minecraft.src.C_260369_.C_260371_;
import net.minecraft.src.C_260369_.C_260402_;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.SpriteSourceCollector;
import net.optifine.util.StrUtils;
import org.slf4j.Logger;

public class C_290208_ {
   private static final Logger f_290791_ = LogUtils.getLogger();
   private static final C_243621_ f_291407_ = new C_243621_("atlases", ".json");
   private final List<C_260369_> f_291450_;

   private C_290208_(List<C_260369_> sourcesIn) {
      this.f_291450_ = sourcesIn;
   }

   public List<Function<C_260391_, C_243582_>> m_294473_(C_77_ resourceManagerIn) {
      final Map<C_5265_, C_260402_> map = new HashMap();
      C_260371_ spritesource$output = new C_260371_() {
         public void m_260840_(C_5265_ locIn, C_260402_ supplierIn) {
            C_260402_ spritesource$spritesupplier = (C_260402_)map.put(locIn, supplierIn);
            if (spritesource$spritesupplier != null) {
               spritesource$spritesupplier.m_260986_();
            }
         }

         public void m_261187_(Predicate<C_5265_> checkIn) {
            Iterator<Entry<C_5265_, C_260402_>> iterator = map.entrySet().iterator();

            while (iterator.hasNext()) {
               Entry<C_5265_, C_260402_> entry = (Entry<C_5265_, C_260402_>)iterator.next();
               if (checkIn.test((C_5265_)entry.getKey())) {
                  ((C_260402_)entry.getValue()).m_260986_();
                  iterator.remove();
               }
            }
         }
      };
      this.f_291450_.forEach(sourceIn -> sourceIn.m_260891_(resourceManagerIn, spritesource$output));
      this.filterSpriteNames(map.keySet());
      Builder<Function<C_260391_, C_243582_>> builder = ImmutableList.builder();
      builder.add((Function)loaderIn -> C_4473_.m_246104_());
      builder.addAll(map.values());
      return builder.build();
   }

   public static C_290208_ m_294995_(C_77_ resourceManagerIn, C_5265_ locationIn) {
      C_5265_ resourcelocation = f_291407_.m_245698_(locationIn);
      List<C_260369_> list = new ArrayList();

      for (C_76_ resource : resourceManagerIn.m_213829_(resourcelocation)) {
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
            f_290791_.error("Failed to parse atlas definition {} in pack {}", new Object[]{resourcelocation, resource.m_215506_(), var11});
         }
      }

      return new C_290208_(list);
   }

   public void addSpriteSources(Collection<C_5265_> spriteNames) {
      for (C_5265_ spriteName : spriteNames) {
         this.f_291450_.add(new C_260420_(spriteName, Optional.empty()));
      }
   }

   public List<C_260369_> getSpriteSources() {
      return this.f_291450_;
   }

   public Set<C_5265_> getSpriteNames(C_77_ resourceManager) {
      Set<C_5265_> spriteNames = new LinkedHashSet();

      for (C_260369_ source : this.f_291450_) {
         C_260371_ out = new SpriteSourceCollector(spriteNames);
         source.m_260891_(resourceManager, out);
      }

      return spriteNames;
   }

   public void filterSpriteNames(Set<C_5265_> spriteNames) {
      String suffixNormal = ShadersTextureType.NORMAL.getSuffix();
      String suffixSpecular = ShadersTextureType.SPECULAR.getSuffix();
      String[] suffixesShaders = new String[]{suffixNormal, suffixSpecular};
      Iterator it = spriteNames.iterator();

      while (it.hasNext()) {
         C_5265_ loc = (C_5265_)it.next();
         String path = loc.m_135815_();
         if (path.endsWith(suffixNormal) || path.endsWith(suffixSpecular)) {
            String pathBase = StrUtils.removeSuffix(path, suffixesShaders);
            C_5265_ locBase = new C_5265_(loc.m_135827_(), pathBase);
            if (spriteNames.contains(locBase)) {
               it.remove();
            }
         }
      }
   }
}
