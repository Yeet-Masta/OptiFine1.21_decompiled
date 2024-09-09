package net.optifine.config;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.optifine.util.BiomeUtils;

public class BiomeId {
   private final net.minecraft.resources.ResourceLocation resourceLocation;
   private net.minecraft.client.multiplayer.ClientLevel world;
   private Biome biome;
   private static Minecraft minecraft = Minecraft.m_91087_();

   private BiomeId(net.minecraft.resources.ResourceLocation resourceLocation) {
      this.resourceLocation = resourceLocation;
      this.world = minecraft.f_91073_;
      this.updateBiome();
   }

   private void updateBiome() {
      this.biome = null;
      Registry<Biome> registry = BiomeUtils.getBiomeRegistry(this.world);
      if (registry.m_7804_(this.resourceLocation)) {
         this.biome = (Biome)registry.m_7745_(this.resourceLocation);
      }
   }

   public Biome getBiome() {
      if (this.world != minecraft.f_91073_) {
         this.world = minecraft.f_91073_;
         this.updateBiome();
      }

      return this.biome;
   }

   public net.minecraft.resources.ResourceLocation getResourceLocation() {
      return this.resourceLocation;
   }

   public String toString() {
      return this.resourceLocation + "";
   }

   public static BiomeId make(net.minecraft.resources.ResourceLocation resourceLocation) {
      BiomeId bi = new BiomeId(resourceLocation);
      return bi.biome == null ? null : bi;
   }
}
