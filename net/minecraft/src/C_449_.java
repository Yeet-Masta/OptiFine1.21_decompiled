package net.minecraft.src;

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
import net.optifine.Config;
import net.optifine.util.PacketRunnable;
import org.slf4j.Logger;

public abstract class C_449_ implements C_141020_, C_450_, Executor {
   private final String f_18680_;
   private static final Logger f_18681_ = LogUtils.getLogger();
   private final Queue f_18682_ = Queues.newConcurrentLinkedQueue();
   private int f_18683_;

   protected C_449_(String nameIn) {
      this.f_18680_ = nameIn;
      C_141017_.f_146067_.m_146072_(this);
   }

   protected abstract Runnable m_6681_(Runnable var1);

   protected abstract boolean m_6362_(Runnable var1);

   public boolean m_18695_() {
      return Thread.currentThread() == this.m_6304_();
   }

   protected abstract Thread m_6304_();

   protected boolean m_5660_() {
      return !this.m_18695_();
   }

   public int m_18696_() {
      return this.f_18682_.size();
   }

   public String m_7326_() {
      return this.f_18680_;
   }

   public CompletableFuture m_18691_(Supplier supplier) {
      return this.m_5660_() ? CompletableFuture.supplyAsync(supplier, this) : CompletableFuture.completedFuture(supplier.get());
   }

   private CompletableFuture m_18689_(Runnable taskIn) {
      return CompletableFuture.supplyAsync(() -> {
         taskIn.run();
         return null;
      }, this);
   }

   @CheckReturnValue
   public CompletableFuture m_18707_(Runnable taskIn) {
      if (this.m_5660_()) {
         return this.m_18689_(taskIn);
      } else {
         taskIn.run();
         return CompletableFuture.completedFuture((Object)null);
      }
   }

   public void m_18709_(Runnable taskIn) {
      if (!this.m_18695_()) {
         this.m_18689_(taskIn).join();
      } else {
         taskIn.run();
      }

   }

   public void m_6937_(Runnable taskIn) {
      this.f_18682_.add(taskIn);
      LockSupport.unpark(this.m_6304_());
   }

   public void execute(Runnable p_execute_1_) {
      if (this.m_5660_()) {
         this.m_6937_(this.m_6681_(p_execute_1_));
      } else {
         p_execute_1_.run();
      }

   }

   public void m_201446_(Runnable runnableIn) {
      this.execute(runnableIn);
   }

   protected void m_18698_() {
      this.f_18682_.clear();
   }

   protected void m_18699_() {
      int count = Integer.MAX_VALUE;
      if (Config.isLazyChunkLoading() && this == C_3391_.m_91087_()) {
         count = this.getTaskCount();
      }

      while(this.m_7245_()) {
         --count;
         if (count <= 0) {
            break;
         }
      }

   }

   public boolean m_7245_() {
      Runnable r = (Runnable)this.f_18682_.peek();
      if (r == null) {
         return false;
      } else if (this.f_18683_ == 0 && !this.m_6362_(r)) {
         return false;
      } else {
         this.m_6367_((Runnable)this.f_18682_.remove());
         return true;
      }
   }

   public void m_18701_(BooleanSupplier isDone) {
      ++this.f_18683_;

      try {
         while(!isDone.getAsBoolean()) {
            if (!this.m_7245_()) {
               this.m_5667_();
            }
         }
      } finally {
         --this.f_18683_;
      }

   }

   public void m_5667_() {
      Thread.yield();
      LockSupport.parkNanos("waiting for tasks", 100000L);
   }

   protected void m_6367_(Runnable taskIn) {
      try {
         taskIn.run();
      } catch (Exception var3) {
         f_18681_.error(LogUtils.FATAL_MARKER, "Error executing task on {}", this.m_7326_(), var3);
      }

   }

   public List m_142754_() {
      return ImmutableList.of(C_141012_.m_146009_(this.f_18680_ + "-pending-tasks", C_141011_.EVENT_LOOPS, this::m_18696_));
   }

   private int getTaskCount() {
      if (this.f_18682_.isEmpty()) {
         return 0;
      } else {
         Runnable[] rs = (Runnable[])this.f_18682_.toArray(new Runnable[this.f_18682_.size()]);
         double chunkUpdateWeight = this.getChunkUpdateWeight(rs);
         if (chunkUpdateWeight < 5.0) {
            return Integer.MAX_VALUE;
         } else {
            int queueSize = rs.length;
            int fps = Math.max(Config.getFpsAverage(), 1);
            double weight = (double)(queueSize * 10 / fps);
            int count = this.getCount(rs, weight);
            return count;
         }
      }
   }

   private int getCount(Runnable[] rs, double maxWeight) {
      double weight = 0.0;

      for(int i = 0; i < rs.length; ++i) {
         Runnable r = rs[i];
         weight += this.getChunkUpdateWeight(r);
         if (weight > maxWeight) {
            return i + 1;
         }
      }

      return rs.length;
   }

   private double getChunkUpdateWeight(Runnable[] rs) {
      double weight = 0.0;

      for(int i = 0; i < rs.length; ++i) {
         Runnable r = rs[i];
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
