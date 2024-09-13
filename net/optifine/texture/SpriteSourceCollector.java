package net.optifine.texture;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.Output;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.SpriteSupplier;
import net.minecraft.resources.ResourceLocation;

public class SpriteSourceCollector implements Output {
   private Set<ResourceLocation> spriteNames;

   public SpriteSourceCollector(Set<ResourceLocation> spriteNames) {
      this.spriteNames = spriteNames;
   }

   public void m_260840_(ResourceLocation locIn, SpriteSupplier supplierIn) {
      this.spriteNames.add(locIn);
   }

   public void m_261187_(Predicate<ResourceLocation> checkIn) {
   }

   public Set<ResourceLocation> getSpriteNames() {
      return this.spriteNames;
   }
}
