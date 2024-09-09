package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterFox() {
      super(EntityType.f_20452_, "fox", 0.4F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new FoxModel(bakeModelLayer(ModelLayers.f_171148_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof FoxModel modelFox)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelFox, Reflector.ModelFox_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("head", 0);
         mapPartFields.put("body", 1);
         mapPartFields.put("leg1", 2);
         mapPartFields.put("leg2", 3);
         mapPartFields.put("leg3", 4);
         mapPartFields.put("leg4", 5);
         mapPartFields.put("tail", 6);
         return mapPartFields;
      }
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      FoxRenderer render = new FoxRenderer(renderManager.getContext());
      render.f_115290_ = (FoxModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
