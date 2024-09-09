package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.DecoratedPotRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterDecoratedPot extends ModelAdapter {
   public ModelAdapterDecoratedPot() {
      super(BlockEntityType.f_271291_, "decorated_pot", 0.0F);
   }

   public Model makeModel() {
      return new DecoratedPotModel();
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
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

   public String[] getModelRendererNames() {
      return new String[]{"neck", "front", "back", "left", "right", "top", "bottom"};
   }

   public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_271291_, index, () -> {
         return new DecoratedPotRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof DecoratedPotRenderer)) {
         return null;
      } else if (!(model instanceof DecoratedPotModel)) {
         Config.warn("Not a decorated pot model: " + String.valueOf(model));
         return null;
      } else {
         DecoratedPotModel decoratedPotModel = (DecoratedPotModel)model;
         renderer = decoratedPotModel.updateRenderer(renderer);
         return renderer;
      }
   }
}
