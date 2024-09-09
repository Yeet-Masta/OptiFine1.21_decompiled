package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.Boat.Type;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBoat extends ModelAdapter {
   public ModelAdapterBoat() {
      super(EntityType.f_20552_, "boat", 0.5F);
   }

   protected ModelAdapterBoat(EntityType entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public Model makeModel() {
      return new BoatModel(bakeModelLayer(ModelLayers.m_171289_(Type.OAK)));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BoatModel modelBoat)) {
         return null;
      } else {
         ImmutableList parts = modelBoat.m_6195_();
         if (parts != null) {
            if (modelPart.equals("bottom")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 0);
            }

            if (modelPart.equals("back")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 1);
            }

            if (modelPart.equals("front")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 2);
            }

            if (modelPart.equals("right")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 3);
            }

            if (modelPart.equals("left")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 4);
            }

            if (modelPart.equals("paddle_left")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 5);
            }

            if (modelPart.equals("paddle_right")) {
               return ModelRendererUtils.getModelRenderer((ImmutableList)parts, 6);
            }
         }

         return modelPart.equals("bottom_no_water") ? modelBoat.m_102282_() : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"bottom", "back", "front", "right", "left", "paddle_left", "paddle_right", "bottom_no_water"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      EntityRenderDispatcher renderManager = Minecraft.m_91087_().m_91290_();
      BoatRenderer renderer = new BoatRenderer(renderManager.getContext(), false);
      rendererCache.put((EntityType)EntityType.f_20552_, index, (EntityRenderer)renderer);
      return makeEntityRender(modelBase, shadowSize, renderer);
   }

   protected static IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, BoatRenderer render) {
      if (!Reflector.RenderBoat_boatResources.exists()) {
         Config.warn("Field not found: RenderBoat.boatResources");
         return null;
      } else {
         Map resources = (Map)Reflector.RenderBoat_boatResources.getValue(render);
         if (resources instanceof ImmutableMap) {
            resources = new HashMap((Map)resources);
            Reflector.RenderBoat_boatResources.setValue(render, resources);
         }

         Collection types = new HashSet(((Map)resources).keySet());
         Iterator var5 = types.iterator();

         while(var5.hasNext()) {
            Boat.Type type = (Boat.Type)var5.next();
            Pair pair = (Pair)((Map)resources).get(type);
            if (modelBase.getClass() == ((Model)pair.getSecond()).getClass()) {
               pair = Pair.of((ResourceLocation)pair.getFirst(), modelBase);
               ((Map)resources).put(type, pair);
            }
         }

         render.f_114477_ = shadowSize;
         return render;
      }
   }
}
