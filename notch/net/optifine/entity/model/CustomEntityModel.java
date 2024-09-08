package net.optifine.entity.model;

import java.util.function.Function;
import net.minecraft.src.C_3181_;
import net.minecraft.src.C_3187_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_4168_;
import net.minecraft.src.C_5265_;

public class CustomEntityModel extends C_3840_ {
   public CustomEntityModel(Function<C_5265_, C_4168_> renderTypeIn) {
      super(renderTypeIn);
   }

   @Override
   public void m_7695_(C_3181_ matrixStackIn, C_3187_ bufferIn, int packedLightIn, int packedOverlayIn, int colorIn) {
   }
}
