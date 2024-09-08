package net.optifine.gui;

import net.minecraft.src.C_213334_;
import net.minecraft.src.C_3451_;
import net.minecraft.src.C_4996_;
import net.minecraft.src.C_3451_.C_252357_;
import net.minecraft.src.C_3451_.C_3452_;

public class GuiOptionButtonOF extends C_3451_ implements IOptionControl {
   private final C_213334_ option;

   public GuiOptionButtonOF(int xIn, int yIn, int widthIn, int heightIn, C_213334_ optionIn, C_4996_ textIn, C_3452_ pressableIn, C_252357_ narrationIn) {
      super(xIn, yIn, widthIn, heightIn, textIn, pressableIn, narrationIn);
      this.option = optionIn;
   }

   public C_213334_ getOption() {
      return this.option;
   }

   public C_213334_ getControlOption() {
      return this.option;
   }
}
