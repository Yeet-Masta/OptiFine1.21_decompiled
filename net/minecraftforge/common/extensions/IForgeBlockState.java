package net.minecraftforge.common.extensions;

import net.minecraft.src.C_1559_;
import net.minecraft.src.C_4675_;

public interface IForgeBlockState {
   private BlockState self() {
      return (BlockState)this;
   }

   default int getLightEmission(C_1559_ level, C_4675_ pos) {
      return this.self().m_60791_();
   }
}
