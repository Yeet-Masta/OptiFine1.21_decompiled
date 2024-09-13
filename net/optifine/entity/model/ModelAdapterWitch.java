package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WitchModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WitchRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.Config;

public class ModelAdapterWitch extends ModelAdapterVillager {
   public ModelAdapterWitch() {
      super(EntityType.f_20495_, "witch", 0.5F);
   }

   @Override
   public Model makeModel() {
      return new WitchModel(bakeModelLayer(ModelLayers.f_171213_));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof WitchModel modelWitch)) {
         return null;
      } else {
         return modelPart.equals("mole") ? modelWitch.m_142109_().getChildModelDeep("mole") : super.getModelRenderer(modelWitch, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectToArray(names, "mole");
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WitchRenderer render = new WitchRenderer(renderManager.getContext());
      render.f_115290_ = (WitchModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
