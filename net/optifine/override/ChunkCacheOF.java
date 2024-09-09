package net.optifine.override;

import java.util.Arrays;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;
import net.optifine.BlockPosM;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements BlockAndTintGetter {
   private final net.minecraft.client.renderer.chunk.RenderChunkRegion chunkCache;
   private final int posX;
   private final int posY;
   private final int posZ;
   private final int sizeX;
   private final int sizeY;
   private final int sizeZ;
   private final int sizeXZ;
   private int[] combinedLights;
   private net.minecraft.world.level.block.state.BlockState[] blockStates;
   private Biome[] biomes;
   private final int arraySize;
   private RenderEnv renderEnv;
   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
   private static final ArrayCache cacheBlockStates = new ArrayCache(net.minecraft.world.level.block.state.BlockState.class, 16);
   private static final ArrayCache cacheBiomes = new ArrayCache(Biome.class, 16);

   public ChunkCacheOF(net.minecraft.client.renderer.chunk.RenderChunkRegion chunkCache, SectionPos sectionPos) {
      this.chunkCache = chunkCache;
      int minChunkX = sectionPos.m_123341_() - 1;
      int minChunkY = sectionPos.m_123342_() - 1;
      int minChunkZ = sectionPos.m_123343_() - 1;
      int maxChunkX = sectionPos.m_123341_() + 1;
      int maxChunkY = sectionPos.m_123342_() + 1;
      int maxChunkZ = sectionPos.m_123343_() + 1;
      this.sizeX = maxChunkX - minChunkX + 1 << 4;
      this.sizeY = maxChunkY - minChunkY + 1 << 4;
      this.sizeZ = maxChunkZ - minChunkZ + 1 << 4;
      this.sizeXZ = this.sizeX * this.sizeZ;
      this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
      this.posX = minChunkX << 4;
      this.posY = minChunkY << 4;
      this.posZ = minChunkZ << 4;
   }

   public int getPositionIndex(BlockPos pos) {
      int dx = pos.m_123341_() - this.posX;
      if (dx >= 0 && dx < this.sizeX) {
         int dy = pos.m_123342_() - this.posY;
         if (dy >= 0 && dy < this.sizeY) {
            int dz = pos.m_123343_() - this.posZ;
            return dz >= 0 && dz < this.sizeZ ? dy * this.sizeXZ + dz * this.sizeX + dx : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public int m_45517_(LightLayer type, BlockPos pos) {
      return this.chunkCache.m_45517_(type, pos);
   }

   public net.minecraft.world.level.block.state.BlockState m_8055_(BlockPos pos) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.blockStates != null) {
         net.minecraft.world.level.block.state.BlockState iblockstate = this.blockStates[index];
         if (iblockstate == null) {
            iblockstate = this.chunkCache.m_8055_(pos);
            this.blockStates[index] = iblockstate;
         }

         return iblockstate;
      } else {
         return this.chunkCache.m_8055_(pos);
      }
   }

   public void renderStart() {
      if (this.combinedLights == null) {
         this.combinedLights = (int[])cacheCombinedLights.allocate(this.arraySize);
      }

      if (this.blockStates == null) {
         this.blockStates = (net.minecraft.world.level.block.state.BlockState[])cacheBlockStates.allocate(this.arraySize);
      }

      if (this.biomes == null) {
         this.biomes = (Biome[])cacheBiomes.allocate(this.arraySize);
      }

      Arrays.fill(this.combinedLights, -1);
      Arrays.fill(this.blockStates, null);
      Arrays.fill(this.biomes, null);
      this.loadBlockStates();
   }

   private void loadBlockStates() {
      if (this.sizeX == 48 && this.sizeY == 48 && this.sizeZ == 48) {
         LevelChunk chunk = this.chunkCache.getLevelChunk(SectionPos.m_123171_(this.posX) + 1, SectionPos.m_123171_(this.posZ) + 1);
         BlockPosM pos = new BlockPosM();

         for (int y = 16; y < 32; y++) {
            int dy = y * this.sizeXZ;

            for (int z = 16; z < 32; z++) {
               int dz = z * this.sizeX;

               for (int x = 16; x < 32; x++) {
                  pos.setXyz(this.posX + x, this.posY + y, this.posZ + z);
                  int index = dy + dz + x;
                  net.minecraft.world.level.block.state.BlockState bs = chunk.m_8055_(pos);
                  this.blockStates[index] = bs;
               }
            }
         }
      }
   }

   public void renderFinish() {
      cacheCombinedLights.free(this.combinedLights);
      this.combinedLights = null;
      cacheBlockStates.free(this.blockStates);
      this.blockStates = null;
      cacheBiomes.free(this.biomes);
      this.biomes = null;
      this.chunkCache.finish();
   }

   public int getCombinedLight(net.minecraft.world.level.block.state.BlockState blockStateIn, BlockAndTintGetter worldIn, BlockPos blockPosIn) {
      int index = this.getPositionIndex(blockPosIn);
      if (index >= 0 && index < this.combinedLights.length && this.combinedLights != null) {
         int light = this.combinedLights[index];
         if (light == -1) {
            light = LightCacheOF.getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
            this.combinedLights[index] = light;
         }

         return light;
      } else {
         return LightCacheOF.getPackedLightRaw(worldIn, blockStateIn, blockPosIn);
      }
   }

   public Biome getBiome(BlockPos pos) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.biomes != null) {
         Biome biome = this.biomes[index];
         if (biome == null) {
            biome = this.chunkCache.getBiome(pos);
            this.biomes[index] = biome;
         }

         return biome;
      } else {
         return this.chunkCache.getBiome(pos);
      }
   }

   public net.minecraft.world.level.block.entity.BlockEntity m_7702_(BlockPos pos) {
      return this.chunkCache.m_7702_(pos);
   }

   public boolean m_45527_(BlockPos pos) {
      return this.chunkCache.m_45527_(pos);
   }

   public FluidState m_6425_(BlockPos pos) {
      return this.m_8055_(pos).m_60819_();
   }

   public int m_6171_(BlockPos blockPosIn, ColorResolver colorResolverIn) {
      return this.chunkCache.m_6171_(blockPosIn, colorResolverIn);
   }

   public LevelLightEngine m_5518_() {
      return this.chunkCache.m_5518_();
   }

   public RenderEnv getRenderEnv() {
      return this.renderEnv;
   }

   public void setRenderEnv(RenderEnv renderEnv) {
      this.renderEnv = renderEnv;
   }

   public float m_7717_(net.minecraft.core.Direction directionIn, boolean shadeIn) {
      return this.chunkCache.m_7717_(directionIn, shadeIn);
   }

   public int m_141928_() {
      return this.chunkCache.m_141928_();
   }

   public int m_141937_() {
      return this.chunkCache.m_141937_();
   }
}
