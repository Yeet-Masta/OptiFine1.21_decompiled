package net.optifine.entity.model;

import java.util.Map;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.level.block.SkullBlock.Type;
import net.minecraft.world.level.block.SkullBlock.Types;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterHead extends ModelAdapter {
   private ModelLayerLocation modelLayer;
   private Types skullBlockType;

   public ModelAdapterHead(String name, ModelLayerLocation modelLayer, Types skullBlockType) {
      super(BlockEntityType.f_58931_, name, 0.0F);
      this.modelLayer = modelLayer;
      this.skullBlockType = skullBlockType;
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new SkullModel(bakeModelLayer(this.modelLayer));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof SkullModel modelSkul)) {
         return null;
      } else if (modelPart.equals("head")) {
         return (net.minecraft.client.model.geom.ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkul, 1);
      } else {
         return modelPart.equals("root") ? (net.minecraft.client.model.geom.ModelPart)Reflector.ModelSkull_renderers.getValue(modelSkul, 0) : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"head", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58931_, index, () -> new net.minecraft.client.renderer.blockentity.SkullBlockRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof net.minecraft.client.renderer.blockentity.SkullBlockRenderer)) {
         return null;
      } else {
         Map<Type, SkullModelBase> models = net.minecraft.client.renderer.blockentity.SkullBlockRenderer.models;
         if (models == null) {
            Config.warn("Field not found: SkullBlockRenderer.models");
            return null;
         } else {
            models.put(this.skullBlockType, (SkullModelBase)modelBase);
            return renderer;
         }
      }
   }
}
