package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TropicalFishModelB;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.TropicalFishRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTropicalFishB extends ModelAdapter {
   public ModelAdapterTropicalFishB() {
      super(EntityType.f_20489_, "tropical_fish_b", 0.2F);
   }

   public ModelAdapterTropicalFishB(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new TropicalFishModelB(bakeModelLayer(ModelLayers.f_171256_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof TropicalFishModelB modelTropicalFish)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("tail")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("tail");
      } else if (modelPart.equals("fin_right")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("fin_top")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("top_fin");
      } else if (modelPart.equals("fin_bottom")) {
         return modelTropicalFish.m_142109_().getChildModelDeep("bottom_fin");
      } else {
         return modelPart.equals("root") ? modelTropicalFish.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "tail", "fin_right", "fin_left", "fin_top", "fin_bottom", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TropicalFishRenderer customRenderer = new TropicalFishRenderer(renderManager.getContext());
      customRenderer.f_114477_ = shadowSize;
      EntityRenderer render = rendererCache.get(EntityType.f_20489_, index, () -> {
         return customRenderer;
      });
      if (!(render instanceof TropicalFishRenderer renderFish)) {
         Config.warn("Not a TropicalFishRenderer: " + String.valueOf(render));
         return null;
      } else if (!Reflector.RenderTropicalFish_modelB.exists()) {
         Config.warn("Model field not found: RenderTropicalFish.modelB");
         return null;
      } else {
         Reflector.RenderTropicalFish_modelB.setValue(renderFish, modelBase);
         return renderFish;
      }
   }
}
