package net.minecraft.client.renderer.chunk;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class RenderRegionCache {
   private final Long2ObjectMap<net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo> f_200460_ = new Long2ObjectOpenHashMap();

   @Nullable
   public net.minecraft.client.renderer.chunk.RenderChunkRegion m_200465_(Level worldIn, SectionPos sectionPosIn) {
      net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo renderregioncache$chunkinfo = this.m_340552_(
         worldIn, sectionPosIn.m_123170_(), sectionPosIn.m_123222_()
      );
      if (renderregioncache$chunkinfo.m_200480_().m_339293_(sectionPosIn.m_123206_())) {
         return null;
      } else {
         int i = sectionPosIn.m_123170_() - 1;
         int j = sectionPosIn.m_123222_() - 1;
         int k = sectionPosIn.m_123170_() + 1;
         int l = sectionPosIn.m_123222_() + 1;
         net.minecraft.client.renderer.chunk.RenderChunk[] arenderchunk = new net.minecraft.client.renderer.chunk.RenderChunk[9];

         for (int i1 = j; i1 <= l; i1++) {
            for (int j1 = i; j1 <= k; j1++) {
               int k1 = net.minecraft.client.renderer.chunk.RenderChunkRegion.m_339116_(i, j, j1, i1);
               net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo renderregioncache$chunkinfo1 = j1 == sectionPosIn.m_123170_()
                     && i1 == sectionPosIn.m_123222_()
                  ? renderregioncache$chunkinfo
                  : this.m_340552_(worldIn, j1, i1);
               arenderchunk[k1] = renderregioncache$chunkinfo1.m_200481_();
            }
         }

         return new net.minecraft.client.renderer.chunk.RenderChunkRegion(worldIn, i, j, arenderchunk, sectionPosIn);
      }
   }

   private net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo m_340552_(Level worldIn, int x, int z) {
      return (net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo)this.f_200460_
         .computeIfAbsent(
            net.minecraft.world.level.ChunkPos.m_45589_(x, z),
            longPosIn -> new net.minecraft.client.renderer.chunk.RenderRegionCache.ChunkInfo(
                  worldIn.m_6325_(net.minecraft.world.level.ChunkPos.m_45592_(longPosIn), net.minecraft.world.level.ChunkPos.m_45602_(longPosIn))
               )
         );
   }

   static final class ChunkInfo {
      private final LevelChunk f_200476_;
      @Nullable
      private net.minecraft.client.renderer.chunk.RenderChunk f_200477_;

      ChunkInfo(LevelChunk chunkIn) {
         this.f_200476_ = chunkIn;
      }

      public LevelChunk m_200480_() {
         return this.f_200476_;
      }

      public net.minecraft.client.renderer.chunk.RenderChunk m_200481_() {
         if (this.f_200477_ == null) {
            this.f_200477_ = new net.minecraft.client.renderer.chunk.RenderChunk(this.f_200476_);
         }

         return this.f_200477_;
      }
   }
}
