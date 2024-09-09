package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexSorting;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
import net.minecraft.CrashReport;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.SectionBufferBuilderPool;
import net.minecraft.client.renderer.SectionOcclusionGraph;
import net.minecraft.client.renderer.ViewArea;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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
   private final PriorityBlockingQueue f_290449_ = Queues.newPriorityBlockingQueue();
   private final Queue f_291696_ = Queues.newLinkedBlockingDeque();
   private int f_291840_ = 2;
   private final Queue f_290841_ = Queues.newConcurrentLinkedQueue();
   final SectionBufferBuilderPack f_290794_;
   private final SectionBufferBuilderPool f_302374_;
   private volatile int f_290603_;
   private volatile boolean f_302977_;
   private final ProcessorMailbox f_290713_;
   private final Executor f_291206_;
   ClientLevel f_291400_;
   final LevelRenderer f_290611_;
   private Vec3 f_290602_;
   final SectionCompiler f_337370_;
   private int countRenderBuilders;
   private List listPausedBuilders;
   public static final RenderType[] BLOCK_RENDER_LAYERS = (RenderType[])RenderType.m_110506_().toArray(new RenderType[0]);
   public static final boolean FORGE;
   public static int renderChunksUpdated;

   public SectionRenderDispatcher(ClientLevel worldIn, LevelRenderer worldRendererIn, Executor executorIn, RenderBuffers fixedBuffersIn, BlockRenderDispatcher blockRendererIn, BlockEntityRenderDispatcher blockEntityRendererIn) {
      this.f_290602_ = Vec3.f_82478_;
      this.listPausedBuilders = new ArrayList();
      this.f_291400_ = worldIn;
      this.f_290611_ = worldRendererIn;
      this.f_290794_ = fixedBuffersIn.m_110098_();
      this.f_302374_ = fixedBuffersIn.m_307906_();
      this.countRenderBuilders = this.f_302374_.m_306121_();
      this.f_291206_ = executorIn;
      this.f_290713_ = ProcessorMailbox.m_18751_(executorIn, "Section Renderer");
      this.f_290713_.m_6937_(this::m_293371_);
      this.f_337370_ = new SectionCompiler(blockRendererIn, blockEntityRendererIn);
      this.f_337370_.sectionRenderDispatcher = this;
   }

   public void m_293166_(ClientLevel worldIn) {
      this.f_291400_ = worldIn;
   }

   private void m_293371_() {
      if (!this.f_302977_ && !this.f_302374_.m_307681_()) {
         RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_293164_();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            SectionBufferBuilderPack sectionbufferbuilderpack = (SectionBufferBuilderPack)Objects.requireNonNull(this.f_302374_.m_307873_());
            if (sectionbufferbuilderpack == null) {
               this.f_290449_.add(sectionrenderdispatcher$rendersection$compiletask);
               return;
            }

            this.f_290603_ = this.f_290449_.size() + this.f_291696_.size();
            CompletableFuture.supplyAsync(Util.m_183946_(sectionrenderdispatcher$rendersection$compiletask.m_294775_(), () -> {
               return sectionrenderdispatcher$rendersection$compiletask.m_294443_(sectionbufferbuilderpack);
            }), this.f_291206_).thenCompose((resultIn) -> {
               return resultIn;
            }).whenComplete((taskResultIn, throwableIn) -> {
               if (throwableIn != null) {
                  Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(throwableIn, "Batching sections"));
               } else {
                  this.f_290713_.m_6937_(() -> {
                     if (taskResultIn == SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL) {
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
   private RenderSection.CompileTask m_293164_() {
      RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask1;
      if (this.f_291840_ <= 0) {
         sectionrenderdispatcher$rendersection$compiletask1 = (RenderSection.CompileTask)this.f_291696_.poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            this.f_291840_ = 2;
            return sectionrenderdispatcher$rendersection$compiletask1;
         }
      }

      sectionrenderdispatcher$rendersection$compiletask1 = (RenderSection.CompileTask)this.f_290449_.poll();
      if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
         --this.f_291840_;
         return sectionrenderdispatcher$rendersection$compiletask1;
      } else {
         this.f_291840_ = 2;
         return (RenderSection.CompileTask)this.f_291696_.poll();
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

   public void m_294870_(Vec3 posIn) {
      this.f_290602_ = posIn;
   }

   public Vec3 m_293014_() {
      return this.f_290602_;
   }

   public void m_295287_() {
      Runnable runnable;
      while((runnable = (Runnable)this.f_290841_.poll()) != null) {
         runnable.run();
      }

   }

   public void m_295202_(RenderSection chunkRenderIn, RenderRegionCache regionCacheIn) {
      chunkRenderIn.m_295370_(regionCacheIn);
   }

   public void m_295714_() {
      this.m_295487_();
   }

   public void m_294204_(RenderSection.CompileTask renderTaskIn) {
      if (!this.f_302977_) {
         this.f_290713_.m_6937_(() -> {
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

   public CompletableFuture m_292947_(MeshData bufferIn, VertexBuffer vertexBufferIn) {
      CompletableFuture var10000;
      if (this.f_302977_) {
         var10000 = CompletableFuture.completedFuture((Object)null);
      } else {
         Runnable var3 = () -> {
            if (vertexBufferIn.m_231230_()) {
               bufferIn.close();
            } else {
               vertexBufferIn.m_85921_();
               vertexBufferIn.m_231221_(bufferIn);
               VertexBuffer.m_85931_();
            }

         };
         Queue var10001 = this.f_290841_;
         Objects.requireNonNull(var10001);
         var10000 = CompletableFuture.runAsync(var3, var10001::add);
      }

      return var10000;
   }

   public CompletableFuture m_339467_(ByteBufferBuilder.Result resultIn, VertexBuffer bufferIn) {
      CompletableFuture var10000;
      if (this.f_302977_) {
         var10000 = CompletableFuture.completedFuture((Object)null);
      } else {
         Runnable var3 = () -> {
            if (bufferIn.m_231230_()) {
               resultIn.close();
            } else {
               bufferIn.m_85921_();
               bufferIn.m_338802_(resultIn);
               VertexBuffer.m_85931_();
            }

         };
         Queue var10001 = this.f_290841_;
         Objects.requireNonNull(var10001);
         var10000 = CompletableFuture.runAsync(var3, var10001::add);
      }

      return var10000;
   }

   private void m_295487_() {
      RenderSection.CompileTask sectionrenderdispatcher$rendersection$compiletask1;
      while(!this.f_290449_.isEmpty()) {
         sectionrenderdispatcher$rendersection$compiletask1 = (RenderSection.CompileTask)this.f_290449_.poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            sectionrenderdispatcher$rendersection$compiletask1.m_292880_();
         }
      }

      while(!this.f_291696_.isEmpty()) {
         sectionrenderdispatcher$rendersection$compiletask1 = (RenderSection.CompileTask)this.f_291696_.poll();
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
         while(this.listPausedBuilders.size() != this.countRenderBuilders) {
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
      Iterator var1 = this.listPausedBuilders.iterator();

      while(var1.hasNext()) {
         SectionBufferBuilderPack builder = (SectionBufferBuilderPack)var1.next();
         this.f_302374_.m_306477_(builder);
      }

      this.listPausedBuilders.clear();
   }

   public boolean updateChunkNow(RenderSection renderChunk, RenderRegionCache regionCacheIn) {
      this.m_295202_(renderChunk, regionCacheIn);
      return true;
   }

   public boolean updateChunkLater(RenderSection renderChunk, RenderRegionCache regionCacheIn) {
      if (this.f_302374_.m_307681_()) {
         return false;
      } else {
         renderChunk.m_294845_(this, regionCacheIn);
         return true;
      }
   }

   public boolean updateTransparencyLater(RenderSection renderChunk) {
      return this.f_302374_.m_307681_() ? false : renderChunk.m_294021_(RenderTypes.TRANSLUCENT, this);
   }

   public void addUploadTask(Runnable r) {
      if (r != null) {
         this.f_290841_.add(r);
      }
   }

   static {
      FORGE = Reflector.ForgeHooksClient.exists();
   }

   public class RenderSection {
      public static final int f_291071_ = 16;
      public final int f_290488_;
      public final AtomicReference f_290312_;
      private final AtomicInteger f_291503_;
      @Nullable
      private RebuildTask f_291315_;
      @Nullable
      private ResortTransparencyTask f_291330_;
      private final Set f_291787_;
      private final ChunkLayerMap f_291754_;
      private AABB f_290371_;
      private boolean f_291619_;
      final BlockPos.MutableBlockPos f_291850_;
      private final BlockPos.MutableBlockPos[] f_291827_;
      private boolean f_291709_;
      private final boolean isMipmaps;
      private boolean playerUpdate;
      private boolean needsBackgroundPriorityUpdate;
      private boolean renderRegions;
      public int regionX;
      public int regionZ;
      public int regionDX;
      public int regionDY;
      public int regionDZ;
      private final RenderSection[] renderChunksOfset16;
      private boolean renderChunksOffset16Updated;
      private LevelChunk chunk;
      private RenderSection[] renderChunkNeighbours;
      private RenderSection[] renderChunkNeighboursValid;
      private boolean renderChunkNeighboursUpated;
      private SectionOcclusionGraph.Node renderInfo;
      public AabbFrame boundingBoxParent;
      private SectionPos sectionPosition;

      public RenderSection(final int indexIn, final int x, final int y, final int z) {
         this.f_290312_ = new AtomicReference(SectionRenderDispatcher.CompiledSection.f_290410_);
         this.f_291503_ = new AtomicInteger(0);
         this.f_291787_ = Sets.newHashSet();
         this.f_291754_ = new ChunkLayerMap((renderType) -> {
            return new VertexBuffer(VertexBuffer.Usage.STATIC);
         });
         this.f_291619_ = true;
         this.f_291850_ = new BlockPos.MutableBlockPos(-1, -1, -1);
         this.f_291827_ = (BlockPos.MutableBlockPos[])Util.m_137469_(new BlockPos.MutableBlockPos[6], (posArrIn) -> {
            for(int i = 0; i < posArrIn.length; ++i) {
               posArrIn[i] = new BlockPos.MutableBlockPos();
            }

         });
         this.isMipmaps = Config.isMipmaps();
         this.playerUpdate = false;
         this.renderRegions = Config.isRenderRegions();
         this.renderChunksOfset16 = new RenderSection[6];
         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighbours = new RenderSection[Direction.f_122346_.length];
         this.renderChunkNeighboursValid = new RenderSection[Direction.f_122346_.length];
         this.renderChunkNeighboursUpated = false;
         this.renderInfo = new SectionOcclusionGraph.Node(this, (Direction)null, 0);
         this.f_290488_ = indexIn;
         this.m_292814_(x, y, z);
      }

      private boolean m_294104_(BlockPos blockPosIn) {
         return SectionRenderDispatcher.this.f_291400_.m_6522_(SectionPos.m_123171_(blockPosIn.m_123341_()), SectionPos.m_123171_(blockPosIn.m_123343_()), ChunkStatus.f_315432_, false) != null;
      }

      public boolean m_294718_() {
         int i = true;
         return !(this.m_293828_() > 576.0) ? true : this.m_294104_(this.f_291850_);
      }

      public AABB m_293301_() {
         return this.f_290371_;
      }

      public VertexBuffer m_294581_(RenderType renderTypeIn) {
         return (VertexBuffer)this.f_291754_.get(renderTypeIn);
      }

      public void m_292814_(int x, int y, int z) {
         this.m_293096_();
         this.f_291850_.m_122178_(x, y, z);
         this.sectionPosition = SectionPos.m_123199_(this.f_291850_);
         int i;
         if (this.renderRegions) {
            i = 8;
            this.regionX = x >> i << i;
            this.regionZ = z >> i << i;
            this.regionDX = x - this.regionX;
            this.regionDY = y;
            this.regionDZ = z - this.regionZ;
         }

         this.f_290371_ = new AABB((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));
         Direction[] var8 = Direction.f_122346_;
         int var5 = var8.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Direction direction = var8[var6];
            this.f_291827_[direction.ordinal()].m_122190_(this.f_291850_).m_122175_(direction, 16);
         }

         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighboursUpated = false;

         for(i = 0; i < this.renderChunkNeighbours.length; ++i) {
            RenderSection neighbour = this.renderChunkNeighbours[i];
            if (neighbour != null) {
               neighbour.renderChunkNeighboursUpated = false;
            }
         }

         this.chunk = null;
         this.boundingBoxParent = null;
      }

      protected double m_293828_() {
         Camera camera = Minecraft.m_91087_().f_91063_.m_109153_();
         double d0 = this.f_290371_.f_82288_ + 8.0 - camera.m_90583_().f_82479_;
         double d1 = this.f_290371_.f_82289_ + 8.0 - camera.m_90583_().f_82480_;
         double d2 = this.f_290371_.f_82290_ + 8.0 - camera.m_90583_().f_82481_;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      public CompiledSection m_293175_() {
         return (CompiledSection)this.f_290312_.get();
      }

      private void m_293096_() {
         this.m_294642_();
         this.f_290312_.set(SectionRenderDispatcher.CompiledSection.f_290410_);
         this.f_291619_ = true;
      }

      public void m_294345_() {
         this.m_293096_();
         this.f_291754_.values().forEach(VertexBuffer::close);
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

      public BlockPos m_292593_(Direction facing) {
         return this.f_291827_[facing.ordinal()];
      }

      public boolean m_294021_(RenderType renderTypeIn, SectionRenderDispatcher renderDispatcherIn) {
         CompiledSection sectionrenderdispatcher$compiledsection = this.m_293175_();
         if (this.f_291330_ != null) {
            this.f_291330_.m_292880_();
         }

         if (!sectionrenderdispatcher$compiledsection.f_290391_.contains(renderTypeIn)) {
            return false;
         } else {
            if (SectionRenderDispatcher.FORGE) {
               this.f_291330_ = new ResortTransparencyTask(new ChunkPos(this.m_295500_()), this.m_293828_(), sectionrenderdispatcher$compiledsection);
            } else {
               this.f_291330_ = new ResortTransparencyTask(this.m_293828_(), sectionrenderdispatcher$compiledsection);
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

      public CompileTask m_295128_(RenderRegionCache regionCacheIn) {
         boolean flag = this.m_294642_();
         RenderChunkRegion renderchunkregion = regionCacheIn.m_200465_(SectionRenderDispatcher.this.f_291400_, SectionPos.m_123199_(this.f_291850_));
         boolean flag1 = this.f_290312_.get() == SectionRenderDispatcher.CompiledSection.f_290410_;
         if (flag1 && flag) {
            this.f_291503_.incrementAndGet();
         }

         ChunkPos forgeChunkPos = SectionRenderDispatcher.FORGE ? new ChunkPos(this.m_295500_()) : null;
         this.f_291315_ = new RebuildTask(forgeChunkPos, this.m_293828_(), renderchunkregion, !flag1 || this.f_291503_.get() > 2);
         return this.f_291315_;
      }

      public void m_294845_(SectionRenderDispatcher dispatcherIn, RenderRegionCache regionCacheIn) {
         CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(regionCacheIn);
         dispatcherIn.m_294204_(sectionrenderdispatcher$rendersection$compiletask);
      }

      void m_295492_(Collection globalEntitiesIn) {
         Set set = Sets.newHashSet(globalEntitiesIn);
         HashSet set1;
         synchronized(this.f_291787_) {
            set1 = Sets.newHashSet(this.f_291787_);
            set.removeAll(this.f_291787_);
            set1.removeAll(globalEntitiesIn);
            this.f_291787_.clear();
            this.f_291787_.addAll(globalEntitiesIn);
         }

         SectionRenderDispatcher.this.f_290611_.m_109762_(set1, set);
      }

      public void m_295370_(RenderRegionCache regionCacheIn) {
         CompileTask sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(regionCacheIn);
         sectionrenderdispatcher$rendersection$compiletask.m_294443_(SectionRenderDispatcher.this.f_290794_);
      }

      public boolean m_292850_(int x, int y, int z) {
         BlockPos blockpos = this.m_295500_();
         return x == SectionPos.m_123171_(blockpos.m_123341_()) || z == SectionPos.m_123171_(blockpos.m_123343_()) || y == SectionPos.m_123171_(blockpos.m_123342_());
      }

      void m_339503_(CompiledSection sectionIn) {
         this.f_290312_.set(sectionIn);
         this.f_291503_.set(0);
         SectionRenderDispatcher.this.f_290611_.m_294499_(this);
      }

      VertexSorting m_338425_() {
         Vec3 vec3 = SectionRenderDispatcher.this.m_293014_();
         return VertexSorting.m_277071_((float)this.regionDX + (float)(vec3.f_82479_ - (double)this.f_291850_.m_123341_()), (float)this.regionDY + (float)(vec3.f_82480_ - (double)this.f_291850_.m_123342_()), (float)this.regionDZ + (float)(vec3.f_82481_ - (double)this.f_291850_.m_123343_()));
      }

      private boolean isWorldPlayerUpdate() {
         if (SectionRenderDispatcher.this.f_291400_ instanceof ClientLevel) {
            ClientLevel worldClient = SectionRenderDispatcher.this.f_291400_;
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

      private RenderType fixBlockLayer(BlockGetter worldReader, BlockState blockState, BlockPos blockPos, RenderType layer) {
         if (CustomBlockLayers.isActive()) {
            RenderType layerCustom = CustomBlockLayers.getRenderLayer(worldReader, blockState, blockPos);
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

      public RenderSection getRenderChunkOffset16(ViewArea viewFrustum, Direction facing) {
         if (!this.renderChunksOffset16Updated) {
            for(int i = 0; i < Direction.f_122346_.length; ++i) {
               Direction ef = Direction.f_122346_[i];
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

      public void setRenderChunkNeighbour(Direction facing, RenderSection neighbour) {
         this.renderChunkNeighbours[facing.ordinal()] = neighbour;
         this.renderChunkNeighboursValid[facing.ordinal()] = neighbour;
      }

      public RenderSection getRenderChunkNeighbour(Direction facing) {
         if (!this.renderChunkNeighboursUpated) {
            this.updateRenderChunkNeighboursValid();
         }

         return this.renderChunkNeighboursValid[facing.ordinal()];
      }

      public SectionOcclusionGraph.Node getRenderInfo() {
         return this.renderInfo;
      }

      public SectionOcclusionGraph.Node getRenderInfo(Direction dirIn, int counterIn) {
         this.renderInfo.initialize(dirIn, counterIn);
         return this.renderInfo;
      }

      private void updateRenderChunkNeighboursValid() {
         int x = this.m_295500_().m_123341_();
         int z = this.m_295500_().m_123343_();
         int north = Direction.NORTH.ordinal();
         int south = Direction.SOUTH.ordinal();
         int west = Direction.WEST.ordinal();
         int east = Direction.EAST.ordinal();
         this.renderChunkNeighboursValid[north] = this.renderChunkNeighbours[north].m_295500_().m_123343_() == z - 16 ? this.renderChunkNeighbours[north] : null;
         this.renderChunkNeighboursValid[south] = this.renderChunkNeighbours[south].m_295500_().m_123343_() == z + 16 ? this.renderChunkNeighbours[south] : null;
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

      public ClientLevel getWorld() {
         return SectionRenderDispatcher.this.f_291400_;
      }

      public SectionPos getSectionPosition() {
         return this.sectionPosition;
      }

      public String toString() {
         return "pos: " + String.valueOf(this.m_295500_());
      }

      class ResortTransparencyTask extends CompileTask {
         private final CompiledSection f_291899_;

         public ResortTransparencyTask(final double distanceSqIn, final CompiledSection compiledChunkIn) {
            this((ChunkPos)null, distanceSqIn, compiledChunkIn);
         }

         public ResortTransparencyTask(ChunkPos pos, double distanceSqIn, CompiledSection compiledChunkIn) {
            super(RenderSection.this, pos, distanceSqIn, true);
            this.f_291899_ = compiledChunkIn;
         }

         protected String m_294775_() {
            return "rend_chk_sort";
         }

         public CompletableFuture m_294443_(SectionBufferBuilderPack builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (!RenderSection.this.m_294718_()) {
               this.f_291175_.set(true);
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else {
               MeshData.SortState meshdata$sortstate = this.f_291899_.f_291674_;
               if (meshdata$sortstate != null && !this.f_291899_.m_294492_(RenderType.m_110466_())) {
                  VertexSorting vertexsorting = RenderSection.this.m_338425_();
                  ByteBufferBuilder.Result bytebufferbuilder$result = meshdata$sortstate.m_340180_(builderIn.m_339320_(RenderType.m_110466_()), vertexsorting);
                  if (bytebufferbuilder$result == null) {
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else if (this.f_291175_.get()) {
                     bytebufferbuilder$result.close();
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else {
                     CompletableFuture completablefuture = SectionRenderDispatcher.this.m_339467_(bytebufferbuilder$result, RenderSection.this.m_294581_(RenderType.m_110466_())).thenApply((voidIn) -> {
                        return SectionRenderDispatcher.SectionTaskResult.CANCELLED;
                     });
                     return completablefuture.handle((taskResultIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(throwableIn, "Rendering section"));
                        }

                        return this.f_291175_.get() ? SectionRenderDispatcher.SectionTaskResult.CANCELLED : SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
               }
            }
         }

         public void m_292880_() {
            this.f_291175_.set(true);
         }
      }

      abstract class CompileTask implements Comparable {
         protected final double f_290350_;
         protected final AtomicBoolean f_291175_;
         protected final boolean f_290632_;
         protected Map modelData;

         public CompileTask(final RenderSection this$1, final double distanceSqIn, final boolean highPriorityIn) {
            this(this$1, (ChunkPos)null, distanceSqIn, highPriorityIn);
         }

         public CompileTask(final RenderSection this$1, ChunkPos pos, double distanceSqIn, boolean highPriorityIn) {
            this.f_291175_ = new AtomicBoolean(false);
            this.f_290350_ = distanceSqIn;
            this.f_290632_ = highPriorityIn;
            if (pos == null) {
               this.modelData = Collections.emptyMap();
            } else {
               this.modelData = Minecraft.m_91087_().f_91073_.getModelDataManager().getAt((ChunkPos)pos);
            }

         }

         public abstract CompletableFuture m_294443_(SectionBufferBuilderPack var1);

         public abstract void m_292880_();

         protected abstract String m_294775_();

         public int compareTo(CompileTask p_compareTo_1_) {
            return Doubles.compare(this.f_290350_, p_compareTo_1_.f_290350_);
         }

         public ModelData getModelData(BlockPos pos) {
            return (ModelData)this.modelData.getOrDefault(pos, ModelData.EMPTY);
         }
      }

      class RebuildTask extends CompileTask {
         @Nullable
         protected RenderChunkRegion f_290484_;

         public RebuildTask(@Nullable final double distanceSqIn, final RenderChunkRegion renderCacheIn, final boolean highPriorityIn) {
            this((ChunkPos)null, distanceSqIn, renderCacheIn, highPriorityIn);
         }

         public RebuildTask(ChunkPos pos, @Nullable double distanceSqIn, RenderChunkRegion renderCacheIn, boolean highPriorityIn) {
            super(RenderSection.this, pos, distanceSqIn, highPriorityIn);
            this.f_290484_ = renderCacheIn;
         }

         protected String m_294775_() {
            return "rend_chk_rebuild";
         }

         public CompletableFuture m_294443_(SectionBufferBuilderPack builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (!RenderSection.this.m_294718_()) {
               this.m_292880_();
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
            } else {
               RenderChunkRegion renderchunkregion = this.f_290484_;
               this.f_290484_ = null;
               if (renderchunkregion == null) {
                  RenderSection.this.m_339503_(SectionRenderDispatcher.CompiledSection.f_336992_);
                  return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL);
               } else {
                  SectionPos sectionpos = SectionPos.m_123199_(RenderSection.this.f_291850_);
                  SectionCompiler.Results sectioncompiler$results = SectionRenderDispatcher.this.f_337370_.compile(sectionpos, renderchunkregion, RenderSection.this.m_338425_(), builderIn, RenderSection.this.regionDX, RenderSection.this.regionDY, RenderSection.this.regionDZ);
                  RenderSection.this.m_295492_(sectioncompiler$results.f_337223_);
                  if (this.f_291175_.get()) {
                     sectioncompiler$results.m_340536_();
                     return CompletableFuture.completedFuture(SectionRenderDispatcher.SectionTaskResult.CANCELLED);
                  } else {
                     CompiledSection sectionrenderdispatcher$compiledsection = new CompiledSection();
                     sectionrenderdispatcher$compiledsection.f_290920_ = sectioncompiler$results.f_337613_;
                     sectionrenderdispatcher$compiledsection.f_290409_.addAll(sectioncompiler$results.f_337248_);
                     sectionrenderdispatcher$compiledsection.f_291674_ = sectioncompiler$results.f_337382_;
                     sectionrenderdispatcher$compiledsection.setAnimatedSprites(sectioncompiler$results.animatedSprites);
                     List list = new ArrayList(sectioncompiler$results.f_336965_.size());
                     sectioncompiler$results.f_336965_.forEach((renderTypeIn, bufferIn) -> {
                        list.add(SectionRenderDispatcher.this.m_292947_(bufferIn, RenderSection.this.m_294581_(renderTypeIn)));
                        sectionrenderdispatcher$compiledsection.f_290391_.add(renderTypeIn);
                     });
                     return Util.m_143840_(list).handle((voidIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           Minecraft.m_91087_().m_231412_(CrashReport.m_127521_(throwableIn, "Rendering section"));
                        }

                        if (this.f_291175_.get()) {
                           return SectionRenderDispatcher.SectionTaskResult.CANCELLED;
                        } else {
                           RenderSection.this.m_339503_(sectionrenderdispatcher$compiledsection);
                           return SectionRenderDispatcher.SectionTaskResult.SUCCESSFUL;
                        }
                     });
                  }
               }
            }
         }

         public void m_292880_() {
            this.f_290484_ = null;
            if (this.f_291175_.compareAndSet(false, true)) {
               RenderSection.this.m_292780_(false);
            }

         }
      }
   }

   static enum SectionTaskResult {
      SUCCESSFUL,
      CANCELLED;

      // $FF: synthetic method
      private static SectionTaskResult[] $values() {
         return new SectionTaskResult[]{SUCCESSFUL, CANCELLED};
      }
   }

   public static class CompiledSection {
      public static final CompiledSection f_290410_ = new CompiledSection() {
         public boolean m_293115_(Direction facing, Direction facing2) {
            return false;
         }

         public void setAnimatedSprites(RenderType layer, BitSet animatedSprites) {
            throw new UnsupportedOperationException();
         }
      };
      public static final CompiledSection f_336992_ = new CompiledSection() {
         public boolean m_293115_(Direction facing, Direction facing2) {
            return true;
         }
      };
      final Set f_290391_ = new ChunkLayerSet();
      final List f_290409_ = Lists.newArrayList();
      VisibilitySet f_290920_ = new VisibilitySet();
      @Nullable
      MeshData.SortState f_291674_;
      private BitSet[] animatedSprites;

      public CompiledSection() {
         this.animatedSprites = new BitSet[RenderType.CHUNK_RENDER_TYPES.length];
      }

      public boolean m_295467_() {
         return this.f_290391_.isEmpty();
      }

      public boolean m_294492_(RenderType renderTypeIn) {
         return !this.f_290391_.contains(renderTypeIn);
      }

      public List m_293674_() {
         return this.f_290409_;
      }

      public boolean m_293115_(Direction facing, Direction facing2) {
         return this.f_290920_.m_112983_(facing, facing2);
      }

      public BitSet getAnimatedSprites(RenderType layer) {
         return this.animatedSprites[layer.ordinal()];
      }

      public void setAnimatedSprites(BitSet[] animatedSprites) {
         this.animatedSprites = animatedSprites;
      }

      public boolean isLayerUsed(RenderType renderTypeIn) {
         return this.f_290391_.contains(renderTypeIn);
      }

      public void setLayerUsed(RenderType renderTypeIn) {
         this.f_290391_.add(renderTypeIn);
      }

      public boolean hasTerrainBlockEntities() {
         return !this.m_295467_() || !this.m_293674_().isEmpty();
      }

      public Set getLayersUsed() {
         return this.f_290391_;
      }
   }
}
