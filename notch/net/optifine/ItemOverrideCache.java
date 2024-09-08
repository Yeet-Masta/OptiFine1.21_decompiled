package net.optifine;

import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4217_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_4217_.C_141725_;
import net.optifine.reflect.Reflector;
import net.optifine.util.CompoundKey;

public class ItemOverrideCache {
   private ItemOverrideProperty[] itemOverrideProperties;
   private Map<CompoundKey, Integer> mapModelIndexes = new HashMap();
   public static final Integer INDEX_NONE = new Integer(-1);

   public ItemOverrideCache(ItemOverrideProperty[] itemOverrideProperties) {
      this.itemOverrideProperties = itemOverrideProperties;
   }

   public Integer getModelIndex(C_1391_ stack, C_3899_ world, C_524_ entity) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      return valueKey == null ? null : (Integer)this.mapModelIndexes.get(valueKey);
   }

   public void putModelIndex(C_1391_ stack, C_3899_ world, C_524_ entity, Integer index) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      if (valueKey != null) {
         this.mapModelIndexes.put(valueKey, index);
      }
   }

   private CompoundKey getValueKey(C_1391_ stack, C_3899_ world, C_524_ entity) {
      Integer[] indexes = new Integer[this.itemOverrideProperties.length];

      for (int i = 0; i < indexes.length; i++) {
         Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
         if (index == null) {
            return null;
         }

         indexes[i] = index;
      }

      return new CompoundKey(indexes);
   }

   public static ItemOverrideCache make(List<C_4217_> overrides) {
      if (overrides.isEmpty()) {
         return null;
      } else if (!Reflector.ItemOverride_listResourceValues.exists()) {
         return null;
      } else {
         Map<C_5265_, Set<Float>> propertyValues = new LinkedHashMap();

         for (C_4217_ itemOverride : overrides) {
            for (C_141725_ resourceValue : (List)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_listResourceValues)) {
               C_5265_ loc = resourceValue.m_173459_();
               float val = resourceValue.m_173460_();
               Set<Float> setValues = (Set<Float>)propertyValues.get(loc);
               if (setValues == null) {
                  setValues = new HashSet();
                  propertyValues.put(loc, setValues);
               }

               setValues.add(val);
            }
         }

         List<ItemOverrideProperty> listProps = new ArrayList();

         for (C_5265_ loc : propertyValues.keySet()) {
            Set<Float> setValues = (Set<Float>)propertyValues.get(loc);
            float[] values = Floats.toArray(setValues);
            ItemOverrideProperty prop = new ItemOverrideProperty(loc, values);
            listProps.add(prop);
         }

         ItemOverrideProperty[] props = (ItemOverrideProperty[])listProps.toArray(new ItemOverrideProperty[listProps.size()]);
         ItemOverrideCache cache = new ItemOverrideCache(props);
         logCache(props, overrides);
         return cache;
      }
   }

   private static void logCache(ItemOverrideProperty[] props, List<C_4217_> overrides) {
      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < props.length; i++) {
         ItemOverrideProperty prop = props[i];
         if (sb.length() > 0) {
            sb.append(", ");
         }

         sb.append(prop.getLocation() + "=" + prop.getValues().length);
      }

      if (overrides.size() > 0) {
         sb.append(" -> " + ((C_4217_)overrides.get(0)).m_111718_() + " ...");
      }

      Config.dbg("ItemOverrideCache: " + sb.toString());
   }

   public String toString() {
      return "properties: " + this.itemOverrideProperties.length + ", modelIndexes: " + this.mapModelIndexes.size();
   }
}
