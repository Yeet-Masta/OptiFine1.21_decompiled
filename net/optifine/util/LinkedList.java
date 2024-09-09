package net.optifine.util;

import java.util.Iterator;

public class LinkedList {
   private Node first;
   private Node last;
   private int size;

   public void addFirst(Node node) {
      this.checkNoParent(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         Node nodeNext = this.first;
         node.setNext(nodeNext);
         nodeNext.setPrev(node);
         this.first = node;
      }

      node.setParent(this);
      ++this.size;
   }

   public void addLast(Node node) {
      this.checkNoParent(node);
      if (this.isEmpty()) {
         this.first = node;
         this.last = node;
      } else {
         Node nodePrev = this.last;
         node.setPrev(nodePrev);
         nodePrev.setNext(node);
         this.last = node;
      }

      node.setParent(this);
      ++this.size;
   }

   public void addAfter(Node nodePrev, Node node) {
      if (nodePrev == null) {
         this.addFirst(node);
      } else if (nodePrev == this.last) {
         this.addLast(node);
      } else {
         this.checkParent(nodePrev);
         this.checkNoParent(node);
         Node nodeNext = nodePrev.getNext();
         nodePrev.setNext(node);
         node.setPrev(nodePrev);
         nodeNext.setPrev(node);
         node.setNext(nodeNext);
         node.setParent(this);
         ++this.size;
      }
   }

   public Node remove(Node node) {
      this.checkParent(node);
      Node prev = node.getPrev();
      Node next = node.getNext();
      if (prev != null) {
         prev.setNext(next);
      } else {
         this.first = next;
      }

      if (next != null) {
         next.setPrev(prev);
      } else {
         this.last = prev;
      }

      node.setPrev((Node)null);
      node.setNext((Node)null);
      node.setParent((LinkedList)null);
      --this.size;
      return node;
   }

   public void moveAfter(Node nodePrev, Node node) {
      this.remove(node);
      this.addAfter(nodePrev, node);
   }

   public boolean find(Node nodeFind, Node nodeFrom, Node nodeTo) {
      this.checkParent(nodeFrom);
      if (nodeTo != null) {
         this.checkParent(nodeTo);
      }

      Node node;
      for(node = nodeFrom; node != null && node != nodeTo; node = node.getNext()) {
         if (node == nodeFind) {
            return true;
         }
      }

      if (node != nodeTo) {
         String var10002 = String.valueOf(nodeFrom);
         throw new IllegalArgumentException("Sublist is not linked, from: " + var10002 + ", to: " + String.valueOf(nodeTo));
      } else {
         return false;
      }
   }

   private void checkParent(Node node) {
      if (node.parent != this) {
         String var10002 = String.valueOf(node);
         throw new IllegalArgumentException("Node has different parent, node: " + var10002 + ", parent: " + String.valueOf(node.parent) + ", this: " + String.valueOf(this));
      }
   }

   private void checkNoParent(Node node) {
      if (node.parent != null) {
         String var10002 = String.valueOf(node);
         throw new IllegalArgumentException("Node has different parent, node: " + var10002 + ", parent: " + String.valueOf(node.parent) + ", this: " + String.valueOf(this));
      }
   }

   public boolean contains(Node node) {
      return node.parent == this;
   }

   public Iterator iterator() {
      Iterator it = new Iterator() {
         Node node = LinkedList.this.getFirst();

         public boolean hasNext() {
            return this.node != null;
         }

         public Node next() {
            Node ret = this.node;
            if (this.node != null) {
               this.node = this.node.next;
            }

            return ret;
         }
      };
      return it;
   }

   public Node getFirst() {
      return this.first;
   }

   public Node getLast() {
      return this.last;
   }

   public int getSize() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size <= 0;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();

      Node node;
      for(Iterator it = this.iterator(); it.hasNext(); sb.append(node.getItem())) {
         node = (Node)it.next();
         if (sb.length() > 0) {
            sb.append(", ");
         }
      }

      int var10000 = this.size;
      return "" + var10000 + " [" + sb.toString() + "]";
   }

   public static class Node {
      private final Object item;
      private Node prev;
      private Node next;
      private LinkedList parent;

      public Node(Object item) {
         this.item = item;
      }

      public Object getItem() {
         return this.item;
      }

      public Node getPrev() {
         return this.prev;
      }

      public Node getNext() {
         return this.next;
      }

      private void setPrev(Node prev) {
         this.prev = prev;
      }

      private void setNext(Node next) {
         this.next = next;
      }

      private void setParent(LinkedList parent) {
         this.parent = parent;
      }

      public String toString() {
         return "" + String.valueOf(this.item);
      }
   }
}
