package net.optifine.config;

import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class IteratableOptionOF extends OptionInstance {
   public IteratableOptionOF(String nameIn) {
      super(
         nameIn,
         OptionInstance.m_231498_(),
         (labelIn, valueIn) -> (Boolean)valueIn ? CommonComponents.f_130653_ : CommonComponents.f_130654_,
         OptionInstance.f_231471_,
         false,
         valueIn -> {
         }
      );
   }

   public void nextOptionValue(int dirIn) {
      Options gameSetings = Minecraft.m_91087_().f_91066_;
      gameSetings.setOptionValueOF(this, dirIn);
   }

   public Component getOptionText() {
      Options gameSetings = Minecraft.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }

   protected Options getOptions() {
      return Minecraft.m_91087_().f_91066_;
   }
}
