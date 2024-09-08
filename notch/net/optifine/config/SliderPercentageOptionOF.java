package net.optifine.config;

import com.mojang.serialization.Codec;
import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3401_;
import net.minecraft.src.C_4996_;

public class SliderPercentageOptionOF extends C_213334_ {
   public SliderPercentageOptionOF(String name, double defVal) {
      super(
         name,
         C_213334_.m_231498_(),
         (labelIn, valuein) -> C_3401_.m_231921_(labelIn, C_4996_.m_237110_(name, new Object[]{valuein})),
         C_213334_.C_213350_.INSTANCE,
         defVal,
         val -> {
         }
      );
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int step, int valueDef) {
      super(
         name,
         C_213334_.m_231498_(),
         (labelIn, valuein) -> C_3401_.m_231921_(labelIn, C_4996_.m_237110_(name, new Object[]{valuein})),
         new C_213334_.C_213341_(valueMin / step, valueMax / step).m_231657_(val -> val * step, val -> val / step),
         Codec.intRange(valueMin, valueMax),
         valueDef,
         val -> {
         }
      );
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int[] stepValues, int valueDef) {
      super(
         name,
         C_213334_.m_231498_(),
         (labelIn, valuein) -> C_3401_.m_231921_(labelIn, C_4996_.m_237110_(name, new Object[]{valuein})),
         new C_213334_.C_213341_(0, stepValues.length - 1).m_231657_(val -> stepValues[val], val -> C_3401_.indexOf(val, stepValues)),
         Codec.intRange(valueMin, valueMax),
         valueDef,
         val -> {
         }
      );
   }

   public double getOptionValue() {
      C_3401_ gameSettings = C_3391_.m_91087_().f_91066_;
      return gameSettings.getOptionFloatValueOF(this);
   }

   public void setOptionValue(double value) {
      C_3401_ gameSettings = C_3391_.m_91087_().f_91066_;
      gameSettings.setOptionFloatValueOF(this, value);
   }

   public C_4996_ getOptionText() {
      C_3401_ gameSetings = C_3391_.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }
}
