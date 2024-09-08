package net.optifine.entity.model.anim;

import net.minecraft.src.C_3889_;
import net.optifine.expr.IExpressionFloat;

public class ModelVariableFloat implements IExpressionFloat, IModelVariableFloat, IModelRendererVariable {
   private String name;
   private C_3889_ modelRenderer;
   private ModelVariableType enumModelVariable;

   public ModelVariableFloat(String name, C_3889_ modelRenderer, ModelVariableType enumModelVariable) {
      this.name = name;
      this.modelRenderer = modelRenderer;
      this.enumModelVariable = enumModelVariable;
   }

   public C_3889_ getModelRenderer() {
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
