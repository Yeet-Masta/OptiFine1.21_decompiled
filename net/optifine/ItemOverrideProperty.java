package net.optifine;

import java.util.Arrays;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ItemOverrideProperty {
   private net.minecraft.resources.ResourceLocation location;
   private float[] values;

   public ItemOverrideProperty(net.minecraft.resources.ResourceLocation location, float[] values) {
      this.location = location;
      this.values = (float[])values.clone();
      Arrays.sort(this.values);
   }

   public Integer getValueIndex(ItemStack stack, net.minecraft.client.multiplayer.ClientLevel world, LivingEntity entity) {
      ItemPropertyFunction itemPropertyGetter = ItemProperties.m_117829_(stack, this.location);
      if (itemPropertyGetter == null) {
         return null;
      } else {
         float val = itemPropertyGetter.m_141951_(stack, world, entity, 0);
         int index = Arrays.binarySearch(this.values, val);
         return index;
      }
   }

   public net.minecraft.resources.ResourceLocation getLocation() {
      return this.location;
   }

   public float[] getValues() {
      return this.values;
   }

   public String toString() {
      return "location: " + this.location + ", values: [" + Config.arrayToString(this.values) + "]";
   }
}
