package net.optifine.entity.model;

import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_3889_;
import net.minecraft.src.C_4168_;

public class ArrowModel extends C_3840_ {
   public C_3889_ body;

   public ArrowModel(C_3889_ body) {
      super(C_4168_::m_110458_);
      this.body = body;
   }

   public void a(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
      this.body.m_104306_(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, colorIn);
   }
}
