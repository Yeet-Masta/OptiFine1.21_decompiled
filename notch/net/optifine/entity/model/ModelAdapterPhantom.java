package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3850_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4374_;
import net.minecraft.src.C_513_;

public class ModelAdapterPhantom extends ModelAdapter {
   private static Map<String, String> mapPartFields = null;

   public ModelAdapterPhantom() {
      super(C_513_.f_20509_, "phantom", 0.75F);
   }

   public C_3840_ makeModel() {
      return new C_3850_(bakeModelLayer(C_141656_.f_171204_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3850_ modelPhantom)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelPhantom.m_142109_();
      } else {
         Map<String, String> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            String name = (String)mapParts.get(modelPart);
            return modelPhantom.m_142109_().getChildModelDeep(name);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, String> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("body", "body");
         mapPartFields.put("head", "head");
         mapPartFields.put("left_wing", "left_wing_base");
         mapPartFields.put("left_wing_tip", "left_wing_tip");
         mapPartFields.put("right_wing", "right_wing_base");
         mapPartFields.put("right_wing_tip", "right_wing_tip");
         mapPartFields.put("tail", "tail_base");
         mapPartFields.put("tail2", "tail_tip");
         mapPartFields.put("root", "root");
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4374_ render = new C_4374_(renderManager.getContext());
      render.g = (C_3850_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
