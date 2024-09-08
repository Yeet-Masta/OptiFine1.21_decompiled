package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_3840_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.util.Either;

public class VirtualEntityRenderer implements IEntityRenderer {
   private C_3840_ model;
   private Either<C_513_, C_1992_> type;
   private C_5265_ locationTextureCustom;

   public VirtualEntityRenderer(C_3840_ model) {
      this.model = model;
   }

   public C_3840_ getModel() {
      return this.model;
   }

   @Override
   public Either<C_513_, C_1992_> getType() {
      return this.type;
   }

   @Override
   public void setType(Either<C_513_, C_1992_> type) {
      this.type = type;
   }

   public C_5265_ getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   public void setLocationTextureCustom(C_5265_ locationTextureCustom) {
      this.locationTextureCustom = locationTextureCustom;
   }
}
