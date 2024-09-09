package net.optifine.entity.model;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.HorseRenderer;
import net.minecraft.world.entity.EntityType;
import net.optifine.reflect.Reflector;

public class ModelAdapterHorse extends ModelAdapter {
   private static Map mapParts = makeMapParts();
   private static Map mapPartsNeck = makeMapPartsNeck();
   private static Map mapPartsHead = makeMapPartsHead();
   private static Map mapPartsBody = makeMapPartsBody();

   public ModelAdapterHorse() {
      super(EntityType.f_20457_, "horse", 0.75F);
   }

   protected ModelAdapterHorse(EntityType type, String name, float shadowSize) {
      super(type, name, shadowSize);
   }

   public Model makeModel() {
      return new HorseModel(bakeModelLayer(ModelLayers.f_171186_));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof HorseModel modelHorse)) {
         return null;
      } else if (mapParts.containsKey(modelPart)) {
         int index = (Integer)mapParts.get(modelPart);
         return (ModelPart)Reflector.getFieldValue(modelHorse, Reflector.ModelHorse_ModelRenderers, index);
      } else {
         ModelPart modelBody;
         String name;
         if (mapPartsNeck.containsKey(modelPart)) {
            modelBody = this.getModelRenderer(modelHorse, "neck");
            name = (String)mapPartsNeck.get(modelPart);
            return modelBody.m_171324_(name);
         } else if (mapPartsHead.containsKey(modelPart)) {
            modelBody = this.getModelRenderer(modelHorse, "head");
            name = (String)mapPartsHead.get(modelPart);
            return modelBody.m_171324_(name);
         } else if (mapPartsBody.containsKey(modelPart)) {
            modelBody = this.getModelRenderer(modelHorse, "body");
            name = (String)mapPartsBody.get(modelPart);
            return modelBody.m_171324_(name);
         } else {
            return null;
         }
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"body", "neck", "back_left_leg", "back_right_leg", "front_left_leg", "front_right_leg", "child_back_left_leg", "child_back_right_leg", "child_front_left_leg", "child_front_right_leg", "tail", "saddle", "head", "mane", "mouth", "left_ear", "right_ear", "left_bit", "right_bit", "left_rein", "right_rein", "headpiece", "noseband"};
   }

   private static Map makeMapParts() {
      Map map = new LinkedHashMap();
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

   private static Map makeMapPartsNeck() {
      Map map = new LinkedHashMap();
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

   private static Map makeMapPartsBody() {
      Map map = new LinkedHashMap();
      map.put("tail", "tail");
      map.put("saddle", "saddle");
      return map;
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      HorseRenderer render = new HorseRenderer(renderManager.getContext());
      render.f_115290_ = (HorseModel)modelBase;
      render.f_114477_ = shadowSize;
      return render;
   }

   private static Map makeMapPartsHead() {
      Map map = new LinkedHashMap();
      map.put("left_ear", "left_ear");
      map.put("right_ear", "right_ear");
      return map;
   }
}
