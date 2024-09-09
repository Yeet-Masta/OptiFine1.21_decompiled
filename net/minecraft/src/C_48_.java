package net.minecraft.src;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;
import com.mojang.logging.LogUtils;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;

public class C_48_ extends C_47_ {
   static final Logger f_215322_ = LogUtils.getLogger();
   private final C_290210_ f_291183_;
   private final String f_291427_;

   C_48_(C_313726_ nameIn, C_290210_ fileIn, String prefixIn) {
      super(nameIn);
      this.f_291183_ = fileIn;
      this.f_291427_ = prefixIn;
   }

   private static String m_245721_(C_51_ typeIn, C_5265_ locIn) {
      return String.format(Locale.ROOT, "%s/%s/%s", typeIn.m_10305_(), locIn.m_135827_(), locIn.m_135815_());
   }

   @Nullable
   public C_243587_ m_8017_(String... pathIn) {
      return this.m_247280_(String.join("/", pathIn));
   }

   public C_243587_ m_214146_(C_51_ type, C_5265_ namespaceIn) {
      return this.m_247280_(m_245721_(type, namespaceIn));
   }

   private String m_292954_(String nameIn) {
      return this.f_291427_.isEmpty() ? nameIn : this.f_291427_ + "/" + nameIn;
   }

   @Nullable
   private C_243587_ m_247280_(String nameIn) {
      ZipFile zipfile = this.f_291183_.m_295521_();
      if (zipfile == null) {
         return null;
      } else {
         ZipEntry zipentry = zipfile.getEntry(this.m_292954_(nameIn));
         return zipentry == null ? null : C_243587_.m_247178_(zipfile, zipentry);
      }
   }

   public Set m_5698_(C_51_ type) {
      ZipFile zipfile = this.f_291183_.m_295521_();
      if (zipfile == null) {
         return Set.of();
      } else {
         Enumeration enumeration = zipfile.entries();
         Set set = Sets.newHashSet();
         String s = this.m_292954_(type.m_10305_() + "/");

         while(enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            String s1 = zipentry.getName();
            String s2 = m_293189_(s, s1);
            if (!s2.isEmpty()) {
               if (C_5265_.m_135843_(s2)) {
                  set.add(s2);
               } else {
                  f_215322_.warn("Non [a-z0-9_.-] character in namespace {} in pack {}, ignoring", s2, this.f_291183_.f_291821_);
               }
            }
         }

         return set;
      }
   }

   @VisibleForTesting
   public static String m_293189_(String namespaceIn, String stringIn) {
      if (!stringIn.startsWith(namespaceIn)) {
         return "";
      } else {
         int i = namespaceIn.length();
         int j = stringIn.indexOf(47, i);
         return j == -1 ? stringIn.substring(i) : stringIn.substring(i, j);
      }
   }

   public void close() {
      this.f_291183_.close();
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_50_.C_243658_ outputIn) {
      ZipFile zipfile = this.f_291183_.m_295521_();
      if (zipfile != null) {
         Enumeration enumeration = zipfile.entries();
         String var10001 = typeIn.m_10305_();
         String s = this.m_292954_(var10001 + "/" + namespaceIn + "/");
         String s1 = s + pathIn + "/";

         while(enumeration.hasMoreElements()) {
            ZipEntry zipentry = (ZipEntry)enumeration.nextElement();
            if (!zipentry.isDirectory()) {
               String s2 = zipentry.getName();
               if (s2.startsWith(s1)) {
                  String s3 = s2.substring(s.length());
                  C_5265_ resourcelocation = C_5265_.m_214293_(namespaceIn, s3);
                  if (resourcelocation != null) {
                     outputIn.accept(resourcelocation, C_243587_.m_247178_(zipfile, zipentry));
                  } else {
                     f_215322_.warn("Invalid path in datapack: {}:{}, ignoring", namespaceIn, s3);
                  }
               }
            }
         }
      }

   }

   public File getFile() {
      return this.f_291183_.f_291821_;
   }

   static class C_290210_ implements AutoCloseable {
      final File f_291821_;
      @Nullable
      private ZipFile f_291060_;
      private boolean f_291441_;

      C_290210_(File fileIn) {
         this.f_291821_ = fileIn;
      }

      @Nullable
      ZipFile m_295521_() {
         if (this.f_291441_) {
            return null;
         } else {
            if (this.f_291060_ == null) {
               try {
                  this.f_291060_ = new ZipFile(this.f_291821_);
               } catch (IOException var2) {
                  C_48_.f_215322_.error("Failed to open pack {}", this.f_291821_, var2);
                  this.f_291441_ = true;
                  return null;
               }
            }

            return this.f_291060_;
         }
      }

      public void close() {
         if (this.f_291060_ != null) {
            IOUtils.closeQuietly(this.f_291060_);
            this.f_291060_ = null;
         }

      }

      protected void finalize() throws Throwable {
         this.close();
         super.finalize();
      }
   }

   public static class C_290229_ implements C_58_.C_243513_ {
      private final File f_290829_;

      public C_290229_(Path pathIn) {
         this(pathIn.toFile());
      }

      public C_290229_(File fileIn) {
         this.f_290829_ = fileIn;
      }

      public C_50_ m_293078_(C_313726_ nameIn) {
         C_290210_ filepackresources$sharedzipfileaccess = new C_290210_(this.f_290829_);
         return new C_48_(nameIn, filepackresources$sharedzipfileaccess, "");
      }

      public C_50_ m_247679_(C_313726_ nameIn, C_58_.C_313540_ infoIn) {
         C_290210_ filepackresources$sharedzipfileaccess = new C_290210_(this.f_290829_);
         C_50_ packresources = new C_48_(nameIn, filepackresources$sharedzipfileaccess, "");
         List list = infoIn.f_316499_();
         if (list.isEmpty()) {
            return packresources;
         } else {
            List list1 = new ArrayList(list.size());
            Iterator var7 = list.iterator();

            while(var7.hasNext()) {
               String s = (String)var7.next();
               list1.add(new C_48_(nameIn, filepackresources$sharedzipfileaccess, s));
            }

            return new C_290066_(packresources, list1);
         }
      }
   }
}
