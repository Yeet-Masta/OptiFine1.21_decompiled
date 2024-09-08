package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4326_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_4326_.C_4327_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterDragon extends ModelAdapter {
   public ModelAdapterDragon() {
      super(C_513_.f_20565_, "dragon", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_4327_(bakeModelLayer(C_141656_.f_171144_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_4327_ modelDragon)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 0);
      } else if (modelPart.equals("spine")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 1);
      } else if (modelPart.equals("jaw")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 2);
      } else if (modelPart.equals("body")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 3);
      } else if (modelPart.equals("left_wing")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 4);
      } else if (modelPart.equals("left_wing_tip")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 5);
      } else if (modelPart.equals("front_left_leg")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 6);
      } else if (modelPart.equals("front_left_shin")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 7);
      } else if (modelPart.equals("front_left_foot")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 8);
      } else if (modelPart.equals("back_left_leg")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 9);
      } else if (modelPart.equals("back_left_shin")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 10);
      } else if (modelPart.equals("back_left_foot")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 11);
      } else if (modelPart.equals("right_wing")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 12);
      } else if (modelPart.equals("right_wing_tip")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 13);
      } else if (modelPart.equals("front_right_leg")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 14);
      } else if (modelPart.equals("front_right_shin")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 15);
      } else if (modelPart.equals("front_right_foot")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 16);
      } else if (modelPart.equals("back_right_leg")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 17);
      } else if (modelPart.equals("back_right_shin")) {
         return (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 18);
      } else {
         return modelPart.equals("back_right_foot") ? (C_3889_)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 19) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "head",
         "spine",
         "jaw",
         "body",
         "left_wing",
         "left_wing_tip",
         "front_left_leg",
         "front_left_shin",
         "front_left_foot",
         "back_left_leg",
         "back_left_shin",
         "back_left_foot",
         "right_wing",
         "right_wing_tip",
         "front_right_leg",
         "front_right_shin",
         "front_right_foot",
         "back_right_leg",
         "back_right_shin",
         "back_right_foot"
      };
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4326_ render = new C_4326_(renderManager.getContext());
      if (!Reflector.EnderDragonRenderer_model.exists()) {
         Config.warn("Field not found: EnderDragonRenderer.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.EnderDragonRenderer_model, modelBase);
         render.e = shadowSize;
         return render;
      }
   }
}
