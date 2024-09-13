package net.optifine.shaders.config;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.ExpressionType;
import net.optifine.expr.IExpression;
import net.optifine.expr.IExpressionBool;
import net.optifine.expr.IExpressionFloat;
import net.optifine.expr.IExpressionResolver;
import net.optifine.expr.ParseException;

public class MacroState {
   private boolean active = true;
   private Deque<Boolean> dequeState = new ArrayDeque();
   private Deque<Boolean> dequeResolved = new ArrayDeque();
   private Map<String, String> mapMacroValues = new HashMap();
   public static Pattern PATTERN_DIRECTIVE = Pattern.m_289905_("\\s*#\\s*(\\w+)\\s*(.*)");
   private static Pattern PATTERN_DEFINED = Pattern.m_289905_("defined\\s+(\\w+)");
   private static Pattern PATTERN_DEFINED_FUNC = Pattern.m_289905_("defined\\s*\\(\\s*(\\w+)\\s*\\)");
   private static Pattern PATTERN_MACRO = Pattern.m_289905_("(\\w+)");
   private static String DEFINE;
   private static String UNDEF;
   private static String IFDEF;
   private static String IFNDEF;
   private static String IF;
   private static String ELSE;
   private static String ELIF;
   private static String ENDIF;
   private static List<String> MACRO_NAMES = Arrays.asList("define", "undef", "ifdef", "ifndef", "if", "else", "elif", "endif");

   public boolean processLine(String line) {
      Matcher m = PATTERN_DIRECTIVE.matcher(line);
      if (!m.matches()) {
         return this.active;
      } else {
         String name = m.group(1);
         String param = m.group(2);
         int posComment = param.indexOf("//");
         if (posComment >= 0) {
            param = param.substring(0, posComment);
         }

         boolean activePrev = this.active;
         this.processMacro(name, param);
         this.active = !this.dequeState.m_274455_(Boolean.FALSE);
         return this.active || activePrev;
      }
   }

   public static boolean isMacroLine(String line) {
      Matcher m = PATTERN_DIRECTIVE.matcher(line);
      if (!m.matches()) {
         return false;
      } else {
         String name = m.group(1);
         return MACRO_NAMES.m_274455_(name);
      }
   }

   private void processMacro(String name, String param) {
      StringTokenizer tok = new StringTokenizer(param, " \t");
      String macro = tok.hasMoreTokens() ? tok.nextToken() : "";
      String rest = tok.hasMoreTokens() ? tok.nextToken("").trim() : "";
      if (name.equals("define")) {
         this.mapMacroValues.put(macro, rest);
      } else if (name.equals("undef")) {
         this.mapMacroValues.remove(macro);
      } else if (name.equals("ifdef")) {
         boolean act = this.mapMacroValues.containsKey(macro);
         this.dequeState.add(act);
         this.dequeResolved.add(act);
      } else if (name.equals("ifndef")) {
         boolean act = !this.mapMacroValues.containsKey(macro);
         this.dequeState.add(act);
         this.dequeResolved.add(act);
      } else if (name.equals("if")) {
         boolean act = this.eval(param);
         this.dequeState.add(act);
         this.dequeResolved.add(act);
      } else if (!this.dequeState.isEmpty()) {
         if (name.equals("elif")) {
            boolean lastState = (Boolean)this.dequeState.removeLast();
            boolean lastResolved = (Boolean)this.dequeResolved.removeLast();
            if (lastResolved) {
               this.dequeState.add(false);
               this.dequeResolved.add(lastResolved);
            } else {
               boolean act = this.eval(param);
               this.dequeState.add(act);
               this.dequeResolved.add(act);
            }
         } else if (name.equals("else")) {
            boolean lastState = (Boolean)this.dequeState.removeLast();
            boolean lastResolved = (Boolean)this.dequeResolved.removeLast();
            boolean act = !lastResolved;
            this.dequeState.add(act);
            this.dequeResolved.add(true);
         } else if (name.equals("endif")) {
            this.dequeState.removeLast();
            this.dequeResolved.removeLast();
         }
      }
   }

   private boolean eval(String str) {
      Matcher md = PATTERN_DEFINED.matcher(str);
      str = md.replaceAll("defined_$1");
      Matcher mdf = PATTERN_DEFINED_FUNC.matcher(str);
      str = mdf.replaceAll("defined_$1");
      boolean replaced = false;
      int count = 0;

      do {
         replaced = false;
         Matcher mmn = PATTERN_MACRO.matcher(str);

         while (mmn.find()) {
            String match = mmn.group();
            if (match.length() > 0) {
               char ch = match.charAt(0);
               if ((Character.isLetter(ch) || ch == '_') && this.mapMacroValues.containsKey(match)) {
                  String val = (String)this.mapMacroValues.get(match);
                  if (val == null) {
                     val = "1";
                  }

                  int start = mmn.start();
                  int end = mmn.end();
                  str = str.substring(0, start) + " " + val + " " + str.substring(end);
                  replaced = true;
                  count++;
                  break;
               }
            }
         }
      } while (replaced && count < 100);

      if (count >= 100) {
         Config.warn("Too many iterations: " + count + ", when resolving: " + str);
         return true;
      } else {
         try {
            IExpressionResolver er = new MacroExpressionResolver(this.mapMacroValues);
            ExpressionParser ep = new ExpressionParser(er);
            IExpression expr = ep.m_82160_(str);
            if (expr.getExpressionType() == ExpressionType.BOOL) {
               IExpressionBool exprBool = (IExpressionBool)expr;
               return exprBool.eval();
            } else if (expr.getExpressionType() == ExpressionType.FLOAT) {
               IExpressionFloat exprFloat = (IExpressionFloat)expr;
               float val = exprFloat.eval();
               return val != 0.0F;
            } else {
               throw new ParseException("Not a boolean or float expression: " + expr.getExpressionType());
            }
         } catch (ParseException var12) {
            Config.warn("Invalid macro expression: " + str);
            Config.warn("Error: " + var12.getMessage());
            return false;
         }
      }
   }
}
