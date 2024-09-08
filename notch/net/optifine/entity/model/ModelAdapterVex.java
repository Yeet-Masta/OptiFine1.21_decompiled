package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3880_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4405_;
import net.minecraft.src.C_513_;

public class ModelAdapterVex extends ModelAdapter {
   private static Map<String, String> mapParts = makeMapParts();

   public ModelAdapterVex() {
      super(C_513_.f_20491_, "vex", 0.3F);
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3880_ modelVex)) {
         return null;
      } else if (modelPart.equals("root")) {
         return modelVex.m_142109_();
      } else if (mapParts.containsKey(modelPart)) {
         String name = (String)mapParts.get(modelPart);
         return modelVex.m_142109_().getChildModelDeep(name);
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
      map.put("head", "head");
      map.put("right_arm", "right_arm");
      map.put("left_arm", "left_arm");
      map.put("right_wing", "right_wing");
      map.put("left_wing", "left_wing");
      map.put("root", "root");
      return map;
   }

   public C_3840_ makeModel() {
      return new C_3880_(bakeModelLayer(C_141656_.f_171209_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4405_ render = new C_4405_(renderManager.getContext());
      render.g = (C_3880_)modelBase;
      render.e = shadowSize;
      return render;
   }
}
