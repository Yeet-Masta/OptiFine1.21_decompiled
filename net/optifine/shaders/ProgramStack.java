package net.optifine.shaders;

import java.util.ArrayDeque;
import java.util.Deque;

public class ProgramStack {
   private Deque<net.optifine.shaders.Program> stack = new ArrayDeque();

   public void push(net.optifine.shaders.Program p) {
      this.stack.addLast(p);
      if (this.stack.size() > 100) {
         throw new RuntimeException("Program stack overflow: " + this.stack.size());
      }
   }

   public net.optifine.shaders.Program pop() {
      if (this.stack.isEmpty()) {
         throw new RuntimeException("Program stack empty");
      } else {
         return (net.optifine.shaders.Program)this.stack.pollLast();
      }
   }
}
