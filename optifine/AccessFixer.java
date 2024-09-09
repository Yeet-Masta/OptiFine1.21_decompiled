package optifine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.InnerClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class AccessFixer {
   private static final Logger LOGGER = LogManager.getLogger();

   public static void fixMemberAccess(ClassNode classOld, ClassNode classNew) {
      List fieldsOld = classOld.fields;
      List fieldsNew = classNew.fields;
      Map mapFieldsOld = getMapFields(fieldsOld);
      Iterator it = fieldsNew.iterator();

      while(it.hasNext()) {
         FieldNode fieldNew = (FieldNode)it.next();
         String idNew = fieldNew.name;
         FieldNode fieldOld = (FieldNode)mapFieldsOld.get(idNew);
         if (fieldOld != null && fieldNew.access != fieldOld.access) {
            fieldNew.access = combineAccess(fieldNew.access, fieldOld.access);
         }
      }

      List methodsOld = classOld.methods;
      List methodsNew = classNew.methods;
      Map mapMethodsOld = getMapMethods(methodsOld);
      Set privateChanged = new HashSet();
      Iterator it = methodsNew.iterator();

      while(it.hasNext()) {
         MethodNode methodNew = (MethodNode)it.next();
         String idNew = methodNew.name + methodNew.desc;
         MethodNode methodOld = (MethodNode)mapMethodsOld.get(idNew);
         if (methodOld != null && methodNew.access != methodOld.access) {
            int accessPrev = methodNew.access;
            methodNew.access = combineAccess(methodNew.access, methodOld.access);
            if (isSet(accessPrev, 2) && !isSet(methodNew.access, 2) && !isSet(methodNew.access, 8) && !methodNew.name.equals("<init>")) {
               privateChanged.add(methodNew.name + methodNew.desc);
            }
         }
      }

      if (!privateChanged.isEmpty()) {
         List changed = new ArrayList();
         classNew.methods.forEach((mn) -> {
            Stream var10000 = StreamSupport.stream(Spliterators.spliteratorUnknownSize(mn.instructions.iterator(), 16), false).filter((i) -> {
               return i.getOpcode() == 183;
            });
            MethodInsnNode.class.getClass();
            var10000.map(MethodInsnNode.class::cast).filter((m) -> {
               return privateChanged.contains(m.name + m.desc);
            }).forEach((m) -> {
               m.setOpcode(182);
               changed.add(m);
            });
         });
      }

      List innerClassesOld = classOld.innerClasses;
      List innerClassesNew = classNew.innerClasses;
      Map mapInnerClassesOld = getMapInnerClasses(innerClassesOld);
      Iterator it = innerClassesNew.iterator();

      while(it.hasNext()) {
         InnerClassNode innerClassNew = (InnerClassNode)it.next();
         String idNew = innerClassNew.name;
         InnerClassNode innerClassOld = (InnerClassNode)mapInnerClassesOld.get(idNew);
         if (innerClassOld != null && innerClassNew.access != innerClassOld.access) {
            int accessNew = combineAccess(innerClassNew.access, innerClassOld.access);
            innerClassNew.access = accessNew;
         }
      }

      if (classNew.access != classOld.access) {
         int accessClassNew = combineAccess(classNew.access, classOld.access);
         classNew.access = accessClassNew;
      }

   }

   private static int combineAccess(int access, int access2) {
      if (access == access2) {
         return access;
      } else {
         int MASK_ACCESS = 7;
         int accessClean = access & ~MASK_ACCESS;
         if (!isSet(access, 16) || !isSet(access2, 16)) {
            accessClean &= -17;
         }

         if (!isSet(access, 1) && !isSet(access2, 1)) {
            if (!isSet(access, 4) && !isSet(access2, 4)) {
               if (isSet(access, MASK_ACCESS) && isSet(access2, MASK_ACCESS)) {
                  return !isSet(access, 2) && !isSet(access2, 2) ? accessClean : accessClean | 2;
               } else {
                  return accessClean;
               }
            } else {
               return accessClean | 4;
            }
         } else {
            return accessClean | 1;
         }
      }
   }

   private static boolean isSet(int access, int flag) {
      return (access & flag) != 0;
   }

   public static Map getMapFields(List fields) {
      Map map = new LinkedHashMap();
      Iterator it = fields.iterator();

      while(it.hasNext()) {
         FieldNode fieldNode = (FieldNode)it.next();
         String id = fieldNode.name;
         map.put(id, fieldNode);
      }

      return map;
   }

   public static Map getMapMethods(List methods) {
      Map map = new LinkedHashMap();
      Iterator it = methods.iterator();

      while(it.hasNext()) {
         MethodNode methodNode = (MethodNode)it.next();
         String id = methodNode.name + methodNode.desc;
         map.put(id, methodNode);
      }

      return map;
   }

   public static Map getMapInnerClasses(List innerClasses) {
      Map map = new LinkedHashMap();
      Iterator it = innerClasses.iterator();

      while(it.hasNext()) {
         InnerClassNode innerClassNode = (InnerClassNode)it.next();
         String id = innerClassNode.name;
         map.put(id, innerClassNode);
      }

      return map;
   }

   private static String toString(int access) {
      StringBuffer sb = new StringBuffer();
      if (isSet(access, 1)) {
         addToBuffer(sb, "public", " ");
      }

      if (isSet(access, 4)) {
         addToBuffer(sb, "protected", " ");
      }

      if (isSet(access, 2)) {
         addToBuffer(sb, "private", " ");
      }

      if (isSet(access, 16)) {
         addToBuffer(sb, "final", " ");
      }

      return sb.toString();
   }

   private static void addToBuffer(StringBuffer sb, String val, String separator) {
      if (sb.length() > 0) {
         sb.append(separator);
      }

      sb.append(val);
   }
}
