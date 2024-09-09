package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.AllayModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterAllay extends ModelAdapter {
   private static Map mapParts = makeMapParts();

   public ModelAdapterAllay() {
      super(EntityType.f_217014_, "allay", 0.4F);
   }

   public Model makeModel() {
      return new AllayModel(bakeModelLayer(ModelLayers.f_233547_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof AllayModel modelAllay)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelAllay.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelAllay.m_142109_().getChildModelDeep(name);
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
      map.put("head", "head");
      map.put("right_arm", "right_arm");
      map.put("left_arm", "left_arm");
      map.put("right_wing", "right_wing");
      map.put("left_wing", "left_wing");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      AllayRenderer render = new AllayRenderer(renderManager.getContext());
      render.f_115290_ = (AllayModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
