package net.optifine.entity.model;

import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.blockentity.EnchantTableRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBook extends ModelAdapter {
   public ModelAdapterBook() {
      super(BlockEntityType.f_58928_, "enchanting_book", 0.0F, new String[]{"book"});
   }

   protected ModelAdapterBook(BlockEntityType tileEntityType, String name, float shadowSize) {
      super(tileEntityType, name, shadowSize);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BookModel(bakeModelLayer(ModelLayers.f_171271_));
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
      if (!(model instanceof BookModel modelBook)) {
         return null;
      } else {
         net.minecraft.client.model.geom.ModelPart root = (net.minecraft.client.model.geom.ModelPart)Reflector.ModelBook_root.getValue(modelBook);
         if (root != null) {
            if (modelPart.equals("cover_right")) {
               return root.getChildModelDeep("left_lid");
            }

            if (modelPart.equals("cover_left")) {
               return root.getChildModelDeep("right_lid");
            }

            if (modelPart.equals("pages_right")) {
               return root.getChildModelDeep("left_pages");
            }

            if (modelPart.equals("pages_left")) {
               return root.getChildModelDeep("right_pages");
            }

            if (modelPart.equals("flipping_page_right")) {
               return root.getChildModelDeep("flip_page1");
            }

            if (modelPart.equals("flipping_page_left")) {
               return root.getChildModelDeep("flip_page2");
            }

            if (modelPart.equals("book_spine")) {
               return root.getChildModelDeep("seam");
            }

            if (modelPart.equals("root")) {
               return root;
            }
         }

         return null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"cover_right", "cover_left", "pages_right", "pages_left", "flipping_page_right", "flipping_page_left", "book_spine", "root"};
   }

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model modelBase, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58928_, index, () -> new EnchantTableRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof EnchantTableRenderer)) {
         return null;
      } else if (!Reflector.TileEntityEnchantmentTableRenderer_modelBook.exists()) {
         Config.warn("Field not found: TileEntityEnchantmentTableRenderer.modelBook");
         return null;
      } else {
         Reflector.setFieldValue(renderer, Reflector.TileEntityEnchantmentTableRenderer_modelBook, modelBase);
         return renderer;
      }
   }
}
