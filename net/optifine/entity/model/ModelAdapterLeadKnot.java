package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.LeashKnotModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LeashKnotRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterLeadKnot extends ModelAdapter {
   public ModelAdapterLeadKnot() {
      super(EntityType.f_20464_, "lead_knot", 0.0F);
   }

   @Override
   public Model makeModel() {
      return new LeashKnotModel(bakeModelLayer(ModelLayers.f_171193_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof LeashKnotModel modelLeashKnot)) {
         return null;
      } else if (modelPart.equals("knot")) {
         return modelLeashKnot.m_142109_().getChildModelDeep("knot");
      } else {
         return modelPart.equals("root") ? modelLeashKnot.m_142109_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"knot", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      LeashKnotRenderer render = new LeashKnotRenderer(renderManager.getContext());
      if (!Reflector.RenderLeashKnot_leashKnotModel.exists()) {
         Config.warn("Field not found: RenderLeashKnot.leashKnotModel");
         return null;
      } else {
         Reflector.setFieldValue(render, Reflector.RenderLeashKnot_leashKnotModel, modelBase);
         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
