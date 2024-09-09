package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CamelModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.CamelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCamel extends ModelAdapter {
   private static Map mapParts = makeMapParts();

   public ModelAdapterCamel() {
      super(EntityType.f_243976_, "camel", 0.7F);
   }

   public Model makeModel() {
      return new CamelModel(bakeModelLayer(ModelLayers.f_244030_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof CamelModel modelCamel)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelCamel.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelCamel.m_142109_().getChildModelDeep(name);
      } else {
         return null;
      }
   }

   public String[] getModelRendererNames() {
      String[] names = (String[])mapParts.keySet().toArray(new String[0]);
      return names;
   }

   private static Map makeMapParts() {
      Map map = new LinkedHashMap();
      map.put("body", "body");
      map.put("hump", "hump");
      map.put("tail", "tail");
      map.put("head", "head");
      map.put("left_ear", "left_ear");
      map.put("right_ear", "right_ear");
      map.put("back_left_leg", "left_hind_leg");
      map.put("back_right_leg", "right_hind_leg");
      map.put("front_left_leg", "left_front_leg");
      map.put("front_right_leg", "right_front_leg");
      map.put("saddle", "saddle");
      map.put("reins", "reins");
      map.put("bridle", "bridle");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CamelRenderer render = new CamelRenderer(renderManager.getContext(), ModelLayers.f_244030_);
      render.f_115290_ = (CamelModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
