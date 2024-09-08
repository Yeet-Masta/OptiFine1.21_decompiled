package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3866_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4252_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox extends ModelAdapter {
   public ModelAdapterShulkerBox() {
      super(C_1992_.f_58939_, "shulker_box", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3866_(bakeModelLayer(C_141656_.f_171180_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3866_ modelShulker)) {
         return null;
      } else if (modelPart.equals("base")) {
         return (C_3889_)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0);
      } else if (modelPart.equals("lid")) {
         return (C_3889_)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1);
      } else {
         return modelPart.equals("head") ? (C_3889_)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"base", "lid", "head"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58939_, index, () -> new C_4252_(dispatcher.getContext()));
      if (!(renderer instanceof C_4252_)) {
         return null;
      } else if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
         Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
         return null;
      } else {
         Reflector.setFieldValue(renderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
         return renderer;
      }
   }
}
