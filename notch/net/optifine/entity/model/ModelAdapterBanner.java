package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4238_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;

public class ModelAdapterBanner extends ModelAdapter {
   public ModelAdapterBanner() {
      super(C_1992_.f_58935_, "banner", 0.0F);
   }

   public C_3840_ makeModel() {
      return new BannerModel();
   }

   public C_3889_ getModelRenderer(C_3840_ model, String modelPart) {
      if (!(model instanceof BannerModel modelBanner)) {
         return null;
      } else if (modelPart.equals("slate")) {
         return modelBanner.bannerSlate;
      } else if (modelPart.equals("stand")) {
         return modelBanner.bannerStand;
      } else {
         return modelPart.equals("top") ? modelBanner.bannerTop : null;
      }
   }

   @Override
   public String[] getModelRendererNames() {
      return new String[]{"slate", "stand", "top"};
   }

   public IEntityRenderer makeEntityRender(C_3840_ model, float shadowSize, RendererCache rendererCache, int index) {
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4244_ renderer = rendererCache.get(C_1992_.f_58935_, index, () -> new C_4238_(dispatcher.getContext()));
      if (!(renderer instanceof C_4238_)) {
         return null;
      } else if (!(model instanceof BannerModel bannerModel)) {
         Config.warn("Not a banner model: " + model);
         return null;
      } else {
         return bannerModel.updateRenderer(renderer);
      }
   }
}
