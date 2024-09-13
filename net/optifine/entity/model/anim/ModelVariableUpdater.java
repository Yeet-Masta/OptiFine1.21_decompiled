package net.optifine.entity.model.anim;

import net.optifine.Config;
import net.optifine.expr.ExpressionParser;
import net.optifine.expr.IExpression;
import net.optifine.expr.ParseException;

public class ModelVariableUpdater {
   private String modelVariableName;
   private String expressionText;
   private IModelVariable modelVariable;
   private IExpression expression;

   public ModelVariableUpdater(String modelVariableName, String expressionText) {
      this.modelVariableName = modelVariableName;
      this.expressionText = expressionText;
   }

   public boolean initialize(IModelResolver mr) {
      this.modelVariable = mr.getModelVariable(this.modelVariableName);
      if (this.modelVariable == null) {
         Config.warn("Model variable not found: " + this.modelVariableName);
         return false;
      } else {
         try {
            ExpressionParser ep = new ExpressionParser(mr);
            this.expression = ep.m_82160_(this.expressionText);
            if (this.modelVariable.getExpressionType() != this.expression.getExpressionType()) {
               Config.warn("Eypression type not matching variable type: " + this.modelVariableName + " != " + this.expressionText);
               return false;
            } else {
               return true;
            }
         } catch (ParseException var3) {
            Config.warn("Error parsing expression: " + this.expressionText);
            Config.warn(var3.getClass().getName() + ": " + var3.getMessage());
            return false;
         }
      }
   }

   public IModelVariable getModelVariable() {
      return this.modelVariable;
   }

   public void m_252999_() {
      this.modelVariable.setValue(this.expression);
   }

   public String toString() {
      return this.modelVariableName + " = " + this.expressionText;
   }
}
