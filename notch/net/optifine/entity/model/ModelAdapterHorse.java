package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3827_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4345_;
import net.minecraft.src.C_513_;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse extends ModelAdapter {
   private static Map<String, Integer> mapParts = makeMapParts();
   private static Map<String, String> mapPartsNeck = makeMapPartsNeck();
   private static Map<String, String> mapPartsHead = makeMapPartsHead();
   private static Map<String, String> mapPartsBody = makeMapPartsBody();

   public ModelAdapterHorse() {
      super(C_513_.f_20457_, "horse", 0.75F);
   }

   protected ModelAdapterHorse(C_513_ type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3827_(bakeModelLayer(C_141656_.f_171186_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3827_ modelHorse)) {
         return null;
      } else if (mapParts.containsKey(modelPart)) {
         int index = (Integer)mapParts.get(modelPart);
         return (C_3889_)Reflector.getFieldValue(modelHorse, Reflector.ModelHorse_ModelRenderers, index);
      } else if (mapPartsNeck.containsKey(modelPart)) {
         C_3889_ modelNeck = this.getModelRenderer(modelHorse, "neck");
         String name = (String)mapPartsNeck.get(modelPart);
         return modelNeck.m_171324_(name);
      } else if (mapPartsHead.containsKey(modelPart)) {
         C_3889_ modelHead = this.getModelRenderer(modelHorse, "head");
         String name = (String)mapPartsHead.get(modelPart);
         return modelHead.m_171324_(name);
      } else if (mapPartsBody.containsKey(modelPart)) {
         C_3889_ modelBody = this.getModelRenderer(modelHorse, "body");
         String name = (String)mapPartsBody.get(modelPart);
         return modelBody.m_171324_(name);
      } else {
         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{
         "body",
         "neck",
         "back_left_leg",
         "back_right_leg",
         "front_left_leg",
         "front_right_leg",
         "child_back_left_leg",
         "child_back_right_leg",
         "child_front_left_leg",
         "child_front_right_leg",
         "tail",
         "saddle",
         "head",
         "mane",
         "mouth",
         "left_ear",
         "right_ear",
         "left_bit",
         "right_bit",
         "left_rein",
         "right_rein",
         "headpiece",
         "noseband"
      };
   }

   private static Map<String, Integer> makeMapParts() {
      Map<String, Integer> map = new LinkedHashMap();
      map.put("body", 0);
      map.put("neck", 1);
      map.put("back_right_leg", 2);
      map.put("back_left_leg", 3);
      map.put("front_right_leg", 4);
      map.put("front_left_leg", 5);
      map.put("child_back_right_leg", 6);
      map.put("child_back_left_leg", 7);
      map.put("child_front_right_leg", 8);
      map.put("child_front_left_leg", 9);
      return map;
   }

   private static Map<String, String> makeMapPartsNeck() {
      Map<String, String> map = new LinkedHashMap();
      map.put("head", "head");
      map.put("mane", "mane");
      map.put("mouth", "upper_mouth");
      map.put("left_bit", "left_saddle_mouth");
      map.put("right_bit", "right_saddle_mouth");
      map.put("left_rein", "left_saddle_line");
      map.put("right_rein", "right_saddle_line");
      map.put("headpiece", "head_saddle");
      map.put("noseband", "mouth_saddle_wrap");
      return map;
   }

   private static Map<String, String> makeMapPartsBody() {
      Map<String, String> map = new LinkedHashMap();
      map.put("tail", "tail");
      map.put("saddle", "saddle");
      return map;
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4345_ render = new C_4345_(renderManager.getContext());
      render.g = (C_3827_)modelBase;
      render.e = shadowSize;
      return render;
   }

   private static Map<String, String> makeMapPartsHead() {
      Map<String, String> map = new LinkedHashMap();
      map.put("left_ear", "left_ear");
      map.put("right_ear", "right_ear");
      return map;
   }
}
