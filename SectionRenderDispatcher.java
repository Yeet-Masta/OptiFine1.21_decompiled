import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.src.C_1559_;
import net.minecraft.src.C_1706_;
import net.minecraft.src.C_1718_;
import net.minecraft.src.C_1873_;
import net.minecraft.src.C_2137_;
import net.minecraft.src.C_276405_;
import net.minecraft.src.C_290184_;
import net.minecraft.src.C_3040_;
import net.minecraft.src.C_313554_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4148_;
import net.minecraft.src.C_452_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4710_;
import net.minecraft.src.C_4675_.C_4681_;
import net.minecraftforge.client.model.data.ModelData;
import net.optifine.Config;
import net.optifine.CustomBlockLayers;
import net.optifine.reflect.Reflector;
import net.optifine.render.AabbFrame;
import net.optifine.render.ChunkLayerMap;
import net.optifine.render.ChunkLayerSet;
import net.optifine.render.ICamera;
import net.optifine.render.RenderTypes;
import net.optifine.util.ChunkUtils;

public class SectionRenderDispatcher {
   private static final int a = 2;
   private final PriorityBlockingQueue<SectionRenderDispatcher.b.a> b = Queues.newPriorityBlockingQueue();
   private final Queue<SectionRenderDispatcher.b.a> c = Queues.newLinkedBlockingDeque();
   private int d = 2;
   private final Queue<Runnable> e = Queues.newConcurrentLinkedQueue();
   final C_290184_ f;
   private final SectionBufferBuilderPool g;
   private volatile int h;
   private volatile boolean i;
   private final C_452_<Runnable> j;
   private final Executor k;
   ClientLevel l;
   final LevelRenderer m;
   private Vec3 n = Vec3.b;
   final SectionCompiler o;
   private int countRenderBuilders;
   private List<C_290184_> listPausedBuilders = new ArrayList();
   public static final RenderType[] BLOCK_RENDER_LAYERS = (RenderType[])RenderType.I().toArray(new RenderType[0]);
   public static final boolean FORGE = Reflector.ForgeHooksClient.exists();
   public static int renderChunksUpdated;

   public SectionRenderDispatcher(
      ClientLevel worldIn,
      LevelRenderer worldRendererIn,
      Executor executorIn,
      C_4148_ fixedBuffersIn,
      BlockRenderDispatcher blockRendererIn,
      BlockEntityRenderDispatcher blockEntityRendererIn
   ) {
      this.l = worldIn;
      this.m = worldRendererIn;
      this.f = fixedBuffersIn.m_110098_();
      this.g = fixedBuffersIn.b();
      this.countRenderBuilders = this.g.c();
      this.k = executorIn;
      this.j = C_452_.m_18751_(executorIn, "Section Renderer");
      this.j.m_6937_(this::j);
      this.o = new SectionCompiler(blockRendererIn, blockEntityRendererIn);
      this.o.sectionRenderDispatcher = this;
   }

   public void a(ClientLevel worldIn) {
      this.l = worldIn;
   }

