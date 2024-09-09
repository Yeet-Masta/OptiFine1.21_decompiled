package net.optifine.entity.model;

import net.minecraft.client.model.Model;
import net.minecraft.resources.ResourceLocation;
import net.optifine.util.Either;

public class VirtualEntityRenderer implements IEntityRenderer {
   private Model model;
   private Either type;
   private ResourceLocation locationTextureCustom;

   public VirtualEntityRenderer(Model model) {
      this.model = model;
   }

   public Model getModel() {
      return this.model;
   }

   public Either getType() {
      return this.type;
   }

   public void setType(Either type) {
      this.type = type;
   }

   public ResourceLocation getLocationTextureCustom() {
      return this.locationTextureCustom;
   }

   public void setLocationTextureCustom(ResourceLocation locationTextureCustom) {
      this.locationTextureCustom = locationTextureCustom;
   }
}
