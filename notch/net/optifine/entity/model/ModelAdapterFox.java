package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3821_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4340_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterFox extends ModelAdapter {
   private static Map<String, Integer> mapPartFields = null;

   public ModelAdapterFox() {
      super(C_513_.f_20452_, "fox", 0.4F);
   }

   public C_3840_ makeModel() {
      return new C_3821_(bakeModelLayer(C_141656_.f_171148_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3821_ modelFox)) {
         return null;
      } else {
         Map<String, Integer> mapParts = getMapPartFields();
         if (mapParts.containsKey(modelPart)) {
            int index = (Integer)mapParts.get(modelPart);
            return (C_3889_)Reflector.getFieldValue(modelFox, Reflector.ModelFox_ModelRenderers, index);
         } else {
            return null;
         }
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])getMapPartFields().keySet().toArray(new String[0]);
   }

   private static Map<String, Integer> getMapPartFields() {
      if (mapPartFields != null) {
         return mapPartFields;
      } else {
         mapPartFields = new LinkedHashMap();
         mapPartFields.put("head", 0);
         mapPartFields.put("body", 1);
         mapPartFields.put("leg1", 2);
         mapPartFields.put("leg2", 3);
         mapPartFields.put("leg3", 4);
         mapPartFields.put("leg4", 5);
         mapPartFields.put("tail", 6);
         return mapPartFields;
      }
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4340_ render = new C_4340_(renderManager.getContext());
      render.g = (C_3821_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
