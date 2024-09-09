package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.RavagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RavagerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterRavager extends ModelAdapter {
   private static Map<String, String> mapPartFields = null;

   public ModelAdapterRavager() {
      super(EntityType.f_20518_, "ravager", 1.1F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new RavagerModel(bakeModelLayer(ModelLayers.f_171175_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof RavagerModel modelRavager)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelRavager.m_142109_();
      } else {
         Map<String, String> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            String name = (String)mapParts.get(modelPart);
            return modelRavager.m_142109_().getChildModelDeep(name);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, String> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("head", "head");
         mapPartFields.put("jaw", "mouth");
         mapPartFields.put("body", "body");
         mapPartFields.put("leg1", "right_hind_leg");
         mapPartFields.put("leg2", "left_hind_leg");
         mapPartFields.put("leg3", "right_front_leg");
         mapPartFields.put("leg4", "left_front_leg");
         mapPartFields.put("neck", "neck");
         mapPartFields.put("root", "root");
         return mapPartFields;
      }
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      RavagerRenderer render = new RavagerRenderer(renderManager.getContext());
      render.f_115290_ = (RavagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
