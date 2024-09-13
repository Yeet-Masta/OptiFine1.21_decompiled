package net.minecraft.client.renderer;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.optifine.Config;
import net.optifine.render.ClearVertexBuffersTask;
import net.optifine.render.VboRegion;

public class ViewArea {
   protected LevelRenderer f_110838_;
   protected Level f_110839_;
   protected int f_291207_;
   protected int f_291809_;
   protected int f_290583_;
   private int f_291500_;
   public SectionRenderDispatcher.RenderSection[] f_291707_;
   private Map<ChunkPos, VboRegion[]> mapVboRegions = new HashMap();
   private int lastCleanIndex = 0;

   public ViewArea(SectionRenderDispatcher renderDispatcherIn, Level worldIn, int countChunksIn, LevelRenderer renderGlobalIn) {
      this.f_110838_ = renderGlobalIn;
      this.f_110839_ = worldIn;
      this.m_110853_(countChunksIn);
      this.m_294128_(renderDispatcherIn);
   }

   protected void m_294128_(SectionRenderDispatcher renderChunkFactory) {
      if (!Minecraft.m_91087_().m_18695_()) {
         throw new IllegalStateException("createSections called from wrong thread: " + Thread.currentThread().getName());
      } else {
         int i = this.f_291809_ * this.f_291207_ * this.f_290583_;
         this.f_291707_ = new SectionRenderDispatcher.RenderSection[i];
         int minBuildHeight = this.f_110839_.m_141937_();

         for (int j = 0; j < this.f_291809_; j++) {
            for (int k = 0; k < this.f_291207_; k++) {
               for (int l = 0; l < this.f_290583_; l++) {
                  int i1 = this.m_293962_(j, k, l);
                  this.f_291707_[i1] = renderChunkFactory.new RenderSection(i1, j * 16, this.f_110839_.m_141937_() + k * 16, l * 16);
                  this.f_291707_[i1].m_292814_(j * 16, k * 16 + minBuildHeight, l * 16);
                  if (Config.isVbo() && Config.isRenderRegions()) {
                     this.updateVboRegion(this.f_291707_[i1]);
                  }
               }
            }
         }

         for (int k = 0; k < this.f_291707_.length; k++) {
            SectionRenderDispatcher.RenderSection renderChunk = this.f_291707_[k];

            for (int lx = 0; lx < Direction.f_122346_.length; lx++) {
               Direction facing = Direction.f_122346_[lx];
               BlockPos posOffset16 = renderChunk.m_292593_(facing);
               SectionRenderDispatcher.RenderSection neighbour = this.m_292642_(posOffset16);
               renderChunk.setRenderChunkNeighbour(facing, neighbour);
            }
         }
      }
   }

   public void m_110849_() {
      for (SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection : this.f_291707_) {
         sectionrenderdispatcher$rendersection.m_294345_();
      }

      this.deleteVboRegions();
   }

   private int m_293962_(int x, int y, int z) {
      return (z * this.f_291207_ + y) * this.f_291809_ + x;
   }

   protected void m_110853_(int renderDistanceChunks) {
      int i = renderDistanceChunks * 2 + 1;
      this.f_291809_ = i;
      this.f_291207_ = this.f_110839_.m_151559_();
      this.f_290583_ = i;
      this.f_291500_ = renderDistanceChunks;
   }

   public int m_295654_() {
      return this.f_291500_;
   }

   public LevelHeightAccessor m_294982_() {
      return this.f_110839_;
   }

   public void m_110850_(double viewEntityX, double viewEntityZ) {
      int i = Mth.m_14165_(viewEntityX);
      int j = Mth.m_14165_(viewEntityZ);

      for (int k = 0; k < this.f_291809_; k++) {
         int l = this.f_291809_ * 16;
         int i1 = i - 7 - l / 2;
         int j1 = i1 + Math.floorMod(k * 16 - i1, l);

         for (int k1 = 0; k1 < this.f_290583_; k1++) {
            int l1 = this.f_290583_ * 16;
            int i2 = j - 7 - l1 / 2;
            int j2 = i2 + Math.floorMod(k1 * 16 - i2, l1);

            for (int k2 = 0; k2 < this.f_291207_; k2++) {
               int l2 = this.f_110839_.m_141937_() + k2 * 16;
               SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection = this.f_291707_[this.m_293962_(k, k2, k1)];
               BlockPos blockpos = sectionrenderdispatcher$rendersection.m_295500_();
               if (j1 != blockpos.m_123341_() || l2 != blockpos.m_123342_() || j2 != blockpos.m_123343_()) {
                  sectionrenderdispatcher$rendersection.m_292814_(j1, l2, j2);
               }
            }
         }
      }
   }

