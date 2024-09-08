package net.optifine.shaders;

import net.minecraft.src.C_3189_;

public class SVertexAttrib {
   public int index;
   public int count;
   public C_3189_.C_3190_ type;
   public int offset;

   public SVertexAttrib(int index, int count, C_3189_.C_3190_ type) {
      this.index = index;
      this.count = count;
      this.type = type;
   }
}
