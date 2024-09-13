package net.optifine.entity.model.anim;

import net.optifine.expr.IExpression;

public class RenderResolverTileEntity implements IRenderResolver {
   @Override
   public IExpression getParameter(String name) {
      RenderEntityParameterBool parBool = RenderEntityParameterBool.m_82160_(name);
      if (parBool != null && parBool.isBlockEntity()) {
         return parBool;
      } else {
         RenderEntityParameterFloat parFloat = RenderEntityParameterFloat.m_82160_(name);
         return parFloat != null && parFloat.isBlockEntity() ? parFloat : null;
      }
   }

   @Override
   public boolean isTileEntity() {
      return true;
   }
}
