package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4247_;
import net.optifine.Config;

public class ModelAdapterTrappedChestLarge extends ModelAdapter {
   public ModelAdapterTrappedChestLarge() {
      super(C_1992_.f_58919_, "trapped_chest_large", 0.0F);
   }

   public C_3840_ makeModel() {
      return new ChestLargeModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
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

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"lid_left", "base_left", "knob_left", "lid_right", "base_right", "knob_right"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58919_, index, () -> new C_4247_(dispatcher.getContext()));
      if (!(renderer instanceof C_4247_)) {
         return null;
      } else if (!(modelBase instanceof ChestLargeModel chestModel)) {
         Config.warn("Not a large chest model: " + modelBase);
         return null;
      } else {
         return chestModel.updateRenderer(renderer);
      }
   }
}
