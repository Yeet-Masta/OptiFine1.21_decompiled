package net.minecraft.core.component;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import it.unimi.dsi.fastutil.objects.ReferenceArraySet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.Level;

public final class PatchedDataComponentMap implements DataComponentMap {
   private final DataComponentMap f_316296_;
   private Reference2ObjectMap f_315990_;
   private boolean f_316660_;
   private CompoundTag tag;

   public PatchedDataComponentMap(DataComponentMap p_i323282_1_) {
      this(p_i323282_1_, Reference2ObjectMaps.emptyMap(), true);
   }

   private PatchedDataComponentMap(DataComponentMap p_i318911_1_, Reference2ObjectMap p_i318911_2_, boolean p_i318911_3_) {
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

   private static boolean m_323581_(DataComponentMap p_323581_0_, Reference2ObjectMap p_323581_1_) {
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_323581_1_).iterator();

      Object object;
      Optional optional;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         Map.Entry entry = (Map.Entry)var2.next();
         object = p_323581_0_.m_318834_((DataComponentType)entry.getKey());
         optional = (Optional)entry.getValue();
         if (optional.isPresent() && optional.get().equals(object)) {
            return false;
         }
      } while(!optional.isEmpty() || object != null);

      return false;
   }

   @Nullable
   public Object m_318834_(DataComponentType p_318834_1_) {
      Optional optional = (Optional)this.f_315990_.get(p_318834_1_);
      return optional != null ? optional.orElse((Object)null) : this.f_316296_.m_318834_(p_318834_1_);
   }

   @Nullable
   public Object m_322371_(DataComponentType p_322371_1_, @Nullable Object p_322371_2_) {
      this.m_322433_();
      Object t = this.f_316296_.m_318834_(p_322371_1_);
      Optional optional;
      if (Objects.equals(p_322371_2_, t)) {
         optional = (Optional)this.f_315990_.remove(p_322371_1_);
      } else {
         optional = (Optional)this.f_315990_.put(p_322371_1_, Optional.ofNullable(p_322371_2_));
      }

      this.markDirty();
      return optional != null ? optional.orElse(t) : t;
   }

   @Nullable
   public Object m_321460_(DataComponentType p_321460_1_) {
      this.m_322433_();
      Object t = this.f_316296_.m_318834_(p_321460_1_);
      Optional optional;
      if (t != null) {
         optional = (Optional)this.f_315990_.put(p_321460_1_, Optional.empty());
      } else {
         optional = (Optional)this.f_315990_.remove(p_321460_1_);
      }

      return optional != null ? optional.orElse((Object)null) : t;
   }

   public void m_320975_(DataComponentPatch p_320975_1_) {
      this.m_322433_();
      ObjectIterator var2 = Reference2ObjectMaps.fastIterable(p_320975_1_.f_314958_).iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         this.m_318645_((DataComponentType)entry.getKey(), (Optional)entry.getValue());
      }

   }

   private void m_318645_(DataComponentType p_318645_1_, Optional p_318645_2_) {
      Object object = this.f_316296_.m_318834_(p_318645_1_);
      if (p_318645_2_.isPresent()) {
         if (p_318645_2_.get().equals(object)) {
            this.f_315990_.remove(p_318645_1_);
         } else {
            this.f_315990_.put(p_318645_1_, p_318645_2_);
         }
      } else if (object != null) {
         this.f_315990_.put(p_318645_1_, Optional.empty());
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
      Iterator var2 = p_324935_1_.iterator();

      while(var2.hasNext()) {
         TypedDataComponent typeddatacomponent = (TypedDataComponent)var2.next();
         typeddatacomponent.m_324030_(this);
      }

   }

   private void m_322433_() {
      if (this.f_316660_) {
         this.f_315990_ = new Reference2ObjectArrayMap(this.f_315990_);
         this.f_316660_ = false;
      }

   }

   public Set m_319675_() {
      if (this.f_315990_.isEmpty()) {
         return this.f_316296_.m_319675_();
      } else {
         Set set = new ReferenceArraySet(this.f_316296_.m_319675_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.f_315990_).iterator();

         while(var2.hasNext()) {
            Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)var2.next();
            Optional optional = (Optional)entry.getValue();
            if (optional.isPresent()) {
               set.add((DataComponentType)entry.getKey());
            } else {
               set.remove(entry.getKey());
            }
         }

         return set;
      }
   }

   public Iterator iterator() {
      if (this.f_315990_.isEmpty()) {
         return this.f_316296_.iterator();
      } else {
         List list = new ArrayList(this.f_315990_.size() + this.f_316296_.m_319491_());
         ObjectIterator var2 = Reference2ObjectMaps.fastIterable(this.f_315990_).iterator();

         while(var2.hasNext()) {
            Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)var2.next();
            if (((Optional)entry.getValue()).isPresent()) {
               list.add(TypedDataComponent.m_321971_((DataComponentType)entry.getKey(), ((Optional)entry.getValue()).get()));
            }
         }

         Iterator var4 = this.f_316296_.iterator();

         while(var4.hasNext()) {
            TypedDataComponent typeddatacomponent = (TypedDataComponent)var4.next();
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

      while(var2.hasNext()) {
         Reference2ObjectMap.Entry entry = (Reference2ObjectMap.Entry)var2.next();
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
         if (p_equals_1_ instanceof PatchedDataComponentMap) {
            PatchedDataComponentMap patcheddatacomponentmap = (PatchedDataComponentMap)p_equals_1_;
            if (this.f_316296_.equals(patcheddatacomponentmap.f_316296_) && this.f_315990_.equals(patcheddatacomponentmap.f_315990_)) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return this.f_316296_.hashCode() + this.f_315990_.hashCode() * 31;
   }

   public String toString() {
      Stream var10000 = this.m_322172_().map(TypedDataComponent::toString);
      return "{" + (String)var10000.collect(Collectors.joining(", ")) + "}";
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
