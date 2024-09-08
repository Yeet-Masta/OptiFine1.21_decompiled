package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_260365_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_1897_.C_1899_;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadPiglin extends ModelAdapterHead {
   public ModelAdapterHeadPiglin() {
      super("head_piglin", null, C_1899_.PIGLIN);
   }

   public C_3840_ makeModel() {
      return new C_260365_(bakeModelLayer(C_141656_.f_260668_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_260365_ modelPiglinHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 0);
      } else if (modelPart.equals("left_ear")) {
         return (C_3889_)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 1);
      } else {
         return modelPart.equals("right_ear") ? (C_3889_)Reflector.ModelPiglinHead_ModelRenderers.getValue(modelPiglinHead, 2) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "left_ear", "right_ear"};
   }
}
