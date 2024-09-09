package net.minecraft.server.packs;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.resources.IoSupplier;

public class CompositePackResources implements PackResources {
   public final PackResources f_290850_;
   public final List f_291498_;

   public CompositePackResources(PackResources p_i294689_1_, List p_i294689_2_) {
      this.f_290850_ = p_i294689_1_;
      List list = new ArrayList(p_i294689_2_.size() + 1);
      list.addAll(Lists.reverse(p_i294689_2_));
      list.add(p_i294689_1_);
      this.f_291498_ = List.copyOf(list);
   }

   @Nullable
   public IoSupplier m_8017_(String... pathIn) {
      return this.f_290850_.m_8017_(pathIn);
   }

   @Nullable
   public IoSupplier m_214146_(PackType type, ResourceLocation namespaceIn) {
      Iterator var3 = this.f_291498_.iterator();

      IoSupplier iosupplier;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         PackResources packresources = (PackResources)var3.next();
         iosupplier = packresources.m_214146_(type, namespaceIn);
      } while(iosupplier == null);

      return iosupplier;
   }

   public void m_8031_(PackType typeIn, String namespaceIn, String pathIn, PackResources.ResourceOutput outputIn) {
      Map map = new HashMap();
      Iterator var6 = this.f_291498_.iterator();

      while(var6.hasNext()) {
         PackResources packresources = (PackResources)var6.next();
         Objects.requireNonNull(map);
         packresources.m_8031_(typeIn, namespaceIn, pathIn, map::putIfAbsent);
      }

      map.forEach(outputIn);
   }

   public Set m_5698_(PackType type) {
      Set set = new HashSet();
      Iterator var3 = this.f_291498_.iterator();

      while(var3.hasNext()) {
         PackResources packresources = (PackResources)var3.next();
         set.addAll(packresources.m_5698_(type));
      }

      return set;
   }

   @Nullable
   public Object m_5550_(MetadataSectionSerializer deserializer) throws IOException {
      return this.f_290850_.m_5550_(deserializer);
   }

   public PackLocationInfo m_318586_() {
      return this.f_290850_.m_318586_();
   }

   public void close() {
      this.f_291498_.forEach(PackResources::close);
   }
}
