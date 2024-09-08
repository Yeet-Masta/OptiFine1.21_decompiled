package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_271057_;
import net.minecraft.src.C_271083_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterSniffer extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterSniffer() {
      super(C_513_.f_271264_, "sniffer", 1.1F);
   }

   public C_3840_ makeModel() {
      return new C_271057_(bakeModelLayer(C_141656_.f_271465_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_271057_ modelSniffer)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelSniffer.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelSniffer.m_142109_().getChildModelDeep(name);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return (String[])mapParts.keySet().toArray(new String[0]);
   }

   private static Map<String, String> makeMapParts() {
      Map<String, String> map = new LinkedHashMap();
      map.put("body", "body");
      map.put("back_left_leg", "left_hind_leg");
      map.put("back_right_leg", "right_hind_leg");
      map.put("middle_left_leg", "left_mid_leg");
      map.put("middle_right_leg", "right_mid_leg");
      map.put("front_left_leg", "left_front_leg");
      map.put("front_right_leg", "right_front_leg");
      map.put("head", "head");
      map.put("left_ear", "left_ear");
      map.put("right_ear", "right_ear");
      map.put("nose", "nose");
      map.put("lower_beak", "lower_beak");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_271083_ render = new C_271083_(renderManager.getContext());
      render.g = (C_271057_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
