package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterEnderChest extends ModelAdapter {
   public ModelAdapterEnderChest() {
      super(BlockEntityType.f_58920_, "ender_chest", 0.0F);
   }

   public Model makeModel() {
      return new ChestModel();
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
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

   public String[] getModelRendererNames() {
      return new String[]{"lid", "base", "knob"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58920_, index, () -> {
         return new ChestRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof ChestRenderer)) {
         return null;
      } else if (!(modelBase instanceof ChestModel)) {
         Config.warn("Not a chest model: " + String.valueOf(modelBase));
         return null;
      } else {
         ChestModel chestModel = (ChestModel)modelBase;
         renderer = chestModel.updateRenderer(renderer);
         return renderer;
      }
   }
}
