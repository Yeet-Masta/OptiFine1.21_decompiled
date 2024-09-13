package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PufferfishSmallModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.PufferfishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterPufferFishSmall extends ModelAdapter {
   public ModelAdapterPufferFishSmall() {
      super(EntityType.f_20516_, "puffer_fish_small", 0.2F);
   }

   @Override
   public Model makeModel() {
      return new PufferfishSmallModel(bakeModelLayer(ModelLayers.f_171173_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof PufferfishSmallModel modelPufferFishSmall)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("eye_right")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("right_eye");
      } else if (modelPart.equals("eye_left")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("left_eye");
      } else if (modelPart.equals("fin_right")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("tail")) {
         return modelPufferFishSmall.m_142109_().getChildModelDeep("back_fin");
      } else {
         return modelPart.equals("root") ? modelPufferFishSmall.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "eye_right", "eye_left", "tail", "fin_right", "fin_left", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      PufferfishRenderer customRenderer = new PufferfishRenderer(renderManager.getContext());
      customRenderer.f_114477_ = shadowSize;
      EntityRenderer render = rendererCache.get(EntityType.f_20516_, index, () -> customRenderer);
      if (!(render instanceof PufferfishRenderer renderFish)) {
         Config.warn("Not a PufferfishRenderer: " + render);
         return null;
      } else if (!Reflector.RenderPufferfish_modelSmall.exists()) {
         Config.warn("Model field not found: RenderPufferfish.modelSmall");
         return null;
      } else {
         Reflector.RenderPufferfish_modelSmall.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
