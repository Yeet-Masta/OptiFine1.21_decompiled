package net.optifine.config;

import net.minecraft.world.item.Item;

public class ItemLocator implements IObjectLocator<Item> {
   public Item getObject(net.minecraft.resources.ResourceLocation loc) {
      return net.optifine.util.ItemUtils.getItem(loc);
   }
}
