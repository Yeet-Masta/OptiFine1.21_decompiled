package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestedHorseModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.ChestedHorseRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterMule extends ModelAdapterHorse {
   public ModelAdapterMule() {
      super(EntityType.f_20503_, "mule", 0.75F);
   }

   public Model makeModel() {
      return new ChestedHorseModel(bakeModelLayer(ModelLayers.f_171200_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof ChestedHorseModel modelHorseChests)) {
         return null;
      } else if (modelPart.equals("left_chest")) {
         return (ModelPart)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 0);
      } else {
         return modelPart.equals("right_chest") ? (ModelPart)Reflector.ModelHorseChests_ModelRenderers.getValue(modelHorseChests, 1) : super.getModelRenderer(model, modelPart);
      }
   }

   public String[] getModelRendererNames() {
      List list = new ArrayList(Arrays.asList(super.getModelRendererNames()));
      list.add("left_chest");
      list.add("right_chest");
      return (String[])list.toArray(new String[list.size()]);
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      ChestedHorseRenderer render = new ChestedHorseRenderer(renderManager.getContext(), 0.92F, ModelLayers.f_171200_);
      render.f_115290_ = (EntityModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
