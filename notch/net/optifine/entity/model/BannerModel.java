package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4238_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BannerModel extends C_3840_ {
   public C_3889_ bannerSlate;
   public C_3889_ bannerStand;
   public C_3889_ bannerTop;

   public BannerModel() {
      super(C_4168_::m_110458_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4238_ renderer = new C_4238_(dispatcher.getContext());
      this.bannerSlate = (C_3889_)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 0);
      this.bannerStand = (C_3889_)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 1);
      this.bannerTop = (C_3889_)Reflector.TileEntityBannerRenderer_modelRenderers.getValue(renderer, 2);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
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
   public void m_7695_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
