package net.optifine.entity.model.anim;

import net.minecraft.src.C_3889_;
import net.optifine.expr.IExpressionResolver;

public interface IModelResolver extends IExpressionResolver {
   C_3889_ getModelRenderer(String var1);

   IModelVariable getModelVariable(String var1);
}
