package net.optifine.entity.model;

import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapterHead {
   public ModelAdapterHeadDragon() {
      super("head_dragon", null, Types.DRAGON);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new DragonHeadModel(bakeModelLayer(ModelLayers.f_171135_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof DragonHeadModel modelDragonHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head);
      } else {
         return modelPart.equals("jaw")
            ? (net.minecraft.client.model.geom.ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw)
            : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "jaw"};
   }
}
