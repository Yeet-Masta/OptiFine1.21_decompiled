package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3826_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4344_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterHoglin extends ModelAdapter {
   private static Map<String, Integer> mapParts = makeMapParts();

   public ModelAdapterHoglin() {
      super(C_513_.f_20456_, "hoglin", 0.7F);
   }

   public ModelAdapterHoglin(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3826_(bakeModelLayer(C_141656_.f_171184_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3826_ modelBoar)) {
         return null;
      } else if (mapParts.containsKey(modelPart)) {
         int index = (Integer)mapParts.get(modelPart);
         return (C_3889_)Reflector.getFieldValue(modelBoar, Reflector.ModelBoar_ModelRenderers, index);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])mapParts.keySet().toArray(new String[0]);
   }

   private static Map<String, Integer> makeMapParts() {
      Map<String, Integer> map = new LinkedHashMap();
      map.put("head", 0);
      map.put("right_ear", 1);
      map.put("left_ear", 2);
      map.put("body", 3);
      map.put("front_right_leg", 4);
      map.put("front_left_leg", 5);
      map.put("back_right_leg", 6);
      map.put("back_left_leg", 7);
      map.put("mane", 8);
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4344_ render = new C_4344_(renderManager.getContext());
      render.g = (C_3826_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
