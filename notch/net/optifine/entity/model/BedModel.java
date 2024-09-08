package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_4241_;
import net.minecraft.src.C_4243_;
import net.minecraft.src.C_4244_;
import net.optifine.Config;
import net.optifine.reflect.Reflector;

public class BedModel extends C_3840_ {
   public C_3889_ headPiece;
   public C_3889_ footPiece;
   public C_3889_[] legs = new C_3889_[4];

   public BedModel() {
      super(C_4168_::m_110458_);
      C_4243_ dispatcher = Config.getMinecraft().m_167982_();
      C_4241_ renderer = new C_4241_(dispatcher.getContext());
      C_3889_ headRoot = (C_3889_)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
      if (headRoot != null) {
         this.headPiece = headRoot.m_171324_("main");
         this.legs[0] = headRoot.m_171324_("left_leg");
         this.legs[1] = headRoot.m_171324_("right_leg");
      }

      C_3889_ footRoot = (C_3889_)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
      if (footRoot != null) {
         this.footPiece = footRoot.m_171324_("main");
         this.legs[2] = footRoot.m_171324_("left_leg");
         this.legs[3] = footRoot.m_171324_("right_leg");
      }
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }

   public C_4244_ updateRenderer(C_4244_ renderer) {
      if (!Reflector.TileEntityBedRenderer_headModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.head");
         return null;
      } else if (!Reflector.TileEntityBedRenderer_footModel.exists()) {
         Config.warn("Field not found: TileEntityBedRenderer.footModel");
         return null;
      } else {
         C_3889_ headRoot = (C_3889_)Reflector.TileEntityBedRenderer_headModel.getValue(renderer);
         if (headRoot != null) {
            headRoot.addChildModel("main", this.headPiece);
            headRoot.addChildModel("left_leg", this.legs[0]);
            headRoot.addChildModel("right_leg", this.legs[1]);
         }

         C_3889_ footRoot = (C_3889_)Reflector.TileEntityBedRenderer_footModel.getValue(renderer);
         if (footRoot != null) {
            footRoot.addChildModel("main", this.footPiece);
            footRoot.addChildModel("left_leg", this.legs[2]);
            footRoot.addChildModel("right_leg", this.legs[3]);
         }

         return renderer;
      }
   }
}
