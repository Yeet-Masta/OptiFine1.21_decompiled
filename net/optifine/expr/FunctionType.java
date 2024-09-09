package net.optifine.expr;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.optifine.Config;
import net.optifine.shaders.uniform.Smoother;
import net.optifine.util.FrameEvent;
import net.optifine.util.MathUtils;

public enum FunctionType {
   PLUS(10, ExpressionType.FLOAT, "+", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   MINUS(10, ExpressionType.FLOAT, "-", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   MUL(11, ExpressionType.FLOAT, "*", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   DIV(11, ExpressionType.FLOAT, "/", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   MOD(11, ExpressionType.FLOAT, "%", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   NEG(12, ExpressionType.FLOAT, "neg", new ExpressionType[]{ExpressionType.FLOAT}),
   // $FF: renamed from: PI net.optifine.expr.FunctionType
   field_20(ExpressionType.FLOAT, "pi", new ExpressionType[0]),
   SIN(ExpressionType.FLOAT, "sin", new ExpressionType[]{ExpressionType.FLOAT}),
   COS(ExpressionType.FLOAT, "cos", new ExpressionType[]{ExpressionType.FLOAT}),
   ASIN(ExpressionType.FLOAT, "asin", new ExpressionType[]{ExpressionType.FLOAT}),
   ACOS(ExpressionType.FLOAT, "acos", new ExpressionType[]{ExpressionType.FLOAT}),
   TAN(ExpressionType.FLOAT, "tan", new ExpressionType[]{ExpressionType.FLOAT}),
   ATAN(ExpressionType.FLOAT, "atan", new ExpressionType[]{ExpressionType.FLOAT}),
   ATAN2(ExpressionType.FLOAT, "atan2", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   TORAD(ExpressionType.FLOAT, "torad", new ExpressionType[]{ExpressionType.FLOAT}),
   TODEG(ExpressionType.FLOAT, "todeg", new ExpressionType[]{ExpressionType.FLOAT}),
   MIN(ExpressionType.FLOAT, "min", (new ParametersVariable()).first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
   MAX(ExpressionType.FLOAT, "max", (new ParametersVariable()).first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT)),
   CLAMP(ExpressionType.FLOAT, "clamp", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   ABS(ExpressionType.FLOAT, "abs", new ExpressionType[]{ExpressionType.FLOAT}),
   FLOOR(ExpressionType.FLOAT, "floor", new ExpressionType[]{ExpressionType.FLOAT}),
   CEIL(ExpressionType.FLOAT, "ceil", new ExpressionType[]{ExpressionType.FLOAT}),
   EXP(ExpressionType.FLOAT, "exp", new ExpressionType[]{ExpressionType.FLOAT}),
   FRAC(ExpressionType.FLOAT, "frac", new ExpressionType[]{ExpressionType.FLOAT}),
   LOG(ExpressionType.FLOAT, "log", new ExpressionType[]{ExpressionType.FLOAT}),
   POW(ExpressionType.FLOAT, "pow", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   RANDOM(ExpressionType.FLOAT, "random", (new ParametersVariable()).repeat(ExpressionType.FLOAT).maxCount(1)),
   ROUND(ExpressionType.FLOAT, "round", new ExpressionType[]{ExpressionType.FLOAT}),
   SIGNUM(ExpressionType.FLOAT, "signum", new ExpressionType[]{ExpressionType.FLOAT}),
   SQRT(ExpressionType.FLOAT, "sqrt", new ExpressionType[]{ExpressionType.FLOAT}),
   FMOD(ExpressionType.FLOAT, "fmod", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   LERP(ExpressionType.FLOAT, "lerp", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   TIME(ExpressionType.FLOAT, "time", new ExpressionType[0]),
   DAY_TIME(ExpressionType.FLOAT, "day_time", new ExpressionType[0]),
   DAY_COUNT(ExpressionType.FLOAT, "day_count", new ExpressionType[0]),
   PRINT(ExpressionType.FLOAT, "print", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   PRINTB(ExpressionType.BOOL, "printb", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.BOOL}),
   // $FF: renamed from: IF net.optifine.expr.FunctionType
   field_21(ExpressionType.FLOAT, "if", (new ParametersVariable()).first(ExpressionType.BOOL, ExpressionType.FLOAT).repeat(ExpressionType.BOOL, ExpressionType.FLOAT).last(ExpressionType.FLOAT)),
   NOT(12, ExpressionType.BOOL, "!", new ExpressionType[]{ExpressionType.BOOL}),
   AND(3, ExpressionType.BOOL, "&&", new ExpressionType[]{ExpressionType.BOOL, ExpressionType.BOOL}),
   // $FF: renamed from: OR net.optifine.expr.FunctionType
   field_22(2, ExpressionType.BOOL, "||", new ExpressionType[]{ExpressionType.BOOL, ExpressionType.BOOL}),
   GREATER(8, ExpressionType.BOOL, ">", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   GREATER_OR_EQUAL(8, ExpressionType.BOOL, ">=", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   SMALLER(8, ExpressionType.BOOL, "<", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   SMALLER_OR_EQUAL(8, ExpressionType.BOOL, "<=", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   EQUAL(7, ExpressionType.BOOL, "==", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   NOT_EQUAL(7, ExpressionType.BOOL, "!=", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   BETWEEN(7, ExpressionType.BOOL, "between", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   EQUALS(7, ExpressionType.BOOL, "equals", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   // $FF: renamed from: IN net.optifine.expr.FunctionType
   field_23(ExpressionType.BOOL, "in", (new ParametersVariable()).first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).last(ExpressionType.FLOAT)),
   SMOOTH(ExpressionType.FLOAT, "smooth", (new ParametersVariable()).first(ExpressionType.FLOAT).repeat(ExpressionType.FLOAT).maxCount(4)),
   TRUE(ExpressionType.BOOL, "true", new ExpressionType[0]),
   FALSE(ExpressionType.BOOL, "false", new ExpressionType[0]),
   IFB(ExpressionType.BOOL, "ifb", (new ParametersVariable()).first(ExpressionType.BOOL, ExpressionType.BOOL).repeat(ExpressionType.BOOL, ExpressionType.BOOL).last(ExpressionType.BOOL)),
   VEC2(ExpressionType.FLOAT_ARRAY, "vec2", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT}),
   VEC3(ExpressionType.FLOAT_ARRAY, "vec3", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT}),
   VEC4(ExpressionType.FLOAT_ARRAY, "vec4", new ExpressionType[]{ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT, ExpressionType.FLOAT});

   private int precedence;
   private ExpressionType expressionType;
   private String name;
   private IParameters parameters;
   public static FunctionType[] VALUES = values();
   private static final Map mapSmooth = new HashMap();

   private FunctionType(ExpressionType expressionType, String name, ExpressionType... parameterTypes) {
      this(0, expressionType, name, (ExpressionType[])parameterTypes);
   }

   private FunctionType(int precedence, ExpressionType expressionType, String name, ExpressionType... parameterTypes) {
      this(precedence, expressionType, name, (IParameters)(new Parameters(parameterTypes)));
   }

   private FunctionType(ExpressionType expressionType, String name, IParameters parameters) {
      this(0, expressionType, name, (IParameters)parameters);
   }

   private FunctionType(int precedence, ExpressionType expressionType, String name, IParameters parameters) {
      this.precedence = precedence;
      this.expressionType = expressionType;
      this.name = name;
      this.parameters = parameters;
   }

   public String getName() {
      return this.name;
   }

   public int getPrecedence() {
      return this.precedence;
   }

   public ExpressionType getExpressionType() {
      return this.expressionType;
   }

   public IParameters getParameters() {
      return this.parameters;
   }

   public int getParameterCount(IExpression[] arguments) {
      return this.parameters.getParameterTypes(arguments).length;
   }

   public ExpressionType[] getParameterTypes(IExpression[] arguments) {
      return this.parameters.getParameterTypes(arguments);
   }

   public float evalFloat(IExpression[] args) {
      Minecraft mc = Minecraft.m_91087_();
      Level world = mc.f_91073_;
      float seed;
      int i;
      switch (this.ordinal()) {
         case 0:
            return evalFloat(args, 0) + evalFloat(args, 1);
         case 1:
            return evalFloat(args, 0) - evalFloat(args, 1);
         case 2:
            return evalFloat(args, 0) * evalFloat(args, 1);
         case 3:
            return evalFloat(args, 0) / evalFloat(args, 1);
         case 4:
            float modX = evalFloat(args, 0);
            float modY = evalFloat(args, 1);
            return modX - modY * (float)((int)(modX / modY));
         case 5:
            return -evalFloat(args, 0);
         case 6:
            return 3.1415927F;
         case 7:
            return Mth.m_14031_(evalFloat(args, 0));
         case 8:
            return Mth.m_14089_(evalFloat(args, 0));
         case 9:
            return MathUtils.asin(evalFloat(args, 0));
         case 10:
            return MathUtils.acos(evalFloat(args, 0));
         case 11:
            return (float)Math.tan((double)evalFloat(args, 0));
         case 12:
            return (float)Math.atan((double)evalFloat(args, 0));
         case 13:
            return (float)Mth.m_14136_((double)evalFloat(args, 0), (double)evalFloat(args, 1));
         case 14:
            return MathUtils.toRad(evalFloat(args, 0));
         case 15:
            return MathUtils.toDeg(evalFloat(args, 0));
         case 16:
            return this.getMin(args);
         case 17:
            return this.getMax(args);
         case 18:
            return Mth.m_14036_(evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2));
         case 19:
            return Mth.m_14154_(evalFloat(args, 0));
         case 20:
            return (float)Mth.m_14143_(evalFloat(args, 0));
         case 21:
            return (float)Mth.m_14167_(evalFloat(args, 0));
         case 22:
            return (float)Math.exp((double)evalFloat(args, 0));
         case 23:
            return Mth.m_14187_(evalFloat(args, 0));
         case 24:
            return (float)Math.log((double)evalFloat(args, 0));
         case 25:
            return (float)Math.pow((double)evalFloat(args, 0), (double)evalFloat(args, 1));
         case 26:
            if (args.length > 0) {
               seed = evalFloat(args, 0);
               int seedInt = Float.floatToIntBits(seed);
               int randInt = Config.intHash(seedInt);
               return (float)Math.abs(randInt) / 2.14748365E9F;
            }

            return (float)Math.random();
         case 27:
            return (float)Math.round(evalFloat(args, 0));
         case 28:
            return Math.signum(evalFloat(args, 0));
         case 29:
            return Mth.m_14116_(evalFloat(args, 0));
         case 30:
            seed = evalFloat(args, 0);
            float fmodY = evalFloat(args, 1);
            return seed - fmodY * (float)Mth.m_14143_(seed / fmodY);
         case 31:
            float k = evalFloat(args, 0);
            float x = evalFloat(args, 1);
            float y = evalFloat(args, 2);
            return Mth.m_14179_(k, x, y);
         case 32:
            if (world == null) {
               return 0.0F;
            }

            return (float)(world.m_46467_() % 720720L) + GameRenderer.getRenderPartialTicks();
         case 33:
            if (world == null) {
               return 0.0F;
            }

            return (float)(world.m_46468_() % 24000L) + GameRenderer.getRenderPartialTicks();
         case 34:
            if (world == null) {
               return 0.0F;
            }

            return (float)(world.m_46468_() / 24000L);
         case 35:
            int printId = (int)evalFloat(args, 0);
            int printFrames = (int)evalFloat(args, 1);
            float printVal = evalFloat(args, 2);
            if (FrameEvent.isActive("CEM-PRINT-" + printId, printFrames)) {
               Config.dbg("CEM print(" + printId + ") = " + printVal);
            }

            return printVal;
         case 36:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 49:
         default:
            Config.warn("Unknown function type: " + String.valueOf(this));
            return 0.0F;
         case 37:
            int countChecks = (args.length - 1) / 2;

            for(i = 0; i < countChecks; ++i) {
               int index = i * 2;
               if (evalBool(args, index)) {
                  return evalFloat(args, index + 1);
               }
            }

            return evalFloat(args, countChecks * 2);
         case 50:
            i = (int)evalFloat(args, 0);
            float valRaw = evalFloat(args, 1);
            float valFadeUp = args.length > 2 ? evalFloat(args, 2) : 1.0F;
            float valFadeDown = args.length > 3 ? evalFloat(args, 3) : valFadeUp;
            float valSmooth = Smoother.getSmoothValue(i, valRaw, valFadeUp, valFadeDown);
            return valSmooth;
      }
   }

   private float getMin(IExpression[] exprs) {
      if (exprs.length == 2) {
         return Math.min(evalFloat(exprs, 0), evalFloat(exprs, 1));
      } else {
         float valMin = evalFloat(exprs, 0);

         for(int i = 1; i < exprs.length; ++i) {
            float valExpr = evalFloat(exprs, i);
            if (valExpr < valMin) {
               valMin = valExpr;
            }
         }

         return valMin;
      }
   }

   private float getMax(IExpression[] exprs) {
      if (exprs.length == 2) {
         return Math.max(evalFloat(exprs, 0), evalFloat(exprs, 1));
      } else {
         float valMax = evalFloat(exprs, 0);

         for(int i = 1; i < exprs.length; ++i) {
            float valExpr = evalFloat(exprs, i);
            if (valExpr > valMax) {
               valMax = valExpr;
            }
         }

         return valMax;
      }
   }

   private static float evalFloat(IExpression[] exprs, int index) {
      IExpressionFloat ef = (IExpressionFloat)exprs[index];
      float val = ef.eval();
      return val;
   }

   public boolean evalBool(IExpression[] args) {
      int countChecks;
      int i;
      int index;
      switch (this.ordinal()) {
         case 36:
            i = (int)evalFloat(args, 0);
            index = (int)evalFloat(args, 1);
            boolean printVal = evalBool(args, 2);
            if (FrameEvent.isActive("CEM-PRINTB-" + i, index)) {
               Config.dbg("CEM printb(" + i + ") = " + printVal);
            }

            return printVal;
         case 37:
         case 50:
         default:
            Config.warn("Unknown function type: " + String.valueOf(this));
            return false;
         case 38:
            return !evalBool(args, 0);
         case 39:
            return evalBool(args, 0) && evalBool(args, 1);
         case 40:
            return evalBool(args, 0) || evalBool(args, 1);
         case 41:
            return evalFloat(args, 0) > evalFloat(args, 1);
         case 42:
            return evalFloat(args, 0) >= evalFloat(args, 1);
         case 43:
            return evalFloat(args, 0) < evalFloat(args, 1);
         case 44:
            return evalFloat(args, 0) <= evalFloat(args, 1);
         case 45:
            return evalFloat(args, 0) == evalFloat(args, 1);
         case 46:
            return evalFloat(args, 0) != evalFloat(args, 1);
         case 47:
            float val = evalFloat(args, 0);
            return val >= evalFloat(args, 1) && val <= evalFloat(args, 2);
         case 48:
            float diff = evalFloat(args, 0) - evalFloat(args, 1);
            float delta = evalFloat(args, 2);
            return Math.abs(diff) <= delta;
         case 49:
            float valIn = evalFloat(args, 0);

            for(countChecks = 1; countChecks < args.length; ++countChecks) {
               float valCheck = evalFloat(args, countChecks);
               if (valIn == valCheck) {
                  return true;
               }
            }

            return false;
         case 51:
            return true;
         case 52:
            return false;
         case 53:
            countChecks = (args.length - 1) / 2;

            for(i = 0; i < countChecks; ++i) {
               index = i * 2;
               if (evalBool(args, index)) {
                  return evalBool(args, index + 1);
               }
            }

            return evalBool(args, countChecks * 2);
      }
   }

   private static boolean evalBool(IExpression[] exprs, int index) {
      IExpressionBool eb = (IExpressionBool)exprs[index];
      boolean val = eb.eval();
      return val;
   }

   public float[] evalFloatArray(IExpression[] args) {
      switch (this.ordinal()) {
         case 54:
            return new float[]{evalFloat(args, 0), evalFloat(args, 1)};
         case 55:
            return new float[]{evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2)};
         case 56:
            return new float[]{evalFloat(args, 0), evalFloat(args, 1), evalFloat(args, 2), evalFloat(args, 3)};
         default:
            Config.warn("Unknown function type: " + String.valueOf(this));
            return null;
      }
   }

   public static FunctionType parse(String str) {
      for(int i = 0; i < VALUES.length; ++i) {
         FunctionType ef = VALUES[i];
         if (ef.getName().equals(str)) {
            return ef;
         }
      }

      return null;
   }

   // $FF: synthetic method
   private static FunctionType[] $values() {
      return new FunctionType[]{PLUS, MINUS, MUL, DIV, MOD, NEG, field_20, SIN, COS, ASIN, ACOS, TAN, ATAN, ATAN2, TORAD, TODEG, MIN, MAX, CLAMP, ABS, FLOOR, CEIL, EXP, FRAC, LOG, POW, RANDOM, ROUND, SIGNUM, SQRT, FMOD, LERP, TIME, DAY_TIME, DAY_COUNT, PRINT, PRINTB, field_21, NOT, AND, field_22, GREATER, GREATER_OR_EQUAL, SMALLER, SMALLER_OR_EQUAL, EQUAL, NOT_EQUAL, BETWEEN, EQUALS, field_23, SMOOTH, TRUE, FALSE, IFB, VEC2, VEC3, VEC4};
   }
}
