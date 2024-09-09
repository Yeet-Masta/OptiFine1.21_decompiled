package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterBell extends ModelAdapter {
   public ModelAdapterBell() {
      super(BlockEntityType.f_58909_, "bell", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BellModel();
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof BellModel modelBell)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelBell.bellBody : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model model, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58909_, index, () -> new BellRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof BellRenderer)) {
         return null;
      } else if (!(model instanceof BellModel bellModel)) {
         Config.warn("Not a bell model: " + model);
         return null;
      } else {
         return bellModel.updateRenderer(renderer);
      }
   }
}
