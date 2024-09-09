package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EvokerFangsModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EvokerFangsRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterEvokerFangs extends ModelAdapter {
   public ModelAdapterEvokerFangs() {
      super(EntityType.f_20569_, "evoker_fangs", 0.0F, new String[]{"evocation_fangs"});
   }

   public Model makeModel() {
      return new EvokerFangsModel(bakeModelLayer(ModelLayers.f_171147_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof EvokerFangsModel modelEvokerFangs)) {
         return null;
      } else if (modelPart.equals("base")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("base");
      } else if (modelPart.equals("upper_jaw")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("upper_jaw");
      } else if (modelPart.equals("lower_jaw")) {
         return modelEvokerFangs.m_142109_().getChildModelDeep("lower_jaw");
      } else {
         return modelPart.equals("root") ? modelEvokerFangs.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"base", "upper_jaw", "lower_jaw", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      EvokerFangsRenderer render = new EvokerFangsRenderer(renderManager.getContext());
      if (!Reflector.RenderEvokerFangs_model.exists()) {
         Config.warn("Field not found: RenderEvokerFangs.model");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderEvokerFangs_model, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
