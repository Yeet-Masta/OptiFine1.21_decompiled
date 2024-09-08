package net.minecraft.src;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.src.C_50_.C_243658_;

public class C_290066_ implements C_50_ {
   public final C_50_ f_290850_;
   public final List<C_50_> f_291498_;

   public C_290066_(C_50_ p_i294689_1_, List<C_50_> p_i294689_2_) {
      this.f_290850_ = p_i294689_1_;
      List<C_50_> list = new ArrayList(p_i294689_2_.size() + 1);
      list.addAll(Lists.reverse(p_i294689_2_));
      list.add(p_i294689_1_);
      this.f_291498_ = List.copyOf(list);
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      return this.f_290850_.m_8017_(pathIn);
   }

   @Nullable
   public C_243587_<InputStream> m_214146_(C_51_ type, C_5265_ namespaceIn) {
      for (C_50_ packresources : this.f_291498_) {
         C_243587_<InputStream> iosupplier = packresources.m_214146_(type, namespaceIn);
         if (iosupplier != null) {
            return iosupplier;
         }
      }

      return null;
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      Map<C_5265_, C_243587_<InputStream>> map = new HashMap();

      for (C_50_ packresources : this.f_291498_) {
         packresources.m_8031_(typeIn, namespaceIn, pathIn, map::putIfAbsent);
      }

      map.forEach(outputIn);
   }

   public Set<String> m_5698_(C_51_ type) {
      Set<String> set = new HashSet();

      for (C_50_ packresources : this.f_291498_) {
         set.addAll(packresources.m_5698_(type));
      }

      return set;
   }

   @Nullable
   public <T> T m_5550_(C_54_<T> deserializer) throws IOException {
      return (T)this.f_290850_.m_5550_(deserializer);
   }

   public C_313726_ m_318586_() {
      return this.f_290850_.m_318586_();
   }

   public void close() {
      this.f_291498_.forEach(C_50_::close);
   }
}
