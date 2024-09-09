package net.minecraft.src;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.optifine.EmissiveTextures;

public class C_4245_ implements C_1766_.C_1768_ {
   public Int2IntFunction m_6959_(C_1991_ blockEntity1, C_1991_ blockEntity2) {
      return (valIn) -> {
         if (EmissiveTextures.isRenderEmissive()) {
            return C_4138_.MAX_BRIGHTNESS;
         } else {
            int i = C_4134_.m_109541_(blockEntity1.m_58904_(), blockEntity1.m_58899_());
            int j = C_4134_.m_109541_(blockEntity2.m_58904_(), blockEntity2.m_58899_());
            int k = C_4138_.m_109883_(i);
            int l = C_4138_.m_109883_(j);
            int i1 = C_4138_.m_109894_(i);
            int j1 = C_4138_.m_109894_(j);
            return C_4138_.m_109885_(Math.max(k, l), Math.max(i1, j1));
         }
      };
   }

   public Int2IntFunction m_7693_(C_1991_ blockEntityIn) {
      return (valIn) -> {
         return valIn;
      };
   }

   public Int2IntFunction m_6502_() {
      return (valIn) -> {
         return valIn;
      };
   }
}
