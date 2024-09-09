package net.optifine.entity.model.anim;

import net.optifine.expr.IExpressionResolver;

public interface IModelResolver extends IExpressionResolver {
   net.minecraft.client.model.geom.ModelPart getModelRenderer(String var1);

   IModelVariable getModelVariable(String var1);
}
