package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FoxModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.FoxRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox extends ModelAdapter {
   private static Map mapPartFields = null;

   public ModelAdapterFox() {
      super(EntityType.f_20452_, "fox", 0.4F);
   }

   public Model makeModel() {
      return new FoxModel(bakeModelLayer(ModelLayers.f_171148_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof FoxModel modelFox)) {
         return null;
      } else {
         Map mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (ModelPart)Reflector.getFieldValue(modelFox, Reflector.ModelFox_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map getMapPartFields() {
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

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      FoxRenderer render = new FoxRenderer(renderManager.getContext());
      render.f_115290_ = (FoxModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
