package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4248_;
import net.optifine.Config;

public class ModelAdapterConduit extends ModelAdapter {
   public ModelAdapterConduit() {
      super(C_1992_.f_58941_, "conduit", 0.0F);
   }

   public C_3840_ makeModel() {
      return new ConduitModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof ConduitModel modelConduit)) {
         return null;
      } else if (modelPart.equals("eye")) {
         return modelConduit.eye;
      } else if (modelPart.equals("wind")) {
         return modelConduit.wind;
      } else if (modelPart.equals("base")) {
         return modelConduit.base;
      } else {
         return modelPart.equals("cage") ? modelConduit.cage : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"eye", "wind", "base", "cage"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58941_, index, () -> new C_4248_(dispatcher.getContext()));
      if (!(renderer instanceof C_4248_)) {
         return null;
      } else if (!(modelBase instanceof ConduitModel conduitModel)) {
         Config.warn("Not a conduit model: " + modelBase);
         return null;
      } else {
         return conduitModel.updateRenderer(renderer);
      }
   }
}
