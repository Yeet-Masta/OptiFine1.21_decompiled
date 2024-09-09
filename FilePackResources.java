import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_313726_;
import net.minecraft.src.C_47_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_50_.C_243658_;
import net.minecraft.src.C_58_.C_243513_;
import net.minecraft.src.C_58_.C_313540_;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class FilePackResources extends C_47_ {
   static final Logger f_10204_ = LogUtils.getLogger();
   private final FilePackResources.b d;
   private final String e;

   FilePackResources(C_313726_ nameIn, FilePackResources.b fileIn, String prefixIn) {
      super(nameIn);
      this.d = fileIn;
      this.e = prefixIn;
   }

   private static String b(C_51_ typeIn, ResourceLocation locIn) {
      return String.format(Locale.ROOT, "%s/%s/%s", typeIn.m_10305_(), locIn.b(), locIn.a());
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      return this.b(String.join("/", pathIn));
   }

   public C_243587_<InputStream> a(C_51_ type, ResourceLocation namespaceIn) {
      return this.b(b(type, namespaceIn));
   }

   private String a(String nameIn) {
      return this.e.isEmpty() ? nameIn : this.e + "/" + nameIn;
   }

   @Nullable
   private C_243587_<InputStream> b(String nameIn) {
      ZipFile zipfile = this.d.a();
      if (zipfile == null) {
         return null;
      } else {
         ZipEntry zipentry = zipfile.getEntry(this.a(nameIn));
         return zipentry == null ? null : C_243587_.m_247178_(zipfile, zipentry);
      }
   }

   public Set<String> m_5698_(C_51_ type) {
      ZipFile zipfile = this.d.a();
      if (zipfile == null) {
         return Set.of();
      } else {
         Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
         Set<String> set = Sets.newHashSet();
         String s = this.a(type.m_10305_() + "/");

         while (enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            String s1 = zipentry.getName();
            String s2 = a(s, s1);
            if (!s2.isEmpty()) {
               if (ResourceLocation.j(s2)) {
                  set.add(s2);
               } else {
                  f_10204_.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", s2, this.d.a);
               }
            }
         }

         return set;
      }
   }

   @VisibleForTesting
   public static String a(String namespaceIn, String stringIn) {
      if (!stringIn.startsWith(namespaceIn)) {
         return "";
      } else {
         int i = namespaceIn.length();
         int j = stringIn.indexOf(47, i);
         return j == -1 ? stringIn.substring(i) : stringIn.substring(i, j);
      }
   }

   public void close() {
      this.d.close();
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      ZipFile zipfile = this.d.a();
      if (zipfile != null) {
         Enumeration<? extends ZipEntry> enumeration = zipfile.entries();
         String s = this.a(typeIn.m_10305_() + "/" + namespaceIn + "/");
         String s1 = s + pathIn + "/";

         while (enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            if (!zipentry.isDirectory()) {
               String s2 = zipentry.getName();
               if (s2.startsWith(s1)) {
                  String s3 = s2.substring(s.length());
                  ResourceLocation resourcelocation = ResourceLocation.b(namespaceIn, s3);
                  if (resourcelocation != null) {
                     outputIn.accept(resourcelocation, C_243587_.m_247178_(zipfile, zipentry));
                  } else {
                     f_10204_.warn("Invalid path in datapack: {}:{}, ignoring", namespaceIn, s3);
                  }
               }
            }
         }
      }
   }

   public File getFile() {
      return this.d.a;
   }

   public static class a implements C_243513_ {
      private final File a;

      public a(Path pathIn) {
         this(pathIn.toFile());
      }

      public a(File fileIn) {
         this.a = fileIn;
      }

      public C_50_ m_293078_(C_313726_ nameIn) {
         FilePackResources.b filepackresources$sharedzipfileaccess = new FilePackResources.b(this.a);
         return new FilePackResources(nameIn, filepackresources$sharedzipfileaccess, "");
      }

      public C_50_ m_247679_(C_313726_ nameIn, C_313540_ infoIn) {
         FilePackResources.b filepackresources$sharedzipfileaccess = new FilePackResources.b(this.a);
         C_50_ packresources = new FilePackResources(nameIn, filepackresources$sharedzipfileaccess, "");
         List<String> list = infoIn.f_316499_();
         if (list.isEmpty()) {
            return packresources;
         } else {
            List<C_50_> list1 = new ArrayList(list.size());

            for (String s : list) {
               list1.add(new FilePackResources(nameIn, filepackresources$sharedzipfileaccess, s));
            }

            return new CompositePackResources(packresources, list1);
         }
      }
   }

   static class b implements AutoCloseable {
      final File a;
      @Nullable
      private ZipFile b;
      private boolean c;

      b(File fileIn) {
         this.a = fileIn;
      }

      @Nullable
      ZipFile a() {
         if (this.c) {
            return null;
         } else {
            if (this.b == null) {
               try {
                  this.b = new ZipFile(this.a);
               } catch (IOException var2) {
                  FilePackResources.f_10204_.error("Failed to open pack {}", this.a, var2);
                  this.c = true;
                  return null;
               }
            }

            return this.b;
         }
      }

      public void close() {
         if (this.b != null) {
            IOUtils.closeQuietly(this.b);
            this.b = null;
         }
      }

      protected void finalize() throws Throwable {
         this.close();
         super.finalize();
      }
   }
}
