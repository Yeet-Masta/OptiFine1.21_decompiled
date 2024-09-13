package net.minecraft.core.component;

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
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.Level;

public class PatchedDataComponentMap implements DataComponentMap {
   private DataComponentMap f_316296_;
   private Reference2ObjectMap<DataComponentType<?>, Optional<?>> f_315990_;
   private boolean f_316660_;
   private CompoundTag tag;

   public PatchedDataComponentMap(DataComponentMap p_i323282_1_) {
      this(p_i323282_1_, Reference2ObjectMaps.emptyMap(), true);
   }

   private PatchedDataComponentMap(DataComponentMap p_i318911_1_, Reference2ObjectMap<DataComponentType<?>, Optional<?>> p_i318911_2_, boolean p_i318911_3_) {
      this.f_316296_ = p_i318911_1_;
      this.f_315990_ = p_i318911_2_;
      this.f_316660_ = p_i318911_3_;
   }

   public static PatchedDataComponentMap m_322493_(DataComponentMap p_322493_0_, DataComponentPatch p_322493_1_) {
      if (m_323581_(p_322493_0_, p_322493_1_.f_314958_)) {
         return new PatchedDataComponentMap(p_322493_0_, p_322493_1_.f_314958_, true);
      } else {
         PatchedDataComponentMap patcheddatacomponentmap = new PatchedDataComponentMap(p_322493_0_);
         patcheddatacomponentmap.m_320975_(p_322493_1_);
         return patcheddatacomponentmap;
      }
   }

   private static boolean m_323581_(DataComponentMap p_323581_0_, Reference2ObjectMap<DataComponentType<?>, Optional<?>> p_323581_1_) {
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_323581_1_).iterator();

      while (var2.hasNext()) {
         Entry<DataComponentType<?>, Optional<?>> entry = (Entry<DataComponentType<?>, Optional<?>>)var2.next();
         Object object = p_323581_0_.m_318834_((DataComponentType)entry.getKey());
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
   public <T> T m_318834_(DataComponentType<? extends T> p_318834_1_) {
      Optional<? extends T> optional = (Optional<? extends T>)this.f_315990_.get(p_318834_1_);
      return (T)(optional != null ? optional.orElse(null) : this.f_316296_.m_318834_(p_318834_1_));
   }

   @Nullable
   public <T> T m_322371_(DataComponentType<? super T> p_322371_1_, @Nullable T p_322371_2_) {
      this.m_322433_();
      T t = (T)this.f_316296_.m_318834_(p_322371_1_);
      Optional<T> optional;
      if (Objects.equals(p_322371_2_, t)) {
         optional = (Optional<T>)this.f_315990_.remove(p_322371_1_);
      } else {
         optional = (Optional<T>)this.f_315990_.put(p_322371_1_, Optional.ofNullable(p_322371_2_));
      }

      this.markDirty();
      return (T)(optional != null ? optional.orElse(t) : t);
   }

   @Nullable
   public <T> T m_321460_(DataComponentType<? extends T> p_321460_1_) {
      this.m_322433_();
      T t = (T)this.f_316296_.m_318834_(p_321460_1_);
      Optional<? extends T> optional;
      if (t != null) {
         optional = (Optional<? extends T>)this.f_315990_.put(p_321460_1_, Optional.m_274566_());
      } else {
         optional = (Optional<? extends T>)this.f_315990_.remove(p_321460_1_);
      }

      return (T)(optional != null ? optional.orElse(null) : t);
   }

   public void m_320975_(DataComponentPatch p_320975_1_) {
      this.m_322433_();
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_320975_1_.f_314958_).iterator();

      while (var2.hasNext()) {
         Entry<DataComponentType<?>, Optional<?>> entry = (Entry<DataComponentType<?>, Optional<?>>)var2.next();
         this.m_318645_((DataComponentType<?>)entry.getKey(), (Optional<?>)entry.getValue());
      }
   }

   private void m_318645_(DataComponentType<?> p_318645_1_, Optional<?> p_318645_2_) {
      Object object = this.f_316296_.m_318834_(p_318645_1_);
      if (p_318645_2_.isPresent()) {
         if (p_318645_2_.get().equals(object)) {
            this.f_315990_.remove(p_318645_1_);
         } else {
            this.f_315990_.put(p_318645_1_, p_318645_2_);
         }
      } else if (object != null) {
         this.f_315990_.put(p_318645_1_, Optional.m_274566_());
      } else {
         this.f_315990_.remove(p_318645_1_);
      }
   }

