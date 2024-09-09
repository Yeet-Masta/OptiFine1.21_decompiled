package net.optifine.texture;

import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.Output;
import net.minecraft.client.renderer.texture.atlas.SpriteSource.SpriteSupplier;

public class SpriteSourceCollector implements Output {
   private Set<net.minecraft.resources.ResourceLocation> spriteNames;

   public SpriteSourceCollector(Set<net.minecraft.resources.ResourceLocation> spriteNames) {
      this.spriteNames = spriteNames;
   }

   public void m_260840_(net.minecraft.resources.ResourceLocation locIn, SpriteSupplier supplierIn) {
      this.spriteNames.add(locIn);
   }

   public void m_261187_(Predicate<net.minecraft.resources.ResourceLocation> checkIn) {
   }

   public Set<net.minecraft.resources.ResourceLocation> getSpriteNames() {
      return this.spriteNames;
   }
}
