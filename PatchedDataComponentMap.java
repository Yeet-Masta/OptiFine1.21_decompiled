import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_313445_;
import net.minecraft.src.C_313461_;
import net.minecraft.src.C_313470_;
import net.minecraft.src.C_313543_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4917_;
import net.minecraft.src.C_4940_;

public final class PatchedDataComponentMap implements C_313470_ {
   private final C_313470_ c;
   private Reference2ObjectMap<C_313543_<?>, Optional<?>> d;
   private boolean e;
   private C_4917_ tag;

   public PatchedDataComponentMap(C_313470_ p_i323282_1_) {
      this(p_i323282_1_, Reference2ObjectMaps.emptyMap(), true);
   }

   private PatchedDataComponentMap(C_313470_ p_i318911_1_, Reference2ObjectMap<C_313543_<?>, Optional<?>> p_i318911_2_, boolean p_i318911_3_) {
      this.c = p_i318911_1_;
      this.d = p_i318911_2_;
      this.e = p_i318911_3_;
   }

   public static PatchedDataComponentMap a(C_313470_ p_322493_0_, C_313461_ p_322493_1_) {
      if (a(p_322493_0_, p_322493_1_.f_314958_)) {
         return new PatchedDataComponentMap(p_322493_0_, p_322493_1_.f_314958_, true);
      } else {
         PatchedDataComponentMap patcheddatacomponentmap = new PatchedDataComponentMap(p_322493_0_);
         patcheddatacomponentmap.a(p_322493_1_);
         return patcheddatacomponentmap;
      }
   }

   private static boolean a(C_313470_ p_323581_0_, Reference2ObjectMap<C_313543_<?>, Optional<?>> p_323581_1_) {
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_323581_1_).iterator();

      while (var2.hasNext()) {
         Entry<C_313543_<?>, Optional<?>> entry = (Entry<C_313543_<?>, Optional<?>>)var2.next();
         Object object = p_323581_0_.m_318834_((C_313543_)entry.getKey());
         Optional<?> optional = (Optional<?>)entry.getValue();
         if (optional.isPresent() && optional.get().equals(object)) {
            return false;
         }

         if (optional.isEmpty() && object == null) {
            return false;
         }
      }

