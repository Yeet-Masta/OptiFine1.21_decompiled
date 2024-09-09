package net.minecraft.client.resources.language;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.FormattedCharSequence;
import net.optifine.Lang;
import org.slf4j.Logger;

public class ClientLanguage extends Language {
   private static final Logger f_118909_ = LogUtils.getLogger();
   private final Map<String, String> f_118910_;
   private final boolean f_118911_;

   private ClientLanguage(Map<String, String> resourceMapIn, boolean reorderIn) {
      this.f_118910_ = resourceMapIn;
      this.f_118911_ = reorderIn;
   }

   public static net.minecraft.client.resources.language.ClientLanguage m_264420_(ResourceManager resourceManagerIn, List<String> namesIn, boolean reorderIn) {
      Map<String, String> map = Maps.newHashMap();

      for (String s : namesIn) {
         String s1 = String.format(Locale.ROOT, "lang/%s.json", s);

         for (String s2 : resourceManagerIn.m_7187_()) {
            try {
               net.minecraft.resources.ResourceLocation resourcelocation = net.minecraft.resources.ResourceLocation.m_339182_(s2, s1);
               m_235035_(s, resourceManagerIn.m_213829_(resourcelocation), map);
               Lang.loadResources(resourceManagerIn, s, map);
            } catch (Exception var10) {
               f_118909_.warn("Skipped language file: {}:{} ({})", new Object[]{s2, s1, var10.toString()});
            }
         }
      }

      return new net.minecraft.client.resources.language.ClientLanguage(ImmutableMap.copyOf(map), reorderIn);
   }

   private static void m_235035_(String nameIn, List<Resource> resourcesIn, Map<String, String> mapIn) {
      for (Resource resource : resourcesIn) {
         try {
            InputStream inputstream = resource.m_215507_();

            try {
               Language.m_128108_(inputstream, mapIn::put);
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

   public FormattedCharSequence m_5536_(FormattedText textIn) {
      return FormattedBidiReorder.m_118931_(textIn, this.f_118911_);
   }

   public Map<String, String> getLanguageData() {
      return this.f_118910_;
   }
}
