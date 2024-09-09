import com.google.common.collect.ImmutableList;
import com.google.common.collect.Queues;
import com.mojang.logging.LogUtils;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import javax.annotation.CheckReturnValue;
import net.minecraft.src.C_141011_;
import net.minecraft.src.C_141012_;
import net.minecraft.src.C_141017_;
import net.minecraft.src.C_141020_;
import net.minecraft.src.C_183120_;
import net.minecraft.src.C_3391_;
import net.minecraft.src.C_450_;
import net.minecraft.src.C_5028_;
import net.minecraft.src.C_5065_;
import net.minecraft.src.C_5073_;
import net.optifine.Config;
import net.optifine.util.PacketRunnable;
import org.slf4j.Logger;

public abstract class BlockableEventLoop<R extends Runnable> implements C_141020_, C_450_<R>, Executor {
   private final String b;
   private static final Logger c = LogUtils.getLogger();
   private final Queue<R> d = Queues.newConcurrentLinkedQueue();
   private int e;

   protected BlockableEventLoop(String nameIn) {
      this.b = nameIn;
      C_141017_.f_146067_.m_146072_(this);
   }

   protected abstract R f(Runnable var1);

   protected abstract boolean e(R var1);

   public boolean bx() {
      return Thread.currentThread() == this.az();
   }

   protected abstract Thread az();

   protected boolean ay() {
      return !this.bx();
   }

   public int by() {
      return this.d.size();
   }

   public String m_7326_() {
      return this.b;
   }

   public <V> CompletableFuture<V> a(Supplier<V> supplier) {
      return this.ay() ? CompletableFuture.supplyAsync(supplier, this) : CompletableFuture.completedFuture(supplier.get());
   }

   private CompletableFuture<Void> a(Runnable taskIn) {
      return CompletableFuture.supplyAsync(() -> {
         taskIn.run();
         return null;
      }, this);
   }

   @CheckReturnValue
   public CompletableFuture<Void> g(Runnable taskIn) {
      if (this.ay()) {
         return this.a(taskIn);
      } else {
         taskIn.run();
         return CompletableFuture.completedFuture(null);
      }
   }

   public void h(Runnable taskIn) {
      if (!this.bx()) {
         this.a(taskIn).join();
      } else {
         taskIn.run();
      }
   }

   public void i(R taskIn) {
      this.d.add(taskIn);
      LockSupport.unpark(this.az());
   }

   public void execute(Runnable p_execute_1_) {
      if (this.ay()) {
         this.i(this.f(p_execute_1_));
      } else {
         p_execute_1_.run();
      }
   }

   public void c(Runnable runnableIn) {
      this.execute(runnableIn);
   }

   protected void bA() {
      this.d.clear();
   }

   protected void bB() {
      int count = Integer.MAX_VALUE;
      if (Config.isLazyChunkLoading() && this == C_3391_.m_91087_()) {
         count = this.getTaskCount();
      }

      while (this.B()) {
         if (--count <= 0) {
            break;
         }
      }
   }

   public boolean B() {
      R r = (R)this.d.peek();
      if (r == null) {
         return false;
      } else if (this.e == 0 && !this.e(r)) {
         return false;
      } else {
         this.d((R)this.d.remove());
         return true;
      }
   }

   public void b(BooleanSupplier isDone) {
      this.e++;

      try {
         while (!isDone.getAsBoolean()) {
            if (!this.B()) {
               this.A();
            }
         }
      } finally {
         this.e--;
      }
   }

   public void A() {
      Thread.yield();
      LockSupport.parkNanos("waiting for tasks", 100000L);
   }

   protected void d(R taskIn) {
      try {
         taskIn.run();
      } catch (Exception var3) {
         c.error(LogUtils.FATAL_MARKER, "Error executing task on {}", this.m_7326_(), var3);
      }
   }

   public List<C_141012_> m_142754_() {
      return ImmutableList.of(C_141012_.m_146009_(this.b + "-pending-tasks", C_141011_.EVENT_LOOPS, this::by));
   }

   private int getTaskCount() {
      if (this.d.isEmpty()) {
         return 0;
      } else {
         R[] rs = (R[])this.d.toArray(new Runnable[this.d.size()]);
         double chunkUpdateWeight = this.getChunkUpdateWeight(rs);
         if (chunkUpdateWeight < 5.0) {
            return Integer.MAX_VALUE;
         } else {
            int queueSize = rs.length;
            int fps = Math.max(Config.getFpsAverage(), 1);
            double weight = (double)(queueSize * 10 / fps);
            return this.getCount(rs, weight);
         }
      }
   }

   private int getCount(R[] rs, double maxWeight) {
      double weight = 0.0;

      for (int i = 0; i < rs.length; i++) {
         R r = rs[i];
         weight += this.getChunkUpdateWeight(r);
         if (weight > maxWeight) {
            return i + 1;
         }
      }

      return rs.length;
   }

   private double getChunkUpdateWeight(R[] rs) {
      double weight = 0.0;

      for (int i = 0; i < rs.length; i++) {
         R r = rs[i];
         weight += this.getChunkUpdateWeight(r);
      }

      return weight;
   }

   private double getChunkUpdateWeight(Runnable r) {
      if (r instanceof PacketRunnable pr) {
         C_5028_ p = pr.getPacket();
         if (p instanceof C_183120_) {
            return 1.0;
         }

         if (p instanceof C_5073_) {
            return 0.2;
         }

         if (p instanceof C_5065_) {
            return 2.6;
         }
      }

      return 0.0;
   }
}
