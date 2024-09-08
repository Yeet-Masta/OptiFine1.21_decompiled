package net.minecraft.src;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.optifine.BlockPosM;
import net.optifine.Vec3M;
import org.slf4j.Logger;

public class C_290263_ {
   private static final Logger f_290342_ = LogUtils.getLogger();
   private static final C_4687_[] f_291333_ = C_4687_.values();
   private static final int f_291236_ = 60;
   private static final double f_291614_ = Math.ceil(Math.sqrt(3.0) * 16.0);
   private boolean f_290608_ = true;
   @Nullable
   private Future<?> f_291408_;
   @Nullable
   private C_4180_ f_290643_;
   private final AtomicReference<C_290263_.C_290246_> f_291855_ = new AtomicReference();
   private final AtomicReference<C_290263_.C_290172_> f_291476_ = new AtomicReference();
   private final AtomicBoolean f_291462_ = new AtomicBoolean(false);
   private C_4134_ levelRenderer;

   public void m_295341_(@Nullable C_4180_ viewAreaIn) {
      if (this.f_291408_ != null) {
         try {
            this.f_291408_.get();
            this.f_291408_ = null;
         } catch (Exception var3) {
            f_290342_.warn("Full update failed", var3);
         }
      }

      this.f_290643_ = viewAreaIn;
      this.levelRenderer = C_3391_.m_91087_().f_91060_;
      if (viewAreaIn != null) {
         this.f_291855_.set(new C_290263_.C_290246_(viewAreaIn.f_291707_.length));
         this.m_295966_();
      } else {
         this.f_291855_.set(null);
      }
   }

   public void m_295966_() {
      this.f_290608_ = true;
   }

   public void m_295738_(C_4273_ frustumIn, List<C_290152_.C_290138_> sectionsIn) {
      this.addSectionsInFrustum(frustumIn, sectionsIn, true, -1);
   }

   public void addSectionsInFrustum(C_4273_ frustumIn, List<C_290152_.C_290138_> sectionsIn, boolean updateSections, int maxChunkDistance) {
      List<C_290152_.C_290138_> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
      List<C_290152_.C_290138_> renderInfosTileEntities = this.levelRenderer.getRenderInfosTileEntities();
      int cameraChunkX = (int)frustumIn.getCameraX() >> 4 << 4;
      int cameraChunkY = (int)frustumIn.getCameraY() >> 4 << 4;
      int cameraChunkZ = (int)frustumIn.getCameraZ() >> 4 << 4;
      int maxChunkDistSq = maxChunkDistance * maxChunkDistance;

      for (C_290263_.C_290165_ sectionocclusiongraph$node : ((C_290263_.C_290246_)this.f_291855_.get()).f_290555_().f_291495_) {
         if (frustumIn.m_113029_(sectionocclusiongraph$node.f_291755_.m_293301_())) {
            if (maxChunkDistance > 0) {
               C_4675_ posChunk = sectionocclusiongraph$node.f_291755_.m_295500_();
               int dx = cameraChunkX - posChunk.u();
               int dy = cameraChunkY - posChunk.v();
               int dz = cameraChunkZ - posChunk.w();
               int chunkDistSq = dx * dx + dy * dy + dz * dz;
               if (chunkDistSq > maxChunkDistSq) {
                  continue;
               }
            }

            if (updateSections) {
               sectionsIn.add(sectionocclusiongraph$node.f_291755_);
            }

            C_290152_.C_290185_ compiledChunk = sectionocclusiongraph$node.f_291755_.m_293175_();
            if (!compiledChunk.m_295467_()) {
               renderInfosTerrain.add(sectionocclusiongraph$node.f_291755_);
            }

            if (!compiledChunk.m_293674_().isEmpty()) {
               renderInfosTileEntities.add(sectionocclusiongraph$node.f_291755_);
            }
         }
      }
   }

   public boolean m_293178_() {
      return this.f_291462_.compareAndSet(true, false);
   }

