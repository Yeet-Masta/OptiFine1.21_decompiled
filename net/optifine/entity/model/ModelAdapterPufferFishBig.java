package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishBig extends ModelAdapter {
   public ModelAdapterPufferFishBig() {
      super(EntityType.f_20516_, "puffer_fish_big", 0.2F);
   }

   public Model makeModel() {
      return new PufferfishBigModel(bakeModelLayer(ModelLayers.f_171171_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof PufferfishBigModel modelPufferFishBig)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("fin_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_blue_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_blue_fin");
      } else if (modelPart.equals("spikes_front_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_front_fin");
      } else if (modelPart.equals("spikes_middle_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_middle_fin");
      } else if (modelPart.equals("spikes_back_top")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("top_back_fin");
      } else if (modelPart.equals("spikes_front_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_front_fin");
      } else if (modelPart.equals("spikes_front_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_front_fin");
      } else if (modelPart.equals("spikes_front_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_front_fin");
      } else if (modelPart.equals("spikes_middle_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_middle_fin");
      } else if (modelPart.equals("spikes_back_bottom")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("bottom_back_fin");
      } else if (modelPart.equals("spikes_back_right")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("right_back_fin");
      } else if (modelPart.equals("spikes_back_left")) {
         return modelPufferFishBig.m_142109_().getChildModelDeep("left_back_fin");
      } else {
         return modelPart.equals("root") ? modelPufferFishBig.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "fin_right", "fin_left", "spikes_front_top", "spikes_middle_top", "spikes_back_top", "spikes_front_right", "spikes_front_left", "spikes_front_bottom", "spikes_middle_bottom", "spikes_back_bottom", "spikes_back_right", "spikes_back_left", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PufferfishRenderer customRenderer = new PufferfishRenderer(renderManager.getContext());
      customRenderer.f_114477_ = shadowSize;
      EntityRenderer render = rendererCache.get(EntityType.f_20516_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof PufferfishRenderer renderFish)) {
         Config.warn("Not a PufferfishRenderer: " + String.valueOf(render));
         return null;
      } else if (!Reflector.RenderPufferfish_modelBig.exists()) {
         Config.warn("Model field not found: RenderPufferfish.modelBig");
         return null;
      } else {
         Reflector.RenderPufferfish_modelBig.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
