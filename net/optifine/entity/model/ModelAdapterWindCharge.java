package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WindChargeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WindChargeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterWindCharge extends ModelAdapter {
   private static Map mapParts = makeMapParts();

   public ModelAdapterWindCharge() {
      super(EntityType.f_303421_, "wind_charge", 0.0F);
   }

   public Model makeModel() {
      return new WindChargeModel(bakeModelLayer(ModelLayers.f_303259_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof WindChargeModel modelWindCharge)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelWindCharge.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelWindCharge.m_142109_().getChildModelDeep(name);
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
      map.put("core", "projectile");
      map.put("wind", "wind");
      map.put("cube1", "cube_r1");
      map.put("cube2", "cube_r2");
      map.put("charge", "wind_charge");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WindChargeRenderer render = new WindChargeRenderer(renderManager.getContext());
      if (!Reflector.RenderWindCharge_model.exists()) {
         Config.warn("Field not found: RenderWindCharge.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderWindCharge_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
