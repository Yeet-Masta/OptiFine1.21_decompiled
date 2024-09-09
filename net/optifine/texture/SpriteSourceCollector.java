package net.optifine.texture;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.resources.ResourceLocation;

public class SpriteSourceCollector implements SpriteSource.Output {
   private Set spriteNames;

   public SpriteSourceCollector(Set spriteNames) {
      this.spriteNames = spriteNames;
   }

   public void m_260840_(ResourceLocation locIn, SpriteSource.SpriteSupplier supplierIn) {
      this.spriteNames.add(locIn);
   }

   public void m_261187_(Predicate checkIn) {
   }

   public Set getSpriteNames() {
      return this.spriteNames;
   }
}
