package net.optifine.entity.model.anim;

import net.minecraft.src.C_3889_;
import net.optifine.expr.IExpressionBool;

public class ModelVariableBool implements IExpressionBool, IModelVariableBool, IModelRendererVariable {
   private String name;
   private C_3889_ modelRenderer;
   private ModelVariableType enumModelVariable;

   public ModelVariableBool(String name, C_3889_ modelRenderer, ModelVariableType enumModelVariable) {
      this.name = name;
      this.modelRenderer = modelRenderer;
      this.enumModelVariable = enumModelVariable;
   }

   public C_3889_ getModelRenderer() {
      return this.modelRenderer;
   }

   @Override
   public boolean eval() {
      return this.getValue();
   }

   @Override
   public boolean getValue() {
      return this.enumModelVariable.getBool(this.modelRenderer);
   }

   @Override
   public void setValue(boolean value) {
      this.enumModelVariable.setBool(this.modelRenderer, value);
   }

   public String toString() {
      return this.name;
   }
}
