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

public class ClassInstanceMultiMap<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> a = Maps.newHashMap();
   private final Class<T> b;
   private final List<T> c = Lists.newArrayList();

   public ClassInstanceMultiMap(Class<T> baseClassIn) {
      this.b = baseClassIn;
      this.a.put(baseClassIn, this.c);
   }

   public boolean add(T p_add_1_) {
      boolean flag = false;

      for (Entry<Class<?>, List<T>> entry : this.a.entrySet()) {
         if (((Class)entry.getKey()).isInstance(p_add_1_)) {
            flag |= ((List)entry.getValue()).add(p_add_1_);
         }
      }

      return flag;
   }

   public boolean remove(Object p_remove_1_) {
      boolean flag = false;

      for (Entry<Class<?>, List<T>> entry : this.a.entrySet()) {
         if (((Class)entry.getKey()).isInstance(p_remove_1_)) {
            List<T> list = (List<T>)entry.getValue();
            flag |= list.remove(p_remove_1_);
         }
      }

      return flag;
   }

   public boolean contains(Object p_contains_1_) {
      return this.a(p_contains_1_.getClass()).contains(p_contains_1_);
   }

   public <S> Collection<S> a(Class<S> classIn) {
      if (!this.b.isAssignableFrom(classIn)) {
         throw new IllegalArgumentException("Don't know how to search for " + classIn);
      } else {
         List<? extends T> list = (List<? extends T>)this.a
            .computeIfAbsent(classIn, class2In -> (List)this.c.stream().filter(class2In::isInstance).collect(Util.b()));
         return Collections.unmodifiableCollection(list);
      }
   }

   public Iterator<T> iterator() {
      return (Iterator<T>)(this.c.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.c.iterator()));
   }

   public List<T> a() {
      return ImmutableList.copyOf(this.c);
   }

   public int size() {
      return this.c.size();
   }

   public List<T> getValues() {
      return this.c;
   }
}
