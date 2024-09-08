package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_213398_;
import net.minecraft.src.C_213428_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterTadpole extends ModelAdapter {
   public ModelAdapterTadpole() {
      super(C_513_.f_217013_, "tadpole", 0.14F);
   }

   public C_3840_ makeModel() {
      return new C_213398_(bakeModelLayer(C_141656_.f_233549_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_213398_ modelTadpole)) {
         return null;
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 0);
      } else {
         return modelPart.equals("tail") ? (C_3889_)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 1) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "tail"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_213428_ render = new C_213428_(renderManager.getContext());
      render.g = (C_213398_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
