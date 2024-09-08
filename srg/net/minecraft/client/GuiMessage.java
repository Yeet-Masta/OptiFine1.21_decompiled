package net.minecraft.client;

import javax.annotation.Nullable;
import net.minecraft.client.GuiMessageTag.Icon;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.FormattedCharSequence;

public record GuiMessage(int f_90786_, Component f_240363_, @Nullable MessageSignature f_240905_, @Nullable GuiMessageTag f_240352_) {
   @Nullable
   public Icon m_324870_() {
      return this.f_240352_ != null ? this.f_240352_.f_240355_() : null;
   }

   public static record Line(int f_240350_, FormattedCharSequence f_240339_, @Nullable GuiMessageTag f_240351_, boolean f_240367_) {
   }
}
