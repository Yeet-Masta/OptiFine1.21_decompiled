package net.optifine.entity.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.WitherBossModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.WitherBossRenderer;
import net.minecraft.world.entity.EntityType;

public class ModelAdapterWither extends ModelAdapter {
   public ModelAdapterWither() {
      super(EntityType.f_20496_, "wither", 0.5F);
   }

   public ModelAdapterWither(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new WitherBossModel(bakeModelLayer(ModelLayers.f_171214_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof WitherBossModel modelWither)) {
         return null;
      } else if (modelPart.equals("body1")) {
         return modelWither.m_142109_().getChildModelDeep("shoulders");
      } else if (modelPart.equals("body2")) {
         return modelWither.m_142109_().getChildModelDeep("ribcage");
      } else if (modelPart.equals("body3")) {
         return modelWither.m_142109_().getChildModelDeep("tail");
      } else if (modelPart.equals("head1")) {
         return modelWither.m_142109_().getChildModelDeep("center_head");
      } else if (modelPart.equals("head2")) {
         return modelWither.m_142109_().getChildModelDeep("right_head");
      } else if (modelPart.equals("head3")) {
         return modelWither.m_142109_().getChildModelDeep("left_head");
      } else {
         return modelPart.equals("root") ? modelWither.m_142109_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body1", "body2", "body3", "head1", "head2", "head3", "root"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      WitherBossRenderer render = new WitherBossRenderer(renderManager.getContext());
      render.f_115290_ = (WitherBossModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }
}
