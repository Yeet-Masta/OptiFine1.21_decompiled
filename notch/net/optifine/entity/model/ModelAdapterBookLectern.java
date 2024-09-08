package net.optifine.entity.model;

import net.minecraft.src.C_141656_;
import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3804_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4250_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ModelAdapterBookLectern extends ModelAdapterBook {
   public ModelAdapterBookLectern() {
      super(C_1992_.f_58908_, "lectern_book", 0.0F);
   }

   public C_3840_ makeModel() {
      return new C_3804_(bakeModelLayer(C_141656_.f_171271_));
   }

   public IEntityRenderer makeEntityRender(C_3840_ modelBase, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58908_, index, () -> new C_4250_(dispatcher.getContext()));
      if (!(renderer instanceof C_4250_)) {
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
