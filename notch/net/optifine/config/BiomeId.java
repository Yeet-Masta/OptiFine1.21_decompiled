package net.optifine.config;

import net.minecraft.src.C_1629_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_3899_;
import net.minecraft.src.C_4705_;
import net.minecraft.src.C_5265_;
import net.optifine.util.BiomeUtils;

public class BiomeId {
   private final C_5265_ resourceLocation;
   private C_3899_ world;
   private C_1629_ biome;
   private static C_3391_ minecraft = C_3391_.m_91087_();

   private BiomeId(C_5265_ resourceLocation) {
      this.resourceLocation = resourceLocation;
      this.world = minecraft.f_91073_;
      this.updateBiome();
   }

   private void updateBiome() {
      this.biome = null;
      C_4705_<C_1629_> registry = BiomeUtils.getBiomeRegistry(this.world);
      if (registry.m_7804_(this.resourceLocation)) {
         this.biome = (C_1629_)registry.m_7745_(this.resourceLocation);
      }
   }

   public C_1629_ getBiome() {
      if (this.world != minecraft.f_91073_) {
         this.world = minecraft.f_91073_;
         this.updateBiome();
      }

      return this.biome;
   }

   public C_5265_ getResourceLocation() {
      return this.resourceLocation;
   }

   public String toString() {
      return this.resourceLocation + "";
   }

   public static BiomeId make(C_5265_ resourceLocation) {
      BiomeId bi = new BiomeId(resourceLocation);
      return bi.biome == null ? null : bi;
   }
}
