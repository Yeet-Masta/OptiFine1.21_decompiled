package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3888_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_1897_.C_1899_;
import net.optifine.reflect.Reflector;

public class ModelAdapterHeadDragon extends ModelAdapterHead {
   public ModelAdapterHeadDragon() {
      super("head_dragon", null, C_1899_.DRAGON);
   }

   public C_3840_ makeModel() {
      return new C_3888_(bakeModelLayer(C_141656_.f_171135_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3888_ modelDragonHead)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (C_3889_)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_head);
      } else {
         return modelPart.equals("jaw") ? (C_3889_)Reflector.getFieldValue(modelDragonHead, Reflector.ModelDragonHead_jaw) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "jaw"};
   }
}
