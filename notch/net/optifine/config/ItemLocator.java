package net.optifine.config;

import net.minecraft.src.C_1381_;
import net.minecraft.src.C_5265_;
import net.optifine.util.ItemUtils;

public class ItemLocator implements IObjectLocator<C_1381_> {
   public C_1381_ getObject(C_5265_ loc) {
      return ItemUtils.getItem(loc);
   }
}
