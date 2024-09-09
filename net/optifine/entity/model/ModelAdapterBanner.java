package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.Config;

public class ModelAdapterBanner extends ModelAdapter {
   public ModelAdapterBanner() {
      super(BlockEntityType.f_58935_, "banner", 0.0F);
   }

   @Override
   public net.minecraft.client.model.Model makeModel() {
      return new BannerModel();
   }

   @Override
   public net.minecraft.client.model.geom.ModelPart getModelRenderer(net.minecraft.client.model.Model model, String modelPart) {
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

   @Override
   public IEntityRenderer makeEntityRender(net.minecraft.client.model.Model model, float shadowSize, RendererCache rendererCache, int index) {
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer = rendererCache.get(
         BlockEntityType.f_58935_, index, () -> new BannerRenderer(dispatcher.getContext())
      );
      if (!(renderer instanceof BannerRenderer)) {
         return null;
      } else if (!(model instanceof BannerModel bannerModel)) {
         Config.warn("Not a banner model: " + model);
         return null;
      } else {
         return bannerModel.updateRenderer(renderer);
      }
   }
}
