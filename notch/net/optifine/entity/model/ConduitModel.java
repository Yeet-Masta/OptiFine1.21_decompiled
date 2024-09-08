package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4248_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ConduitModel extends C_3840_ {
   public C_3889_ eye;
   public C_3889_ wind;
   public C_3889_ base;
   public C_3889_ cage;

   public ConduitModel() {
      super(C_4168_::m_110452_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4248_ renderer = new C_4248_(dispatcher.getContext());
      this.eye = (C_3889_)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 0);
      this.wind = (C_3889_)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 1);
      this.base = (C_3889_)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 2);
      this.cage = (C_3889_)Reflector.TileEntityConduitRenderer_modelRenderers.getValue(renderer, 3);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityConduitRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityConduitRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 0, this.eye);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 1, this.wind);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 2, this.base);
         Reflector.TileEntityConduitRenderer_modelRenderers.setValue(renderer, 3, this.cage);
         return renderer;
      }
   }

   @Override
   public void m_7695_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
