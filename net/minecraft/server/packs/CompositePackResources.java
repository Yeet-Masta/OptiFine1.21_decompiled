package net.minecraft.server.packs;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources.ResourceOutput;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;

public class CompositePackResources implements PackResources {
   public PackResources f_290850_;
   public List<PackResources> f_291498_;

   public CompositePackResources(PackResources p_i294689_1_, List<PackResources> p_i294689_2_) {
      this.f_290850_ = p_i294689_1_;
      List<PackResources> list = new ArrayList(p_i294689_2_.size() + 1);
      list.addAll(Lists.reverse(p_i294689_2_));
      list.add(p_i294689_1_);
      this.f_291498_ = List.copyOf(list);
   }

   @Nullable
   public IoSupplier<InputStream> m_8017_(String... pathIn) {
      return this.f_290850_.m_8017_(pathIn);
   }

   @Nullable
   public IoSupplier<InputStream> m_214146_(PackType type, ResourceLocation namespaceIn) {
      for (PackResources packresources : this.f_291498_) {
         IoSupplier<InputStream> iosupplier = packresources.m_214146_(type, namespaceIn);
         if (iosupplier != null) {
            return iosupplier;
         }
      }

      return null;
   }

   public void m_8031_(PackType typeIn, String namespaceIn, String pathIn, ResourceOutput outputIn) {
      Map<ResourceLocation, IoSupplier<InputStream>> map = new HashMap();

      for (PackResources packresources : this.f_291498_) {
         packresources.m_8031_(typeIn, namespaceIn, pathIn, map::putIfAbsent);
      }

      map.forEach(outputIn);
   }

   public Set<String> m_5698_(PackType type) {
      Set<String> set = new HashSet();

      for (PackResources packresources : this.f_291498_) {
         set.addAll(packresources.m_5698_(type));
      }

      return set;
   }

   @Nullable
   public <T> T m_5550_(MetadataSectionSerializer<T> deserializer) throws IOException {
      return (T)this.f_290850_.m_5550_(deserializer);
   }

   public PackLocationInfo m_318586_() {
      return this.f_290850_.m_318586_();
   }

   public void close() {
      this.f_291498_.forEach(PackResources::close);
   }
}
