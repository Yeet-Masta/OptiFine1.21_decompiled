package net.minecraft.src;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.annotation.Nullable;

public class C_290066_ implements C_50_ {
   public final C_50_ f_290850_;
   public final List f_291498_;

   public C_290066_(C_50_ p_i294689_1_, List p_i294689_2_) {
      this.f_290850_ = p_i294689_1_;
      List list = new ArrayList(p_i294689_2_.size() + 1);
      list.addAll(Lists.reverse(p_i294689_2_));
      list.add(p_i294689_1_);
      this.f_291498_ = List.copyOf(list);
   }

   @Nullable
   public C_243587_ m_8017_(String... pathIn) {
      return this.f_290850_.m_8017_(pathIn);
   }

   @Nullable
   public C_243587_ m_214146_(C_51_ type, C_5265_ namespaceIn) {
      Iterator var3 = this.f_291498_.iterator();

      C_243587_ iosupplier;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         C_50_ packresources = (C_50_)var3.next();
         iosupplier = packresources.m_214146_(type, namespaceIn);
      } while(iosupplier == null);

      return iosupplier;
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_50_.C_243658_ outputIn) {
      Map map = new HashMap();
      Iterator var6 = this.f_291498_.iterator();

      while(var6.hasNext()) {
         C_50_ packresources = (C_50_)var6.next();
         Objects.requireNonNull(map);
         packresources.m_8031_(typeIn, namespaceIn, pathIn, map::putIfAbsent);
      }

      map.forEach(outputIn);
   }

   public Set m_5698_(C_51_ type) {
      Set set = new HashSet();
      Iterator var3 = this.f_291498_.iterator();

      while(var3.hasNext()) {
         C_50_ packresources = (C_50_)var3.next();
         set.addAll(packresources.m_5698_(type));
      }

      return set;
   }

   @Nullable
   public Object m_5550_(C_54_ deserializer) throws IOException {
      return this.f_290850_.m_5550_(deserializer);
   }

   public C_313726_ m_318586_() {
      return this.f_290850_.m_318586_();
   }

   public void close() {
      this.f_291498_.forEach(C_50_::close);
   }
}
