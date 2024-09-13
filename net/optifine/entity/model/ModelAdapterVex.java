package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.VexModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.VexRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterVex extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterVex() {
      super(EntityType.f_20491_, "vex", 0.3F);
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof VexModel modelVex)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelVex.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelVex.m_142109_().getChildModelDeep(name);
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
      map.put("right_arm", "right_arm");
      map.put("left_arm", "left_arm");
      map.put("right_wing", "right_wing");
      map.put("left_wing", "left_wing");
      map.put("root", "root");
      return map;
   }

   @Override
   public Model makeModel() {
      return new VexModel(bakeModelLayer(ModelLayers.f_171209_));
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      VexRenderer render = new VexRenderer(renderManager.getContext());
      render.f_115290_ = (VexModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
