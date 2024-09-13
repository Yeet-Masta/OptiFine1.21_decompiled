package net.optifine;

import java.util.Arrays;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemOverrideProperty {
   private ResourceLocation location;
   private float[] values;

   public ItemOverrideProperty(ResourceLocation location, float[] values) {
      this.location = location;
      this.values = (float[])values.clone();
      Arrays.m_277065_(this.values);
   }

   public Integer getValueIndex(ItemStack stack, ClientLevel world, LivingEntity entity) {
      ItemPropertyFunction itemPropertyGetter = ItemProperties.m_117829_(stack, this.location);
      if (itemPropertyGetter == null) {
         return null;
      } else {
         float val = itemPropertyGetter.m_141951_(stack, world, entity, 0);
         int index = Arrays.binarySearch(this.values, val);
         return index;
      }
   }

   public ResourceLocation getLocation() {
      return this.location;
   }

   public float[] getValues() {
      return this.values;
   }

   public String toString() {
      return "location: " + this.location + ", values: [" + Config.arrayToString(this.values) + "]";
   }
}
