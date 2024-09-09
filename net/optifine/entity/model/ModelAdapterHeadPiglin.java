package net.optifine.entity.model;

import net.minecraft.client.model.PiglinHeadModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadPiglin extends ModelAdapterHead {
   public ModelAdapterHeadPiglin() {
      super("head_piglin", null, Types.PIGLIN);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new PiglinHeadModel(bakeModelLayer(ModelLayers.f_260668_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof PiglinHeadModel modelPiglinHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 0);
      } else if (modelPart.equals("left_ear")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 1);
      } else {
         return modelPart.equals("right_ear")
            ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 2)
            : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "left_ear", "right_ear"};
   }
}
