package net.optifine;

import java.util.Arrays;
import net.minecraft.src.C_1391_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4463_;
import net.minecraft.src.C_4467_;
import net.minecraft.src.C_524_;
import net.minecraft.src.C_5265_;

public class ItemOverrideProperty {
   private C_5265_ location;
   private float[] values;

   public ItemOverrideProperty(C_5265_ location, float[] values) {
      this.location = location;
      this.values = (float[])values.clone();
      Arrays.sort(this.values);
   }

   public Integer getValueIndex(C_1391_ stack, C_3899_ world, C_524_ entity) {
      C_4467_ itemPropertyGetter = C_4463_.m_117829_(stack, this.location);
      if (itemPropertyGetter == null) {
         return null;
      } else {
         float val = itemPropertyGetter.m_141951_(stack, world, entity, 0);
         int index = Arrays.binarySearch(this.values, val);
         return index;
      }
   }

   public C_5265_ getLocation() {
      return this.location;
   }

   public float[] getValues() {
      return this.values;
   }

   public String toString() {
      return "location: " + this.location + ", values: [" + Config.arrayToString(this.values) + "]";
   }
}
