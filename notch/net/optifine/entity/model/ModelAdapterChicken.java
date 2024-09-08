package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3807_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4317_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterChicken extends ModelAdapter {
   public ModelAdapterChicken() {
      super(C_513_.f_20555_, "chicken", 0.3F);
   }

   public C_3840_ makeModel() {
      return new C_3807_(bakeModelLayer(C_141656_.f_171277_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3807_ modelChicken)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 0);
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 1);
      } else if (modelPart.equals("right_leg")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 2);
      } else if (modelPart.equals("left_leg")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 3);
      } else if (modelPart.equals("right_wing")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 4);
      } else if (modelPart.equals("left_wing")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 5);
      } else if (modelPart.equals("bill")) {
         return (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 6);
      } else {
         return modelPart.equals("chin") ? (C_3889_)Reflector.ModelChicken_ModelRenderers.getValue(modelChicken, 7) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_leg", "left_leg", "right_wing", "left_wing", "bill", "chin"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4317_ render = new C_4317_(renderManager.getContext());
      render.g = (C_3807_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
