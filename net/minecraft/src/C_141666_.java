package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class C_141666_ {
   private final List f_171577_;
   private final C_141659_ f_171578_;
   private final Map f_171579_ = Maps.newHashMap();
   private String name;

   C_141666_(List p_i171580_1_, C_141659_ p_i171580_2_) {
      this.f_171577_ = p_i171580_1_;
      this.f_171578_ = p_i171580_2_;
   }

   public C_141666_ m_171599_(String p_171599_1_, C_141662_ p_171599_2_, C_141659_ p_171599_3_) {
      C_141666_ partdefinition = new C_141666_(p_171599_2_.m_171557_(), p_171599_3_);
      partdefinition.setName(p_171599_1_);
      C_141666_ partdefinition1 = (C_141666_)this.f_171579_.put(p_171599_1_, partdefinition);
      if (partdefinition1 != null) {
         partdefinition.f_171579_.putAll(partdefinition1.f_171579_);
      }

      return partdefinition;
   }

   public C_3889_ m_171583_(int p_171583_1_, int p_171583_2_) {
      Object2ObjectArrayMap object2objectarraymap = (Object2ObjectArrayMap)this.f_171579_.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, (p_171590_2_) -> {
         return ((C_141666_)p_171590_2_.getValue()).m_171583_(p_171583_1_, p_171583_2_);
      }, (p_171594_0_, p_171594_1_) -> {
         return p_171594_0_;
      }, Object2ObjectArrayMap::new));
      List list = (List)this.f_171577_.stream().map((p_171586_2_) -> {
         return p_171586_2_.m_171455_(p_171583_1_, p_171583_2_);
      }).collect(ImmutableList.toImmutableList());
      C_3889_ modelpart = new C_3889_(list, object2objectarraymap);
      modelpart.m_233560_(this.f_171578_);
      modelpart.m_171322_(this.f_171578_);
      modelpart.setName(this.name);
      return modelpart;
   }

   public C_141666_ m_171597_(String p_171597_1_) {
      return (C_141666_)this.f_171579_.get(p_171597_1_);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
