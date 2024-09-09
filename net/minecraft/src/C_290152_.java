package net.minecraft.src;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.primitives.Doubles;
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

public class C_290152_ {
   private static final int f_291537_ = 2;
   private final PriorityBlockingQueue f_290449_ = Queues.newPriorityBlockingQueue();
   private final Queue f_291696_ = Queues.newLinkedBlockingDeque();
   private int f_291840_ = 2;
   private final Queue f_290841_ = Queues.newConcurrentLinkedQueue();
   final C_290184_ f_290794_;
   private final C_302100_ f_302374_;
   private volatile int f_290603_;
   private volatile boolean f_302977_;
   private final C_452_ f_290713_;
   private final Executor f_291206_;
   C_3899_ f_291400_;
   final C_4134_ f_290611_;
   private C_3046_ f_290602_;
   final C_336511_ f_337370_;
   private int countRenderBuilders;
   private List listPausedBuilders;
   public static final C_4168_[] BLOCK_RENDER_LAYERS = (C_4168_[])C_4168_.m_110506_().toArray(new C_4168_[0]);
   public static final boolean FORGE;
   public static int renderChunksUpdated;

   public C_290152_(C_3899_ worldIn, C_4134_ worldRendererIn, Executor executorIn, C_4148_ fixedBuffersIn, C_4183_ blockRendererIn, C_4243_ blockEntityRendererIn) {
      this.f_290602_ = C_3046_.f_82478_;
      this.listPausedBuilders = new ArrayList();
      this.f_291400_ = worldIn;
      this.f_290611_ = worldRendererIn;
      this.f_290794_ = fixedBuffersIn.m_110098_();
      this.f_302374_ = fixedBuffersIn.m_307906_();
      this.countRenderBuilders = this.f_302374_.m_306121_();
      this.f_291206_ = executorIn;
      this.f_290713_ = C_452_.m_18751_(executorIn, "Section Renderer");
      this.f_290713_.m_6937_(this::m_293371_);
      this.f_337370_ = new C_336511_(blockRendererIn, blockEntityRendererIn);
      this.f_337370_.sectionRenderDispatcher = this;
   }

   public void m_293166_(C_3899_ worldIn) {
      this.f_291400_ = worldIn;
   }

