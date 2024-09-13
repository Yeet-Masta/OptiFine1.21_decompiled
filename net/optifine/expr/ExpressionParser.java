package net.optifine.expr;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.optifine.Config;

public class ExpressionParser {
   private IExpressionResolver expressionResolver;

   public ExpressionParser(IExpressionResolver expressionResolver) {
      this.expressionResolver = expressionResolver;
   }

   public IExpressionFloat parseFloat(String str) throws ParseException {
      IExpression expr = this.m_82160_(str);
      if (!(expr instanceof IExpressionFloat)) {
         throw new ParseException("Not a float expression: " + expr.getExpressionType());
      } else {
         return (IExpressionFloat)expr;
      }
   }

   public IExpressionBool parseBool(String str) throws ParseException {
      IExpression expr = this.m_82160_(str);
      if (!(expr instanceof IExpressionBool)) {
         throw new ParseException("Not a boolean expression: " + expr.getExpressionType());
      } else {
         return (IExpressionBool)expr;
      }
   }

   public IExpression m_82160_(String str) throws ParseException {
      try {
         Token[] tokens = TokenParser.m_82160_(str);
         if (tokens == null) {
            return null;
         } else {
            Deque<Token> deque = new ArrayDeque(Arrays.asList(tokens));
            return this.parseInfix(deque);
         }
      } catch (IOException var4) {
         throw new ParseException(var4.getMessage(), var4);
      }
   }

   private IExpression parseInfix(Deque<Token> deque) throws ParseException {
      if (deque.isEmpty()) {
         return null;
      } else {
         List<IExpression> listExpr = new LinkedList();
         List<Token> listOperTokens = new LinkedList();
         IExpression expr = this.parseExpression(deque);
         checkNull(expr, "Missing expression");
         listExpr.add(expr);

         while (true) {
            Token tokenOper = (Token)deque.poll();
            if (tokenOper == null) {
               return this.makeInfix(listExpr, listOperTokens);
            }

            if (tokenOper.getType() != TokenType.OPERATOR) {
               throw new ParseException("Invalid operator: " + tokenOper);
            }

            IExpression expr2 = this.parseExpression(deque);
            checkNull(expr2, "Missing expression");
            listOperTokens.add(tokenOper);
            listExpr.add(expr2);
         }
      }
   }

   private IExpression makeInfix(List<IExpression> listExpr, List<Token> listOper) throws ParseException {
      List<FunctionType> listFunc = new LinkedList();

      for (Token token : listOper) {
         FunctionType type = FunctionType.m_82160_(token.getText());
         checkNull(type, "Invalid operator: " + token);
         listFunc.add(type);
      }

      return this.makeInfixFunc(listExpr, listFunc);
   }

