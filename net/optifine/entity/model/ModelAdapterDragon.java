package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterDragon extends ModelAdapter {
   public ModelAdapterDragon() {
      super(EntityType.f_20565_, "dragon", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new net.minecraft.client.renderer.entity.EnderDragonRenderer.DragonModel(bakeModelLayer(ModelLayers.f_171144_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof net.minecraft.client.renderer.entity.EnderDragonRenderer.DragonModel modelDragon)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 0);
      } else if (modelPart.equals("spine")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 1);
      } else if (modelPart.equals("jaw")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 2);
      } else if (modelPart.equals("body")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 3);
      } else if (modelPart.equals("left_wing")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 4);
      } else if (modelPart.equals("left_wing_tip")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 5);
      } else if (modelPart.equals("front_left_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 6);
      } else if (modelPart.equals("front_left_shin")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 7);
      } else if (modelPart.equals("front_left_foot")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 8);
      } else if (modelPart.equals("back_left_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 9);
      } else if (modelPart.equals("back_left_shin")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 10);
      } else if (modelPart.equals("back_left_foot")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 11);
      } else if (modelPart.equals("right_wing")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 12);
      } else if (modelPart.equals("right_wing_tip")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 13);
      } else if (modelPart.equals("front_right_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 14);
      } else if (modelPart.equals("front_right_shin")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 15);
      } else if (modelPart.equals("front_right_foot")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 16);
      } else if (modelPart.equals("back_right_leg")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 17);
      } else if (modelPart.equals("back_right_shin")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 18);
      } else {
         return modelPart.equals("back_right_foot")
            ? (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragon, Reflector.ModelDragon_ModelRenderers, 19)
            : null;
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

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      net.minecraft.client.renderer.entity.EnderDragonRenderer render = new net.minecraft.client.renderer.entity.EnderDragonRenderer(renderManager.getContext());
      if (!Reflector.EnderDragonRenderer_model.exists()) {
         Config.warn("Field not found: EnderDragonRenderer.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.EnderDragonRenderer_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
