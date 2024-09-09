package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterIllusioner extends ModelAdapterIllager {
   public ModelAdapterIllusioner() {
      super(EntityType.f_20459_, "illusioner", 0.5F, new String[]{"illusion_illager"});
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      IllagerModel model = new IllagerModel(bakeModelLayer(ModelLayers.f_171191_));
      model.m_102934_().f_104207_ = true;
      return model;
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      IllusionerRenderer render = new IllusionerRenderer(renderManager.getContext());
      render.f_115290_ = (IllagerModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
