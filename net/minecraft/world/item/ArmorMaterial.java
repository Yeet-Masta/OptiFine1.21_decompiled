package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public record ArmorMaterial(Map f_316203_, int f_313926_, Holder f_313996_, Supplier f_315867_, List f_315892_, float f_316002_, float f_317001_) {
   public static final Codec f_314133_;

   public ArmorMaterial(Map defense, int enchantmentValue, Holder equipSound, Supplier repairIngredient, List layers, float toughness, float knockbackResistance) {
      this.f_316203_ = defense;
      this.f_313926_ = enchantmentValue;
      this.f_313996_ = equipSound;
      this.f_315867_ = repairIngredient;
      this.f_315892_ = layers;
      this.f_316002_ = toughness;
      this.f_317001_ = knockbackResistance;
   }

   public int m_323068_(ArmorItem.Type p_323068_1_) {
      return (Integer)this.f_316203_.getOrDefault(p_323068_1_, 0);
   }

   public Map f_316203_() {
      return this.f_316203_;
   }

   public int f_313926_() {
      return this.f_313926_;
   }

   public Holder f_313996_() {
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
      f_314133_ = BuiltInRegistries.f_315942_.m_206110_();
   }

   public static final class Layer {
      private final ResourceLocation f_317138_;
      private final String f_315638_;
      private final boolean f_315668_;
      private final ResourceLocation f_314936_;
      private final ResourceLocation f_315722_;

      public Layer(ResourceLocation p_i322033_1_, String p_i322033_2_, boolean p_i322033_3_) {
         this.f_317138_ = p_i322033_1_;
         this.f_315638_ = p_i322033_2_;
         this.f_315668_ = p_i322033_3_;
         this.f_314936_ = this.m_320920_(true);
         this.f_315722_ = this.m_320920_(false);
      }

      public Layer(ResourceLocation p_i320417_1_) {
         this(p_i320417_1_, "", false);
      }

      private ResourceLocation m_320920_(boolean p_320920_1_) {
         return this.f_317138_.m_247266_((p_319090_2_) -> {
            String var10000 = this.f_317138_.m_135815_();
            return "textures/models/armor/" + var10000 + "_layer_" + (p_320920_1_ ? 2 : 1) + this.f_315638_ + ".png";
         });
      }

      public ResourceLocation m_318738_(boolean p_318738_1_) {
         return p_318738_1_ ? this.f_314936_ : this.f_315722_;
      }

      public boolean m_324910_() {
         return this.f_315668_;
      }

      public ResourceLocation getAssetName() {
         return this.f_317138_;
      }

      public String getSuffix() {
         return this.f_315638_;
      }
   }
}
