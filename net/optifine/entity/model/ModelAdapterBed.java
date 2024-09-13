package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterBed extends ModelAdapter {
   public ModelAdapterBed() {
      super(BlockEntityType.f_58940_, "bed", 0.0F);
   }

   @Override
   public Model makeModel() {
      return new BedModel();
   }

   @Override
   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof BedModel modelBed)) {
         return null;
      } else if (modelPart.equals("head")) {
         return modelBed.headPiece;
      } else if (modelPart.equals("foot")) {
         return modelBed.footPiece;
      } else {
         ModelPart[] legs = modelBed.legs;
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

   @Override
   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58940_, index, () -> new BedRenderer(dispatcher.getContext()));
      if (!(renderer instanceof BedRenderer)) {
         return null;
      } else if (!(modelBase instanceof BedModel bedModel)) {
         Config.warn("Not a BedModel: " + modelBase);
         return null;
      } else {
         return bedModel.updateRenderer(renderer);
      }
   }
}