   private void j() {
      if (!this.i && !this.g.b()) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask = this.k();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            C_290184_ sectionbufferbuilderpack = (C_290184_)Objects.requireNonNull(this.g.a());
            if (sectionbufferbuilderpack == null) {
               this.b.add(sectionrenderdispatcher$rendersection$compiletask);
               return;
            }

            this.h = this.b.size() + this.c.size();
            CompletableFuture.supplyAsync(
                  Util.a(
                     sectionrenderdispatcher$rendersection$compiletask.b(),
                     (Supplier)(() -> sectionrenderdispatcher$rendersection$compiletask.a(sectionbufferbuilderpack))
                  ),
                  this.k
               )
               .thenCompose(resultIn -> resultIn)
               .whenComplete((taskResultIn, throwableIn) -> {
                  if (throwableIn != null) {
                     C_3391_.m_91087_().a(CrashReport.a(throwableIn, "Batching sections"));
                  } else {
                     this.j.m_6937_((Runnable)() -> {
                        if (taskResultIn == SectionRenderDispatcher.c.a) {
                           sectionbufferbuilderpack.m_294577_();
                        } else {
                           sectionbufferbuilderpack.m_293358_();
                        }

                        this.g.a(sectionbufferbuilderpack);
                        this.j();
                     });
                  }
               });
         }
      }
   }

   @Nullable
   private SectionRenderDispatcher.b.a k() {
      if (this.d <= 0) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask = (SectionRenderDispatcher.b.a)this.c.poll();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            this.d = 2;
            return sectionrenderdispatcher$rendersection$compiletask;
         }
      }

      SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask1 = (SectionRenderDispatcher.b.a)this.b.poll();
      if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
         this.d--;
         return sectionrenderdispatcher$rendersection$compiletask1;
      } else {
         this.d = 2;
         return (SectionRenderDispatcher.b.a)this.c.poll();
      }
   }

   public String a() {
      return String.format(Locale.ROOT, "pC: %03d, pU: %02d, aB: %02d", this.h, this.e.size(), this.g.c());
   }

   public int b() {
      return this.h;
   }

   public int c() {
      return this.e.size();
   }

   public int d() {
      return this.g.c();
   }

   public void a(Vec3 posIn) {
      this.n = posIn;
   }

   public Vec3 e() {
      return this.n;
   }

   public void f() {
      Runnable runnable;
      while ((runnable = (Runnable)this.e.poll()) != null) {
         runnable.run();
      }
   }

   public void a(SectionRenderDispatcher.b chunkRenderIn, RenderRegionCache regionCacheIn) {
      chunkRenderIn.b(regionCacheIn);
   }

   public void g() {
      this.l();
   }

   public void a(SectionRenderDispatcher.b.a renderTaskIn) {
      if (!this.i) {
         this.j.m_6937_((Runnable)() -> {
            if (!this.i) {
               if (renderTaskIn.c) {
                  this.b.offer(renderTaskIn);
               } else {
                  this.c.offer(renderTaskIn);
               }

               this.h = this.b.size() + this.c.size();
               this.j();
            }
         });
      }
   }

   public CompletableFuture<Void> a(MeshData bufferIn, VertexBuffer vertexBufferIn) {
      return this.i ? CompletableFuture.completedFuture(null) : CompletableFuture.runAsync(() -> {
         if (vertexBufferIn.e()) {
            bufferIn.close();
         } else {
            vertexBufferIn.a();
            vertexBufferIn.a(bufferIn);
            VertexBuffer.b();
         }
      }, this.e::add);
   }

   public CompletableFuture<Void> a(ByteBufferBuilder.a resultIn, VertexBuffer bufferIn) {
      return this.i ? CompletableFuture.completedFuture(null) : CompletableFuture.runAsync(() -> {
         if (bufferIn.e()) {
            resultIn.close();
         } else {
            bufferIn.a();
            bufferIn.a(resultIn);
            VertexBuffer.b();
         }
      }, this.e::add);
   }

   private void l() {
      while (!this.b.isEmpty()) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask = (SectionRenderDispatcher.b.a)this.b.poll();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            sectionrenderdispatcher$rendersection$compiletask.a();
         }
      }

      while (!this.c.isEmpty()) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask1 = (SectionRenderDispatcher.b.a)this.c.poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            sectionrenderdispatcher$rendersection$compiletask1.a();
         }
      }

      this.h = 0;
   }

   public boolean h() {
      return this.h == 0 && this.e.isEmpty();
   }

   public void i() {
      this.i = true;
      this.l();
      this.f();
   }

   public void pauseChunkUpdates() {
      long timeStartMs = System.currentTimeMillis();
      if (this.listPausedBuilders.size() <= 0) {
         while (this.listPausedBuilders.size() != this.countRenderBuilders) {
            this.f();
            C_290184_ builder = this.g.a();
            if (builder != null) {
               this.listPausedBuilders.add(builder);
            }

            if (System.currentTimeMillis() > timeStartMs + 1000L) {
               break;
            }
         }
      }
   }

   public void resumeChunkUpdates() {
      for (C_290184_ builder : this.listPausedBuilders) {
         this.g.a(builder);
      }

      this.listPausedBuilders.clear();
   }

   public boolean updateChunkNow(SectionRenderDispatcher.b renderChunk, RenderRegionCache regionCacheIn) {
      this.a(renderChunk, regionCacheIn);
      return true;
   }

   public boolean updateChunkLater(SectionRenderDispatcher.b renderChunk, RenderRegionCache regionCacheIn) {
      if (this.g.b()) {
         return false;
      } else {
         renderChunk.a(this, regionCacheIn);
         return true;
      }
   }

   public boolean updateTransparencyLater(SectionRenderDispatcher.b renderChunk) {
      return this.g.b() ? false : renderChunk.a(RenderTypes.TRANSLUCENT, this);
   }

   public void addUploadTask(Runnable r) {
      if (r != null) {
         this.e.add(r);
      }
   }

   public static class a {
      public static final SectionRenderDispatcher.a a = new SectionRenderDispatcher.a() {
         @Override
         public boolean a(Direction facing, Direction facing2) {
            return false;
         }

         public void setAnimatedSprites(RenderType layer, BitSet animatedSprites) {
            throw new UnsupportedOperationException();
         }
      };
      public static final SectionRenderDispatcher.a b = new SectionRenderDispatcher.a() {
         @Override
         public boolean a(Direction facing, Direction facing2) {
            return true;
         }
      };
      final Set<RenderType> c = new ChunkLayerSet();
      final List<BlockEntity> d = Lists.newArrayList();
      VisibilitySet e = new VisibilitySet();
      @Nullable
      MeshData.b f;
      private BitSet[] animatedSprites = new BitSet[RenderType.CHUNK_RENDER_TYPES.length];

      public boolean a() {
         return this.c.isEmpty();
      }

      public boolean a(RenderType renderTypeIn) {
         return !this.c.contains(renderTypeIn);
      }

      public List<BlockEntity> b() {
         return this.d;
      }

      public boolean a(Direction facing, Direction facing2) {
         return this.e.a(facing, facing2);
      }

      public BitSet getAnimatedSprites(RenderType layer) {
         return this.animatedSprites[layer.ordinal()];
      }

      public void setAnimatedSprites(BitSet[] animatedSprites) {
         this.animatedSprites = animatedSprites;
      }

      public boolean isLayerUsed(RenderType renderTypeIn) {
         return this.c.contains(renderTypeIn);
      }

      public void setLayerUsed(RenderType renderTypeIn) {
         this.c.add(renderTypeIn);
      }

      public boolean hasTerrainBlockEntities() {
         return !this.a() || !this.b().isEmpty();
      }

      public Set<RenderType> getLayersUsed() {
         return this.c;
      }
   }

   public class b {
      public static final int a = 16;
      public final int b;
      public final AtomicReference<SectionRenderDispatcher.a> c = new AtomicReference(SectionRenderDispatcher.a.a);
      private final AtomicInteger e = new AtomicInteger(0);
      @Nullable
      private SectionRenderDispatcher.b.b f;
      @Nullable
      private SectionRenderDispatcher.b.c g;
      private final Set<BlockEntity> h = Sets.newHashSet();
      private final ChunkLayerMap<VertexBuffer> i = new ChunkLayerMap<>(renderType -> new VertexBuffer(VertexBuffer.a.a));
      private C_3040_ j;
      private boolean k = true;
      final C_4681_ l = new C_4681_(-1, -1, -1);
      private final C_4681_[] m = Util.a(new C_4681_[6], posArrIn -> {
         for (int i = 0; i < posArrIn.length; i++) {
            posArrIn[i] = new C_4681_();
         }
      });
      private boolean n;
      private final boolean isMipmaps = Config.isMipmaps();
      private boolean playerUpdate = false;
      private boolean needsBackgroundPriorityUpdate;
      private boolean renderRegions = Config.isRenderRegions();
      public int regionX;
      public int regionZ;
      public int regionDX;
      public int regionDY;
      public int regionDZ;
      private final SectionRenderDispatcher.b[] renderChunksOfset16 = new SectionRenderDispatcher.b[6];
      private boolean renderChunksOffset16Updated = false;
      private C_2137_ chunk;
      private SectionRenderDispatcher.b[] renderChunkNeighbours = new SectionRenderDispatcher.b[Direction.r.length];
      private SectionRenderDispatcher.b[] renderChunkNeighboursValid = new SectionRenderDispatcher.b[Direction.r.length];
      private boolean renderChunkNeighboursUpated = false;
      private SectionOcclusionGraph.d renderInfo = new SectionOcclusionGraph.d(this, null, 0);
      public AabbFrame boundingBoxParent;
      private C_4710_ sectionPosition;

      public b(final int indexIn, final int x, final int y, final int z) {
         this.b = indexIn;
         this.a(x, y, z);
      }

      private boolean a(C_4675_ blockPosIn) {
         return SectionRenderDispatcher.this.l
               .m_6522_(C_4710_.m_123171_(blockPosIn.m_123341_()), C_4710_.m_123171_(blockPosIn.m_123343_()), C_313554_.f_315432_, false)
            != null;
      }

      public boolean a() {
         int i = 24;
         return !(this.c() > 576.0) ? true : this.a(this.l);
      }

      public C_3040_ b() {
         return this.j;
      }

      public VertexBuffer a(RenderType renderTypeIn) {
         return (VertexBuffer)this.i.get(renderTypeIn);
      }

      public void a(int x, int y, int z) {
         this.k();
         this.l.m_122178_(x, y, z);
         this.sectionPosition = C_4710_.m_123199_(this.l);
         if (this.renderRegions) {
            int bits = 8;
            this.regionX = x >> bits << bits;
            this.regionZ = z >> bits << bits;
            this.regionDX = x - this.regionX;
            this.regionDY = y;
            this.regionDZ = z - this.regionZ;
         }

         this.j = new C_3040_((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));

         for (Direction direction : Direction.r) {
            this.m[direction.ordinal()].m_122190_(this.l).c(direction, 16);
         }

         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighboursUpated = false;

         for (int i = 0; i < this.renderChunkNeighbours.length; i++) {
            SectionRenderDispatcher.b neighbour = this.renderChunkNeighbours[i];
            if (neighbour != null) {
               neighbour.renderChunkNeighboursUpated = false;
            }
         }

         this.chunk = null;
         this.boundingBoxParent = null;
      }

      protected double c() {
         Camera camera = C_3391_.m_91087_().j.l();
         double d0 = this.j.f_82288_ + 8.0 - camera.b().c;
         double d1 = this.j.f_82289_ + 8.0 - camera.b().d;
         double d2 = this.j.f_82290_ + 8.0 - camera.b().e;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      public SectionRenderDispatcher.a d() {
         return (SectionRenderDispatcher.a)this.c.get();
      }

      private void k() {
         this.j();
         this.c.set(SectionRenderDispatcher.a.a);
         this.k = true;
      }

      public void e() {
         this.k();
         this.i.values().forEach(VertexBuffer::close);
      }

      public C_4675_ f() {
         return this.l;
      }

      public void a(boolean immediate) {
         boolean flag = this.k;
         this.k = true;
         this.n = immediate | (flag && this.n);
         if (this.isWorldPlayerUpdate()) {
            this.playerUpdate = true;
         }

         if (!flag) {
            SectionRenderDispatcher.this.m.onChunkRenderNeedsUpdate(this);
         }
      }

      public void g() {
         this.k = false;
         this.n = false;
         this.playerUpdate = false;
         this.needsBackgroundPriorityUpdate = false;
      }

      public boolean h() {
         return this.k;
      }

      public boolean i() {
         return this.k && this.n;
      }

      public C_4675_ a(Direction facing) {
         return this.m[facing.ordinal()];
      }

      public boolean a(RenderType renderTypeIn, SectionRenderDispatcher renderDispatcherIn) {
         SectionRenderDispatcher.a sectionrenderdispatcher$compiledsection = this.d();
         if (this.g != null) {
            this.g.a();
         }

         if (!sectionrenderdispatcher$compiledsection.c.contains(renderTypeIn)) {
            return false;
         } else {
            if (SectionRenderDispatcher.FORGE) {
               this.g = new SectionRenderDispatcher.b.c(new ChunkPos(this.f()), this.c(), sectionrenderdispatcher$compiledsection);
            } else {
               this.g = new SectionRenderDispatcher.b.c(this.c(), sectionrenderdispatcher$compiledsection);
            }

            renderDispatcherIn.a(this.g);
            return true;
         }
      }

      protected boolean j() {
         boolean flag = false;
         if (this.f != null) {
            this.f.a();
            this.f = null;
            flag = true;
         }

         if (this.g != null) {
            this.g.a();
            this.g = null;
         }

         return flag;
      }

      public SectionRenderDispatcher.b.a a(RenderRegionCache regionCacheIn) {
         boolean flag = this.j();
         RenderChunkRegion renderchunkregion = regionCacheIn.a(SectionRenderDispatcher.this.l, C_4710_.m_123199_(this.l));
         boolean flag1 = this.c.get() == SectionRenderDispatcher.a.a;
         if (flag1 && flag) {
            this.e.incrementAndGet();
         }

         ChunkPos forgeChunkPos = SectionRenderDispatcher.FORGE ? new ChunkPos(this.f()) : null;
         this.f = new SectionRenderDispatcher.b.b(forgeChunkPos, this.c(), renderchunkregion, !flag1 || this.e.get() > 2);
         return this.f;
      }

      public void a(SectionRenderDispatcher dispatcherIn, RenderRegionCache regionCacheIn) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask = this.a(regionCacheIn);
         dispatcherIn.a(sectionrenderdispatcher$rendersection$compiletask);
      }

      void a(Collection<BlockEntity> globalEntitiesIn) {
         Set<BlockEntity> set = Sets.newHashSet(globalEntitiesIn);
         Set<BlockEntity> set1;
         synchronized (this.h) {
            set1 = Sets.newHashSet(this.h);
            set.removeAll(this.h);
            set1.removeAll(globalEntitiesIn);
            this.h.clear();
            this.h.addAll(globalEntitiesIn);
         }

         SectionRenderDispatcher.this.m.a(set1, set);
      }

      public void b(RenderRegionCache regionCacheIn) {
         SectionRenderDispatcher.b.a sectionrenderdispatcher$rendersection$compiletask = this.a(regionCacheIn);
         sectionrenderdispatcher$rendersection$compiletask.a(SectionRenderDispatcher.this.f);
      }

      public boolean b(int x, int y, int z) {
         C_4675_ blockpos = this.f();
         return x == C_4710_.m_123171_(blockpos.m_123341_()) || z == C_4710_.m_123171_(blockpos.m_123343_()) || y == C_4710_.m_123171_(blockpos.m_123342_());
      }

      void a(SectionRenderDispatcher.a sectionIn) {
         this.c.set(sectionIn);
         this.e.set(0);
         SectionRenderDispatcher.this.m.a(this);
      }

      C_276405_ l() {
         Vec3 vec3 = SectionRenderDispatcher.this.e();
         return C_276405_.m_277071_(
            (float)this.regionDX + (float)(vec3.c - (double)this.l.m_123341_()),
            (float)this.regionDY + (float)(vec3.d - (double)this.l.m_123342_()),
            (float)this.regionDZ + (float)(vec3.e - (double)this.l.m_123343_())
         );
      }

      private boolean isWorldPlayerUpdate() {
         if (SectionRenderDispatcher.this.l instanceof ClientLevel) {
            ClientLevel worldClient = SectionRenderDispatcher.this.l;
            return worldClient.isPlayerUpdate();
         } else {
            return false;
         }
      }

      public boolean isPlayerUpdate() {
         return this.playerUpdate;
      }

      public void setNeedsBackgroundPriorityUpdate(boolean needsBackgroundPriorityUpdate) {
         this.needsBackgroundPriorityUpdate = needsBackgroundPriorityUpdate;
      }

      public boolean needsBackgroundPriorityUpdate() {
         return this.needsBackgroundPriorityUpdate;
      }

      private RenderType fixBlockLayer(C_1559_ worldReader, BlockState blockState, C_4675_ blockPos, RenderType layer) {
         if (CustomBlockLayers.isActive()) {
            RenderType layerCustom = CustomBlockLayers.getRenderLayer(worldReader, blockState, blockPos);
            if (layerCustom != null) {
               return layerCustom;
            }
         }

         if (this.isMipmaps) {
            if (layer == RenderTypes.CUTOUT) {
               C_1706_ block = blockState.m_60734_();
               if (block instanceof C_1873_) {
                  return layer;
               }

               if (block instanceof C_1718_) {
                  return layer;
               }

               return RenderTypes.CUTOUT_MIPPED;
            }
         } else if (layer == RenderTypes.CUTOUT_MIPPED) {
            return RenderTypes.CUTOUT;
         }

         return layer;
      }

      public SectionRenderDispatcher.b getRenderChunkOffset16(ViewArea viewFrustum, Direction facing) {
         if (!this.renderChunksOffset16Updated) {
            for (int i = 0; i < Direction.r.length; i++) {
               Direction ef = Direction.r[i];
               C_4675_ posOffset16 = this.a(ef);
               this.renderChunksOfset16[i] = viewFrustum.a(posOffset16);
            }

            this.renderChunksOffset16Updated = true;
         }

         return this.renderChunksOfset16[facing.ordinal()];
      }

      public C_2137_ getChunk() {
         return this.getChunk(this.l);
      }

      private C_2137_ getChunk(C_4675_ posIn) {
         C_2137_ chunkLocal = this.chunk;
         if (chunkLocal != null && ChunkUtils.isLoaded(chunkLocal)) {
            return chunkLocal;
         } else {
            chunkLocal = SectionRenderDispatcher.this.l.m_46745_(posIn);
            this.chunk = chunkLocal;
            return chunkLocal;
         }
      }

      public boolean isChunkRegionEmpty() {
         return this.isChunkRegionEmpty(this.l);
      }

      private boolean isChunkRegionEmpty(C_4675_ posIn) {
         int yStart = posIn.m_123342_();
         int yEnd = yStart + 15;
         return this.getChunk(posIn).m_5566_(yStart, yEnd);
      }

      public void setRenderChunkNeighbour(Direction facing, SectionRenderDispatcher.b neighbour) {
         this.renderChunkNeighbours[facing.ordinal()] = neighbour;
         this.renderChunkNeighboursValid[facing.ordinal()] = neighbour;
      }

      public SectionRenderDispatcher.b getRenderChunkNeighbour(Direction facing) {
         if (!this.renderChunkNeighboursUpated) {
            this.updateRenderChunkNeighboursValid();
         }

         return this.renderChunkNeighboursValid[facing.ordinal()];
      }

      public SectionOcclusionGraph.d getRenderInfo() {
         return this.renderInfo;
      }

      public SectionOcclusionGraph.d getRenderInfo(Direction dirIn, int counterIn) {
         this.renderInfo.initialize(dirIn, counterIn);
         return this.renderInfo;
      }

      private void updateRenderChunkNeighboursValid() {
         int x = this.f().m_123341_();
         int z = this.f().m_123343_();
         int north = Direction.c.ordinal();
         int south = Direction.d.ordinal();
         int west = Direction.e.ordinal();
         int east = Direction.f.ordinal();
         this.renderChunkNeighboursValid[north] = this.renderChunkNeighbours[north].f().m_123343_() == z - 16 ? this.renderChunkNeighbours[north] : null;
         this.renderChunkNeighboursValid[south] = this.renderChunkNeighbours[south].f().m_123343_() == z + 16 ? this.renderChunkNeighbours[south] : null;
         this.renderChunkNeighboursValid[west] = this.renderChunkNeighbours[west].f().m_123341_() == x - 16 ? this.renderChunkNeighbours[west] : null;
         this.renderChunkNeighboursValid[east] = this.renderChunkNeighbours[east].f().m_123341_() == x + 16 ? this.renderChunkNeighbours[east] : null;
         this.renderChunkNeighboursUpated = true;
      }

      public boolean isBoundingBoxInFrustum(ICamera camera, int frameCount) {
         return this.getBoundingBoxParent().isBoundingBoxInFrustumFully(camera, frameCount) ? true : camera.isBoundingBoxInFrustum(this.j);
      }

      public AabbFrame getBoundingBoxParent() {
         if (this.boundingBoxParent == null) {
            C_4675_ pos = this.f();
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            int bits = 5;
            int xp = x >> bits << bits;
            int yp = y >> bits << bits;
            int zp = z >> bits << bits;
            if (xp != x || yp != y || zp != z) {
               AabbFrame bbp = SectionRenderDispatcher.this.m.getRenderChunk(new C_4675_(xp, yp, zp)).getBoundingBoxParent();
               if (bbp != null && bbp.a == (double)xp && bbp.b == (double)yp && bbp.c == (double)zp) {
                  this.boundingBoxParent = bbp;
               }
            }

            if (this.boundingBoxParent == null) {
               int delta = 1 << bits;
               this.boundingBoxParent = new AabbFrame((double)xp, (double)yp, (double)zp, (double)(xp + delta), (double)(yp + delta), (double)(zp + delta));
            }
         }

         return this.boundingBoxParent;
      }

      public ClientLevel getWorld() {
         return SectionRenderDispatcher.this.l;
      }

      public C_4710_ getSectionPosition() {
         return this.sectionPosition;
      }

      public String toString() {
         return "pos: " + this.f();
      }

      abstract class a implements Comparable<SectionRenderDispatcher.b.a> {
         protected final double a;
         protected final AtomicBoolean b = new AtomicBoolean(false);
         protected final boolean c;
         protected Map<C_4675_, ModelData> modelData;

         public a(final double distanceSqIn, final boolean highPriorityIn) {
            this(null, distanceSqIn, highPriorityIn);
         }

         public a(ChunkPos pos, double distanceSqIn, boolean highPriorityIn) {
            this.a = distanceSqIn;
            this.c = highPriorityIn;
            if (pos == null) {
               this.modelData = Collections.emptyMap();
            } else {
               this.modelData = C_3391_.m_91087_().r.getModelDataManager().getAt(pos);
            }
         }

         public abstract CompletableFuture<SectionRenderDispatcher.c> a(C_290184_ var1);

         public abstract void a();

         protected abstract String b();

         public int a(SectionRenderDispatcher.b.a p_compareTo_1_) {
            return Doubles.compare(this.a, p_compareTo_1_.a);
         }

         public ModelData getModelData(C_4675_ pos) {
            return (ModelData)this.modelData.getOrDefault(pos, ModelData.EMPTY);
         }
      }

      class b extends SectionRenderDispatcher.b.a {
         @Nullable
         protected RenderChunkRegion d;

         public b(@Nullable final double distanceSqIn, final RenderChunkRegion renderCacheIn, final boolean highPriorityIn) {
            this(null, distanceSqIn, renderCacheIn, highPriorityIn);
         }

         public b(ChunkPos pos, @Nullable double distanceSqIn, RenderChunkRegion renderCacheIn, boolean highPriorityIn) {
            super(pos, distanceSqIn, highPriorityIn);
            this.d = renderCacheIn;
         }

         @Override
         protected String b() {
            return "rend_chk_rebuild";
         }

         @Override
         public CompletableFuture<SectionRenderDispatcher.c> a(C_290184_ builderIn) {
            if (this.b.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else if (!b.this.a()) {
               this.a();
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else if (this.b.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else {
               RenderChunkRegion renderchunkregion = this.d;
               this.d = null;
               if (renderchunkregion == null) {
                  b.this.a(SectionRenderDispatcher.a.b);
                  return CompletableFuture.completedFuture(SectionRenderDispatcher.c.a);
               } else {
                  C_4710_ sectionpos = C_4710_.m_123199_(b.this.l);
                  SectionCompiler.a sectioncompiler$results = SectionRenderDispatcher.this.o
                     .compile(sectionpos, renderchunkregion, b.this.l(), builderIn, b.this.regionDX, b.this.regionDY, b.this.regionDZ);
                  b.this.a(sectioncompiler$results.a);
                  if (this.b.get()) {
                     sectioncompiler$results.a();
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
                  } else {
                     SectionRenderDispatcher.a sectionrenderdispatcher$compiledsection = new SectionRenderDispatcher.a();
                     sectionrenderdispatcher$compiledsection.e = sectioncompiler$results.d;
                     sectionrenderdispatcher$compiledsection.d.addAll(sectioncompiler$results.b);
                     sectionrenderdispatcher$compiledsection.f = sectioncompiler$results.e;
                     sectionrenderdispatcher$compiledsection.setAnimatedSprites(sectioncompiler$results.animatedSprites);
                     List<CompletableFuture<Void>> list = new ArrayList(sectioncompiler$results.c.size());
                     sectioncompiler$results.c.forEach((renderTypeIn, bufferIn) -> {
                        list.add(SectionRenderDispatcher.this.a(bufferIn, b.this.a(renderTypeIn)));
                        sectionrenderdispatcher$compiledsection.c.add(renderTypeIn);
                     });
                     return Util.e(list).handle((voidIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           C_3391_.m_91087_().a(CrashReport.a(throwableIn, "Rendering section"));
                        }

                        if (this.b.get()) {
                           return SectionRenderDispatcher.c.b;
                        } else {
                           b.this.a(sectionrenderdispatcher$compiledsection);
                           return SectionRenderDispatcher.c.a;
                        }
                     });
                  }
               }
            }
         }

         @Override
         public void a() {
            this.d = null;
            if (this.b.compareAndSet(false, true)) {
               b.this.a(false);
            }
         }
      }

      class c extends SectionRenderDispatcher.b.a {
         private final SectionRenderDispatcher.a e;

         public c(final double distanceSqIn, final SectionRenderDispatcher.a compiledChunkIn) {
            this(null, distanceSqIn, compiledChunkIn);
         }

         public c(ChunkPos pos, double distanceSqIn, SectionRenderDispatcher.a compiledChunkIn) {
            super(pos, distanceSqIn, true);
            this.e = compiledChunkIn;
         }

         @Override
         protected String b() {
            return "rend_chk_sort";
         }

         @Override
         public CompletableFuture<SectionRenderDispatcher.c> a(C_290184_ builderIn) {
            if (this.b.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else if (!b.this.a()) {
               this.b.set(true);
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else if (this.b.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
            } else {
               MeshData.b meshdata$sortstate = this.e.f;
               if (meshdata$sortstate != null && !this.e.a(RenderType.f())) {
                  C_276405_ vertexsorting = b.this.l();
                  ByteBufferBuilder.a bytebufferbuilder$result = meshdata$sortstate.a(builderIn.a(RenderType.f()), vertexsorting);
                  if (bytebufferbuilder$result == null) {
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
                  } else if (this.b.get()) {
                     bytebufferbuilder$result.close();
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
                  } else {
                     CompletableFuture<SectionRenderDispatcher.c> completablefuture = SectionRenderDispatcher.this.a(
                           bytebufferbuilder$result, b.this.a(RenderType.f())
                        )
                        .thenApply(voidIn -> SectionRenderDispatcher.c.b);
                     return completablefuture.handle((taskResultIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           C_3391_.m_91087_().a(CrashReport.a(throwableIn, "Rendering section"));
                        }

                        return this.b.get() ? SectionRenderDispatcher.c.b : SectionRenderDispatcher.c.a;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(SectionRenderDispatcher.c.b);
               }
            }
         }

         @Override
         public void a() {
            this.b.set(true);
         }
      }
   }

   static enum c {
      a,
      b;
   }
}
