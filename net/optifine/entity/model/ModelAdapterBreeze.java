package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BreezeModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterBreeze extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterBreeze() {
      super(EntityType.f_302782_, "breeze", 0.8F);
   }

   protected ModelAdapterBreeze(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public Model makeModel() {
      return new BreezeModel(bakeModelLayer(ModelLayers.f_303100_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BreezeModel modelBreeze)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelBreeze.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelBreeze.m_142109_().getChildModelDeep(name);
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
      map.put("rods", "rods");
      map.put("head", "head");
      map.put("wind_body", "wind_body");
      map.put("wind_middle", "wind_mid");
      map.put("wind_bottom", "wind_bottom");
      map.put("wind_top", "wind_top");
      map.put("root", "root");
      return map;
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BreezeRenderer render = new BreezeRenderer(renderManager.getContext());
      render.f_115290_ = (BreezeModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
