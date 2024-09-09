package net.minecraft.client.renderer;

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
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkTrackingView;
import net.minecraft.util.VisibleForDebug;
import net.minecraft.world.level.LevelHeightAccessor;
import net.optifine.BlockPosM;
import net.optifine.Vec3M;
import org.slf4j.Logger;

public class SectionOcclusionGraph {
   private static final Logger f_290342_ = LogUtils.getLogger();
   private static final net.minecraft.core.Direction[] f_291333_ = net.minecraft.core.Direction.values();
   private static final int f_291236_ = 60;
   private static final double f_291614_ = Math.ceil(Math.sqrt(3.0) * 16.0);
   private boolean f_290608_ = true;
   @Nullable
   private Future<?> f_291408_;
   @Nullable
   private net.minecraft.client.renderer.ViewArea f_290643_;
   private final AtomicReference<net.minecraft.client.renderer.SectionOcclusionGraph.GraphState> f_291855_ = new AtomicReference();
   private final AtomicReference<net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents> f_291476_ = new AtomicReference();
   private final AtomicBoolean f_291462_ = new AtomicBoolean(false);
   private net.minecraft.client.renderer.LevelRenderer levelRenderer;

   public void m_295341_(@Nullable net.minecraft.client.renderer.ViewArea viewAreaIn) {
      if (this.f_291408_ != null) {
         try {
            this.f_291408_.get();
            this.f_291408_ = null;
         } catch (Exception var3) {
            f_290342_.warn("Full update failed", var3);
         }
      }

      this.f_290643_ = viewAreaIn;
      this.levelRenderer = Minecraft.m_91087_().f_91060_;
      if (viewAreaIn != null) {
         this.f_291855_.set(new net.minecraft.client.renderer.SectionOcclusionGraph.GraphState(viewAreaIn.f_291707_.length));
         this.m_295966_();
      } else {
         this.f_291855_.set(null);
      }
   }

   public void m_295966_() {
      this.f_290608_ = true;
   }

   public void m_295738_(
      net.minecraft.client.renderer.culling.Frustum frustumIn, List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> sectionsIn
   ) {
      this.addSectionsInFrustum(frustumIn, sectionsIn, true, -1);
   }

