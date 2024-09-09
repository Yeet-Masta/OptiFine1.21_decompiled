package net.minecraft.src;

import javax.annotation.Nullable;

public record C_3385_(int f_90786_, C_4996_ f_240363_, @Nullable C_213508_ f_240905_, @Nullable C_240334_ f_240352_) {
   public C_3385_(int updateCounterCreated, C_4996_ content, @Nullable C_213508_ headerSignature, @Nullable C_240334_ tag) {
      this.f_90786_ = updateCounterCreated;
      this.f_240363_ = content;
      this.f_240905_ = headerSignature;
      this.f_240352_ = tag;
   }

   @Nullable
   public C_240334_.C_240333_ m_324870_() {
      return this.f_240352_ != null ? this.f_240352_.f_240355_() : null;
   }

   public int f_90786_() {
      return this.f_90786_;
   }

   public C_4996_ f_240363_() {
      return this.f_240363_;
   }

   @Nullable
   public C_213508_ f_240905_() {
      return this.f_240905_;
   }

   @Nullable
   public C_240334_ f_240352_() {
      return this.f_240352_;
   }

   public static record C_240330_(int f_240350_, C_178_ f_240339_, @Nullable C_240334_ f_240351_, boolean f_240367_) {
      public C_240330_(int addedTime, C_178_ content, @Nullable C_240334_ tag, boolean endOfEntry) {
         this.f_240350_ = addedTime;
         this.f_240339_ = content;
         this.f_240351_ = tag;
         this.f_240367_ = endOfEntry;
      }

      public int f_240350_() {
         return this.f_240350_;
      }

      public C_178_ f_240339_() {
         return this.f_240339_;
      }

      @Nullable
      public C_240334_ f_240351_() {
         return this.f_240351_;
      }

      public boolean f_240367_() {
         return this.f_240367_;
      }
   }
}
