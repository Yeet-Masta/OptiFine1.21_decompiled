package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_243524_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4313_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_1205_.C_1208_;
import net.optifine.Config;

public class ModelAdapterRaft extends ModelAdapter {
   public ModelAdapterRaft() {
      super(C_513_.f_20552_, "raft", 0.5F);
   }

   protected ModelAdapterRaft(C_513_ entityType, String name, float shadowSize) {
      super(entityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_243524_(bakeModelLayer(C_141656_.m_171289_(C_1208_.BAMBOO)));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_243524_ modelRaft)) {
         return null;
      } else {
         ImmutableList<C_3889_> parts = modelRaft.m_6195_();
         if (parts != null) {
            if (modelPart.equals("bottom")) {
               return ModelRendererUtils.getModelRenderer(parts, 0);
            }

            if (modelPart.equals("paddle_left")) {
               return ModelRendererUtils.getModelRenderer(parts, 1);
            }

            if (modelPart.equals("paddle_right")) {
               return ModelRendererUtils.getModelRenderer(parts, 2);
            }
         }

         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"bottom", "paddle_left", "paddle_right"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4313_ customRenderer = new C_4313_(renderManager.getContext(), false);
      C_4331_ rendererCached = rendererCache.get(C_513_.f_20552_, index, () -> customRenderer);
      if (!(rendererCached instanceof C_4313_ renderer)) {
         Config.warn("Not a BoatRender: " + rendererCached);
         return null;
      } else {
         return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
      }
   }
}