   public void addSectionsInFrustum(
      net.minecraft.client.renderer.culling.Frustum frustumIn,
      List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> sectionsIn,
      boolean updateSections,
      int maxChunkDistance
   ) {
      List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
      List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> renderInfosTileEntities = this.levelRenderer.getRenderInfosTileEntities();
      int cameraChunkX = (int)frustumIn.getCameraX() >> 4 << 4;
      int cameraChunkY = (int)frustumIn.getCameraY() >> 4 << 4;
      int cameraChunkZ = (int)frustumIn.getCameraZ() >> 4 << 4;
      int maxChunkDistSq = maxChunkDistance * maxChunkDistance;

      for (net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node : ((net.minecraft.client.renderer.SectionOcclusionGraph.GraphState)this.f_291855_
            .get())
         .f_290555_()
         .f_291495_) {
         if (frustumIn.m_113029_(sectionocclusiongraph$node.f_291755_.m_293301_())) {
            if (maxChunkDistance > 0) {
               BlockPos posChunk = sectionocclusiongraph$node.f_291755_.m_295500_();
               int dx = cameraChunkX - posChunk.m_123341_();
               int dy = cameraChunkY - posChunk.m_123342_();
               int dz = cameraChunkZ - posChunk.m_123343_();
               int chunkDistSq = dx * dx + dy * dy + dz * dz;
               if (chunkDistSq > maxChunkDistSq) {
                  continue;
               }
            }

            if (updateSections) {
               sectionsIn.add(sectionocclusiongraph$node.f_291755_);
            }

            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection compiledChunk = sectionocclusiongraph$node.f_291755_.m_293175_();
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

   public void m_294751_(net.minecraft.world.level.ChunkPos chunkPosIn) {
      net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents sectionocclusiongraph$graphevents = (net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents)this.f_291476_
         .get();
      if (sectionocclusiongraph$graphevents != null) {
         this.m_294370_(sectionocclusiongraph$graphevents, chunkPosIn);
      }

      net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents sectionocclusiongraph$graphevents1 = ((net.minecraft.client.renderer.SectionOcclusionGraph.GraphState)this.f_291855_
            .get())
         .f_291329_;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         this.m_294370_(sectionocclusiongraph$graphevents1, chunkPosIn);
      }
   }

   public void m_293743_(net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn) {
      net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents sectionocclusiongraph$graphevents = (net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents)this.f_291476_
         .get();
      if (sectionocclusiongraph$graphevents != null) {
         sectionocclusiongraph$graphevents.f_290616_.add(sectionIn);
      }

      net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents sectionocclusiongraph$graphevents1 = ((net.minecraft.client.renderer.SectionOcclusionGraph.GraphState)this.f_291855_
            .get())
         .f_291329_;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         sectionocclusiongraph$graphevents1.f_290616_.add(sectionIn);
      }

      if (sectionIn.m_293175_().hasTerrainBlockEntities()) {
         this.f_291462_.set(true);
      }
   }

   public void m_292654_(
      boolean renderManyIn,
      net.minecraft.client.Camera cameraIn,
      net.minecraft.client.renderer.culling.Frustum frustumIn,
      List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> sectionsIn
   ) {
      net.minecraft.world.phys.Vec3 vec3 = cameraIn.m_90583_();
      if (this.f_290608_ && (this.f_291408_ == null || this.f_291408_.isDone())) {
         this.m_295789_(renderManyIn, cameraIn, vec3);
      }

      this.m_293052_(renderManyIn, frustumIn, sectionsIn, vec3);
   }

   private void m_295789_(boolean renderManyIn, net.minecraft.client.Camera cameraIn, net.minecraft.world.phys.Vec3 viewPosIn) {
      this.f_290608_ = false;
      this.f_291408_ = net.minecraft.Util.m_183991_()
         .submit(
            () -> {
               net.minecraft.client.renderer.SectionOcclusionGraph.GraphState sectionocclusiongraph$graphstate = new net.minecraft.client.renderer.SectionOcclusionGraph.GraphState(
                  this.f_290643_.f_291707_.length
               );
               this.f_291476_.set(sectionocclusiongraph$graphstate.f_291329_);
               Queue<net.minecraft.client.renderer.SectionOcclusionGraph.Node> queue = Queues.newArrayDeque();
               this.m_294555_(cameraIn, queue);
               queue.forEach(nodeIn -> sectionocclusiongraph$graphstate.f_290555_.f_291257_.m_294528_(nodeIn.f_291755_, nodeIn));
               this.m_293858_(sectionocclusiongraph$graphstate.f_290555_, viewPosIn, queue, renderManyIn, sectionIn -> {
               });
               this.f_291855_.set(sectionocclusiongraph$graphstate);
               this.f_291476_.set(null);
               this.f_291462_.set(true);
            }
         );
   }

   private void m_293052_(
      boolean renderManyIn,
      net.minecraft.client.renderer.culling.Frustum frustumIn,
      List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> sectionsIn,
      net.minecraft.world.phys.Vec3 viewPosIn
   ) {
      net.minecraft.client.renderer.SectionOcclusionGraph.GraphState sectionocclusiongraph$graphstate = (net.minecraft.client.renderer.SectionOcclusionGraph.GraphState)this.f_291855_
         .get();
      this.m_294187_(sectionocclusiongraph$graphstate);
      if (!sectionocclusiongraph$graphstate.f_291329_.f_290616_.isEmpty()) {
         Queue<net.minecraft.client.renderer.SectionOcclusionGraph.Node> queue = Queues.newArrayDeque();

         while (!sectionocclusiongraph$graphstate.f_291329_.f_290616_.isEmpty()) {
            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection)sectionocclusiongraph$graphstate.f_291329_
               .f_290616_
               .poll();
            net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node = sectionocclusiongraph$graphstate.f_290555_
               .f_291257_
               .m_295569_(sectionrenderdispatcher$rendersection);
            if (sectionocclusiongraph$node != null && sectionocclusiongraph$node.f_291755_ == sectionrenderdispatcher$rendersection) {
               queue.add(sectionocclusiongraph$node);
            }
         }

         List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> renderInfos = this.levelRenderer.getRenderInfos();
         List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
         List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> renderInfosTileEntities = this.levelRenderer
            .getRenderInfosTileEntities();
         net.minecraft.client.renderer.culling.Frustum frustum = net.minecraft.client.renderer.LevelRenderer.m_295345_(frustumIn);
         Consumer<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> consumer = sectionIn -> {
            if (frustum.m_113029_(sectionIn.m_293301_())) {
               sectionsIn.add(sectionIn);
               if (sectionIn == renderInfos) {
                  net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection compiledChunk = (net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection)sectionIn.f_290312_
                     .get();
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

   private void m_294187_(net.minecraft.client.renderer.SectionOcclusionGraph.GraphState stateIn) {
      LongIterator longiterator = stateIn.f_291329_.f_291517_.iterator();

      while (longiterator.hasNext()) {
         long i = longiterator.nextLong();
         List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> list = (List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection>)stateIn.f_290555_
            .f_290746_
            .get(i);
         if (list != null && ((net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection)list.get(0)).m_294718_()) {
            stateIn.f_291329_.f_290616_.addAll(list);
            stateIn.f_290555_.f_290746_.remove(i);
         }
      }

      stateIn.f_291329_.f_291517_.clear();
   }

   private void m_294370_(net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents eventsIn, net.minecraft.world.level.ChunkPos chunkPosIn) {
      eventsIn.f_291517_.add(net.minecraft.world.level.ChunkPos.m_45589_(chunkPosIn.f_45578_ - 1, chunkPosIn.f_45579_));
      eventsIn.f_291517_.add(net.minecraft.world.level.ChunkPos.m_45589_(chunkPosIn.f_45578_, chunkPosIn.f_45579_ - 1));
      eventsIn.f_291517_.add(net.minecraft.world.level.ChunkPos.m_45589_(chunkPosIn.f_45578_ + 1, chunkPosIn.f_45579_));
      eventsIn.f_291517_.add(net.minecraft.world.level.ChunkPos.m_45589_(chunkPosIn.f_45578_, chunkPosIn.f_45579_ + 1));
   }

   private void m_294555_(net.minecraft.client.Camera cameraIn, Queue<net.minecraft.client.renderer.SectionOcclusionGraph.Node> nodesIn) {
      int i = 16;
      net.minecraft.world.phys.Vec3 vec3 = cameraIn.m_90583_();
      BlockPos blockpos = cameraIn.m_90588_();
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection = this.f_290643_.m_292642_(blockpos);
      if (sectionrenderdispatcher$rendersection == null) {
         LevelHeightAccessor levelheightaccessor = this.f_290643_.m_294982_();
         boolean flag = blockpos.m_123342_() > levelheightaccessor.m_141937_();
         int j = flag ? levelheightaccessor.m_151558_() - 8 : levelheightaccessor.m_141937_() + 8;
         int k = net.minecraft.util.Mth.m_14107_(vec3.f_82479_ / 16.0) * 16;
         int l = net.minecraft.util.Mth.m_14107_(vec3.f_82481_ / 16.0) * 16;
         int i1 = this.f_290643_.m_295654_();
         List<net.minecraft.client.renderer.SectionOcclusionGraph.Node> list = Lists.newArrayList();

         for (int j1 = -i1; j1 <= i1; j1++) {
            for (int k1 = -i1; k1 <= i1; k1++) {
               net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection1 = this.f_290643_
                  .m_292642_(new BlockPos(k + SectionPos.m_175554_(j1, 8), j, l + SectionPos.m_175554_(k1, 8)));
               if (sectionrenderdispatcher$rendersection1 != null && this.m_294269_(blockpos, sectionrenderdispatcher$rendersection1.m_295500_())) {
                  net.minecraft.core.Direction direction = flag ? net.minecraft.core.Direction.DOWN : net.minecraft.core.Direction.UP;
                  net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node = sectionrenderdispatcher$rendersection1.getRenderInfo(
                     direction, 0
                  );
                  sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, direction);
                  if (j1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, net.minecraft.core.Direction.EAST);
                  } else if (j1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, net.minecraft.core.Direction.WEST);
                  }

                  if (k1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, net.minecraft.core.Direction.SOUTH);
                  } else if (k1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.f_290314_, net.minecraft.core.Direction.NORTH);
                  }

                  list.add(sectionocclusiongraph$node);
               }
            }
         }

         list.sort(Comparator.comparingDouble(nodeIn -> blockpos.m_123331_(nodeIn.f_291755_.m_295500_().m_7918_(8, 8, 8))));
         nodesIn.addAll(list);
      } else {
         nodesIn.add(sectionrenderdispatcher$rendersection.getRenderInfo(null, 0));
      }
   }

