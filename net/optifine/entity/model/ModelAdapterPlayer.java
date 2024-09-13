package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EntityType;

public abstract class ModelAdapterPlayer extends ModelAdapterBiped {
   protected ModelAdapterPlayer(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (model instanceof PlayerModel playerModel) {
         if (modelPart.equals("left_sleeve")) {
            return playerModel.f_103374_;
         }

         if (modelPart.equals("right_sleeve")) {
            return playerModel.f_103375_;
         }

         if (modelPart.equals("left_pants")) {
            return playerModel.f_103376_;
         }

         if (modelPart.equals("right_pants")) {
            return playerModel.f_103377_;
         }

         if (modelPart.equals("jacket")) {
            return playerModel.f_103378_;
         }
      }

      return super.getModelRenderer(model, modelPart);
   }

   @Override
   public String[] getModelRendererNames() {
      List<String> names = new ArrayList(Arrays.asList(super.getModelRendererNames()));
      names.add("left_sleeve");
      names.add("right_sleeve");
      names.add("left_pants");
      names.add("right_pants");
      names.add("jacket");
      return (String[])names.toArray(new String[names.size()]);
   }
}
