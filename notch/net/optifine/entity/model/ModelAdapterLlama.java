package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3837_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4359_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterLlama extends ModelAdapter {
   public ModelAdapterLlama() {
      super(C_513_.f_20466_, "llama", 0.7F);
   }

   public ModelAdapterLlama(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3837_(bakeModelLayer(C_141656_.f_171194_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3837_ modelLlama)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 0);
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 1);
      } else if (modelPart.equals("leg1")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 2);
      } else if (modelPart.equals("leg2")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 3);
      } else if (modelPart.equals("leg3")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 4);
      } else if (modelPart.equals("leg4")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 5);
      } else if (modelPart.equals("chest_right")) {
         return (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 6);
      } else {
         return modelPart.equals("chest_left") ? (C_3889_)Reflector.ModelLlama_ModelRenderers.getValue(modelLlama, 7) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "leg1", "leg2", "leg3", "leg4", "chest_right", "chest_left"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4359_ render = new C_4359_(renderManager.getContext(), C_141656_.f_171194_);
      render.g = (C_3837_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
