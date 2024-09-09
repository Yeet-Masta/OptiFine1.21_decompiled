import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.src.C_2068_;

public abstract class Property<T extends Comparable<T>> {
   private final Class<T> a;
   private final String b;
   @Nullable
   private Integer c;
   private final Codec<T> d = Codec.STRING
      .comapFlatMap(
         p_61697_1_ -> (DataResult)this.b(p_61697_1_)
               .map(DataResult::success)
               .orElseGet(() -> DataResult.error(() -> "Unable to read property: " + this + " with value: " + p_61697_1_)),
         this::a
      );
   private final Codec<Property.a<T>> e = this.d.xmap(this::b, Property.a::b);

   protected Property(String name, Class<T> valueClass) {
      this.a = valueClass;
      this.b = name;
   }

   public Property.a<T> b(T p_61699_1_) {
      return new Property.a<>(this, p_61699_1_);
   }

   public Property.a<T> a(C_2068_<?, ?> p_61694_1_) {
      return new Property.a<>(this, (T)p_61694_1_.c(this));
   }

   public Stream<Property.a<T>> c() {
      return this.a().stream().map(this::b);
   }

   public Codec<T> d() {
      return this.d;
   }

   public Codec<Property.a<T>> e() {
      return this.e;
   }

   public String f() {
      return this.b;
   }

   public Class<T> g() {
      return this.a;
   }

   public abstract Collection<T> a();

   public abstract String a(T var1);

   public abstract Optional<T> b(String var1);

   public String toString() {
      return MoreObjects.toStringHelper(this).add("name", this.b).add("clazz", this.a).add("values", this.a()).toString();
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         return p_equals_1_ instanceof Property<?> property ? this.a.equals(property.a) && this.b.equals(property.b) : false;
      }
   }

   public final int hashCode() {
      if (this.c == null) {
         this.c = this.b();
      }

      return this.c;
   }

   public int b() {
      return 31 * this.a.hashCode() + this.b.hashCode();
   }

   public <U, S extends C_2068_<?, S>> DataResult<S> a(DynamicOps<U> p_156031_1_, S p_156031_2_, U p_156031_3_) {
      DataResult<T> dataresult = this.d.parse(p_156031_1_, p_156031_3_);
      return dataresult.map(p_156028_2_ -> (C_2068_)p_156031_2_.a(this, p_156028_2_)).setPartial(p_156031_2_);
   }

   public static record a<T extends Comparable<T>>(Property<T> a, T b) {
      public a(Property<T> a, T b) {
         if (!a.a().contains(b)) {
            throw new IllegalArgumentException("Value " + b + " does not belong to property " + a);
         } else {
            this.a = a;
            this.b = b;
         }
      }

      public String toString() {
         return this.a.f() + "=" + this.a.a(this.b);
      }
   }
}
