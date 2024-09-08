package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4242_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;

public class ModelAdapterBell extends ModelAdapter {
   public ModelAdapterBell() {
      super(C_1992_.f_58909_, "bell", 0.0F);
   }

   public C_3840_ makeModel() {
      return new BellModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
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

   public IEntityRenderer makeEntityRender(C_3840_ model, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58909_, index, () -> new C_4242_(dispatcher.getContext()));
      if (!(renderer instanceof C_4242_)) {
         return null;
      } else if (!(model instanceof BellModel bellModel)) {
         Config.warn("Not a bell model: " + model);
         return null;
      } else {
         return bellModel.updateRenderer(renderer);
      }
   }
}
