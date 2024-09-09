package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AxolotlModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.AxolotlRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterAxolotl extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterAxolotl() {
      super(EntityType.f_147039_, "axolotl", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new AxolotlModel(bakeModelLayer(ModelLayers.f_171263_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof AxolotlModel modelAxolotl)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelAxolotl, Reflector.ModelAxolotl_ModelRenderers, index);
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
         mapPartFields.put("tail", 0);
         mapPartFields.put("leg2", 1);
         mapPartFields.put("leg1", 2);
         mapPartFields.put("leg4", 3);
         mapPartFields.put("leg3", 4);
         mapPartFields.put("body", 5);
         mapPartFields.put("head", 6);
         mapPartFields.put("top_gills", 7);
         mapPartFields.put("left_gills", 8);
         mapPartFields.put("right_gills", 9);
         return mapPartFields;
      }
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      AxolotlRenderer render = new AxolotlRenderer(renderManager.getContext());
      render.f_115290_ = (AxolotlModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
