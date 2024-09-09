package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.TippableArrowRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterArrow extends ModelAdapter {
   public ModelAdapterArrow() {
      super(EntityType.f_20548_, "arrow", 0.0F);
   }

   protected ModelAdapterArrow(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ArrowModel(new net.minecraft.client.model.geom.ModelPart(new ArrayList(), new HashMap()));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ArrowModel modelArrow)) {
         return null;
      } else {
         return modelPart.equals("body") ? modelArrow.body : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"body"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      TippableArrowRenderer render = new TippableArrowRenderer(renderManager.getContext());
      render.model = (ArrowModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
