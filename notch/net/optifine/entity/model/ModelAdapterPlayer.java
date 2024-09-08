package net.optifine.entity.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3853_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_513_;

public abstract class ModelAdapterPlayer extends ModelAdapterBiped {
   protected ModelAdapterPlayer(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (model instanceof C_3853_ playerModel) {
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
