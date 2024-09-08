package net.minecraft.src;

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
import net.minecraft.src.C_50_.C_243658_;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class C_53_ implements C_50_ {
   private static final Logger f_10315_ = LogUtils.getLogger();
   private final C_313726_ f_314150_;
   private final C_243532_ f_243789_;
   private final Set<String> f_10314_;
   private final List<Path> f_244169_;
   private final Map<C_51_, List<Path>> f_244459_;

   C_53_(C_313726_ nameIn, C_243532_ metadataIn, Set<String> namespacesIn, List<Path> rootPathsIn, Map<C_51_, List<Path>> typePathsIn) {
      this.f_314150_ = nameIn;
      this.f_243789_ = metadataIn;
      this.f_10314_ = namespacesIn;
      this.f_244169_ = rootPathsIn;
      this.f_244459_ = typePathsIn;
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      C_5144_.m_245411_(pathIn);
      List<String> list = List.of(pathIn);

      for (Path path : this.f_244169_) {
         Path path1 = C_5144_.m_245247_(path, list);
         if (Files.exists(path1, new LinkOption[0]) && C_243631_.m_246877_(path1)) {
            return C_243587_.m_246697_(path1);
         }
      }

      return null;
   }

   public void m_245163_(C_51_ packTypeIn, C_5265_ locationIn, Consumer<Path> consumerPathIn) {
      C_5144_.m_245538_(locationIn.m_135815_()).ifSuccess(partsIn -> {
         String s = locationIn.m_135827_();

         for (Path path : (List)this.f_244459_.get(packTypeIn)) {
            Path path1 = path.resolve(s);
            consumerPathIn.accept(C_5144_.m_245247_(path1, partsIn));
         }
      }).ifError(errorIn -> f_10315_.error("Invalid path {}: {}", locationIn, errorIn.message()));
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      C_243658_ outputOF = (locIn, suppIn) -> {
         if (locIn.m_135815_().startsWith("models/block/template_glass_pane")) {
            C_243587_<InputStream> suppOF = this.getResourceOF(typeIn, locIn);
            if (suppOF != null) {
               suppIn = suppOF;
            }
         }

         outputIn.accept(locIn, suppIn);
      };
      C_5144_.m_245538_(pathIn).ifSuccess(partsIn -> {
         List<Path> list = (List<Path>)this.f_244459_.get(typeIn);
         int i = list.size();
         if (i == 1) {
            m_246310_(outputOF, namespaceIn, (Path)list.get(0), partsIn);
         } else if (i > 1) {
            Map<C_5265_, C_243587_<InputStream>> map = new HashMap();

            for (int j = 0; j < i - 1; j++) {
               m_246310_(map::putIfAbsent, namespaceIn, (Path)list.get(j), partsIn);
            }

            Path path = (Path)list.get(i - 1);
            if (map.isEmpty()) {
               m_246310_(outputOF, namespaceIn, path, partsIn);
            } else {
               m_246310_(map::putIfAbsent, namespaceIn, path, partsIn);
               map.forEach(outputOF);
            }
         }
      }).ifError(errorIn -> f_10315_.error("Invalid path {}: {}", pathIn, errorIn.message()));
   }

   private static void m_246310_(C_243658_ outputIn, String namespaceIn, Path pathIn, List<String> partsIn) {
      Path path = pathIn.resolve(namespaceIn);
      C_243631_.m_246914_(namespaceIn, path, partsIn, outputIn);
   }

   @Nullable
   public C_243587_<InputStream> m_214146_(C_51_ type, C_5265_ namespaceIn) {
      C_243587_<InputStream> res = this.getResourcesImpl(type, namespaceIn);
      return res != null ? res : this.getResourceOF(type, namespaceIn);
   }

   @Nullable
   public C_243587_<InputStream> getResourcesImpl(C_51_ type, C_5265_ namespaceIn) {
      return (C_243587_<InputStream>)C_5144_.m_245538_(namespaceIn.m_135815_()).mapOrElse(partsIn -> {
         String s = namespaceIn.m_135827_();

         for (Path path : (List)this.f_244459_.get(type)) {
            Path path1 = C_5144_.m_245247_(path.resolve(s), partsIn);
            if (Files.exists(path1, new LinkOption[0]) && C_243631_.m_246877_(path1)) {
               return C_243587_.m_246697_(path1);
            }
         }

         return null;
      }, errorIn -> {
         f_10315_.error("Invalid path {}: {}", namespaceIn, errorIn.message());
         return null;
      });
   }

   public Set<String> m_5698_(C_51_ type) {
      return this.f_10314_;
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
                     var9 = this.f_243789_.m_245920_(deserializer);
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

      return (T)this.f_243789_.m_245920_(deserializer);
   }

   public C_313726_ m_318586_() {
      return this.f_314150_;
   }

   public void close() {
   }

   public C_140974_ m_215363_() {
      return locIn -> Optional.ofNullable(this.m_214146_(C_51_.CLIENT_RESOURCES, locIn)).map(supplierIn -> new C_76_(this, supplierIn));
   }

   public C_243587_<InputStream> getResourceOF(C_51_ type, C_5265_ locationIn) {
      String pathStr = "/" + type.m_10305_() + "/" + locationIn.m_135827_() + "/" + locationIn.m_135815_();
      InputStream is = ReflectorForge.getOptiFineResourceStream(pathStr);
      if (is != null) {
         return () -> is;
      } else {
         URL url = C_53_.class.getResource(pathStr);
         return url != null ? () -> url.openStream() : null;
      }
   }
}
