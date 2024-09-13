package net.optifine.gui;

import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Button.CreateNarration;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.network.chat.Component;

public class GuiOptionButtonOF extends Button implements IOptionControl {
   private OptionInstance option;

   public GuiOptionButtonOF(
      int xIn, int yIn, int widthIn, int heightIn, OptionInstance optionIn, Component textIn, OnPress pressableIn, CreateNarration narrationIn
   ) {
      super(xIn, yIn, widthIn, heightIn, textIn, pressableIn, narrationIn);
      this.option = optionIn;
   }

   public OptionInstance getOption() {
      return this.option;
   }

   @Override
   public OptionInstance getControlOption() {
      return this.option;
   }
}
