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

public class ChestLargeModel extends C_3840_ {
   public C_3889_ lid_left;
   public C_3889_ base_left;
   public C_3889_ knob_left;
   public C_3889_ lid_right;
   public C_3889_ base_right;
   public C_3889_ knob_right;

   public ChestLargeModel() {
      super(C_4168_::m_110452_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4247_ renderer = new C_4247_(dispatcher.getContext());
      this.lid_right = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 3);
      this.base_right = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 4);
      this.knob_right = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 5);
      this.lid_left = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 6);
      this.base_left = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 7);
      this.knob_left = (C_3889_)Reflector.TileEntityChestRenderer_modelRenderers.getValue(renderer, 8);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityChestRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: TileEntityChestRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 3, this.lid_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 4, this.base_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 5, this.knob_right);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 6, this.lid_left);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 7, this.base_left);
         Reflector.TileEntityChestRenderer_modelRenderers.setValue(renderer, 8, this.knob_left);
         return renderer;
      }
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
