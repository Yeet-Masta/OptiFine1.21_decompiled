import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.src.C_141659_;
import net.minecraft.src.C_141660_;
import net.minecraft.src.C_141662_;

public class PartDefinition {
   private final List<C_141660_> a;
   private final C_141659_ b;
   private final Map<String, PartDefinition> c = Maps.newHashMap();
   private String name;

   PartDefinition(List<C_141660_> p_i171580_1_, C_141659_ p_i171580_2_) {
      this.a = p_i171580_1_;
      this.b = p_i171580_2_;
   }

   public PartDefinition a(String p_171599_1_, C_141662_ p_171599_2_, C_141659_ p_171599_3_) {
      PartDefinition partdefinition = new PartDefinition(p_171599_2_.m_171557_(), p_171599_3_);
      partdefinition.setName(p_171599_1_);
      PartDefinition partdefinition1 = (PartDefinition)this.c.put(p_171599_1_, partdefinition);
      if (partdefinition1 != null) {
         partdefinition.c.putAll(partdefinition1.c);
      }

      return partdefinition;
   }

   public ModelPart a(int p_171583_1_, int p_171583_2_) {
      Object2ObjectArrayMap<String, ModelPart> object2objectarraymap = (Object2ObjectArrayMap<String, ModelPart>)this.c
         .entrySet()
         .stream()
         .collect(
            Collectors.toMap(
               Entry::getKey,
               p_171590_2_ -> ((PartDefinition)p_171590_2_.getValue()).a(p_171583_1_, p_171583_2_),
               (p_171594_0_, p_171594_1_) -> p_171594_0_,
               Object2ObjectArrayMap::new
            )
         );
      List<ModelPart.a> list = (List<ModelPart.a>)this.a
         .stream()
         .map(p_171586_2_ -> p_171586_2_.a(p_171583_1_, p_171583_2_))
         .collect(ImmutableList.toImmutableList());
      ModelPart modelpart = new ModelPart(list, object2objectarraymap);
      modelpart.a(this.b);
      modelpart.b(this.b);
      modelpart.setName(this.name);
      return modelpart;
   }

   public PartDefinition a(String p_171597_1_) {
      return (PartDefinition)this.c.get(p_171597_1_);
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
