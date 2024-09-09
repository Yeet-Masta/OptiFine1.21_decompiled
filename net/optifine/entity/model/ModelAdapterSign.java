package net.optifine.entity.model;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
   public ModelAdapterSign() {
      super(BlockEntityType.f_58924_, "sign", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new net.minecraft.client.renderer.blockentity.SignRenderer.SignModel(bakeModelLayer(ModelLayers.m_171291_(WoodType.f_61830_)));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof net.minecraft.client.renderer.blockentity.SignRenderer.SignModel modelSign)) {
         return null;
      } else if (modelPart.equals("board")) {
         return modelSign.f_173655_.getChildModelDeep("sign");
      } else {
         return modelPart.equals("stick") ? modelSign.f_173655_.getChildModelDeep("stick") : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"board", "stick"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58924_, index, () -> new net.minecraft.client.renderer.blockentity.SignRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof net.minecraft.client.renderer.blockentity.SignRenderer)) {
         return null;
      } else if (!Reflector.TileEntitySignRenderer_signModels.exists()) {
         Config.warn("Field not found: TileEntitySignRenderer.signModels");
         return null;
      } else {
         Map<WoodType, net.minecraft.client.renderer.blockentity.SignRenderer.SignModel> signModels = (Map<WoodType, net.minecraft.client.renderer.blockentity.SignRenderer.SignModel>)Reflector.getFieldValue(
            renderer, Reflector.TileEntitySignRenderer_signModels
         );
         if (signModels == null) {
            Config.warn("Field not found: TileEntitySignRenderer.signModels");
            return null;
         } else {
            if (signModels instanceof ImmutableMap) {
               signModels = new HashMap(signModels);
               Reflector.TileEntitySignRenderer_signModels.setValue(renderer, signModels);
            }

            for (WoodType type : new HashSet(signModels.keySet())) {
               signModels.put(type, (net.minecraft.client.renderer.blockentity.SignRenderer.SignModel)modelBase);
            }

            return renderer;
         }
      }
   }
}
