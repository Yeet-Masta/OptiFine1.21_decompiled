package net.optifine.entity.model;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.optifine.util.Either;

public class VirtualEntityRenderer implements IEntityRenderer {
   private net.minecraft.client.model.Model model;
   private Either<EntityType, BlockEntityType> type;
   private net.minecraft.resources.ResourceLocation locationTextureCustom;

   public VirtualEntityRenderer(net.minecraft.client.model.Model model) {
      this.model = model;
   }

   public net.minecraft.client.model.Model getModel() {
      return this.model;
   }

   @Override
   public Either<EntityType, BlockEntityType> getType() {
      return this.type;
   }

   @Override
   public void setType(Either<EntityType, BlockEntityType> type) {
      this.type = type;
   }

   @Override
   public net.minecraft.resources.ResourceLocation getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   @Override
   public void setLocationTextureCustom(net.minecraft.resources.ResourceLocation locationTextureCustom) {
      this.locationTextureCustom = locationTextureCustom;
   }
}
