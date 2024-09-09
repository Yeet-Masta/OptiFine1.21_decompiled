package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.TadpoleModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.TadpoleRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterTadpole extends ModelAdapter {
   public ModelAdapterTadpole() {
      super(EntityType.f_217013_, "tadpole", 0.14F);
   }

   public Model makeModel() {
      return new TadpoleModel(bakeModelLayer(ModelLayers.f_233549_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof TadpoleModel modelTadpole)) {
         return null;
      } else if (modelPart.equals("body")) {
         return (ModelPart)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 0);
      } else {
         return modelPart.equals("tail") ? (ModelPart)Reflector.ModelTadpole_ModelRenderers.getValue(modelTadpole, 1) : null;
      }
   }

   public String[] getModelRendererNames() {
      String[] names = new String[]{"body", "tail"};
      return names;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TadpoleRenderer render = new TadpoleRenderer(renderManager.getContext());
      render.f_115290_ = (TadpoleModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
