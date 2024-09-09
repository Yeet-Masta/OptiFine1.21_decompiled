package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BeeRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterBee extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterBee() {
      super(EntityType.f_20550_, "bee", 0.4F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BeeModel(bakeModelLayer(ModelLayers.f_171268_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof BeeModel modelBee)) {
         return null;
      } else if (modelPart.equals("body")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelBee, Reflector.ModelBee_ModelRenderers, 0);
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         net.minecraft.client.model.geom.ModelPart body = this.getModelRenderer(modelBee, "body");
         return body.getChildModelDeep(name);
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
      map.put("body", "bone");
      map.put("torso", "body");
      map.put("right_wing", "right_wing");
      map.put("left_wing", "left_wing");
      map.put("front_legs", "front_legs");
      map.put("middle_legs", "middle_legs");
      map.put("back_legs", "back_legs");
      map.put("stinger", "stinger");
      map.put("left_antenna", "left_antenna");
      map.put("right_antenna", "right_antenna");
      return map;
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BeeRenderer render = new BeeRenderer(renderManager.getContext());
      render.f_115290_ = (BeeModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
