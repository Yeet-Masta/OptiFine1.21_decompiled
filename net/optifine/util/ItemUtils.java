package net.optifine.util;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.optifine.reflect.Reflector;

public class ItemUtils {
   private static CompoundTag EMPTY_TAG = new CompoundTag();

   public static Item getItem(net.minecraft.resources.ResourceLocation loc) {
      return !BuiltInRegistries.f_257033_.m_7804_(loc) ? null : (Item)BuiltInRegistries.f_257033_.m_7745_(loc);
   }

   public static int getId(Item item) {
      return BuiltInRegistries.f_257033_.m_7447_(item);
   }

   public static CompoundTag getTag(ItemStack itemStack) {
      if (itemStack == null) {
         return EMPTY_TAG;
      } else {
         net.minecraft.core.component.PatchedDataComponentMap components = (net.minecraft.core.component.PatchedDataComponentMap)Reflector.ItemStack_components
            .getValue(itemStack);
         return components == null ? EMPTY_TAG : components.getTag();
      }
   }
}
