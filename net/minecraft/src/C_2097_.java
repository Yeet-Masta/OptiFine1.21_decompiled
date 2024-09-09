package net.minecraft.src;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class C_2097_ {
   private final Class f_61686_;
   private final String f_61687_;
   @Nullable
   private Integer f_61688_;
   private final Codec f_61689_;
   private final Codec f_61690_;

   protected C_2097_(String name, Class valueClass) {
      this.f_61689_ = Codec.STRING.comapFlatMap((p_61697_1_) -> {
         return (DataResult)this.m_6215_(p_61697_1_).map(DataResult::success).orElseGet(() -> {
            return DataResult.error(() -> {
               String var10000 = String.valueOf(this);
               return "Unable to read property: " + var10000 + " with value: " + p_61697_1_;
            });
         });
      }, this::m_6940_);
      this.f_61690_ = this.f_61689_.xmap(this::m_61699_, C_2099_::f_61713_);
      this.f_61686_ = valueClass;
      this.f_61687_ = name;
   }

   public C_2099_ m_61699_(Comparable p_61699_1_) {
      return new C_2099_(this, p_61699_1_);
   }

   public C_2099_ m_61694_(C_2068_ p_61694_1_) {
      return new C_2099_(this, p_61694_1_.m_61143_(this));
   }

   public Stream m_61702_() {
      return this.m_6908_().stream().map(this::m_61699_);
   }

   public Codec m_156037_() {
      return this.f_61689_;
   }

   public Codec m_61705_() {
      return this.f_61690_;
   }

   public String m_61708_() {
      return this.f_61687_;
   }

   public Class m_61709_() {
      return this.f_61686_;
   }

   public abstract Collection m_6908_();

   public abstract String m_6940_(Comparable var1);

   public abstract Optional m_6215_(String var1);

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.f_61687_).add("clazz", this.f_61686_).add("values", this.m_6908_()).toString();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         boolean var10000;
         if (p_equals_1_ instanceof C_2097_) {
            C_2097_ property = (C_2097_)p_equals_1_;
            var10000 = this.f_61686_.equals(property.f_61686_) && this.f_61687_.equals(property.f_61687_);
         } else {
            var10000 = false;
         }

         return var10000;
      }
   }

   public final int hashCode() {
      if (this.f_61688_ == null) {
         this.f_61688_ = this.m_6310_();
      }

      return this.f_61688_;
   }

   public int m_6310_() {
      return 31 * this.f_61686_.hashCode() + this.f_61687_.hashCode();
   }

   public DataResult m_156031_(DynamicOps p_156031_1_, C_2068_ p_156031_2_, Object p_156031_3_) {
      DataResult dataresult = this.f_61689_.parse(p_156031_1_, p_156031_3_);
      return dataresult.map((p_156028_2_) -> {
         return (C_2068_)p_156031_2_.m_61124_(this, p_156028_2_);
      }).setPartial(p_156031_2_);
   }

   public static record C_2099_(C_2097_ f_61712_, Comparable f_61713_) {
      public C_2099_(C_2097_ property, Comparable value) {
         if (!property.m_6908_().contains(value)) {
            String var10002 = String.valueOf(value);
            throw new IllegalArgumentException("Value " + var10002 + " does not belong to property " + String.valueOf(property));
         } else {
            this.f_61712_ = property;
            this.f_61713_ = value;
         }
      }

      public String toString() {
         String var10000 = this.f_61712_.m_61708_();
         return var10000 + "=" + this.f_61712_.m_6940_(this.f_61713_);
      }

      public C_2097_ f_61712_() {
         return this.f_61712_;
      }

      public Comparable f_61713_() {
         return this.f_61713_;
      }
   }
}
