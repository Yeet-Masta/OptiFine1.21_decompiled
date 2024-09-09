package net.minecraftforge.entity;

import net.minecraft.src.C_4917_;
import net.minecraft.src.C_507_;
import net.minecraft.src.C_5247_;

public class PartEntity extends C_507_ {
   private final C_507_ parent;

   public PartEntity(C_507_ parent) {
      super(parent.m_6095_(), parent.m_9236_());
      this.parent = parent;
   }

   public C_507_ getParent() {
      return this.parent;
   }

   protected void m_8097_(C_5247_.C_313487_ builderIn) {
      throw new UnsupportedOperationException();
   }

   protected void m_7378_(C_4917_ compound) {
      throw new UnsupportedOperationException();
   }

   protected void m_7380_(C_4917_ compound) {
      throw new UnsupportedOperationException();
   }
}
