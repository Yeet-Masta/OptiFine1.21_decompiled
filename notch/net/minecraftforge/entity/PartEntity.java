package net.minecraftforge.entity;

import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_.C_313487_;

public class PartEntity<T extends C_507_> extends C_507_ {
   private final T parent;

   public PartEntity(T parent) {
      super(parent.m_6095_(), parent.m_9236_());
      this.parent = parent;
   }

   public T getParent() {
      return this.parent;
   }

   protected void m_8097_(C_313487_ builderIn) {
      throw new UnsupportedOperationException();
   }

   protected void m_7378_(C_4917_ compound) {
      throw new UnsupportedOperationException();
   }

   protected void m_7380_(C_4917_ compound) {
      throw new UnsupportedOperationException();
   }
}
