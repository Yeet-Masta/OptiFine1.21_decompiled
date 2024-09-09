package net.optifine.entity.model;

import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.reflect.Reflector;
import net.optifine.util.Either;

public class ModelAdapterShield extends ModelAdapter {
   public ModelAdapterShield() {
      super((Either<EntityType, BlockEntityType>)null, "shield", 0.0F, null);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ShieldModel(bakeModelLayer(ModelLayers.f_171179_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ShieldModel modelShield)) {
         return null;
      } else if (modelPart.equals("plate")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 1);
      } else if (modelPart.equals("handle")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 2);
      } else {
         return modelPart.equals("root") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShield_ModelRenderers.getValue(modelShield, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"plate", "handle", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      IEntityRenderer rc = new VirtualEntityRenderer(modelBase);
      return rc;
   }
}
