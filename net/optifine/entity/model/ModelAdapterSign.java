package net.optifine.entity.model;

import com.google.common.collect.ImmutableMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterSign extends ModelAdapter {
   public ModelAdapterSign() {
      super(BlockEntityType.f_58924_, "sign", 0.0F);
   }

   public Model makeModel() {
      return new SignRenderer.SignModel(bakeModelLayer(ModelLayers.m_171291_(WoodType.f_61830_)));
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
      if (!(model instanceof SignRenderer.SignModel modelSign)) {
         return null;
      } else if (modelPart.equals("board")) {
         return modelSign.f_173655_.getChildModelDeep("sign");
      } else {
         return modelPart.equals("stick") ? modelSign.f_173655_.getChildModelDeep("stick") : null;
      }
   }

   public String[] getModelRendererNames() {
      return new String[]{"board", "stick"};
   }

   public IEntityRenderer makeEntityRender(Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58924_, index, () -> {
         return new SignRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof SignRenderer)) {
         return null;
      } else if (!Reflector.TileEntitySignRenderer_signModels.exists()) {
         Config.warn("Field not found: TileEntitySignRenderer.signModels");
         return null;
      } else {
         Map signModels = (Map)Reflector.getFieldValue(renderer, Reflector.TileEntitySignRenderer_signModels);
         if (signModels == null) {
            Config.warn("Field not found: TileEntitySignRenderer.signModels");
            return null;
         } else {
            if (signModels instanceof ImmutableMap) {
               signModels = new HashMap((Map)signModels);
               Reflector.TileEntitySignRenderer_signModels.setValue(renderer, signModels);
            }

            Collection types = new HashSet(((Map)signModels).keySet());
            Iterator var9 = types.iterator();

            while(var9.hasNext()) {
               WoodType type = (WoodType)var9.next();
               ((Map)signModels).put(type, (SignRenderer.SignModel)modelBase);
            }

            return renderer;
         }
      }
   }
}
