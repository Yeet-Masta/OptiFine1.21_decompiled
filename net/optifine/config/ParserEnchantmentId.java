package net.optifine.config;

import net.minecraft.world.item.enchantment.Enchantment;
import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId implements IParserInt {
   @Override
   public int m_82160_(String str, int defVal) {
      Enchantment en = EnchantmentUtils.getEnchantment(str);
      return en == null ? defVal : EnchantmentUtils.getId(en);
   }
}
