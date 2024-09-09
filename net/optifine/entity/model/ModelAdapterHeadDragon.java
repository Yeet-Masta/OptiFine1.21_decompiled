package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.dragon.DragonHeadModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapterHead {
   public ModelAdapterHeadDragon() {
      super("head_dragon", (ModelLayerLocation)null, Types.DRAGON);
   }

   public Model makeModel() {
      return new DragonHeadModel(bakeModelLayer(ModelLayers.f_171135_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof DragonHeadModel modelDragonHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head);
      } else {
         return modelPart.equals("jaw") ? (ModelPart)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw) : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"head", "jaw"};
   }
}
