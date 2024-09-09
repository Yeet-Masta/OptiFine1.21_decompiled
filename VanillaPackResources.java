import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.src.C_140974_;
import net.minecraft.src.C_243532_;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_313726_;
import net.minecraft.src.C_47_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_5144_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_54_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_50_.C_243658_;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class VanillaPackResources implements C_50_ {
   private static final Logger c = LogUtils.getLogger();
   private final C_313726_ d;
   private final C_243532_ e;
   private final Set<String> f;
   private final List<Path> g;
   private final Map<C_51_, List<Path>> h;

   VanillaPackResources(C_313726_ nameIn, C_243532_ metadataIn, Set<String> namespacesIn, List<Path> rootPathsIn, Map<C_51_, List<Path>> typePathsIn) {
      this.d = nameIn;
      this.e = metadataIn;
      this.f = namespacesIn;
      this.g = rootPathsIn;
      this.h = typePathsIn;
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      C_5144_.m_245411_(pathIn);
      List<String> list = List.of(pathIn);

      for (Path path : this.g) {
         Path path1 = C_5144_.m_245247_(path, list);
         if (Files.exists(path1, new LinkOption[0]) && PathPackResources.a(path1)) {
            return C_243587_.m_246697_(path1);
         }
      }

      return null;
   }

   public void a(C_51_ packTypeIn, ResourceLocation locationIn, Consumer<Path> consumerPathIn) {
      C_5144_.m_245538_(locationIn.a()).ifSuccess(partsIn -> {
         String s = locationIn.b();

         for (Path path : (List)this.h.get(packTypeIn)) {
            Path path1 = path.resolve(s);
            consumerPathIn.accept(C_5144_.m_245247_(path1, partsIn));
         }
      }).ifError(errorIn -> c.error("Invalid path {}: {}", locationIn, errorIn.message()));
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      C_243658_ outputOF = (locIn, suppIn) -> {
         if (locIn.a().startsWith("models/block/template_glass_pane")) {
            C_243587_<InputStream> suppOF = this.getResourceOF(typeIn, locIn);
            if (suppOF != null) {
               suppIn = suppOF;
            }
         }

         outputIn.accept(locIn, suppIn);
      };
      C_5144_.m_245538_(pathIn).ifSuccess(partsIn -> {
         List<Path> list = (List<Path>)this.h.get(typeIn);
         int i = list.size();
         if (i == 1) {
            a(outputOF, namespaceIn, (Path)list.get(0), partsIn);
         } else if (i > 1) {
            Map<ResourceLocation, C_243587_<InputStream>> map = new HashMap();

            for (int j = 0; j < i - 1; j++) {
               a(map::putIfAbsent, namespaceIn, (Path)list.get(j), partsIn);
            }

            Path path = (Path)list.get(i - 1);
            if (map.isEmpty()) {
               a(outputOF, namespaceIn, path, partsIn);
            } else {
               a(map::putIfAbsent, namespaceIn, path, partsIn);
               map.forEach(outputOF);
            }
         }
      }).ifError(errorIn -> c.error("Invalid path {}: {}", pathIn, errorIn.message()));
   }

   private static void a(C_243658_ outputIn, String namespaceIn, Path pathIn, List<String> partsIn) {
      Path path = pathIn.resolve(namespaceIn);
      PathPackResources.a(namespaceIn, path, partsIn, outputIn);
   }

   @Nullable
   public C_243587_<InputStream> a(C_51_ type, ResourceLocation namespaceIn) {
      C_243587_<InputStream> res = this.getResourcesImpl(type, namespaceIn);
      return res != null ? res : this.getResourceOF(type, namespaceIn);
   }

   @Nullable
   public C_243587_<InputStream> getResourcesImpl(C_51_ type, ResourceLocation namespaceIn) {
      return (C_243587_<InputStream>)C_5144_.m_245538_(namespaceIn.a()).mapOrElse(partsIn -> {
         String s = namespaceIn.b();

         for (Path path : (List)this.h.get(type)) {
            Path path1 = C_5144_.m_245247_(path.resolve(s), partsIn);
            if (Files.exists(path1, new LinkOption[0]) && PathPackResources.a(path1)) {
               return C_243587_.m_246697_(path1);
            }
         }

         return null;
      }, errorIn -> {
         c.error("Invalid path {}: {}", namespaceIn, errorIn.message());
         return null;
      });
   }

   public Set<String> m_5698_(C_51_ type) {
      return this.f;
   }

   @Nullable
   public <T> T m_5550_(C_54_<T> deserializer) {
      C_243587_<InputStream> iosupplier = this.m_8017_("pack.mcmeta");
      if (iosupplier != null) {
         try {
            InputStream inputstream = (InputStream)iosupplier.m_247737_();

            Object var9;
            label53: {
               try {
                  T t = (T)C_47_.m_10214_(deserializer, inputstream);
                  if (t == null) {
                     var9 = this.e.m_245920_(deserializer);
                     break label53;
                  }

                  var9 = t;
               } catch (Throwable var7) {
                  if (inputstream != null) {
                     try {
                        inputstream.close();
                     } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                     }
                  }

                  throw var7;
               }

               if (inputstream != null) {
                  inputstream.close();
               }

               return (T)var9;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            return (T)var9;
         } catch (IOException var8) {
         }
      }

      return (T)this.e.m_245920_(deserializer);
   }

   public C_313726_ m_318586_() {
      return this.d;
   }

   public void close() {
   }

   public C_140974_ d() {
      return locIn -> Optional.ofNullable(this.a(C_51_.CLIENT_RESOURCES, locIn)).map(supplierIn -> new C_76_(this, supplierIn));
   }

   public C_243587_<InputStream> getResourceOF(C_51_ type, ResourceLocation locationIn) {
      String pathStr = "/" + type.m_10305_() + "/" + locationIn.b() + "/" + locationIn.a();
      InputStream is = ReflectorForge.getOptiFineResourceStream(pathStr);
      if (is != null) {
         return () -> is;
      } else {
         URL url = VanillaPackResources.class.getResource(pathStr);
         return url != null ? () -> url.openStream() : null;
      }
   }
}
