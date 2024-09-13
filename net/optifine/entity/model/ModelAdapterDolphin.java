package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.DolphinModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.DolphinRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterDolphin extends ModelAdapter {
   public ModelAdapterDolphin() {
      super(EntityType.f_20559_, "dolphin", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new DolphinModel(bakeModelLayer(ModelLayers.f_171131_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof DolphinModel modelDolphin)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelDolphin.m_142109_();
      } else {
         ModelPart modelBody = modelDolphin.m_142109_().m_171324_("body");
         if (modelBody == null) {
            return null;
         } else if (modelPart.equals("body")) {
            return modelBody;
         } else if (modelPart.equals("back_fin")) {
            return modelBody.m_171324_("back_fin");
         } else if (modelPart.equals("left_fin")) {
            return modelBody.m_171324_("left_fin");
         } else if (modelPart.equals("right_fin")) {
            return modelBody.m_171324_("right_fin");
         } else if (modelPart.equals("tail")) {
            return modelBody.m_171324_("tail");
         } else if (modelPart.equals("tail_fin")) {
            return modelBody.m_171324_("tail").m_171324_("tail_fin");
         } else {
            return modelPart.equals("head") ? modelBody.m_171324_("head") : null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body", "back_fin", "left_fin", "right_fin", "tail", "tail_fin", "head", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      DolphinRenderer render = new DolphinRenderer(renderManager.getContext());
      render.f_115290_ = (DolphinModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
