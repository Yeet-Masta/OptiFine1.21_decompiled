package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterTrappedChest extends ModelAdapter {
   public ModelAdapterTrappedChest() {
      super(BlockEntityType.f_58919_, "trapped_chest", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ChestModel();
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ChestModel modelChest)) {
         return null;
      } else if (modelPart.equals("lid")) {
         return modelChest.lid;
      } else if (modelPart.equals("base")) {
         return modelChest.base;
      } else {
         return modelPart.equals("knob") ? modelChest.knob : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"lid", "base", "knob"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58919_, index, () -> new ChestRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof ChestRenderer)) {
         return null;
      } else if (!(modelBase instanceof ChestModel chestModel)) {
         Config.warn("Not a chest model: " + modelBase);
         return null;
      } else {
         return chestModel.updateRenderer(renderer);
      }
   }
}
