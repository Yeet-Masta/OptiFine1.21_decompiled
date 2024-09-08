package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_271025_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;

public class ModelAdapterDecoratedPot extends ModelAdapter {
   public ModelAdapterDecoratedPot() {
      super(C_1992_.f_271291_, "decorated_pot", 0.0F);
   }

   public C_3840_ makeModel() {
      return new DecoratedPotModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof DecoratedPotModel modelDecoratedPot)) {
         return null;
      } else if (modelPart.equals("neck")) {
         return modelDecoratedPot.neck;
      } else if (modelPart.equals("front")) {
         return modelDecoratedPot.frontSide;
      } else if (modelPart.equals("back")) {
         return modelDecoratedPot.backSide;
      } else if (modelPart.equals("left")) {
         return modelDecoratedPot.leftSide;
      } else if (modelPart.equals("right")) {
         return modelDecoratedPot.rightSide;
      } else if (modelPart.equals("top")) {
         return modelDecoratedPot.top;
      } else {
         return modelPart.equals("bottom") ? modelDecoratedPot.bottom : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"neck", "front", "back", "left", "right", "top", "bottom"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ model, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_271291_, index, () -> new C_271025_(dispatcher.getContext()));
      if (!(renderer instanceof C_271025_)) {
         return null;
      } else if (!(model instanceof DecoratedPotModel decoratedPotModel)) {
         Config.warn("Not a decorated pot model: " + model);
         return null;
      } else {
         return decoratedPotModel.updateRenderer(renderer);
      }
   }
}
