package net.optifine.override;

import java.util.Arrays;
import net.minecraft.src.C_1557_;
import net.minecraft.src.C_1607_;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_1991_;
import net.minecraft.src.C_2064_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_2681_;
import net.minecraft.src.C_2691_;
import net.minecraft.src.C_4269_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4687_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4982_;
import net.optifine.BlockPosM;
import net.optifine.render.LightCacheOF;
import net.optifine.render.RenderEnv;
import net.optifine.util.ArrayCache;

public class ChunkCacheOF implements C_1557_ {
   private final C_4269_ chunkCache;
   private final int posX;
   private final int posY;
   private final int posZ;
   private final int sizeX;
   private final int sizeY;
   private final int sizeZ;
   private final int sizeXZ;
   private int[] combinedLights;
   private C_2064_[] blockStates;
   private C_1629_[] biomes;
   private final int arraySize;
   private RenderEnv renderEnv;
   private static final ArrayCache cacheCombinedLights = new ArrayCache(int.class, 16);
   private static final ArrayCache cacheBlockStates = new ArrayCache(C_2064_.class, 16);
   private static final ArrayCache cacheBiomes = new ArrayCache(C_1629_.class, 16);

   public ChunkCacheOF(C_4269_ chunkCache, C_4710_ sectionPos) {
      this.chunkCache = chunkCache;
      int minChunkX = sectionPos.u() - 1;
      int minChunkY = sectionPos.v() - 1;
      int minChunkZ = sectionPos.w() - 1;
      int maxChunkX = sectionPos.u() + 1;
      int maxChunkY = sectionPos.v() + 1;
      int maxChunkZ = sectionPos.w() + 1;
      this.sizeX = maxChunkX - minChunkX + 1 << 4;
      this.sizeY = maxChunkY - minChunkY + 1 << 4;
      this.sizeZ = maxChunkZ - minChunkZ + 1 << 4;
      this.sizeXZ = this.sizeX * this.sizeZ;
      this.arraySize = this.sizeX * this.sizeY * this.sizeZ;
      this.posX = minChunkX << 4;
      this.posY = minChunkY << 4;
      this.posZ = minChunkZ << 4;
   }

   public int getPositionIndex(C_4675_ pos) {
      int dx = pos.u() - this.posX;
      if (dx >= 0 && dx < this.sizeX) {
         int dy = pos.v() - this.posY;
         if (dy >= 0 && dy < this.sizeY) {
            int dz = pos.w() - this.posZ;
            return dz >= 0 && dz < this.sizeZ ? dy * this.sizeXZ + dz * this.sizeX + dx : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   public int a(C_1607_ type, C_4675_ pos) {
      return this.chunkCache.a(type, pos);
   }

   public C_2064_ a_(C_4675_ pos) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.blockStates != null) {
         C_2064_ iblockstate = this.blockStates[index];
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
         this.blockStates = (C_2064_[])cacheBlockStates.allocate(this.arraySize);
      }

      if (this.biomes == null) {
         this.biomes = (C_1629_[])cacheBiomes.allocate(this.arraySize);
      }

      Arrays.fill(this.combinedLights, -1);
      Arrays.fill(this.blockStates, null);
      Arrays.fill(this.biomes, null);
      this.loadBlockStates();
   }

   private void loadBlockStates() {
      if (this.sizeX == 48 && this.sizeY == 48 && this.sizeZ == 48) {
         C_2137_ chunk = this.chunkCache.getLevelChunk(C_4710_.m_123171_(this.posX) + 1, C_4710_.m_123171_(this.posZ) + 1);
         BlockPosM pos = new BlockPosM();

         for (int y = 16; y < 32; y++) {
            int dy = y * this.sizeXZ;

            for (int z = 16; z < 32; z++) {
               int dz = z * this.sizeX;

               for (int x = 16; x < 32; x++) {
                  pos.setXyz(this.posX + x, this.posY + y, this.posZ + z);
                  int index = dy + dz + x;
                  C_2064_ bs = chunk.m_8055_(pos);
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

   public int getCombinedLight(C_2064_ blockStateIn, C_1557_ worldIn, C_4675_ blockPosIn) {
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

   public C_1629_ getBiome(C_4675_ pos) {
      int index = this.getPositionIndex(pos);
      if (index >= 0 && index < this.arraySize && this.biomes != null) {
         C_1629_ biome = this.biomes[index];
         if (biome == null) {
            biome = this.chunkCache.getBiome(pos);
            this.biomes[index] = biome;
         }

         return biome;
      } else {
         return this.chunkCache.getBiome(pos);
      }
   }

   public C_1991_ c_(C_4675_ pos) {
      return this.chunkCache.m_7702_(pos);
   }

   public boolean h(C_4675_ pos) {
      return this.chunkCache.h(pos);
   }

   public C_2691_ b_(C_4675_ pos) {
      return this.a_(pos).u();
   }

   public int a(C_4675_ blockPosIn, C_4982_ colorResolverIn) {
      return this.chunkCache.m_6171_(blockPosIn, colorResolverIn);
   }

   public C_2681_ y_() {
      return this.chunkCache.m_5518_();
   }

   public RenderEnv getRenderEnv() {
      return this.renderEnv;
   }

   public void setRenderEnv(RenderEnv renderEnv) {
      this.renderEnv = renderEnv;
   }

   public float a(C_4687_ directionIn, boolean shadeIn) {
      return this.chunkCache.m_7717_(directionIn, shadeIn);
   }

   public int J_() {
      return this.chunkCache.m_141928_();
   }

   public int I_() {
      return this.chunkCache.m_141937_();
   }
}
