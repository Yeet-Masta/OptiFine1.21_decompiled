package net.minecraft.client.model.geom.builders;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;

public class PartDefinition {
   private final List<CubeDefinition> f_171577_;
   private final PartPose f_171578_;
   private final Map<String, PartDefinition> f_171579_ = Maps.newHashMap();
   private String name;

   PartDefinition(List<CubeDefinition> p_i171580_1_, PartPose p_i171580_2_) {
      this.f_171577_ = p_i171580_1_;
      this.f_171578_ = p_i171580_2_;
   }

   public PartDefinition m_171599_(String p_171599_1_, CubeListBuilder p_171599_2_, PartPose p_171599_3_) {
      PartDefinition partdefinition = new PartDefinition(p_171599_2_.m_171557_(), p_171599_3_);
      partdefinition.setName(p_171599_1_);
      PartDefinition partdefinition1 = (PartDefinition)this.f_171579_.put(p_171599_1_, partdefinition);
      if (partdefinition1 != null) {
         partdefinition.f_171579_.putAll(partdefinition1.f_171579_);
      }

      return partdefinition;
   }

   public ModelPart m_171583_(int p_171583_1_, int p_171583_2_) {
      Object2ObjectArrayMap<String, ModelPart> object2objectarraymap = (Object2ObjectArrayMap<String, ModelPart>)this.f_171579_
         .entrySet()
         .stream()
         .collect(
            Collectors.toMap(
               Entry::getKey,
               p_171590_2_ -> ((PartDefinition)p_171590_2_.getValue()).m_171583_(p_171583_1_, p_171583_2_),
               (p_171594_0_, p_171594_1_) -> p_171594_0_,
               Object2ObjectArrayMap::new
            )
         );
      List<ModelPart.Cube> list = (List<ModelPart.Cube>)this.f_171577_
         .stream()
         .map(p_171586_2_ -> p_171586_2_.m_171455_(p_171583_1_, p_171583_2_))
         .collect(ImmutableList.toImmutableList());
      ModelPart modelpart = new ModelPart(list, object2objectarraymap);
      modelpart.m_233560_(this.f_171578_);
      modelpart.m_171322_(this.f_171578_);
      modelpart.setName(this.name);
      return modelpart;
   }

   public PartDefinition m_171597_(String p_171597_1_) {
      return (PartDefinition)this.f_171579_.get(p_171597_1_);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
