package net.optifine.config;

import net.minecraft.src.C_1505_;
import net.optifine.util.EnchantmentUtils;

public class ParserEnchantmentId implements IParserInt {
   @Override
   public int parse(String str, int defVal) {
      C_1505_ en = EnchantmentUtils.getEnchantment(str);
      return en == null ? defVal : EnchantmentUtils.getId(en);
   }
}
