package net.minecraft.src;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_50_.C_243658_;
import net.minecraft.src.C_58_.C_243513_;
import net.minecraft.src.C_58_.C_313540_;
import org.slf4j.Logger;

public class C_243631_ extends C_47_ {
   private static final Logger f_244043_ = LogUtils.getLogger();
   private static final Joiner f_244478_ = Joiner.on("/");
   public final Path f_243919_;

   public C_243631_(C_313726_ nameIn, Path pathIn) {
      super(nameIn);
      this.f_243919_ = pathIn;
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      C_5144_.m_245411_(pathIn);
      Path path = C_5144_.m_245247_(this.f_243919_, List.of(pathIn));
      return Files.exists(path, new LinkOption[0]) ? C_243587_.m_246697_(path) : null;
   }

   public static boolean m_246877_(Path pathIn) {
      return true;
   }

   @Nullable
   public C_243587_<InputStream> m_214146_(C_51_ type, C_5265_ namespaceIn) {
      Path path = this.f_243919_.resolve(type.m_10305_()).resolve(namespaceIn.m_135827_());
      return m_247113_(namespaceIn, path);
   }

   @Nullable
   public static C_243587_<InputStream> m_247113_(C_5265_ locIn, Path pathIn) {
      return (C_243587_<InputStream>)C_5144_.m_245538_(locIn.m_135815_()).mapOrElse(listIn -> {
         Path path = C_5144_.m_245247_(pathIn, listIn);
         return m_246992_(path);
      }, errorIn -> {
         f_244043_.error("Invalid path {}: {}", locIn, errorIn.message());
         return null;
      });
   }

   @Nullable
   private static C_243587_<InputStream> m_246992_(Path pathIn) {
      return Files.exists(pathIn, new LinkOption[0]) && m_246877_(pathIn) ? C_243587_.m_246697_(pathIn) : null;
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      C_5144_.m_245538_(pathIn).ifSuccess(listIn -> {
         Path path = this.f_243919_.resolve(typeIn.m_10305_()).resolve(namespaceIn);
         m_246914_(namespaceIn, path, listIn, outputIn);
      }).ifError(errorIn -> f_244043_.error("Invalid path {}: {}", pathIn, errorIn.message()));
   }

   public static void m_246914_(String namespaceIn, Path pathIn, List<String> partsIn, C_243658_ outputIn) {
      Path path = C_5144_.m_245247_(pathIn, partsIn);

      try {
         Stream<Path> stream = Files.find(path, Integer.MAX_VALUE, (path2In, attrsIn) -> attrsIn.isRegularFile(), new FileVisitOption[0]);

         try {
            stream.forEach(path3In -> {
               String s = f_244478_.join(pathIn.relativize(path3In));
               C_5265_ resourcelocation = C_5265_.m_214293_(namespaceIn, s);
               if (resourcelocation == null) {
                  C_5322_.m_143785_(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", namespaceIn, s));
               } else {
                  outputIn.accept(resourcelocation, C_243587_.m_246697_(path3In));
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

   public Set<String> m_5698_(C_51_ type) {
      Set<String> set = Sets.newHashSet();
      Path path = this.f_243919_.resolve(type.m_10305_());

      try {
         DirectoryStream<Path> directorystream = Files.newDirectoryStream(path);

         try {
            for (Path path1 : directorystream) {
               String s = path1.getFileName().toString();
               if (C_5265_.m_135843_(s)) {
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

   public static class C_290098_ implements C_243513_ {
      private final Path f_290684_;

      public C_290098_(Path pathIn) {
         this.f_290684_ = pathIn;
      }

      public C_50_ m_293078_(C_313726_ nameIn) {
         return new C_243631_(nameIn, this.f_290684_);
      }

      public C_50_ m_247679_(C_313726_ nameIn, C_313540_ infoIn) {
         C_50_ packresources = this.m_293078_(nameIn);
         List<String> list = infoIn.f_316499_();
         if (list.isEmpty()) {
            return packresources;
         } else {
            List<C_50_> list1 = new ArrayList(list.size());

            for (String s : list) {
               Path path = this.f_290684_.resolve(s);
               list1.add(new C_243631_(nameIn, path));
            }

            return new C_290066_(packresources, list1);
         }
      }
   }
}
