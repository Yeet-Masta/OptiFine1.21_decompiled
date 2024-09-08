package net.optifine.expr;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestExpressions {
   public static void main(String[] args) throws Exception {
      ExpressionParser ep = new ExpressionParser(null);

      while (true) {
         try {
            InputStreamReader ir = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(ir);
            String line = br.readLine();
            if (line.length() <= 0) {
               return;
            }

            IExpression expr = ep.parse(line);
            if (expr instanceof IExpressionFloat ef) {
               float val = ef.eval();
               System.out.println(val + "");
            }

            if (expr instanceof IExpressionBool eb) {
               boolean val = eb.eval();
               System.out.println(val + "");
            }
         } catch (Exception var8) {
            var8.printStackTrace();
         }
      }
   }
}
