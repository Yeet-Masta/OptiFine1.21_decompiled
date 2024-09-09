package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.optifine.Config;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestRaft extends ModelAdapterRaft {
   public ModelAdapterChestRaft() {
      super(EntityType.f_217016_, "chest_raft", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ChestRaftModel(bakeModelLayer(ModelLayers.m_233550_(Type.BAMBOO)));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ChestRaftModel modelChestRaft)) {
         return null;
      } else {
         ImmutableList<net.minecraft.client.model.geom.ModelPart> parts = modelChestRaft.m_6195_();
         if (parts != null) {
            if (modelPart.equals("chest_base")) {
               return ModelRendererUtils.getModelRenderer(parts, 3);
            }

            if (modelPart.equals("chest_lid")) {
               return ModelRendererUtils.getModelRenderer(parts, 4);
            }

            if (modelPart.equals("chest_knob")) {
               return ModelRendererUtils.getModelRenderer(parts, 5);
            }
         }

         return super.getModelRenderer(modelChestRaft, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])ArrayUtils.addObjectsToArray(names, new String[]{"chest_base", "chest_lid", "chest_knob"});
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.entity.EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BoatRenderer customRenderer = new BoatRenderer(renderManager.getContext(), true);
      net.minecraft.client.renderer.entity.EntityRenderer rendererCached = rendererCache.get(EntityType.f_217016_, index, () -> customRenderer);
      if (!(rendererCached instanceof BoatRenderer renderer)) {
         Config.warn("Not a BoatRender: " + rendererCached);
         return null;
      } else {
         return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
      }
   }
}
