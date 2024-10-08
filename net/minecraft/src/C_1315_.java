package net.minecraft.src;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public record C_1315_(Map f_316203_, int f_313926_, C_203228_ f_313996_, Supplier f_315867_, List f_315892_, float f_316002_, float f_317001_) {
   public static final Codec f_314133_;

   public C_1315_(Map defense, int enchantmentValue, C_203228_ equipSound, Supplier repairIngredient, List layers, float toughness, float knockbackResistance) {
      this.f_316203_ = defense;
      this.f_313926_ = enchantmentValue;
      this.f_313996_ = equipSound;
      this.f_315867_ = repairIngredient;
      this.f_315892_ = layers;
      this.f_316002_ = toughness;
      this.f_317001_ = knockbackResistance;
   }

   public int m_323068_(C_1313_.C_265803_ p_323068_1_) {
      return (Integer)this.f_316203_.getOrDefault(p_323068_1_, 0);
   }

   public Map f_316203_() {
      return this.f_316203_;
   }

   public int f_313926_() {
      return this.f_313926_;
   }

   public C_203228_ f_313996_() {
      return this.f_313996_;
   }

   public Supplier f_315867_() {
      return this.f_315867_;
   }

   public List f_315892_() {
      return this.f_315892_;
   }

   public float f_316002_() {
      return this.f_316002_;
   }

   public float f_317001_() {
      return this.f_317001_;
   }

   static {
      f_314133_ = C_256712_.f_315942_.m_206110_();
   }

   public static final class C_313715_ {
      private final C_5265_ f_317138_;
      private final String f_315638_;
      private final boolean f_315668_;
      private final C_5265_ f_314936_;
      private final C_5265_ f_315722_;

      public C_313715_(C_5265_ p_i322033_1_, String p_i322033_2_, boolean p_i322033_3_) {
         this.f_317138_ = p_i322033_1_;
         this.f_315638_ = p_i322033_2_;
         this.f_315668_ = p_i322033_3_;
         this.f_314936_ = this.m_320920_(true);
         this.f_315722_ = this.m_320920_(false);
      }

      public C_313715_(C_5265_ p_i320417_1_) {
         this(p_i320417_1_, "", false);
      }

      private C_5265_ m_320920_(boolean p_320920_1_) {
         return this.f_317138_.m_247266_((p_319090_2_) -> {
            String var10000 = this.f_317138_.m_135815_();
            return "textures/models/armor/" + var10000 + "_layer_" + (p_320920_1_ ? 2 : 1) + this.f_315638_ + ".png";
         });
      }

      public C_5265_ m_318738_(boolean p_318738_1_) {
         return p_318738_1_ ? this.f_314936_ : this.f_315722_;
      }

      public boolean m_324910_() {
         return this.f_315668_;
      }

      public C_5265_ getAssetName() {
         return this.f_317138_;
      }

      public String getSuffix() {
         return this.f_315638_;
      }
   }
}
