package net.optifine.util;

import net.minecraft.util.Mth;

public class MathUtilsTest {
   public static void main(String[] args) throws Exception {
      OPER[] values = MathUtilsTest.OPER.values();

      for(int i = 0; i < values.length; ++i) {
         OPER oper = values[i];
         dbg("******** " + String.valueOf(oper) + " ***********");
         test(oper, false);
      }

   }

   private static void test(OPER oper, boolean fast) {
      Mth.fastMath = fast;
      double min;
      double max;
      switch (oper.ordinal()) {
         case 0:
         case 1:
            min = -3.1415927410125732;
            max = 3.1415927410125732;
            break;
         case 2:
         case 3:
            min = -1.0;
            max = 1.0;
            break;
         default:
            return;
      }

      int count = 10;

      for(int i = 0; i <= count; ++i) {
         double val = min + (double)i * (max - min) / (double)count;
         float res1;
         float res2;
         switch (oper.ordinal()) {
            case 0:
               res1 = (float)Math.sin(val);
               res2 = Mth.m_14031_((float)val);
               break;
            case 1:
               res1 = (float)Math.cos(val);
               res2 = Mth.m_14089_((float)val);
               break;
            case 2:
               res1 = (float)Math.asin(val);
               res2 = MathUtils.asin((float)val);
               break;
            case 3:
               res1 = (float)Math.acos(val);
               res2 = MathUtils.acos((float)val);
               break;
            default:
               return;
         }

         dbg(String.format("%.2f, Math: %f, Helper: %f, diff: %f", val, res1, res2, Math.abs(res1 - res2)));
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

      // $FF: synthetic method
      private static OPER[] $values() {
         return new OPER[]{SIN, COS, ASIN, ACOS};
      }
   }
}
