package net.optifine.shaders.config;

import java.lang.invoke.StringConcatFactory;
import net.minecraft.src.C_3045_;
import net.minecraft.src.C_4713_;
import net.optifine.Config;
import net.optifine.util.StrUtils;
import org.joml.Vector4f;

public class ShaderLine {
   private ShaderLine.Type type;
   private String name;
   private String value;
   private String line;

   public ShaderLine(ShaderLine.Type type, String name, String value, String line) {
      this.type = type;
      this.name = name;
      this.value = value;
      this.line = line;
   }

   public ShaderLine.Type getType() {
      return this.type;
   }

   public String getName() {
      return this.name;
   }

   public String getValue() {
      return this.value;
   }

   public boolean isUniform() {
      return this.type == ShaderLine.Type.UNIFORM;
   }

   public boolean isUniform(String name) {
      return this.isUniform() && name.equals(this.name);
   }

   public boolean isAttribute() {
      return this.type == ShaderLine.Type.ATTRIBUTE;
   }

   public boolean isAttribute(String name) {
      return this.isAttribute() && name.equals(this.name);
   }

   public boolean isProperty() {
      return this.type == ShaderLine.Type.PROPERTY;
   }

   public boolean isConstInt() {
      return this.type == ShaderLine.Type.CONST_INT;
   }

   public boolean isConstFloat() {
      return this.type == ShaderLine.Type.CONST_FLOAT;
   }

   public boolean isConstBool() {
      return this.type == ShaderLine.Type.CONST_BOOL;
   }

   public boolean isExtension() {
      return this.type == ShaderLine.Type.EXTENSION;
   }

   public boolean isConstVec2() {
      return this.type == ShaderLine.Type.CONST_VEC2;
   }

   public boolean isConstVec4() {
      return this.type == ShaderLine.Type.CONST_VEC4;
   }

   public boolean isConstIVec3() {
      return this.type == ShaderLine.Type.CONST_IVEC3;
   }

   public boolean isLayout() {
      return this.type == ShaderLine.Type.LAYOUT;
   }

   public boolean isLayout(String name) {
      return this.isLayout() && name.equals(this.name);
   }

   public boolean isProperty(String name) {
      return this.isProperty() && name.equals(this.name);
   }

   public boolean isProperty(String name, String value) {
      return this.isProperty(name) && value.equals(this.value);
   }

   public boolean isConstInt(String name) {
      return this.isConstInt() && name.equals(this.name);
   }

   public boolean isConstIntSuffix(String suffix) {
      return this.isConstInt() && this.name.endsWith(suffix);
   }

   public boolean isConstIVec3(String name) {
      return this.isConstIVec3() && name.equals(this.name);
   }

   public boolean isConstFloat(String name) {
      return this.isConstFloat() && name.equals(this.name);
   }

   public boolean isConstBool(String name) {
      return this.isConstBool() && name.equals(this.name);
   }

   public boolean isExtension(String name) {
      return this.isExtension() && name.equals(this.name);
   }

   public boolean isConstBoolSuffix(String suffix) {
      return this.isConstBool() && this.name.endsWith(suffix);
   }

   public boolean isConstBoolSuffix(String suffix, boolean val) {
      return this.isConstBoolSuffix(suffix) && this.getValueBool() == val;
   }

   public boolean isConstBool(String name1, String name2) {
      return this.isConstBool(name1) || this.isConstBool(name2);
   }

   public boolean isConstBool(String name1, String name2, String name3) {
      return this.isConstBool(name1) || this.isConstBool(name2) || this.isConstBool(name3);
   }

   public boolean isConstBool(String name, boolean val) {
      return this.isConstBool(name) && this.getValueBool() == val;
   }

   public boolean isConstBool(String name1, String name2, boolean val) {
      return this.isConstBool(name1, name2) && this.getValueBool() == val;
   }

   public boolean isConstBool(String name1, String name2, String name3, boolean val) {
      return this.isConstBool(name1, name2, name3) && this.getValueBool() == val;
   }

   public boolean isConstVec2(String name) {
      return this.isConstVec2() && name.equals(this.name);
   }

   public boolean isConstVec4Suffix(String suffix) {
      return this.isConstVec4() && this.name.endsWith(suffix);
   }

   public int getValueInt() {
      try {
         return Integer.parseInt(this.value);
      } catch (NumberFormatException var2) {
         throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
      }
   }

   public float getValueFloat() {
      try {
         return Float.parseFloat(this.value);
      } catch (NumberFormatException var2) {
         throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
      }
   }

   public C_4713_ getValueIVec3() {
      if (this.value == null) {
         return null;
      } else {
         String str = this.value.trim();
         str = StrUtils.removePrefix(str, "ivec3");
         str = StrUtils.trim(str, " ()");
         String[] parts = Config.tokenize(str, ", ");
         if (parts.length != 3) {
            return null;
         } else {
            int[] vals = new int[3];

            for (int i = 0; i < parts.length; i++) {
               String part = parts[i];
               int val = Config.parseInt(part, Integer.MIN_VALUE);
               if (val == Integer.MIN_VALUE) {
                  return null;
               }

               vals[i] = val;
            }

            return new C_4713_(vals[0], vals[1], vals[2]);
         }
      }
   }

   public C_3045_ getValueVec2() {
      if (this.value == null) {
         return null;
      } else {
         String str = this.value.trim();
         str = StrUtils.removePrefix(str, "vec2");
         str = StrUtils.trim(str, " ()");
         String[] parts = Config.tokenize(str, ", ");
         if (parts.length != 2) {
            return null;
         } else {
            float[] fs = new float[2];

            for (int i = 0; i < parts.length; i++) {
               String part = parts[i];
               part = StrUtils.removeSuffix(part, new String[]{"F", "f"});
               float f = Config.parseFloat(part, Float.MAX_VALUE);
               if (f == Float.MAX_VALUE) {
                  return null;
               }

               fs[i] = f;
            }

            return new C_3045_(fs[0], fs[1]);
         }
      }
   }

   public Vector4f getValueVec4() {
      if (this.value == null) {
         return null;
      } else {
         String str = this.value.trim();
         str = StrUtils.removePrefix(str, "vec4");
         str = StrUtils.trim(str, " ()");
         String[] parts = Config.tokenize(str, ", ");
         if (parts.length != 4) {
            return null;
         } else {
            float[] fs = new float[4];

            for (int i = 0; i < parts.length; i++) {
               String part = parts[i];
               part = StrUtils.removeSuffix(part, new String[]{"F", "f"});
               float f = Config.parseFloat(part, Float.MAX_VALUE);
               if (f == Float.MAX_VALUE) {
                  return null;
               }

               fs[i] = f;
            }

            return new Vector4f(fs[0], fs[1], fs[2], fs[3]);
         }
      }
   }

   public boolean getValueBool() {
      String valLow = this.value.toLowerCase();
      if (!valLow.equals("true") && !valLow.equals("false")) {
         throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
      } else {
         return Boolean.valueOf(this.value);
      }
   }

   public String toString() {
      return StringConcatFactory.makeConcatWithConstants<"makeConcatWithConstants","\u0001">(this.line);
   }

   public static enum Type {
      UNIFORM,
      ATTRIBUTE,
      CONST_INT,
      CONST_IVEC3,
      CONST_FLOAT,
      CONST_VEC2,
      CONST_VEC4,
      CONST_BOOL,
      PROPERTY,
      EXTENSION,
      LAYOUT;
   }
}
