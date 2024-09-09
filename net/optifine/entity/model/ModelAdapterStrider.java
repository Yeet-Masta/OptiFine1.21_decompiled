package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.StriderModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.StriderRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterStrider extends ModelAdapter {
   private static Map mapParts = makeMapParts();

   public ModelAdapterStrider() {
      super(EntityType.f_20482_, "strider", 0.5F);
   }

   protected ModelAdapterStrider(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new StriderModel(bakeModelLayer(ModelLayers.f_171251_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof StriderModel modelStrider)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelStrider.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelStrider.m_142109_().getChildModelDeep(name);
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
      map.put("right_leg", "right_leg");
      map.put("left_leg", "left_leg");
      map.put("body", "body");
      map.put("hair_right_bottom", "right_bottom_bristle");
      map.put("hair_right_middle", "right_middle_bristle");
      map.put("hair_right_top", "right_top_bristle");
      map.put("hair_left_top", "left_top_bristle");
      map.put("hair_left_middle", "left_middle_bristle");
      map.put("hair_left_bottom", "left_bottom_bristle");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      StriderRenderer render = new StriderRenderer(renderManager.getContext());
      render.f_115290_ = (StriderModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
