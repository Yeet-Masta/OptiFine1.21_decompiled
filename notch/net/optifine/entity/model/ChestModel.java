package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.minecraft.src.C_4247_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class ChestModel extends C_3840_ {
   public C_3889_ lid;
   public C_3889_ base;
   public C_3889_ knob;

   public ChestModel() {
      super(C_4168_::m_110452_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4247_ renderer = new C_4247_(dispatcher.getContext());
      this.lid = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 0);
      this.base = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 1);
      this.knob = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 2);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 0, this.lid);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 1, this.base);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 2, this.knob);
         return renderer;
      }
   }

   @Override
   public void m_7695_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
