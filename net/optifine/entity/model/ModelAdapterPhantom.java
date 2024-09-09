package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PhantomModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PhantomRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterPhantom extends ModelAdapter {
   private static Map<String, String> mapPartFields = null;

   public ModelAdapterPhantom() {
      super(EntityType.f_20509_, "phantom", 0.75F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PhantomModel(bakeModelLayer(ModelLayers.f_171204_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof PhantomModel modelPhantom)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelPhantom.m_142109_();
      } else {
         Map<String, String> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            String name = (String)mapParts.get(modelPart);
            return modelPhantom.m_142109_().getChildModelDeep(name);
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
         mapPartFields.put("body", "body");
         mapPartFields.put("head", "head");
         mapPartFields.put("left_wing", "left_wing_base");
         mapPartFields.put("left_wing_tip", "left_wing_tip");
         mapPartFields.put("right_wing", "right_wing_base");
         mapPartFields.put("right_wing_tip", "right_wing_tip");
         mapPartFields.put("tail", "tail_base");
         mapPartFields.put("tail2", "tail_tip");
         mapPartFields.put("root", "root");
         return mapPartFields;
      }
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PhantomRenderer render = new PhantomRenderer(renderManager.getContext());
      render.f_115290_ = (PhantomModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
