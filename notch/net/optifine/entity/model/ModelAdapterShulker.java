package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3866_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4386_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulker extends ModelAdapter {
   public ModelAdapterShulker() {
      super(C_513_.f_20521_, "shulker", 0.0F);
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
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4386_ render = new C_4386_(renderManager.getContext());
      render.g = (C_3866_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
