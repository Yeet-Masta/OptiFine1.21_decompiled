package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.TurtleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TurtleRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterTurtle extends ModelAdapterQuadruped {
   public ModelAdapterTurtle() {
      super(EntityType.f_20490_, "turtle", 0.7F);
   }

   @Override
   public Model makeModel() {
      return new TurtleModel(bakeModelLayer(ModelLayers.f_171260_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof QuadrupedModel)) {
         return null;
      } else {
         TurtleModel modelQuadruped = (TurtleModel)model;
         return modelPart.equals("body2") ? (ModelPart)Reflector.ModelTurtle_body2.getValue(modelQuadruped) : super.getModelRenderer(model, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectToArray(names, "body2");
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TurtleRenderer render = new TurtleRenderer(renderManager.getContext());
      render.f_115290_ = (TurtleModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