      return true;
   }

   @Nullable
   public <T> T m_318834_(C_313543_<? extends T> p_318834_1_) {
      Optional<? extends T> optional = (Optional<? extends T>)this.d.get(p_318834_1_);
      return (T)(optional != null ? optional.orElse(null) : this.c.m_318834_(p_318834_1_));
   }

   @Nullable
   public <T> T b(C_313543_<? super T> p_322371_1_, @Nullable T p_322371_2_) {
      this.h();
      T t = (T)this.c.m_318834_(p_322371_1_);
      Optional<T> optional;
      if (Objects.equals(p_322371_2_, t)) {
         optional = (Optional<T>)this.d.remove(p_322371_1_);
      } else {
         optional = (Optional<T>)this.d.put(p_322371_1_, Optional.ofNullable(p_322371_2_));
      }

      this.markDirty();
      return (T)(optional != null ? optional.orElse(t) : t);
   }

   @Nullable
   public <T> T d(C_313543_<? extends T> p_321460_1_) {
      this.h();
      T t = (T)this.c.m_318834_(p_321460_1_);
      Optional<? extends T> optional;
      if (t != null) {
         optional = (Optional<? extends T>)this.d.put(p_321460_1_, Optional.empty());
      } else {
         optional = (Optional<? extends T>)this.d.remove(p_321460_1_);
      }

      return (T)(optional != null ? optional.orElse(null) : t);
   }

   public void a(C_313461_ p_320975_1_) {
      this.h();
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_320975_1_.f_314958_).iterator();

      while (var2.hasNext()) {
         Entry<C_313543_<?>, Optional<?>> entry = (Entry<C_313543_<?>, Optional<?>>)var2.next();
         this.a((C_313543_<?>)entry.getKey(), (Optional<?>)entry.getValue());
      }
   }

   private void a(C_313543_<?> p_318645_1_, Optional<?> p_318645_2_) {
      Object object = this.c.m_318834_(p_318645_1_);
      if (p_318645_2_.isPresent()) {
         if (p_318645_2_.get().equals(object)) {
            this.d.remove(p_318645_1_);
         } else {
            this.d.put(p_318645_1_, p_318645_2_);
         }
      } else if (object != null) {
         this.d.put(p_318645_1_, Optional.empty());
      } else {
         this.d.remove(p_318645_1_);
      }
   }

   public void b(C_313461_ p_324830_1_) {
      this.h();
      this.d.clear();
      this.d.putAll(p_324830_1_.f_314958_);
   }

   public void a(C_313470_ p_324935_1_) {
      for (C_313445_<?> typeddatacomponent : p_324935_1_) {
         typeddatacomponent.a(this);
      }
   }

   private void h() {
      if (this.e) {
         this.d = new Reference2ObjectArrayMap(this.d);
         this.e = false;
      }
   }

   public Set<C_313543_<?>> m_319675_() {
      if (this.d.isEmpty()) {
         return this.c.m_319675_();
      } else {
         Set<C_313543_<?>> set = new ReferenceArraySet(this.c.m_319675_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.d).iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>>)var2.next();
            Optional<?> optional = (Optional<?>)entry.getValue();
            if (optional.isPresent()) {
               set.add((C_313543_)entry.getKey());
            } else {
               set.remove(entry.getKey());
            }
         }

         return set;
      }
   }

   public Iterator<C_313445_<?>> iterator() {
      if (this.d.isEmpty()) {
         return this.c.iterator();
      } else {
         List<C_313445_<?>> list = new ArrayList(this.d.size() + this.c.m_319491_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.d).iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>>)var2.next();
            if (((Optional)entry.getValue()).isPresent()) {
               list.add(C_313445_.m_321971_((C_313543_)entry.getKey(), ((Optional)entry.getValue()).get()));
            }
         }

         for (C_313445_<?> typeddatacomponent : this.c) {
            if (!this.d.containsKey(typeddatacomponent.f_316611_())) {
               list.add(typeddatacomponent);
            }
         }

         return list.iterator();
      }
   }

   public int m_319491_() {
      int i = this.c.m_319491_();
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.d).iterator();

      while (var2.hasNext()) {
         it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<C_313543_<?>, Optional<?>>)var2.next();
         boolean flag = ((Optional)entry.getValue()).isPresent();
         boolean flag1 = this.c.m_321946_((C_313543_)entry.getKey());
         if (flag != flag1) {
            i += flag ? 1 : -1;
         }
      }

      return i;
   }

   public C_313461_ f() {
      if (this.d.isEmpty()) {
         return C_313461_.f_315512_;
      } else {
         this.e = true;
         return new C_313461_(this.d);
      }
   }

   public PatchedDataComponentMap g() {
      this.e = true;
      return new PatchedDataComponentMap(this.c, this.d, true);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof PatchedDataComponentMap patcheddatacomponentmap
            && this.c.equals(patcheddatacomponentmap.c)
            && this.d.equals(patcheddatacomponentmap.d)) {
            return true;
         }

         return false;
      }
   }

   public int hashCode() {
      return this.c.hashCode() + this.d.hashCode() * 31;
   }

   public String toString() {
      return "{" + (String)this.m_322172_().map(C_313445_::toString).collect(Collectors.joining(", ")) + "}";
   }

   public C_4917_ getTag() {
      if (this.tag == null) {
         C_1596_ world = C_3391_.m_91087_().r;
         if (world != null) {
            C_313461_ patch = this.f();
            this.tag = (C_4917_)C_313461_.f_315187_.encodeStart(world.m_9598_().m_318927_(C_4940_.f_128958_), patch).getOrThrow();
         }
      }

      return this.tag;
   }

   private void markDirty() {
      this.tag = null;
   }
}
