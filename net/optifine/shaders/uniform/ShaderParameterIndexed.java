package net.optifine.shaders.uniform;

import net.optifine.expr.IExpressionFloat;

public class ShaderParameterIndexed implements IExpressionFloat {
   private ShaderParameterFloat type;
   private int index1;
   private int index2;

   public ShaderParameterIndexed(ShaderParameterFloat type) {
      this(type, 0, 0);
   }

   public ShaderParameterIndexed(ShaderParameterFloat type, int index1) {
      this(type, index1, 0);
   }

   public ShaderParameterIndexed(ShaderParameterFloat type, int index1, int index2) {
      this.type = type;
      this.index1 = index1;
      this.index2 = index2;
   }

   public float eval() {
      return this.type.eval(this.index1, this.index2);
   }

   public String toString() {
      if (this.type.getIndexNames1() == null) {
         return "" + String.valueOf(this.type);
      } else {
         String var10000;
         if (this.type.getIndexNames2() == null) {
            var10000 = String.valueOf(this.type);
            return var10000 + "." + this.type.getIndexNames1()[this.index1];
         } else {
            var10000 = String.valueOf(this.type);
            return var10000 + "." + this.type.getIndexNames1()[this.index1] + "." + this.type.getIndexNames2()[this.index2];
         }
      }
   }
}
