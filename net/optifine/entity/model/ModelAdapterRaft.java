package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.optifine.Config;

public class ModelAdapterRaft extends ModelAdapter {
   public ModelAdapterRaft() {
      super(EntityType.f_20552_, "raft", 0.5F);
   }

   protected ModelAdapterRaft(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   @Override
   public Model makeModel() {
      return new RaftModel(bakeModelLayer(ModelLayers.m_171289_(Type.BAMBOO)));
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof RaftModel modelRaft)) {
         return null;
      } else {
         ImmutableList<ModelPart> parts = modelRaft.m_6195_();
         if (parts != null) {
            if (modelPart.equals("bottom")) {
               return ModelRendererUtils.getModelRenderer(parts, 0);
            }

            if (modelPart.equals("paddle_left")) {
               return ModelRendererUtils.getModelRenderer(parts, 1);
            }

            if (modelPart.equals("paddle_right")) {
               return ModelRendererUtils.getModelRenderer(parts, 2);
            }
         }

         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "paddle_left", "paddle_right"};
   }

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BoatRenderer customRenderer = new BoatRenderer(renderManager.getContext(), false);
      EntityRenderer rendererCached = rendererCache.get(EntityType.f_20552_, index, () -> customRenderer);
      if (!(rendererCached instanceof BoatRenderer renderer)) {
         Config.warn("Not a BoatRender: " + rendererCached);
         return null;
      } else {
         return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
      }
   }
}
