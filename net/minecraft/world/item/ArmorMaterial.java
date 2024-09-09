package net.minecraft.world.item;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.crafting.Ingredient;

public record ArmorMaterial(
   Map<Type, Integer> f_316203_,
   int f_313926_,
   Holder<SoundEvent> f_313996_,
   Supplier<Ingredient> f_315867_,
   List<net.minecraft.world.item.ArmorMaterial.Layer> f_315892_,
   float f_316002_,
   float f_317001_
) {
   public static final Codec<Holder<net.minecraft.world.item.ArmorMaterial>> f_314133_ = BuiltInRegistries.f_315942_.m_206110_();

   public int m_323068_(Type p_323068_1_) {
      return (Integer)this.f_316203_.getOrDefault(p_323068_1_, 0);
   }

   public static final class Layer {
      private final net.minecraft.resources.ResourceLocation f_317138_;
      private final String f_315638_;
      private final boolean f_315668_;
      private final net.minecraft.resources.ResourceLocation f_314936_;
      private final net.minecraft.resources.ResourceLocation f_315722_;

      public Layer(net.minecraft.resources.ResourceLocation p_i322033_1_, String p_i322033_2_, boolean p_i322033_3_) {
         this.f_317138_ = p_i322033_1_;
         this.f_315638_ = p_i322033_2_;
         this.f_315668_ = p_i322033_3_;
         this.f_314936_ = this.m_320920_(true);
         this.f_315722_ = this.m_320920_(false);
      }

      public Layer(net.minecraft.resources.ResourceLocation p_i320417_1_) {
         this(p_i320417_1_, "", false);
      }

      private net.minecraft.resources.ResourceLocation m_320920_(boolean p_320920_1_) {
         return this.f_317138_
            .m_247266_(p_319090_2_ -> "textures/models/armor/" + this.f_317138_.m_135815_() + "_layer_" + (p_320920_1_ ? 2 : 1) + this.f_315638_ + ".png");
      }

      public net.minecraft.resources.ResourceLocation m_318738_(boolean p_318738_1_) {
         return p_318738_1_ ? this.f_314936_ : this.f_315722_;
      }

      public boolean m_324910_() {
         return this.f_315668_;
      }

      public net.minecraft.resources.ResourceLocation getAssetName() {
         return this.f_317138_;
      }

      public String getSuffix() {
         return this.f_315638_;
      }
   }
}
