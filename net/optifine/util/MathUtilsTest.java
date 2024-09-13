package net.optifine.util;

import net.minecraft.util.Mth;

public class MathUtilsTest {
   public static void main(String[] args) throws Exception {
      MathUtilsTest.OPER[] values = MathUtilsTest.OPER.values();

      for (int i = 0; i < values.length; i++) {
         MathUtilsTest.OPER oper = values[i];
         dbg("******** " + oper + " ***********");
         m_125854_(oper, false);
      }
   }

   private static void m_125854_(MathUtilsTest.OPER oper, boolean fast) {
      Mth.fastMath = fast;
      double min;
      double max;
      switch (oper) {
         case SIN:
         case COS:
            min = (float) -Math.PI;
            max = (float) Math.PI;
            break;
         case ASIN:
         case ACOS:
            min = -1.0;
            max = 1.0;
            break;
         default:
            return;
      }

      int count = 10;

      for (int i = 0; i <= count; i++) {
         double val = min + (double)i * (max - min) / (double)count;
         float res1;
         float res2;
         switch (oper) {
            case SIN:
               res1 = (float)Math.sin(val);
               res2 = Mth.m_14031_((float)val);
               break;
            case COS:
               res1 = (float)Math.cos(val);
               res2 = Mth.m_14089_((float)val);
               break;
            case ASIN:
               res1 = (float)Math.asin(val);
               res2 = MathUtils.asin((float)val);
               break;
            case ACOS:
               res1 = (float)Math.acos(val);
               res2 = MathUtils.acos((float)val);
               break;
            default:
               return;
         }

         dbg(String.m_12886_("%.2f, Math: %f, Helper: %f, diff: %f", new Object[]{val, res1, res2, Math.abs(res1 - res2)}));
      }
   }

   public static void dbg(String str) {
      System.out.println(str);
   }

   private static enum OPER {
      SIN,
      COS,
      ASIN,
      ACOS;
   }
}
