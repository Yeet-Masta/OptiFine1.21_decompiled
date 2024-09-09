package net.optifine.entity.model;

import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BannerModel extends net.minecraft.client.model.Model {
   public net.minecraft.client.model.geom.ModelPart bannerSlate;
   public net.minecraft.client.model.geom.ModelPart bannerStand;
   public net.minecraft.client.model.geom.ModelPart bannerTop;

   public BannerModel() {
      super(net.minecraft.client.renderer.RenderType::m_110458_);
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher dispatcher = Config.getMinecraft().m_167982_();
      BannerRenderer renderer = new BannerRenderer(dispatcher.getContext());
      this.bannerSlate = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 0);
      this.bannerStand = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 1);
      this.bannerTop = (net.minecraft.client.model.geom.ModelPart)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 2);
   }

   public net.minecraft.client.renderer.blockentity.BlockEntityRenderer updateRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRenderer renderer) {
      if (!Reflector.TileEntityBannerRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityBannerRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 0, this.bannerSlate);
         Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 1, this.bannerStand);
         Reflector.TileEntityBannerRenderer_modelRenderers.setValue(renderer, 2, this.bannerTop);
         return renderer;
      }
   }

   @Override
   public void m_7695_(
      com.mojang.blaze3d.vertex.PoseStack matrixStackIn, com.mojang.blaze3d.vertex.VertexConsumer bufferIn, int packedLightIn, int packedOverlayIn, int colorIn
   ) {
   }
}
