package net.optifine.entity.model;

import net.minecraft.client.model.ShulkerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterShulkerBox extends ModelAdapter {
   public ModelAdapterShulkerBox() {
      super(BlockEntityType.f_58939_, "shulker_box", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new ShulkerModel(bakeModelLayer(ModelLayers.f_171180_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof ShulkerModel modelShulker)) {
         return null;
      } else if (modelPart.equals("base")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 0);
      } else if (modelPart.equals("lid")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 1);
      } else {
         return modelPart.equals("head") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelShulker_ModelRenderers.getValue(modelShulker, 2) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"base", "lid", "head"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58939_, index, () -> new ShulkerBoxRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof ShulkerBoxRenderer)) {
         return null;
      } else if (!Reflector.TileEntityShulkerBoxRenderer_model.exists()) {
         Config.warn("Field not found: TileEntityShulkerBoxRenderer.model");
         return null;
      } else {
         Reflector.setFieldValue(renderer, Reflector.TileEntityShulkerBoxRenderer_model, modelBase);
         return renderer;
      }
   }
}
