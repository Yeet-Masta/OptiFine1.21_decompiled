package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3869_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4413_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterWitherSkull extends ModelAdapter {
   public ModelAdapterWitherSkull() {
      super(C_513_.f_20498_, "wither_skull", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3869_(bakeModelLayer(C_141656_.f_171220_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3869_ modelSkeletonHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 1);
      } else {
         return modelPart.equals("root") ? (C_3889_)Reflector.ModelSkull_renderers.getValue(modelSkeletonHead, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "root"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4413_ render = new C_4413_(renderManager.getContext());
      if (!Reflector.RenderWitherSkull_model.exists()) {
         Config.warn("Field not found: RenderWitherSkull_model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderWitherSkull_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
