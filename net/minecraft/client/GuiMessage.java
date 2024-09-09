package net.minecraft.client;

import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.util.FormattedCharSequence;

public record GuiMessage(int f_90786_, Component f_240363_, @Nullable MessageSignature f_240905_, @Nullable GuiMessageTag f_240352_) {
   public GuiMessage(int updateCounterCreated, Component content, @Nullable MessageSignature headerSignature, @Nullable GuiMessageTag tag) {
      this.f_90786_ = updateCounterCreated;
      this.f_240363_ = content;
      this.f_240905_ = headerSignature;
      this.f_240352_ = tag;
   }

   @Nullable
   public GuiMessageTag.Icon m_324870_() {
      return this.f_240352_ != null ? this.f_240352_.f_240355_() : null;
   }

   public int f_90786_() {
      return this.f_90786_;
   }

   public Component f_240363_() {
      return this.f_240363_;
   }

   @Nullable
   public MessageSignature f_240905_() {
      return this.f_240905_;
   }

   @Nullable
   public GuiMessageTag f_240352_() {
      return this.f_240352_;
   }

   public static record Line(int f_240350_, FormattedCharSequence f_240339_, @Nullable GuiMessageTag f_240351_, boolean f_240367_) {
      public Line(int addedTime, FormattedCharSequence content, @Nullable GuiMessageTag tag, boolean endOfEntry) {
         this.f_240350_ = addedTime;
         this.f_240339_ = content;
         this.f_240351_ = tag;
         this.f_240367_ = endOfEntry;
      }

      public int f_240350_() {
         return this.f_240350_;
      }

      public FormattedCharSequence f_240339_() {
         return this.f_240339_;
      }

      @Nullable
      public GuiMessageTag f_240351_() {
         return this.f_240351_;
      }

      public boolean f_240367_() {
         return this.f_240367_;
      }
   }
}
