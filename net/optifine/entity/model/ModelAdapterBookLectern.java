package net.optifine.entity.model;

import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.LecternRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBookLectern extends ModelAdapterBook {
   public ModelAdapterBookLectern() {
      super(BlockEntityType.f_58908_, "lectern_book", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BookModel(bakeModelLayer(ModelLayers.f_171271_));
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58908_, index, () -> new LecternRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof LecternRenderer)) {
         return null;
      } else if (!Reflector.TileEntityLecternRenderer_modelBook.exists()) {
         Config.warn("Field not found: TileEntityLecternRenderer.modelBook");
         return null;
      } else {
         Reflector.setFieldValue(renderer, Reflector.TileEntityLecternRenderer_modelBook, modelBase);
         return renderer;
      }
   }
}