   private void m_293371_() {
      if (!this.f_302977_ && !this.f_302374_.m_307681_()) {
         C_290138_.C_290109_ sectionrenderdispatcher$rendersection$compiletask = this.m_293164_();
         if (sectionrenderdispatcher$rendersection$compiletask != null) {
            C_290184_ sectionbufferbuilderpack = (C_290184_)Objects.requireNonNull(this.f_302374_.m_307873_());
            if (sectionbufferbuilderpack == null) {
               this.f_290449_.add(sectionrenderdispatcher$rendersection$compiletask);
               return;
            }

            this.f_290603_ = this.f_290449_.size() + this.f_291696_.size();
            CompletableFuture.supplyAsync(C_5322_.m_183946_(sectionrenderdispatcher$rendersection$compiletask.m_294775_(), () -> {
               return sectionrenderdispatcher$rendersection$compiletask.m_294443_(sectionbufferbuilderpack);
            }), this.f_291206_).thenCompose((resultIn) -> {
               return resultIn;
            }).whenComplete((taskResultIn, throwableIn) -> {
               if (throwableIn != null) {
                  C_3391_.m_91087_().m_231412_(C_4883_.m_127521_(throwableIn, "Batching sections"));
               } else {
                  this.f_290713_.m_6937_(() -> {
                     if (taskResultIn == C_290152_.C_290089_.SUCCESSFUL) {
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
   private C_290138_.C_290109_ m_293164_() {
      C_290138_.C_290109_ sectionrenderdispatcher$rendersection$compiletask1;
      if (this.f_291840_ <= 0) {
         sectionrenderdispatcher$rendersection$compiletask1 = (C_290138_.C_290109_)this.f_291696_.poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            this.f_291840_ = 2;
            return sectionrenderdispatcher$rendersection$compiletask1;
         }
      }

      sectionrenderdispatcher$rendersection$compiletask1 = (C_290138_.C_290109_)this.f_290449_.poll();
      if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
         --this.f_291840_;
         return sectionrenderdispatcher$rendersection$compiletask1;
      } else {
         this.f_291840_ = 2;
         return (C_290138_.C_290109_)this.f_291696_.poll();
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

   public void m_294870_(C_3046_ posIn) {
      this.f_290602_ = posIn;
   }

   public C_3046_ m_293014_() {
      return this.f_290602_;
   }

   public void m_295287_() {
      Runnable runnable;
      while((runnable = (Runnable)this.f_290841_.poll()) != null) {
         runnable.run();
      }

   }

   public void m_295202_(C_290138_ chunkRenderIn, C_200009_ regionCacheIn) {
      chunkRenderIn.m_295370_(regionCacheIn);
   }

   public void m_295714_() {
      this.m_295487_();
   }

   public void m_294204_(C_290138_.C_290109_ renderTaskIn) {
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

   public CompletableFuture m_292947_(C_336471_ bufferIn, C_3186_ vertexBufferIn) {
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
               C_3186_.m_85931_();
            }

         };
         Queue var10001 = this.f_290841_;
         Objects.requireNonNull(var10001);
         var10000 = CompletableFuture.runAsync(var3, var10001::add);
      }

      return var10000;
   }

   public CompletableFuture m_339467_(C_336589_.C_336543_ resultIn, C_3186_ bufferIn) {
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
               C_3186_.m_85931_();
            }

         };
         Queue var10001 = this.f_290841_;
         Objects.requireNonNull(var10001);
         var10000 = CompletableFuture.runAsync(var3, var10001::add);
      }

      return var10000;
   }

   private void m_295487_() {
      C_290138_.C_290109_ sectionrenderdispatcher$rendersection$compiletask1;
      while(!this.f_290449_.isEmpty()) {
         sectionrenderdispatcher$rendersection$compiletask1 = (C_290138_.C_290109_)this.f_290449_.poll();
         if (sectionrenderdispatcher$rendersection$compiletask1 != null) {
            sectionrenderdispatcher$rendersection$compiletask1.m_292880_();
         }
      }

      while(!this.f_291696_.isEmpty()) {
         sectionrenderdispatcher$rendersection$compiletask1 = (C_290138_.C_290109_)this.f_291696_.poll();
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
            C_290184_ builder = this.f_302374_.m_307873_();
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
         C_290184_ builder = (C_290184_)var1.next();
         this.f_302374_.m_306477_(builder);
      }

      this.listPausedBuilders.clear();
   }

   public boolean updateChunkNow(C_290138_ renderChunk, C_200009_ regionCacheIn) {
      this.m_295202_(renderChunk, regionCacheIn);
      return true;
   }

   public boolean updateChunkLater(C_290138_ renderChunk, C_200009_ regionCacheIn) {
      if (this.f_302374_.m_307681_()) {
         return false;
      } else {
         renderChunk.m_294845_(this, regionCacheIn);
         return true;
      }
   }

   public boolean updateTransparencyLater(C_290138_ renderChunk) {
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

   public class C_290138_ {
      public static final int f_291071_ = 16;
      public final int f_290488_;
      public final AtomicReference f_290312_;
      private final AtomicInteger f_291503_;
      @Nullable
      private C_290046_ f_291315_;
      @Nullable
      private C_290157_ f_291330_;
      private final Set f_291787_;
      private final ChunkLayerMap f_291754_;
      private C_3040_ f_290371_;
      private boolean f_291619_;
      final C_4675_.C_4681_ f_291850_;
      private final C_4675_.C_4681_[] f_291827_;
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
      private final C_290138_[] renderChunksOfset16;
      private boolean renderChunksOffset16Updated;
      private C_2137_ chunk;
      private C_290138_[] renderChunkNeighbours;
      private C_290138_[] renderChunkNeighboursValid;
      private boolean renderChunkNeighboursUpated;
      private C_290263_.C_290165_ renderInfo;
      public AabbFrame boundingBoxParent;
      private C_4710_ sectionPosition;

      public C_290138_(final int indexIn, final int x, final int y, final int z) {
         this.f_290312_ = new AtomicReference(C_290152_.C_290185_.f_290410_);
         this.f_291503_ = new AtomicInteger(0);
         this.f_291787_ = Sets.newHashSet();
         this.f_291754_ = new ChunkLayerMap((renderType) -> {
            return new C_3186_(C_3186_.C_285533_.STATIC);
         });
         this.f_291619_ = true;
         this.f_291850_ = new C_4675_.C_4681_(-1, -1, -1);
         this.f_291827_ = (C_4675_.C_4681_[])C_5322_.m_137469_(new C_4675_.C_4681_[6], (posArrIn) -> {
            for(int i = 0; i < posArrIn.length; ++i) {
               posArrIn[i] = new C_4675_.C_4681_();
            }

         });
         this.isMipmaps = Config.isMipmaps();
         this.playerUpdate = false;
         this.renderRegions = Config.isRenderRegions();
         this.renderChunksOfset16 = new C_290138_[6];
         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighbours = new C_290138_[C_4687_.f_122346_.length];
         this.renderChunkNeighboursValid = new C_290138_[C_4687_.f_122346_.length];
         this.renderChunkNeighboursUpated = false;
         this.renderInfo = new C_290263_.C_290165_(this, (C_4687_)null, 0);
         this.f_290488_ = indexIn;
         this.m_292814_(x, y, z);
      }

      private boolean m_294104_(C_4675_ blockPosIn) {
         return C_290152_.this.f_291400_.m_6522_(C_4710_.m_123171_(blockPosIn.u()), C_4710_.m_123171_(blockPosIn.w()), C_313554_.f_315432_, false) != null;
      }

      public boolean m_294718_() {
         int i = true;
         return !(this.m_293828_() > 576.0) ? true : this.m_294104_(this.f_291850_);
      }

      public C_3040_ m_293301_() {
         return this.f_290371_;
      }

      public C_3186_ m_294581_(C_4168_ renderTypeIn) {
         return (C_3186_)this.f_291754_.get(renderTypeIn);
      }

      public void m_292814_(int x, int y, int z) {
         this.m_293096_();
         this.f_291850_.m_122178_(x, y, z);
         this.sectionPosition = C_4710_.m_123199_(this.f_291850_);
         int i;
         if (this.renderRegions) {
            i = 8;
            this.regionX = x >> i << i;
            this.regionZ = z >> i << i;
            this.regionDX = x - this.regionX;
            this.regionDY = y;
            this.regionDZ = z - this.regionZ;
         }

         this.f_290371_ = new C_3040_((double)x, (double)y, (double)z, (double)(x + 16), (double)(y + 16), (double)(z + 16));
         C_4687_[] var8 = C_4687_.f_122346_;
         int var5 = var8.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            C_4687_ direction = var8[var6];
            this.f_291827_[direction.ordinal()].m_122190_(this.f_291850_).m_122175_(direction, 16);
         }

         this.renderChunksOffset16Updated = false;
         this.renderChunkNeighboursUpated = false;

         for(i = 0; i < this.renderChunkNeighbours.length; ++i) {
            C_290138_ neighbour = this.renderChunkNeighbours[i];
            if (neighbour != null) {
               neighbour.renderChunkNeighboursUpated = false;
            }
         }

         this.chunk = null;
         this.boundingBoxParent = null;
      }

      protected double m_293828_() {
         C_3373_ camera = C_3391_.m_91087_().f_91063_.m_109153_();
         double d0 = this.f_290371_.f_82288_ + 8.0 - camera.m_90583_().f_82479_;
         double d1 = this.f_290371_.f_82289_ + 8.0 - camera.m_90583_().f_82480_;
         double d2 = this.f_290371_.f_82290_ + 8.0 - camera.m_90583_().f_82481_;
         return d0 * d0 + d1 * d1 + d2 * d2;
      }

      public C_290185_ m_293175_() {
         return (C_290185_)this.f_290312_.get();
      }

      private void m_293096_() {
         this.m_294642_();
         this.f_290312_.set(C_290152_.C_290185_.f_290410_);
         this.f_291619_ = true;
      }

      public void m_294345_() {
         this.m_293096_();
         this.f_291754_.values().forEach(C_3186_::close);
      }

      public C_4675_ m_295500_() {
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
            C_290152_.this.f_290611_.onChunkRenderNeedsUpdate(this);
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

      public C_4675_ m_292593_(C_4687_ facing) {
         return this.f_291827_[facing.ordinal()];
      }

      public boolean m_294021_(C_4168_ renderTypeIn, C_290152_ renderDispatcherIn) {
         C_290185_ sectionrenderdispatcher$compiledsection = this.m_293175_();
         if (this.f_291330_ != null) {
            this.f_291330_.m_292880_();
         }

         if (!sectionrenderdispatcher$compiledsection.f_290391_.contains(renderTypeIn)) {
            return false;
         } else {
            if (C_290152_.FORGE) {
               this.f_291330_ = new C_290157_(new C_1560_(this.m_295500_()), this.m_293828_(), sectionrenderdispatcher$compiledsection);
            } else {
               this.f_291330_ = new C_290157_(this.m_293828_(), sectionrenderdispatcher$compiledsection);
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

      public C_290109_ m_295128_(C_200009_ regionCacheIn) {
         boolean flag = this.m_294642_();
         C_4269_ renderchunkregion = regionCacheIn.m_200465_(C_290152_.this.f_291400_, C_4710_.m_123199_(this.f_291850_));
         boolean flag1 = this.f_290312_.get() == C_290152_.C_290185_.f_290410_;
         if (flag1 && flag) {
            this.f_291503_.incrementAndGet();
         }

         C_1560_ forgeChunkPos = C_290152_.FORGE ? new C_1560_(this.m_295500_()) : null;
         this.f_291315_ = new C_290046_(forgeChunkPos, this.m_293828_(), renderchunkregion, !flag1 || this.f_291503_.get() > 2);
         return this.f_291315_;
      }

      public void m_294845_(C_290152_ dispatcherIn, C_200009_ regionCacheIn) {
         C_290109_ sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(regionCacheIn);
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

         C_290152_.this.f_290611_.m_109762_(set1, set);
      }

      public void m_295370_(C_200009_ regionCacheIn) {
         C_290109_ sectionrenderdispatcher$rendersection$compiletask = this.m_295128_(regionCacheIn);
         sectionrenderdispatcher$rendersection$compiletask.m_294443_(C_290152_.this.f_290794_);
      }

      public boolean m_292850_(int x, int y, int z) {
         C_4675_ blockpos = this.m_295500_();
         return x == C_4710_.m_123171_(blockpos.u()) || z == C_4710_.m_123171_(blockpos.w()) || y == C_4710_.m_123171_(blockpos.v());
      }

      void m_339503_(C_290185_ sectionIn) {
         this.f_290312_.set(sectionIn);
         this.f_291503_.set(0);
         C_290152_.this.f_290611_.m_294499_(this);
      }

      C_276405_ m_338425_() {
         C_3046_ vec3 = C_290152_.this.m_293014_();
         return C_276405_.m_277071_((float)this.regionDX + (float)(vec3.f_82479_ - (double)this.f_291850_.u()), (float)this.regionDY + (float)(vec3.f_82480_ - (double)this.f_291850_.v()), (float)this.regionDZ + (float)(vec3.f_82481_ - (double)this.f_291850_.w()));
      }

      private boolean isWorldPlayerUpdate() {
         if (C_290152_.this.f_291400_ instanceof C_3899_) {
            C_3899_ worldClient = C_290152_.this.f_291400_;
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

      private C_4168_ fixBlockLayer(C_1559_ worldReader, C_2064_ blockState, C_4675_ blockPos, C_4168_ layer) {
         if (CustomBlockLayers.isActive()) {
            C_4168_ layerCustom = CustomBlockLayers.getRenderLayer(worldReader, blockState, blockPos);
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

      public C_290138_ getRenderChunkOffset16(C_4180_ viewFrustum, C_4687_ facing) {
         if (!this.renderChunksOffset16Updated) {
            for(int i = 0; i < C_4687_.f_122346_.length; ++i) {
               C_4687_ ef = C_4687_.f_122346_[i];
               C_4675_ posOffset16 = this.m_292593_(ef);
               this.renderChunksOfset16[i] = viewFrustum.m_292642_(posOffset16);
            }

            this.renderChunksOffset16Updated = true;
         }

         return this.renderChunksOfset16[facing.ordinal()];
      }

      public C_2137_ getChunk() {
         return this.getChunk(this.f_291850_);
      }

      private C_2137_ getChunk(C_4675_ posIn) {
         C_2137_ chunkLocal = this.chunk;
         if (chunkLocal != null && ChunkUtils.isLoaded(chunkLocal)) {
            return chunkLocal;
         } else {
            chunkLocal = C_290152_.this.f_291400_.m_46745_(posIn);
            this.chunk = chunkLocal;
            return chunkLocal;
         }
      }

      public boolean isChunkRegionEmpty() {
         return this.isChunkRegionEmpty(this.f_291850_);
      }

      private boolean isChunkRegionEmpty(C_4675_ posIn) {
         int yStart = posIn.v();
         int yEnd = yStart + 15;
         return this.getChunk(posIn).a(yStart, yEnd);
      }

      public void setRenderChunkNeighbour(C_4687_ facing, C_290138_ neighbour) {
         this.renderChunkNeighbours[facing.ordinal()] = neighbour;
         this.renderChunkNeighboursValid[facing.ordinal()] = neighbour;
      }

      public C_290138_ getRenderChunkNeighbour(C_4687_ facing) {
         if (!this.renderChunkNeighboursUpated) {
            this.updateRenderChunkNeighboursValid();
         }

         return this.renderChunkNeighboursValid[facing.ordinal()];
      }

      public C_290263_.C_290165_ getRenderInfo() {
         return this.renderInfo;
      }

      public C_290263_.C_290165_ getRenderInfo(C_4687_ dirIn, int counterIn) {
         this.renderInfo.initialize(dirIn, counterIn);
         return this.renderInfo;
      }

      private void updateRenderChunkNeighboursValid() {
         int x = this.m_295500_().u();
         int z = this.m_295500_().w();
         int north = C_4687_.NORTH.ordinal();
         int south = C_4687_.SOUTH.ordinal();
         int west = C_4687_.WEST.ordinal();
         int east = C_4687_.EAST.ordinal();
         this.renderChunkNeighboursValid[north] = this.renderChunkNeighbours[north].m_295500_().w() == z - 16 ? this.renderChunkNeighbours[north] : null;
         this.renderChunkNeighboursValid[south] = this.renderChunkNeighbours[south].m_295500_().w() == z + 16 ? this.renderChunkNeighbours[south] : null;
         this.renderChunkNeighboursValid[west] = this.renderChunkNeighbours[west].m_295500_().u() == x - 16 ? this.renderChunkNeighbours[west] : null;
         this.renderChunkNeighboursValid[east] = this.renderChunkNeighbours[east].m_295500_().u() == x + 16 ? this.renderChunkNeighbours[east] : null;
         this.renderChunkNeighboursUpated = true;
      }

      public boolean isBoundingBoxInFrustum(ICamera camera, int frameCount) {
         return this.getBoundingBoxParent().isBoundingBoxInFrustumFully(camera, frameCount) ? true : camera.isBoundingBoxInFrustum(this.f_290371_);
      }

      public AabbFrame getBoundingBoxParent() {
         if (this.boundingBoxParent == null) {
            C_4675_ pos = this.m_295500_();
            int x = pos.u();
            int y = pos.v();
            int z = pos.w();
            int bits = 5;
            int xp = x >> bits << bits;
            int yp = y >> bits << bits;
            int zp = z >> bits << bits;
            if (xp != x || yp != y || zp != z) {
               AabbFrame bbp = C_290152_.this.f_290611_.getRenderChunk(new C_4675_(xp, yp, zp)).getBoundingBoxParent();
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

      public C_3899_ getWorld() {
         return C_290152_.this.f_291400_;
      }

      public C_4710_ getSectionPosition() {
         return this.sectionPosition;
      }

      public String toString() {
         return "pos: " + String.valueOf(this.m_295500_());
      }

      class C_290157_ extends C_290109_ {
         private final C_290185_ f_291899_;

         public C_290157_(final double distanceSqIn, final C_290185_ compiledChunkIn) {
            this((C_1560_)null, distanceSqIn, compiledChunkIn);
         }

         public C_290157_(C_1560_ pos, double distanceSqIn, C_290185_ compiledChunkIn) {
            super(C_290138_.this, pos, distanceSqIn, true);
            this.f_291899_ = compiledChunkIn;
         }

         protected String m_294775_() {
            return "rend_chk_sort";
         }

         public CompletableFuture m_294443_(C_290184_ builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else if (!C_290138_.this.m_294718_()) {
               this.f_291175_.set(true);
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else {
               C_336471_.C_336506_ meshdata$sortstate = this.f_291899_.f_291674_;
               if (meshdata$sortstate != null && !this.f_291899_.m_294492_(C_4168_.m_110466_())) {
                  C_276405_ vertexsorting = C_290138_.this.m_338425_();
                  C_336589_.C_336543_ bytebufferbuilder$result = meshdata$sortstate.m_340180_(builderIn.m_339320_(C_4168_.m_110466_()), vertexsorting);
                  if (bytebufferbuilder$result == null) {
                     return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
                  } else if (this.f_291175_.get()) {
                     bytebufferbuilder$result.close();
                     return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
                  } else {
                     CompletableFuture completablefuture = C_290152_.this.m_339467_(bytebufferbuilder$result, C_290138_.this.m_294581_(C_4168_.m_110466_())).thenApply((voidIn) -> {
                        return C_290152_.C_290089_.CANCELLED;
                     });
                     return completablefuture.handle((taskResultIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           C_3391_.m_91087_().m_231412_(C_4883_.m_127521_(throwableIn, "Rendering section"));
                        }

                        return this.f_291175_.get() ? C_290152_.C_290089_.CANCELLED : C_290152_.C_290089_.SUCCESSFUL;
                     });
                  }
               } else {
                  return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
               }
            }
         }

         public void m_292880_() {
            this.f_291175_.set(true);
         }
      }

      abstract class C_290109_ implements Comparable {
         protected final double f_290350_;
         protected final AtomicBoolean f_291175_;
         protected final boolean f_290632_;
         protected Map modelData;

         public C_290109_(final C_290138_ this$1, final double distanceSqIn, final boolean highPriorityIn) {
            this(this$1, (C_1560_)null, distanceSqIn, highPriorityIn);
         }

         public C_290109_(final C_290138_ this$1, C_1560_ pos, double distanceSqIn, boolean highPriorityIn) {
            this.f_291175_ = new AtomicBoolean(false);
            this.f_290350_ = distanceSqIn;
            this.f_290632_ = highPriorityIn;
            if (pos == null) {
               this.modelData = Collections.emptyMap();
            } else {
               this.modelData = C_3391_.m_91087_().f_91073_.getModelDataManager().getAt(pos);
            }

         }

         public abstract CompletableFuture m_294443_(C_290184_ var1);

         public abstract void m_292880_();

         protected abstract String m_294775_();

         public int compareTo(C_290109_ p_compareTo_1_) {
            return Doubles.compare(this.f_290350_, p_compareTo_1_.f_290350_);
         }

         public ModelData getModelData(C_4675_ pos) {
            return (ModelData)this.modelData.getOrDefault(pos, ModelData.EMPTY);
         }
      }

      class C_290046_ extends C_290109_ {
         @Nullable
         protected C_4269_ f_290484_;

         public C_290046_(@Nullable final double distanceSqIn, final C_4269_ renderCacheIn, final boolean highPriorityIn) {
            this((C_1560_)null, distanceSqIn, renderCacheIn, highPriorityIn);
         }

         public C_290046_(C_1560_ pos, @Nullable double distanceSqIn, C_4269_ renderCacheIn, boolean highPriorityIn) {
            super(C_290138_.this, pos, distanceSqIn, highPriorityIn);
            this.f_290484_ = renderCacheIn;
         }

         protected String m_294775_() {
            return "rend_chk_rebuild";
         }

         public CompletableFuture m_294443_(C_290184_ builderIn) {
            if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else if (!C_290138_.this.m_294718_()) {
               this.m_292880_();
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else if (this.f_291175_.get()) {
               return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
            } else {
               C_4269_ renderchunkregion = this.f_290484_;
               this.f_290484_ = null;
               if (renderchunkregion == null) {
                  C_290138_.this.m_339503_(C_290152_.C_290185_.f_336992_);
                  return CompletableFuture.completedFuture(C_290152_.C_290089_.SUCCESSFUL);
               } else {
                  C_4710_ sectionpos = C_4710_.m_123199_(C_290138_.this.f_291850_);
                  C_336511_.C_336499_ sectioncompiler$results = C_290152_.this.f_337370_.compile(sectionpos, renderchunkregion, C_290138_.this.m_338425_(), builderIn, C_290138_.this.regionDX, C_290138_.this.regionDY, C_290138_.this.regionDZ);
                  C_290138_.this.m_295492_(sectioncompiler$results.f_337223_);
                  if (this.f_291175_.get()) {
                     sectioncompiler$results.m_340536_();
                     return CompletableFuture.completedFuture(C_290152_.C_290089_.CANCELLED);
                  } else {
                     C_290185_ sectionrenderdispatcher$compiledsection = new C_290185_();
                     sectionrenderdispatcher$compiledsection.f_290920_ = sectioncompiler$results.f_337613_;
                     sectionrenderdispatcher$compiledsection.f_290409_.addAll(sectioncompiler$results.f_337248_);
                     sectionrenderdispatcher$compiledsection.f_291674_ = sectioncompiler$results.f_337382_;
                     sectionrenderdispatcher$compiledsection.setAnimatedSprites(sectioncompiler$results.animatedSprites);
                     List list = new ArrayList(sectioncompiler$results.f_336965_.size());
                     sectioncompiler$results.f_336965_.forEach((renderTypeIn, bufferIn) -> {
                        list.add(C_290152_.this.m_292947_(bufferIn, C_290138_.this.m_294581_(renderTypeIn)));
                        sectionrenderdispatcher$compiledsection.f_290391_.add(renderTypeIn);
                     });
                     return C_5322_.m_143840_(list).handle((voidIn, throwableIn) -> {
                        if (throwableIn != null && !(throwableIn instanceof CancellationException) && !(throwableIn instanceof InterruptedException)) {
                           C_3391_.m_91087_().m_231412_(C_4883_.m_127521_(throwableIn, "Rendering section"));
                        }

                        if (this.f_291175_.get()) {
                           return C_290152_.C_290089_.CANCELLED;
                        } else {
                           C_290138_.this.m_339503_(sectionrenderdispatcher$compiledsection);
                           return C_290152_.C_290089_.SUCCESSFUL;
                        }
                     });
                  }
               }
            }
         }

         public void m_292880_() {
            this.f_290484_ = null;
            if (this.f_291175_.compareAndSet(false, true)) {
               C_290138_.this.m_292780_(false);
            }

         }
      }
   }

   static enum C_290089_ {
      SUCCESSFUL,
      CANCELLED;

      // $FF: synthetic method
      private static C_290089_[] $values() {
         return new C_290089_[]{SUCCESSFUL, CANCELLED};
      }
   }

   public static class C_290185_ {
      public static final C_290185_ f_290410_ = new C_290185_() {
         public boolean m_293115_(C_4687_ facing, C_4687_ facing2) {
            return false;
         }

         public void setAnimatedSprites(C_4168_ layer, BitSet animatedSprites) {
            throw new UnsupportedOperationException();
         }
      };
      public static final C_290185_ f_336992_ = new C_290185_() {
         public boolean m_293115_(C_4687_ facing, C_4687_ facing2) {
            return true;
         }
      };
      final Set f_290391_ = new ChunkLayerSet();
      final List f_290409_ = Lists.newArrayList();
      C_4272_ f_290920_ = new C_4272_();
      @Nullable
      C_336471_.C_336506_ f_291674_;
      private BitSet[] animatedSprites;

      public C_290185_() {
         this.animatedSprites = new BitSet[C_4168_.CHUNK_RENDER_TYPES.length];
      }

      public boolean m_295467_() {
         return this.f_290391_.isEmpty();
      }

      public boolean m_294492_(C_4168_ renderTypeIn) {
         return !this.f_290391_.contains(renderTypeIn);
      }

      public List m_293674_() {
         return this.f_290409_;
      }

      public boolean m_293115_(C_4687_ facing, C_4687_ facing2) {
         return this.f_290920_.m_112983_(facing, facing2);
      }

      public BitSet getAnimatedSprites(C_4168_ layer) {
         return this.animatedSprites[layer.ordinal()];
      }

      public void setAnimatedSprites(BitSet[] animatedSprites) {
         this.animatedSprites = animatedSprites;
      }

      public boolean isLayerUsed(C_4168_ renderTypeIn) {
         return this.f_290391_.contains(renderTypeIn);
      }

      public void setLayerUsed(C_4168_ renderTypeIn) {
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
