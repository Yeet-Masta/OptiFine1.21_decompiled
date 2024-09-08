package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4241_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;

public class ModelAdapterBed extends ModelAdapter {
   public ModelAdapterBed() {
      super(C_1992_.f_58940_, "bed", 0.0F);
   }

   public C_3840_ makeModel() {
      return new BedModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof BedModel modelBed)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBed.headPiece;
      } else if (modelPart.equals("foot")) {
         return modelBed.footPiece;
      } else {
         C_3889_[] legs = modelBed.legs;
         if (legs != null) {
            if (modelPart.equals("leg1")) {
               return legs[0];
            }

            if (modelPart.equals("leg2")) {
               return legs[1];
            }

            if (modelPart.equals("leg3")) {
               return legs[2];
            }

            if (modelPart.equals("leg4")) {
               return legs[3];
            }
         }

         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "foot", "leg1", "leg2", "leg3", "leg4"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58940_, index, () -> new C_4241_(dispatcher.getContext()));
      if (!(renderer instanceof C_4241_)) {
         return null;
      } else if (!(modelBase instanceof BedModel bedModel)) {
         Config.warn("Not a BedModel: " + modelBase);
         return null;
      } else {
         return bedModel.updateRenderer(renderer);
      }
   }
}
