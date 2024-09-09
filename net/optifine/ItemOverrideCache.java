package net.optifine;

import com.google.common.primitives.Floats;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.optifine.reflect.Reflector;
import net.optifine.util.CompoundKey;

public class ItemOverrideCache {
   private ItemOverrideProperty[] itemOverrideProperties;
   private Map mapModelIndexes = new HashMap();
   public static final Integer INDEX_NONE = new Integer(-1);

   public ItemOverrideCache(ItemOverrideProperty[] itemOverrideProperties) {
      this.itemOverrideProperties = itemOverrideProperties;
   }

   public Integer getModelIndex(ItemStack stack, ClientLevel world, LivingEntity entity) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      return valueKey == null ? null : (Integer)this.mapModelIndexes.get(valueKey);
   }

   public void putModelIndex(ItemStack stack, ClientLevel world, LivingEntity entity, Integer index) {
      CompoundKey valueKey = this.getValueKey(stack, world, entity);
      if (valueKey != null) {
         this.mapModelIndexes.put(valueKey, index);
      }
   }

   private CompoundKey getValueKey(ItemStack stack, ClientLevel world, LivingEntity entity) {
      Integer[] indexes = new Integer[this.itemOverrideProperties.length];

      for(int i = 0; i < indexes.length; ++i) {
         Integer index = this.itemOverrideProperties[i].getValueIndex(stack, world, entity);
         if (index == null) {
            return null;
         }

         indexes[i] = index;
      }

      return new CompoundKey(indexes);
   }

   public static ItemOverrideCache make(List overrides) {
      if (overrides.isEmpty()) {
         return null;
      } else if (!Reflector.ItemOverride_listResourceValues.exists()) {
         return null;
      } else {
         Map propertyValues = new LinkedHashMap();
         Iterator it = overrides.iterator();

         while(it.hasNext()) {
            ItemOverride itemOverride = (ItemOverride)it.next();
            List resourceValues = (List)Reflector.getFieldValue(itemOverride, Reflector.ItemOverride_listResourceValues);

            float val;
            Object setValues;
            for(Iterator var5 = resourceValues.iterator(); var5.hasNext(); ((Set)setValues).add(val)) {
               ItemOverride.Predicate resourceValue = (ItemOverride.Predicate)var5.next();
               ResourceLocation loc = resourceValue.m_173459_();
               val = resourceValue.m_173460_();
               setValues = (Set)propertyValues.get(loc);
               if (setValues == null) {
                  setValues = new HashSet();
                  propertyValues.put(loc, setValues);
               }
            }
         }

         List listProps = new ArrayList();
         Set setPropertyLocations = propertyValues.keySet();
         Iterator it = setPropertyLocations.iterator();

         while(it.hasNext()) {
            ResourceLocation loc = (ResourceLocation)it.next();
            Set setValues = (Set)propertyValues.get(loc);
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

   private static void logCache(ItemOverrideProperty[] props, List overrides) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < props.length; ++i) {
         ItemOverrideProperty prop = props[i];
         if (sb.length() > 0) {
            sb.append(", ");
         }

         String var10001 = String.valueOf(prop.getLocation());
         sb.append(var10001 + "=" + prop.getValues().length);
      }

      if (overrides.size() > 0) {
         sb.append(" -> " + String.valueOf(((ItemOverride)overrides.get(0)).m_111718_()) + " ...");
      }

      Config.dbg("ItemOverrideCache: " + sb.toString());
   }

   public String toString() {
      int var10000 = this.itemOverrideProperties.length;
      return "properties: " + var10000 + ", modelIndexes: " + this.mapModelIndexes.size();
   }
}
