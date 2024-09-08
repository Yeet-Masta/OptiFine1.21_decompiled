package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3798_;
import net.minecraft.src.C_3799_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4307_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterArmorStand extends ModelAdapterBiped {
   public ModelAdapterArmorStand() {
      super(C_513_.f_20529_, "armor_stand", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3799_(bakeModelLayer(C_141656_.f_171155_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3799_ modelArmorStand)) {
         return null;
      } else if (modelPart.equals("right")) {
         return (C_3889_)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 0);
      } else if (modelPart.equals("left")) {
         return (C_3889_)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 1);
      } else if (modelPart.equals("waist")) {
         return (C_3889_)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 2);
      } else {
         return modelPart.equals("base")
            ? (C_3889_)Reflector.getFieldValue(modelArmorStand, Reflector.ModelArmorStand_ModelRenderers, 3)
            : super.getModelRenderer(modelArmorStand, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])Config.addObjectsToArray(names, new String[]{"right", "left", "waist", "base"});
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4307_ render = new C_4307_(renderManager.getContext());
      render.g = (C_3798_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
