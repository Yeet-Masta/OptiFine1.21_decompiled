package net.optifine.entity.model;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.util.Either;

public interface IEntityRenderer {
   Either<EntityType, BlockEntityType> getType();

   void setType(Either<EntityType, BlockEntityType> var1);

   net.minecraft.resources.ResourceLocation getLocationTextureCustom();

   void setLocationTextureCustom(net.minecraft.resources.ResourceLocation var1);
}
