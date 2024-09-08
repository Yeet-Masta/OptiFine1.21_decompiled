package net.optifine.config;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4995_;
import net.minecraft.src.C_4996_;

public class IteratableOptionOF extends C_213334_ {
   public IteratableOptionOF(String nameIn) {
      super(
         nameIn,
         C_213334_.m_231498_(),
         (labelIn, valueIn) -> (Boolean)valueIn ? C_4995_.f_130653_ : C_4995_.f_130654_,
         C_213334_.f_231471_,
         false,
         valueIn -> {
         }
      );
   }

   public void nextOptionValue(int dirIn) {
      C_3401_ gameSetings = C_3391_.m_91087_().f_91066_;
      gameSetings.setOptionValueOF(this, dirIn);
   }

   public C_4996_ getOptionText() {
      C_3401_ gameSetings = C_3391_.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }

   protected C_3401_ getOptions() {
      return C_3391_.m_91087_().f_91066_;
   }
}
