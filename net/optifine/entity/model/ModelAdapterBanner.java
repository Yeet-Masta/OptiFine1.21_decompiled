package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterBanner extends ModelAdapter {
   public ModelAdapterBanner() {
      super(BlockEntityType.f_58935_, "banner", 0.0F);
   }

   public Model makeModel() {
      return new BannerModel();
   }

   public ModelPart getModelRenderer(Model model, String modelPart) {
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

   public String[] getModelRendererNames() {
      return new String[]{"slate", "stand", "top"};
   }

   public IEntityRenderer makeEntityRender(Model model, float shadowSize, RendererCache rendererCache, int index) {
      BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BlockEntityRenderer renderer = rendererCache.get(BlockEntityType.f_58935_, index, () -> {
         return new BannerRenderer(dispatcher.getContext());
      });
      if (!(renderer instanceof BannerRenderer)) {
         return null;
      } else if (!(model instanceof BannerModel)) {
         Config.warn("Not a banner model: " + String.valueOf(model));
         return null;
      } else {
         BannerModel bannerModel = (BannerModel)model;
         renderer = bannerModel.updateRenderer(renderer);
         return renderer;
      }
   }
}