   private IExpression makeInfixFunc(List<IExpression> listExpr, List<FunctionType> listFunc) throws ParseException {
      if (listExpr.size() != listFunc.size() + 1) {
         throw new ParseException("Invalid infix expression, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
      } else if (listExpr.size() == 1) {
         return (IExpression)listExpr.get(0);
      } else {
         int minPrecedence = Integer.MAX_VALUE;
         int maxPrecedence = Integer.MIN_VALUE;

         for (FunctionType type : listFunc) {
            minPrecedence = Math.min(type.getPrecedence(), minPrecedence);
            maxPrecedence = Math.max(type.getPrecedence(), maxPrecedence);
         }

         if (maxPrecedence >= minPrecedence && maxPrecedence - minPrecedence <= 10) {
            for (int i = maxPrecedence; i >= minPrecedence; i--) {
               this.mergeOperators(listExpr, listFunc, i);
            }

            if (listExpr.size() == 1 && listFunc.size() == 0) {
               return (IExpression)listExpr.get(0);
            } else {
               throw new ParseException("Error merging operators, expressions: " + listExpr.size() + ", operators: " + listFunc.size());
            }
         } else {
            throw new ParseException("Invalid infix precedence, min: " + minPrecedence + ", max: " + maxPrecedence);
         }
      }
   }

   private void mergeOperators(List<IExpression> listExpr, List<FunctionType> listFuncs, int precedence) throws ParseException {
      for (int i = 0; i < listFuncs.size(); i++) {
         FunctionType type = (FunctionType)listFuncs.get(i);
         if (type.getPrecedence() == precedence) {
            listFuncs.remove(i);
            IExpression expr1 = (IExpression)listExpr.remove(i);
            IExpression expr2 = (IExpression)listExpr.remove(i);
            IExpression exprOper = makeFunction(type, new IExpression[]{expr1, expr2});
            listExpr.add(i, exprOper);
            i--;
         }
      }
   }

   private IExpression parseExpression(Deque<Token> deque) throws ParseException {
      Token token = (Token)deque.poll();
      checkNull(token, "Missing expression");
      switch (<unrepresentable>.$SwitchMap$net$optifine$expr$TokenType[token.getType().ordinal()]) {
         case 1:
            return makeConstantFloat(token);
         case 2:
            FunctionType type = this.getFunctionType(token, deque);
            if (type != null) {
               return this.makeFunction(type, deque);
            }

            return this.makeVariable(token);
         case 3:
            return this.makeBracketed(token, deque);
         case 4:
            FunctionType operType = FunctionType.m_82160_(token.getText());
            checkNull(operType, "Invalid operator: " + token);
            if (operType == FunctionType.PLUS) {
               return this.parseExpression(deque);
            } else if (operType == FunctionType.MINUS) {
               IExpression exprNeg = this.parseExpression(deque);
               return makeFunction(FunctionType.NEG, new IExpression[]{exprNeg});
            } else if (operType == FunctionType.NOT) {
               IExpression exprNot = this.parseExpression(deque);
               return makeFunction(FunctionType.NOT, new IExpression[]{exprNot});
            }
         default:
            throw new ParseException("Invalid expression: " + token);
      }
   }

   private static IExpression makeConstantFloat(Token token) throws ParseException {
      float val = Config.parseFloat(token.getText(), Float.NaN);
      if (val == Float.NaN) {
         throw new ParseException("Invalid float value: " + token);
      } else {
         return new ConstantFloat(val);
      }
   }

   private FunctionType getFunctionType(Token token, Deque<Token> deque) throws ParseException {
      Token tokenNext = (Token)deque.peek();
      if (tokenNext != null && tokenNext.getType() == TokenType.BRACKET_OPEN) {
         FunctionType type = FunctionType.m_82160_(token.getText());
         checkNull(type, "Unknown function: " + token);
         return type;
      } else {
         FunctionType type = FunctionType.m_82160_(token.getText());
         if (type == null) {
            return null;
         } else if (type.getParameterCount(new IExpression[0]) > 0) {
            throw new ParseException("Missing arguments: " + type);
         } else {
            return type;
         }
      }
   }

   private IExpression makeFunction(FunctionType type, Deque<Token> deque) throws ParseException {
      if (type.getParameterCount(new IExpression[0]) == 0) {
         Token tokenNext = (Token)deque.peek();
         if (tokenNext == null || tokenNext.getType() != TokenType.BRACKET_OPEN) {
            return makeFunction(type, new IExpression[0]);
         }
      }

      Token tokenOpen = (Token)deque.poll();
      Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
      IExpression[] exprs = this.parseExpressions(dequeBracketed);
      return makeFunction(type, exprs);
   }

   private IExpression[] parseExpressions(Deque<Token> deque) throws ParseException {
      List<IExpression> list = new ArrayList();

      while (true) {
         Deque<Token> dequeArg = getGroup(deque, TokenType.COMMA, false);
         IExpression expr = this.parseInfix(dequeArg);
         if (expr == null) {
            return (IExpression[])list.toArray(new IExpression[list.size()]);
         }

         list.add(expr);
      }
   }

   private static IExpression makeFunction(FunctionType type, IExpression[] args) throws ParseException {
      ExpressionType[] funcParamTypes = type.getParameterTypes(args);
      if (args.length != funcParamTypes.length) {
         throw new ParseException(
            "Invalid number of arguments, function: \"" + type.getName() + "\", count arguments: " + args.length + ", should be: " + funcParamTypes.length
         );
      } else {
         for (int i = 0; i < args.length; i++) {
            IExpression arg = args[i];
            ExpressionType argType = arg.getExpressionType();
            ExpressionType funcParamType = funcParamTypes[i];
            if (argType != funcParamType) {
               throw new ParseException(
                  "Invalid argument type, function: \"" + type.getName() + "\", index: " + i + ", type: " + argType + ", should be: " + funcParamType
               );
            }
         }

         if (type.getExpressionType() == ExpressionType.FLOAT) {
            return new FunctionFloat(type, args);
         } else if (type.getExpressionType() == ExpressionType.BOOL) {
            return new FunctionBool(type, args);
         } else if (type.getExpressionType() == ExpressionType.FLOAT_ARRAY) {
            return new FunctionFloatArray(type, args);
         } else {
            throw new ParseException("Unknown function type: " + type.getExpressionType() + ", function: " + type.getName());
         }
      }
   }

   private IExpression makeVariable(Token token) throws ParseException {
      if (this.expressionResolver == null) {
         throw new ParseException("Model variable not found: " + token);
      } else {
         IExpression expr = this.expressionResolver.getExpression(token.getText());
         if (expr == null) {
            throw new ParseException("Model variable not found: " + token);
         } else {
            return expr;
         }
      }
   }

   private IExpression makeBracketed(Token token, Deque<Token> deque) throws ParseException {
      Deque<Token> dequeBracketed = getGroup(deque, TokenType.BRACKET_CLOSE, true);
      return this.parseInfix(dequeBracketed);
   }

   private static Deque<Token> getGroup(Deque<Token> deque, TokenType tokenTypeEnd, boolean tokenEndRequired) throws ParseException {
      Deque<Token> dequeGroup = new ArrayDeque();
      int level = 0;
      Iterator it = deque.iterator();

      while (it.hasNext()) {
         Token token = (Token)it.next();
         it.remove();
         if (level == 0 && token.getType() == tokenTypeEnd) {
            return dequeGroup;
         }

         dequeGroup.add(token);
         if (token.getType() == TokenType.BRACKET_OPEN) {
            level++;
         }

         if (token.getType() == TokenType.BRACKET_CLOSE) {
            level--;
         }
      }

      if (tokenEndRequired) {
         throw new ParseException("Missing end token: " + tokenTypeEnd);
      } else {
         return dequeGroup;
      }
   }

   private static void checkNull(Object obj, String message) throws ParseException {
      if (obj == null) {
         throw new ParseException(message);
      }
   }
}
