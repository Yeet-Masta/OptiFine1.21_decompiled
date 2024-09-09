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
import net.minecraft.src.C_243587_;
import net.minecraft.src.C_313726_;
import net.minecraft.src.C_50_;
import net.minecraft.src.C_51_;
import net.minecraft.src.C_54_;
import net.minecraft.src.C_50_.C_243658_;

public class CompositePackResources implements C_50_ {
   public final C_50_ c;
   public final List<C_50_> d;

   public CompositePackResources(C_50_ p_i294689_1_, List<C_50_> p_i294689_2_) {
      this.c = p_i294689_1_;
      List<C_50_> list = new ArrayList(p_i294689_2_.size() + 1);
      list.addAll(Lists.reverse(p_i294689_2_));
      list.add(p_i294689_1_);
      this.d = List.copyOf(list);
   }

   @Nullable
   public C_243587_<InputStream> m_8017_(String... pathIn) {
      return this.c.m_8017_(pathIn);
   }

   @Nullable
   public C_243587_<InputStream> a(C_51_ type, ResourceLocation namespaceIn) {
      for (C_50_ packresources : this.d) {
         C_243587_<InputStream> iosupplier = packresources.a(type, namespaceIn);
         if (iosupplier != null) {
            return iosupplier;
         }
      }

      return null;
   }

   public void m_8031_(C_51_ typeIn, String namespaceIn, String pathIn, C_243658_ outputIn) {
      Map<ResourceLocation, C_243587_<InputStream>> map = new HashMap();

      for (C_50_ packresources : this.d) {
         packresources.m_8031_(typeIn, namespaceIn, pathIn, map::putIfAbsent);
      }

      map.forEach(outputIn);
   }

   public Set<String> m_5698_(C_51_ type) {
      Set<String> set = new HashSet();

      for (C_50_ packresources : this.d) {
         set.addAll(packresources.m_5698_(type));
      }

      return set;
   }

   @Nullable
   public <T> T m_5550_(C_54_<T> deserializer) throws IOException {
      return (T)this.c.m_5550_(deserializer);
   }

   public C_313726_ m_318586_() {
      return this.c.m_318586_();
   }

   public void close() {
      this.d.forEach(C_50_::close);
   }
}
