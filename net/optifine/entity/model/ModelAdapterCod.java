package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterCod extends ModelAdapter {
   public ModelAdapterCod() {
      super(EntityType.f_20556_, "cod", 0.3F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new CodModel(bakeModelLayer(ModelLayers.f_171278_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof CodModel modelCod)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelCod.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("fin_back")) {
         return modelCod.m_142109_().getChildModelDeep("top_fin");
      } else if (modelPart.equals("head")) {
         return modelCod.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("nose")) {
         return modelCod.m_142109_().getChildModelDeep("nose");
      } else if (modelPart.equals("fin_right")) {
         return modelCod.m_142109_().getChildModelDeep("right_fin");
      } else if (modelPart.equals("fin_left")) {
         return modelCod.m_142109_().getChildModelDeep("left_fin");
      } else if (modelPart.equals("tail")) {
         return modelCod.m_142109_().getChildModelDeep("tail_fin");
      } else {
         return modelPart.equals("root") ? modelCod.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "fin_back", "head", "nose", "fin_right", "fin_left", "tail", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      CodRenderer render = new CodRenderer(renderManager.getContext());
      render.f_115290_ = (CodModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
