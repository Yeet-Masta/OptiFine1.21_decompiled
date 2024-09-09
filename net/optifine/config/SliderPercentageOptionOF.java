package net.optifine.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class SliderPercentageOptionOF extends net.minecraft.client.OptionInstance {
   public SliderPercentageOptionOF(String name, double defVal) {
      super(
         name,
         net.minecraft.client.OptionInstance.m_231498_(),
         (labelIn, valuein) -> net.minecraft.client.Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein})),
         net.minecraft.client.OptionInstance.UnitDouble.INSTANCE,
         defVal,
         val -> {
         }
      );
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int step, int valueDef) {
      super(
         name,
         net.minecraft.client.OptionInstance.m_231498_(),
         (labelIn, valuein) -> net.minecraft.client.Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein})),
         new net.minecraft.client.OptionInstance.IntRange(valueMin / step, valueMax / step).m_231657_(val -> val * step, val -> val / step),
         Codec.intRange(valueMin, valueMax),
         valueDef,
         val -> {
         }
      );
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int[] stepValues, int valueDef) {
      super(
         name,
         net.minecraft.client.OptionInstance.m_231498_(),
         (labelIn, valuein) -> net.minecraft.client.Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein})),
         new net.minecraft.client.OptionInstance.IntRange(0, stepValues.length - 1)
            .m_231657_(val -> stepValues[val], val -> net.minecraft.client.Options.indexOf(val, stepValues)),
         Codec.intRange(valueMin, valueMax),
         valueDef,
         val -> {
         }
      );
   }

   public double getOptionValue() {
      net.minecraft.client.Options gameSettings = Minecraft.m_91087_().f_91066_;
      return gameSettings.getOptionFloatValueOF(this);
   }

   public void setOptionValue(double value) {
      net.minecraft.client.Options gameSettings = Minecraft.m_91087_().f_91066_;
      gameSettings.setOptionFloatValueOF(this, value);
   }

   public Component getOptionText() {
      net.minecraft.client.Options gameSetings = Minecraft.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }
}
