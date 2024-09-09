package net.minecraft.src;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class C_164_ extends AbstractCollection {
   private final Map f_13527_ = Maps.newHashMap();
   private final Class f_13528_;
   private final List f_13529_ = Lists.newArrayList();

   public C_164_(Class baseClassIn) {
      this.f_13528_ = baseClassIn;
      this.f_13527_.put(baseClassIn, this.f_13529_);
   }

   public boolean add(Object p_add_1_) {
      boolean flag = false;
      Iterator var3 = this.f_13527_.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (((Class)entry.getKey()).isInstance(p_add_1_)) {
            flag |= ((List)entry.getValue()).add(p_add_1_);
         }
      }

      return flag;
   }

   public boolean remove(Object p_remove_1_) {
      boolean flag = false;
      Iterator var3 = this.f_13527_.entrySet().iterator();

      while(var3.hasNext()) {
         Map.Entry entry = (Map.Entry)var3.next();
         if (((Class)entry.getKey()).isInstance(p_remove_1_)) {
            List list = (List)entry.getValue();
            flag |= list.remove(p_remove_1_);
         }
      }

      return flag;
   }

   public boolean contains(Object p_contains_1_) {
      return this.m_13533_(p_contains_1_.getClass()).contains(p_contains_1_);
   }

   public Collection m_13533_(Class classIn) {
      if (!this.f_13528_.isAssignableFrom(classIn)) {
         throw new IllegalArgumentException("Don't know how to search for " + String.valueOf(classIn));
      } else {
         List list = (List)this.f_13527_.computeIfAbsent(classIn, (class2In) -> {
            Stream var10000 = this.f_13529_.stream();
            Objects.requireNonNull(class2In);
            return (List)var10000.filter(class2In::isInstance).collect(C_5322_.m_323807_());
         });
         return Collections.unmodifiableCollection(list);
      }
   }

   public Iterator iterator() {
      return (Iterator)(this.f_13529_.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.f_13529_.iterator()));
   }

   public List m_13532_() {
      return ImmutableList.copyOf(this.f_13529_);
   }

   public int size() {
      return this.f_13529_.size();
   }

   public List getValues() {
      return this.f_13529_;
   }
}