   private void m_293858_(
      net.minecraft.client.renderer.SectionOcclusionGraph.GraphStorage storageIn,
      net.minecraft.world.phys.Vec3 viewPosIn,
      Queue<net.minecraft.client.renderer.SectionOcclusionGraph.Node> nodesIn,
      boolean renderManyIn,
      Consumer<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> consumerIn
   ) {
      int i = 16;
      BlockPos blockpos = new BlockPos(
         net.minecraft.util.Mth.m_14107_(viewPosIn.f_82479_ / 16.0) * 16,
         net.minecraft.util.Mth.m_14107_(viewPosIn.f_82480_ / 16.0) * 16,
         net.minecraft.util.Mth.m_14107_(viewPosIn.f_82481_ / 16.0) * 16
      );
      BlockPos blockpos1 = blockpos.m_7918_(8, 8, 8);

      while (!nodesIn.isEmpty()) {
         net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node = (net.minecraft.client.renderer.SectionOcclusionGraph.Node)nodesIn.poll();
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection = sectionocclusiongraph$node.f_291755_;
         if (storageIn.f_291495_.add(sectionocclusiongraph$node)) {
            consumerIn.accept(sectionocclusiongraph$node.f_291755_);
         }

         boolean flag = Math.abs(sectionrenderdispatcher$rendersection.m_295500_().m_123341_() - blockpos.m_123341_()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.m_295500_().m_123342_() - blockpos.m_123342_()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.m_295500_().m_123343_() - blockpos.m_123343_()) > 60;

         for (net.minecraft.core.Direction direction : f_291333_) {
            net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection1 = this.m_295926_(
               blockpos, sectionrenderdispatcher$rendersection, direction
            );
            if (sectionrenderdispatcher$rendersection1 != null && (!renderManyIn || !sectionocclusiongraph$node.m_294495_(direction.m_122424_()))) {
               if (renderManyIn && sectionocclusiongraph$node.m_292787_()) {
                  net.minecraft.client.renderer.chunk.SectionRenderDispatcher.CompiledSection sectionrenderdispatcher$compiledsection = sectionrenderdispatcher$rendersection.m_293175_();
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
                  BlockPos blockpos2 = sectionrenderdispatcher$rendersection1.m_295500_();
                  BlockPos blockpos3 = blockpos2.m_7918_(
                     (
                           direction.m_122434_() == net.minecraft.core.Direction.Axis.X
                              ? blockpos1.m_123341_() > blockpos2.m_123341_()
                              : blockpos1.m_123341_() < blockpos2.m_123341_()
                        )
                        ? 16
                        : 0,
                     (
                           direction.m_122434_() == net.minecraft.core.Direction.Axis.Y
                              ? blockpos1.m_123342_() > blockpos2.m_123342_()
                              : blockpos1.m_123342_() < blockpos2.m_123342_()
                        )
                        ? 16
                        : 0,
                     (
                           direction.m_122434_() == net.minecraft.core.Direction.Axis.Z
                              ? blockpos1.m_123343_() > blockpos2.m_123343_()
                              : blockpos1.m_123343_() < blockpos2.m_123343_()
                        )
                        ? 16
                        : 0
                  );
                  net.minecraft.world.phys.Vec3 vec31 = new net.minecraft.world.phys.Vec3(
                     (double)blockpos3.m_123341_(), (double)blockpos3.m_123342_(), (double)blockpos3.m_123343_()
                  );
                  net.minecraft.world.phys.Vec3 vec3 = viewPosIn.m_82546_(vec31).m_82541_().m_82490_(f_291614_);
                  boolean flag2 = true;

                  while (viewPosIn.m_82546_(vec31).m_82556_() > 3600.0) {
                     vec31 = vec31.m_82549_(vec3);
                     LevelHeightAccessor levelheightaccessor = this.f_290643_.m_294982_();
                     if (vec31.f_82480_ > (double)levelheightaccessor.m_151558_() || vec31.f_82480_ < (double)levelheightaccessor.m_141937_()) {
                        break;
                     }

                     net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionrenderdispatcher$rendersection2 = this.f_290643_
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

               net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node1 = storageIn.f_291257_
                  .m_295569_(sectionrenderdispatcher$rendersection1);
               if (sectionocclusiongraph$node1 != null) {
                  sectionocclusiongraph$node1.m_293452_(direction);
               } else {
                  net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node2 = sectionrenderdispatcher$rendersection1.getRenderInfo(
                     direction, sectionocclusiongraph$node.f_291195_ + 1
                  );
                  sectionocclusiongraph$node2.setDirections(sectionocclusiongraph$node.f_290314_, direction);
                  if (sectionrenderdispatcher$rendersection1.m_294718_()) {
                     nodesIn.add(sectionocclusiongraph$node2);
                     storageIn.f_291257_.m_294528_(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                  } else if (this.m_294269_(blockpos, sectionrenderdispatcher$rendersection1.m_295500_())) {
                     storageIn.f_291257_.m_294528_(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                     ((List)storageIn.f_290746_
                           .computeIfAbsent(
                              net.minecraft.world.level.ChunkPos.m_151388_(sectionrenderdispatcher$rendersection1.m_295500_()), posLongIn -> new ArrayList()
                           ))
                        .add(sectionrenderdispatcher$rendersection1);
                  }
               }
            }
         }
      }
   }

   private boolean m_294269_(BlockPos blockPos1, BlockPos blockPos2) {
      int i = SectionPos.m_123171_(blockPos1.m_123341_());
      int j = SectionPos.m_123171_(blockPos1.m_123343_());
      int k = SectionPos.m_123171_(blockPos2.m_123341_());
      int l = SectionPos.m_123171_(blockPos2.m_123343_());
      return ChunkTrackingView.m_294571_(i, j, this.f_290643_.m_295654_(), k, l);
   }

   @Nullable
   private net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection m_295926_(
      BlockPos blockPosIn, net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn, net.minecraft.core.Direction dirIn
   ) {
      BlockPos blockpos = sectionIn.m_292593_(dirIn);
      net.minecraft.client.multiplayer.ClientLevel world = this.levelRenderer.f_109465_;
      if (blockpos.m_123342_() < world.m_141937_() || blockpos.m_123342_() >= world.m_151558_()) {
         return null;
      } else if (net.minecraft.util.Mth.m_14040_(blockPosIn.m_123342_() - blockpos.m_123342_()) > this.levelRenderer.renderDistance) {
         return null;
      } else {
         int dxs = blockPosIn.m_123341_() - blockpos.m_123341_();
         int dzs = blockPosIn.m_123343_() - blockpos.m_123343_();
         int distSq = dxs * dxs + dzs * dzs;
         return distSq > this.levelRenderer.renderDistanceXZSq ? null : this.f_290643_.m_292642_(blockpos);
      }
   }

   @Nullable
   @VisibleForDebug
   protected net.minecraft.client.renderer.SectionOcclusionGraph.Node m_292796_(
      net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn
   ) {
      return ((net.minecraft.client.renderer.SectionOcclusionGraph.GraphState)this.f_291855_.get()).f_290555_.f_291257_.m_295569_(sectionIn);
   }

   public boolean needsFrustumUpdate() {
      return this.f_291462_.get();
   }

   public void setNeedsFrustumUpdate(boolean val) {
      this.f_291462_.set(val);
   }

   static record GraphEvents(LongSet f_291517_, BlockingQueue<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection> f_290616_) {
      public GraphEvents() {
         this(new LongOpenHashSet(), new LinkedBlockingQueue());
      }
   }

   static record GraphState(
      net.minecraft.client.renderer.SectionOcclusionGraph.GraphStorage f_290555_, net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents f_291329_
   ) {
      public GraphState(int storage) {
         this(
            new net.minecraft.client.renderer.SectionOcclusionGraph.GraphStorage(storage),
            new net.minecraft.client.renderer.SectionOcclusionGraph.GraphEvents()
         );
      }
   }

   static class GraphStorage {
      public final net.minecraft.client.renderer.SectionOcclusionGraph.SectionToNodeMap f_291257_;
      public final Set<net.minecraft.client.renderer.SectionOcclusionGraph.Node> f_291495_;
      public final Long2ObjectMap<List<net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection>> f_290746_;
      public final Vec3M vec3M1 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M2 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M3 = new Vec3M(0.0, 0.0, 0.0);
      public final BlockPosM blockPosM1 = new BlockPosM();

      public GraphStorage(int capacityIn) {
         this.f_291257_ = new net.minecraft.client.renderer.SectionOcclusionGraph.SectionToNodeMap(capacityIn);
         this.f_291495_ = new ObjectLinkedOpenHashSet(capacityIn);
         this.f_290746_ = new Long2ObjectOpenHashMap();
      }

      public String toString() {
         return "sectionToNode: " + this.f_291257_ + ", renderSections: " + this.f_291495_ + ", sectionsWaiting: " + this.f_290746_;
      }
   }

   @VisibleForDebug
   public static class Node {
      @VisibleForDebug
      public final net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection f_291755_;
      private int f_291521_;
      int f_290314_;
      @VisibleForDebug
      protected int f_291195_;

      public Node(
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn, @Nullable net.minecraft.core.Direction directionIn, int counterIn
      ) {
         this.f_291755_ = sectionIn;
         if (directionIn != null) {
            this.m_293452_(directionIn);
         }

         this.f_291195_ = counterIn;
      }

      void setDirections(int directionsIn, net.minecraft.core.Direction directionIn) {
         this.f_290314_ = this.f_290314_ | directionsIn | 1 << directionIn.ordinal();
      }

      public void initialize(net.minecraft.core.Direction facingIn, int counter) {
         this.f_291521_ = facingIn != null ? 1 << facingIn.ordinal() : 0;
         this.f_290314_ = 0;
         this.f_291195_ = counter;
      }

      public String toString() {
         return this.f_291755_.m_295500_() + "";
      }

      boolean m_294495_(net.minecraft.core.Direction directionIn) {
         return (this.f_290314_ & 1 << directionIn.ordinal()) > 0;
      }

      void m_293452_(net.minecraft.core.Direction directionIn) {
         this.f_291521_ = (byte)(this.f_291521_ | this.f_291521_ | 1 << directionIn.ordinal());
      }

      @VisibleForDebug
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
         return p_equals_1_ instanceof net.minecraft.client.renderer.SectionOcclusionGraph.Node sectionocclusiongraph$node
            ? this.f_291755_.m_295500_().equals(sectionocclusiongraph$node.f_291755_.m_295500_())
            : false;
      }
   }

   static class SectionToNodeMap {
      private final net.minecraft.client.renderer.SectionOcclusionGraph.Node[] f_291767_;

      SectionToNodeMap(int sizeIn) {
         this.f_291767_ = new net.minecraft.client.renderer.SectionOcclusionGraph.Node[sizeIn];
      }

      public void m_294528_(
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn, net.minecraft.client.renderer.SectionOcclusionGraph.Node nodeIn
      ) {
         this.f_291767_[sectionIn.f_290488_] = nodeIn;
      }

      @Nullable
      public net.minecraft.client.renderer.SectionOcclusionGraph.Node m_295569_(
         net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection sectionIn
      ) {
         int i = sectionIn.f_290488_;
         return i >= 0 && i < this.f_291767_.length ? this.f_291767_[i] : null;
      }
   }
}
