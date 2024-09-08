package net.minecraft.src;

import javax.annotation.Nullable;
import net.minecraft.src.C_240334_.C_240333_;

public record C_3385_(int f_90786_, C_4996_ f_240363_, @Nullable C_213508_ f_240905_, @Nullable C_240334_ f_240352_) {
   @Nullable
   public C_240333_ m_324870_() {
      return this.f_240352_ != null ? this.f_240352_.f_240355_() : null;
   }

   public static record C_240330_(int f_240350_, C_178_ f_240339_, @Nullable C_240334_ f_240351_, boolean f_240367_) {
   }
}
