package net.minecraft.server.packs;

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
import net.minecraft.FileUtil;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.optifine.reflect.ReflectorForge;
import org.slf4j.Logger;

public class VanillaPackResources implements PackResources {
   private static final Logger f_10315_ = LogUtils.getLogger();
   private final PackLocationInfo f_314150_;
   private final BuiltInMetadata f_243789_;
   private final Set<String> f_10314_;
   private final List<Path> f_244169_;
   private final Map<PackType, List<Path>> f_244459_;

   VanillaPackResources(
      PackLocationInfo nameIn, BuiltInMetadata metadataIn, Set<String> namespacesIn, List<Path> rootPathsIn, Map<PackType, List<Path>> typePathsIn
   ) {
      this.f_314150_ = nameIn;
      this.f_243789_ = metadataIn;
      this.f_10314_ = namespacesIn;
      this.f_244169_ = rootPathsIn;
      this.f_244459_ = typePathsIn;
   }

   @Nullable
   public IoSupplier<InputStream> m_8017_(String... pathIn) {
      FileUtil.m_245411_(pathIn);
      List<String> list = List.of(pathIn);

      for (Path path : this.f_244169_) {
         Path path1 = FileUtil.m_245247_(path, list);
         if (Files.exists(path1, new LinkOption[0]) && net.minecraft.server.packs.PathPackResources.m_246877_(path1)) {
            return IoSupplier.m_246697_(path1);
         }
      }

      return null;
   }

   public void m_245163_(PackType packTypeIn, net.minecraft.resources.ResourceLocation locationIn, Consumer<Path> consumerPathIn) {
      FileUtil.m_245538_(locationIn.m_135815_()).ifSuccess(partsIn -> {
         String s = locationIn.m_135827_();

         for (Path path : (List)this.f_244459_.get(packTypeIn)) {
            Path path1 = path.resolve(s);
            consumerPathIn.accept(FileUtil.m_245247_(path1, partsIn));
         }
      }).ifError(errorIn -> f_10315_.error("Invalid path {}: {}", locationIn, errorIn.message()));
   }

   public void m_8031_(PackType typeIn, String namespaceIn, String pathIn, ResourceOutput outputIn) {
      ResourceOutput outputOF = (locIn, suppIn) -> {
         if (locIn.m_135815_().startsWith("models/block/template_glass_pane")) {
            IoSupplier<InputStream> suppOF = this.getResourceOF(typeIn, locIn);
            if (suppOF != null) {
               suppIn = suppOF;
            }
         }

         outputIn.accept(locIn, suppIn);
      };
      FileUtil.m_245538_(pathIn).ifSuccess(partsIn -> {
         List<Path> list = (List<Path>)this.f_244459_.get(typeIn);
         int i = list.size();
         if (i == 1) {
            m_246310_(outputOF, namespaceIn, (Path)list.get(0), partsIn);
         } else if (i > 1) {
            Map<net.minecraft.resources.ResourceLocation, IoSupplier<InputStream>> map = new HashMap();

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

   private static void m_246310_(ResourceOutput outputIn, String namespaceIn, Path pathIn, List<String> partsIn) {
      Path path = pathIn.resolve(namespaceIn);
      net.minecraft.server.packs.PathPackResources.m_246914_(namespaceIn, path, partsIn, outputIn);
   }

   @Nullable
   public IoSupplier<InputStream> m_214146_(PackType type, net.minecraft.resources.ResourceLocation namespaceIn) {
      IoSupplier<InputStream> res = this.getResourcesImpl(type, namespaceIn);
      return res != null ? res : this.getResourceOF(type, namespaceIn);
   }

   @Nullable
   public IoSupplier<InputStream> getResourcesImpl(PackType type, net.minecraft.resources.ResourceLocation namespaceIn) {
      return (IoSupplier<InputStream>)FileUtil.m_245538_(namespaceIn.m_135815_()).mapOrElse(partsIn -> {
         String s = namespaceIn.m_135827_();

         for (Path path : (List)this.f_244459_.get(type)) {
            Path path1 = FileUtil.m_245247_(path.resolve(s), partsIn);
            if (Files.exists(path1, new LinkOption[0]) && net.minecraft.server.packs.PathPackResources.m_246877_(path1)) {
               return IoSupplier.m_246697_(path1);
            }
         }

         return null;
      }, errorIn -> {
         f_10315_.error("Invalid path {}: {}", namespaceIn, errorIn.message());
         return null;
      });
   }

   public Set<String> m_5698_(PackType type) {
      return this.f_10314_;
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> deserializer) {
      IoSupplier<InputStream> iosupplier = this.m_8017_("pack.mcmeta");
      if (iosupplier != null) {
         try {
            InputStream inputstream = (InputStream)iosupplier.m_247737_();

            Object var9;
            label53: {
               try {
                  T t = (T)AbstractPackResources.m_10214_(deserializer, inputstream);
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

   public PackLocationInfo m_318586_() {
      return this.f_314150_;
   }

   public void close() {
   }

   public ResourceProvider m_215363_() {
      return locIn -> Optional.ofNullable(this.m_214146_(PackType.CLIENT_RESOURCES, locIn)).map(supplierIn -> new Resource(this, supplierIn));
   }

   public IoSupplier<InputStream> getResourceOF(PackType type, net.minecraft.resources.ResourceLocation locationIn) {
      String pathStr = "/" + type.m_10305_() + "/" + locationIn.m_135827_() + "/" + locationIn.m_135815_();
      InputStream is = ReflectorForge.getOptiFineResourceStream(pathStr);
      if (is != null) {
         return () -> is;
      } else {
         URL url = net.minecraft.server.packs.VanillaPackResources.class.getResource(pathStr);
         return url != null ? () -> url.openStream() : null;
      }
   }
}
