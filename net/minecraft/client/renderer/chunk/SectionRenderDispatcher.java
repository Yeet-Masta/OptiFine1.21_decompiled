package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.vertex.VertexSorting;
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
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
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
   private static final int f_291537_ = 2;
   private final PriorityBlockingQueue<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask> f_290449_ = Queues.newPriorityBlockingQueue();
   private final Queue<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask> f_291696_ = Queues.newLinkedBlockingDeque();
   private int f_291840_ = 2;
   private final Queue<Runnable> f_290841_ = Queues.newConcurrentLinkedQueue();
   final SectionBufferBuilderPack f_290794_;
   private final net.minecraft.client.renderer.SectionBufferBuilderPool f_302374_;
   private volatile int f_290603_;
   private volatile boolean f_302977_;
   private final ProcessorMailbox<Runnable> f_290713_;
   private final Executor f_291206_;
   net.minecraft.client.multiplayer.ClientLevel f_291400_;
   final net.minecraft.client.renderer.LevelRenderer f_290611_;
   private net.minecraft.world.phys.Vec3 f_290602_ = net.minecraft.world.phys.Vec3.f_82478_;
   final net.minecraft.client.renderer.chunk.SectionCompiler f_337370_;
   private int countRenderBuilders;
   private List<SectionBufferBuilderPack> listPausedBuilders = new ArrayList();
   public static final net.minecraft.client.renderer.RenderType[] BLOCK_RENDER_LAYERS = (net.minecraft.client.renderer.RenderType[])net.minecraft.client.renderer.RenderType.m_110506_()
      .toArray(new net.minecraft.client.renderer.RenderType[0]);
   public static final boolean FORGE = Reflector.ForgeHooksClient.exists();
   public static int renderChunksUpdated;

   public SectionRenderDispatcher(
      net.minecraft.client.multiplayer.ClientLevel worldIn,
      net.minecraft.client.renderer.LevelRenderer worldRendererIn,
      Executor executorIn,
      RenderBuffers fixedBuffersIn,
      net.minecraft.client.renderer.block.BlockRenderDispatcher blockRendererIn,
      net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher blockEntityRendererIn
   ) {
      this.f_291400_ = worldIn;
      this.f_290611_ = worldRendererIn;
      this.f_290794_ = fixedBuffersIn.m_110098_();
      this.f_302374_ = fixedBuffersIn.m_307906_();
      this.countRenderBuilders = this.f_302374_.m_306121_();
      this.f_291206_ = executorIn;
      this.f_290713_ = ProcessorMailbox.m_18751_(executorIn, "Section Renderer");
      this.f_290713_.m_6937_(this::m_293371_);
      this.f_337370_ = new net.minecraft.client.renderer.chunk.SectionCompiler(blockRendererIn, blockEntityRendererIn);
      this.f_337370_.sectionRenderDispatcher = this;
   }

   public void m_293166_(net.minecraft.client.multiplayer.ClientLevel worldIn) {
      this.f_291400_ = worldIn;
   }

   private void m_293371_() {
      if (!this.f_302977_ && !this.f_302374_.m_307681_()) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_293164_();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            SectionBufferBuilderPack sectionbufferbuilderpack = (SectionBufferBuilderPack)Objects.requireNonNull(this.f_302374_.m_307873_());
            if (sectionbufferbuilderpack == null) {
               this.f_290449_.add(sectionrenderdispatcher$rendersection$compiletask);
               return;
            }

            this.f_290603_ = this.f_290449_.size() + this.f_291696_.size();
            CompletableFuture.supplyAsync(
                  net.minecraft.Util.m_183946_(
                     sectionrenderdispatcher$rendersection$compiletask.m_294775_(),
                     () -> sectionrenderdispatcher$rendersection$compiletask.m_294443_(sectionbufferbuilderpack)
                  ),
                  this.f_291206_
               )
               .thenCompose(resultIn -> resultIn)
               .whenComplete((taskResultIn, throwableIn) -> {
                  if (throwableIn != null) {
                     Minecraft.m_91087_().m_231412_(net.minecraft.CrashReport.m_127521_(throwableIn, "Batching sections"));
                  } else {
                     this.f_290713_.m_6937_((Runnable)() -> {
                        if (taskResultIn == net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL) {
                           sectionbufferbuilderpack.m_294577_();
                        } else {
                           sectionbufferbuilderpack.m_293358_();
                        }

                        this.f_302374_.m_306477_(sectionbufferbuilderpack);
                        this.m_293371_();
                     });
                  }
               });
         }
      }
   }

   @Nullable
   private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask m_293164_() {
      if (this.f_291840_ <= 0) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask)this.f_291696_
            .poll();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            this.f_291840_ = 2;
            return sectionrenderdispatcher$rendersection$compiletask;
         }
      }

      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask1 = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask)this.f_290449_
         .poll();
      if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
         this.f_291840_--;
         return sectionrenderdispatcher$rendersection$compiletask1;
      } else {
         this.f_291840_ = 2;
         return (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask)this.f_291696_.poll();
      }
   }

   public String m_292950_() {
      return String.format(Locale.ROOT, "pC: %03d, pU: %02d, aB: %02d", this.f_290603_, this.f_290841_.size(), this.f_302374_.m_306121_());
   }

   public int m_293066_() {
      return this.f_290603_;
   }

   public int m_294057_() {
      return this.f_290841_.size();
   }

   public int m_293327_() {
      return this.f_302374_.m_306121_();
   }

   public void m_294870_(net.minecraft.world.phys.Vec3 posIn) {
      this.f_290602_ = posIn;
   }

   public net.minecraft.world.phys.Vec3 m_293014_() {
      return this.f_290602_;
   }

   public void m_295287_() {
      Runnable runnable;
      while ((runnable = (Runnable)this.f_290841_.poll()) != null) {
         runnable.run();
      }
   }

   public void m_295202_(
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection chunkRenderIn,
      net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn
   ) {
      chunkRenderIn.m_295370_(regionCacheIn);
   }

   public void m_295714_() {
      this.m_295487_();
   }

   public void m_294204_(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask renderTaskIn) {
      if (!this.f_302977_) {
         this.f_290713_.m_6937_((Runnable)() -> {
            if (!this.f_302977_) {
               if (renderTaskIn.f_290632_) {
                  this.f_290449_.offer(renderTaskIn);
               } else {
                  this.f_291696_.offer(renderTaskIn);
               }

               this.f_290603_ = this.f_290449_.size() + this.f_291696_.size();
               this.m_293371_();
            }
         });
      }
   }

   public CompletableFuture<Void> m_292947_(com.mojang.blaze3d.vertex.MeshData bufferIn, com.mojang.blaze3d.vertex.VertexBuffer vertexBufferIn) {
      return this.f_302977_ ? CompletableFuture.completedFuture(null) : CompletableFuture.runAsync(() -> {
         if (vertexBufferIn.m_231230_()) {
            bufferIn.close();
         } else {
            vertexBufferIn.m_85921_();
            vertexBufferIn.m_231221_(bufferIn);
            com.mojang.blaze3d.vertex.VertexBuffer.m_85931_();
         }
      }, this.f_290841_::add);
   }

   public CompletableFuture<Void> m_339467_(com.mojang.blaze3d.vertex.ByteBufferBuilder.Result resultIn, com.mojang.blaze3d.vertex.VertexBuffer bufferIn) {
      return this.f_302977_ ? CompletableFuture.completedFuture(null) : CompletableFuture.runAsync(() -> {
         if (bufferIn.m_231230_()) {
            resultIn.close();
         } else {
            bufferIn.m_85921_();
            bufferIn.m_338802_(resultIn);
            com.mojang.blaze3d.vertex.VertexBuffer.m_85931_();
         }
      }, this.f_290841_::add);
   }

   private void m_295487_() {
      while (!this.f_290449_.isEmpty()) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask)this.f_290449_
            .poll();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            sectionrenderdispatcher$rendersection$compiletask.m_292880_();
         }
      }

      while (!this.f_291696_.isEmpty()) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask1 = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask)this.f_291696_
            .poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            sectionrenderdispatcher$rendersection$compiletask1.m_292880_();
         }
      }

      this.f_290603_ = 0;
   }

   public boolean m_293214_() {
      return this.f_290603_ == 0 && this.f_290841_.isEmpty();
   }

   public void m_294449_() {
      this.f_302977_ = true;
      this.m_295487_();
      this.m_295287_();
   }

   public void pauseChunkUpdates() {
      long timeStartMs = System.currentTimeMillis();
      if (this.listPausedBuilders.size() <= 0) {
         while (this.listPausedBuilders.size() != this.countRenderBuilders) {
            this.m_295287_();
            SectionBufferBuilderPack builder = this.f_302374_.m_307873_();
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
      for (SectionBufferBuilderPack builder : this.listPausedBuilders) {
         this.f_302374_.m_306477_(builder);
      }

      this.listPausedBuilders.clear();
   }

   public boolean updateChunkNow(
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk,
      net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn
   ) {
      this.m_295202_(renderChunk, regionCacheIn);
      return true;
   }

   public boolean updateChunkLater(
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk,
      net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn
   ) {
      if (this.f_302374_.m_307681_()) {
         return false;
      } else {
         renderChunk.m_294845_(this, regionCacheIn);
         return true;
      }
   }

   public boolean updateTransparencyLater(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection renderChunk) {
      return this.f_302374_.m_307681_() ? false : renderChunk.m_294021_(RenderTypes.TRANSLUCENT, this);
   }

   public void addUploadTask(Runnable r) {
      if (r != null) {
         this.f_290841_.add(r);
      }
   }

   public static class CompiledSection {
      public static final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection f_290410_ = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection() {
         @Override
         public boolean m_293115_(net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2) {
            return false;
         }

         public void setAnimatedSprites(net.minecraft.client.renderer.RenderType layer, BitSet animatedSprites) {
            throw new UnsupportedOperationException();
         }
      };
      public static final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection f_336992_ = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection() {
         @Override
         public boolean m_293115_(net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2) {
            return true;
         }
      };
      final Set<net.minecraft.client.renderer.RenderType> f_290391_ = new ChunkLayerSet();
      final List<net.minecraft.world.level.block.entity.BlockEntity> f_290409_ = Lists.newArrayList();
      net.minecraft.client.renderer.chunk.VisibilitySet f_290920_ = new net.minecraft.client.renderer.chunk.VisibilitySet();
      @Nullable
      com.mojang.blaze3d.vertex.MeshData.SortState f_291674_;
      private BitSet[] animatedSprites = new BitSet[net.minecraft.client.renderer.RenderType.CHUNK_RENDER_TYPES.length];

      public boolean m_295467_() {
         return this.f_290391_.isEmpty();
      }

      public boolean m_294492_(net.minecraft.client.renderer.RenderType renderTypeIn) {
         return !this.f_290391_.contains(renderTypeIn);
      }

      public List<net.minecraft.world.level.block.entity.BlockEntity> m_293674_() {
         return this.f_290409_;
      }

      public boolean m_293115_(net.minecraft.core.Direction facing, net.minecraft.core.Direction facing2) {
         return this.f_290920_.m_112983_(facing, facing2);
      }

      public BitSet getAnimatedSprites(net.minecraft.client.renderer.RenderType layer) {
         return this.animatedSprites[layer.ordinal()];
      }

      public void setAnimatedSprites(BitSet[] animatedSprites) {
         this.animatedSprites = animatedSprites;
      }

      public boolean isLayerUsed(net.minecraft.client.renderer.RenderType renderTypeIn) {
         return this.f_290391_.contains(renderTypeIn);
      }

      public void setLayerUsed(net.minecraft.client.renderer.RenderType renderTypeIn) {
         this.f_290391_.add(renderTypeIn);
      }

      public boolean hasTerrainBlockEntities() {
         return !this.m_295467_() || !this.m_293674_().isEmpty();
      }

      public Set<net.minecraft.client.renderer.RenderType> getLayersUsed() {
         return this.f_290391_;
      }
   }

   public class RenderSection {
      public static final int f_291071_ = 16;
      public final int f_290488_;
      public final AtomicReference<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection> f_290312_ = new AtomicReference(
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection.f_290410_
      );
      private final AtomicInteger f_291503_ = new AtomicInteger(0);
      @Nullable
      private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.RebuildTask f_291315_;
      @Nullable
      private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.ResortTransparencyTask f_291330_;
      private final Set<net.minecraft.world.level.block.entity.BlockEntity> f_291787_ = Sets.newHashSet();
      private final ChunkLayerMap<com.mojang.blaze3d.vertex.VertexBuffer> f_291754_ = new ChunkLayerMap<>(
         renderType -> new com.mojang.blaze3d.vertex.VertexBuffer(com.mojang.blaze3d.vertex.VertexBuffer.Usage.STATIC)
      );
      private AABB f_290371_;
      private boolean f_291619_ = true;
      final MutableBlockPos f_291850_ = new MutableBlockPos(-1, -1, -1);
      private final MutableBlockPos[] f_291827_ = net.minecraft.Util.m_137469_(new MutableBlockPos[6], posArrIn -> {
         for (int i = 0; i < posArrIn.length; i++) {
            posArrIn[i] = new MutableBlockPos();
         }
      });
      private boolean f_291709_;
      private final boolean isMipmaps = Config.isMipmaps();
      private boolean playerUpdate = false;
      private boolean needsBackgroundPriorityUpdate;
      private boolean renderRegions = Config.isRenderRegions();
      public int regionX;
      public int regionZ;
      public int regionDX;
      public int regionDY;
      public int regionDZ;
      private final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[] renderChunksOfset16 = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[6];
      private boolean renderChunksOffset16Updated = false;
      private LevelChunk chunk;
      private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[] renderChunkNeighbours = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[net.minecraft.core.Direction.f_122346_.length];
      private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[] renderChunkNeighboursValid = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection[net.minecraft.core.Direction.f_122346_.length];
      private boolean renderChunkNeighboursUpated = false;
      private net.minecraft.client.renderer.SectionOcclusionGraph.Node renderInfo = new net.minecraft.client.renderer.SectionOcclusionGraph.Node(this, null, 0);
      public AabbFrame boundingBoxParent;
      private SectionPos sectionPosition;

      public RenderSection(final int indexIn, final int x, final int y, final int z) {
         this.f_290488_ = indexIn;
         this.m_292814_(x, y, z);
      }

      private boolean m_294104_(BlockPos blockPosIn) {
         return SectionRenderDispatcher.this.f_291400_
               .m_6522_(SectionPos.m_123171_(blockPosIn.m_123341_()), SectionPos.m_123171_(blockPosIn.m_123343_()), ChunkStatus.f_315432_, false)
            != null;
      }

      public boolean m_294718_() {
         int i = 24;
         return !(this.m_293828_() > 576.0) ? true : this.m_294104_(this.f_291850_);
      }

      public AABB m_293301_() {
         return this.f_290371_;
      }

      public com.mojang.blaze3d.vertex.VertexBuffer m_294581_(net.minecraft.client.renderer.RenderType renderTypeIn) {
         return this.f_291754_.get(renderTypeIn);
      }

      public void m_292814_(int x, int y, int z) {
         this.m_293096_();
         this.f_291850_.m_122178_(x, y, z);
         this.sectionPosition = SectionPos.m_123199_(this.f_291850_);
         if (this.renderRegions) {
            int bits = 8;
            this.regionX = x >> bits << bits;
            this.regionZ = z >> bits << bits;
            this.regionDX = x - this.regionX;
            this.regionDY = y;
            this.regionDZ = z - this.regionZ;
         }

         this.f_290371_ = new AABB((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));

         for (net.minecraft.core.Direction direction : net.minecraft.core.Direction.f_122346_) {
            this.f_291827_[direction.ordinal()].m_122190_(this.f_291850_).m_122175_(direction, 16);
         }

         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighboursUpated = false;

         for (int i = 0; i < this.renderChunkNeighbours.length; i++) {
            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection neighbour = this.renderChunkNeighbours[i];
            if (neighbour != null) {
               neighbour.renderChunkNeighboursUpated = false;
            }
         }

         this.chunk = null;
         this.boundingBoxParent = null;
      }

      protected double m_293828_() {
         net.minecraft.client.Camera camera = Minecraft.m_91087_().f_91063_.m_109153_();
         double d0 = this.f_290371_.f_82288_ + 8.0 - camera.m_90583_().f_82479_;
         double d1 = this.f_290371_.f_82289_ + 8.0 - camera.m_90583_().f_82480_;
         double d2 = this.f_290371_.f_82290_ + 8.0 - camera.m_90583_().f_82481_;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      public net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection m_293175_() {
         return (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection)this.f_290312_.get();
      }

      private void m_293096_() {
         this.m_294642_();
         this.f_290312_.set(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection.f_290410_);
         this.f_291619_ = true;
      }

      public void m_294345_() {
         this.m_293096_();
         this.f_291754_.values().forEach(com.mojang.blaze3d.vertex.VertexBuffer::close);
      }

      public BlockPos m_295500_() {
         return this.f_291850_;
      }

      public void m_292780_(boolean immediate) {
         boolean flag = this.f_291619_;
         this.f_291619_ = true;
         this.f_291709_ = immediate | (flag && this.f_291709_);
         if (this.isWorldPlayerUpdate()) {
            this.playerUpdate = true;
         }

         if (!flag) {
            SectionRenderDispatcher.this.f_290611_.onChunkRenderNeedsUpdate(this);
         }
      }

      public void m_294599_() {
         this.f_291619_ = false;
         this.f_291709_ = false;
         this.playerUpdate = false;
         this.needsBackgroundPriorityUpdate = false;
      }

      public boolean m_295586_() {
         return this.f_291619_;
      }

      public boolean m_295878_() {
         return this.f_291619_ && this.f_291709_;
      }

      public BlockPos m_292593_(net.minecraft.core.Direction facing) {
         return this.f_291827_[facing.ordinal()];
      }

      public boolean m_294021_(
         net.minecraft.client.renderer.RenderType renderTypeIn, net.minecraft.client.renderer.chunk.SectionRenderDispatcher renderDispatcherIn
      ) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection sectionrenderdispatcher$compiledsection = this.m_293175_();
         if (this.f_291330_ != null) {
            this.f_291330_.m_292880_();
         }

         if (!sectionrenderdispatcher$compiledsection.f_290391_.contains(renderTypeIn)) {
            return false;
         } else {
            if (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.FORGE) {
               this.f_291330_ = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.ResortTransparencyTask(
                  new net.minecraft.world.level.ChunkPos(this.m_295500_()), this.m_293828_(), sectionrenderdispatcher$compiledsection
               );
            } else {
               this.f_291330_ = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.ResortTransparencyTask(
                  this.m_293828_(), sectionrenderdispatcher$compiledsection
               );
            }

            renderDispatcherIn.m_294204_(this.f_291330_);
            return true;
         }
      }

      protected boolean m_294642_() {
         boolean flag = false;
         if (this.f_291315_ != null) {
            this.f_291315_.m_292880_();
            this.f_291315_ = null;
            flag = true;
         }

         if (this.f_291330_ != null) {
            this.f_291330_.m_292880_();
            this.f_291330_ = null;
         }

         return flag;
      }

      public net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask m_295128_(
         net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn
      ) {
         boolean flag = this.m_294642_();
         net.minecraft.client.renderer.chunk.RenderChunkRegion renderchunkregion = regionCacheIn.m_200465_(
            SectionRenderDispatcher.this.f_291400_, SectionPos.m_123199_(this.f_291850_)
         );
         boolean flag1 = this.f_290312_.get() == net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection.f_290410_;
         if (flag1 && flag) {
            this.f_291503_.incrementAndGet();
         }

         net.minecraft.world.level.ChunkPos forgeChunkPos = net.minecraft.client.renderer.chunk.SectionRenderDispatcher.FORGE
            ? new net.minecraft.world.level.ChunkPos(this.m_295500_())
            : null;
         this.f_291315_ = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.RebuildTask(
            forgeChunkPos, this.m_293828_(), renderchunkregion, !flag1 || this.f_291503_.get() > 2
         );
         return this.f_291315_;
      }

      public void m_294845_(
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher dispatcherIn, net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn
      ) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(
            regionCacheIn
         );
         dispatcherIn.m_294204_(sectionrenderdispatcher$rendersection$compiletask);
      }

      void m_295492_(Collection<net.minecraft.world.level.block.entity.BlockEntity> globalEntitiesIn) {
         Set<net.minecraft.world.level.block.entity.BlockEntity> set = Sets.newHashSet(globalEntitiesIn);
         Set<net.minecraft.world.level.block.entity.BlockEntity> set1;
         synchronized (this.f_291787_) {
            set1 = Sets.newHashSet(this.f_291787_);
            set.removeAll(this.f_291787_);
            set1.removeAll(globalEntitiesIn);
            this.f_291787_.clear();
            this.f_291787_.addAll(globalEntitiesIn);
         }

         SectionRenderDispatcher.this.f_290611_.m_109762_(set1, set);
      }

      public void m_295370_(net.minecraft.client.renderer.chunk.RenderRegionCache regionCacheIn) {
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(
            regionCacheIn
         );
         sectionrenderdispatcher$rendersection$compiletask.m_294443_(SectionRenderDispatcher.this.f_290794_);
      }

      public boolean m_292850_(int x, int y, int z) {
         BlockPos blockpos = this.m_295500_();
         return x == SectionPos.m_123171_(blockpos.m_123341_())
            || z == SectionPos.m_123171_(blockpos.m_123343_())
            || y == SectionPos.m_123171_(blockpos.m_123342_());
      }

      void m_339503_(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection sectionIn) {
         this.f_290312_.set(sectionIn);
         this.f_291503_.set(0);
         SectionRenderDispatcher.this.f_290611_.m_294499_(this);
      }

      VertexSorting m_338425_() {
         net.minecraft.world.phys.Vec3 vec3 = SectionRenderDispatcher.this.m_293014_();
         return VertexSorting.m_277071_(
            (float)this.regionDX + (float)(vec3.f_82479_ - (double)this.f_291850_.m_123341_()),
            (float)this.regionDY + (float)(vec3.f_82480_ - (double)this.f_291850_.m_123342_()),
            (float)this.regionDZ + (float)(vec3.f_82481_ - (double)this.f_291850_.m_123343_())
         );
      }

      private boolean isWorldPlayerUpdate() {
         if (SectionRenderDispatcher.this.f_291400_ instanceof net.minecraft.client.multiplayer.ClientLevel) {
            net.minecraft.client.multiplayer.ClientLevel worldClient = SectionRenderDispatcher.this.f_291400_;
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

      private net.minecraft.client.renderer.RenderType fixBlockLayer(
         BlockGetter worldReader,
         net.minecraft.world.level.block.state.BlockState blockState,
         BlockPos blockPos,
         net.minecraft.client.renderer.RenderType layer
      ) {
         if (CustomBlockLayers.isActive()) {
            net.minecraft.client.renderer.RenderType layerCustom = CustomBlockLayers.getRenderLayer(worldReader, blockState, blockPos);
            if (layerCustom != null) {
               return layerCustom;
            }
         }

         if (this.isMipmaps) {
            if (layer == RenderTypes.CUTOUT) {
               Block block = blockState.m_60734_();
               if (block instanceof RedStoneWireBlock) {
                  return layer;
               }

               if (block instanceof CactusBlock) {
                  return layer;
               }

               return RenderTypes.CUTOUT_MIPPED;
            }
         } else if (layer == RenderTypes.CUTOUT_MIPPED) {
            return RenderTypes.CUTOUT;
         }

         return layer;
      }

      public net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection getRenderChunkOffset16(
         net.minecraft.client.renderer.ViewArea viewFrustum, net.minecraft.core.Direction facing
      ) {
         if (!this.renderChunksOffset16Updated) {
            for (int i = 0; i < net.minecraft.core.Direction.f_122346_.length; i++) {
               net.minecraft.core.Direction ef = net.minecraft.core.Direction.f_122346_[i];
               BlockPos posOffset16 = this.m_292593_(ef);
               this.renderChunksOfset16[i] = viewFrustum.m_292642_(posOffset16);
            }

            this.renderChunksOffset16Updated = true;
         }

         return this.renderChunksOfset16[facing.ordinal()];
      }

      public LevelChunk getChunk() {
         return this.getChunk(this.f_291850_);
      }

      private LevelChunk getChunk(BlockPos posIn) {
         LevelChunk chunkLocal = this.chunk;
         if (chunkLocal != null && ChunkUtils.isLoaded(chunkLocal)) {
            return chunkLocal;
         } else {
            chunkLocal = SectionRenderDispatcher.this.f_291400_.m_46745_(posIn);
            this.chunk = chunkLocal;
            return chunkLocal;
         }
      }

      public boolean isChunkRegionEmpty() {
         return this.isChunkRegionEmpty(this.f_291850_);
      }

      private boolean isChunkRegionEmpty(BlockPos posIn) {
         int yStart = posIn.m_123342_();
         int yEnd = yStart + 15;
         return this.getChunk(posIn).m_5566_(yStart, yEnd);
      }

      public void setRenderChunkNeighbour(
         net.minecraft.core.Direction facing, net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection neighbour
      ) {
         this.renderChunkNeighbours[facing.ordinal()] = neighbour;
         this.renderChunkNeighboursValid[facing.ordinal()] = neighbour;
      }

      public net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection getRenderChunkNeighbour(net.minecraft.core.Direction facing) {
         if (!this.renderChunkNeighboursUpated) {
            this.updateRenderChunkNeighboursValid();
         }

         return this.renderChunkNeighboursValid[facing.ordinal()];
      }

      public net.minecraft.client.renderer.SectionOcclusionGraph.Node getRenderInfo() {
         return this.renderInfo;
      }

      public net.minecraft.client.renderer.SectionOcclusionGraph.Node getRenderInfo(net.minecraft.core.Direction dirIn, int counterIn) {
         this.renderInfo.initialize(dirIn, counterIn);
         return this.renderInfo;
      }

      private void updateRenderChunkNeighboursValid() {
         int x = this.m_295500_().m_123341_();
         int z = this.m_295500_().m_123343_();
         int north = net.minecraft.core.Direction.NORTH.ordinal();
         int south = net.minecraft.core.Direction.SOUTH.ordinal();
         int west = net.minecraft.core.Direction.WEST.ordinal();
         int east = net.minecraft.core.Direction.EAST.ordinal();
         this.renderChunkNeighboursValid[north] = this.renderChunkNeighbours[north].m_295500_().m_123343_() == z - 16
            ? this.renderChunkNeighbours[north]
            : null;
         this.renderChunkNeighboursValid[south] = this.renderChunkNeighbours[south].m_295500_().m_123343_() == z + 16
            ? this.renderChunkNeighbours[south]
            : null;
         this.renderChunkNeighboursValid[west] = this.renderChunkNeighbours[west].m_295500_().m_123341_() == x - 16 ? this.renderChunkNeighbours[west] : null;
         this.renderChunkNeighboursValid[east] = this.renderChunkNeighbours[east].m_295500_().m_123341_() == x + 16 ? this.renderChunkNeighbours[east] : null;
         this.renderChunkNeighboursUpated = true;
      }

      public boolean isBoundingBoxInFrustum(ICamera camera, int frameCount) {
         return this.getBoundingBoxParent().isBoundingBoxInFrustumFully(camera, frameCount) ? true : camera.isBoundingBoxInFrustum(this.f_290371_);
      }

      public AabbFrame getBoundingBoxParent() {
         if (this.boundingBoxParent == null) {
            BlockPos pos = this.m_295500_();
            int x = pos.m_123341_();
            int y = pos.m_123342_();
            int z = pos.m_123343_();
            int bits = 5;
            int xp = x >> bits << bits;
            int yp = y >> bits << bits;
            int zp = z >> bits << bits;
            if (xp != x || yp != y || zp != z) {
               AabbFrame bbp = SectionRenderDispatcher.this.f_290611_.getRenderChunk(new BlockPos(xp, yp, zp)).getBoundingBoxParent();
               if (bbp != null && bbp.f_82288_ == (double)xp && bbp.f_82289_ == (double)yp && bbp.f_82290_ == (double)zp) {
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

      public net.minecraft.client.multiplayer.ClientLevel getWorld() {
         return SectionRenderDispatcher.this.f_291400_;
      }

      public SectionPos getSectionPosition() {
         return this.sectionPosition;
      }

      public String toString() {
         return "pos: " + this.m_295500_();
      }

      abstract class CompileTask implements Comparable<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask> {
         protected final double f_290350_;
         protected final AtomicBoolean f_291175_ = new AtomicBoolean(false);
         protected final boolean f_290632_;
         protected Map<BlockPos, ModelData> modelData;

         public CompileTask(final double distanceSqIn, final boolean highPriorityIn) {
            this(null, distanceSqIn, highPriorityIn);
         }

         public CompileTask(net.minecraft.world.level.ChunkPos pos, double distanceSqIn, boolean highPriorityIn) {
            this.f_290350_ = distanceSqIn;
            this.f_290632_ = highPriorityIn;
            if (pos == null) {
               this.modelData = Collections.emptyMap();
            } else {
               this.modelData = Minecraft.m_91087_().f_91073_.getModelDataManager().getAt(pos);
            }
         }

         public abstract CompletableFuture<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult> m_294443_(
            SectionBufferBuilderPack var1
         );

         public abstract void m_292880_();

         protected abstract String m_294775_();

         public int compareTo(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask p_compareTo_1_) {
            return Doubles.compare(this.f_290350_, p_compareTo_1_.f_290350_);
         }

         public ModelData getModelData(BlockPos pos) {
            return (ModelData)this.modelData.getOrDefault(pos, ModelData.EMPTY);
         }
      }

      class RebuildTask extends net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask {
         @Nullable
         protected net.minecraft.client.renderer.chunk.RenderChunkRegion f_290484_;

         public RebuildTask(
            @Nullable final double distanceSqIn, final net.minecraft.client.renderer.chunk.RenderChunkRegion renderCacheIn, final boolean highPriorityIn
         ) {
            this(null, distanceSqIn, renderCacheIn, highPriorityIn);
         }

         public RebuildTask(
            net.minecraft.world.level.ChunkPos pos,
            @Nullable double distanceSqIn,
            net.minecraft.client.renderer.chunk.RenderChunkRegion renderCacheIn,
            boolean highPriorityIn
         ) {
            super(pos, distanceSqIn, highPriorityIn);
            this.f_290484_ = renderCacheIn;
         }

         @Override
         protected String m_294775_() {
            return "rend_chk_rebuild";
         }

         @Override
         public CompletableFuture<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult> m_294443_(SectionBufferBuilderPack builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (!RenderSection.this.m_294718_()) {
               this.m_292880_();
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else {
               net.minecraft.client.renderer.chunk.RenderChunkRegion renderchunkregion = this.f_290484_;
               this.f_290484_ = null;
               if (renderchunkregion == null) {
                  RenderSection.this.m_339503_(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection.f_336992_);
                  return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL);
               } else {
                  SectionPos sectionpos = SectionPos.m_123199_(RenderSection.this.f_291850_);
                  net.minecraft.client.renderer.chunk.SectionCompiler.Results sectioncompiler$results = SectionRenderDispatcher.this.f_337370_
                     .compile(
                        sectionpos,
                        renderchunkregion,
                        RenderSection.this.m_338425_(),
                        builderIn,
                        RenderSection.this.regionDX,
                        RenderSection.this.regionDY,
                        RenderSection.this.regionDZ
                     );
                  RenderSection.this.m_295492_(sectioncompiler$results.f_337223_);
                  if (this.f_291175_.get()) {
                     sectioncompiler$results.m_340536_();
                     return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else {
                     net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection sectionrenderdispatcher$compiledsection = new net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection();
                     sectionrenderdispatcher$compiledsection.f_290920_ = sectioncompiler$results.f_337613_;
                     sectionrenderdispatcher$compiledsection.f_290409_.addAll(sectioncompiler$results.f_337248_);
                     sectionrenderdispatcher$compiledsection.f_291674_ = sectioncompiler$results.f_337382_;
                     sectionrenderdispatcher$compiledsection.setAnimatedSprites(sectioncompiler$results.animatedSprites);
                     List<CompletableFuture<Void>> list = new ArrayList(sectioncompiler$results.f_336965_.size());
                     sectioncompiler$results.f_336965_.forEach((renderTypeIn, bufferIn) -> {
                        list.add(SectionRenderDispatcher.this.m_292947_(bufferIn, RenderSection.this.m_294581_(renderTypeIn)));
                        sectionrenderdispatcher$compiledsection.f_290391_.add(renderTypeIn);
                     });
                     return net.minecraft.Util.m_143840_(list).handle((voidIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           Minecraft.m_91087_().m_231412_(net.minecraft.CrashReport.m_127521_(throwableIn, "Rendering section"));
                        }

                        if (this.f_291175_.get()) {
                           return net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED;
                        } else {
                           RenderSection.this.m_339503_(sectionrenderdispatcher$compiledsection);
                           return net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
                        }
                     });
                  }
               }
            }
         }

         @Override
         public void m_292880_() {
            this.f_290484_ = null;
            if (this.f_291175_.compareAndSet(false, true)) {
               RenderSection.this.m_292780_(false);
            }
         }
      }

      class ResortTransparencyTask extends net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection.CompileTask {
         private final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection f_291899_;

         public ResortTransparencyTask(
            final double distanceSqIn, final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection compiledChunkIn
         ) {
            this(null, distanceSqIn, compiledChunkIn);
         }

         public ResortTransparencyTask(
            net.minecraft.world.level.ChunkPos pos,
            double distanceSqIn,
            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection compiledChunkIn
         ) {
            super(pos, distanceSqIn, true);
            this.f_291899_ = compiledChunkIn;
         }

         @Override
         protected String m_294775_() {
            return "rend_chk_sort";
         }

         @Override
         public CompletableFuture<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult> m_294443_(SectionBufferBuilderPack builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (!RenderSection.this.m_294718_()) {
               this.f_291175_.set(true);
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else {
               com.mojang.blaze3d.vertex.MeshData.SortState meshdata$sortstate = this.f_291899_.f_291674_;
               if (meshdata$sortstate != null && !this.f_291899_.m_294492_(net.minecraft.client.renderer.RenderType.m_110466_())) {
                  VertexSorting vertexsorting = RenderSection.this.m_338425_();
                  com.mojang.blaze3d.vertex.ByteBufferBuilder.Result bytebufferbuilder$result = meshdata$sortstate.m_340180_(
                     builderIn.m_339320_(net.minecraft.client.renderer.RenderType.m_110466_()), vertexsorting
                  );
                  if (bytebufferbuilder$result == null) {
                     return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else if (this.f_291175_.get()) {
                     bytebufferbuilder$result.close();
                     return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else {
                     CompletableFuture<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult> completablefuture = SectionRenderDispatcher.this.m_339467_(
                           bytebufferbuilder$result, RenderSection.this.m_294581_(net.minecraft.client.renderer.RenderType.m_110466_())
                        )
                        .thenApply(voidIn -> net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                     return completablefuture.handle(
                        (taskResultIn, throwableIn) -> {
                           if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                              Minecraft.m_91087_().m_231412_(net.minecraft.CrashReport.m_127521_(throwableIn, "Rendering section"));
                           }

                           return this.f_291175_.get()
                              ? net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED
                              : net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
                        }
                     );
                  }
               } else {
                  return CompletableFuture.completedFuture(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.SectionTaskResult.CANCELLED);
               }
            }
         }

         @Override
         public void m_292880_() {
            this.f_291175_.set(true);
         }
      }
   }

   static enum SectionTaskResult {
      SUCCESSFUL,
      CANCELLED;
   }
}
