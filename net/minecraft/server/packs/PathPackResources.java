package net.minecraft.server.packs;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import org.slf4j.Logger;

public class PathPackResources extends AbstractPackResources {
   private static final Logger f_244043_ = LogUtils.getLogger();
   private static final Joiner f_244478_ = Joiner.on("/");
   public final Path f_243919_;

   public PathPackResources(PackLocationInfo nameIn, Path pathIn) {
      super(nameIn);
      this.f_243919_ = pathIn;
   }

   @Nullable
   public IoSupplier m_8017_(String... pathIn) {
      FileUtil.m_245411_(pathIn);
      Path path = FileUtil.m_245247_(this.f_243919_, List.of(pathIn));
      return Files.exists(path, new LinkOption[0]) ? IoSupplier.m_246697_(path) : null;
   }

   public static boolean m_246877_(Path pathIn) {
      return true;
   }

   @Nullable
   public IoSupplier m_214146_(PackType type, ResourceLocation namespaceIn) {
      Path path = this.f_243919_.resolve(type.m_10305_()).resolve(namespaceIn.m_135827_());
      return m_247113_(namespaceIn, path);
   }

   @Nullable
   public static IoSupplier m_247113_(ResourceLocation locIn, Path pathIn) {
      return (IoSupplier)FileUtil.m_245538_(locIn.m_135815_()).mapOrElse((listIn) -> {
         Path path = FileUtil.m_245247_(pathIn, listIn);
         return m_246992_(path);
      }, (errorIn) -> {
         f_244043_.error("Invalid path {}: {}", locIn, errorIn.message());
         return null;
      });
   }

   @Nullable
   private static IoSupplier m_246992_(Path pathIn) {
      return Files.exists(pathIn, new LinkOption[0]) && m_246877_(pathIn) ? IoSupplier.m_246697_(pathIn) : null;
   }

   public void m_8031_(PackType typeIn, String namespaceIn, String pathIn, PackResources.ResourceOutput outputIn) {
      FileUtil.m_245538_(pathIn).ifSuccess((listIn) -> {
         Path path = this.f_243919_.resolve(typeIn.m_10305_()).resolve(namespaceIn);
         m_246914_(namespaceIn, path, listIn, outputIn);
      }).ifError((errorIn) -> {
         f_244043_.error("Invalid path {}: {}", pathIn, errorIn.message());
      });
   }

   public static void m_246914_(String namespaceIn, Path pathIn, List partsIn, PackResources.ResourceOutput outputIn) {
      Path path = FileUtil.m_245247_(pathIn, partsIn);

      try {
         Stream stream = Files.find(path, Integer.MAX_VALUE, (path2In, attrsIn) -> {
            return attrsIn.isRegularFile();
         }, new FileVisitOption[0]);

         try {
            stream.forEach((path3In) -> {
               String s = f_244478_.join(pathIn.relativize(path3In));
               ResourceLocation resourcelocation = ResourceLocation.m_214293_(namespaceIn, s);
               if (resourcelocation == null) {
                  Util.m_143785_(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", namespaceIn, s));
               } else {
                  outputIn.accept(resourcelocation, IoSupplier.m_246697_(path3In));
               }

            });
         } catch (Throwable var9) {
            if (stream != null) {
               try {
                  stream.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }
            }

            throw var9;
         }

         if (stream != null) {
            stream.close();
         }
      } catch (NoSuchFileException | NotDirectoryException var10) {
      } catch (IOException var11) {
         f_244043_.error("Failed to list path {}", path, var11);
      }

   }

   public Set m_5698_(PackType type) {
      Set set = Sets.newHashSet();
      Path path = this.f_243919_.resolve(type.m_10305_());

      try {
         DirectoryStream directorystream = Files.newDirectoryStream(path);

         try {
            Iterator var5 = directorystream.iterator();

            while(var5.hasNext()) {
               Path path1 = (Path)var5.next();
               String s = path1.getFileName().toString();
               if (ResourceLocation.m_135843_(s)) {
                  set.add(s);
               } else {
                  f_244043_.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", s, this.f_243919_);
               }
            }
         } catch (Throwable var9) {
            if (directorystream != null) {
               try {
                  directorystream.close();
               } catch (Throwable var8) {
                  var9.addSuppressed(var8);
               }
            }

            throw var9;
         }

         if (directorystream != null) {
            directorystream.close();
         }
      } catch (NoSuchFileException | NotDirectoryException var10) {
      } catch (IOException var11) {
         f_244043_.error("Failed to list path {}", path, var11);
      }

      return set;
   }

   public void close() {
   }

   public static class PathResourcesSupplier implements Pack.ResourcesSupplier {
      private final Path f_290684_;

      public PathResourcesSupplier(Path pathIn) {
         this.f_290684_ = pathIn;
      }

      public PackResources m_293078_(PackLocationInfo nameIn) {
         return new PathPackResources(nameIn, this.f_290684_);
      }

      public PackResources m_247679_(PackLocationInfo nameIn, Pack.Metadata infoIn) {
         PackResources packresources = this.m_293078_(nameIn);
         List list = infoIn.f_316499_();
         if (list.isEmpty()) {
            return packresources;
         } else {
            List list1 = new ArrayList(list.size());
            Iterator var6 = list.iterator();

            while(var6.hasNext()) {
               String s = (String)var6.next();
               Path path = this.f_290684_.resolve(s);
               list1.add(new PathPackResources(nameIn, path));
            }

            return new CompositePackResources(packresources, list1);
         }
      }
   }
}
