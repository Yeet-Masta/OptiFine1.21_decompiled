package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SnifferModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SnifferRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSniffer extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterSniffer() {
      super(EntityType.f_271264_, "sniffer", 1.1F);
   }

   @Override
   public Model makeModel() {
      return new SnifferModel(bakeModelLayer(ModelLayers.f_271465_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SnifferModel modelSniffer)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelSniffer.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelSniffer.m_142109_().getChildModelDeep(name);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])mapParts.keySet().toArray(new String[0]);
   }

   private static Map<String, String> makeMapParts() {
      Map<String, String> map = new LinkedHashMap();
      map.put("body", "body");
      map.put("back_left_leg", "left_hind_leg");
      map.put("back_right_leg", "right_hind_leg");
      map.put("middle_left_leg", "left_mid_leg");
      map.put("middle_right_leg", "right_mid_leg");
      map.put("front_left_leg", "left_front_leg");
      map.put("front_right_leg", "right_front_leg");
      map.put("head", "head");
      map.put("left_ear", "left_ear");
      map.put("right_ear", "right_ear");
      map.put("nose", "nose");
      map.put("lower_beak", "lower_beak");
      map.put("root", "root");
      return map;
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SnifferRenderer render = new SnifferRenderer(renderManager.getContext());
      render.f_115290_ = (SnifferModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