   public void m_294751_(C_1560_ chunkPosIn) {
      C_290263_.C_290172_ sectionocclusiongraph$graphevents = (C_290263_.C_290172_)this.f_291476_.get();
      if (sectionocclusiongraph$graphevents != null) {
         this.m_294370_(sectionocclusiongraph$graphevents, chunkPosIn);
      }

      C_290263_.C_290172_ sectionocclusiongraph$graphevents1 = ((C_290263_.C_290246_)this.f_291855_.get()).f_291329_;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         this.m_294370_(sectionocclusiongraph$graphevents1, chunkPosIn);
      }
   }

   public void m_293743_(C_290152_.C_290138_ sectionIn) {
      C_290263_.C_290172_ sectionocclusiongraph$graphevents = (C_290263_.C_290172_)this.f_291476_.get();
      if (sectionocclusiongraph$graphevents != null) {
         sectionocclusiongraph$graphevents.f_290616_.add(sectionIn);
      }

      C_290263_.C_290172_ sectionocclusiongraph$graphevents1 = ((C_290263_.C_290246_)this.f_291855_.get()).f_291329_;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         sectionocclusiongraph$graphevents1.f_290616_.add(sectionIn);
      }

      if (sectionIn.m_293175_().hasTerrainBlockEntities()) {
         this.f_291462_.set(true);
      }
   }

   public void m_292654_(boolean renderManyIn, C_3373_ cameraIn, C_4273_ frustumIn, List<C_290152_.C_290138_> sectionsIn) {
      C_3046_ vec3 = cameraIn.m_90583_();
      if (this.f_290608_ && (this.f_291408_ == null || this.f_291408_.isDone())) {
         this.m_295789_(renderManyIn, cameraIn, vec3);
      }

      this.m_293052_(renderManyIn, frustumIn, sectionsIn, vec3);
   }

   private void m_295789_(boolean renderManyIn, C_3373_ cameraIn, C_3046_ viewPosIn) {
      this.f_290608_ = false;
      this.f_291408_ = C_5322_.m_183991_().submit(() -> {
         C_290263_.C_290246_ sectionocclusiongraph$graphstate = new C_290263_.C_290246_(this.f_290643_.f_291707_.length);
         this.f_291476_.set(sectionocclusiongraph$graphstate.f_291329_);
         Queue<C_290263_.C_290165_> queue = Queues.newArrayDeque();
         this.m_294555_(cameraIn, queue);
         queue.forEach(nodeIn -> sectionocclusiongraph$graphstate.f_290555_.f_291257_.m_294528_(nodeIn.f_291755_, nodeIn));
         this.m_293858_(sectionocclusiongraph$graphstate.f_290555_, viewPosIn, queue, renderManyIn, sectionIn -> {
         });
         this.f_291855_.set(sectionocclusiongraph$graphstate);
         this.f_291476_.set(null);
         this.f_291462_.set(true);
      });
   }

   private void m_293052_(boolean renderManyIn, C_4273_ frustumIn, List<C_290152_.C_290138_> sectionsIn, C_3046_ viewPosIn) {
      C_290263_.C_290246_ sectionocclusiongraph$graphstate = (C_290263_.C_290246_)this.f_291855_.get();
      this.m_294187_(sectionocclusiongraph$graphstate);
      if (!sectionocclusiongraph$graphstate.f_291329_.f_290616_.isEmpty()) {
         Queue<C_290263_.C_290165_> queue = Queues.newArrayDeque();

         while (!sectionocclusiongraph$graphstate.f_291329_.f_290616_.isEmpty()) {
            C_290152_.C_290138_ sectionrenderdispatcher$rendersection = (C_290152_.C_290138_)sectionocclusiongraph$graphstate.f_291329_.f_290616_.poll();
            C_290263_.C_290165_ sectionocclusiongraph$node = sectionocclusiongraph$graphstate.f_290555_
               .f_291257_
               .m_295569_(sectionrenderdispatcher$rendersection);
            if (sectionocclusiongraph$node != null && sectionocclusiongraph$node.f_291755_ == sectionrenderdispatcher$rendersection) {
               queue.add(sectionocclusiongraph$node);
            }
         }

         List<C_290152_.C_290138_> renderInfos = this.levelRenderer.getRenderInfos();
         List<C_290152_.C_290138_> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
         List<C_290152_.C_290138_> renderInfosTileEntities = this.levelRenderer.getRenderInfosTileEntities();
         C_4273_ frustum = C_4134_.m_295345_(frustumIn);
         Consumer<C_290152_.C_290138_> consumer = sectionIn -> {
            if (frustum.m_113029_(sectionIn.m_293301_())) {
               sectionsIn.add(sectionIn);
               if (sectionIn == renderInfos) {
                  C_290152_.C_290185_ compiledChunk = (C_290152_.C_290185_)sectionIn.f_290312_.get();
                  if (!compiledChunk.m_295467_()) {
                     renderInfosTerrain.add(sectionIn);
                  }

                  if (!compiledChunk.m_293674_().isEmpty()) {
                     renderInfosTileEntities.add(sectionIn);
                  }
               }
            }
         };
         this.m_293858_(sectionocclusiongraph$graphstate.f_290555_, viewPosIn, queue, renderManyIn, consumer);
      }
   }

   private void m_294187_(C_290263_.C_290246_ stateIn) {
      LongIterator longiterator = stateIn.f_291329_.f_291517_.iterator();

      while (longiterator.hasNext()) {
         long i = longiterator.nextLong();
         List<C_290152_.C_290138_> list = (List<C_290152_.C_290138_>)stateIn.f_290555_.f_290746_.get(i);
         if (list != null && ((C_290152_.C_290138_)list.get(0)).m_294718_()) {
            stateIn.f_291329_.f_290616_.addAll(list);
            stateIn.f_290555_.f_290746_.remove(i);
         }
      }

      stateIn.f_291329_.f_291517_.clear();
   }

   private void m_294370_(C_290263_.C_290172_ eventsIn, C_1560_ chunkPosIn) {
      eventsIn.f_291517_.add(C_1560_.m_45589_(chunkPosIn.f_45578_ - 1, chunkPosIn.f_45579_));
      eventsIn.f_291517_.add(C_1560_.m_45589_(chunkPosIn.f_45578_, chunkPosIn.f_45579_ - 1));
      eventsIn.f_291517_.add(C_1560_.m_45589_(chunkPosIn.f_45578_ + 1, chunkPosIn.f_45579_));
      eventsIn.f_291517_.add(C_1560_.m_45589_(chunkPosIn.f_45578_, chunkPosIn.f_45579_ + 1));
   }

   private void m_294555_(C_3373_ cameraIn, Queue<C_290263_.C_290165_> nodesIn) {
      int i = 16;
      C_3046_ vec3 = cameraIn.m_90583_();
      C_4675_ blockpos = cameraIn.m_90588_();
      C_290152_.C_290138_ sectionrenderdispatcher$rendersection = this.f_290643_.m_292642_(blockpos);
      if (sectionrenderdispatcher$rendersection == null) {
         C_141183_ levelheightaccessor = this.f_290643_.m_294982_();
         boolean flag = blockpos.v() > levelheightaccessor.m_141937_();
         int j = flag ? levelheightaccessor.m_151558_() - 8 : levelheightaccessor.m_141937_() + 8;
         int k = C_188_.m_14107_(vec3.f_82479_ / 16.0) * 16;
         int l = C_188_.m_14107_(vec3.f_82481_ / 16.0) * 16;
         int i1 = this.f_290643_.m_295654_();
         List<C_290263_.C_290165_> list = Lists.newArrayList();

         for (int j1 = -i1; j1 <= i1; j1++) {
            for (int k1 = -i1; k1 <= i1; k1++) {
               C_290152_.C_290138_ sectionrenderdispatcher$rendersection1 = this.f_290643_
                  .m_292642_(new C_4675_(k + C_4710_.m_175554_(j1, 8), j, l + C_4710_.m_175554_(k1, 8)));
               if (sectionrenderdispatcher$rendersection1 != null && this.m_294269_(blockpos, sectionrenderdispatcher$rendersection1.m_295500_())) {
                  C_4687_ direction = flag ? C_4687_.DOWN : C_4687_.UP;
                  C_290263_.C_290165_ sectionocclusiongraph$node = sectionrenderdispatcher$rendersection1.getRenderInfo(direction, 0);
                  sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, direction);
                  if (j1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, C_4687_.EAST);
                  } else if (j1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, C_4687_.WEST);
                  }

                  if (k1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, C_4687_.SOUTH);
                  } else if (k1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, C_4687_.NORTH);
                  }

                  list.add(sectionocclusiongraph$node);
               }
            }
         }

         list.sort(Comparator.comparingDouble(nodeIn -> blockpos.j(nodeIn.f_291755_.m_295500_().m_7918_(8, 8, 8))));
         nodesIn.addAll(list);
      } else {
         nodesIn.add(sectionrenderdispatcher$rendersection.getRenderInfo(null, 0));
      }
   }

   private void m_293858_(
      C_290263_.C_290171_ storageIn, C_3046_ viewPosIn, Queue<C_290263_.C_290165_> nodesIn, boolean renderManyIn, Consumer<C_290152_.C_290138_> consumerIn
   ) {
      int i = 16;
      C_4675_ blockpos = new C_4675_(
         C_188_.m_14107_(viewPosIn.f_82479_ / 16.0) * 16, C_188_.m_14107_(viewPosIn.f_82480_ / 16.0) * 16, C_188_.m_14107_(viewPosIn.f_82481_ / 16.0) * 16
      );
      C_4675_ blockpos1 = blockpos.m_7918_(8, 8, 8);

      while (!nodesIn.isEmpty()) {
         C_290263_.C_290165_ sectionocclusiongraph$node = (C_290263_.C_290165_)nodesIn.poll();
         C_290152_.C_290138_ sectionrenderdispatcher$rendersection = sectionocclusiongraph$node.f_291755_;
         if (storageIn.f_291495_.add(sectionocclusiongraph$node)) {
            consumerIn.accept(sectionocclusiongraph$node.f_291755_);
         }

         boolean flag = Math.abs(sectionrenderdispatcher$rendersection.m_295500_().u() - blockpos.u()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.m_295500_().v() - blockpos.v()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.m_295500_().w() - blockpos.w()) > 60;

         for (C_4687_ direction : f_291333_) {
            C_290152_.C_290138_ sectionrenderdispatcher$rendersection1 = this.m_295926_(blockpos, sectionrenderdispatcher$rendersection, direction);
            if (sectionrenderdispatcher$rendersection1 != null && (!renderManyIn || !sectionocclusiongraph$node.m_294495_(direction.m_122424_()))) {
               if (renderManyIn && sectionocclusiongraph$node.m_292787_()) {
                  C_290152_.C_290185_ sectionrenderdispatcher$compiledsection = sectionrenderdispatcher$rendersection.m_293175_();
                  boolean flag1 = false;

                  for (int j = 0; j < f_291333_.length; j++) {
                     if (sectionocclusiongraph$node.m_295060_(j) && sectionrenderdispatcher$compiledsection.m_293115_(f_291333_[j].m_122424_(), direction)) {
                        flag1 = true;
                        break;
                     }
                  }

                  if (!flag1) {
                     continue;
                  }
               }

               if (renderManyIn && flag) {
                  C_4675_ blockpos2 = sectionrenderdispatcher$rendersection1.m_295500_();
                  C_4675_ blockpos3 = blockpos2.m_7918_(
                     (direction.m_122434_() == C_4687_.C_4689_.X ? blockpos1.u() > blockpos2.u() : blockpos1.u() < blockpos2.u()) ? 16 : 0,
                     (direction.m_122434_() == C_4687_.C_4689_.Y ? blockpos1.v() > blockpos2.v() : blockpos1.v() < blockpos2.v()) ? 16 : 0,
                     (direction.m_122434_() == C_4687_.C_4689_.Z ? blockpos1.w() > blockpos2.w() : blockpos1.w() < blockpos2.w()) ? 16 : 0
                  );
                  C_3046_ vec31 = new C_3046_((double)blockpos3.u(), (double)blockpos3.v(), (double)blockpos3.w());
                  C_3046_ vec3 = viewPosIn.m_82546_(vec31).m_82541_().m_82490_(f_291614_);
                  boolean flag2 = true;

                  while (viewPosIn.m_82546_(vec31).m_82556_() > 3600.0) {
                     vec31 = vec31.m_82549_(vec3);
                     C_141183_ levelheightaccessor = this.f_290643_.m_294982_();
                     if (vec31.f_82480_ > (double)levelheightaccessor.m_151558_() || vec31.f_82480_ < (double)levelheightaccessor.m_141937_()) {
                        break;
                     }

                     C_290152_.C_290138_ sectionrenderdispatcher$rendersection2 = this.f_290643_
                        .m_292642_(storageIn.blockPosM1.setXyz(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_));
                     if (sectionrenderdispatcher$rendersection2 == null || storageIn.f_291257_.m_295569_(sectionrenderdispatcher$rendersection2) == null) {
                        flag2 = false;
                        break;
                     }
                  }

                  if (!flag2) {
                     continue;
                  }
               }

               C_290263_.C_290165_ sectionocclusiongraph$node1 = storageIn.f_291257_.m_295569_(sectionrenderdispatcher$rendersection1);
               if (sectionocclusiongraph$node1 != null) {
                  sectionocclusiongraph$node1.m_293452_(direction);
               } else {
                  C_290263_.C_290165_ sectionocclusiongraph$node2 = sectionrenderdispatcher$rendersection1.getRenderInfo(
                     direction, sectionocclusiongraph$node.f_291195_ + 1
                  );
                  sectionocclusiongraph$node2.setDirections(sectionocclusiongraph$node.f_290314_, direction);
                  if (sectionrenderdispatcher$rendersection1.m_294718_()) {
                     nodesIn.add(sectionocclusiongraph$node2);
                     storageIn.f_291257_.m_294528_(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                  } else if (this.m_294269_(blockpos, sectionrenderdispatcher$rendersection1.m_295500_())) {
                     storageIn.f_291257_.m_294528_(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                     ((List)storageIn.f_290746_
                           .computeIfAbsent(C_1560_.m_151388_(sectionrenderdispatcher$rendersection1.m_295500_()), posLongIn -> new ArrayList()))
                        .add(sectionrenderdispatcher$rendersection1);
                  }
               }
            }
         }
      }
   }

   private boolean m_294269_(C_4675_ blockPos1, C_4675_ blockPos2) {
      int i = C_4710_.m_123171_(blockPos1.u());
      int j = C_4710_.m_123171_(blockPos1.w());
      int k = C_4710_.m_123171_(blockPos2.u());
      int l = C_4710_.m_123171_(blockPos2.w());
      return C_290036_.m_294571_(i, j, this.f_290643_.m_295654_(), k, l);
   }

   @Nullable
   private C_290152_.C_290138_ m_295926_(C_4675_ blockPosIn, C_290152_.C_290138_ sectionIn, C_4687_ dirIn) {
      C_4675_ blockpos = sectionIn.m_292593_(dirIn);
      C_3899_ world = this.levelRenderer.f_109465_;
      if (blockpos.v() < world.I_() || blockpos.v() >= world.am()) {
         return null;
      } else if (C_188_.m_14040_(blockPosIn.v() - blockpos.v()) > this.levelRenderer.renderDistance) {
         return null;
      } else {
         int dxs = blockPosIn.u() - blockpos.u();
         int dzs = blockPosIn.w() - blockpos.w();
         int distSq = dxs * dxs + dzs * dzs;
         return distSq > this.levelRenderer.renderDistanceXZSq ? null : this.f_290643_.m_292642_(blockpos);
      }
   }

   @Nullable
   @C_140994_
   protected C_290263_.C_290165_ m_292796_(C_290152_.C_290138_ sectionIn) {
      return ((C_290263_.C_290246_)this.f_291855_.get()).f_290555_.f_291257_.m_295569_(sectionIn);
   }

   public boolean needsFrustumUpdate() {
      return this.f_291462_.get();
   }

   public void setNeedsFrustumUpdate(boolean val) {
      this.f_291462_.set(val);
   }

   static class C_290086_ {
      private final C_290263_.C_290165_[] f_291767_;

      C_290086_(int sizeIn) {
         this.f_291767_ = new C_290263_.C_290165_[sizeIn];
      }

      public void m_294528_(C_290152_.C_290138_ sectionIn, C_290263_.C_290165_ nodeIn) {
         this.f_291767_[sectionIn.f_290488_] = nodeIn;
      }

      @Nullable
      public C_290263_.C_290165_ m_295569_(C_290152_.C_290138_ sectionIn) {
         int i = sectionIn.f_290488_;
         return i >= 0 && i < this.f_291767_.length ? this.f_291767_[i] : null;
      }
   }

   @C_140994_
   public static class C_290165_ {
      @C_140994_
      public final C_290152_.C_290138_ f_291755_;
      private int f_291521_;
      int f_290314_;
      @C_140994_
      protected int f_291195_;

      public C_290165_(C_290152_.C_290138_ sectionIn, @Nullable C_4687_ directionIn, int counterIn) {
         this.f_291755_ = sectionIn;
         if (directionIn != null) {
            this.m_293452_(directionIn);
         }

         this.f_291195_ = counterIn;
      }

      void setDirections(int directionsIn, C_4687_ directionIn) {
         this.f_290314_ = this.f_290314_ | directionsIn | 1 << directionIn.ordinal();
      }

      public void initialize(C_4687_ facingIn, int counter) {
         this.f_291521_ = facingIn != null ? 1 << facingIn.ordinal() : 0;
         this.f_290314_ = 0;
         this.f_291195_ = counter;
      }

      public String toString() {
         return this.f_291755_.m_295500_() + "";
      }

      boolean m_294495_(C_4687_ directionIn) {
         return (this.f_290314_ & 1 << directionIn.ordinal()) > 0;
      }

      void m_293452_(C_4687_ directionIn) {
         this.f_291521_ = (byte)(this.f_291521_ | this.f_291521_ | 1 << directionIn.ordinal());
      }

      @C_140994_
      protected boolean m_295060_(int dirIn) {
         return (this.f_291521_ & 1 << dirIn) > 0;
      }

      boolean m_292787_() {
         return this.f_291521_ != 0;
      }

      public int hashCode() {
         return this.f_291755_.m_295500_().hashCode();
      }

      public boolean equals(Object p_equals_1_) {
         return p_equals_1_ instanceof C_290263_.C_290165_ sectionocclusiongraph$node
            ? this.f_291755_.m_295500_().equals(sectionocclusiongraph$node.f_291755_.m_295500_())
            : false;
      }
   }

   static class C_290171_ {
      public final C_290263_.C_290086_ f_291257_;
      public final Set<C_290263_.C_290165_> f_291495_;
      public final Long2ObjectMap<List<C_290152_.C_290138_>> f_290746_;
      public final Vec3M vec3M1 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M2 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M3 = new Vec3M(0.0, 0.0, 0.0);
      public final BlockPosM blockPosM1 = new BlockPosM();

      public C_290171_(int capacityIn) {
         this.f_291257_ = new C_290263_.C_290086_(capacityIn);
         this.f_291495_ = new ObjectLinkedOpenHashSet(capacityIn);
         this.f_290746_ = new Long2ObjectOpenHashMap();
      }

      public String toString() {
         return "sectionToNode: " + this.f_291257_ + ", renderSections: " + this.f_291495_ + ", sectionsWaiting: " + this.f_290746_;
      }
   }

   static record C_290172_(LongSet f_291517_, BlockingQueue<C_290152_.C_290138_> f_290616_) {
      public C_290172_() {
         this(new LongOpenHashSet(), new LinkedBlockingQueue());
      }
   }

   static record C_290246_(C_290263_.C_290171_ f_290555_, C_290263_.C_290172_ f_291329_) {
      public C_290246_(int storage) {
         this(new C_290263_.C_290171_(storage), new C_290263_.C_290172_());
      }
   }
}
