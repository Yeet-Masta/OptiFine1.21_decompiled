package net.optifine.reflect;

import java.lang.reflect.Field;
import net.optifine.Log;

public class FieldLocatorName implements IFieldLocator {
   private ReflectorClass reflectorClass = null;
   private String targetFieldName = null;

   public FieldLocatorName(ReflectorClass reflectorClass, String targetFieldName) {
      this.reflectorClass = reflectorClass;
      this.targetFieldName = targetFieldName;
   }

   public Field getField() {
      Class cls = this.reflectorClass.getTargetClass();
      if (cls == null) {
         return null;
      } else {
         try {
            Field targetField = this.getDeclaredField(cls, this.targetFieldName);
            targetField.setAccessible(true);
            return targetField;
         } catch (NoSuchFieldException var3) {
            String var10000 = cls.getName();
            Log.log("(Reflector) Field not present: " + var10000 + "." + this.targetFieldName);
            return null;
         } catch (SecurityException var4) {
            var4.printStackTrace();
            return null;
         } catch (Throwable var5) {
            var5.printStackTrace();
            return null;
         }
      }
   }

   private Field getDeclaredField(Class cls, String name) throws NoSuchFieldException {
      Field[] fields = cls.getDeclaredFields();

      for(int i = 0; i < fields.length; ++i) {
         Field field = fields[i];
         if (field.getName().equals(name)) {
            return field;
         }
      }

      if (cls == Object.class) {
         throw new NoSuchFieldException(name);
      } else {
         return this.getDeclaredField(cls.getSuperclass(), name);
      }
   }
}
