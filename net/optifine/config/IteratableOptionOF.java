package net.optifine.config;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class IteratableOptionOF extends net.minecraft.client.OptionInstance {
   public IteratableOptionOF(String nameIn) {
      super(
         nameIn,
         net.minecraft.client.OptionInstance.m_231498_(),
         (labelIn, valueIn) -> (Boolean)valueIn ? CommonComponents.f_130653_ : CommonComponents.f_130654_,
         net.minecraft.client.OptionInstance.f_231471_,
         false,
         valueIn -> {
         }
      );
   }

   public void nextOptionValue(int dirIn) {
      net.minecraft.client.Options gameSetings = Minecraft.m_91087_().f_91066_;
      gameSetings.setOptionValueOF(this, dirIn);
   }

   public Component getOptionText() {
      net.minecraft.client.Options gameSetings = Minecraft.m_91087_().f_91066_;
      return gameSetings.getKeyComponentOF(this);
   }

   protected net.minecraft.client.Options getOptions() {
      return Minecraft.m_91087_().f_91066_;
   }
}
