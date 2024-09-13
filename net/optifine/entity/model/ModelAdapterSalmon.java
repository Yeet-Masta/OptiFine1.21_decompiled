package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.SalmonModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.SalmonRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterSalmon extends ModelAdapter {
   public ModelAdapterSalmon() {
      super(EntityType.f_20519_, "salmon", 0.3F);
   }

   @Override
   public Model makeModel() {
      return new SalmonModel(bakeModelLayer(ModelLayers.f_171176_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SalmonModel modelSalmon)) {
         return null;
      } else if (modelPart.equals("body_front")) {
         return modelSalmon.m_142109_().getChildModelDeep("body_front");
      } else if (modelPart.equals("body_back")) {
         return modelSalmon.m_142109_().getChildModelDeep("body_back");
      } else if (modelPart.equals("head")) {
         return modelSalmon.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("fin_back_1")) {
         return modelSalmon.m_142109_().getChildModelDeep("top_front_fin");
      } else if (modelPart.equals("fin_back_2")) {
         return modelSalmon.m_142109_().getChildModelDeep("top_back_fin");
      } else if (modelPart.equals("tail")) {
         return modelSalmon.m_142109_().getChildModelDeep("back_fin");
      } else if (modelPart.equals("fin_right")) {
         return modelSalmon.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelSalmon.m_142109_().getChildModelDeep("left_fin");
      } else {
         return modelPart.equals("root") ? modelSalmon.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body_front", "body_back", "head", "fin_back_1", "fin_back_2", "tail", "fin_right", "fin_left", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      SalmonRenderer render = new SalmonRenderer(renderManager.getContext());
      render.f_115290_ = (SalmonModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
