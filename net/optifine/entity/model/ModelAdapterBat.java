package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterBat extends ModelAdapter {
   public ModelAdapterBat() {
      super(EntityType.f_20549_, "bat", 0.25F);
   }

   @Override
   public Model makeModel() {
      return new BatModel(bakeModelLayer(ModelLayers.f_171265_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BatModel modelBat)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBat.m_142109_().getChildModelDeep("head");
      } else if (modelPart.equals("body")) {
         return modelBat.m_142109_().getChildModelDeep("body");
      } else if (modelPart.equals("right_wing")) {
         return modelBat.m_142109_().getChildModelDeep("right_wing");
      } else if (modelPart.equals("left_wing")) {
         return modelBat.m_142109_().getChildModelDeep("left_wing");
      } else if (modelPart.equals("outer_right_wing")) {
         return modelBat.m_142109_().getChildModelDeep("right_wing_tip");
      } else if (modelPart.equals("outer_left_wing")) {
         return modelBat.m_142109_().getChildModelDeep("left_wing_tip");
      } else if (modelPart.equals("feet")) {
         return modelBat.m_142109_().getChildModelDeep("feet");
      } else {
         return modelPart.equals("root") ? modelBat.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "body", "right_wing", "left_wing", "outer_right_wing", "outer_left_wing", "feet", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BatRenderer render = new BatRenderer(renderManager.getContext());
      render.f_115290_ = (BatModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
