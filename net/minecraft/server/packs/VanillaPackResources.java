package net.minecraft.server.packs;

import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.resources.ResourceLocation;
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
   private final Set f_10314_;
   private final List f_244169_;
   private final Map f_244459_;

   VanillaPackResources(PackLocationInfo nameIn, BuiltInMetadata metadataIn, Set namespacesIn, List rootPathsIn, Map typePathsIn) {
      this.f_314150_ = nameIn;
      this.f_243789_ = metadataIn;
      this.f_10314_ = namespacesIn;
      this.f_244169_ = rootPathsIn;
      this.f_244459_ = typePathsIn;
   }

   @Nullable
   public IoSupplier m_8017_(String... pathIn) {
      FileUtil.m_245411_(pathIn);
      List list = List.of(pathIn);
      Iterator var3 = this.f_244169_.iterator();

      Path path1;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         Path path = (Path)var3.next();
         path1 = FileUtil.m_245247_(path, list);
      } while(!Files.exists(path1, new LinkOption[0]) || !PathPackResources.m_246877_(path1));

      return IoSupplier.m_246697_(path1);
   }

   public void m_245163_(PackType packTypeIn, ResourceLocation locationIn, Consumer consumerPathIn) {
      FileUtil.m_245538_(locationIn.m_135815_()).ifSuccess((partsIn) -> {
         String s = locationIn.m_135827_();
         Iterator var6 = ((List)this.f_244459_.get(packTypeIn)).iterator();

         while(var6.hasNext()) {
            Path path = (Path)var6.next();
            Path path1 = path.resolve(s);
            consumerPathIn.accept(FileUtil.m_245247_(path1, partsIn));
         }

      }).ifError((errorIn) -> {
         f_10315_.error("Invalid path {}: {}", locationIn, errorIn.message());
      });
   }

   public void m_8031_(PackType typeIn, String namespaceIn, String pathIn, PackResources.ResourceOutput outputIn) {
      PackResources.ResourceOutput outputOF = (locIn, suppIn) -> {
         if (locIn.m_135815_().startsWith("models/block/template_glass_pane")) {
            IoSupplier suppOF = this.getResourceOF(typeIn, locIn);
            if (suppOF != null) {
               suppIn = suppOF;
            }
         }

         outputIn.accept(locIn, suppIn);
      };
      FileUtil.m_245538_(pathIn).ifSuccess((partsIn) -> {
         List list = (List)this.f_244459_.get(typeIn);
         int i = list.size();
         if (i == 1) {
            m_246310_(outputOF, namespaceIn, (Path)list.get(0), partsIn);
         } else if (i > 1) {
            Map map = new HashMap();

            for(int j = 0; j < i - 1; ++j) {
               Objects.requireNonNull(map);
               m_246310_(map::putIfAbsent, namespaceIn, (Path)list.get(j), partsIn);
            }

            Path path = (Path)list.get(i - 1);
            if (map.isEmpty()) {
               m_246310_(outputOF, namespaceIn, path, partsIn);
            } else {
               Objects.requireNonNull(map);
               m_246310_(map::putIfAbsent, namespaceIn, path, partsIn);
               map.forEach(outputOF);
            }
         }

      }).ifError((errorIn) -> {
         f_10315_.error("Invalid path {}: {}", pathIn, errorIn.message());
      });
   }

   private static void m_246310_(PackResources.ResourceOutput outputIn, String namespaceIn, Path pathIn, List partsIn) {
      Path path = pathIn.resolve(namespaceIn);
      PathPackResources.m_246914_(namespaceIn, path, partsIn, outputIn);
   }

   @Nullable
   public IoSupplier m_214146_(PackType type, ResourceLocation namespaceIn) {
      IoSupplier res = this.getResourcesImpl(type, namespaceIn);
      return res != null ? res : this.getResourceOF(type, namespaceIn);
   }

   @Nullable
   public IoSupplier getResourcesImpl(PackType type, ResourceLocation namespaceIn) {
      return (IoSupplier)FileUtil.m_245538_(namespaceIn.m_135815_()).mapOrElse((partsIn) -> {
         String s = namespaceIn.m_135827_();
         Iterator var5 = ((List)this.f_244459_.get(type)).iterator();

         Path path1;
         do {
            if (!var5.hasNext()) {
               return null;
            }

            Path path = (Path)var5.next();
            path1 = FileUtil.m_245247_(path.resolve(s), partsIn);
         } while(!Files.exists(path1, new LinkOption[0]) || !PathPackResources.m_246877_(path1));

         return IoSupplier.m_246697_(path1);
      }, (errorIn) -> {
         f_10315_.error("Invalid path {}: {}", namespaceIn, errorIn.message());
         return null;
      });
   }

   public Set m_5698_(PackType type) {
      return this.f_10314_;
   }

   @Nullable
   public Object m_5550_(MetadataSectionSerializer deserializer) {
      IoSupplier iosupplier = this.m_8017_("pack.mcmeta");
      if (iosupplier != null) {
         try {
            InputStream inputstream = (InputStream)iosupplier.m_247737_();

            Object var5;
            label53: {
               try {
                  Object t = AbstractPackResources.m_10214_(deserializer, inputstream);
                  if (t != null) {
                     var5 = t;
                     break label53;
                  }

                  var5 = this.f_243789_.m_245920_(deserializer);
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

               return var5;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            return var5;
         } catch (IOException var8) {
         }
      }

      return this.f_243789_.m_245920_(deserializer);
   }

   public PackLocationInfo m_318586_() {
      return this.f_314150_;
   }

   public void close() {
   }

   public ResourceProvider m_215363_() {
      return (locIn) -> {
         return Optional.ofNullable(this.m_214146_(PackType.CLIENT_RESOURCES, locIn)).map((supplierIn) -> {
            return new Resource(this, supplierIn);
         });
      };
   }

   public IoSupplier getResourceOF(PackType type, ResourceLocation locationIn) {
      String var10000 = type.m_10305_();
      String pathStr = "/" + var10000 + "/" + locationIn.m_135827_() + "/" + locationIn.m_135815_();
      InputStream is = ReflectorForge.getOptiFineResourceStream(pathStr);
      if (is != null) {
         return () -> {
            return is;
         };
      } else {
         URL url = VanillaPackResources.class.getResource(pathStr);
         return url != null ? () -> {
            return url.openStream();
         } : null;
      }
   }
}
