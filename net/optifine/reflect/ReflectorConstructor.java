package net.optifine.reflect;

import java.lang.reflect.Constructor;
import net.optifine.Log;
import net.optifine.util.ArrayUtils;

public class ReflectorConstructor implements IResolvable {
   private ReflectorClass reflectorClass = null;
   private Class[] parameterTypes = null;
   private boolean checked = false;
   private Constructor targetConstructor = null;

   public ReflectorConstructor(ReflectorClass reflectorClass, Class[] parameterTypes) {
      this.reflectorClass = reflectorClass;
      this.parameterTypes = parameterTypes;
      ReflectorResolver.register(this);
   }

   public Constructor getTargetConstructor() {
      if (this.checked) {
         return this.targetConstructor;
      } else {
         this.checked = true;
         Class cls = this.reflectorClass.getTargetClass();
         if (cls == null) {
            return null;
         } else {
            try {
               this.targetConstructor = findConstructor(cls, this.parameterTypes);
               if (this.targetConstructor == null) {
                  String var10000 = cls.getName();
                  Log.dbg("(Reflector) Constructor not present: " + var10000 + ", params: " + ArrayUtils.arrayToString((Object[])this.parameterTypes));
               }

               if (this.targetConstructor != null) {
                  this.targetConstructor.setAccessible(true);
               }
            } catch (Throwable var3) {
               var3.printStackTrace();
            }

            return this.targetConstructor;
         }
      }
   }

   private static Constructor findConstructor(Class cls, Class[] paramTypes) {
      Constructor[] cs = cls.getDeclaredConstructors();

      for(int i = 0; i < cs.length; ++i) {
         Constructor c = cs[i];
         Class[] types = c.getParameterTypes();
         if (Reflector.matchesTypes(paramTypes, types)) {
            return c;
         }
      }

      return null;
   }

   public boolean exists() {
      if (this.checked) {
         return this.targetConstructor != null;
      } else {
         return this.getTargetConstructor() != null;
      }
   }

   public void deactivate() {
      this.checked = true;
      this.targetConstructor = null;
   }

   public Object newInstance(Object... params) {
      return Reflector.newInstance(this, params);
   }

   public void resolve() {
      Constructor c = this.getTargetConstructor();
   }
}
