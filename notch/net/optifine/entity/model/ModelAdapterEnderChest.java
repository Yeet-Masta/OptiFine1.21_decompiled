package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4247_;
import net.optifine.Config;

public class ModelAdapterEnderChest extends ModelAdapter {
   public ModelAdapterEnderChest() {
      super(C_1992_.f_58920_, "ender_chest", 0.0F);
   }

   public C_3840_ makeModel() {
      return new ChestModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
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

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58920_, index, () -> new C_4247_(dispatcher.getContext()));
      if (!(renderer instanceof C_4247_)) {
         return null;
      } else if (!(modelBase instanceof ChestModel chestModel)) {
         Config.warn("Not a chest model: " + modelBase);
         return null;
      } else {
         return chestModel.updateRenderer(renderer);
      }
   }
}
