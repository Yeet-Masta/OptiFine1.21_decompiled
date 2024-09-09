package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PufferfishBigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishBig extends ModelAdapter {
   public ModelAdapterPufferFishBig() {
      super(EntityType.f_20516_, "puffer_fish_big", 0.2F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PufferfishBigModel(bakeModelLayer(ModelLayers.f_171171_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
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

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "body",
         "fin_right",
         "fin_left",
         "spikes_front_top",
         "spikes_middle_top",
         "spikes_back_top",
         "spikes_front_right",
         "spikes_front_left",
         "spikes_front_bottom",
         "spikes_middle_bottom",
         "spikes_back_bottom",
         "spikes_back_right",
         "spikes_back_left",
         "root"
      };
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PufferfishRenderer customRenderer = new PufferfishRenderer(renderManager.getContext());
      customRenderer.f_114477_ = shadowSize;
      net.minecraft.client.renderer.entity.EntityRenderer render = rendererCache.get(EntityType.f_20516_, index, () -> customRenderer);
      if (!(render instanceof PufferfishRenderer renderFish)) {
         Config.warn("Not a PufferfishRenderer: " + render);
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
