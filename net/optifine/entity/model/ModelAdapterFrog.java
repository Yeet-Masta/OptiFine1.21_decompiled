package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.FrogRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterFrog extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterFrog() {
      super(EntityType.f_217012_, "frog", 0.3F);
   }

   @Override
   public Model makeModel() {
      return new FrogModel(bakeModelLayer(ModelLayers.f_233546_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof FrogModel modelFrog)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelFrog.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelFrog.m_142109_().getChildModelDeep(name);
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
      map.put("head", "head");
      map.put("eyes", "eyes");
      map.put("tongue", "tongue");
      map.put("left_arm", "left_arm");
      map.put("right_arm", "right_arm");
      map.put("left_leg", "left_leg");
      map.put("right_leg", "right_leg");
      map.put("croaking_body", "croaking_body");
      map.put("root", "root");
      return map;
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      FrogRenderer render = new FrogRenderer(renderManager.getContext());
      render.f_115290_ = (FrogModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