   public void m_110859_(int sectionX, int sectionY, int sectionZ, boolean rerenderOnMainThread) {
      int i = Math.floorMod(sectionX, this.f_291809_);
      int j = Math.floorMod(sectionY - this.f_110839_.m_151560_(), this.f_291207_);
      int k = Math.floorMod(sectionZ, this.f_290583_);
      SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection = this.f_291707_[this.m_293962_(i, j, k)];
      sectionrenderdispatcher$rendersection.m_292780_(rerenderOnMainThread);
   }

   @Nullable
   public SectionRenderDispatcher.RenderSection m_292642_(BlockPos pos) {
      int i = pos.m_123342_() - this.f_110839_.m_141937_() >> 4;
      if (i >= 0 && i < this.f_291207_) {
         int j = Mth.m_14100_(pos.m_123341_() >> 4, this.f_291809_);
         int k = Mth.m_14100_(pos.m_123343_() >> 4, this.f_290583_);
         return this.f_291707_[this.m_293962_(j, i, k)];
      } else {
         return null;
      }
   }

   private void updateVboRegion(SectionRenderDispatcher.RenderSection renderChunk) {
      BlockPos pos = renderChunk.m_295500_();
      int rx = pos.m_123341_() >> 8 << 8;
      int rz = pos.m_123343_() >> 8 << 8;
      ChunkPos cp = new ChunkPos(rx, rz);
      RenderType[] layers = RenderType.CHUNK_RENDER_TYPES;
      VboRegion[] regions = (VboRegion[])this.mapVboRegions.get(cp);
      if (regions == null) {
         regions = new VboRegion[layers.length];

         for (int ix = 0; ix < layers.length; ix++) {
            if (!layers[ix].isNeedsSorting()) {
               regions[ix] = new VboRegion(layers[ix]);
            }
         }

         this.mapVboRegions.put(cp, regions);
      }

      for (int ixx = 0; ixx < layers.length; ixx++) {
         RenderType layer = layers[ixx];
         VboRegion vr = regions[ixx];
         renderChunk.m_294581_(layer).setVboRegion(vr);
      }
   }

   public void deleteVboRegions() {
      for (ChunkPos cp : this.mapVboRegions.keySet()) {
         VboRegion[] vboRegions = (VboRegion[])this.mapVboRegions.get(cp);

         for (int i = 0; i < vboRegions.length; i++) {
            VboRegion vboRegion = vboRegions[i];
            if (vboRegion != null) {
               vboRegion.deleteGlBuffers();
            }

            vboRegions[i] = null;
         }
      }

      this.mapVboRegions.clear();
   }

   public int getHighestUsedChunkIndex(int chunkX, int minChunkIndex, int chunkZ) {
      chunkX = Mth.m_14100_(chunkX, this.f_291809_);
      minChunkIndex = Mth.m_14045_(minChunkIndex, 0, this.f_291207_);
      chunkZ = Mth.m_14100_(chunkZ, this.f_290583_);

      for (int chunkY = this.f_291207_ - 1; chunkY >= minChunkIndex; chunkY--) {
         SectionRenderDispatcher.RenderSection rc = this.f_291707_[this.m_293962_(chunkX, chunkY, chunkZ)];
         if (!rc.m_293175_().m_295467_()) {
            return chunkY;
         }
      }

      return -1;
   }

   public void clearUnusedVbos() {
      int fps = Config.limit(Config.getFpsAverage(), 1, 1000);
      int countCheckMax = Config.limit(this.f_291707_.length / (10 * fps), 3, 100);
      int countClearMax = Config.limit(countCheckMax / 3, 1, 3);
      int countClear = 0;
      int index = Config.limit(this.lastCleanIndex, 0, this.f_291707_.length - 1);

      for (int indexMax = Math.min(index + countCheckMax, this.f_291707_.length); index < indexMax && countClear < countClearMax; index++) {
         SectionRenderDispatcher.RenderSection rc = this.f_291707_[index];
         ClearVertexBuffersTask clearTask = ClearVertexBuffersTask.make(rc.m_293175_().getLayersUsed(), rc);
         if (clearTask != null) {
            Minecraft.m_91087_().f_91060_.m_295427_().addUploadTask(clearTask);
            countClear++;
         }
      }

      if (index >= this.f_291707_.length) {
         index = 0;
      }

      this.lastCleanIndex = index;
   }
}
