package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.RabbitModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterRabbit extends ModelAdapter {
   private static Map mapPartFields = null;

   public ModelAdapterRabbit() {
      super(EntityType.f_20517_, "rabbit", 0.3F);
   }

   public Model makeModel() {
      return new RabbitModel(bakeModelLayer(ModelLayers.f_171174_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof RabbitModel modelRabbit)) {
         return null;
      } else {
         Map mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (ModelPart)Reflector.getFieldValue(modelRabbit, Reflector.ModelRabbit_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"left_foot", "right_foot", "left_thigh", "right_thigh", "body", "left_arm", "right_arm", "head", "right_ear", "left_ear", "tail", "nose"};
   }

   private static Map getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("left_foot", 0);
         mapPartFields.put("right_foot", 1);
         mapPartFields.put("left_thigh", 2);
         mapPartFields.put("right_thigh", 3);
         mapPartFields.put("body", 4);
         mapPartFields.put("left_arm", 5);
         mapPartFields.put("right_arm", 6);
         mapPartFields.put("head", 7);
         mapPartFields.put("right_ear", 8);
         mapPartFields.put("left_ear", 9);
         mapPartFields.put("tail", 10);
         mapPartFields.put("nose", 11);
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      RabbitRenderer render = new RabbitRenderer(renderManager.getContext());
      render.f_115290_ = (RabbitModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
