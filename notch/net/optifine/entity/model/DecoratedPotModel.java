package net.optifine.entity.model;

import net.minecraft.src.C_271025_;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class DecoratedPotModel extends C_3840_ {
   public C_3889_ neck;
   public C_3889_ frontSide;
   public C_3889_ backSide;
   public C_3889_ leftSide;
   public C_3889_ rightSide;
   public C_3889_ top;
   public C_3889_ bottom;

   public DecoratedPotModel() {
      super(C_4168_::m_110458_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_271025_ renderer = new C_271025_(dispatcher.getContext());
      this.neck = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 0);
      this.frontSide = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 1);
      this.backSide = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 2);
      this.leftSide = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 3);
      this.rightSide = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 4);
      this.top = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 5);
      this.bottom = (C_3889_)Reflector.TileEntityDecoratedPotRenderer_modelRenderers.getValue(renderer, 6);
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityDecoratedPotRenderer_modelRenderers.exists()) {
         Config.warn("Field not found: DecoratedPotRenderer.modelRenderers");
         return null;
      } else {
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 0, this.neck);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 1, this.frontSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 2, this.backSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 3, this.leftSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 4, this.rightSide);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 5, this.top);
         Reflector.TileEntityDecoratedPotRenderer_modelRenderers.setValue(renderer, 6, this.bottom);
         return renderer;
      }
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
