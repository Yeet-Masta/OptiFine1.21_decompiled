package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3804_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4249_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBook extends ModelAdapter {
   public ModelAdapterBook() {
      super(C_1992_.f_58928_, "enchanting_book", 0.0F, new String[]{"book"});
   }

   protected ModelAdapterBook(C_1992_ tileEntityType, String name, float shadowSize) {
      super(tileEntityType, name, shadowSize);
   }

   public C_3840_ makeModel() {
      return new C_3804_(bakeModelLayer(C_141656_.f_171271_));
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof C_3804_ modelBook)) {
         return null;
      } else {
         C_3889_ root = (C_3889_)Reflector.ModelBook_root.getValue(modelBook);
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

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58928_, index, () -> new C_4249_(dispatcher.getContext()));
      if (!(renderer instanceof C_4249_)) {
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
