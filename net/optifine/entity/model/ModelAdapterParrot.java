package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterParrot extends ModelAdapter {
   public ModelAdapterParrot() {
      super(EntityType.f_20508_, "parrot", 0.3F);
   }

   public Model makeModel() {
      return new ParrotModel(bakeModelLayer(ModelLayers.f_171203_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ParrotModel modelParrot)) {
         return null;
      } else if (modelPart.equals("body")) {
         return modelParrot.m_142109_().m_171324_("body");
      } else if (modelPart.equals("tail")) {
         return modelParrot.m_142109_().m_171324_("tail");
      } else if (modelPart.equals("left_wing")) {
         return modelParrot.m_142109_().m_171324_("left_wing");
      } else if (modelPart.equals("right_wing")) {
         return modelParrot.m_142109_().m_171324_("right_wing");
      } else if (modelPart.equals("head")) {
         return modelParrot.m_142109_().m_171324_("head");
      } else if (modelPart.equals("left_leg")) {
         return modelParrot.m_142109_().m_171324_("left_leg");
      } else if (modelPart.equals("right_leg")) {
         return modelParrot.m_142109_().m_171324_("right_leg");
      } else {
         return modelPart.equals("root") ? modelParrot.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "tail", "left_wing", "right_wing", "head", "left_leg", "right_leg", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ParrotRenderer render = new ParrotRenderer(renderManager.getContext());
      render.f_115290_ = (ParrotModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
