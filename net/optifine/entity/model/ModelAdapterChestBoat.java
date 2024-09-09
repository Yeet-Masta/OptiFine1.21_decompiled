package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestBoat extends ModelAdapterBoat {
   public ModelAdapterChestBoat() {
      super(EntityType.f_217016_, "chest_boat", 0.5F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ChestBoatModel(bakeModelLayer(ModelLayers.m_233550_(Type.OAK)));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ChestBoatModel modelChestBoat)) {
         return null;
      } else {
         ImmutableList<net.minecraft.client.model.geom.ModelPart> parts = modelChestBoat.m_6195_();
         if (parts != null) {
            if (modelPart.equals("chest_base")) {
               return ModelRendererUtils.getModelRenderer(parts, 7);
            }

            if (modelPart.equals("chest_lid")) {
               return ModelRendererUtils.getModelRenderer(parts, 8);
            }

            if (modelPart.equals("chest_knob")) {
               return ModelRendererUtils.getModelRenderer(parts, 9);
            }
         }

         return super.getModelRenderer(modelChestBoat, modelPart);
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
      BoatRenderer renderer = new BoatRenderer(renderManager.getContext(), true);
      rendererCache.put(EntityType.f_217016_, index, renderer);
      return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
   }
}
