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
import java.util.Map.Entry;

public class C_164_<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> f_13527_ = Maps.newHashMap();
   private final Class<T> f_13528_;
   private final List<T> f_13529_ = Lists.newArrayList();

   public C_164_(Class<T> baseClassIn) {
      this.f_13528_ = baseClassIn;
      this.f_13527_.put(baseClassIn, this.f_13529_);
   }

   public boolean add(T p_add_1_) {
      boolean flag = false;

      for (Entry<Class<?>, List<T>> entry : this.f_13527_.entrySet()) {
         if (((Class)entry.getKey()).isInstance(p_add_1_)) {
            flag |= ((List)entry.getValue()).add(p_add_1_);
         }
      }

      return flag;
   }

   public boolean remove(Object p_remove_1_) {
      boolean flag = false;

      for (Entry<Class<?>, List<T>> entry : this.f_13527_.entrySet()) {
         if (((Class)entry.getKey()).isInstance(p_remove_1_)) {
            List<T> list = (List<T>)entry.getValue();
            flag |= list.remove(p_remove_1_);
         }
      }

      return flag;
   }

   public boolean contains(Object p_contains_1_) {
      return this.m_13533_(p_contains_1_.getClass()).contains(p_contains_1_);
   }

   public <S> Collection<S> m_13533_(Class<S> classIn) {
      if (!this.f_13528_.isAssignableFrom(classIn)) {
         throw new IllegalArgumentException("Don't know how to search for " + classIn);
      } else {
         List<? extends T> list = (List<? extends T>)this.f_13527_
            .computeIfAbsent(classIn, class2In -> (List)this.f_13529_.stream().filter(class2In::isInstance).collect(C_5322_.m_323807_()));
         return Collections.unmodifiableCollection(list);
      }
   }

   public Iterator<T> iterator() {
      return (Iterator<T>)(this.f_13529_.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.f_13529_.iterator()));
   }

   public List<T> m_13532_() {
      return ImmutableList.copyOf(this.f_13529_);
   }

   public int size() {
      return this.f_13529_.size();
   }

   public List<T> getValues() {
      return this.f_13529_;
   }
}