   public void m_324830_(DataComponentPatch p_324830_1_) {
      this.m_322433_();
      this.f_315990_.clear();
      this.f_315990_.putAll(p_324830_1_.f_314958_);
   }

   public void m_324935_(DataComponentMap p_324935_1_) {
      for (TypedDataComponent<?> typeddatacomponent : p_324935_1_) {
         typeddatacomponent.m_324030_(this);
      }
   }

   private void m_322433_() {
      if (this.f_316660_) {
         this.f_315990_ = new Reference2ObjectArrayMap(this.f_315990_);
         this.f_316660_ = false;
      }
   }

   public Set<DataComponentType<?>> m_319675_() {
      if (this.f_315990_.isEmpty()) {
         return this.f_316296_.m_319675_();
      } else {
         Set<DataComponentType<?>> set = new ReferenceArraySet(this.f_316296_.m_319675_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.f_315990_).iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>>)var2.next();
            Optional<?> optional = (Optional<?>)entry.getValue();
            if (optional.isPresent()) {
               set.add((DataComponentType)entry.getKey());
            } else {
               set.remove(entry.getKey());
            }
         }

         return set;
      }
   }

   public Iterator<TypedDataComponent<?>> iterator() {
      if (this.f_315990_.isEmpty()) {
         return this.f_316296_.iterator();
      } else {
         List<TypedDataComponent<?>> list = new ArrayList(this.f_315990_.size() + this.f_316296_.m_319491_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.f_315990_).iterator();

         while (var2.hasNext()) {
            it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>>)var2.next();
            if (((Optional)entry.getValue()).isPresent()) {
               list.add(TypedDataComponent.m_321971_((DataComponentType)entry.getKey(), ((Optional)entry.getValue()).get()));
            }
         }

         for (TypedDataComponent<?> typeddatacomponent : this.f_316296_) {
            if (!this.f_315990_.containsKey(typeddatacomponent.f_316611_())) {
               list.add(typeddatacomponent);
            }
         }

         return list.iterator();
      }
   }

   public int m_319491_() {
      int i = this.f_316296_.m_319491_();
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.f_315990_).iterator();

      while (var2.hasNext()) {
         it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> entry = (it.unimi.dsi.fastutil.objects.Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>>)var2.next();
         boolean flag = ((Optional)entry.getValue()).isPresent();
         boolean flag1 = this.f_316296_.m_321946_((DataComponentType)entry.getKey());
         if (flag != flag1) {
            i += flag ? 1 : -1;
         }
      }

      return i;
   }

   public DataComponentPatch m_320212_() {
      if (this.f_315990_.isEmpty()) {
         return DataComponentPatch.f_315512_;
      } else {
         this.f_316660_ = true;
         return new DataComponentPatch(this.f_315990_);
      }
   }

   public PatchedDataComponentMap m_319920_() {
      this.f_316660_ = true;
      return new PatchedDataComponentMap(this.f_316296_, this.f_315990_, true);
   }

   public boolean equals(Object p_equals_1_) {
      if (this == p_equals_1_) {
         return true;
      } else {
         if (p_equals_1_ instanceof PatchedDataComponentMap patcheddatacomponentmap
            && this.f_316296_.equals(patcheddatacomponentmap.f_316296_)
            && this.f_315990_.equals(patcheddatacomponentmap.f_315990_)) {
            return true;
         }

         return false;
      }
   }

   public int hashCode() {
      return this.f_316296_.hashCode() + this.f_315990_.hashCode() * 31;
   }

   public String toString() {
      return "{" + (String)this.m_322172_().map(TypedDataComponent::toString).collect(Collectors.joining(", ")) + "}";
   }

   public CompoundTag getTag() {
      if (this.tag == null) {
         Level world = Minecraft.m_91087_().f_91073_;
         if (world != null) {
            DataComponentPatch patch = this.m_320212_();
            this.tag = (CompoundTag)DataComponentPatch.f_315187_.encodeStart(world.m_9598_().m_318927_(NbtOps.f_128958_), patch).getOrThrow();
         }
      }

      return this.tag;
   }

   private void markDirty() {
      this.tag = null;
   }
}
