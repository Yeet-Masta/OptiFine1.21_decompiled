package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_213401_;
import net.minecraft.src.C_213431_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;

public class ModelAdapterWarden extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterWarden() {
      super(C_513_.f_217015_, "warden", 0.9F);
   }

   public C_3840_ makeModel() {
      return new C_213401_(bakeModelLayer(C_141656_.f_233548_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_213401_ modelWarden)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelWarden.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelWarden.m_142109_().getChildModelDeep(name);
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
      map.put("body", "bone");
      map.put("torso", "body");
      map.put("head", "head");
      map.put("right_leg", "right_leg");
      map.put("left_leg", "left_leg");
      map.put("right_arm", "right_arm");
      map.put("left_arm", "left_arm");
      map.put("right_tendril", "right_tendril");
      map.put("left_tendril", "left_tendril");
      map.put("right_ribcage", "right_ribcage");
      map.put("left_ribcage", "left_ribcage");
      map.put("root", "root");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_213431_ render = new C_213431_(renderManager.getContext());
      render.g = (C_213401_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
