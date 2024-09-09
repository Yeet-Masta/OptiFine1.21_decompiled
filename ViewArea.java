import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.src.C_141183_;
import net.minecraft.src.C_1596_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.optifine.Config;
import net.optifine.render.ClearVertexBuffersTask;
import net.optifine.render.VboRegion;

public class ViewArea {
   protected final LevelRenderer a;
   protected final C_1596_ b;
   protected int c;
   protected int d;
   protected int e;
   private int g;
   public SectionRenderDispatcher.b[] f;
   private Map<ChunkPos, VboRegion[]> mapVboRegions = new HashMap();
   private int lastCleanIndex = 0;

   public ViewArea(SectionRenderDispatcher renderDispatcherIn, C_1596_ worldIn, int countChunksIn, LevelRenderer renderGlobalIn) {
      this.a = renderGlobalIn;
      this.b = worldIn;
      this.a(countChunksIn);
      this.a(renderDispatcherIn);
   }

   protected void a(SectionRenderDispatcher renderChunkFactory) {
      if (!C_3391_.m_91087_().bx()) {
         throw new IllegalStateException("createSections called from wrong thread: " + Thread.currentThread().getName());
      } else {
         int i = this.d * this.c * this.e;
         this.f = new SectionRenderDispatcher.b[i];
         int minBuildHeight = this.b.m_141937_();

         for (int j = 0; j < this.d; j++) {
            for (int k = 0; k < this.c; k++) {
               for (int l = 0; l < this.e; l++) {
                  int i1 = this.a(j, k, l);
                  this.f[i1] = renderChunkFactory.new b(i1, j * 16, this.b.m_141937_() + k * 16, l * 16);
                  this.f[i1].a(j * 16, k * 16 + minBuildHeight, l * 16);
                  if (Config.isVbo() && Config.isRenderRegions()) {
                     this.updateVboRegion(this.f[i1]);
                  }
               }
            }
         }

         for (int k = 0; k < this.f.length; k++) {
            SectionRenderDispatcher.b renderChunk = this.f[k];

            for (int lx = 0; lx < Direction.r.length; lx++) {
               Direction facing = Direction.r[lx];
               C_4675_ posOffset16 = renderChunk.a(facing);
               SectionRenderDispatcher.b neighbour = this.a(posOffset16);
               renderChunk.setRenderChunkNeighbour(facing, neighbour);
            }
         }
      }
   }

   public void a() {
      for (SectionRenderDispatcher.b sectionrenderdispatcher$rendersection : this.f) {
         sectionrenderdispatcher$rendersection.e();
      }

      this.deleteVboRegions();
   }

   private int a(int x, int y, int z) {
      return (z * this.c + y) * this.d + x;
   }

   protected void a(int renderDistanceChunks) {
      int i = renderDistanceChunks * 2 + 1;
      this.d = i;
      this.c = this.b.m_151559_();
      this.e = i;
      this.g = renderDistanceChunks;
   }

   public int b() {
      return this.g;
   }

   public C_141183_ c() {
      return this.b;
   }

   public void a(double viewEntityX, double viewEntityZ) {
      int i = Mth.c(viewEntityX);
      int j = Mth.c(viewEntityZ);

      for (int k = 0; k < this.d; k++) {
         int l = this.d * 16;
         int i1 = i - 7 - l / 2;
         int j1 = i1 + Math.floorMod(k * 16 - i1, l);

         for (int k1 = 0; k1 < this.e; k1++) {
            int l1 = this.e * 16;
            int i2 = j - 7 - l1 / 2;
            int j2 = i2 + Math.floorMod(k1 * 16 - i2, l1);

            for (int k2 = 0; k2 < this.c; k2++) {
               int l2 = this.b.m_141937_() + k2 * 16;
               SectionRenderDispatcher.b sectionrenderdispatcher$rendersection = this.f[this.a(k, k2, k1)];
               C_4675_ blockpos = sectionrenderdispatcher$rendersection.f();
               if (j1 != blockpos.m_123341_() || l2 != blockpos.m_123342_() || j2 != blockpos.m_123343_()) {
                  sectionrenderdispatcher$rendersection.a(j1, l2, j2);
               }
            }
         }
      }
   }

   public void a(int sectionX, int sectionY, int sectionZ, boolean rerenderOnMainThread) {
      int i = Math.floorMod(sectionX, this.d);
      int j = Math.floorMod(sectionY - this.b.m_151560_(), this.c);
      int k = Math.floorMod(sectionZ, this.e);
      SectionRenderDispatcher.b sectionrenderdispatcher$rendersection = this.f[this.a(i, j, k)];
      sectionrenderdispatcher$rendersection.a(rerenderOnMainThread);
   }

   @Nullable
   public SectionRenderDispatcher.b a(C_4675_ pos) {
      int i = pos.m_123342_() - this.b.m_141937_() >> 4;
      if (i >= 0 && i < this.c) {
         int j = Mth.b(pos.m_123341_() >> 4, this.d);
         int k = Mth.b(pos.m_123343_() >> 4, this.e);
         return this.f[this.a(j, i, k)];
      } else {
         return null;
      }
   }

   private void updateVboRegion(SectionRenderDispatcher.b renderChunk) {
      C_4675_ pos = renderChunk.f();
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
         renderChunk.a(layer).setVboRegion(vr);
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
      chunkX = Mth.b(chunkX, this.d);
      minChunkIndex = Mth.a(minChunkIndex, 0, this.c);
      chunkZ = Mth.b(chunkZ, this.e);

      for (int chunkY = this.c - 1; chunkY >= minChunkIndex; chunkY--) {
         SectionRenderDispatcher.b rc = this.f[this.a(chunkX, chunkY, chunkZ)];
         if (!rc.d().a()) {
            return chunkY;
         }
      }

      return -1;
   }

   public void clearUnusedVbos() {
      int fps = Config.limit(Config.getFpsAverage(), 1, 1000);
      int countCheckMax = Config.limit(this.f.length / (10 * fps), 3, 100);
      int countClearMax = Config.limit(countCheckMax / 3, 1, 3);
      int countClear = 0;
      int index = Config.limit(this.lastCleanIndex, 0, this.f.length - 1);

      for (int indexMax = Math.min(index + countCheckMax, this.f.length); index < indexMax && countClear < countClearMax; index++) {
         SectionRenderDispatcher.b rc = this.f[index];
         ClearVertexBuffersTask clearTask = ClearVertexBuffersTask.make(rc.d().getLayersUsed(), rc);
         if (clearTask != null) {
            C_3391_.m_91087_().f.h().addUploadTask(clearTask);
            countClear++;
         }
      }

      if (index >= this.f.length) {
         index = 0;
      }

      this.lastCleanIndex = index;
   }
}
