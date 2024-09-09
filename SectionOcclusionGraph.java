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
import net.minecraft.src.C_140994_;
import net.minecraft.src.C_141183_;
import net.minecraft.src.C_290036_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_4675_;
import net.minecraft.src.C_4710_;
import net.optifine.BlockPosM;
import net.optifine.Vec3M;
import org.slf4j.Logger;

public class SectionOcclusionGraph {
   private static final Logger a = LogUtils.getLogger();
   private static final Direction[] b = Direction.values();
   private static final int c = 60;
   private static final double d = Math.ceil(Math.sqrt(3.0) * 16.0);
   private boolean e = true;
   @Nullable
   private Future<?> f;
   @Nullable
   private ViewArea g;
   private final AtomicReference<SectionOcclusionGraph.b> h = new AtomicReference();
   private final AtomicReference<SectionOcclusionGraph.a> i = new AtomicReference();
   private final AtomicBoolean j = new AtomicBoolean(false);
   private LevelRenderer levelRenderer;

   public void a(@Nullable ViewArea viewAreaIn) {
      if (this.f != null) {
         try {
            this.f.get();
            this.f = null;
         } catch (Exception var3) {
            a.warn("Full update failed", var3);
         }
      }

      this.g = viewAreaIn;
      this.levelRenderer = C_3391_.m_91087_().f;
      if (viewAreaIn != null) {
         this.h.set(new SectionOcclusionGraph.b(viewAreaIn.f.length));
         this.a();
      } else {
         this.h.set(null);
      }
   }

   public void a() {
      this.e = true;
   }

   public void a(Frustum frustumIn, List<SectionRenderDispatcher.b> sectionsIn) {
      this.addSectionsInFrustum(frustumIn, sectionsIn, true, -1);
   }

   public void addSectionsInFrustum(Frustum frustumIn, List<SectionRenderDispatcher.b> sectionsIn, boolean updateSections, int maxChunkDistance) {
      List<SectionRenderDispatcher.b> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
      List<SectionRenderDispatcher.b> renderInfosTileEntities = this.levelRenderer.getRenderInfosTileEntities();
      int cameraChunkX = (int)frustumIn.getCameraX() >> 4 << 4;
      int cameraChunkY = (int)frustumIn.getCameraY() >> 4 << 4;
      int cameraChunkZ = (int)frustumIn.getCameraZ() >> 4 << 4;
      int maxChunkDistSq = maxChunkDistance * maxChunkDistance;

      for (SectionOcclusionGraph.d sectionocclusiongraph$node : ((SectionOcclusionGraph.b)this.h.get()).a().b) {
         if (frustumIn.a(sectionocclusiongraph$node.a.b())) {
            if (maxChunkDistance > 0) {
               C_4675_ posChunk = sectionocclusiongraph$node.a.f();
               int dx = cameraChunkX - posChunk.m_123341_();
               int dy = cameraChunkY - posChunk.m_123342_();
               int dz = cameraChunkZ - posChunk.m_123343_();
               int chunkDistSq = dx * dx + dy * dy + dz * dz;
               if (chunkDistSq > maxChunkDistSq) {
                  continue;
               }
            }

            if (updateSections) {
               sectionsIn.add(sectionocclusiongraph$node.a);
            }

            SectionRenderDispatcher.a compiledChunk = sectionocclusiongraph$node.a.d();
            if (!compiledChunk.a()) {
               renderInfosTerrain.add(sectionocclusiongraph$node.a);
            }

            if (!compiledChunk.b().isEmpty()) {
               renderInfosTileEntities.add(sectionocclusiongraph$node.a);
            }
         }
      }
   }

   public boolean b() {
      return this.j.compareAndSet(true, false);
   }

