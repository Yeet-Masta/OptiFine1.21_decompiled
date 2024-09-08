package net.minecraft.src;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class C_2097_<T extends Comparable<T>> {
   private final Class<T> f_61686_;
   private final String f_61687_;
   @Nullable
   private Integer f_61688_;
   private final Codec<T> f_61689_ = Codec.STRING
      .comapFlatMap(
         p_61697_1_ -> (DataResult)this.m_6215_(p_61697_1_)
               .map(DataResult::success)
               .orElseGet(() -> DataResult.error(() -> "Unable to read property: " + this + " with value: " + p_61697_1_)),
         this::m_6940_
      );
   private final Codec<C_2097_.C_2099_<T>> f_61690_ = this.f_61689_.xmap(this::m_61699_, C_2097_.C_2099_::f_61713_);

   protected C_2097_(String name, Class<T> valueClass) {
      this.f_61686_ = valueClass;
      this.f_61687_ = name;
   }

   public C_2097_.C_2099_<T> m_61699_(T p_61699_1_) {
      return new C_2097_.C_2099_<>(this, p_61699_1_);
   }

   public C_2097_.C_2099_<T> m_61694_(C_2068_<?, ?> p_61694_1_) {
      return new C_2097_.C_2099_<>(this, (T)p_61694_1_.m_61143_(this));
   }

   public Stream<C_2097_.C_2099_<T>> m_61702_() {
      return this.m_6908_().stream().map(this::m_61699_);
   }

   public Codec<T> m_156037_() {
      return this.f_61689_;
   }

   public Codec<C_2097_.C_2099_<T>> m_61705_() {
      return this.f_61690_;
   }

   public String m_61708_() {
      return this.f_61687_;
   }

   public Class<T> m_61709_() {
      return this.f_61686_;
   }

   public abstract Collection<T> m_6908_();

   public abstract String m_6940_(T var1);

   public abstract Optional<T> m_6215_(String var1);

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.f_61687_).add("clazz", this.f_61686_).add("values", this.m_6908_()).toString();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof C_2097_<?> property ? this.f_61686_.equals(property.f_61686_) && this.f_61687_.equals(property.f_61687_) : false;
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

   public <U, S extends C_2068_<?, S>> DataResult<S> m_156031_(DynamicOps<U> p_156031_1_, S p_156031_2_, U p_156031_3_) {
      DataResult<T> dataresult = this.f_61689_.parse(p_156031_1_, p_156031_3_);
      return dataresult.map(p_156028_2_ -> (C_2068_)p_156031_2_.m_61124_(this, p_156028_2_)).setPartial(p_156031_2_);
   }

   public static record C_2099_<T extends Comparable<T>>(C_2097_<T> f_61712_, T f_61713_) {
      public C_2099_(C_2097_<T> f_61712_, T f_61713_) {
         if (!f_61712_.m_6908_().contains(f_61713_)) {
            throw new IllegalArgumentException("Value " + f_61713_ + " does not belong to property " + f_61712_);
         } else {
            this.f_61712_ = f_61712_;
            this.f_61713_ = f_61713_;
         }
      }

      public String toString() {
         return this.f_61712_.m_61708_() + "=" + this.f_61712_.m_6940_(this.f_61713_);
      }
   }
}
