package net.optifine.shaders.uniform;

import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionFloatArray;

public enum UniformType {
   BOOL,
   INT,
   FLOAT,
   VEC2,
   VEC3,
   VEC4;

   public ShaderUniformBase makeShaderUniform(String name) {
      switch (this.ordinal()) {
         case 0:
            return new ShaderUniform1i(name);
         case 1:
            return new ShaderUniform1i(name);
         case 2:
            return new ShaderUniform1f(name);
         case 3:
            return new ShaderUniform2f(name);
         case 4:
            return new ShaderUniform3f(name);
         case 5:
            return new ShaderUniform4f(name);
         default:
            throw new RuntimeException("Unknown uniform type: " + String.valueOf(this));
      }
   }

   public void updateUniform(IExpression expression, ShaderUniformBase uniform) {
      switch (this.ordinal()) {
         case 0:
            this.updateUniformBool((IExpressionBool)expression, (ShaderUniform1i)uniform);
            return;
         case 1:
            this.updateUniformInt((IExpressionFloat)expression, (ShaderUniform1i)uniform);
            return;
         case 2:
            this.updateUniformFloat((IExpressionFloat)expression, (ShaderUniform1f)uniform);
            return;
         case 3:
            this.updateUniformFloat2((IExpressionFloatArray)expression, (ShaderUniform2f)uniform);
            return;
         case 4:
            this.updateUniformFloat3((IExpressionFloatArray)expression, (ShaderUniform3f)uniform);
            return;
         case 5:
            this.updateUniformFloat4((IExpressionFloatArray)expression, (ShaderUniform4f)uniform);
            return;
         default:
            throw new RuntimeException("Unknown uniform type: " + String.valueOf(this));
      }
   }

   private void updateUniformBool(IExpressionBool expression, ShaderUniform1i uniform) {
      boolean val = expression.eval();
      int valInt = val ? 1 : 0;
      uniform.setValue(valInt);
   }

   private void updateUniformInt(IExpressionFloat expression, ShaderUniform1i uniform) {
      int val = (int)expression.eval();
      uniform.setValue(val);
   }

   private void updateUniformFloat(IExpressionFloat expression, ShaderUniform1f uniform) {
      float val = expression.eval();
      uniform.setValue(val);
   }

   private void updateUniformFloat2(IExpressionFloatArray expression, ShaderUniform2f uniform) {
      float[] val = expression.eval();
      if (val.length != 2) {
         throw new RuntimeException("Value length is not 2, length: " + val.length);
      } else {
         uniform.setValue(val[0], val[1]);
      }
   }

   private void updateUniformFloat3(IExpressionFloatArray expression, ShaderUniform3f uniform) {
      float[] val = expression.eval();
      if (val.length != 3) {
         throw new RuntimeException("Value length is not 3, length: " + val.length);
      } else {
         uniform.setValue(val[0], val[1], val[2]);
      }
   }

   private void updateUniformFloat4(IExpressionFloatArray expression, ShaderUniform4f uniform) {
      float[] val = expression.eval();
      if (val.length != 4) {
         throw new RuntimeException("Value length is not 4, length: " + val.length);
      } else {
         uniform.setValue(val[0], val[1], val[2], val[3]);
      }
   }

   public boolean matchesExpressionType(ExpressionType expressionType) {
      switch (this.ordinal()) {
         case 0:
            return expressionType == ExpressionType.BOOL;
         case 1:
            return expressionType == ExpressionType.FLOAT;
         case 2:
            return expressionType == ExpressionType.FLOAT;
         case 3:
         case 4:
         case 5:
            return expressionType == ExpressionType.FLOAT_ARRAY;
         default:
            throw new RuntimeException("Unknown uniform type: " + String.valueOf(this));
      }
   }

   public static UniformType parse(String type) {
      UniformType[] values = values();

      for(int i = 0; i < values.length; ++i) {
         UniformType uniformType = values[i];
         if (uniformType.name().toLowerCase().equals(type)) {
            return uniformType;
         }
      }

      return null;
   }

   // $FF: synthetic method
   private static UniformType[] $values() {
      return new UniformType[]{BOOL, INT, FLOAT, VEC2, VEC3, VEC4};
   }
}
