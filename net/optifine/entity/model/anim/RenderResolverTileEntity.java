package net.optifine.entity.model.anim;

import net.optifine.expr.IExpression;

public class RenderResolverTileEntity implements IRenderResolver {
   public IExpression getParameter(String name) {
      RenderEntityParameterBool parBool = RenderEntityParameterBool.parse(name);
      if (parBool != null && parBool.isBlockEntity()) {
         return parBool;
      } else {
         RenderEntityParameterFloat parFloat = RenderEntityParameterFloat.parse(name);
         return parFloat != null && parFloat.isBlockEntity() ? parFloat : null;
      }
   }

   public boolean isTileEntity() {
      return true;
   }
}
