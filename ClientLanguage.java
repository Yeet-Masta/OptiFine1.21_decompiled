import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.src.C_178_;
import net.minecraft.src.C_4512_;
import net.minecraft.src.C_4907_;
import net.minecraft.src.C_5000_;
import net.minecraft.src.C_76_;
import net.minecraft.src.C_77_;
import net.optifine.Lang;
import org.slf4j.Logger;

public class ClientLanguage extends C_4907_ {
   private static final Logger f_128101_ = LogUtils.getLogger();
   private final Map<String, String> c;
   private final boolean d;

   private ClientLanguage(Map<String, String> resourceMapIn, boolean reorderIn) {
      this.c = resourceMapIn;
      this.d = reorderIn;
   }

   public static ClientLanguage a(C_77_ resourceManagerIn, List<String> namesIn, boolean reorderIn) {
      Map<String, String> map = Maps.newHashMap();

      for (String s : namesIn) {
         String s1 = String.format(Locale.ROOT, "lang/%s.json", s);

         for (String s2 : resourceManagerIn.m_7187_()) {
            try {
               ResourceLocation resourcelocation = ResourceLocation.a(s2, s1);
               a(s, resourceManagerIn.a(resourcelocation), map);
               Lang.loadResources(resourceManagerIn, s, map);
            } catch (Exception var10) {
               f_128101_.warn("Skipped language file: {}:{} ({})", new Object[]{s2, s1, var10.toString()});
            }
         }
      }

      return new ClientLanguage(ImmutableMap.copyOf(map), reorderIn);
   }

   private static void a(String nameIn, List<C_76_> resourcesIn, Map<String, String> mapIn) {
      for (C_76_ resource : resourcesIn) {
         try {
            InputStream inputstream = resource.m_215507_();

            try {
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
            f_128101_.warn("Failed to load translations for {} from pack {}", new Object[]{nameIn, resource.m_215506_(), var10});
         }
      }
   }

   public String m_118919_(String keyIn, String defIn) {
      return (String)this.c.getOrDefault(keyIn, defIn);
   }

   public boolean m_6722_(String keyIn) {
      return this.c.containsKey(keyIn);
   }

   public boolean m_6627_() {
      return this.d;
   }

   public C_178_ m_5536_(C_5000_ textIn) {
      return C_4512_.m_118931_(textIn, this.d);
   }

   public Map<String, String> getLanguageData() {
      return this.c;
   }
}
