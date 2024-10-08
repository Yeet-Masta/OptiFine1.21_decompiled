package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterChestLarge extends ModelAdapter {
   public ModelAdapterChestLarge() {
      super(BlockEntityType.f_58918_, "chest_large", 0.0F);
   }

   public Model makeModel() {
      return new ChestLargeModel();
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ChestLargeModel modelChest)) {
         return null;
      } else if (modelPart.equals("lid_left")) {
         return modelChest.lid_left;
      } else if (modelPart.equals("base_left")) {
         return modelChest.base_left;
      } else if (modelPart.equals("knob_left")) {
         return modelChest.knob_left;
      } else if (modelPart.equals("lid_right")) {
         return modelChest.lid_right;
      } else if (modelPart.equals("base_right")) {
         return modelChest.base_right;
      } else {
         return modelPart.equals("knob_right") ? modelChest.knob_right : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"lid_left", "base_left", "knob_left", "lid_right", "base_right", "knob_right"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58918_, index, () -> {
         return new ChestRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof ChestRenderer)) {
         return null;
      } else if (!(modelBase instanceof ChestLargeModel)) {
         Config.warn("Not a large chest model: " + String.valueOf(modelBase));
         return null;
      } else {
         ChestLargeModel chestModel = (ChestLargeModel)modelBase;
         renderer = chestModel.updateRenderer(renderer);
         return renderer;
      }
   }
}
