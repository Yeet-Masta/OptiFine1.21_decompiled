package net.optifine.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class DummyList implements List {
   public static final DummyList INSTANCE = new DummyList();

   public int size() {
      return 0;
   }

   public boolean isEmpty() {
      return true;
   }

   public boolean contains(Object o) {
      return false;
   }

   public Iterator iterator() {
      return null;
   }

   public Object[] toArray() {
      return null;
   }

   public Object[] toArray(Object[] a) {
      return null;
   }

   public boolean add(Object e) {
      return false;
   }

   public boolean remove(Object o) {
      return false;
   }

   public boolean containsAll(Collection c) {
      return false;
   }

   public boolean addAll(Collection c) {
      return false;
   }

   public boolean addAll(int index, Collection c) {
      return false;
   }

   public boolean removeAll(Collection c) {
      return false;
   }

   public boolean retainAll(Collection c) {
      return false;
   }

   public void clear() {
   }

   public Object get(int index) {
      return null;
   }

   public Object set(int index, Object element) {
      return null;
   }

   public void add(int index, Object element) {
   }

   public Object remove(int index) {
      return null;
   }

   public int indexOf(Object o) {
      return -1;
   }

   public int lastIndexOf(Object o) {
      return -1;
   }

   public ListIterator listIterator() {
      return null;
   }

   public ListIterator listIterator(int index) {
      return null;
   }

   public List subList(int fromIndex, int toIndex) {
      return this;
   }

   public class DummyListIterator implements ListIterator {
      public DummyListIterator(final DummyList this$0) {
      }

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         return null;
      }

      public boolean hasPrevious() {
         return false;
      }

      public Object previous() {
         return null;
      }

      public int nextIndex() {
         return 0;
      }

      public int previousIndex() {
         return 0;
      }

      public void remove() {
      }

      public void set(Object e) {
      }

      public void add(Object e) {
      }
   }

   public class DummyIterator implements Iterator {
      public DummyIterator(final DummyList this$0) {
      }

      public boolean hasNext() {
         return false;
      }

      public Object next() {
         return null;
      }
   }
}
