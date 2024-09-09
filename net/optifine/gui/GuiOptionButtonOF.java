package net.optifine.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.CreateNarration;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class GuiOptionButtonOF extends Button implements IOptionControl {
   private final net.minecraft.client.OptionInstance option;

   public GuiOptionButtonOF(
      int xIn,
      int yIn,
      int widthIn,
      int heightIn,
      net.minecraft.client.OptionInstance optionIn,
      Component textIn,
      OnPress pressableIn,
      CreateNarration narrationIn
   ) {
      super(xIn, yIn, widthIn, heightIn, textIn, pressableIn, narrationIn);
      this.option = optionIn;
   }

   public net.minecraft.client.OptionInstance getOption() {
      return this.option;
   }

   @Override
   public net.minecraft.client.OptionInstance getControlOption() {
      return this.option;
   }
}
