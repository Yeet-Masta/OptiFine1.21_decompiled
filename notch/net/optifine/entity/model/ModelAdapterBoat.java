package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3803_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4313_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.minecraft.src.C_1205_.C_1208_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBoat extends ModelAdapter {
   public ModelAdapterBoat() {
      super(C_513_.f_20552_, "boat", 0.5F);
   }

   protected ModelAdapterBoat(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3803_(bakeModelLayer(C_141656_.m_171289_(C_1208_.OAK)));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3803_ modelBoat)) {
         return null;
      } else {
         ImmutableList<C_3889_> parts = modelBoat.m_6195_();
         if (parts != null) {
            if (modelPart.equals("bottom")) {
               return ModelRendererUtils.getModelRenderer(parts, 0);
            }

            if (modelPart.equals("back")) {
               return ModelRendererUtils.getModelRenderer(parts, 1);
            }

            if (modelPart.equals("front")) {
               return ModelRendererUtils.getModelRenderer(parts, 2);
            }

            if (modelPart.equals("right")) {
               return ModelRendererUtils.getModelRenderer(parts, 3);
            }

            if (modelPart.equals("left")) {
               return ModelRendererUtils.getModelRenderer(parts, 4);
            }

            if (modelPart.equals("paddle_left")) {
               return ModelRendererUtils.getModelRenderer(parts, 5);
            }

            if (modelPart.equals("paddle_right")) {
               return ModelRendererUtils.getModelRenderer(parts, 6);
            }
         }

         return modelPart.equals("bottom_no_water") ? modelBoat.m_102282_() : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left", "paddle_left", "paddle_right", "bottom_no_water"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4313_ renderer = new C_4313_(renderManager.getContext(), false);
      rendererCache.put(C_513_.f_20552_, index, renderer);
      return makeEntityRender(modelBase, shadowSize, renderer);
   }

   protected static IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, C_4313_ render) {
      if (!Reflector.RenderBoat_boatResources.exists()) {
         Config.warn("Field not found: RenderBoat.boatResources");
         return null;
      } else {
         Map<C_1208_, Pair<C_5265_, C_3840_>> resources = (Map<C_1208_, Pair<C_5265_, C_3840_>>)Reflector.RenderBoat_boatResources.getValue(render);
         if (resources instanceof ImmutableMap) {
            resources = new HashMap(resources);
            Reflector.RenderBoat_boatResources.setValue(render, resources);
         }

         for (C_1208_ type : new HashSet(resources.keySet())) {
            Pair<C_5265_, C_3840_> pair = (Pair<C_5265_, C_3840_>)resources.get(type);
            if (modelBase.getClass() == ((C_3840_)pair.getSecond()).getClass()) {
               pair = Pair.of((C_5265_)pair.getFirst(), modelBase);
               resources.put(type, pair);
            }
         }

         render.e = shadowSize;
         return render;
      }
   }
}
