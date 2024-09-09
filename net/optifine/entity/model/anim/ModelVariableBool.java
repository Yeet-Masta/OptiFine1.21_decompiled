package net.optifine.entity.model.anim;

import net.optifine.expr.IExpressionBool;

public class ModelVariableBool implements IExpressionBool, IModelVariableBool, IModelRendererVariable {
   private String name;
   private net.minecraft.client.model.geom.ModelPart modelRenderer;
   private ModelVariableType enumModelVariable;

   public ModelVariableBool(String name, net.minecraft.client.model.geom.ModelPart modelRenderer, ModelVariableType enumModelVariable) {
      this.name = name;
      this.modelRenderer = modelRenderer;
      this.enumModelVariable = enumModelVariable;
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer() {
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
