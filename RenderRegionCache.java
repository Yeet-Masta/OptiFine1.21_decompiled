import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_4710_;

public class RenderRegionCache {
   private final Long2ObjectMap<RenderRegionCache.a> a = new Long2ObjectOpenHashMap();

   @Nullable
   public RenderChunkRegion a(C_1596_ worldIn, C_4710_ sectionPosIn) {
      RenderRegionCache.a renderregioncache$chunkinfo = this.a(worldIn, sectionPosIn.m_123170_(), sectionPosIn.m_123222_());
      if (renderregioncache$chunkinfo.a().m_339293_(sectionPosIn.m_123206_())) {
         return null;
      } else {
         int i = sectionPosIn.m_123170_() - 1;
         int j = sectionPosIn.m_123222_() - 1;
         int k = sectionPosIn.m_123170_() + 1;
         int l = sectionPosIn.m_123222_() + 1;
         RenderChunk[] arenderchunk = new RenderChunk[9];

         for (int i1 = j; i1 <= l; i1++) {
            for (int j1 = i; j1 <= k; j1++) {
               int k1 = RenderChunkRegion.a(i, j, j1, i1);
               RenderRegionCache.a renderregioncache$chunkinfo1 = j1 == sectionPosIn.m_123170_() && i1 == sectionPosIn.m_123222_()
                  ? renderregioncache$chunkinfo
                  : this.a(worldIn, j1, i1);
               arenderchunk[k1] = renderregioncache$chunkinfo1.b();
            }
         }

         return new RenderChunkRegion(worldIn, i, j, arenderchunk, sectionPosIn);
      }
   }

   private RenderRegionCache.a a(C_1596_ worldIn, int x, int z) {
      return (RenderRegionCache.a)this.a
         .computeIfAbsent(ChunkPos.c(x, z), longPosIn -> new RenderRegionCache.a(worldIn.m_6325_(ChunkPos.a(longPosIn), ChunkPos.b(longPosIn))));
   }

   static final class a {
      private final C_2137_ a;
      @Nullable
      private RenderChunk b;

      a(C_2137_ chunkIn) {
         this.a = chunkIn;
      }

      public C_2137_ a() {
         return this.a;
      }

      public RenderChunk b() {
         if (this.b == null) {
            this.b = new RenderChunk(this.a);
         }

         return this.b;
      }
   }
}
