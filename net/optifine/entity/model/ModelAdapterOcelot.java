package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.OcelotRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterOcelot extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterOcelot() {
      super(EntityType.f_20505_, "ocelot", 0.4F);
   }

   protected ModelAdapterOcelot(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new OcelotModel(bakeModelLayer(ModelLayers.f_171201_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof OcelotModel modelOcelot)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelOcelot, Reflector.ModelOcelot_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "tail", "tail2", "head", "body"};
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("back_left_leg", 0);
         mapPartFields.put("back_right_leg", 1);
         mapPartFields.put("front_left_leg", 2);
         mapPartFields.put("front_right_leg", 3);
         mapPartFields.put("tail", 4);
         mapPartFields.put("tail2", 5);
         mapPartFields.put("head", 6);
         mapPartFields.put("body", 7);
         return mapPartFields;
      }
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      OcelotRenderer render = new OcelotRenderer(renderManager.getContext());
      render.f_115290_ = (OcelotModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
