package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4325_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class EnderCrystalModel extends C_3840_ {
   public C_3889_ cube;
   public C_3889_ glass;
   public C_3889_ base;

   public EnderCrystalModel() {
      super(C_4168_::m_110458_);
      C_4325_ renderer = new C_4325_(C_3391_.m_91087_().m_91290_().getContext());
      this.cube = (C_3889_)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 0);
      this.glass = (C_3889_)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 1);
      this.base = (C_3889_)Reflector.RenderEnderCrystal_modelRenderers.getValue(renderer, 2);
   }

   public C_4325_ updateRenderer(C_4325_ render) {
      if (!Reflector.RenderEnderCrystal_modelRenderers.exists()) {
         Config.warn("Field not found: RenderEnderCrystal.modelEnderCrystal");
         return null;
      } else {
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 0, this.cube);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 1, this.glass);
         Reflector.RenderEnderCrystal_modelRenderers.setValue(render, 2, this.base);
         return render;
      }
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
