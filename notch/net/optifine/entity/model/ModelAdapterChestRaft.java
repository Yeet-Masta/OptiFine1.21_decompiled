package net.optifine.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.src.C_141656_;
import net.minecraft.src.C_243630_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4313_;
import net.minecraft.src.C_4330_;
import net.minecraft.src.C_4331_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_1205_.C_1208_;
import net.optifine.Config;
import net.optifine.util.ArrayUtils;

public class ModelAdapterChestRaft extends ModelAdapterRaft {
   public ModelAdapterChestRaft() {
      super(C_513_.f_217016_, "chest_raft", 0.5F);
   }

   public C_3840_ makeModel() {
      return new C_243630_(bakeModelLayer(C_141656_.m_233550_(C_1208_.BAMBOO)));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_243630_ modelChestRaft)) {
         return null;
      } else {
         ImmutableList<C_3889_> parts = modelChestRaft.c();
         if (parts != null) {
            if (modelPart.equals("chest_base")) {
               return ModelRendererUtils.getModelRenderer(parts, 3);
            }

            if (modelPart.equals("chest_lid")) {
               return ModelRendererUtils.getModelRenderer(parts, 4);
            }

            if (modelPart.equals("chest_knob")) {
               return ModelRendererUtils.getModelRenderer(parts, 5);
            }
         }

         return super.getModelRenderer(modelChestRaft, modelPart);
      }
   }

   @Override
   public String[] getModelRendererNames() {
      String[] names = super.getModelRendererNames();
      return (String[])ArrayUtils.addObjectsToArray(names, new String[]{"chest_base", "chest_lid", "chest_knob"});
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4330_ renderManager = C_3391_.m_91087_().m_91290_();
      C_4313_ customRenderer = new C_4313_(renderManager.getContext(), true);
      C_4331_ rendererCached = rendererCache.get(C_513_.f_217016_, index, () -> customRenderer);
      if (!(rendererCached instanceof C_4313_ renderer)) {
         Config.warn("Not a BoatRender: " + rendererCached);
         return null;
      } else {
         return ModelAdapterBoat.makeEntityRender(modelBase, shadowSize, renderer);
      }
   }
}
