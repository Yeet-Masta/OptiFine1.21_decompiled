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
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_313726_;
import net.minecraft.src.C_47_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_5144_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_50_.C_243658_;
import net.minecraft.src.C_58_.C_243513_;
import net.minecraft.src.C_58_.C_313540_;
import org.slf4j.Logger;

public class PathPackResources extends C_47_ {
   private static final Logger f_10204_ = LogUtils.getLogger();
   private static final Joiner d = Joiner.on("/");
   public final Path e;

   public PathPackResources(C_313726_ nameIn, Path pathIn) {
      super(nameIn);
      this.e = pathIn;
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      C_5144_.m_245411_(pathIn);
      Path path = C_5144_.m_245247_(this.e, List.of(pathIn));
      return Files.exists(path, new LinkOption[0]) ? C_243587_.m_246697_(path) : null;
   }

   public static boolean a(Path pathIn) {
      return true;
   }

   @Nullable
   public C_243587_<InputStream> a(C_51_ type, ResourceLocation namespaceIn) {
      Path path = this.e.resolve(type.m_10305_()).resolve(namespaceIn.b());
      return a(namespaceIn, path);
   }

   @Nullable
   public static C_243587_<InputStream> a(ResourceLocation locIn, Path pathIn) {
      return (C_243587_<InputStream>)C_5144_.m_245538_(locIn.a()).mapOrElse(listIn -> {
         Path path = C_5144_.m_245247_(pathIn, listIn);
         return b(path);
      }, errorIn -> {
         f_10204_.error("Invalid path {}: {}", locIn, errorIn.message());
         return null;
      });
   }

   @Nullable
   private static C_243587_<InputStream> b(Path pathIn) {
      return Files.exists(pathIn, new LinkOption[0]) && a(pathIn) ? C_243587_.m_246697_(pathIn) : null;
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      C_5144_.m_245538_(pathIn).ifSuccess(listIn -> {
         Path path = this.e.resolve(typeIn.m_10305_()).resolve(namespaceIn);
         a(namespaceIn, path, listIn, outputIn);
      }).ifError(errorIn -> f_10204_.error("Invalid path {}: {}", pathIn, errorIn.message()));
   }

   public static void a(String namespaceIn, Path pathIn, List<String> partsIn, C_243658_ outputIn) {
      Path path = C_5144_.m_245247_(pathIn, partsIn);

      try {
         Stream<Path> stream = Files.find(path, Integer.MAX_VALUE, (path2In, attrsIn) -> attrsIn.isRegularFile(), new FileVisitOption[0]);

         try {
            stream.forEach(path3In -> {
               String s = d.join(pathIn.relativize(path3In));
               ResourceLocation resourcelocation = ResourceLocation.b(namespaceIn, s);
               if (resourcelocation == null) {
                  Util.b(String.format(Locale.ROOT, "Invalid path in pack: %s:%s, ignoring", namespaceIn, s));
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
         f_10204_.error("Failed to list path {}", path, var11);
      }
   }

   public Set<String> m_5698_(C_51_ type) {
      Set<String> set = Sets.newHashSet();
      Path path = this.e.resolve(type.m_10305_());

      try {
         DirectoryStream<Path> directorystream = Files.newDirectoryStream(path);

         try {
            for (Path path1 : directorystream) {
               String s = path1.getFileName().toString();
               if (ResourceLocation.j(s)) {
                  set.add(s);
               } else {
                  f_10204_.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", s, this.e);
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
         f_10204_.error("Failed to list path {}", path, var11);
      }

      return set;
   }

   public void close() {
   }

   public static class a implements C_243513_ {
      private final Path a;

      public a(Path pathIn) {
         this.a = pathIn;
      }

      public C_50_ m_293078_(C_313726_ nameIn) {
         return new PathPackResources(nameIn, this.a);
      }

      public C_50_ m_247679_(C_313726_ nameIn, C_313540_ infoIn) {
         C_50_ packresources = this.m_293078_(nameIn);
         List<String> list = infoIn.f_316499_();
         if (list.isEmpty()) {
            return packresources;
         } else {
            List<C_50_> list1 = new ArrayList(list.size());

            for (String s : list) {
               Path path = this.a.resolve(s);
               list1.add(new PathPackResources(nameIn, path));
            }

            return new CompositePackResources(packresources, list1);
         }
      }
   }
}
