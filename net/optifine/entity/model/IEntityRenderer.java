package net.optifine.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.optifine.util.Either;

public interface IEntityRenderer {
   Either getType();

   void setType(Either var1);

   ResourceLocation getLocationTextureCustom();

   void setLocationTextureCustom(ResourceLocation var1);
}
