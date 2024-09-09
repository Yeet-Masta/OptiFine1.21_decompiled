package net.minecraft.src;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.optifine.Lang;
import org.slf4j.Logger;

public class C_4511_ extends C_4907_ {
   private static final Logger f_118909_ = LogUtils.getLogger();
   private final Map f_118910_;
   private final boolean f_118911_;

   private C_4511_(Map resourceMapIn, boolean reorderIn) {
      this.f_118910_ = resourceMapIn;
      this.f_118911_ = reorderIn;
   }

   public static C_4511_ m_264420_(C_77_ resourceManagerIn, List namesIn, boolean reorderIn) {
      Map map = Maps.newHashMap();
      Iterator var4 = namesIn.iterator();

      while(var4.hasNext()) {
         String s = (String)var4.next();
         String s1 = String.format(Locale.ROOT, "lang/%s.json", s);
         Iterator var7 = resourceManagerIn.m_7187_().iterator();

         while(var7.hasNext()) {
            String s2 = (String)var7.next();

            try {
               C_5265_ resourcelocation = C_5265_.m_339182_(s2, s1);
               m_235035_(s, resourceManagerIn.m_213829_(resourcelocation), map);
               Lang.loadResources((C_77_)resourceManagerIn, (String)s, map);
            } catch (Exception var10) {
               f_118909_.warn("Skipped language file: {}:{} ({})", new Object[]{s2, s1, var10.toString()});
            }
         }
      }

      return new C_4511_(ImmutableMap.copyOf(map), reorderIn);
   }

   private static void m_235035_(String nameIn, List resourcesIn, Map mapIn) {
      Iterator var3 = resourcesIn.iterator();

      while(var3.hasNext()) {
         C_76_ resource = (C_76_)var3.next();

         try {
            InputStream inputstream = resource.m_215507_();

            try {
               Objects.requireNonNull(mapIn);
               C_4907_.m_128108_(inputstream, mapIn::put);
            } catch (Throwable var9) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }
               }

               throw var9;
            }

            if (inputstream != null) {
               inputstream.close();
            }
         } catch (IOException var10) {
            f_118909_.warn("Failed to load translations for {} from pack {}", new Object[]{nameIn, resource.m_215506_(), var10});
         }
      }

   }

   public String m_118919_(String keyIn, String defIn) {
      return (String)this.f_118910_.getOrDefault(keyIn, defIn);
   }

   public boolean m_6722_(String keyIn) {
      return this.f_118910_.containsKey(keyIn);
   }

   public boolean m_6627_() {
      return this.f_118911_;
   }

   public C_178_ m_5536_(C_5000_ textIn) {
      return C_4512_.m_118931_(textIn, this.f_118911_);
   }

   public Map getLanguageData() {
      return this.f_118910_;
   }
}
