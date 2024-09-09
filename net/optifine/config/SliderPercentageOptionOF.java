package net.optifine.config;

import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;

public class SliderPercentageOptionOF extends OptionInstance {
   public SliderPercentageOptionOF(String name, double defVal) {
      super(name, OptionInstance.m_231498_(), (labelIn, valuein) -> {
         return Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein}));
      }, OptionInstance.UnitDouble.INSTANCE, defVal, (val) -> {
      });
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int step, int valueDef) {
      super(name, OptionInstance.m_231498_(), (labelIn, valuein) -> {
         return Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein}));
      }, (new OptionInstance.IntRange(valueMin / step, valueMax / step)).m_231657_((val) -> {
         return val * step;
      }, (val) -> {
         return val / step;
      }), Codec.intRange(valueMin, valueMax), valueDef, (val) -> {
      });
   }

   public SliderPercentageOptionOF(String name, int valueMin, int valueMax, int[] stepValues, int valueDef) {
      super(name, OptionInstance.m_231498_(), (labelIn, valuein) -> {
         return Options.m_231921_(labelIn, Component.m_237110_(name, new Object[]{valuein}));
      }, (new OptionInstance.IntRange(0, stepValues.length - 1)).m_231657_((val) -> {
         return stepValues[val];
      }, (val) -> {
         return Options.indexOf(val, stepValues);
      }), Codec.intRange(valueMin, valueMax), valueDef, (val) -> {
      });
   }

   public double getOptionValue() {
      Options gameSettings = Minecraft.m_91087_().f_91066_;
      return gameSettings.getOptionFloatValueOF(this);
   }

   public void setOptionValue(double value) {
      Options gameSettings = Minecraft.m_91087_().f_91066_;
      gameSettings.setOptionFloatValueOF(this, value);
   }

   public Component getOptionText() {
      Options gameSetings = Minecraft.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }
}
