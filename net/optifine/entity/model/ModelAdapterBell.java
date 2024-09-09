package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterBell extends ModelAdapter {
   public ModelAdapterBell() {
      super(BlockEntityType.f_58909_, "bell", 0.0F);
   }

   public Model makeModel() {
      return new BellModel();
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BellModel modelBell)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelBell.bellBody : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58909_, index, () -> {
         return new BellRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof BellRenderer)) {
         return null;
      } else if (!(model instanceof BellModel)) {
         Config.warn("Not a bell model: " + String.valueOf(model));
         return null;
      } else {
         BellModel bellModel = (BellModel)model;
         renderer = bellModel.updateRenderer(renderer);
         return renderer;
      }
   }
}
