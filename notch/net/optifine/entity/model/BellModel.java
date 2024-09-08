package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4242_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BellModel extends C_3840_ {
   public C_3889_ bellBody;

   public BellModel() {
      super(C_4168_::m_110458_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4242_ renderer = new C_4242_(dispatcher.getContext());
      this.bellBody = (C_3889_)Reflector.TileEntityBellRenderer_modelRenderer.getValue(renderer);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityBellRenderer_modelRenderer.exists()) {
         Config.warn("Field not found: TileEntityBellRenderer.modelRenderer");
         return null;
      } else {
         Reflector.TileEntityBellRenderer_modelRenderer.setValue(renderer, this.bellBody);
         return renderer;
      }
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
