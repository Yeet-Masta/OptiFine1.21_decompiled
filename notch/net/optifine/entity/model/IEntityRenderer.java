package net.optifine.entity.model;

import net.minecraft.src.C_1992_;
import net.minecraft.src.C_513_;
import net.minecraft.src.C_5265_;
import net.optifine.util.Either;

public interface IEntityRenderer {
   Either<C_513_, C_1992_> getType();

   void setType(Either<C_513_, C_1992_> var1);

   C_5265_ getLocationTextureCustom();

   void setLocationTextureCustom(C_5265_ var1);
}
