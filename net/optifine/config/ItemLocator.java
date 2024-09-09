package net.optifine.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.optifine.util.ItemUtils;

public class ItemLocator implements IObjectLocator {
   public Item getObject(ResourceLocation loc) {
      Item item = ItemUtils.getItem(loc);
      return item;
   }
}
