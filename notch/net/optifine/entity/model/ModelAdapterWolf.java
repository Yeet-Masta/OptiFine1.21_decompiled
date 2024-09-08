package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3885_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4414_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterWolf extends ModelAdapter {
   public ModelAdapterWolf() {
      super(C_513_.f_20499_, "wolf", 0.5F);
   }

   protected ModelAdapterWolf(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3885_(bakeModelLayer(C_141656_.f_171221_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3885_ modelWolf)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 0);
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 2);
      } else if (modelPart.equals("leg1")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 3);
      } else if (modelPart.equals("leg2")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 4);
      } else if (modelPart.equals("leg3")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 5);
      } else if (modelPart.equals("leg4")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 6);
      } else if (modelPart.equals("tail")) {
         return (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 7);
      } else {
         return modelPart.equals("mane") ? (C_3889_)Reflector.ModelWolf_ModelRenderers.getValue(modelWolf, 9) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "tail", "mane"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4414_ render = new C_4414_(renderManager.getContext());
      render.g = (C_3885_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
