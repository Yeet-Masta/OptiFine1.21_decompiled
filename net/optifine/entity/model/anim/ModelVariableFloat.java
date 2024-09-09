package net.optifine.entity.model.anim;

import net.optifine.expr.IExpressionFloat;

public class ModelVariableFloat implements IExpressionFloat, IModelVariableFloat, IModelRendererVariable {
   private String name;
   private net.minecraft.client.model.geom.ModelPart modelRenderer;
   private ModelVariableType enumModelVariable;

   public ModelVariableFloat(String name, net.minecraft.client.model.geom.ModelPart modelRenderer, ModelVariableType enumModelVariable) {
      this.name = name;
      this.modelRenderer = modelRenderer;
      this.enumModelVariable = enumModelVariable;
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer() {
      return this.modelRenderer;
   }

   @Override
   public float eval() {
      return this.getValue();
   }

   @Override
   public float getValue() {
      return this.enumModelVariable.getFloat(this.modelRenderer);
   }

   @Override
   public void setValue(float value) {
      this.enumModelVariable.setFloat(this.modelRenderer, value);
   }

   public String toString() {
      return this.name;
   }
}
