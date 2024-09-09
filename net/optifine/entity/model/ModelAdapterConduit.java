package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.ConduitRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterConduit extends ModelAdapter {
   public ModelAdapterConduit() {
      super(BlockEntityType.f_58941_, "conduit", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ConduitModel();
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ConduitModel modelConduit)) {
         return null;
      } else if (modelPart.equals("eye")) {
         return modelConduit.eye;
      } else if (modelPart.equals("wind")) {
         return modelConduit.wind;
      } else if (modelPart.equals("base")) {
         return modelConduit.base;
      } else {
         return modelPart.equals("cage") ? modelConduit.cage : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"eye", "wind", "base", "cage"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58941_, index, () -> new ConduitRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof ConduitRenderer)) {
         return null;
      } else if (!(modelBase instanceof ConduitModel conduitModel)) {
         Config.warn("Not a conduit model: " + modelBase);
         return null;
      } else {
         return conduitModel.updateRenderer(renderer);
      }
   }
}