   public void a(ChunkPos chunkPosIn) {
      SectionOcclusionGraph.a sectionocclusiongraph$graphevents = (SectionOcclusionGraph.a)this.i.get();
      if (sectionocclusiongraph$graphevents != null) {
         this.a(sectionocclusiongraph$graphevents, chunkPosIn);
      }

      SectionOcclusionGraph.a sectionocclusiongraph$graphevents1 = ((SectionOcclusionGraph.b)this.h.get()).b;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         this.a(sectionocclusiongraph$graphevents1, chunkPosIn);
      }
   }

   public void a(SectionRenderDispatcher.b sectionIn) {
      SectionOcclusionGraph.a sectionocclusiongraph$graphevents = (SectionOcclusionGraph.a)this.i.get();
      if (sectionocclusiongraph$graphevents != null) {
         sectionocclusiongraph$graphevents.b.add(sectionIn);
      }

      SectionOcclusionGraph.a sectionocclusiongraph$graphevents1 = ((SectionOcclusionGraph.b)this.h.get()).b;
      if (sectionocclusiongraph$graphevents1 != sectionocclusiongraph$graphevents) {
         sectionocclusiongraph$graphevents1.b.add(sectionIn);
      }

      if (sectionIn.d().hasTerrainBlockEntities()) {
         this.j.set(true);
      }
   }

   public void a(boolean renderManyIn, Camera cameraIn, Frustum frustumIn, List<SectionRenderDispatcher.b> sectionsIn) {
      Vec3 vec3 = cameraIn.b();
      if (this.e && (this.f == null || this.f.isDone())) {
         this.a(renderManyIn, cameraIn, vec3);
      }

      this.a(renderManyIn, frustumIn, sectionsIn, vec3);
   }

   private void a(boolean renderManyIn, Camera cameraIn, Vec3 viewPosIn) {
      this.e = false;
      this.f = Util.g().submit(() -> {
         SectionOcclusionGraph.b sectionocclusiongraph$graphstate = new SectionOcclusionGraph.b(this.g.f.length);
         this.i.set(sectionocclusiongraph$graphstate.b);
         Queue<SectionOcclusionGraph.d> queue = Queues.newArrayDeque();
         this.a(cameraIn, queue);
         queue.forEach(nodeIn -> sectionocclusiongraph$graphstate.a.a.a(nodeIn.a, nodeIn));
         this.a(sectionocclusiongraph$graphstate.a, viewPosIn, queue, renderManyIn, sectionIn -> {
         });
         this.h.set(sectionocclusiongraph$graphstate);
         this.i.set(null);
         this.j.set(true);
      });
   }

   private void a(boolean renderManyIn, Frustum frustumIn, List<SectionRenderDispatcher.b> sectionsIn, Vec3 viewPosIn) {
      SectionOcclusionGraph.b sectionocclusiongraph$graphstate = (SectionOcclusionGraph.b)this.h.get();
      this.a(sectionocclusiongraph$graphstate);
      if (!sectionocclusiongraph$graphstate.b.b.isEmpty()) {
         Queue<SectionOcclusionGraph.d> queue = Queues.newArrayDeque();

         while (!sectionocclusiongraph$graphstate.b.b.isEmpty()) {
            SectionRenderDispatcher.b sectionrenderdispatcher$rendersection = (SectionRenderDispatcher.b)sectionocclusiongraph$graphstate.b.b.poll();
            SectionOcclusionGraph.d sectionocclusiongraph$node = sectionocclusiongraph$graphstate.a.a.a(sectionrenderdispatcher$rendersection);
            if (sectionocclusiongraph$node != null && sectionocclusiongraph$node.a == sectionrenderdispatcher$rendersection) {
               queue.add(sectionocclusiongraph$node);
            }
         }

         List<SectionRenderDispatcher.b> renderInfos = this.levelRenderer.getRenderInfos();
         List<SectionRenderDispatcher.b> renderInfosTerrain = this.levelRenderer.getRenderInfosTerrain();
         List<SectionRenderDispatcher.b> renderInfosTileEntities = this.levelRenderer.getRenderInfosTileEntities();
         Frustum frustum = LevelRenderer.a(frustumIn);
         Consumer<SectionRenderDispatcher.b> consumer = sectionIn -> {
            if (frustum.a(sectionIn.b())) {
               sectionsIn.add(sectionIn);
               if (sectionIn == renderInfos) {
                  SectionRenderDispatcher.a compiledChunk = (SectionRenderDispatcher.a)sectionIn.c.get();
                  if (!compiledChunk.a()) {
                     renderInfosTerrain.add(sectionIn);
                  }

                  if (!compiledChunk.b().isEmpty()) {
                     renderInfosTileEntities.add(sectionIn);
                  }
               }
            }
         };
         this.a(sectionocclusiongraph$graphstate.a, viewPosIn, queue, renderManyIn, consumer);
      }
   }

   private void a(SectionOcclusionGraph.b stateIn) {
      LongIterator longiterator = stateIn.b.a.iterator();

      while (longiterator.hasNext()) {
         long i = longiterator.nextLong();
         List<SectionRenderDispatcher.b> list = (List<SectionRenderDispatcher.b>)stateIn.a.c.get(i);
         if (list != null && ((SectionRenderDispatcher.b)list.get(0)).a()) {
            stateIn.b.b.addAll(list);
            stateIn.a.c.remove(i);
         }
      }

      stateIn.b.a.clear();
   }

   private void a(SectionOcclusionGraph.a eventsIn, ChunkPos chunkPosIn) {
      eventsIn.a.add(ChunkPos.c(chunkPosIn.e - 1, chunkPosIn.f));
      eventsIn.a.add(ChunkPos.c(chunkPosIn.e, chunkPosIn.f - 1));
      eventsIn.a.add(ChunkPos.c(chunkPosIn.e + 1, chunkPosIn.f));
      eventsIn.a.add(ChunkPos.c(chunkPosIn.e, chunkPosIn.f + 1));
   }

   private void a(Camera cameraIn, Queue<SectionOcclusionGraph.d> nodesIn) {
      int i = 16;
      Vec3 vec3 = cameraIn.b();
      C_4675_ blockpos = cameraIn.c();
      SectionRenderDispatcher.b sectionrenderdispatcher$rendersection = this.g.a(blockpos);
      if (sectionrenderdispatcher$rendersection == null) {
         C_141183_ levelheightaccessor = this.g.c();
         boolean flag = blockpos.m_123342_() > levelheightaccessor.m_141937_();
         int j = flag ? levelheightaccessor.m_151558_() - 8 : levelheightaccessor.m_141937_() + 8;
         int k = Mth.a(vec3.c / 16.0) * 16;
         int l = Mth.a(vec3.e / 16.0) * 16;
         int i1 = this.g.b();
         List<SectionOcclusionGraph.d> list = Lists.newArrayList();

         for (int j1 = -i1; j1 <= i1; j1++) {
            for (int k1 = -i1; k1 <= i1; k1++) {
               SectionRenderDispatcher.b sectionrenderdispatcher$rendersection1 = this.g
                  .a(new C_4675_(k + C_4710_.m_175554_(j1, 8), j, l + C_4710_.m_175554_(k1, 8)));
               if (sectionrenderdispatcher$rendersection1 != null && this.a(blockpos, sectionrenderdispatcher$rendersection1.f())) {
                  Direction direction = flag ? Direction.a : Direction.b;
                  SectionOcclusionGraph.d sectionocclusiongraph$node = sectionrenderdispatcher$rendersection1.getRenderInfo(direction, 0);
                  sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.d, direction);
                  if (j1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.d, Direction.f);
                  } else if (j1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.d, Direction.e);
                  }

                  if (k1 > 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.d, Direction.d);
                  } else if (k1 < 0) {
                     sectionocclusiongraph$node.setDirections(sectionocclusiongraph$node.d, Direction.c);
                  }

                  list.add(sectionocclusiongraph$node);
               }
            }
         }

         list.sort(Comparator.comparingDouble(nodeIn -> blockpos.m_123331_(nodeIn.a.f().m_7918_(8, 8, 8))));
         nodesIn.addAll(list);
      } else {
         nodesIn.add(sectionrenderdispatcher$rendersection.getRenderInfo(null, 0));
      }
   }

   private void a(
      SectionOcclusionGraph.c storageIn,
      Vec3 viewPosIn,
      Queue<SectionOcclusionGraph.d> nodesIn,
      boolean renderManyIn,
      Consumer<SectionRenderDispatcher.b> consumerIn
   ) {
      int i = 16;
      C_4675_ blockpos = new C_4675_(Mth.a(viewPosIn.c / 16.0) * 16, Mth.a(viewPosIn.d / 16.0) * 16, Mth.a(viewPosIn.e / 16.0) * 16);
      C_4675_ blockpos1 = blockpos.m_7918_(8, 8, 8);

      while (!nodesIn.isEmpty()) {
         SectionOcclusionGraph.d sectionocclusiongraph$node = (SectionOcclusionGraph.d)nodesIn.poll();
         SectionRenderDispatcher.b sectionrenderdispatcher$rendersection = sectionocclusiongraph$node.a;
         if (storageIn.b.add(sectionocclusiongraph$node)) {
            consumerIn.accept(sectionocclusiongraph$node.a);
         }

         boolean flag = Math.abs(sectionrenderdispatcher$rendersection.f().m_123341_() - blockpos.m_123341_()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.f().m_123342_() - blockpos.m_123342_()) > 60
            || Math.abs(sectionrenderdispatcher$rendersection.f().m_123343_() - blockpos.m_123343_()) > 60;

         for (Direction direction : b) {
            SectionRenderDispatcher.b sectionrenderdispatcher$rendersection1 = this.a(blockpos, sectionrenderdispatcher$rendersection, direction);
            if (sectionrenderdispatcher$rendersection1 != null && (!renderManyIn || !sectionocclusiongraph$node.a(direction.g()))) {
               if (renderManyIn && sectionocclusiongraph$node.a()) {
                  SectionRenderDispatcher.a sectionrenderdispatcher$compiledsection = sectionrenderdispatcher$rendersection.d();
                  boolean flag1 = false;

                  for (int j = 0; j < b.length; j++) {
                     if (sectionocclusiongraph$node.a(j) && sectionrenderdispatcher$compiledsection.a(b[j].g(), direction)) {
                        flag1 = true;
                        break;
                     }
                  }

                  if (!flag1) {
                     continue;
                  }
               }

               if (renderManyIn && flag) {
                  C_4675_ blockpos2 = sectionrenderdispatcher$rendersection1.f();
                  C_4675_ blockpos3 = blockpos2.m_7918_(
                     (direction.o() == Direction.a.a ? blockpos1.m_123341_() > blockpos2.m_123341_() : blockpos1.m_123341_() < blockpos2.m_123341_()) ? 16 : 0,
                     (direction.o() == Direction.a.b ? blockpos1.m_123342_() > blockpos2.m_123342_() : blockpos1.m_123342_() < blockpos2.m_123342_()) ? 16 : 0,
                     (direction.o() == Direction.a.c ? blockpos1.m_123343_() > blockpos2.m_123343_() : blockpos1.m_123343_() < blockpos2.m_123343_()) ? 16 : 0
                  );
                  Vec3 vec31 = new Vec3((double)blockpos3.m_123341_(), (double)blockpos3.m_123342_(), (double)blockpos3.m_123343_());
                  Vec3 vec3 = viewPosIn.d(vec31).d().a(d);
                  boolean flag2 = true;

                  while (viewPosIn.d(vec31).g() > 3600.0) {
                     vec31 = vec31.e(vec3);
                     C_141183_ levelheightaccessor = this.g.c();
                     if (vec31.d > (double)levelheightaccessor.m_151558_() || vec31.d < (double)levelheightaccessor.m_141937_()) {
                        break;
                     }

                     SectionRenderDispatcher.b sectionrenderdispatcher$rendersection2 = this.g.a(storageIn.blockPosM1.setXyz(vec31.c, vec31.d, vec31.e));
                     if (sectionrenderdispatcher$rendersection2 == null || storageIn.a.a(sectionrenderdispatcher$rendersection2) == null) {
                        flag2 = false;
                        break;
                     }
                  }

                  if (!flag2) {
                     continue;
                  }
               }

               SectionOcclusionGraph.d sectionocclusiongraph$node1 = storageIn.a.a(sectionrenderdispatcher$rendersection1);
               if (sectionocclusiongraph$node1 != null) {
                  sectionocclusiongraph$node1.b(direction);
               } else {
                  SectionOcclusionGraph.d sectionocclusiongraph$node2 = sectionrenderdispatcher$rendersection1.getRenderInfo(
                     direction, sectionocclusiongraph$node.b + 1
                  );
                  sectionocclusiongraph$node2.setDirections(sectionocclusiongraph$node.d, direction);
                  if (sectionrenderdispatcher$rendersection1.a()) {
                     nodesIn.add(sectionocclusiongraph$node2);
                     storageIn.a.a(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                  } else if (this.a(blockpos, sectionrenderdispatcher$rendersection1.f())) {
                     storageIn.a.a(sectionrenderdispatcher$rendersection1, sectionocclusiongraph$node2);
                     ((List)storageIn.c.computeIfAbsent(ChunkPos.a(sectionrenderdispatcher$rendersection1.f()), posLongIn -> new ArrayList()))
                        .add(sectionrenderdispatcher$rendersection1);
                  }
               }
            }
         }
      }
   }

   private boolean a(C_4675_ blockPos1, C_4675_ blockPos2) {
      int i = C_4710_.m_123171_(blockPos1.m_123341_());
      int j = C_4710_.m_123171_(blockPos1.m_123343_());
      int k = C_4710_.m_123171_(blockPos2.m_123341_());
      int l = C_4710_.m_123171_(blockPos2.m_123343_());
      return C_290036_.m_294571_(i, j, this.g.b(), k, l);
   }

   @Nullable
   private SectionRenderDispatcher.b a(C_4675_ blockPosIn, SectionRenderDispatcher.b sectionIn, Direction dirIn) {
      C_4675_ blockpos = sectionIn.a(dirIn);
      ClientLevel world = this.levelRenderer.u;
      if (blockpos.m_123342_() < world.m_141937_() || blockpos.m_123342_() >= world.m_151558_()) {
         return null;
      } else if (Mth.a(blockPosIn.m_123342_() - blockpos.m_123342_()) > this.levelRenderer.renderDistance) {
         return null;
      } else {
         int dxs = blockPosIn.m_123341_() - blockpos.m_123341_();
         int dzs = blockPosIn.m_123343_() - blockpos.m_123343_();
         int distSq = dxs * dxs + dzs * dzs;
         return distSq > this.levelRenderer.renderDistanceXZSq ? null : this.g.a(blockpos);
      }
   }

   @Nullable
   @C_140994_
   protected SectionOcclusionGraph.d b(SectionRenderDispatcher.b sectionIn) {
      return ((SectionOcclusionGraph.b)this.h.get()).a.a.a(sectionIn);
   }

   public boolean needsFrustumUpdate() {
      return this.j.get();
   }

   public void setNeedsFrustumUpdate(boolean val) {
      this.j.set(val);
   }

   static record a(LongSet a, BlockingQueue<SectionRenderDispatcher.b> b) {
      public a() {
         this(new LongOpenHashSet(), new LinkedBlockingQueue());
      }
   }

   static record b(SectionOcclusionGraph.c a, SectionOcclusionGraph.a b) {
      public b(int storage) {
         this(new SectionOcclusionGraph.c(storage), new SectionOcclusionGraph.a());
      }
   }

   static class c {
      public final SectionOcclusionGraph.e a;
      public final Set<SectionOcclusionGraph.d> b;
      public final Long2ObjectMap<List<SectionRenderDispatcher.b>> c;
      public final Vec3M vec3M1 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M2 = new Vec3M(0.0, 0.0, 0.0);
      public final Vec3M vec3M3 = new Vec3M(0.0, 0.0, 0.0);
      public final BlockPosM blockPosM1 = new BlockPosM();

      public c(int capacityIn) {
         this.a = new SectionOcclusionGraph.e(capacityIn);
         this.b = new ObjectLinkedOpenHashSet(capacityIn);
         this.c = new Long2ObjectOpenHashMap();
      }

      public String toString() {
         return "sectionToNode: " + this.a + ", renderSections: " + this.b + ", sectionsWaiting: " + this.c;
      }
   }

   @C_140994_
   public static class d {
      @C_140994_
      public final SectionRenderDispatcher.b a;
      private int c;
      int d;
      @C_140994_
      protected int b;

      public d(SectionRenderDispatcher.b sectionIn, @Nullable Direction directionIn, int counterIn) {
         this.a = sectionIn;
         if (directionIn != null) {
            this.b(directionIn);
         }

         this.b = counterIn;
      }

      void setDirections(int directionsIn, Direction directionIn) {
         this.d = this.d | directionsIn | 1 << directionIn.ordinal();
      }

      public void initialize(Direction facingIn, int counter) {
         this.c = facingIn != null ? 1 << facingIn.ordinal() : 0;
         this.d = 0;
         this.b = counter;
      }

      public String toString() {
         return this.a.f() + "";
      }

      boolean a(Direction directionIn) {
         return (this.d & 1 << directionIn.ordinal()) > 0;
      }

      void b(Direction directionIn) {
         this.c = (byte)(this.c | this.c | 1 << directionIn.ordinal());
      }

      @C_140994_
      protected boolean a(int dirIn) {
         return (this.c & 1 << dirIn) > 0;
      }

      boolean a() {
         return this.c != 0;
      }

      public int hashCode() {
         return this.a.f().hashCode();
      }

      public boolean equals(Object p_equals_1_) {
         return p_equals_1_ instanceof SectionOcclusionGraph.d sectionocclusiongraph$node ? this.a.f().equals(sectionocclusiongraph$node.a.f()) : false;
      }
   }

   static class e {
      private final SectionOcclusionGraph.d[] a;

      e(int sizeIn) {
         this.a = new SectionOcclusionGraph.d[sizeIn];
      }

      public void a(SectionRenderDispatcher.b sectionIn, SectionOcclusionGraph.d nodeIn) {
         this.a[sectionIn.b] = nodeIn;
      }

      @Nullable
      public SectionOcclusionGraph.d a(SectionRenderDispatcher.b sectionIn) {
         int i = sectionIn.b;
         return i >= 0 && i < this.a.length ? this.a[i] : null;
      }
   }
}
