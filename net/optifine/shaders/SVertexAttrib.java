package net.optifine.shaders;

public class SVertexAttrib {
   public int index;
   public int count;
   public com.mojang.blaze3d.vertex.VertexFormatElement.Type type;
   public int offset;

   public SVertexAttrib(int index, int count, com.mojang.blaze3d.vertex.VertexFormatElement.Type type) {
      this.index = index;
      this.count = count;
      this.type = type;
   }
}
